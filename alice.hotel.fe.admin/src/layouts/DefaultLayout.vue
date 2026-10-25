<template>
  <div id="page_wrapper" class="page_wrapper" :class="{ vertical: isVertical }">
    <THeaderVertical v-if="isVertical" />
    <THeaderHorizontal v-else />

    <main id="top">
      <!-- Top bar is fixed; keep out of flex sizing via CSS -->
      <LeftSideBar />
      <div class="main-container pms-shell">
        <router-view v-slot="{ Component, route }">
          <transition
            :enter-active-class="`animate__faster animate__animated animate__fadeIn`"
            :leave-active-class="`animate__faster animate__animated animate__fadeOut`"
            mode="out-in"
          >
            <div :key="route.name" class="pms-page">
              <component :is="Component" />
            </div>
          </transition>
        </router-view>
      </div>
      <RightSideBar />
    </main>

    <TFooter />
  </div>

  <Loading v-if="cmStore.loading" />
</template>

<script setup lang="ts">
import TFooter from "@/layouts/components/TFooter.vue";
import LeftSideBar from "@/layouts/components/LeftSideBar.vue";
import RightSideBar from "@/layouts/components/RightSideBar.vue";
import { onMounted, ref } from "vue";
import THeaderHorizontal from "@/layouts/components/THeaderHorizontal.vue";
import THeaderVertical from "@/layouts/components/THeaderVertical.vue";
import { useRoute } from "vue-router";
import Loading from "@/components/common/LoadingComponent.vue";
import { commonStore } from "@/stores/common";

const cmStore = commonStore();
const route = useRoute();
const isVertical = ref(true);

onMounted(() => {
  if (route.path.includes("horizontal")) {
    isVertical.value = false;
  }
});
</script>

<style scoped lang="scss">
.pms-shell {
  background: #f3f6f9;
  min-height: calc(100vh - 64px);
  width: 100%;
}
.pms-page {
  width: 100%;
}
</style>
