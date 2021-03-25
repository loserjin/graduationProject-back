package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.lang.Result;
import com.canteen.entity.Admin;
import com.canteen.service.AdminService;
import com.canteen.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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


    @RequiresAuthentication
    @GetMapping("infos")
    public Result list (@RequestParam (defaultValue="1")Integer current,
                        @RequestParam (defaultValue="5")Integer size,
                        @RequestParam (defaultValue = "")String adminName){
        Page page = new Page(current,size);
        IPage pageDate = adminService.page(page,new QueryWrapper<Admin>().eq("adminRole",0).like("adminName", adminName));
        return Result.succ(pageDate);
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
    public Result save(@Validated @RequestBody  Admin admin){
        if (ShiroUtils.getProfile().getAdminRole().equals(1)){
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
    public  Result delect(@RequestParam Integer adminId){
        if (ShiroUtils.getProfile().getAdminRole().equals(1)){
            Admin admin= adminService.getById(adminId);
            adminService.removeById(adminId);
            Assert.notNull(admin,"该条记录不存在");
            return Result.succ("删除成功");
        }else
            return Result.fail("没有权限");

    }
}
