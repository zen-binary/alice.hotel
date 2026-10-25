package com.winwintek.web.backend.api.dto.admin.internPlanCenter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternPlanCenterDetailListDto {

    private String internDetailSeq;

    private String week;

    private String startDate;

    private String endDate;

    private String content;

    private String delYn;
}
