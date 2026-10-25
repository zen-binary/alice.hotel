<template>
  <PageContainer>
    <h1 v-once>
      {{ modeScreen === MODE_CREATE ? t("notice.create.titleForm") : t("notice.update.titleForm") }}
    </h1>
    <div class="box dp_block">
      <div class="box_section">
        <div class="tbl tbl_row tbl_scroll scrollx_tbl_xs">
          <table>
            <colgroup>
              <col style="width: 15%">
              <col style="width: auto" />
            </colgroup>
            <!-- form data -->
            <tbody>
              <!-- Row 1 -->
              <tr>
                <th scope="row" class="required">
                    {{ t("notice.create.title") }}
                </th>
                <td>
                  <div class="wd_p50">
                    <InputBase
                      id="input_validate"
                      v-model="noticeResModel.title"
                      class="custom-ip"
                      :name="'name'"
                      :is-show-count-word="true"
                      :max-words="30"
                      required                      
                    />
                  </div>
                </td>
              </tr>
              <!-- Row 2 -->
              <tr>
                <th scope="row" class="required">
                    {{ t("notice.create.content") }}
                </th>
                <td>
                  <div class="wd_p50"></div>
                  <QuillEditor
                    ref="myEditor"
                    class="custom-quill-editor"
                    :modules="modules"
                    toolbar="full"
                    @editor-change="changeEditor()"
                  />
                </td>
              </tr>
              <!-- Row 3 -->
              <tr>
                <th scope="row" class="required">
                    {{ t("notice.create.file") }}
                </th>
                <td>
                  <div class="wd_p50">
                    <InputFileBase
                      :id="'idNotice'"
                      ref="childRef"
                      v-model="fileArr"
                      :refer-key="noticeSeq"
                      :mode="modeScreen"
                      :name="'idNotice'"
                      :type="fileTypeAll"
                      :org-name="sectionName"
                      :category="fileTypeAll"
                      :section-name="sectionName"
                      :multiple="true"
                      :max-file="5"
                      :show-mess="true"
                    />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">
                    {{ t("notice.create.timeShow") }}
                </th>
                <td>
                  <div class="search_flex">
                    <BaseDatePicker
                      id="startDateShow_validate"
                      v-model="noticeResModel.startDateShow"
                      :placeholder="t('notice.placeholder.startDate')"
                      required
                    />
                    <div style="padding: 10px;">
                      ~
                    </div>
                    <BaseDatePicker
                      id="endDateShow_validate"
                      v-model="noticeResModel.endDateShow"
                      :placeholder="t('notice.placeholder.endDate')"
                      required
                    />
                  </div>
                </td>
              </tr>

              <!-- Row 4 -->
              <tr>
                <th scope="row" class="required">
                    {{ t("notice.create.useYn") }}
                </th>
                <td>
                  <div class="select_checkbox">
                    <RadiobuttonBase
                      v-for="item in listRadio1"
                      :id="item.cdId"
                      :key="item.cdId"
                      v-model="noticeResModel.useYn"
                      :value="item.cdId"
                      :name="item.cdId"
                      :label="item.cdNm"
                      :checked="item.cdId == 'Y'"
                    />
                  </div>
                </td>
              </tr>
              <!-- Row 5 -->
              <tr>
                <th scope="row"  class="required">
                    {{ t("notice.create.topFixYn") }}
                </th>
                <td>
                  <div class="select_checkbox">
                    <RadiobuttonBase
                      v-for="itemT in listRadio2"
                      :id="itemT.cdId"
                      :key="itemT.cdId"
                      v-model="noticeResModel.topFixYn"
                      :value="itemT.cdId"
                      :name="itemT.cdId"
                      :label="itemT.cdNm"
                      :checked="itemT.cdId == '1'"
                    />
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <!-- button action -->
        <div class="box_section">
          <!-- create -->
          <div
            v-if="modeScreen === MODE_CREATE"
            class="btn_group btn_end mg_t15"
          >
            <button
              type="button"
              class="btn_md btn_lightgray"
              @click="back()"
            >
              {{ t("notice.create.btnCancel") }}
            </button>
            <button
              type="button"
              class="btn_md btn_primary"
              :disabled="!isCheck"
              @click="modalOpenCreateConfirm = true"
            >
              {{ t("notice.create.btnSave") }}
            </button>
          </div>

          <!-- edit -->
          <div
            v-if="modeScreen === MODE_EDIT"
            class="btn_group btn_end mg_t30"
          >
            <button
              type="button"
              class="btn_md btn_lightgray"
              @click="back()"
            >
              {{ t("common.cancel") }}
            </button>
            <button
              type="button"
              class="btn_md btn_primary"
              :disabled="!isCheck"
              @click="modalOpenCreateConfirm = true"
            >
              {{ t("common.save") }}
            </button>
          </div>
        </div>
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
              <p>{{ modeScreen === MODE_CREATE ? t("notice.message.saveInsertSuccess") : t("notice.message.saveUpdateSuccess") }}</p>
            </div>
          </template>
          <template #footer>
            <!-- Footer content goes here -->
            <button
              type="button"
              class="button btn_md btn_primary popup_close"
              @click="back"
            >
              {{ t("notice.modal.btnSave") }}
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
              <p> {{ modeScreen === MODE_CREATE ? t("notice.message.confirmInsert") : t("notice.message.confirmUpdate") }}</p>
            </div>
          </template>
          <template #footer>
            <button
              v-if="modeScreen === MODE_CREATE"
              type="button"
              class="button btn_md btn_primary"
              @click="saveData"
            >
              {{ t("notice.modal.btnSave") }}
            </button>
            <button
              v-if="modeScreen === MODE_EDIT"
              type="button"
              class="button btn_md btn_primary"
              @click="editData"
            >
              {{ t("notice.modal.btnSave") }}
            </button>
            <button
              type="button"
              class="button btn_md btn_white popup_close"
              @click="cancelAction"
            >
              {{ t("notice.modal.btnCancel") }}
            </button>
          </template>
        </TModalConfirm>
        <TModalAlert
          :is-open="modalErrorDate"
          :is-show-header="true"
          :is-show-footer="true"
          :modal-id="modalId"
          @close-modal="modalErrorDate = false"
        >
          <!-- Modal content goes here -->
          <template #default>
            <div class="box_section">
              <p>{{ t('notice.message.validateTime') }}</p>
            </div>
          </template>
          <template #footer>
            <!-- Footer content goes here -->
            <button
              type="button"
              class="button btn_md btn_primary"
              @click="cancelAction"
            >
              {{ t("notice.modal.btnSave") }}
            </button>
          </template>
        </TModalAlert>
      </div>
    </div>
  </PageContainer>
