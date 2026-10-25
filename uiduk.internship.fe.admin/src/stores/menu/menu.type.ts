export type MenuResponseModel = {
  rowNumber: string;
  id: string;
  siteType: string;
  name: string;
  route: string;
  parentId: string;
  displayOrder: string;
  delYn: string;
  vnName: string;
  regId: string;
  regDate: string;
  screenName: string;
};

export type MenuRequestModel = {
  id: string;
  siteType: string;
  name: string;
  route: string;
  parentId: string;
  vnName: string;
  screenName: string;
  displayOrder: string;
};

export type MenuSearchModel = {
  name: string;
  route: string;
  vnName: string;
  page: number;
  size: number;
  sort: string;
};
