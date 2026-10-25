package com.winwintek.web.backend.api.dto.admin.internPlan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternPlanListDto {

    private String rowNumb;

    private String internPlanSeq;

    private String statusCd;

    private String status;

    private String confirmDate;

    private String cormNameKr;

    private String internPlanNm;

    private Integer executionCount;

    private String registerDate;

    private String internShipDate;

    private String regDate;

    private String rejectionReasonDetails;

}
