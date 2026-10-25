export type ResourceRoleResponseModel = {
    rlId: string
    rsId: string
    rrId: string
}

export type ResourceRoleRequestModel = {
    roleId: string
    listInsert: Array<string>
    listDelete: Array<string>
}