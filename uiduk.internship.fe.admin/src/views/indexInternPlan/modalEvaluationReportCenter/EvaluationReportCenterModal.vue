<template>
  <TModal :is-open="isOpen" :is-show-header="true" :is-show-footer="true" class-size="medium-large" :key="modalKey" @close-modal="closeModal">
    <!-- Modal content goes here -->
    <template #title>
      <strong>{{ t("evaluationReportCenter.title") + ` ${planNm}` }}</strong>
    </template>
    <template #default>
      <div id="instructor_evaluation" class="instructor_evaluation">
        <div class="instructor_evaluation__title">
          <h2>{{ t("evaluationReportCenter.title") }}</h2>
          <div class="instructor_evaluation__title__right">
            <div class="right_select_box">
              <SelectBoxBase v-model="stdValue" name="selectbox" :data="listStdName" :placeholder="t('evaluationReportCenter.empty')" />
            </div>
            <div>
              <ButtonExportPDFV1 class="btn_lg" id="instructor_evaluation" :nameExport="'instructor_evaluation'" :disabled="statusBtnPdf" />
            </div>
          </div>
        </div>

        <div>
          <div class="instructor_evaluation__content">
            <h3>{{ t("evaluationReportCenter.studentInfo") }}</h3>
            <div class="instructor_evaluation__content__table">
              <div class="table_row">
                <!-- 실습생 소속 -->
                <div class="table_col">
                  <div>
                    <p>{{ t("evaluationReportCenter.deptName") }}</p>
                  </div>
                  <div class="table_col__text">
                    <p>{{ dataForm.deptStdNm }}</p>
                  </div>
                </div>

                <!-- 실습기관명 -->
                <div class="table_col">
                  <div>
                    <p>{{ t("evaluationReportCenter.corporateName") }}</p>
                  </div>
                  <div class="table_col__text">
                    <p>{{ dataForm.companyNm }}</p>
                  </div>
                </div>
              </div>

              <div class="table_row">
                <!-- 실습생 성명 -->
                <div class="table_col">
                  <div>
                    <p>{{ t("evaluationReportCenter.studentName") }}</p>
                  </div>
                  <div class="table_col__text">
                    <p>{{ dataForm.stdNm }}</p>
                  </div>
                </div>

                <!-- 현장실습기간 -->
                <div class="table_col">
                  <div>
                    <p>{{ t("evaluationReportCenter.internDate") }}</p>
                  </div>
                  <div class="table_col__text">
                    <p v-if="dataForm.internStartDate || dataForm.internEndDate">
                      {{ dataForm.internStartDate }} ~ {{ dataForm.internEndDate }}({{
                        dataForm.internStartDate && dataForm.internEndDate
                          ? calculateWeeksBetweenDates(dataForm.internStartDate, dataForm.internEndDate)
                          : ""
                      }}주)
                    </p>
                  </div>
                </div>
              </div>

              <div class="table_row">
                <!-- 실습생 근무부서 -->
                <div class="table_col">
                  <div>
                    <p>{{ t("evaluationReportCenter.stdPosition") }}</p>
                  </div>
                  <div>
                    <InputBase v-model="dataForm.internWorkDept" />
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="instructor_evaluation__content">
            <h3>{{ t("evaluationReportCenter.evaluation") }}</h3>
            <div class="instructor_evaluation__content__table1">
              <div class="table_row__mobile">
                <p>{{ t("evaluationReportCenter.title1") }}</p>
                <p>{{ t("evaluationReportCenter.title2") }}</p>
                <p>{{ t("evaluationReportCenter.title3") }}</p>
              </div>

              <div class="table_row">
                <div class="table_row__head">
                  <!-- 평가항목  -->
                  <p>{{ t("evaluationReportCenter.evalField") }}</p>
                </div>

                <div class="table_row__head">
                  <!-- 기준점수 -->
                  <p>{{ t("evaluationReportCenter.evalStandard") }}</p>
                </div>

                <div class="table_row__head">
                  <div class="wd_p100 ta_c">
                    <!-- 평가 -->
                    <p>{{ t("evaluationReportCenter.evalStandardScore") }}</p>
                  </div>
                  <div>
                    <p>{{ t("evaluationReportCenter.standardExcellent") }}</p>
                    <p>{{ t("evaluationReportCenter.standardGood") }}</p>
                    <p>{{ t("evaluationReportCenter.standardAverage") }}</p>
                    <p>{{ t("evaluationReportCenter.standardPoor") }}</p>
                    <p>{{ t("evaluationReportCenter.standardVeryPoor") }}</p>
                  </div>
                </div>

                <div class="table_row__head">
                  <!-- 평가점수 -->
                  <p>{{ t("evaluationReportCenter.evalScore") }}</p>
                </div>
              </div>

              <div class="table_row">
                <div class="table_row__head">
                  <!-- 출결사항 -->
                  <p>{{ t("evaluationReportCenter.attendaceStatus") }}</p>
                </div>
                <div class="table_row__head">
                  <p>25{{ t("evaluationReportCenter.dot") }}</p>
                </div>
                <div class="table_row__head">
                  <div></div>
                  <div>
                    <p>25</p>
                    <p>20</p>
                    <p>15</p>
                    <p>10</p>
                    <p>5</p>
                  </div>
                </div>

                <div class="table_row__head">
                  <div class="input_envaltion_not_print">
                    <InputBase v-model="dataForm.attendanceStatus" :is-number="true" @blur="handleBlur(ATTENDANCE, dataForm.attendanceStatus)" />
                  </div>
                  <div class="input_envaltion_print">
                    <p>{{ dataForm.attendanceStatus }}/25</p>
                  </div>
                </div>
              </div>

              <div class="table_row">
                <div class="table_row__head">
                  <!-- 주간보고서 -->
                  <p>{{ t("evaluationReportCenter.weekReport") }}</p>
                </div>
                <div class="table_row__head">
                  <p>25{{ t("evaluationReportCenter.dot") }}</p>
                </div>
                <div class="table_row__head">
                  <div></div>
                  <div>
                    <p>25</p>
                    <p>20</p>
                    <p>15</p>
                    <p>10</p>
                    <p>5</p>
                  </div>
                </div>

                <div class="table_row__head">
                  <div class="input_envaltion_not_print">
                    <InputBase v-model="dataForm.weekReport" :is-number="true" @blur="handleBlur(WEEK, dataForm.weekReport)" />
                  </div>
                  <div class="input_envaltion_print">
                    <p>{{ dataForm.weekReport }}/25</p>
                  </div>
                </div>
              </div>

              <div class="table_row">
                <div class="table_row__head">
                  <!-- 종합보고서 -->
                  <p>{{ t("evaluationReportCenter.summayReport") }}</p>
                </div>
                <div class="table_row__head">
                  <p>25{{ t("evaluationReportCenter.dot") }}</p>
                </div>
                <div class="table_row__head">
                  <div></div>
                  <div>
                    <p>25</p>
                    <p>20</p>
                    <p>15</p>
                    <p>10</p>
                    <p>5</p>
                  </div>
                </div>

                <div class="table_row__head">
                  <div class="input_envaltion_not_print">
                    <InputBase v-model="dataForm.summaryReport" :is-number="true" @blur="handleBlur(SUMMARY, dataForm.summaryReport)" />
                  </div>
                  <div class="input_envaltion_print">
                    <p>{{ dataForm.summaryReport }}/25</p>
                  </div>
                </div>
              </div>

              <div class="table_row">
                <div class="table_row__head">
                  <!-- 방문지도 -->
                  <p>{{ t("evaluationReportCenter.guidanceReport") }}</p>
                </div>
                <div class="table_row__head">
                  <p>25{{ t("evaluationReportCenter.dot") }}</p>
                </div>
                <div class="table_row__head">
                  <div></div>
                  <div>
                    <p>25</p>
                    <p>20</p>
                    <p>15</p>
                    <p>10</p>
                    <p>5</p>
                  </div>
                </div>

                <div class="table_row__head">
                  <div class="input_envaltion_not_print">
                    <InputBase v-model="dataForm.guidanceReport" :is-number="true" @blur="handleBlur(GUIDANCE, dataForm.guidanceReport)" />
                  </div>
                  <div class="input_envaltion_print">
                    <p>{{ dataForm.guidanceReport }}/25</p>
                  </div>
                </div>
              </div>

              <div class="table_row">
                <div class="table_row__head">
                  <!-- 평가 총점 -->
                  <p>{{ t("evaluationReportCenter.sumScore") }}</p>
                </div>
                <div class="table_row__head">
                  <p>{{ totalReview ? totalReview : "-" }}/100</p>
                </div>
              </div>
            </div>
          </div>

          <div class="instructor_evaluation__content">
            <h3>{{ t("evaluationReportCenter.evalComplete") }}</h3>
            <div class="instructor_evaluation__content__table2">
              <div class="table_row">
                <div class="table_row__head">
                  <!-- 성적 종합평가 -->
                  <p>{{ t("evaluationReportCenter.evalTotal") }}</p>
                </div>

                <div class="table_row__head">
                  <div class="table_row__head__content">
                    <div>
                      <!-- 실습기관 평가점수 -->
                      <p>{{ t("evaluationReportCenter.evalProf") }}</p>
                      <p>{{ t("evaluationReportCenter.scoreOnStandard") }}</p>
                    </div>
                    <div>
                      <p>{{ dataForm.totalCompanyScore ? dataForm.totalCompanyScore + t("evaluationReportCenter.dot") : "-" }}</p>
                    </div>
                  </div>

                  <div class="table_row__head__content">
                    <div>
                      <!-- 지도교수 평가점수 -->
                      <p>{{ t("evaluationReportCenter.evalCorp") }}</p>
                      <p>{{ t("evaluationReportCenter.scoreOnStandard") }}</p>
                    </div>
                    <div>
                      <p>{{ totalReview ? totalReview + t("evaluationReportCenter.dot") : "-" }}</p>
                    </div>
                  </div>

                  <div class="table_row__head__content">
                    <div>
                      <!-- 합계 -->
                      <p class="exist_one_print">{{ t("evaluationReportCenter.totalScore") }}</p>
                    </div>
                    <div>
                      <p>{{ totalFinal ? totalFinal + t("evaluationReportCenter.dot") : "-" }}</p>
                    </div>
                  </div>
                </div>
              </div>

              <div class="table_row">
                <div class="table_row__head">
                  <!-- 이수구분(Pass/Fail) -->
                  <p>{{ t("evaluationReportCenter.evalPassFail") }}</p>
                </div>
                <div class="table_row__head">
                  <div class="table_row__head__content">
                    <div>
                      <p>{{ t("evaluationReportCenter.evalPass") }}</p>
                      <p>121{{ t("evaluationReportCenter.scorePass") }}</p>
                    </div>
                    <div>
                      <p>{{ totalCheckPass ? totalCheckPass + t("evaluationReportCenter.dot") : "-" }}</p>
                    </div>
                  </div>
                  <div class="table_row__head__content">
                    <div>
                      <p>{{ t("evaluationReportCenter.evalFail") }}</p>
                      <p>{{ t("evaluationReportCenter.scoreFail") }}</p>
                    </div>
                    <div>
                      <p>{{ totalCheckFail ? totalCheckFail + t("evaluationReportCenter.dot") : "-" }}</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="instructor_evaluation__content envaltion_top">
            <!-- 위와 같이 산업체 현장실습 방문지도 보고서를 제출합니다. -->
            <p>{{ t("evaluationReportCenter.profInfo") }}</p>

            <div class="group_file__content">
              <p>
                {{ new Date().getFullYear() }}{{ t("evaluationReportCenter.year") }} {{ new Date().getMonth() + 1
                }}{{ t("evaluationReportCenter.year") }} {{ new Date().getDate() }}일
              </p>
              <p>{{ dataForm.deptProfNm }}</p>
              <p>{{ dataForm.profNm }}</p>
              <div>
                <InputFileBase
                  v-if="stdValue !== ''"
                  :key="key"
                  :id="'upload_file_evaluation'"
                  ref="fileSignProfEvaluation"
                  :refer-key="dataForm.id"
                  :mode="`edit`"
                  :name="'upload_file_evaluation'"
                  :type="fileType"
                  :org-name="'EVALUATION_SIGN_PROF'"
                  :category="'img'"
                  :section-name="'EVALUATION_SIGN_PROF'"
                  :preview-flag="true"
                  :show-mess="true"
                />
              </div>
            </div>

            <!-- 위덕대학교 현장실습지원센터장 귀하 -->
            <p>{{ t("evaluationReportCenter.recipientInfo") }}</p>
          </div>
        </div>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button type="button" class="button btn_md btn_primary" @click="handleActionSaveClick" :disabled="statusBtnConfirm">
        {{ t("common.save") }}
      </button>
      <button type="button" class="button btn_md btn_white popup_close" @click="closeModal">{{ t("common.close") }}</button>
    </template>
  </TModal>

  <TModalConfirm :is-open="modalOpenConfirm" :is-show-footer="true" :is-show-header="true" @close-modal="closeModalConfirm">
    <!-- Modal content goes here -->
    <template #title>
      <strong></strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ t("evaluationReportCenter.confirmSave") }}</p>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button class="button btn_md btn_primary" type="button" @click="confirmAction">{{ t("common.confirm") }}</button>
      <button class="button btn_md btn_white popup_close" type="button" @click="closeModalConfirm">{{ t("common.cancel") }}</button>
    </template>
  </TModalConfirm>

  <TModalAlert :is-open="modalOpenAlert" :is-show-footer="true" :is-show-header="true" @close-modal="closeModalAlert">
    <!-- Modal content goes here -->
    <template #title>
      <strong></strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ t("evaluationReportCenter.saveSuccess") }}</p>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button class="button btn_md btn_primary" type="button" @click="closeModalAlert">{{ t("common.confirm") }}</button>
    </template>
  </TModalAlert>
