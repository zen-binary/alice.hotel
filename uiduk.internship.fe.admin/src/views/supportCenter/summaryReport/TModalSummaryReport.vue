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
      <strong>{{ t("supportCenter.summaryReport.title") }}</strong>
    </template>
    <template #default>
      <div id="summary-report" >
      <div class="personal-info-block">
        <div class="personal-info-block__field-training">
          <div class="title title_print">
            <span>{{ t("supportCenter.summaryReport.title") }}</span>
            <span>{{ dataForm.stdNo ? ` - ${dataForm.stdNm} (${dataForm.deptNm} ${dataForm.stdYear} ${dataForm.stdNo})` : "" }}</span>
          </div>
          <div class="summary_report__title__right">
            <div>
              <div class="right_select_box">
                <SelectBoxBase id="selectbox_stdName" v-model="stdId" name="selectbox_stdName" :data="listStdName" />
              </div>
              <ButtonExportPDFV1 class="btn_lg" id="summary-report" :name-export="nameExportPDF" :disabled="statusBtnPdf" />
            </div>
          </div>
        </div>

        <div class="body">
          <div class="body__label title_print_sub">{{ t("supportCenter.summaryReport.content") }}</div>
          <div class="body__content">
            <TextArea
              id="input_content"
              v-model="dataForm.content"
              required
              :disabled="isDisabled"
              :maxlength="2000"
              name="input_content"
              cols="30"
              rows="10"
              :placeholder="t('supportCenter.summaryReport.contentPlaceholder')"
            />
          </div>
        </div>
      </div>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_md btn_primary" :disabled="isDisabledSave" @click="handleActionSaveClick">       
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
        <p>{{ t("supportCenter.summaryReport.confirmSave") }}</p>
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
        <p>{{ t("supportCenter.summaryReport.saveSuccess") }}</p>
      </div>
    </template>
    <template #footer>
      <button class="button btn_md btn_primary" type="button" @click="closeModalAlert">{{ t("common.confirm") }}</button>
    </template>
  </TModalAlert>
</template>

<script setup lang="ts">
import TextArea from "@/components/common/input/TextArea.vue";
import { SummaryReportReqModel, SummaryReportResModel } from "@/stores/supportCenter/summaryReport/summaryReport.type";
import { summaryReportStore } from "@/stores/supportCenter/summaryReport/useSummaryReport";
import { ref, defineExpose, watch, computed } from "vue";
import { useI18n } from "vue-i18n";

const { t } = useI18n();
const storeSummaryReport = summaryReportStore();

const isOpen = ref(false);
const modalOpenConfirm = ref(false);
const modalOpenAlert = ref(false);
const statusBtnPdf = ref(true);
const modalKey = ref(1);
const modalId = ref("summaryReport");
const planId = ref("");
const stdId = ref("");
const isDisabled = ref(true);

const listStdName = ref([{ cdId: "", cdNm: t("common.selectedStd"), upCdId: "ALL" }]);

const dataForm = ref<SummaryReportResModel>({
  internStdId: "",
  stdSummaryReportId: "",
  deptNm: "",
  stdNm: "",
  stdYear: "",
  stdNo: "",
  content: "",
});
const dataRequest = ref<SummaryReportReqModel>({
  stdSummaryReportSeq: "",
  internPlanSeq: "",
  internStdSeq: "",
  content: "",
});

const nameExportPDF = `summary_report`;

// first data
const isDisabledSave = computed(() => {
  return isDisabled.value || !(dataForm.value.content && dataForm.value.content.trim().length > 0);
});

const openModal = (id: string) => {
  planId.value = id;
  modalKey.value++;
  getListStdName();

  isOpen.value = true;
};

const closeModal = () => {
  stdId.value = "";
  listStdName.value = [{ cdId: "", cdNm: t("common.selectedStd"), upCdId: "ALL" }];
  dataForm.value = {
    internStdId: "",
    stdSummaryReportId: "",
    stdNm: "",
    deptNm: "",
    stdYear: "",
    stdNo: "",
    content: "",
  } as SummaryReportResModel;

  isOpen.value = false;
};

