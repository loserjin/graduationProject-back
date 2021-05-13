package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.dto.UserorderDto;
import com.canteen.common.lang.Result;
import com.canteen.entity.*;
import com.canteen.service.AdminService;
import com.canteen.service.DailymenuService;

import com.canteen.service.UserorderService;
import com.canteen.service.UserorderdetailService;
import com.canteen.util.JwtUtils;
import com.canteen.util.Pager;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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
@RequestMapping("/userorder")
public class UserorderController {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserorderService userorderService;

    @Autowired
    DailymenuService dailymenuService;

    @Autowired
    UserorderdetailService userorderdetailService;
    @Autowired
    AdminService adminService;


    @RequiresAuthentication
    @GetMapping("/infos")
    public Result list(HttpServletRequest request,
                       @RequestParam(defaultValue = "5") Integer size,
                       @RequestParam(defaultValue = "1") Integer current,
                       @RequestParam(defaultValue = "0") Integer departmentId,
                       @RequestParam(defaultValue = "0") Integer departmentfloorId,
                       @RequestParam(defaultValue = "") String name,
                       @RequestParam(defaultValue = "") String date,
                       @RequestParam(defaultValue = "5") Integer time) {
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
        Page page = new Page(current, size);
        if (aadmin.getAdminRole().intValue()==1){
            if (aadmin.getDepartmentId().intValue()==1){
                IPage pageDate = userorderService.page(page, new QueryWrapper<Userorder>()
                        .eq(departmentId.intValue()!=0,"departmentId",departmentId)
                        .eq(departmentfloorId.intValue()!=0,"departmentfloorId",departmentfloorId)
                        .eq(time.intValue()!=5,"dailymenuTime",time)
                        .like("dailymenuCreatime", date)
                        .like("useraddressName", name)
                        .orderByAsc("dailymenuCreatime"));
                return Result.succ(pageDate);
            }else{
                IPage pageDate = userorderService.page(page, new QueryWrapper<Userorder>()
                        .eq("departmentId",aadmin.getDepartmentId())
                        .eq(departmentfloorId.intValue()!=0,"departmentfloorId",departmentfloorId)
                        .eq(time.intValue()!=5,"dailymenuTime",time)
                        .like("dailymenuCreatime", date)
                        .like("useraddressName", name)
                        .orderByAsc("dailymenuCreatime"));
                return Result.succ(pageDate);
            }
        }else {
            IPage pageDate = userorderService.page(page, new QueryWrapper<Userorder>()
                    .eq("departmentId",aadmin.getDepartmentId())
                    .eq("departmentfloorId",aadmin.getDepartmentfloorId())
                    .eq(time.intValue()!=5,"dailymenuTime",time)
                    .like("dailymenuCreatime", date)
                    .like("useraddressName", name)
                    .orderByAsc("dailymenuCreatime"));
            return Result.succ(pageDate);
        }
    }

}
