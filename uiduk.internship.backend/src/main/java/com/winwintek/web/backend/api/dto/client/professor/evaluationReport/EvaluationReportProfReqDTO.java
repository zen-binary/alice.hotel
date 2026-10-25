package com.winwintek.web.backend.api.dto.client.professor.evaluationReport;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EvaluationReportProfReqDTO {

    private String internProcessEvaluationSeq;

    private String internPlanSeq;

    private String internStdSeq;

    private String workingDepartment;

    private Integer attendanceStatus;

    private Integer weekReport;

    private Integer summaryReport;

    private Integer guidanceReport;

}
