import { defineStore } from "pinia";
import { ref } from "vue";
import { RequestOptions, useFetchData } from "@/utils/useFetchData";
import { ListWeekInfoResModel, LitStudentAdmin, WeekReportResModel } from "./weekReport.type";
import { CREATE_WEEK_REPORT, GET_DETAIL_WEEK_REPORT, GET_LIST_DATA_STUDENT, GET_LIST_DATA_WEEK } from "./weekReport.service";

export const weekReportStore = defineStore("weekReportStore", () => {
  const status = ref<number>();
  const resultData = ref<WeekReportResModel>();
  const listWeek = ref<ListWeekInfoResModel[]>([]);
  const listStd = ref<LitStudentAdmin[]>([]);
  const listWeekReportData = ref<string[]>([]);

  const getListWeek = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<ListWeekInfoResModel>(GET_LIST_DATA_WEEK, option);

    listWeek.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getListStd = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<LitStudentAdmin>(GET_LIST_DATA_STUDENT, option);

    listStd.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getDetailWeekReport = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<WeekReportResModel>(GET_DETAIL_WEEK_REPORT, option);

    resultData.value = dataFetch.value;
    status.value = statusFetch;
  };

  const createWeekReport = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<Array<string>>(CREATE_WEEK_REPORT, option);

    listWeekReportData.value = dataFetch.value;
    status.value = statusFetch;
  };

  return {
    status,
    resultData,
    listWeek,
    listStd,
    listWeekReportData,
    getListWeek,
    getListStd,
    getDetailWeekReport,
    createWeekReport
  };
});
