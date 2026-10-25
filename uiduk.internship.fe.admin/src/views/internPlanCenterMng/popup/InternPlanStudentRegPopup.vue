<template>
  <TModal :classSize="`large`" :is-open="isOpen" :is-show-header="true" :is-show-footer="true" :modal-id="'modalStudent'" @close-modal="closeModal">
    <!-- Modal content goes here -->
    <template #title>
      <strong>{{ t("internPlanCenterMng.popup.pageTitle") }}</strong>
    </template>
    <template #default>
      <div class="box_section">
        <div>
          <h2 class="text_bold">{{ internPlanNm }}</h2>
          <h3 class="mt-2 text_bold">{{ t("internPlanCenterMng.popup.subtitle") }}</h3>
        </div>
        <hr />
        <div class="mb-2">
          <span class="mr-2">{{ t("internPlanCenterMng.popup.subStatus.title1") }} {{ totalStatusApprove.approveStatusCount }}</span>
          <span class="mr-2">{{ t("internPlanCenterMng.popup.subStatus.title2") }} {{ totalStatusApprove.notApproveStatusCount }}</span>
          <span class="mr-2">{{ t("internPlanCenterMng.popup.subStatus.title3") }} {{ totalStatusApprove.waitApproveStatusCount }}</span>
        </div>

        <GridComponent
          ref="gridStudentReg"
          :column-defs="columnDefs"
          :rows-data="rowData"
          :pagination="pagination"
          :total-row="totalRow"
          :pagination-page-size="paginationPageSize"
          @get-data="getDataAll"
          :autoSizeStrategy="true"
        >
          <template #itemSelected>
            <div>
              <span>{{ listSelect.length }} {{ t("internPlanCenterMng.subPage") }}</span>
            </div>
          </template>
          <template #button>
            <div class="btn_group btn_end">
              <button type="button" :disabled="isDisabledActionDelete" class="mg_l15 btn_md btn_lightgray" @click="handleDeleteInternStd">
                {{ t("common.delete") }}
              </button>
              <button type="button" :disabled="isDisabledAction" class="btn_md btn_primary" @click="handleApproveInternStd">
                {{ t("internPlanCenterMng.popup.btn.approve") }}
              </button>
              <button type="button" :disabled="isDisabledAction" class="btn_md btn_primary" @click="handleCancelApproveInternStd">
                {{ t("internPlanCenterMng.popup.btn.cancelApprove") }}
              </button>
              <button type="button" :disabled="isDisabledAction" class="btn_md btn_primary" @click="handleNotApproveInternStd">
                {{ t("internPlanCenterMng.popup.btn.notApprove") }}
              </button>
              <button type="button" :disabled="isDisabledAction" class="btn_md btn_primary" @click="handleCancelNotApproveInternStd">
                {{ t("internPlanCenterMng.popup.btn.cancelNotApprove") }}
              </button>
              <ExportFileExcel :data="dataExport" :file-name="fileNameExport" :btn-name="nameOfbtn" @click="handleActionExportExcel"/>
            </div>
          </template>
        </GridComponent>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button type="button" class="button btn_md btn_white popup_close" @click="closeModal">{{ t("common.close") }}</button>
    </template>
  </TModal>
  <TModalAlert :is-open="modalOpenAlert" :is-show-header="true" :is-show-footer="true" :modal-id="'alertModal'" @close-modal="closeModalAlert">
    <!-- Modal content goes here -->
    <template #title>
      <strong>{{ messageAlert }}</strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ messageAlert }}</p>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button type="button" class="button btn_md btn_primary" @click="closeModalAlert">{{ t("common.confirm") }}</button>
    </template>
  </TModalAlert>

  <TModalConfirm
    :is-open="modalOpenConfirm"
    :is-show-header="true"
    :is-show-footer="true"
    :modal-id="'confirmModal'"
    @close-modal="closeModalConfirm"
  >
    <!-- Modal content goes here -->
    <template #title>
      <strong>{{ messageConfirm }}</strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ messageConfirm }}</p>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button type="button" class="button btn_md btn_primary" @click="handleConfirmModal">{{ t("common.confirm") }}</button>
      <button type="button" class="button btn_md btn_white popup_close" @click="closeModalConfirm">{{ t("common.cancel") }}</button>
    </template>
  </TModalConfirm>
</template>

