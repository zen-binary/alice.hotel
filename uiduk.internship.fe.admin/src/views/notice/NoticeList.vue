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
                {{ t("notice.search.title") }}
              </p>
              <InputBase id="input_validate" v-model="dataSearch.title" :name="'title'" :mode="'show'" />
            </li>
            <li>
              <p class="ta_r">
                {{ t("notice.search.timeShow") }}
              </p>
              <BaseDatePicker v-model="dataSearch.startDateShow" />
              <span style="padding-left: 10px">~</span>
              <BaseDatePicker v-model="dataSearch.endDateShow" />
            </li>
            <li>
              <p class="ta_r">
                {{ t("notice.search.useYn") }}
              </p>
              <RadiobuttonBase
                v-for="item in listRadio1"
                :id="item.cdId"
                :key="item.cdId"
                v-model="dataSearch.useYn"
                :mode="'show'"
                :value="item.cdId"
                :name="item.cdId"
                :checked="item.cdId == radiovalue1"
                :label="item.cdNm"
              />
            </li>
            <li>
              <p class="ta_r">
                {{ t("notice.search.timeRegDate") }}
              </p>
              <BaseDatePicker v-model="dataSearch.startDate" />
              <span style="padding-left: 10px">~</span>
              <BaseDatePicker v-model="dataSearch.endDate" />
            </li>
            <li>
              <p class="ta_r">
                {{ t("notice.search.regName") }}
              </p>
              <InputBase :id="'regName'" v-model="dataSearch.regName" :name="'regName'" :mode="'show'" />
            </li>
          </ul>
          <div class="btn_group btn_end mg_t30 btn_form_search">
            <button type="button" class="button btn_md btn_lightgray btn_responsive" @click="reset">
              {{ t("notice.search.btnReset") }}
            </button>
            <button type="button" class="button btn_md btn_primary btn_responsive" @click="search">
              {{ t("notice.search.btnSearch") }}
            </button>
          </div>
        </div>
      </div>
    </div>
    <!-- grid table notice data -->
    <div class="box dp_block">
      <div id="keyNoticeId" class="box_section">
        <div class="page_toparea">
          <p class="box_title_sm">
            {{ t("notice.table.titleTable") }}
          </p>
        </div>
        <GridComponent
          ref="grid"
          :column-defs="columnNoticeDefs"
          :rows-data="rowNoticeData"
          :pagination="pagination"
          :total-row="totalNoticeRecord"
          :pagination-page-size="paginationPageSize"
          @get-data="getListNotice"
        >
          <template #itemSelected>
            <div>
              <span>{{ listCheckBoxNotice.length }} {{ t("notice.subPage") }}</span>
            </div>
          </template>
          <template #button>
            <div class="btn_group btn_end">
              <button
                type="button"
                class="mg_l15 btn_md btn_lightgray"
                :disabled="listCheckBoxNotice.length == 0"
                @click="modalOpenDeleteConfirm = true"
              >
                {{ t("notice.table.btnDelete") }}
              </button>
              <button type="button" class="btn_md btn_primary" @click="btnCreate">
                {{ t("notice.table.btnCreate") }}
              </button>
              <ExportFileExcel
                :data="dataExport"
                :file-name="fileNameExport"
                :btn-name="t('notice.table.btnExcel')"
                :call-data="true"
                class="btn_excel"
                @click="openModalConfirmDownload"
                ref="downloadExcelRef"
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
              <p>{{ t("notice.message.deleteSuccess") }}</p>
            </div>
          </template>
          <template #footer>
            <!-- Footer content goes here -->
            <button type="button" class="button btn_md btn_primary popup_close" @click="cancelAction">
              {{ t("notice.table.btnConfirm") }}
            </button>
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
              <p>{{ t("notice.message.confirmDelete") }}</p>
            </div>
          </template>
          <template #footer>
            <!-- Footer content goes here -->
            <button type="button" class="button btn_md btn_primary" @click="confirmDelete">
              {{ t("notice.table.btnConfirm") }}
            </button>
            <button type="button" class="button btn_md btn_white popup_close" @click="cancelAction">
              {{ t("notice.table.btnCancel") }}
            </button>
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
              <p>{{ t("notice.message.validateTime") }}</p>
            </div>
          </template>
          <template #footer>
            <!-- Footer content goes here -->
            <button type="button" class="button btn_md btn_primary" @click="cancelAction">
              {{ t("notice.table.btnConfirm") }}
            </button>
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
import { FORMAT_YYY_MM_DD, MODE_CREATE, MODE_EDIT, PAGINATION_PAGE_SIZE, SUCCSESS_STATUS } from "@/constants/screen.const";
import GridComponent from "@/components/common/grid/GridComponent.vue";
import GridCheckBox from "@/components/common/grid/GridCheckBox.vue";
import type { NoticeResDTO, NoticeSearchDTO } from "@/stores/notice/notice.type";
import { noticeStore } from "@/stores/notice";
import { format } from "date-fns";
import ButtonGridComponent from "@/components/common/grid/ButtonGridComponent.vue";
import { ExcelData } from "@/model/common/excelData.type";
import { SCREEN } from "@/router/screen";
import { useRouter } from "vue-router";

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
const rowNoticeDataExcel = ref([] as Array<NoticeResDTO>);
const callDataExcel = ref(true);
const storeNotice = noticeStore();
const totalNoticeRecord = ref(0);
const rowNoticeData = ref([] as Array<NoticeResDTO>);
const paginationPageSize = PAGINATION_PAGE_SIZE;
const listCheckBoxNotice = ref([] as Array<string>);
const listDelete = ref([] as Array<string>);
const listDataFromDB = ref([] as Array<string>);
const pagination = ref(true);
const { t } = useI18n();
const pageTitle = t("notice.title");
const fileNameExport = t("notice.table.titleTable");
const router = useRouter();
const radiovalue1 = ref("0");
const modalErrorDate = ref(false);
const grid = ref(null);
const cloneDataSearch = ref(null);

