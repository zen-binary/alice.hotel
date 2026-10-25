package com.winwintek.web.backend.api.dto.admin.corporateChange;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CorporateChangeRequest {

    private String commSeq;

    private String commNm;

    private String commDept;

    private String positionName;

    private String companyPhone;

    private String mobilePhone;

    private String email;
}
