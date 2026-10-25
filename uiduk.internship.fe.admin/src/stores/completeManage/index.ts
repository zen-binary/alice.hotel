import { defineStore } from "pinia";
import { CompleteManageResponse } from "./completeManage.type";
import { ref } from "vue";
import { RequestOptions } from "https";
import { useFetchData } from "@/utils/useFetchData";
import { END_POINTS_CANCEL_COMPLETE, END_POINTS_CANCEL_NOT_COMPLETE, END_POINTS_CANCEL_RESERVATION, END_POINTS_COMPLETE, END_POINTS_LIST_COMPLETE_MANAGE, END_POINTS_RESERVE, END_POINTS_UN_FINISH } from "./completeManage.service";

export const completeManageStore = defineStore("completeManageStore", () => {
  const resultData = ref<CompleteManageResponse[]>([]);
  const status = ref<number>();
  const totalRecord = ref<number>()

  const getListCompleteManage = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<CompleteManageResponse>(END_POINTS_LIST_COMPLETE_MANAGE, option);

    resultData.value = dataFetch.value;
    status.value = statusFetch;
    totalRecord.value = dataFetch.value.data.totalElements;
    
  };

  const completeItem = async (option: RequestOptions) => {
    const {  statusFetch } = await useFetchData(END_POINTS_COMPLETE, option);

    status.value = statusFetch;
  };

  const cancelCompleteItem = async (option: RequestOptions) => {
    const {  statusFetch } = await useFetchData(END_POINTS_CANCEL_COMPLETE, option);

    status.value = statusFetch;
  };

  const unFinishItem = async (option: RequestOptions) => {
    const {  statusFetch } = await useFetchData(END_POINTS_UN_FINISH, option);

    status.value = statusFetch;
  };

  const cancelNotCompleteItem = async (option: RequestOptions) => {
    const {  statusFetch } = await useFetchData(END_POINTS_CANCEL_NOT_COMPLETE, option);

    status.value = statusFetch;
  };

  const reserveItem = async (option: RequestOptions) => {
    const {  statusFetch } = await useFetchData(END_POINTS_RESERVE, option);

    status.value = statusFetch;
  };

  const cancelReserveItem = async (option: RequestOptions) => {
    const {  statusFetch } = await useFetchData(END_POINTS_CANCEL_RESERVATION, option);

    status.value = statusFetch;
  };

  return {
    resultData,
    status,
    getListCompleteManage,
    completeItem,
    cancelCompleteItem,
    unFinishItem,
    cancelNotCompleteItem,
    reserveItem,
    cancelReserveItem,
    totalRecord
  };
});
