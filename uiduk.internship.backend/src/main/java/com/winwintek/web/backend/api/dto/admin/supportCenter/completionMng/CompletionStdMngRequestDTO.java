package com.winwintek.web.backend.api.dto.admin.supportCenter.completionMng;

import com.winwintek.web.backend.api.dto.common.pageable.PageableDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompletionStdMngRequestDTO extends PageableDto {

    private String internPlanId;

    private String internWaitComplete;

    private String internComplete;

    private String internNotComplete;

    private String internCancelBackups;

    private String approveStatusIntern;

}
