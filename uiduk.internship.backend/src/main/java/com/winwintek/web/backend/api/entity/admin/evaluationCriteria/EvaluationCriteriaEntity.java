package com.winwintek.web.backend.api.entity.admin.evaluationCriteria;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table (name = "EVALUATION_CRITERIA")
public class EvaluationCriteriaEntity extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    /** PK */
//    @Column (name = "EVALUATION_CRITERIA_SEQ")
    private String evaluationCriteriaSeq;

    /**
     * 평가영역
     */
//    @Column (name = "CRITERIA_FIELD")
    private String criteriaField;

    /**
     * 평가항목
     */
//    @Column (name = "CRITERIA_CATEGORY")
    private String criteriaCategory;

    /**
     * 표시 순서
     */
//    @Column (name = "DISPLAY_ORDER")
    private Integer displayOrder;

    /**
     * 삭제여부
     */
//    @Column (name = "DEL_YN")
    private String delYn;

}
