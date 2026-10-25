<template>
  <PageContainer>
    <Breadcrumb :pageTitle="'Resource Management'"></Breadcrumb>

    <!-- fomr search -->
    <div class="box dp_block">
      <div class="search_box col_3">
        <ul>
          <li>
            <p>Name Resource</p>
            <InputBase :id="'name'" :name="'name'" :mode="'show'" v-model="resourceSearchModel.name"></InputBase>
          </li>

          <li>
            <p>Type</p>
            <SelectBoxBase :id="'typeResource'" :name="'typeResource'" v-model="selectTypeValue" :data="listSelectType" :mode="mode"></SelectBoxBase>
          </li>

          <li>
            <p>Method</p>
            <SelectBoxBase :id="'typeMethod'" :name="'typeMethod'" v-model="selectMethodValue" :data="listSelectMethod" :mode="mode"></SelectBoxBase>
          </li>

          <li>
            <p>URL Resource</p>
            <InputBase :id="'url'" :name="'url'" :mode="'show'" v-model="resourceSearchModel.url"></InputBase>
          </li>

          <li>
            <p>Reg Date</p>
            <div class="search_flex w-full" style="position: relative">
              <BaseDatePicker v-model="resourceSearchModel.startDate" :placeholder="'Start Date'"></BaseDatePicker>
              <span>~</span>
              <BaseDatePicker v-model="resourceSearchModel.endDate" :placeholder="'End Date'"></BaseDatePicker>
            </div>
          </li>
        </ul>

        <!-- button search -->
        <div class="btn_group btn_end mg_t10">
          <button type="button" class="button btn_md btn_lightgray btn_responsive" @click="reset()">Reset</button>
          <button type="button" class="button btn_md btn_primary btn_responsive" @click="search()">Search</button>
        </div>
      </div>
    </div>

    <!-- grid table data -->
    <div class="box dp_block">
      <div class="box_section">
        <div class="page_toparea">
          <h2 class="title-search">Resource Data</h2>
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
              <button type="button" class="btn_md btn_primary" @click="create()">Create</button>
            </div>
          </template>
        </GridComponent>
      </div>
    </div>
  </PageContainer>

  <!-- Modal create or edit resource -->
  <TModal :is-open="modalSaveDataOpen" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
    <!-- Modal content goes here -->
    <template #title>
      <strong>Create Resource</strong>
    </template>
    <template #default>
      <table class="tbl_row">
        <colgroup>
          <col style="width: 15%" />
          <col style="width: 35%" />
          <col style="width: 15%" />
          <col style="width: 35%" />
        </colgroup>

        <!-- form data -->
        <tbody>
          <tr>
            <th scope="row">
              <p class="required">Name</p>
            </th>
            <td colspan="1">
              <InputBase :id="'name'" :name="'name'" :mode="'show'" v-model="resourceResModel.rsNm"></InputBase>
            </td>

            <th scope="row">
              <p class="mg_l10">Type</p>
            </th>
            <td colspan="3">
              <SelectBoxBase
                :id="'typeResource'"
                :name="'typeResource'"
                v-model="selectTypeValueCreate"
                :data="listSelectTypeCreate"
                :mode="mode"
              ></SelectBoxBase>
            </td>
          </tr>

          <tr>
            <th scope="row">
              <p>Method</p>
            </th>
            <td colspan="1">
              <SelectBoxBase
                :id="'typeMethod'"
                :name="'typeMethod'"
                v-model="selectMethodValueCreate"
                :data="listSelectMethodCreate"
                :mode="mode"
                class="mg_t10"
              ></SelectBoxBase>
            </td>

            <th scope="row">
              <p class="required mg_l10">URL</p>
            </th>
            <td colspan="3">
              <InputBase :id="'url'" :name="'url'" :mode="'show'" v-model="resourceResModel.url" class="mg_t10"></InputBase>
            </td>
          </tr>
        </tbody>
      </table>
    </template>
    <template #footer>
      <button :disabled="!isCheck" type="button" class="btn_md btn_primary" @click="confirmAction">Save</button>
      <button type="button" class="btn_md btn_lightgray popup_close" @click="cancelAction">Back</button>
    </template>
  </TModal>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, watch, onBeforeMount } from "vue";
import {
  FORMAT_YYY_MM_DD,
  MODE_EDIT,
  PAGINATION_PAGE_SIZE,
  SUCCSESS_STATUS,
  MODE_SHOW,
  MODE_CREATE,
  SUCCSESS_STATUS_CREATE,
} from "../../constants/screen.const";
import InputBase from "@/components/common/input/InputBase.vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import BaseDatePicker from "@/components/common/input/BaseDatePicker.vue";
import { ResourceRequestModel, ResourceResponseModel, ResourceSearchModel } from "../../stores/resourceService/resource.type";
import { resourceStore } from "../../stores/resourceService";
import { format } from "date-fns";
import GridComponent from "@/components/common/grid/GridComponent.vue";
import Swal from "sweetalert2";
import GroupButtonGridComponent from "@/components/common/grid/GroupButtonGridComponent.vue";

