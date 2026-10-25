import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import { RequestOptions } from "@/utils/useFetchData.ts";
import { getDocRoomList, getDocRoomDetail } from "@/stores/docRoom/docRoom.service";
import { DocRoomDetailResDTO, DocRoomReqDTO } from "@/stores/docRoom/docRoom.type";

export const docRoomStore = defineStore("docRoomStore", () => {
  const listDocRoom = ref<DocRoomResDTO[]>([]);
  const status = ref<number | null>(null);
  const totalItems = ref<number>(0);
  const docRoomDetail = ref<DocRoomDetailResDTO>();

  const fetchDocRoomList = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<DocRoomResDTO[]>(getDocRoomList, option);

    listDocRoom.value = dataFetch.value?.data?.content || [];  
    status.value = statusFetch;
    totalItems.value = dataFetch.value?.data?.totalElements || 0;
  };

  const fetchDocRoomDetail = async (option: RequestOptions) => {
    try {
      const { dataFetch, statusFetch } = await useFetchData<DocRoomDetailResDTO>(getDocRoomDetail, option);
      status.value = statusFetch;
      docRoomDetail.value = dataFetch.value?.data ?? {};
    } catch (error) {
      console.error("Error fetching notice detail:", error);
      status.value = 500;
      docRoomDetail.value = {};
    }
  };

  return {
    listDocRoom,
    status,
    fetchDocRoomList,
    fetchDocRoomDetail,
    totalItems,
    docRoomDetail
  };
});