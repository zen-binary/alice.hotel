<template>
  <PageContainer>
    <h1 v-once>
      {{ t("internPlanningMng.pageTitle") }}
    </h1>
    <div class="box dp_block">
      <div class="box_section">
        <div class="search_box col_2">
          <ul>
            <li>
              <p class="ta_r">{{ t("internPlanningMng.search1") }}</p>
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
              <p class="ta_r">{{ t("internPlanningMng.search2") }}</p>
              <InputBase v-model="searchModel.internPlanNm" />
            </li>
            <li>
              <p class="ta_r">{{ t("internPlanningMng.search3") }}</p>
              <div class="search_flex" style="position: relative; width: 100%">
                <BaseDatePicker v-model="searchModel.registerStr"></BaseDatePicker>
                <span>~</span>
                <BaseDatePicker v-model="searchModel.registerEnd"></BaseDatePicker>
              </div>
            </li>
            <li>
              <p class="ta_r">{{ t("internPlanningMng.search4") }}</p>
              <div class="search_flex" style="position: relative; width: 100%">
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
            {{ t("internPlanCenterMng.title") }}
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
          
        >
          <template #itemSelected>
            <div>
              <span>{{ listItemSelected && listItemSelected.length }} {{ t("internPlanCenterMng.subPage") }}</span>
            </div>
          </template>
          <template #buttonLeft></template>
          <template #button>
            <div class="btn_group btn_end">
              <button type="button" :disabled="isDisabledDelete" class="mg_l15 btn_md btn_lightgray" @click="openModalConfirmDelete">
                {{ t("common.delete") }}
              </button>
              <button type="button" :disabled="isDisabledCopy" @click="handleCopyInternPlan" class="btn_md btn_primary">
                {{ t("internPlanningMng.btnCopy") }}
              </button>
              <button type="button" class="btn_md btn_primary" @click="gotoRegister">{{ t("internPlanningMng.btnRegister") }}</button>
              <ExportFileExcel :data="dataExport" :file-name="fileNameExport" :btn-name="nameOfbtn" />
            </div>
          </template>
        </GridComponent>
      </div>
    </div>
    <TModalConfirm :is-open="modalOpenConfirmDelete" :is-show-header="true" :modal-id="'confirmDowload'" @close-modal="closeModalConfirmDelete">
      <template #default>
        <div class="box_section">
          <p>{{ t("common.message.confirmDelete") }}</p>
        </div>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_primary" @click="handleDelete">{{ t("common.confirm") }}</button>
        <button type="button" class="button btn_md btn_white popup_close" @click="closeModalConfirmDelete">{{ t("common.cancel") }}</button>
      </template>
    </TModalConfirm>
    <TModalAlert :is-open="modalOpenAlertDelete" :is-show-header="true" :is-show-footer="true">
      <template #default>
        <div class="box_section">
          <p>{{ t("internPlanningMng.successDelete") }}</p>
        </div>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_primary" @click="confirmAlertDelete">{{ t("common.confirm") }}</button>
      </template>
    </TModalAlert>
    <TModal :is-open="isModalReason" :is-show-header="true" :is-show-footer="true" :modal-id="'modalReason'" @close-modal="closeModalReason">
      <template #title>
        <strong>{{ t("internPlanningMng.titleModalReject") }}</strong>
      </template>
      <template #default><TextArea :readonly="true" :id="'modalReasonReject'" v-model="itemSelectedReasonReject.rejectionReasonDetails" /></template>
      <template #footer>
        <button type="button" class="btn_md btn_lightgray popup_close" @click="closeModalReason">{{ t("common.cancel") }}</button>
      </template>
    </TModal>
    <TModalAlert
      :is-open="isModalValidateDate"
      :is-show-header="true"
      :is-show-footer="true"
      :modal-id="'modalValidateDate'"
      @close-modal="closeModalValidateDate"
    >
      <template #default>
        <div class="box_section">
          <p>{{ t("common.messageCheckDate") }}</p>
        </div>
      </template>
      <template #footer>
        <button type="button" class="btn_md btn_lightgray popup_close" @click="closeModalValidateDate">{{ t("common.confirm") }}</button>
      </template>
    </TModalAlert>
  </PageContainer>
</template>

<script lang="ts" setup>
import { ref, onMounted, onBeforeMount, watch } from "vue";
import { FORMAT_YYY_MM_DD, MODE_COPY, MODE_CREATE, PAGINATION_PAGE_SIZE, SUCCSESS_STATUS } from "@/constants/screen.const";
import { commonStore } from "@/stores/common";
import { useRouter } from "vue-router";
import { InternPlanListModel, InternPlanningListSearchModel, InternPlanRequestModel } from "@/stores/internPlanningMng/internPlanningMng.type";
import { CodeMngModel } from "@/stores/common/codeMng/codeMng.type";
import { codeMngStore } from "@/stores/common/codeMng";
import { CD_ID_STS_REJECT, CD_ID_STS_WAIT_APPROVE, MODE_DETAIL, UP_CD_INTERN_PLAN } from "@/constants/common.const";
import InputBase from "@/components/common/input/InputBase.vue";
import { internPlanMngStore } from "@/stores/internPlanningMng";
import { format } from "date-fns";
import GroupButtonGridComponent from "@/components/common/grid/GroupButtonGridComponent.vue";
import { SCREEN } from "@/router/screen";
import GridCheckBox from "@/components/common/grid/GridCheckBox.vue";

