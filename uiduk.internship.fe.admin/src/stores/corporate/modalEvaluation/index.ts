import { defineStore } from "pinia";
import { ref } from "vue";
import { RequestOptions, useFetchData } from "@/utils/useFetchData";
import { EvaluationStdModel } from "./modalEvaluation.type";
import { GET_LIST_DATA, SAVE_DATA } from "./modalEvaluation.service";

export const modalEvaluationStore = defineStore("modalEvaluation", () => {
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
