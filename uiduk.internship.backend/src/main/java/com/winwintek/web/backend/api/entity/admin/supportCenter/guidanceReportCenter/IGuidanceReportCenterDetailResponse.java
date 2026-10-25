package com.winwintek.web.backend.api.entity.admin.supportCenter.guidanceReportCenter;

import java.sql.Clob;

public interface IGuidanceReportCenterDetailResponse {
    String getId();

    String getInternDate();

    String getInternWorkDept();

    String getInternManager();

    Clob getInternSituation();

    Clob getInternContent();

    Clob getInternCompanyFeedback();

    Clob getInternProfFeedback();
}
