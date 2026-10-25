package com.winwintek.web.backend.api.dto.admin.corporateChange;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CorporateInfoChangeResponse {


    // corp info
    private String cormSeq;

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
    List<CorporateChangeResponse> lstCorporateChange;
}
