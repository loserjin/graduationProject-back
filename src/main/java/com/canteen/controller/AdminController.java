package com.canteen.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.lang.Result;
import com.canteen.entity.Admin;
import com.canteen.service.AdminService;
import com.canteen.util.JwtUtils;
import com.canteen.util.ShiroUtils;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 毕业设计
 * @since 2021-03-02
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    JwtUtils jwtUtils;


    @RequiresAuthentication
    @GetMapping("infos")
    public Result list (@RequestParam (defaultValue="1")Integer current,
                        @RequestParam (defaultValue="5")Integer size,
                        @RequestParam (defaultValue = "")String adminName,
                        @RequestParam(defaultValue = "0")Integer departmentId,
                        @RequestParam(defaultValue = "0")Integer departmentfloorId,
                        HttpServletRequest request){
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);

        Page page = new Page(current,size);
        //超级管理员查看全部
        if (aadmin.getDepartmentId().intValue()==1){
            IPage pageDate = adminService.page(page,new QueryWrapper<Admin>()
                    .eq(departmentId.intValue()!=0,"departmentId",departmentId)
                    .eq(departmentfloorId.intValue()!=0,"departmentfloorId",departmentfloorId)
                    .like("adminName", adminName).orderByAsc("adminId"));
            return Result.succ(pageDate);
        }else
            if (aadmin.getAdminRole().intValue()==1 && aadmin.getDepartmentId().intValue()!=1){
                //所属饭堂超级管理员查看自己饭堂的
                IPage pageDate = adminService.page(page,new QueryWrapper<Admin>()
                        .eq("departmentId",aadmin.getDepartmentId())
                        .eq(departmentfloorId.intValue()!=0,"departmentfloorId",departmentfloorId)
                        .like("adminName", adminName)
                        .orderByAsc("adminId"));
                return Result.succ(pageDate);
        }else {
            //普通管理员查看自己楼层的
            IPage pageDate = adminService.page(page,new QueryWrapper<Admin>()
                    .eq("departmentfloorId",aadmin.getDepartmentfloorId())
                    .like("adminName", adminName)
                    .orderByAsc("adminId"));
            return Result.succ(pageDate);
        }
    }

    @RequiresAuthentication
    @GetMapping("/info")
    public Result index(@RequestParam Integer adminId){
        Admin admin= adminService.getById(adminId);
        Assert.notNull(admin,"该记录不存在");
        return Result.succ(admin);
    }

    @RequiresAuthentication
    @PostMapping("/edit")
    public Result save(@Validated @RequestBody  Admin admin,
                       HttpServletRequest request){
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
        //超级管理员或者所属饭堂超级管理员才可更改
        if (aadmin.getAdminRole().intValue()==1){
            admin.setAdminPwd(SecureUtil.md5(admin.getAdminPwd()));
            adminService.saveOrUpdate(admin);
            return  Result.succ(null);
        }else
            return Result.fail("没有权限编辑");
    }

//    @RequiresAuthentication
//    @PostMapping("/edit")
//    public Result save(@Validated @RequestBody List<Admin> list){
//        for (Admin admin:list){
//            if (admin.getAdminRole()==null||admin.getAdminRole()==0) {
//                adminService.saveOrUpdate(admin);
//            }
//        }
//        return Result.succ(null);
//    }


    @RequiresAuthentication
    @PostMapping("delect")
    public  Result delect(@RequestParam Integer adminId,
                          HttpServletRequest request){
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
        if (aadmin.getAdminRole().equals(1)){
            Admin admin= adminService.getById(adminId);
            adminService.removeById(adminId);
            Assert.notNull(admin,"该条记录不存在");
            return Result.succ("删除成功");
        }else
            return Result.fail("没有权限");

    }


}
