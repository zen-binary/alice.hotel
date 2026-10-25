<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <aside class="left-side-bar">
    <div class="side-bar-container">
      <section class="menu">
        <aside class="logo">
          <router-link :to="{ name: SCREEN.home.name }">
            <img src="@/assets/images/icon_logo.svg" alt="logo"/>
          </router-link>
        </aside>
        <aside class="header_func">
          <div class="header_func_left">
            <label class="burger" for="burger">
              <input id="burger" v-model="checked" type="checkbox"/>
              <span/>
              <span/>
              <span/>
            </label>
          </div>
          <section class="header_func_right">
            <aside class="header_func_info">
              <!--              <IconUser color="#1f4575"/>-->
              <!--              <p>name</p>-->
            </aside>
            <aside class="header_func_main">
              <!--              <IconMonitor color="#1fccccc  ccc   c c4575"/>-->
              <p>({{ userInfo?.data?.userName }}) 담당자님 환영합니다.</p>
            </aside>
            <div></div>
            <aside class="header_func_logout" style="cursor: pointer" @click="handleLogout">
              <img src="@/assets/images/icon_logout.svg" alt="logout"/>
            </aside>
          </section>
        </aside>
      </section>
    </div>
  </aside>
</template>
<script setup lang="ts">
import {ref, watch} from "vue";
import {SCREEN} from "@/router/screen";
import commonService from "@/service/common/CommonService";
import {getUserInfo, removeUserInfo} from "@/utils/storage";
import {useRouter} from "vue-router";

const checked = ref(false);
const router = useRouter();
const userInfo = ref();

watch(checked, () => {
  const header_wrap = document.getElementsByClassName("header_wrapper")[0];
  checked.value ? header_wrap.classList.add("on") : header_wrap.classList.remove("on");
});

onMounted(()=>{
  userInfo.value = JSON.parse(getUserInfo() || "{}");
})

const handleLogout = async () => {
  await commonService.logout().then(async (response) => {
    if (response.status == 200) {
      await removeUserInfo();
      window.location.href = `${import.meta.env.VITE_DOMAIN_SITE_CLIENT}`;
    }
  });
};
</script>

<style scoped lang="scss">
.header_func_main {
  p {
    font-weight: 500;
    color: var(--error1);
  }
}
</style>
