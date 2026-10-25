<template>
  <PageContainer>
    <BreadcrumbComponent :pageTitle="'Đặt phòng'" />

    <div class="box dp_block">
      <div class="box_section">
        <div class="search_box col_4">
          <ul>
            <li>
              <p>Trạng thái</p>
              <SelectBoxBase id="bkStatus" name="bkStatus" v-model="status" :data="statusItems" />
            </li>
            <li>
              <p>Từ ngày</p>
              <BaseDatePicker id="bkFrom" v-model="fromDate" />
            </li>
            <li>
              <p>Đến ngày</p>
              <BaseDatePicker id="bkTo" v-model="toDate" />
            </li>
            <li>
              <p>Mã / tên / SĐT</p>
              <InputBase id="bkKw" v-model="keyword" placeholder="Tìm nhanh…" isSearch @search="load" />
            </li>
          </ul>
          <div class="btn_group btn_end mg_t10">
            <button type="button" class="button btn_md btn_lightgray" @click="reset">Đặt lại</button>
            <button type="button" class="button btn_md btn_primary" @click="load">Tìm</button>
            <button type="button" class="button btn_md btn_primary" @click="$router.push({ name: 'frontDesk' })">
              Mở Lễ tân
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="box dp_block">
      <div class="box_section">
        <div class="pms_list_head">
          <h2>Danh sách đặt phòng ({{ filtered.length }})</h2>
        </div>
        <div class="table_wrap">
          <table class="table_base">
            <thead>
              <tr>
                <th>Mã đơn</th>
                <th>Khách</th>
                <th>Phòng</th>
                <th>Ngày</th>
                <th>Trạng thái</th>
                <th>Tổng</th>
                <th>Còn lại</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="b in filtered" :key="b.bookingId">
                <td><strong>{{ b.bookingCode }}</strong></td>
                <td>
                  <div>{{ b.guestName }}</div>
                  <small>{{ b.guestPhone || "—" }}</small>
                </td>
                <td>{{ b.roomTypeName }} / P.{{ b.roomNumber || "—" }}</td>
                <td>{{ b.checkIn }} → {{ b.checkOut }}</td>
                <td>{{ statusLabel(b.status) }}</td>
                <td>{{ money(b.totalAmount) }}</td>
                <td class="due">{{ money(b.remainingAmount) }}</td>
                <td class="actions">
                  <button type="button" class="button btn_sm btn_primary" @click="openFolio(b.bookingId)">Phiếu thu</button>
                  <button
                    v-if="['CONFIRMED', 'PENDING_PAYMENT'].includes(b.status)"
                    type="button"
                    class="button btn_sm btn_lightgray"
                    @click="goCheckIn(b)"
                  >
                    CI
                  </button>
                </td>
              </tr>
              <tr v-if="!filtered.length">
                <td colspan="8" class="empty">Không có đơn phù hợp.</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <TModal :is-open="folioOpen" :is-show-header="true" :is-show-footer="true" modal-id="bkFolio" class-size="small" @close-modal="folioOpen = false">
      <template #title><strong>{{ folio?.bookingCode }}</strong></template>
      <template #default>
        <p>{{ folio?.guestName }} · {{ folio?.guestPhone }}</p>
        <p>{{ folio?.roomTypeName }} / P.{{ folio?.roomNumber }} · {{ statusLabel(folio?.status) }}</p>
        <table class="tbl_row mg_t10">
          <colgroup><col style="width: 40%" /><col style="width: 60%" /></colgroup>
          <tbody>
            <tr><th scope="row"><p>Tổng</p></th><td>{{ money(folio?.totalAmount) }}</td></tr>
            <tr><th scope="row"><p>Đã thu</p></th><td>{{ money(folio?.paidAmount) }}</td></tr>
            <tr><th scope="row"><p>Còn lại</p></th><td class="due">{{ money(folio?.remainingAmount) }}</td></tr>
          </tbody>
        </table>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_lightgray popup_close" @click="folioOpen = false">Đóng</button>
        <button type="button" class="button btn_md btn_primary" @click="$router.push({ name: 'frontDesk' })">Xử lý Lễ tân</button>
      </template>
    </TModal>
  </PageContainer>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import http from "@/utils/http";
import PageContainer from "@/components/common/PageContainer.vue";
import BreadcrumbComponent from "@/components/common/BreadcrumbComponent.vue";
import InputBase from "@/components/common/input/InputBase.vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import BaseDatePicker from "@/components/common/input/BaseDatePicker.vue";
import TModal from "@/components/common/excel/TModal.vue";
import { aliceBookingStore } from "@/stores/aliceBooking";
import { ALICE_BOOKINGS } from "@/stores/aliceBooking/aliceBooking.service";

const store = aliceBookingStore();
const router = useRouter();
const status = ref("");
const fromDate = ref("");
const toDate = ref("");
const keyword = ref("");
const folioOpen = ref(false);
const folio = ref<any>(null);
const rows = ref<any[]>([]);

const statusItems = [
  { cdId: "", cdNm: "Tất cả" },
  { cdId: "PENDING_PAYMENT", cdNm: "Chờ thanh toán" },
  { cdId: "CONFIRMED", cdNm: "Đã xác nhận" },
  { cdId: "CHECKED_IN", cdNm: "Đang ở" },
  { cdId: "CHECKED_OUT", cdNm: "Đã trả phòng" },
  { cdId: "CANCELLED", cdNm: "Đã hủy" },
  { cdId: "NO_SHOW", cdNm: "Không đến" },
];

const money = (v: any) => Number(v || 0).toLocaleString("vi-VN") + " ₫";
const statusLabel = (s: string) =>
  ({
    PENDING_PAYMENT: "Chờ thanh toán",
    CONFIRMED: "Đã xác nhận",
    CHECKED_IN: "Đang ở",
    CHECKED_OUT: "Đã trả phòng",
    CANCELLED: "Đã hủy",
    NO_SHOW: "Không đến",
    EXPIRED_HOLD: "Hết hạn giữ chỗ",
    PAID: "Đã thu",
    SUCCESS: "Thành công",
  } as any)[s] || s;

const filtered = computed(() => {
  let list = [...rows.value];
  if (fromDate.value) list = list.filter((b) => String(b.checkIn) >= fromDate.value);
  if (toDate.value) list = list.filter((b) => String(b.checkIn) <= toDate.value);
  const q = keyword.value.trim().toLowerCase();
  if (q) {
    list = list.filter((b: any) =>
      JSON.stringify([b.bookingCode, b.guestName, b.guestPhone]).toLowerCase().includes(q),
    );
  }
  return list;
});

const load = async () => {
  await store.loadBookings(status.value || undefined);
  rows.value = [...(store.bookings || [])];
};

const reset = async () => {
  status.value = "";
  fromDate.value = "";
  toDate.value = "";
  keyword.value = "";
  await load();
};

const openFolio = async (id: string) => {
  const res = await http.get(`${ALICE_BOOKINGS}/${id}`);
  folio.value = res.data?.data;
  folioOpen.value = true;
};

const goCheckIn = (b: any) => {
  router.push({ name: "frontDesk", query: { bookingId: b.bookingId, action: "check-in" } });
};

onMounted(load);
</script>

<style scoped>
.due { color: #b42318; font-weight: 600; }
.actions { text-align: right; white-space: nowrap; }
.actions .button { margin-left: 4px; }
.empty { text-align: center; color: #888; padding: 24px !important; }
small { color: #717171; }
</style>
