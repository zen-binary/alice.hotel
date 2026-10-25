import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import { RequestOptions } from "@/utils/useFetchData.ts";
import { ParticipationCertificateDTO } from "./participationCertificate.type";
import { END_POINTS_POST } from "./participationCertificate.service";

export const participationCertificateStore = defineStore("participationCertificateStore", () => {
  const dataId = ref<ParticipationCertificateDTO>();
  const status = ref<number | null>(null);

  const fetchId = async (option: RequestOptions) => {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<ParticipationCertificateDTO[]>(`${END_POINTS_POST}`, option);

    dataId.value = dataFetch.value;
    status.value = statusFetch;
  };

  

  return {
    dataId,
    status,
    fetchId,
  };
});
