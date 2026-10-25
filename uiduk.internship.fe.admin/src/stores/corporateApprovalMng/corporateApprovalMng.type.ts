export interface CorporateApprovalMngResponse {
  rowNumb: string;
  cormSeq: string;
  cormStatusCd: string;
  cormStatusNm: string;
  cormConfirmDate: string;
  cormUserId: string;
  cormNameKr: string;
  cormClassificationNm: string;
  cormAwardNm: string;
  regDate: string;
}

export interface CorporateApprovalMngSearch {
  cormStatusCd: string;
  cormUserId: string;
  cormNmKr: string;
  cormClassificationCd: string;
  cormAwardCd: string;
  cormConfirmDateStr: string;
  cormConfirmDateEnd: string;
  strDate: string;
  endDate: string;
  page: number;
  size: number;
  sort: string;
}
export interface CorporateApproval {
  cormUserId: string;
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
  lstCorporateManager: CorporateApprovalManager[];
}

export interface CorporateApprovalManager {
  commNm: string;
  commDept: string;
  positionName: string;
  companyPhone: string;
  mobilePhone: string;
  email: string;
}
