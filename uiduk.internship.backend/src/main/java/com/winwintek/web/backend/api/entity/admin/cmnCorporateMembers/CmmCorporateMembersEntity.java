package com.winwintek.web.backend.api.entity.admin.cmnCorporateMembers;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicUpdate
@Table (name = "CORPORATE_MEMBERS")
public class CmmCorporateMembersEntity extends AbstractAuditingEntity<String> implements Serializable {

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

    private String cormAddress;

    private String cormAddressDetail;

    private String cormUserId;

    private String cormPhone;

    private String cormStatus;

    private String cormZoneCode;

    private Date cormConfirmDate;

    private String useYn;

    private String delYn;

}
