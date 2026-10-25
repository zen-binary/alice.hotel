export type DocRoomModel = {
    rowNumber: number
    docSeq: string
    useYn: string
    title: string
    regId: string
    regDate: string
}

export type DocRoomSearchModel = {
    title: string
    createdBy: string
    regStartDate: string
    regEndDate: string
    useYn: string
    page: number;
    size: number;
    sort: string;
}
export type DocRoomCreModel = {
    docSeq: string
    title: string
    useYn: string
    content: string
    topFixYn: string
}

export type DocIdModel = {
    docSeq: string
}