<script setup lang="ts">
import { ref, defineEmits, nextTick, onUpdated, watch, onBeforeMount } from "vue";
import { SUCCSESS_STATUS, PAGINATION_PAGE_SIZE,FORMAT_YYY_MM_DD } from "@/constants/screen.const";
import { useI18n } from "vue-i18n";
import { internPlanCenterMngStore } from "@/stores/internPlanCenterMng/useInternPlanCenterMng";
import GridCheckBox from "@/components/common/grid/GridCheckBox.vue";
import { format } from "date-fns";
const { t } = useI18n();
const props = defineProps<{
  isOpen: boolean;
  internPlanSeq: string;
  internPlanNm: string;
}>();
const emit = defineEmits(["closeModal", "getAllDataInternPlan"]);

const rowData = ref<any>([]);
const paginationPageSize = PAGINATION_PAGE_SIZE;
const pagination = ref(true);
const totalRow = ref();
const internPlanMng = internPlanCenterMngStore();
const searchModel = ref({
  idInternPlan: props.internPlanSeq,
  page: 1,
  size: 10,
  sort: "",
});
const gridStudentReg = ref(null);
const cloneDataSearch = ref(null)
const listSelect = ref([]);
const modalOpenAlert = ref(false);
const messageAlert = ref("");

const modalOpenConfirm = ref(false);
const messageConfirm = ref("");

const handleConfirmModal = ref(function () {});

const isDisabledAction = ref(true);
const isDisabledActionDelete = ref(true);

const columnDefs = ref([
  {
    headerName: "",
    field: "selected",
    width: 100,
    headerComponent: GridCheckBox,
    headerComponentParams: {
      checkAll: "gridCheckAll2",
      mode: "all",
      onCustomClick: handleClickAll2,
      gridName: "grid2",
    },
    cellRenderer: GridCheckBox,
    cellRendererParams: {
      checkAll: "gridCheckAll2",
      childId: "internStdSeq",
      mode: "child",
      gridName: "grid2",
      onCustomClick: handleClickChild2,
    },
    maxWidth:60
  },
  {
    headerName: t("common.rowNum"),
    field: "rowNumb",
    maxWidth: 90,
  },
  {
    headerName: t("internPlanCenterMng.popup.column1"),
    field: "approveStatus",
    minWidth: 120,
  },
  {
    headerName: t("internPlanCenterMng.popup.column2"),
    field: "colgNm",
    flex: 0.7,
  },
  {
    headerName: t("internPlanCenterMng.popup.column3"),
    field: "sustNm",
    flex: 0.7,
  },
  {
    headerName: t("internPlanCenterMng.popup.column4"),
    field: "shyrNm",
    flex: 0.3,
  },
  {
    headerName: t("internPlanCenterMng.popup.column5"),
    field: "stdNo",
    flex: 0.3,
  },
  {
    headerName: t("internPlanCenterMng.popup.column6"),
    field: "korNm",
    flex: 1.1,
  },
  {
    headerName: t("internPlanCenterMng.popup.column7"),
    field: "handNo",
    flex: 1.1,
  },
  {
    headerName: t("internPlanCenterMng.popup.column8"),
    field: "email",
    flex: 0.2,
  },
  {
    headerName: t("internPlanCenterMng.popup.column9"),
    field: "regDate",
    flex: 0.7,
  },
]);

const totalStatusApprove = ref({
  waitApproveStatusCount: 0,
  approveStatusCount: 0,
  notApproveStatusCount: 0,
});

const dataExport = ref<any>([]);
const nameOfbtn = ref<string>(t("internPlanCenterMng.btnDowload"));
const fileNameExport = ref<string>(t("internPlanCenterMng.popup.pageTitle"));

onBeforeMount(() => {
  cloneDataSearch.value = {...searchModel.value}
});

const search = () => {
  cloneDataSearch.value = {...searchModel.value}
  gridStudentReg.value.initGrid()
  listSelect.value = []
}

const getDataAll = async (page = 1, pageSize = 10) => {
  getStatusApproveStudent();

  cloneDataSearch.value.page = page;
  cloneDataSearch.value.size = pageSize;
  await internPlanMng.getListInternPlanStudent({
    method: "POST",
    body: cloneDataSearch.value,
  });
  const { arr } = areAllItemsEqual(internPlanMng.resultDataStudent.data.content, listSelect.value, "gridCheckAll2");
  rowData.value = arr;
  totalRow.value = internPlanMng.resultDataStudent.data.totalElements ?? 0;
  rowData.value.forEach((item: any) => {
    item.regDate = format(new Date(item.regDate), FORMAT_YYY_MM_DD);
  });
};

