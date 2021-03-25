package com.canteen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author 毕业设计
 * @since 2021-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜ID
     */
    @TableId(value = "menuId", type = IdType.AUTO)
    private Integer menuId;

    /**
     * 菜名
     */
    @TableField("menuName")
    private String menuName;

    /**
     * 实际金额
     */
    @TableField("menuMoney")
    private Integer menuMoney;

    /**
     * 应付定金
     */
    @TableField("menuFMoney")
    private Integer menuFMoney;

    /**
     * 菜的图片
     */
    @TableField("menuPic")
    private String menuPic;


    /**
     * 所属饭堂ID
     */
    @TableField("departmentId")
    private Integer departmentId;

    /**
     * 所属楼层
     */
    @TableField("departmentName")
    private String departmentName;

    @TableField("departmentfloorId")
    private Integer departmentfloorId;

    @TableField("departmentfloorName")
    private String departmentfloorName;

    @TableField("typeId")
    private Integer typeId;

    @TableField("typeName")
    private String typeName;

    /**
     * 创建管理员ID
     */
    @TableField("adminId")
    private Integer adminId;

    @TableField("adminName")
    private String adminName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("menuCreatime")
    private LocalDateTime menuCreatime;
    /**
     * 配料
     */
    @TableField(exist = false)
    private List<Menucomponent> menucomponents;

}
