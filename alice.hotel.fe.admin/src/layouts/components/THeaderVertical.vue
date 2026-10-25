<template>
  <header id="header" class="header_wrapper pms_nav">
    <div class="header_container">
      <div class="pms_user">
        <img :src="intro.avatar" alt="" @error="handleErrorAvatar" />
        <div class="pms_user_meta">
          <strong>{{ userInfo?.data?.userName || "Admin" }}</strong>
          <span>{{ roleLabel }}</span>
        </div>
      </div>

      <nav class="pms_menu" aria-label="Menu chính">
        <div
          v-for="(submenu, index) in submenus"
          :key="submenu?.id"
          class="pms_group"
          :class="{
            open: isOpen(index, submenu),
            active: hasActiveChild(submenu) || isLeafActive(submenu),
          }"
        >
          <button
            type="button"
            class="pms_group_btn"
            @click="onRootMenuClick(submenu, index)"
          >
            <span class="pms_ico" v-html="groupIcon(submenu.name)" />
            <span class="pms_label">{{ submenu.name }}</span>
            <span
              v-if="hasChildren(submenu)"
              class="pms_chev"
              :class="{ open: isOpen(index, submenu) }"
            />
          </button>

          <ul v-if="hasChildren(submenu)" class="pms_children">
            <li v-for="(item, itemIndex) in submenu.subMenus" :key="itemIndex">
              <a
                href="javascript:void(0);"
                :class="{ on: isChildActive(item) }"
                @click="handleNextScreen(item?.screenName)"
              >
                {{ item.name }}
              </a>
            </li>
          </ul>
        </div>

        <p v-if="!submenus.length && !menuError" class="menu_empty">Đang tải menu…</p>
        <p v-if="menuError" class="menu_empty error">{{ menuError }}</p>
      </nav>
    </div>

    <div class="header_footer">
      <aside class="header_func_logout" style="cursor: pointer" @click="handleLogout">
        <img src="@/assets/images/icon_logout.svg" alt="logout" />
        <p>Đăng xuất</p>
      </aside>
    </div>
  </header>
</template>

<script lang="ts" setup>
import { computed, onMounted, ref, watch } from "vue";
import { useRouter, useRoute } from "vue-router";
import { SCREEN } from "@/router/screen";
import { useFetchData } from "@/utils/useFetchData";
import { SUCCSESS_STATUS } from "@/constants/screen.const";
import { END_POINTS_HEADER_MENU } from "@/stores/common/commonServices/commonServices.service";
import { getUserInfo, removeUserInfo } from "@/utils/storage";
import { IS_AGREE, ROLE_CORM } from "@/constants/common.const";
import { commonStore } from "@/stores/common";
import commonService from "@/service/common/CommonService";

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

const ICONS: Record<string, string> = {
  home: `<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8"><path d="M3 10.5 12 3l9 7.5V20a1 1 0 0 1-1 1h-5v-6H9v6H4a1 1 0 0 1-1-1v-9.5z"/></svg>`,
  ops: `<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8"><rect x="3" y="4" width="18" height="16" rx="2"/><path d="M3 9h18M8 4v16"/></svg>`,
  rooms: `<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8"><path d="M4 20V8l8-4 8 4v12"/><path d="M9 20v-6h6v6"/><path d="M4 12h16"/></svg>`,
  sales: `<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8"><path d="M6 7h12l-1 11H7L6 7z"/><path d="M9 7V5a3 3 0 0 1 6 0v2"/></svg>`,
  report: `<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8"><path d="M4 19V5"/><path d="M4 19h16"/><path d="M8 16v-5"/><path d="M12 16V8"/><path d="M16 16v-3"/></svg>`,
  config: `<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8"><circle cx="12" cy="12" r="3"/><path d="M12 2v2M12 20v2M4.9 4.9l1.4 1.4M17.7 17.7l1.4 1.4M2 12h2M20 12h2M4.9 19.1l1.4-1.4M17.7 6.3l1.4-1.4"/></svg>`,
  guests: `<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8"><circle cx="9" cy="8" r="3"/><path d="M3 19c.6-3 2.8-4.5 6-4.5S14.4 16 15 19"/><circle cx="17" cy="9" r="2.2"/><path d="M17 14.5c2.2.3 3.6 1.5 4.2 3.5"/></svg>`,
  system: `<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8"><path d="M12 3 5 6v6c0 4.2 2.8 7.2 7 8 4.2-.8 7-3.8 7-8V6l-7-3z"/></svg>`,
  default: `<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8"><circle cx="12" cy="12" r="8"/></svg>`,
};

