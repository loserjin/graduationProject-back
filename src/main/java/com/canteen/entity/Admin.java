package com.canteen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 *
 * </p>
 *
 * @author 毕业设计
 * @since 2021-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "adminId", type = IdType.AUTO)
    private Integer adminId;

    @TableField("adminRole")
    private Integer adminRole;

    @NotBlank(message = "昵称不能为空")
    @TableField("adminName")
    private String adminName;

    @NotBlank(message = "密码不能为空")
    @TableField("adminPwd")
    private String adminPwd;

    @NotBlank(message = "电话不能为空")
    @TableField("adminTel")
    private String adminTel;

    @NotBlank(message = "身份证不能为空")
    @TableField("adminIdcard")
    private String adminIdcard;

    @Email(message = "邮箱格式不正确")
    @TableField("adminEmail")
    private String adminEmail;

    @TableField("departmentId")
    private Integer departmentId;

    @TableField("departmentName")
    private String departmentName;

    @TableField("departmentfloorId")
    private Integer departmentfloorId;

    @TableField("departmentfloorName")
    private String departmentfloorName;

    @TableField("adminSex")
    private String adminSex;

    @TableField("adminCreatime")
    private LocalDateTime adminCreatime;

    @TableField("adminStatus")
    private Integer adminStatus;


}
