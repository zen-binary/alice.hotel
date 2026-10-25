export interface QnaSearchDTO {
    title: string;
    answerYn: string;
    regName: string;
    phone: string;
    email: string;
    startDate: string;
    endDate: string;
    page: number;
    size: number;
    sort: string;
}
export interface QnaResDTO {
    rowNumber: number;
    qnaSeq: string;
    answerYn: string;
    title: string;
    regId: string;
    phone: string;
    email: string;
    regDate: string;
}
export interface QnaReqDTO {
    qnaSeq: string;
    answer: string;
}
export interface QnaDetailResDTO {
    qnaSeq: string;
    regId: string;
    phone: string;
    email: string;
    title: string;
    content: string;
    answer: string;
}