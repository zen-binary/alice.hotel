<template>
  <PageContainer>
    <h1 v-once>
      {{ t("qna.update.titleForm") }}
    </h1>
    <div class="box dp_block">
      <div class="box_section">
        <div class="tbl tbl_row tbl_scroll scrollx_tbl_xs">
          <table>
            <colgroup>
              <col style="width: 15%">
              <col style="width: auto" />
            </colgroup>
            <tbody>
              <!-- Row 1 -->
              <tr>
                <th scope="row">{{ t("qna.update.regId") }}</th>
                <td>
                  <div class="wd_p50">
                    <InputBase
                      :id="'name'"
                      v-model="qnaResModel.regId"
                      :name="'name'"
                      :mode="'show'"
                      readonly
                    />
                  </div>
                </td>
              </tr>
              <!-- Row 2 -->
              <tr>
                <th scope="row">{{ t("qna.update.phone") }}</th>
                <td>
                  <div class="wd_p50">
                    <InputBase
                      :id="'phone'"
                      v-model="qnaResModel.phone"
                      :name="'phone'"
                      :mode="'show'"
                      readonly
                    />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row">{{ t("qna.update.email") }}</th>
                <td>
                  <div class="wd_p50">
                    <InputBase
                      :id="'email'"
                      v-model="qnaResModel.email"
                      :name="'email'"
                      :mode="'show'"
                      readonly
                    />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row">
                    {{ t("qna.update.title") }}
                </th>
                <td>
                  <div class="wd_p50">
                    <InputBase
                      :id="'title'"
                      v-model="qnaResModel.title"
                      :name="'title'"
                      :mode="'show'"
                      readonly
                    />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row">
                    {{ t("qna.update.content") }}
                </th>
                <td>
                  <TextArea
                    :id="'content'"
                    v-model="qnaResModel.content"
                    :name="'content'"
                    readonly                 
                  />
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <div class="box dp_block">
      <div class="box_section">
        <div class="tbl tbl_row tbl_scroll scrollx_tbl_xs">
          <table>
            <colgroup>
              <col style="width: 15%">
              <col style="width: auto" />
            </colgroup>
            <tbody>
              <tr>
                <th scope="row" class="required">                
                    {{ t("qna.update.answer") }}            
                </th>
                <td colspan="3">
                  <TextArea
                    id="text_validate"
                    v-model="qnaResModel.answer"
                    :name="'answer'"
                    :is-show-count-word="true" 
                    :max-words="500"
                    :placeholder="t('qna.placeholder.answer')"
                    required
                  />
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <!-- button action -->
        <TModalAlert
          :is-open="modalCreateSuccess"
          :is-show-header="true"
          :is-show-footer="true"
          :modal-id="modalId"
          @close-modal="closeModalCreateSuccess"
        >
          <!-- Modal content goes here -->
          <template #default>
            <div class="box_section">
              <p>{{ answerText ? t("qna.message.saveUpdateSuccess") : t("qna.message.saveInsertSuccess") }}</p>
            </div>
          </template>
          <template #footer>
            <!-- Footer content goes here -->
            <button
              type="button"
              class="button btn_md btn_primary popup_close"
              @click="back"
            >
              {{ t("qna.update.btnSave") }}
            </button>
          </template>
        </TModalAlert>
        <TModalConfirm
          :is-open="modalOpenCreateConfirm"
          :is-show-header="true"
          :is-show-footer="true"
          :modal-id="modalId"
          @close-modal="closeModalCreateConfirm"
        >
          <template #default>
            <div class="box_section">
              <p> {{ answerText ? t("qna.message.confirmUpdate") : t("qna.message.confirmInsert") }}</p>
            </div>
          </template>
          <template #footer>
            <button
              v-if="modeScreen === MODE_EDIT"
              type="button"
              class="button btn_md btn_primary"
              @click="editData"
            >
              {{ t("qna.update.btnSave") }}
            </button>
            <button
              type="button"
              class="button btn_md btn_white popup_close"
              @click="cancelAction"
            >
              {{ t("qna.update.btnCancel") }}
            </button>
          </template>
        </TModalConfirm>
      </div>
    </div>
    <div class="box_section">
      <!-- create -->
      <div
        class="btn_group btn_end mg_t15"
      >
        <button
          type="button"
          class="btn_md btn_lightgray"
          @click="back()"
        >
          {{ t("qna.update.btnCancel") }}
        </button>
        <button
          v-if="answerText"
          type="button"
          :disabled="!isCheck"
          class="btn_md bg-red"
          @click="modalOpenCreateConfirm = true"
        >
          {{ t("qna.update.btnEdit1") }}
        </button>
        <button
          v-else
          type="button"
          :disabled="!isCheck"
          class="btn_md btn_primary"
          @click="modalOpenCreateConfirm = true"
        >
          {{ t("qna.update.btnEdit2") }}
        </button>
      </div>
    </div>
  </PageContainer>
