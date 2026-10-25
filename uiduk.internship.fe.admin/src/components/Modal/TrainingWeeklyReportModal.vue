<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<script setup lang="ts">
import { ref, defineEmits, defineExpose } from "vue";

const isOpen = ref(false);
const modalId = ref("trainingWeeklyReportModalId");
const listSelectBoxValidate = ref<[]>([]);
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
</script>
<template>
  <TModal :classSize="`large`" :is-open="isOpen" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
    <template #title>
      <strong>개인정보이용동의</strong>
    </template>
    <template #default>
      <div class="training-report-block">
        <div class="head">
          <div class="head__title">
            <span>현장실습 주간보고서</span>
            <span>홍길동(경영학과 1학년 2455026)</span>
          </div>
          <div class="head__dowload">
            <button type="button" class="button btn_white">PDF 다운로드</button>
          </div>
        </div>
        <div class="form">
          <div class="row">
            <div class="item">
              <div class="note">
                <p class="validate_password">담당자 출석 처리 전, 휴일과 결석일은 작성할 수 없습니다.</p>
                <div class="select-input">
                  <SelectBoxBase class="select-option" name="selectbox" :data="listSelectBoxValidate" placeholder="주차 선택" />
                </div>
              </div>
            </div>
            <div class="item">
              <div class="label">날짜</div>
              <div class="input"><BaseDatePicker id="date_validate" required /></div>
            </div>
            <div class="item">
              <div class="label">출·퇴근 시간</div>
              <div class="input">
                <div class="option">
                  <SelectBoxBase class="select-option" name="selectbox" :data="listSelectBoxValidate" />
                  <SelectBoxBase class="select-option" name="selectbox" :data="listSelectBoxValidate" />
                </div>
                <span>~</span>
                <div class="option">
                  <SelectBoxBase class="select-option" name="selectbox" :data="listSelectBoxValidate" />
                  <SelectBoxBase class="select-option" name="selectbox" :data="listSelectBoxValidate" />
                </div>
              </div>
            </div>
            <div class="item">
              <div class="label">현장실습 내용</div>
              <div class="input"><InputBase id="input_validate" v-model="inputValue" required placeholder="내용을 입력해 주세요." /></div>
            </div>
            <div class="item">
              <div class="label">현장실습 후기</div>
              <div class="input"><TextArea id="text_validate" v-model="textValue" required placeholder="내용을 입력해 주세요." /></div>
            </div>
            <div class="item">
              <div class="label">출·퇴근 확인(직인·서명)</div>
              <div class="input file">
                <InputFileBase
                  :mode="`create`"
                  :id="`회사 CI`"
                  :type="'img'"
                  :preview-flag="true"
                  :description="'학생날인을 등록해 주세요.'"
                  :multiple="true"
                  :max-file="5"
                  :show-mess="false"
                ></InputFileBase>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="item">
              <div class="label">날짜</div>
              <div class="input"><BaseDatePicker id="date_validate" required /></div>
            </div>
            <div class="item">
              <div class="label">출·퇴근 시간</div>
              <div class="input">
                <div class="option">
                  <SelectBoxBase class="select-option" name="selectbox" :data="listSelectBoxValidate" />
                  <SelectBoxBase class="select-option" name="selectbox" :data="listSelectBoxValidate" />
                </div>
                <span>~</span>
                <div class="option">
                  <SelectBoxBase class="select-option" name="selectbox" :data="listSelectBoxValidate" />
                  <SelectBoxBase class="select-option" name="selectbox" :data="listSelectBoxValidate" />
                </div>
              </div>
            </div>
            <div class="item">
              <div class="label">현장실습 내용</div>
              <div class="input"><InputBase id="input_validate" v-model="inputValue" required placeholder="내용을 입력해 주세요." /></div>
            </div>
            <div class="item">
              <div class="label">현장실습 후기</div>
              <div class="input"><TextArea id="text_validate" v-model="textValue" required placeholder="내용을 입력해 주세요." /></div>
            </div>
            <div class="item">
              <div class="label">출·퇴근 확인(직인·서명)</div>
              <div class="input file">
                <InputFileBase
                  :mode="`create`"
                  :id="`회사 CI`"
                  :type="'img'"
                  :preview-flag="true"
                  :description="'학생날인을 등록해 주세요.'"
                  :multiple="true"
                  :max-file="5"
                  :show-mess="false"
                ></InputFileBase>
              </div>
            </div>
          </div>
        </div>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_xl btn_primary" @click="cancelAction">저장</button>
    </template>
  </TModal>
</template>

<style lang="scss" scoped>
.training-report-block {
  margin-bottom: 24px;
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
      &:first-child {
        border-bottom: 1px solid var(--gray--e4);
        margin-bottom: 24px;
      }
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
