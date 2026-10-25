package com.winwintek.web.backend.api.domain.admin.supportCenter.summaryReport.service;

import com.winwintek.web.backend.api.dto.admin.supportCenter.summaryReport.SummaryReportSupportCenterReqDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.summaryReport.SummaryReportSupportCenterResDTO;

public interface SummaryReportSupportCenterService {

    SummaryReportSupportCenterResDTO getDetailSummaryReport(String internStdId);

    String createSummaryReport(SummaryReportSupportCenterReqDTO dto);

    String updateSummaryReport(SummaryReportSupportCenterReqDTO dto);

}
