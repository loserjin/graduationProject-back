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
 * @since 2021-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 种类ID，例如红烧类等等
     */
    @TableId(value = "typeId", type = IdType.AUTO)
    private Integer typeId;

    /**
     * 种类名
     */
    @TableField("typeName")
    private String typeName;

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


}
