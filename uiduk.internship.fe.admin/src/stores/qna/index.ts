import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import { QNA_DELETE_POST, QNA_DETAIL_POST, QNA_LIST_POST, QNA_UPDATE_POST } from "@/stores/qna/qna.service.ts";
import { RequestOptions } from "@/utils/useFetchData.ts";
import { QnaResDTO, QnaDetailResDTO } from "@/stores/qna/qna.type.ts";

export const qnaStore = defineStore("qnaStore", () => {
  const listQna = ref<QnaResDTO[]>([]);
  const dataId = ref<QnaDetailResDTO>();
  const status = ref<number>();
  const res = ref<string | null>("")
  const totalRecord = ref<number>();
  
  const qnaList = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<QnaResDTO[]>(QNA_LIST_POST, option);

    listQna.value = dataFetch.value;
    totalRecord.value = dataFetch.value.data.totalElements;    
    status.value = statusFetch;
  };

  const qnaDetail = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<QnaDetailResDTO>(QNA_DETAIL_POST, option);

    dataId.value = dataFetch.value;
    status.value = statusFetch;
  };

  const deleteQna = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<string>(QNA_DELETE_POST, option);
    res.value = dataFetch.value;
    status.value = statusFetch;
  };

  const updateQna = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<string>(QNA_UPDATE_POST, option);
    res.value = dataFetch.value;
    status.value = statusFetch;
  };

  return {
    res,
    listQna,
    dataId,
    status,
    totalRecord,
    qnaList,
    qnaDetail,
    deleteQna,
    updateQna,
  };
});