const getListStdName = async () => {
  await storeSummaryReport.getListStd({
    method: "POST",
    body: planId.value,
  });

  listStdName.value = [
    ...listStdName.value,
    ...storeSummaryReport.listStd.data.map((item) => ({
      cdId: item.id,
      cdNm: item.name,
    })),
  ];
};

const getDetailReport = async () => {
  await storeSummaryReport.getDetailSummaryReport({
    method: "POST",
    body: stdId.value,
  });

  dataForm.value = storeSummaryReport.resultData?.data;
};

//action handle click
const closeModalConfirm = () => {
  modalOpenConfirm.value = false;
};

const closeModalAlert = () => {
  modalOpenAlert.value = false;
  closeModal();
};

const handleActionSaveClick = () => {
  modalOpenConfirm.value = true;
};

//interact with db
const getDataForm = () => {
  dataRequest.value.stdSummaryReportSeq = dataForm.value.stdSummaryReportId;
  dataRequest.value.internPlanSeq = planId.value;
  dataRequest.value.internStdSeq = stdId.value;
  dataRequest.value.content = dataForm.value.content;
};

const save = async () => {
  getDataForm();
  if (dataForm.value.stdSummaryReportId != null && dataForm.value.stdSummaryReportId != "") {
    await storeSummaryReport.updateSummaryReport({
      method: "POST",
      body: dataRequest.value,
    });
  } else {
    await storeSummaryReport.createSummaryReport({
      method: "POST",
      body: dataRequest.value,
    });
  }

  modalOpenConfirm.value = false;
  modalOpenAlert.value = true;
};

watch(
  () => stdId.value,
  async (newValue) => {
    if (newValue == "") {
      dataForm.value = {
        internStdId: "",
        stdSummaryReportId: "",
        stdNm: "",
        deptNm: "",
        stdYear: "",
        stdNo: "",
        content: "",
      } as SummaryReportResModel;

      statusBtnPdf.value = true;
      isDisabled.value = true;
    } else {
      getDetailReport();
      statusBtnPdf.value = false;
      isDisabled.value = false;
    }
  },
);

defineExpose({
  openModal,
});
</script>

<style lang="scss" scoped>
.summary_report {
  &__title {
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-wrap: wrap;
    gap: 12px;

    & > h2 {
      font-size: 18px;
      font-weight: 500;
      line-height: 27px;
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
}

.personal-info-block {
  &__field-training {
    display: flex;
    justify-content: space-between;
    align-items: center;
    @media screen and (max-width: 768px) {
      justify-content: space-between;
      align-items: flex-start;
      flex-direction: column-reverse;
      gap: 10px;
    }

    .title {
      font-size: 18px;
      font-weight: 500;
      @media screen and (max-width: 768px) {
        font-size: 16px;
      }
    }

    .dowload {
      font-size: 14px;
      font-weight: 500;
      @media screen and (max-width: 768px) {
        font-size: 12px;
      }
    }
  }

  margin-bottom: 24px;
  .head {
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
  .body {
    &__label {
      font-size: 16px;
      font-weight: 500;
      color: var(--gray--1d);
      margin-bottom: 8px;
      position: relative;
      margin-left: 10px;
      @media screen and (max-width: 768px) {
        font-size: 14px;
      }
      &::before {
        content: "";
        position: absolute;
        left: -13px;
        top: 50%;
        transform: translateY(-50%);
        width: 4px;
        height: 4px;
        background-color: var(--blue--009);
        border-radius: 50%;
        @media screen and (max-width: 600px) {
          top: 25%;
          left: -8px;
        }
      }
    }
    &__content {
      font-size: 14px;
      textarea {
        width: 100%;
        max-height: 200px;
        color: var(--gray--1d);
        &::placeholder {
          color: var(--gray--be);
        }
      }
    }
  }
}

@media print {
  .title_print{
    margin-bottom: 14px;
  }
  .title_print > span{
    font-size: 24px;
    font-weight: 500;
  }
  .title_print_sub{
    margin-bottom: 12px !important;
  }
}
</style>
