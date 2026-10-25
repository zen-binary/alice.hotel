<template>
  <TModal :classSize="`large`" :is-open="isOpen" :is-show-header="true" :is-show-footer="true" :modal-id="'modalStudent'" @close-modal="closeModal">
    <!-- Modal content goes here -->
    <template #title>
      <strong>{{ t("completionMng.pageTitle") }}</strong>
    </template>
    <template #default>
      <div class="box_section">
        <div>
          <h2>{{ internPlanNm }}</h2>
          <p class="mt-2">{{ t("completionMng.subtitle") }}</p>
        </div>
        <hr />
        <div class="mb-2">
          <span class="mr-2">{{ t("completionMng.subStatus.title1") }} {{ totalStatusComplete.completeCount }}</span>
          <span class="mr-2">{{ t("completionMng.subStatus.title2") }} {{ totalStatusComplete.notCompleteCount }}</span>
          <span class="mr-2">{{ t("completionMng.subStatus.title3") }} {{ totalStatusComplete.waitCompleteCount }}</span>
          <span class="mr-2">{{ t("completionMng.subStatus.title4") }} {{ totalStatusComplete.backupsCount }}</span>
        </div>

        <GridComponent
          ref="grid"
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
              <span>{{ listSelect.length }} {{ t("completionMng.subPage") }}</span>
            </div>
          </template>
          <template #button>
            <div class="btn_group btn_end">
              <button type="button" :disabled="isDisabledAction" class="btn_md btn_primary" @click="handleCompletionIntern">
                {{ t("completionMng.btn.complete") }}
              </button>
              <button type="button" :disabled="isDisabledAction" class="btn_md btn_primary" @click="handleWaitCompleteIntern">
                {{ t("completionMng.btn.cancelComplete") }}
              </button>
              <button type="button" :disabled="isDisabledAction" class="btn_md btn_primary" @click="handleNotApproveIntern">
                {{ t("completionMng.btn.notComplete") }}
              </button>
              <button type="button" :disabled="isDisabledAction" class="btn_md btn_primary" @click="handleWaitNotApproveIntern">
                {{ t("completionMng.btn.cancelNotComplete") }}
              </button>
              <button type="button" :disabled="isDisabledAction" class="btn_md btn_primary" @click="handleBackupsCompleteIntern">
                {{ t("completionMng.btn.backups") }}
              </button>
              <button type="button" :disabled="isDisabledAction" class="btn_md btn_primary" @click="handleWaitBackupsIntern">
                {{ t("completionMng.btn.cancelBackups") }}
              </button>
              <ExportFileExcel :disabled="totalRow == 0" :data="dataExport" :file-name="fileNameExport" :btn-name="nameOfbtn" />
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
import { SUCCSESS_STATUS, PAGINATION_PAGE_SIZE, FORMAT_YYY_MM_DD } from "@/constants/screen.const";
import { useI18n } from "vue-i18n";
import { completionMngStore } from "@/stores/completionMng/useCompletionMng";
import GridCheckBox from "@/components/common/grid/GridCheckBox.vue";
import { format } from "date-fns";
const { t } = useI18n();
const props = defineProps<{
  isOpen: boolean;
  internPlanSeq: string;
  internPlanNm: string;
}>();
const emit = defineEmits(["closeModal"]);

const rowData = ref<any>([]);
const paginationPageSize = PAGINATION_PAGE_SIZE;
const pagination = ref(true);
const totalRow = ref();
const completionMng = completionMngStore();
const searchModel = ref({
  internPlanId: props.internPlanSeq,
  page: 1,
  size: 10,
  sort: "",
});
const listSelect = ref([]);
const modalOpenAlert = ref(false);
const messageAlert = ref("");
const messageAlertTemp = ref("");

const modalOpenConfirm = ref(false);
const messageConfirm = ref("");

const handleConfirmModal = ref(function () {});

const isDisabledAction = ref(true);

