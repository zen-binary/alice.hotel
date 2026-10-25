<template>
  <PageContainer>
    <h1 v-once>
      {{ t("indexInternPlan.title") }}
    </h1>
    <div class="box dp_block">
      <div class="box_section">
        <div class="search_box col_2">
          <ul>
            <li>
              <p class="ta_r">{{ t("indexInternPlan.search.nameIntern") }}</p>
              <InputBase :id="'name'" :name="'name'" :mode="'show'" v-model="internSearchModel.internPlanNm"></InputBase>
            </li>

            <li>
              <p class="ta_r">{{ t("indexInternPlan.search.time") }}</p>
              <div class="search_flex wd_p100" style="position: relative">
                <BaseDatePicker v-model="internSearchModel.startDate"></BaseDatePicker>
                <span>~</span>
                <BaseDatePicker v-model="internSearchModel.endDate"></BaseDatePicker>
              </div>
            </li>
          </ul>

          <div class="btn_group btn_end mg_t30 btn_form_search">
            <button type="button" class="button btn_md btn_lightgray btn_responsive" @click="reset()">{{ t("indexInternPlan.button.reset") }}</button>
            <button type="button" class="button btn_md btn_primary btn_responsive" @click="search()">{{ t("indexInternPlan.button.search") }}</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Grid table data -->
    <div class="box dp_block">
      <div class="box_section">
        <div class="page_toparea">
            <p class="box_title_sm">{{ t("indexInternPlan.grid.title") }}</p>
        </div>
        <GridComponent
          ref="grid"
          :column-defs="columnDefs"
          :rows-data="rowData"
          :pagination="pagination"
          :total-row="totalRow"
          :pagination-page-size="paginationPageSize"
          :autoSizeStrategy="true"
          @get-data="getDataAll"
        >
          <template #button>
            <div class="btn_group btn_end">
              <ExportFileExcel
                :data="dataExport"
                :file-name="fileNameExport"
                :btnName="nameOfbtn"
                :disabled="status"
                @click="handleActionClickExportExcel"
              ></ExportFileExcel>
            </div>
          </template>
        </GridComponent>
      </div>
    </div>
  </PageContainer>

  <!-- modal -->
  <TModalPreTestTable :is-open="isOpenModalPreTest" :internPlanSeq="internPlanSeqData" :modal-id="modalId" @close-modal="closeModalPreTestTable" />
  <AttendanceTrackingCenterModal
    v-if="modalOpenAttendanceTracking"
    :isOpen="modalOpenAttendanceTracking"
    :internPlanId="internPlanAttendanceId"
    @closeModal="closeModalAttendanceTracking"
  ></AttendanceTrackingCenterModal>

  <TModalCorporateSurvey ref="corporateSurvey" />
  <TModalStudentSurvey ref="studentSurvey" />
  <TModalWeekReport ref="weekReport" />
  <TModalSummaryReport ref="summaryReport" />
  <ModalAgreementCenter ref="modalAgreementCenter" />

  <GuidanceReportCenterModal
    v-if="modalOpenGuidanceReport"
    :isOpen="modalOpenGuidanceReport"
    :internPlan="internPlanGuidanceReport"
    @closeModal="closeModalGuidanceReport"
  ></GuidanceReportCenterModal>
  <EvaluationReportCenterModal
    v-if="modalOpenEvaluationReport"
    :isOpen="modalOpenEvaluationReport"
    :internPlan="internPlanEvaluationReport"
    @closeModal="closeModalEvaluationReport"
  ></EvaluationReportCenterModal>
  <ModalEvaluationTableCenter :internPlanSeq="internPlanSeqSelected" :is-open="isOpenModalEvaluation" :handle-cancel="closeModalEvaluation" />
  <CompletionMngModal
    v-if="modalOpenCompletion"
    :isOpen="modalOpenCompletion"
    :internPlanSeq="internPlanCompletion"
    :internPlanNm="internPlanNmCompletion"
    @closeModal="closeModalCompletion"
  ></CompletionMngModal>

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

