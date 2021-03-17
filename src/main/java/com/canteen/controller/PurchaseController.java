package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.dto.DateDto;
import com.canteen.common.lang.Result;
import com.canteen.entity.Admin;
import com.canteen.entity.Purchase;
import com.canteen.service.AdminService;
import com.canteen.service.PurchaseService;
import com.canteen.util.ShiroUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.SendHandler;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 毕业设计
 * @since 2021-03-14
 */
@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @RequiresAuthentication
    @GetMapping("/infos")
    public Result list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "") String date) {
        Page page = new Page(current, size);
        IPage pageDate = purchaseService.page(page, new QueryWrapper<Purchase>().like("purchaseCreatime", date));
        return Result.succ(pageDate);
    }

    @RequiresAuthentication
    @PostMapping("/edit")
    public Result save(@RequestBody Purchase purchase) {
        //先判断记录是否存在，如果存在则判断是否超级管理员或者是自己部门的信息，true则可以更改，false则没有权限编辑
        //                   如果不存在则判断是否超级管理员，是超级管理员则可以输入部门信息，不是超级管理员将自身的部门信息导入，
        Purchase temp=null;

        //判断记录是否存在
        if (purchase.getPurchaseId()!=null)
        {
            temp=purchaseService.getById(purchase.getPurchaseId());
            //判断是否是自己部门的信息或者是超级管理员，是就有权限更改，否则判断错误
            if ((temp.getDepartmentfloorId().equals(ShiroUtils.getProfile().getDepartmentfloorId()))||(ShiroUtils.getProfile().getDepartmentfloorId().equals(1)))
            {
                temp=new Purchase();
                temp.setAdminId(ShiroUtils.getProfile().getAdminId());
                temp.setAdminName(ShiroUtils.getProfile().getAdminName());
                temp.setDepartmentId(purchaseService.getById(purchase.getPurchaseId()).getDepartmentId());
                temp.setDepartmentName(purchaseService.getById(purchase.getPurchaseId()).getDepartmentName());
                temp.setDepartmentfloorId(purchaseService.getById(purchase.getPurchaseId()).getDepartmentfloorId());
                temp.setDepartmentfloorName(purchaseService.getById(purchase.getPurchaseId()).getDepartmentfloorName());
                BeanUtils.copyProperties(purchase,temp,
                        "adminId",
                        "adminName",
                        "purchaseCreatime",
                        "departmentId",
                        "departmentName",
                        "departmentfloorId",
                        "departmentfloorName");
            }else{
                return Result.fail("没有权限");
            }
            //信息不存在则创建
        }else{
            //判断是否为超级管理员
            if (ShiroUtils.getProfile().getAdminRole().equals(1)){
                temp=new Purchase();
                temp.setAdminId(ShiroUtils.getProfile().getAdminId());
                temp.setAdminName(ShiroUtils.getProfile().getAdminName());
                BeanUtils.copyProperties(purchase,temp,
                        "adminId",
                        "adminName",
                        "purchaseCreatime",
                        "purchaseId");
            }else{
                temp=new Purchase();
                temp.setAdminId(ShiroUtils.getProfile().getAdminId());
                temp.setAdminName(ShiroUtils.getProfile().getAdminName());
                temp.setDepartmentId(ShiroUtils.getProfile().getDepartmentId());
                temp.setDepartmentName(ShiroUtils.getProfile().getDepartmentName());
                temp.setDepartmentfloorId(ShiroUtils.getProfile().getDepartmentfloorId());
                temp.setDepartmentfloorName(ShiroUtils.getProfile().getDepartmentfloorName());
                BeanUtils.copyProperties(purchase,temp,
                        "adminId",
                        "purchaseId",
                        "adminName",
                        "purchaseCreatime",
                        "departmentId",
                        "departmentName",
                        "departmentfloorId",
                        "departmentfloorName");
            }
        }

        purchaseService.saveOrUpdate(temp);
        return Result.succ("null");
    }
    @RequiresAuthentication
    @PostMapping("delect")
    public  Result delect(@RequestParam Integer purchaseId){
        Purchase purchase=purchaseService.getById(purchaseId);
       purchaseService.removeById(purchaseId);
        Assert.notNull(purchase,"该条记录不存在");
        return Result.succ("null");
    }
}
