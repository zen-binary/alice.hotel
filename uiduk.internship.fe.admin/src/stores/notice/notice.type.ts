export interface NoticeSearchDTO {
    title: string;
    startDateShow: string;
    endDateShow: string;
    useYn: string;
    startDate: string;
    endDate: string;
    regName: string;
    page: number;
    size: number;
    sort: string;
}
export interface NoticeResDTO {
    noId: number;
    useYn: string;
    title: string;
    noticeSeq: string;
    startDateShow: string;
    endDateShow: string;
    regDate: string;
    regId: string;
}
export interface NoticeReqDTO {
    noticeSeq: string;
    title: string;
    content: string;
    startDateShow: string;
    endDateShow: string;
    useYn: string;
    topFixYn: string;
}
export interface NoticeDetailResDTO {
    noticeSeq: string;
    title: string;
    content: string;
    startDateShow: string;
    endDateShow: string;
    useYn: string;
    topFixYn: string;
}