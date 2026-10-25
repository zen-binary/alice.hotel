package com.winwintek.web.backend.api.dto.admin.supportCenter.indexInternPlan;

import com.winwintek.web.backend.api.dto.common.pageable.PageableDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IndexInternPlanSearchDTO extends PageableDto {

    private String internPlanNm;

    private String startDate;

    private String endDate;

}
