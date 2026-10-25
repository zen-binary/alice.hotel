<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div class="box dp_block">
    <div class="box_section">
      <p class="box_title_sm required">Pagination</p>
      <p class="box_title_sm required">Current page: {{ paginationValue }}</p>
      <Pagination v-model="paginationValue" :total-record="totalRecord" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import Pagination from "@/components/common/PaginationComponent.vue";

//Pagination
const paginationValue = ref<number>(1);
const totalRecord = 200;
</script>

<style scoped></style>
