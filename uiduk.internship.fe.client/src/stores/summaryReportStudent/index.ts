import { RequestOptions, useFetchData } from "@/utils/useFetchData";
import { defineStore } from "pinia";

import { ref } from "vue";
import { SummaryDetailResponseModel } from "./summaryReportStudent.type";
import { CREATE_SUMMARY_REPORT_STUDENT, DETAIL_SUMMARY_REPORT_STUDENT, UPDATE_SUMMARY_REPORT_STUDENT } from "./summaryReportStudent.service";

export const summaryReportStudentStore = defineStore("summaryReportStudentStore", () => {
  const detailData = ref<SummaryDetailResponseModel>();
  const status = ref<number>();
  const dataRes = ref();

  const getDetail = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<SummaryDetailResponseModel[]>(DETAIL_SUMMARY_REPORT_STUDENT, option);

    detailData.value = dataFetch.value?.data || null;
    status.value = statusFetch;
  };

  const createSummary = async (option: RequestOptions) => {
    const { statusFetch, dataFetch } = await useFetchData<[]>(CREATE_SUMMARY_REPORT_STUDENT, option);

    status.value = statusFetch;
    dataRes.value = dataFetch.value;
  };

  const updateSummary = async (option: RequestOptions) => {
    const { statusFetch } = await useFetchData<[]>(UPDATE_SUMMARY_REPORT_STUDENT, option);

    status.value = statusFetch;
  };
  return {
    detailData,
    status,
    getDetail,
    createSummary,
    updateSummary,
    dataRes,
  };
});
