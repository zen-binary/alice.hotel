export interface CorporateInfoRegister {
    userId: string;
    userPw: string; 
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
    lstCorporateReg: CorporateRegister[];
}

export interface CorporateRegister {
    commNm: string;
    commDept: string;
    positionName: string;
    companyPhone: string;
    mobilePhone: string;
    email: string;
}