package com.alice.hotel.api.dto.system.loginHistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginHistoryResDTO {
    private String rowNum;

    private String slId;

    private String userId;

    private String userNm;

    private String userDiv;

    private String loginYn;

    private String loginMsg;

    private String loginIp;

    private String connOs;

    private String loginType;

    private String loginDate;

    private String accessType;

    private String browserInfo;

    private String roleInfo;
}
