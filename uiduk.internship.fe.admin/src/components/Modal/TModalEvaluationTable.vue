<script setup lang="ts">
import { ref, defineEmits, defineExpose } from "vue";

const isOpen = ref(false);
const modalId = ref("evaluationTableId");

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
defineExpose({
  openModal,
});
const listSelectBoxValidate = ref<[]>([]);

// Checkbox

const listRadioQuestion1 = [
  { value: "q15", label: "10" },
  { value: "q14", label: "8" },
  { value: "q13", label: "6" },
  { value: "q12", label: "4" },
  { value: "q11", label: "2" },
];

const listRadioQuestion2 = [
  { value: "q25", label: "10" },
  { value: "q24", label: "8" },
  { value: "q23", label: "6" },
  { value: "q22", label: "4" },
  { value: "q21", label: "2" },
];

const listRadioQuestion3 = [
  { value: "q35", label: "10" },
  { value: "q34", label: "8" },
  { value: "q33", label: "6" },
  { value: "q32", label: "4" },
  { value: "q31", label: "2" },
];

const listRadioQuestion4 = [
  { value: "q45", label: "10" },
  { value: "q44", label: "8" },
  { value: "q43", label: "6" },
  { value: "q42", label: "4" },
  { value: "q41", label: "2" },
];

const listRadioQuestion5 = [
  { value: "q55", label: "10" },
  { value: "q54", label: "8" },
  { value: "q53", label: "6" },
  { value: "q52", label: "4" },
  { value: "q51", label: "2" },
];

const listRadioQuestion6 = [
  { value: "q65", label: "10" },
  { value: "q64", label: "8" },
  { value: "q63", label: "6" },
  { value: "q62", label: "4" },
  { value: "q61", label: "2" },
];

const listRadioQuestion7 = [
  { value: "q75", label: "5" },
  { value: "q74", label: "4" },
  { value: "q73", label: "3" },
  { value: "q72", label: "2" },
  { value: "q71", label: "1" },
];

const listRadioQuestion8 = [
  { value: "q85", label: "5" },
  { value: "q84", label: "4" },
  { value: "q83", label: "3" },
  { value: "q82", label: "2" },
  { value: "q81", label: "1" },
];

const listRadioQuestion9 = [
  { value: "q95", label: "5" },
  { value: "q94", label: "4" },
  { value: "q93", label: "3" },
  { value: "q92", label: "2" },
  { value: "q91", label: "1" },
];

const listRadioQuestion10 = [
  { value: "q105", label: "5" },
  { value: "q104", label: "4" },
  { value: "q103", label: "3" },
  { value: "q102", label: "2" },
  { value: "q101", label: "1" },
];

const listRadioQuestion11 = [
  { value: "q115", label: "20" },
  { value: "q114", label: "16" },
  { value: "q113", label: "12" },
  { value: "q112", label: "8" },
  { value: "q111", label: "4" },
];