</template>
  
  <script setup lang="ts">
  import { ref, watch, onBeforeMount, onMounted } from "vue";
  import { MODE_CREATE, MODE_EDIT, MODE_SHOW } from "@/constants/screen.const";
  import { SCREEN } from "@/router/screen";
  import { useRouter, useRoute } from "vue-router";
  import { useI18n } from "vue-i18n";
  import { QnaDetailResDTO, QnaReqDTO } from "@/stores/qna/qna.type";
  import { qnaStore } from "@/stores/qna";
  import TextArea from "@/components/common/input/TextArea.vue";
  const { t } = useI18n();
  
  const router = useRouter();
  const storeQna = qnaStore();
  const route = useRoute();
  const qnaSeq = ref("");
  const mode = ref(MODE_SHOW);
  const displayOrderCreateValue = ref("");
  const isCheck = ref(false);
  const modeScreen = ref("");
  const qnaResModel = ref({} as QnaDetailResDTO);
  const qnaReqModel = ref({} as QnaReqDTO);
  const modalCreateSuccess = ref(false);
  const modalId = ref("modalId");
  const modalOpenCreateConfirm = ref(false);
  const answerText = ref("");
  const closeModalCreateConfirm = () => {
    modalOpenCreateConfirm.value = false;
  };
  const closeModalCreateSuccess = () => {
    modalCreateSuccess.value = false;
  }
  const modalErrorDate = ref(false);
  const cancelAction = () => {
    closeModalCreateSuccess();
    closeModalCreateConfirm();
    modalErrorDate.value = false;
  };

  function checkValid() {
    isCheck.value = !!(qnaResModel.value.answer);
  }
  
  function back() {
    router.replace({ path: SCREEN.qna.path });
  }
  
  function getFormData() {
    qnaReqModel.value.qnaSeq = qnaSeq.value;
    qnaReqModel.value.answer = qnaResModel.value.answer;
  }
  
  
  async function editData() {
    closeModalCreateConfirm();;
    modalCreateSuccess.value = true;
    getFormData();
    await storeQna.updateQna({
      method: "PUT",
      body: qnaReqModel.value,
    });
  }
  onBeforeMount(() => {
    modeScreen.value = Array.isArray(route.params.mode) ? route.params.mode[0] : route.params.mode;
    qnaSeq.value = window.history.state.qnaSeq;
    if (modeScreen.value === MODE_EDIT) {
      mode.value = MODE_EDIT;
    }
  });
  
  
  onMounted(async () => {
    if (modeScreen.value === MODE_EDIT) {
      await storeQna.qnaDetail({
        method: "POST",
        body: window.history.state.qnaSeq,
      });
      if (storeQna.dataId) {
        qnaResModel.value = storeQna.dataId.data;
        answerText.value = qnaResModel.value.answer;
      }
    }
  });
  watch(() => qnaResModel, checkValid, { deep: true });
  watch(displayOrderCreateValue, checkValid);
  </script>
  