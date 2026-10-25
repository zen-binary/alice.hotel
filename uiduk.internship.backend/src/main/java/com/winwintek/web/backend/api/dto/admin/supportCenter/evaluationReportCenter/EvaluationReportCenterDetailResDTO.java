package com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationReportCenter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluationReportCenterDetailResDTO {
    private String id;

    private String internPlanNm;

    private String deptStdNm;

    private String companyNm;

    private String internStartDate;

    private String internEndDate;

    private String stdNm;

    private String profNm;

    private String deptProfNm;

    private String internWorkDept;

    private Integer attendanceStatus;

    private Integer weekReport;

    private Integer summaryReport;

    private Integer guidanceReport;

    private Integer totalCompanyScore;

    private String isUpdate;
}
