<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <header id="header" class="header_wrapper">
    <div class="header_container">
      <div class="header_intro">
        <img :src="intro.avatar" alt="avatar" @error="handleErrorAvatar"/>
        <p>{{userInfo?.data?.userName}}</p>
        <span>{{userInfo?.data?.lastLoginDate}}</span>
      </div>
      <div class="gnb_submenu_container">
        <div class="gnb_submenu_vertical">
        <div v-for="(submenu, index) in submenus" :key="submenu?.id" class="gnb_2depth">
          <div class="grid_content" :class="{ on: isShowDep2[index]?.show }">
            <div>
              <IconTreeStructure :width="24" :height="24" :fill="isShowDep2[index]?.show ? '#1c54a2' : '#717171'" />
              <strong class="gnb_tit" style="cursor: pointer" @click="handleOpenDep2(index, submenus)">
                {{ submenu.name }}
              </strong>
            </div>
            <ul>
              <li v-for="(item, itemIndex) in submenu?.subMenus" :key="itemIndex">
                <a
                  v-if="item.subMenus"
                  class="isMenuDep3"
                  :class="{ on: isShowDep3[itemIndex]?.show }"
                  href="javascript:void(0);"
                  @click="handleOpenDep3(itemIndex, submenu.subMenus)"
                >
                  {{ item.name }}
                </a>
                <a v-else href="javascript:void(0);" :class="{router_link_exact_active:route.name === item?.screenName || route.meta.parent === item?.screenName}" @click="handleNextScreen(item?.screenName)">{{ item.name }}</a>
                <ul v-if="item.subMenus" class="gnb_3depth" :class="{ on: isShowDep3[itemIndex]?.show }">
                  <li v-for="(subItem, subItemIndex) in item.subMenus" :key="subItemIndex">
                    <a href="javascript:void(0);" :class="{router_link_exact_active:route.name === subItem?.screenName}" @click="handleNextScreen(subItem?.screenName)">{{ subItem.name }}</a>
                  </li>
                </ul>
              </li>
            </ul>
          </div>
        </div>
      </div>
      </div>
    </div>

    <div class="header_footer">
      <aside class="header_func_logout" style="cursor: pointer" @click="handleLogout">
        <img src="@/assets/images/icon_logout.svg" alt="logout"/>
        <p>Logout</p>
      </aside>
    </div>
  </header>
  <!--  <Loading v-if="isLoading"></Loading>-->
</template>

<script lang="ts" setup>
import { useRouter, useRoute } from "vue-router";
import { onMounted, onUpdated, getCurrentInstance, ref, watch } from "vue";
import data from "@/data/mockHeader.json";
import commonService from "@/service/common/CommonService";
import ChangeTheme from "@/layouts/components/ChangeTheme.vue";
import { SCREEN } from "@/router/screen";
import { useI18n } from "vue-i18n";
import IconTreeStructure from "@/components/icon/IconTreeStructure.vue";
import { useFetchData } from "@/utils/useFetchData";
import { PostType } from "@/stores/common/demoFetchData/demo.type";
import { SUCCSESS_STATUS } from "@/constants/screen.const";
import { END_POINTS_HEADER_MENU } from "@/stores/common/commonServices/commonServices.service";
import {getUserInfo, removeUserInfo} from "@/utils/storage";
import {IS_AGREE, ROLE_CORM} from "@/constants/common.const";
import {commonStore} from "@/stores/common";

interface IShow {
  show: boolean;
}

interface ISubMenus {
  id: number;
  name: string;
  parentId: null | number;
  screenName: null | string;
  route: null | string;
  displayOrder: number;
  treeLevel: number;
  subMenus: ISubMenus[] | null;
}

const { appContext } = getCurrentInstance();
const global = appContext.config.globalProperties.$global;

const { t } = useI18n();
const router = useRouter();
const route = useRoute();
const store = commonStore()

const intro = ref({
  avatar: "",
});
const submenus = ref<ISubMenus[]>([]);
const isShowDep2 = ref<IShow[]>([]);
const isShowDep3 = ref<IShow[]>([]);
const isLoading = ref(false);
const isTemplate = ref(false);
const userInfo = ref()

onMounted(async () => {
  userInfo.value = JSON.parse(getUserInfo() || "{}");
  //call data
  await getMenu();

  //Mock Data
  // submenus.value = data;

  submenus.value.map((_) => {
    isShowDep2.value.push({ show: false });
  });

  setTimeout(() => {
    isLoading.value = false;
  }, 2000);
});

onUpdated(() => {
  if (route.name === SCREEN.template.name) {
    isTemplate.value = true;
  }
});

watch(route, () => {
  const inputCheck = document.getElementById("burger");
  const header = document.getElementById("header");
  inputCheck.checked = false;
  header?.classList.remove("on");
});

const handleErrorAvatar = () => {
  return (intro.value.avatar = "/images/error_avatar.svg");
};
//cal data
const getMenu = async () => {
  const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(`${END_POINTS_HEADER_MENU}`, {
    method: "POST",
  });
  if (statusFetch.value === SUCCSESS_STATUS) {
    submenus.value = dataFetch.value.data;
  }
};

//handle menu
const handleOpenDep2 = (index: number, menu: ISubMenus[]) => {
  if(!isShowDep2.value[index].show) {
    isShowDep2.value = [];
    isShowDep3.value = [];
    menu.map((_: ISubMenus) => {
      isShowDep2.value.push({show: false});
    });
  }
  isShowDep2.value[index].show = !isShowDep2.value[index].show;
};
const handleOpenDep3 = (index: number, menu: ISubMenus[]) => {
  if (isShowDep3.value[index]?.show) {
    return (isShowDep3.value[index].show = !isShowDep3.value[index].show);
  }

  isShowDep3.value = [];
  menu.map((_) => {
    isShowDep3.value.push({ show: false });
  });

  isShowDep3.value[index].show = !isShowDep3.value[index].show;
};
const handleNextScreen = (screenName: string | null) => {
  if (isTemplate.value) return (window.location.href = screenName);

  if (userInfo.value?.data?.userDiv === ROLE_CORM && userInfo.value?.data?.policy !== IS_AGREE) {
    return store.setCheckRole(true)
  }

  if (screenName && SCREEN[screenName] && SCREEN[screenName].path) {
    return router.push({ path: SCREEN[screenName].path });
  }
};

function setLocale(locale: "vi" | "ko" | "en") {
  global.setLocale(locale);
}

const handleLogout = async () => {
  await commonService.logout();
  await removeUserInfo()
  window.location.href = `${import.meta.env.VITE_DOMAIN_SITE_CLIENT}`;
};
</script>
