export interface LoginSchoolNoticeResDTO {
    noticeSeq: string;
    title: string; 
    regDate: string;
}

export interface SchoolNoticeResDTO {
    rowNumber: string,
    noticeSeq: string,
    title: string,
    regDate: string
}

export interface SchoolNoticeReqDTO {
    title: string
    page: number
    size: number
    sort: string
}

export interface SchoolNoticeDetailResDTO {
    title: string,
    content: string,
    regDate: string
}