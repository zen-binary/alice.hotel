<template>
  <PageContainer>
    <BreadcrumbComponent :pageTitle="'Lễ tân'" />

    <div class="fd box dp_block">
      <div class="box_section fd_body">
        <!-- Command bar -->
        <div class="fd_cmd">
          <div class="fd_cmd_left">
            <div class="fd_date">
              <span class="fd_date_day">{{ todayShort }}</span>
              <span class="fd_date_full">{{ todayLabel }}</span>
            </div>
            <div class="fd_occ" title="Phòng đang sử dụng">
              <div class="fd_occ_bar">
                <i :style="{ width: occupancyPct + '%' }" />
              </div>
              <span>{{ occupiedCount }}/{{ roomCount }} · {{ occupancyPct }}%</span>
            </div>
            <span class="shift-pill" :class="shiftOpen ? 'on' : 'off'">
              <i class="dot" />
              {{ shiftOpen ? "Ca đang mở" : "Chưa mở ca" }}
            </span>
          </div>
          <div class="fd_cmd_right">
            <button v-if="!shiftOpen" type="button" class="button btn_sm btn_primary" @click="dlgShiftOpen = true">
              Mở ca
            </button>
            <button v-else type="button" class="button btn_sm btn_lightgray" @click="dlgShiftClose = true">
              Đóng ca
            </button>
            <button type="button" class="button btn_sm btn_primary" @click="dlgWalkIn = true">Khách vãng lai</button>
            <button type="button" class="button btn_sm btn_lightgray" :disabled="loading" @click="reload">
              {{ loading ? "Đang tải…" : "Tải lại" }}
            </button>
          </div>
        </div>

        <!-- KPI strip -->
        <div class="fd_kpis">
          <button type="button" class="kpi" @click="tabIndex = 3">
            <span class="kpi_n">{{ store.board?.arrivals?.length || 0 }}</span>
            <span class="kpi_l">Sẽ đến</span>
          </button>
          <button type="button" class="kpi" @click="tabIndex = 3">
            <span class="kpi_n">{{ store.board?.inHouse?.length || 0 }}</span>
            <span class="kpi_l">Đang ở</span>
          </button>
          <button type="button" class="kpi" @click="tabIndex = 3">
            <span class="kpi_n">{{ store.board?.departures?.length || 0 }}</span>
            <span class="kpi_l">Sẽ đi</span>
          </button>
          <button type="button" class="kpi warn" @click="tabIndex = 3">
            <span class="kpi_n">{{ dirtyRooms.length }}</span>
            <span class="kpi_l">Cần dọn</span>
          </button>
          <div class="kpi muted">
            <span class="kpi_n">{{ timeline.length }}</span>
            <span class="kpi_l">Đặt phòng (14 ngày)</span>
          </div>
        </div>

        <SelectorComponent v-model="tabIndex" :data="tabLabels" class="fd_tabs" />

        <div class="fd_panel" v-if="!loading || roomCount">
          <BookingTimeline
            v-if="tab === 'timeline'"
            :rooms="store.board?.rooms || []"
            :bookings="timeline"
            :room-types="store.roomTypes"
            :days="timelineDays"
            :today-iso="todayIso"
            @open-folio="openFolio"
            @check-in="doCheckIn"
            @check-out="doCheckOut"
            @move="openMove"
            @no-show="doNoShow"
            @cancel="doCancel"
            @modify="onModify"
            @assign-room="onAssignRoom"
          />
          <RoomRack
            v-else-if="tab === 'rack'"
            :rooms="store.board?.rooms || []"
            :arrivals="store.board?.arrivals || []"
            :in-house="store.board?.inHouse || []"
            :departures="store.board?.departures || []"
            @room-click="onRoomClick"
          />
          <TodayOpsPanel
            v-else
            :arrivals="store.board?.arrivals || []"
            :in-house="store.board?.inHouse || []"
            :departures="store.board?.departures || []"
            :dirty-rooms="dirtyRooms"
            @check-in="doCheckIn"
            @check-out="doCheckOut"
            @open-folio="openFolio"
            @no-show="doNoShow"
            @add-service="openAddService"
            @pay="openPay"
            @mark-clean="markClean"
          />
        </div>
        <div v-else class="fd_loading">Đang tải dữ liệu lễ tân…</div>
      </div>
    </div>

    <FolioDrawer
      v-model:open="folioOpen"
      :folio="folio"
      @add-service="openAddService"
      @pay="openPay"
      @move="openMove"
    />

    <WalkInWizard
      v-model:open="dlgWalkIn"
      :room-types="store.roomTypes"
      :rooms="store.board?.rooms || []"
      :services="store.services"
      :timeline="timeline"
      :loading="busy"
      @submit="createWalkIn"
    />

    <TModal :is-open="dlgCheckIn" :is-show-header="true" :is-show-footer="true" modal-id="fdCheckIn" class-size="small" @close-modal="dlgCheckIn = false">
      <template #title><strong>Nhận phòng {{ checkInTarget?.bookingCode }}</strong></template>
      <template #default>
        <table class="tbl_row">
          <colgroup><col style="width: 30%" /><col style="width: 70%" /></colgroup>
          <tbody>
            <tr>
              <th scope="row"><p>Phòng nhận</p></th>
              <td><SelectBoxBase id="ciRoom" name="ciRoom" v-model="checkInForm.roomId" :data="checkInRoomItems" /></td>
            </tr>
            <tr>
              <th scope="row"><p>CCCD / hộ chiếu</p></th>
              <td><InputBase id="ciId" v-model="checkInForm.guestIdNumber" /></td>
            </tr>
            <tr>
              <th scope="row"><p>Nhận sớm</p></th>
              <td><SelectBoxBase id="ciEarly" name="ciEarly" v-model="checkInForm.earlyCheckIn" :data="ynOptions" /></td>
            </tr>
            <tr>
              <th scope="row"><p>Ép phòng bẩn</p></th>
              <td><SelectBoxBase id="ciForce" name="ciForce" v-model="checkInForm.forceDirtyOverride" :data="ynOptions" /></td>
            </tr>
            <tr v-if="checkInForm.forceDirtyOverride === 'Y'">
              <th scope="row"><p class="required">Lý do</p></th>
              <td><InputBase id="ciReason" v-model="checkInForm.overrideReason" placeholder="Lý do override" /></td>
            </tr>
          </tbody>
        </table>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_lightgray popup_close" @click="dlgCheckIn = false">Huỷ</button>
        <button type="button" class="button btn_md btn_primary" @click="submitCheckIn">Xác nhận nhận phòng</button>
      </template>
    </TModal>

    <TModal :is-open="dlgCheckOut" :is-show-header="true" :is-show-footer="true" modal-id="fdCheckOut" class-size="small" @close-modal="dlgCheckOut = false">
      <template #title><strong>Trả phòng</strong></template>
      <template #default>
        <p class="dlg_note">Còn lại: <strong>{{ money(checkOutTarget?.remainingAmount) }}</strong></p>
        <table class="tbl_row">
          <colgroup><col style="width: 30%" /><col style="width: 70%" /></colgroup>
          <tbody>
            <tr>
              <th scope="row"><p>Trả muộn</p></th>
              <td><SelectBoxBase id="coLate" name="coLate" v-model="checkOutForm.lateCheckOut" :data="ynOptions" /></td>
            </tr>
            <tr>
              <th scope="row"><p>Thu còn lại</p></th>
              <td><SelectBoxBase id="coMethod" name="coMethod" v-model="checkOutForm.method" :data="payMethodOptions" /></td>
            </tr>
          </tbody>
        </table>
        <p v-if="!shiftOpen && Number(checkOutTarget?.remainingAmount || 0) > 0" class="dlg_warn">
          Cần mở ca trước khi thu tiền trả phòng.
        </p>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_lightgray popup_close" @click="dlgCheckOut = false">Huỷ</button>
        <button type="button" class="button btn_md btn_primary" @click="submitCheckOut">Hoàn tất</button>
      </template>
    </TModal>

    <TModal :is-open="dlgService" :is-show-header="true" :is-show-footer="true" modal-id="fdService" class-size="small" @close-modal="dlgService = false">
      <template #title><strong>Thêm dịch vụ</strong></template>
      <template #default>
        <table class="tbl_row">
          <colgroup><col style="width: 30%" /><col style="width: 70%" /></colgroup>
          <tbody>
            <tr>
              <th scope="row"><p>Dịch vụ</p></th>
              <td><SelectBoxBase id="svcId" name="svcId" v-model="svcForm.serviceId" :data="serviceItems" /></td>
            </tr>
            <tr>
              <th scope="row"><p>Số lượng</p></th>
              <td><InputBase id="svcQty" v-model="svcForm.qty" isNumber /></td>
            </tr>
          </tbody>
        </table>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_lightgray popup_close" @click="dlgService = false">Huỷ</button>
        <button type="button" class="button btn_md btn_primary" @click="submitService">Lưu</button>
      </template>
    </TModal>

    <TModal :is-open="dlgPay" :is-show-header="true" :is-show-footer="true" modal-id="fdPay" class-size="small" @close-modal="dlgPay = false">
      <template #title><strong>Thu tiền quầy</strong></template>
      <template #default>
        <p v-if="!shiftOpen" class="dlg_warn">Bắt buộc mở ca trước khi thu tiền.</p>
        <table class="tbl_row">
          <colgroup><col style="width: 30%" /><col style="width: 70%" /></colgroup>
          <tbody>
            <tr>
              <th scope="row"><p>Hình thức</p></th>
              <td><SelectBoxBase id="payMethod" name="payMethod" v-model="payForm.method" :data="payMethodOptions" /></td>
            </tr>
            <tr>
              <th scope="row"><p>Số tiền</p></th>
              <td><InputBase id="payAmt" v-model="payForm.amount" isNumber /></td>
            </tr>
          </tbody>
        </table>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_lightgray popup_close" @click="dlgPay = false">Huỷ</button>
        <button type="button" class="button btn_md btn_primary" :disabled="!shiftOpen" @click="submitPay">Xác nhận</button>
      </template>
    </TModal>

    <TModal :is-open="dlgMove" :is-show-header="true" :is-show-footer="true" modal-id="fdMove" class-size="small" @close-modal="dlgMove = false">
      <template #title><strong>Đổi phòng</strong></template>
      <template #default>
        <table class="tbl_row">
          <colgroup><col style="width: 30%" /><col style="width: 70%" /></colgroup>
          <tbody>
            <tr>
              <th scope="row"><p>Phòng mới</p></th>
              <td><SelectBoxBase id="moveRoom" name="moveRoom" v-model="moveRoomId" :data="cleanRoomItems" /></td>
            </tr>
          </tbody>
        </table>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_lightgray popup_close" @click="dlgMove = false">Huỷ</button>
        <button type="button" class="button btn_md btn_primary" @click="submitMove">Đổi phòng</button>
      </template>
    </TModal>

    <TModal :is-open="dlgShiftOpen" :is-show-header="true" :is-show-footer="true" modal-id="fdShiftOpen" class-size="mini" @close-modal="dlgShiftOpen = false">
      <template #title><strong>Mở ca</strong></template>
      <template #default>
        <table class="tbl_row">
          <colgroup><col style="width: 35%" /><col style="width: 65%" /></colgroup>
          <tbody>
            <tr>
              <th scope="row"><p>Tiền đầu ca</p></th>
              <td><InputBase id="openCash" v-model="openingCash" isNumber /></td>
            </tr>
          </tbody>
        </table>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_lightgray popup_close" @click="dlgShiftOpen = false">Huỷ</button>
        <button type="button" class="button btn_md btn_primary" @click="doOpenShift">Mở ca</button>
      </template>
    </TModal>

    <TModal :is-open="dlgShiftClose" :is-show-header="true" :is-show-footer="true" modal-id="fdShiftClose" class-size="mini" @close-modal="dlgShiftClose = false">
      <template #title><strong>Đóng ca</strong></template>
      <template #default>
        <table class="tbl_row">
          <colgroup><col style="width: 35%" /><col style="width: 65%" /></colgroup>
          <tbody>
            <tr>
              <th scope="row"><p>Tiền cuối ca</p></th>
              <td><InputBase id="closeCash" v-model="closingCash" isNumber /></td>
            </tr>
          </tbody>
        </table>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_lightgray popup_close" @click="dlgShiftClose = false">Huỷ</button>
        <button type="button" class="button btn_md btn_primary" @click="doCloseShift">Đóng ca</button>
      </template>
    </TModal>
  </PageContainer>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive, ref } from "vue";
