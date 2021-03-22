package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.lang.Result;
import com.canteen.entity.Component;
import com.canteen.entity.Department;
import com.canteen.service.ComponentService;
import com.canteen.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
@RequestMapping("/component")
public class ComponentController {
    @Autowired
    ComponentService componentService;
    //查询配料资料
    @RequiresAuthentication
    @GetMapping("/infos")
    public Result list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "") String componentName){
        Page page = new Page(current, size);
        IPage pageDate = componentService.page(page, new QueryWrapper<Component>().like("componentName", componentName));
        return Result.succ(pageDate);
    }
    @RequiresAuthentication
    @GetMapping("/info")
    public Result index(@RequestParam Integer componentId){
        Component component=componentService.getById(componentId);
        Assert.notNull(component,"该记录不存在");
        return Result.succ(component);
    }

    //编辑增加配料资料
    @RequiresAuthentication
    @PostMapping("/edit")
    public Result save(@RequestBody Component component) {
        component.setAdminId(ShiroUtils.getProfile().getAdminId());
        component.setAdminName(ShiroUtils.getProfile().getAdminName());
        componentService.saveOrUpdate(component);
        return Result.succ("null");
    }


    @RequiresAuthentication
    @PostMapping("delect")
    public  Result delect(@RequestParam Integer componentId){
        if (ShiroUtils.getProfile().getAdminRole().equals(1)){
            Component component =componentService.getById(componentId);
            componentService.removeById(componentId);
            Assert.notNull(component,"该条记录不存在");
            return Result.succ("null");
        } else
            return Result.fail("没有权限");

    }




}