<script setup lang="ts">
import { internPlanStore } from "@/stores/indexInternPlan/indexInternPlan";
import { ListInternPlanType, SearchInternPlanType } from "@/stores/indexInternPlan/indexInternPlan/indexInternPlan.type";
import { format } from "date-fns";
import { ref, onBeforeMount, watch } from "vue";
import { FORMAT_YYY_MM_DD, PAGINATION_PAGE_SIZE } from "@/constants/screen.const";
import { ExcelData } from "@/model/common/excelData.type";
import { useI18n } from "vue-i18n";
import MutiButtonGridComponent from "@/components/common/grid/MutiButtonGridComponent.vue";
import TModalPreTestTable from "./tModalPreTestTable/TModalPreTestTable.vue";
import AttendanceTrackingCenterModal from "./modalAttendanceTrackingCenter/AttendanceTrackingCenterModal.vue";
import TModalCorporateSurvey from "../supportCenter/corpSurvey/TModalCorporateSurvey.vue";
import TModalStudentSurvey from "../supportCenter/stdSurvey/TModalStudentSurvey.vue";
import TModalWeekReport from "../supportCenter/weekReport/TModalWeekReport.vue";
import TModalSummaryReport from "../supportCenter/summaryReport/TModalSummaryReport.vue";
import ModalAgreementCenter from "./modalAgreementCenter/ModalAgreementCenter.vue";
import GuidanceReportCenterModal from "./modalGuidanceReportCenter/GuidanceReportCenterModal.vue";
import EvaluationReportCenterModal from "./modalEvaluationReportCenter/EvaluationReportCenterModal.vue";
import ModalEvaluationTableCenter from "./modalEvaluationCenter/ModalEvaluationTableCenter.vue";
import CompletionMngModal from "./modalCompletionMng/CompletionMngModal.vue";

const { t } = useI18n();
const storeInternPlan = internPlanStore();

// TModalPreTestTable
const modalId = ref("demo");
const isOpenModalPreTest = ref(false);
const internPlanSeqData = ref("");

// AttendanceTrackingCenterModal
const modalOpenAttendanceTracking = ref(false);
const internPlanAttendanceId = ref("");

// TModalCorporateSurvey
const corporateSurvey = ref(null);

// TModalStudentSurvey
const studentSurvey = ref(null);

// TModalWeekReport
const weekReport = ref(null);

// TModalSummaryReport
const summaryReport = ref(null);

// ModalAgreementCenter
const modalAgreementCenter = ref(null);

// GuidanceReportCenterModal
const modalOpenGuidanceReport = ref(false);
const internPlanGuidanceReport = ref();

// EvaluationReportCenterModal
const modalOpenEvaluationReport = ref(false);
const internPlanEvaluationReport = ref();

// ModalEvaluationTableCenter
const internPlanSeqSelected = ref("");
const isOpenModalEvaluation = ref(false);

// CompletionMngModal
const modalOpenCompletion = ref(false);
const internPlanCompletion = ref("");
const internPlanNmCompletion = ref("");

const isModalValidateDate = ref(false);

//search
const internSearchModel = ref({
  internPlanNm: "",
  startDate: "",
  endDate: "",
  page: 1,
  size: PAGINATION_PAGE_SIZE,
  sort: "",
} as SearchInternPlanType);

