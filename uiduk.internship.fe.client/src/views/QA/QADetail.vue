<template>
  <div class="qa-container">
    <div class="question">
      <div>
        <h2 class="question__title">{{ qnaDetail?.title }}</h2>
        <span class="datetime">{{ qnaDetail?.regDate }}</span>
      </div>
      <div class="confirm-answer">
        <!-- <button class="btn_round btn_sm btn_bo_primary">{{ t("qna.detailScreen.confirmAnswer") }}</button> -->
        <button
          :class="qnaDetail?.answer === null ? 'btn_round btn_sm btn_bo_primary status-qa-no' : 'btn_round btn_sm btn_bo_primary status-qa-yes'"
        >
          {{ qnaDetail?.answer === null ? t("qna.listSreen.answerStatus_NO") : t("qna.listSreen.answerStatus_YES") }}
        </button>
      </div>
    </div>
    <div class="content">
      <TextArea v-if="qnaDetail" v-model="qnaDetail.content" :is-detail="true"></TextArea>
    </div>
    <div class="answer" v-if="qnaDetail?.answer !== null">
      <IconRectangle class="icon"></IconRectangle>
      <div class="answer__outline">
        <div class="title">
          <div>{{ qnaDetail?.modifyId }}</div>
          <div class="datetime">{{ qnaDetail?.modifyDate }}</div>
        </div>
        <!-- <div class="paragraph" v-html="qnaDetail?.answer"></div> -->
         <div class="paragraph">
          <TextArea v-if="qnaDetail" v-model="qnaDetail.answer" :is-detail="true"></TextArea>
         </div>
      </div>
    </div>
  </div>
  <div class="btn-action">
    <button class="button btn_round btn_md btn_white" @click="back">{{ t("common.title.list") }}</button>
    <button v-if="qnaDetail?.answer === null" class="button btn_round btn_md btn_bo_primary" @click="modalOpenConfirm = true">
      {{ t("common.delete") }}
    </button>
  </div>
  <TModalConfirm :is-open="modalOpenConfirm" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
    <!-- Modal content goes here -->
    <template #title>
      <strong>개인정보 수집 및 이용에 대한 동의</strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ t("qna.detailScreen.alert1.title") }}</p>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button type="button" class="button btn_md btn_primary" @click="confirmAction(id)">{{ t("common.confirm") }}</button>
      <button type="button" class="button btn_md btn_white popup_close" @click="cancelAction1">{{ t("common.cancel") }}</button>
    </template>
  </TModalConfirm>
  <TModalAlert :is-open="modalOpenAlert" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
    <!-- Modal content goes here -->
    <template #title>
      <strong>개인정보 수집 및 이용에 대한 동의</strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ t("qna.detailScreen.alert2.title") }}</p>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button type="button" class="button btn_md btn_primary" @click="cancelAction2">{{ t("common.confirm") }}</button>
    </template>
  </TModalAlert>
</template>
<script lang="ts" setup>
import IconRectangle from "@/components/icon/IconRectangle.vue";
import { onBeforeMount } from "vue";
import { ref } from "vue";
import { SCREEN } from "@/router/screen";
import { useRouter } from "vue-router";
import { format } from "date-fns";
import { FORMAT_YYY_MM_DD_HH_MM_SS } from "../../constants/screen.const";
import { qnaStore } from "@/stores/qna";
import { QnaDetailResDTO } from "@/stores/qna/qna.type";
import { useI18n } from "vue-i18n";
import TextArea from "@/components/common/input/TextArea.vue";

const { t } = useI18n();
const router = useRouter();
const qaStore = qnaStore();
const qnaDetail = ref<QnaDetailResDTO>();
const modalOpenConfirm = ref(false);
const modalId = ref("modalId");
const modalOpen = ref(false);
const modalOpenAlert = ref(false);
const id = window.history.state.id;

const getQnaDetail = async (id: string) => {
  await qaStore.fetchQnaDetail({
    method: "POST",
    body: id,
    headers: {
      "Content-Type": "text/plain",
    },
  });

  qnaDetail.value = qaStore.qnaDetail;
  qnaDetail.value.regDate = format(new Date(qnaDetail.value.regDate), FORMAT_YYY_MM_DD_HH_MM_SS);
  qnaDetail.value.modifyDate = format(new Date(qnaDetail.value.modifyDate), FORMAT_YYY_MM_DD_HH_MM_SS);
};

const confirmAction = async (id: string) => {
  await qaStore.fetchDeleteQna({
    method: "DELETE",
    body: id,
    headers: {
      "Content-Type": "text/plain",
    },
  });
  if (qaStore.status === "200001") {
    modalOpenConfirm.value = false;
    modalOpenAlert.value = true;
  }
};

const cancelAction1 = () => {
  modalOpen.value = false;
  modalOpenConfirm.value = false;
};

const cancelAction2 = () => {
  modalOpenAlert.value = false;
  back();
};

const closeModal = () => {
  modalOpen.value = false;
  modalOpenConfirm.value = false;
  modalOpenAlert.value = false;
};

const back = () => {
  const mode = "";
  router.push({
    name: SCREEN.qaCRUD.name,
    params: { path: mode },
  });
};

onBeforeMount(() => {
  const id = window.history.state.id;
  if (id) {
    getQnaDetail(id);
  } else {
    back();
  }
});
</script>
<style lang="scss" scoped>
.datetime {
  font-size: 14px;
  font-weight: 400;
  color: var(--gray--71);
}
.question {
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid var(--gray--f8);
  padding-bottom: 40px;
  @media screen and (max-width: 768px) {
    flex-direction: column-reverse;
    padding-bottom: 28px;
    gap: 10px;
  }
  &__title {
    color: var(--gray--1d);
    font-weight: 500;
  }
  .btn_bo_primary {
    color: var(--Primary-50_Primary, #0098e5);
    background: var(--primary-opacirty-12, #0098e51f);
    border: none;
  }
}
.content,
.answer,
.question {
  padding-left: 16px;
  @media screen and (max-width: 768px) {
    padding-left: 8px;
  }
}
.content {
  margin: 40px 0;
  @media screen and (max-width: 768px) {
    margin: 28px 0;
  }
}
.answer {
  padding: 16px;
  background: var(--gray--f8);
  border-radius: 5px;
  display: flex;
  gap: 15px;
  @media screen and (max-width: 768px) {
    padding: 28px 8px;
  }

  &__outline {
    display: flex;
    flex-direction: column;
    .title {
      display: flex;
      gap: 10px;
      margin-bottom: 12px;
      color: var(--gray--1d);
    }
    .paragraph {
      color: var(--gray--1d);
      white-space: break-spaces;
      word-wrap: break-word;
    }
  }
}
.btn-action {
  display: flex;
  gap: 10px;
  justify-content: center;
  margin-top: 40px;
  @media screen and (max-width: 768px) {
    margin-top: 36px;
  }
  button {
    width: 78px;
    @media screen and (max-width: 768px) {
      width: fit-content;
    }
    &:nth-child(2) {
      color: var(--pink--85);
      border: 1px solid var(--pink--85);
    }
    .status-qa-no {
      background-color: #f2f2f2;
      color: #666666;
      padding: 5px 10px;
      border-radius: 5px;
    }
    .status-qa-yes {
      background-color: #e5f5ff;
      color: #39a8ff;
      padding: 5px 10px;
      border: none;
      border-radius: 5px;
      display: inline-block;
      text-align: center;
    }
  }
}
</style>
