<template>
  <PageContainer>
    <h1 v-once>
      {{ modeScreen === MODE_CREATE ? t("47.documentRoom.create.titleForm") : t("47.documentRoom.update.titleForm") }}
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
              <tr>
                <th scope="row" class="required">
                  {{ t("47.documentRoom.create.title") }}
                </th>
                <td>
                  <div class="wd_p50">
                    <InputBase
                      id="input_validate"
                      v-model="docRoomModel.title"
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
                  {{ t("47.documentRoom.create.content") }}             
                </th>
                <td>
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
                  {{ t("47.documentRoom.create.file") }}
                </th>
                <td>
                  <InputFileBase
                    :id="'idDocRoom'"
                    ref="childRef"
                    v-model="fileArr"
                    :refer-key="docRoomSeq"
                    :mode="modeScreen"
                    :name="'idDocRoom'"
                    :type="fileTypeAll"
                    :org-name="sectionName"
                    :category="fileTypeAll"
                    :section-name="sectionName"
                    :multiple="true"
                    :max-file="5"
                    :show-mess="false"
                    :max-size="20"
                  />
                  <span style="color: red">
                    {{ t("47.documentRoom.textFile") }}
                  </span>
                </td>
              </tr>

              <!-- Row 4 -->
              <tr>
                <th scope="row" class="required">
                  {{ t("47.documentRoom.create.useYn") }}
                </th>
                <td>
                  <div class="select_checkbox">
                    <RadiobuttonBase
                      v-for="itemT in listRadio"
                      :id="itemT.cdId+'_useYn'"
                      :key="itemT.cdId"
                      v-model="docRoomModel.useYn"
                      :value="itemT.cdId"
                      :name="itemT.cdId+'_useYn'"
                      :label="itemT.cdNm"
                      :checked="itemT.cdId == docRoomModel.useYn"
                    />
                  </div>
                </td>
              </tr>

              <!-- Row 5 -->
              <tr>
                <th scope="row" class="required">
                  {{ t("47.documentRoom.create.topFixYn") }}
                </th>
                <td>
                  <div class="select_checkbox">
                    <RadiobuttonBase
                      v-for="itemT in listRadio"
                      :id="itemT.cdId"
                      :key="itemT.cdId"
                      v-model="docRoomModel.topFixYn"
                      :value="itemT.cdId"
                      :name="itemT.cdId"
                      :label="itemT.cdNm"
                      :checked="itemT.cdId == 'Y'"
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
              {{ t("47.documentRoom.create.btnCancel") }}
            </button>
            <button
              type="button"
              class="btn_md btn_primary"
              :disabled="!isCheck"
              @click="modalOpenCreateConfirm = true"
            >
              {{ t("47.documentRoom.create.btnSave") }}
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
          <template #default>
            <div class="box_section">
              <p>{{ modeScreen === MODE_CREATE ? t("47.documentRoom.message.saveInsertSuccess") : t("47.documentRoom.message.saveUpdateSuccess") }}</p>
            </div>
          </template>
          <template #footer>
            <button
              type="button"
              class="button btn_md btn_primary popup_close"
              @click="back"
            >
              {{ t("47.documentRoom.modal.btnSave") }}
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
              <p> {{ modeScreen === MODE_CREATE ? t("47.documentRoom.message.confirmInsert") : t("47.documentRoom.message.confirmUpdate") }}</p>
            </div>
          </template>
          <template #footer>
            <button
              v-if="modeScreen === MODE_CREATE"
              type="button"
              class="button btn_md btn_primary"
              @click="saveOrEdit"
            >
              {{ t("47.documentRoom.modal.btnSave") }}
            </button>
            <button
              v-if="modeScreen === MODE_EDIT"
              type="button"
              class="button btn_md btn_primary"
              @click="saveOrEdit"
            >
              {{ t("47.documentRoom.modal.btnSave") }}
            </button>
            <button
              type="button"
              class="button btn_md btn_white popup_close"
              @click="cancelAction"
            >
              {{ t("47.documentRoom.modal.btnCancel") }}
            </button>
          </template>
        </TModalConfirm>
      </div>
    </div>
  </PageContainer>
</template>

<script setup lang="ts">
import {ref, onBeforeMount, onMounted, watch} from 'vue';
import {useI18n} from 'vue-i18n';
import InputBase from '@/components/common/input/InputBase.vue';
import InputFileBase from "@/components/common/input/InputFileBase.vue";
import {MODE_CREATE, MODE_EDIT, FILE_TYPE_ALL} from '@/constants/screen.const';
import {SCREEN} from '@/router/screen';
import type {DocRoomCreModel, DocIdModel} from '@/stores/docRoom/docRoom.type';
import PageContainer from "@/components/common/PageContainer.vue";
import RadiobuttonBase from "@/components/common/input/RadiobuttonBase.vue";
import {docRoomStore} from '@/stores/docRoom';
import {useRoute, useRouter} from "vue-router";
import TModalConfirm from "@/components/common/excel/TModalConfirm.vue";
import TModalAlert from "@/components/common/excel/TModalAlert.vue";
import ImageUploader from 'quill-image-uploader';
import { commonStore } from '@/stores/common';
import { uploadFileEditor } from "@/stores/common/fileMng/fileMng.service";

