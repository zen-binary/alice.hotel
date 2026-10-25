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

export type WeekReportReqModel = {
  stdWeekReportSeq: string;
  internDetailSeq: string;
  internStdSeq: string;
  dateWeek: string;
  inHour: string;
  inMinute: string;
  outHour: string;
  outMinute: string;
  jobContent: string;
  acquiredKnowledge: string;
};

export interface LitStudentAdmin {
  id: string;
  name: string;
}
