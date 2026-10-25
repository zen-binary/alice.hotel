export type fileMngModel = {
    id: number,
    fimSubFileId: number,
    fimFileCategory: string,
    fimFileName: string,
    fimFilePath: string,
    fimFileExt: string,
    fimFileSize: number,
    fimUseYn: string,
    fimReferKeyId: number,
    fimFileOrgName: string,
    fimSectionName: string,
    deleteFlag: number,
}

export type fileUploadedInfo = {
    id: number,
    fimFileName: string,
    fimFilePath: string,
    deleteFlag: number,
}
