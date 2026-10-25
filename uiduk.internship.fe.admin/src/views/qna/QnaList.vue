<template>
  <PageContainer>
    <h1 v-once>
      {{ pageTitle }}
    </h1>
    <!-- search data -->
    <div class="box dp_block">
      <div class="box_section">
        <div class="search_box col_2">
          <ul>
            <li>
              <p class="ta_r">
                {{ t("qna.search.title") }}
              </p>
              <InputBase :id="'title'" v-model="dataSearch.title" :name="'title'" :mode="'show'" />
            </li>
            <li>
              <p class="ta_r">
                {{ t("qna.search.answerYn") }}
              </p>
              <RadiobuttonBase
                v-for="item in listRadio1"
                :id="item.cdId"
                :key="item.cdId"
                v-model="dataSearch.answerYn"
                :mode="'show'"
                :value="item.cdId"
                :name="item.cdId"
                :checked="item.cdId == radiovalue1"
                :label="item.cdNm"
              />
            </li>
            <li>
              <p class="ta_r">
                {{ t("qna.search.regName") }}
              </p>
              <InputBase :id="'name'" v-model="dataSearch.regName" :name="'name'" :mode="'show'" />
            </li>
            <li>
              <p class="ta_r">
                {{ t("qna.search.phone") }}
              </p>
              <InputBase :id="'name'" v-model="dataSearch.phone" :name="'name'" :mode="'show'" />
            </li>
            <li>
              <p class="ta_r">
                {{ t("qna.search.email") }}
              </p>
              <InputBase :id="'name'" v-model="dataSearch.email" :name="'name'" :mode="'show'" />
            </li>
            <li>
              <p class="ta_r">
                {{ t("qna.search.timeRegDate") }}
              </p>
              <BaseDatePicker v-model="dataSearch.startDate" />
              <span style="padding-left: 10px">~</span>
              <BaseDatePicker v-model="dataSearch.endDate" />
            </li>
          </ul>
          <div class="btn_group btn_end mg_t30 btn_form_search">
            <button type="button" class="button btn_md btn_lightgray btn_responsive" @click="reset">
              {{ t("qna.search.btnReset") }}
            </button>
            <button type="button" class="button btn_md btn_primary btn_responsive" @click="search">
              {{ t("qna.search.btnSearch") }}
            </button>
          </div>
        </div>
      </div>
    </div>
    <!-- grid table qna data -->
    <div class="box dp_block">
      <div id="keyQnaId" class="box_section">
        <div class="page_toparea">
          <p class="box_title_sm">
            {{ t("qna.table.titleTable") }}
          </p>
        </div>
        <GridComponent
          ref="grid"
          :column-defs="columnQnaDefs"
          :rows-data="rowQnaData"
          :pagination="pagination"
          :total-row="totalQnaRecord"
          :pagination-page-size="paginationPageSize"
          @get-data="getListQna"
        >
          <template #itemSelected>
            <div>
              <span>{{ listCheckBoxQna.length }} 개 선택</span>
            </div>
          </template>
          <template #button>
            <div class="btn_group btn_end">
              <button
                type="button"
                class="mg_l15 btn_md btn_lightgray"
                :disabled="listCheckBoxQna.length == 0"
                @click="modalOpenDeleteConfirm = true"
              >
                삭제
              </button>
              <ExportFileExcel
                :data="dataExport"
                :file-name="fileNameExport"
                :btn-name="nameOfbtn"
                @click="openModalConfirmDownload"
                ref="downloadExcelRef"
                :call-data="true"
              />
            </div>
          </template>
        </GridComponent>
        <TModalAlert
          :is-open="modalDeleteSuccess"
          :is-show-header="true"
          :is-show-footer="true"
          :modal-id="modalId"
          @close-modal="closeModalDeleteSuccess"
        >
          <!-- Modal content goes here -->
          <template #default>
            <div class="box_section">
              <p>{{ t("qna.message.deleteSuccess") }}</p>
            </div>
          </template>
          <template #footer>
            <!-- Footer content goes here -->
            <button type="button" class="button btn_md btn_primary popup_close" @click="cancelAction">확인</button>
          </template>
        </TModalAlert>
        <TModalConfirm
          :is-open="modalOpenDeleteConfirm"
          :is-show-header="true"
          :is-show-footer="true"
          :modal-id="modalId"
          @close-modal="closeModalDeleteConfirm"
        >
          <!-- Modal content goes here -->
          <template #default>
            <div class="box_section">
              <p>{{ t("qna.message.confirmDelete") }}</p>
            </div>
          </template>
          <template #footer>
            <!-- Footer content goes here -->
            <button type="button" class="button btn_md btn_primary" @click="confirmDelete">확인</button>
            <button type="button" class="button btn_md btn_white popup_close" @click="cancelAction">취소</button>
          </template>
        </TModalConfirm>
        <TModalAlert
          :is-open="modalErrorDate"
          :is-show-header="true"
          :is-show-footer="true"
          :modal-id="modalId"
          @close-modal="modalErrorDate = false"
        >
          <!-- Modal content goes here -->
          <template #default>
            <div class="box_section">
              <p>{{ t("qna.message.validateTime") }}</p>
            </div>
          </template>
          <template #footer>
            <!-- Footer content goes here -->
            <button type="button" class="button btn_md btn_primary" @click="cancelAction">확인</button>
          </template>
        </TModalAlert>
        <TModalConfirm
          :is-open="modalOpenConfirmDownload"
          :is-show-header="true"
          :modal-id="'confirmDowloadExcel'"
          @close-modal="closeModalConfirmDownload"
        >
          <template #default>
            <div class="box_section">
              <p>{{ t("common.message.confirmDowloadExcel") }}</p>
            </div>
          </template>
          <template #footer>
            <button type="button" class="button btn_md btn_primary" @click="downloadExcelCallData">{{ t("common.confirm") }}</button>
            <button type="button" class="button btn_md btn_white popup_close" @click="closeModalConfirmDownload">{{ t("common.cancel") }}</button>
          </template>
        </TModalConfirm>
      </div>
    </div>
  </PageContainer>
