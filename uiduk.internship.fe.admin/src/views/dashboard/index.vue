<template>
  <IndexInternPlan v-if="checkAuth"/>
  <div v-else>
    <BeforeApproval v-if="!checkRole"/>
    <OperateInternList v-else/>
  </div>
</template>

<script setup lang="ts">
import {onMounted} from "vue"
import BeforeApproval from "@/views/BeforeApproval.vue";
import OperateInternList from "@/views/corporate/operateIntern/OperateInternList.vue";
import {getUserInfo} from "@/utils/storage";
import {IS_AGREE, ROLE_CORM} from "@/constants/common.const";
import IndexInternPlan from "@/views/indexInternPlan/IndexInternPlan.vue";

const checkAuth = ref(false)
const checkRole = ref(false)
const userInfo = JSON.parse(getUserInfo() || "{}")

onMounted(() => {
  checkAuth.value = userInfo?.data?.userDiv !== ROLE_CORM;
  checkRole.value = userInfo?.data?.userDiv === ROLE_CORM && userInfo?.data?.policy === IS_AGREE;
});
</script>

<style scoped lang="scss">

</style>