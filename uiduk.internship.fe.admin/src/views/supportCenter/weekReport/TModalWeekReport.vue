<template>
  <TModal
    :classSize="`large`"
    :is-open="isOpen"
    :is-show-header="true"
    :is-show-footer="true"
    :modal-id="modalId"
    :key="modalKey"
    @close-modal="closeModal"
  >
    <template #title>
      <strong>{{ t("supportCenter.weekReport.titleHeader") }}</strong>
    </template>
    <template #default>
      <div class="training-report-block">
        <div class="training-report-block__head">
          <div class="training-report-block__head__title">
            <span>{{ t("supportCenter.weekReport.titleHeader") }}</span>
            <span>{{ dataForm.stdNo ? `${dataForm.stdNm} (${dataForm.deptNm} ${dataForm.shyrNm} ${dataForm.stdNo})` : "" }}</span>
          </div>

          <div class="training-report-block__head__right">
            <div class="right_select_box">
              <SelectBoxBase id="selectbox_week" v-model="detailId" name="selectbox_stdName" :data="listWeek" />
            </div>

            <div class="right_select_box">
              <SelectBoxBase id="selectbox_stdName" v-model="stdId" name="selectbox_stdName" :data="listStdName" />
            </div>

            <ButtonExportPDFV1 id="week_report" class="btn_lg" :name-export="nameExport" :disabled="statusBtnPdf" />
          </div>
        </div>

        <div id="week_report" class="training-report-block__form">
          <div class="training-report-block__form__row">
            <div class="item">
              <div class="note">
                <p class="validate_password">{{ t("supportCenter.weekReport.warning") }}</p>
              </div>
            </div>
          </div>

          <div class="training-report-block__form__row" v-for="(item, index) in dataForm.lstWeekCalendar" :key="index">
            <div class="item">
              <div class="label">{{ t("supportCenter.weekReport.day") }}</div>
              <div class="input">
                <BaseDatePicker v-model="item.dateWeek" :id="'input_date' + index" :isDetail="true" />
              </div>
            </div>

            <div class="item">
              <div class="label">{{ t("supportCenter.weekReport.time") }}</div>
              <div class="input">
                <div class="option">
                  <SelectBoxBase
                    v-model="item.inHour"
                    class="select-option"
                    name="selectbox_in_hour"
                    :disabled="item.isDisabled === STATUS_YES"
                    :data="listSelectBoxHour"
                  />
                  <SelectBoxBase
                    v-model="item.inMinute"
                    class="select-option"
                    name="selectbox_in_minute"
                    :disabled="item.isDisabled === STATUS_YES"
                    :data="listSelectBoxMinute"
                  />
                </div>
                <span>~</span>
                <div class="option">
                  <SelectBoxBase
                    v-model="item.outHour"
                    class="select-option"
                    name="selectbox_out_hour"
                    :disabled="item.isDisabled === STATUS_YES"
                    :data="listSelectBoxHour"
                  />
                  <SelectBoxBase
                    v-model="item.outMinute"
                    class="select-option"
                    name="selectbox_out_minute"
                    :disabled="item.isDisabled === STATUS_YES"
                    :data="listSelectBoxMinute"
                  />
                </div>
              </div>
            </div>

            <div class="item">
              <div class="label">{{ t("supportCenter.weekReport.jobContent") }}</div>
              <div class="input">
                <InputBase
                  id="input_validate"
                  v-model="item.jobContent"
                  :disabled="item.isDisabled === STATUS_YES"
                  :placeholder="t('supportCenter.weekReport.jobContentPlaceholder')"
                />
              </div>
            </div>

            <div class="item">
              <div class="label">{{ t("supportCenter.weekReport.knowledge") }}</div>
              <div class="input">
                <TextArea
                  id="text_validate"
                  v-model="item.acquiredKnowledge"
                  :disabled="item.isDisabled === STATUS_YES"
                  :placeholder="t('supportCenter.weekReport.knowledgePlaceholder')"
                />
              </div>
            </div>

            <div class="item">
              <div class="label">{{ t("supportCenter.weekReport.signature") }}</div>
              <div class="input file" v-if="item.isDisabled !== STATUS_YES">
                <InputFileBase
                  :mode="MODE_DETAIL"
                  :id="`corp_sign_file` + index"
                  :type="'img'"
                  :preview-flag="true"
                  :show-mess="false"
                  :orgName="'CORM_SIGN'"
                  :category="'img'"
                  :sectionName="'CORM_SIGN'"
                  :referKey="dataForm.corpId"
                />

                <InputFileBase
                  :id="`input_file` + index"
                  :ref="(el) => (fileSignSp[index] = el)"
                  :mode="MODE_EDIT"
                  :type="fileType"
                  :org-name="'STD_WEEK_REPORT'"
                  :category="'img'"
                  :section-name="'STD_WEEK_REPORT'"
                  :preview-flag="true"
                  :description="t('supportCenter.weekReport.signaturePlaceholder')"
                  :multiple="false"
                  :max-file="1"
                  :show-mess="false"
                  :refer-key="item.stdWeekReportId"
                  :key="item.stdWeekReportId"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_md btn_primary" :disabled="statusBtnConfirm" @click="handleActionSaveClick">
        {{ t("common.save") }}
      </button>
      <button type="button" class="button btn_md btn_white popup_close" @click="closeModal">{{ t("common.close") }}</button>
    </template>
  </TModal>

  <TModalConfirm :is-open="modalOpenConfirm" :is-show-footer="true" :is-show-header="true" @close-modal="closeModalConfirm">
    <template #title>
      <strong></strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ t("supportCenter.weekReport.confirmSave") }}</p>
      </div>
    </template>
    <template #footer>
      <button class="button btn_md btn_primary" type="button" @click="save">{{ t("common.confirm") }}</button>
      <button class="button btn_md btn_white popup_close" type="button" @click="closeModalConfirm">{{ t("common.cancel") }}</button>
    </template>
  </TModalConfirm>

  <TModalAlert :is-open="modalOpenAlert" :is-show-footer="true" :is-show-header="true" @close-modal="closeModalAlert">
    <template #title>
      <strong></strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ t("supportCenter.weekReport.saveSuccess") }}</p>
      </div>
    </template>
    <template #footer>
      <button class="button btn_md btn_primary" type="button" @click="closeModalAlert">{{ t("common.confirm") }}</button>
    </template>
  </TModalAlert>
