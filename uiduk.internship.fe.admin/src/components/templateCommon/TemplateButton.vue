<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div class="box dp_block">
    <div class="box_section">
      <h3 class="box_title">BUTTON</h3>
      <p class="box_title_sm">Button - size</p>
      <div class="flex ga-2">
        <button type="button" class="button btn_xs btn_white">btn_xs</button>
        <button type="button" class="button btn_sm btn_white">btn_sm</button>
        <button type="button" class="button btn_md btn_white">btn_md</button>
        <button type="button" class="button btn_xl btn_white">btn_xl</button>
      </div>
    </div>
    <div class="box_section">
      <p class="box_title_sm">Button - form</p>
      <div>
        <button type="button" class="button btn_xl btn_primary btn_responsive">Search</button>
        <button type="button" class="button btn_xl btn_lightgray btn_responsive">Reset</button>
        <hr />
      </div>
      <p class="box_title_sm">Button - color</p>
      <div>
        <button type="button" class="btn_round btn_sm btn_primary">Lưu</button>
        <button type="button" class="btn_round btn_sm btn_secondary">Lưu tạm thời</button>
        <button type="button" class="btn_round btn_sm btn_bo_primary">Print</button>
        <button type="button" class="btn_round btn_sm btn_lightgray">Thực hiện 1 action</button>
        <button type="button" class="btn_round btn_sm btn_white">List/Back</button>
        <hr />
      </div>
      <p class="box_title_sm">Button - icon</p>
      <div>
        <button type="button" class="btn_round btn_lg btn_secondary btn_icon_download">Download</button>
      </div>
    </div>
    <div class="box_section">
      <p class="box_title_sm">Button - responsive</p>
      <div class="btn_group btn_start">
        <button type="button" class="button btn_xs btn_white btn_responsive">btn_xs</button>
        <button type="button" class="button btn_xs btn_white btn_responsive">btn_xs</button>
        <button type="button" class="button btn_xs btn_white btn_responsive">btn_xs</button>
      </div>
      <div class="btn_group btn_end">
        <button type="button" class="button btn_sm btn_white btn_responsive">btn_sm</button>
        <button type="button" class="button btn_sm btn_white btn_responsive">btn_sm</button>
        <button type="button" class="button btn_sm btn_white btn_responsive">btn_sm</button>
      </div>
      <div class="btn_group btn_center">
        <button type="button" class="button btn_md btn_white btn_responsive">btn_md</button>
        <button type="button" class="button btn_md btn_white btn_responsive">btn_md</button>
        <button type="button" class="button btn_md btn_white btn_responsive">btn_md</button>
      </div>
    </div>
    <div class="box_section">
      <p class="box_title_sm">Button - disabled</p>
      <div class="flex ga-2">
        <button type="button" class="btn_round btn_sm btn_primary" disabled>Lưu</button>
        <button type="button" class="btn_round btn_sm btn_secondary" disabled>Lưu tạm thời</button>
        <button type="button" class="btn_round btn_sm btn_bo_primary" disabled>Print</button>
        <button type="button" class="btn_round btn_sm btn_lightgray" disabled>Thực hiện 1 action</button>
        <button type="button" class="btn_round btn_sm btn_white" disabled>List/Back</button>
        <hr />
      </div>
    </div>

    <div class="box_section">
      <h3 class="box_title font_20">Dowload Excel Button</h3>
      <div class="dp_flex" style="gap: 10px;flex-wrap: wrap">
        <!-- Set data from client -->
        <ExportFileExcel :data="dataExport" :file-name="fileNameExport" :btn-name="nameOfbtn" />
        <ExportFileExcel :data="dataExport" :file-name="fileNameExport" :btn-name="nameOfbtn + ' Color Header'" :array-color="arrayColor" />
        <!-- Call data -->
        <ExportFileExcel
          ref="exportExcelRef"
          :data="dataExport"
          :file-name="fileNameExport"
          :btn-name="nameOfbtn + ' Call data'"
          @click="exportExcel"
        />
        <!-- <button type="button" class="button btn_xs btn_blue " @click="exportExcel">Dowload Excel</button> -->
      </div>
      <hr />
      <h3 class="box_title font_20">Read Excel Button</h3>
      <div class="dp_flex" style="gap: 10px">
        <ReadFileExcel :id="idEx" v-model="modelValueEx" :name="nameEx" :label-file="labelEx" />
        <div v-if="modelValueEx.length">
          <ul>
            <li v-for="(sheet, index) in modelValueEx" :key="index">
              <h4>sheetName : {{ sheet.name }}</h4>
              <pre>data :{{ sheet.data }}</pre>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <p class="box_title_sm">Button - location</p>
      <div>
        <button type="button" class="btn_round btn_sm btn_primary" @click="openPostcode">Tìm mã bưu chính</button>
        <div v-if="address">
              <p>Địa chỉ: {{ address }}</p>
              <p>Mã bưu chính: {{ postcode }}</p>
            </div>
        <hr />
      </div>
  </div>
</template>

<script setup lang="ts">
import ReadFileExcel from "@/components/common/excel/ReadFileExcel.vue";
import ExportFileExcel from "@/components/common/excel/ExportFileExcel.vue";
import { ExcelData } from "@/model/common/excelData.type.js";
import { ref } from "vue";

const exportExcelRef = ref(null);
const idEx = "readExcel";
const nameEx = "excelName";
const labelEx = "Read Excel";

// data exel
const dataExport = ref<Array<ExcelData>>([
  {
    sheetName: "sheet1",
    data: [
      [1, 2, 3],
      [4, 5, 6],
    ],
    header: ["col1", "col2", "col3"],
  },
  {
    sheetName: "sheet2",
    data: [
      [1, 2, 3],
      [4, 5, 6],
    ],
    header: ["col4", "col5", "col6"],
  },
]);
const arrayColor = ref([
  { color: "F15922", name: ["col2", "col5"] },
  { color: "A0C5E8", name: ["col4", "col3"] },
  { color: "FF8991", name: ["col1", "col6"] },
]);
const fileNameExport = ref("fileDowload");
const nameOfbtn = ref("Dowload Excel");
const modelValueEx = ref([]);
const address = ref('')
const postcode = ref('')
const exportExcel = () => {
  // Thực hiện xử lý data xong call dowload
  exportExcelRef.value.downloadExcel();
};
const openPostcode = () => {
      new window.daum.Postcode({
        oncomplete: (data : any) => {
          // Sau khi người dùng chọn địa chỉ, thông tin sẽ được trả về.
          address.value = data.address;
          postcode.value = data.zonecode;
        },
      }).open();
    }
</script>

<style scoped></style>
