package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.lang.Result;
import com.canteen.entity.Admin;
import com.canteen.entity.Discuss;
import com.canteen.entity.Notice;
import com.canteen.service.AdminService;
import com.canteen.service.DiscussService;
import com.canteen.util.JwtUtils;
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
 * @since 2021-05-11
 */
@RestController
@RequestMapping("/discuss")
public class DiscussController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AdminService adminService;
    @Autowired
    DiscussService discussService;

    //查询评论资料
    @RequiresAuthentication
    @GetMapping("/infos")
    public Result list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "0") Integer departmentId,
            @RequestParam(defaultValue = "0") Integer departmentfloorId,
            HttpServletRequest request) {
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
//        Assert.isTrue(aadmin.getAdminRole().intValue()!=1,"权限不足");
        if (aadmin.getAdminRole().intValue()==1){
            if (aadmin.getDepartmentfloorId().intValue()!=1){
                departmentId=aadmin.getDepartmentId();
            }
        }else {
            departmentfloorId=aadmin.getDepartmentfloorId();
            departmentId=aadmin.getDepartmentId();
        }
        Page page = new Page(current, size);
        IPage pageDate = discussService.page(page, new QueryWrapper<Discuss>()
                .eq(departmentId.intValue()!=0,"departmentId",departmentId)
                .eq(departmentfloorId.intValue()!=0,"departmentfloorId",departmentfloorId)
                .orderByAsc("departmentId"));
        return Result.succ(pageDate);
    }


   //删除评论
    @RequiresAuthentication
    @PostMapping("delect")
    public  Result delect(@RequestParam Integer discussId,
                          HttpServletRequest request){
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
        if (aadmin.getAdminRole().intValue()==1){
            Discuss discuss=discussService.getById(discussId);
            Assert.notNull(discuss,"该条记录不存在");
            discussService.removeById(discussId);
            return Result.succ("删除成功");
        } else
            return Result.fail("没有权限");
    }
}

