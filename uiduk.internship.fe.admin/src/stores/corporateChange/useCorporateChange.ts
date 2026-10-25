import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import { CORPORATE_CHANGE, CORPORATE_CHANGE_UPDATE } from "@/stores/corporateChange/corporateChange.service";
import { CorporateInfoChange } from "@/stores/corporateChange/corporateChange.type";
import { RequestOptions } from "@/utils/useFetchData.ts";
export const corporateChangeStore = defineStore("corporateChange", () => {
  const dataId = ref<string>();
  const corporateChange = ref<string>();
  const status = ref<number | null>(null);

  const fetchDetailCorporate = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<CorporateInfoChange>(CORPORATE_CHANGE, option);
    corporateChange.value = dataFetch.value;
    status.value = statusFetch;
  };

  const fetchUpdateCorporate = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<string>(CORPORATE_CHANGE_UPDATE, option);
    dataId.value = dataFetch.value;
    status.value = statusFetch;
  };

  return {
    dataId,
    status,
    corporateChange,
    fetchDetailCorporate,
    fetchUpdateCorporate,
  };
});
