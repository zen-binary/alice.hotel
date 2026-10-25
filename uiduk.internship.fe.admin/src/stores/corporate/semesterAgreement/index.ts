import { defineStore } from "pinia";
import { SignedResponses, StudentInternCorporateRes } from "./semesterAgreement.type";
import { ref } from "vue";
import { RequestOptions, useFetchData } from "@/utils/useFetchData";
import { CREATE_TRIPARTITE_AGREE_DATA, GET_LIST_DATA, GET_LIST_STD, UPDATE_APPEND_DATA } from "./semesterAgreement.service";

export const semesterAgreementStore = defineStore("semesterAgreementStore", () => {
  const status = ref<number>();
  const resultData = ref<SignedResponses[]>([]);
  const resultDataStd = ref<StudentInternCorporateRes[]>([]);

  const getListSign = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<SignedResponses[]>(GET_LIST_DATA, option);

    resultData.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getListStudent = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<StudentInternCorporateRes[]>(GET_LIST_STD, option);

    resultDataStd.value = dataFetch.value;
    status.value = statusFetch;
  };

  const updateAppentData = async (option: RequestOptions) => {
    const { statusFetch } = await useFetchData(UPDATE_APPEND_DATA, option);

    status.value = statusFetch;
  };

  const createTripartiteAgreeData = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<StudentInternCorporateRes>(CREATE_TRIPARTITE_AGREE_DATA, option);

    resultDataStd.value = dataFetch.value;
    status.value = statusFetch;
  };

  return {
    status,
    resultData,
    resultDataStd,
    getListSign,
    getListStudent,
    updateAppentData,
    createTripartiteAgreeData
  };
});
