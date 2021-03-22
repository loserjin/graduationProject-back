package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.lang.Result;
import com.canteen.entity.Departmentfloor;
import com.canteen.entity.Menu;
import com.canteen.entity.Purchase;
import com.canteen.service.DepartmentfloorService;
import com.canteen.service.MenuService;
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
 * @since 2021-03-14
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    MenuService menuService;

    //查询总菜谱
    @RequiresAuthentication
    @GetMapping("/infos")
    public Result list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "0") Integer departmentfloorId){
        Page page = new Page(current, size);
        if (departmentfloorId.intValue()==0) {
            IPage pageDate = menuService.page(page, new QueryWrapper<Menu>());
            return Result.succ(pageDate);
        }else {
            IPage pageDate = menuService.page(page, new QueryWrapper<Menu>().eq("departmentfloorId", departmentfloorId).orderByDesc("menuCreatime"));
            return Result.succ(pageDate);
        }

    }


    //查询菜谱内某个菜的信息
    @RequiresAuthentication
    @GetMapping("/info")
    public Result index(@RequestParam Integer menuId){
        Menu menu=menuService.getById(menuId);
        Assert.notNull(menu,"该记录不存在");
        return Result.succ(menu);
    }

    //编辑总菜谱资料
    @RequiresAuthentication
    @PostMapping("/edit")
    public Result save(@RequestBody Menu menu) {
        //先判断记录是否存在，如果存在则判断是否超级管理员或者是自己部门的信息，true则可以更改，false则没有权限编辑
        //                   如果不存在则判断是否超级管理员，是超级管理员则可以输入部门信息，不是超级管理员将自身的部门信息导入，
        Menu temp=null;

        //判断记录是否存在
        if (menuService.getById(menu.getMenuId())!=null)
        {
            temp=menuService.getById(menu.getMenuId());
            //判断是否是自己部门的信息或者是超级管理员，是就有权限更改，否则判断错误
            if ((temp.getDepartmentfloorId().equals(ShiroUtils.getProfile().getDepartmentfloorId()))||(ShiroUtils.getProfile().getDepartmentfloorId().equals(1)))
            {
                temp=new Menu();
                temp.setAdminId(ShiroUtils.getProfile().getAdminId());
                temp.setAdminName(ShiroUtils.getProfile().getAdminName());
                temp.setDepartmentId(menuService.getById(menu.getMenuId()).getDepartmentId());
                temp.setDepartmentName(menuService.getById(menu.getMenuId()).getDepartmentName());
                temp.setDepartmentfloorId(menuService.getById(menu.getMenuId()).getDepartmentfloorId());
                temp.setDepartmentfloorName(menuService.getById(menu.getMenuId()).getDepartmentfloorName());
                BeanUtils.copyProperties(menu,temp,
                        "adminId",
                        "adminName",
                        "menuCreatime",
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
                temp=new Menu();
                temp.setAdminId(ShiroUtils.getProfile().getAdminId());
                temp.setAdminName(ShiroUtils.getProfile().getAdminName());
                BeanUtils.copyProperties(menu,temp,
                        "adminId",
                        "adminName",
                        "menuCreatime",
                        "menuId");
            }else{
                temp=new Menu();
                temp.setAdminId(ShiroUtils.getProfile().getAdminId());
                temp.setAdminName(ShiroUtils.getProfile().getAdminName());
                temp.setDepartmentId(ShiroUtils.getProfile().getDepartmentId());
                temp.setDepartmentName(ShiroUtils.getProfile().getDepartmentName());
                temp.setDepartmentfloorId(ShiroUtils.getProfile().getDepartmentfloorId());
                temp.setDepartmentfloorName(ShiroUtils.getProfile().getDepartmentfloorName());
                BeanUtils.copyProperties(menu,temp,
                        "adminId",
                        "menuId",
                        "adminName",
                        "menuCreatime",
                        "departmentId",
                        "departmentName",
                        "departmentfloorId",
                        "departmentfloorName");
            }
        }

        menuService.saveOrUpdate(temp);
        return Result.succ("null");
    }


    @RequiresAuthentication
    @PostMapping("delect")
    public  Result delect(@RequestParam Integer menuId){
        if (ShiroUtils.getProfile().getAdminRole().equals(1)||menuService.getById(menuId).getDepartmentfloorId().equals(ShiroUtils.getProfile().getDepartmentfloorId())){
            Menu menu = menuService.getById(menuId);
            menuService.removeById(menuId);
            Assert.notNull(menu,"该条记录不存在");
            return Result.succ("null");
        } else
            return Result.fail("没有权限");

    }
}
