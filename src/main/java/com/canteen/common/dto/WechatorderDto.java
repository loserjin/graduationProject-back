package com.canteen.common.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class WechatorderDto {

    private String userorderId;

    /**
     * 所属用户ID
     */

    private String openId;

    /**
     * 总价
     */

    private float userorderSmoney;

    /**
     * 应支付订金的金额
     */

    private float userorderFmoney;

    /**
     * 订金状态，
     0为未支付订金，1为已支付订金，2为退订金，3退款
     */

    private Integer userorderFStatus;

    /**
     * 应支付的尾款金额
     */

    private float userorderMmoney;

    /**
     * 尾款状态，
     0为未支付尾款，1为已支付尾款
     */

    private Integer userorderMStatus;


    private LocalDateTime userorderCreatime;


    private Integer useraddressId;


    private String useraddressTel;


    private String useraddressName;

    private String useraddress;

    private String gender;


    private Integer departmentId;


    private String departmentName;


    private Integer departmentfloorId;


    private String departmentfloorName;


    private Integer dailymenuTime;


    private LocalDate dailymenuCreatime;
}
