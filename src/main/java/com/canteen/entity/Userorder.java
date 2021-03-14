package com.canteen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Userorder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "userorderId", type = IdType.AUTO)
    private Integer userorderId;

    /**
     * 所属用户名
     */
    @TableField("userName")
    private String userName;

    /**
     * 所属用户ID
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 订单详情，包括菜名。价格。数量
     */
    @TableField("userorderDetail")
    private String userorderDetail;

    /**
     * 0为未支付尾款，1为已支付尾款
     */
    @TableField("userorderStatus")
    private Integer userorderStatus;

    /**
     * 总价
     */
    @TableField("userorderIdSmoney")
    private Integer userorderIdSmoney;

    /**
     * 已经支付订金的金额
     */
    @TableField("userorderIdFmoney")
    private Integer userorderIdFmoney;

    /**
     * 应支付的尾款金额
     */
    @TableField("userorderIdMmoney")
    private Integer userorderIdMmoney;


}
