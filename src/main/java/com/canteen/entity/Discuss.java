package com.canteen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @since 2021-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Discuss implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(value = "discussId", type = IdType.AUTO)
    private Integer discussId;

    /**
     * 内容
     */
    @TableField("discussText")
    private String discussText;

    /**
     * 星星，1-5星
     */
    @TableField("discussStart")
    private Integer discussStart;

    /**
     * 评论用户
     */
    @TableField("discussName")
    private String discussName;

    @TableField("departmentId")
    private Integer departmentId;

    @TableField("departmentName")
    private String departmentName;

    @TableField("departmentfloorId")
    private Integer departmentfloorId;

    @TableField("departmentfloorName")
    private String departmentfloorName;

    /**
     * 评论时间
     */
    @TableField("discussCreatime")
    private LocalDateTime discussCreatime;

    @TableField("openId")
    private String openId;

}
