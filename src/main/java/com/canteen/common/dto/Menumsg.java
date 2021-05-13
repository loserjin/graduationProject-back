package com.canteen.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class Menumsg {
    private Integer Num;
    private String menuName;
    private List component;
}
