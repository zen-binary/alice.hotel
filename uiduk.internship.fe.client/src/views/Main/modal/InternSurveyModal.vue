<template>
  <TModal
    :class-size="`large`"
    :is-open="isOpen"
    :is-show-footer="true"
    :is-show-header="true"
    :modal-id="modalId"
    @close-modal="closeModal"
  >
    <template #title>
      <strong>{{ t("13.studentSurvey.titleHeader") }}</strong>
    </template>
    <template #default>
      <div
        id="std-survey"
        class="survey-block"
      >
        <div class="survey-block__field-training">
          <div class="title">
            {{ t("13.studentSurvey.titleForm") }}
          </div>
          <div class="dowload btn_group">
            <ButtonExportPDFV1
              id="std-survey"
              :name-export="nameExportPDF"
            />
          </div>
        </div>
        <div class="survey-block__option-field">
          <div class="item">
            <div class="label">
              {{ t("13.studentSurvey.nameUser") }}
            </div>
            <div>{{ internStdSurveyDetailReqDTO.studentName }}</div>
          </div>
          <div class="item">
            <div class="label">
              {{ t("13.studentSurvey.grade") }}
            </div>
            <div class="option">
              <RadiobuttonBase
                v-for="item in listRadioGrade"
                :checked="item.cdId == internStdSurveyDetailReqDTO.shyrCode"
                :is-disable="true"
                :label="item.cdNm"
                :name="item.cdId"
              />
            </div>
          </div>
          <div class="item">
            <div class="label">
              {{ t("13.studentSurvey.period") }}
            </div>
            <div class="option">
              <RadiobuttonBase
                v-for="item in listRadioPeriod"
                :checked="item.cdId == internStdSurveyDetailReqDTO?.internTerm"
                :is-disable="true"
                :label="item.cdNm"
                :name="item.cdId"
              />
            </div>
          </div>
        </div>
        <div class="survey-block__table">
          <div class="survey-container">
            <div class="survey-header">
              <div class="survey-item">
                {{ t("13.studentSurvey.col1") }}
              </div>
              <div class="survey-scale">
                <div class="survey-score">
                  {{ t("13.studentSurvey.col2") }}
                </div>
                <div class="d-flex">
                  <div class="scale-item">
                    {{ t("13.studentSurvey.col2-1") }}
                  </div>
                  <div class="scale-item">
                    {{ t("13.studentSurvey.col2-2") }}
                  </div>
                  <div class="scale-item">
                    {{ t("13.studentSurvey.col2-3") }}
                  </div>
                  <div class="scale-item">
                    {{ t("13.studentSurvey.col2-4") }}
                  </div>
                  <div class="scale-item">
                    {{ t("13.studentSurvey.col2-5") }}
                  </div>
                </div>
              </div>
            </div>
            <div
              v-for="(question, index) in internStdSurveyDetailReqDTO.stdSurveyAnsDTOList"
              :key="index"
              class="survey-question"
            >
              <div class="question-item">
                {{ question.qusContent }}
              </div>
              <div class="outline-score">
                <div
                  v-for="item in listRadioQuestionOne"
                  :key="item.value"
                  class="question-score"
                >
                  <RadiobuttonBase
                    :id="`question-${index}-${item.value}`"
                    v-model="question.score"
                    :checked="question.score !== undefined && item.value === question.score"
                    :label="item.label"
                    :name="`question-${index}`"
                    :value="item.value"
                    @click="handleRadioChange(index, item.value)"
                  />
                </div>
              </div>
            </div>

            <div class="survey-question">
              <div class="question-item question-total">
                {{ t("13.studentSurvey.row") }}
              </div>
              <div class="outline-score">
                <div class="question-score question-score-total">
                  <span class="total">
                    {{ calculatedAverageScore != 0 ? calculatedAverageScore : "" }}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="survey-block__description">
          <div class="survey-block__description__title">
            {{ t("13.studentSurvey.titleArea") }}
          </div>
          <div class="survey-block__description__body">
            <TextArea
              id="text_validate"
              v-model="internStdSurveyDetailReqDTO.internRecommend"
              :value="internStdSurveyDetailReqDTO.internRecommend"
              placeholder="내용을 입력해 주세요. (최대 500자 제한)"
              :max-words="500"
              :is-show-count-word="true"
              style="max-width: 90%;"
              required
            />
          </div>
        </div>
      </div>
    </template>
    <template #footer>
      <button
        class="button btn_xl btn_primary"
        type="button"
        @click="saveSurvey"
      >
        {{ t("common.save") }}
      </button>
    </template>
  </TModal>
  <TModalConfirm
    :is-open="modalOpenConfirm"
    :is-show-footer="true"
    :is-show-header="true"
    :modal-id="modalIdConfirm"
    @close-modal="closeModalConfirm"
  >
    <!-- Modal content goes here -->
    <template #title>
      <strong />
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ t("13.studentSurvey.contentConfirm") }}</p>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button
        class="button btn_md btn_primary"
        type="button"
        @click="confirmAction"
      >
        {{ t("common.confirm") }}
      </button>
      <button
        class="button btn_md btn_white popup_close"
        type="button"
        @click="closeModalConfirm"
      >
        {{ t("common.cancel") }}
      </button>
    </template>
  </TModalConfirm>
  <TModalAlert
    :is-open="modalOpenAlert"
    :is-show-footer="true"
    :is-show-header="true"
    :modal-id="modalIdAlert"
    @close-modal="closeModal"
  >
    <!-- Modal content goes here -->
    <template #title>
      <strong />
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ contentAlert }}</p>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button
        class="button btn_md btn_primary"
        type="button"
        @click="alertAction"
      >
        {{ t("common.confirm") }}
      </button>
    </template>
  </TModalAlert>
