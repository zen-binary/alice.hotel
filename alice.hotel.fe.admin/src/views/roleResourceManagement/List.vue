<template>
  <PageContainer>
    <Breadcrumb :pageTitle="'Phân quyền API theo vai trò'"></Breadcrumb>
    <div class="divide_box">
      <!-- grid table role data -->
      <div class="box dp_block" style="width: 35%">
        <div class="box_section">
          <div class="page_toparea">
            <h2 class="box_title mg_b15">Danh sách vai trò</h2>
          </div>

          <GridComponent
            ref="grid"
            :column-defs="columnRoleDefs"
            :rows-data="rowRoleData"
            :pagination="pagination"
            :total-row="totalRoleRecord"
            :pagination-page-size="paginationPageSize"
            @get-data="getListRole"
          />
        </div>
      </div>

      <!-- grid table resource data -->
      <div class="box dp_block" style="width: 65%">
        <div class="box_section" id="keyResourceId">
          <div class="page_toparea">
            <h2 class="box_title mg_b15">Danh sách tài nguyên</h2>
          </div>
          <div class="search_box col_3">
            <ul>
              <li>
                <p>URL</p>
                <InputBase :id="'credit'" v-model="resourceSearchModel.url" />
              </li>
              <li>
                <p>Tên</p>
                <InputBase :id="'credit'" v-model="resourceSearchModel.name" />
              </li>
              <li>
                <p>Phương thức</p>
                <SelectBoxBase
                  :id="'typeMethod'"
                  :name="'typeMethod'"
                  v-model="selectMethodValue"
                  :data="listSelectMethod"
                  :mode="'show'"
                ></SelectBoxBase>
              </li>
            </ul>
            <div class="dp_flex btn_group btn_end mg_t15">
              <button type="button" class="button btn_md btn_lightgray btn_responsive" @click="reset()">Đặt lại</button>
              <button type="button" class="button btn_md btn_primary btn_responsive" @click="searchClick">Tìm kiếm</button>
            </div>
          </div>
          <GridComponent
            ref="grid"
            :column-defs="columnResourceDefs"
            :rows-data="rowResourceData"
            :pagination="pagination"
            :total-row="totalResourceRecord"
            :pagination-page-size="paginationPageSize"
            @get-data="getListResource"
          />
        </div>
      </div>
    </div>
    <div class="btn_group btn_center">
      <button type="button" class="btn_xl btn_icon btn_primary" @click="handSaveClick()" :disabled="roleId == 0">Lưu</button>
    </div>
  </PageContainer>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { PAGINATION_PAGE_SIZE, SUCCSESS_STATUS } from "../../constants/screen.const";
import type { ResourceResponseModel, ResourceSearchModel } from "../../stores/resourceService/resource.type";
import { resourceStore } from "../../stores/resourceService";
import ButtonGridComponent from "@/components/common/grid/ButtonGridComponent.vue";
import Swal from "sweetalert2";
import type { RoleResponseModel, RoleSearchModel } from "../../stores/roleResource/roleResource.type";
import GridComponent from "@/components/common/grid/GridComponent.vue";
import type { ResourceRoleRequestModel } from "../../stores/resourceRoleService/resourceRole.type";
import { roleResourceStore } from "@/stores/roleResource";
import { resourceRoleStore } from "@/stores/resourceRoleService";
import GridCheckBox from "@/components/common/grid/GridCheckBox.vue";

const storeResource = resourceStore();
const storeRole = roleResourceStore();
const storeResourceRole = resourceRoleStore();
const totalRoleRecord = ref(0);
const totalResourceRecord = ref(0);
const roleId = ref(0);
const rowRoleData = ref([] as Array<RoleResponseModel>);
const rowResourceData = ref([] as Array<ResourceResponseModel>);
const paginationPageSize = PAGINATION_PAGE_SIZE;
const listCheckBoxResource = ref([] as Array<string>);
const listInsert = ref([] as Array<string>);
const listDelete = ref([] as Array<string>);
const listDataFromDB = ref([] as Array<string>);
const selectMethodValue = ref("");
const pagination = ref(true);

const columnRoleDefs = ref([
  {
    headerName: "Mã vai trò",
    field: "rlCd",
    flex: 1,
  },
  {
    headerName: "Tên vai trò",
    field: "rlNm",
    flex: 1,
  },
  {
    headerName: "Thao tác",
    cellRenderer: ButtonGridComponent,
    cellRendererParams: {
      className: "button btn_xs btn_primary",
      value: "Chọn",
      onCustomEvent: handleActionRoleClick,
    },
  },
]);

const columnResourceDefs = ref([
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
  },
  {
    headerName: "STT",
    field: "rowNumber",
    flex: 1,
  },
  {
    headerName: "Tên tài nguyên",
    field: "rsNm",
    flex: 2,
  },
  {
    headerName: "Loại",
    field: "rsType",
    flex: 2,
  },
  {
    headerName: "Phương thức",
    field: "httpMethod",
    flex: 2,
  },
  {
    headerName: "Đường dẫn API",
    field: "url",
    flex: 4,
  },
]);

const resourceSearchModel = ref({
  page: 1,
  size: paginationPageSize,
  sort: "",
} as ResourceSearchModel);

const roleSearchModel = ref({
  page: 1,
  size: paginationPageSize,
  sort: "",
} as RoleSearchModel);

const listSelectMethod = [
  { cdId: "", cdNm: "Tất cả" },
  { cdId: "GET", cdNm: "GET" },
  { cdId: "POST", cdNm: "POST" },
  { cdId: "PUT", cdNm: "PUT" },
  { cdId: "DELETE", cdNm: "DELETE" },
] as Array<any>;

