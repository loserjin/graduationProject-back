package com.canteen.shiro;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AccountProfile implements Serializable {


    private Integer adminId;


    private Integer adminRole;


    private String adminName;


    private String adminTel;


    private String adminIdcard;


    private String adminEmail;


    private Integer departmentId;


    private Integer departmentFoor;


    private LocalDateTime adminCreatime;


}
