export type CorporatePreTestIdModel = {
    corporatePreTestSeq: string
    internPlanSeq: string
}

export type PreTestDetailSelectModel = {
    preTestDetailSeq: string
    addContent: string
}

export type PreTestDetailModel = {
    preTestDetailSeq: string
    preTestCategorySeq: string
    content: string
    displayOrder: string
    addContentYn: string
    checkYn: string
    contentDetail: string
}

export type PreTestCategoryModel = {
    preTestCategorySeq: string
    categoryIndex: string
    content: string
    displayOrder: string  
    preTestDetailDTO: []
}

export type CorporateMemberInfoModel = {
    cormSeq: string
    cormNameKr: string
    cormNameEn: string
    cormType: string
    cormCategories: string
    corporatePreTestSeq: string
    standardIndustryType: string
    internPlanSeq: string
    preTestDetailSelectDTOList: []
    preTestCategoryDTOList: []
}
