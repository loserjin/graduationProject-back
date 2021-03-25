package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.lang.Result;
import com.canteen.entity.Departmentfloor;
import com.canteen.service.DepartmentfloorService;
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
 * @since 2021-03-19
 */
@RestController
@RequestMapping("/departmentfloor")
public class DepartmentfloorController {
    @Autowired
    DepartmentfloorService departmentfloorService;

    //查询饭堂部门资料
    @RequiresAuthentication
    @GetMapping("/infos")
    public Result list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "") String departmentName){
        Page page = new Page(current, size);
        if (departmentName.equals("")) {
            IPage pageDate = departmentfloorService.page(page, new QueryWrapper<Departmentfloor>().orderByDesc("departmentName"));
            return Result.succ(pageDate);
        }else {
            IPage pageDate = departmentfloorService.page(page, new QueryWrapper<Departmentfloor>().eq("departmentName", departmentName));
            return Result.succ(pageDate);
        }

    }
    @RequiresAuthentication
    @GetMapping("/info")
    public Result index(@RequestParam Integer departmentfloorId){
        Departmentfloor departmentfloor=departmentfloorService.getById(departmentfloorId);
        Assert.notNull(departmentfloor,"该记录不存在");
        return Result.succ(departmentfloor);
    }

    //编辑饭堂部门资料
    @RequiresAuthentication
    @PostMapping("/edit")
    public Result save(@RequestBody Departmentfloor departmentfloor) {
        //不是超级管理员无法编辑
        Departmentfloor temp = null;
        if (ShiroUtils.getProfile().getAdminRole().equals(1)) {
            departmentfloorService.saveOrUpdate(departmentfloor);
        } else {
            return Result.fail("没有权限");
        }
        return Result.succ("null");
    }


    @RequiresAuthentication
    @PostMapping("delect")
    public  Result delect(@RequestParam Integer departmentfloorId){
        if (ShiroUtils.getProfile().getAdminRole().equals(1)){
            Departmentfloor departmentfloor =departmentfloorService.getById(departmentfloorId);
            departmentfloorService.removeById(departmentfloorId);
            Assert.notNull(departmentfloor,"该条记录不存在");
            return Result.succ("null");
        } else
            return Result.fail("没有权限");

    }
}
