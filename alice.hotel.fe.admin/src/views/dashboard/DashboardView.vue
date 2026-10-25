<template>
  <PageContainer>
    <BreadcrumbComponent :pageTitle="'Trang chủ'" />

    <div class="box dp_block">
      <div class="box_section">
        <div class="page_toparea">
          <div>
            <h2 class="title-search">Báo cáo vận hành</h2>
            <p class="hint">Doanh thu · công suất phòng · giá trung bình</p>
          </div>
          <div class="btn_group btn_end">
            <a class="button btn_md btn_lightgray" :href="excelUrl" target="_blank" rel="noopener">Xuất Excel</a>
            <router-link class="button btn_md btn_primary" :to="{ name: 'frontDesk' }">Mở Lễ tân</router-link>
          </div>
        </div>

        <div class="today-strip">
          <div class="t-item"><span>Sẽ đến hôm nay</span><strong>{{ todayArrivals }}</strong></div>
          <div class="t-item"><span>Đang ở</span><strong>{{ todayInHouse }}</strong></div>
          <div class="t-item"><span>Sẽ đi hôm nay</span><strong>{{ todayDepartures }}</strong></div>
          <div class="t-item warn"><span>Phòng cần dọn</span><strong>{{ todayDirty }}</strong></div>
        </div>
      </div>
    </div>

    <div v-if="d" class="kpi-grid">
      <div class="box dp_block kpi">
        <div class="box_section">
          <span class="l">Doanh thu hôm nay</span>
          <strong>{{ money(d.revenueToday) }}</strong>
        </div>
      </div>
      <div class="box dp_block kpi">
        <div class="box_section">
          <span class="l">Doanh thu tháng</span>
          <strong>{{ money(d.revenueMonth) }}</strong>
        </div>
      </div>
      <div class="box dp_block kpi">
        <div class="box_section">
          <span class="l">Công suất</span>
          <strong>{{ Number(d.occupancyPercent || 0).toFixed(1) }}%</strong>
        </div>
      </div>
      <div class="box dp_block kpi">
        <div class="box_section">
          <span class="l">Giá phòng TB</span>
          <strong>{{ money(d.adr) }}</strong>
          <em>Doanh thu/phòng {{ money(d.revpar) }}</em>
        </div>
      </div>
    </div>

    <div v-if="d" class="dash_grid">
      <div class="box dp_block">
        <div class="box_section">
          <div class="page_toparea">
            <h2 class="title-search">Doanh thu 7 ngày</h2>
          </div>
          <div class="bars">
            <div v-for="r in d.revenueByDay || []" :key="r.day" class="bar-col">
              <div class="amt">{{ shortMoney(r.amount) }}</div>
              <div class="bar" :style="{ height: barHeight(r.amount) + 'px' }" />
              <div class="lbl">{{ String(r.day).slice(5) }}</div>
            </div>
          </div>
        </div>
      </div>

      <div class="side">
        <div class="box dp_block">
          <div class="box_section">
            <div class="page_toparea">
              <h2 class="title-search">Theo loại phòng (tháng)</h2>
            </div>
            <div v-for="r in d.revenueByRoomType || []" :key="r.roomTypeId" class="type-row">
              <div class="type-meta">
                <span>{{ r.roomTypeName }}</span>
                <b>{{ money(r.amount) }}</b>
              </div>
              <div class="track"><i :style="{ width: typePct(r.amount) + '%' }" /></div>
            </div>
            <p v-if="!(d.revenueByRoomType || []).length" class="empty">Chưa có dữ liệu</p>
          </div>
        </div>
        <div class="box dp_block">
          <div class="box_section">
            <div class="page_toparea">
              <h2 class="title-search">Dịch vụ bán chạy</h2>
            </div>
            <div class="svc">
              <span v-for="s in d.topServices || []" :key="s.serviceId">{{ s.name }}</span>
              <p v-if="!(d.topServices || []).length" class="empty">Chưa có</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </PageContainer>
</template>

<script setup lang="ts">
import { computed, onMounted } from "vue";
import PageContainer from "@/components/common/PageContainer.vue";
import BreadcrumbComponent from "@/components/common/BreadcrumbComponent.vue";
import { aliceBookingStore } from "@/stores/aliceBooking";
import { ALICE_EXCEL } from "@/stores/aliceBooking/aliceBooking.service";

