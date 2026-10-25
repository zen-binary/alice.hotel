<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div>
    <div class="box dp_block">
      <div class="box_section">
        <p class="box_title_sm">Upload file Component</p>
        <div class="search_box col_3">
          <ul>
            <li>
              <p>Upload file with id</p>
            </li>
            <li>
              <p>Id value: {{ idUpload }}</p>
            </li>
            <li>
              <InputBase v-model="idUpload" />
            </li>
            <li>
              <p>Upload file with screen name</p>
            </li>
            <li>
              <p>Screen name value: {{ screenName }}</p>
            </li>
            <li>
              <InputBase v-model="screenName" />
            </li>
          </ul>
        </div>
<!--        <FileUpload :id="idUpload" ref="upload" :mode="MODE_UPLOAD" :accept-type="ACCEPTTYPE_IMG" :screen-name="screenName" />-->
      </div>
      <button class="button btn_secondary" @click="handleSaveData">Save</button>
    </div>

    <div class="box dp_block">
      <div class="box_section">
        <p class="box_title_sm">Preview/download file</p>
        <div class="search_box col_3">
          <ul>
            <li>
              <p>Upload file with id</p>
            </li>
            <li>
              <p>Id value: {{ idUpload }}</p>
            </li>
            <li>
              <InputBase v-model="idUpload" is-detail />
            </li>
            <li>
              <p>Upload file with screen name</p>
            </li>
            <li>
              <p>Screen name value: {{ screenName }}</p>
            </li>
            <li>
              <InputBase v-model="screenName" is-detail />
            </li>
          </ul>
        </div>
        <button class="button btn_secondary mb-4" @click="showFile = !showFile">Show</button>
<!--        <FileUpload v-if="showFile" :id="idUpload" :mode="MODE_DETAIL" :accept-type="ACCEPTTYPE_IMG" :screen-name="screenName" class="mb-4" />-->
<!--        <FileUpload v-if="showFile" :id="idUpload" :mode="MODE_DOWNLOAD" :accept-type="ACCEPTTYPE_IMG" :screen-name="screenName" />-->

        <InputFileBase
            :mode="`create`"
            :id="`template_1`"
            :type="'img'"
            :preview-flag="true"
            :description="'학생날인을 등록해 주세요.'"
            :multiple="true"
            :max-file="5"
            :show-mess="false"
        ></InputFileBase>
        <InputFileBase
            :mode="`create`"
            :id="`template_3`"
            :type="'img'"
            :description="'학생날인을 등록해 주세요.'"
            :show-mess="false"
            :category="'img'"
            :sectionName="'template'"
            ref="test"
        ></InputFileBase>
        <InputFileBase
            :mode="`edit`"
            :id="`template_4`"
            :type="'img'"
            :description="'학생날인을 등록해 주세요.'"
            :show-mess="false"
            :category="'img'"
            :sectionName="'template'"
            ref="uploadFiles"
            :referKey="2"
        ></InputFileBase>
        <InputFileBase
            :mode="`edit`"
            :id="`template_4`"
            :type="'img'"
            :description="'학생날인을 등록해 주세요.'"
            :show-mess="false"
            :category="'img'"
            :sectionName="'template'"
            ref="uploadFiles"
            :referKey="4"
        ></InputFileBase>
        <button class="button btn_secondary" @click="handleSaveFile">Save File</button>
        <hr/>
        <InputFileBase :mode="`create`" :id="`template_2`" :type="'img'" :preview-flag="true" :show-mess="false" />
        <button class="button btn_secondary" @click="dowloadMuitple">dowLoad multiple file</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ACCEPTTYPE_IMG, MODE_DETAIL, MODE_DOWNLOAD, MODE_UPLOAD } from "@/constants/common.const.js";
import { ref } from "vue";
import FileUpload from "@/components/common/input/FileUpload.vue";
import InputFileBase from "@/components/common/input/InputFileBase.vue";
import {fileMngStore} from "@/stores/common/fileMng/index"
const upload = ref<HTMLElement>(null);
const idUpload = ref(1);
const screenName = ref("template");
const showFile = ref(true);
const test2222 = ref(null);
const fileStore = fileMngStore();
// file upload
const handleSaveData = () => {
  // upload.value.uploadFile();
};
const test = ref(null);
const handleSaveFile = async () => {
  if(test.value){
    test.value.upLoadFile(2);
  }

};
const dowloadMuitple = async ()=> {
  let params = {} as any
  params.lstId = ['2','4']
  params.sectionName = ['template','template']
  await fileStore.dowLoadMultipleFile(params)
}
</script>

<style scoped></style>