</template>

<script lang="ts" setup>
import { ref, watch, onBeforeMount } from "vue";
import { useI18n } from "vue-i18n";
import { FORMAT_YYY_MM_DD, MODE_EDIT, PAGINATION_PAGE_SIZE, SUCCSESS_STATUS } from "@/constants/screen.const";
import GridComponent from "@/components/common/grid/GridComponent.vue";
import GridCheckBox from "@/components/common/grid/GridCheckBox.vue";
import { qnaStore } from "@/stores/qna";
import { format } from "date-fns";
import ButtonGridComponent from "@/components/common/grid/ButtonGridComponent.vue";
import { ExcelData } from "@/model/common/excelData.type";
import { SCREEN } from "@/router/screen";
import { useRouter } from "vue-router";
import { QnaResDTO, QnaSearchDTO } from "@/stores/qna/qna.type";

const modalOpenDeleteConfirm = ref(false);
const modalDeleteSuccess = ref(false);
const modalId = ref("modalId");
const closeModalDeleteConfirm = () => {
  modalOpenDeleteConfirm.value = false;
};
const cancelAction = () => {
  closeModalDeleteConfirm();
  closeModalDeleteSuccess();
  modalErrorDate.value = false;
};
const closeModalDeleteSuccess = () => {
  modalDeleteSuccess.value = false;
};
const storeQna = qnaStore();
const totalQnaRecord = ref(0);
const rowQnaData = ref([] as Array<QnaResDTO>);
const rowQnaDataExcel = ref([] as Array<QnaResDTO>);
const callDataExcel = ref(true);
const paginationPageSize = PAGINATION_PAGE_SIZE;
const listCheckBoxQna = ref([] as Array<string>);
const listDelete = ref([] as Array<string>);
const listDataFromDB = ref([] as Array<string>);
const pagination = ref(true);
const { t } = useI18n();
const pageTitle = t("qna.title");
const fileNameExport = t("qna.table.titleTable");
const nameOfbtn = "엑셀다운로드";
const router = useRouter();
const radiovalue1 = ref("0");
const modalErrorDate = ref(false);
const listRadio1 = ref([
  {
    cdId: "ALL",
    cdNm: "전체",
  },
  {
    cdId: "N",
    cdNm: "미답변",
  },
  {
    cdId: "Y",
    cdNm: "답변완료",
  },
]);
const dataSearch = ref<QnaSearchDTO>({
  page: 1,
  size: PAGINATION_PAGE_SIZE,
  sort: "",
  title: "",
  answerYn: "",
  regName: "",
  phone: "",
  email: "",
  startDate: "",
  endDate: "",
});

