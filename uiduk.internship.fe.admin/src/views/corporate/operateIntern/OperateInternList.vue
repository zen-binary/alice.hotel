<template>
  <PageContainer>
    <h1 v-once>
      {{ t("operateIntern.pageTitle") }}
    </h1>

    <!-- fomr search -->
    <div class="box dp_block">
      <div class="box_section">
        <div class="search_box col_2">
          <ul>
            <li>
              <!-- 현장실습명 -->
              <p class="ta_r">{{ t("operateIntern.search.planName") }}</p>
              <InputBase :id="'planName'" v-model="operateSearchModel.planName" :name="'planName'" :mode="'show'" />
            </li>

            <li>
              <!-- 운영기간 -->
              <p class="ta_r">{{ t("operateIntern.search.internDate") }}</p>
              <div class="search_flex" style="position: relative; width: 100%">
                <BaseDatePicker v-model="operateSearchModel.startDate" />
                <span>~</span>
                <BaseDatePicker v-model="operateSearchModel.endDate" />
              </div>
            </li>
          </ul>

          <div class="btn_group btn_end mg_t30 btn_form_search">
            <button type="button" class="button btn_md btn_lightgray btn_responsive" @click="reset()">
              {{ t("operateIntern.search.btnReset") }}
            </button>
            <button type="button" class="button btn_md btn_primary btn_responsive" @click="search()">
              {{ t("operateIntern.search.btnSearch") }}
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- grid table data -->
    <div class="box dp_block">
      <div class="box_section">
        <div class="page_toparea">
          <p class="box_title_sm"> {{ t("operateIntern.table.title") }}</p>
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
          <template #button>
            <div class="btn_group btn_end">
              <ExportFileExcel
                ref="exportExcelRef"
                :data="dataExport"
                :file-name="fileNameExport"
                :btn-name="btnExportName"
                @click="handleActionClickExportExcel"
              />
            </div>
          </template>
        </GridComponent>
      </div>
    </div>
  </PageContainer>

  <!-- modal -->
  <ModalEvaluationTable :intern-plan-seq="internPlanSeqSelected" :is-open="isOpenModalEvaluation" :handle-cancel="closeModalEvaluation" />
  <ModalSurvey :intern-plan-seq="internPlanSeqSelected" :is-open="isOpenModalSurvey" :handle-cancel="closeModalSurvey" />
  <AttendanceTrackingModal
    v-if="modalOpenAttendanceTracking"
    :is-open="modalOpenAttendanceTracking"
    :intern-plan-id="internPlanAttendanceId"
    @close-modal="closeModalAttendanceTracking"
  />
  <ModalSemesterAgreement ref="openModalSemesterAgreement" />
  <CorporatePreTestList ref="openCorporatePreTestList" />

  <!-- alert -->
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
      <button type="button" class="btn_md btn_primary popup_close" @click="closeModalValidateDate">{{ t("common.confirm") }}</button>
    </template>
  </TModalAlert>
</template>

<script lang="ts" setup>
import { ref, onBeforeMount, watch } from "vue";
import InputBase from "@/components/common/input/InputBase.vue";
import { FORMAT_YYY_MM_DD, PAGINATION_PAGE_SIZE } from "@/constants/screen.const";
import GridComponent from "@/components/common/grid/GridComponent.vue";
import { useI18n } from "vue-i18n";
import { ExcelData } from "@/model/common/excelData.type";
import MutiButtonGridComponent from "@/components/common/grid/MutiButtonGridComponent.vue";
import { OperateInternResponseModel, OperateInternSearchModel } from "@/stores/corporate/operateIntern/operateIntern.type";
import { operateInternStore } from "@/stores/corporate/operateIntern/useOperateIntern";
import { format, formatDate } from "date-fns";
import ModalSemesterAgreement from "./ModalSemesterAgreement.vue";
import ModalEvaluationTable from "../modalEvaluation/ModalEvaluationTable.vue";
import ModalSurvey from "../modalSurvey/ModalSurvey.vue";
import CorporatePreTestList from "@/views/corporatePreTest/CorporatePreTestList.vue";

