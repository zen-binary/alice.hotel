<template>
  <PageContainer>
    <button type="button" class="button btn_md btn_white border_medium_gray bo_rd4 mg_r10" @click="moveMenu('Resource')">Resource</button>
    <button type="button" class="button btn_md btn_white border_medium_gray bo_rd4 mg_r10" @click="moveMenu('ResourceRole')">Role Resource</button>
    <button type="button" class="button btn_md btn_white border_medium_gray bo_rd4" @click="moveMenu('Menu')">Menu</button>
  </PageContainer>
</template>

<script setup lang="ts">
import { SCREEN } from "../router/screen";
import { useRouter } from "vue-router";

const router = useRouter();

const moveMenu = (name) => {
  let pathName = "";
  switch (name) {
    case "Resource":
      pathName = SCREEN.resourceManagement.name;
      break;
    case "Menu":
      pathName = SCREEN.pathMenu.name;
      break;
    case "ResourceRole":
      pathName = SCREEN.roleResourceManagement.name;
      break;
  }
  router.push({ name: pathName });
};
</script>
