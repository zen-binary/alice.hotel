<template>
  <PageContainer>
    <h1 v-once>
      {{ t("corporateApprovalMng.pageTitle") }}
    </h1>
    <div class="box dp_block">
      <div class="box_section">
        <div class="search_box col_2 ta_r">
          <ul>
            <li>
              <p class="ta_r">{{ t("corporateApprovalMng.search1") }}</p>
              <RadiobuttonBase
                v-for="item in listStatus"
                :mode="modeShow"
                :value="item.cdId"
                :id="'status_' + item.cdId"
                :name="'status_' + item.cdId"
                :key="'status_' + item.cdId"
                :label="item.cdNm"
                v-model="searchModel.cormStatusCd"
                :checked="searchModel.cormStatusCd === item.cdId"
                class="radio_status_approval"
              ></RadiobuttonBase>
            </li>
            <li>
              <p class="ta_r">{{ t("corporateApprovalMng.search2") }}</p>
              <BaseDatePicker v-model="searchModel.cormConfirmDateStr"></BaseDatePicker>
              <div class="search_flex">
                <span>~</span>
              </div>
              <BaseDatePicker v-model="searchModel.cormConfirmDateEnd"></BaseDatePicker>
            </li>
            <li>
              <p class="ta_r">{{ t("corporateApprovalMng.search3") }}</p>
              <InputBase v-model="searchModel.cormUserId" />
            </li>
            <li>
              <p class="ta_r">{{ t("corporateApprovalMng.search4") }}</p>
              <InputBase v-model="searchModel.cormNmKr" />
            </li>
            <li>
              <p class="ta_r">{{ t("corporateApprovalMng.search5") }}</p>
              <SelectBoxBase :data="listSelectType" v-model="searchModel.cormClassificationCd"></SelectBoxBase>
            </li>
            <li>
              <p class="ta_r">{{ t("corporateApprovalMng.search6") }}</p>
              <SelectBoxBase :data="listSelectAward" v-model="searchModel.cormAwardCd"></SelectBoxBase>
            </li>
            <li>
              <p class="ta_r">{{ t("corporateApprovalMng.search7") }}</p>
              <BaseDatePicker v-model="searchModel.strDate"></BaseDatePicker>
              <div class="search_flex">
                <span>~</span>
              </div>
              <BaseDatePicker v-model="searchModel.endDate"></BaseDatePicker>
            </li>
          </ul>
          <div class="btn_group btn_end mg_t10 btn_form_search">
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
            {{ t("corporateApprovalMng.tableTitle") }}
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
              <span>{{ listSelect.length }} {{ t("corporateApprovalMng.subPage") }}</span>
            </div>
          </template>
          <template #button>
            <div class="btn_group btn_end">
              <button type="button" :disabled="!isDisabledApproval" class="btn_md btn_primary" @click="handleApproveCorporate">
                {{ t("corporateApprovalMng.approve") }}
              </button>
              <ExportFileExcel
                :data="dataExport"
                :file-name="fileNameExport"
                :btn-name="nameOfbtn"
                @click="handleActionClickExportExcel"
                :textConfirmDownload="textConfirmExcel"
              />
            </div>
          </template>
        </GridComponent>
      </div>
    </div>

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
    <CorporateApprovalDetail
      v-if="modalOpenDetailCorp"
      :isOpen="modalOpenDetailCorp"
      :cormSeq="cormSeq"
      @closeModal="closeModalDetailCorp"
      @getAllDataInternPlan="getDataAll"
    ></CorporateApprovalDetail>
  </PageContainer>
</template>

