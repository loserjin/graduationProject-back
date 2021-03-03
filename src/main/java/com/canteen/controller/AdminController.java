package com.canteen.controller;


import com.canteen.common.lang.Result;
import com.canteen.entity.Admin;
import com.canteen.service.AdminService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
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

    @PostMapping("/edit")
    public Result save(@Validated @RequestBody  Admin admin){
//        Admin temp = null;
//        if (admin.getAdminId()!=null){
//            temp=adminService.getById(admin.getAdminId());
//            Assert.isTrue(temp.getAdminStatus()==1,"没有权限编辑");
//        }else{
//            temp=new Admin();
//            temp.setAdminStatus(1);
//        }
//        BeanUtils.copyProperties(admin,temp,"adminId","adminCreatime");
        adminService.saveOrUpdate(admin);
        return  Result.succ(null);
    }

}