</template>
<script lang="ts" setup>
import { defineExpose, ref } from "vue";
import { useI18n } from "vue-i18n";
import TModalConfirm from "@/components/common/excel/TModalConfirm.vue";
import TModalAlert from "@/components/common/excel/TModalAlert.vue";
import { stdSurveyStore } from "@/stores/studentSurvey";
import { codeMngStore } from "@/stores/common/codeMng";
import CodeMngModel from "@/model/common/CodeMngModel";
import {
  InternStdSurveyCreModel,
  InternStdSurveyDetailReqDTO,
  ListStdSurveyAnsCreModel,
} from "@/stores/studentSurvey/studentSurvey.type";

const isOpen = ref<boolean>(false);
const key = ref<number>(0);
const { t } = useI18n();
const internPlanSeq = ref("");
const modalId = ref<string>("surveyModalId");
const modalIdConfirm = ref<string>("modalIdConfirm");
const modalIdAlert = ref<string>("modalIdAlert");

const modalOpenConfirm = ref<boolean>(false);
const modalOpenAlert = ref<boolean>(false);
const contentAlert = ref<string>("");

const valueAns = ref<Array<number>>([]);
const radioQuestionValues = ref();

const internStdSurveyCreModel = ref<InternStdSurveyCreModel>({} as InternStdSurveyCreModel);
const listStdSurveyAnsCreModel = ref<ListStdSurveyAnsCreModel>({} as ListStdSurveyAnsCreModel);
const internStdSurveyDetailReqDTO = ref<InternStdSurveyDetailReqDTO>({} as InternStdSurveyDetailReqDTO);

const calculatedAverageScore = ref<number>(0);

const nameExportPDF = `student_survey`;

const internStdSurveySeq = ref<string>("");

const detailSurveyAns = ref();

const listRadioQuestionOne = ref([
  { value: "5", label: "5" },
  { value: "4", label: "4" },
  { value: "3", label: "3" },
  { value: "2", label: "2" },
  { value: "1", label: "1" },
]);

const stdStore = stdSurveyStore();

const codeMng = codeMngStore();
const listRadioPeriod = ref<CodeMngModel[]>([]);
const listRadioGrade = ref<CodeMngModel[]>([]);

const handleRadioChange = (index, value) => {
  radioQuestionValues.value[index].score = value;
};

const saveSurvey = async () => {  
  const allScoresFilled = radioQuestionValues.value.every((item) => item.score !== null);

  // key pass validate
  key.value = 0;
  if (!allScoresFilled && detailSurveyAns.value.length != 10) {
    contentAlert.value = t("13.studentSurvey.contentAlert1");
    key.value++;
    modalOpenAlert.value = true;
  } else if (
    internStdSurveyDetailReqDTO.value.internRecommend === "" ||
    internStdSurveyDetailReqDTO.value.internRecommend === undefined
  ) {
    contentAlert.value = t("13.studentSurvey.contentAlert2");
    key.value++;
    modalOpenAlert.value = true;
  } else {
    contentAlert.value = t("13.studentSurvey.contentAlert3");
    modalOpenConfirm.value = true;
  }
};

