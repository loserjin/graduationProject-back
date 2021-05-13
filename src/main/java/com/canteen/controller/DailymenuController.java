package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.lang.Result;
import com.canteen.entity.*;
import com.canteen.mapper.DailymenuMapper;
import com.canteen.service.AdminService;
import com.canteen.service.DailymenuService;
import com.canteen.service.MenuService;
import com.canteen.util.JwtUtils;
import com.canteen.util.ShiroUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 毕业设计
 * @since 2021-03-14
 */
@RestController
@RequestMapping("/dailymenu")
public class DailymenuController {
    @Autowired
    DailymenuService dailymenuService;


    @Autowired
    MenuService menuService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AdminService adminService;


    //查询今日菜谱
    @RequiresAuthentication
    @GetMapping("/infos")
    public Result list(@RequestParam (defaultValue="1")Integer current,
                       @RequestParam (defaultValue="5")Integer size,
                       @RequestParam(defaultValue = "") Integer dailymenuId,
                       @RequestParam (defaultValue = "0")Integer departmentfloorId,
                       @RequestParam (defaultValue = "0")Integer departmentId,
                       @RequestParam (defaultValue = "")String menuName,
                       @RequestParam(defaultValue = "") Integer typeId,
                       @RequestParam(defaultValue = "") String date,
                       @RequestParam(defaultValue = "5") Integer time,
                       HttpServletRequest request) {
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
        Page page = new Page(current, size);

        if (aadmin.getAdminRole().intValue()==1){
            if (aadmin.getDepartmentId()==1){
                IPage pageDate = dailymenuService.page(page, new QueryWrapper<Dailymenu>()
                        .eq(departmentId.intValue()!=0,"departmentId",departmentId)
                        .eq(departmentfloorId.intValue()!=0,"departmentfloorId",departmentfloorId)
                        .eq("dailymenuId",dailymenuId)
                        .eq("typeId",typeId)
                        .like("menuName",menuName)
                        .like("dailymenuCreatime",date)
                        .like("dailymenuTime",time)
                        .orderByAsc("dailymenuCreatime")
                        .orderByAsc("dailymenuTime")
                        .orderByAsc("departmentfloorName"));
                return Result.succ(pageDate);
            }else {
                IPage pageDate = dailymenuService.page(page, new QueryWrapper<Dailymenu>()
                        .eq("departmentId",aadmin.getDepartmentId())
                        .eq(departmentfloorId.intValue()!=0,"departmentfloorId",departmentfloorId)
                        .eq("dailymenuId",dailymenuId)
                        .eq("typeId",typeId)
                        .like("menuName",menuName)
                        .like("dailymenuCreatime",date)
                        .like("dailymenuTime",time)
                        .orderByAsc("dailymenuCreatime")
                        .orderByAsc("dailymenuTime")
                        .orderByAsc("departmentfloorName"));
                return  Result.succ(pageDate);
            }
        }else{
            IPage pageDate = dailymenuService.page(page, new QueryWrapper<Dailymenu>()
                    .eq("departmentId",aadmin.getDepartmentId())
                    .eq("departmentfloorId",aadmin.getDepartmentfloorId())
                    .eq("dailymenuId",dailymenuId)
                    .eq("typeId",typeId)
                    .like("menuName",menuName)
                    .like("dailymenuCreatime",date)
                    .like("dailymenuTime",time)
                    .orderByAsc("dailymenuCreatime")
                    .orderByAsc("dailymenuTime")
                    .orderByAsc("departmentfloorName"));
            return Result.succ(pageDate);
        }

    }

    //增加今日菜品
    @RequiresAuthentication
    @PostMapping("/edit")
    public Result save(@RequestParam Integer[] menuId,
                       @RequestParam String dailymenuCreatime,
                       @RequestParam Integer dailymenuTime) {
        Integer flag=0;
        //将要导入的菜单ID信息寻找出来然后复制到每日菜单的表中
        for (int i=0;i<=menuId.length-1;i++)
        {
            Menu menu=menuService.getById(menuId[i]);
            Dailymenu dailymenu=new Dailymenu();
            BeanUtils.copyProperties(menu,dailymenu,"adminId","adminName","menuCreatime");
            //String 转 Localdatetime
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime time = LocalDateTime.parse(dailymenuCreatime+" 00:00:00",df);

            dailymenu.setDailymenuCreatime(time);
            dailymenu.setDailymenuTime(dailymenuTime);
            //查找是否有重复
            Dailymenu date = dailymenuService.getOne(new QueryWrapper<Dailymenu>()
                    .eq("dailymenuCreatime",dailymenuCreatime+" 00:00:00")
                    .eq("dailymenuTime",dailymenuTime)
                    .eq("menuId",menu.getMenuId())
                    .eq("departmentfloorId",menu.getDepartmentfloorId()));
            if (date==null){
                dailymenuService.saveOrUpdate(dailymenu);
                flag=1;
            }
        }
//        for (int i=0;i<=menuId.length;i++){
//            System.out.println(menuId[i]);
//        }
        if (flag==1){
            String eatime;
            if (dailymenuTime==0){
                eatime="早上";
            }else if (dailymenuTime==1){
                eatime="中午";
            }else eatime="晚上";
            return Result.succ("添加"+dailymenuCreatime+","+eatime+"时段菜单成功");
        }else {
            return Result.fail(dailymenuCreatime+","+dailymenuTime+"时段菜单中均有重复的菜");
        }

    }


    @RequiresAuthentication
    @PostMapping("delect")
    public  Result delect(@RequestParam Integer dailymenuId){
        if (ShiroUtils.getProfile().getAdminRole().equals(1)||dailymenuService.getById(dailymenuId).getDepartmentfloorId().equals(ShiroUtils.getProfile().getDepartmentfloorId())){
            Dailymenu dailymenu = dailymenuService.getById(dailymenuId);
            dailymenuService.removeById(dailymenuId);
            Assert.notNull(dailymenu,"该条记录不存在");
            return Result.succ("null");
        } else
            return Result.fail("没有权限");

    }
}
