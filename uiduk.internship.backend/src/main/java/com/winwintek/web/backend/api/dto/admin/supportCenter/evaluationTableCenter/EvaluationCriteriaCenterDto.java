package com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationTableCenter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluationCriteriaCenterDto {
    private String evaluationCriteriaSeq;

    private String criteriaCategory;

    private String displayOrder;

    private String studentEvaluationSeq;

    private Integer score;
}