const getStatusApproveStudent = async () => {
  await internPlanMng.getStatusApprove({
    method: "POST",
    body: {
      idInternPlan: props.internPlanSeq,
    },
  });
  totalStatusApprove.value = internPlanMng.resultDataStsStudent.data;
};

async function deletedIntenStd() {
  let idDelete = listSelect.value.map((item) => item.internStdSeq);
  await internPlanMng.deleteInternStd({
    method: "PUT",
    body: idDelete,
  });
  listSelect.value = [];
  if (internPlanMng.status.value && internPlanMng.status.value == SUCCSESS_STATUS) {
    search();
    emit("getAllDataInternPlan");
    openModalAlert(t("internPlanCenterMng.message.deleteSuccess"));
  }
}
async function approveIntenStd() {
  let idInternStd = listSelect.value.map((item) => item.internStdSeq);
  await internPlanMng.approveInternStd({
    method: "PUT",
    body: idInternStd,
  });
  listSelect.value = [];
  if (internPlanMng.status.value && internPlanMng.status.value == SUCCSESS_STATUS) {
    search();
    openModalAlert(t("internPlanCenterMng.message.changeStatusApproveSuccess"));
  } else {
    openModalAlert(t("internPlanCenterMng.message.changeStatusFail"));
  }
}

async function cancelapproveIntenStd() {
  let idInternStd = listSelect.value.map((item) => item.internStdSeq);
  await internPlanMng.cancelApproveInternStd({
    method: "PUT",
    body: idInternStd,
  });
  listSelect.value = [];
  if (internPlanMng.status.value && internPlanMng.status.value == SUCCSESS_STATUS) {
    search();
    openModalAlert(t("internPlanCenterMng.message.changeStatusCancelApproveSuccess"));
  } else {
    openModalAlert(t("internPlanCenterMng.message.changeStatusFail"));
  }
}

async function cancelRejectIntenStd() {
  let idInternStd = listSelect.value.map((item) => item.internStdSeq);
  await internPlanMng.cancelApproveInternStd({
    method: "PUT",
    body: idInternStd,
  });
  listSelect.value = [];
  if (internPlanMng.status.value && internPlanMng.status.value == SUCCSESS_STATUS) {
    search();
    openModalAlert(t("internPlanCenterMng.message.changeStatusCancelNotApproveSuccess"));
  } else {
    openModalAlert(t("internPlanCenterMng.message.changeStatusFail"));
  }
}

async function notApproveIntenStd() {
  let idInternStd = listSelect.value.map((item) => item.internStdSeq);
  await internPlanMng.notApproveInternStd({
    method: "PUT",
    body: idInternStd,
  });
  listSelect.value = [];
  if (internPlanMng.status.value && internPlanMng.status.value == SUCCSESS_STATUS) {
    search();
    openModalAlert(t("internPlanCenterMng.message.changeStatusNotApproveSuccess"));
  } else {
    openModalAlert(t("internPlanCenterMng.message.changeStatusFail"));
  }
}
function handleDeleteInternStd() {
  messageConfirm.value = t("internPlanCenterMng.message.confirmDelete");
  opemModalConfirm(() => {
    modalOpenConfirm.value = false;
    deletedIntenStd();
  });
}

function handleApproveInternStd() {
  messageConfirm.value = t("internPlanCenterMng.message.confirmChangeStatusApprove");
  opemModalConfirm(() => {
    modalOpenConfirm.value = false;
    approveIntenStd();
  });
}

function handleCancelApproveInternStd() {
  messageConfirm.value = t("internPlanCenterMng.message.confirmChangeStatusCancelApprove");
  opemModalConfirm(() => {
    modalOpenConfirm.value = false;
    cancelapproveIntenStd();
  });
}

function handleCancelNotApproveInternStd() {
  messageConfirm.value = t("internPlanCenterMng.message.confirmChangeStatusCancelNotApprove");
  opemModalConfirm(() => {
    modalOpenConfirm.value = false;
    cancelRejectIntenStd();
  });
}

function handleNotApproveInternStd() {
  messageConfirm.value = t("internPlanCenterMng.message.confirmChangeStatusNotApprove");
  opemModalConfirm(() => {
    modalOpenConfirm.value = false;
    notApproveIntenStd();
  });
}

