import { GET_LIST_DATA } from "./roleResource.service";
import { defineStore } from "pinia";
import type { RoleResponseModel, RoleSearchModel } from "./roleResource.type";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import { PostType } from "@/stores/common/demoFetchData/demo.type.ts";
import { RequestOptions } from "@/utils/useFetchData.ts";

export const roleResourceStore = defineStore("roleResourceStore", () => {
  const status = ref<number>();
  const resultData = ref<RoleResponseModel[]>([]);
  const totalRecord = ref<number>();

  const getListRole = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<PostType[]>(GET_LIST_DATA, option);
    resultData.value = dataFetch.value.data.content;
    status.value = statusFetch.value;
    totalRecord.value = dataFetch.value.data.totalElements;
  };
  return {
    status,
    resultData,
    totalRecord,
    getListRole,
  };
});
