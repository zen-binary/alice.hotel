<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div>
    <div class="header_total_row">
      <div class="header_total_row-select wd_p40">
        <span class="wd_75">Total {{ totalRow }}</span>
        <div class="wd_70 ">
          <SelectBoxBase v-model="selectBoxValue" class="wd_70" name="selectbox" :data="listSelectBoxValidate" />
        </div>
        <slot name="buttonLeft"></slot>
      </div>
      <div class="button-list">
        <slot name="button"></slot>
      </div>

    </div>
    <slot name='itemSelected'></slot>

    <!-- The AG Grid component -->
    <ag-grid-vue
        class="ag-theme-quartz mt-4"
        :row-data="computedData"
        :column-defs="columnDefs"
        style="width: 100%"
        :style="{ height: height }"
        :grid-options="gridOptions"
        :pagination="pagination"
        :pagination-page-size="selectBoxValue"
        :pagination-page-size-selector="paginationPageSizeSelector"
        :overlay-no-rows-template="noRowsText"
        :suppress-pagination-panel="true"
    />
    <Pagination v-if="pagination && computedData.length" :key="keyPagi" v-model="currentPage" :total-record="totalRow"
        :page-size="selectBoxValue" class="mt-4"/>
  </div>
</template>
<script setup lang="ts">
import {AgGridVue} from "ag-grid-vue3";
import {onMounted, ref, watch, computed, onBeforeMount} from "vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import CodeMngModel from "@/model/common/CodeMngModel";
import Pagination from "@/components/common/PaginationComponent.vue";

const props = defineProps({
  columnDefs: {
    type: Array,
    required: true,
  },
  rowsData: {
    type: Array,
    required: true,
  },
  pagination: {
    type: Boolean,
    required: false,
    default: false,
  },
  paginationPageSize: {
    type: Number,
    required: false,
    default: 10,
  },
  paginationPageSizeSelector: {
    type: Array as () => number[],
    required: false,
    default: () => [10, 20, 50, 100],
  },
  noRowsText: {
    type: String,
    required: false,
    default: "조회된 데이터가 없습니다",
  },
  totalRow: {
    type: Number,
    required: false,
    default: 0,
  },
  autoSizeStrategy: {
    type: Boolean,
    required: false,
  }
});

const emit = defineEmits(["getData"]);
const gridOptions = {
  defaultColDef: {
    flex: 1,
    minWidth: 100,
    resizable: true,
    suppressMovable: true,
  },
  rowSelection: "multiple",
  suppressRowClickSelection: true,
  autoSizeStrategy: {
    type: props.autoSizeStrategy ? 'fitCellContents' : ''
  }
};
const selectBoxValue = ref(props.paginationPageSize);
const listSelectBoxValidate = ref<CodeMngModel[]>([]);
const currentPage = ref(1);
const keyPagi = ref(0);
const height = ref("");

// Fetch data when the component is mounted
onBeforeMount(() => {
  props.paginationPageSizeSelector.map((item) => {
    listSelectBoxValidate.value.push({cdId: item, cdNm: item.toString(), upCdId: item.toString()});
  });
});
onMounted(() => {
  getData(currentPage.value);
  calculatorHeight(props.rowsData)
});

watch(
    [selectBoxValue, currentPage],
    ([newSelectValue, newPage], [oldSelectValue, oldPage]) => {
      if(newSelectValue !== oldSelectValue ) {
        currentPage.value = 1
      }
      if (newSelectValue !== oldSelectValue || newPage !== oldPage) {
        getData(currentPage.value);
      }
    }
);

watch(
    () => props.rowsData,
    (newValue) => {
      calculatorHeight(newValue);
    },
);

const computedData = computed(() => {
  return props.rowsData;
});

// const computedData = computed(() => {
//   if (!props.rowsData?.length || !props.rowsData) {
//     height.value = 200;
//   } else if (props.rowsData.length <= 10) {
//     height.value = props.rowsData.length <= 10 ? props.rowsData.length * 42 + 51 : 471;
//   } else {
//     height.value = 500;
//   }
//   return props.rowsData;
// });

const getData = (value: number) => {
  emit("getData", value, selectBoxValue.value);
};

const calculatorHeight = (arr) => {
  if (!arr?.length) {
    height.value = "200px";
  } else if (arr.length <= 10) {
    height.value = props.autoSizeStrategy ? `${arr.length * 42 + 51 + 5}px` : `${arr.length * 42 + 51}px`;
  } else {
    height.value = "471px";
  }
}

const initGrid = () => {
  selectBoxValue.value = 10
  if(currentPage.value === 1){
    emit("getData", currentPage.value, selectBoxValue.value);
  }
  currentPage.value = 1;
};

defineExpose(
    {initGrid}
)
</script>

<style lang="scss">
.header_total_row {
  display: flex;
  justify-content: space-between;
}

.header_total_row-select{
  display: flex;
  align-items: center;
}

.ag-sort-indicator-icon.ag-sort-ascending-icon > span,
.ag-sort-indicator-icon.ag-sort-descending-icon > span{
  position: relative;
}

.ag-sort-indicator-icon.ag-sort-ascending-icon > span:before,
.ag-sort-indicator-icon.ag-sort-descending-icon > span:before{
  position: absolute;
  top: 0;
  right: 0;
}
.ag-header-cell-label {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
