import { defineStore } from "pinia";
import { ref } from "vue";
import { RequestOptions, useFetchData } from "@/utils/useFetchData";
import { WeekCalendarClientResModel, WeekClientModel } from "./tranningWeeklyReport.type";
import { GET_DETAIL_REPORT_WEEK, GET_LIST_WEEK, SAVE_DETAIL_REPORT_WEEK } from "./tranningWeeklyReport.service";

export const tranningWeekReportStore = defineStore("tranningWeekReport", () => {
  const weekClientModel = ref<WeekClientModel>();
  const listWeekClient = ref<WeekCalendarClientResModel>();
  const status = ref<number>();
  const listIdStdWeekReport = ref<Array<string>>([]);

  const getListWeekClient = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<RequestOptions>(GET_LIST_WEEK, option);
    weekClientModel.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getListWeekCalendar = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<RequestOptions>(GET_DETAIL_REPORT_WEEK, option);
    listWeekClient.value = dataFetch.value;
    status.value = statusFetch;
  };

  const saveListWeekCalendar = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<RequestOptions>(SAVE_DETAIL_REPORT_WEEK, option);
    listIdStdWeekReport.value = dataFetch.value;
    status.value = statusFetch;
  };

  return {
    weekClientModel,
    status,
    listWeekClient,
    listIdStdWeekReport,
    getListWeekCalendar,
    getListWeekClient,
    saveListWeekCalendar,
  };
});