onBeforeMount(() => {
  cloneDataSearch.value = { ...dataSearch.value };
});
const listRadio1 = ref([
  {
    cdId: "0",
    cdNm: "전체",
  },
  {
    cdId: "Y",
    cdNm: "사용",
  },
  {
    cdId: "N",
    cdNm: "미사용",
  },
]);
const dataSearch = ref<NoticeSearchDTO>({
  page: 1,
  size: PAGINATION_PAGE_SIZE,
  sort: "",
  title: "",
  startDateShow: "",
  endDateShow: "",
  useYn: "",
  startDate: "",
  endDate: "",
  regName: "",
});
const reset = () => {
  dataSearch.value = {
    page: 1,
    size: PAGINATION_PAGE_SIZE,
    sort: "",
    title: "",
    startDateShow: "",
    endDateShow: "",
    useYn: "0",
    startDate: "",
    endDate: "",
    regName: "",
  };
};
const search = () => {
  cloneDataSearch.value = { ...dataSearch.value };
  grid.value.initGrid();
  listCheckBoxNotice.value = [];
};

const deleteListNotice = async (list: any) => {
  closeModalDeleteConfirm();
  modalDeleteSuccess.value = true;
  await storeNotice.deleteNotice({
    method: "POST",
    body: list,
  });
  if (storeNotice.status.value && storeNotice.status.value === SUCCSESS_STATUS) {
    listCheckBoxNotice.value = [];
  }
  cloneDataSearch.value = { ...dataSearch.value };
  grid.value.initGrid();
  callDataExcel.value = true;
};

const columnNoticeDefs = ref([
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
    headerName: t("notice.table.no"),
    field: "noId",
    maxWidth: 90,
  },
  {
    headerName: t("notice.table.useYn"),
    field: "useYn",
    flex: 1,
  },
  {
    headerName: t("notice.table.title"),
    field: "title",
    flex: 2,
  },
  {
    headerName: t("notice.table.timeShow"),
    field: "startDateShow",
  },
  {
    headerName: t("notice.table.regName"),
    field: "regId",
    flex: 1,
  },
  {
    headerName: t("notice.table.regDate"),
    field: "regDate",
    maxWidth: 130
  },
  {
    headerName: t("notice.table.edit"),
    cellRenderer: ButtonGridComponent,
    cellRendererParams: {
      className: "button btn_xs btn_primary",
      value: t("common.edit"),
      onCustomEvent: handleClickEdit,
    },
    maxWidth: 90
  },
]);
const dataExport = ref([
  {
    sheetName: "공지사항 목록",
    data: [],
    header: [
      t("notice.table.no"),
      t("notice.table.useYn"),
      t("notice.table.title"),
      t("notice.table.timeShow"),
      t("notice.table.regName"),
      t("notice.table.regDate"),
    ],
  },
] as Array<ExcelData>);
const confirmDelete = () => {
  deleteListNotice(listCheckBoxNotice.value);
};
const btnCreate = () => {
  router.push({
    name: SCREEN.noticeCrud.name,
    params: { mode: MODE_CREATE },
  });
};

function checkAll(value) {
  const newRow = rowNoticeData.value.map((item) => {
    if (value) {
      listCheckBoxNotice.value.push(item.noticeSeq);
      const index = listDelete.value.indexOf(item.noticeSeq);
      if (index !== -1) {
        listDelete.value.splice(index, 1);
      }
    } else {
      const index = listCheckBoxNotice.value.indexOf(item.noticeSeq);
      if (index !== -1) {
        listCheckBoxNotice.value.splice(index, 1);
      }
      if (listDataFromDB.value.includes(item.noticeSeq)) {
        listDelete.value.push(item.noticeSeq);
      }
    }
    return {
      ...item,
      selected: value,
    };
  });
  listCheckBoxNotice.value = [...new Set(listCheckBoxNotice.value)];
  listDelete.value = [...new Set(listDelete.value)];
  rowNoticeData.value = newRow;
}

