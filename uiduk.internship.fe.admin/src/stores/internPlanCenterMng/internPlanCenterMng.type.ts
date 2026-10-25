export type InternPlanListSearchModel = {
  status: string;
  internPlanNm: string;
  registerStr: string;
  registerEnd: string;
  internShipStr: string;
  internShipEnd: string;
  page: number;
  size: number;
  sort: string;
};

export type InternPlanListModel = {
  rowNumb: string;
  internPlanSeq: string;
  status: string;
  confirmDate: string;
  cormNameKr: string;
  internPlanNm: string;
  executionCount: number;
  registerDate: string;
  internShipDate: string;
  regDate: string;
  internRecruit: number;
  internApplyRecruit: number;
};

export type InternPlanStudentListModel = {
  rowNumb: string;
  internStdSeq: string;
  approveStatus: string;
  colgNm: string;
  sustNm: string;
  shyrNm: string;
  stdNo: string;
  korNm: string;
  handNo: string;
  email: string;
  regDate: string;
};

export type InternPlanStudentStsModel = {
  waitApproveStatusCount: string;
  approveStatusCount: string;
  notApproveStatusCount: string;
};


export type InternPlanResponseModel = {
  listInternPlanDetail: InternPlanDetailListModel[];
  internPlanSeq: string | null;
  internPlanNm: string;
  referInternPlanSeq: string;
  referInternPlanNm: string;
  executionCount: number;
  cormSeq: string;
  cormNameKr: string;
  commSeq: string;
  commNm: string;
  registerStr: Date | null;
  registerEnd: Date | null;
  internshipStr: Date | null;
  internshipEnd: Date | null;
  internRecruitmentQuantity: number;
  internshipField: string;
  staffNo: string;
  staffNm: string;
  internYear: string;
  internTerm: string;
  duration: string;
  durationHourStr: string;
  durationHourEnd: string;
  durationMinuteStr: string;
  durationMinuteEnd: string;
  internshipWeekday: string;
  internshipDay: string;
  paymentAmount: string;
  paymentFrequency: string;
  paymentTimingType: string;
  paymentDate: string;
  employmentContractYn: string;
  insuranceParticipationYn: string;
  internJobSummary: string;
  requirements: string;
  status: string;
};

export type InternPlanDetailListModel = {
  internDetailSeq: string | null;
  week: string;
  startDate: string | null;
  endDate: string | null;
  content: string;
  delYn: string;
};