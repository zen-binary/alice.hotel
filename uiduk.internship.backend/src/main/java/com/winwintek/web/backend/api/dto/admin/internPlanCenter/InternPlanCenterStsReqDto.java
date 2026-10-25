package com.winwintek.web.backend.api.dto.admin.internPlanCenter;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InternPlanCenterStsReqDto {
    private List<String> lstIdInternPlan;

    private String stsInternPlan;

    private String reasonMessage;
}