import { useRoute } from "vue-router";
import http from "@/utils/http";
import { aliceBookingStore } from "@/stores/aliceBooking";
import { ALICE_BOOKINGS } from "@/stores/aliceBooking/aliceBooking.service";
import PageContainer from "@/components/common/PageContainer.vue";
import BreadcrumbComponent from "@/components/common/BreadcrumbComponent.vue";
import SelectorComponent from "@/components/common/SelectorComponent.vue";
import InputBase from "@/components/common/input/InputBase.vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import TModal from "@/components/common/excel/TModal.vue";
import BookingTimeline from "./components/BookingTimeline.vue";
import RoomRack from "./components/RoomRack.vue";
import TodayOpsPanel from "./components/TodayOpsPanel.vue";
import FolioDrawer from "./components/FolioDrawer.vue";
import WalkInWizard from "./components/WalkInWizard.vue";

const store = aliceBookingStore();
const shiftOpen = computed(() => store.shift?.status === "OPEN");
const route = useRoute();
const tabIndex = ref(1);
const tabLabels = ["Timeline", "Sơ đồ phòng", "Vận hành hôm nay"];
const tab = computed(() => (["timeline", "rack", "ops"] as const)[tabIndex.value - 1] || "timeline");
const busy = ref(false);
const loading = ref(false);
const folioOpen = ref(false);
const folio = ref<any>(null);
const timeline = ref<any[]>([]);
const dlgWalkIn = ref(false);
const dlgService = ref(false);
const dlgPay = ref(false);
const dlgMove = ref(false);
const dlgCheckIn = ref(false);
const dlgCheckOut = ref(false);
const dlgShiftOpen = ref(false);
const dlgShiftClose = ref(false);
const openingCash = ref("0");
const closingCash = ref("0");
const activeBookingId = ref("");
const moveRoomId = ref("");
const checkInTarget = ref<any>(null);
const checkOutTarget = ref<any>(null);

