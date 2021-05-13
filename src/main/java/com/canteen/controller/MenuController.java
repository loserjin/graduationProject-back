package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.lang.Result;
import com.canteen.entity.Admin;
import com.canteen.entity.Departmentfloor;
import com.canteen.entity.Menu;
import com.canteen.entity.Purchase;
import com.canteen.mapper.MenuMapper;
import com.canteen.service.AdminService;
import com.canteen.service.DepartmentfloorService;
import com.canteen.service.MenuService;
import com.canteen.service.MenucomponentService;
import com.canteen.shiro.JwtFilter;
import com.canteen.util.JwtUtils;
import com.canteen.util.ShiroUtils;
import org.apache.ibatis.jdbc.Null;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    @Autowired
    MenucomponentService menucomponentService;
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AdminService adminService;

    @RequiresAuthentication
    @RequestMapping("/infos")
    public Result list(@RequestParam (defaultValue="1")Integer current,
                       @RequestParam (defaultValue="5")Integer size,
                       @RequestParam(defaultValue = "") Integer menuId,
                       @RequestParam (defaultValue = "")Integer departmentId,
                       @RequestParam (defaultValue = "")Integer departmentfloorId,
                       @RequestParam (defaultValue = "")String menuName,
                       @RequestParam(defaultValue = "") Integer typeId,
                       HttpServletRequest request){
        Page page = new Page(current,size);
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
        if (aadmin.getAdminRole().intValue()==1){
            if (aadmin.getDepartmentId().intValue()==1){
                IPage<Menu> iPage = menuMapper.queryClass(page,menuId,departmentId,departmentfloorId,menuName,typeId);
                return Result.succ(iPage);
            }else {
                departmentId=aadmin.getDepartmentId();
                IPage<Menu> iPage = menuMapper.queryClass(page,menuId,departmentId,departmentfloorId,menuName,typeId);
                return Result.succ(iPage);
            }
        }else {
            departmentfloorId=aadmin.getDepartmentfloorId();
            IPage<Menu> iPage = menuMapper.queryClass(page,menuId,departmentId,departmentfloorId,menuName,typeId);
            return Result.succ(iPage);
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
    public Result save(@RequestBody Menu menu,
                       HttpServletRequest request) {
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
        if (aadmin.getAdminRole().intValue()==1){
            //超级管理员
            if (aadmin.getDepartmentId().intValue()==1){
                menu.setAdminName(aadmin.getAdminName());
                menu.setAdminId(aadmin.getAdminId());
                menuService.saveOrUpdate(menu);
                return Result.succ("编辑成功");
            }else {
                //所属饭堂超级管理员
                menu.setAdminName(aadmin.getAdminName());
                menu.setAdminId(aadmin.getAdminId());
                menu.setDepartmentId(aadmin.getDepartmentId());
                menu.setDepartmentName(aadmin.getDepartmentName());
                menuService.saveOrUpdate(menu);
                return Result.succ("编辑成功");
            }
        }else{
            //普通管理员
            menu.setAdminName(aadmin.getAdminName());
            menu.setAdminId(aadmin.getAdminId());
            menu.setDepartmentId(aadmin.getDepartmentId());
            menu.setDepartmentName(aadmin.getDepartmentName());
            menu.setDepartmentfloorId(aadmin.getDepartmentfloorId());
            menu.setDepartmentfloorName(aadmin.getDepartmentfloorName());
            menuService.saveOrUpdate(menu);
            return Result.succ("编辑成功");
        }
    }


    @RequiresAuthentication
    @PostMapping("delect")
    public  Result delect(@RequestParam Integer menuId,
                          HttpServletRequest request) {
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
        Menu menu = menuService.getById(menuId);
        if (aadmin.getAdminRole().intValue()==1||menu.getDepartmentfloorId().intValue()==aadmin.getDepartmentfloorId().intValue()){
            menuService.removeById(menuId);
            Assert.notNull(menu,"该条记录不存在");
            return Result.succ("删除成功");
        } else
            return Result.fail("没有权限");

    }
}
