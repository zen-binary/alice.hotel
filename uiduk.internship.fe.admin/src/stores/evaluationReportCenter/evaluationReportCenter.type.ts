export interface EvaluationReportProfDetailRes {
  id: string;
  internPlanNm: string;
  deptStdNm: string;
  companyNm: string;
  internStartDate: string;
  internEndDate: string;
  stdNm: string;
  internWorkDept: string;
  attendanceStatus: number;
  weekReport: number;
  summaryReport: number;
  guidanceReport: number;
  totalCompanyScore: number;
  deptProfNm: string;
  profNm: string;
  isUpdate: string;
}
export interface StudentInternClient {
  id: string;
  name: string;
}
export interface EvaluationReportProfRequest {
  internProcessEvaluationSeq: string;
  internPlanSeq: string;
  internStdSeq: string;
  workingDepartment: string;
  attendanceStatus: number;
  weekReport: number;
  summaryReport: number;
  guidanceReport: number;
}
