package com.winwintek.web.backend.api.dto.admin.internPlanCenter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternPlanCountStsReqDto {

    private String idInternPlan;

    private String waitApproveStatusCount;

    private String approveStatusCount;

    private String notApproveStatusCount;

}