</template>

<script setup lang="ts">
import TModal from "@/components/common/excel/TModal.vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import InputBase from "@/components/common/input/InputBase.vue";
import InputFileBase from "@/components/common/input/InputFileBase.vue";
import ButtonExportPDFV1 from "@/components/common/ButtonExportPDFV1.vue";
import { watch, ref, watchEffect, onBeforeMount } from "vue";
import { evaluationReportCenterStore } from "@/stores/evaluationReportCenter/useEvaluationReportCenter";
import { EvaluationReportProfDetailRes, EvaluationReportProfRequest } from "@/stores/evaluationReportCenter/evaluationReportCenter.type";
import { format } from "date-fns";
import { useI18n } from "vue-i18n";
import { ATTENDANCE, GUIDANCE, SUMMARY, WEEK } from "@/constants/common.const";
import { FILE_TYPE_IMAGE, SUCCSESS_STATUS, FORMAT_YYY_MM_DD_HASH } from "@/constants/screen.const";
import { CodeMngModel } from "@/stores/common/codeMng/codeMng.type";

const { t } = useI18n();
const store = evaluationReportCenterStore();

const modalOpenConfirm = ref(false);
const modalOpenAlert = ref(false);
const idPlan = ref("");
const planNm = ref("");
const stdValue = ref("");
const categoryStatus = ref(true);
const totalReview = ref(0);
const totalFinal = ref(0);
const totalCheckPass = ref(0);
const totalCheckFail = ref(0);
const statusBtnConfirm = ref(true);
const statusBtnPdf = ref(true);