const {t} = useI18n();
const store = commonStore();
const route = useRoute();
const router = useRouter();
const drStore = docRoomStore();
const fileTypeAll = FILE_TYPE_ALL;
const docRoomSeq = ref("");
const modalOpenCreateConfirm = ref(false);
const modalCreateSuccess = ref(false);
const isCheck = ref(false);
const fileArrOld = ref([]);
const listRadio = ref([
  {cdId: "Y", cdNm: "사용" },
  {cdId: "N", cdNm: "미사용" },
]);

const closeModalCreateConfirm = () => {
  modalOpenCreateConfirm.value = false;
};

const closeModalCreateSuccess = () => {
  modalCreateSuccess.value = false;
}
const docRoomModel = ref<DocRoomCreModel>({
  docSeq: "",
  title: "",
  useYn: "Y",
  content: "",
  topFixYn: ""
});
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
          formData.append("orgName", "DOC_ROOM");
          formData.append("sectionName", "DOC_ROOM");
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
const docRoomCreModel = ref<DocRoomCreModel>({} as DocRoomCreModel);
const docIdModel = ref<DocIdModel>({} as DocIdModel);

const modeScreen = ref('');
const sectionName = 'DOCROOM';
const fileArr = ref([]);
const modFile = ref(MODE_CREATE);

const childRef = ref(null);
const myEditor = ref(null);

const modalOpenConfirm = ref(false);
const modalId = ref("modalId");

const titleCharacterCount = ref(0);

const updateCharacterCount = () => {
  titleCharacterCount.value = docRoomModel.value.title.length ? docRoomModel.value.title.length : 0;

};

onBeforeMount(() => {
  modeScreen.value = route.params.mode;
  docIdModel.value.docSeq = window.history.state.id;
  docRoomSeq.value = window.history.state.id;
  if (modeScreen.value === MODE_EDIT) {
    modFile.value = MODE_EDIT;
  }
  if (modeScreen.value === MODE_CREATE) {
    modFile.value = MODE_CREATE;
  }

});

onMounted(async () => {
  if (modeScreen.value === MODE_EDIT) {
    await loadData()
  }
});


const loadData = async () => {
  await drStore.getDocRoomDetail({
    method: 'POST',
    body: docIdModel.value
  });
  docRoomModel.value = drStore.list.data;

  updateCharacterCount();

  if (myEditor.value) {
    myEditor.value.setHTML(docRoomModel.value.content)
  }
  if(fileArr.value.length > 0){
    fileArrOld.value = [...fileArr.value];
  }
};

const isArrayEqual = (a, b) => 
  a.length === b.length && a.every((val, index) => val === b[index]);

async function checkData(decSeq: any){
  const childComponent = childRef.value;
  childComponent.upLoadFile(decSeq);
}

const back = () => {
  router.replace({path: SCREEN.docRoom.path});
};
function checkValid() {
  isCheck.value = !!(docRoomModel.value.title && docRoomModel.value.content && fileArr.value.length > 0);
}
const saveOrEdit = async () => {
  docRoomCreModel.value.title = docRoomModel.value.title;
  docRoomCreModel.value.content = docRoomModel.value.content;
  docRoomCreModel.value.topFixYn = docRoomModel.value.topFixYn.length > 0 ? docRoomModel.value.topFixYn : "Y";
  docRoomCreModel.value.useYn = docRoomModel.value.useYn.length > 0 ? docRoomModel.value.useYn : "Y";
  closeModalCreateConfirm();
  modalCreateSuccess.value = true;
  if (modeScreen.value === MODE_EDIT) {
    docRoomCreModel.value.docSeq = docIdModel.value.docSeq;
    await drStore.updateDocRoom({
      method: 'POST',
      body: docRoomCreModel.value
    });
    if(!isArrayEqual(fileArr.value, fileArrOld.value)){
      checkData(docRoomCreModel.value.docSeq);
    }
  } else if (modeScreen.value === MODE_CREATE) {
    await drStore.saveDocRoom({
      method: 'POST',
      body: docRoomCreModel.value
    });
    checkData(drStore.list.data);
  }
};

const changeEditor = () => {
  if (myEditor.value) {
    docRoomModel.value.content = myEditor.value.getHTML().toString().replace("<p><br></p>", "");
  }
}

const cancelAction = () => {
  modalOpenConfirm.value = false;
  closeModalCreateSuccess();
  closeModalCreateConfirm();
};
watch(() => fileArr, checkValid, { deep: true });
watch(() => docRoomModel, checkValid, { deep: true });
</script>