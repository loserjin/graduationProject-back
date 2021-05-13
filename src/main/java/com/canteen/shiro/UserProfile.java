package com.canteen.shiro;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class UserProfile implements Serializable {

    private String openId;

    private LocalDateTime lastTime;

    private String sessionKey;

    private String province;

    private String county;

    private String avatarUrl;

    private String gender;

    private String nickName;

    private String realName;

    private String userTel;

    private String userAddress;

}