const store = commonStore();
const router = useRouter();
const rowData = ref<InternPlanListModel>([]);
const paginationPageSize = PAGINATION_PAGE_SIZE;
const pagination = ref(true);
const totalRow = ref();
const { t } = useI18n();
const modeShow = "detail";
const codeMng = codeMngStore();
const internPlanMng = internPlanMngStore();
const fileNameExport = ref<string>(t("internPlanningMng.pageTitle"));
const nameOfbtn = ref<string>(t("internPlanningMng.btnDowload"));
const dataExport = ref<any>([]);
const isDisabledDelete = ref(true);
const isDisabledCopy = ref(true);
const modalOpenConfirmDelete = ref(false);
const modalOpenAlertDelete = ref(false);
const isModalReason = ref(false);
const isModalValidateDate = ref(false);

const listItemSelected = ref([] as Array<InternPlanListModel>);
const itemSelectedReasonReject = ref({} as InternPlanListModel);

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
const cloneDataSearch = ref(null)
const grid = ref(null)

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
          searchModel.value.internShipStr = searchModel.value.internShipEnd;
          openModalValidateDate();
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
          searchModel.value.internShipEnd = searchModel.value.internShipStr;
          openModalValidateDate();
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
          searchModel.value.registerStr = searchModel.value.registerEnd;
          openModalValidateDate();
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
          searchModel.value.registerEnd = searchModel.value.registerStr;
          openModalValidateDate();
        });
      }
    }
  },
);

const listStatus = ref<CodeMngModel[]>([{ cdId: "", cdNm: t("common.all"), upCdId: "" }]);

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

onBeforeMount(() => {
  cloneDataSearch.value = {...searchModel.value}
  getListCodeMng();
});

const columnDefs = ref([
  {
    headerName: "",
    field: "selected",
    headerComponent: GridCheckBox,
    headerComponentParams: {
      checkAll: "checkAll",
      mode: "all",
      onCustomClick: checkAll,
      gridName: "grid1",
    },
    cellRenderer: GridCheckBox,
    cellRendererParams: {
      checkAll: "checkAll",
      childId: "userId",
      mode: "child",
      gridName: "grid1",
      onCustomClick: checkChild,
    },
    flex: 0.1,
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
    headerName: t("internPlanningMng.column1"),
    field: "status",
    flex: 0.7,
    maxWidth: 110
  },
  {
    headerName: t("internPlanningMng.column2"),
    field: "confirmDate",
    flex: 0.7,
  },
  {
    headerName: t("internPlanningMng.column3"),
    field: "cormNameKr",
    flex: 0.7,
    minWidth:150
  },
  {
    headerName: t("internPlanningMng.column4"),
    field: "internPlanNm",
    flex: 1.3,
    minWidth:200
  },
  {
    headerName: t("internPlanningMng.column5"),
    field: "executionCount",
    flex: 0.3,
    maxWidth:100
  },
  {
    headerName: t("internPlanningMng.column6"),
    field: "registerDate",
    flex: 1.1,
  },
  {
    headerName: t("internPlanningMng.column7"),
    field: "internShipDate",
    flex: 1.1,
  },
  {
    headerName: t("internPlanningMng.column8"),
    field: "regDate",
    flex: 0.7,
  },
  {
    headerName: t("internPlanningMng.column9"),
    cellRenderer: GroupButtonGridComponent,
    cellRendererParams: {
      value1: t("internPlanningMng.searchText"),
      value2: t("internPlanningMng.reject"),
      onCustomEvent1: handleDetail,
      onCustomEvent2: handleReject,
    },
    maxWidth: 150,
  },
]);

function checkAll(value: boolean) {
  if (value) {
    const itemsToAdd = rowData.value.filter(
      (item) => !listItemSelected.value.some((selectedItem) => selectedItem.internPlanSeq === item.internPlanSeq),
    );
    listItemSelected.value = [...listItemSelected.value, ...itemsToAdd];
  } else {
    listItemSelected.value = [];
  }

  rowData.value = rowData.value.map((item) => ({
    ...item,
    selected: value,
  }));
}

function checkChild(value: InternPlanListModel, flag: boolean) {
  const exists = listItemSelected.value.some((item) => item.internPlanSeq === value.internPlanSeq);

  if (flag && !exists) {
    listItemSelected.value.push(value);
  } else if (!flag && exists) {
    listItemSelected.value = listItemSelected.value.filter((item) => item.internPlanSeq !== value.internPlanSeq);
  }
}