const listStdName = ref<CodeMngModel[]>([{ cdId: "", cdNm: t("common.selectedStd"), upCdId: "ALL" }]);

const props = defineProps<{
  isOpen: boolean;
  internPlan: object;
}>();
const emit = defineEmits(["closeModal"]);

const dataForm = ref<EvaluationReportProfDetailRes>({
  id: "",
  internPlanNm: "",
  deptStdNm: "",
  companyNm: "",
  internStartDate: "",
  internEndDate: "",
  stdNm: "",
  internWorkDept: "",
  attendanceStatus: 0,
  weekReport: 0,
  summaryReport: 0,
  guidanceReport: 0,
  totalCompanyScore: 0,
  deptProfNm: "",
  profNm: "",
  isUpdate: "",
});
const dataRequest = ref<EvaluationReportProfRequest>({
  internProcessEvaluationSeq: "",
  internPlanSeq: "",
  internStdSeq: "",
  workingDepartment: "",
  attendanceStatus: 0,
  weekReport: 0,
  summaryReport: 0,
  guidanceReport: 0,
});

const key = ref(1);
const modalKey = ref(1);
const fileType = FILE_TYPE_IMAGE;
const fileSignProfEvaluation = ref(null);

// first data
onBeforeMount(async () => {
  idPlan.value = props.internPlan.internPlanSeq;
  planNm.value = props.internPlan.internPlanNm;
  getListStdName();
});