const grid = ref(null);
const cloneDataSearch = ref(null);
const totalRow = ref(0);
const pagination = ref(true);
const paginationPageSize = ref(PAGINATION_PAGE_SIZE);
const rowData = ref<Array<ListInternPlanType>>([]);
const columnDefs = ref([
  { headerName: t("indexInternPlan.grid.no"), field: "rowNumber", maxWidth: 90 },
  { headerName: t("indexInternPlan.grid.cormNameKr"), field: "cormNameKr", flex: 3 },
  { headerName: t("indexInternPlan.grid.internPlanNm"), field: "internPlanNm", flex: 1 },
  { headerName: t("indexInternPlan.grid.quality"), field: "quality", minWidth: 120 },
  { headerName: t("indexInternPlan.grid.timePractice"), field: "timePractice", flex: 1 },
  {
    headerName: t("indexInternPlan.grid.manage"),
    cellRenderer: MutiButtonGridComponent,
    cellRendererParams: {
      buttons: [
        {
          label: t("indexInternPlan.grid.service"),
          className: "button btn_xs btn_primary",
          isDisabled: false,
          isShow: true,
          onClick: handleAgreementClick,
        },
        {
          label: t("indexInternPlan.grid.officeBuilding"),
          className: "button btn_xs btn_primary",
          isDisabled: false,
          isShow: true,
          onClick: handleInspectionClick,
        },
        {
          label: t("indexInternPlan.grid.world"),
          className: "button btn_xs btn_primary",
          isDisabled: false,
          isShow: true,
          onClick: handleAttendanceClick,
        },
        {
          label: t("indexInternPlan.grid.evaluationSheet"),
          className: "button btn_xs btn_primary",
          isDisabled: false,
          isShow: true,
          onClick: handleEvaluationClick,
        },
        {
          label: t("indexInternPlan.grid.survey"),
          className: "button btn_xs btn_primary",
          isDisabled: false,
          isShow: true,
          onClick: handleSurveyClick,
        },
        {
          label: t("indexInternPlan.grid.visitorMapReport"),
          className: "button btn_xs btn_primary",
          isDisabled: false,
          isShow: true,
          onClick: handleVisitingClick,
        },
        {
          label: t("indexInternPlan.grid.instructorEvaluationForm"),
          className: "button btn_xs btn_primary",
          isDisabled: false,
          isShow: true,
          onClick: handleInstructorClick,
        },
        {
          label: t("indexInternPlan.grid.student"),
          className: "button btn_xs btn_primary",
          isDisabled: false,
          isShow: true,
          onClick: handleSurveyStudentClick,
        },
        {
          label: t("indexInternPlan.grid.weeklyReport"),
          className: "button btn_xs btn_primary",
          isDisabled: false,
          isShow: true,
          onClick: handleWeeklyClick,
        },
        {
          label: t("indexInternPlan.grid.comprehensiveReport"),
          className: "button btn_xs btn_primary",
          isDisabled: false,
          isShow: true,
          onClick: handleComprehensiveClick,
        },
        {
          label: t("indexInternPlan.grid.earnedIncomeManagement"),
          className: "button btn_xs btn_primary",
          isDisabled: false,
          isShow: true,
          onClick: handleCompletionClick,
        },
      ],
    },
    flex: 6,
  },
]);

const status = ref(true);
const fileNameExport = ref("현장실습운영관리");
const nameOfbtn = ref("엑셀다운로드");
const dataExport = ref<Array<ExcelData>>([]);

onBeforeMount(() => {
  cloneDataSearch.value = { ...internSearchModel.value };
});

//first data
async function getDataAll(page = 1, pageSize = 10) {
  cloneDataSearch.value.page = page;
  cloneDataSearch.value.size = pageSize;

  await storeInternPlan.searchInternPlan({
    method: "POST",
    body: cloneDataSearch.value,
  });

  rowData.value = storeInternPlan.resultData.data.content.map((item) => {
    item.internStrDate = item.internStrDate ? format(item.internStrDate, FORMAT_YYY_MM_DD) : "";
    item.internEndDate = item.internEndDate ? format(item.internEndDate, FORMAT_YYY_MM_DD) : "";
    item.timePractice = item.internEndDate ? item.internStrDate + "~" + item.internEndDate : item.internStrDate;

    return item;
  });

  status.value = !(rowData.value.length > 0);
  totalRow.value = storeInternPlan.resultData.data.totalElements;
}

//search data
function search() {
  cloneDataSearch.value = { ...internSearchModel.value };
  grid.value.initGrid();
}

function reset() {
  internSearchModel.value.internPlanNm = "";
  internSearchModel.value.startDate = "";
  internSearchModel.value.endDate = "";
  internSearchModel.value.page = 1;
  internSearchModel.value.size = paginationPageSize.value;
  internSearchModel.value.sort = "";
}

//action handle click
// TModalPreTestTable
function handleInspectionClick(data: any) {
  isOpenModalPreTest.value = true;
  internPlanSeqData.value = data.internPlanSeq;
}
function closeModalPreTestTable() {
  isOpenModalPreTest.value = false;
}

// AttendanceTrackingCenterModal
function handleAttendanceClick(data: any) {
  internPlanAttendanceId.value = data.internPlanSeq;
  modalOpenAttendanceTracking.value = true;
}
function closeModalAttendanceTracking() {
  modalOpenAttendanceTracking.value = false;
}