import AttendanceTrackingModal from "../modalAttendanceTracking/AttendanceTrackingModal.vue";
const { t } = useI18n();
const storeOperateIntern = operateInternStore();
const openCorporatePreTestList = ref(null);
const isModalValidateDate = ref(false);

//search
const grid = ref(null);
const cloneDataSearch = ref(null);
const operateSearchModel = ref({
  planName: "",
  startDate: "",
  endDate: "",
  page: 1,
  size: 10,
  sort: "",
} as OperateInternSearchModel);

//table data
const rowData = ref<Array<OperateInternResponseModel>>([]);
const paginationPageSize = PAGINATION_PAGE_SIZE;
const pagination = ref(true);
const totalRow = ref(0);
const internPlanSeqSelected = ref("");

const columnDefs = ref([
  {
    headerName: t("operateIntern.table.rowNum"),
    field: "rowNumber",
    maxWidth: 90,
  },
  {
    headerName: t("operateIntern.table.corporateNm"),
    field: "corporateNm",
    flex: 2,
  },
  {
    headerName: t("operateIntern.table.planNm"),
    field: "internPlanNm",
    flex: 2.5,
  },
  {
    headerName: t("operateIntern.table.numberIntern"),
    field: "numberIntern",
    maxWidth: 120,
  },
  {
    headerName: t("operateIntern.table.internDate"),
    field: "internDate",
    flex: 1.5,
  },
  {
    headerName: t("operateIntern.table.management"),
    cellRenderer: MutiButtonGridComponent,
    cellRendererParams: (item: any) => ({
      buttons: [
        {
          label: t("operateIntern.table.button.preCheck"),
          className: "button btn_xs btn_primary",
          isDisabled: customAddDay(item?.data.internStrDate, 7),
          isShow: true,
          onClick: handleClickBtnPreCheck,
        },
        {
          label: t("operateIntern.table.button.checkIn"),
          isDisabled: customAddDay(item?.data.internEndDate, 14),
          isShow: true,
          className: "button btn_xs btn_primary",
          onClick: handleClickBtnCheckIn,
        },
        {
          label: t("operateIntern.table.button.evaluation"),
          isDisabled: customAddDay(item?.data.internEndDate, 14),
          isShow: true,
          className: "button btn_xs btn_primary",
          onClick: handleClickBtnEvaluation,
        },
        {
          label: t("operateIntern.table.button.survey"),
          isDisabled: customAddDay(item?.data.internEndDate, 14),
          isShow: true,
          className: "button btn_xs btn_primary",
          onClick: handleClickBtnSurvey,
        },
        {
          label: t("operateIntern.table.button.sign"),
          isDisabled: customAddDay(item?.data.internStrDate, 7),
          isShow: true,
          className: "button btn_xs btn_primary",
          onClick: handleClickBtnSign,
        },
      ],
    }),
    flex: 2.5,
    cellStyle: {
      textAlign: "center",
    },
  },
]);
const openModalSemesterAgreement = ref(null);

//download excel
const exportExcelRef = ref(null);
const fileNameExport = ref(t("operateIntern.table.excel.fileName"));
const btnExportName = t("operateIntern.table.excel.btnDownload");
const dataExport = ref<Array<ExcelData>>([]);

onBeforeMount(() => {
  cloneDataSearch.value = { ...operateSearchModel.value };
});

//method
//first data
function customAddDay(strDate: string, numberDay: number): boolean {
  if (!strDate) return false;

  const date = new Date(strDate);
  if (isNaN(date.getTime())) return false;
  date.setDate(date.getDate() + numberDay);

  return date <= new Date();
}

const getDataAll = async (page = 1, pageSize = 10) => {
  cloneDataSearch.value.page = page;
  cloneDataSearch.value.size = pageSize;
  await storeOperateIntern.searchOperateIntern({
    method: "POST",
    body: cloneDataSearch.value,
  });

  rowData.value = storeOperateIntern.resultData.data.content.map((item) => {
    item.internStrDate = item.internStrDate ? format(item.internStrDate, FORMAT_YYY_MM_DD) : "";
    item.internEndDate = item.internEndDate ? format(item.internEndDate, FORMAT_YYY_MM_DD) : "";
    item.internDate = item.internEndDate ? item.internStrDate + "~" + item.internEndDate : item.internStrDate;

    return item;
  });
  totalRow.value = storeOperateIntern.resultData.data.totalElements;
};