const closeModal = () => {
  emit("closeModal");
};

const calculateWeeksBetweenDates = (startDate, endDate) => {
  const start = new Date(startDate);
  const end = new Date(endDate);
  const daysDiff = (end - start) / (1000 * 60 * 60 * 24);
  const weeks = Math.floor(daysDiff / 7);

  return weeks;
};

const getListStdName = async () => {
  await store.getlistStd({
    method: "POST",
    body: idPlan.value,
  });

  listStdName.value = [
    ...listStdName.value,
    ...store.listStd.data.map((item) => ({
      cdId: item.id,
      cdNm: item.name,
    })),
  ];
};

const getDetail = async () => {
  await store.fetchDetailProfessor({
    method: "POST",
    body: { internPlanId: idPlan.value, internStdId: stdValue.value },
  });
  const rep = store.list.data;
  dataForm.value = {
    ...rep,
    attendanceStatus: rep.attendanceStatus ? rep.attendanceStatus : 0,
    weekReport: rep.attendanceStatus ? rep.weekReport : 0,
    summaryReport: rep.attendanceStatus ? rep.attendanceStatus : 0,
    guidanceReport: rep.guidanceReport ? rep.summaryReport : 0,
  };
  dataForm.value.internStartDate = dataForm.value.internStartDate ? format(dataForm.value.internStartDate, FORMAT_YYY_MM_DD_HASH) : "";
  dataForm.value.internEndDate = dataForm.value.internEndDate ? format(dataForm.value.internEndDate, FORMAT_YYY_MM_DD_HASH) : "";
  statusBtnPdf.value = !(dataForm.value.isUpdate === "Y");
  key.value++;
};

