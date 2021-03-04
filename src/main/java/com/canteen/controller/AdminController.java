package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @RequiresAuthentication
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
        if (admin.getAdminRole()==null||admin.getAdminRole()==0){
            adminService.saveOrUpdate(admin);
            return  Result.succ(null);
        }else
            return Result.fail("编辑的管理员角色错误");
    }
    @RequiresAuthentication
    @GetMapping("infos")
    public Result list (@RequestParam (defaultValue="1")Integer current,@RequestParam (defaultValue="5")Integer size){
        Page page = new Page(current,size);
        IPage pageDate = adminService.page(page,new QueryWrapper<Admin>().eq("adminRole",0));
        return Result.succ(pageDate);
    }

}