const grid = ref(null);
const cloneDataSearch = ref(null);

onBeforeMount(() => {
  cloneDataSearch.value = { ...dataSearch.value };
});
const reset = () => {
  dataSearch.value = {
    page: 1,
    size: PAGINATION_PAGE_SIZE,
    sort: "",
    title: "",
    answerYn: "ALL",
    regName: "",
    phone: "",
    email: "",
    startDate: "",
    endDate: "",
  };
};
const search = () => {
  cloneDataSearch.value = { ...dataSearch.value };
  grid.value.initGrid();
  listCheckBoxQna.value = [];
};

const deleteListQna = async (list: any) => {
  closeModalDeleteConfirm();
  modalDeleteSuccess.value = true;
  await storeQna.deleteQna({
    method: "POST",
    body: list,
  });
  if (storeQna.status.value && storeQna.status.value === SUCCSESS_STATUS) {
    listCheckBoxQna.value = [];
  }
  cloneDataSearch.value = { ...dataSearch.value };
  grid.value.initGrid();
  callDataExcel.value = true;
};

const columnQnaDefs = ref([
  {
    headerName: "",
    field: "selected",
    width: 100,
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
    maxWidth: 60,
    cellStyle:{
      display: "flex",
      alignItems: "center",
    }
  },
  {
    headerName: t("qna.table.no"),
    field: "rowNumber",
    maxWidth: 90,
  },
  {
    headerName: t("qna.table.answerYn"),
    field: "answerYn",
    flex: 1,
  },
  {
    headerName: t("qna.table.title"),
    field: "title",
    flex: 3,
  },
  {
    headerName: t("qna.table.regId"),
    field: "regId",
    flex: 1,
  },
  {
    headerName: t("qna.table.phone"),
    field: "phone",
    flex: 1,
  },
  {
    headerName: t("qna.table.email"),
    field: "email",
    flex: 1.5,
  },
  {
    headerName: t("qna.table.regDate"),
    field: "regDate",
    maxWidth: 130,
  },
  {
    headerName: t("qna.table.edit"),
    cellRenderer: ButtonGridComponent,
    cellRendererParams: {
      className: "button btn_xs btn_primary",
      value: "조회",
      onCustomEvent: handleClickEdit,
    },
    maxWidth: 90,
  },
]);
const dataExport = ref([
  {
    sheetName: "Q&A 목록",
    data: [],
    header: [
      t("qna.table.no"),
      t("qna.table.answerYn"),
      t("qna.table.title"),
      t("qna.table.regId"),
      t("qna.table.phone"),
      t("qna.table.email"),
      t("qna.table.regDate"),
    ],
  },
] as Array<ExcelData>);
const confirmDelete = () => {
  deleteListQna(listCheckBoxQna.value);
};

function checkAll(value) {
  const newRow = rowQnaData.value.map((item) => {
    if (value) {
      listCheckBoxQna.value.push(item.qnaSeq);
      const index = listDelete.value.indexOf(item.qnaSeq);
      if (index !== -1) {
        listDelete.value.splice(index, 1);
      }
    } else {
      const index = listCheckBoxQna.value.indexOf(item.qnaSeq);
      if (index !== -1) {
        listCheckBoxQna.value.splice(index, 1);
      }
      if (listDataFromDB.value.includes(item.qnaSeq)) {
        listDelete.value.push(item.qnaSeq);
      }
    }
    return {
      ...item,
      selected: value,
    };
  });
  listCheckBoxQna.value = [...new Set(listCheckBoxQna.value)];
  listDelete.value = [...new Set(listDelete.value)];
  rowQnaData.value = newRow;
}

function checkChild(value, flag) {
  if (flag) {
    listCheckBoxQna.value.push(value.qnaSeq);
    const index = listDelete.value.indexOf(value.qnaSeq);
    if (index !== -1) {
      listDelete.value.splice(index, 1);
    }
  } else {
    const index = listCheckBoxQna.value.indexOf(value.qnaSeq);
    if (index != -1) {
      listCheckBoxQna.value.splice(index, 1);
    }
    listDelete.value.push(value.qnaSeq);
  }
  listCheckBoxQna.value = [...new Set(listCheckBoxQna.value)];
  listDelete.value = [...new Set(listDelete.value)];
}