const router = useRouter();
const route = useRoute();
const store = commonStore();

const intro = ref({ avatar: "" });
const submenus = ref<ISubMenus[]>([]);
const isShowDep2 = ref<IShow[]>([]);
const userInfo = ref<any>();
const menuError = ref("");

const roleLabel = computed(() => {
  const d = userInfo.value?.data || {};
  const roles = String(d.userRoles || "")
    .split(",")
    .map((s: string) => s.trim());
  if (roles.includes("1")) return "Quản trị viên";
  if (roles.includes("3")) return "Lễ tân";
  if (d.roleName && String(d.roleName) !== "null") return String(d.roleName);
  if (d.userDivName) return String(d.userDivName);
  return "Alice PMS";
});

onMounted(async () => {
  userInfo.value = JSON.parse(getUserInfo() || "{}");
  await getMenu();
});

watch(
  () => route.name,
  () => {
    // Khi đổi trang: mở nhóm chứa mục đang active (vẫn cho phép user thu gọn sau đó)
    if (!isShowDep2.value.length) return;
    isShowDep2.value = submenus.value.map((m) => ({ show: hasActiveChild(m) }));
  },
);

const handleErrorAvatar = () => {
  intro.value.avatar = "/images/error_avatar.svg";
};

const getMenu = async () => {
  menuError.value = "";
  const { dataFetch, statusFetch, error } = await useFetchData<any>(`${END_POINTS_HEADER_MENU}`, {
    method: "POST",
  });
  if (statusFetch.value === SUCCSESS_STATUS) {
    const list = dataFetch.value?.data || [];
    submenus.value = Array.isArray(list) ? list : [];
    // Expand only the group that contains the current route
    isShowDep2.value = submenus.value.map((m) => ({ show: hasActiveChild(m) }));
    if (!submenus.value.length) {
      menuError.value = "Không có menu. Thử đăng nhập lại.";
    }
  } else {
    menuError.value = error.value || "Không tải được menu";
  }
};

const hasChildren = (submenu: ISubMenus) => Array.isArray(submenu?.subMenus) && submenu.subMenus.length > 0;

const hasActiveChild = (submenu: ISubMenus) => {
  const kids = submenu?.subMenus || [];
  return kids.some((item) => isChildActive(item));
};

const isChildActive = (item: ISubMenus) =>
  route.name === item?.screenName || route.meta.parent === item?.screenName;

const isLeafActive = (submenu: ISubMenus) =>
  !hasChildren(submenu) && !!submenu.screenName && route.name === submenu.screenName;

/** Chỉ theo state mở/đóng — không khóa bởi trang con đang active */
const isOpen = (index: number, _submenu?: ISubMenus) => !!isShowDep2.value[index]?.show;

const groupIcon = (name: string) => {
  const n = (name || "")
    .toLowerCase()
    .normalize("NFD")
    .replace(/\p{M}/gu, "");
  if (n.includes("trang") || n.includes("home")) return ICONS.home;
  if (n.includes("van hanh") || n.includes("front") || n.includes("ops")) return ICONS.ops;
  if (n.includes("kho") || n.includes("phong") || n.includes("room")) return ICONS.rooms;
  if (n.includes("ban hang") || n.includes("sale") || n.includes("dich vu") || n.includes("voucher")) return ICONS.sales;
  if (n.includes("bao cao") || n.includes("report") || n.includes("dashboard")) return ICONS.report;
  if (n.includes("cau hinh") || n.includes("config") || n.includes("setting")) return ICONS.config;
  if (n.includes("khach")) return ICONS.guests;
  if (n.includes("he thong") || n.includes("phan quyen")) return ICONS.system;
  return ICONS.default;
};

const onRootMenuClick = (submenu: ISubMenus, index: number) => {
  if (!hasChildren(submenu) && submenu?.screenName) {
    handleNextScreen(submenu.screenName);
    return;
  }
  const currentlyOpen = !!isShowDep2.value[index]?.show;
  const willOpen = !currentlyOpen;
  // Accordion: mở nhóm này / đóng các nhóm khác; cho phép thu gọn cả khi đang đứng ở trang con
  isShowDep2.value = submenus.value.map((_, i) => ({
    show: i === index ? willOpen : false,
  }));
};

