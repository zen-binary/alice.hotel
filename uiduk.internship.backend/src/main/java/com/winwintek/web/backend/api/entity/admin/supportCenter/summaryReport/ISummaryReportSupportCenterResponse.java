package com.winwintek.web.backend.api.entity.admin.supportCenter.summaryReport;

import java.sql.Clob;

public interface ISummaryReportSupportCenterResponse {

    String getInternStdId();

    String getStdSummaryReportId();

    String getStdNm();

    String getDeptNm();

    String getStdYear();

    String getStdNo();

    Clob getContent();

}
