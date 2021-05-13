package com.canteen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canteen.common.dto.MenuCompoentDto;
import com.canteen.common.dto.UserorderDto;
import com.canteen.common.dto.WechatorderDto;
import com.canteen.common.lang.Result;
import com.canteen.entity.*;
import com.canteen.mapper.DailymenuMapper;
import com.canteen.service.*;
import com.canteen.util.JwtUtils;
import com.canteen.util.Pager;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 毕业设计
 * @since 2021-04-07
 */
@RestController
@RequestMapping("/wechat")

public class WechatController {
    @Autowired
    DepartmentService departmentService;
    @Autowired
    DepartmentfloorService departmentfloorService;
    @Autowired
    TypeService typeService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserorderService userorderService;
    @Autowired
    UserorderdetailService userorderdetailService;
    @Autowired
    DailymenuService dailymenuService;
    @Autowired
    MenucomponentService menucomponentService;
    @Autowired
    NoticeService noticeService;
    @Autowired
    DiscussService discussService;
    @Autowired
    UserService userService;


    //查询饭堂资料
    @GetMapping("/department/infos")
    public Result list(
            @RequestParam(defaultValue = "") String departmentName,
            @RequestParam(defaultValue = "0") Integer departmentId) {
        List <Department> departments = departmentService.list(new QueryWrapper<Department>()
                .eq(departmentId.intValue() != 0, "departmentId", departmentId)
                .like("departmentName", departmentName)
                .orderByAsc("departmentId"));
        return Result.succ(departments);
    }

    @GetMapping("/department/info")
    public Result indexdepartment(@RequestParam Integer departmentId){
        Department department=departmentService.getById(departmentId);
        Assert.notNull(department,"该记录不存在");
        return Result.succ(department);
    }


    //查询饭堂部门资料
    @GetMapping("/departmentfloor/infos")
    public Result list(
            @RequestParam(defaultValue = "") String departmentfloorName,
            @RequestParam(defaultValue = "0")Integer departmentId,
            @RequestParam(defaultValue = "0")Integer departmentfloorId){
        List<Departmentfloor>departmentfloors = departmentfloorService.list(new QueryWrapper<Departmentfloor>()
                .eq(departmentId.intValue()!=0,"departmentId",departmentId)
                .eq(departmentfloorId.intValue()!=0,"departmentfloorId",departmentfloorId)
                .like("departmentfloorName",departmentfloorName)
                .orderByAsc("departmentName"));
        return Result.succ(departmentfloors);
    }

    @GetMapping("/departmentfloor/info")
    public Result indexfloor(@RequestParam Integer departmentfloorId){
        Departmentfloor departmentfloor=departmentfloorService.getById(departmentfloorId);
        Assert.notNull(departmentfloor,"该记录不存在");
        return Result.succ(departmentfloor);
    }


    //查询菜系
    @GetMapping("/type/infos")
    public Result list(
            @RequestParam(defaultValue = "0") Integer departmentId,
            @RequestParam(defaultValue = "0") Integer departmentfloorId,
            @RequestParam(defaultValue = "") String departmentfloorName,
            @RequestParam(defaultValue = "") String departmentName,
            HttpServletRequest request){
        List<Type>types = typeService.list( new QueryWrapper<Type>()
                .eq(departmentId.intValue()!=0,"departmentId",departmentId)
                .eq(departmentfloorId.intValue()!=0,"departmentfloorId",departmentfloorId)
                .like("departmentName",departmentName)
                .like("departmentfloorName",departmentfloorName)
                .orderByAsc("departmentName")
                .orderByAsc("departmentfloorName"));
        return Result.succ(types);

    }


    @GetMapping("/type/info")
    public Result indextype(@RequestParam Integer typeId){
        Type type= typeService.getById(typeId);
        Assert.notNull(type,"该记录不存在");
        return Result.succ(type);
    }

    @GetMapping("dailymenu/infos")
    public Result list(@RequestParam(defaultValue = "0") Integer dailymenuId,
                       @RequestParam(defaultValue = "0") Integer departmentfloorId,
                       @RequestParam(defaultValue = "0") Integer departmentId,
                       @RequestParam(defaultValue = "")String menuName,
                       @RequestParam(defaultValue = "0") Integer typeId,
                       @RequestParam(defaultValue = "") String date,
                       @RequestParam(defaultValue = "5")Integer time){
        List <Dailymenu> dailymenus=dailymenuService.list(new QueryWrapper<Dailymenu>()
                .eq(departmentfloorId.intValue()!=0,"departmentfloorId",departmentfloorId)
                .eq(departmentId.intValue()!=0,"departmentId",departmentId)
                .eq(dailymenuId.intValue()!=0,"dailymenuId",dailymenuId)
                .like("menuName",menuName)
                .eq(typeId.intValue()!=0,"typeId",typeId)
                .eq(time.intValue()!=5,"dailymenuTime",time)
                .like("dailymenuCreatime",date));
        for (Dailymenu dailymenu:dailymenus){
            List <Menucomponent> menucomponents=menucomponentService.list(new QueryWrapper<Menucomponent>()
                    .eq("menuId",dailymenu.getMenuId()));
            dailymenu.setMenucomponents(menucomponents);
        }
        return Result.succ(dailymenus);

    }

