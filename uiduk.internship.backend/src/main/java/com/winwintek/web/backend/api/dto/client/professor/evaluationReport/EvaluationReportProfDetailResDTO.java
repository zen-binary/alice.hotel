package com.winwintek.web.backend.api.dto.client.professor.evaluationReport;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EvaluationReportProfDetailResDTO {

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
