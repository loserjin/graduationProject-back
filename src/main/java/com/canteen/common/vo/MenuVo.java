package com.canteen.common.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.canteen.entity.Menucomponent;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class MenuVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜ID
     */

    private Integer menuId;

    /**
     * 菜名
     */

    private String menuName;

    /**
     * 实际金额
     */

    private Integer menuMoney;

    /**
     * 应付定金
     */

    private Integer menuFMoney;

    /**
     * 菜的图片
     */

    private String menuPic;

    /**
     * 配料
     */

    private List<Menucomponent> menucomponents;

    /**
     * 所属饭堂ID
     */

    private Integer departmentId;

    /**
     * 所属楼层
     */

    private String departmentName;


    private Integer departmentfloorId;


    private String departmentfloorName;


    private Integer typeId;


    private String typeName;

    /**
     * 创建管理员ID
     */

    private Integer adminId;


    private String adminName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime menuCreatime;


}
