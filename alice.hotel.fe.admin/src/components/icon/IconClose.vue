<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<script lang="ts" setup>
const props = withDefaults(
  defineProps<{
    width?: number;
    height?: number;
    fill?: string;
  }>(),
  {
    height: 10,
    width: 10,
    fill: "#717171",
  },
);
</script>
<template>
  <svg xmlns="http://www.w3.org/2000/svg" :width="width" :height="height" viewBox="0 0 10 10" fill="none">
    <path
      d="M8.60007 0.199951L4.99922 3.79995L1.39922 0.199951L0.199219 1.39995L3.79922 4.99995L0.199219 8.59995L1.39922 9.79995L4.99922 6.19995L8.60007 9.79995L9.80007 8.59995L6.20007 4.99995L9.80007 1.39995L8.60007 0.199951Z"
      :fill="fill"
    />
  </svg>
</template>
