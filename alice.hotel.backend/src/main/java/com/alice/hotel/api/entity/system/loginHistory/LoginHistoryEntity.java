package com.alice.hotel.api.entity.system.loginHistory;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;


@Entity
@Table(name="SYS_LOGIN_HISTORY")
@Data
public class LoginHistoryEntity {

    @Id
    private String slId;

    private String userId;

    private String userNm;

    private String userDiv;

    private String loginYn;

    private String loginMsg;

    private String loginIp;

    private String connOs;

    private String loginType;

    private String accessType;

    private Date loginDate;

    private String browserInfo;

    private String roleInfo;

}
