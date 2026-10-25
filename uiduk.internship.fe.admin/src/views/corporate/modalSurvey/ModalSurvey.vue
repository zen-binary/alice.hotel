<template>
  <TModal :classSize="`large`" :is-open="isOpen" :is-show-header="true" :is-show-footer="false" :modal-id="modalId" @close-modal="handleCancel">
    <template #title>
      <strong>{{ t("modalSurveyCorp.titleModal") }}</strong>
    </template>
    <template #default>
      <div id="surveyContainer" class="survey-block">
        <div class="survey-block__field-training">
          <div class="title">{{ t("modalSurveyCorp.subTitle") }}</div>
          <div class="dowload">
            <button type="button" :disabled="isCheckRequired" class="button btn_white hidden_print" @click="confirmSave">
              {{ t("common.save") }}
            </button>
            <ButtonExportPDFV1 id="surveyContainer" :nameExport="t('modalSurveyCorp.btnExportPdf')" />
          </div>
        </div>
        <div class="survey-block__option-field">
          <div class="item">
            <div class="label">{{ t("modalSurveyCorp.title1") }}:</div>
            <div>{{ internCorporateSurvey ? internCorporateSurvey.cormNm : "-" }}</div>
          </div>
          <div class="item">
            <div class="label">{{ t("modalSurveyCorp.title2") }}:</div>
            <div class="select_checkbox">
              <div v-for="item in listInternQuantity">
                <RadiobuttonBase
                  :id="'internQuantity' + item.cdId"
                  :key="'internQuantity' + item.cdId"
                  :name="'internQuantity'"
                  :value="internQuantitySelected"
                  :checked="item.cdId === internQuantitySelected"
                  :is-disable="true"
                  :label="item.cdNm"
                />
              </div>
            </div>
          </div>
          <div class="item">
            <div class="label">{{ t("modalSurveyCorp.title3") }}:</div>
            <div class="mg_l15">
              <div class="select_checkbox" v-if="internCorporateSurvey">
                <RadiobuttonBase
                  v-for="item in listTermCd"
                  :id="'termCd' + item.cdId"
                  :key="'termCd' + item.cdId"
                  v-model="internCorporateSurvey.internshipTerm"
                  :value="item.cdNm"
                  :name="'termCd'"
                  :is-disable="true"
                  :checked="item.cdId == internCorporateSurvey.internshipTerm"
                  :label="item.cdNm"
                />
              </div>
            </div>
          </div>
        </div>
        <div class="survey-block__table">
          <div class="survey-container">
            <div class="survey-header">
              <div class="survey-item">{{ t("modalSurveyCorp.title4") }}</div>
              <div class="survey-scale">
                <div class="survey-score">{{ t("modalSurveyCorp.title5") }}</div>
                <div class="d-flex">
                  <div class="scale-item">{{ t("modalSurveyCorp.title6") }}</div>
                  <div class="scale-item">{{ t("modalSurveyCorp.title7") }}</div>
                  <div class="scale-item">{{ t("modalSurveyCorp.title8") }}</div>
                  <div class="scale-item">{{ t("modalSurveyCorp.title9") }}</div>
                  <div class="scale-item">{{ t("modalSurveyCorp.title10") }}</div>
                </div>
              </div>
            </div>

            <div class="survey-question" v-for="qus in internCorporateSurvey?.listQus" :key="qus.corporateSurveyQusSeq">
              <div class="question-item">{{ qus.qusContent }}</div>
              <div class="outline-score">
                <div class="question-score" v-for="item in listScore">
                  <RadiobuttonBase
                    :id="'score' + item.cdId + qus.corporateSurveyQusSeq"
                    :value="item.cdId"
                    :name="qus.corporateSurveyQusSeq + 'score'"
                    :label="item.cdNm"
                    v-model="qus.score"
                    :checked="qus.score === item.cdId"
                  />
                </div>
              </div>
            </div>

            <div class="survey-question">
              <div class="question-item question-total">{{ t("modalSurveyCorp.title11") }}</div>
              <div class="outline-score">
                <div class="question-score question-score-total">
                  <span class="total">{{ averageScore }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="survey-block__description">
          <div class="title">{{ t("modalSurveyCorp.title12") }}</div>
          <div class="body" v-if="internCorporateSurvey">
            <TextArea
              v-model="internCorporateSurvey.internRecommend"
              name="internRecommend"
              id="internRecommend"
              cols="30"
              rows="5"
              required
              :max-words="500"
              :placeholder="t('modalSurveyCorp.title13')"
            ></TextArea>
          </div>
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
<script setup lang="ts">
import { CD_ID_INT_COU_01, CD_ID_INT_COU_02, CD_ID_INT_COU_03, CD_ID_INT_COU_04, UP_CD_ID_INT_COU_00, UP_CD_NCR_T11 } from "@/constants/common.const";
import { codeMngStore } from "@/stores/common/codeMng";
import { CodeMngModel } from "@/stores/common/codeMng/codeMng.type";
import { modalSurveyCorpStore } from "@/stores/corporate/modalSurvey";
import { InternCorporateSurveyModel } from "@/stores/corporate/modalSurvey/modalSurvey.type";
import { ref, defineEmits, defineExpose } from "vue";

const modalId = ref("surveyModalId");

const props = defineProps<{
  internPlanSeq: string;
  isOpen: boolean;
  handleCancel: () => void;
}>();

const { t } = useI18n();
const modalSurveyStore = modalSurveyCorpStore();
const codeMng = codeMngStore();
const createListPoints = (points: string[]): CodeMngModel[] => {
  return points.map((point) => ({ cdId: point, cdNm: point, upCdId: "" }));
};

const internCorporateSurvey = ref<InternCorporateSurveyModel>();
const listTermCd = ref<Array<CodeMngModel>>([]);
const listInternQuantity = ref<Array<CodeMngModel>>([]);
const listScore = ref<CodeMngModel[]>(createListPoints(["5", "4", "3", "2", "1"]));
const internQuantitySelected = ref("");

const averageScore = computed(() => {
  if (internCorporateSurvey.value?.listQus.length) {
    const totalScore = internCorporateSurvey.value.listQus.reduce((acc, qus) => acc + Number(qus.score), 0);
    return (totalScore / internCorporateSurvey.value.listQus.length).toFixed(2);
  }
  return "0.00";
});

watch(
  () => props.isOpen,
  async (newVal) => {
    if (newVal) {
      await getListCodeMng();
      await getDataDetail();
    } else {
      listTermCd.value = [];
      listInternQuantity.value = [];
      internCorporateSurvey.value = null;
    }
  },
);

async function getDataDetail() {
  await modalSurveyStore.getSuveyCorp({
    method: "POST",
    body: { internPlanSeq: props.internPlanSeq },
  });

  const internData = modalSurveyStore.resultData.data;
  if (internData) {
    const { internQuantity } = internData;
    switch (true) {
      case internQuantity >= 1 && internQuantity <= 3:
        internQuantitySelected.value = CD_ID_INT_COU_01;
        break;
      case internQuantity >= 4 && internQuantity <= 6:
        internQuantitySelected.value = CD_ID_INT_COU_02;
        break;
      case internQuantity >= 7 && internQuantity <= 10:
        internQuantitySelected.value = CD_ID_INT_COU_03;
        break;
      case internQuantity > 10:
        internQuantitySelected.value = CD_ID_INT_COU_04;
        break;
    }
  }
  internCorporateSurvey.value = internData;
}

async function getListCodeMng() {
  await codeMng.getCodeInfo({
    method: "POST",
    body: {
      upCdIdList: [UP_CD_NCR_T11, UP_CD_ID_INT_COU_00],
    },
  });
  codeMng.codeInfo.data.forEach((item) => {
    if (item.upCdId === UP_CD_NCR_T11) {
      listTermCd.value.push(item);
    }
    if (item.upCdId === UP_CD_ID_INT_COU_00) {
      listInternQuantity.value.push(item);
    }
  });
}

const isModalConfirmSave = ref(false);

function confirmSave() {
  isModalConfirmSave.value = true;
}

function closeModalConfirmSave() {
  isModalConfirmSave.value = false;
}

const isModalAlertSuccessSave = ref(false);

function closeModalSuccessSave() {
  isModalAlertSuccessSave.value = false;
  props.handleCancel();
}

const isCheckRequired = computed(() => {
  if (!internCorporateSurvey.value) {
    return true;
  }
  let requiredAllScore = false;
  for (const qus of internCorporateSurvey.value.listQus) {
    if (!qus.score || qus.score.trim() === "") {
      requiredAllScore = true;
      break;
    }
    if (requiredAllScore) break;
  }
  const feedbackMissing = !internCorporateSurvey.value.internRecommend.trim();
  return requiredAllScore || feedbackMissing;
});

const saveData = async () => {
  await modalSurveyStore.saveSuveyCorp({ method: "POST", body: internCorporateSurvey.value });
  closeModalConfirmSave();
  isModalAlertSuccessSave.value = true;
};
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

    .dowload {
      display: flex;
      gap: 10px;
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
        align-items: start;
        gap: 18px;
        width: fit-content;
        flex-direction: column;
        margin-bottom: 17px;

        &:first-child {
          flex-direction: row;
          white-space: nowrap;
          margin: 0 0 12px 0;
          gap: 10px;
        }
        &:last-child {
          margin: 0;
        }
      }

      .label {
        width: 100%;
        max-width: 100px;
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

    .title {
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

    .body {
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