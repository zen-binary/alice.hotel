package com.winwintek.web.backend.api.dto.admin.internPlanCenter;

import com.winwintek.web.backend.api.dto.common.pageable.PageableDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternPlanCenterStudentDto extends PageableDto {
    private String idInternPlan;
}
