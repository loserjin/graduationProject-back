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
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 饭堂ID
     */
    @TableId(value = "departmentId", type = IdType.AUTO)
    private Integer departmentId;

    /**
     * 饭堂名称
     */
    @TableField("departmentName")
    private String departmentName;

    /**
     * 饭堂图片
     */
    @TableField("departmentPic")
    private String departmentPic;

}
