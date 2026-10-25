package com.winwintek.web.backend.api.dto.admin.supportCenter.guidanceReportCenter;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GuidanceReportCenterReqDTO {

    private String guidanceReportSeq;

    private String internPlanSeq;

    private String internStdSeq;

    private String workingDepartment;

    private String personInCharge;

    private String actualSituation;

    private String guidanceContent;

    private String corporateFeedback;

    private String resultsAndFeedback;

    private String internDate;
}
