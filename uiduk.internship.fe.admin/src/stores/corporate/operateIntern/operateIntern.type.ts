export type OperateInternSearchModel = {
  planName: string;
  startDate: string;
  endDate: string;
  page: number;
  size: number;
  sort: string;
};

export type OperateInternResponseModel = {
  rowNumber: number;
  internPlanId: string;
  corporateNm: string;
  internPlanNm: string;
  numberIntern: number;
  internStrDate: string;
  internEndDate: string;
  internDate: string;
  corporatePreTestSeq: string
};
