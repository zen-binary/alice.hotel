<script setup lang="ts">
import { ref, defineEmits, defineExpose } from "vue";
import InputBase from "../common/input/InputBase.vue";
import TextArea from "../common/input/TextArea.vue";

const isOpen = ref(false);
const modalId = ref("preCheckTableId");

const emit = defineEmits(["confirm", "cancel"]);

const closeModal = () => {
  isOpen.value = false;
};

const openModal = () => {
  isOpen.value = true;
};
defineExpose({
  openModal,
});
const listCheckBox = ref([
  { id: "q1ss", name: "일반사무실 형태" },
  { id: "q2ss", name: "연구소 형태" },
  { id: "q3ss", name: "생산/제조 등의 공장 형태" },
  { id: "q4ss", name: "주 사업장 외(건설현장, 자동차/선박/항공 등) 형태" },
]);
const listCheckBox1 = ref([
  { id: "q1ss1", name: "일반사무실 형태" },
  { id: "q2ss2", name: "연구소 형태" },
  { id: "q3ss3", name: "생산/제조 등의 공장 형태" },
  { id: "q4ss4", name: "주 사업장 외(건설현장, 자동차/선박/항공 등) 형태" },
]);
const listCheckBox2 = ref([
  { id: "q123", name: "소속 근로자와 같은 공간에 배정된다." },
  { id: "q123", name: "소속 근로자와 분리된 별도의 공간에 배정된다." },
]);
const listCheckBox3 = ref([
  { id: "q123", name: "모든 필요 물품 및 비품을 제공한다." },
  { id: "q123", name: "전혀 제공하지 않는다." },
]);
const listCheckBox4 = ref([
  { id: "q123", name: "물품 제조∙생산 등의 활동(업무)에 참여하지 않는다." },
  { id: "q123", name: "물품 제조∙생산 등의 활동(업무)에 일부 참여한다." },
  { id: "q123", name: "물품 제조∙생산 등의 활동(업무)을 주 실습 직무로 참여한다." },
]);
const listCheckBox5 = ref([
  { id: "q123", name: "취급하지 않는다." },
  { id: "q123", name: "취급한다.(6-2 문항으로)" },
]);
const listCheckBox6 = ref([
  { id: "q123", name: "제공된다" },
  { id: "q123", name: "제공되지 않는다." },
]);
const checkboxGroup = ref([]);
</script>
<template>
  <TModal :classSize="`xlarge`" :is-open="isOpen" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
    <template #title>
      <strong>평가표</strong>
    </template>
    <template #default>
      <div class="precheck-container">
        <div class="precheck-container__form">
          <div class="option"></div>
          <div class="option">
            <button type="button" class="button btn_white">저장</button>
            <button type="button" class="button btn_white">PDF 다운로드</button>
          </div>
        </div>
        <div class="precheck-container__header">
          <h2 class="title-block">표준 현장실습학기제[Co-op] 평가표</h2>
        </div>
        <div class="precheck-container__student-info">
          <div class="title-block">평가대상 학생정보</div>
          <div class="content-block">
            <div class="item">
              <div class="label">실습기관명</div>
              <div class="value">상생전자</div>
            </div>
            <div class="item">
              <div class="label">한국표준산업분류코드</div>
              <div class="value">분류코드를 입력해주세요.</div>
            </div>
            <div class="item">
              <div class="label">업태</div>
              <div class="value">음식/숙박업</div>
            </div>
            <div class="item">
              <div class="label">업종</div>
              <div class="value">호텔업</div>
            </div>
          </div>
        </div>
        <div class="precheck-container__table">
          <div class="title-block">자체 점검표 [실습기관에서 작성]</div>
          <div class="tbl tbl_col tbl_scroll scrollx_tbl_lg scrolly_tbl_xxl" style="overflow-x: auto">
            <table>
              <thead>
                <tr>
                  <th class="text-bold" style="width: 10%"></th>
                  <th class="text-bold" style="width: 30%">점검 사항</th>
                  <th class="text-bold" style="width: 45%">내용 (해당 사항에 체크 또는 기술)</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td class="text-bold">1</td>
                  <td class="text-bold">
                    실습기관의 사업장(근무환경) 형태
                    <br />
                    [복수선택 가능]
                  </td>
                  <td class="text-bold">
                    <div>
                      <CheckboxBase
                        v-for="item in listCheckBox"
                        :id="item.id"
                        :key="item.id"
                        v-model="checkboxGroup"
                        :value="item.name"
                        :name="item.id"
                        :label="item.name"
                      />
                      <div class="input-other">
                        <div class="checkbox">
                          <CheckboxBase :id="'1ádsad'" v-model="checkboxGroup" :value="`기타`" :name="`1ádsad`" :label="`기타`" />
                        </div>
                        <div class="text">
                          <InputBase model-value="" mode="1" :disabled="true" />
                        </div>
                      </div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <td class="text-bold">2</td>
                  <td class="text-bold">
                    학생이 배정될 부서 또는실습공간의 환경 및 형태
                    <br />
                    [복수선택 가능]
                  </td>
                  <td class="text-bold">
                    <div>
                      <CheckboxBase
                        v-for="item in listCheckBox1"
                        :id="item.id"
                        :key="item.id"
                        v-model="checkboxGroup"
                        :value="item.name"
                        :name="item.id"
                        :label="item.name"
                      />
                      <div class="input-other">
                        <div class="checkbox">
                          <CheckboxBase :id="'1ádsad'" v-model="checkboxGroup" :value="`기타`" :name="`1ádsad`" :label="`기타`" />
                        </div>
                        <div class="text">
                          <InputBase v-mode="1" :disabled="true" />
                        </div>
                      </div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <td class="text-bold">3</td>
                  <td class="text-bold">학생에 대한 실습공간 배정 형태</td>
                  <td class="text-bold">
                    <div>
                      <CheckboxBase
                        v-for="item in listCheckBox2"
                        :id="item.id"
                        :key="item.id"
                        v-model="checkboxGroup"
                        :value="item.name"
                        :name="item.id"
                        :label="item.name"
                      />
                    </div>
                  </td>
                </tr>
                <tr>
                  <td class="text-bold">4</td>
                  <td class="text-bold">학생에게 실습에 필요한 물품 제공 여부(책상, PC 등)</td>
                  <td class="text-bold">
                    <div>
                      <CheckboxBase
                        v-for="item in listCheckBox3"
                        :id="item.id"
                        :key="item.id"
                        v-model="checkboxGroup"
                        :value="item.name"
                        :name="item.id"
                        :label="item.name"
                      />
                      <div class="input-other">
                        <div class="checkbox">
                          <CheckboxBase
                            :id="'1ádsad'"
                            v-model="checkboxGroup"
                            :value="`일부 제공한다.(학생 준비 필요 물품 :`"
                            :name="`1ádsad`"
                            :label="`일부 제공한다.(학생 준비 필요 물품 :`"
                          />
                        </div>
                        <div class="text">
                          <InputBase v-mode="1" :disabled="true" />
                        </div>
                      </div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <td class="text-bold">5</td>
                  <td class="text-bold">학생의 제조 또는 생산 업무에 참여 여부</td>
                  <td class="text-bold">
                    <div>
                      <CheckboxBase
                        v-for="item in listCheckBox4"
                        :id="item.id"
                        :key="item.id"
                        v-model="checkboxGroup"
                        :value="item.name"
                        :name="item.id"
                        :label="item.name"
                      />
                    </div>
                  </td>
                </tr>
                <tr>
                  <td class="text-bold">6-1</td>
                  <td class="text-bold">
                    학생이 수행할 실습 직무의
                    <br />
                    화학물질 또는 유해물질 등의 취급 여부
                  </td>
                  <td class="text-bold">
                    <div>
                      <CheckboxBase
                        v-for="item in listCheckBox5"
                        :id="item.id"
                        :key="item.id"
                        v-model="checkboxGroup"
                        :value="item.name"
                        :name="item.id"
                        :label="item.name"
                      />
                    </div>
                  </td>
                </tr>
                <tr>
                  <td class="text-bold">6-2</td>
                  <td class="text-bold">
                    화학/유해 물질을 취급할 경우
                    <br />
                    안전보호장비(구) 제공 여부
                    <br />
                    ※ 실험·실습 형태 및 화학·유해 물질을 취급하는 경우는
                    <br />
                    현장교육담당자의 입회하에 실시되어야 함
                  </td>
                  <td class="text-bold">
                    <div>
                      <CheckboxBase
                        v-for="item in listCheckBox6"
                        :id="item.id"
                        :key="item.id"
                        v-model="checkboxGroup"
                        :value="item.name"
                        :name="item.id"
                        :label="item.name"
                      />
                    </div>
                  </td>
                </tr>
                <tr>
                  <td class="text-bold">7</td>
                  <td class="text-bold">
                    기타 유의사항(필요시 작성)
                  </td>
                  <td class="text-bold">
                    <div>
                      <TextArea model-value="" placeholder="내용을 입력해주세요." />
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </template>
  </TModal>
</template>

<style lang="scss" scoped>
.precheck-container {
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
  &__table {
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
        flex: 1 1 calc(50% - 10px);
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
          &:first-child {
            background: var(--Gray-scale-5, rgb(248, 248, 248));
          }
          .input-other {
            display: flex;
            align-items: center;
            .checkbox {
              margin-right: 10px;
            }
          }
        }
      }
    }
  }
}
</style>
