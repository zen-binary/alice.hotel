import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import { RequestOptions } from "@/utils/useFetchData.ts";
import { InternStudentRegResDTO, InternStudentListResDTO } from "./internStudent.type";
import { createInternStudent, getInternshipYear, getInternStudentRegList, updateInternStudentStatus } from "./internStudent.service";

export const internStudentStore = defineStore("internStudentStore", () => {
  const internStudentResponse = ref<InternStudentRegResDTO | null>(null);
  const status = ref<number | null>(null);
  const internStudentList = ref<InternStudentListResDTO[]>([]);
  const totalItems = ref<number>(0);
  const response = ref<any>(null);
  const listInternshipYear = ref<string[]>([]);

  const fetchCreateInternStudent = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<InternStudentRegResDTO>(createInternStudent, option);

    internStudentResponse.value = dataFetch.value?.data || null;
    status.value = statusFetch.value || null;
  };

  const fetchInternStudentList = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<InternStudentListResDTO[]>(getInternStudentRegList, option);

    internStudentList.value = dataFetch.value?.data || [];
    status.value = statusFetch.value || null;
    totalItems.value = internStudentList.value.length;
  };

  const fetchUpdateInternStudentStatus = async (option: RequestOptions) => {
    try {
      const dataFetch = await useFetchData<{ code: string; data: boolean; message: string }>(updateInternStudentStatus, option);

      response.value = dataFetch?.dataFetch?.value || null;
      status.value = dataFetch?.dataFetch?.value?.code || null;
    } catch (error) {
      console.log(error);
    }
  };

  const fetchInternshipStudentYear = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<string[]>(getInternshipYear, option);
        listInternshipYear.value = dataFetch.value?.data || [];
        status.value = statusFetch;
  };

  return {
    internStudentResponse,
    internStudentList,
    status,
    response,
    totalItems,
    listInternshipYear,
    fetchCreateInternStudent,
    fetchInternStudentList,
    fetchUpdateInternStudentStatus,
    fetchInternshipStudentYear
  };
});
