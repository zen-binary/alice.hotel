export type InternMngListModel = { 
    internPlanSeq: string
    internPlanNm: string
    intershipField: string
    intershipStr: string
    intershipEnd: string
    cormNameKr: string
    cormNameEn: string
    cormType: string
    address: string
    guidanceReportSeq: string
    internProcessEvaluationSeq: string
    internshipStatus: string
    isShowReport: string
}

export type InternMngSearchModel = { 
    nameIntern: string
    year: string
}

export type InternMngResModel = { 
    internMngList: []
    yearList: []
}