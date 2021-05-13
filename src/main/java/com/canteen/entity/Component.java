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
public class Component implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配料ID
     */
    @TableId(value = "componentId", type = IdType.AUTO)
    private Integer componentId;

    /**
     * 配料名
     */
    @TableField("componentName")
    private String componentName;

    /**
     * 价钱
     */
    @TableField("componentMoney")
    private Integer componentMoney;

    /**
     * 配料图片
     */
    @TableField("componentPic")
    private String componentPic;

    @TableField("adminId")
    private Integer adminId;

    @TableField("adminName")
    private String adminName;

    //0荤类，1素类，2调料
    @TableField("componentType")
    private String componentType;
}
