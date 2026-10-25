package com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable.evaluationCriteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluationCriteriaDto {

    private String evaluationCriteriaSeq;

    private String criteriaCategory;

    private String displayOrder;

    private String studentEvaluationSeq;

    private Integer score;

}