<script setup lang="ts">
import { ref, onBeforeMount, nextTick, onUpdated, watch } from "vue";
import { FORMAT_YYY_MM_DD, PAGINATION_PAGE_SIZE, SUCCSESS_STATUS } from "@/constants/screen.const";
import { CorporateApprovalMngSearch } from "@/stores/corporateApprovalMng/corporateApprovalMng.type";
import { CodeMngModel } from "@/stores/common/codeMng/codeMng.type";
import { codeMngStore } from "@/stores/common/codeMng";
import InputBase from "@/components/common/input/InputBase.vue";
import { corporateApprovalMngStore } from "@/stores/corporateApprovalMng/useCorporateApprovalMng";
import { format } from "date-fns";
import GridComponent from "@/components/common/grid/GridComponent.vue";
import GridCheckBox from "@/components/common/grid/GridCheckBox.vue";
import ButtonGridComponent from "@/components/common/grid/ButtonGridComponent.vue";
import CorporateApprovalDetail from "./modalCorpDetail/CorporateApprovalDetail.vue";
import { UP_CD_CORM_TYPE, UP_CD_CORM_AWARD, UP_CD_CORM_APPROVAL, CD_ID_CORM_WAIT_APPROVAL } from "@/constants/common.const";
import { ExcelData } from "@/model/common/excelData.type";

const rowData = ref<any>([]);
const paginationPageSize = PAGINATION_PAGE_SIZE;
const pagination = ref(true);
const totalRow = ref();
const { t } = useI18n();
const modeShow = "detail";
const codeMng = codeMngStore();
const corporateApprovalMng = corporateApprovalMngStore();
const fileNameExport = ref<string>(t("corporateApprovalMng.pageTitle"));
const nameOfbtn = ref<string>(t("corporateApprovalMng.btnDowload"));
const textConfirmExcel = ref<string>(t("corporateApprovalMng.textConfirmExcel"));
const dataExport = ref<Array<ExcelData>>([]);
const currentPage = ref(1);

const searchModel = ref({
  cormStatusCd: "",
  cormUserId: "",
  cormNmKr: "",
  cormClassificationCd: "",
  cormAwardCd: "",
  cormConfirmDateStr: "",
  cormConfirmDateEnd: "",
  strDate: "",
  endDate: "",
  page: currentPage.value,
  size: paginationPageSize,
  sort: "",
} as CorporateApprovalMngSearch);

const cormSeq = ref("");

const listStatus = ref<CodeMngModel[]>([{ cdId: "", cdNm: t("common.all"), upCdId: "" }]);
const listSelectType = ref([{ cdId: "", cdNm: t("common.all"), upCdId: "" }]);
const listSelectAward = ref([{ cdId: "", cdNm: t("common.all"), upCdId: "" }]);

const listSelect = ref([]);

const modalOpenAlert = ref(false);
const messageAlert = ref("");

const modalOpenConfirm = ref(false);
const messageConfirm = ref("");

const handleConfirmModal = ref(function () {});

const modalOpenDetailCorp = ref(false);
const isDisabledApproval = ref(false);
const grid = ref(null);
const cloneDataSearch = ref(null);

onBeforeMount(() => {
  cloneDataSearch.value = { ...searchModel.value };
  getListCodeMng();
});
const columnDefs = ref([
  {
    headerName: "",
    field: "selected",
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
      childId: "cormSeq",
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
    headerName: t("corporateApprovalMng.column1"),
    field: "cormStatusNm",
    flex: 1.5,
  },
  {
    headerName: t("corporateApprovalMng.column2"),
    field: "cormConfirmDate",
    flex: 1.5,
  },
  {
    headerName: t("corporateApprovalMng.column3"),
    field: "cormUserId",
    flex: 2,
  },
  {
    headerName: t("corporateApprovalMng.column4"),
    field: "cormNameKr",
    flex: 4,
  },
  {
    headerName: t("corporateApprovalMng.column5"),
    field: "cormClassificationNm",
    flex: 3,
  },
  {
    headerName: t("corporateApprovalMng.column6"),
    field: "cormAwardNm",
    flex: 3,
  },
  {
    headerName: t("corporateApprovalMng.column7"),
    field: "regDate",
    flex: 1.5,
  },
  {
    headerName: t("corporateApprovalMng.column8"),
    cellRenderer: ButtonGridComponent,
    cellRendererParams: {
      className: "button btn_xs btn_primary",
      value: t("corporateApprovalMng.lookup"),
      onCustomEvent: openModalDetailCorp,
    },
    flex: 1,
  },
]);

