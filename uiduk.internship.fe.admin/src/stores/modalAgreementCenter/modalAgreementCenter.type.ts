export type AgreementCenterTableModel = {
  internTerm: string;
  internYear: string;
  cormNameKr: string;
  internshipStr: string;
  internshipEnd: string;
  duration: string;
  durationHourStr: string;
  durationMinuteStr: string;
  durationHourEnd: string;
  durationMinuteEnd: string;
  internshipWeekDay: string;
  internshipDay: string;
  paymentAmount: string;
  paymentFrequency: string;
  paymentTimingType: string;
  paymentDate: string;
  employmentContractYn: string;
  insuranceParticipationYn: string;
  regDate: string;
  urlFile: string;
  interPlanNm: string;
  cormSeq: string;
  uniSignedDate: string;
  managerNm: string;
};

export type StudentInternSupportCenterModel = {
  id: string;
  name: string;
  corpSignedYn: string;
  stdSignedYn: string;
  univSignedYn: string;
  tripartiteAgreementSeq: string;
  uniSignedDate: string;
  deptStdNm: string;
};

export type UpdateListAppendSupportCenterModel = {
  lstId: Array<string>;
};
