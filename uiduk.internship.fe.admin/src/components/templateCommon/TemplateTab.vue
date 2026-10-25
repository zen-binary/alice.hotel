<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div class="box dp_block">
    <div class="box_section">
      <p class="box_title_sm">Tab</p>
      <div class="btn_group">
        <button class="button btn_white btn_md" :class="{ btn_active: tabActive === 1 }" @click="tabActive = 1">Tab 1</button>
        <button class="button btn_white btn_md" :class="{ btn_active: tabActive === 2 }" @click="tabActive = 2">Tab 2</button>
        <button class="button btn_white btn_md" :class="{ btn_active: tabActive === 3 }" @click="tabActive = 3">Tab 3</button>
      </div>
    </div>

    <div class="box_section">
      <p class="box_title_sm">Step progress</p>
      <div class="step_group">
        <ul>
          <li v-for="i in 5">
            <button class="button" :class="{ btn_active: stepActive === i }" @click="stepActive = i">
              {{ i }}
            </button>
          </li>
        </ul>
      </div>
    </div>

    <div class="box_section">
      <SelectorComponent v-model="loginForm.mode" :data="titleForm" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from "vue";
import SelectorComponent from "@/components/common/SelectorComponent.vue";
import globalCommon from "@/utils/global";

const tabActive = ref(1);
const stepActive = ref(1);

const loginForm = reactive({
  mode: 1,
  account: "",
  password: "",
  saveLogin: false,
});

const titleForm = reactive(["학생", "교원", "실습기관", "지원센터"]);
</script>

<style scoped></style>
