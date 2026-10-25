<template>
  <div id="training-report-block" class="training-report-block">
    <div class="head">
      <div class="head__title">
        <span>{{ t("professor.weekReport.titleHeader") }}</span>
        <span>{{ dataForm.stdNo ? `${dataForm.stdNm} (${dataForm.deptNm} ${dataForm.shyrNm} ${dataForm.stdNo})` : "" }}</span>
      </div>
    </div>

    <div class="form">
      <div class="row">
        <div class="item">
          <div class="note">
            <p class="validate_password">{{ t("professor.weekReport.warning") }}</p>
            <div class="select-input">
              <SelectBoxBase class="select-option" id="selectbox_week" v-model="detailId" name="selectbox_week" :data="listWeek" />
            </div>
          </div>
        </div>
      </div>

      <div class="row" v-for="(item, index) in dataForm.lstWeekCalendar" :key="index">
        <div class="item">
          <div class="label">{{ t("professor.weekReport.day") }}</div>
          <div class="input">
            <BaseDatePicker v-model="item.dateWeek" :id="'input_date' + index" :isDetail="true" />
          </div>
        </div>

        <div class="item">
          <div class="label">{{ t("professor.weekReport.time") }}</div>
          <div class="input">
            <div class="option">
              <SelectBoxBase v-model="item.inHour" class="select-option" name="selectbox_in_hour" :disabled="true" :data="listSelectBoxHour" />
              <SelectBoxBase v-model="item.inMinute" class="select-option" name="selectbox_in_minute" :disabled="true" :data="listSelectBoxMinute" />
            </div>
            <span>~</span>
            <div class="option">
              <SelectBoxBase v-model="item.outHour" class="select-option" name="selectbox_out_hour" :disabled="true" :data="listSelectBoxHour" />
              <SelectBoxBase
                v-model="item.outMinute"
                class="select-option"
                name="selectbox_out_minute"
                :disabled="true"
                :data="listSelectBoxMinute"
              />
            </div>
          </div>
        </div>

        <div class="item">
          <div class="label">{{ t("professor.weekReport.jobContent") }}</div>
          <div class="input">
            <InputBase id="input_validate" v-model="item.jobContent" :disabled="true" />
          </div>
        </div>

        <div class="item">
          <div class="label">{{ t("professor.weekReport.knowledge") }}</div>
          <div class="input">
            <TextArea id="text_validate" v-model="item.acquiredKnowledge" :disabled="true" />
          </div>
        </div>

        <div class="item">
          <div class="label">{{ t("professor.weekReport.signature") }}</div>
          <div class="input file" v-if="item.isDisabled !== 'Y'">
            <InputFileBase
              :id="`corp_sign_file` + index"
              :mode="MODE_DETAIL"
              :type="'img'"
              :preview-flag="true"
              :show-mess="false"
              :org-name="'CORM_SIGN'"
              :category="'img'"
              :section-name="'CORM_SIGN'"
              :refer-key="dataForm.corpId"
            />

            <InputFileBase
              :id="`input_file` + index"
              :mode="MODE_DETAIL"
              :type="fileType"
              :org-name="'STD_WEEK_REPORT'"
              :category="'img'"
              :section-name="'STD_WEEK_REPORT'"
              :preview-flag="true"
              :multiple="false"
              :max-file="1"
              :show-mess="false"
              :refer-key="item.stdWeekReportId"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import { FILE_TYPE_IMAGE, MODE_DETAIL } from "@/constants/screen.const";
import { CodeMngModel } from "@/stores/common/codeMng/codeMng.type";
import { instructorEvaluationStore } from "@/stores/instructorEvaluation";
import { WeekReportResModel } from "@/stores/instructorEvaluation/instructorEvaluation.type";
import { ref, watch, defineProps, onBeforeMount } from "vue";
import { useI18n } from "vue-i18n";

const { t } = useI18n();
const storeWeekReport = instructorEvaluationStore();

const props = defineProps({
  planId: {
    type: String,
    default: "",
  },
  stdId: {
    type: String,
    default: "",
  },
});

const fileType = ref(FILE_TYPE_IMAGE);
const detailId = ref("");

const listWeek = ref([]);
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

onBeforeMount(() => {
  getListWeek();
  getListHourMinute();
});

//first data
const getListWeek = async () => {
  await storeWeekReport.getListWeek({
    method: "POST",
    body: props.planId,
  });

  listWeek.value = storeWeekReport.listWeek.data.map((item) => ({
    cdId: item.internPlanDetailId,
    cdNm: `주 ${item.internWeek}`,
  }));

  detailId.value = listWeek.value?.[0].cdId;
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

const getDetail = async () => {
  await storeWeekReport.fetchDetailWeeklyReport({
    method: "POST",
    body: { internDetailId: detailId.value, internStdId: props.stdId },
  });

  dataForm.value = storeWeekReport.listWeeklyReport?.data;
  dataForm.value.lstWeekCalendar.forEach((item) => {
    item.dateWeek ||= item.attendanceDate;
  });
};

watch(
  () => detailId.value,
  async () => {
    await getDetail();
  },
);

watch(
  () => props.stdId,
  () => {
    getDetail();
  },
);
</script>

<style lang="scss" scoped>
.training-report-block {
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
  .form {
    .row {
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
