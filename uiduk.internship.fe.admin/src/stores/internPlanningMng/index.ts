import { defineStore } from "pinia";
import { ref } from "vue";
import {
  CorporateManagerListModel,
  InternPlanCormMemberInfoModel,
  InternPlanExistingModel,
  InternPlanListModel,
  InternPlanRequestModel,
  StaffInternModel,
} from "./internPlanningMng.type";
import { RequestOptions, useFetchData } from "@/utils/useFetchData";
import { PostType } from "../common/demoFetchData/demo.type";
import {
  CREATE,
  DELETE,
  DETAIL,
  GET_COMPORATOR_MANAGER,
  GET_CORM_MEMBER_INFO,
  GET_COUNT_EXECUTION,
  GET_EXISTING_INTERN,
  GET_EXISTING_INTERN_UPDATE,
  GET_LIST_DATA,
  GET_LIST_PROFESSOR,
} from "./internPlanningMng.service";

export const internPlanMngStore = defineStore("internPlanMng", () => {
  const resultData = ref<InternPlanListModel[]>([]);
  const cormMemberInfo = ref<InternPlanCormMemberInfoModel>();
  const listInternExist = ref<InternPlanExistingModel[]>([]);
  const listComporatorManager = ref<CorporateManagerListModel[]>([]);
  const listProfessor = ref<StaffInternModel[]>([]);
  const detailInternPlan = ref<InternPlanRequestModel>();
  const countExecution = ref<number>();
  const status = ref<number>();
  const totalRecord = ref<number>();
  const internPlanSeqSave = ref<string>("");

  const searchListInternPlan = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(GET_LIST_DATA, option);
    resultData.value = dataFetch.value;
    status.value = statusFetch;
  };

  const deleteListInternPlan = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(DELETE, option);
    resultData.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getCormMemberInfo = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(GET_CORM_MEMBER_INFO, option);
    cormMemberInfo.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getListInternExist = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(GET_EXISTING_INTERN, option);
    listInternExist.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getListInternExistUpdate = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(GET_EXISTING_INTERN_UPDATE, option);
    listInternExist.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getCountExecution = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(GET_COUNT_EXECUTION, option);
    countExecution.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getComporatorManager = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(GET_COMPORATOR_MANAGER, option);
    listComporatorManager.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getListProfessor = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(GET_LIST_PROFESSOR, option);
    listProfessor.value = dataFetch.value;
    status.value = statusFetch;
  };

  const saveInternPlan = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(CREATE, option);
    internPlanSeqSave.value = dataFetch.value;
    status.value = statusFetch;
  };

  const detailInternPlanMng = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(DETAIL, option);
    detailInternPlan.value = dataFetch.value;
    status.value = statusFetch;
  };

  return {
    resultData,
    status,
    cormMemberInfo,
    totalRecord,
    listInternExist,
    countExecution,
    listComporatorManager,
    listProfessor,
    detailInternPlan,
    internPlanSeqSave,
    searchListInternPlan,
    getCormMemberInfo,
    deleteListInternPlan,
    getListInternExist,
    getCountExecution,
    getComporatorManager,
    getListProfessor,
    saveInternPlan,
    detailInternPlanMng,
    getListInternExistUpdate,
  };
});
