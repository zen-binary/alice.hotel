<template>
  <TModal :classSize="`xlarge`" :is-open="isOpen" :is-show-header="true" :is-show-footer="false" :modal-id="modalId" :key="modalKey" @close-modal="closeModal">
    <template #title>
      <strong>{{ t("attendanceTrackingCenter.title") }}</strong>
    </template>
    <template #default>
      <div class="evaluation-container" id="attendance_tracking_center">
        <div class="evaluation-container__form">
          <div class="option">
            <div class="title-block mb-0">{{ t("attendanceTrackingCenter.titleSelectStudent") }}</div>
            <div>
              <SelectBoxBase name="lstStudent" :data="lstDataStudent" v-model="attendanceTrackingReq.internStdSeq" />
            </div>
          </div>
          <div class="option">
            <button
              type="button"
              class="button btn_primary btn_md hidden_print"
              @click="handleSetupAttendanceTracking"
              :disabled="
                attendanceTrackingReq.internStdSeq == '' || attendanceTrackingReq.internPlanSeq == '' || lstDataAttendanceTracking.length == 0
              "
            >
              {{ t("common.save") }}
            </button>
            <ButtonExportPDFV1
              :disabled="attendanceTrackingReq.internStdSeq == '' || attendanceTrackingReq.internPlanSeq == ''"
              class="btn_md"
              id="attendance_tracking_center"
              name-export="attendance_tracking_center"
            ></ButtonExportPDFV1>
          </div>
        </div>
        <div>
          <div class="evaluation-container__header">
            <h2 class="title-block">{{ t("attendanceTrackingCenter.titleAttendace") }}</h2>
          </div>
          <div class="evaluation-container__student-info">
            <div class="title-block">{{ t("attendanceTrackingCenter.infoStudent.title") }}</div>
            <div class="content-block">
              <div class="item">
                <div class="label">{{ t("attendanceTrackingCenter.infoStudent.colgNm") }}</div>
                <div class="value">{{ dataInfoStudent.colgNm }}</div>
              </div>
              <div class="item">
                <div class="label">{{ t("attendanceTrackingCenter.infoStudent.cormName") }}</div>
                <div class="value">{{ dataInfoStudent.cormNameKr }}</div>
              </div>
              <div class="item">
                <div class="label">{{ t("attendanceTrackingCenter.infoStudent.internshipDate") }}</div>
                <div class="value">{{ dataInfoStudent.internship }}</div>
              </div>
              <div class="item">
                <div class="label">{{ t("attendanceTrackingCenter.infoStudent.korNm") }}</div>
                <div class="value">{{ dataInfoStudent.korNm }}</div>
              </div>
              <div class="item">
                <div class="label">{{ t("attendanceTrackingCenter.infoStudent.stdNo") }}</div>
                <div class="value">{{ dataInfoStudent.stdNo }}</div>
              </div>
              <div class="item">
                <div class="label">{{ t("attendanceTrackingCenter.infoStudent.sustMajor") }}</div>
                <div class="value">{{ dataInfoStudent.sustNm }}/{{ dataInfoStudent.majorNm }}</div>
              </div>
            </div>
          </div>
          <div class="evaluation-container__table">
            <div class="title-block">{{ t("attendanceTrackingCenter.attendance.title") }}</div>
            <div class="tbl tbl_col tbl_scroll scrollx_tbl_xxl scrolly_tbl_xxl" style="overflow-x: auto">
              <table>
                <thead>
                  <tr>
                    <th class="text-bold" rowspan="2" style="width: 100px">{{ t("attendanceTrackingCenter.attendance.year") }}</th>
                    <th class="text-bold" rowspan="2" style="width: 100px">{{ t("attendanceTrackingCenter.attendance.week") }}</th>
                    <th class="text-bold" colspan="2" style="width: 100px">{{ t("attendanceTrackingCenter.attendance.mon") }}</th>
                    <th class="text-bold" colspan="2" style="width: 100px">{{ t("attendanceTrackingCenter.attendance.tue") }}</th>
                    <th class="text-bold" colspan="2" style="width: 100px">{{ t("attendanceTrackingCenter.attendance.wed") }}</th>
                    <th class="text-bold" colspan="2" style="width: 100px">{{ t("attendanceTrackingCenter.attendance.thu") }}</th>
                    <th class="text-bold" colspan="2" style="width: 100px">{{ t("attendanceTrackingCenter.attendance.fri") }}</th>
                    <th class="text-bold" colspan="2" style="width: 100px">{{ t("attendanceTrackingCenter.attendance.sat") }}</th>
                    <th class="text-bold" colspan="2" style="width: 100px">{{ t("attendanceTrackingCenter.attendance.sun") }}</th>
                    <th class="text-bold" rowspan="2" style="width: 80px; word-break: keep-all; white-space: normal">
                      {{ t("attendanceTrackingCenter.attendance.attendance") }}
                    </th>
                    <th class="text-bold" rowspan="2" style="width: 80px; word-break: keep-all; white-space: normal">
                      {{ t("attendanceTrackingCenter.attendance.absence") }}
                    </th>
                    <th class="text-bold" rowspan="2" style="width: 80px; word-break: keep-all; white-space: normal">
                      {{ t("attendanceTrackingCenter.attendance.holiday") }}
                    </th>
                  </tr>
                  <tr>
                    <th class="text-bold">{{ t("attendanceTrackingCenter.attendance.date") }}</th>
                    <th class="text-bold">{{ t("attendanceTrackingCenter.attendance.attendanceAction") }}</th>
                    <th class="text-bold">{{ t("attendanceTrackingCenter.attendance.date") }}</th>
                    <th class="text-bold">{{ t("attendanceTrackingCenter.attendance.attendanceAction") }}</th>
                    <th class="text-bold">{{ t("attendanceTrackingCenter.attendance.date") }}</th>
                    <th class="text-bold">{{ t("attendanceTrackingCenter.attendance.attendanceAction") }}</th>
                    <th class="text-bold">{{ t("attendanceTrackingCenter.attendance.date") }}</th>
                    <th class="text-bold">{{ t("attendanceTrackingCenter.attendance.attendanceAction") }}</th>
                    <th class="text-bold">{{ t("attendanceTrackingCenter.attendance.date") }}</th>
                    <th class="text-bold">{{ t("attendanceTrackingCenter.attendance.attendanceAction") }}</th>
                    <th class="text-bold">{{ t("attendanceTrackingCenter.attendance.date") }}</th>
                    <th class="text-bold">{{ t("attendanceTrackingCenter.attendance.attendanceAction") }}</th>
                    <th class="text-bold">{{ t("attendanceTrackingCenter.attendance.date") }}</th>
                    <th class="text-bold">{{ t("attendanceTrackingCenter.attendance.attendanceAction") }}</th>
                  </tr>
                </thead>
                <tbody v-for="(attendanceTracking, index) in lstDataAttendanceTracking" :key="index">
                  <tr v-for="(item, idx) in attendanceTracking?.lstDayWeek" :key="idx">
                    <td v-if="idx === 0" :rowspan="attendanceTracking?.lstDayWeek.length">{{ attendanceTracking.year }}</td>
                    <td v-if="idx === 0" :rowspan="attendanceTracking?.lstDayWeek.length">{{ attendanceTracking.week }} 주차</td>
                    <template v-for="(child, idxChild) in item" :key="idxChild">
                      <td class="text-bold" style="width: 100px">{{ child?.info?.date }}</td>
                      <td class="text-bold" style="width: 100px">
                        <SelectBoxBase
                          v-if="child?.info"
                          :data="listStatus"
                          :placeholder="t('attendanceTrackingCenter.attendance.attendanceAction')"
                          v-model="child.info.status"
                        />
                      </td>
                    </template>
                    <td class="text-bold" v-if="idx === 0" :rowspan="attendanceTracking?.lstDayWeek.length">
                      {{ attendanceTracking.attendance }} {{ t("attendanceTrackingCenter.attendance.day") }}
                    </td>
                    <td class="text-bold" v-if="idx === 0" :rowspan="attendanceTracking?.lstDayWeek.length">{{ attendanceTracking.absence }} 일</td>
                    <td class="text-bold" v-if="idx === 0" :rowspan="attendanceTracking?.lstDayWeek.length">{{ attendanceTracking.holiday }} 일</td>
                  </tr>
                </tbody>
                <tfoot>
                  <tr>
                    <td class="bg-ligh1 text-bold" rowspan="1" colspan="16">{{ t("attendanceTrackingCenter.attendance.totalAttendance") }}</td>
                    <td class="text-bold bg-total" rowspan="1">
                      {{ totalEvaluationScore.attendance }} {{ t("attendanceTrackingCenter.attendance.day") }}
                    </td>
                    <td class="text-bold bg-total" rowspan="1">
                      {{ totalEvaluationScore.absence }} {{ t("attendanceTrackingCenter.attendance.day") }}
                    </td>
                    <td class="text-bold bg-total" rowspan="1">
                      {{ totalEvaluationScore.holiday }} {{ t("attendanceTrackingCenter.attendance.day") }}
                    </td>
                  </tr>
                </tfoot>
              </table>
            </div>
          </div>
        </div>
      </div>
    </template>
  </TModal>
  <TModalAlert :is-open="modalOpenAlert" :is-show-header="true" :is-show-footer="true" :modal-id="'alertModal'" @close-modal="closeModalAlert">
    <!-- Modal content goes here -->
    <template #title>
      <strong>{{ messageAlert }}</strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ messageAlert }}</p>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button type="button" class="button btn_md btn_primary" @click="closeModalAlert">{{ t("common.confirm") }}</button>
    </template>
  </TModalAlert>

  <TModalConfirm
    :is-open="modalOpenConfirm"
    :is-show-header="true"
    :is-show-footer="true"
    :modal-id="'confirmModal'"
    @close-modal="closeModalConfirm"
  >
    <!-- Modal content goes here -->
    <template #title>
      <strong>{{ messageConfirm }}</strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ messageConfirm }}</p>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button type="button" class="button btn_md btn_primary" @click="handleConfirmModal">{{ t("common.confirm") }}</button>
      <button type="button" class="button btn_md btn_white popup_close" @click="closeModalConfirm">{{ t("common.cancel") }}</button>
    </template>
  </TModalConfirm>
