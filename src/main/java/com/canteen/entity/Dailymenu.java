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
public class Dailymenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 每日菜单ID
     */
    @TableId(value = "dailymenuId", type = IdType.AUTO)
    private Integer dailymenuId;

    /**
     * 菜的ID
     */
    @TableField("menuId")
    private Integer menuId;

    /**
     * 菜名
     */
    @TableField("menuName")
    private String menuName;

    /**
     * 菜的金额
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
     * 所属饭堂名字
     */
    @TableField("departmentName")
    private String departmentName;

    /**
     * 所属饭堂楼层ID
     */
    @TableField("departmentfoorId")
    private Integer departmentfoorId;

    /**
     * 所属饭堂楼层名字
     */
    @TableField("departmentfloorName")
    private String departmentfloorName;

    /**
     * 生成时间
     */
    @TableField("dailymenuCreatime")
    private LocalDateTime dailymenuCreatime;


}
