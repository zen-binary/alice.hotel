package com.winwintek.web.backend.api.entity.client.professor.guidanceReport;

import java.sql.Clob;

public interface IGuidanceReportDetailResponse {

    String getId();

    String getInternDate();

    String getInternWorkDept();

    String getInternManager();

    Clob getInternSituation();

    Clob getInternContent();

    Clob getInternCompanyFeedback();

    Clob getInternProfFeedback();

}
