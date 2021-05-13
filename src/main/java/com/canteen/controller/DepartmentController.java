package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.lang.Result;
import com.canteen.entity.Admin;
import com.canteen.entity.Department;
import com.canteen.entity.Purchase;
import com.canteen.service.AdminService;
import com.canteen.service.DepartmentService;
import com.canteen.service.PurchaseService;
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
 * @since 2021-03-14
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AdminService adminService;

//查询饭堂资料
    @RequiresAuthentication
    @GetMapping("/infos")
    public Result list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "") String departmentName,
            @RequestParam(defaultValue = "0") Integer departmentId,
            HttpServletRequest request) {
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
        Page page = new Page(current, size);
        if (aadmin.getAdminRole().intValue()==1){
            if (aadmin.getDepartmentId()==1){
                IPage pageDate = departmentService.page(page, new QueryWrapper<Department>()
                        .eq(departmentId.intValue()!=0,"departmentId",departmentId)
                        .like("departmentName", departmentName)
                        .orderByAsc("departmentId"));
                return Result.succ(pageDate);
            }else{
                IPage pageDate = departmentService.page(page, new QueryWrapper<Department>()
                        .eq("departmentId",aadmin.getDepartmentId())
                        .like("departmentName", departmentName));
                return Result.succ(pageDate);
            }
        }else {
            IPage pageDate = departmentService.page(page, new QueryWrapper<Department>()
                    .eq("departmentId",aadmin.getDepartmentId())
                    .like("departmentName", departmentName));
            return Result.succ(pageDate);
        }
    }
    @RequiresAuthentication
    @GetMapping("/info")
    public Result index(@RequestParam Integer departmentId){
        Department department=departmentService.getById(departmentId);
        Assert.notNull(department,"该记录不存在");
        return Result.succ(department);
    }

//编辑饭堂资料
    @RequiresAuthentication
    @PostMapping("/edit")
    public Result save(@RequestBody Department department,
                       HttpServletRequest request) {
        //不是超级管理员无法编辑
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
        if (aadmin.getAdminRole().intValue() == 1) {
            departmentService.saveOrUpdate(department);
            return Result.succ("修改成功");
        } else {
            return Result.fail("没有权限");
        }

    }


    @RequiresAuthentication
    @PostMapping("delect")
    public  Result delect(@RequestParam Integer departmentId,
                          HttpServletRequest request){
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
        if (aadmin.getAdminRole().intValue()==1&&aadmin.getDepartmentId().intValue()==1){
            Department department =departmentService.getById(departmentId);
            departmentService.removeById(departmentId);
            Assert.notNull(department,"该条记录不存在");
            return Result.succ("null");
        } else
            return Result.fail("没有权限");

    }
}

