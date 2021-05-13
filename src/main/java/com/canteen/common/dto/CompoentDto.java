package com.canteen.common.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class CompoentDto {


    private Integer componentId;

    private String componentName;

    private Float componentNum;



}
