export type RoleResponseModel = {
    rlId: number
    rlNm: string
    category: string
    etc: string
    rlCd: string
    rlDesc: string
}

export type RoleSearchModel = {
    rlId: string
    rlNm: string
    category: string
    etc: string
    rlCd: string
    rlDesc: string

    page: number
    size: number
    sort: string
}
