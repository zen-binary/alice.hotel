<script setup lang="ts">
import { STATE_N, STATE_Y, INTERNSHIP_STATUS_APPROVED } from "@/constants/common.const";
import { commonStore } from "@/stores/common";
import { CodeMngModel } from "@/stores/common/codeMng/codeMng.type";
import { tranningWeekReportStore } from "@/stores/tranningWeeklyReport";
import { WeekCalendarClientResModel, WeekClientModel } from "@/stores/tranningWeeklyReport/tranningWeeklyReport.type";
import { ref, defineEmits, defineExpose, ComponentPublicInstance } from "vue";

const isOpen = ref(false);
const { t } = useI18n();
const cmn = commonStore();
const modalId = ref("trainingWeeklyReportModalId");
const emit = defineEmits(["confirm", "cancel"]);
const tranningWeeklyReport = tranningWeekReportStore();
const weekClietnModel = ref<WeekClientModel>();
const listWeek = ref<CodeMngModel[]>([]);
const weekSelected = ref<string>("");
const listWeekCalendar = ref<Array<WeekCalendarClientResModel>>([]);
const listHours = ref<Array<CodeMngModel>>([]);
const listMinutes = ref<Array<CodeMngModel>>([]);
const stateY = ref(STATE_Y);
const isModalAlertSuccessSave = ref(false);
const isModalConfirmSave = ref(false);
const internPlanSeq = ref("");
const isDisabledComplete = ref(false);

onBeforeMount(() => {
  for (let i = 0; i < 24; i++) {
    const hour = i.toString().padStart(2, "0");
    listHours.value.push({ cdId: hour, cdNm: hour, upCdId: "hours" });
  }

  for (let i = 0; i < 60; i++) {
    const minute = i.toString().padStart(2, "0");
    listMinutes.value.push({ cdId: minute, cdNm: minute, upCdId: "minutes" });
  }
});

const isFileArrayInvalid = computed(() => {
  const filteredList = listWeekCalendar.value?.filter((weekCalendar) => weekCalendar.isDisabled === STATE_N);

  return (
    !Array.isArray(filteredList) ||
    filteredList.length === 0 ||
    filteredList.some((weekCalendar) => !Array.isArray(weekCalendar.fileArray) || weekCalendar.fileArray.length === 0)
  );
});

const areFieldsInvalid = computed(() => {
  const filteredList = listWeekCalendar.value?.filter((weekCalendar) => weekCalendar.isDisabled === STATE_N);

  return (
    !Array.isArray(filteredList) ||
    filteredList.length === 0 ||
    filteredList.some((weekCalendar) => {
      const inHour = parseInt(weekCalendar.inHour?.trim(), 10);
      const inMinute = parseInt(weekCalendar.inMinute?.trim(), 10);
      const outHour = parseInt(weekCalendar.outHour?.trim(), 10);
      const outMinute = parseInt(weekCalendar.outMinute?.trim(), 10);

      const inTotalMinutes = inHour * 60 + inMinute;
      const outTotalMinutes = outHour * 60 + outMinute;

      return (
        !weekCalendar.inHour?.trim() ||
        !weekCalendar.inMinute?.trim() ||
        !weekCalendar.outHour?.trim() ||
        !weekCalendar.outMinute?.trim() ||
        !weekCalendar.jobContent?.trim() ||
        !weekCalendar.acquiredKnowledge?.trim() ||
        outTotalMinutes <= inTotalMinutes
      );
    })
  );
});

const getDataListWeek = async () => {
  await tranningWeeklyReport.getListWeekClient({
    method: "POST",
    body: { internPlanId: internPlanSeq.value },
  });
  weekClietnModel.value = tranningWeeklyReport.weekClientModel.data;
  isDisabledComplete.value = tranningWeeklyReport.weekClientModel.data.internshipStatus == INTERNSHIP_STATUS_APPROVED;
  weekClietnModel.value?.lstWeek.forEach((item) => {
    listWeek.value.push({ cdId: item.internDetailSeq, cdNm: "주" + item.week, upCdId: "" });
  });
  if (listWeek.value && listWeek.value.length > 0) {
    weekSelected.value = listWeek.value[0].cdId;
  }
};

