package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.lang.Result;
import com.canteen.entity.Admin;
import com.canteen.entity.Departmentfloor;
import com.canteen.service.AdminService;
import com.canteen.service.DepartmentfloorService;
import com.canteen.util.JwtUtils;
import com.canteen.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
 * @since 2021-03-19
 */
@RestController
@RequestMapping("/departmentfloor")
public class DepartmentfloorController {
    @Autowired
    DepartmentfloorService departmentfloorService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AdminService adminService;

    //查询饭堂部门资料
    @RequiresAuthentication
    @GetMapping("/infos")
    public Result list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "") String departmentfloorName,
            @RequestParam(defaultValue = "0")Integer departmentId,
            @RequestParam(defaultValue = "0")Integer departmentfloorId,
            HttpServletRequest request){
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
        Page page = new Page(current, size);
        if (aadmin.getAdminRole().intValue()==1){
            if (aadmin.getDepartmentId()==1){
                IPage pageDate = departmentfloorService.page(page, new QueryWrapper<Departmentfloor>()
                        .eq(departmentId.intValue()!=0,"departmentId",departmentId)
                        .eq(departmentfloorId.intValue()!=0,"departmentfloorId",departmentfloorId)
                        .like("departmentfloorName",departmentfloorName)
                        .orderByAsc("departmentName"));
                return Result.succ(pageDate);
            }else {
                IPage pageDate = departmentfloorService.page(page, new QueryWrapper<Departmentfloor>()
                        .eq("departmentId",aadmin.getDepartmentId())
                        .eq(departmentfloorId.intValue()!=0,"departmentfloorId",departmentfloorId)
                        .like("departmentfloorName",departmentfloorName)
                        .orderByAsc("departmentfloorName"));
                return  Result.succ(pageDate);
            }
        }else{
            IPage pageDate = departmentfloorService.page(page, new QueryWrapper<Departmentfloor>()
                    .eq("departmentId",aadmin.getDepartmentId())
                    .eq("departmentfloorId",aadmin.getDepartmentfloorId()));
            return Result.succ(pageDate);
        }

    }
    @RequiresAuthentication
    @GetMapping("/info")
    public Result index(@RequestParam Integer departmentfloorId){
        Departmentfloor departmentfloor=departmentfloorService.getById(departmentfloorId);
        Assert.notNull(departmentfloor,"该记录不存在");
        return Result.succ(departmentfloor);
    }

    //编辑饭堂部门资料
    @RequiresAuthentication
    @PostMapping("/edit")
    public Result save(@RequestBody Departmentfloor departmentfloor,
                       HttpServletRequest request) {
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
        //不是超级管理员无法编辑
        if (aadmin.getAdminRole().intValue()==1) {
            departmentfloorService.saveOrUpdate(departmentfloor);
            return Result.succ("修改成功");
        } else {
            return Result.fail("没有权限");
        }
    }


    @RequiresAuthentication
    @PostMapping("delect")
    public  Result delect(@RequestParam Integer departmentfloorId,
                          HttpServletRequest request){
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
        if (aadmin.getAdminRole().intValue()==1){
            Departmentfloor departmentfloor =departmentfloorService.getById(departmentfloorId);
            departmentfloorService.removeById(departmentfloorId);
            Assert.notNull(departmentfloor,"该条记录不存在");
            return Result.succ("删除成功");
        } else
            return Result.fail("没有权限");

    }
}
