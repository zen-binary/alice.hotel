import {defineStore} from "pinia";
import {ref} from "vue";
import {useFetchData} from "@/utils/useFetchData";
import {RequestOptions} from "@/utils/useFetchData.ts";
import { InternStdSurveyDetailReqDTO, StdSurveyInfoModel } from "@/stores/studentSurvey/studentSurvey.type";
import {
  GET_SURVEY_DETAIL,
  SAVE_INTERN_STD_SURVEY,
  SAVE_LIST_STD_SURVEY_ANS, UPDATE_INTERN_STD_SURVEY, UPDATE_LIST_STD_SURVEY_ANS,
} from "@/stores/studentSurvey/studentSurvey.service";

export const stdSurveyStore = defineStore("stdSurveyStore", () => {
  const list = ref<InternStdSurveyDetailReqDTO[]>([]);
  // const dataId = ref<DocIdModel>();
  const status = ref<number | null>(null);

  const getStdSurveyDetail = async (option: RequestOptions) => {
    const {
      dataFetch,
      statusFetch,
      error,
      isLoading,
      fetchData
    } = await useFetchData<StdSurveyInfoModel[]>(GET_SURVEY_DETAIL, option);

    list.value = dataFetch.value;
    status.value = statusFetch;
  };

  const saveInternStdSurvey = async (option: RequestOptions) => {
    const {
      dataFetch,
      statusFetch,
      error,
      isLoading,
      fetchData
    } = await useFetchData<StdSurveyInfoModel[]>(SAVE_INTERN_STD_SURVEY , option);
    list.value = dataFetch.value;
    status.value = statusFetch;
  };

  const updateInternStdSurvey = async (option: RequestOptions) => {
    const {
      dataFetch,
      statusFetch,
      error,
      isLoading,
      fetchData
    } = await useFetchData<StdSurveyInfoModel[]>(UPDATE_INTERN_STD_SURVEY , option);

    status.value = statusFetch;
  };

  const saveListSurveyAns = async (option: RequestOptions) => {
    const {
      dataFetch,
      statusFetch,
      error,
      isLoading,
      fetchData
    } = await useFetchData<StdSurveyInfoModel[]>(SAVE_LIST_STD_SURVEY_ANS, option);

    status.value = statusFetch;
  };

  const updateListSurveyAns = async (option: RequestOptions) => {
    const {
      dataFetch,
      statusFetch,
      error,
      isLoading,
      fetchData
    } = await useFetchData<StdSurveyInfoModel[]>(UPDATE_LIST_STD_SURVEY_ANS, option);

    status.value = statusFetch;
  };

  return {
    list,
    status,
    saveInternStdSurvey,
    saveListSurveyAns,
    updateListSurveyAns,
    getStdSurveyDetail,
    updateInternStdSurvey
  };
});
