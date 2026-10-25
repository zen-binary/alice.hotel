<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <header class="nav_wrap">
    <div class="header_wrap">
      <div class="header_content" :class="isCheck ? 'banner_white' : 'banner_transparent'">
        <div class="grid_content" :class="{ isLogin: showMenu }">
          <h1 class="header_logo">
            <router-link to="/">
              <img v-if="!isCheck" src="@/assets/images/icon_logo_white.svg" alt="logo" />
              <img v-else src="@/assets/images/icon_logo.svg" alt="logo" />
            </router-link>
          </h1>
          <div class="group_menu_wrap" v-if="!showMenu">
            <nav id="gnb_skip" class="gnb_wrap" tabindex="0">
              <ul class="gnb_1depth">
                <li v-for="i in listNav" @mouseenter="handleHoverDepth1(true)" @mouseleave="handleHoverDepth1(false)">
                  <a href="javascript:void(0);">menu {{ i }}</a>
                </li>
              </ul>
            </nav>
            <label class="burger" for="burger">
              <input id="burger" v-model="hiddenMenu" type="checkbox" />
              <span />
              <span />
              <span />
            </label>
            <button class="btn_logout" @click="handleRedirectLogin"><span>로그인</span></button>
          </div>
        </div>
      </div>
      <div class="gnb_submenu_horizontal" :class="{ on: hiddenMenu }" @mouseenter="handleHoverDepth1(true)" @mouseleave="handleHoverDepth1(false)">
        <div v-for="i in listNav" id="sub2" class="gnb_2depth">
          <div class="grid_content">
            <strong class="gnb_tit">Menu {{ i }}</strong>
            <ul>
              <li v-for="i in 2">
                <a href="javascript:void(0);">Dep2 {{ i }}</a>
                <ul class="gnb_3depth">
                  <li v-for="i in 2">
                    <a href="javascript:void(0);">Dep3 {{ i }}</a>
                  </li>
                </ul>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <div class="header_wrap_mobile" :class="{ on: hiddenMenu }">
      <div class="header_container">
        <div class="lnb_submenu_horizontal">
          <div v-for="(i, index) in listNav" id="sub2" class="lnb_depth" :key="index">
            <div class="grid_content">
              <input :id="`lnb_menu_${index}`" type="checkbox" class="lnb_checkbox" />
              <label :for="`lnb_menu_${index}`" class="lnb_tit">Menu {{ i }}</label>
              <ul class="lnb_1depth">
                <li v-for="i in 10">
                  <a href="javascript:void(0);">Dep2 {{ i }}</a>
                  <!--                  <ul class="lnb_2depth">-->
                  <!--                    <li v-for="i in 2">-->
                  <!--                      <a href="javascript:void(0);">Dep3 {{ i }}</a>-->
                  <!--                    </li>-->
                  <!--                  </ul>-->
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script lang="ts" setup>
import { computed, ref } from "vue";
import globalCommon from "@/utils/global";
import { commonStore } from "@/stores/common";
import { useRouter } from "vue-router";
import { SCREEN } from "@/router/screen";

const props = defineProps({
  isLogin: {
    type: Boolean,
    default: false,
  },
  isBackground: {
    type: Boolean,
    default: false,
  },
});

const router = useRouter();

const store = commonStore();
const hiddenMenu = ref(false);
// const hiddenMenu = ref(true);

const listNav = ref(4);
const activeLnbMenu = ref(0);

const handleHoverDepth1 = (value: boolean) => {
  hiddenMenu.value = value;
};

const showMenu = ref<boolean>(store.isShowMenu || props.isLogin);
const showBackground = ref<boolean>(props.isBackground || store.isShowBackground);

watch(
  () => store.isShowMenu,
  (value) => {
    showMenu.value = value;
  },
);

const isCheck = computed(() => {
  //Add conditions if needed, (for example only show on dashboard)
  // return (isScroll.value || hiddenMenu.value || containerClass.value !== 'large') && !props.showMenu
  return isScroll.value || hiddenMenu.value || containerClass.value !== "large" || showBackground.value;
});

const { windowWidth, containerClass } = globalCommon.useResponsive();
const { isScroll } = globalCommon.useScroll(1);

const handleRedirectLogin = () => {
  router.push({ name: SCREEN.login.name });
};
</script>