const store = aliceBookingStore();
const d = computed(() => store.dashboard);
const excelUrl = `${import.meta.env.VITE_API_ENDPOINT}${ALICE_EXCEL}`;
const money = (v: any) => Number(v || 0).toLocaleString("vi-VN") + " ₫";
const shortMoney = (v: any) => {
  const n = Number(v || 0);
  if (n >= 1_000_000) return (n / 1_000_000).toFixed(1) + "Tr";
  if (n >= 1000) return Math.round(n / 1000) + "k";
  return String(n);
};

const todayArrivals = computed(() => store.board?.arrivals?.length || 0);
const todayInHouse = computed(() => store.board?.inHouse?.length || 0);
const todayDepartures = computed(() => store.board?.departures?.length || 0);
const todayDirty = computed(
  () => (store.board?.rooms || []).filter((r: any) => r.houseStatus === "VACANT_DIRTY").length,
);

const maxDay = computed(() => Math.max(1, ...(d.value?.revenueByDay || []).map((x: any) => Number(x.amount || 0))));
const barHeight = (amount: any) => Math.max(6, Math.round((Number(amount || 0) / maxDay.value) * 150));
const typeTotal = computed(
  () => (d.value?.revenueByRoomType || []).reduce((s: number, x: any) => s + Number(x.amount || 0), 0) || 1,
);
const typePct = (amount: any) => Math.round((Number(amount || 0) / typeTotal.value) * 100);

onMounted(async () => {
  await Promise.all([store.loadDashboard(), store.loadBoard()]);
});
</script>

<style scoped>
.hint { margin: 4px 0 0; color: #5b6b76; font-size: 13px; }
.today-strip {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 10px;
  margin-top: 14px;
}
.t-item {
  display: flex;
  flex-direction: column;
  gap: 4px;
  min-width: 0;
  padding: 12px 14px;
  border-radius: 10px;
  background: #f6f8fb;
  border: 1px solid #e6edf2;
}
.t-item span { font-size: 12px; color: #5b6b76; }
.t-item strong { font-size: 1.45rem; color: #0f1c24; letter-spacing: -0.03em; font-variant-numeric: tabular-nums; }
.t-item.warn { background: #fff7f0; border-color: #f3ddc8; }
.t-item.warn strong { color: #c45c12; }
.kpi-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 12px;
}
.kpi { position: relative; overflow: hidden; }
.kpi::before {
  content: "";
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  width: 3px;
  background: linear-gradient(180deg, #33b4f0, #0098e5);
}
.kpi .l { display: block; font-size: 12px; color: #5b6b76; margin-bottom: 6px; }
.kpi strong { font-size: 1.55rem; color: #0f1c24; letter-spacing: -0.03em; font-variant-numeric: tabular-nums; }
.kpi em { display: block; margin-top: 4px; font-style: normal; font-size: 12px; color: #5b6b76; }
.dash_grid {
  display: grid;
  grid-template-columns: 1.4fr 1fr;
  gap: 12px;
}
.side { display: flex; flex-direction: column; gap: 12px; }
.bars {
  display: flex;
  align-items: flex-end;
  gap: 12px;
  height: 210px;
  padding-top: 8px;
}
.bar-col {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-end;
  height: 100%;
}
.bar {
  width: 100%;
  max-width: 40px;
  background: linear-gradient(180deg, #33b4f0, #0098e5);
  border-radius: 8px 8px 3px 3px;
}
.amt { font-size: 10px; color: #5b6b76; margin-bottom: 6px; }
.lbl { font-size: 11px; margin-top: 8px; color: #5b6b76; }
.type-row { margin-bottom: 14px; }
.type-meta {
  display: flex;
  justify-content: space-between;
  gap: 10px;
  font-size: 13px;
  margin-bottom: 6px;
}
.track {
  height: 8px;
  background: #eef3f7;
  border-radius: 999px;
  overflow: hidden;
}
.track i {
  display: block;
  height: 100%;
  background: linear-gradient(90deg, #0098e5, #33b4f0);
  border-radius: 999px;
}
.svc { display: flex; flex-wrap: wrap; gap: 8px; }
.svc span {
  background: #eef6fb;
  color: #0b6ea8;
  font-size: 12px;
  font-weight: 600;
  padding: 6px 10px;
  border-radius: 999px;
}
.empty { color: #5b6b76; font-size: 13px; margin: 0; }
@media (max-width: 960px) {
  .kpi-grid, .today-strip { grid-template-columns: repeat(2, 1fr); }
  .dash_grid { grid-template-columns: 1fr; }
}
</style>
