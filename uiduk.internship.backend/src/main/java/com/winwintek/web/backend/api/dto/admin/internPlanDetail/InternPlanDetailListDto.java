package com.winwintek.web.backend.api.dto.admin.internPlanDetail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternPlanDetailListDto {

    private String internDetailSeq;

    private String week;

    private String startDate;

    private String endDate;

    private String content;

    private String delYn;

}
