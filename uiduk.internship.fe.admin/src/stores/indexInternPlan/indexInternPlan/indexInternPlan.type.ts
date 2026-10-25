export interface SearchInternPlanType {
  internPlanNm: string;
  startDate: string;
  endDate: string;
  page: number;
  size: number;
  sort: "";
}

export interface ListInternPlanType {
  rowNumber: number;
  internPlanSeq: string;
  cormNameKr: string;
  internPlanNm: string;
  quality: string;
  internStrDate: string;
  internEndDate: string;
  timePractice: string;
}
