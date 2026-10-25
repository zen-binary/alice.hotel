package com.winwintek.web.backend.api.dto.admin.supportCenter.completeManage;

import com.winwintek.web.backend.api.dto.common.pageable.PageableDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompleteGetByInternPlanId extends PageableDto {

    private String internPlanId;
}
