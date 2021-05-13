package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.lang.Result;
import com.canteen.entity.Admin;
import com.canteen.entity.Department;
import com.canteen.entity.Notice;
import com.canteen.service.AdminService;
import com.canteen.service.NoticeService;
import com.canteen.util.JwtUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 毕业设计
 * @since 2021-05-10
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AdminService adminService;
    @Autowired
    NoticeService noticeService;

    //查询公告资料
    @RequiresAuthentication
    @GetMapping("/infos")
    public Result list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "0") Integer departmentId,
            @RequestParam(defaultValue = "") String text,
            HttpServletRequest request) {
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
//        Assert.isTrue(aadmin.getAdminRole().intValue()!=1,"权限不足");
        if (aadmin.getDepartmentfloorId().intValue()!=1){
            departmentId=aadmin.getDepartmentId();
        }
        Page page = new Page(current, size);
        IPage pageDate = noticeService.page(page, new QueryWrapper<Notice>()
                .eq(departmentId.intValue()!=0,"departmentId",departmentId)
                .like("noticeText",text)
                .orderByAsc("departmentId"));
        return Result.succ(pageDate);
    }

    @RequiresAuthentication
    @GetMapping("/info")
    public Result index(@RequestParam Integer noticeId){
        Notice notice=noticeService.getById(noticeId);
        Assert.notNull(notice,"该记录不存在");
        return Result.succ(notice);
    }

    //更改公告信息
    @RequiresAuthentication
    @PostMapping("/edit")
    public Result save(@RequestBody Notice notice,
                       HttpServletRequest request) {
        //不是超级管理员无法编辑
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
        if (aadmin.getAdminRole().intValue() == 1) {
            if (aadmin.getDepartmentfloorId().intValue()!=1)
            {
                notice.setDepartmentName(aadmin.getDepartmentName());
                notice.setDepartmentId(aadmin.getDepartmentId());
            }
            noticeService.saveOrUpdate(notice);
            return Result.succ("修改成功");
        } else {
            return Result.fail("没有权限");
        }
    }

    //更改公告状态
    @RequiresAuthentication
    @PostMapping("/editstatus")
    public Result save(@RequestParam Integer[] noticeId,
                       HttpServletRequest request) {
        //不是超级管理员无法编辑
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
        if (aadmin.getAdminRole().intValue() == 1) {
            for (Integer i :noticeId){
                Notice notice=noticeService.getById(i);
                notice.setNoticeState(1);
                noticeService.saveOrUpdate(notice);
            }
            return Result.succ("修改成功");
        } else {
            return Result.fail("没有权限");
        }
    }


    @RequiresAuthentication
    @PostMapping("delect")
    public  Result delect(@RequestParam Integer noticeId,
                          HttpServletRequest request){
        String jwt=request.getHeader("Authorization");
        Integer aadminId=Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin=adminService.getById(aadminId);
        if (aadmin.getAdminRole().intValue()==1){
            Notice notice =noticeService.getById(noticeId);
            Assert.notNull(notice,"该条记录不存在");
            noticeService.removeById(noticeId);
            return Result.succ("删除成功");
        } else
            return Result.fail("没有权限");
    }
}
