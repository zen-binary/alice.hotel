package com.winwintek.web.backend.api.entity.client.student.summaryReportStudent;

import java.sql.Clob;

public interface ISummaryReportStudentResponse {

    String getInternStdId();

    String getStdSummaryReportId();

    String getStdNm();

    String getDeptNm();

    String getStdYear();

    String getStdNo();

    Clob getContent();

    String getInternPlanSeq();

    String getInternshipStatus();

}
