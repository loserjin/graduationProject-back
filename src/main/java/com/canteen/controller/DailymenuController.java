package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.lang.Result;
import com.canteen.entity.Dailymenu;
import com.canteen.entity.Menu;
import com.canteen.service.DailymenuService;
import com.canteen.service.MenuService;
import com.canteen.util.ShiroUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    //查询今日菜谱
    @RequiresAuthentication
    @GetMapping("/infos")
    public Result list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "") String date,
            @RequestParam(defaultValue = "0") Integer departmentfloorId){
        Page page = new Page(current, size);
        if (departmentfloorId.intValue()==0) {
            IPage pageDate = dailymenuService.page(page, new QueryWrapper<Dailymenu>().like("dailymenuCreatime",date));
            return Result.succ(pageDate);
        }else {
            IPage pageDate = dailymenuService.page(page,new QueryWrapper<Dailymenu>().eq("departmentfloorId",departmentfloorId).like("dailymenuCreatime",date));
            return Result.succ(pageDate);
        }

    }



    //增加今日菜品

    @RequiresAuthentication
    @PostMapping("/edits")

    public Result save(@RequestParam Integer menuId,
                       @RequestParam (defaultValue = "") @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date) {
        //将要导入的菜单ID信息寻找出来然后复制到每日菜单的表中
        Dailymenu temp=new Dailymenu();
       if (ShiroUtils.getProfile().getAdminRole().equals(1)||menuService.getById(menuId).getDepartmentfloorId().equals(ShiroUtils.getProfile().getDepartmentfloorId()))
       {
           BeanUtils.copyProperties(menuService.getById(menuId),temp,"adminName","adminId","menuCreatime");
           temp.setDailymenuCreatime(date);
       }
        dailymenuService.saveOrUpdate(temp);
        return Result.succ(null);
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
