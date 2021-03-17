package com.canteen.shiro;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    private String departmentName;

    private Integer departmentfloorId;

    private String departmentfloorName;

    private String adminSex;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime adminCreatime;

}
