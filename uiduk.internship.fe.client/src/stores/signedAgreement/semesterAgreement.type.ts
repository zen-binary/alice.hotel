export type SignedResponses = {
  internPlanName: string;
  internYear: string;
  internTerm: string;
  cormName: string;
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
  employmentContract: string;
  insuranceParticipation: string;
  corporateSignedDate: string;
  urlFile: string;
  tripartiteSeq: string;
  stdSignedDate: string;
  corpSignedYn: string;
  stdSignedYn: string;
  univSignedYn: string;
  managerNm: string;
};

export interface SignRequestDTO {
  tripartiteSeq: string;
}
