export type SearchWeekReportModel = {
  internPlanId: string;
};

export type WeekClientModel = {
  stdNm: string;
  deptNm: string;
  stdYear: string;
  stdNo: string;
  cormSeq: string;
  lstWeek: Array<ListWeekClient>;
};

export type ListWeekClient = {
  internDetailSeq: string;
  internStartDate: string;
  internEndDate: string;
  week: string;
};

export type WeekCalendarClientResModel = {
  stdWeekReportId: string;
  internPlanDetailSeq: string;
  internStdSeq: string;
  dateWeek: string;
  inHour: string;
  inMinute: string;
  outHour: string;
  outMinute: string;
  jobContent: string;
  acquiredKnowledge: string;
  attendanceDate: string;
  isDisabled: string;
  dayOfWeek: string;
  fileArray:[]
};
