package com.canteen.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class ALLPurchaseDto {
    private int departmentId;
    private String departmentName;
    private List data;
    private List allcompoent;
}
