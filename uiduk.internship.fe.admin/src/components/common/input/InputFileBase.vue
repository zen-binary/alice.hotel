<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div class="top">
    <form ref="fileForm" v-if="mode != modeDetail" class="file_form_style">
      <button type="button" :class="{ disable_button: mode != modeDetail }">
        <input
          ref="inputFile"
          type="file"
          :id="id"
          :name="name"
          :disabled="isDisable"
          @change="handleFileChange"
          :accept="acceptType"
          hidden
          :multiple="multiple"
        />
      </button>
    </form>
  </div>
  <div class="attach_filewrap" v-if="!previewFlag">
    <div class="attach_file_group">
      <label v-if="mode != modeDetail" class="btn_round2 btn_white btn_lg" :for="id">파일 추가</label>
      <p>{{ description }}</p>
    </div>
    <div class="attach_file_list">
      <div class="file_row" v-for="item in fileInfo" :key="item.fileUrl">
        <div class="file_name">
          <p>{{ item.fimOrgName }}</p>
          <p>{{ item.size }} KB</p>
          <!-- <span class="button_close" @click="removeFile(item.fileName)">close</span> -->
          <button type="button" class="btn_icon_close" @click="removeFile(item.fileName)"></button>
        </div>
      </div>
      <template v-if="mode == modeEdit">
        <div class="file_row" v-for="(item, index) in fileUploadedInfo" :key="index">
          <div class="file_name">
            <p>{{ item.fimOrgName }}</p>
            <button type="button" class="btn_icon_download" @click="confirmDowload(item.fileName, item.fimOrgName)"></button>
            <button type="button" class="btn_icon_close" @click="openModal(item)"></button>
          </div>
        </div>
      </template>
      <template v-if="mode == modeDetail">
        <div class="file_row" v-for="(item, index) in fileUploadedInfo" :key="index">
          <div class="file_name">
            <p>{{ item.fimOrgName }}</p>
            <button type="button" class="btn_icon_download" @click="confirmDowload(item.fileName, item.fimOrgName)"></button>
          </div>
        </div>
      </template>
    </div>
  </div>

  <!-- preview -->
  <div class="bottom" :class="{ preview_img: previewFlag }">
    <template v-if="previewFlag && mode != modeCreate">
      <div class="add_photo" v-if="mode != modeDetail">
        <label class="btn_add_photo" :for="id">
          <p>사진추가</p>
        </label>
        <p class="add_photo_description">{{ description }}</p>
      </div>
      <div class="add_photo mg_r10" v-for="item in fileInfo">
        <img :key="item.fileUrl" :src="item.fileUrl" :class="className" />
        <p class="button_close" @click="removeFile(item.fileName)">close</p>
      </div>
      <div class="add_photo mg_r10" v-for="item in fileUploadedInfo">
        <img :key="item.url" :src="item.url" :class="className + ' img_preview_style'" @click="confirmDowload(item.fileName, item.fimOrgName)" />
        <p class="button_close" @click="openModal(item)" v-if="mode == modeEdit">close</p>
      </div>
    </template>
    <template v-if="previewFlag && mode == modeCreate">
      <div class="add_photo">
        <label class="btn_add_photo" :for="id">
          <p>사진추가</p>
        </label>
        <p class="add_photo_description">{{ description }}</p>
      </div>
      <div class="add_photo mg_r10" v-for="item in fileInfo">
        <img :key="item.fileUrl" :src="item.fileUrl" :class="className" />
        <p class="button_close" @click="removeFile(item.fileName)">close</p>
      </div>
    </template>
  </div>
  <p class="file_sub_title" v-if="subTitle">{{ subTitle }}</p>
  <p v-if="showMess && mode != modeDetail" style="color: red" :class="{'previewMess' : previewFlag }">* 파일 교체 시 기존 파일 삭제 후 새로운 파일을 업로드 해주세요.</p>
  <form ref="fileForm" v-if="mode != modeDetail && !defaultFlag">
    <div class="input_file">
      <input ref="inputFile" type="file" :id="id" :name="name" @change="handleFileChange" :accept="acceptType" hidden :multiple="multiple" />
      <label :for="id" class="file_button">{{ label }}</label>
    </div>
  </form>
  <TModalConfirm :is-open="modalOpenConfirm" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
    <!-- Modal content goes here -->
    <template #title>
      <strong>개인정보 수집 및 이용에 대한 동의</strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ messageContent }}</p>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button type="button" class="button btn_md btn_primary" @click="confirmAction">{{ t.t("common.confirm") }}</button>
      <button type="button" class="button btn_md btn_white popup_close" @click="cancelAction" v-if="showFlag">{{ t.t("common.cancel") }}</button>
    </template>
  </TModalConfirm>
