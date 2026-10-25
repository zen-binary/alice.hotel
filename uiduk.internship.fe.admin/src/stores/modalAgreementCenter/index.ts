import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import { PostType } from "@/stores/common/demoFetchData/demo.type.ts";
import { RequestOptions } from "@/utils/useFetchData.ts";
import { AgreementCenterTableModel, StudentInternSupportCenterModel } from "./modalAgreementCenter.type";
import { APPEND_ALL, GET_DATA, GET_LIST_STUDENT_INFO } from "./modalAgreementCenter.service";

export const modalAgreementCenterStore = defineStore("modalAgreementCenter", () => {
  const resultData = ref<AgreementCenterTableModel>();
  const resultDataStd = ref<StudentInternSupportCenterModel[]>();
  const status = ref<number>();

  const getSignSupportCenter = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(GET_DATA, option);
    resultData.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getListStudent = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(GET_LIST_STUDENT_INFO, option);
    resultDataStd.value = dataFetch.value;
    status.value = statusFetch;
  };

  const appendAll = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(APPEND_ALL, option);
    status.value = statusFetch;
  };

  return {
    resultData,
    status,
    resultDataStd,
    getSignSupportCenter,
    getListStudent,
    appendAll,
  };
});
