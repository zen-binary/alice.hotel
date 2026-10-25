import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import { RequestOptions } from "@/utils/useFetchData.ts";
import { DashboardDocRoomResDTO } from "./docRoom.type";
import { getDashboardDocRoomList } from "./docRoom.service";

export const dashboardDocRoomStore = defineStore("dashboardDocRoomStore", () => {
    const listDashboardDocRoom = ref<DashboardDocRoomResDTO[]>([]);
    const status = ref<number | null>(null);

    const fetchDashboardDocRoom = async (option: RequestOptions) => {
        const { dataFetch, statusFetch } = await useFetchData<DashboardDocRoomResDTO[]>(getDashboardDocRoomList, option);
        listDashboardDocRoom.value = dataFetch.value?.data || [];
        status.value = statusFetch.value;
    }
    return { 
        listDashboardDocRoom, 
        status, 
        fetchDashboardDocRoom
    };
});