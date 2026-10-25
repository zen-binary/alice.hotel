<template>
  <div class="personal-info-block">
    <div class="head">
      <div class="head__title">
        <span>{{ t("professor.summaryReport.title") }}</span>
        <span>{{ dataForm.stdNo ? `${dataForm.stdNm} (${dataForm.deptNm} ${dataForm.stdYear} ${dataForm.stdNo})` : "" }}</span>
      </div>
    </div>
    <div class="body">
      <div class="body__label">{{ t("professor.summaryReport.content") }}</div>
      <div class="body__content">
        <TextArea
          :disabled="true"
          id="input_content"
          v-model="dataForm.content"
          :maxlength="2000"
          name="input_content"
          cols="30"
          rows="10"
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import TextArea from "@/components/common/input/TextArea.vue";
import { SUCCSESS_STATUS } from "@/constants/screen.const";
import { instructorEvaluationStore } from "@/stores/instructorEvaluation";
import { SummaryReportResModel } from "@/stores/instructorEvaluation/instructorEvaluation.type";
import { onBeforeMount, defineProps, ref, watch } from "vue";
import { useI18n } from "vue-i18n";

const { t } = useI18n();
const storeSummaryReport = instructorEvaluationStore();

const props = defineProps({
  stdId: {
    type: String,
    default: "",
  },
});

const dataForm = ref<SummaryReportResModel>({
  internStdId: "",
  stdSummaryReportId: "",
  deptNm: "",
  stdNm: "",
  stdYear: "",
  stdNo: "",
  content: "",
});

onBeforeMount(() => {
  getDetail();
});

//first data
const getDetail = async () => {
  await storeSummaryReport
    .fetchDetailSummaryReport({
      method: "POST",
      body: props.stdId,
    })
    .then(() => {
      if (storeSummaryReport.status?.value === SUCCSESS_STATUS) {
        dataForm.value = storeSummaryReport.listSummaryReport?.data;
      }
    });
};

watch(
  () => props.stdId,
  (newValue) => {
    if (newValue == "") {
      dataForm.value = {
        internStdId: "",
        stdSummaryReportId: "",
        deptNm: "",
        stdNm: "",
        stdYear: "",
        stdNo: "",
        content: "",
      } as SummaryReportResModel;
    } else {
      getDetail();
    }
  },
);
</script>

<style lang="scss" scoped>
.personal-info-block {
  margin-bottom: 24px;
  margin-top: 24px;
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
</style>
