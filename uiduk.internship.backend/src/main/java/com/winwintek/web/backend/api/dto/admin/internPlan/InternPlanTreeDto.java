package com.winwintek.web.backend.api.dto.admin.internPlan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternPlanTreeDto {

    private String internPlanSeq;

    private String referInternPlanSeq;

    private Integer executionCount;

    private String treeRoot;

}