function search() {
  cloneDataSearch.value = { ...searchModel.value };
  grid.value.initGrid();
  listSelect.value = [];
}
async function getListCodeMng() {
  await codeMng.getCodeInfo({
    method: "POST",
    body: {
      upCdIdList: [UP_CD_CORM_APPROVAL, UP_CD_CORM_AWARD, UP_CD_CORM_TYPE],
    },
  });
  codeMng.codeInfo.data.forEach((item) => {
    if (item.upCdId === UP_CD_CORM_APPROVAL) {
      listStatus.value.push(item);
    }
    if (item.upCdId === UP_CD_CORM_TYPE) {
      listSelectType.value.push(item);
    }
    if (item.upCdId === UP_CD_CORM_AWARD) {
      listSelectAward.value.push(item);
    }
  });
}
const getDataAll = async (page = 1, pageSize = 10) => {
  cloneDataSearch.value.page = page;
  cloneDataSearch.value.size = pageSize;
  await corporateApprovalMng.getListCorporateApprovalMng({
    method: "POST",
    body: cloneDataSearch.value,
  });
  const { arr } = areAllItemsEqual(corporateApprovalMng.resultData.data.content, listSelect.value, "gridCheckAll");
  rowData.value = arr;
  totalRow.value = corporateApprovalMng.resultData.data.totalElements;

  rowData.value.forEach((item: any) => {
    if (item.cormConfirmDate) {
      item.cormConfirmDate = format(new Date(item.cormConfirmDate), FORMAT_YYY_MM_DD);
    } else {
      item.cormConfirmDate = "-";
    }
    item.regDate = format(new Date(item.regDate), FORMAT_YYY_MM_DD);
  });
};

const handleActionClickExportExcel = async () => {
  if (totalRow.value > 0) {
    await corporateApprovalMng
      .getaAllDataExcel({
        method: "POST",
        body: cloneDataSearch.value,
      })
      .then(() => {
        const dataExcel = corporateApprovalMng.resultDataExcel.data;
        exportExcel(dataExcel);
      });
  } else {
    exportExcel([]);
  }
};

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
    el.cormConfirmDate = el.cormConfirmDate ? format(new Date(el.cormConfirmDate), FORMAT_YYY_MM_DD) : "-";
    el.regDate = format(new Date(el.regDate), FORMAT_YYY_MM_DD);

    colExcel.push(el.rowNumb);
    colExcel.push(el.cormStatusNm);
    colExcel.push(el.cormConfirmDate);
    colExcel.push(el.cormUserId);
    colExcel.push(el.cormNameKr);
    colExcel.push(el.cormClassificationNm);
    colExcel.push(el.cormAwardNm);
    colExcel.push(el.regDate);
    rowExcel.push(colExcel);
  });

  dataExport.value = [{ sheetName: "sheet1", data: rowExcel, header: rowExcelHeader }] as Array<ExcelData>;
};

async function approveCorporate() {
  let idCorporate = listSelect.value.filter((item) => item.cormStatusCd == CD_ID_CORM_WAIT_APPROVAL).map((item) => item.cormSeq);
  await corporateApprovalMng.approveCorporateApproval({
    method: "PUT",
    body: idCorporate,
  });
  listSelect.value = [];
  if (corporateApprovalMng.status.value && corporateApprovalMng.status.value == SUCCSESS_STATUS) {
    search();
    openModalAlert(t("corporateApprovalMng.message.changeStatusSuccess"));
  } else {
    openModalAlert(t("corporateApprovalMng.message.changeStatusFail"));
  }
}

function handleApproveCorporate() {
  messageConfirm.value = t("corporateApprovalMng.message.confirmChangeStatus");
  opemModalConfirm(() => {
    modalOpenConfirm.value = false;
    approveCorporate();
  });
}