const columnDefs = ref([
  {
    headerName: "",
    field: "selected",
    width: 100,
    headerComponent: GridCheckBox,
    headerComponentParams: {
      checkAll: "gridCheckAll1",
      mode: "all",
      onCustomClick: handleClickAll,
      gridName: "grid2",
    },
    cellRenderer: GridCheckBox,
    cellRendererParams: {
      checkAll: "gridCheckAll1",
      childId: "internStdSeq",
      mode: "child",
      gridName: "grid2",
      onCustomClick: handleClickChild,
    },
    maxWidth: 60,
    cellStyle:{
      display: "flex",
      alignItems: "center",
    }
  },
  {
    headerName: t("common.rowNum"),
    field: "rowNumb",
    maxWidth: 90,
  },
  {
    headerName: t("completionMng.column1"),
    field: "internShipStatus",
    minWidth: 120,
  },
  {
    headerName: t("completionMng.column2"),
    field: "colgNm",
    flex: 0.7,
  },
  {
    headerName: t("completionMng.column3"),
    field: "sustNm",
    flex: 0.7,
  },
  {
    headerName: t("completionMng.column4"),
    field: "shyrNm",
    flex: 0.3,
  },
  {
    headerName: t("completionMng.column5"),
    field: "stdNo",
    flex: 0.3,
  },
  {
    headerName: t("completionMng.column6"),
    field: "korNm",
    flex: 1.1,
  },
  {
    headerName: t("completionMng.column7"),
    field: "handNo",
    flex: 1.1,
  },
  {
    headerName: t("completionMng.column8"),
    field: "email",
    flex: 0.2,
  },
  {
    headerName: t("completionMng.column9"),
    field: "regDate",
    flex: 0.7,
  },
]);

const totalStatusComplete = ref({
  waitCompleteCount: 0,
  completeCount: 0,
  notCompleteCount: 0,
  backupsCount: 0,
});