</template>
<script setup lang="ts">
import { ref, defineEmits, watch, onBeforeMount } from "vue";
import { attendanceTrackingCenterStore } from "@/stores/attendanceTrackingCenter/useAttendanceTrackingCenter";
import { AttendanceTrackingReqModel, AttendanceTrackingStudentInfoResModel } from "@/stores/attendanceTrackingCenter/attendanceTrackingCenter.type";
import { codeMngStore } from "@/stores/common/codeMng";
import { CodeMngModel } from "@/stores/common/codeMng/codeMng.type";
import { SUCCSESS_STATUS } from "@/constants/screen.const";
import { UP_CD_ATTENDANCE } from "@/constants/common.const";
import ButtonExportPDFV1 from "@/components/common/ButtonExportPDFV1.vue";
const modalId = ref("attendanceTrackingCenterModal");
const emit = defineEmits(["closeModal"]);
const { t } = useI18n();
const attendanceTracking = attendanceTrackingCenterStore();
const codeMng = codeMngStore();

const props = defineProps<{
  isOpen: boolean;
  internPlanId: string;
}>();

const attendanceTrackingReq = ref<AttendanceTrackingReqModel>({
  internPlanSeq: props.internPlanId,
  internStdSeq: "",
  lstAttendanceTracking: [],
});

