import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import { END_POINTS_CODEMNG } from "@/stores/common/codeMng/codeMng.service";
import { RequestOptions } from "@/utils/useFetchData";
import type { CodeMngModel, CodeMngRes } from "./codeMng.type";

// Define a Pinia store named "codemng"
export const codeMngStore = defineStore("codemng", () => {
  // Reactive reference to hold the fetched code information
  const codeInfo = ref<CodeMngModel[]>();
  // Reactive reference to hold the status of the fetch operation
  const status = ref<number>();

  // Async function to fetch code information based on provided options
  const getCodeInfo = async (option: RequestOptions) => {
    // Call the useFetchData utility to fetch data from the specified endpoint
    const { dataFetch, statusFetch } = await useFetchData<CodeMngRes[]>(END_POINTS_CODEMNG, option);

    // Update the reactive state with the fetched data and status
    codeInfo.value = dataFetch?.value; // Set the codeInfo to the fetched data
    status.value = statusFetch; // Set the status to the fetch status
  };

  // Return the reactive state and the fetching function for use in components
  return {
    codeInfo,
    status,
    getCodeInfo,
  };
});
