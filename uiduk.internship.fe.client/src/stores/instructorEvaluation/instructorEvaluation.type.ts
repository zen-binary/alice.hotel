export interface EvaluationReportProfDetailRes {
  id: string;
  internPlanNm: string;
  deptStdNm: string;
  companyNm: string;
  internStartDate: string;
  internEndDate: string;
  stdNm: string;
  internWorkDept: string;
  attendanceStatus: number;
  weekReport: number;
  summaryReport: number;
  guidanceReport: number;
  totalCompanyScore: number;
  deptProfNm: string;
  profNm: string;
  isUpdate: string;
}
export interface StudentInternClient {
  id: string;
  name: string;
}
export interface EvaluationReportProfRequest {
  internProcessEvaluationSeq: string;
  internPlanSeq: string;
  internStdSeq: string;
  workingDepartment: string;
  attendanceStatus: number;
  weekReport: number;
  summaryReport: number;
  guidanceReport: number;
}

export interface ResponseDetailRollCall { }

export type ListWeekInfoResModel = {
  internPlanDetailId: string;
  internWeek: string;
};

export type WeekReportResModel = {
  stdNm: string;
  deptCd: string;
  deptNm: string;
  shyrCd: string;
  shyrNm: string;
  stdNo: string;
  corpId: string;
  lstWeekCalendar: Array<WeekCalendarResDTO>;
};

export type WeekCalendarResDTO = {
  stdWeekReportId: string;
  dateWeek: string;
  inHour: string;
  inMinute: string;
  outHour: string;
  outMinute: string;
  jobContent: string;
  acquiredKnowledge: string;
  attendanceDate: string;
  isDisabled: string;
};

export type SummaryReportResModel = {
  internStdId: string;
  stdSummaryReportId: string;
  stdNm: string;
  deptNm: string;
  stdYear: string;
  stdNo: string;
  content: string;
};
