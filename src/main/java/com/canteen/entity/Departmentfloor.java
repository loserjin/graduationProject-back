package com.canteen.entity;

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
public class Departmentfloor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 楼层ID
     */
    @TableField("departmentfloorId")
    private Integer departmentfloorId;

    /**
     * 楼层名字
     */
    @TableId("departmentfloorName")
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


}
