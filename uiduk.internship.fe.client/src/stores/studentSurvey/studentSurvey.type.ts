export interface StdSurveyInfoModel {
  name: string
  shyrCode: string
  internTerm: number
  internPlanSeq: string
  internStdSeq: string
}

export interface InternStdSurveyCreModel {
  internStdSurveySeq?: string
  internPlanSeq: string
  internStdSeq: string
  internRecommend: string
}

export interface ListStdSurveyAnsCreModel {
  listAnsSurvey: Array<any>;
}

export type StdSurveyAnsDTO = {
  stdSurveyAnsSeq: string
  qusContent: string
  displayOrder: string
  stdSurveyQusSeq: string
  score: string
}

export type InternStdSurveyDetailReqDTO ={
    studentName: string
    shyrCode : string
    internTerm: string
    internPlanSeq: string
    internStdSeq: string
    internRecommend: string
    internStdSurveySeq: string
    stdSurveyAnsDTOList: Array<StdSurveyAnsDTO>
}