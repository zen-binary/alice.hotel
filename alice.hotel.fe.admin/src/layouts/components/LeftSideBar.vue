<template>
  <aside class="left-side-bar">
    <div class="side-bar-container">
      <section class="menu">
        <aside class="logo">
          <router-link :to="{ name: SCREEN.home.name }" class="top_brand">
            <span class="mark">A</span>
            <span class="txt">
              <strong>Alice Hotel</strong>
              <em>Quản trị</em>
            </span>
          </router-link>
        </aside>
        <aside class="header_func">
          <div class="header_func_left">
            <label class="burger" for="burger">
              <input id="burger" v-model="checked" type="checkbox" />
              <span />
              <span />
              <span />
            </label>
          </div>
          <section class="header_func_right">
            <aside class="header_func_main">
              <p>Xin chào, {{ userInfo?.data?.userName || "Quản trị viên" }}</p>
            </aside>
            <div class="sep" />
            <aside class="header_func_logout" style="cursor: pointer" @click="handleLogout">
              <img src="@/assets/images/icon_logout.svg" alt="logout" />
            </aside>
          </section>
        </aside>
      </section>
    </div>
  </aside>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from "vue";
import { SCREEN } from "@/router/screen";
import commonService from "@/service/common/CommonService";
import { getUserInfo, removeUserInfo } from "@/utils/storage";
import { useRouter } from "vue-router";

const checked = ref(false);
const router = useRouter();
const userInfo = ref<any>();

watch(checked, () => {
  const header_wrap = document.getElementsByClassName("header_wrapper")[0];
  if (!header_wrap) return;
  checked.value ? header_wrap.classList.add("on") : header_wrap.classList.remove("on");
});

onMounted(() => {
  userInfo.value = JSON.parse(getUserInfo() || "{}");
});

const handleLogout = async () => {
  await commonService.logout().catch(() => undefined);
  await removeUserInfo();
  await router.push({ name: SCREEN.login.name });
};
</script>

<style scoped lang="scss">
.top_brand {
  display: inline-flex;
  align-items: center;
  gap: 10px;
  text-decoration: none;
  color: inherit;
}
.mark {
  width: 32px;
  height: 32px;
  border-radius: 8px;
  background: linear-gradient(145deg, #e7d3a1, #c4a35a);
  color: #0e2438;
  font-weight: 700;
  font-size: 14px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
}
.txt strong {
  display: block;
  font-size: 14px;
  color: #f7f4ee;
  line-height: 1.15;
  font-weight: 700;
}
.txt em {
  display: block;
  font-style: normal;
  font-size: 11px;
  color: rgba(247, 244, 238, 0.62);
}
.header_func_main p {
  font-weight: 500;
  color: #0f1c24;
  margin: 0;
  font-size: 13px;
}
.sep {
  width: 1px;
  align-self: stretch;
  background: #e4ecf2;
}
</style>