const modeScreen = ref("");
const storeResource = resourceStore();
const pagination = ref(true);
const totalRow = ref();
const keyId = ref(0);
const isCheck = ref(false);
const mode = ref(MODE_SHOW);
const rowData = ref<Array<ResourceResponseModel>>([]);
const paginationPageSize = ref(PAGINATION_PAGE_SIZE);
const selectTypeValue = ref("");
const selectMethodValue = ref("");
const selectTypeValueCreate = ref("");
const selectMethodValueCreate = ref("");

const listSelectType = [
  { cdId: "ALL", cdNm: "ALL" },
  { cdId: "API", cdNm: "API" },
  { cdId: "MENU", cdNm: "MENU" },
];

const listSelectMethod = [
  { cdId: "ALL", cdNm: "ALL" },
  { cdId: "GET", cdNm: "GET" },
  { cdId: "POST", cdNm: "POST" },
  { cdId: "PUT", cdNm: "PUT" },
  { cdId: "DELETE", cdNm: "DELETE" },
];

const listSelectTypeCreate = [
  { cdId: "API", cdNm: "API" },
  { cdId: "MENU", cdNm: "MENU" },
];

const listSelectMethodCreate = [
  { cdId: "GET", cdNm: "GET" },
  { cdId: "POST", cdNm: "POST" },
  { cdId: "PUT", cdNm: "PUT" },
  { cdId: "DELETE", cdNm: "DELETE" },
];

const resourceSearchModel = ref({
  url: "",
  method: "",
  name: "",
  startDate: "",
  endDate: "",
  type: "",
  page: 1,
  size: PAGINATION_PAGE_SIZE,
  sort: "",
} as ResourceSearchModel);

const resourceResModel = ref<ResourceResponseModel>({});
const resourceReqModel = ref<ResourceRequestModel>({});

const columnDefs = ref([
  {
    headerName: "STT",
    field: "rowNumber",
    flex: 1,
  },
  {
    headerName: "Name Resource",
    field: "rsNm",
    flex: 1,
  },
  {
    headerName: "URL Resource",
    field: "url",
    flex: 1,
  },
  {
    headerName: "Method",
    field: "httpMethod",
    flex: 1,
  },
  {
    headerName: "Type",
    field: "rsType",
    flex: 1,
  },
  {
    headerName: "Reg Date",
    field: "regDate",
    flex: 1,
  },
  {
    headerName: "Action",
    cellRenderer: GroupButtonGridComponent,
    cellRendererParams: {
      value1: "Edit",
      value2: "Delete",
      onCustomEvent1: handleEditClick,
      onCustomEvent2: handleDeleteClick,
    },
  },
]);

const modalSaveDataOpen = ref(false);
const modalId = ref("demo");

function checkValid() {
  isCheck.value = !!resourceResModel.value.rsNm && !!resourceResModel.value.url;
}

function handleKeyPress(event: KeyboardEvent) {
  if (event.key === "Enter") {
    search();
  }
}

function openModal() {
  modalSaveDataOpen.value = true;
}

function closeModal() {
  modalSaveDataOpen.value = false;
  resetModel();
}

function cancelAction() {
  modeScreen.value = "";
  closeModal();
}

function resetModel() {
  resourceResModel.value.rsId = "";
  resourceResModel.value.rsNm = "";
  resourceResModel.value.url = "";
  selectTypeValue.value = listSelectType[0].cdId;
  selectMethodValue.value = listSelectMethod[0].cdId;
}

function reset() {
  resourceSearchModel.value.url = "";
  resourceSearchModel.value.method = "";
  resourceSearchModel.value.name = "";
  resourceSearchModel.value.startDate = "";
  resourceSearchModel.value.endDate = "";
  resourceSearchModel.value.type = "";
  resourceSearchModel.value.page = 1;
  resourceSearchModel.value.size = paginationPageSize.value;
  resourceSearchModel.value.sort = "";

  selectMethodValue.value = listSelectMethod[0].cdId;
  selectTypeValue.value = listSelectType[0].cdId;
}

function search() {
  keyId.value++;
  resourceSearchModel.value.type = selectTypeValue.value === "ALL" ? "" : selectTypeValue.value;
  resourceSearchModel.value.method = selectMethodValue.value === "ALL" ? "" : selectMethodValue.value;
  resourceSearchModel.value.page = 1;
  getDataAll();
}

async function getDataAll(page = 1, pageSize = 10) {
  if (resourceSearchModel.value.startDate) {
    resourceSearchModel.value.startDate = format(resourceSearchModel.value.startDate, FORMAT_YYY_MM_DD);
  }
  if (resourceSearchModel.value.endDate) {
    resourceSearchModel.value.endDate = format(resourceSearchModel.value.endDate, FORMAT_YYY_MM_DD);
  }
  resourceSearchModel.value.page = page;
  resourceSearchModel.value.size = pageSize;
  await storeResource.searchResource({
    method: "POST",
    body: resourceSearchModel.value,
  });
  rowData.value = storeResource.resultData.data.content.map((item) => {
    item.regDate = format(item.regDate, FORMAT_YYY_MM_DD);
    return item;
  });
  totalRow.value = storeResource.resultData.data.totalElements;
}

