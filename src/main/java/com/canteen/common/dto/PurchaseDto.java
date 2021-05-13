package com.canteen.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseDto {
    private int departmentId;
    private String departmentName;
    private int departmentfloorId;
    private String departmentfloorName;
    private List detaildata;
    private List floorcompoent;

    @Data
    public static class TimeDto {
        private  Integer dailymenuTime;
        private  String time;
        private List menumsg;
        private List componentmsg;
    }
}
