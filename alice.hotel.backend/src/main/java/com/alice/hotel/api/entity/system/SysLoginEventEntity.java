package com.alice.hotel.api.entity.system;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Date;

@DynamicUpdate
@Entity
@Table(name="SYS_LOGIN_HISTORY")
@Getter
@Setter
public class SysLoginEventEntity {
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
    private String browserInfo;
    private String roleInfo;

    @Column(name = "REG_DATE", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date regDate;

}
