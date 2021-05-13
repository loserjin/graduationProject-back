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
 * @since 2021-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Menucomponent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "menucomponentId", type = IdType.AUTO)
    private Integer menucomponentId;

    @TableField("menuId")
    private Integer menuId;

    @TableField("menuName")
    private String menuName;

    @TableField("componentId")
    private Integer componentId;

    @TableField("componentName")
    private String componentName;

    @TableField("componentMoney")
    private Float componentMoney;

    @TableField("componentNum")
    private Float componentNum;

    /**
     * 配料图片
     */
    @TableField("componentPic")
    private String componentPic;

    //0荤类，1素类，2调料
    @TableField("componentType")
    private String componentType;


}