const radioQuestionValue1 = ref("q15");
const radioQuestionValue2 = ref("q25");
const radioQuestionValue3 = ref("q35");
const radioQuestionValue4 = ref("q45");
const radioQuestionValue5 = ref("q55");
const radioQuestionValue6 = ref("q65");
const radioQuestionValue7 = ref("q75");
const radioQuestionValue8 = ref("q85");
const radioQuestionValue9 = ref("q95");
const radioQuestionValue10 = ref("q105");
const radioQuestionValue11 = ref("q115");
const updateTotalPoints = () => {};
</script>
<template>
  <TModal :classSize="`xlarge`" :is-open="isOpen" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
    <template #title>
      <strong>평가표</strong>
    </template>
    <template #default>
      <div class="evaluation-container">
        <div class="evaluation-container__form">
          <div class="option">
            <div class="title-block mb-0">학생선택</div>
            <div><SelectBoxBase name="selectbox" :data="listSelectBoxValidate" /></div>
          </div>
          <div class="option">
            <button type="button" class="button btn_white">저장</button>
            <button type="button" class="button btn_white">PDF 다운로드</button>
          </div>
        </div>
        <div class="evaluation-container__header">
          <h2 class="title-block">표준 현장실습학기제[Co-op] 평가표</h2>
        </div>
        <div class="evaluation-container__student-info">
          <div class="title-block">평가대상 학생정보</div>
          <div class="content-block">
            <div class="item">
              <div class="label">대학명</div>
              <div class="value">위덕대학교</div>
            </div>
            <div class="item">
              <div class="label">대학명</div>
              <div class="value">위덕대학교</div>
            </div>
            <div class="item">
              <div class="label">대학명</div>
              <div class="value">위덕대학교</div>
            </div>
            <div class="item">
              <div class="label">대학명</div>
              <div class="value">위덕대학교</div>
            </div>
            <div class="item">
              <div class="label">대학명</div>
              <div class="value">위덕대학교</div>
            </div>
            <div class="item">
              <div class="label">대학명</div>
              <div class="value">위덕대학교</div>
            </div>
          </div>
        </div>
        <div class="evaluation-container__table">
          <div class="title-block">평가표</div>
          <div class="tbl tbl_col tbl_scroll scrollx_tbl_lg scrolly_tbl_xxl" style="overflow-x: auto">
            <table>
              <thead>
                <tr>
                  <th class="text-bold" rowspan="2" style="width: 15%">평가영역</th>
                  <th class="text-bold" rowspan="2" style="width: 30%">평가항목</th>
                  <th class="text-bold" colspan="5">평가점수</th>
                  <th class="text-bold" rowspan="2" style="width: 15%">합계</th>
                </tr>
                <tr>
                  <th class="text-bold">매우우수</th>
                  <th class="text-bold">우수</th>
                  <th class="text-bold">보통</th>
                  <th class="text-bold">미흡</th>
                  <th class="text-bold">매우미흡</th>
                </tr>
              </thead>
              <tbody>
                <!-- col1 -->
                <tr>
                  <td class="bg-ligh1 text-bold" rowspan="6" style="width: 15%">수행능력</td>
                  <td class="text-bold">실무관련 지식</td>
                  <td v-for="(item, index) in listRadioQuestion1" :key="index">
                    <RadiobuttonBase
                      :id="item.value"
                      :value="item.label"
                      :name="`q1`"
                      :checked="item.value == radioQuestionValue1"
                      :label="item.label"
                      @click="updateTotalPoints"
                      v-model="radioQuestionValue1"
                    />
                  </td>
                  <td class="text-bold" rowspan="6" style="width: 15%">10/60</td>
                </tr>
                <tr>
                  <td class="text-bold">업무숙지 능력</td>
                  <td v-for="(item, index) in listRadioQuestion2" :key="index">
                    <RadiobuttonBase
                      :id="item.value"
                      :value="item.label"
                      :name="`q2`"
                      :checked="item.value == radioQuestionValue2"
                      :label="item.label"
                      @change="updateTotalPoints"
                      v-model="radioQuestionValue2"
                    />
                  </td>
                </tr>
                <tr>
                  <td class="text-bold">업무이행 (과정)능력</td>
                  <td v-for="(item, index) in listRadioQuestion3" :key="index">
                    <RadiobuttonBase
                      :id="item.value"
                      :value="item.label"
                      :name="`q3`"
                      :checked="item.value == radioQuestionValue3"
                      :label="item.label"
                      @change="updateTotalPoints"
                      v-model="radioQuestionValue3"
                    />
                  </td>
                </tr>
                <tr>
                  <td class="text-bold">업무이행 (결과)수준</td>
                  <td v-for="(item, index) in listRadioQuestion4" :key="index">
                    <RadiobuttonBase
                      :id="item.value"
                      :value="item.label"
                      :name="`q4`"
                      :checked="item.value == radioQuestionValue4"
                      :label="item.label"
                      @change="updateTotalPoints"
                      v-model="radioQuestionValue4"
                    />
                  </td>
                </tr>
                <tr>
                  <td class="text-bold">혁신/창의성</td>
                  <td v-for="(item, index) in listRadioQuestion5" :key="index">
                    <RadiobuttonBase
                      :id="item.value"
                      :value="item.label"
                      :name="`q5`"
                      :checked="item.value == radioQuestionValue5"
                      :label="item.label"
                      @change="updateTotalPoints"
                      v-model="radioQuestionValue5"
                    />
                  </td>
                </tr>
                <tr>
                  <td class="text-bold">소통/표현 능력</td>
                  <td v-for="(item, index) in listRadioQuestion6" :key="index">
                    <RadiobuttonBase
                      :id="item.value"
                      :value="item.label"
                      :name="`q6`"
                      :checked="item.value == radioQuestionValue6"
                      :label="item.label"
                      @change="updateTotalPoints"
                      v-model="radioQuestionValue6"
                    />
                  </td>
                </tr>

                <!-- col2 -->
                <tr>
                  <td class="bg-ligh1 text-bold" rowspan="4" style="width: 15%">수행태도</td>
                  <td class="text-bold">성실성/시간관리 능력</td>
                  <td v-for="(item, index) in listRadioQuestion7" :key="index">
                    <RadiobuttonBase
                      :id="item.value"
                      :value="item.label"
                      :name="`q7`"
                      :checked="item.value == radioQuestionValue7"
                      :label="item.label"
                      @change="updateTotalPoints"
                      v-model="radioQuestionValue7"
                    />
                  </td>
                  <td class="text-bold" rowspan="4" style="width: 15%">20/60</td>
                </tr>
                <tr>
                  <td class="text-bold">책임감</td>
                  <td v-for="(item, index) in listRadioQuestion8" :key="index">
                    <RadiobuttonBase
                      :id="item.value"
                      :value="item.label"
                      :name="`q8`"
                      :checked="item.value == radioQuestionValue8"
                      :label="item.label"
                      @change="updateTotalPoints"
                      v-model="radioQuestionValue8"
                    />
                  </td>
                </tr>
                <tr>
                  <td class="text-bold">대인관계/협업 능력</td>
                  <td v-for="(item, index) in listRadioQuestion9" :key="index">
                    <RadiobuttonBase
                      :id="item.value"
                      :value="item.label"
                      :name="`q9`"
                      :checked="item.value == radioQuestionValue9"
                      :label="item.label"
                      @change="updateTotalPoints"
                      v-model="radioQuestionValue9"
                    />
                  </td>
                </tr>
                <tr>
                  <td class="text-bold">업무절차/기준 준수</td>
                  <td v-for="(item, index) in listRadioQuestion10" :key="index">
                    <RadiobuttonBase
                      :id="item.value"
                      :value="item.label"
                      :name="`q10`"
                      :checked="item.value == radioQuestionValue10"
                      :label="item.label"
                      @change="updateTotalPoints"
                      v-model="radioQuestionValue10"
                    />
                  </td>
                </tr>

                <!-- col3 -->
                <tr>
                  <td class="bg-ligh1 text-bold" rowspan="1" style="width: 15%">평가영역</td>
                  <td class="text-bold">출석/결석/지각(근태)</td>
                  <td v-for="(item, index) in listRadioQuestion11" :key="index">
                    <RadiobuttonBase
                      :id="item.value"
                      :value="item.label"
                      :name="`q11`"
                      :checked="item.value == radioQuestionValue11"
                      :label="item.label"
                      @change="updateTotalPoints"
                      v-model="radioQuestionValue11"
                    />
                  </td>
                  <td class="text-bold" rowspan="1" style="width: 15%">0/20</td>
                </tr>

                <!-- col4 -->
                <tr>
                  <td class="bg-ligh1 text-bold" rowspan="1" colspan="7" style="width: 15%">평가 총점</td>
                  <td class="text-bold bg-total" rowspan="1" style="width: 15%">10/100</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="evaluation-container__comment">
          <div class="title-block">평가 의견</div>
          <div class="body">
            <div class="label">[작성기준] 학생에게 비공개되며, 학생에 대한 객관적이고 전반적인 실습평가 의견 기술 바랍니다.</div>
            <div class="input">
              <TextArea model-value="" style="border-radius: 0" placeholder="내용을 입력해 주세요. (최대 300자 제한)" />
            </div>
          </div>
        </div>
        <div class="evaluation-container__feedback">
          <div class="title-block">학생에 대한 피드백</div>
          <div class="body">
            <div class="label">[작성기준] 학생에게 공개되며, 학생의 장/단점, 개선할 사항, 직무관련 수강교과 제안 등 기술 바랍니다.</div>
            <div class="input">
              <TextArea style="border-radius: 0" model-value="" placeholder="내용을 입력해 주세요. (최대 300자 제한)" />
            </div>
          </div>
        </div>
        <div class="evaluation-container__document">
          <div class="head">
            <div class="label">붙임서류</div>
            <div class="value">출석부</div>
          </div>
          <div class="body">
            <p>
              본 기관에서는 교육부 고시에 따른 표준 현장실습학기제(Co-op) 운영 완료에 따라
              <br />
              귀 대학의 참여학생에 대한 평가표 및 출럭부를 제출합니다.
            </p>
            <p class="date">2025년 1월 15일</p>
          </div>
        </div>
        <div class="evaluation-container__reviewer">
          <div class="title-block">실습기관 평가자</div>
          <div class="content-block">
            <div class="item">
              <div class="label">실습기관명</div>
              <div class="value">상생전자</div>
            </div>
            <div class="item">
              <div class="label">부서명</div>
              <div class="value">총무과</div>
            </div>
            <div class="item">
              <div class="label">직위</div>
              <div class="value">부장</div>
            </div>
            <div class="item">
              <div class="label">설명/날인</div>
              <div class="value">김부장</div>
            </div>
          </div>
        </div>
        <div class="evaluation-container__signature">
          <div class="title-block">위덕대학교 귀중</div>
        </div>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_xl btn_primary" @click="cancelAction">확인</button>
    </template>
  </TModal>
</template>

<style lang="scss" scoped>
.evaluation-container {
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
          font-weight: 500;
          background: var(--Gray-scale-5, rgb(248, 248, 248));
          @media screen and (max-width: 1024px) {
            border-right: 0;
          }
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
</style>