    //订单查询
    @RequiresAuthentication
    @GetMapping("/userorder/infos")
    public Result list(HttpServletRequest request,
                       @RequestParam(defaultValue = "0") Integer departmentId,
                       @RequestParam(defaultValue = "0") Integer departmentfloorId,
                       @RequestParam(defaultValue = "") String date,
                       @RequestParam(defaultValue = "5") Integer time) {
        String jwt = request.getHeader("Authorization");
        String openId = jwtUtils.getClaimByToken(jwt).getSubject();
//        String openId="测试";
        List <Userorder> userorders = userorderService.list(new QueryWrapper<Userorder>()
                .eq("openId",openId)
                .eq(departmentId.intValue()!=0,"departmentId",departmentId)
                .eq(departmentfloorId.intValue()!=0,"departmentfloorId",departmentfloorId)
                .eq(time.intValue()!=5,"dailymenuTime",time)
                .like("dailymenuCreatime", date)
                .orderByDesc("userorderId"));
        List<WechatorderDto> wechatorderDtos=new ArrayList<>();
        for (Userorder userorder:userorders){
            WechatorderDto wechatorderDto=new WechatorderDto();
            wechatorderDto.setDailymenuCreatime(LocalDate.from(userorder.getDailymenuCreatime()));
            BeanUtils.copyProperties(userorder,wechatorderDto,"dailymenuCreatime");
            wechatorderDtos.add(wechatorderDto);

        }
        return Result.succ(wechatorderDtos);
    }

    @RequiresAuthentication
    @PostMapping("/userorder/edit")
    public Result save(@RequestBody UserorderDto userorderDto,
                       HttpServletRequest request) {
        //设置订单编号
        String jwt = request.getHeader("Authorization");
        String openId = jwtUtils.getClaimByToken(jwt).getSubject();
        Random random=new Random();
        Integer ran=random.nextInt(99999);
        String userorderId = String.valueOf(LocalDateTime.now())+String.valueOf(ran);
//
//        String openId="嘻嘻";
//        String userorderId="测试2";
//
        //查所订的菜的饭堂信息和用餐时间，并set进userorderDto中
        Dailymenu dailymenu = dailymenuService.getById(userorderDto.getDailymenuId()[0]);
        //检测订单是否不允许提交
        LocalDateTime endtime=dailymenu.getDailymenuCreatime();
        LocalDateTime starttime;
        starttime=LocalDateTime.now().plusDays(2);
        if (starttime.isBefore(endtime)) {
            userorderDto.setDepartmentId(dailymenu.getDepartmentId());
            userorderDto.setDepartmentName(dailymenu.getDepartmentName());
            userorderDto.setDepartmentfloorId(dailymenu.getDepartmentfloorId());
            userorderDto.setDepartmentfloorName(dailymenu.getDepartmentfloorName());
            userorderDto.setDailymenuTime(dailymenu.getDailymenuTime());
            userorderDto.setDailymenuCreatime(dailymenu.getDailymenuCreatime());
            userorderDto.setUserorderId(userorderId);
            userorderDto.setOpenId(openId);
            Userorder userorder = new Userorder();
            BeanUtils.copyProperties(userorderDto, userorder, "menuId", "menuTotal");
            //总价
            float userorderSmoney = 0.00f;
            //订金
            float userorderFmoney = 0.00f;
            //尾款
            float userorderMmoney = 0.00f;
            //循环查询每日菜谱内的数据然后导入订单详情表，统计出金额
            for (int i = 0; i <= userorderDto.getDailymenuId().length - 1; i++) {
                Userorderdetail userorderdetail = new Userorderdetail();
                Dailymenu temp = dailymenuService.getById(userorderDto.getDailymenuId()[i]);
                BeanUtils.copyProperties(temp, userorderdetail, "dailymenuCreatime", "dailymenuTime", "menuMoney", "menuFMoney");
                userorderdetail.setUserorderFStatus(0);
                userorderdetail.setUserorderMStatus(0);
                userorderdetail.setUserorderId(userorderId);
                //订了多少份
                Integer num = userorderDto.getMenuTotal()[i];
                userorderdetail.setMenuTotal(num);
                //订金
                userorderdetail.setMenuFMoney(temp.getMenuFMoney() * num);
                //菜的金额
                userorderdetail.setMenuMoney(temp.getMenuMoney() * num);
                //汇入订单总的金额和总的订金
                //总价
                userorderSmoney += temp.getMenuMoney() * num;
                //订金
                userorderFmoney += temp.getMenuFMoney() * num;

                userorderdetail.setDailymenuCreatime(dailymenu.getDailymenuCreatime());
                userorderdetail.setDailymenuTime(dailymenu.getDailymenuTime());
                userorderdetailService.saveOrUpdate(userorderdetail);
            }
            userorder.setUserorderMmoney(userorderSmoney - userorderFmoney);
            userorder.setUserorderFmoney(userorderFmoney);
            userorder.setUserorderSmoney(userorderSmoney);
            userorder.setUserorderFStatus(0);
            userorder.setUserorderMStatus(0);
            userorderService.saveOrUpdate(userorder);
            return Result.succ(userorder.getUserorderId());
        }else return Result.fail(406,"超出可以预定的时间","超出可以预定的时间");

    }
    //修改订金支付状态
    @RequiresAuthentication
    @PostMapping("/userorder/changeFstatus")
    public Result saveFStatus(@RequestParam String userorderId,
                       @RequestParam Integer userorderFStatus,
                       HttpServletRequest request) {
        Userorder userorder=userorderService.getById(userorderId);
        Assert.notNull(userorder,"查无订单");
        LocalDateTime endtime=userorder.getDailymenuCreatime();
        LocalDateTime starttime;
        starttime=LocalDateTime.now().plusDays(2);
        if (starttime.isBefore(endtime))
        {
            userorder.setUserorderFStatus(userorderFStatus);
            userorderService.saveOrUpdate(userorder);
            List<Userorderdetail> userorderdetails=userorderdetailService.list(new QueryWrapper<Userorderdetail>()
                    .eq("userorderId",userorderId));
            for (Userorderdetail userorderdetail:userorderdetails){
                userorderdetail.setUserorderFStatus(userorderFStatus);
                userorderdetailService.saveOrUpdate(userorderdetail);
            }
            return Result.succ("操作成功");
        }else {
            return Result.fail(406,"本订单已经超出允许操作的时间","本订单已经超出允许操作的时间");
        }
    }

