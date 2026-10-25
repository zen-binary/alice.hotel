<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <button v-if="!callData" v-bind="attrs" type="button" class="button btn_md btn_secondary btn_exel_download" @click="openModalConfirm">
    {{ btnName }}
  </button>
  <button v-if="callData" v-bind="attrs" type="button" class="button btn_md btn_secondary btn_exel_download">
    {{ btnName }}
  </button>
  <TModalConfirm :is-open="modalOpenConfirm" :is-show-header="true" :modal-id="'confirmDowload'" @close-modal="closeModalConfirm">
    <template #default>
      <div class="box_section">
        <p>{{ textConfirmDownload ? textConfirmDownload : t("common.message.confirmDowloadExcel") }}</p>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_md btn_primary" @click="downloadExcel">{{ t("common.confirm") }}</button>
      <button type="button" class="button btn_md btn_white popup_close" @click="closeModalConfirm">{{ t("common.cancel") }}</button>
    </template>
  </TModalConfirm>
</template>
<script setup>
import ExcelJS from "exceljs";
import { useAttrs, ref } from "vue";
import { useI18n } from "vue-i18n";
import { format } from "date-fns";

const attrs = useAttrs();
const { t } = useI18n();

const props = defineProps({
  data: {
    type: Array,
    required: true,
  },
  btnName: {
    type: String,
    required: true,
  },
  fileName: {
    type: String,
    required: true,
  },
  callData: {
    type: Boolean,
    default: false,
  },
  arrayColor: {
    type: Array,
    default: () => [],
  },
  textConfirmDownload: {
    type: String,
    required: false,
    default: "",
  },
});

const modalOpenConfirm = ref(false);

function openModalConfirm() {
  modalOpenConfirm.value = true;
}

function closeModalConfirm() {
  modalOpenConfirm.value = false;
}

const downloadExcel = () => {
  executeDownloadExcel();
};

/**
 *
 */
const executeDownloadExcel = () => {
  const workbook = new ExcelJS.Workbook();
  let sheet;
  const newArr = {};
  props.data.forEach((element, index) => {
    newArr[`sheet${index}`] = [];
    element.header.forEach((data) => {
      props.arrayColor?.forEach((item) => {
        if (item?.name?.indexOf(data) > -1) {
          newArr[`sheet${index}`].push(item.color);
        }
      });
    });
  });
  props.data.forEach((element, idx) => {
    sheet = workbook.addWorksheet(element.sheetName);
    const headerRow = sheet.addRow(element.header);

    // Set color for the header row
    headerRow.eachCell((cell, colNumber) => {
      const color = newArr[`sheet${idx}`][colNumber - 1];
      if (color) {
        cell.fill = {
          type: "pattern",
          pattern: "solid",
          fgColor: { argb: color },
        };
      } else {
        cell.fill = {
          type: "pattern",
          pattern: "none", // No background color
        };
      }
    });
    element.data.forEach((el) => {
      sheet.addRow(el);
    });
  });

  workbook.xlsx.writeBuffer().then((buffer) => {
    const blob = new Blob([buffer], {
      type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
    });
    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    let newFileName = `${format(new Date(), "yyyyMMddHHmmss")}_${props.fileName}.xlsx`;
    link.download = newFileName;
    link.click();
    link.remove();
  });

  closeModalConfirm();
};
defineExpose({
  downloadExcel,
});
</script>