const handleNextScreen = (screenName: string | null) => {
  if (userInfo.value?.data?.userDiv === ROLE_CORM && userInfo.value?.data?.policy !== IS_AGREE) {
    return store.setCheckRole(true);
  }
  if (!screenName) return;
  const screen = (SCREEN as Record<string, { path: string; name: string }>)[screenName];
  if (screen?.name) {
    return router.push({ name: screen.name }).catch((err) => {
      if (err?.name === "NavigationDuplicated") return;
      if (screen.path && !screen.path.includes(":")) {
        return router.push({ path: screen.path }).catch(() => undefined);
      }
    });
  }
};

const handleLogout = async () => {
  await commonService.logout().catch(() => undefined);
  await removeUserInfo();
  await router.push({ name: SCREEN.login.name });
};
</script>

<style scoped lang="scss">
.pms_nav {
  background: #0e2438;
}

.header_footer,
.header_func_logout p {
  color: rgba(247, 244, 238, 0.82);
}

.pms_user {
  display: flex;
  align-items: center;
  gap: 10px;
  margin: 4px 12px 14px;
  padding: 10px 12px;
  background: rgba(255, 255, 255, 0.06);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 10px;
}

.pms_user img {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  object-fit: cover;
  background: rgba(255, 255, 255, 0.12);
  flex: 0 0 36px;
}

.pms_user_meta {
  min-width: 0;
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.pms_user_meta strong {
  font-size: 13px;
  font-weight: 700;
  color: #f7f4ee;
  line-height: 1.2;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.pms_user_meta span {
  font-size: 11px;
  color: rgba(247, 244, 238, 0.55);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.pms_menu {
  display: flex;
  flex-direction: column;
  gap: 2px;
  padding: 0 10px 16px;
}

.pms_group_btn {
  width: 100%;
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 9px 10px;
  border: 0;
  background: transparent;
  border-radius: 8px;
  cursor: pointer;
  text-align: left;
  color: rgba(247, 244, 238, 0.82);
  transition: background 0.12s ease, color 0.12s ease;
}

.pms_group_btn:hover {
  background: rgba(255, 255, 255, 0.06);
  color: #fff;
}

.pms_group.active > .pms_group_btn,
.pms_group.open > .pms_group_btn {
  color: #fff;
  background: rgba(255, 255, 255, 0.06);
  font-weight: 600;
}

.pms_ico {
  width: 18px;
  height: 18px;
  flex: 0 0 18px;
  display: inline-flex;
  color: inherit;
}

.pms_ico :deep(svg) {
  width: 18px;
  height: 18px;
  display: block;
}

.pms_label {
  flex: 1;
  font-size: 13px;
  font-weight: 600;
  line-height: 1.25;
  min-width: 0;
}

.pms_chev {
  width: 8px;
  height: 8px;
  border-right: 1.5px solid currentColor;
  border-bottom: 1.5px solid currentColor;
  transform: rotate(-45deg);
  opacity: 0.55;
  transition: transform 0.15s ease;
  flex: 0 0 auto;
  margin-right: 4px;
}

.pms_chev.open {
  transform: rotate(45deg);
  opacity: 0.85;
}

.pms_children {
  list-style: none;
  margin: 0 0 6px;
  padding: 2px 0 4px 38px;
  display: none;
  flex-direction: column;
  gap: 2px;
}

.pms_group.open > .pms_children {
  display: flex;
}

.pms_children a {
  display: block;
  padding: 7px 10px;
  border-radius: 7px;
  font-size: 13px;
  font-weight: 500;
  color: rgba(247, 244, 238, 0.68);
  text-decoration: none;
  line-height: 1.3;
  transition: background 0.12s ease, color 0.12s ease;
}

.pms_children a:hover {
  background: rgba(255, 255, 255, 0.06);
  color: #fff;
}

.pms_children a.on {
  background: rgba(0, 152, 229, 0.22);
  color: #fff;
  font-weight: 700;
}

.menu_empty {
  padding: 12px 8px;
  font-size: 13px;
  color: rgba(247, 244, 238, 0.55);
}

.menu_empty.error {
  color: #b42318;
}
</style>
