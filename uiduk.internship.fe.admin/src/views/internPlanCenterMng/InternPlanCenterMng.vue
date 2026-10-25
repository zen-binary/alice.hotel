<template>
  <PageContainer>
    <h1 v-once>
      {{ t('internPlanCenterMng.pageTitle') }}
    </h1>
    <div class="box dp_block">
      <div class="box_section">
        <div class="search_box col_3 ta_r">
          <ul>
            <li>
              <p class="ta_r">{{ t("internPlanCenterMng.search1") }}</p>
              <RadiobuttonBase
                v-for="item in listStatus"
                :mode="modeShow"
                :value="item.cdId"
                :id="'status' + item.cdId"
                :name="'status' + item.cdId"
                :key="'status' + item.cdId"
                :label="item.cdNm"
                v-model="searchModel.status"
                :checked="searchModel.status === item.cdId"
              ></RadiobuttonBase>
            </li>
            <li>
              <p class="ta_r">{{ t("internPlanCenterMng.search2") }}</p>
              <InputBase v-model="searchModel.internPlanNm" />
            </li>
            <li>
              <p class="ta_r">{{ t("internPlanCenterMng.search3") }}</p>
              <div class="search_flex" style="position: relative">
                <BaseDatePicker v-model="searchModel.registerStr"></BaseDatePicker>
                <span>~</span>
                <BaseDatePicker v-model="searchModel.registerEnd"></BaseDatePicker>
              </div>
            </li>
            <li>
              <p class="ta_r">{{ t("internPlanCenterMng.search4") }}</p>
              <div class="search_flex" style="position: relative">
                <BaseDatePicker v-model="searchModel.internShipStr"></BaseDatePicker>
                <span>~</span>
                <BaseDatePicker v-model="searchModel.internShipEnd"></BaseDatePicker>
              </div>
            </li>
          </ul>
          <div class="btn_group btn_end mg_t30 btn_form_search">
            <button type="button" class="button btn_md btn_lightgray btn_responsive" @click="reset()">{{ t("common.reset") }}</button>
            <button type="button" class="button btn_md btn_primary btn_responsive" @click="search()">{{ t("common.search") }}</button>
          </div>
        </div>
      </div>
    </div>
    <div class="box dp_block">
      <div class="box_section">
        <div class="page_toparea">
          <p class="box_title_sm">
            {{ t("internPlanCenterMng.pageTitle") }}
          </p>
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
              <span>{{ listSelect.length }} {{ t("internPlanCenterMng.subPage") }}</span>
            </div>
          </template>
          <template #button>
            <div class="btn_group btn_end">
              <button type="button" :disabled="listSelect.length == 0" class="mg_l15 btn_md btn_lightgray" @click="handleDeleteInternPlan">{{ t("common.delete") }}</button>
              <button type="button" :disabled="listSelect.length == 0" class="btn_md btn_primary" @click="handleApproveInternPlan">{{ t("internPlanCenterMng.approve") }}</button>
              <button type="button" :disabled="listSelect.length == 0" class="btn_md btn_primary" @click="handleCancelApproveInternPlan">{{ t("internPlanCenterMng.cancelApprove") }}</button>
              <button type="button" :disabled="listSelect.length == 0" class="btn_md btn_primary" @click="openModalMessageReject">{{ t("internPlanCenterMng.reject") }}</button>
              <button type="button" :disabled="listSelect.length == 0" class="btn_md btn_primary" @click="handleCancelRejectInternPlan">{{ t("internPlanCenterMng.cancelReject") }}</button>
              <ExportFileExcel :data="dataExport" :file-name="fileNameExport" :btn-name="nameOfbtn" @click="handleActionExportExcel"/>
            </div>
          </template>
        </GridComponent>
      </div>
    </div>
    <InternPlanStudentRegPopup
      v-if="modalOpen"
      :isOpen="modalOpen"
      :internPlanSeq="internPlanSeq"
      :internPlanNm="internPlanNm"
      @closeModal="closeModal"
      @getAllDataInternPlan="getDataAll"
    ></InternPlanStudentRegPopup>

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

    <TModal :is-open="modalOpenMessageReject" :is-show-header="true" :is-show-footer="true" :modal-id="'modalOpenMessageReject'" @close-modal="closeModalMessageReject">
        <!-- Modal content goes here -->
        <template #title>
          <strong>{{ t("internPlanCenterMng.modal.tile") }}</strong>
        </template>
        <template #default>
          <div class="box_section">
            <TextArea id="reszie_auto"  cols="30" rows="10" v-model="messageReject" :resize-auto="true" :is-show-count-word="false" :max-words="4000" :disabled="checkModalMessageReject"/>
          </div>
        </template>
        <template #footer>
          <!-- Footer content goes here -->
          <button type="button" class="button btn_md btn_primary" @click="handleRejectInternPlan" v-if="!checkModalMessageReject">{{ t("common.confirm") }}</button>
          <button type="button" class="button btn_md btn_white popup_close" @click="closeModalMessageReject">{{ t("common.cancel") }}</button>
        </template>
      </TModal>
  </PageContainer>
