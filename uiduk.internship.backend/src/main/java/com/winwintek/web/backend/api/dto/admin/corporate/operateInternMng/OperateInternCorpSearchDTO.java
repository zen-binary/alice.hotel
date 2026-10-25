package com.winwintek.web.backend.api.dto.admin.corporate.operateInternMng;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.dto.common.pageable.PageableDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OperateInternCorpSearchDTO extends PageableDto {

    private String planName;

    private String startDate;

    private String endDate;

    private String planApproveStatus;

    private String corpApproveStatus;

    private String delYn;

}
