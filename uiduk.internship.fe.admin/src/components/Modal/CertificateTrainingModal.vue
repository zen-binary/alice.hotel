<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<script setup lang="ts">
import { ref, defineEmits, defineExpose } from "vue";

const isOpen = ref(false);
const modalId = ref("certificateTrainingId");

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
  <TModal :classSize="`large`" :is-open="isOpen" :is-show-header="true" :is-show-footer="false" :modal-id="modalId" @close-modal="closeModal">
    <template #title>
      <strong>개인정보이용동의</strong>
    </template>
    <template #default>
      <div class="personal-info-block">
        <div class="head">
          <div class="head__title"></div>
          <div class="head__dowload">
            <button type="button" class="button btn_white">PDF 다운로드</button>
          </div>
        </div>
        <div class="body">
          <img src="@/assets/images/iconlogo.png" alt="Logo" class="image" />
          <div class="overlay-text">
            <div class="overlay-text__outline">
              <div class="title">산업체 현장실습 참가 증명서</div>
              <div class="content">
                <div class="outline">
                  <span>성명</span>
                  <span>홍길동</span>
                </div>
                <div class="outline">
                  <span>소속</span>
                  <span>위덕대학교 디자인학과(부)</span>
                </div>
                <div class="outline">
                  <span>주민등록번호</span>
                  <span>000000-0000000</span>
                </div>
                <div class="outline">
                  <span>현장실습 기관</span>
                  <span>(주)삼성디자인</span>
                </div>
                <div class="outline">
                  <span>현장실습 기간</span>
                  <span>2024.10.01~2024.10.30</span>
                </div>
                <div class="outline">
                  <span>현장실습 분야</span>
                  <span>전공분야</span>
                </div>
              </div>
            </div>
            <div class="overlay-text__certificate">
              <div class="title">위 사람은 위덕대학교가 시행하는 산업체 현장실습 교육과정에 참가하여 현장실습을 이수하였음을 증명함.</div>
              <div class="date">2024년 10월 1일</div>
              <div class="note">위덕대학교 현장실습지원 센터장</div>
            </div>
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
    @media screen and (max-width: 865px) {
      flex-direction: column-reverse;
      align-items: flex-start;
      gap: 10px;
      margin-bottom: 16px;
    }
    &__title {
      span {
        font-size: 16px;
        font-weight: 500;
        @media screen and (max-width: 865px) {
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
      @media screen and (max-width: 865px) {
        font-size: 12px;
      }
    }
  }
  .body {
    border: 1px solid var(--gray--e4);
    border-radius: 5px;
    max-height: 774px;
    height: 774px;
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;

    margin: auto;
    @media screen and (max-width: 865px) {
      height: 500px;
    }
    .image {
      position: absolute;
      width: 400px;
      height: 334px;
      object-fit: cover;
      left: 15%;
      right: 0;
      margin: 0 auto;
      @media screen and (max-width: 865px) {
        width: 200px;
        height: auto;
      }
    }

    .overlay-text {
      text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.7);
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      font-size: 18px;
      z-index: 1;
      color: var(--gray--1d);
      font-weight: 500;
      display: flex;
      flex-direction: column;
      align-items: center;
      &__outline {
        .title {
          font-size: 32px;
          margin-bottom: 60px;
          @media screen and (max-width: 865px) {
            font-size: 20px;
            margin-bottom: 20px;
            white-space: nowrap;
          }
        }
        .content {
          padding: 0;
          .outline {
            display: flex;
            margin-bottom: 24px;
            align-items: center;
            flex-direction: row;
            @media screen and (max-width: 865px) {
              margin-bottom: 12px;
            }
            span {
              font-size: 18px;
              @media screen and (max-width: 865px) {
                font-size: 14px;
              }
              &:first-child {
                width: 40%;
              }
              &:last-child {
                width: 60%;
              }
            }
          }
        }
      }
      &__certificate {
        text-align: center;
        margin-top: 60px;
        font-weight: 500;
        font-size: 18px;
        @media screen and (max-width: 865px) {
          margin-top: 20px;
          font-size: 14px;
        }
        .title {
          color: var(--gray--1d);
        }
        .date {
          margin: 24px;
          color: var(--primary--e5);
        }
        .note {
          color: var(--gray--55);
        }
      }
    }
  }
}
</style>