</template>

<script setup lang="ts">
import { ref, onBeforeMount, nextTick, onUpdated ,watch} from "vue";
import { FORMAT_YYY_MM_DD, PAGINATION_PAGE_SIZE, SUCCSESS_STATUS } from "@/constants/screen.const";
import { InternPlanningListSearchModel } from "@/stores/internPlanCenterMng/internPlanCenterMng.type";
import { CodeMngModel } from "@/stores/common/codeMng/codeMng.type";
import { codeMngStore } from "@/stores/common/codeMng";
import { UP_CD_INTERN_PLAN, CD_INTERN_PLAN_REJECT_APPROVAL,MODE_DETAIL } from "@/constants/common.const";
import InputBase from "@/components/common/input/InputBase.vue";
import { internPlanCenterMngStore } from "@/stores/internPlanCenterMng/useInternPlanCenterMng";
import { format } from "date-fns";
import MutiButtonGridComponent from "@/components/common/grid/MutiButtonGridComponent.vue";
import InternPlanStudentRegPopup from "./popup/InternPlanStudentRegPopup.vue";
import GridComponent from "@/components/common/grid/GridComponent.vue";
import GridCheckBox from "@/components/common/grid/GridCheckBox.vue";
import { SCREEN } from "@/router/screen";
const router = useRouter();

const rowData = ref<any>([]);
const paginationPageSize = PAGINATION_PAGE_SIZE;
const pagination = ref(true);
const totalRow = ref();
const { t } = useI18n();
const modeShow = "detail";
const codeMng = codeMngStore();
const internPlanMng = internPlanCenterMngStore();
const fileNameExport = ref<string>(t("internPlanCenterMng.pageTitle"));
const nameOfbtn = ref<string>(t("internPlanCenterMng.btnDowload"));
const dataExport = ref<any>([]);
const grid = ref(null)
const cloneDataSearch = ref(null)

const searchModel = ref({
  internPlanNm: "",
  status: "",
  registerEnd: "",
  registerStr: "",
  internShipStr: "",
  internShipEnd: "",
  page: 1,
  size: 10,
  sort: "",
} as InternPlanningListSearchModel);

const modalOpen = ref(false);
const internPlanSeq = ref("");
const internPlanNm = ref("");

const listStatus = ref<CodeMngModel[]>([{ cdId: "", cdNm: t("common.all"), upCdId: "" }]);
const listSelect = ref([]);

const modalOpenAlert = ref(false);
const messageAlert = ref("");

const modalOpenConfirm = ref(false);
const messageConfirm = ref("");

const handleConfirmModal = ref(function () {});

const modalOpenMessageReject = ref(false);
const messageReject = ref('');
const checkModalMessageReject = ref(false);

onBeforeMount(() => {
  cloneDataSearch.value = {...searchModel.value}
  getListCodeMng();
});

