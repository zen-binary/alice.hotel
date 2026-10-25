<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div class="page_toparea mt-10 mb-10">
    <h2 class="section_tit_lg">
      {{ pageTitle }}
    </h2>
    <ul class="navi_list" v-if="breadcrumbItems">
      <li v-for="(item, index) in breadcrumbItems" :key="index">
        <router-link :to="item.link">
          {{ item.label }}
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import { defineComponent, PropType } from "vue";
import { RouterLink } from "vue-router";

export default defineComponent({
  components: {
    RouterLink,
  },
  props: {
    pageTitle: {
      type: String,
      required: true,
    },
    breadcrumbItems: {
      type: Array as PropType<Array<{ label: string; link: string }>>,
      default: () => [],
    },
  },
});
</script>

<style scoped>
/* Add your component-specific styles here */
</style>
