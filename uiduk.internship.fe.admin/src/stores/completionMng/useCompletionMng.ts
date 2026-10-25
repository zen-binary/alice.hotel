import { defineStore } from "pinia";
import { ref } from "vue";
import { CompletionMngResponseModel, CountCompletionModel } from "./completionMng.type";
import { RequestOptions, useFetchData } from "@/utils/useFetchData";
import {
  GET_LIST_DATA,
  COMPLETE_STS_INTERN,
  WAIT_COMPLETE_STS_INTERN,
  NOT_COMPLETE_STS_INTERN,
  BACKUPS_COMPLETE_STS_INTERN,
  COUNT_COMPLETE_STS,
} from "./completionMng.service";

export const completionMngStore = defineStore("completionMng", () => {
  const resultData = ref<CompletionMngResponseModel[]>([]);
  const resultDataCountSts = ref<CountCompletionModel>();
  const status = ref<number>();
  const totalRecord = ref<number>();

  const getLstInternStd = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<CompletionMngResponseModel[]>(GET_LIST_DATA, option);
    resultData.value = dataFetch.value;
    status.value = statusFetch;
  };

  const completeInternSts = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<boolean[]>(COMPLETE_STS_INTERN, option);
    status.value = statusFetch;
  };

  const waitCompleteInternSts = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<boolean[]>(WAIT_COMPLETE_STS_INTERN, option);
    status.value = statusFetch;
  };

  const notCompleteInternSts = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<boolean[]>(NOT_COMPLETE_STS_INTERN, option);
    status.value = statusFetch;
  };

  const backupsCompleteInternSts = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<boolean[]>(BACKUPS_COMPLETE_STS_INTERN, option);
    status.value = statusFetch;
  };

  const getCountCompleteSts = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<boolean[]>(COUNT_COMPLETE_STS, option);
    resultDataCountSts.value = dataFetch.value;
    status.value = statusFetch;
  };


  return {
    status,
    resultData,
    totalRecord,
    resultDataCountSts,
    getLstInternStd,
    completeInternSts,
    waitCompleteInternSts,
    notCompleteInternSts,
    backupsCompleteInternSts,
    getCountCompleteSts
  };
});
