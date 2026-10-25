import { defineStore } from "pinia";
import { ref } from "vue";
import { RequestOptions, useFetchData } from "@/utils/useFetchData";
import { StudentInternClient, StudentSurveyResModel } from "./studentSurvey.type";
import { GET_DETAIL_STD_SURVEY, GET_LIST_DATA_STUDENT } from "./studentSurvey.service";

export const studentSurveyStore = defineStore("studentSurveyStore", () => {
  const status = ref<number>();
  const resultData = ref<StudentSurveyResModel>();
  const listStd = ref<StudentInternClient[]>([]);

  const getListStd = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<StudentSurveyResModel>(GET_LIST_DATA_STUDENT, option);

    listStd.value = dataFetch.value
    status.value = statusFetch
  }

  const getDetailStdSurvey = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<StudentSurveyResModel>(GET_DETAIL_STD_SURVEY, option);

    resultData.value = dataFetch.value;
    status.value = statusFetch;
  };

  return {
    status,
    resultData,
    listStd,
    getListStd,
    getDetailStdSurvey,
  };
});
