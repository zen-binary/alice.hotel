package com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationReportCenter;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EvaluationReportCenterReqDTO {

    private String internProcessEvaluationSeq;

    private String internPlanSeq;

    private String internStdSeq;

    private String workingDepartment;

    private Integer attendanceStatus;

    private Integer weekReport;

    private Integer summaryReport;

    private Integer guidanceReport;

}