//action handle click
const closeModalConfirm = () => {
  modalOpenConfirm.value = false;
};

const closeModalAlert = () => {
  stdValue.value = "";
  modalKey.value++;
  modalOpenAlert.value = false;
};

const handleBlur = (type: string, data: string) => {
  const lstScore = [5, 10, 15, 20, 25];
  if (data.length > 1 && data.startsWith("0") && !data.startsWith("0.")) {
    data = data.replace(/^0+/, "");
  }

  switch (type) {
    case ATTENDANCE:
      if (!lstScore.includes(Number(data))) {
        dataForm.value.attendanceStatus = 0;
      } else {
        dataForm.value.attendanceStatus = Number(data);
      }
      break;
    case WEEK:
      if (!lstScore.includes(Number(data))) {
        dataForm.value.weekReport = 0;
      } else {
        dataForm.value.weekReport = Number(data);
      }
      break;
    case SUMMARY:
      if (!lstScore.includes(Number(data))) {
        dataForm.value.summaryReport = 0;
      } else {
        dataForm.value.summaryReport = Number(data);
      }
      break;
    case GUIDANCE:
      if (!lstScore.includes(Number(data))) {
        dataForm.value.guidanceReport = 0;
      } else {
        dataForm.value.guidanceReport = Number(data);
      }
      break;
  }
};

const checkData = async (id: any) => {
  const childComponent = fileSignProfEvaluation.value;
  await childComponent.upLoadFile(id);
};

