package com.winwintek.web.backend.api.dto.client.corporateRegister;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CorporateInfoRegisterRequest {
    // corp user
    private String userId;

    private String userPw;
    // corp info
    private String cormNameKr;

    private String cormNameEn;

    private String cormAgentNmKr;

    private String cormRegistNumber;

    private String cormType;

    private String address;

    private String cormCategories;

    private String cormClassificationCd;

    private String cormHomepageUrl;

    private String cormAwardCd;

    private String cormZoneCode;

    private String cormAddress;

    private String cormAddressDetail;

    private String cormUserId;

    private String cormPhone;

    private String cormStatus;

    // corp reg
    List<CorporateRegisterRequest> lstCorporateReg;
}
