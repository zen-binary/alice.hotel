import { RequestOptions } from "@/utils/useFetchData.ts";
import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import { EvaluationReportProfDetailRes, StudentInternClient } from "./evaluationReportCenter.type";
import {
  END_POINTS_CREATE_INSTRUCTOR_EVALUATION,
  END_POINTS_DETAIL_INSTRUCTOR_EVALUATION,
  END_POINTS_STD,
  END_POINTS_UPDATE_INSTRUCTOR_EVALUATION,
} from "./evaluationReportCenter.service";

export const evaluationReportCenterStore = defineStore("evaluationReportCenterStore", () => {
  const list = ref<EvaluationReportProfDetailRes>();
  const listStd = ref<StudentInternClient[]>([]);
  const status = ref<number | null>(null);
  const dataRes = ref();

  const fetchDetailProfessor = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<EvaluationReportProfDetailRes>(END_POINTS_DETAIL_INSTRUCTOR_EVALUATION, option);
    list.value = dataFetch.value;
    status.value = statusFetch;
  };

  const updateForm = async (option: RequestOptions) => {
    const  { dataFetch, statusFetch } = await useFetchData(END_POINTS_UPDATE_INSTRUCTOR_EVALUATION, option);
    dataRes.value = dataFetch.value;
    status.value = statusFetch;
  };

  const createForm = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData(END_POINTS_CREATE_INSTRUCTOR_EVALUATION, option);

    dataRes.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getlistStd = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<StudentInternClient>(END_POINTS_STD, option);
    listStd.value = dataFetch.value;
    status.value = statusFetch;
  };


  return {
    list,
    status,
    fetchDetailProfessor,
    updateForm,
    createForm,
    getlistStd,
    listStd,
    dataRes,
  };
});