//interact with db
const handleActionSaveClick = () => {
  modalOpenConfirm.value = true;
};

const confirmAction = async () => {
  dataRequest.value.internProcessEvaluationSeq = dataForm.value.id ? dataForm.value.id : "";
  dataRequest.value.internPlanSeq = idPlan.value;
  dataRequest.value.internStdSeq = stdValue.value;
  dataRequest.value.workingDepartment = dataForm.value.internWorkDept;
  dataRequest.value.attendanceStatus = dataForm.value.attendanceStatus;
  dataRequest.value.weekReport = dataForm.value.weekReport;
  dataRequest.value.summaryReport = dataForm.value.summaryReport;
  dataRequest.value.guidanceReport = dataForm.value.guidanceReport;

  if (dataForm.value.isUpdate == "Y") {
    await store.updateForm({
      method: "PUT",
      body: dataRequest.value,
    });
    if (store.status?.value == SUCCSESS_STATUS) {
      checkData(store?.dataRes?.data);
    }
  } else {
    await store.createForm({
      method: "POST",
      body: dataRequest.value,
    });
    if (store.status?.value == SUCCSESS_STATUS) {
      dataForm.value.id = store.dataRes.data;
      dataForm.value.isUpdate = "Y";
      statusBtnPdf.value = !(dataForm.value.isUpdate === "Y");
      checkData(store?.dataRes?.data);
    }
  }
  modalOpenConfirm.value = false;
  modalOpenAlert.value = true;
};

watch(
  () => stdValue.value,
  (newValue) => {
    if (newValue == "") {
      categoryStatus.value = true;
      dataForm.value = {
        id: "",
        internPlanNm: "",
        deptStdNm: "",
        companyNm: "",
        internStartDate: "",
        internEndDate: "",
        stdNm: "",
        internWorkDept: "",
        attendanceStatus: 0,
        weekReport: 0,
        summaryReport: 0,
        guidanceReport: 0,
        totalCompanyScore: 0,
        deptProfNm: "",
        profNm: "",
        isUpdate: "N",
      } as EvaluationReportProfDetailRes;

      statusBtnConfirm.value = true;
      statusBtnPdf.value = true;
    } else {
      categoryStatus.value = false;
      statusBtnConfirm.value = false;
      getDetail();
    }
  },
);

watchEffect(() => {
  totalReview.value =
    Number(dataForm.value.attendanceStatus) +
    Number(dataForm.value.weekReport) +
    Number(dataForm.value.summaryReport) +
    Number(dataForm.value.guidanceReport);
  totalFinal.value = totalReview.value + Number(dataForm.value.totalCompanyScore);
  if (totalFinal.value >= 140) {
    totalCheckPass.value = totalFinal.value;
    totalCheckFail.value = 0;
  } else {
    totalCheckFail.value = totalFinal.value;
    totalCheckPass.value = 0;
  }
});
</script>

