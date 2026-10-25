import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import { RequestOptions } from "@/utils/useFetchData.ts";
import { InternshipPlanRegFormDTO, InternshipRegResDTO } from "./internshipPlan.type";
import { getInternPlanRegForm, getInternshipPlanRegistrationList, getListYear } from "@/stores/internshipPlanRegistration/internshipPlan.service";

export const internshipPlanRegistrationStore = defineStore("internshipPlanRegistrationStore", () => {

    const listInternshipPlanRegistration = ref<any[]>([]);
    const status = ref<number | null>(null);
    const totalItems = ref<number>(0);
    const listYear = ref<any[]>([]);
    const internPlanRegForm = ref<any>();

    const fetchInternshipPlanRegistrationList = async (option: RequestOptions) => {
        const { dataFetch, statusFetch } = await useFetchData<InternshipRegResDTO[]>(getInternshipPlanRegistrationList, option);
        listInternshipPlanRegistration.value = dataFetch.value?.data || [];
        status.value = statusFetch;
        totalItems.value = listInternshipPlanRegistration.value.length;
    };

    const fetchListYear = async (option: RequestOptions) => {
        const { dataFetch, statusFetch } = await useFetchData<string[]>(getListYear, option);
        listYear.value = dataFetch.value?.data || [];
        status.value = statusFetch;
    };

    const fetchInternPlanRegForm = async (option: RequestOptions) => {
        const { dataFetch, statusFetch } = await useFetchData<InternshipPlanRegFormDTO[]>(getInternPlanRegForm, option);
        internPlanRegForm.value = dataFetch.value?.data || [];
        status.value = statusFetch;
    };

    return {
        listInternshipPlanRegistration,
        status,
        listYear,
        totalItems,
        internPlanRegForm,
        fetchInternshipPlanRegistrationList,
        fetchListYear,
        fetchInternPlanRegForm
    }
})  