const handleActionClickExportExcel = async () => {
  if (totalRow.value > 0) {
    const searchToExport = { ...cloneDataSearch.value };
    searchToExport.page = 1;
    searchToExport.size = totalRow.value;

    await storeOperateIntern
      .searchOperateIntern({
        method: "POST",
        body: searchToExport,
      })
      .then(() => {
        const dataExcel = storeOperateIntern.resultData.data.content;
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
    if (index != columnDefs.value.length - 1) {
      rowExcelHeader.push(el.headerName);
    }
  });

  dataExcel.forEach((el: any) => {
    let colExcel = [] as Array<any>;
    el.internDate = el.internEndDate
      ? format(el.internStrDate, FORMAT_YYY_MM_DD) + "~" + format(el.internEndDate, FORMAT_YYY_MM_DD)
      : format(el.internEndDate, FORMAT_YYY_MM_DD);
    colExcel.push(el.rowNumber);
    colExcel.push(el.corporateNm);
    colExcel.push(el.internPlanNm);
    colExcel.push(el.numberIntern);
    colExcel.push(el.internDate);
    rowExcel.push(colExcel);
  });

  dataExport.value = [{ sheetName: "sheet1", data: rowExcel, header: rowExcelHeader }] as Array<ExcelData>;
};

//search data
const search = () => {
  cloneDataSearch.value = { ...operateSearchModel.value };
  grid.value.initGrid();
};

const reset = () => {
  operateSearchModel.value = {
    planName: "",
    startDate: "",
    endDate: "",
    page: 1,
    size: operateSearchModel.value.size,
    sort: "",
  };
};

//action handle click
function handleClickBtnPreCheck(item) {
  openCorporatePreTestList.value?.openModal(item.internPlanId);
}

const modalOpenAttendanceTracking = ref(false);
const internPlanAttendanceId = ref("");
function handleClickBtnCheckIn(data: any) {
  internPlanAttendanceId.value = data.internPlanId;
  modalOpenAttendanceTracking.value = true;
}
function closeModalAttendanceTracking() {
  modalOpenAttendanceTracking.value = false;
}

const isOpenModalEvaluation = ref(false);

function handleClickBtnEvaluation(data: any) {
  isOpenModalEvaluation.value = true;
  internPlanSeqSelected.value = data.internPlanId;
}

function closeModalEvaluation() {
  isOpenModalEvaluation.value = false;
}

const isOpenModalSurvey = ref(false);

function handleClickBtnSurvey(data: any) {
  isOpenModalSurvey.value = true;
  internPlanSeqSelected.value = data.internPlanId;
}

function closeModalSurvey() {
  isOpenModalSurvey.value = false;
}

function handleClickBtnSign(item) {
  openModalSemesterAgreement.value?.openModal(item.internPlanId);
}

function openModalValidateDate() {
  isModalValidateDate.value = true;
}

function closeModalValidateDate() {
  isModalValidateDate.value = false;
}

watch(
  () => operateSearchModel.value.startDate,
  (newValue) => {
    if (!operateSearchModel.value.endDate) {
      operateSearchModel.value.startDate = newValue;
    } else {
      const dStart = formatDate(newValue, FORMAT_YYY_MM_DD);
      const dEnd = formatDate(operateSearchModel.value.endDate, FORMAT_YYY_MM_DD);
      if (dStart > dEnd) {
        nextTick(() => {
          operateSearchModel.value.startDate = operateSearchModel.value.endDate;
          openModalValidateDate();
        });
      }
    }
  },
);

watch(
  () => operateSearchModel.value.endDate,
  (newValue) => {
    if (!operateSearchModel.value.startDate) {
      operateSearchModel.value.endDate = newValue;
    } else {
      const dStart = formatDate(operateSearchModel.value.startDate, FORMAT_YYY_MM_DD);
      const dEnd = formatDate(newValue, FORMAT_YYY_MM_DD);
      if (dStart > dEnd) {
        nextTick(() => {
          operateSearchModel.value.endDate = operateSearchModel.value.startDate;
          openModalValidateDate();
        });
      }
    }
  },
);
</script>
