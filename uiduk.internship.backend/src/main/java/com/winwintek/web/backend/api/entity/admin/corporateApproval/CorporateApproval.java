package com.winwintek.web.backend.api.entity.admin.corporateApproval;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Date;

@DynamicUpdate
@Entity
@Table (name = "CORPORATE_MEMBERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CorporateApproval extends AbstractAuditingEntity<String> implements Serializable {
    @Id
    private String cormSeq;

    private String cormNameKr;

    private String cormNameEn;

    private String cormAgentNmKr;

    private String cormRegistNumber;

    private String cormType;

    private String address;

    private String cormCategories;

    private String cormClassificationCd;

    private String cormHomepageUrl;

    private String cormAwardCd;

    private String cormZoneCode;

    private String cormAddress;

    private String cormAddressDetail;

    private String cormUserId;

    private String cormPhone;

    private String cormStatus;

    private Date cormConfirmDate;

    private String useYn;

    private String delYn;
}
