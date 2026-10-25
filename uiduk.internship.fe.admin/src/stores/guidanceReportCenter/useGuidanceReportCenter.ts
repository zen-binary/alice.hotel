import { RequestOptions } from "@/utils/useFetchData.ts";
import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import {
  END_POINTS_CREATE_GUID_REPORT_REPORT,
  END_POINTS_DETAIL_GUID_REPORT_REPORT,
  END_POINTS_STD,
  END_POINTS_UPDATE_GUID_REPORT_REPORT,
} from "./guidanceReportCenter.service";
import { GuidanceReportCenterDetailRes, StudentIntern } from "./guidanceReportCenter.type";

export const guidanceReportCenterStore = defineStore("guidanceReportCenterStore", () => {
  const list = ref<GuidanceReportCenterDetailRes>();
  const listStd = ref<StudentIntern[]>([]);
  const status = ref<number | null>(null);
  const dataRes = ref();

  const fetchDetail = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<GuidanceReportCenterDetailRes>(END_POINTS_DETAIL_GUID_REPORT_REPORT, option);
    list.value = dataFetch.value;
    status.value = statusFetch;
  };

  const updateForm = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData(END_POINTS_UPDATE_GUID_REPORT_REPORT, option);

    dataRes.value = dataFetch.value;
    status.value = statusFetch;
  };

  const createForm = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData(END_POINTS_CREATE_GUID_REPORT_REPORT, option);

    dataRes.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getlistStd = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<StudentIntern>(END_POINTS_STD, option);

    listStd.value = dataFetch.value;
    status.value = statusFetch;
  };

  return {
    list,
    status,
    fetchDetail,
    updateForm,
    getlistStd,
    listStd,
    createForm,
    dataRes,
  };
});
