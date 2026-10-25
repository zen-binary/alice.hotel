import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import { RequestOptions } from "@/utils/useFetchData.ts";
import { DashboardNoticeResDTO } from "./notice.type";
import { getDashboardNoticeList } from "./notice.service";

export const dashboardNoticeStore = defineStore("dashboardNoticeStore", () => {
    const listDashboardNotice = ref<DashboardNoticeResDTO[]>([]);
    const status = ref<number | null>(null);

    const fetchDashboardNotice = async (option: RequestOptions) => {
        const { dataFetch, statusFetch } = await useFetchData<DashboardNoticeResDTO[]>(getDashboardNoticeList, option);
        listDashboardNotice.value = dataFetch.value?.data || [];
        status.value = statusFetch.value;
    }
    return { 
        listDashboardNotice, 
        status, 
        fetchDashboardNotice 
    };
});