package com.winwintek.web.backend.api.entity.admin.supportCenter.evaluationTableCenter;

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
@Entity
@Getter
@Setter
@Table (name = "EVALUATION_CRITERIA")
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class EvaluationCriteriaCenterEntity extends AbstractAuditingEntity<Long> implements Serializable {
    @Id
    private String evaluationCriteriaSeq;

    private String criteriaField;

    private String criteriaCategory;

    private Integer displayOrder;

    private String delYn;
}
