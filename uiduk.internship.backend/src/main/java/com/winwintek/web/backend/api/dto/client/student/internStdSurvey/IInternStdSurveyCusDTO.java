package com.winwintek.web.backend.api.dto.client.student.internStdSurvey;

import java.sql.Clob;

public interface IInternStdSurveyCusDTO {

    String getStudentName();

    String getShyrCode();

    String getInternTerm();

    String getInternPlanSeq();

    String getInternStdSeq();

    Clob getInternRecommend();

    String getInternStdSurveySeq();

}