const dataInfoStudent = ref<AttendanceTrackingStudentInfoResModel>({
  colgNm: "",
  sustNm: "",
  majorNm: "",
  stdNo: "",
  korNm: "",
  cormNameKr: "",
  internship: "",
});

const lstDataStudent = ref<CodeMngModel[]>([{ cdId: "", cdNm: t("common.selectedStd"), upCdId: "ALL" }]);
const lstDataAttendanceTracking = ref([]);
const listStatus = ref<CodeMngModel[]>([]);

const totalEvaluationScore = ref({
  attendance: 0,
  absence: 0,
  holiday: 0,
});

const modalKey = ref(1);
const modalOpenAlert = ref(false);
const messageAlert = ref("");

const modalOpenConfirm = ref(false);
const messageConfirm = ref("");

const handleConfirmModal = ref(function () {});

onBeforeMount(async () => {
  await getListCodeMng();
  await fnLstStudent();
});

const fnLstStudent = async () => {
  await attendanceTracking.getDataLstStudent({
    method: "POST",
    body: props.internPlanId,
  });

  lstDataStudent.value = [
    ...lstDataStudent.value,
    ...attendanceTracking.resultLstStudent.data.map((item) => ({
      cdId: item.id,
      cdNm: item.name,
    })),
  ];
};

