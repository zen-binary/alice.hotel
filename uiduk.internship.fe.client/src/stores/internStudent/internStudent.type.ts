export interface InternStudentRegResDTO {
  internStdSeq: string;
  stdNo: string;
  internPlanSeq: string;
  approveStatus: string;
  delYn: string;
}

export interface InternshipStudentReqDTO {
  selectedYear: string
  internshipStatus: string
  internshipName: string
}

export interface InternStudentListResDTO {
  internStdSeq: string
  internshipName: string
  approveStatusCd: string
  approveStatusNm: string
  internshipStatusCd: string
  internshipStatusNm: string
  internshipField: string
  cormName: string
  address: string
  internshipStr: string
  internshipEnd: string
  corporateSignYn: string
  urlFile: string
  internPlanSeq: string
}
