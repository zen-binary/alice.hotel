import { defineStore } from "pinia";
import { ref } from "vue";
import { GET_LIST_DATA, DETAIL_BY_ID, CREATE, UPDATE, DELETE } from "./resource.service";
import type { ResourceResponseModel } from "./resource.type";
import { useFetchData } from "@/utils/useFetchData";
import { PostType } from "@/stores/common/demoFetchData/demo.type.ts";
import { RequestOptions } from "@/utils/useFetchData.ts";

export const resourceStore = defineStore("resourceStore", () => {
  const resultData = ref<ResourceResponseModel[]>([]);
  const detailData = ref<ResourceResponseModel>();
  const status = ref<number>();
  const totalRecord = ref<number>();

  const searchResource = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<PostType[]>(GET_LIST_DATA, option);
    resultData.value = dataFetch.value;
    totalRecord.value = dataFetch.value.data.totalElements;
    status.value = statusFetch;
  };

  const getDetailById = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<PostType[]>(DETAIL_BY_ID, option);
    detailData.value = dataFetch.value.data;
    status.value = statusFetch;
  };

  const createResource = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<PostType[]>(CREATE, option);
    status.value = statusFetch;
  };

  const updateResource = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<PostType[]>(UPDATE, option);
    status.value = statusFetch;
  };

  const deleteResoure = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<PostType[]>(DELETE, option);
    resultData.value = dataFetch.value;
    status.value = statusFetch;
  };

  return {
    resultData,
    detailData,
    status,
    totalRecord,
    searchResource,
    getDetailById,
    createResource,
    updateResource,
    deleteResoure,
  };
});
