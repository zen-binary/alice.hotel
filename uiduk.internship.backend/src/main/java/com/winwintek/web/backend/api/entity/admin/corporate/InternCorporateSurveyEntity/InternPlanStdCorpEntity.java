package com.winwintek.web.backend.api.entity.admin.corporate.InternCorporateSurveyEntity;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table (name = "INTERN_STD")
public class InternPlanStdCorpEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String internStdSeq;

    private String stdNo;

    private String internPlanSeq;

    private String approveStatus;

    private Date approveDate;

    private String internshipStatus;

    private String delYn;

}