function closeModalDetailCorp() {
  modalOpenDetailCorp.value = false;
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

function openModalDetailCorp(data: any) {
  cormSeq.value = data.cormSeq;
  modalOpenDetailCorp.value = true;
}

function reset() {
  searchModel.value = {
    cormStatusCd: "",
    cormUserId: "",
    cormNmKr: "",
    cormClassificationCd: "",
    cormAwardCd: "",
    cormConfirmDateStr: "",
    cormConfirmDateEnd: "",
    strDate: "",
    endDate: "",
    page: 1,
    size: 10,
    sort: "",
  };
}
const formatDate = (date) => {
  return new Date(date);
};
watch(
  listSelect,
  (newValue) => {
    if (newValue) {
      isDisabledApproval.value = newValue.length > 0 && newValue.every((item) => item.cormStatusCd == CD_ID_CORM_WAIT_APPROVAL);
    }
  },
  { deep: true },
);
watch(
  () => searchModel.value.cormConfirmDateStr,
  (newValue) => {
    if (!searchModel.value.cormConfirmDateEnd) {
      searchModel.value.cormConfirmDateStr = newValue;
    } else {
      const dStart = formatDate(newValue);
      const dEnd = formatDate(searchModel.value.cormConfirmDateEnd);
      if (dStart > dEnd) {
        nextTick(() => {
          openModalAlert(t("common.messageCheckDate"));
          searchModel.value.cormConfirmDateStr = searchModel.value.cormConfirmDateEnd;
        });
      }
    }
  },
);

watch(
  () => searchModel.value.cormConfirmDateEnd,
  (newValue) => {
    if (!searchModel.value.cormConfirmDateStr) {
      searchModel.value.cormConfirmDateEnd = newValue;
    } else {
      const dStart = formatDate(searchModel.value.cormConfirmDateStr);
      const dEnd = formatDate(newValue);
      if (dStart > dEnd) {
        nextTick(() => {
          openModalAlert(t("common.messageCheckDate"));
          searchModel.value.cormConfirmDateEnd = searchModel.value.cormConfirmDateStr;
        });
      }
    }
  },
);

watch(
  () => searchModel.value.strDate,
  (newValue) => {
    if (!searchModel.value.endDate) {
      searchModel.value.strDate = newValue;
    } else {
      const dStart = formatDate(newValue);
      const dEnd = formatDate(searchModel.value.endDate);
      if (dStart > dEnd) {
        nextTick(() => {
          openModalAlert(t("common.messageCheckDate"));
          searchModel.value.strDate = searchModel.value.endDate;
        });
      }
    }
  },
);

watch(
  () => searchModel.value.endDate,
  (newValue) => {
    if (!searchModel.value.strDate) {
      searchModel.value.endDate = newValue;
    } else {
      const dStart = formatDate(searchModel.value.strDate);
      const dEnd = formatDate(newValue);
      if (dStart > dEnd) {
        nextTick(() => {
          openModalAlert(t("common.messageCheckDate"));
          searchModel.value.endDate = searchModel.value.strDate;
        });
      }
    }
  },
);
function handleClickAll(value) {
  rowData.value.forEach((item) => {
    if (value) {
      if (!listSelect.value.some((el) => el.cormSeq == item.cormSeq)) {
        listSelect.value.push(item);
      }
    } else {
      listSelect.value = listSelect.value.filter((el) => el.cormSeq != item.cormSeq);
    }
  });
}

function handleClickChild(value, flag) {
  if (flag) {
    listSelect.value.push(value);
  } else {
    listSelect.value = listSelect.value.filter((item) => item.cormSeq !== value.cormSeq);
  }
}
function areAllItemsEqual(rowData, listSelect, idInput) {
  const all = document.getElementById(idInput);
  all.checked = false;
  let checked;
  let arr = [...rowData];

  checked = rowData.every((item1) => {
    return listSelect.some((item2) => item1.cormSeq === item2.cormSeq);
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
      const isSelected = listSelect.some((selectedItem) => selectedItem.cormSeq === item.cormSeq);
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
    const e = document.getElementById(item.cormSeq);
    if (e) {
      e.checked = true;
    }
  });
});
</script>

<style scoped>
</style>