</template>

<script setup lang="ts">
import { ref, watch, onBeforeMount, onMounted } from "vue";
import { FILE_TYPE_ALL, FORMAT_YYY_MM_DD, MODE_CREATE, MODE_EDIT, MODE_SHOW, SUCCSESS_STATUS } from "@/constants/screen.const";
import { SCREEN } from "@/router/screen";
import { useRouter, useRoute } from "vue-router";
import { useI18n } from "vue-i18n";
import InputBase from "@/components/common/input/InputBase.vue";
import InputFileBase from '@/components/common/input/InputFileBase.vue';
import { NoticeDetailResDTO, NoticeReqDTO } from "@/stores/notice/notice.type";
import { noticeStore } from "@/stores/notice";
import { format } from "date-fns";
import ImageUploader from 'quill-image-uploader';
import { uploadFileEditor } from "@/stores/common/fileMng/fileMng.service";
import { commonStore } from "@/stores/common";

const { t } = useI18n();
const router = useRouter();
const storeNotice = noticeStore();
const route = useRoute();
const noticeSeq = ref("");
const mode = ref(MODE_SHOW);
const displayOrderCreateValue = ref("");
const isCheck = ref(false);
const modeScreen = ref("");
const noticeResModel = ref({} as NoticeDetailResDTO);
const noticeReqModel = ref({} as NoticeReqDTO);
const fileArr = ref([]);
const fileArrOld = ref([]);
const fileTypeAll = FILE_TYPE_ALL;
const childRef = ref(null);
const myEditor = ref(null);
const sectionName = 'NOTICE';
const modalCreateSuccess = ref(false);
const modalId = ref("modalId");
const modalOpenCreateConfirm = ref(false);
const store = commonStore();
const modules = {
  name: "imageUploader",
  module: ImageUploader,
  options: {
    upload: (file: any) => {
      return new Promise((resolve, reject) => {
        if (!file) {
          reject("Upload failed");
        } else {
          store.setLoading(true);
          const formData = new FormData();
          formData.append("file", file);
          formData.append("category", "img");
          formData.append("orgName", "NOTICE");
          formData.append("sectionName", "NOTICE");
          uploadFileEditor(formData)
            .then((res: any) => {
              resolve(res.data.data.urlFile);
              store.setLoading(false);
            })
            .catch(() => {
              reject("Upload failed");
            });
        }
      });
    },
  },
};
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
const listRadio1 = ref([
        {
          cdId: "Y",
          cdNm: "사용",
        },
        {
          cdId: "N",
          cdNm: "미사용",
        },
]);
const listRadio2 = ref([
        {
          cdId: "1",
          cdNm: "사용",
        },
        {
          cdId: "2",
          cdNm: "미사용",
        },
]);
function checkValid() {
  isCheck.value = !!(noticeResModel.value.title && noticeResModel.value.content && noticeResModel.value.startDateShow && noticeResModel.value.endDateShow && fileArr.value.length > 0);
}

