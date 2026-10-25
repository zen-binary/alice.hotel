<template>
  <PageContainer class="page-first">
    <div class="qa-header-title">{{ t("qna.title") }}</div>
  </PageContainer>
  <PageContainer>
    <QAList v-if="path === allowedPaths[0]"></QAList>
    <QAForm v-if="path === allowedPaths[1]"></QAForm>
    <QADetail v-if="path === allowedPaths[2]"></QADetail>
  </PageContainer>
</template>
<script lang="ts" setup>
import QADetail from "@/views/QA/QADetail.vue";
import QAForm from "@/views/QA/QAForm.vue";
import QAList from "@/views/QA/QAList.vue";
import { SCREEN } from "@/router/screen";
import { useI18n } from "vue-i18n";

// const store = commonStore();
const { t }= useI18n();
const route = useRoute();
const router = useRouter();
const allowedPaths = ["", "create", "detail"];
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
.qa-header-title {
  font-size: 40px;
  font-weight: 500;
  color: var(--gray--1d);
  @media screen and (max-width: 768px) {
    font-size: 26px;
  }
}
.qa-container {
  background: var(--white);
  box-shadow: 0px 2px 4px 0px var(--GrayOpacirty12);
  border-radius: 10px;
  padding: 40px 32px;
  @media screen and (max-width: 768px) {
    padding: 28px 16px;
  }
}
</style>
