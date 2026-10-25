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
    height: 20,
    width: 20,
    fill: "#0098E6",
  },
);
</script>
<template>
  <svg xmlns="http://www.w3.org/2000/svg" :width="width" :height="height" viewBox="0 0 20 20" fill="none">
    <path
      d="M0 4C0 1.79086 1.79086 0 4 0H16C18.2091 0 20 1.79086 20 4V16C20 18.2091 18.2091 20 16 20H4C1.79086 20 0 18.2091 0 16V4Z"
      :fill="fill"
      fill-opacity="0.08"
    />
    <path
      d="M6.60352 14.446V5.55396H8.41552L10.9955 10.294L11.8835 12.214H11.9435C11.8595 11.29 11.7155 10.114 11.7155 9.10596V5.55396H13.3955V14.446H11.5955L9.02752 9.69395L8.12752 7.78596H8.06752C8.15152 8.74596 8.28352 9.86196 8.28352 10.882V14.446H6.60352Z"
      :fill="fill"
    />
  </svg>
</template>