    //修改尾款状态
    @RequiresAuthentication
    @PostMapping("/userorder/changeMstatus")
    public Result saveMStatus(@RequestParam String userorderId,
                              @RequestParam Integer userorderMStatus,
                              HttpServletRequest request) {
        Userorder userorder=userorderService.getById(userorderId);
        Assert.notNull(userorder,"查无订单");
        LocalDateTime starttime=null;
        LocalDateTime endtime=null;
        LocalDateTime time=userorder.getDailymenuCreatime();
        if (userorder.getDailymenuTime()==0){
             starttime=time.plusHours(6);
             endtime=time.plusHours(9);
        }
        if (userorder.getDailymenuTime()==1){
             starttime=time.plusHours(10);
             endtime=time.plusHours(13);
        }
        if (userorder.getDailymenuTime()==2){
             starttime=time.plusHours(16);
             endtime=time.plusHours(19);
        }
        if (LocalDateTime.now().isBefore(endtime)&&LocalDateTime.now().isAfter(starttime)){
                userorder.setUserorderFStatus(userorderMStatus);
                userorderService.saveOrUpdate(userorder);
                List<Userorderdetail> userorderdetails=userorderdetailService.list(new QueryWrapper<Userorderdetail>()
                        .eq("userorderId",userorderId));
                for (Userorderdetail userorderdetail:userorderdetails){
                    userorderdetail.setUserorderFStatus(userorderMStatus);
                    userorderdetailService.saveOrUpdate(userorderdetail);
                }
            return Result.succ("操作成功");
        }else {
            return Result.fail(406,"本订单已经超出允许操作的时间","本订单已经超出允许操作的时间");
        }

    }

    //外卖配送，同时支付尾款和订金
    @RequiresAuthentication
    @PostMapping("/userorder/changestatus")
    public Result saveStatus(@RequestParam String userorderId,
                              @RequestParam Integer userorderStatus,
                              HttpServletRequest request) {
        Userorder userorder=userorderService.getById(userorderId);
        Assert.notNull(userorder,"查无订单");
        LocalDateTime endtime=userorder.getDailymenuCreatime();
        LocalDateTime starttime=LocalDateTime.now().plusDays(2);
        if (starttime.isBefore(endtime))
        {
            userorder.setUserorderFStatus(userorderStatus);
            userorder.setUserorderMStatus(userorderStatus);
            userorderService.saveOrUpdate(userorder);
            List<Userorderdetail> userorderdetails=userorderdetailService.list(new QueryWrapper<Userorderdetail>()
                    .eq("userorderId",userorderId));
            for (Userorderdetail userorderdetail:userorderdetails){
                userorderdetail.setUserorderFStatus(userorderStatus);
                userorderdetail.setUserorderMStatus(userorderStatus);
                userorderdetailService.saveOrUpdate(userorderdetail);
            }
            return Result.succ("操作成功");
        }else {
            return Result.fail(406,"本订单已经超出允许操作的时间","本订单已经超出允许操作的时间");
        }
    }

