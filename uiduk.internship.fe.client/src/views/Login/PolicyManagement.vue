<template>
  <PolicyConfirmation v-if="isStep === 'step1'" @stepAgree="handleStep" />
  <PolicyInfo v-else />
</template>

<script setup lang="ts">
import PolicyConfirmation from "@/views/Login/PolicyConfirmation.vue";
import PolicyInfo from "@/views/Login/PolicyInfo.vue";
import {commonStore} from "@/stores/common";
import {onBeforeMount} from "vue";

const store = commonStore()
const isStep = ref('step1')

onBeforeMount(() => {
  store.setShowMenu(true)
  store.setShowBackground(true)
})

function handleStep(data: any) {
  isStep.value = data;
}
</script>

<style scoped>
</style>