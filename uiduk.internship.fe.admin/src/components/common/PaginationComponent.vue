<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div class="pagination" :class="{ pagination_bounded: isBounded }">
    <div class="pagination_wrap">
      <a
        v-if="paginationBoundary"
        href="javascript:void(0);"
        class="func first"
        :class="{ pagi_not_click: active === 1 }"
        data-content="First page"
        @click="backToStart"
      />
      <a href="javascript:void(0);" class="func back" :class="{ pagi_not_click: active === 1 }" data-content="Back page" @click="back" />
      <a v-for="i in paginationPage" :key="i">
        <p :class="{ active: i === active }" @click="toggle(i)">{{ i }}</p>
      </a>
      <a href="javascript:void(0);" class="func next" :class="{ pagi_not_click: active === pageEnd }" data-content="Next page" @click="next" />
      <a
        v-if="paginationBoundary"
        href="javascript:void(0);"
        class="func last"
        :class="{ pagi_not_click: active === pageEnd }"
        data-content="Last page"
        @click="nextToEnd"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { getCurrentInstance, ref, watch, computed } from "vue";

const props = defineProps({
  modelValue: {
    type: Number,
    required: true,
    default: 1,
  },
  totalRecord: {
    type: Number,
    required: true,
    default: 10,
  },
  pageSize: {
    type: Number,
    required: false,
    default: 10,
  },
  paginationBoundary: {
    type: Boolean,
    default: false,
  },
  isBounded: {
    type: Boolean,
    default: false,
  },
});
const { appContext } = getCurrentInstance();
const global = appContext.config.globalProperties.$global;

const emit = defineEmits(["update:modelValue"]);
const active = ref<number>(1);
const { containerClass } = global.useResponsive();

const paginationPage = computed(() => {
  const totalPage = Math.ceil(props.totalRecord / props.pageSize);
  const maxPages = containerClass.value !== "large" ? 5 : props.pageSize;

  if (totalPage <= 10) {
    return Array.from({ length: totalPage }, (_, i) => i + 1);
  }
  const index = Math.ceil(active.value / maxPages);

  const startPage = (index - 1) * maxPages + 1;
  let endPage = index * maxPages;
  const checkLength = Math.ceil(totalPage / maxPages);

  if (index === checkLength) {
    endPage = totalPage;
  }

  return Array.from({ length: endPage - startPage + 1 }, (_, index) => startPage + index);
});

const pageEnd = computed(() => {
  return Math.ceil(props.totalRecord / props.pageSize);
});

watch(
  () => props.modelValue,
  (newValue) => {
    active.value = newValue;
  },
);

watch(active, () => {
  emit("update:modelValue", active.value);
});

const toggle = (value: number) => {
  active.value = value;
};
const backToStart = () => {
  active.value = 1;
};
const back = () => {
  --active.value;
};
const next = () => {
  ++active.value;
};
const nextToEnd = () => {
  active.value = Math.ceil(props.totalRecord / props.pageSize);
};
</script>

<style scoped></style>
