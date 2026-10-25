<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div class="page_container page-first" v-bind="attrs">
    <div>
      <slot />
    </div>
  </div>
</template>

<script setup lang="ts">
import { useAttrs } from "vue";

defineOptions({
  inheritAttrs: false,
});

const attrs = useAttrs();
</script>

<style scoped lang="scss"></style>
