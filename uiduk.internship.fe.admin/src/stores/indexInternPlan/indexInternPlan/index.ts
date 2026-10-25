import { defineStore } from "pinia";
import { ListInternPlanType, SearchInternPlanType } from "./indexInternPlan.type";
import { ref } from "vue";
import { RequestOptions, useFetchData } from "@/utils/useFetchData";
import { LIST_INTERN_PLAN } from "./indexInternPlan.service";

export const internPlanStore = defineStore('internPlanStore', ()=>{
    const resultData = ref<ListInternPlanType[]>([])
    const status = ref<number>()
    const totalRecord = ref<number>()

    const searchInternPlan = async (option:  RequestOptions) =>{

        const { dataFetch, statusFetch} = await useFetchData<SearchInternPlanType[]>(LIST_INTERN_PLAN, option);

        resultData.value = dataFetch.value;
        totalRecord.value = dataFetch.value.data.totalElements;
        status.value = statusFetch;
        
    }

    return{
        resultData,
        status,
        totalRecord,
        searchInternPlan
    }


})