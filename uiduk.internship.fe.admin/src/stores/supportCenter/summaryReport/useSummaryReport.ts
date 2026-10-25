import { defineStore } from "pinia";
import { ref } from "vue";
import { RequestOptions, useFetchData } from "@/utils/useFetchData";
import { ListStudentAdmin, SummaryReportResModel } from "./summaryReport.type";
import { CREATE_SUMMARY_REPORT, GET_DETAIL_SUMMARY_REPORT, GET_LIST_DATA_STUDENT, UPDATE_SUMMARY_REPORT } from "./summaryReport.service";

export const summaryReportStore = defineStore("summaryReportStore", () => {
  const status = ref<number>();
  const resultData = ref<SummaryReportResModel>();
  const listStd = ref<ListStudentAdmin[]>([]);
  const dataRes = ref<string>();

  const getListStd = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<ListStudentAdmin>(GET_LIST_DATA_STUDENT, option);

    listStd.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getDetailSummaryReport = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<SummaryReportResModel>(GET_DETAIL_SUMMARY_REPORT, option);

    resultData.value = dataFetch.value;
    status.value = statusFetch;
  };

  const createSummaryReport = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<string>(CREATE_SUMMARY_REPORT, option);

    dataRes.value = dataFetch.value;
    status.value = statusFetch;
  };

  const updateSummaryReport = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<string>(UPDATE_SUMMARY_REPORT, option);

    dataRes.value = dataFetch.value;
    status.value = statusFetch;
  };

  return {
    status,
    resultData,
    listStd,
    dataRes,
    getListStd,
    getDetailSummaryReport,
    createSummaryReport,
    updateSummaryReport,
  };
});
