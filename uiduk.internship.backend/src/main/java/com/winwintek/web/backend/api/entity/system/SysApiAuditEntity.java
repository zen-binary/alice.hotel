package com.winwintek.web.backend.api.entity.system;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@DynamicUpdate
@Entity
@Table(name = "SYS_API_USE_HIS")
@Getter
@Setter
public class SysApiAuditEntity {
    @Id
    private String sauhId;
    private long rsId;
    private String userId;
    private LocalDate callDate;
    private String httpMethod;
    private String url;
    private Integer stsCode;
    private String execTime;
    private String clientIp;
    private String params;
    private String reqPayload;
    private String resPayload;
}