</template>

<script setup lang="ts">
import BaseDatePicker from "@/components/common/input/BaseDatePicker.vue";
import { MODE_DETAIL, STATUS_YES } from "@/constants/common.const";
import { FILE_TYPE_IMAGE, MODE_EDIT, SUCCSESS_STATUS } from "@/constants/screen.const";
import CodeMngModel from "@/model/common/CodeMngModel";
import { weekReportStore } from "@/stores/supportCenter/weekReport/useWeekReport";
import { WeekReportReqModel, WeekReportResModel } from "@/stores/supportCenter/weekReport/weekReport.type";
import { ref, watch, defineExpose, ComponentPublicInstance } from "vue";
import { useI18n } from "vue-i18n";

const { t } = useI18n();
const storeWeekReport = weekReportStore();

const isOpen = ref(false);
const modalOpenConfirm = ref(false);
const modalOpenAlert = ref(false);
const planId = ref("");
const detailId = ref("");
const stdId = ref("");
const modalId = ref("weekReport");
const modalKey = ref(1);
const statusBtnPdf = ref(true);
const statusBtnConfirm = ref(true);

const listWeek = ref([]);
const listStdName = ref<CodeMngModel[]>([{ cdId: "", cdNm: t("common.selectedStd"), upCdId: "ALL" }]);
const listSelectBoxHour = ref<CodeMngModel[]>([]);
const listSelectBoxMinute = ref<CodeMngModel[]>([]);

const dataForm = ref<WeekReportResModel>({
  stdNm: "",
  deptCd: "",
  deptNm: "",
  shyrCd: "",
  shyrNm: "",
  stdNo: "",
  corpId: "",
  lstWeekCalendar: [],
});
const dataRequest = ref<WeekReportReqModel[]>([
  {
    stdWeekReportSeq: "",
    internDetailSeq: "",
    internStdSeq: "",
    dateWeek: "",
    inHour: "",
    inMinute: "",
    outHour: "",
    outMinute: "",
    jobContent: "",
    acquiredKnowledge: "",
  },
]);

const nameExport = ref("weekReport");
const fileType = ref(FILE_TYPE_IMAGE);
type ChildComponentInstance = ComponentPublicInstance<{
  upLoadFile: (id: any) => void;
}>;

const fileSignSp = ref<(ChildComponentInstance | null)[]>([]);

//first data
const openModal = (id: string) => {
  planId.value = id;
  stdId.value = "";
  modalKey.value++;
  getListWeek();
  getListStdName();
  getListHourMinute();

  isOpen.value = true;
};

