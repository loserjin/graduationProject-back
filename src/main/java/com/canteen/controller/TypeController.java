package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.lang.Result;
import com.canteen.entity.*;
import com.canteen.service.AdminService;
import com.canteen.service.PurchaseService;
import com.canteen.service.TypeService;
import com.canteen.util.JwtUtils;
import com.canteen.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 毕业设计
 * @since 2021-03-15
 */
@RestController
@RequestMapping("/type")
public class TypeController {
    @Autowired
    TypeService typeService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AdminService adminService;

    @RequiresAuthentication
    @GetMapping("/infos")
    public Result list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "0") Integer departmentId,
            @RequestParam(defaultValue = "0") Integer departmentfloorId,
            @RequestParam(defaultValue = "") String departmentfloorName,
            @RequestParam(defaultValue = "") String departmentName,
            HttpServletRequest request){
        Page page = new Page(current, size);
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
        if (aadmin.getAdminRole().intValue()==1){
            if (aadmin.getDepartmentId()==1){
                IPage pageDate = typeService.page(page, new QueryWrapper<Type>()
                        .eq(departmentId.intValue()!=0,"departmentId",departmentId)
                        .eq(departmentfloorId.intValue()!=0,"departmentfloorId",departmentfloorId)
                        .like("departmentName",departmentName)
                        .like("departmentfloorName",departmentfloorName)
                        .orderByAsc("departmentName")
                        .orderByAsc("departmentfloorName"));
                return Result.succ(pageDate);
            }else {
                IPage pageDate = typeService.page(page, new QueryWrapper<Type>()
                        .eq("departmentId",aadmin.getDepartmentId())
                        .eq(departmentfloorId.intValue()!=0,"departmentfloorId",departmentfloorId)
                        .like("departmentfloorName",departmentfloorName)
                        .orderByAsc("departmentfloorName"));
                return  Result.succ(pageDate);
            }
        }else{
            IPage pageDate = typeService.page(page, new QueryWrapper<Type>()
                    .eq("departmentId",aadmin.getDepartmentId())
                    .eq("departmentfloorId",aadmin.getDepartmentfloorId()));
            return Result.succ(pageDate);
        }
    }

    @RequiresAuthentication
    @GetMapping("/info")
    public Result index(@RequestParam Integer typeId){
        Type type= typeService.getById(typeId);
        Assert.notNull(type,"该记录不存在");
        return Result.succ(type);
    }

    @RequiresAuthentication
    @PostMapping("/edit")
    public Result save(@RequestBody Type type,
                       HttpServletRequest request) {

        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
        if (aadmin.getAdminRole().intValue()==1){
            //超级管理员
            if (aadmin.getDepartmentId().intValue()==1){
                type.setAdminName(aadmin.getAdminName());
                type.setAdminId(aadmin.getAdminId());
                typeService.saveOrUpdate(type);
                return Result.succ("编辑成功");
            }else {
                //所属饭堂超级管理员
                type.setAdminName(aadmin.getAdminName());
                type.setAdminId(aadmin.getAdminId());
                type.setDepartmentId(aadmin.getDepartmentId());
                type.setDepartmentName(aadmin.getDepartmentName());
                typeService.saveOrUpdate(type);
                return Result.succ("编辑成功");
            }
        }else{
            //普通管理员
            type.setAdminName(aadmin.getAdminName());
            type.setAdminId(aadmin.getAdminId());
            type.setDepartmentId(aadmin.getDepartmentId());
            type.setDepartmentName(aadmin.getDepartmentName());
            type.setDepartmentfloorId(aadmin.getDepartmentfloorId());
            type.setDepartmentfloorName(aadmin.getDepartmentfloorName());
            typeService.saveOrUpdate(type);
            return Result.succ("编辑成功");
        }
    }
    @RequiresAuthentication
    @PostMapping("delect")
    public  Result delect(@RequestParam Integer typeId){
        Type type=typeService.getById(typeId);
        if (ShiroUtils.getProfile().getAdminRole().equals(1)||type.getDepartmentfloorId().equals(ShiroUtils.getProfile().getDepartmentfloorId())){
           typeService.removeById(typeId);
            Assert.notNull(type,"该条记录不存在");
            return Result.succ("null");
        }else
            return Result.fail("没有权限");
    }
}
