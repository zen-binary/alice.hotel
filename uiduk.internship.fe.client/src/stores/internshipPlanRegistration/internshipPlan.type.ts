export interface InternshipRegResDTO {
  internPlanSeq: string;
  internshipName: string;
  corpName: string;
  quantity: string;
  internshipField: string;
  address: string;
  startDate: string;
  endDate: string;
  receptionStatus: string;
  cormSeq: string;
}

export interface InternshipRegReqDTO {
  selectedYear: string;
  receptionStatus: string;
  internshipName: string;
}

export interface InternshipPlanRegFormDTO {
  internPlanSeq: string;
  internPlanName: string;
  referInternPlanName: string;
  executionCount: string;
  cormName: string;
  commName: string;
  telNo: string;
  registerStr: string;
  registerEnd: string;
  internshipStr: string;
  internshipEnd: string;
  quantity: string;
  internshipField: string;
  staffName: string;
  internYear: string;
  internTerm: string;
  duration: string;
  durationHourStr: string;
  durationMinuteStr: string;
  durationHourEnd: string;
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
  isDisabled: string;
  internPlanDetailResDTO: InternPlanDetailResDTO[];
}

export interface InternPlanDetailResDTO{
  week: string
  startDate: string
  endDate: string
  content: string
}