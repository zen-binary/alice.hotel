<template>
  <header class="nav_wrap hotel-header">
    <div class="hotel-bar">
      <router-link class="brand" :to="{ name: SCREEN.home.name }">
        <span class="mark">A</span>
        <span class="brand-text">
          <strong>Alice Hotel</strong>
          <em>Khách sạn</em>
        </span>
      </router-link>

      <nav class="links" aria-label="Điều hướng">
        <a :href="homeHref" :class="{ on: route.name === SCREEN.home.name && !route.hash }" @click.prevent="goHome()">Giới thiệu</a>
        <a :href="roomsHref" :class="{ on: route.hash === '#hang-phong' }" @click.prevent="goHome('#hang-phong')">Hạng phòng</a>
        <a :href="stayHref" :class="{ on: route.hash === '#luu-tru' }" @click.prevent="goHome('#luu-tru')">Lưu trú</a>
        <router-link :to="{ name: SCREEN.book.name }">Đặt phòng</router-link>
        <router-link :to="{ name: SCREEN.myBookings.name }">Đơn của tôi</router-link>
      </nav>

      <div class="account">
        <template v-if="userName">
          <span class="who">{{ userName }}</span>
          <button type="button" class="text-btn" @click="handleLogout">Đăng xuất</button>
        </template>
        <template v-else>
          <router-link class="text-btn" :to="{ name: SCREEN.login.name }">Đăng nhập</router-link>
          <router-link class="solid" to="/register">Đăng ký</router-link>
        </template>
      </div>
    </div>
  </header>
</template>

<script lang="ts" setup>
import { onMounted, ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { SCREEN } from "@/router/screen";
import commonService from "@/service/common/CommonService";
import { getUserInfo, removeUserInfo } from "@/utils/storage";

const router = useRouter();
const route = useRoute();
const userName = ref("");
const homeHref = router.resolve({ name: SCREEN.home.name }).href;
const roomsHref = router.resolve({ name: SCREEN.home.name, hash: "#hang-phong" }).href;
const stayHref = router.resolve({ name: SCREEN.home.name, hash: "#luu-tru" }).href;

const goHome = async (hash = "") => {
  if (route.name === SCREEN.home.name && route.hash === hash) {
    const el = hash ? document.querySelector(hash) : null;
    if (el) el.scrollIntoView({ behavior: "smooth", block: "start" });
    else window.scrollTo({ top: 0, behavior: "smooth" });
    return;
  }
  await router.push(hash ? { name: SCREEN.home.name, hash } : { name: SCREEN.home.name });
};

const readUser = () => {
  try {
    const raw = getUserInfo();
    if (!raw) {
      userName.value = "";
      return;
    }
    const info = JSON.parse(raw);
    userName.value = info?.data?.userName || info?.userName || "Khách";
  } catch {
    userName.value = "";
  }
};

onMounted(readUser);
watch(() => route.fullPath, readUser);

const handleLogout = async () => {
  await commonService.logout().catch(() => undefined);
  removeUserInfo();
  userName.value = "";
  await router.push({ name: SCREEN.home.name });
};
</script>

<style lang="scss">
.hotel-header.nav_wrap {
  height: 72px;
  border-bottom: 0;
  background: rgba(14, 36, 56, 0.96);
  backdrop-filter: blur(12px);
  box-shadow: 0 8px 24px rgba(14, 36, 56, 0.18);
}

.hotel-header .hotel-bar {
  height: 72px;
  max-width: 1180px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  align-items: center;
  gap: 28px;
}

.hotel-header .brand {
  display: inline-flex;
  align-items: center;
  gap: 10px;
  color: #f7f4ee;
  text-decoration: none;
  margin-right: auto;
}

.hotel-header .mark {
  width: 34px;
  height: 34px;
  border-radius: 10px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(145deg, #e7d3a1, #c4a35a);
  color: #0e2438;
  font-weight: 700;
}

.hotel-header .brand-text strong {
  display: block;
  font-size: 15px;
  font-weight: 700;
  line-height: 1.1;
  color: #f7f4ee;
}

.hotel-header .brand-text em {
  display: block;
  font-style: normal;
  font-size: 11px;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  color: rgba(247, 244, 238, 0.62);
}

.hotel-header .links {
  display: flex;
  align-items: center;
  gap: 4px;
  min-width: 0;
}

.hotel-header .links a {
  color: rgba(247, 244, 238, 0.82);
  text-decoration: none;
  font-size: 14px;
  font-weight: 600;
  padding: 8px 12px;
  border-radius: 999px;
  white-space: nowrap;
}

.hotel-header .links a.router-link-exact-active,
.hotel-header .links a.on,
.hotel-header .links a:hover {
  color: #fff;
  background: rgba(255, 255, 255, 0.08);
}

.hotel-header .account {
  display: flex;
  align-items: center;
  gap: 10px;
}

.hotel-header .who {
  color: rgba(247, 244, 238, 0.8);
  font-size: 13px;
  max-width: 140px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.hotel-header .text-btn {
  background: transparent;
  border: 1px solid rgba(255, 255, 255, 0.22);
  color: #f7f4ee;
  border-radius: 999px;
  padding: 8px 14px;
  font-size: 13px;
  font-weight: 600;
  text-decoration: none;
  cursor: pointer;
  white-space: nowrap;
}

.hotel-header .solid {
  background: #c4a35a;
  color: #0e2438;
  border-radius: 999px;
  padding: 8px 14px;
  font-size: 13px;
  font-weight: 700;
  text-decoration: none;
  white-space: nowrap;
}

@media (max-width: 1100px) {
  .hotel-header .links a:nth-child(2),
  .hotel-header .links a:nth-child(3) {
    display: none;
  }
}
@media (max-width: 760px) {
  .hotel-header .brand-text em,
  .hotel-header .who,
  .hotel-header .links a:nth-child(5) {
    display: none;
  }
  .hotel-header .hotel-bar {
    gap: 8px;
    padding: 0 12px;
  }
  .hotel-header .links a {
    padding: 8px;
  }
}
</style>
