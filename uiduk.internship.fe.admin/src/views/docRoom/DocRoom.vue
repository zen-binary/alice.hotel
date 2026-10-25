<template>
  <PageContainer>
    <h1 v-once>
      {{ t("47.documentRoom.titlePage") }}
    </h1>
    <div class="box dp_block">
      <div class="box_section">
        <div class="search_box col_2">
          <ul>
            <li>
              <p class="ta_r">
                {{ t("47.documentRoom.search.ttl") }}
              </p>
              <InputBase v-model="docRoomSearchModel.title" />
            </li>
            <li>
              <p class="ta_r">
                {{ t("47.documentRoom.search.regDate") }}
              </p>
              <BaseDatePicker v-model="startDate" />
              <span style="padding-left: 10px">~</span>
              <BaseDatePicker v-model="endDate" />
            </li>
            <li>
              <p class="ta_r">
                {{ t("47.documentRoom.search.useYn") }}
              </p>
              <RadiobuttonBase
                v-for="item in listRadio1"
                :id="item.cdId"
                :key="item.cdId"
                v-model="docRoomSearchModel.useYn"
                :mode="'show'"
                :value="item.cdId"
                :name="item.cdId"
                :checked="item.cdId == docRoomSearchModel.useYn"
                :label="item.cdNm"
              />
            </li>
            <li>
              <p class="ta_r">
                {{ t("47.documentRoom.search.subscribers") }}
              </p>
              <InputBase v-model="docRoomSearchModel.createdBy" />
            </li>
          </ul>

          <div class="btn_group btn_end mg_t30 btn_form_search">
            <button type="button" class="button btn_md btn_lightgray btn_responsive" @click="resetSearch">
              {{ t("common.reset") }}
            </button>
            <button type="button" class="button btn_md btn_primary btn_responsive" @click="searchClick">
              {{ t("common.search") }}
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="box dp_block">
      <div class="box_section">
        <div class="page_toparea">
          <p class="box_title_sm">
            {{ t("47.documentRoom.title") }}
          </p>
        </div>
        <GridComponent
          ref="grid"
          :rows-data="rowData"
          :column-defs="columnDefs"
          :pagination="true"
          :pagination-page-size="paginationPageSize"
          :overlay-no-rows-template="noRowsText"
          :total-row="totalRows"
          @get-data="loadRowData"
        >
          <template #itemSelected>
            <div>
              <span class="mr-2">{{ listSelect.length }} {{ t("47.documentRoom.search.textTotal") }}</span>
            </div>
          </template>
          <template #button>
            <div class="btn_group btn_end">
              <button :disabled="listSelect.length == 0" type="button" class="button btn_md btn_lightgray" @click="deleteData">
                {{ t("common.delete") }}
              </button>
              <button type="button" class="button btn_md btn_primary" @click="create">
                {{ t("47.documentRoom.table.btnCreate") }}
              </button>
              <ExportFileExcel
                :data="dataExport"
                :file-name="fileNameExport"
                :call-data="true"
                :btn-name="t('47.documentRoom.btnExcel')"
                @click="openModalConfirmDownload"
                ref="downloadExcelRef"
              />
            </div>
          </template>
        </GridComponent>
      </div>
    </div>
  </PageContainer>

  <!-- Modal Confirm for Delete -->
  <TModalConfirm
    :is-open="modalOpenConfirm"
    :is-show-header="true"
    :is-show-footer="true"
    :modal-id="modalIdConfirm"
    @close-modal="closeModalConfirm"
  >
    <template #title>
      <strong />
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ t("47.documentRoom.message.confirmDelete") }}</p>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_md btn_primary" @click="confirmAction">
        {{ t("common.confirm") }}
      </button>
      <button type="button" class="button btn_md btn_white popup_close" @click="closeModalConfirm">
        {{ t("common.cancel") }}
      </button>
    </template>
  </TModalConfirm>

  <!-- Modal Alert for Save Confirmation -->
  <TModalAlert :is-open="modalOpenAlert" :is-show-header="true" :is-show-footer="true" :modal-id="modalIdAlert" @close-modal="closeModalAlert">
    <template #title>
      <strong />
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ alertMessage }}</p>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_md btn_primary" @click="closeModalAlert">
        {{ t("common.confirm") }}
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
</template>