const ynOptions = [
  { cdId: "N", cdNm: "Không" },
  { cdId: "Y", cdNm: "Có" },
];
const payMethodOptions = [
  { cdId: "CASH", cdNm: "Tiền mặt" },
  { cdId: "TRANSFER", cdNm: "Chuyển khoản" },
];

const svcForm = reactive({ serviceId: "", qty: "1" });
const payForm = reactive({ method: "CASH", amount: "0" });
const checkInForm = reactive({
  roomId: "",
  guestIdNumber: "",
  earlyCheckIn: "N",
  forceDirtyOverride: "N",
  overrideReason: "",
});
const checkOutForm = reactive({ lateCheckOut: "N", method: "CASH" });

const todayIso = new Date().toISOString().slice(0, 10);
const todayShort = new Date().toLocaleDateString("vi-VN", { day: "2-digit", month: "2-digit" });
const todayLabel = new Date().toLocaleDateString("vi-VN", {
  weekday: "long",
  day: "2-digit",
  month: "2-digit",
  year: "numeric",
});
const money = (v: any) => Number(v || 0).toLocaleString("vi-VN") + " ₫";
const serviceItems = computed(() =>
  (store.services || []).map((s: any) => ({
    cdId: s.serviceId,
    cdNm: `${s.name} (${money(s.unitPrice)})`,
  })),
);
const cleanRoomItems = computed(() =>
  (store.board?.rooms || [])
    .filter((r: any) => r.houseStatus === "VACANT_CLEAN")
    .map((r: any) => ({
      cdId: r.roomId,
      cdNm: `P.${r.roomNumber} · ${r.roomTypeName}`,
    })),
);
const dirtyRooms = computed(() => (store.board?.rooms || []).filter((r: any) => r.houseStatus === "VACANT_DIRTY"));
const roomCount = computed(() => (store.board?.rooms || []).filter((r: any) => r.activeYn !== "N").length);
const occupiedCount = computed(() => (store.board?.rooms || []).filter((r: any) => r.houseStatus === "OCCUPIED").length);
const occupancyPct = computed(() => (roomCount.value ? Math.round((occupiedCount.value * 100) / roomCount.value) : 0));

