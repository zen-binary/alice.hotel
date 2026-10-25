import { defineStore } from "pinia";
import { ref } from "vue";
import type { CorporatePreTestIdModel, CorporateMemberInfoModel } from "./corporatePreTest.type";
import {GET_LIST_DATA, CREATE_DATA, GET_DETAIL_CORP_PRE_TEST } from "./corporatePreTest.service";
import { useFetchData } from "@/utils/useFetchData";

export const corporatePreTestStore = defineStore("corporatePreTestStore", () => {
    const corporatePreTestIdModel = ref<CorporatePreTestIdModel>();
    const corporateMemberInfoModel = ref<CorporateMemberInfoModel>();
    const checkIns = ref<boolean>
    const status = ref<number>();

    const getAllData = async (option: CorporatePreTestIdModel) => {
        const { dataFetch, statusFetch  } = await useFetchData<CorporatePreTestIdModel>(GET_DETAIL_CORP_PRE_TEST, option);
        corporateMemberInfoModel.value = dataFetch.value;
        status.value = statusFetch;
    };

    const createData = async (option: CorporateMemberInfoModel) => {
        const { dataFetch, statusFetch  } = await useFetchData<CorporateMemberInfoModel>(CREATE_DATA, option);
        checkIns.value = dataFetch.value;
        status.value = statusFetch;
    };

    return {
        corporatePreTestIdModel,
        corporateMemberInfoModel,
        status,
        checkIns,
        getAllData,
        createData,
      };
})