package com.winwintek.web.backend.api.dto.admin.internPlan;

import com.winwintek.web.backend.api.dto.common.pageable.PageableDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class InternPlanSearchDto extends PageableDto {

    private String status;

    private String internPlanNm;

    private String registerStr;

    private String registerEnd;

    private String internShipStr;

    private String internShipEnd;

}
