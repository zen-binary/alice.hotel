export type ResourceResponseModel = {
  rowNumber: string
  rsId: string
  url: string
  httpMethod: string
  rsNm: string
  rsType: string
  regDate: string
}

export type ResourceRequestModel = {
  rsId: string
  url: string
  httpMethod: string
  rsNm: string
  rsType: string
}

export type ResourceSearchModel = {
  url: string
  method: string
  name: string
  type: string
  startDate: string
  endDate: string
  page: number
  size: number
  sort: ''
}