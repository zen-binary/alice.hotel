<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div class="box dp_block">
    <div class="box_section">
      <p class="box_title_sm">Grid Component</p>
      <button class="btn_primary button" @click="search">Mô phỏng button Search</button>
      <GridComponent
        ref="grid"
        :column-defs="colDefs"
        :rows-data="rowData"
        :pagination="pagination"
        :total-row="totalRow"
        :pagination-page-size="paginationPageSize"
        @get-data="fetchData"
      >
        <template #itemSelected>
          <div>
            <span>{{ valueSeleted }} 개 선택</span>
          </div>
        </template>
        <template #button>
          <div class="btn_group btn_end">
            <button type="button" class="button btn_sm btn_white btn_responsive">btn_sm</button>
            <button type="button" class="button btn_sm btn_white btn_responsive">btn_sm</button>
            <button type="button" class="button btn_sm btn_white btn_responsive">btn_sm</button>
            <button type="button" class="button btn_sm btn_white btn_responsive">btn_sm</button>
            <button type="button" class="button btn_sm btn_white btn_responsive">btn_sm</button>
            <button type="button" class="button btn_sm btn_white btn_responsive">btn_sm</button>
            
          </div>
        </template>
      </GridComponent>
    </div>
  </div>
</template>

<script setup lang="ts">
import GridComponent from "@/components/common/grid/GridComponent.vue";
import http from "@/utils/http.js";
import GridCheckBox from "@/components/common/grid/GridCheckBox.vue";
import { nextTick, onUpdated, ref } from "vue";

//Grid
const valueSeleted = ref(10);
const rowData = ref([]);
const colDefs = ref([
  {
    headerName: "",
    field: "selected",
    width: 100,
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
      childId: "userId",
      mode: "child",
      gridName: "grid1",
      onCustomClick: handleClickChild,
    },
  },
  { field: "userId" },
  { field: "username" },
  { field: "email" },
  { field: "birthdate" },
]);

const pagination = ref(true);
const paginationPageSize = ref(10);
const totalRow = ref(0);
const listSelect = ref([]);
const grid = ref(null)

//Logic
onUpdated(async () => {
  await nextTick();
  listSelect.value.forEach((item) => {
    const e = document.getElementById(item.userId);
    if (e) {
      e.checked = true;
    }
  });
});

//grid
const fetchData = async (currentPage: number, pageSzie: number) => {
  try {
    const response = await http.get("https://menu-api-50av.onrender.com/api/items", {
      params: {
        page: currentPage,
        pageSize: pageSzie,
      },
    });
    totalRow.value = response.data.totalItems;
    const { arr } = areAllItemsEqual(response.data.items, listSelect.value, "gridCheckAll");
    rowData.value = arr;
  } catch (err) {
    console.log(err);
  }
};

function handleClickAll(value) {
  if (value) {
    listSelect.value = [...rowData.value];
  } else {
    listSelect.value = [];
  }

  //add logic update value selected
  rowData.value = rowData.value.map((item) => {
    return { ...item, selected: value }
  });
}

function handleClickChild(value, flag) {
  if (flag) {
    listSelect.value.push(value);
  } else {
    listSelect.value = listSelect.value.filter((item) => item.userId !== value.userId);
  }

  //add logic update value selected
  rowData.value = rowData.value.map((item) => {
    if (item.userId === value.userId) {
      return { ...item, selected: flag };
    }
    return item;
  });
}

function areAllItemsEqual(rowData, listSelect, idInput) {
  const all = document.getElementById(idInput);
  all.checked = false;
  let checked;
  let arr = [...rowData];

  checked = rowData.every((item1) => {
    return listSelect.some((item2) => item1.userId === item2.userId);
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
      const isSelected = listSelect.some((selectedItem) => selectedItem.userId === item.userId);
      return {
        ...item,
        selected: isSelected,
      };
    });
  }

  return { checked, arr };
}

const search = () => {
  grid.value.initGrid()
}
</script>

<style scoped></style>