const fnInfoStudent = async () => {
  if (attendanceTrackingReq.value.internStdSeq == "") {
    return;
  }
  await attendanceTracking.getDataInfoStudent({
    method: "POST",
    body: {
      internPlanSeq: props.internPlanId,
      internStdSeq: attendanceTrackingReq.value.internStdSeq,
    },
  });
  const data = attendanceTracking.resultInfoStudent?.data;
  dataInfoStudent.value = data;
  dataInfoStudent.value.internship = `${formatDate(data.internshipStr)} ~ ${formatDate(data.internshipEnd)}`;
};

const fnDataAttendanceTracking = async () => {
  if (attendanceTrackingReq.value.internStdSeq == "") {
    return;
  }
  await attendanceTracking.getDataAttendanceTracking({
    method: "POST",
    body: {
      internPlanSeq: props.internPlanId,
      internStdSeq: attendanceTrackingReq.value.internStdSeq,
    },
  });
  lstDataAttendanceTracking.value = attendanceTracking.resultAttendanceTracking?.data;

  totalEvaluationScore.value.attendance = lstDataAttendanceTracking.value.reduce((sum, item) => sum + item.attendance, 0);
  totalEvaluationScore.value.absence = lstDataAttendanceTracking.value.reduce((sum, item) => sum + item.absence, 0);
  totalEvaluationScore.value.holiday = lstDataAttendanceTracking.value.reduce((sum, item) => sum + item.holiday, 0);
};

async function getListCodeMng() {
  await codeMng.getCodeInfo({
    method: "POST",
    body: {
      upCdIdList: [UP_CD_ATTENDANCE],
    },
  });
  codeMng.codeInfo.data.forEach((item) => {
    listStatus.value.push(item);
  });
}

const setupAttendanceTracking = async () => {
  await attendanceTracking.setupAttendanceTracking({
    method: "PUT",
    body: {
      ...attendanceTrackingReq.value,
      lstAttendanceTracking: lstDataAttendanceTracking.value,
    },
  });
  if (attendanceTracking.status.value && attendanceTracking.status.value == SUCCSESS_STATUS) {
    fnDataAttendanceTracking();
    openModalAlert(t("attendanceTrackingCenter.message.saveSuccess"));
  }
};

function handleSetupAttendanceTracking() {
  messageConfirm.value = t("attendanceTrackingCenter.message.confirmSave");
  opemModalConfirm(() => {
    modalOpenConfirm.value = false;
    setupAttendanceTracking();
  });
}

function closeModalAlert() {
  attendanceTrackingReq.value.internStdSeq = "";
  modalKey.value++;
  modalOpenAlert.value = false;
}

function openModalAlert(mess: any) {
  messageAlert.value = mess;
  modalOpenAlert.value = true;
}

function opemModalConfirm(customAction: Function = () => {}) {
  handleConfirmModal.value = customAction;
  modalOpenConfirm.value = true;
}

function closeModalConfirm() {
  modalOpenConfirm.value = false;
}

const formatDate = (dateString: any) => {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");

  return `${year}.${month}.${day}`;
};