const dataExport = ref<any>([]);
const nameOfbtn = ref<string>(t("completionMng.btnDowload"));
const fileNameExport = ref<string>(t("completionMng.pageTitle"));
const grid = ref(null);
const cloneDataSearch = ref(null);
onBeforeMount(() => {
  cloneDataSearch.value = { ...searchModel.value };
});
const loadDataGird = () => {
  cloneDataSearch.value = { ...searchModel.value };
  grid.value.initGrid();
  listSelect.value = [];
};
const getDataAll = async (page = 1, pageSize = 10) => {
  getCompletionStsIntern();
  cloneDataSearch.value.page = page;
  cloneDataSearch.value.size = pageSize;
  await completionMng.getLstInternStd({
    method: "POST",
    body: cloneDataSearch.value,
  });
  const { arr } = areAllItemsEqual(completionMng.resultData.data.content, listSelect.value, "gridCheckAll1");
  rowData.value = arr;
  totalRow.value = completionMng.resultData.data.totalElements ?? 0;
  rowData.value.forEach((item: any) => {
    item.regDate = format(new Date(item.regDate), FORMAT_YYY_MM_DD);
  });

  let rowExcel = [] as Array<Array<any>>;
  let rowExcelHeader = [] as Array<any>;

  columnDefs.value.forEach((el, index) => {
    if (index > 0 && index < columnDefs.value.length) {
      rowExcelHeader.push(el.headerName);
    }
  });

  rowData.value.forEach((el) => {
    let colExcel = [] as Array<any>;
    colExcel.push(el.rowNumb);
    colExcel.push(el.internShipStatus);
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
  dataExport.value = [{ sheetName: "sheet1", data: rowExcel, header: rowExcelHeader }];
};

const getCompletionStsIntern = async () => {
  await completionMng.getCountCompleteSts({
    method: "POST",
    body: {
      internPlanId: props.internPlanSeq,
    },
  });
  totalStatusComplete.value = completionMng.resultDataCountSts.data;
};

async function completionIntern() {
  let idInternStd = listSelect.value.map((item) => item.internStdSeq);
  await completionMng.completeInternSts({
    method: "PUT",
    body: idInternStd,
  });
  listSelect.value = [];
  if (completionMng.status.value && completionMng.status.value == SUCCSESS_STATUS) {
    loadDataGird();
    openModalAlert(t("completionMng.message.changeCompletionSuccess"));
  } else {
    openModalAlert(t("completionMng.message.changeStatusFail"));
  }
}

async function waitCompleteIntern() {
  let idInternStd = listSelect.value.map((item) => item.internStdSeq);
  await completionMng.waitCompleteInternSts({
    method: "PUT",
    body: idInternStd,
  });
  listSelect.value = [];
  if (completionMng.status.value && completionMng.status.value == SUCCSESS_STATUS) {
    loadDataGird();
    openModalAlert(messageAlertTemp);
  } else {
    openModalAlert(t("completionMng.message.changeStatusFail"));
  }
}

async function notApproveIntern() {
  let idInternStd = listSelect.value.map((item) => item.internStdSeq);
  await completionMng.notCompleteInternSts({
    method: "PUT",
    body: idInternStd,
  });
  listSelect.value = [];
  if (completionMng.status.value && completionMng.status.value == SUCCSESS_STATUS) {
    loadDataGird();
    openModalAlert(t("completionMng.message.changeNotApproveSuccess"));
  } else {
    openModalAlert(t("completionMng.message.changeStatusFail"));
  }
}

async function backupsCompleteIntern() {
  let idInternStd = listSelect.value.map((item) => item.internStdSeq);
  await completionMng.backupsCompleteInternSts({
    method: "PUT",
    body: idInternStd,
  });
  listSelect.value = [];
  if (completionMng.status.value && completionMng.status.value == SUCCSESS_STATUS) {
    loadDataGird();
    openModalAlert(t("completionMng.message.changeBackupsSuccess"));
  } else {
    openModalAlert(t("completionMng.message.changeStatusFail"));
  }
}

function handleCompletionIntern() {
  messageConfirm.value = t("completionMng.message.confirmCompletion");
  opemModalConfirm(() => {
    modalOpenConfirm.value = false;
    completionIntern();
  });
}

function handleWaitCompleteIntern() {
  messageConfirm.value = t("completionMng.message.confirmCancelCompletion");
  messageAlertTemp.value = t("completionMng.message.changeCancelCompletionSuccess");
  opemModalConfirm(() => {
    modalOpenConfirm.value = false;
    waitCompleteIntern();
  });
}
function handleWaitNotApproveIntern() {
  messageConfirm.value = t("completionMng.message.confirmCancelNotApprove");
  messageAlertTemp.value = t("completionMng.message.changeCancelNotApproveSuccess");
  opemModalConfirm(() => {
    modalOpenConfirm.value = false;
    waitCompleteIntern();
  });
}
function handleWaitBackupsIntern() {
  messageConfirm.value = t("completionMng.message.confirmCancelBackups");
  messageAlertTemp.value = t("completionMng.message.changeCancelBackupsSuccess");
  opemModalConfirm(() => {
    modalOpenConfirm.value = false;
    waitCompleteIntern();
  });
}
function handleNotApproveIntern() {
  messageConfirm.value = t("completionMng.message.confirmNotApprove");
  opemModalConfirm(() => {
    modalOpenConfirm.value = false;
    notApproveIntern();
  });
}

function handleBackupsCompleteIntern() {
  messageConfirm.value = t("completionMng.message.confirmBackups");
  opemModalConfirm(() => {
    modalOpenConfirm.value = false;
    backupsCompleteIntern();
  });
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
      isDisabledAction.value = newValue.length == 0;
    }
  },
  { deep: true },
);

function handleClickAll(value) {
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

function handleClickChild(value, flag) {
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
