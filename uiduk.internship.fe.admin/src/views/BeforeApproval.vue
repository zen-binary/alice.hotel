<template>
  <PageContainer>
    <div class="box dp_block">
      <h2 class="center-text">{{ t("beforeApproval.title") }}</h2>
      <TModalAlert :is-open="modalOpenAlert" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
        <template #title>
          <strong>{{ t("beforeApproval.titleModal") }}</strong>
        </template>
        <template #default>
          <div class="box_section">
            <p>{{ t("beforeApproval.contentModal") }}</p>
          </div>
        </template>
        <template #footer>
          <button type="button" class="button btn_md btn_primary" @click="cancelAction">{{ t("common.confirm") }}</button>
        </template>
      </TModalAlert>
    </div>
  </PageContainer>
</template>
<script setup lang="ts">
import { ref } from "vue";
import { useI18n } from "vue-i18n";
import {commonStore} from "@/stores/common";

const store = commonStore()
const modalOpenAlert = toRef(store, "isCheckRole");
const modalId = ref("modalId");
const { t } = useI18n();

const closeModal = () => {
  store.setCheckRole(false)
};
const cancelAction = () => {
  closeModal();
};
</script>
<style lang="scss">
.center-text {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 700px;
  margin: 0;
  text-align: center;
  color: red;
}
</style>