<style scoped lang="scss">
.instructor_evaluation {
  color: var(--Gray-scale-90, rgba(29, 29, 29, 1));

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

  &__content {
    margin-top: 20px;
    margin-bottom: 24px;

    & > h3 {
      font-size: 16px;
      font-weight: 500;
      line-height: 24px;
      position: relative;
      padding-left: 12px;
      margin-bottom: 8px;

      &:before {
        content: "";
        position: absolute;
        top: 50%;
        left: 0;
        transform: translateY(-50%);
        width: 4px;
        height: 4px;
        border-radius: 50%;
        background: var(--Primary-50_Primary, rgba(0, 152, 229, 1));
      }
    }

    &__table {
      border-top: 1px solid #2d2d2d;

      .table_row {
        display: flex;

        .table_col {
          width: 100%;
          display: flex;
          align-items: center;
          border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

          & > div:first-child {
            width: 200px;
            background: var(--Gray-scale-5, rgba(248, 248, 248, 1));
            padding: 17.5px 16px;

            & p {
              font-size: 14px;
              font-weight: 500;
              line-height: 21px;
              color: var(--Gray-scale-90, rgba(29, 29, 29, 1));
            }
          }

          & > div:last-child {
            padding: 8px 16px;
            //word-break: break-word;
            overflow-wrap: break-word;
            flex: 1;

            & p {
              font-size: 14px;
              font-weight: 400;
              line-height: 21px;
              color: var(--Gray-scale-80, rgba(45, 45, 45, 1));
            }
          }
        }
      }
    }

    &__table1 {
      border-top: 1px solid #2d2d2d;
      border-bottom: 1px solid #2d2d2d;

      .table_row__mobile {
        display: none;
      }

      .table_row {
        display: flex;
        border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

        &__mobile {
          display: none;
        }

        &:nth-child(2) {
          background: var(--Gray-scale-5, rgba(248, 248, 248, 1)) !important;
        }

        &:nth-last-child(1) {
          .table_row__head:nth-child(1) {
            flex: 9;
            background: var(--Gray-scale-5, rgba(248, 248, 248, 1));
          }

          .table_row__head:nth-child(2) {
            flex: 3;
          }
        }

        p {
          font-size: 14px;
          font-weight: 500;
          line-height: 21px;
          padding: 9.5px 0;
        }

        &__head {
          display: flex;
          justify-content: center;
          align-items: center;
          border-right: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

          &:nth-child(1),
          &:nth-child(2) {
            flex: 2;
          }

          &:nth-child(3) {
            flex: 5;
            display: flex;
            flex-direction: column;

            & > div:nth-child(1) {
              border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));
            }

            & > div:nth-child(2) {
              display: flex;
              width: 100%;

              & p {
                flex: 1;
                white-space: nowrap;
                text-align: center;
              }
            }
          }

          &:nth-child(4) {
            flex: 3;
          }

          &:nth-last-child(1) {
            border-right: none;
            padding: 8px 16px;

            > div {
              width: 100%;
            }
          }
        }
      }
    }

    &:nth-last-child(1) {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      gap: 24px;
      white-space: normal;

      & > p:first-child {
        font-size: 18px;
        font-weight: 500;
        line-height: 27px;
        color: var(--Gray-scale-80, rgba(45, 45, 45, 1));
        text-align: center;
      }

      & > p:last-child {
        font-size: 16px;
        font-weight: 500;
        line-height: 24px;
        color: var(--Gray-scale-70, rgba(85, 85, 85, 1));
        text-align: center;
      }
    }

    .group_file__content {
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
      align-items: center;
      gap: 24px;

      & p {
        font-size: 16px;
        font-weight: 500;
        line-height: 24px;
        color: var(--Gray-scale-70, rgba(85, 85, 85, 1));
      }

      .add_photo {
        background: red;
      }
    }

    &__table2 {
      display: flex;
      justify-content: center;

      .table_row {
        flex: 1;

        &:nth-child(1) {
          flex: 3;
        }

        &:nth-child(2) {
          flex: 2;
        }

        &__head {
          display: flex;
          justify-content: center;
          box-sizing: border-box;

          & > div {
            flex: 1;
            text-align: center;
          }

          &:nth-child(1) {
            border-top: 1px solid var(--Gray-scale-80, rgba(45, 45, 45, 1));
            background: var(--Gray-scale-5, rgba(248, 248, 248, 1));
            border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));
            padding: 9.5px;

            p {
              font-size: 14px;
              font-weight: 500;
              line-height: 21px;
            }
          }

          &:nth-child(2) {
            .table_row__head__content {
              display: flex;
              flex-direction: column;

              & > div:first-child {
                flex: 1;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                gap: 2px;
                background: var(--Gray-scale-5, rgba(248, 248, 248, 1));
                padding: 8px 0;
                border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

                & p {
                  font-size: 12px;
                  font-weight: 500;
                  line-height: 18px;
                  letter-spacing: -0.01em;
                  color: var(--Gray-scale-60, rgba(113, 113, 113, 1));

                  &:first-child {
                    font-size: 14px;
                    font-weight: 500;
                    line-height: 21px;
                    color: var(--Gray-scale-90, rgba(29, 29, 29, 1));
                  }
                }
              }

              & > div:last-child {
                padding: 17.5px 0;
                border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

                p {
                  font-size: 14px;
                  font-weight: 400;
                  line-height: 21px;
                  letter-spacing: -0.02em;
                  color: var(--Gray-scale-80, rgba(45, 45, 45, 1));
                }
              }
            }
          }
        }
      }
    }
  }
}
.input_envaltion_print{
  display: none;
  text-align: center;
}
@media screen and (max-width: 768px) {
  .instructor_evaluation {
    &__title {
      & > h2 {
        font-size: 18px;
        font-weight: 500;
        line-height: 27px;
      }

      &__right {
        display: flex;
        align-items: center;
        gap: 8px;
        width: 100%;

        .right_select_box {
          width: 100%;
        }
        & > div:first-child {
          flex: 2;
          width: 100%;
        }
        & > div:last-child {
          flex: 3;
          width: 100%;
        }
      }
    }

    &__content {
      .table_row {
        flex-direction: column;
      }

      &__table1 {
        border-top: none;
        border-bottom: none;

        .table_row__mobile {
          padding: 16px;
          border-radius: 8px;
          background: var(--Gray-scale-5, rgba(248, 248, 248, 1));
          margin-bottom: 20px;
          display: flex;
          flex-direction: column;
          gap: 4px;

          p {
            font-size: 14px;
            font-weight: 500;
            line-height: 21px;
          }
        }

        .table_row {
          display: flex;
          border-bottom: none;
          margin-top: 12px;

          &:nth-child(1) {
            display: block;
          }

          &:nth-child(2) {
            display: none;
          }

          p {
            font-size: 14px;
            font-weight: 500;
            line-height: 21px;
            padding: 0;
          }

          &__head {
            display: flex;
            justify-content: center;
            border-bottom: none;
            border-right: none;

            &:nth-child(1) {
              flex: 2;
            }

            &:nth-last-child(1) {
              border-right: none;
              padding: 8px 0px;

              > div {
                width: 100%;
              }
            }
          }

          &:nth-last-child(1) {
            border-top: 1px solid var(--Gray-scale-80, rgba(45, 45, 45, 1));
            border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

            .table_row__head {
              p {
                padding: 9.5px;
              }
            }

            .table_row__head:nth-last-child(1) {
              padding: 0;
            }
          }

          &:not(:nth-last-child(1)) {
            .table_row__head {
              justify-content: flex-start;
              border-right: none;

              &:nth-child(2),
              &:nth-child(3) {
                display: none;
              }
            }
          }
        }
      }

      &__table2 {
        flex-direction: column;
      }
    }
  }
}

