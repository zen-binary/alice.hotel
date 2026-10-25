<script setup lang="ts">
import { FILE_TYPE_IMAGE } from "@/constants/screen.const";
import { CodeMngModel } from "@/stores/common/codeMng/codeMng.type";
import { evaluationCenterStore } from "@/stores/evaluationCenter/useEvaluationCenter";
import { EvaluationStdModel, EvaluationTableDetailModel, StudentInfoModel } from "@/stores/evaluationCenter/evaluationCenter.type";
import { ref, defineExpose } from "vue";

const modalId = ref("evaluationTableId");

const props = defineProps<{
  internPlanSeq: string;
  isOpen: boolean;
  handleCancel: () => void;
}>();

const modalEvaluation = evaluationCenterStore();
const { t } = useI18n();
const evaluationTableDetail = ref<EvaluationTableDetailModel>({ internPlanSeq: props.internPlanSeq, stdNo: "" });
const evaluationModel = ref<EvaluationStdModel>();
const listStudentInfo = ref<CodeMngModel[]>([]);
const listStudentFullInfo = ref<StudentInfoModel[]>([]);
const studentInfoSelected = ref<StudentInfoModel>();
const totalScoreOverall = ref(0);
const createListPoints = (points: string[]): CodeMngModel[] => {
  return points.map((point) => ({ cdId: point, cdNm: point, upCdId: "" }));
};
const fileTypeImg = FILE_TYPE_IMAGE;
const listPointAbility = ref<CodeMngModel[]>(createListPoints(["10", "8", "6", "4", "2"]));
const listPointAttitudeImplementation = ref<CodeMngModel[]>(createListPoints(["5", "4", "3", "2", "1"]));
const listPointDiligenceAttitude = ref<CodeMngModel[]>(createListPoints(["20", "16", "12", "8", "4"]));

async function getDataEvaluation() {
  await modalEvaluation.getEvaluationStd({ method: "POST", body: evaluationTableDetail.value });
  evaluationModel.value = modalEvaluation.resultData.data;

  listStudentFullInfo.value = evaluationModel.value?.listStudentInfo ?? [];
  listStudentInfo.value = [
    { cdId: "", cdNm: t("common.selectedStd"), upCdId: "" },
    ...listStudentFullInfo.value.map((item) => ({
      cdId: item.stdNo,
      cdNm: item.korNm,
      upCdId: "",
    })),
  ];

  if (evaluationModel.value?.listEvaluationCriteria) {
    evaluationModel.value.listEvaluationCriteria.forEach((item) => {
      item.criteriaChild.forEach((child) => {
        child.score = child.score !== null && child.score !== undefined && child.score !== "" ? String(child.score) : null;
      });
    });
  }

  if (!studentInfoSelected.value) {
    evaluationModel.value.listEvaluationCriteria = [];
  }
}

watch(
  () => evaluationModel.value?.listEvaluationCriteria,
  (newCriteriaList) => {
    if (newCriteriaList) {
      let overallTotal = 0;
      newCriteriaList.forEach((parent) => {
        const totalScore = parent.criteriaChild.reduce((sum, child) => sum + Number(child.score), 0);
        parent.scoreTotal = totalScore;
        overallTotal += totalScore;
      });
      totalScoreOverall.value = overallTotal;
    }
  },
  { deep: true },
);

watch(
  () => props.isOpen,
  (newVal) => {
    if (newVal) {
      evaluationTableDetail.value = { internPlanSeq: "", stdNo: "" };
      evaluationTableDetail.value.internPlanSeq = props.internPlanSeq;
      getDataEvaluation();
    } else {
      evaluationModel.value = null;
      evaluationTableDetail.value = { internPlanSeq: "", stdNo: "" };
      studentInfoSelected.value = null;
      listStudentInfo.value = [];
      listStudentFullInfo.value = [];
    }
  },
);

function changeStudent(data: string) {
  if (data) {
    evaluationTableDetail.value.stdNo = data;
    studentInfoSelected.value = listStudentFullInfo.value.find((student) => student.stdNo === data);
    getDataEvaluation();
  } else {
    evaluationModel.value = {
      evaluationFeedback: "",
      evaluationStdSeq: null,
      evaluationSubmitDate: null,
      internStdSeq: null,
      studentFeedback: "",
      listEvaluationCriteria: [],
      internshipEvaluatorDto: evaluationModel.value?.internshipEvaluatorDto,
      listStudentInfo: evaluationModel.value?.listStudentInfo,
    };
    studentInfoSelected.value = null;
  }
}

