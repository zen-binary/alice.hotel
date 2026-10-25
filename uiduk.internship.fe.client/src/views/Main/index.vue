<template>
  <MainIndex v-if="userInfo?.data?.userDiv === ROLE_STD"/>
  <ProfessorMain v-if="userInfo?.data?.userDiv === ROLE_STF"/>
</template>

<script setup lang="ts">

import MainIndex from "@/views/Main/MainIndex.vue";
import ProfessorMain from "@/views/Main/ProfessorMain.vue";
import {commonStore} from "@/stores/common";
import {getUserInfo} from "@/utils/storage";
import {ROLE_STD, ROLE_STF} from "@/constants/common.const";
import {onUnmounted} from "vue";

const store = commonStore()
const userInfo = JSON.parse(getUserInfo() || "{}")

onMounted(()=>{
  store.setShowBackground(true)
})

onUnmounted(()=>{
  store.setShowBackground(false)
})
</script>

<style scoped lang="scss">

</style>