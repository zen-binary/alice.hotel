export type AttendanceTrackingReqModel = {
  internPlanSeq: string;
  internStdSeq: string;
  lstAttendanceTracking: AttendanceTrackingModel[];
};

export type AttendanceTrackingModel = {
  internDetailSeq: string;
  year: string;
  week: string;
  lstDayWeek: AttendanceTrackingDayWeekModel[][];
  attendance: number;
  absence: number;
  holiday: number;
};

export type AttendanceTrackingDayWeekModel = {
  dayOfWeek: string;
  date: string;
  info: AttendanceTrackingDayModel;
};

export type AttendanceTrackingDayModel = {
  stdAttendanceSeq: string;
  date: string;
  status: string;
};

export type AttendanceTrackingStudentInfoResModel = {
  colgNm: string;
  sustNm: string;
  majorNm: string;
  stdNo: string;
  korNm: string;
  cormNameKr: string;
  internship: string;
};

export type AttendanceTrackingLstStudentResModel = {
  stdNo: string;
  korNm: string;
};
