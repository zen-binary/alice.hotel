package com.winwintek.web.backend.api.entity.client.professor.evaluationReport.summaryReport;

import java.sql.Clob;

public interface ISummaryReportProfResponse {

    String getInternStdId();

    String getStdSummaryReportId();

    String getStdNm();

    String getDeptNm();

    String getStdYear();

    String getStdNo();

    Clob getContent();

}