watch(
  () => isOpen.value,
  async (newVal) => {
    if (newVal) {
      await getDataListWeek();
    } else {
      weekClietnModel.value = null;
      listWeek.value = [];
      weekSelected.value = "";
      listWeekCalendar.value = [];
      fileUploadStdWeekReport.value = [];
    }
  },
);

const getListWeekCalendar = async (internDetailId: string) => {
  await tranningWeeklyReport.getListWeekCalendar({
    method: "POST",
    body: { internDetailId: internDetailId },
  });
  listWeekCalendar.value = tranningWeeklyReport.listWeekClient.data;
};

watch(
  () => weekSelected.value,
  async (newVal) => {
    if (newVal) {
      await getListWeekCalendar(newVal);
    } else {
      listWeekCalendar.value = [];
    }
  },
);

type ChildComponentInstance = ComponentPublicInstance<{
  upLoadFile: (id: any) => void;
}>;

const fileUploadStdWeekReport = ref<(ChildComponentInstance | null)[]>([]);

const uploadFile = async (data: any, index: number) => {
  const uploader = fileUploadStdWeekReport.value[index];
  if (uploader && typeof uploader.upLoadFile === "function") {
    await uploader.upLoadFile(data);
  }
};

const confirmSaveData = () => {
  isModalConfirmSave.value = true;
};

const closeModalConfirmSave = () => {
  isModalConfirmSave.value = false;
};

const saveData = async () => {
  closeModalConfirmSave();
  listWeekCalendar.value.forEach((item) => {
    delete item.fileArray;
  });
  await tranningWeeklyReport.saveListWeekCalendar({
    method: "POST",
    body: listWeekCalendar.value,
  });
  const listIdStdWeekReport = tranningWeeklyReport.listIdStdWeekReport.data;
  fileUploadStdWeekReport.value = fileUploadStdWeekReport.value.filter((item) => item != null);
  listIdStdWeekReport.forEach(async (value, index) => {
    await uploadFile(value, index);
  });
  isModalAlertSuccessSave.value = true;
};

const closeModal = () => {
  isOpen.value = false;
};

const cancelAction = () => {
  isModalAlertSuccessSave.value = false;
  emit("cancel");
  closeModal();
};

const openModal = (internDetailId: string) => {
  isOpen.value = true;
  internPlanSeq.value = internDetailId;
};

