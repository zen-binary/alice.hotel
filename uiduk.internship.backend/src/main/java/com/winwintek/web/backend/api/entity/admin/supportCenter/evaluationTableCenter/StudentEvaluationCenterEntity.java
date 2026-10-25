package com.winwintek.web.backend.api.entity.admin.supportCenter.evaluationTableCenter;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "STUDENT_EVALUATION")
public class StudentEvaluationCenterEntity extends AbstractAuditingEntity<Long> implements Serializable {
    @Id
    private String studentEvaluationSeq;

    private String evaluationStdSeq;

    private String evaluationCriteriaSeq;

    private Integer score;

    private String delYn;
}
