package com.winwintek.web.backend.api.dto.client.corporateRegister;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CorporateRegisterRequest {

    private String commNm;

    private String commDept;

    private String positionName;

    private String companyPhone;

    private String mobilePhone;

    private String email;
}
