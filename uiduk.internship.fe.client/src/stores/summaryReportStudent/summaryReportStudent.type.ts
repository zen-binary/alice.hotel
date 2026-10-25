export interface SummaryDetailResponseModel {
  internStdId: string;
  stdSummaryReportId: string;
  stdNm: string;
  deptNm: string;
  stdYear: string;
  stdNo: string;
  content: string;
  internPlanSeq: string;
}

export interface CreateComprehensive {
  stdSummaryReportSeq: string;
  internPlanSeq: string;
  internStdSeq: string;
  content: string;
}
