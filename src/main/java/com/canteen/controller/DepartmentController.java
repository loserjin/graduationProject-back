package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.lang.Result;
import com.canteen.entity.Admin;
import com.canteen.entity.Department;
import com.canteen.service.DepartmentService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 毕业设计
 * @since 2021-03-14
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    //查全部饭堂资料
    @RequiresAuthentication
    @GetMapping("infos")
    public Result list (@RequestParam(defaultValue="1")Integer current, @RequestParam (defaultValue="5")Integer size){
        Page page = new Page(current,size);
        IPage pageDate = departmentService.page(page,new QueryWrapper<Department>().eq("adminRole",0));
        return Result.succ(pageDate);
    }
}
