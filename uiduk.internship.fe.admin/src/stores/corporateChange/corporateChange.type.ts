export interface CorporateInfoChange {
    cormUserId: string;
    userPw: string; 
    cormSeq: string;
    cormNameKr: string;
    cormNameEn: string;
    cormAgentNmKr: string;
    cormRegistNumber: string;
    cormType: string;
    address: string;
    cormCategories: string;
    cormClassificationCd: string;
    cormHomepageUrl: string;
    cormAwardCd: string;
    cormZoneCode: string;
    cormAddress: string;
    cormAddressDetail: string;
    cormPhone: string;
    cormStatus: string;
    lstCorporateChange: CorporateChange[];
}

export interface CorporateChange {
    commNm: string;
    commDept: string;
    positionName: string;
    companyPhone: string;
    mobilePhone: string;
    email: string;
}