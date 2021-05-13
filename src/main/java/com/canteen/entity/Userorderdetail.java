package com.canteen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author 毕业设计
 * @since 2021-05-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Userorderdetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "userorderdetailId", type = IdType.AUTO)
    private Integer userorderdetailId;

    @TableField("userorderId")
    private String userorderId;

    @TableField("dailymenuId")
    private Integer dailymenuId;

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
    private Float menuMoney;

    /**
     * 应付订金
     */
    @TableField("menuFMoney")
    private Float menuFMoney;

    /**
     * 菜的图片
     */
    @TableField("menuPic")
    private String menuPic;

    @TableField("typeId")
    private Integer typeId;

    @TableField("typeName")
    private String typeName;

    /**
     * 菜的数量
     */
    @TableField("menuTotal")
    private Integer menuTotal;

    @TableField("dailymenuTime")
    private Integer dailymenuTime;

    @TableField("dailymenuCreatime")
    private LocalDateTime dailymenuCreatime;

    /**
     * 楼层ID
     */
    @TableField("departmentfloorId")
    private Integer departmentfloorId;

    /**
     * 楼层名字
     */
    @TableField("departmentfloorName")
    private String departmentfloorName;

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
     * 订金状态，
     0为未支付订金，1为已支付订金，2为退订金，3退款
     */
    @TableField("userorderFStatus")
    private Integer userorderFStatus;

    /**
     * 尾款状态，
     0为未支付尾款，1为已支付尾款
     */
    @TableField("userorderMStatus")
    private Integer userorderMStatus;


}
