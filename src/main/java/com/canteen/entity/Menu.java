package com.canteen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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
     * 配料
     */
    @TableField("menuComponent")
    private String menuComponent;

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
    @TableField("menuCreatime")
    private LocalDateTime menuCreatime;


}
