import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import { CORPORATE_REGISTER, CORPORATE_REGISTER_CHECK, CORPORATE_REGISTER_CMNG } from "@/stores/corporateRegister/corporateRegister.service";
import { RequestOptions } from "@/utils/useFetchData.ts";
import type { CodeMngModel, CodeMngRes } from "@/stores/common/codeMng/codeMng.type";
export const corporateRegisterStore = defineStore("corporateRegister", () => {
  const dataId = ref<string>();
  const isCheck = ref<boolean>();
  const status = ref<number | null>(null);

  const codeInfo = ref<CodeMngModel[]>();
  // Reactive reference to hold the status of the fetch operation
  const statusCmng = ref<number>();

  const fetchCorporateRegister = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<string>(CORPORATE_REGISTER, option);
    dataId.value = dataFetch.value;
    status.value = statusFetch;
  };

  const fetchRegisterCheck = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<boolean>(CORPORATE_REGISTER_CHECK, option);
    isCheck.value = dataFetch.value;
    status.value = statusFetch;
  };

  // Async function to fetch code information based on provided options
  const getCodeInfo = async (option: RequestOptions) => {
    // Call the useFetchData utility to fetch data from the specified endpoint
    const { dataFetch, statusFetch } = await useFetchData<CodeMngRes[]>(CORPORATE_REGISTER_CMNG, option);
    // Update the reactive state with the fetched data and status
    codeInfo.value = dataFetch?.value; // Set the codeInfo to the fetched data
    statusCmng.value = statusFetch; // Set the status to the fetch status
  };

  return {
    dataId,
    status,
    isCheck,
    fetchCorporateRegister,
    fetchRegisterCheck,
    getCodeInfo,
    statusCmng,
    codeInfo,
  };
});
