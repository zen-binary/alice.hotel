export type CompletionMngRequestModel = {
  internPlanId: string;
  page: number;
  size: number;
  sort: string;
};

export type CompletionMngResponseModel = {
  rowNumb: string;
  internStdSeq: string;
  internShipStatusCd: string;
  internShipStatus: string;
  colgNm: string;
  sustNm: string;
  shyrNm: string;
  stdNo: string;
  korNm: string;
  handNo: string;
  email: string;
  regDate: string;
};
export type CountCompletionModel = {
  waitCompleteCount: number;
  completeCount: number;
  notCompleteCount: number;
};
