package com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationTableCenter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentEvaluationCenterRequestDto {
    private String studentEvaluationSeq;

    private String evaluationCriteriaSeq;

    private Integer score;
}
