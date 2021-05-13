package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.lang.Result;
import com.canteen.entity.Component;
import com.canteen.entity.Dailymenu;
import com.canteen.entity.Departmentfloor;
import com.canteen.entity.Menucomponent;
import com.canteen.mapper.MenucomponentMapper;
import com.canteen.service.ComponentService;
import com.canteen.service.MenucomponentService;
import com.canteen.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 毕业设计
 * @since 2021-03-23
 */
@RestController
@RequestMapping("/menucomponent")
public class MenucomponentController {
    @Autowired
    MenucomponentService menucomponentService;
    @Autowired
    MenucomponentMapper menucomponentMapper;


    //查询配料资料
    @RequiresAuthentication
    @GetMapping("/infos")
    public Result list(@RequestParam Integer menuId){
       List<Menucomponent> menucomponents= menucomponentMapper.selectList(new QueryWrapper<Menucomponent>().eq("menuId",menuId.intValue()));
       return Result.succ(menucomponents);
    }

//    //查询单个配料
//    @RequiresAuthentication
//    @GetMapping("/info")
//    public Result index(@RequestParam Integer componentId){
//        Component component=componentService.getById(componentId);
//        Assert.notNull(component,"该记录不存在");
//        return Result.succ(component);
//    }

    //增加或修改配料资料

    @RequiresAuthentication
    @PostMapping("/edit")
    public Result save(@RequestBody Menucomponent menucomponent) {
    //将要导入的菜单ID信息寻找出来然后复制到每日菜单的表中
        menucomponentService.saveOrUpdate(menucomponent);
    return Result.succ("编辑成功");
}


    @RequiresAuthentication
    @PostMapping("delect")
    public  Result delect(@RequestParam Integer MenuComponentId){
            Menucomponent menucomponent =menucomponentService.getById(MenuComponentId);
            menucomponentService.removeById(MenuComponentId);
            Assert.notNull(menucomponent,"该条记录不存在");
            return Result.succ("null");
    }

}
