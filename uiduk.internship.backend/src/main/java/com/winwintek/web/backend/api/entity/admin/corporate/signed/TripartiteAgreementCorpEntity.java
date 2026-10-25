package com.winwintek.web.backend.api.entity.admin.corporate.signed;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@DynamicUpdate
@Table (name = "TRIPARTITE_AGREEMENT")
public class TripartiteAgreementCorpEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String tripartiteAgreementSeq;

    private String internPlanSeq;

    private String internStdSeq;

    private String corporateSignedYn;

    private Date corporateSignedDate;

    private String stdSignedYn;

    private String stdSignedDate;

    private String univSignedYn;

    private String univSignedDate;

    private String lastSignedDate;

    private String delYn;

}