// ModalAgreementCenter
function handleAgreementClick(data: any) {
  modalAgreementCenter.value?.openModal(data.internPlanSeq);
}

// ModalEvaluationTableCenter
function handleEvaluationClick(data: any) {
  isOpenModalEvaluation.value = true;
  internPlanSeqSelected.value = data.internPlanSeq;
}
function closeModalEvaluation() {
  isOpenModalEvaluation.value = false;
}

// TModalCorporateSurvey
function handleSurveyClick(item) {
  corporateSurvey.value?.openModal(item.internPlanSeq);
}

// GuidanceReportCenterModal
function handleVisitingClick(data: any) {
  internPlanGuidanceReport.value = data;
  modalOpenGuidanceReport.value = true;
}
function closeModalGuidanceReport() {
  modalOpenGuidanceReport.value = false;
}

// EvaluationReportCenterModal
function handleInstructorClick(data: any) {
  internPlanEvaluationReport.value = data;
  modalOpenEvaluationReport.value = true;
}
function closeModalEvaluationReport() {
  modalOpenEvaluationReport.value = false;
}

// TModalStudentSurvey
function handleSurveyStudentClick(item) {
  studentSurvey.value?.openModal(item.internPlanSeq);
}

// TModalWeekReport
function handleWeeklyClick(item) {
  weekReport.value?.openModal(item.internPlanSeq);
}

// TModalSummaryReport
function handleComprehensiveClick(item) {
  summaryReport.value?.openModal(item.internPlanSeq);
}

// CompletionMngModal
function handleCompletionClick(item: any) {
  internPlanCompletion.value = item.internPlanSeq;
  internPlanNmCompletion.value = item.internPlanNm;
  modalOpenCompletion.value = true;
}
function closeModalCompletion() {
  modalOpenCompletion.value = false;
}

function openModalValidateDate() {
  isModalValidateDate.value = true;
}

function closeModalValidateDate() {
  isModalValidateDate.value = false;
}


async function handleActionClickExportExcel() {
  if (totalRow.value > 0) {
    const searchToExport = { ...cloneDataSearch.value };
    searchToExport.page = 1;
    searchToExport.size = totalRow.value;

    await storeInternPlan
      .searchInternPlan({
        method: "POST",
        body: searchToExport,
      })
      .then(() => {
        const dataExcel = storeInternPlan.resultData.data.content;
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
    if (index != columnDefs.value.length - 1) {
      rowExcelHeader.push(el.headerName);
    }
  });

  dataExcel.forEach((el: any) => {
    let colExcel = [] as Array<any>;
    el.timePractice = el.internEndDate
      ? format(el.internStrDate, FORMAT_YYY_MM_DD) + "~" + format(el.internEndDate, FORMAT_YYY_MM_DD)
      : format(el.internEndDate, FORMAT_YYY_MM_DD);
    colExcel.push(el.rowNumber);
    colExcel.push(el.cormNameKr);
    colExcel.push(el.internPlanNm);
    colExcel.push(el.quality);
    colExcel.push(el.timePractice);
    rowExcel.push(colExcel);
  });

  dataExport.value = [{ sheetName: "sheet1", data: rowExcel, header: rowExcelHeader }] as Array<ExcelData>;
};

watch(
  () => internSearchModel.value.startDate,
  (newValue) => {
    if (!internSearchModel.value.endDate) {
      internSearchModel.value.startDate = newValue;
    } else {
      const dStart = format(newValue, FORMAT_YYY_MM_DD);
      const dEnd = format(internSearchModel.value.endDate, FORMAT_YYY_MM_DD);
      if (dStart > dEnd) {
        nextTick(() => {
          internSearchModel.value.startDate = internSearchModel.value.endDate;
          openModalValidateDate();
        });
      }
    }
  },
);

watch(
  () => internSearchModel.value.endDate,
  (newValue) => {
    if (!internSearchModel.value.startDate) {
      internSearchModel.value.endDate = newValue;
    } else {
      const dStart = format(internSearchModel.value.startDate, FORMAT_YYY_MM_DD);
      const dEnd = format(newValue, FORMAT_YYY_MM_DD);
      if (dStart > dEnd) {
        nextTick(() => {
          internSearchModel.value.endDate = internSearchModel.value.startDate;
          openModalValidateDate();
        });
      }
    }
  },
);
</script>
