import { defineStore } from "pinia";
import { SignedResponses } from "./semesterAgreement.type";
import { ref } from "vue";
import { RequestOptions, useFetchData } from "@/utils/useFetchData";
import { GET_LIST_DATA, UPDATE_APPEND_DATA } from "./semesterAgreement.service";

export const signedAgreementStore = defineStore("signedAgreementStore", () => {
  const status = ref<number>();
  const resultData = ref<SignedResponses>({});

  const getListSign = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<SignedResponses>(GET_LIST_DATA, option);

    resultData.value = dataFetch.value?.data || null;
    status.value = statusFetch;
  };

  const updateAppentData = async (option: RequestOptions) => {
    const { statusFetch } = await useFetchData(UPDATE_APPEND_DATA, option);

    status.value = statusFetch?.value;  
  };

  return {
    status,
    resultData,
    getListSign,
    updateAppentData,
  };
});
