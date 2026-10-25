package com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationTableCenter;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class EvaluationCriteriaParentCenterDto {
    private List<EvaluationCriteriaCenterDto> criteriaChild;

    private String criteriaField;
}