</template>

<script setup>
import {
  FILE_TYPE_IMAGE,
  FILE_TYPE_OFFICE,
  FILE_TYPE_VIDEO,
  ACCEPTTYPE_OFICE,
  ACCEPTTYPE_IMG,
  ACCEPTTYPE_VIDEO,
  ACCEPTTYPE_ALL,
  MODE_CREATE,
  MODE_EDIT,
  MODE_DETAIL,
  SUCCSESS_STATUS,
  FILE_TYPE_ALL,
  FILE_TYPE_CUSTOM,
  ACCEPTTYPE_CUSTOM,
} from "@/constants/screen.const.ts";

import { ref, defineProps, defineEmits, defineExpose } from "vue";
import { fileMngStore } from "../../../stores/common/fileMng";
import { useI18n } from "vue-i18n";
import Swal from "sweetalert2";
import { checkFlagStore } from "@/stores/common/checkRoleUrl";
import TModalConfirm from "@/components/common/excel/TModalConfirm.vue";
const props = defineProps({
  modelValue: [],
  maxFile: {
    type: Number,
    default: 1,
  },
  id: String,
  name: String,
  type: String,
  previewFlag: {
    type: Boolean,
    default: false,
  },
  multiple: {
    type: Boolean,
    default: false,
  },
  orgName: String,
  category: String,
  sectionName: String,
  mode: String,
  referKey: Number,
  label: {
    type: String,
    default: "첨부파일",
  },
  defaultFlag: {
    type: Boolean,
    default: true,
  },
  showMess: {
    type: Boolean,
    default: true,
  },
  subTitle: String,
  className: String,
  maxSize: {
    type: Number,
    default: 10,
  },
  dowloadOriginFlag: {
    type: Boolean,
    default: false,
  },
  description: {
    type: String,
    default: "",
  },
});
const modalId = ref("file");
const emit = defineEmits(["update:modelValue"]);
const messageContent = ref("");
const fileForm = ref(null);
const t = useI18n();
const isShow = ref(false);
const store = fileMngStore();
const roleStore = checkFlagStore();
const selectedFile = ref(null);
const fileName = ref("");
const fileId = ref("");
const totalFile = ref([]);
const fileInfo = ref([]);
const acceptType = ref("");
const fileArray = ref([]);
const modeCreate = MODE_CREATE;
const modeEdit = MODE_EDIT;
const modeDetail = MODE_DETAIL;
const fileUploadedInfo = ref([]);
const modalIdModify = "modalIdModify";
const modalOpenModify = ref(false);
const isDisable = ref(false);
const modalOpenConfirm = ref(false);
const confirmType = ref("");
const fimOrgName = ref("");
const showFlag = ref(true);
const totalFiles = ref([]);
onMounted(() => {
  switch (props.type) {
    case FILE_TYPE_IMAGE:
      acceptType.value = ACCEPTTYPE_IMG;
      break;
    case FILE_TYPE_OFFICE:
      acceptType.value = ACCEPTTYPE_OFICE;
      break;
    case FILE_TYPE_VIDEO:
      acceptType.value = ACCEPTTYPE_VIDEO;
      break;
    case FILE_TYPE_ALL:
      acceptType.value = ACCEPTTYPE_ALL;
      break;
    case FILE_TYPE_CUSTOM:
      acceptType.value = ACCEPTTYPE_CUSTOM;
      break;
    default:
      break;
  }
  if (props.mode != MODE_CREATE) {
    getFileUploaded();
  }
});
const dowload = async (fileName, fimOrgName) => {
  let params = {};
  params.encodedFileName = fileName;
  if (props.dowloadOriginFlag) {
    await store.getFileDowloadOrigin(params);
  } else {
    await store.getFileDowload(params);
  }
  if (store.status && store.status == SUCCSESS_STATUS) {
    let data = store.fileDowload;
    const url = window.URL.createObjectURL(new Blob([data], { type: store.type }));
    let fileNameFromServer = "";
    if (props.dowloadOriginFlag) {
      fileNameFromServer = fimOrgName;
    } else {
      fileNameFromServer = "downloadedFile.zip";
    }

    const link = document.createElement("a");
    link.href = url;
    link.setAttribute("download", fileNameFromServer);
    document.body.appendChild(link);
    link.click();
    link.remove();
    window.URL.revokeObjectURL(url);
  }
};
const removeFileExtension = (filename) => {
  // Danh sách các đuôi tập tin cần loại bỏ
  const extensions = [
    ".hwp",
    ".hwpx",
    ".doc",
    ".docx",
    ".xls",
    ".xlsx",
    ".ppt",
    ".pptx",
    ".txt",
    ".pdf",
    ".zip",
    ".jpg",
    ".jpeg",
    ".png",
    ".avi",
    ".flv",
    ".mkv",
    ".mov",
    ".mpeg",
    ".mp4",
    ".ogg",
    ".wma",
    ".wmv",
  ];

  // Kiểm tra từng đuôi trong danh sách
  for (const ext of extensions) {
    if (filename.toLowerCase().endsWith(ext)) {
      return filename.slice(0, -ext.length); // Cắt bỏ phần đuôi
    }
  }

  // Trả về tên gốc nếu không có đuôi nào khớp
  return filename;
};
const confirmDowload = async (name, orgName) => {
  showFlag.value = true;
  confirmType.value = "dowLoad";
  messageContent.value = t.t("common.message.confirmDownloadFile");
  fimOrgName.value = orgName;
  fileName.value = removeFileExtension(name);
  modalOpenConfirm.value = true;
};
const closeModal = () => {
  modalOpenConfirm.value = false;
};
const confirmAction = () => {
  switch (confirmType.value) {
    case "del":
      deleteFile(fileName.value, fileId.value);
      break;
    case "dowLoad":
      dowload(fileName.value, fimOrgName.value);
      break;
    default:
      break;
  }

  closeModal();
};
const cancelAction = () => {
  // Handle cancel action
  closeModal();
};
const openModal = (data) => {
  messageContent.value = t.t("common.message.confirmDelete");
  confirmType.value = "del";
  showFlag.value = true;
  modalOpenConfirm.value = true;
  fileName.value = data.fileName;
  fileId.value = data.id;
};
const getFileUploaded = async () => {
  let params = {};
  params.fimFileCategory = props.category;
  params.fimFileOrgName = props.orgName;
  params.fimSectionName = props.sectionName;
  params.fimReferKeyId = props.referKey;
  await store.getListFileUpload(params);
  if (store.status == SUCCSESS_STATUS) {
    let datas = store.fileUploadedInfoList;
    datas.forEach((item) => {
      let fileUploaded = {};
      fileUploaded.fileName = item.fimFileName;
      fileUploaded.uploadDirectory = item.fimFilePath;
      fileUploaded.id = item.id;
      fileUploaded.url = item.urlFile;
      fileUploaded.fimOrgName = item.fimOrgName;
      fileUploaded.size = (item.size / 1024).toFixed(2);
      fileUploadedInfo.value.push(fileUploaded);
    });
    if (props.maxFile === 1) {
      if (fileUploadedInfo.value.length >= 1) {
        isDisable.value = true;
      }
    }
    totalFiles.value = [...totalFiles.value, ...fileUploadedInfo.value]
    emit("update:modelValue", totalFiles.value);
  }
};
const upLoadFile = async (referKeyId) => {
  if (!referKeyId || fileArray.value.length == 0) {
    return;
  }
  const formData = new FormData();

  formData.append("orgName", props.orgName);
  formData.append("category", props.category);
  formData.append("sectionName", props.sectionName);
  formData.append("referKeyId", referKeyId);

  fileArray.value.forEach((item) => {
    formData.append("files", item.file);
  });
  await store.addfile(formData);
  if (store.status == SUCCSESS_STATUS) {
  }
};
const handleFileChange = (event) => {
  totalFile.value = Array.from(event.target.files);

  isShow.value = true;
  if (
    totalFile.value.length > props.maxFile ||
    fileInfo.value.length > props.maxFile ||
    (!props.multiple && fileInfo.value.length > 0) ||
    fileInfo.value.length + totalFile.value.length > props.maxFile ||
    (!props.multiple && fileUploadedInfo.value.length > 0) ||
    (props.multiple && fileUploadedInfo.value.length + totalFile.value.length + fileInfo.value.length > props.maxFile)
  ) {
    return;
  }
  const allowedExtensions = acceptType.value;

  totalFile.value.forEach((item) => {
    const fileExtension = "." + item.name.split(".").pop();
    if (allowedExtensions.includes(fileExtension)) {
      let file = {};
      let fileUpload = {};
      file.fileName = item.name;
      file.fimOrgName = item.name;
      file.fileUrl = window.URL.createObjectURL(item);
      file.size = (item.size / 1024).toFixed(2);
      fileUpload.name = item.name;
      fileUpload.file = item;

      if (item.size > props.maxSize * 1024 * 1024) {
        showFlag.value = false;
        messageContent.value = `${props.maxSize}MB 이하의 파일 등록가능합니다.`;
        modalOpenConfirm.value = true;
        confirmType.value = ''
        event.target.value = "";
        return;
      }
      fileInfo.value.push(file);
      fileArray.value.push(fileUpload);
    }
  });
  if (props.maxFile === 1) {
    if (fileArray.value.length >= 1) {
      props.isDisable = true;
    }
  }
  totalFiles.value = [...totalFiles.value, ...fileArray.value]
  emit("update:modelValue", totalFiles.value);
};
const removeFile = (fileName) => {
  fileInfo.value = fileInfo.value.filter((item) => item.fileName != fileName);
  fileArray.value = fileArray.value.filter((item) => item.name != fileName);
  fileForm.value.reset();
  if (fileInfo.value.length == 0) {
    isShow.value = false;
  }
  isDisable.value = false;
  totalFiles.value = totalFiles.value.filter((item) => item.name != fileName);
  
  emit("update:modelValue", totalFiles.value);
};
const deleteFile = async (fileName, id) => {
  fileUploadedInfo.value = fileUploadedInfo.value.filter((item) => item.fileName != fileName);
  totalFiles.value = totalFiles.value.filter((item) => item.fileName != fileName);
  await store.deleteFileMng(fileName);
  isDisable.value = false;
  emit("update:modelValue", totalFiles.value);
};

