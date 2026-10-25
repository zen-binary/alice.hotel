import {defineStore} from "pinia";
import {ref} from "vue";
import {useFetchData} from "@/utils/useFetchData";
import {
    DELETE_DR,
    UPDATE_DR,
    FIND_BY_ID,
    GET_PAGE_DR,
    SAVE_DR,
} from "./docRoom.service"
import {RequestOptions} from "@/utils/useFetchData.ts";
import {DocIdModel, DocRoomModel} from "@/stores/docRoom/docRoom.type";

export const docRoomStore = defineStore("docRoomStore", () => {
    const list = ref<DocRoomModel[]>([]);
    const dataId = ref<DocIdModel>();
    const status = ref<number | null>(null);

    const getPageDocRooms = async (option: RequestOptions) => {
        const {
            dataFetch,
            statusFetch,
            error,
            isLoading,
            fetchData
        } = await useFetchData<DocRoomModel[]>(GET_PAGE_DR, option);

        list.value = dataFetch.value;
        status.value = statusFetch;
    };

    const saveDocRoom = async (option: RequestOptions) => {
        const {
            dataFetch,
            statusFetch,
            error,
            isLoading,
            fetchData
        } = await useFetchData<any>(SAVE_DR, option);

        list.value = dataFetch.value;
        status.value = statusFetch;
    };

    const getDocRoomDetail = async (option: RequestOptions) => {
        const {
            dataFetch,
            statusFetch,
            error,
            isLoading,
            fetchData
        } = await useFetchData<DocRoomModel>(FIND_BY_ID, option);

        list.value = dataFetch.value;
        status.value = statusFetch;
    };

    const updateDocRoom = async (option: RequestOptions) => {
        const {
            dataFetch,
            statusFetch,
            error,
            isLoading,
            fetchData
        } = await useFetchData<DocRoomModel>(UPDATE_DR, option);

        list.value = dataFetch.value;
        status.value = statusFetch;
    };

    const deleteDocRoom = async (option: RequestOptions) => {
        const {
            dataFetch,
            statusFetch,
            error,
            isLoading,
            fetchData
        } = await useFetchData<any>(DELETE_DR, option);

        list.value = dataFetch.value;
        status.value = statusFetch;
    };

    return {
        list,
        dataId,
        status,
        getPageDocRooms,
        saveDocRoom,
        getDocRoomDetail,
        updateDocRoom,
        deleteDocRoom,
    };
});
