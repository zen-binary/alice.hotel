package com.winwintek.web.backend.api.entity.admin.evaluationStd;

import java.sql.Clob;

public interface EvaluationStdDetailResponse {

    String getInternStdSeq();

    String getEvaluationStdSeq();

    Clob getEvaluationFeedback();

    Clob getStudentFeedback();

    String getEvaluationSubmitDate();

}