const columnDefs = ref([
  {
    headerName: "",
    field: "selected",
    width: 100,
    headerComponent: GridCheckBox,
    headerComponentParams: {
      checkAll: "gridCheckAll",
      mode: "all",
      onCustomClick: handleClickAll,
      gridName: "grid1",
    },
    cellRenderer: GridCheckBox,
    cellRendererParams: {
      checkAll: "gridCheckAll",
      childId: "internPlanSeq",
      mode: "child",
      gridName: "grid1",
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
    headerName: t("internPlanCenterMng.column1"),
    field: "status",
    minWidth: 120,
  },
  {
    headerName: t("internPlanCenterMng.column2"),
    field: "confirmDate",
    minWidth: 150,
  },
  {
    headerName: t("internPlanCenterMng.column3"),
    field: "cormNameKr",
    flex: 0.7,
  },
  {
    headerName: t("internPlanCenterMng.column4"),
    field: "internPlanNm",
    flex: 1.3,
  },
  {
    headerName: t("internPlanCenterMng.column5"),
    field: "executionCount",
    flex: 0.3,
  },
  {
    headerName: t("internPlanCenterMng.column6"),
    field: "registerDate",
    flex: 1.1,
  },
  {
    headerName: t("internPlanCenterMng.column7"),
    field: "internShipDate",
    flex: 1.1,
  },
  {
    headerName: t("internPlanCenterMng.column8"),
    field: "regDate",
    flex: 0.7,
  },
  {
    headerName: t("internPlanCenterMng.column9"),
    field: "internRecruit",
    minWidth: 120,
  },
  {
    headerName: t("internPlanCenterMng.column10"),
    field: "internApplyRecruit",
    minWidth: 120,
  },
  {
    headerName: t("internPlanCenterMng.column11"),
    flex: 1.8,
    cellRenderer: MutiButtonGridComponent,
    cellRendererParams:(data)=>( {
      buttons: [
        {
          label: t("internPlanCenterMng.studentReg"),
          className: "button btn_xs btn_primary",
          isDisabled: false,
          isShow: true,
          onClick: (data: object) => {
            openModalStudenReg(data);
          },
        },
        {
          label: t("internPlanCenterMng.searchText"),
          isDisabled: false,
          isShow: true,
          className: "button btn_xs btn_primary",
          onClick: (data: object) => {
            detailInternPlan(data);
          },
        },
        {
          label: t("internPlanCenterMng.reject"),
          isDisabled: false,
          isShow: data?.data.statusCd == CD_INTERN_PLAN_REJECT_APPROVAL,
          className: "button btn_xs btn_primary",

          onClick: (data: object) => {
            openModalReject(data);
          },
        },
      ],
    }),
  },
]);

function search() {
  cloneDataSearch.value = {...searchModel.value}
  grid.value.initGrid()
  listSelect.value = []
}
async function getListCodeMng() {
  await codeMng.getCodeInfo({
    method: "POST",
    body: {
      upCdIdList: [UP_CD_INTERN_PLAN],
    },
  });
  codeMng.codeInfo.data.forEach((item) => {
    listStatus.value.push(item);
  });
}
const getDataAll = async (page = 1, pageSize = 10) => {
  messageReject.value = '';
  cloneDataSearch.value.page = page;
  cloneDataSearch.value.size = pageSize;
  await internPlanMng.searchListInternPlan({
    method: "POST",
    body: cloneDataSearch.value,
  });
  const { arr } = areAllItemsEqual(internPlanMng.resultData.data.content, listSelect.value, "gridCheckAll");
  rowData.value = arr;
  totalRow.value = internPlanMng.resultData.data.totalElements ?? 0;

  rowData.value.forEach((item: any) => {
    if (item.confirmDate) {
      item.confirmDate = format(new Date(item.confirmDate), FORMAT_YYY_MM_DD);
    } else {
      item.confirmDate = "-";
    }
    item.regDate = format(new Date(item.regDate), FORMAT_YYY_MM_DD);
    item.executionCount = item.executionCount + t("internPlanCenterMng.times");
  });
};

async function deletedIntenPlan() {
  let idDelete = listSelect.value.map(item => item.internPlanSeq);
  await internPlanMng.deleteInternPlan({
    method: "PUT",
    body: idDelete,
  });
  listSelect.value = [];
  if (internPlanMng.status.value && internPlanMng.status.value == SUCCSESS_STATUS) {
    search();
    openModalAlert(t('internPlanCenterMng.message.deleteSuccess'));
  }
}
async function approveIntenPlan() {
  let idInternPlan = listSelect.value.map(item => item.internPlanSeq);
  await internPlanMng.approveInternPlan({
    method: "PUT",
    body: {
      lstIdInternPlan: idInternPlan,
      reasonMessage: "",
    },
  });
  listSelect.value = [];
  if (internPlanMng.status.value && internPlanMng.status.value == SUCCSESS_STATUS) {
    search();
    openModalAlert(t('internPlanCenterMng.message.changeStatusApproveSuccess'));
  }else{
    openModalAlert(t('internPlanCenterMng.message.changeStatusFail'));
  }
}

async function cancelApproveIntenPlan() {
  let idInternPlan = listSelect.value.map(item => item.internPlanSeq);
  await internPlanMng.cancelApproveInternPlan({
    method: "PUT",
    body: {
      lstIdInternPlan: idInternPlan,
      reasonMessage: "",
    },
  });
  listSelect.value = [];
  if (internPlanMng.status.value && internPlanMng.status.value == SUCCSESS_STATUS) {
    search();
    openModalAlert(t('internPlanCenterMng.message.changeStatusCancelApproveSuccess'));
  }else{
    openModalAlert(t('internPlanCenterMng.message.changeStatusFail'));
  }
}

async function cancelRejectIntenPlan() {
  let idInternPlan = listSelect.value.map(item => item.internPlanSeq);
  await internPlanMng.cancelApproveInternPlan({
    method: "PUT",
    body: {
      lstIdInternPlan: idInternPlan,
      reasonMessage: "",
    },
  });
  listSelect.value = [];
  if (internPlanMng.status.value && internPlanMng.status.value == SUCCSESS_STATUS) {
    search();
    openModalAlert(t('internPlanCenterMng.message.changeCancelRejectInternPlanSuccess'));
  }else{
    openModalAlert(t('internPlanCenterMng.message.changeStatusFail'));
  }
}

async function rejectIntenPlan() {
  let idInternPlan = listSelect.value.map(item => item.internPlanSeq);
  await internPlanMng.rejectApproveInternPlan({
    method: "PUT",
    body: {
      lstIdInternPlan: idInternPlan,
      reasonMessage: messageReject.value,
    },
  });
  listSelect.value = [];
  if (internPlanMng.status.value && internPlanMng.status.value == SUCCSESS_STATUS) {
    search();
    openModalAlert(t('internPlanCenterMng.message.rejectInternPlanSuccess'));
  }else{
    openModalAlert(t('internPlanCenterMng.message.changeStatusFail'));
  }
}

function handleDeleteInternPlan() {
  messageConfirm.value = t('internPlanCenterMng.message.confirmDelete')
  opemModalConfirm(() => {
    modalOpenConfirm.value = false;
    deletedIntenPlan();
  });
}

function handleApproveInternPlan() {
  messageConfirm.value = t('internPlanCenterMng.message.confirmChangeStatusApprove')
  opemModalConfirm(() => {
    modalOpenConfirm.value = false;
    approveIntenPlan();
  });
}

function handleCancelApproveInternPlan() {
  messageConfirm.value = t('internPlanCenterMng.message.confirmChangeStatusCancelApprove')
  opemModalConfirm(() => {
    modalOpenConfirm.value = false;
    cancelApproveIntenPlan();
  });
}

function handleCancelRejectInternPlan() {
  messageConfirm.value = t('internPlanCenterMng.message.confirmCancelRejectInternPlan')
  opemModalConfirm(() => {
    modalOpenConfirm.value = false;
    cancelRejectIntenPlan();
  });
}

function handleRejectInternPlan() {
  modalOpenMessageReject.value = false;
  messageConfirm.value = t('internPlanCenterMng.message.confirmRejectInternPlan')
  opemModalConfirm(() => {
    modalOpenConfirm.value = false;
    rejectIntenPlan();
  });
}

const handleActionExportExcel = async () => {
  if (totalRow.value > 0) {
    const searchToExport = { ...cloneDataSearch.value };
    searchToExport.page = 1;
    searchToExport.size = totalRow.value;

    await internPlanMng
      .searchListInternPlan({
        method: "POST",
        body: searchToExport,
      })
      .then(() => {
        const dataExcel = internPlanMng.resultData.data.content;
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
    if (index > 0 && index < columnDefs.value.length - 1) {
      rowExcelHeader.push(el.headerName);
    }
  });

  dataExcel.forEach((el: any) => {
    let colExcel = [] as Array<any>;
    el.confirmDate = el.confirmDate ? format(new Date(el.confirmDate), FORMAT_YYY_MM_DD) : "-";
    el.regDate = format(new Date(el.regDate), FORMAT_YYY_MM_DD);
    el.executionCount = el.executionCount + t("internPlanCenterMng.times");
    
    colExcel.push(el.rowNumb);
    colExcel.push(el.status);
    colExcel.push(el.confirmDate);
    colExcel.push(el.cormNameKr);
    colExcel.push(el.internPlanNm);
    colExcel.push(el.executionCount);
    colExcel.push(el.registerDate);
    colExcel.push(el.internShipDate);
    colExcel.push(el.regDate);
    colExcel.push(el.internRecruit);
    colExcel.push(el.internApplyRecruit);
    rowExcel.push(colExcel);
  });

  dataExport.value = [{ sheetName: "sheet1", data: rowExcel, header: rowExcelHeader }] as Array<ExcelData>;
}

function openModalMessageReject() {
  messageReject.value = '';
  checkModalMessageReject.value = false;
  modalOpenMessageReject.value = true;
}
function closeModalMessageReject() {
  modalOpenMessageReject.value = false;
}

function openModalReject(data: any) {
  messageReject.value = data.rejectionReasonDetails
  checkModalMessageReject.value = true;
  modalOpenMessageReject.value = true;
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
function closeModal() {
  modalOpen.value = false;
}

function openModalStudenReg(data: any) {
  internPlanSeq.value = data.internPlanSeq;
  internPlanNm.value = data.internPlanNm;
  modalOpen.value = true;
}
function detailInternPlan(data: any) {
  router.push({
    name: SCREEN.internPlanCenterDetail.name,
    params: { mode: MODE_DETAIL },
    state: { internPlanSeq: data.internPlanSeq },
  });
}

function reset() {
  searchModel.value = {
    internPlanNm: "",
    status: "",
    registerEnd: "",
    registerStr: "",
    internShipStr: "",
    internShipEnd: "",
    page: 1,
    size: 10,
    sort: "",
  };
}
const formatDate = (date) => {
  return new Date(date);
};

watch(
  () => searchModel.value.internShipStr,
  (newValue) => {
    if (!searchModel.value.internShipEnd) {
      searchModel.value.internShipStr = newValue;
    } else {
      const dStart = formatDate(newValue);
      const dEnd = formatDate(searchModel.value.internShipEnd);
      if (dStart > dEnd) {
        nextTick(() => {
          openModalAlert( t("common.messageCheckDate"))
          searchModel.value.internShipStr = searchModel.value.internShipEnd;
        });
      }
    }
  },
);

watch(
  () => searchModel.value.internShipEnd,
  (newValue) => {
    if (!searchModel.value.internShipStr) {
      searchModel.value.internShipEnd = newValue;
    } else {
      const dStart = formatDate(searchModel.value.internShipStr);
      const dEnd = formatDate(newValue);
      if (dStart > dEnd) {
        nextTick(() => {
          openModalAlert( t("common.messageCheckDate"))
          searchModel.value.internShipEnd = searchModel.value.internShipStr;
        });
      }
    }
  },
);

watch(
  () => searchModel.value.registerStr,
  (newValue) => {
    if (!searchModel.value.registerEnd) {
      searchModel.value.registerStr = newValue;
    } else {
      const dStart = formatDate(newValue);
      const dEnd = formatDate(searchModel.value.registerEnd);
      if (dStart > dEnd) {
        nextTick(() => {
          openModalAlert( t("common.messageCheckDate"))
          searchModel.value.registerStr = searchModel.value.registerEnd;
        });
      }
    }
  },
);

watch(
  () => searchModel.value.registerEnd,
  (newValue) => {
    if (!searchModel.value.registerStr) {
      searchModel.value.registerEnd = newValue;
    } else {
      const dStart = formatDate(searchModel.value.registerStr);
      const dEnd = formatDate(newValue);
      if (dStart > dEnd) {
        nextTick(() => {
          openModalAlert( t("common.messageCheckDate"))
          searchModel.value.registerEnd = searchModel.value.registerStr;
        });
      }
    }
  },
);

function handleClickAll(value) {
  rowData.value.forEach((item) => {
      if (value) {
        if (!listSelect.value.some((el) => el.internPlanSeq == item.internPlanSeq)) {
            listSelect.value.push(item);
          }
        } else {
          listSelect.value = listSelect.value.filter(
          (el) => el.internPlanSeq != item.internPlanSeq
      );
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
    listSelect.value = listSelect.value.filter((item) => item.internPlanSeq != value.internPlanSeq);
  }

  //add logic update value selected
  rowData.value = rowData.value.map((item) => {
    if (item.internPlanSeq === value.internPlanSeq) {
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
    return listSelect.some((item2) => item1.internPlanSeq === item2.internPlanSeq);
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
      const isSelected = listSelect.some((selectedItem) => selectedItem.internPlanSeq === item.internPlanSeq);
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
    const e = document.getElementById(item.internPlanSeq);
    if (e) {
      e.checked = true;
    }
  });
});
</script>
