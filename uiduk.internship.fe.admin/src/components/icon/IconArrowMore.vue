<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <svg xmlns="http://www.w3.org/2000/svg" :width="props.width" :height="props.height" viewBox="0 0 20 20" fill="none">
    <path d="M5.33333 14.9998L13.3333 6.99984V14.1665H15V4.1665H5V5.83317H12.1667L4.16667 13.8332L5.33333 14.9998Z" :fill="props.fill" />
  </svg>
</template>
<script lang="ts" setup>
const props = withDefaults(
  defineProps<{
    width?: number;
    height?: number;
    fill?: string;
  }>(),
  {
    height: 20,
    width: 20,
    fill: "#1D1D1D",
  },
);
</script>
<style scoped lang="scss"></style>
