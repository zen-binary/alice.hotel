<template>
  <PageContainer>
    <BreadcrumbComponent :pageTitle="'menu management'" />

    <!-- fomr search -->
    <div class="box dp_block">
      <div class="box_section">
        <div class="search_box col_3">
          <ul>
            <li>
              <p>Name Menu</p>
              <InputBase :id="'name'" :name="'name'" :mode="'show'" v-model="menuSearchModel.name"></InputBase>
            </li>

            <li>
              <p>Route Menu</p>
              <InputBase :id="'route'" :name="'route'" :mode="'show'" v-model="menuSearchModel.route"></InputBase>
            </li>

            <li>
              <p>VN Name</p>
              <InputBase :id="'vnName'" :name="'vnName'" :mode="'show'" v-model="menuSearchModel.vnName"></InputBase>
            </li>
          </ul>
          <div class="btn_group btn_end mg_t10">
            <button type="button" class="button btn_md btn_lightgray btn_responsive" @click="reset()">Reset</button>
            <button type="button" class="button btn_md btn_primary btn_responsive" @click="search()">Search</button>
          </div>
        </div>
      </div>
    </div>

    <!-- grid table data -->
    <div class="box dp_block">
      <div class="box_section">
        <div class="page_toparea">
          <h2 class="title-search">Menu Data</h2>
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
</template>

<script lang="ts" setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import InputBase from "@/components/common/input/InputBase.vue";
import LinkGridComponent from "../../components/common/grid/LinkGridComponent.vue";
import { PAGINATION_PAGE_SIZE, MODE_DETAIL, MODE_CREATE } from "../../constants/screen.const";
import type { MenuResponseModel } from "../../stores/menu/menu.type";
import { menuStore } from "@/stores/menu";
import { SCREEN } from "../../router/screen";
import { STATUS_NO, STATUS_YES } from "../../constants/common.const";
import GridComponent from "@/components/common/grid/GridComponent.vue";
import { useRouter } from "vue-router";

const storeMenu = menuStore();
const router = useRouter();
const keyId = ref(0);
const rowData = ref<Array<MenuResponseModel>>([]);
const paginationPageSize = PAGINATION_PAGE_SIZE;
const pagination = ref(true);
const totalRow = ref();

const handleKeyPress = (event: KeyboardEvent) => {
  if (event.key === "Enter") {
    search();
  }
};

const columnDefs = ref([
  {
    headerName: "STT",
    field: "rowNumber",
    flex: 1,
  },
  {
    headerName: "Name Menu",
    field: "name",
    cellRenderer: LinkGridComponent,
    cellRendererParams: { onCustomEvent: handleCustomClick },
    cellStyle: {
      color: "#2704FF",
      display: "flex",
      alignItems: "center",
    },
  },
  {
    headerName: "Site Type",
    field: "siteType",
    cellRenderer(params: any) {
      if (params.data.siteType === "SITE_TYPE_01") {
        return "Admin";
      } else if (params.data.siteType === "SITE_TYPE_02") {
        return "Professor";
      } else if (params.data.siteType === "SITE_TYPE_03") {
        return "Student";
      } else {
        return "CORPORATE MEMBERS";
      }
    },
    flex: 1,
  },
  {
    headerName: "Route Menu",
    field: "route",
    flex: 1,
  },
  {
    headerName: "Parent",
    field: "parentId",
    flex: 1,
  },
  {
    headerName: "VN Name",
    field: "vnName",
    flex: 1,
  },
]);
const menuSearchModel = ref({
  name: "",
  route: "",
  vnName: "",
  page: 1,
  size: 10,
  sort: "",
});

function reset() {
  menuSearchModel.value = {
    name: "",
    route: "",
    vnName: "",
    page: 1,
    size: menuSearchModel.value.size,
    sort: "",
  };
}

function handleCustomClick(data: any) {
  router.push({
    name: SCREEN.crudPathMenu.name,
    params: { mode: MODE_DETAIL },
    state: { id: data.id },
  });
}

const search = () => {
  keyId.value++;
  menuSearchModel.value.page = 1;
  getDataAll();
};

function create() {
  router.push({
    name: SCREEN.crudPathMenu.name,
    params: { mode: MODE_CREATE },
  });
}

const getDataAll = async (page = 1, pageSize = 10) => {
  menuSearchModel.value.page = page;
  menuSearchModel.value.size = pageSize;
  await storeMenu.searchMenu({
    method: "POST",
    body: menuSearchModel.value,
  });
  rowData.value = storeMenu.resultData.data.content;
  totalRow.value = storeMenu.resultData.data.totalElements;
};
onMounted(() => {
  document.addEventListener("keypress", handleKeyPress);
});

onBeforeUnmount(() => {
  document.removeEventListener("keypress", handleKeyPress);
});
</script>