const handleActionExportExcel = async () => {
  if (totalRow.value > 0) {
    const searchToExport = { ...cloneDataSearch.value };
    searchToExport.page = 1;
    searchToExport.size = totalRow.value;
  
    await internPlanMng
      .getListInternPlanStudent({
        method: "POST",
        body: searchToExport,
      })
      .then(() => {
        const dataExcel = internPlanMng.resultDataStudent.data.content;
        exportExcel(dataExcel);
      });
  } else {
    exportExcel([]);
  }
}
  
const exportExcel = (dataExcel: any) => {
  let rowExcel = [] as Array<Array<any>>;
  let rowExcelHeader = [] as Array<any>;

  columnDefs.value.forEach((el, index) => {
    if (index > 0 && index < columnDefs.value.length) {
      rowExcelHeader.push(el.headerName);
    }
  });

  dataExcel.forEach((el: any) => {
    let colExcel = [] as Array<any>;
    el.regDate = format(new Date(el.regDate), FORMAT_YYY_MM_DD);

    colExcel.push(el.rowNumb);
    colExcel.push(el.approveStatus);
    colExcel.push(el.colgNm);
    colExcel.push(el.sustNm);
    colExcel.push(el.shyrNm);
    colExcel.push(el.stdNo);
    colExcel.push(el.korNm);
    colExcel.push(el.handNo);
    colExcel.push(el.email);
    colExcel.push(el.regDate);
    rowExcel.push(colExcel);
  });

  dataExport.value = [{ sheetName: "sheet1", data: rowExcel, header: rowExcelHeader }] as Array<ExcelData>;
}

function closeModalAlert() {
  modalOpenAlert.value = false;
}
function openModalAlert(mess: any) {
  messageAlert.value = mess;
  modalOpenAlert.value = true;
}
function opemModalConfirm(customAction: Function = () => {}) {
  handleConfirmModal.value = customAction;
  modalOpenConfirm.value = true;
}

function closeModalConfirm() {
  modalOpenConfirm.value = false;
}
const closeModal = () => {
  emit("closeModal");
};

watch(
  listSelect,
  (newValue) => {
    if (newValue) {
        isDisabledAction.value = !(newValue?.length > 0 && newValue.every((item) => item.approveStatusCd != "STD_A01_P08"));
        isDisabledActionDelete.value = newValue.length == 0
    }
  },
  { deep: true },
);

function handleClickAll2(value) {
  rowData.value.forEach((item) => {
    if (value) {
      if (!listSelect.value.some((el) => el.internStdSeq == item.internStdSeq)) {
        listSelect.value.push(item);
      }
    } else {
      listSelect.value = listSelect.value.filter((el) => el.internStdSeq != item.internStdSeq);
    }
  });

  //add logic update value selected
  rowData.value = rowData.value.map((item) => {
    return { ...item, selected: value }
  });
}

function handleClickChild2(value, flag) {
  if (flag) {
    listSelect.value.push(value);
  } else {
    listSelect.value = listSelect.value.filter((item) => item.internStdSeq !== value.internStdSeq);
  }

  //add logic update value selected
  rowData.value = rowData.value.map((item) => {
    if (item.internStdSeq === value.internStdSeq) {
      return { ...item, selected: flag };
    }
    return item;
  });
}
function areAllItemsEqual(rowData, listSelect, idInput) {
  const all = document.getElementById(idInput);
  all.checked = false;
  let checked;
  let arr = [...rowData];

  checked = rowData.every((item1) => {
    return listSelect.some((item2) => item1.internStdSeq === item2.internStdSeq);
  });

  if (checked) {
    if (checked && rowData?.length) all.checked = true;
    arr = arr.map((item) => {
      return {
        ...item,
        selected: true,
      };
    });
  } else {
    arr = arr.map((item) => {
      const isSelected = listSelect.some((selectedItem) => selectedItem.internStdSeq === item.internStdSeq);
      return {
        ...item,
        selected: isSelected,
      };
    });
  }

  return { checked, arr };
}
//Logic
onUpdated(async () => {
  await nextTick();
  listSelect.value.forEach((item) => {
    const e = document.getElementById(item.internStdSeq);
    if (e) {
      e.checked = true;
    }
  });
});
</script>
<style scoped lang="scss">
.text_bold {
  font-weight: 500;
  color: black;
}
</style>