const isModalConfirmSave = ref(false);

function confirmSave() {
  isModalConfirmSave.value = true;
}

function closeModalConfirmSave() {
  isModalConfirmSave.value = false;
}

const isCheckRequired = computed(() => {
  if (!evaluationModel.value) {
    return true;
  }
  let requiredAllScore = false;
  for (const parent of evaluationModel.value.listEvaluationCriteria) {
    for (const child of parent.criteriaChild) {
      if (!child.score || child.score.trim() === "") {
        requiredAllScore = true;
        break;
      }
    }
    if (requiredAllScore) break;
  }
  const feedbackMissing = !evaluationModel.value.evaluationFeedback.trim() || !evaluationModel.value.studentFeedback.trim();
  return requiredAllScore || feedbackMissing;
});

const isModalAlertSuccessSave = ref(false);

function closeModalSuccessSave() {
  isModalAlertSuccessSave.value = false;
}

const saveData = async () => {
  const {
    evaluationStdSeq,
    internStdSeq,
    evaluationFeedback,
    studentFeedback,
    evaluationSubmitDate,
    listEvaluationCriteria = [],
  } = evaluationModel.value || {};

  const listStdEvaluation = listEvaluationCriteria.flatMap(({ criteriaChild = [] }) =>
    criteriaChild.map((child) => ({
      studentEvaluationSeq: child.studentEvaluationSeq,
      evaluationCriteriaSeq: child.evaluationCriteriaSeq,
      score: Number(child.score),
    })),
  );

  const obj = {
    evaluationStdSeq,
    internStdSeq,
    evaluationFeedback,
    studentFeedback,
    evaluationSubmitDate,
    listStdEvaluation,
  };

  await modalEvaluation.saveDataEvaluation({ method: "POST", body: obj });
  closeModalConfirmSave();
  getDataEvaluation();
  isModalAlertSuccessSave.value = true;
};

defineExpose({});
</script>

