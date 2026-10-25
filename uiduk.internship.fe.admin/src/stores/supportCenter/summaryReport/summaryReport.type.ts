export type SummaryReportResModel = {
  internStdId: string;
  stdSummaryReportId: string;
  stdNm: string;
  deptNm: string;
  stdYear: string;
  stdNo: string;
  content: string;
};

export type SummaryReportReqModel = {
    stdSummaryReportSeq: string;
    internPlanSeq: string;
    internStdSeq: string;
    content: string;
  };

export interface ListStudentAdmin {
  id: string;
  name: string;
}
