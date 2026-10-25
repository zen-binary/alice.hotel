export type CorporateSurveyResModel = {
    corpSurveyId: string;
    corporateNm: string;
    numberIntern: number;
    termCd: string;
    termNm: string;
    recommend: string;
    numberInternCd: string;
    lstSurveyQuest: Array<CorporateSurveyQuestResModel>;
};
  
export type CorporateSurveyQuestResModel = {
    surveyQuestId: string;
    questContent: string;
    displayOrder: string;
    score: number;
}

export interface LitStudentAdmin {
    id: string;
    name: string;
  }