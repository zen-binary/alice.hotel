export interface QnaListResDTO {
    qnaSeq: string,
    title: string,
    regDate: string,
    answer: string,
}

export interface QnaReqDTO {
    title: string
    page: number
    size: number
    sort: string
}

export interface QnaDetailResDTO {
    title: string,
    content: string,
    regDate: string,
    answer: string,
    modifyId: string,
    modifyDate: string
}

export interface QnaRegReqDTO {
    title: string,
    content: string,
}

export interface QnaRegResDTO {
    qnaSeq: string,
    title: string,
    content: string
}