const closeModalConfirm = () => {
  modalOpenConfirm.value = false;
};

const confirmAction = async () => {
  modalOpenConfirm.value = false
  await handleUpdateOrSave();
  modalOpenAlert.value = true;
};

const handleUpdateOrSave = async () => {
  internStdSurveyCreModel.value.internStdSurveySeq = internStdSurveyDetailReqDTO.value.internStdSurveySeq;
  internStdSurveyCreModel.value.internPlanSeq = internStdSurveyDetailReqDTO.value?.internPlanSeq;
  internStdSurveyCreModel.value.internStdSeq = internStdSurveyDetailReqDTO.value?.internStdSeq;
  internStdSurveyCreModel.value.internRecommend = internStdSurveyDetailReqDTO.value.internRecommend;

  if (key.value == 0) {
    if (detailSurveyAns.value.internStdSurveySeq) {
      // update
      await stdStore.updateInternStdSurvey({
        method: "POST",
        body: internStdSurveyCreModel.value,
      });
      listStdSurveyAnsCreModel.value.listAnsSurvey = detailSurveyAns.value.stdSurveyAnsDTOList.map((question) => ({
        stdSurveyAnsSeq: question.stdSurveyAnsSeq,
        score: question.score,
        stdSurveyQusSeq: question.stdSurveyQusSeq,
        internStdSurveySeq: internStdSurveyCreModel.value.internStdSurveySeq,
      }));

      for (let i = 0; i < detailSurveyAns.value.length; i++) {
        listStdSurveyAnsCreModel.value.listAnsSurvey[i].score = valueAns.value[i];
      }

      await stdStore.updateListSurveyAns({
        method: "POST",
        body: listStdSurveyAnsCreModel.value,
      });
    } else {
      // save new
      await stdStore.saveInternStdSurvey({
        method: "POST",
        body: internStdSurveyCreModel.value,
      });
      internStdSurveySeq.value = stdStore.list.data;

      listStdSurveyAnsCreModel.value.listAnsSurvey = radioQuestionValues.value.map((question) => ({
        score: question.score,
        stdSurveyQusSeq: question.stdSurveyQusSeq,
        internStdSurveySeq: internStdSurveySeq.value,
      }));

      await stdStore.saveListSurveyAns({
        method: "POST",
        body: listStdSurveyAnsCreModel.value,
      });
    }
  }
};

const alertAction = async () => {
  modalOpenAlert.value = false;
  isOpen.value = !(key.value == 0);
};

const openModal = async (internDetailId: string) => {
  // Get info student login
  internPlanSeq.value = internDetailId;
  await stdStore.getStdSurveyDetail({
    method: "POST",
    body: {
      internPlanSeq: internPlanSeq.value
    },
  });
  internStdSurveyDetailReqDTO.value = stdStore.list.data;  
  radioQuestionValues.value = stdStore.list.data.stdSurveyAnsDTOList.map((question) => ({
    score: question.score,
    stdSurveyQusSeq: question.stdSurveyQusSeq,
    qusContent: question.qusContent,
    displayOrder: question.displayOrder,
  }));
  detailSurveyAns.value = stdStore.list.data;

  const totalScore = detailSurveyAns.value.stdSurveyAnsDTOList.reduce((sum, item) => {
    return sum + Number(item.score);
  }, 0);
  calculatedAverageScore.value = Number((totalScore / detailSurveyAns.value.stdSurveyAnsDTOList.length).toFixed(2));
  // Get code Period
  await codeMng.getCodeInfo({
    method: "POST",
    body: {
      upCdIdList: ["NCR_T11"],
    },
  });
  listRadioPeriod.value = codeMng.codeInfo.data;

  // Get code Grade
  await codeMng.getCodeInfo({
    method: "POST",
    body: {
      upCdIdList: ["SHYR_0"],
    },
  });
  listRadioGrade.value = codeMng.codeInfo.data;
  isOpen.value = true;
};


