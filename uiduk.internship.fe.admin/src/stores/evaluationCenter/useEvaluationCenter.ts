import { defineStore } from "pinia";
import { ref } from "vue";
import { RequestOptions, useFetchData } from "@/utils/useFetchData";
import { EvaluationStdModel } from "./evaluationCenter.type";
import { GET_LIST_DATA, SAVE_DATA } from "./evaluationCenter.service";

export const evaluationCenterStore = defineStore("evaluationCenterStore", () => {
  const status = ref<number>();
  const resultData = ref<EvaluationStdModel>();

  const getEvaluationStd = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<EvaluationStdModel>(GET_LIST_DATA, option);
    resultData.value = dataFetch.value;
    status.value = statusFetch;
  };

  const saveDataEvaluation = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<EvaluationStdModel>(SAVE_DATA, option);
    status.value = statusFetch;
  };

  return {
    status,
    resultData,
    getEvaluationStd,
    saveDataEvaluation,
  };
});
