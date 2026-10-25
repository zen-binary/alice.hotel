<template>
  <TModal
    :class-size="`large`"
    :is-open="isOpen"
    :is-show-footer="true"
    :is-show-header="true"
    :modal-id="modalId"
    :key="modalKey"
    @close-modal="closeModal"
  >
    <template #title>
      <strong>{{ t("supportCenter.studentSurvey.titleHeader") }}</strong>
    </template>
    <template #default>
      <div id="std-survey" class="survey-block">
        <div class="survey-block__field-training">
          <div class="title">{{ t("supportCenter.studentSurvey.titleForm") }}</div>
          <div class="student_survey__title__right">
            <div>
              <div class="right_select_box">
                <SelectBoxBase id="selectbox_stdName" v-model="stdValue" name="selectbox_stdName" :data="listStdName" />
              </div>
              <ButtonExportPDFV1 class="btn_lg" id="std-survey" :name-export="nameExportPDF" :disabled="statusBtnPdf" />
            </div>
          </div>
        </div>

        <div class="survey-block__option-field">
          <div class="item">
            <!-- 성명 -->
            <div class="label">{{ t("supportCenter.studentSurvey.nameUser") }}</div>
            <div>{{ dataForm.stdNm }}</div>
          </div>

          <div class="item">
            <!-- 학년 -->
            <div class="label">{{ t("supportCenter.studentSurvey.grade") }}</div>
            <div class="option">
              <RadiobuttonBase
                v-for="item in listRadioGrade"
                :id="item.cdNm"
                :key="item.cdId"
                v-model="dataForm.shyrCd"
                :value="item.cdId"
                :name="item.cdNm"
                :checked="item.cdId == dataForm.shyrCd"
                :label="item.cdNm"
                :is-disable="true"
              />
            </div>
          </div>

          <div class="item">
            <!-- 현장실습 시기 -->
            <div class="label">{{ t("supportCenter.studentSurvey.period") }}</div>
            <div class="option">
              <RadiobuttonBase
                v-for="item in listRadioPeriod"
                :id="item.cdNm"
                :key="item.cdId"
                v-model="dataForm.termCd"
                :value="item.cdId"
                :name="item.cdNm"
                :checked="item.cdId == dataForm.termCd"
                :label="item.cdNm"
                :is-disable="true"
              />
            </div>
          </div>
        </div>

        <div class="survey-block__table">
          <div class="survey-container">
            <div class="survey-header">
              <div class="survey-item">{{ t("supportCenter.studentSurvey.surveyFeild") }}</div>
              <div class="survey-scale">
                <div class="survey-score">{{ t("supportCenter.studentSurvey.surveyScore") }}</div>
                <div class="d-flex">
                  <div class="scale-item">{{ t("supportCenter.studentSurvey.ofCourse") }}</div>
                  <div class="scale-item">{{ t("supportCenter.studentSurvey.yes") }}</div>
                  <div class="scale-item">{{ t("supportCenter.studentSurvey.average") }}</div>
                  <div class="scale-item">{{ t("supportCenter.studentSurvey.no") }}</div>
                  <div class="scale-item">{{ t("supportCenter.studentSurvey.notAtAll") }}</div>
                </div>
              </div>
            </div>

            <div v-for="(question, index) in listRadioQuestion" :key="index" class="survey-question">
              <div class="question-item">
                {{ question.questContent }}
              </div>
              <div class="outline-score">
                <div v-for="item in listRadioQuestionScore" :key="item.value" class="question-score">
                  <RadiobuttonBase
                    :id="`question-${index}-${item.value}`"
                    :checked="item.value === Number(listRadioQuestion[index].score)"
                    :label="item.label"
                    :name="`question-${index}`"
                    :value="item.value"
                    :is-disable="true"
                  />
                </div>
              </div>
            </div>

            <div class="survey-question">
              <div class="question-item question-total">{{ t("supportCenter.studentSurvey.totalScore") }}</div>
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
          <div class="survey-block__description__title">{{ t("supportCenter.studentSurvey.titleArea") }}</div>
          <div class="survey-block__description__body">
            <TextArea :readonly="true" v-model="dataForm.recommend" :value="dataForm.recommend" />
          </div>
        </div>
      </div>
    </template>
    <template #footer>
      <button class="button btn_md btn_white" type="button" @click="closeModal()">{{ t("common.close") }}</button>
    </template>
  </TModal>