<script setup lang="ts">
import { ref, watch, onUpdated, nextTick, onBeforeMount } from "vue";
import { useI18n } from "vue-i18n";
import InputBase from "@/components/common/input/InputBase.vue";
import GridComponent from "@/components/common/grid/GridComponent.vue";
import BaseDatePicker from "@/components/common/input/BaseDatePicker.vue";
import type { DocRoomSearchModel, DocRoomModel } from "@/stores/docRoom/docRoom.type";
import GridCheckBox from "@/components/common/grid/GridCheckBox.vue";
import PageContainer from "@/components/common/PageContainer.vue";
import { FORMAT_YYY_MM_DD, MODE_CREATE, MODE_EDIT, PAGINATION_PAGE_SIZE } from "@/constants/screen.const";
import { SCREEN } from "@/router/screen";
import { useRouter } from "vue-router";
import { ExcelData } from "@/model/common/excelData.type";
import { docRoomStore } from "@/stores/docRoom";
import TModalConfirm from "@/components/common/excel/TModalConfirm.vue";
import { format } from "date-fns";
import TModalAlert from "@/components/common/excel/TModalAlert.vue";

const { t } = useI18n();
const router = useRouter();
const drStore = docRoomStore();

const modalOpenConfirm = ref(false);
const modalOpenAlert = ref(false);
const modalIdConfirm = ref("modalIdConfirm");
const modalIdAlert = ref("modalIdAlert");
const alertMessage = ref("");
const callDataExcel = ref(true);
const currentPage = ref(1);
const paginationPageSize = PAGINATION_PAGE_SIZE;

