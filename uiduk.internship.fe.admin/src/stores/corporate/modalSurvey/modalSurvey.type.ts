export type InternCorporateSurveyModel = {
  internPlanSeq: string;
  cormNm: string;
  internQuantity: number;
  internshipTerm: string;
  internCorporateSurveySeq: string;
  internRecommend: string;
  listQus: Array<CorporateSurveyQusModel>;
};

export type CorporateSurveyQusModel = {
  corporateSurveyQusSeq: string;
  qusContent: string;
  displayOrder: number;
  corporateSurveyAnsSeq: string;
  score: string;
};

export type InternCorporateSurveyReqModel = {
  internPlanSeq: string;
};
