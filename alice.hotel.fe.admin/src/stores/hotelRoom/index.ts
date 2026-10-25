import { defineStore } from "pinia";
import { ref } from "vue";
import type { HotelRoomModel } from "./hotelRoom.type";
import { HOTEL_ROOM_CREATE, HOTEL_ROOM_DETAIL, HOTEL_ROOM_LIST } from "./hotelRoom.service";
import { useFetchData } from "@/utils/useFetchData";
import { PostType } from "@/stores/common/demoFetchData/demo.type";
import { RequestOptions } from "@/utils/useFetchData";
import http from "@/utils/http";

export const hotelRoomStore = defineStore("hotelRoomStore", () => {
  const list = ref<HotelRoomModel[]>([]);
  const detail = ref<HotelRoomModel>();
  const totalElements = ref(0);
  const status = ref<number>();

  const search = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<PostType[]>(HOTEL_ROOM_LIST, option);
    status.value = statusFetch.value;
    const page = dataFetch.value?.data;
    list.value = page?.content || [];
    totalElements.value = page?.totalElements || 0;
  };

  const getDetail = async (roomId: string) => {
    const res = await http.get(`${HOTEL_ROOM_DETAIL}/${roomId}`);
    detail.value = res.data?.data;
    return detail.value;
  };

  const create = async (body: HotelRoomModel) => {
    const res = await http.post(HOTEL_ROOM_CREATE, body);
    return res.data?.data;
  };

  const update = async (roomId: string, body: HotelRoomModel) => {
    const res = await http.put(`${HOTEL_ROOM_DETAIL}/${roomId}`, body);
    return res.data?.data;
  };

  const remove = async (roomId: string) => {
    const res = await http.delete(`${HOTEL_ROOM_DETAIL}/${roomId}`);
    return res.data?.data;
  };

  return { list, detail, totalElements, status, search, getDetail, create, update, remove };
});
