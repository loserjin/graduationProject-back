package com.canteen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.canteen.common.dto.*;
import com.canteen.common.lang.Result;
import com.canteen.entity.*;
import com.canteen.entity.Component;
import com.canteen.service.*;
import com.canteen.util.JwtUtils;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.UIManager.get;
@Slf4j
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
    @Autowired
    DailymenuService dailymenuService;
    @Autowired
    DepartmentfloorService departmentfloorService;
    @Autowired
    UserorderdetailService userorderdetailService;
    @Autowired
    MenucomponentService menucomponentService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AdminService adminService;

    @RequiresAuthentication
    @GetMapping("/infos")
    public Result list(
//            @RequestParam(defaultValue = "1") Integer current,
//            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "0") Integer wastestatus,
            @RequestParam(defaultValue = "") String date,
            @RequestParam(defaultValue = "0") Integer departmentId,
            @RequestParam(defaultValue = "0") Integer departmentfloorId,
            HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        Integer aadminId = Integer.parseInt(jwtUtils.getClaimByToken(jwt).getSubject());
        Admin aadmin = adminService.getById(aadminId);

        if (aadmin.getDepartmentId().intValue() == 1) {
            if (aadmin.getDepartmentfloorId().intValue() != 1) {
                departmentId = aadmin.getDepartmentId();
            }
        } else {
            departmentfloorId = aadmin.getDepartmentfloorId();
            departmentId = aadmin.getDepartmentId();
        }

        ArrayList<PurchaseDto> purchaseDtos = new ArrayList<>();
        List<AllCompoentDto> allCompoentDtos = new ArrayList<>();


        //获取饭堂内的楼层
        List<Departmentfloor> departmentfloors = departmentfloorService.list(new QueryWrapper<Departmentfloor>()
                .eq("departmentId", departmentId)
                .eq(departmentfloorId.intValue() != 0, "departmentfloorId", departmentfloorId));

        ALLPurchaseDto allPurchaseDto = new ALLPurchaseDto();
        allPurchaseDto.setDepartmentId(departmentfloors.get(0).getDepartmentId());
        allPurchaseDto.setDepartmentName(departmentfloors.get(0).getDepartmentName());
        //加入purchaseDtos中
        for (Departmentfloor departmentfloor : departmentfloors) {
            List<FloorCompoentDto> floorCompoentDtos = new ArrayList<>();
            PurchaseDto purchaseDto = new PurchaseDto();

            ArrayList<PurchaseDto.TimeDto> timeDtos = new ArrayList<>();
            //设置purchaseDtos的数据展示方式
            PurchaseDto.TimeDto timeDto0 = new PurchaseDto.TimeDto();
            timeDto0.setTime("早餐");
            timeDto0.setDailymenuTime(0);
            timeDtos.add(0, timeDto0);
            PurchaseDto.TimeDto timeDto1 = new PurchaseDto.TimeDto();
            timeDto1.setTime("午餐");
            timeDto1.setDailymenuTime(1);
            timeDtos.add(1, timeDto1);
            PurchaseDto.TimeDto timeDto2 = new PurchaseDto.TimeDto();
            timeDto2.setTime("晚餐");
            timeDto2.setDailymenuTime(2);
            timeDtos.add(2, timeDto2);

            for (PurchaseDto.TimeDto timeDto : timeDtos) {
                //查找某饭堂某时段订单
                List<TimeCompoentDto> timeCompoentDtos = new ArrayList<>();
//                 List<Userorderdetail> userorderdetails=new ArrayList<>();
//                 List<Dailymenu> dailymenus=null;
                List<Userorderdetail> userorderdetails = userorderdetailService.list(new QueryWrapper<Userorderdetail>()
                        //指定饭堂
                        .eq("departmentId", departmentId)
                        .eq("departmentfloorId", departmentfloor.getDepartmentfloorId())
                        .eq("dailymenuTime", timeDto.getDailymenuTime())
                        .eq("userorderFStatus", 1)
                        //指定日期
                        .like("dailymenuCreatime", date));
                //根据条件查找饭堂内的某时段每日菜单，然后导入purchaseDto.data
                List<Dailymenu> dailymenus = dailymenuService.list(new QueryWrapper<Dailymenu>()
                        //指定饭堂
                        .eq("departmentId", departmentId)
                        .eq("departmentfloorId", departmentfloor.getDepartmentfloorId())
                        .eq("dailymenuTime", timeDto.getDailymenuTime())
                        //指定日期
                        .like("dailymenuCreatime", date));
                List<Component> components = new ArrayList<>();
                List<Menumsg> menumsgs = new ArrayList<>();
                //双循环查找List<Dailymenu> dailymenus符合条件的数据
                for (Dailymenu dailymenu : dailymenus) {
                    Menumsg menumsg = new Menumsg();
                    Integer sum = 0;
                    for (int j = 0; j < userorderdetails.size(); j++) {
                        if (userorderdetails.get(j).getDailymenuId() == dailymenu.getDailymenuId()) {
                            sum = sum + userorderdetails.get(j).getMenuTotal();
                            ;
                        }
                    }
                    menumsg.setNum(sum);
                    menumsg.setMenuName(dailymenu.getMenuName());
                    //收集配料数量
                    List<Menucomponent> menucomponents = menucomponentService.list(new QueryWrapper<Menucomponent>()
                            .eq("menuId", dailymenu.getMenuId()));
                    List<MenuCompoentDto> menuCompoentDtos = new ArrayList<>();
                    for (Menucomponent menucomponent : menucomponents) {
                        MenuCompoentDto menuCompoentDto = new MenuCompoentDto();
                        menuCompoentDto.setComponentName(menucomponent.getComponentName());
                        menuCompoentDto.setComponentNum(menucomponent.getComponentNum());
                        menuCompoentDto.setComponentId(menucomponent.getComponentId());
                        menuCompoentDtos.add(menuCompoentDto);
                    }
                    menumsg.setComponent(menuCompoentDtos);
                    menumsgs.add(menumsg);
//                     time配料信息,取出配料内的数据成一个列表，算出某个时段需要的配料
                    for (MenuCompoentDto menuCompoentDto : menuCompoentDtos) {
                        Integer flag = 0;
                        for (int i = 0; i < timeCompoentDtos.size(); i++) {
                            if (timeCompoentDtos.get(i).getComponentId().intValue() == menuCompoentDto.getComponentId().intValue()) {
                                float num = timeCompoentDtos.get(i).getComponentNum();
                                float num1 = menuCompoentDto.getComponentNum() * menumsg.getNum();
                                timeCompoentDtos.get(i).setComponentNum(num1 + num);
                                flag = 1;
                            }
                        }
                        if (flag == 0) {
                            float num = menuCompoentDto.getComponentNum() * menumsg.getNum();
                            TimeCompoentDto timeCompoentDto = new TimeCompoentDto();
                            timeCompoentDto.setComponentId(menuCompoentDto.getComponentId());
                            timeCompoentDto.setComponentName(menuCompoentDto.getComponentName());
                            timeCompoentDto.setComponentNum(num);
                            timeCompoentDtos.add(timeCompoentDto);
                        }
                    }

                }

                timeDto.setMenumsg(menumsgs);
                timeDto.setComponentmsg(timeCompoentDtos);
                //floor配料数据循环去重放进饭堂信息
                for (TimeCompoentDto timeCompoentDto : timeCompoentDtos) {
                    Integer flag = 0;
                    for (int i = 0; i < floorCompoentDtos.size(); i++) {
                        if (floorCompoentDtos.get(i).getComponentId().intValue() == timeCompoentDto.getComponentId().intValue()) {
                            float num = floorCompoentDtos.get(i).getComponentNum();
                            float num1 = timeCompoentDto.getComponentNum();
                            floorCompoentDtos.get(i).setComponentNum(num1 + num);
                            flag = 1;
                        }
                    }
                    if (flag == 0) {
                        FloorCompoentDto floorCompoentDto = new FloorCompoentDto();
                        floorCompoentDto.setComponentId(timeCompoentDto.getComponentId());
                        floorCompoentDto.setComponentName(timeCompoentDto.getComponentName());
                        floorCompoentDto.setComponentNum(timeCompoentDto.getComponentNum());
                        floorCompoentDtos.add(floorCompoentDto);
                    }
                }
            }
            //全部配料统计
            for (FloorCompoentDto floorCompoentDto : floorCompoentDtos) {
                Integer flag = 0;
                for (int i = 0; i < allCompoentDtos.size(); i++) {
                    if (allCompoentDtos.get(i).getComponentId().intValue() == floorCompoentDto.getComponentId().intValue()) {
                        float num = allCompoentDtos.get(i).getComponentNum();
                        float num1 = floorCompoentDto.getComponentNum();
                        allCompoentDtos.get(i).setComponentNum(num1 + num);
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    AllCompoentDto allCompoentDto = new AllCompoentDto();
                    allCompoentDto.setComponentId(floorCompoentDto.getComponentId());
                    allCompoentDto.setComponentName(floorCompoentDto.getComponentName());
                    allCompoentDto.setComponentNum(floorCompoentDto.getComponentNum());
                    allCompoentDtos.add(allCompoentDto);
                }
            }

            purchaseDto.setFloorcompoent(floorCompoentDtos);
            purchaseDto.setDetaildata(timeDtos);
            BeanUtils.copyProperties(departmentfloor, purchaseDto);
            purchaseDtos.add(purchaseDto);
        }
        allPurchaseDto.setData(purchaseDtos);
        allPurchaseDto.setAllcompoent(allCompoentDtos);

        return Result.succ(allPurchaseDto);

//        return Result.succ(table);
    }


}

