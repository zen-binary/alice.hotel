export interface DocRoomResDTO {
    rowNumber: string,
    docSeq: string,
    title: string,
    regDate: string
}

export interface DocRoomReqDTO {
    title: string
    page: number
    size: number
    sort: string
}

export interface DocRoomDetailResDTO {
    title: string,
    content: string,
    regDate: string
}