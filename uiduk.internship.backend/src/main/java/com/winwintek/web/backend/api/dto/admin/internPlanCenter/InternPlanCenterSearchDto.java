package com.winwintek.web.backend.api.dto.admin.internPlanCenter;

import com.winwintek.web.backend.api.dto.common.pageable.PageableDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternPlanCenterSearchDto extends PageableDto {

    private String status;

    private String internPlanNm;

    private String registerStr;

    private String registerEnd;

    private String internShipStr;

    private String internShipEnd;

}
