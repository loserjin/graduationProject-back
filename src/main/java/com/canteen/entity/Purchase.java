package com.canteen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

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
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "purchaseId", type = IdType.AUTO)
    private Integer purchaseId;

    /**
     * 材料类别，0为蔬菜，1为肉类
     */
    @TableField("purchaseType")
    private Integer purchaseType;

    /**
     * 材料名称
     */
    @TableField("purchaseName")
    private String purchaseName;

    /**
     * 材料数量
     */
    @TableField("purchaseTotal")
    private Float purchaseTotal;

    /**
     * 材料单价
     */
    @TableField("purchaseMoney")
    private Float purchaseMoney;

    /**
     * 创建总价
     */
    @TableField("purchaseTotalmoney")
    private Float purchaseTotalmoney;

    /**
     * 所属饭堂
     */
    @TableField("departmentId")
    private Integer departmentId;

    @TableField("departmentName")
    private String departmentName;

    @TableField("departmentfloorId")
    private Integer departmentfloorId;

    @TableField("departmentfloorName")
    private String departmentfloorName;

    @TableField("adminId")
    private Integer adminId;

    @TableField("adminName")
    private String adminName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("purchaseCreatime")
    private LocalDateTime purchaseCreatime;


}
