import { RequestOptions } from "@/utils/useFetchData.ts";
import { defineStore } from "pinia";
import { ref } from "vue";
import { useFetchData } from "@/utils/useFetchData";
import {
  EvaluationReportProfDetailRes,
  ListWeekInfoResModel,
  ResponseDetailRollCall,
  StudentInternClient,
  SummaryReportResModel,
  WeekReportResModel,
} from "./instructorEvaluation.type";
import {
  END_POINTS_CREATE_INSTRUCTOR_EVALUATION,
  END_POINTS_DETAIL_COMPREHENSIVE_REPORT,
  END_POINTS_DETAIL_INSTRUCTOR_EVALUATION,
  END_POINTS_DETAIL_ROLL_CALL,
  END_POINTS_DETAIL_WEEKLY_REPORT,
  END_POINTS_STD,
  END_POINTS_UPDATE_INSTRUCTOR_EVALUATION,
  GET_LIST_DATA_WEEK,
} from "./instructorEvaluation.service";

export const instructorEvaluationStore = defineStore("instructorEvaluationStore", () => {
  const list = ref<EvaluationReportProfDetailRes>({});
  const listStd = ref<StudentInternClient[]>([]);
  const listRollCall = ref<ResponseDetailRollCall>({});
  const listWeeklyReport = ref<WeekReportResModel>();
  const listSummaryReport = ref<SummaryReportResModel>();
  const status = ref<number | null>(null);
  const dataRes = ref();
  const listWeek = ref<ListWeekInfoResModel[]>([]);

  const fetchDetailProfessor = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<EvaluationReportProfDetailRes>(END_POINTS_DETAIL_INSTRUCTOR_EVALUATION, option);

    list.value = dataFetch.value;
    status.value = statusFetch;
  };

  const updateForm = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData(END_POINTS_UPDATE_INSTRUCTOR_EVALUATION, option);

    dataRes.value = dataFetch.value;
    status.value = statusFetch;
  };

  const createForm = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData(END_POINTS_CREATE_INSTRUCTOR_EVALUATION, option);

    dataRes.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getlistStd = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<StudentInternClient>(END_POINTS_STD, option);

    listStd.value = dataFetch.value;
    status.value = statusFetch;
  };

  const fetchDetailRollCall = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<ResponseDetailRollCall>(END_POINTS_DETAIL_ROLL_CALL, option);

    listRollCall.value = dataFetch.value;
    status.value = statusFetch;
  };

  const fetchDetailWeeklyReport = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<WeekReportResModel>(END_POINTS_DETAIL_WEEKLY_REPORT, option);

    listWeeklyReport.value = dataFetch.value;
    status.value = statusFetch;
  };

  const fetchDetailSummaryReport = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<SummaryReportResModel>(END_POINTS_DETAIL_COMPREHENSIVE_REPORT, option);

    listSummaryReport.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getListWeek = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<ListWeekInfoResModel>(GET_LIST_DATA_WEEK, option);

    listWeek.value = dataFetch.value;
    status.value = statusFetch;
  };

  return {
    list,
    status,
    fetchDetailProfessor,
    updateForm,
    createForm,
    getlistStd,
    listStd,
    fetchDetailRollCall,
    listRollCall,
    fetchDetailWeeklyReport,
    listWeeklyReport,
    fetchDetailSummaryReport,
    listSummaryReport,
    dataRes,
    listWeek,
    getListWeek
  };
});
