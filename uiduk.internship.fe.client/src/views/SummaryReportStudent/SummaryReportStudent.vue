<template>
  <TModal
    classSize="medium-large"
    :is-open="isOpen"
    :is-show-header="true"
    :is-show-footer="false"
    :modal-id="'modalId'"
    @close-modal="closeModal"
    :key="modalKey"
  >
    <template #title>
      <strong>{{ t("summaryReport.title") }}</strong>
    </template>
    <template #default>
      <div id="summary_report">
      <div class="personal-info-block">
        <div class="head title_head_print">
          <div class="head__title title_print">
            <span>{{ t("summaryReport.title") }}</span>
            <span>{{ userInfo.userId ? `${userInfo.userName} (${userInfo.colgCdNm} ${userInfo.shyrCdNm} ${userInfo.userId})` : "" }}</span>
          </div>
          <div class="head__dowload">
            <ButtonExportPDFV1
              class="btn_lg"
              id="summary_report"
              nameExport="summary_report"
              :disabled="isDisabled"
            />
          </div>
        </div>
        <div class="body" id="export">
          <div class="body__label title_print_sub">{{ t("summaryReport.label") }}</div>
          <div class="body__content">
            <TextArea
              id="input_content"
              v-model="summaryDetailResponseModel.content"
              required
              name="input_content"
              cols="30"
              rows="10"
              :placeholder="t('summaryReport.placeholder')"
              maxlength="2000"
              :disabled="isDisabledComplete"
            />
          </div>
        </div>
      </div>
      </div>
    </template>
    <template #footer>
      <button
        type="button"
        class="button btn_xl btn_primary"
        @click="openModalConfirm"
        :disabled="isDisabled || isDisabledComplete"
      >
        {{ t("common.save") }}
      </button>
    </template>
  </TModal>
  <TModalAlert :is-open="isModalAlertSuccessSave" :is-show-header="true" :is-show-footer="true">
    <template #default>
      <div class="box_section">
        <p>{{ t("summaryReport.textModal") }}</p>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_md btn_primary" @click="cancelAction">{{ t("common.confirm") }}</button>
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
      <strong></strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ t("summaryReport.confirmSave") }}</p>
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
import TextArea from "@/components/common/input/TextArea.vue";
import { summaryReportStudentStore } from "@/stores/summaryReportStudent";
import { CreateComprehensive, SummaryDetailResponseModel } from "@/stores/summaryReportStudent/summaryReportStudent.type";
import { getUserInfo } from "@/utils/storage";
import { ref, computed } from "vue";
import ButtonExportPDFV1 from "@/components/common/ButtonExportPDFV1.vue";
import { useI18n } from "vue-i18n";
import { SUCCSESS_STATUS } from "@/constants/screen.const";
import { INTERNSHIP_STATUS_APPROVED } from "@/constants/common.const";
const { t } = useI18n();
const isModalAlertSuccessSave = ref(false);
const modalKey = ref(1);
const idPlan = ref("");
const isOpen = ref(false);
const userInfo = ref(JSON.parse(getUserInfo()).data);
const store = summaryReportStudentStore();
const summaryDetailResponseModel = ref<SummaryDetailResponseModel>({
  internStdId: "",
  stdSummaryReportId: "",
  stdNm: "",
  deptNm: "",
  stdYear: "",
  stdNo: "",
  content: "",
  internPlanSeq: "",
});
const createComprehensive = ref<CreateComprehensive>({
  stdSummaryReportSeq: "",
  internPlanSeq: "",
  internStdSeq: "",
  content: "",
});
const modalOpenConfirm = ref(false);
const isDisabledComplete = ref(false);

const fetchDataId = async () => {
  await store.getDetail({
    method: "POST",
    body: idPlan.value,
  });

  if (store?.detailData) {
    summaryDetailResponseModel.value = store?.detailData;
    isDisabledComplete.value = store?.detailData.internshipStatus == INTERNSHIP_STATUS_APPROVED;
  }

  isOpen.value = true;
};

const isDisabled = computed(() => {
  return !(summaryDetailResponseModel.value.content && summaryDetailResponseModel.value.content.length > 0);
});
const create = async () => {
  createComprehensive.value.stdSummaryReportSeq = summaryDetailResponseModel.value.stdSummaryReportId
    ? summaryDetailResponseModel.value.stdSummaryReportId
    : "";
  createComprehensive.value.internPlanSeq = idPlan.value;
  createComprehensive.value.internStdSeq = summaryDetailResponseModel.value.internStdId;
  createComprehensive.value.content = summaryDetailResponseModel.value.content;

  if (summaryDetailResponseModel.value.stdSummaryReportId == null) {
    await store.createSummary({
      method: "POST",
      body: createComprehensive.value,
    });

    if (store.status?.value == SUCCSESS_STATUS) {
      summaryDetailResponseModel.value.stdSummaryReportId = store.dataRes.data;
      isModalAlertSuccessSave.value = true;
    }
  } else {
    await store.updateSummary({
      method: "POST",
      body: createComprehensive.value,
    });

    if (store.status?.value == SUCCSESS_STATUS) {
      isModalAlertSuccessSave.value = true;
    }
  }
};

const closeModal = () => {
  idPlan.value = "";
  summaryDetailResponseModel.value = {
    internStdId: "",
    stdSummaryReportId: "",
    stdNm: "",
    deptNm: "",
    stdYear: "",
    stdNo: "",
    content: "",
    internPlanSeq: "",
  };
  isOpen.value = false;
};

const cancelAction = () => {
  isModalAlertSuccessSave.value = false;
  closeModal();
};

const openModal = (id: string) => {
  idPlan.value = id;
  fetchDataId();
};
const closeModalConfirm = () => {
  modalOpenConfirm.value = false;
}
const handleConfirmModal = () => {
  closeModalConfirm();
  create();
};
const openModalConfirm = () => {
  modalOpenConfirm.value = true;
}
defineExpose({
  openModal,
});
</script>
<style lang="scss" scoped>
.personal-info-block {
  
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
    font-size: 24px !important;
    font-weight: 500;
  }
  .title_print_sub{
    margin-bottom: 12px !important;
  }
  .title_head_print{
    margin-bottom: 10px !important;
  }
}
</style>
