import { defineStore } from "pinia";
import { ref } from "vue";
import { RequestOptions, useFetchData } from "@/utils/useFetchData";
import { CorporateSurveyResModel, LitStudentAdmin } from "./corporateSurvey.type";
import { GET_DETAIL_CORP_SURVEY, GET_LIST_DATA_STUDENT } from "./corporateSurvey.service";

export const corporateSurveyStore = defineStore("corporateSurveyStore", () => {
  const status = ref<number>();
  const resultData = ref<CorporateSurveyResModel>();
  const listStd = ref<LitStudentAdmin[]>([]);

  const getListStd = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<LitStudentAdmin>(GET_LIST_DATA_STUDENT, option);

    listStd.value = dataFetch.value
    status.value = statusFetch
  }

  const getDetailCorpSurvey = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<CorporateSurveyResModel>(GET_DETAIL_CORP_SURVEY, option);

    resultData.value = dataFetch.value;
    status.value = statusFetch;
  };

  return {
    status,
    resultData,
    listStd,
    getListStd,
    getDetailCorpSurvey,
  };
});
