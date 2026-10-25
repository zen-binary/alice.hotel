import { defineStore } from "pinia";
import { ref } from "vue";
import { RequestOptions, useFetchData } from "@/utils/useFetchData";
import { InternCorporateSurveyModel } from "./modalSurvey.type";
import { GET_DETAIL, SAVE_DATA } from "./modalSurvey.service";

export const modalSurveyCorpStore = defineStore("modalSurveyCorp", () => {
  const status = ref<number>();
  const resultData = ref<InternCorporateSurveyModel[]>([]);

  const getSuveyCorp = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<InternCorporateSurveyModel[]>(GET_DETAIL, option);

    resultData.value = dataFetch.value;
    status.value = statusFetch;
  };

  const saveSuveyCorp = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<boolean>(SAVE_DATA, option);

    status.value = statusFetch;
  };

  return {
    status,
    resultData,
    getSuveyCorp,
    saveSuveyCorp,
  };
});
