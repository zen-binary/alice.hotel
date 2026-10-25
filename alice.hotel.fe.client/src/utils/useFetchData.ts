import { ref } from "vue";
import http from "@/utils/http.ts";
import { commonStore } from "../stores/common.ts";

export interface RequestOptions {
  method?: "GET" | "POST" | "PUT" | "DELETE" | "PATCH";
  params?: Record<string, any>;
  body?: Record<string, any>;
  headers?: Record<string, string>;
}

export const useFetchData = async <T>(url: string, options: RequestOptions = {}) => {
  const cmStore = commonStore();

  const dataFetch = ref<T | null>(null);
  const error = ref<string>();
  const isLoading = ref(false);
  const statusFetch = ref<number | null>(null);

  const fetchData = async () => {
    cmStore.setLoading(true);
    isLoading.value = true;
    error.value = "";
    statusFetch.value = null;

    try {
      const response = await http({
        url,
        method: options.method || "GET",
        params: options.method === "GET" ? options.params : undefined,
        data: options.method !== "GET" ? options.body : undefined,
        headers: options.headers, // If you want to override the default headers
      });
      dataFetch.value = response.data;
      statusFetch.value = response.status;
    } catch (err) {
      const typeError = err as Error;
      error.value = typeError.message;
    } finally {
      cmStore.setLoading(false);
      isLoading.value = false;
    }
  };

  await fetchData();

  return { dataFetch, statusFetch, error, isLoading, fetchData };
};
