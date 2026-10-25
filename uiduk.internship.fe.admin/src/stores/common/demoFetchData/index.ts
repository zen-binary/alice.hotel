import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import { END_POINTS_COMMENT, END_POINTS_POST } from "@/stores/common/demoFetchData/demo.service.ts";
import { RequestOptions } from "@/utils/useFetchData.ts";
import { PostType, PostType1 } from "@/stores/common/demoFetchData/demo.type.ts";

export const demoStore = defineStore("demoStore", () => {
  const list = ref<PostType[]>([]);
  const listComment = ref<PostType1[]>([]);
  const dataId = ref<PostType>();
  const status = ref<number | null>(null);

  const fetchList = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(END_POINTS_POST, option);

    list.value = dataFetch.value;
    status.value = statusFetch;
  };

  const fetchId = async (id: string | number, option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(`${END_POINTS_POST}/${id}`, option);

    dataId.value = dataFetch.value;
    status.value = statusFetch;
  };

  const fetchListComment = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(END_POINTS_COMMENT, option);

    listComment.value = dataFetch.value;
    status.value = statusFetch;
  };

  const changePost = async (id: string | number, option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(`${END_POINTS_POST}/${id}`, option);

    status.value = statusFetch;
  };

  return {
    list,
    dataId,
    listComment,
    status,
    fetchList,
    fetchId,
    fetchListComment,
    changePost,
  };
});