</template>

<script lang="ts" setup>
import { defineExpose, ref, watch } from "vue";
import { useI18n } from "vue-i18n";
import { codeMngStore } from "@/stores/common/codeMng";
import CodeMngModel from "@/model/common/CodeMngModel";
import RadiobuttonBase from "@/components/common/input/RadiobuttonBase.vue";
import { UP_CD_STD_GRADE, UP_CD_STD_PERIOD } from "@/constants/common.const";
import { StudentSurveyQuestResModel, StudentSurveyResModel } from "@/stores/supportCenter/stdSurvey/studentSurvey.type";
import { studentSurveyStore } from "@/stores/supportCenter/stdSurvey/useStudentSurvey";

const { t } = useI18n();
const isOpen = ref<boolean>(false);
const statusBtnPdf = ref(true);
const modalId = ref<string>("surveyModalId");
const modalKey = ref(1);

const codeMng = codeMngStore();
const storeStdSurvey = studentSurveyStore();

const listStdName = ref([{ cdId: "", cdNm: t("common.selectedStd"), upCdId: "ALL" }]);
const listRadioGrade = ref<CodeMngModel[]>([]);
const listRadioPeriod = ref<CodeMngModel[]>([]);
const listRadioQuestion = ref<StudentSurveyQuestResModel[]>([]);
const listRadioQuestionScore = ref([
  { value: 5, label: "5" },
  { value: 4, label: "4" },
  { value: 3, label: "3" },
  { value: 2, label: "2" },
  { value: 1, label: "1" },
]);

const stdValue = ref("");
const idPlan = ref("");
const calculatedAverageScore = ref<number>(0);
const dataForm = ref<StudentSurveyResModel>({
  stdSurveyId: "",
  stdNm: "",
  shyrCd: "",
  shyrNm: "",
  termCd: "",
  recommend: "",
  termNm: "",
  lstSurveyQuest: [],
});

const nameExportPDF = `student_survey`;

//first data
const openModal = async (id: string) => {
  idPlan.value = id;
  modalKey.value++;
  getListStdName();
  getListRadio();
  getDetailSurvey();

  isOpen.value = true;
};

const closeModal = async () => {
  stdValue.value = "";
  listStdName.value = [{ cdId: "", cdNm: t("common.selectedStd"), upCdId: "ALL" }];
  dataForm.value = {
    stdSurveyId: "",
    stdNm: "",
    shyrCd: "",
    shyrNm: "",
    termCd: "",
    recommend: "",
    termNm: "",
    lstSurveyQuest: [],
  } as StudentSurveyResModel;
  isOpen.value = false;
};

const getListStdName = async () => {
  await storeStdSurvey.getListStd({
    method: "POST",
    body: idPlan.value,
  });

  listStdName.value = [
    ...listStdName.value,
    ...storeStdSurvey.listStd.data.map((item) => ({
      cdId: item.id,
      cdNm: item.name,
    })),
  ];
};

const getListRadio = async () => {
  await codeMng.getCodeInfo({
    method: "POST",
    body: {
      upCdIdList: [UP_CD_STD_GRADE, UP_CD_STD_PERIOD],
    },
  });

  listRadioGrade.value = codeMng.codeInfo?.data.filter((item) => item.upCdId === UP_CD_STD_GRADE);
  listRadioPeriod.value = codeMng.codeInfo?.data.filter((item) => item.upCdId === UP_CD_STD_PERIOD);
};

const getDetailSurvey = async () => {
  await storeStdSurvey.getDetailStdSurvey({
    method: "POST",
    body: stdValue.value,
  });

  dataForm.value = storeStdSurvey.resultData?.data;
  listRadioQuestion.value = dataForm.value.lstSurveyQuest;
  const totalScore = listRadioQuestion.value.reduce((sum, item) => {
    return sum + item.score;
  }, 0);
  calculatedAverageScore.value = Number((totalScore / listRadioQuestion.value.length).toFixed(2));
};

watch(
  () => stdValue.value,
  async (newValue) => {
    getDetailSurvey();

    if (newValue == "") {
      statusBtnPdf.value = true;
    } else {
      statusBtnPdf.value = false;
    }
  },
);

defineExpose({
  openModal,
});
</script>

<style lang="scss" scoped>
.student_survey {
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

    .dowload {
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
        max-width: 90px;
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
