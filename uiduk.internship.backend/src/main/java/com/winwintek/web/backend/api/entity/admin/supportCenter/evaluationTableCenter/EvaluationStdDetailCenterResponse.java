package com.winwintek.web.backend.api.entity.admin.supportCenter.evaluationTableCenter;

import java.sql.Clob;

public interface EvaluationStdDetailCenterResponse {

    String getInternStdSeq();

    String getEvaluationStdSeq();

    Clob getEvaluationFeedback();

    Clob getStudentFeedback();

    String getEvaluationSubmitDate();
}
