import { defineStore } from "pinia";
import { ref } from "vue";
import type { CorporatePreTestSeqDTO, CorporateMemberReqDTO } from "./preTest.type";
import {GET_LIST_DATA } from "./preTest.service";
import { useFetchData } from "@/utils/useFetchData";
import { RequestOptions } from "@/utils/useFetchData.ts";

export const preTestStore = defineStore("preTestStore", () => {
    const corporatePreTestSeqDTO = ref<CorporatePreTestSeqDTO>();
    const corporateMemberReqDTO = ref<CorporateMemberReqDTO>();
    const status = ref<number>();

    const getAllData = async (option: RequestOptions) => {
        const { dataFetch, statusFetch  } = await useFetchData<CorporatePreTestSeqDTO>(GET_LIST_DATA, option);
        corporateMemberReqDTO.value = dataFetch.value;
        status.value = statusFetch;
    };

    return {
        corporatePreTestSeqDTO,
        corporateMemberReqDTO,
        status,
        getAllData
      };
})