async function handleEditClick(event: { rsId: any }) {
  await storeResource.getDetailById({
    method: "POST",
    body: event.rsId.toString(),
  });

  resourceResModel.value = storeResource.detailData;
  selectTypeValueCreate.value = resourceResModel.value.rsType;
  selectMethodValueCreate.value = resourceResModel.value.httpMethod;
  modeScreen.value = MODE_EDIT;
  openModal();
}

async function handleDeleteClick(event: { rsId: any }) {
  const result = await Swal.fire({
    title: "Are you sure delete?",
    icon: "question",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Yes",
    cancelButtonText: "No",
  });

  if (result.isConfirmed) {
    await storeResource.deleteResoure({
      method: "DELETE",
      body: event.rsId.toString(),
    });
    if (storeResource.status.value && storeResource.status.value == SUCCSESS_STATUS) {
      noitificationAlert();
    } else {
      noitificationErrorAlert();
    }
  }
}

function create() {
  selectTypeValueCreate.value = listSelectTypeCreate[0].cdId;
  selectMethodValueCreate.value = listSelectMethodCreate[0].cdId;
  modeScreen.value = MODE_CREATE;
  openModal();
}

function getFormData() {
  resourceReqModel.value.rsId = resourceResModel.value.rsId;
  resourceReqModel.value.url = resourceResModel.value.url;
  resourceReqModel.value.rsNm = resourceResModel.value.rsNm;
  resourceReqModel.value.rsType = selectTypeValueCreate;
  resourceReqModel.value.httpMethod = selectMethodValueCreate;
}

function confirmAction() {
  let title = "";
  if (modeScreen.value === MODE_CREATE) {
    title = "Are you sure save (create)?";
  } else if (modeScreen.value === MODE_EDIT) {
    title = "Are you sure save (edit)?";
  }
  Swal.fire({
    title: title,
    icon: "question",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Yes",
    cancelButtonText: "No",
  }).then((result) => {
    if (result.isConfirmed) {
      if (modeScreen.value === MODE_CREATE) {
        saveData();
      } else if (modeScreen.value === MODE_EDIT) {
        editData();
      }
    }
  });
}
function noitificationErrorAlert() {
  Swal.fire({
    title: "Fail!",
    icon: "error",
  }).then(() => {
    closeModal();
  });
}

function noitificationAlert() {
  Swal.fire({
    title: "Successfully!!!",
    icon: "success",
  }).then(() => {
    closeModal();
    reset();
    getDataAll();
  });
}

async function saveData() {
  getFormData();
  await storeResource.createResource({
    method: "POST",
    body: resourceReqModel.value,
  });
  if (storeResource.status.value && storeResource.status.value == SUCCSESS_STATUS_CREATE) {
    noitificationAlert();
  } else {
    noitificationErrorAlert();
  }
}

async function editData() {
  getFormData();
  await storeResource.updateResource({
    method: "PUT",
    body: resourceReqModel.value,
  });
  if (storeResource.status.value && storeResource.status.value == SUCCSESS_STATUS) {
    noitificationAlert();
  } else {
    noitificationErrorAlert();
  }
}

onBeforeMount(() => {
  selectTypeValue.value = listSelectType[0].cdId;
  selectMethodValue.value = listSelectMethod[0].cdId;
});

onMounted(() => {
  document.addEventListener("keypress", handleKeyPress);
});

onBeforeUnmount(() => {
  document.removeEventListener("keypress", handleKeyPress);
});

watch(() => resourceResModel, checkValid, { deep: true });

watch(
  () => resourceSearchModel.value.startDate,
  (newValue) => {
    if (!resourceSearchModel.value.endDate) {
      resourceSearchModel.value.startDate = newValue;
    } else {
      const dStart = format(newValue, FORMAT_YYY_MM_DD);
      const dEnd = format(resourceSearchModel.value.endDate, FORMAT_YYY_MM_DD);
      if (dStart > dEnd) {
        resourceSearchModel.value.startDate = resourceSearchModel.value.endDate;
        Swal.fire({
          title: "The end date cannot be earlier than the start date.",
          icon: "error",
        });
      }
    }
  },
);

watch(
  () => resourceSearchModel.value.endDate,
  (newValue) => {
    if (!resourceSearchModel.value.startDate) {
      resourceSearchModel.value.endDate = newValue;
    } else {
      const dStart = format(resourceSearchModel.value.startDate, FORMAT_YYY_MM_DD);
      const dEnd = format(newValue, FORMAT_YYY_MM_DD);
      if (dStart > dEnd) {
        resourceSearchModel.value.endDate = resourceSearchModel.value.startDate;
        Swal.fire({
          title: "The end date cannot be earlier than the start date.",
          icon: "error",
        });
      }
    }
  },
);
</script>
