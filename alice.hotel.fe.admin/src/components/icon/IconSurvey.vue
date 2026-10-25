<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <svg xmlns="http://www.w3.org/2000/svg" :width="width" :height="height" viewBox="0 0 24 24" fill="none">
    <path
      d="M6.16667 3H14.8333C15.408 3 15.9591 3.24082 16.3654 3.66947C16.7717 4.09812 17 4.67951 17 5.28571V16.7143C17 17.3205 16.7717 17.9019 16.3654 18.3305C15.9591 18.7592 15.408 19 14.8333 19H6.16667C5.59203 19 5.04093 18.7592 4.6346 18.3305C4.22827 17.9019 4 17.3205 4 16.7143V5.28571C4 4.67951 4.22827 4.09812 4.6346 3.66947C5.04093 3.24082 5.59203 3 6.16667 3Z"
      :stroke="fill"
      stroke-width="1.5"
      stroke-linecap="round"
      stroke-linejoin="round"
    />
    <path
      d="M18 5C18.6188 5 18.879 5.25585 19.3166 5.7113C19.7542 6.16675 20 6.78446 20 7.42856V19.5714C20 20.2155 19.7542 20.8332 19.3166 21.2887C18.879 21.7441 18.2855 22 17.6667 22H8.33333C7.71449 22 7.121 21.7441 6.68342 21.2887C6.24583 20.8332 6 20.6441 6 20"
      :stroke="fill"
      stroke-width="1.5"
      stroke-linecap="round"
      stroke-linejoin="round"
    />
    <path d="M13 10L10 13L9 12" :stroke="fill" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round" />
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
    height: 24,
    width: 24,
    fill: "#2D2D2D",
  },
);
</script>
