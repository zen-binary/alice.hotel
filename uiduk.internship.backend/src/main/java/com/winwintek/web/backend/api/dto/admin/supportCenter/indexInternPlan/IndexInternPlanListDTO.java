package com.winwintek.web.backend.api.dto.admin.supportCenter.indexInternPlan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IndexInternPlanListDTO {

    private Long rowNumber;

    private String internPlanSeq;

    private String cormNameKr;

    private String internPlanNm;

    private String quality;

    private String internStrDate;

    private String internEndDate;

}
