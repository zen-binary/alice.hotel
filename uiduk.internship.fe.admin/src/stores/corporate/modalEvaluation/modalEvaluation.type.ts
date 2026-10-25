export type EvaluationTableDetailModel = {
  internPlanSeq: string;
  stdNo: string;
};

export type EvaluationStdModel = {
  internshipEvaluatorDto: InternshipEvaluatorModel;
  listStudentInfo: Array<StudentInfoModel>;
  listEvaluationCriteria: Array<EvaluationCriteriaParentModel>;
  evaluationStdSeq: string;
  internStdSeq: string;
  evaluationFeedback: string;
  studentFeedback: string;
  evaluationSubmitDate: string;
};

export type InternshipEvaluatorModel = {
  cormSeq: string;
  cormNameKr: string;
  commNm: string;
  commDept: string;
  positionName: string;
};

export type StudentInfoModel = {
  stdNo: string;
  korNm: string;
  colgCdNm: string;
  sustCdNm: string;
  mjCdNm: string;
  internshipStr: string;
  internshipEnd: string;
  cormNameKr: string;
};

export type EvaluationCriteriaParentModel = {
  criteriaChild: Array<EvaluationCriteriaModel>;
  criteriaField: string;
  scoreTotal: number;
};

export type EvaluationCriteriaModel = {
  evaluationCriteriaSeq: string;
  criteriaCategory: string;
  displayOrder: string;
  studentEvaluationSeq: string;
  score: number;
};
