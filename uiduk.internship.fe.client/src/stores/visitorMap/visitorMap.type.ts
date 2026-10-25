export interface GuidanceReportProfDetailRes {
  id: string;
  internPlanNm: string;
  deptStdNm: string;
  companyNm: string;
  internDate: string;
  stdNm: string;
  internWorkDept: string;
  internManager: string;
  internSituation: string;
  internContent: string;
  internCompanyFeedback: string;
  internProfFeedback: string;
  deptProfNm: string;
  profNm: string;
  isUpdate: string;
}

export interface GuidanceReportProfRequest {
  guidanceReportSeq: string;
  internPlanSeq: string;
  internStdSeq: string;
  workingDepartment: string;
  personInCharge: string;
  actualSituation: string;
  guidanceContent: string;
  corporateFeedback: string;
  resultsAndFeedback: string;
  internDate: string;
}

export interface StudentInternClient {
  id: string;
  name: string;
}