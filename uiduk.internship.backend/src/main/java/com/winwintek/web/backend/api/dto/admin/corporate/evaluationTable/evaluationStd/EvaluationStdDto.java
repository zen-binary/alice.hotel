package com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable.evaluationStd;

import com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable.evaluationCriteria.EvaluationCriteriaParentDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EvaluationStdDto {

    private InternshipEvaluatorDto internshipEvaluatorDto;

    private List<StudentInfoDto> listStudentInfo;

    private List<EvaluationCriteriaParentDto> listEvaluationCriteria;

    private String evaluationStdSeq;

    private String internStdSeq;

    private String evaluationFeedback;

    private String studentFeedback;

    private String evaluationSubmitDate;

}
