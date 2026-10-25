package com.winwintek.web.backend.api.dto.admin.supportCenter.summaryReport;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SummaryReportSupportCenterReqDTO {

    private String stdSummaryReportSeq;

    private String internPlanSeq;

    private String internStdSeq;

    private String content;

}
