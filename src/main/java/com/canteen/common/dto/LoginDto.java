package com.canteen.common.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class LoginDto implements Serializable {

    @NotBlank(message = "昵称不能为空")
    private String adminName;
    @NotBlank(message = "密码不能为空")
    private String adminPwd;
    @NotNull(message = "角色不能为空")
    private Integer adminRole;
}