defineExpose({
  upLoadFile,
});
</script>
<style scoped>
.img_base_preview {
  width: 200px !important;
  height: 200px !important;
}

.show_file_input_base {
  display: flex !important;
}

.btn_close_file_base {
  margin-top: -15px !important;
  width: 12px !important;
}

.btn_close_preview_img {
}

.btn_icon .icon_only {
}

.file_form_style {
  display: flex;
}

.file_sub_title {
  color: red;
  padding-top: 5px;
}

.input_file {
  position: relative;
  display: inline-block;
}

.file_button {
  display: inline-block;
  padding: 7px 20px;
  background-color: var(--white-color);
  color: black;
  border: 1px solid var(--gray-lavender);
  cursor: pointer;
  border-radius: 4px;
}

.file_button:hover {
  background-color: var(--light-gray);
}

/* Ẩn input[type="file"] */
.input_file input[type="file"] {
  position: absolute;
  top: 0;
  left: 0;
  width: 0;
  height: 0;
  opacity: 0;
  overflow: hidden;
}

.flex_input {
  flex-direction: column;
}

.preview_img {
  display: flex !important;
  flex-wrap: wrap;
  gap: 10px;
}

.disable_button {
  padding: 0 !important;
}

.img_preview_style {
  cursor: pointer;
}
.previewMess {
  margin-top: 10px;
}
</style>
