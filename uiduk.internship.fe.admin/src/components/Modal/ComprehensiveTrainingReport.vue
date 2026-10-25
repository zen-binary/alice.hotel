<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<script setup lang="ts">
import { ref, defineEmits, defineExpose } from "vue";

const isOpen = ref(false);
const modalId = ref("comprehensiveTrainingReportId");

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
      <strong>현장실습 종합보고서</strong>
    </template>
    <template #default>
      <div class="personal-info-block">
        <div class="head">
          <div class="head__title">
            <span>현장실습 주간보고서</span>
            <span>홍길동(경영학과 1학년 2455026)</span>
          </div>
          <div class="head__dowload">
            <button type="button" class="button btn_white">PDF 다운로드</button>
          </div>
        </div>
        <div class="body">
          <div class="body__label">실습기관 소개,현장실습 내용,배운점과 소감,향후 진로계획,건의사항 등을 기재바랍니다.</div>
          <div class="body__content">
            <textarea name="" id="" cols="30" rows="10" placeholder="내용을 입력해 주세요. (최대 2,000자 제한)"></textarea>
          </div>
        </div>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_xl btn_primary" @click="cancelAction">확인</button>
    </template>
  </TModal>
</template>
<style lang="scss" scoped>
.personal-info-block {
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
  .body {
    &__label {
      font-size: 16px;
      font-weight: 500;
      color: var(--gray--1d);
      margin-bottom: 8px;
      position: relative;
      margin-left: 10px;
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
        @media screen and (max-width: 600px) {
          top: 25%;
          left: -8px;
        }
      }
    }
    &__content {
      font-size: 14px;
      textarea {
        width: 100%;
        max-height: 200px;
        color: var(--gray--1d);
        &::placeholder {
          color: var(--gray--be);
        }
      }
    }
  }
}
</style>
