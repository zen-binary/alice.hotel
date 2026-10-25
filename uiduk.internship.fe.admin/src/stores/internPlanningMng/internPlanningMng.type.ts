export type InternPlanningListSearchModel = {
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
  statusCd: string;
  status: string;
  confirmDate: string;
  cormNameKr: string;
  internPlanNm: string;
  executionCount: number;
  registerDate: string;
  internShipDate: string;
  regDate: string;
  rejectionReasonDetails: string;
  isCheck: boolean;
};

export type InternPlanRequestModel = {
  listInternPlanDetail: InternPlanDetailListModel[];
  internPlanSeq: string | null;
  internPlanNm: string;
  referInternPlanSeq: string | null;
  executionCount: number;
  cormSeq: string;
  commSeq: string;
  registerStr: Date | null;
  registerEnd: Date | null;
  internshipStr: Date | null;
  internshipEnd: Date | null;
  internRecruitmentQuantity: number;
  internshipField: string;
  staffNo: string;
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

export type InternPlanCormMemberInfoModel = {
  cormSeq: string;
  cormNameKr: string;
  cormNameEn: string;
};

export type InternPlanExistingModel = {
  internPlanSeq: string;
  internPlanNm: string;
  referInternPlanSeq: string;
};

export type CorporateManagerListModel = {
  commSeq: string;
  commNm: string;
  mobilePhone: string;
};

export type StaffInternModel = {
  staffNo: string;
  korNm: string;
};
