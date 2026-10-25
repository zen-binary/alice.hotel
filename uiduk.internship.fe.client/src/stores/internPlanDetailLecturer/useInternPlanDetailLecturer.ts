import { defineStore } from "pinia";
import { ref } from "vue";
import { InternPlanResponseModel } from "./internPlanDetailLecturer.type";
import { RequestOptions, useFetchData } from "@/utils/useFetchData";
import { PostType } from "../common/demoFetchData/demo.type";
import { DETAIL } from "./internPlanDetailLecturer.service";

export const internPlanDetailLecturerStore = defineStore("internPlanDetailLecturer", () => {
  const status = ref<number>();
  const detailInternPlan = ref<InternPlanResponseModel>();

  const detailInternPlanMng = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(DETAIL, option);
    detailInternPlan.value = dataFetch.value;
    status.value = statusFetch;
  };

  return {
    status,
    detailInternPlanMng,
    detailInternPlan,
  };
});
