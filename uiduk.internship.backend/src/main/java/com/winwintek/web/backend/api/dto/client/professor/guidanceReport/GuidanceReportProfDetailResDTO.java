package com.winwintek.web.backend.api.dto.client.professor.guidanceReport;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GuidanceReportProfDetailResDTO {

    private String id;

    private String internPlanNm;

    private String deptStdNm;

    private String companyNm;

    private String internDate;

    private String stdNm;

    private String profNm;

    private String deptProfNm;

    private String internWorkDept;

    private String internManager;

    private String internSituation;

    private String internContent;

    private String internCompanyFeedback;

    private String internProfFeedback;

    private String isUpdate;

}
