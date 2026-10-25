package com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable.studentEvaluation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentEvaluationRequestDto {

    private String studentEvaluationSeq;

    private String evaluationCriteriaSeq;

    private Integer score;

}
