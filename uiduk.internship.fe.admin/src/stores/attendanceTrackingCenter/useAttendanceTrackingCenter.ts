import { defineStore } from "pinia";
import { AttendanceTrackingModel, AttendanceTrackingLstStudentResModel, AttendanceTrackingStudentInfoResModel } from "./attendanceTrackingCenter.type";
import { ref } from "vue";
import { RequestOptions, useFetchData } from "@/utils/useFetchData";
import { GET_LIST_ATTENDANCE_TRACKING, SETUP_ATTENDANCE_TRACKING, GET_LIST_STUDENT, GET_INFO_STUDENT } from "./attendanceTrackingCenter.service";

export const attendanceTrackingCenterStore = defineStore("attendanceTrackingCenterStore", () => {
  const status = ref<number>();
  const resultAttendanceTracking = ref<AttendanceTrackingModel[]>([]);
  const resultLstStudent = ref<AttendanceTrackingLstStudentResModel[]>([]);
  const resultInfoStudent = ref<AttendanceTrackingStudentInfoResModel>();

  const getDataAttendanceTracking = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<AttendanceTrackingModel[]>(GET_LIST_ATTENDANCE_TRACKING, option);
    resultAttendanceTracking.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getDataLstStudent = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<AttendanceTrackingLstStudentResModel[]>(GET_LIST_STUDENT, option);
    resultLstStudent.value = dataFetch.value;
    status.value = statusFetch;
  };

  const getDataInfoStudent = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<AttendanceTrackingStudentInfoResModel>(GET_INFO_STUDENT, option);
    resultInfoStudent.value = dataFetch.value;
    status.value = statusFetch;
  };

  const setupAttendanceTracking = async (option: RequestOptions) => {
    const { statusFetch } = await useFetchData<boolean>(SETUP_ATTENDANCE_TRACKING, option);
    status.value = statusFetch;
  };

  return {
    status,
    resultAttendanceTracking,
    getDataAttendanceTracking,
    resultLstStudent,
    getDataLstStudent,
    resultInfoStudent,
    getDataInfoStudent,
    setupAttendanceTracking,
  };
});