defineExpose({
  openModal,
});
</script>
<template>
  <TModal :classSize="`large`" :is-open="isOpen" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
    <template #title>
      <strong>{{ t("tranningWeeklyReport.titleModal") }}</strong>
    </template>
    <template #default>
      <div class="training-report-block" id="tranningReportBlock">
        <div class="training-report-block__head">
          <div class="training-report-block__head__title">
            <span>{{ t("tranningWeeklyReport.subTitle") }}</span>
            <span>
              {{ weekClietnModel && weekClietnModel.stdNm }} ({{ weekClietnModel && weekClietnModel.deptNm }}
              {{ weekClietnModel && weekClietnModel.stdYear }} {{ weekClietnModel && weekClietnModel.stdNo }})
            </span>
          </div>
          <div class="training-report-block__head__dowload">
            <ButtonExportPDFV1 type="button" id="tranningReportBlock" name-export="tranningReportBlock" class="button btn_white" />
          </div>
        </div>
        <div class="training-report-block__form">
          <div class="training-report-block__form__row">
            <div class="item">
              <div class="note">
                <p class="validate_password">{{ t("tranningWeeklyReport.messValidate") }}</p>
                <div class="select-input">
                  <SelectBoxBase v-model="weekSelected" class="select-option" name="selectbox" :data="listWeek" placeholder="주차 선택" />
                </div>
              </div>
            </div>
          </div>
          <div class="training-report-block__form__row" v-for="(weekCalendar, index) in listWeekCalendar" :key="index">
            <div class="item">
              <div class="label">{{ t("tranningWeeklyReport.column1") }}</div>
              <div class="input"><BaseDatePicker id="date_validate" v-model="weekCalendar.dateWeek" :disabled="true" /></div>
            </div>
            <div class="item">
              <div class="label">{{ t("tranningWeeklyReport.column2") }}</div>
              <div class="input">
                <div class="option">
                  <SelectBoxBase
                    class="select-option"
                    :id="'inHour' + index"
                    v-model="weekCalendar.inHour"
                    name="selectbox"
                    :data="listHours"
                    :disabled="weekCalendar.isDisabled === stateY || isDisabledComplete"
                    :required="weekCalendar.isDisabled !== stateY"
                  />
                  <SelectBoxBase
                    class="select-option"
                    :id="'inMinute' + index"
                    v-model="weekCalendar.inMinute"
                    name="selectbox"
                    :data="listMinutes"
                    :disabled="weekCalendar.isDisabled === stateY || isDisabledComplete"
                    :required="weekCalendar.isDisabled !== stateY"
                  />
                </div>
                <span>~</span>
                <div class="option">
                  <SelectBoxBase
                    class="select-option"
                    :id="'outHour' + index"
                    v-model="weekCalendar.outHour"
                    name="selectbox"
                    :data="listHours"
                    :disabled="weekCalendar.isDisabled === stateY || isDisabledComplete"
                    :required="weekCalendar.isDisabled !== stateY"
                  />
                  <SelectBoxBase
                    class="select-option"
                    :id="'outMinute' + index"
                    v-model="weekCalendar.outMinute"
                    name="selectbox"
                    :data="listMinutes"
                    :disabled="weekCalendar.isDisabled === stateY || isDisabledComplete"
                    :required="weekCalendar.isDisabled !== stateY"
                  />
                </div>
              </div>
            </div>
            <div class="item">
              <div class="label">{{ t("tranningWeeklyReport.column3") }}</div>
              <div class="input">
                <InputBase
                  :disabled="weekCalendar.isDisabled === stateY || isDisabledComplete"
                  :id="'jobContent' + index"
                  v-model="weekCalendar.jobContent"
                  placeholder="내용을 입력해 주세요."
                  :required="weekCalendar.isDisabled !== stateY"
                />
              </div>
            </div>
            <div class="item">
              <div class="label">{{ t("tranningWeeklyReport.column4") }}</div>
              <div class="input">
                <TextArea
                  :disabled="weekCalendar.isDisabled === stateY || isDisabledComplete"
                  :id="'acquiredKnowledge' + index"
                  v-model="weekCalendar.acquiredKnowledge"
                  placeholder="내용을 입력해 주세요."
                  :required="weekCalendar.isDisabled !== stateY"
                />
              </div>
            </div>
            <div class="item">
              <div class="label">{{ t("tranningWeeklyReport.column5") }}</div>
              <div class="input file" v-if="weekClietnModel && weekCalendar.isDisabled !== stateY">
                <InputFileBase
                  :mode="`detail`"
                  :id="`cormSign`"
                  :type="'img'"
                  :preview-flag="true"
                  :multiple="true"
                  :show-mess="false"
                  :orgName="'CORM_SIGN'"
                  :category="'img'"
                  :sectionName="'CORM_SIGN'"
                  ref="cormSignRef"
                  :referKey="weekClietnModel.cormSeq"
                ></InputFileBase>
                <InputFileBase
                  :mode="weekCalendar.isDisabled === stateY  || isDisabledComplete ? 'detail' : `edit`"
                  :id="`imgStdWeekReport` + index"
                  :ref="(el) => (fileUploadStdWeekReport[index] = el)"
                  :type="'img'"
                  :preview-flag="true"
                  :description="weekCalendar.isDisabled === stateY ? t('tranningWeeklyReport.title2') : t('tranningWeeklyReport.title1')"
                  :multiple="false"
                  :max-file="1"
                  :show-mess="false"
                  :orgName="'STD_WEEK_REPORT'"
                  :category="'img'"
                  :sectionName="'STD_WEEK_REPORT'"
                  :referKey="weekCalendar.stdWeekReportId"
                  :key="`imgStdWeekReport` + weekCalendar.stdWeekReportId + index"
                  v-model="weekCalendar.fileArray"
                ></InputFileBase>
              </div>
              <div class="input file" v-else>{{ t("tranningWeeklyReport.title2") }}</div>
            </div>
            <hr />
          </div>
        </div>
      </div>
    </template>
    <template #footer>
      <button
        type="button"
        :disabled="!weekSelected || areFieldsInvalid || isFileArrayInvalid || isDisabledComplete"
        class="button btn_xl btn_primary"
        @click="confirmSaveData"
      >
        {{ t("common.save") }}
      </button>
    </template>
  </TModal>
  <TModalAlert :is-open="isModalAlertSuccessSave" :is-show-header="true" :is-show-footer="true">
    <template #default>
      <div class="box_section">
        <p>{{ t("tranningWeeklyReport.saveSuccess") }}</p>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_md btn_primary" @click="cancelAction">{{ t("common.confirm") }}</button>
    </template>
  </TModalAlert>
  <TModalConfirm :is-open="isModalConfirmSave" :is-show-header="true" @close-modal="closeModalConfirmSave">
    <template #default>
      <div class="box_section">
        <p>{{ t("common.message.save") }}</p>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_md btn_primary" @click="saveData">{{ t("common.confirm") }}</button>
      <button type="button" class="button btn_md btn_white popup_close" @click="closeModalConfirmSave">{{ t("common.cancel") }}</button>
    </template>
  </TModalConfirm>
