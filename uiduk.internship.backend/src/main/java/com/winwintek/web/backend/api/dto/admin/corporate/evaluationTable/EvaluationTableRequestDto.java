package com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable;

import com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable.studentEvaluation.StudentEvaluationRequestDto;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluationTableRequestDto {

    List<StudentEvaluationRequestDto> listStdEvaluation;

    private String evaluationStdSeq;

    private String internStdSeq;

    private String evaluationFeedback;

    private String studentFeedback;

    private String evaluationSubmitDate;

}
