import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import { RequestOptions } from "@/utils/useFetchData.ts";
import { getQnaDetail, getQnaList, createQna, deleteQna } from "./qna.service.ts";
import { QnaDetailResDTO, QnaRegResDTO, QnaResDTO } from "./qna.type.ts";

export const qnaStore = defineStore("qnaStore", () => {
  const listQna = ref<QnaResDTO[]>([]);
  const status = ref<number | null>(null);
  const totalItems = ref<number>(0);
  const qnaDetail = ref<QnaDetailResDTO>();
  const qnaRegRes = ref<QnaRegResDTO>();
  const responseMsg = ref<string>();

  const fetchQnaList = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<QnaResDTO[]>(getQnaList, option);

    listQna.value = dataFetch.value?.data?.content || [];
    status.value = statusFetch;
    totalItems.value = dataFetch.value?.data?.totalElements || 0;
  };

  const fetchQnaDetail = async (option: RequestOptions) => {
    try {
      const { dataFetch, statusFetch } = await useFetchData<QnaDetailResDTO>(getQnaDetail, option);
      status.value = statusFetch;
      qnaDetail.value = dataFetch.value?.data ?? {};
    } catch (error) {
      console.error("Error fetching qna detail:", error);
      status.value = 500;
      qnaDetail.value = {};
    }
  };

  const fetchCreateQna = async (option: RequestOptions) => {
    try {
      const { dataFetch, statusFetch } = await useFetchData<QnaRegResDTO>(createQna, option);
      status.value = statusFetch;
      qnaRegRes.value = dataFetch.value?.data ?? {};
    } catch (error) {
      console.error("Error fetching qna detail:", error);
      status.value = 500;
      qnaDetail.value = {};
    }
  };

  const fetchDeleteQna = async (option: RequestOptions) => {
    try {
      const { dataFetch } = await useFetchData<{code: string; data: string; message: string}>(deleteQna, option);
      status.value = dataFetch.value.code;
    //   responseMsg = dataFetch.value?.message || "Unknown error";
    } catch (error) {
        console.log(error)
    //   console.error("Error fetching qna detail:", error);
      status.value = 500;
    }
  };

  return {
    listQna,
    status,
    totalItems,
    qnaDetail,
    responseMsg,
    fetchQnaList,
    fetchQnaDetail,
    fetchCreateQna,
    fetchDeleteQna,
  };
});