function areAllItemsEqual(rowData: InternPlanListModel[], listItemSelected: { value: InternPlanListModel[] }, idInput: string) {
  const allCheckbox = document.getElementById(idInput) as HTMLInputElement;
  allCheckbox.checked = false;

  const allChecked = rowData.every((item) => listItemSelected.value.some((selectedItem) => selectedItem.internPlanSeq === item.internPlanSeq));

  allCheckbox.checked = allChecked;

  const updatedArr = rowData.map((item) => {
    const isSelected = listItemSelected.value.some((selectedItem) => selectedItem.internPlanSeq === item.internPlanSeq);

    return {
      ...item,
      selected: allChecked ? true : isSelected,
    };
  });

  return { checked: allChecked, arr: updatedArr };
}

function search() {
  cloneDataSearch.value = {...searchModel.value};
  grid.value.initGrid();
  listItemSelected.value = [];
}

const getDataAll = async (page = 1, pageSize = 10) => {
  cloneDataSearch.value.page = page;
  cloneDataSearch.value.size = pageSize;
  await internPlanMng.searchListInternPlan({
    method: "POST",
    body: cloneDataSearch.value,
  });
  const { arr } = areAllItemsEqual(internPlanMng.resultData.data.content, listItemSelected, "checkAll");
  rowData.value = arr;
  totalRow.value = internPlanMng.resultData.data.totalElements;

  if (rowData.value?.length <= 0) {
    const checkAllElement = document.getElementById("checkAll") as HTMLInputElement;
    if (checkAllElement) {
      checkAllElement.checked = false;
    }
  }
  rowData.value.forEach((item: any) => {
    item.confirmDate = item.confirmDate ? format(new Date(item.confirmDate), FORMAT_YYY_MM_DD) : "-";

    item.regDate = format(new Date(item.regDate), FORMAT_YYY_MM_DD);
    item.executionCount += t("internPlanningMng.times");
    item.isCheck = item.statusCd === CD_ID_STS_REJECT;
  });

  let rowExcel = [] as Array<Array<any>>;
  let rowExcelHeader = [] as Array<any>;

  columnDefs.value.forEach((el, index) => {
    if (index > 0 && index < columnDefs.value.length - 1) {
      rowExcelHeader.push(el.headerName);
    }
  });

  rowData.value.forEach((el) => {
    let colExcel = [] as Array<any>;
    colExcel.push(el.rowNumb);
    colExcel.push(el.status);
    colExcel.push(el.confirmDate);
    colExcel.push(el.cormNameKr);
    colExcel.push(el.internPlanNm);
    colExcel.push(el.executionCount);
    colExcel.push(el.registerDate);
    colExcel.push(el.internShipDate);
    colExcel.push(el.regDate);
    rowExcel.push(colExcel);
  });
  dataExport.value = [{ sheetName: "sheet1", data: rowExcel, header: rowExcelHeader }];
};

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

function handleDetail(data: InternPlanListModel) {
  router.push({
    name: SCREEN.internPlanningCrud.name,
    params: { mode: MODE_DETAIL },
    state: { internPlanSeq: data.internPlanSeq },
  });
}

function closeModalReason() {
  isModalReason.value = false;
}

function openModalValidateDate() {
  isModalValidateDate.value = true;
}

function closeModalValidateDate() {
  isModalValidateDate.value = false;
}

function handleReject(data: InternPlanListModel) {
  isModalReason.value = true;
  itemSelectedReasonReject.value = data;
}

watch(
  listItemSelected,
  (newValue) => {
    if (newValue) {
      isDisabledDelete.value = !(newValue.length > 0 && newValue.every((item) => item.statusCd === CD_ID_STS_WAIT_APPROVE));
    }
  },
  { deep: true },
);

watch(
  listItemSelected,
  (newValue) => {
    if (newValue) {
      isDisabledCopy.value = newValue.length !== 1;
    }
  },
  { deep: true },
);

function openModalConfirmDelete() {
  modalOpenConfirmDelete.value = true;
}

function closeModalConfirmDelete() {
  modalOpenConfirmDelete.value = false;
}

async function handleDelete() {
  const listIdSelected = listItemSelected.value.map((item) => item.internPlanSeq);
  await internPlanMng.deleteListInternPlan({
    method: "POST",
    body: listIdSelected,
  });
  if (internPlanMng.status.value === SUCCSESS_STATUS) {
    closeModalConfirmDelete();
    modalOpenAlertDelete.value = true;
  }
}

function confirmAlertDelete() {
  modalOpenAlertDelete.value = false;
  search();
}

function gotoRegister() {
  router.push({
    name: SCREEN.internPlanningCrud.name,
    params: { mode: MODE_CREATE },
    state: { internPlanSeq: "" },
  });
}

async function handleCopyInternPlan() {
  let internDetailSeq = listItemSelected.value[0].internPlanSeq;
  router.push({
    name: SCREEN.internPlanningCrud.name,
    params: { mode: MODE_COPY },
    state: { internPlanSeq: internDetailSeq },
  });
}
</script>
