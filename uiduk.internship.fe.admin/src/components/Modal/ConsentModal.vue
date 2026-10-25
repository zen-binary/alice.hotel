<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <TModal :classSize="`large`" :is-open="isOpen" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
    <template #title>
      <strong>개인정보이용동의</strong>
    </template>
    <template #default>
      <div class="consent-personal-info">
        <p class="title-header">현장실습 설문조사</p>
        <div class="paragraph">
          <div class="title">관련법규</div>
          <div class="text">개인정보 보호법 제15조, 제18조 제2항·제3항</div>
        </div>
        <div class="paragraph">
          <div class="title">개인정보 수집·이용 동의</div>
          <div class="body">
            <p>
              - 개인정보 항목
              <br />
              - 개인정보 항목
              <br />
              - 수집근거
              <br />
              - 현장실습보험가입,현장실습지원비 지급
              <br />
              - 성명·학번·주민등록번호·전화번호·은행명·계좌번호
              <br />
              - 대학생 현장실습운영규정 16조 2항(학생보호)·대학생 현장실습운영규정 7조(현장실습지원비
            </p>
          </div>
          <div class="note">
            <div class="question">상기와 같이 개인정보 수집·활용에 동의하십니까?</div>
            <div class="option">
              <RadiobuttonBase
                v-for="item in listRadio1"
                :id="item.id"
                :key="item.id"
                v-model="radiovalue1"
                :value="item.name"
                :name="item.id"
                :checked="item.id == radiovalue1"
                :label="item.name"
              ></RadiobuttonBase>
            </div>
          </div>
        </div>
        <div class="paragraph">
          <div class="title">제 3자의 제공</div>
          <div class="body">
            <p>
              - 제공목적
              <br />
              - 제공항목
              <br />
              - 제공받는 자
              <br />
              - 이용기간
              <br />
              - 현장실습보험가입·현장실습지원비 지급
              <br />
              - 성명·주민등록번호·은행명·계좌번호
              <br />
              - 가입 보험회사·대구은행 - 현장실습비 지급 종료일
            </p>
          </div>
          <div class="note">
            <div class="question">상기와 같이 개인정보 수집·활용에 동의하십니까?</div>
            <div class="option">
              <RadiobuttonBase
                v-for="item in listRadio2"
                :id="item.id"
                :key="item.id"
                v-model="radiovalue2"
                :value="item.name"
                :name="item.id"
                :checked="item.id == radiovalue2"
                :label="item.name"
              ></RadiobuttonBase>
            </div>
          </div>
        </div>
        <div class="paragraph-note">
          <div class="body">
            <p>개인정보 제공 동의 거부 권리 및 동의 거부에 따른 불이익 내용 또는 제한사항</p>
            <p>
              - 귀하는 개인정보제공동의를 거부할 권리가 있음
              <br />
              - 단, 개인정보 수집·활용에 동의하지 않을 경우 현장실습 참여제한 등의 불이익을 받을 수 있음
            </p>
            <p>
              개인정보 제공자가 동의한 내용 외의 다른 목적으로 활용하지 않으며, 제공된 개인정보의 이용을 거부하고자 할 때에는
              <br />
              개인정보 관리책임자를 통해 열람, 정정, 삭제를 요구할 수 있음
            </p>
          </div>
        </div>
        <div class="commit">
          <div class="title">“개인정보보호법” 등 관련 법규에 의거하여 상기 본인은 위와같이 개인정보 수집 및 활용에 동의함</div>
          <div class="date">
            <span>2024년 10월 1일</span>
            <span>홍길동(경영학과 1학년 2412345)</span>
          </div>
          <div class="note">위덕대학교 현장실습지원센터장 귀하</div>
        </div>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_xl btn_white popup_close" @click="confirmAction">닫기</button>
      <button type="button" class="button btn_xl btn_primary" @click="cancelAction">확인</button>
    </template>
  </TModal>
</template>
<script setup lang="ts">
import { ref, defineEmits, defineExpose } from "vue";

const isOpen = ref(false);
const modalId = ref("consentModalId");

const emit = defineEmits(["confirm", "cancel"]);

const closeModal = () => {
  isOpen.value = false;
};
const confirmAction = () => {
  emit("confirm");
  closeModal();
};
const cancelAction = () => {
  emit("cancel");
  closeModal();
};

const openModal = () => {
  isOpen.value = true;
};
const radiovalue1 = ref("3");
const radiovalue2 = ref("5");
const listRadio1 = ref([
  { id: "3", name: "동의합니다" },
  { id: "4", name: "동의하지 않습니다" },
]);
const listRadio2 = ref([
  { id: "5", name: "동의합니다" },
  { id: "6", name: "동의하지 않습니다" },
]);
defineExpose({
  openModal,
});
</script>
