package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.lang.Result;
import com.canteen.entity.Dailymenu;
import com.canteen.entity.Purchase;
import com.canteen.entity.Type;
import com.canteen.service.PurchaseService;
import com.canteen.service.TypeService;
import com.canteen.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 毕业设计
 * @since 2021-03-15
 */
@RestController
@RequestMapping("/type")
public class TypeController {
    @Autowired
    TypeService typeService;

    @RequiresAuthentication
    @GetMapping("/infos")
    public Result list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "0") Integer departmentfloorId){
        Page page = new Page(current, size);
        if (departmentfloorId.intValue()==0) {
            IPage pageDate = typeService.page(page, new QueryWrapper<Type>());
            return Result.succ(pageDate);
        }else {
            IPage pageDate = typeService.page(page,new QueryWrapper<Type>().eq("departmentfloorId",departmentfloorId));
            return Result.succ(pageDate);
        }

    }

    @RequiresAuthentication
    @GetMapping("/info")
    public Result index(@RequestParam Integer typeId){
        Type type= typeService.getById(typeId);
        Assert.notNull(type,"该记录不存在");
        return Result.succ(type);
    }

    @RequiresAuthentication
    @PostMapping("/edit")
    public Result save(@RequestBody Type type) {
        //先判断记录是否存在，如果存在则判断是否超级管理员或者是自己部门的信息，true则可以更改，false则没有权限编辑
        //                   如果不存在则判断是否超级管理员，是超级管理员则可以输入部门信息，不是超级管理员将自身的部门信息导入，
        Type temp=null;

        //判断记录是否存在
        if (type.getTypeId()!=null)
        {
            temp=typeService.getById(type.getTypeId());
            //判断是否是自己部门的信息或者是超级管理员，是就有权限更改，否则判断错误
            if ((temp.getDepartmentfloorId().equals(ShiroUtils.getProfile().getDepartmentfloorId()))||(ShiroUtils.getProfile().getDepartmentfloorId().equals(1)))
            {
                temp=new Type();
                temp.setAdminId(ShiroUtils.getProfile().getAdminId());
                temp.setAdminName(ShiroUtils.getProfile().getAdminName());
                temp.setDepartmentId(typeService.getById(type.getTypeId()).getDepartmentId());
                temp.setDepartmentName(typeService.getById(type.getTypeId()).getDepartmentName());
                temp.setDepartmentfloorId(typeService.getById(type.getTypeId()).getDepartmentfloorId());
                temp.setDepartmentfloorName(typeService.getById(type.getTypeId()).getDepartmentfloorName());
                BeanUtils.copyProperties(type,temp,
                        "adminId",
                        "adminName",
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
            //是
            if (ShiroUtils.getProfile().getAdminRole().equals(1)){
                temp=new Type();
                temp.setAdminId(ShiroUtils.getProfile().getAdminId());
                temp.setAdminName(ShiroUtils.getProfile().getAdminName());
                BeanUtils.copyProperties(type,temp,
                        "adminId",
                        "adminName",
                        "purchaseCreatime",
                        "purchaseId");
            }else{
                temp=new Type();
                temp.setAdminId(ShiroUtils.getProfile().getAdminId());
                temp.setAdminName(ShiroUtils.getProfile().getAdminName());
                temp.setDepartmentId(ShiroUtils.getProfile().getDepartmentId());
                temp.setDepartmentName(ShiroUtils.getProfile().getDepartmentName());
                temp.setDepartmentfloorId(ShiroUtils.getProfile().getDepartmentfloorId());
                temp.setDepartmentfloorName(ShiroUtils.getProfile().getDepartmentfloorName());
                BeanUtils.copyProperties(type,temp,
                        "adminId",
                        "adminName",
                        "departmentId",
                        "departmentName",
                        "departmentfloorId",
                        "departmentfloorName");
            }
        }

       typeService.saveOrUpdate(temp);
        return Result.succ("null");
    }
    @RequiresAuthentication
    @PostMapping("delect")
    public  Result delect(@RequestParam Integer typeId){
        Type type=typeService.getById(typeId);
        if (ShiroUtils.getProfile().getAdminRole().equals(1)||type.getDepartmentfloorId().equals(ShiroUtils.getProfile().getDepartmentfloorId())){
           typeService.removeById(typeId);
            Assert.notNull(type,"该条记录不存在");
            return Result.succ("null");
        }else
            return Result.fail("没有权限");

    }
}
