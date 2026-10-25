package com.winwintek.web.backend.api.dto.admin.corporateApproval;

import com.winwintek.web.backend.api.dto.common.pageable.PageableDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CorporateApprovalSearchDTO extends PageableDto {

    private String cormStatusCd;

    private String cormUserId;

    private String cormNmKr;

    private String cormClassificationCd;

    private String cormAwardCd;

    private String cormConfirmDateStr;

    private String cormConfirmDateEnd;

    private String strDate;

    private String endDate;

}
