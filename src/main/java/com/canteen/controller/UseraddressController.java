package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.lang.Result;
import com.canteen.entity.Admin;
import com.canteen.entity.Useraddress;
import com.canteen.service.UseraddressService;
import com.canteen.util.JwtUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 毕业设计
 * @since 2021-05-03
 */
@RestController
@RequestMapping("/useraddress")
public class UseraddressController {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UseraddressService useraddressService;

    @RequiresAuthentication
    @GetMapping("infos")
    public Result list (@RequestParam(defaultValue="1")Integer current,
                        @RequestParam (defaultValue="5")Integer size,
                        HttpServletRequest request){
        String jwt=request.getHeader("Authorization");
        String  openId=jwtUtils.getClaimByToken(jwt).getSubject();
        Page page = new Page(current,size);
        IPage pageDate = useraddressService.page(page,new QueryWrapper<Useraddress>().eq("openId",openId));
            return Result.succ(pageDate);
    }

    @RequiresAuthentication
    @GetMapping("/info")
    public Result index(@RequestParam Integer useraddressId){
        Useraddress useraddress=useraddressService.getById(useraddressId);
        Assert.notNull(useraddress,"该记录不存在");
        return Result.succ(useraddress);
    }

    @RequiresAuthentication
    @PostMapping("/edit")
    public Result save(@RequestBody  Useraddress useraddress,
                       HttpServletRequest request){
        String jwt=request.getHeader("Authorization");
        String  openId=jwtUtils.getClaimByToken(jwt).getSubject();
        useraddress.setOpenId(openId);
        useraddressService.saveOrUpdate(useraddress);
        return Result.succ("编辑成功");
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
    public  Result delect(@RequestParam Integer useraddressId,
                          HttpServletRequest request){
        Useraddress useraddress= useraddressService.getById(useraddressId);
        useraddressService.removeById(useraddressId);
        Assert.notNull(useraddress,"该条记录不存在");
        return Result.succ("删除成功");
    }

}