</template>

<style lang="scss" scoped>
.training-report-block {
  margin-bottom: 24px;
  &__head {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 24px;
    @media screen and (max-width: 768px) {
      flex-direction: column-reverse;
      align-items: flex-start;
      gap: 10px;
      margin-bottom: 16px;
    }
    &__title {
      span {
        font-size: 16px;
        font-weight: 500;
        @media screen and (max-width: 768px) {
          font-size: 14px;
        }
        &:last-child {
          margin-left: 15px;
          color: var(--gray--55);
        }
      }
    }
    &__dowload {
      font-size: 14px;
      font-weight: 500;
      @media screen and (max-width: 768px) {
        font-size: 12px;
      }
    }
  }
  &__form {
    &__row {
      &:first-child {
        border-bottom: 1px solid var(--gray--e4);
        margin-bottom: 24px;
      }
      .item {
        display: flex;
        gap: 10px;
        margin-bottom: 16px;
        align-items: center;
        @media screen and (max-width: 768px) {
          flex-direction: column;
          align-items: flex-start;
        }
        .note {
          display: flex;
          align-items: center;
          width: 100%;
          justify-content: space-between;
          background: var(--gray--f8);
          padding: 16px;
          @media screen and (max-width: 768px) {
            flex-direction: column;
            align-items: flex-start;
            gap: 10px;
          }
          .select-input {
            @media screen and (max-width: 768px) {
              width: 100%;
              background: var(--white);
            }
          }
        }
        .label {
          flex: 1;
          font-size: 14px;
          font-weight: 500;
        }
        .file {
          justify-content: flex-start !important;
          gap: 0 !important;
          @media screen and (max-width: 768px) {
            width: 100%;
            display: block !important;
          }
        }
        .input {
          flex: 5;
          display: flex;
          width: 100%;
          gap: 5px;
          justify-content: space-between;
          align-items: center;
          span {
            @media screen and (max-width: 768px) {
              display: none;
            }
          }
          .option {
            display: flex;
            gap: 10px;
            width: 100%;
            @media screen and (max-width: 768px) {
              flex-direction: column;
            }
          }
        }
      }
    }
  }
}
</style>
