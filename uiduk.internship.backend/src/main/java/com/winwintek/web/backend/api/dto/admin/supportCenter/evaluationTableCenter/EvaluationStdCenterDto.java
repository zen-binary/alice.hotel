package com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationTableCenter;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EvaluationStdCenterDto {

    private InternshipEvaluatorCenterDto internshipEvaluatorDto;

    private List<StudentInfoCenterDto> listStudentInfo;

    private List<EvaluationCriteriaParentCenterDto> listEvaluationCriteria;

    private String evaluationStdSeq;

    private String internStdSeq;

    private String evaluationFeedback;

    private String studentFeedback;

    private String evaluationSubmitDate;
}
