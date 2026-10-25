import { defineStore } from "pinia";
import { AttendanceTrackingModel, AttendanceTrackingLstStudentResModel, AttendanceTrackingStudentInfoResModel } from "./attendanceTrackingLecturer.type";
import { ref } from "vue";
import { RequestOptions, useFetchData } from "@/utils/useFetchData";
import { GET_LIST_ATTENDANCE_TRACKING,GET_LIST_STUDENT, GET_INFO_STUDENT } from "./attendanceTrackingLecturer.service";

export const attendanceTrackingLecturerStore = defineStore("attendanceTrackingCenterStore", () => {
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


  return {
    status,
    resultAttendanceTracking,
    getDataAttendanceTracking,
    resultLstStudent,
    getDataLstStudent,
    resultInfoStudent,
    getDataInfoStudent,
  };
});