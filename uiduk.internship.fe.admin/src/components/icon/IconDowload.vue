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
    height: 14,
    width: 13,
    fill: "#BEBEBE",
  },
);
</script>
<template>
  <svg xmlns="http://www.w3.org/2000/svg" :width="width" :height="height" viewBox="0 0 14 13" fill="none">
    <path
      fill-rule="evenodd"
      clip-rule="evenodd"
      d="M6.32258 8.29678V0H7.67742V8.29678L10.5445 5.37303L11.5025 6.35L7 10.9415L2.49753 6.35L3.45555 5.37303L6.32258 8.29678ZM0 8.79227H1.35484V11.6184H12.6452V8.79227H14V13H0V8.79227Z"
      :fill="fill"
    />
  </svg>
</template>