const timelineDays = computed(() => {
  const days: string[] = [];
  const start = new Date();
  for (let i = 0; i < 14; i++) {
    const d = new Date(start);
    d.setDate(start.getDate() + i);
    days.push(d.toISOString().slice(0, 10));
  }
  return days;
});

const checkInRoomItems = computed(() => {
  const typeId = checkInTarget.value?.roomTypeId;
  return (store.board?.rooms || [])
    .filter(
      (r: any) =>
        (!typeId || r.roomTypeId === typeId) &&
        (r.houseStatus === "VACANT_CLEAN" ||
          r.houseStatus === "VACANT_DIRTY" ||
          r.roomId === checkInTarget.value?.roomId),
    )
    .map((r: any) => ({
      cdId: r.roomId,
      cdNm: `P.${r.roomNumber} · ${r.houseStatus === "VACANT_CLEAN" ? "Sạch" : r.houseStatus === "VACANT_DIRTY" ? "Bẩn" : "Đang ở"}`,
    }));
});

const reload = async () => {
  loading.value = true;
  try {
    await Promise.all([store.loadBoard(), store.loadRoomTypes(), store.loadServices(), store.loadShift()]);
    const from = timelineDays.value[0];
    const to = timelineDays.value[timelineDays.value.length - 1];
    const res = await http.get("/a/front-desk/timeline", { params: { from, to } });
    timeline.value = res.data?.data || [];
  } finally {
    loading.value = false;
  }
};

