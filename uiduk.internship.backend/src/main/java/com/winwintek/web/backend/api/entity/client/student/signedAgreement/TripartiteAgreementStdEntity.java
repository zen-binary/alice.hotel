package com.winwintek.web.backend.api.entity.client.student.signedAgreement;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@DynamicUpdate
@Table (name = "TRIPARTITE_AGREEMENT")
public class TripartiteAgreementStdEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String tripartiteAgreementSeq;
    private String internPlanSeq;
    private String internStdSeq;
    private String corporateSignedYn;
    private Instant corporateSignedDate;
    private String stdSignedYn;
    private Instant stdSignedDate;
    private String univSignedYn;
    private String univSignedDate;
    private String lastSignedDate;
    private String delYn;

}
