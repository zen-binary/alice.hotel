<template>
  <PageContainer class="home_page">
    <div class="hero box dp_block">
      <div class="box_section">
        <p class="kicker">Alice Hotel · Quản lý khách sạn</p>
        <h1>Xin chào, {{ displayName }}</h1>
        <p class="lead">Quản lý lễ tân, phòng, giá và doanh thu trong một console.</p>
        <div class="btn_group">
          <button type="button" class="button btn_md btn_primary" @click="go('frontDesk')">Mở Lễ tân</button>
          <button type="button" class="button btn_md btn_lightgray" @click="go('bookingAdmin')">Đặt phòng</button>
          <button type="button" class="button btn_md btn_lightgray" @click="go('dashboard')">Báo cáo</button>
        </div>
      </div>
    </div>

    <div class="quick_grid">
      <button v-for="item in shortcuts" :key="item.name" type="button" class="box dp_block card" @click="go(item.name)">
        <div class="box_section">
          <span class="tag">{{ item.group }}</span>
          <strong>{{ item.title }}</strong>
          <p>{{ item.desc }}</p>
        </div>
      </button>
    </div>
  </PageContainer>
</template>

<script setup lang="ts">
import { computed, onBeforeMount } from "vue";
import { useRouter } from "vue-router";
import PageContainer from "@/components/common/PageContainer.vue";
import { commonStore } from "@/stores/common";
import { getUserInfo } from "@/utils/storage";
import { SCREEN } from "@/router/screen";

const store = commonStore();
const router = useRouter();

onBeforeMount(() => {
  store.setShowMenu(true);
});

const displayName = computed(() => {
  try {
    const info = JSON.parse(getUserInfo() || "{}");
    return info?.data?.userName || "Admin";
  } catch {
    return "Admin";
  }
});

const shortcuts = [
  { name: "frontDesk", group: "Vận hành", title: "Lễ tân", desc: "Lịch phòng · sơ đồ phòng · nhận / trả phòng" },
  { name: "bookingAdmin", group: "Vận hành", title: "Đặt phòng", desc: "Danh sách đơn và phiếu thu" },
  { name: "room", group: "Kho phòng", title: "Phòng", desc: "Trạng thái buồng và bảo trì" },
  { name: "roomType", group: "Kho phòng", title: "Loại phòng", desc: "Hạng phòng và sức chứa" },
  { name: "rateCalendar", group: "Kho phòng", title: "Bảng giá", desc: "Giá theo ngày và ngừng bán" },
  { name: "hotelService", group: "Bán hàng", title: "Dịch vụ", desc: "Dịch vụ kèm phòng" },
  { name: "voucher", group: "Bán hàng", title: "Mã giảm giá", desc: "Mã giảm giá" },
  { name: "dashboard", group: "Báo cáo", title: "Báo cáo", desc: "Doanh thu và công suất phòng" },
  { name: "hotelSettings", group: "Cấu hình", title: "Khách sạn", desc: "Giờ nhận / trả, phí" },
];

const go = (key: string) => {
  const screen = (SCREEN as Record<string, { name: string }>)[key];
  if (screen?.name) router.push({ name: screen.name });
};
</script>

<style scoped lang="scss">
.home_page {
  padding-bottom: 24px;
}
.hero {
  margin-bottom: 12px;
}
.kicker {
  margin: 0 0 6px;
  font-size: 11px;
  letter-spacing: 0.12em;
  text-transform: uppercase;
  color: #5b6b76;
}
.hero h1 {
  margin: 0 0 8px;
  font-size: 1.75rem;
  color: #0f1c24;
  font-weight: 700;
}
.lead {
  margin: 0 0 16px;
  color: #5b6b76;
  font-size: 14px;
}
.quick_grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 12px;
}
.card {
  text-align: left;
  cursor: pointer;
  border: 0;
  width: 100%;
  transition: border-color 0.15s ease, box-shadow 0.15s ease;
}
.card:hover {
  box-shadow: 0 8px 24px rgba(15, 28, 36, 0.06);
}
.tag {
  display: inline-block;
  font-size: 11px;
  font-weight: 600;
  color: #0098e5;
  margin-bottom: 6px;
}
.card strong {
  display: block;
  font-size: 15px;
  color: #0f1c24;
  margin-bottom: 4px;
}
.card p {
  margin: 0;
  font-size: 13px;
  color: #5b6b76;
}
@media (max-width: 960px) {
  .quick_grid {
    grid-template-columns: 1fr 1fr;
  }
}
@media (max-width: 640px) {
  .quick_grid {
    grid-template-columns: 1fr;
  }
}
</style>