const openFolio = async (bookingId: string) => {
  const res = await http.get(`${ALICE_BOOKINGS}/${bookingId}`);
  folio.value = res.data?.data;
  folioOpen.value = true;
};

const onRoomClick = async (r: any) => {
  if (r.houseStatus === "VACANT_DIRTY") {
    if (!confirm(`Đánh dấu P.${r.roomNumber} đã sạch?`)) return;
    await store.setHouseStatus(r.roomId, "VACANT_CLEAN");
    await reload();
    return;
  }
  const b = [...(store.board?.inHouse || []), ...(store.board?.arrivals || []), ...(store.board?.departures || [])].find(
    (x: any) => x.roomId === r.roomId,
  );
  if (b) await openFolio(b.bookingId);
};

const markClean = async (r: any) => {
  await store.setHouseStatus(r.roomId, "VACANT_CLEAN");
  await reload();
};

const createWalkIn = async (payload: any) => {
  if (!payload.guestName || !payload.roomTypeId || !payload.roomId) return alert("Thiếu thông tin");
  busy.value = true;
  try {
    const created = await store.walkIn(payload);
    dlgWalkIn.value = false;
    await reload();
    if (created?.bookingId) await openFolio(created.bookingId);
  } catch (e: any) {
    alert(e?.response?.data?.message || "Tạo walk-in thất bại");
  } finally {
    busy.value = false;
  }
};

const doCheckIn = (b: any) => {
  checkInTarget.value = b;
  checkInForm.roomId = b.roomId || "";
  checkInForm.guestIdNumber = b.guestIdNumber || "";
  checkInForm.earlyCheckIn = "N";
  checkInForm.forceDirtyOverride = "N";
  checkInForm.overrideReason = "";
  dlgCheckIn.value = true;
};

const submitCheckIn = async () => {
  if (checkInForm.forceDirtyOverride === "Y" && !checkInForm.overrideReason.trim()) {
    return alert("Nhập lý do ép nhận phòng bẩn");
  }
  try {
    await store.checkIn(checkInTarget.value.bookingId, {
      roomId: checkInForm.roomId,
      guestIdNumber: checkInForm.guestIdNumber,
      earlyCheckIn: checkInForm.earlyCheckIn === "Y",
      forceDirtyOverride: checkInForm.forceDirtyOverride === "Y",
      overrideReason: checkInForm.overrideReason,
    });
    dlgCheckIn.value = false;
    await reload();
  } catch {
    alert("Nhận phòng thất bại. Phòng phải sạch hoặc ghi rõ lý do ngoại lệ.");
  }
};