    //删除订单，将openId改为空，但不是删除订单
    @RequiresAuthentication
    @PostMapping("/userorder/delect")
    public Result saveFStatus(@RequestParam String userorderId,
                              HttpServletRequest request) {
        Userorder userorder=userorderService.getById(userorderId);
        Assert.notNull(userorder,"查无订单");
        userorder.setOpenId("null");
        userorderService.saveOrUpdate(userorder);
       return Result.succ("删除成功");
    }


    //查订单详情
    @RequiresAuthentication
    @GetMapping("/userorderdetail/infos")
    public Result list(  @RequestParam(defaultValue = "5") Integer size,
                @RequestParam(defaultValue = "1") Integer current,
                @RequestParam(defaultValue = "") String userorderId) {
        Page page = new Page(current, size);
        IPage pageDate = userorderdetailService.page(page, new QueryWrapper<Userorderdetail>().eq("userorderId", userorderId));
        return Result.succ(pageDate);
    }

    //查询公告资料
    @GetMapping("notice/infos")
    public Result listnotices(
            @RequestParam(defaultValue = "0") Integer departmentId) {

        List <Notice> notices = noticeService.list(new QueryWrapper<Notice>()
                .eq("departmentId",departmentId)
                .orderByAsc("noticeCreatime"));
        return Result.succ(notices);
    }

    //查询单个公告资料
    @GetMapping("notice/info")
    public Result notice(@RequestParam Integer noticeId) {

        Notice notice = noticeService.getById(noticeId);
        return Result.succ(notice);
    }

    //提交评论
    @RequiresAuthentication
    @PostMapping("discuss/edit")
    public  Result savediscuss(@RequestBody Discuss discuss,
                          @RequestParam (defaultValue = "0")Integer namestatus,
                          HttpServletRequest request){
        String jwt=request.getHeader("Authorization");
        String openId=jwtUtils.getClaimByToken(jwt).getSubject();
        User weixin=userService.getById(openId);
        if (namestatus.intValue()==1){
            discuss.setDiscussName("匿名用户");
        }else {
            discuss.setDiscussName(weixin.getNickName());
        }
        discuss.setOpenId(weixin.getOpenId());
        discussService.save(discuss);
        return Result.succ("评论成功");
    }

    //查询评论
    @GetMapping("discuss/infos")
    public Result discussinfos(@RequestParam(defaultValue = "0") Integer departmentId,
                               @RequestParam(defaultValue = "0") Integer departmentfloorId,
                               HttpServletRequest request){

        List<Discuss> discusses=discussService.list(new QueryWrapper<Discuss>()
                .eq(departmentId.intValue()!=0,"departmentId",departmentId)
                .eq(departmentfloorId.intValue()!=0,"departmentfloorId",departmentfloorId));
        for (Discuss discuss:discusses){
            discuss.setOpenId(null);
        }
        return Result.succ(discusses);
    }

    //查询自己的评论
    @RequiresAuthentication
    @GetMapping("discuss/myinfos")
    public Result discussmyinfos(@RequestParam(defaultValue = "0") Integer departmentId,
                               @RequestParam(defaultValue = "0") Integer departmentfloorId,
                               HttpServletRequest request){
        String jwt=request.getHeader("Authorization");
        String openId=jwtUtils.getClaimByToken(jwt).getSubject();
        List<Discuss> discusses=discussService.list(new QueryWrapper<Discuss>()
                .eq(departmentId.intValue()!=0,"departmentId",departmentId)
                .eq(departmentfloorId.intValue()!=0,"departmentfloorId",departmentfloorId)
                .eq("openId",openId));
        for (Discuss discuss:discusses){
            discuss.setOpenId(null);
        }
        return Result.succ(discusses);
    }
    //删除评论
    @RequiresAuthentication
    @PostMapping("discuss/delect")
    public  Result delect(@RequestParam Integer discussId,HttpServletRequest request){
        String jwt=request.getHeader("Authorization");
        String openId=jwtUtils.getClaimByToken(jwt).getSubject();
            Discuss discuss=discussService.getById(discussId);
            Assert.notNull(discuss,"该条记录不存在");
            Assert.isTrue(discuss.getOpenId().equals(openId),"非本人评论");
            discussService.removeById(discussId);
            return Result.succ("删除成功");
    }
}
