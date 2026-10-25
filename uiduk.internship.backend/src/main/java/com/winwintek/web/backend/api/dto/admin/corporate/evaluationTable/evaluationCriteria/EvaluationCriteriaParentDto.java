package com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable.evaluationCriteria;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EvaluationCriteriaParentDto {

    private List<EvaluationCriteriaDto> criteriaChild;

    private String criteriaField;

}