<template>
  <TModal :classSize="`xlarge`" :is-open="isOpen" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="handleCancel">
    <template #title>
      <strong>{{ t("evaluationTableCenter.modalTitle") }}</strong>
    </template>
    <template #default>
      <div id="evaluationContainer" class="evaluation-container">
        <div class="evaluation-container__form">
          <div class="option">
            <div class="title-block mb-0">{{ t("evaluationTableCenter.labelSelect") }}</div>
            <div>
              <SelectBoxBase id="listStudentInfo" :data="listStudentInfo" v-model="evaluationTableDetail.stdNo" @change="changeStudent" />
            </div>
          </div>
          <div class="option">
            <button type="button" :disabled="!studentInfoSelected || isCheckRequired" class="button btn_white hidden_print" @click="confirmSave">
              {{ t("common.save") }}
            </button>
            <ButtonExportPDFV1 id="evaluationContainer" :nameExport="t('evaluationTableCenter.btnExportPdf')" />
          </div>
        </div>
        <div class="evaluation-container__header">
          <h2 class="title-block">{{ t("evaluationTableCenter.title1") }}</h2>
        </div>
        <div class="evaluation-container__student-info">
          <div class="title-block">{{ t("evaluationTableCenter.title2") }}</div>
          <div class="content-block">
            <div class="item">
              <div class="label">{{ t("evaluationTableCenter.title3") }}</div>
              <div class="value">{{ studentInfoSelected ? studentInfoSelected?.colgCdNm : "" }}</div>
            </div>
            <div class="item">
              <div class="label">{{ t("evaluationTableCenter.title4") }}</div>
              <div class="value">{{ studentInfoSelected ? studentInfoSelected?.cormNameKr : "" }}</div>
            </div>
            <div class="item">
              <div class="label">{{ t("evaluationTableCenter.title5") }}</div>
              <div class="value">
                {{ studentInfoSelected ? studentInfoSelected?.internshipStr + " ~ " + studentInfoSelected?.internshipEnd : "" }}
              </div>
            </div>
            <div class="item">
              <div class="label">{{ t("evaluationTableCenter.title6") }}</div>
              <div class="value">{{ studentInfoSelected ? studentInfoSelected?.korNm : "" }}</div>
            </div>
            <div class="item">
              <div class="label">{{ t("evaluationTableCenter.title7") }}</div>
              <div class="value">{{ studentInfoSelected ? studentInfoSelected?.stdNo : "" }}</div>
            </div>
            <div class="item">
              <div class="label">{{ t("evaluationTableCenter.title8") }}</div>
              <div class="value">
                {{
                  studentInfoSelected ? studentInfoSelected?.sustCdNm + "/" + (studentInfoSelected?.mjCdNm ? studentInfoSelected?.mjCdNm : "") : ""
                }}
              </div>
            </div>
          </div>
        </div>
        <div class="evaluation-container__table">
          <div class="title-block">{{ t("evaluationTableCenter.title9") }}</div>
          <div class="tbl tbl_col tbl_scroll scrollx_tbl_lg scrolly_tbl_xxl" style="overflow-x: auto">
            <table>
              <thead>
                <tr>
                  <th class="text-bold" rowspan="2" style="width: 15%">{{ t("evaluationTableCenter.title10") }}</th>
                  <th class="text-bold" rowspan="2" style="width: 30%">{{ t("evaluationTableCenter.title11") }}</th>
                  <th class="text-bold" colspan="5">{{ t("evaluationTableCenter.title12") }}</th>
                  <th class="text-bold" rowspan="2" style="width: 15%">{{ t("evaluationTableCenter.title13") }}</th>
                </tr>
                <tr>
                  <th class="text-bold">{{ t("evaluationTableCenter.title14") }}</th>
                  <th class="text-bold">{{ t("evaluationTableCenter.title15") }}</th>
                  <th class="text-bold">{{ t("evaluationTableCenter.title16") }}</th>
                  <th class="text-bold">{{ t("evaluationTableCenter.title17") }}</th>
                  <th class="text-bold">{{ t("evaluationTableCenter.title18") }}</th>
                </tr>
              </thead>
              <tbody v-if="evaluationModel && evaluationModel?.listEvaluationCriteria">
                <template v-for="(cfd, indexCfd) in evaluationModel?.listEvaluationCriteria" :key="cfd.criteriaField">
                  <template v-for="(child, indexChild) in cfd.criteriaChild" :key="child.evaluationCriteriaSeq">
                    <tr>
                      <td class="bg-ligh1 text-bold" :rowspan="cfd.criteriaChild.length" v-if="indexChild === 0">{{ cfd.criteriaField }}</td>
                      <td>{{ child.criteriaCategory }}</td>
                      <template v-if="indexCfd === 0" v-for="pointAbility in listPointAbility" :key="pointAbility.cdId">
                        <td>
                          <RadiobuttonBase
                            :id="'pointAbility' + pointAbility.cdId + child.evaluationCriteriaSeq"
                            :value="pointAbility.cdId"
                            :name="'pointAbility' + child.evaluationCriteriaSeq"
                            :checked="pointAbility.cdId === child.score"
                            v-model="child.score"
                            :label="pointAbility.cdNm"
                          />
                        </td>
                      </template>
                      <template v-if="indexCfd === 1" v-for="pointImpl in listPointAttitudeImplementation" :key="pointImpl.cdId">
                        <td>
                          <RadiobuttonBase
                            :id="'pointImpl' + pointImpl.cdId + child.evaluationCriteriaSeq"
                            :value="pointImpl.cdId"
                            :name="'pointImpl' + child.evaluationCriteriaSeq"
                            :checked="pointImpl.cdId === child.score"
                            :label="pointImpl.cdNm"
                            v-model="child.score"
                          />
                        </td>
                      </template>
                      <template v-if="indexCfd === 2" v-for="pointDiligence in listPointDiligenceAttitude" :key="pointDiligence.cdId">
                        <td>
                          <RadiobuttonBase
                            :id="'pointDiligence' + pointDiligence.cdId + child.evaluationCriteriaSeq"
                            :value="pointDiligence.cdId"
                            :name="'pointDiligence' + child.evaluationCriteriaSeq"
                            :checked="pointDiligence.cdId === child.score"
                            :label="pointDiligence.cdNm"
                            v-model="child.score"
                          />
                        </td>
                      </template>
                      <td :rowspan="cfd.criteriaChild.length" v-if="indexChild === 0">
                        {{ cfd.scoreTotal ? cfd.scoreTotal : "" }} / {{ indexCfd === 0 ? 60 : indexCfd === 1 ? 20 : 20 }}
                      </td>
                    </tr>
                  </template>
                </template>
                <tr>
                  <td class="bg-ligh1 text-bold" rowspan="1" colspan="7" style="width: 15%">{{ t("evaluationTableCenter.title19") }}</td>
                  <td class="text-bold bg-total" rowspan="1" style="width: 15%">{{ totalScoreOverall ? totalScoreOverall : "-" }}/100</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="evaluation-container__comment">
          <div class="title-block">{{ t("evaluationTableCenter.title20") }}</div>
          <div class="body">
            <div class="label">{{ t("evaluationTableCenter.title21") }}</div>
            <div class="input" v-if="evaluationModel">
              <TextArea
                v-model="evaluationModel.evaluationFeedback"
                style="border-radius: 0"
                id="evaluationFeedback"
                required
                :max-words="300"
                :placeholder="t('evaluationTableCenter.title24')"
              />
            </div>
          </div>
        </div>
        <div class="evaluation-container__feedback">
          <div class="title-block">{{ t("evaluationTableCenter.title22") }}</div>
          <div class="body">
            <div class="label">{{ t("evaluationTableCenter.title23") }}</div>
            <div class="input" v-if="evaluationModel">
              <TextArea
                v-model="evaluationModel.studentFeedback"
                style="border-radius: 0"
                id="studentFeedback"
                :max-words="300"
                required
                :placeholder="t('evaluationTableCenter.title24')"
              />
            </div>
          </div>
        </div>
        <div class="evaluation-container__document">
          <div class="head">
            <div class="label">{{ t("evaluationTableCenter.title25") }}</div>
            <div class="value">{{ t("evaluationTableCenter.title26") }}</div>
          </div>
          <div class="body">
            <p>
              {{ t("evaluationTableCenter.title27") }}
              <br />
              {{ t("evaluationTableCenter.title28") }}
            </p>
            <p class="date">{{ evaluationModel ? evaluationModel.evaluationSubmitDate : "" }}</p>
          </div>
        </div>
        <div class="evaluation-container__reviewer">
          <div class="title-block">{{ t("evaluationTableCenter.title29") }}</div>
          <div class="content-block">
            <div class="item">
              <div class="label">{{ t("evaluationTableCenter.title30") }}</div>
              <div class="value">
                {{ evaluationModel && evaluationModel.internshipEvaluatorDto ? evaluationModel.internshipEvaluatorDto.cormNameKr : "" }}
              </div>
            </div>
            <div class="item">
              <div class="label">{{ t("evaluationTableCenter.title31") }}</div>
              <div class="value">
                {{ evaluationModel && evaluationModel.internshipEvaluatorDto ? evaluationModel.internshipEvaluatorDto.commDept : "" }}
              </div>
            </div>
            <div class="item">
              <div class="label">{{ t("evaluationTableCenter.title32") }}</div>
              <div class="value">
                {{ evaluationModel && evaluationModel.internshipEvaluatorDto ? evaluationModel.internshipEvaluatorDto.positionName : "" }}
              </div>
            </div>
            <div class="item">
              <div class="label">{{ t("evaluationTableCenter.title33") }}</div>
              <div class="value dp_flex" style="align-items: center; gap: 10px">
                {{ evaluationModel && evaluationModel.internshipEvaluatorDto ? evaluationModel.internshipEvaluatorDto.commNm : "" }}
                <InputFileBase
                  v-if="evaluationModel"
                  :mode="`detail`"
                  :id="`cormSign`"
                  :type="fileTypeImg"
                  :preview-flag="true"
                  :multiple="true"
                  :show-mess="false"
                  :orgName="'CORM_SIGN'"
                  :category="'img'"
                  :sectionName="'CORM_SIGN'"
                  ref="cormSignRef"
                  :referKey="evaluationModel.internshipEvaluatorDto.cormSeq"
                ></InputFileBase>
              </div>
            </div>
          </div>
        </div>
        <div class="evaluation-container__signature">
          <div class="title-block">{{ t("evaluationTableCenter.title34") }}</div>
        </div>
      </div>
    </template>
    <template #footer>
      <div></div>
    </template>
  </TModal>
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
  <TModalAlert :is-open="isModalAlertSuccessSave" :is-show-header="true" :is-show-footer="true">
    <template #default>
      <div class="box_section">
        <p>{{ t("common.message.saveSuccess") }}</p>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_md btn_primary" @click="closeModalSuccessSave">{{ t("common.confirm") }}</button>
    </template>
  </TModalAlert>
</template>

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
        }
        .label {
          flex: 1;
          border-right: 1px solid var(--Gray-scale-80, rgb(45, 45, 45));
          @media screen and (max-width: 1024px) {
            border-right: 0;
          }
          font-weight: 500;
          background: var(--Gray-scale-5, rgb(248, 248, 248));
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
          display: flex;
          align-items: center;
          justify-content: center;
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
