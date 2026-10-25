package com.winwintek.web.backend.api.entity.admin.supportCenter.agreement;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@DynamicUpdate
@Table (name = "TRIPARTITE_AGREEMENT")
public class SignTripartiteAgreementEntity extends AbstractAuditingEntity<String> implements Serializable {
    @Id
    private String tripartiteAgreementSeq;

    private String internPlanSeq;

    private String internStdSeq;

    private String corporateSignedYn;

    private Date corporateSignedDate;

    private String stdSignedYn;

    private String stdSignedDate;

    private String univSignedYn;

    private Date univSignedDate;

    private String lastSignedDate;

    private String delYn;

}
