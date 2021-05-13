package com.canteen.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

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
public class Userorder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("userorderId")
    private String userorderId;

    /**
     * 所属用户ID
     */
    @TableField("openId")
    private String openId;

    /**
     * 总价
     */
    @TableField("userorderSmoney")
    private float userorderSmoney;

    /**
     * 应支付订金的金额
     */
    @TableField("userorderFmoney")
    private float userorderFmoney;

    /**
     * 订金状态，
0为未支付订金，1为已支付订金，2为退订金，3退款
     */
    @TableField("userorderFStatus")
    private Integer userorderFStatus;

    /**
     * 应支付的尾款金额
     */
    @TableField("userorderMmoney")
    private float userorderMmoney;

    /**
     * 尾款状态，
0为未支付尾款，1为已支付尾款
     */
    @TableField("userorderMStatus")
    private Integer userorderMStatus;

    @TableField("userorderCreatime")
    private LocalDateTime userorderCreatime;

    @TableField("useraddressId")
    private Integer useraddressId;

    @TableField("useraddressTel")
    private String useraddressTel;

    @TableField("useraddressName")
    private String useraddressName;

    private String useraddress;

    private String gender;

    @TableField("departmentId")
    private Integer departmentId;

    @TableField("departmentName")
    private String departmentName;

    @TableField("departmentfloorId")
    private Integer departmentfloorId;

    @TableField("departmentfloorName")
    private String departmentfloorName;

    @TableField("dailymenuTime")
    private Integer dailymenuTime;

    @TableField("dailymenuCreatime")
    private LocalDateTime dailymenuCreatime;


}
