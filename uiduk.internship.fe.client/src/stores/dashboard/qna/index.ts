import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import { RequestOptions } from "@/utils/useFetchData.ts";
import { DashboardQnaResDTO } from "./qna.type";
import { getDashboardQnaList } from "./qna.service";

export const dashboardQnaStore = defineStore("dashboardQnaStore", () => {
    const listDashboardQNA = ref<DashboardQnaResDTO[]>([]);
    const status = ref<number | null>(null);

    const fetchDashboardQNA = async (option: RequestOptions) => {
        const { dataFetch, statusFetch } = await useFetchData<DashboardQnaResDTO[]>(getDashboardQnaList, option);
        listDashboardQNA.value = dataFetch.value?.data || [];
        status.value = statusFetch.value;
    }
    return { 
        listDashboardQNA, 
        status, 
        fetchDashboardQNA 
    };
});