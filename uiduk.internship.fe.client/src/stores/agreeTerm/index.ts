import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData, RequestOptions } from "@/utils/useFetchData";

export const agreeTermStore = defineStore("agreeTermStore", () => {
  const status = ref<number | null>(null);

  const agreeTerm = async (option: RequestOptions) => {
    try {
      const { dataFetch } = await useFetchData<any>("c/agree-term", option);
      status.value = dataFetch.value.code;
    } catch (err) {
      console.error(err);
      status.value = 500;
    }
  };

  return { status ,  agreeTerm };
});