function back() {
  router.replace({ path: SCREEN.notice.path });
}

function getFormData() {
  noticeReqModel.value.noticeSeq = noticeSeq.value;
  noticeReqModel.value.title = noticeResModel.value.title;
  noticeReqModel.value.content = noticeResModel.value.content;
  noticeReqModel.value.startDateShow = noticeResModel.value.startDateShow;
  noticeReqModel.value.endDateShow = noticeResModel.value.endDateShow;
  noticeReqModel.value.useYn = noticeResModel.value.useYn ?? "Y";
  noticeReqModel.value.topFixYn = noticeResModel.value.topFixYn === "2" ? "N" : "Y";
}
const changeEditor = () => {
  if (myEditor.value) {
    noticeResModel.value.content = myEditor.value.getHTML().toString().replace("<p><br></p>", "");
  }
}
async function saveData() {
  closeModalCreateConfirm();
  modalCreateSuccess.value = true;
  getFormData();
  await storeNotice
    .noticeCreate({
      method: "POST",
      body: noticeReqModel.value,
    })
    .then(() => {
      if (storeNotice.status?.value && storeNotice.status?.value == SUCCSESS_STATUS) {        
        noticeSeq.value = storeNotice.res.data
        createFile(noticeSeq);
      }
    });
}

async function editData() {
  closeModalCreateConfirm();
  modalCreateSuccess.value = true;
  getFormData();
  await storeNotice.updateNotice({
    method: "PUT",
    body: noticeReqModel.value,
  });
  if (storeNotice.status?.value && storeNotice.status?.value == SUCCSESS_STATUS) {
    if(!isArrayEqual(fileArr.value, fileArrOld.value)){
      createFile(noticeSeq);
    }
  }
}
async function createFile(id: string) {
  const childComponent = childRef.value;
  childComponent.upLoadFile(id.value);
}
const isArrayEqual = (a, b) => 
  a.length === b.length && a.every((val, index) => val === b[index]);

onBeforeMount(() => {
  modeScreen.value = Array.isArray(route.params.mode) ? route.params.mode[0] : route.params.mode;
  noticeSeq.value = window.history.state.noticeSeq;
  if (modeScreen.value === MODE_EDIT) {
    mode.value = MODE_EDIT;
  }
});


onMounted(async () => {
  if (modeScreen.value === MODE_EDIT) {
    await storeNotice.noticeDetail({
      method: "POST",
      body: window.history.state.noticeSeq,
    });
    if (storeNotice.dataId) {
      noticeResModel.value = storeNotice.dataId.data;
      noticeResModel.value.topFixYn = noticeResModel.value.topFixYn ==="Y" ? "1" : "2";
      if (myEditor.value) {
        myEditor.value.setHTML(noticeResModel.value.content);
      }
    }
    if(fileArr.value.length > 0){
      fileArrOld.value = [...fileArr.value];
    }
  }
});
watch(() => fileArr, checkValid, { deep: true });
watch(() => noticeResModel, checkValid, { deep: true });
watch(displayOrderCreateValue, checkValid);
watch(
  () => noticeResModel.value.startDateShow,
  (newValue) => {
    if (!noticeResModel.value.endDateShow) {
      noticeResModel.value.startDateShow = newValue;
    } else {
      const dStart = format(newValue, FORMAT_YYY_MM_DD);
      const dEnd = format(noticeResModel.value.endDateShow, FORMAT_YYY_MM_DD);
      if (dStart > dEnd) {
        noticeResModel.value.startDateShow = noticeResModel.value.endDateShow;
        modalErrorDate.value = true;
      }
    }
  },
);

watch(
  () => noticeResModel.value.endDateShow,
  (newValue) => {
    if (!noticeResModel.value.startDateShow) {
      noticeResModel.value.endDateShow = newValue;
    } else {
      const dStart = format(noticeResModel.value.startDateShow, FORMAT_YYY_MM_DD);
      const dEnd = format(newValue, FORMAT_YYY_MM_DD);     
      if (dStart > dEnd) {
        noticeResModel.value.endDateShow = noticeResModel.value.startDateShow;
        modalErrorDate.value = true;
      }
    }
  },
);
</script>
<style>
  .custom-quill-editor .ql-editor {
  height: 200px;
  }
  .custom-ip {
    width: 500px;
  }
</style>
