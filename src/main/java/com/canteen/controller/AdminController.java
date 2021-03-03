package com.canteen.controller;


import com.canteen.common.lang.Result;
import com.canteen.entity.Admin;
import com.canteen.service.AdminService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @RequiresAuthentication

    @GetMapping("/index")
    public Result index(){
       Admin admin= adminService.getById(2);
       return Result.succ(admin);
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody  Admin admin){
        return  Result.succ(admin);
    }

}
