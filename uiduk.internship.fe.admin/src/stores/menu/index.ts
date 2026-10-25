import { defineStore } from "pinia";
import { ref } from "vue";
import type { MenuResponseModel } from "./menu.type";
import { GET_LIST_DATA, DETAIL_BY_ID, CREATE, UPDATE, DELETE, GET_COUNT_ORDER, GET_LIST_PARENT } from "./menu.service";
import { useFetchData } from "@/utils/useFetchData";
import { PostType } from "@/stores/common/demoFetchData/demo.type.ts";
import { RequestOptions } from "@/utils/useFetchData.ts";

export const menuStore = defineStore("menuStore", () => {
  const resultData = ref<MenuResponseModel[]>([]);
  const detailData = ref<MenuResponseModel>();
  const status = ref<number>();
  const totalRecord = ref<number>();

  const searchMenu = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(GET_LIST_DATA, option);
    resultData.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getDetailById = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(DETAIL_BY_ID, option);
    detailData.value = dataFetch.value.data;
    status.value = statusFetch.value;
  };

  const createMenu = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(CREATE, option);
    detailData.value = dataFetch.value.data;
    status.value = statusFetch;
  };

  const updateMenu = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(UPDATE, option);
    resultData.value = dataFetch.value;
    status.value = statusFetch;
  };

  const deleteMenu = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(DELETE, option);
    resultData.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getSelectBoxParent = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(GET_LIST_PARENT, option);
    resultData.value = dataFetch.value.data;
    status.value = statusFetch.value;
  };

  const getCountOrder = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(GET_COUNT_ORDER, option);
    resultData.value = dataFetch.value.data;
    status.value = statusFetch.value;
  };

  return {
    resultData,
    detailData,
    status,
    totalRecord,
    searchMenu,
    getDetailById,
    createMenu,
    updateMenu,
    deleteMenu,
    getSelectBoxParent,
    getCountOrder,
  };
});
