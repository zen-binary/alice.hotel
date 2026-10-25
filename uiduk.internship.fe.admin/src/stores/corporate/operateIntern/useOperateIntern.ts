import { defineStore } from "pinia";
import { OperateInternResponseModel } from "./operateIntern.type";
import { ref } from "vue";
import { RequestOptions, useFetchData } from "@/utils/useFetchData";
import { GET_LIST_DATA } from "./operateIntern.service";

export const operateInternStore = defineStore("operateInternStore", () => {
  const status = ref<number>();
  const resultData = ref<OperateInternResponseModel[]>([]);

  const searchOperateIntern = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<OperateInternResponseModel[]>(GET_LIST_DATA, option);

    resultData.value = dataFetch.value;
    status.value = statusFetch;
  };

  return {
    status,
    resultData,
    searchOperateIntern,
  };
});
