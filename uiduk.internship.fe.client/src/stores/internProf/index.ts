import { defineStore } from "pinia";
import { ref } from "vue";
import type { InternMngResModel, InternMngSearchModel } from "./internProf.type";
import {GET_LIST_DATA } from "./internProf.service";
import { useFetchData } from "@/utils/useFetchData";

export const internProfStore = defineStore("internProfStore", () => {
    const internMngResModel = ref<InternMngResModel>();
    const internMngSearchModel = ref<InternMngSearchModel>();
    const status = ref<number>();

    const getAllData = async (option: InternMngSearchModel) => {
        const { dataFetch, statusFetch  } = await useFetchData<InternMngSearchModel>(GET_LIST_DATA, option);
        internMngResModel.value = dataFetch.value;
        status.value = statusFetch;
    };

    return {
        internMngResModel,
        internMngSearchModel,
        status,
        getAllData,
      };
})