const docRoomSearchModel = ref<DocRoomSearchModel>({
  title: "",
  createdBy: "",
  regStartDate: "",
  regEndDate: "",
  useYn: "ALL",
  page: currentPage.value,
  size: paginationPageSize,
  sort: "",
});
const grid = ref(null)
const cloneDataSearch = ref(null)
const totalRows = ref<number>();
const noRowsText = t("47.documentRoom.noRow");
const startDate = ref<string>("");
const endDate = ref<string>("");
const rowData = ref<DocRoomModel[]>([]);
const rowDataExcel = ref<DocRoomModel[]>([]);
const listSelect = ref([]);
const fileNameExport = t("47.documentRoom.fileNameExport");
const dataExport = ref([
  {
    sheetName: "자료실 목록",
    data: [],
    header: [t("common.rowNum"), t("47.documentRoom.table.title"), t("47.documentRoom.table.regId"), t("47.documentRoom.table.regDate")],
  },
] as Array<ExcelData>);
  const listRadio1 = ref([
  {
    cdId: "ALL",
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
const columnDefs = ref([
  {
    headerName: "check",
    field: "selected",
    flex: 1,
    headerComponent: GridCheckBox,
    headerComponentParams: {
      checkAll: "checkAll",
      mode: "all",
      onCustomClick: handleClickAll,
      gridName: "grid",
    },
    cellRenderer: GridCheckBox,
    cellRendererParams: {
      checkAll: "checkAll",
      childId: "docSeq ",
      mode: "child",
      gridName: "grid",
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
    field: "rowNumber",
    maxWidth: 90,
  },
  {
    headerName: t("47.documentRoom.table.useYn"),
    field: "useYn",
    flex: 1,
  },
  {
    headerName: t("47.documentRoom.table.title"),
    field: "title",
    flex: 5,
  },
  {
    headerName: t("47.documentRoom.table.regId"),
    field: "regId",
    flex: 5,
  },
  {
    headerName: t("47.documentRoom.table.regDate"),
    field: "regDate",
    flex: 1,
  },
  {
    headerName: t("47.documentRoom.table.edit"),
    field: "",
    cellRenderer: (params) => {
      // create button
      const button = document.createElement("button");
      button.innerText = "수정";
      button.className = "btn_round btn_xs btn_primary";

      // handle event
      button.addEventListener("click", () => {
        handleCustomClick(params.data);
      });

      return button;
    },
    maxWidth: 90,
  },
]);
const dataExcel = () => {
  dataExport.value[0].data = rowDataExcel.value.map((row) => [row.rowNumber, row.title, row.regId, row.regDate]);
};

onBeforeMount(()=>{
  cloneDataSearch.value = {...docRoomSearchModel.value}
})

onUpdated(async () => {
  await nextTick();
  listSelect.value.forEach((item) => {
    const e = document.getElementById(item.docSeq);
    if (e) {
      e.checked = true;
    }
  });
});

const loadRowData = async (page = 1, size = 10) => {
  cloneDataSearch.value.useYn = docRoomSearchModel.value.useYn === "ALL" ? "" : docRoomSearchModel.value.useYn;
  cloneDataSearch.value.page = page;
  cloneDataSearch.value.size = size;
  cloneDataSearch.value.sort = "";
  await drStore.getPageDocRooms({
    method: "POST",
    body: cloneDataSearch.value,
  });
  const { arr } = areAllItemsEqual(drStore.list.data.content, listSelect.value, "checkAll");
  totalRows.value = drStore.list.data.totalElements;
  rowData.value = arr;
  rowData.value.map((item) => {
    item.regDate = format(item.regDate, FORMAT_YYY_MM_DD);
    item.useYn = item.useYn === "Y" ? "사용" : "미사용";
  });
};

function mapDataExcel(rowData: any) {
  let arr = [...rowData];
  arr.map((item: any) => {
    item.regDate = format(item.regDate, FORMAT_YYY_MM_DD);
    return item;
  });
  return arr;
}
const deleteDocRoom = async () => {
  await drStore.deleteDocRoom({
    method: "POST",
    body: listSelect.value,
  });
  listSelect.value = [];
  callDataExcel.value = true;
};

function searchClick() {
  docRoomSearchModel.value.regStartDate = startDate.value;
  docRoomSearchModel.value.regEndDate = endDate.value;
  cloneDataSearch.value = { ...docRoomSearchModel.value };
  grid.value.initGrid();
  listSelect.value = [];
}

function create() {
  router.push({
    name: SCREEN.crudDocRoom.name,
    params: { mode: MODE_CREATE },
  });
}

function deleteData() {
  modalOpenConfirm.value = true;
}

function handleCustomClick(data: any) {
  router.push({
    name: SCREEN.crudDocRoom.name,
    params: { mode: MODE_EDIT },
    state: {
      id: data.docSeq,
    },
  });
}

function resetSearch() {
  docRoomSearchModel.value.title = "";
  docRoomSearchModel.value.createdBy = "";
  docRoomSearchModel.value.regStartDate = "";
  docRoomSearchModel.value.regEndDate = "";
  startDate.value = "";
  endDate.value = "";
}

function areAllItemsEqual(rowData, listSelect, idInput) {
  const all = document.getElementById(idInput);
  all.checked = false;
  let checked;
  let arr = [...rowData];

  checked = rowData.every((item1) => {
    return listSelect.some((item2) => item1.docSeq === item2.docSeq);
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
      const isSelected = listSelect.some((selectedItem) => selectedItem.docSeq === item.docSeq);
      return {
        ...item,
        selected: isSelected,
      };
    });
  }

  return { checked, arr };
}

function handleClickAll(value) {
  rowData.value.forEach((item) => {
    if (value) {
      if (!listSelect.value.some((el) => el.docSeq == item.docSeq)) {
        listSelect.value.push(item);
      }
    } else {
      listSelect.value = listSelect.value.filter((el) => el.docSeq != item.docSeq);
    }
  });
}

function handleClickChild(value, flag) {
  if (flag) {
    listSelect.value.push(value);
  } else {
    listSelect.value = listSelect.value.filter((item) => item.docSeq !== value.docSeq);
  }
}

watch(startDate, (newValue) => {
  if (!endDate.value) {
    startDate.value = newValue;
  } else {
    const dStart = format(newValue, FORMAT_YYY_MM_DD);
    const dEnd = format(endDate.value, FORMAT_YYY_MM_DD);
    if (dStart > dEnd) {
      nextTick(() => {
        startDate.value = endDate.value;

        alertMessage.value = t("common.messageCheckDate");
        modalOpenAlert.value = true;
      });
    }
  }
});

watch(endDate, (newValue) => {
  if (!startDate.value) {
    endDate.value = newValue;
  } else {
    const dStart = format(startDate.value, FORMAT_YYY_MM_DD);
    const dEnd = format(newValue, FORMAT_YYY_MM_DD);
    if (dStart > dEnd) {
      nextTick(() => {
        endDate.value = startDate.value;

        alertMessage.value = t("common.messageCheckDate");
        modalOpenAlert.value = true;
      });
    }
  }
});

// Modal
const confirmAction = async () => {
  await deleteDocRoom();
  docRoomSearchModel.value.regStartDate = startDate.value;
  docRoomSearchModel.value.regEndDate = endDate.value;
  cloneDataSearch.value = { ...docRoomSearchModel.value };
  grid.value.initGrid();
  closeModalConfirm();

  alertMessage.value = t("47.documentRoom.message.deleteSuccess");
  modalOpenAlert.value = true;
};

const closeModalAlert = () => {
  modalOpenAlert.value = false;
};

const closeModalConfirm = () => {
  modalOpenConfirm.value = false;
};

const downloadExcelRef = ref(null);
const modalOpenConfirmDownload = ref(false);

const downloadExcelCallData = async () => {
  if (callDataExcel.value) {
    const updatedDataSearch = { ...cloneDataSearch.value, size: totalRows.value !== 0 ? totalRows.value : 10 };
    await drStore.getPageDocRooms({
      method: "POST",
      body: updatedDataSearch,
    });
    const arrExcel = mapDataExcel(drStore.list.data.content);
    rowDataExcel.value = arrExcel;
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