async function getListQna(page = 1, pageSize = 10) {
  cloneDataSearch.value.answerYn = dataSearch.value.answerYn === "ALL" ? "" : dataSearch.value.answerYn;
  cloneDataSearch.value.page = page;
  cloneDataSearch.value.size = pageSize;
  await storeQna.qnaList({
    method: "POST",
    body: cloneDataSearch.value,
  });
  if (storeQna.status.value && storeQna.status.value === SUCCSESS_STATUS) {
    totalQnaRecord.value = storeQna.totalRecord ?? 0;
    const { arr } = areAllItemsEqual(storeQna.listQna.data.content, listCheckBoxQna.value, "checkAll");
    rowQnaData.value = arr;
    dataSearch.value.answerYn = dataSearch.value.answerYn === "" ? "ALL" : dataSearch.value.answerYn;
  }
}
const dataExcel = () => {
  dataExport.value[0].data = rowQnaDataExcel.value.map((row) => [
    row.rowNumber,
    row.answerYn,
    row.title,
    row.regId,
    row.phone,
    row.email,
    row.regDate,
  ]);
};

function areAllItemsEqual(rowData, listSelect, idInput) {
  const all = document.getElementById(idInput);
  all.checked = false;
  let checked;

  let arr = [...rowData];
  arr.map((item: any) => {
    item.regDate = format(item.regDate, FORMAT_YYY_MM_DD);
    item.answerYn = item.answerYn === "Y" ? "답변완료" : "미답변";
    return item;
  });

  checked = rowData.every((item1) => {
    return listCheckBoxQna.value.some((item2) => item1.qnaSeq === item2);
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
      const isSelected = listCheckBoxQna.value.some((selectedItem) => selectedItem === item.qnaSeq);
      return {
        ...item,
        selected: isSelected,
      };
    });
  }

  return { checked, arr };
}
function mapDataExcel(rowData: any) {
  let arr = [...rowData];
  arr.map((item: any) => {
    item.regDate = format(item.regDate, FORMAT_YYY_MM_DD);
    item.answerYn = item.answerYn === "Y" ? "답변완료" : "미답변";
    return item;
  });
  return arr;
}

async function handleClickEdit(event: { qnaSeq: string; answerYn: string }) {
  router.push({
    name: SCREEN.qnaCrud.name,
    params: {
      mode: MODE_EDIT,
    },
    state: {
      qnaSeq: event.qnaSeq,
    },
  });
}
watch(
  () => dataSearch.value.startDate,
  (newValue) => {
    if (!dataSearch.value.endDate) {
      dataSearch.value.startDate = newValue;
    } else {
      const dStart = format(newValue, FORMAT_YYY_MM_DD);
      const dEnd = format(dataSearch.value.endDate, FORMAT_YYY_MM_DD);
      if (dStart > dEnd) {
        dataSearch.value.startDate = dataSearch.value.endDate;
        modalErrorDate.value = true;
      }
    }
  },
);

watch(
  () => dataSearch.value.endDate,
  (newValue) => {
    if (!dataSearch.value.startDate) {
      dataSearch.value.endDate = newValue;
    } else {
      const dStart = format(dataSearch.value.startDate, FORMAT_YYY_MM_DD);
      const dEnd = format(newValue, FORMAT_YYY_MM_DD);
      if (dStart > dEnd) {
        dataSearch.value.endDate = dataSearch.value.startDate;
        modalErrorDate.value = true;
      }
    }
  },
);

const downloadExcelRef = ref(null);
const modalOpenConfirmDownload = ref(false);

const downloadExcelCallData = async () => {
  if (callDataExcel.value) {
    const updatedDataSearch = { ...cloneDataSearch.value, size: totalQnaRecord.value !== 0 ? totalQnaRecord.value : 10 };
    await storeQna.qnaList({
      method: "POST",
      body: updatedDataSearch,
    });
    const arrExcel = mapDataExcel(storeQna.listQna.data.content);
    rowQnaDataExcel.value = arrExcel;
    callDataExcel.value = false;
    dataExcel();
  }
  downloadExcelRef.value.downloadExcel();
  closeModalConfirmDownload();
};

const openModalConfirmDownload = () => {
  modalOpenConfirmDownload.value = true;
};

const closeModalConfirmDownload = () => {
  modalOpenConfirmDownload.value = false;
};
</script>