const doCheckOut = (b: any) => {
  checkOutTarget.value = b;
  checkOutForm.lateCheckOut = "N";
  checkOutForm.method = "CASH";
  dlgCheckOut.value = true;
};

const submitCheckOut = async () => {
  if (!shiftOpen.value && Number(checkOutTarget.value?.remainingAmount || 0) > 0) {
    alert("Cần mở ca trước khi thu tiền trả phòng");
    return;
  }
  try {
    await store.checkOut(checkOutTarget.value.bookingId, {
      lateCheckOut: checkOutForm.lateCheckOut === "Y",
      method: checkOutForm.method,
    });
    dlgCheckOut.value = false;
    await reload();
  } catch {
    alert("Trả phòng thất bại");
  }
};

const doNoShow = async (id: string) => {
  if (!confirm("Đánh dấu no-show?")) return;
  await store.noShow(id);
  await reload();
};

const doCancel = async (id: string) => {
  if (!confirm("Hủy đơn đặt phòng này?")) return;
  await http.post(`${ALICE_BOOKINGS}/${id}/cancel`, { reason: "Admin cancel" });
  await reload();
};

const onModify = async (payload: { bookingId: string; checkIn: string; checkOut: string }) => {
  try {
    await http.post(`${ALICE_BOOKINGS}/${payload.bookingId}/modify`, {
      checkIn: payload.checkIn,
      checkOut: payload.checkOut,
    });
    await reload();
  } catch {
    alert("Không đổi được ngày — kiểm tra phòng trống.");
  }
};

const onAssignRoom = async (payload: { bookingId: string; roomId: string }) => {
  try {
    await http.post(`${ALICE_BOOKINGS}/${payload.bookingId}/assign-room`, { newRoomId: payload.roomId });
    await reload();
  } catch {
    alert("Không gán được phòng.");
  }
};

const openAddService = (b: any) => {
  activeBookingId.value = b.bookingId;
  svcForm.serviceId = serviceItems.value[0]?.cdId || "";
  svcForm.qty = "1";
  dlgService.value = true;
};

const submitService = async () => {
  await store.addService(activeBookingId.value, {
    serviceId: svcForm.serviceId,
    qty: Number(svcForm.qty || 1),
  });
  dlgService.value = false;
  await reload();
  if (folioOpen.value) await openFolio(activeBookingId.value);
};

const openPay = (b: any) => {
  if (!shiftOpen.value) {
    alert("Bắt buộc mở ca trước khi thu tiền quầy.");
    dlgShiftOpen.value = true;
    return;
  }
  activeBookingId.value = b.bookingId;
  payForm.method = "CASH";
  payForm.amount = String(Number(b.remainingAmount || 0));
  dlgPay.value = true;
};

const submitPay = async () => {
  try {
    await store.pay(activeBookingId.value, {
      method: payForm.method,
      amount: Number(payForm.amount || 0),
    });
    dlgPay.value = false;
    await reload();
    if (folioOpen.value) await openFolio(activeBookingId.value);
  } catch {
    alert("Thu tiền thất bại — hãy mở ca trước.");
  }
};

const openMove = (b: any) => {
  activeBookingId.value = b.bookingId;
  moveRoomId.value = "";
  dlgMove.value = true;
};

const submitMove = async () => {
  if (!moveRoomId.value) return;
  await http.post(`${ALICE_BOOKINGS}/${activeBookingId.value}/move`, { newRoomId: moveRoomId.value });
  dlgMove.value = false;
  await reload();
  await openFolio(activeBookingId.value);
};

const doOpenShift = async () => {
  await store.openShift(Number(openingCash.value || 0));
  dlgShiftOpen.value = false;
};
const doCloseShift = async () => {
  try {
    await store.closeShift(Number(closingCash.value || 0));
    dlgShiftClose.value = false;
  } catch {
    alert("Không đóng được ca. Hãy tải lại rồi thử lại.");
  }
};