const closeModal = () => {
  emit("closeModal");
};
watch(
  () => attendanceTrackingReq.value.internStdSeq,
  async (newValue) => {
    if (newValue != "") {
      await fnInfoStudent();
      await fnDataAttendanceTracking();
    } else {
      lstDataAttendanceTracking.value = [];
      dataInfoStudent.value = {
        colgNm: "",
        sustNm: "",
        majorNm: "",
        stdNo: "",
        korNm: "",
        cormNameKr: "",
        internship: "",
      };
      totalEvaluationScore.value = {
        attendance: 0,
        absence: 0,
        holiday: 0,
      };
    }
  },
);
</script>
<style lang="scss" scoped>
.evaluation-container {
  &__form,
  &__header,
  &__student-info,
  &__table,
  &__comment,
  &__feedback,
  &__document,
  &__reviewer,
  &__signature {
    margin-bottom: 20px;
  }
  &__form {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid var(--black);
    padding-bottom: 20px;
    @media screen and (max-width: 768px) {
      flex-direction: column-reverse;
      align-items: flex-start;
      gap: 10px;
    }
    .option {
      display: flex;
      align-items: center;
      gap: 10px;
      @media screen and (max-width: 768px) {
        width: 100%;
        justify-content: flex-end;
      }
    }
  }
  &__student-info {
    .content-block {
      display: flex;
      flex-wrap: wrap;
      gap: 10px;
      .item {
        flex: 1 1 calc(33.333% - 10px);
        box-sizing: border-box;
        display: flex;
        text-align: center;
        border: 1px solid var(--Gray-scale-80, rgb(45, 45, 45));
        @media screen and (max-width: 1024px) {
          flex-direction: column;
          flex: 1 1 calc(50% - 10px);
        }
        @media screen and (max-width: 375px) {
          flex: 1 1 100%;
        }
        .label,
        .value {
          padding: 10px;
          font-size: 14px;
          display: flex;
          align-items: center;
          justify-content: center;
        }
        .label {
          flex: 1;
          border-right: 1px solid var(--Gray-scale-80, rgb(45, 45, 45));
          font-weight: 500;
          background: var(--Gray-scale-5, rgb(248, 248, 248));
          @media screen and (max-width: 1024px) {
            border-right: 0;
          }
        }
        .value {
          flex: 1;
        }
      }
    }
  }
  &__table {
    table {
      thead,
      tbody {
        border-bottom: 1px solid;
      }
      tr {
        th {
          background: var(--Gray-scale-5, rgb(248, 248, 248));
        }
        td {
          .radio_row {
            margin-right: 0;
            justify-content: space-evenly;
            font-size: 16px;
          }
        }
      }
    }
  }
  &__comment,
  &__feedback {
    .body {
      .label {
        padding: 10px 15px;
        background: var(--Gray-scale-5, rgb(248, 248, 248));
        font-weight: 500;
        border: 1px solid var(--input--bd);
      }
    }
  }
  &__reviewer {
    .content-block {
      display: flex;
      flex-wrap: wrap;
      gap: 10px;
      .item {
        flex: 1 1 calc(50% - 10px);
        box-sizing: border-box;
        display: flex;
        text-align: center;
        border: 1px solid var(--Gray-scale-80, rgb(45, 45, 45));
        @media screen and (max-width: 1024px) {
          flex-direction: column;
        }
        .label,
        .value {
          padding: 10px;
          font-size: 14px;
        }
        .label {
          flex: 1;
          max-width: 30%;
          border-right: 1px solid var(--Gray-scale-80, rgb(45, 45, 45));
          font-weight: 500;
          background: var(--Gray-scale-5, rgb(248, 248, 248));
          @media screen and (max-width: 1024px) {
            max-width: 100%;
            border-right: 0;
          }
        }
        .value {
          flex: 1;
        }
      }
    }
  }
  &__document {
    .head {
      display: flex;
      border: 1px solid var(--Gray-scale-80, rgb(45, 45, 45));
      margin-bottom: 20px;
      .label,
      .value {
        padding: 15px 10px;
        text-align: center;
        font-size: 16px;
      }
      .label {
        flex: 1;
        max-width: 30%;
        background: var(--Gray-scale-5, rgb(248, 248, 248));
        border-right: 1px solid var(--Gray-scale-80, rgb(45, 45, 45));
      }
      .value {
        flex: 1;
      }
    }
    .body {
      text-align: center;
      p {
        font-size: 14px;
        font-weight: 500;
      }
      .date {
        margin: 10px 0;
        font-weight: 700;
      }
    }
  }
}

.bg-total {
  background: rgba(#ccc, 0.5);
}

.text-bold {
  font-weight: 700;
  color: var(--black);
  font-size: 16px;
}
.bg-ligh1 {
  background: var(--Gray-scale-5, rgb(248, 248, 248));
}
.title-block {
  font-size: 16px;
  font-weight: 700;
  margin-bottom: 10px;
}
@media print {
  .item {
    align-items: stretch;
    height: auto !important;
  }

  .label,
  .value {
    height: auto !important;
    max-height: none !important;
  }
}
</style>
