export type StudentSurveyResModel = {
    stdSurveyId: string;
    stdNm: string;
    shyrCd: string;
    shyrNm: string;
    recommend: string;
    termCd: string;
    termNm: string;
    lstSurveyQuest: Array<StudentSurveyQuestResModel>;
};
  
export type StudentSurveyQuestResModel = {
    surveyQuestId: string;
    questContent: string;
    displayOrder: string;
    score: number;
}

export interface StudentInternClient {
    id: string;
    name: string;
  }