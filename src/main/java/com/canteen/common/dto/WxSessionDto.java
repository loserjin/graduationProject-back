package com.canteen.common.dto;

import lombok.Data;

@Data
public class WxSessionDto {
    private String session_key;
    private String openid;
}
