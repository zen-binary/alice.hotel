export type CorporatePreTestSeqDTO = {
    internPlanSeq: string
}

export type PreTestDetailSelectResDTO = {
    preTestDetailSeq: string
    addContent: string
}

export type PreTestDetailResDTO = {
    preTestDetailSeq: string
    preTestCategorySeq: string
    content: string
    displayOrder: string
    addContentYn: string
    checkYn: string
    contentDetail: string
}

export type PreTestCategoryResDTO = {
    preTestCategorySeq: string
    categoryIndex: string
    content: string
    displayOrder: string  
    preTestDetailResDTOList: []
}

export type CorporateMemberReqDTO = {
    cormSeq: string
    cormNameKr: string
    cormNameEn: string
    cormType: string
    cormCategories: string
    corporatePreTestSeq: string
    standardIndustryType: string
    internPlanSeq: string
    preTestDetailSelectResDTO: []
    preTestCategoryResDTO: []
}