function checkChild(value, flag) {
  if (flag) {
    listCheckBoxNotice.value.push(value.noticeSeq);
    const index = listDelete.value.indexOf(value.noticeSeq);
    if (index !== -1) {
      listDelete.value.splice(index, 1);
    }
  } else {
    const index = listCheckBoxNotice.value.indexOf(value.noticeSeq);
    if (index != -1) {
      listCheckBoxNotice.value.splice(index, 1);
    }
    listDelete.value.push(value.noticeSeq);
  }
  listCheckBoxNotice.value = [...new Set(listCheckBoxNotice.value)];
  listDelete.value = [...new Set(listDelete.value)];
}

async function getListNotice(page = 1, pageSize = 10) {
  cloneDataSearch.value.useYn = dataSearch.value.useYn === "0" ? "" : dataSearch.value.useYn;
  cloneDataSearch.value.page = page;
  cloneDataSearch.value.size = pageSize;
  await storeNotice.noticeList({
    method: "POST",
    body: cloneDataSearch.value,
  });
  if (storeNotice.status.value && storeNotice.status.value === SUCCSESS_STATUS) {
    totalNoticeRecord.value = storeNotice.totalRecord ?? 0;
    const { arr } = areAllItemsEqual(storeNotice.listNotice.data.content, listCheckBoxNotice.value, "checkAll");
    rowNoticeData.value = arr;
    dataSearch.value.useYn = dataSearch.value.useYn === "" ? "0" : dataSearch.value.useYn;
  }
}
const dataExcel = () => {
  dataExport.value[0].data = rowNoticeDataExcel.value.map((row) => [row.noId, row.useYn, row.title, row.startDateShow, row.regId, row.regDate]);
};
function mapDataExcel(rowData: any) {
  let arr = [...rowData];
  arr.map((item: any) => {
    item.regDate = format(item.regDate, FORMAT_YYY_MM_DD);
    item.startDateShow = format(item.startDateShow, FORMAT_YYY_MM_DD) + " ~ " + format(item.endDateShow, FORMAT_YYY_MM_DD);
    item.useYn = item.useYn === "Y" ? "사용" : "미사용";
    return item;
  });
  return arr;
}
function areAllItemsEqual(rowData, listSelect, idInput) {
  const all = document.getElementById(idInput);
  all.checked = false;
  let checked;

  let arr = [...rowData];
  arr.map((item: any) => {
    item.regDate = format(item.regDate, FORMAT_YYY_MM_DD);
    item.startDateShow = format(item.startDateShow, FORMAT_YYY_MM_DD) + " ~ " + format(item.endDateShow, FORMAT_YYY_MM_DD);
    item.useYn = item.useYn === "Y" ? "사용" : "미사용";
    return item;
  });

  checked = rowData.every((item1) => {
    return listCheckBoxNotice.value.some((item2) => item1.noticeSeq === item2);
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
      const isSelected = listCheckBoxNotice.value.some((selectedItem) => selectedItem === item.noticeSeq);
      return {
        ...item,
        selected: isSelected,
      };
    });
  }

  return { checked, arr };
}

async function handleClickEdit(event: { noticeSeq: string }) {
  router.push({
    name: SCREEN.noticeCrud.name,
    params: {
      mode: MODE_EDIT,
    },
    state: {
      noticeSeq: event.noticeSeq,
    },
  });
}
watch(
  () => dataSearch.value.startDateShow,
  (newValue) => {
    if (!dataSearch.value.endDateShow) {
      dataSearch.value.startDateShow = newValue;
    } else {
      const dStart = format(newValue, FORMAT_YYY_MM_DD);
      const dEnd = format(dataSearch.value.endDateShow, FORMAT_YYY_MM_DD);
      if (dStart > dEnd) {
        dataSearch.value.startDateShow = dataSearch.value.endDateShow;
        modalErrorDate.value = true;
      }
    }
  },
);

watch(
  () => dataSearch.value.endDateShow,
  (newValue) => {
    if (!dataSearch.value.startDateShow) {
      dataSearch.value.endDateShow = newValue;
    } else {
      const dStart = format(dataSearch.value.startDateShow, FORMAT_YYY_MM_DD);
      const dEnd = format(newValue, FORMAT_YYY_MM_DD);
      if (dStart > dEnd) {
        dataSearch.value.endDateShow = dataSearch.value.startDateShow;
        modalErrorDate.value = true;
      }
    }
  },
);
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

const openModalConfirmDownload = () => {
  modalOpenConfirmDownload.value = true;
};

const closeModalConfirmDownload = () => {
  modalOpenConfirmDownload.value = false;
};

const downloadExcelCallData = async () => {
  if (callDataExcel.value) {
    const updatedDataSearch = { ...cloneDataSearch.value, size: totalNoticeRecord.value !== 0 ? totalNoticeRecord.value : 10 };
    await storeNotice.noticeList({
      method: "POST",
      body: updatedDataSearch,
    });
    const arrExcel = mapDataExcel(storeNotice.listNotice.data.content);
    rowNoticeDataExcel.value = arrExcel;
    callDataExcel.value = false;
    dataExcel();
  }
  downloadExcelRef.value.downloadExcel();
  closeModalConfirmDownload();
};
</script>
