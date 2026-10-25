import { RequestOptions } from "@/utils/useFetchData.ts";
import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import { END_POINTS_CREATE_VISIT_MAP_REPORT, END_POINTS_DETAIL_VISIT_MAP_REPORT, END_POINTS_STD, END_POINTS_UPDATE_VISIT_MAP_REPORT } from "./visitorMap.service";
import { GuidanceReportProfDetailRes, StudentInternClient } from "./visitorMap.type";

export const visitorMapStore = defineStore("visitorMapStore", () => {
  const list = ref<GuidanceReportProfDetailRes>({});
  const listStd = ref<StudentInternClient[]>([]);
  const status = ref<number | null>(null);
  const dataRes = ref();

  const fetchDetail = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<GuidanceReportProfDetailRes>(END_POINTS_DETAIL_VISIT_MAP_REPORT, option);

    list.value = dataFetch.value;
    status.value = statusFetch;
  };

  const updateForm = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData(END_POINTS_UPDATE_VISIT_MAP_REPORT, option);

    dataRes.value = dataFetch.value;
    status.value = statusFetch;
  };

  const createForm = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData(END_POINTS_CREATE_VISIT_MAP_REPORT, option);

    dataRes.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getlistStd = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<StudentInternClient>(END_POINTS_STD, option);

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
    dataRes
  };
});
