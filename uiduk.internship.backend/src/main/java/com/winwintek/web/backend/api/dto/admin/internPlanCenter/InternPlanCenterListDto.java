package com.winwintek.web.backend.api.dto.admin.internPlanCenter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternPlanCenterListDto {
    private String rowNumb;

    private String internPlanSeq;

    private String status;

    private String statusCd;

    private String confirmDate;

    private String cormNameKr;

    private String internPlanNm;

    private Integer executionCount;

    private String registerDate;

    private String internShipDate;

    private String regDate;

    private Integer internRecruit;

    private Integer internApplyRecruit;

    private String rejectionReasonDetails;

}
