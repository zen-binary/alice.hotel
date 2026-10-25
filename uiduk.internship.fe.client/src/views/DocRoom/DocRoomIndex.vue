<template>
  <PageContainer class="page-first">
    <div class="sn-header-title">{{ t("docRoom.title") }}</div>
  </PageContainer>
  <PageContainer>
    <DocRoomList v-if="path === allowedPaths[0]" />
    <DocRoomDetail v-if="path === allowedPaths[1]" />
  </PageContainer>
</template>
<script lang="ts" setup>
import DocRoomDetail from "@/views/DocRoom/DocRoomDetail.vue";
import DocRoomList from "@/views/DocRoom/DocRoomList.vue";
import { useI18n } from "vue-i18n";

import { SCREEN } from "@/router/screen";

// const store = commonStore();
const { t } = useI18n();
const route = useRoute();
const router = useRouter();
const allowedPaths = ["", "detail"];
const path = ref<string>((route.params.path as string) || allowedPaths[0]);

onBeforeMount(() => {
  const pathParam = (route.params.path as String) || allowedPaths[0];
  const idParam = route.params.id;

  // Validate the path
  if (!allowedPaths.includes(pathParam)) {
    router.push(SCREEN.notFound.path);
    return;
  }
  path.value = pathParam;
});
watch(route, (newRoute: any) => {
  if (newRoute.params.path && !allowedPaths.includes(newRoute.params.path as string)) {
    router.push(SCREEN.notFound.path);
  } else {
    path.value = (newRoute.params.path as string) || allowedPaths[0];
  }
});
</script>
<style lang="scss">
.page_container {
  margin-top: 0 !important;
  padding-bottom: 40px;
  @media screen and (max-width: 768px) {
    padding-bottom: 16px;
  }
}
.sn-header-title {
  font-size: 40px;
  font-weight: 500;
  color: var(--gray--1d);
  @media screen and (max-width: 768px) {
    font-size: 26px;
  }
}
.sn-container {
  background: var(--white);
  box-shadow: 0px 2px 4px 0px var(--GrayOpacirty12);
  border-radius: 10px;
  padding: 40px 32px;
  @media screen and (max-width: 768px) {
    padding: 28px 16px;
  }
}
</style>
