import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import { RequestOptions } from "@/utils/useFetchData.ts";
import { LoginSchoolNoticeResDTO, SchoolNoticeDetailResDTO, SchoolNoticeResDTO } from "./schoolNotice.type";
import { getNoticeDetailNoLogin, getNoticeListNoLogin, getSchoolNoticeList } from "./schoolNotice.service";

export const schoolNoticeStore = defineStore("schoolNoticeStore", () => {
  const listLoginSchoolNotice = ref<LoginSchoolNoticeResDTO[]>([]);
  const status = ref<number | null>(null);
  const listNoticeNoLogin = ref<SchoolNoticeResDTO[]>([]);
  const noticeDetailNoLogin = ref<SchoolNoticeDetailResDTO>();
  const totalItems = ref<number>(0);

  const fetchLoginSchoolNotice = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<LoginSchoolNoticeResDTO[]>(getSchoolNoticeList, option);
    listLoginSchoolNotice.value = dataFetch.value?.data || [];
    status.value = statusFetch.value;
  };

  const fetchNoticeListNoLogin = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<SchoolNoticeResDTO[]>(getNoticeListNoLogin, option);
    listNoticeNoLogin.value = dataFetch.value?.data?.content || [];
    status.value = statusFetch.value;
    totalItems.value = dataFetch?.value?.data?.totalElements || 0;
  }

  const fetchNoticeDetailNoLogin = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<SchoolNoticeDetailResDTO>(getNoticeDetailNoLogin, option);
    status.value = statusFetch.value;
    noticeDetailNoLogin.value = dataFetch.value.data || {};
  }
  return {
    listLoginSchoolNotice,
    status,
    totalItems,
    listNoticeNoLogin,
    noticeDetailNoLogin,
    fetchLoginSchoolNotice,
    fetchNoticeListNoLogin,
    fetchNoticeDetailNoLogin
  };
});
