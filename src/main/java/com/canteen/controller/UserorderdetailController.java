package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.lang.Result;
import com.canteen.entity.Admin;
import com.canteen.entity.Userorder;
import com.canteen.entity.Userorderdetail;
import com.canteen.service.AdminService;
import com.canteen.service.UserorderdetailService;
import com.canteen.util.JwtUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 毕业设计
 * @since 2021-05-04
 */
@RestController
@RequestMapping("/userorderdetail")
public class UserorderdetailController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AdminService adminService;
    @Autowired
    UserorderdetailService userorderdetailService;

    @RequiresAuthentication
    @GetMapping("/infos")
    public Result list(  @RequestParam(defaultValue = "5") Integer size,
                         @RequestParam(defaultValue = "1") Integer current,
                         @RequestParam(defaultValue = "") String userorderId) {
        Page page = new Page(current, size);
        IPage pageDate = userorderdetailService.page(page, new QueryWrapper<Userorderdetail>().eq("userorderId", userorderId));
        return Result.succ(pageDate);
    }
}