@media screen and (max-width: 480px) {
  .instructor_evaluation {
    &__title {
      display: flex;
      align-items: flex-start;
      flex-direction: column-reverse;

      & > h2 {
        font-size: 18px;
        font-weight: 500;
        line-height: 27px;
      }

      &__right {
        flex-direction: column;
      }
    }

    &__content {
      &__table {
        .table_row {
          border-bottom: none;

          .table_col {
            flex-direction: column;
            align-items: flex-start;
            border-top: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

            & > div {
              width: 100% !important;
            }

            & > div.table_col__text {
              padding: 18px 16px;

              & p {
                font-size: 14px;
                font-weight: 400;
                line-height: 20px;
              }
            }
          }
        }
      }

      &__table2 {
        flex-direction: column;

        .table_row {
          flex: 1;

          &__head {
            display: flex;
            flex-direction: column;
            text-align: center;

            &:nth-child(2) {
              .table_row__head__content {
                display: flex;
                flex-direction: column;

                & > div:first-child {
                  padding: 9.5px 0;
                  flex-direction: row;
                }

                & > div:last-child {
                  padding: 9.5px 0;
                }
              }
            }
          }
        }
      }
    }
  }
}
@media print {
  .exist_one_print {
    padding: 10px;
  }
  .input_envaltion_not_print{
    display: none;
  }
  .input_envaltion_print{
    display: block;
  }
  .envaltion_top{
    margin-top: 160px
  }
}
</style>
