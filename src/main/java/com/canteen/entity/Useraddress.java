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
 * @since 2021-05-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Useraddress implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 地址表ID
     */
    @TableId(value = "useraddressId", type = IdType.AUTO)
    private Integer useraddressId;

    /**
     * openID
     */
    @TableField("openId")
    private String openId;

    /**
     * 联系电话
     */
    @TableField("useraddressTel")
    private String useraddressTel;

    /**
     * 联系姓名
     */
    @TableField("useraddressName")
    private String useraddressName;

    /**
     * 联系地址
     */
    @TableField("useraddress")
    private String useraddress;

    /**
     * 性别
     */
    @TableField("gender")
    private String gender;

}
