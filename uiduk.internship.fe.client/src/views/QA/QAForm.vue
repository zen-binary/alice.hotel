<template>
  <div class="qa-container">
    <div class="form-create">
      <div class="form-create__input">
        <label class="required">{{ t("qna.createScreen.title") }}</label>
        <InputBase 
          required
          id="input_validate" 
          :placeholder="t('qna.createScreen.titleInputBasePlaceHolder')" 
          v-model="qnaRegReq.title" 
          :is-show-count-word="true" 
          :max-words="30"/>
      </div>
      <div class="form-create__input">
        <label class="required">{{ t("qna.createScreen.content") }}</label>
        <TextArea
          required
          v-model="qnaRegReq.content"
          class="text_validate"
          id="reszie_auto"
          :resize-auto="true"
          :is-show-count-word="true"
          :max-words="500"
          :placeholder="t('qna.createScreen.contentInputBasePlaceHolder')"
        />
      </div>
    </div>
  </div>
  <div class="btn-action">
    <button class="button btn_round btn_md btn_white" @click="back">{{ t("common.cancel") }}</button>
    <button class="button btn_round btn_md btn_primary" @click="submit" :disabled="!qnaRegReq.title || !qnaRegReq.content">{{ t("qna.createScreen.buttonCreate") }}</button>
  </div>
  <TModalConfirm :is-open="modalOpenConfirm" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
    <!-- Modal content goes here -->
    <template #title>
      <strong>개인정보 수집 및 이용에 대한 동의</strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ t("qna.createScreen.alert.alert1.title") }}</p>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button type="button" class="button btn_md btn_primary" @click="confirmAction">{{ t("common.confirm") }}</button>
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
        <p>{{ t("qna.createScreen.alert.alert2.title") }}</p>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button type="button" class="button btn_md btn_primary" @click="cancelAction2">{{ t("common.confirm") }}</button>
    </template>
  </TModalAlert>
</template>
<script lang="ts" setup>
import { SCREEN } from "@/router/screen";
import { qnaStore } from "@/stores/qna";
import { QnaRegReqDTO } from "@/stores/qna/qna.type";
import { useRouter } from "vue-router";
import { ref } from "vue";
import { commonStore } from "@/stores/common";
import { SUCCSESS_STATUS } from "@/constants/screen.const";
import { useI18n } from "vue-i18n";

const { t } = useI18n();
const router = useRouter();
const qaStore = qnaStore();
const modalOpenConfirm = ref(false);
const modalOpenAlert = ref(false);
const modalOpen = ref(false);
const modalId = ref("modalId");
const qnaRegReq = ref<QnaRegReqDTO>({
  title: "",
  content: "",
});

const submit = () => {
  modalOpenConfirm.value = true;
};

const confirmAction = async () => {
  await qaStore.fetchCreateQna({
    method: "POST",
    body: qnaRegReq.value,
  })
  if (qaStore.status.value === SUCCSESS_STATUS) {
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
</script>
<style lang="scss" scoped>
.form-create {
  &__input {
    display: flex;
    @media screen and (max-width: 768px) {
      flex-direction: column;
    }
    &:first-child {
      align-items: center;
      margin-bottom: 24px;
    }
    .qa-textarea {
      min-height: 329px;
      @media screen and (max-width: 768px) {
        min-height: 200px;
      }
    }
    label {
      width: 10%;
      color: var(--gray--1d);
      font-size: 14px;
      font-weight: 500;
      @media screen and (max-width: 768px) {
        width: 100%;
        margin-bottom: 8px;
      }
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
  }
}
</style>
