import { defineStore } from "pinia";
import { ref } from "vue";
import { CorporateApprovalMngResponse, CorporateApproval} from "./corporateApprovalMng.type";
import { RequestOptions, useFetchData } from "@/utils/useFetchData";
import {
  GET_LIST_DATA,
  GET_DETAIL_CORPORATE,
  CORPORATE_APPROVAL,
  EXCEL_DOWNLOAD_ALL
} from "./corporateApprovalMng.service";

export const corporateApprovalMngStore = defineStore("corporateApprovalMng", () => {
  const resultData = ref<CorporateApprovalMngResponse[]>([]);
  const resultDataExcel = ref<CorporateApprovalMngResponse[]>([]);
  const resultDataCorporate = ref<CorporateApproval[]>([]);
  const status = ref<number>();
  const totalRecord = ref<number>();

  const getListCorporateApprovalMng = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<CorporateApprovalMngResponse[]>(GET_LIST_DATA, option);
    resultData.value = dataFetch.value;
    status.value = statusFetch;
  };

  const approveCorporateApproval = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<boolean[]>(CORPORATE_APPROVAL, option);
    status.value = statusFetch;
  };

  const getDetailCorporate = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<boolean[]>(GET_DETAIL_CORPORATE, option);
    resultDataCorporate.value = dataFetch.value;
    status.value = statusFetch;
  };
  const getaAllDataExcel = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<CorporateApprovalMngResponse[]>(EXCEL_DOWNLOAD_ALL, option);
    resultDataExcel.value = dataFetch.value;
    status.value = statusFetch;
  };
  return {
    resultData,
    status,
    totalRecord,
    resultDataCorporate,
    getListCorporateApprovalMng,
    approveCorporateApproval,
    getDetailCorporate,
    getaAllDataExcel,
    resultDataExcel
  };
});