const closeModal = () => {
  listStdName.value = [{ cdId: "", cdNm: t("common.selectedStd"), upCdId: "ALL" }];
  listSelectBoxHour.value = [];
  listSelectBoxMinute.value = [];

  isOpen.value = false;
};

const getListWeek = async () => {
  await storeWeekReport.getListWeek({
    method: "POST",
    body: planId.value,
  });

  listWeek.value = storeWeekReport.listWeek.data.map((item) => ({
    cdId: item.internPlanDetailId,
    cdNm: `주 ${item.internWeek}`,
  }));

  detailId.value = listWeek.value?.[0].cdId;
};

const getListStdName = async () => {
  await storeWeekReport.getListStd({
    method: "POST",
    body: planId.value,
  });

  listStdName.value = [
    ...listStdName.value,
    ...storeWeekReport.listStd.data.map((item) => ({
      cdId: item.id,
      cdNm: item.name,
    })),
  ];
};

const getListHourMinute = () => {
  for (let i = 0; i < 24; i++) {
    const hour = i.toString().padStart(2, "0");
    listSelectBoxHour.value.push({ cdId: hour, cdNm: hour, upCdId: "hours" });
  }

  for (let i = 0; i < 60; i++) {
    const minute = i.toString().padStart(2, "0");
    listSelectBoxMinute.value.push({ cdId: minute, cdNm: minute, upCdId: "minutes" });
  }
};

const getDetailWeekReport = async () => {
  await storeWeekReport.getDetailWeekReport({
    method: "POST",
    body: { internDetailId: detailId.value, internStdId: stdId.value },
  });

  dataForm.value = storeWeekReport.resultData?.data;
  dataForm.value.lstWeekCalendar.forEach((item) => {
    item.dateWeek ||= item.attendanceDate;
  });

  statusBtnConfirm.value = dataForm.value.lstWeekCalendar.every((item) => item.isDisabled === "Y");
};

//action handle click
const closeModalConfirm = () => {
  modalOpenConfirm.value = false;
};

const closeModalAlert = () => {
  stdId.value = "";
  detailId.value = listWeek.value?.[0].cdId;
  modalKey.value++;
  fileSignSp.value = [];
  modalOpenAlert.value = false;
};

const handleActionSaveClick = () => {
  modalOpenConfirm.value = true;
};

const checkData = (id: any, index: number) => {
  const childComponent = fileSignSp.value[index];
  if (childComponent && typeof childComponent.upLoadFile === "function") {
    childComponent.upLoadFile(id);
  }
};

//interact with db
const getDataForm = () => {
  dataRequest.value = dataForm.value.lstWeekCalendar
    .filter((el) => el.isDisabled === "N")
    .map((item) => {
      return {
        stdWeekReportSeq: item.stdWeekReportId,
        internDetailSeq: detailId.value,
        internStdSeq: stdId.value,
        dateWeek: item.dateWeek,
        inHour: item.inHour,
        inMinute: item.inMinute,
        outHour: item.outHour,
        outMinute: item.outMinute,
        jobContent: item.jobContent,
        acquiredKnowledge: item.acquiredKnowledge,
      };
    }) as WeekReportReqModel[];
};

const save = async () => {
  getDataForm();

  await storeWeekReport
    .createWeekReport({
      method: "POST",
      body: dataRequest.value,
    })
    .then(() => {
      if (storeWeekReport.status?.value === SUCCSESS_STATUS) {
        fileSignSp.value = fileSignSp.value.filter((item) => item !== null)
        storeWeekReport.listWeekReportData.data.forEach((item, index) => {
          checkData(item, index);
          dataForm.value.lstWeekCalendar[index].stdWeekReportId = item;
        });
      }
    });

  modalOpenConfirm.value = false;
  modalOpenAlert.value = true;
};

watch(
  () => detailId.value,
  async () => {
    await getDetailWeekReport();
  },
);

watch(
  () => stdId.value,
  async (newValue) => {
    await getDetailWeekReport();
    statusBtnPdf.value = newValue === "";
  },
);

defineExpose({
  openModal,
});
</script>

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
    &__right {
      display: flex;
      align-items: center;
      gap: 8px;

      .right_select_box {
        width: 160px;
      }

      & > div {
        display: flex;
        align-items: center;
        gap: 8px;
      }
    }
  }
  &__form {
    &__row {
      border-bottom: 1px solid var(--gray--e4);
      margin-bottom: 24px;

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
