<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <svg version="1.0" xmlns="http://www.w3.org/2000/svg"
      :width="width" :height="height" viewBox="0 0 96.000000 96.000000"
      preserveAspectRatio="xMidYMid meet">

    <g transform="translate(0.000000,96.000000) scale(0.100000,-0.100000)"
        :fill="fill" stroke="none">
      <path d="M160 880 c-17 -17 -20 -33 -20 -100 0 -96 16 -120 80 -120 l40 0 0
-225 0 -226 25 -24 c23 -24 30 -25 140 -25 l115 0 0 -30 c0 -56 27 -70 140
-70 132 0 140 7 140 120 0 113 -8 120 -140 120 -113 0 -140 -14 -140 -70 l0
-30 -108 0 c-71 0 -112 4 -120 12 -8 8 -12 52 -12 130 l0 118 120 0 120 0 0
-30 c0 -56 27 -70 140 -70 132 0 140 7 140 120 0 113 -8 120 -140 120 -113 0
-140 -14 -140 -70 l0 -30 -120 0 -120 0 0 80 0 80 40 0 c64 0 80 24 80 120 0
113 -8 120 -140 120 -87 0 -103 -3 -120 -20z m215 -100 l0 -75 -95 0 -95 0 -3
64 c-4 94 -7 92 100 89 l93 -3 0 -75z m400 -300 l0 -75 -95 0 -95 0 -3 64 c-4
94 -7 92 100 89 l93 -3 0 -75z m0 -300 l0 -75 -95 0 -95 0 -3 64 c-4 94 -7 92
100 89 l93 -3 0 -75z"/>
    </g>
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
      width: 21,
      fill: "#717171",
    },
);
</script>
<style scoped lang="scss"></style>
