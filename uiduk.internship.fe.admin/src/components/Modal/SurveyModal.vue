<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <TModal :classSize="`large`" :is-open="isOpen" :is-show-header="true" :is-show-footer="true" :modal-id="modalId"
      @close-modal="closeModal">
    <template #title>
      <strong>개인정보이용동의</strong>
    </template>
    <template #default>
      <div class="survey-block">
        <div class="field-training">
          <div class="title">현장실습 설문조사서(실습생용)</div>
          <div class="dowload">
            <button type="button" class="button btn_white">PDF 다운로드</button>
          </div>
        </div>
        <div class="option-field">
          <div class="item">
            <div class="label">성명</div>
            <div>홍길동</div>
          </div>
          <div class="item">
            <div class="label">학년</div>
            <div class="option">
              <RadiobuttonBase
                  v-for="item in listRadioGrade"
                  :id="item.value"
                  :key="item.value"
                  v-model="radiovalueGrade"
                  :value="item.label"
                  :name="item.value"
                  :checked="item.value == radiovalueGrade"
                  :label="item.label"
              ></RadiobuttonBase>
            </div>
          </div>
          <div class="item">
            <div class="label">현장실습 시기</div>
            <div class="option">
              <RadiobuttonBase
                  v-for="item in listRadioPeriod"
                  :id="item.value"
                  :key="item.value"
                  v-model="radiovaluePeriod"
                  :value="item.label"
                  :name="item.value"
                  :checked="item.value == radiovaluePeriod"
                  :label="item.label"
              ></RadiobuttonBase>
            </div>
          </div>
        </div>
        <div class="table">
          <div class="survey-container">
            <div class="survey-header">
              <div class="survey-item">설문항목</div>
              <div class="survey-scale">
                <div class="survey-score">설문점수</div>
                <div class="d-flex">
                  <div class="scale-item">매우 그렇다</div>
                  <div class="scale-item">그렇다</div>
                  <div class="scale-item">보통이다</div>
                  <div class="scale-item">아니다</div>
                  <div class="scale-item">전혀 아니다</div>
                </div>
              </div>
            </div>

            <div class="survey-question">
              <div class="question-item">1. 현장실습 프로그램에 만족한다</div>
              <div class="outline-score">
                <div class="question-score" v-for="item in listRadioQuestionOne">
                  <RadiobuttonBase
                      :id="item.value"
                      :key="item.value"
                      v-model="radioQuestionValueOne"
                      :value="item.label"
                      :name="item.value"
                      :checked="item.value == radioQuestionValueOne"
                      :label="item.label"
                  />
                </div>
              </div>
            </div>

            <div class="survey-question">
              <div class="question-item">2. 현장실습을 통해 전공 실무를 배울 수 있었다.</div>
              <div class="outline-score">
                <div class="question-score" v-for="(item, index) in listRadioQuestionTwo" :key="index">
                  <RadiobuttonBase
                      :id="item.value"
                      :value="item.label"
                      :name="item.value"
                      :checked="item.value == radioQuestionValueTwo"
                      :label="item.label"
                      v-model="radioQuestionValueTwo"
                  />
                </div>
              </div>
            </div>

            <div class="survey-question">
              <div class="question-item">3. 현장실습을 통해 전공 실무를 배울 수 있었다.</div>
              <div class="outline-score">
                <div class="question-score" v-for="(item, index) in listRadioQuestionThree" :key="index">
                  <RadiobuttonBase
                      :id="item.value"
                      :value="item.label"
                      :name="item.value"
                      :checked="item.value == radioQuestionValueThree"
                      :label="item.label"
                      v-model="radioQuestionValueThree"
                  />
                </div>
              </div>
            </div>
            <div class="survey-question">
              <div class="question-item question-total">설문평점</div>
              <div class="outline-score">
                <div class="question-score question-score-total"><span class="total">4.56</span></div>
              </div>
            </div>
          </div>
        </div>
        <div class="description">
          <div class="title">현장실습 프로그램에 대한 건의사항</div>
          <div class="body">
            <textarea name="" id="" cols="30" rows="5" placeholder="내용을 입력해 주세요. (최대 500자 제한)"></textarea>
          </div>
        </div>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_xl btn_primary" @click="cancelAction">확인</button>
    </template>
  </TModal>
</template>
<script setup lang="ts">
import {ref, defineEmits, defineExpose} from "vue";

const isOpen = ref(false);
const modalId = ref("surveyModalId");

const emit = defineEmits(["confirm", "cancel"]);

const closeModal = () => {
  isOpen.value = false;
};
const cancelAction = () => {
  emit("cancel");
  closeModal();
};

const openModal = () => {
  isOpen.value = true;
};
const radiovalueGrade = ref("5-1");
const radiovaluePeriod = ref("5-2");
const listRadioPeriod = ref([
  {value: "5-2", label: "1학년"},
  {value: "7-2", label: "2학년"},
  {value: "8-2", label: "3학년"},
  {value: "9-2", label: "4학년"},
]);
const listRadioGrade = ref([
  {value: "5-1", label: "1학년"},
  {value: "6-1", label: "2학년"},
  {value: "7-1", label: "3학년"},
  {value: "8-1", label: "4학년"},
]);
const radioQuestionValueOne = ref("q15");
const listRadioQuestionOne = ref([
  {value: "q15", label: "5"},
  {value: "q14", label: "4"},
  {value: "q13", label: "3"},
  {value: "q12", label: "2"},
  {value: "q11", label: "1"},
]);
const radioQuestionValueTwo = ref("q25");
const listRadioQuestionTwo = ref([
  {value: "q25", label: "5"},
  {value: "q24", label: "4"},
  {value: "q23", label: "3"},
  {value: "q22", label: "2"},
  {value: "q21", label: "1"},
]);
const radioQuestionValueThree = ref("q35");
const listRadioQuestionThree = ref([
  {value: "q35", label: "5"},
  {value: "q34", label: "4"},
  {value: "q33", label: "3"},
  {value: "q32", label: "2"},
  {value: "q31", label: "1"},
]);
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

  .field-training {
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

  .option-field {
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

  .table {
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

  .description {
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
