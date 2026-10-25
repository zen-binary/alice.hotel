package com.winwintek.web.backend.api.dto.admin.supportCenter.guidanceReportCenter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuidanceReportCenterDetailResDTO {
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