function checkAll(value) {
  const newRow = rowResourceData.value.map((item) => {
    if (value) {
      listCheckBoxResource.value.push(item.rsId);
      const index = listDelete.value.indexOf(item.rsId);
      if (index !== -1) {
        listDelete.value.splice(index, 1);
      }
    } else {
      const index = listCheckBoxResource.value.indexOf(item.rsId);
      if (index !== -1) {
        listCheckBoxResource.value.splice(index, 1);
      }
      if (listDataFromDB.value.includes(item.rsId)) {
        listDelete.value.push(item.rsId);
      }
    }
    return {
      ...item,
      selected: value,
    };
  });
  listCheckBoxResource.value = [...new Set(listCheckBoxResource.value)];
  listDelete.value = [...new Set(listDelete.value)];
  rowResourceData.value = newRow;
}

function checkChild(value, flag) {
  if (flag) {
    listCheckBoxResource.value.push(value.rsId);
    const index = listDelete.value.indexOf(value.rsId);
    if (index !== -1) {
      listDelete.value.splice(index, 1);
    }
  } else {
    const index = listCheckBoxResource.value.indexOf(value.rsId);
    if (index != -1) {
      listCheckBoxResource.value.splice(index, 1);
    }
    listDelete.value.push(value.rsId);
  }
  listCheckBoxResource.value = [...new Set(listCheckBoxResource.value)];
  listDelete.value = [...new Set(listDelete.value)];
}

async function handleActionRoleClick(event: { rlId: any }) {
  roleId.value = event.rlId;

  await storeResourceRole.findByRoleId({
    method: "POST",
    body: event.rlId.toString(),
  });
  if (storeResourceRole.status.value && storeResourceRole.status.value === SUCCSESS_STATUS) {
    listDataFromDB.value = storeResourceRole.resultResourceRole.data.map((el) => el.rsId);
    listCheckBoxResource.value = storeResourceRole.resultResourceRole.data.map((el) => el.rsId);
    const { arr } = areAllItemsEqual(rowResourceData.value, listCheckBoxResource.value, "checkAll");
    rowResourceData.value = arr;
  }
}

function searchClick() {
  resourceSearchModel.value.page = 1;
  getListResource();
}

async function getListRole(page = 1, pageSize = 10) {
  roleSearchModel.value.page = page;
  roleSearchModel.value.size = pageSize;
  await storeRole.getListRole({
    method: "POST",
    body: roleSearchModel.value,
  });
  if (storeRole.status && storeRole.status === SUCCSESS_STATUS) {
    rowRoleData.value = storeRole.resultData;
    totalRoleRecord.value = storeRole.totalRecord ?? 0;
  }
}

async function getListResource(page = 1, pageSize = 10) {
  resourceSearchModel.value.method = selectMethodValue.value;
  resourceSearchModel.value.page = page;
  resourceSearchModel.value.size = pageSize;
  await storeResource.searchResource({
    method: "POST",
    body: resourceSearchModel.value,
  });

  if (storeResource.status.value && storeResource.status.value === SUCCSESS_STATUS) {
    totalResourceRecord.value = storeResource.totalRecord ?? 0;
    const { arr } = areAllItemsEqual(storeResource.resultData.data.content, listCheckBoxResource.value, "checkAll");
    rowResourceData.value = arr;
  }
}

function areAllItemsEqual(rowData, listSelect, idInput) {
  const all = document.getElementById(idInput);
  all.checked = false;
  let checked;
  let arr = [...rowData];

  checked = rowData.every((item1) => {
    return listCheckBoxResource.value.some((item2) => item1.rsId === item2);
  });

  if (checked) {
    all.checked = true;
    arr = arr.map((item) => {
      return {
        ...item,
        selected: true,
      };
    });
  } else {
    arr = arr.map((item) => {
      const isSelected = listCheckBoxResource.value.some((selectedItem) => selectedItem === item.rsId);
      return {
        ...item,
        selected: isSelected,
      };
    });
  }

  return { checked, arr };
}

function notificationAlert() {
  Swal.fire({
    title: "Đã lưu",
    icon: "success",
  }).then(() => {
    listInsert.value.map((item) => listDataFromDB.value.push(item));
    listInsert.value = [];
    listDelete.value = [];
  });
}

function handSaveClick() {
  Swal.fire({
    title: "Lưu phân quyền này?",
    icon: "question",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Lưu",
    cancelButtonText: "Không",
  }).then((result) => {
    if (result.isConfirmed) {
      saveData();
    }
  });
}

function reset() {
  selectMethodValue.value = "";
  resourceSearchModel.value.url = "";
  resourceSearchModel.value.name = "";
}

async function saveData() {
  if (listInsert.value.length == 0) {
    listInsert.value = listCheckBoxResource.value.filter((item) => !listDataFromDB.value.includes(item));
  } else {
    listInsert.value = listCheckBoxResource.value.filter((item) => !listInsert.value.includes(item));
  }
  listInsert.value = [...new Set(listInsert.value)];

  const resourceRoleModel = {
    listInsert: listInsert.value,
    listDelete: listDelete.value,
    roleId: roleId.value,
  } as ResourceRoleRequestModel;

  await storeResourceRole.createOrDeleteResourceRole({
    method: "POST",
    body: resourceRoleModel,
  });
  notificationAlert();
}
</script>

<style>
#keyResourceId .ag-root-wrapper-body.ag-layout-normal {
  min-height: 600px !important;
}
</style>
