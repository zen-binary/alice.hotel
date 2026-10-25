import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import { NOTICE_CREATE_POST, NOTICE_DELETE_POST, NOTICE_DETAIL_POST, NOTICE_LIST_POST, NOTICE_UPDATE_POST } from "@/stores/notice/notice.service.ts";
import { RequestOptions } from "@/utils/useFetchData.ts";
import { NoticeReqDTO, NoticeResDTO, NoticeDetailResDTO } from "@/stores/notice/notice.type.ts";

export const noticeStore = defineStore("noticeStore", () => {
  const listNotice = ref<NoticeResDTO[]>([]);
  const dataId = ref<NoticeDetailResDTO>();
  const status = ref<number>();
  const res = ref<string | null>("")
  const totalRecord = ref<number>();
  
  const noticeList = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<NoticeResDTO[]>(NOTICE_LIST_POST, option);

    listNotice.value = dataFetch.value;
    totalRecord.value = dataFetch.value.data.totalElements;    
    status.value = statusFetch;
  };

  const noticeDetail = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<NoticeDetailResDTO>(NOTICE_DETAIL_POST, option);

    dataId.value = dataFetch.value;
    status.value = statusFetch;
  };
  const noticeCreate = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<string>(NOTICE_CREATE_POST, option);

    res.value = dataFetch.value;
    status.value = statusFetch;
  };

  const deleteNotice = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<string>(NOTICE_DELETE_POST, option);
    res.value = dataFetch.value;
    status.value = statusFetch;
  };

  const updateNotice = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<string>(NOTICE_UPDATE_POST, option);
    res.value = dataFetch.value;
    status.value = statusFetch;
  };

  return {
    res,
    listNotice,
    dataId,
    status,
    totalRecord,
    noticeList,
    noticeDetail,
    deleteNotice,
    noticeCreate,
    updateNotice,
  };
});
