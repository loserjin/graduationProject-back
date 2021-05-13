package com.canteen.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.canteen.common.dto.LoginDto;
import com.canteen.common.lang.Result;
import com.canteen.entity.Admin;
import com.canteen.service.AdminService;
import com.canteen.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountController {

    @Autowired
    AdminService adminService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){

        Admin admin=adminService.getOne(new QueryWrapper<Admin>().eq("adminName",loginDto.getAdminName()));
        Assert.notNull(admin,"用户不存在");

        if (!admin.getAdminPwd().equals(SecureUtil.md5(loginDto.getAdminPwd()))){
            return  Result.fail("密码不正确");
        }
        if (!admin.getAdminRole().equals(loginDto.getAdminRole())){
            return  Result.fail("管理员角色不正确");
        }
        String jwt=jwtUtils.generateToken(admin.getAdminId());
        response.setHeader("Authorization",jwt);
        response.setHeader("Access-control-Expose-Headers","Authorization");

        return Result.succ(MapUtil.builder()
                .put("adminId",admin.getAdminId())
                .put("adminRole",admin.getAdminRole())
                .put("adminName",admin.getAdminName())
                .put("adminTel",admin.getAdminTel())
                .put("adminSex",admin.getAdminSex())
                .put("departmentmentId",admin.getDepartmentId())
                .put("departmentName",admin.getDepartmentName())
                .put("departmentfloorId",admin.getDepartmentfloorId())
                .put("departmentfloorName",admin.getDepartmentfloorName())
                .put("adminCreatime",admin.getAdminCreatime())
                .put("adminStatus",admin.getAdminStatus())
                .map()
        );

    }
    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

}