onMounted(async () => {
  await reload();
  const bid = route.query.bookingId as string | undefined;
  if (bid) {
    if (route.query.action === "check-in") {
      const all = [...(store.board?.arrivals || []), ...(store.board?.inHouse || []), ...(store.board?.departures || [])];
      const b = all.find((x: any) => x.bookingId === bid);
      if (b) doCheckIn(b);
      else await openFolio(bid);
    } else {
      await openFolio(bid);
    }
  }
});
</script>

<style scoped>
.fd {
  margin-top: 0;
}
.fd_body {
  padding-top: 14px !important;
}
.fd_cmd {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  flex-wrap: wrap;
  padding-bottom: 12px;
  border-bottom: 1px solid #e8eef3;
}
.fd_cmd_left,
.fd_cmd_right {
  display: flex;
  align-items: center;
  gap: 10px;
  flex-wrap: wrap;
}
.fd_date {
  display: flex;
  align-items: baseline;
  gap: 8px;
}
.fd_date_day {
  font-size: 1.35rem;
  font-weight: 700;
  color: #0f1c24;
  letter-spacing: -0.02em;
}
.fd_date_full {
  font-size: 12px;
  color: #6b7c88;
  text-transform: capitalize;
}
.fd_occ {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 12px;
  font-weight: 600;
  color: #3d4f5c;
  padding: 4px 10px;
  background: #f3f7fa;
  border-radius: 6px;
}
.fd_occ_bar {
  width: 56px;
  height: 6px;
  border-radius: 999px;
  background: #d9e4ec;
  overflow: hidden;
}
.fd_occ_bar i {
  display: block;
  height: 100%;
  background: #0b7bb5;
  border-radius: inherit;
}
.shift-pill {
  font-size: 12px;
  font-weight: 600;
  padding: 5px 10px;
  border-radius: 6px;
  display: inline-flex;
  align-items: center;
  gap: 6px;
}
.shift-pill .dot {
  width: 7px;
  height: 7px;
  border-radius: 50%;
  background: currentColor;
}
.shift-pill.on {
  background: #e8f7ef;
  color: #187a45;
}
.shift-pill.off {
  background: #fff3e8;
  color: #b85a10;
}
.fd_kpis {
  display: grid;
  grid-template-columns: repeat(5, minmax(0, 1fr));
  gap: 8px;
  margin: 12px 0 4px;
}
.kpi {
  display: flex;
  align-items: baseline;
  gap: 8px;
  padding: 8px 12px;
  border: 1px solid #e4ecf2;
  border-radius: 6px;
  background: #fafcfd;
  text-align: left;
  cursor: pointer;
  transition: border-color 0.12s, background 0.12s;
}
.kpi:hover {
  border-color: #b7d4e8;
  background: #f3f9fd;
}
.kpi.muted {
  cursor: default;
}
.kpi.muted:hover {
  border-color: #e4ecf2;
  background: #fafcfd;
}
.kpi.warn .kpi_n {
  color: #b85a10;
}
.kpi_n {
  font-size: 1.2rem;
  font-weight: 700;
  color: #0f1c24;
  line-height: 1;
  font-variant-numeric: tabular-nums;
}
.kpi_l {
  font-size: 12px;
  color: #6b7c88;
  white-space: nowrap;
}
.fd_tabs {
  margin: 8px 0 12px;
}
.fd_panel {
  min-height: 360px;
}
.fd_loading {
  padding: 48px 16px;
  text-align: center;
  color: #6b7c88;
  font-size: 14px;
}
@media (max-width: 960px) {
  .fd_kpis {
    grid-template-columns: repeat(2, 1fr);
  }
}
.dlg_note {
  margin: 0 0 12px;
  color: #5b6b76;
  font-size: 13px;
}
.dlg_warn {
  margin: 8px 0 0;
  padding: 8px 10px;
  background: #fff5f4;
  color: #b42318;
  border-radius: 6px;
  font-size: 13px;
}
.button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
</style>