const closeModal = async () => {
  isOpen.value = false;
};

defineExpose({
  openModal,
});
</script>
<style lang="scss" scoped>
.block-center-50 {
  width: 50%;
  padding: 10px 0;
}

.survey-block {
  margin-bottom: 32px;

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

    &__dowload {
      font-size: 14px;
      font-weight: 500;
      @media screen and (max-width: 768px) {
        font-size: 12px;
      }
    }
  }

  &__option-field {
    margin: 24px 0;
    background: var(--gray--f8);
    border-radius: 5px;
    padding: 16px;
    @media screen and (max-width: 768px) {
    }

    .item {
      display: flex;
      align-items: center;
      margin-bottom: 20px;
      @media screen and (max-width: 768px) {
        &:first-child {
          flex-direction: row;
          white-space: nowrap;
          margin: 0 0 12px 0;
          gap: 10px;
        }
        &:last-child {
          margin: 0;
        }
        align-items: start;
        gap: 18px;
        width: fit-content;
        flex-direction: column;
        margin-bottom: 17px;
      }

      .label {
        width: 100%;
        max-width: 110px;
        font-weight: 500;
        @media screen and (max-width: 768px) {
          width: fit-content;
        }
      }

      .option {
        display: flex;
        gap: 10px;
      }
    }
  }

  &__table {
    .survey-container {
      display: flex;
      flex-direction: column;
      border-top: 2px solid var(--black);
      border-bottom: 2px solid var(--black);
      width: 100%;
      @media screen and (max-width: 768px) {
        border: none;
      }
    }

    .survey-header {
      display: flex;
      justify-content: center;
      align-items: center;
      background: var(--gray--f8);

      border-bottom: 1px solid var(--gray--e4);
      @media screen and (max-width: 768px) {
        display: none;
      }
    }

    .survey-item {
      flex: 0 0 50%;
      text-align: center;
      height: 100%;
      padding: 30px 0;
      border-right: 1px solid var(--gray--e4);
      color: var(--gray--1d);
      font-weight: 500;
    }

    .survey-score {
      flex: 1;
      text-align: center;
      padding: 15px 0;
      border-bottom: 1px solid var(--gray--e4);
      color: var(--gray--1d);
      font-weight: 500;
    }

    .survey-scale {
      display: flex;
      flex-direction: column;
      flex: 0 0 50%;
    }

    .scale-item {
      padding: 5px;
      flex: 1;
      text-align: center;
      color: var(--gray--1d);
      font-weight: 500;
    }

    .survey-question {
      display: flex;
      align-items: center;
      border-bottom: 1px solid var(--gray--e4);
      @media screen and (max-width: 768px) {
        border-bottom: none;
        align-items: flex-start;
        flex-direction: column;
      }

      .question-item {
        text-align: center;
        flex: 0 0 50%;
        padding: 16px;
        border-right: 1px solid var(--gray--e4);
        @media screen and (max-width: 768px) {
          border-right: none;
          padding: 16px 0;
        }
      }

      .question-total {
        background: var(--gray--f8);
        text-align: center;
        @media screen and (max-width: 768px) {
          margin-top: 20px;
          width: 100%;
          border-top: 2px solid var(--black);
        }
      }

      .outline-score {
        width: 100%;
        display: flex;

        .question-score-total {
          @media screen and (max-width: 768px) {
            border-top: 1px solid var(--gray--e4);
            border-bottom: 1px solid var(--gray--e4);
            padding: 10px;
          }
        }

        .question-score {
          flex: 1;
          text-align: center;

          .radio_row {
            justify-content: space-evenly;
            @media screen and (max-width: 768px) {
              justify-content: flex-start;
            }
          }

          .total {
            font-size: 16px;
            font-weight: 500;
            color: var(--gray--1d);
          }
        }
      }
    }
  }

  &__description {
    margin-top: 24px;

    &__title {
      color: var(--gray--1d);
      font-size: 16px;

      font-weight: 500;
      position: relative;
      margin-left: 15px;
      margin-bottom: 8px;
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
      }
    }

    &__body {
      textarea {
        color: var(--gray--1d);
        border-radius: 5px;
        font-size: 14px;
        border: 1px solid var(--gray--e4);
      }
    }
  }
}
</style>
