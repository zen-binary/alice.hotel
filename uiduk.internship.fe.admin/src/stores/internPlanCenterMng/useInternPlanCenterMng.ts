import { defineStore } from "pinia";
import { ref } from "vue";
import { InternPlanListModel, InternPlanStudentListModel, InternPlanStudentStsModel,InternPlanResponseModel } from "./internPlanCenterMng.type";
import { RequestOptions, useFetchData } from "@/utils/useFetchData";
import { PostType } from "../common/demoFetchData/demo.type";
import {
  GET_LIST_DATA,
  GET_LIST_DATA_STUDENT,
  GET_LIST_DATA_STUDENT_STS,
  APPROVE_STS,
  REJECT_APPROVE_STS,
  CANCEL_APPROVE_STS,
  DELETE_INTERN_PLAN,
  APPROVE_STS_STD,
  NOT_APPROVE_STS_STD,
  CANCEL_APPROVE_STS_STD,
  DELETE_INTERN_STD,
  DETAIL
} from "./internPlanCenterMng.service";

export const internPlanCenterMngStore = defineStore("internPlanCenterMng", () => {
  const resultData = ref<InternPlanListModel[]>([]);
  const resultDataStudent = ref<InternPlanStudentListModel[]>([]);
  const resultDataStsStudent = ref<InternPlanStudentStsModel>();
  const status = ref<number>();
  const totalRecord = ref<number>();
  const detailInternPlan = ref<InternPlanResponseModel>();

  const searchListInternPlan = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(GET_LIST_DATA, option);
    resultData.value = dataFetch.value;
    status.value = statusFetch;
  };

  const deleteInternPlan = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(DELETE_INTERN_PLAN, option);
    status.value = statusFetch;
  };

  const approveInternPlan = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(APPROVE_STS, option);
    status.value = statusFetch;
  };

  const rejectApproveInternPlan = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(REJECT_APPROVE_STS, option);
    status.value = statusFetch;
  };

  const cancelApproveInternPlan = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(CANCEL_APPROVE_STS, option);
    status.value = statusFetch;
  };

  const getListInternPlanStudent = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(GET_LIST_DATA_STUDENT, option);
    resultDataStudent.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getStatusApprove = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(GET_LIST_DATA_STUDENT_STS, option);
    resultDataStsStudent.value = dataFetch.value;
    status.value = statusFetch;
  };

  const deleteInternStd = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(DELETE_INTERN_STD, option);
    status.value = statusFetch;
  };

  const approveInternStd = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(APPROVE_STS_STD, option);
    status.value = statusFetch;
  };

  const notApproveInternStd = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(NOT_APPROVE_STS_STD, option);
    status.value = statusFetch;
  };

  const cancelApproveInternStd = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(CANCEL_APPROVE_STS_STD, option);
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
    totalRecord,
    searchListInternPlan,
    getListInternPlanStudent,
    resultDataStudent,
    getStatusApprove,
    resultDataStsStudent,
    deleteInternPlan,
    approveInternPlan,
    rejectApproveInternPlan,
    cancelApproveInternPlan,
    deleteInternStd,
    approveInternStd,
    notApproveInternStd,
    cancelApproveInternStd,
    detailInternPlanMng,
    detailInternPlan
  };
});
