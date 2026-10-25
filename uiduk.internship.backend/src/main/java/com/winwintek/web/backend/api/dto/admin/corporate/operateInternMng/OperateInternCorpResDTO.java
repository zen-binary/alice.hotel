package com.winwintek.web.backend.api.dto.admin.corporate.operateInternMng;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OperateInternCorpResDTO {

    private Integer rowNumber;

    private String internPlanId;

    private String corporateNm;

    private String internPlanNm;

    private Integer numberIntern;

    private String internStrDate;

    private String internEndDate;

}
