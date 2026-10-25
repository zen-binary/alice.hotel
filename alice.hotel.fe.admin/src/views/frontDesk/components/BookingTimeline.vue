<template>
  <div class="tl">
    <div class="tl_toolbar">
      <div class="tl_filters">
        <label>
          <span>Loại phòng</span>
          <SelectBoxBase id="tlType" name="tlType" v-model="filterType" :data="typeItems" />
        </label>
        <label>
          <span>Tầng</span>
          <SelectBoxBase id="tlFloor" name="tlFloor" v-model="filterFloor" :data="floorItems" />
        </label>
        <label>
          <span>Trạng thái</span>
          <SelectBoxBase id="tlStatus" name="tlStatus" v-model="filterStatus" :data="statusFilterItems" />
        </label>
      </div>
      <div class="legend">
        <span><i class="c confirmed" /> Đã xác nhận</span>
        <span><i class="c pending" /> Chờ thanh toán</span>
        <span><i class="c inhouse" /> Đang ở</span>
        <span><i class="c other" /> Khác</span>
      </div>
    </div>

    <div v-if="!filteredRooms.length" class="tl_empty">
      <strong>Không có phòng theo bộ lọc</strong>
      <p>Đổi loại phòng / tầng hoặc kiểm tra danh mục phòng.</p>
    </div>

    <div v-else class="tl-wrap" ref="wrapRef">
      <div class="tl-head">
        <div class="tl-room sticky">Phòng</div>
        <div
          v-for="d in dayMeta"
          :key="d.iso"
          class="tl-day"
          :class="{ today: d.iso === todayIso }"
        >
          <em>{{ d.wd }}</em>
          <strong>{{ d.label }}</strong>
        </div>
      </div>

      <div
        v-for="r in filteredRooms"
        :key="r.roomId"
        class="tl-row"
        @dragover.prevent
        @drop="onDropRoom($event, r.roomId)"
      >
        <div class="tl-room sticky" :class="houseClass(r.houseStatus)">
          <strong>{{ r.roomNumber }}</strong>
          <small>{{ shortType(r.roomTypeName) }}</small>
        </div>
        <div class="tl-track" :ref="(el) => setTrack(r.roomId, el)">
          <div
            v-for="b in bookingsOnRoom(r.roomId)"
            :key="b.bookingId"
            class="tl-bar"
            :class="`st-${b.status}`"
            :style="barStyle(b)"
            draggable="true"
            :title="`${b.bookingCode} · ${b.guestName} · ${b.status}`"
            @click.stop="emit('open-folio', b.bookingId)"
            @contextmenu.prevent="openMenu($event, b)"
            @dragstart="onDragStart($event, b)"
          >
            <span class="name">{{ b.guestName }}</span>
            <button type="button" class="more" @click.stop="openMenu($event, b)">⋮</button>
          </div>
        </div>
      </div>

      <div v-if="!hasAnyBooking" class="tl_hint">
        Chưa có đặt phòng trong 14 ngày tới — dùng <strong>Khách vãng lai</strong> hoặc tạo đặt phòng mới.
      </div>
    </div>

    <div v-if="menu.open" class="ctx" :style="{ top: menu.y + 'px', left: menu.x + 'px' }" @click.stop>
      <button type="button" @click="emit('open-folio', menu.booking.bookingId); closeMenu()">Phiếu thu</button>
      <button
        v-if="['CONFIRMED', 'PENDING_PAYMENT'].includes(menu.booking.status)"
        type="button"
        @click="emit('check-in', menu.booking); closeMenu()"
      >
        Nhận phòng
      </button>
      <button
        v-if="menu.booking.status === 'CHECKED_IN'"
        type="button"
        @click="emit('check-out', menu.booking); closeMenu()"
      >
        Trả phòng
      </button>
      <button type="button" @click="emit('move', menu.booking); closeMenu()">Đổi phòng</button>
      <button type="button" @click="openModify(); closeMenu()">Đổi ngày</button>
      <button
        v-if="menu.booking.status === 'CONFIRMED'"
        type="button"
        class="danger"
        @click="emit('no-show', menu.booking.bookingId); closeMenu()"
      >
        Không đến
      </button>
      <button
        v-if="['CONFIRMED', 'PENDING_PAYMENT'].includes(menu.booking.status)"
        type="button"
        class="danger"
        @click="emit('cancel', menu.booking.bookingId); closeMenu()"
      >
        Hủy đơn
      </button>
    </div>

    <TModal
      :is-open="dlgModify"
      :is-show-header="true"
      :is-show-footer="true"
      modal-id="tlModify"
      class-size="small"
      @close-modal="dlgModify = false"
    >
      <template #title><strong>Đổi ngày — {{ modifyTarget?.bookingCode }}</strong></template>
      <template #default>
        <table class="tbl_row">
          <colgroup><col style="width: 30%" /><col style="width: 70%" /></colgroup>
          <tbody>
            <tr>
              <th scope="row"><p>Nhận phòng</p></th>
              <td><BaseDatePicker id="modIn" v-model="mod.checkIn" /></td>
            </tr>
            <tr>
              <th scope="row"><p>Trả phòng</p></th>
              <td><BaseDatePicker id="modOut" v-model="mod.checkOut" /></td>
            </tr>
          </tbody>
        </table>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_lightgray popup_close" @click="dlgModify = false">Huỷ</button>
        <button type="button" class="button btn_md btn_primary" @click="submitModify">Lưu</button>
      </template>
    </TModal>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, onUnmounted, reactive, ref } from "vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import BaseDatePicker from "@/components/common/input/BaseDatePicker.vue";
import TModal from "@/components/common/excel/TModal.vue";

const props = defineProps<{
  rooms: any[];
  bookings: any[];
  roomTypes: any[];
  days: string[];
  todayIso: string;
}>();

const emit = defineEmits<{
  (e: "open-folio", id: string): void;
  (e: "check-in", b: any): void;
  (e: "check-out", b: any): void;
  (e: "move", b: any): void;
  (e: "no-show", id: string): void;
  (e: "cancel", id: string): void;
  (e: "modify", payload: { bookingId: string; checkIn: string; checkOut: string }): void;
  (e: "assign-room", payload: { bookingId: string; roomId: string }): void;
}>();

const filterType = ref("");
const filterFloor = ref("");
const filterStatus = ref("");
const dlgModify = ref(false);
const modifyTarget = ref<any>(null);
const mod = reactive({ checkIn: "", checkOut: "" });
const dragBooking = ref<any>(null);
const tracks = new Map<string, HTMLElement>();
const WD = ["CN", "T2", "T3", "T4", "T5", "T6", "T7"];

const menu = reactive<{ open: boolean; x: number; y: number; booking: any }>({
  open: false,
  x: 0,
  y: 0,
  booking: null,
});

const typeItems = computed(() => [
  { cdId: "", cdNm: "Tất cả" },
  ...(props.roomTypes || []).map((t: any) => ({ cdId: t.roomTypeId, cdNm: t.name })),
]);
const floorItems = computed(() => [
  { cdId: "", cdNm: "Tất cả" },
  ...[...new Set((props.rooms || []).map((r: any) => r.floorNo).filter((n: any) => n != null))]
    .sort((a: any, b: any) => a - b)
    .map((n: any) => ({ cdId: String(n), cdNm: `Tầng ${n}` })),
]);
const statusFilterItems = [
  { cdId: "", cdNm: "Tất cả" },
  { cdId: "PENDING_PAYMENT", cdNm: "Chờ thanh toán" },
  { cdId: "CONFIRMED", cdNm: "Đã xác nhận" },
  { cdId: "CHECKED_IN", cdNm: "Đang ở" },
];

const filteredRooms = computed(() => {
  let list = [...(props.rooms || [])].filter((r) => r.activeYn !== "N");
  if (filterType.value) list = list.filter((r) => r.roomTypeId === filterType.value);
  if (filterFloor.value) list = list.filter((r) => String(r.floorNo) === filterFloor.value);
  return list.sort((a, b) => String(a.roomNumber).localeCompare(String(b.roomNumber), undefined, { numeric: true }));
});

const dayMeta = computed(() =>
  (props.days || []).map((iso) => {
    const d = new Date(iso + "T12:00:00");
    return {
      iso,
      wd: WD[d.getDay()],
      label: `${String(d.getDate()).padStart(2, "0")}/${String(d.getMonth() + 1).padStart(2, "0")}`,
    };
  }),
);

const hasAnyBooking = computed(() => {
  const ids = new Set(filteredRooms.value.map((r) => r.roomId));
  return (props.bookings || []).some((b) => ids.has(b.roomId) && (!filterStatus.value || b.status === filterStatus.value));
});

const shortType = (n: string) => (n || "").slice(0, 12);
const houseClass = (s: string) =>
  ({
    VACANT_CLEAN: "hs-clean",
    VACANT_DIRTY: "hs-dirty",
    OCCUPIED: "hs-occ",
    OUT_OF_ORDER: "hs-ooo",
  } as any)[s] || "";

const bookingsOnRoom = (roomId: string) => {
  let list = (props.bookings || []).filter((b) => b.roomId === roomId);
  if (filterStatus.value) list = list.filter((b) => b.status === filterStatus.value);
  return list;
};

const setTrack = (roomId: string, el: any) => {
  if (el) tracks.set(roomId, el as HTMLElement);
};

const barStyle = (b: any) => {
  const days = props.days;
  const start = days.indexOf(String(b.checkIn).slice(0, 10));
  const end = days.indexOf(String(b.checkOut).slice(0, 10));
  const left = Math.max(0, start) / days.length;
  const rightIdx = end < 0 ? days.length : end;
  const width = Math.max(1, rightIdx - Math.max(0, start)) / days.length;
  return { left: `${left * 100}%`, width: `${width * 100}%` };
};

const openMenu = (e: MouseEvent, b: any) => {
  menu.open = true;
  menu.x = Math.min(e.clientX, window.innerWidth - 180);
  menu.y = Math.min(e.clientY, window.innerHeight - 220);
  menu.booking = b;
};
const closeMenu = () => {
  menu.open = false;
};

const openModify = () => {
  modifyTarget.value = menu.booking;
  mod.checkIn = String(menu.booking.checkIn).slice(0, 10);
  mod.checkOut = String(menu.booking.checkOut).slice(0, 10);
  dlgModify.value = true;
};

const submitModify = () => {
  emit("modify", {
    bookingId: modifyTarget.value.bookingId,
    checkIn: mod.checkIn,
    checkOut: mod.checkOut,
  });
  dlgModify.value = false;
};

const onDragStart = (e: DragEvent, b: any) => {
  dragBooking.value = b;
  e.dataTransfer?.setData("text/plain", b.bookingId);
};

const onDropRoom = (_e: DragEvent, roomId: string) => {
  const b = dragBooking.value;
  if (!b || b.roomId === roomId) return;
  if (!confirm(`Gán ${b.bookingCode} sang phòng mới?`)) return;
  emit("assign-room", { bookingId: b.bookingId, roomId });
  dragBooking.value = null;
};

const onDocClick = () => closeMenu();
onMounted(() => document.addEventListener("click", onDocClick));
onUnmounted(() => document.removeEventListener("click", onDocClick));
</script>

<style scoped>
.tl_toolbar {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  gap: 12px;
  flex-wrap: wrap;
  margin-bottom: 10px;
}
.tl_filters {
  display: grid;
  grid-template-columns: repeat(3, minmax(140px, 200px));
  gap: 10px;
  flex: 1;
}
.tl_filters label {
  display: flex;
  flex-direction: column;
  gap: 4px;
  margin: 0;
}
.tl_filters label > span {
  font-size: 11px;
  font-weight: 600;
  color: #6b7c88;
  text-transform: uppercase;
  letter-spacing: 0.04em;
}
.legend {
  display: flex;
  gap: 12px;
  font-size: 12px;
  color: #5b6b76;
  flex-wrap: wrap;
  padding-bottom: 4px;
}
.legend .c {
  display: inline-block;
  width: 10px;
  height: 10px;
  border-radius: 2px;
  margin-right: 4px;
  vertical-align: -1px;
}
.c.confirmed { background: #1b7f4a; }
.c.pending { background: #c45c12; }
.c.inhouse { background: #b42318; }
.c.other { background: #0b7bb5; }
.tl-wrap {
  position: relative;
  overflow: auto;
  border: 1px solid #dce6ee;
  border-radius: 8px;
  background: #fff;
  max-height: calc(100vh - 280px);
  min-height: 280px;
}
.tl-head,
.tl-row {
  display: flex;
  min-width: 980px;
}
.tl-head {
  position: sticky;
  top: 0;
  z-index: 3;
  background: #f0f4f8;
  border-bottom: 1px solid #dce6ee;
}
.tl-room.sticky {
  position: sticky;
  left: 0;
  z-index: 2;
  width: 96px;
  flex: 0 0 96px;
  padding: 8px 10px;
  border-right: 1px solid #dce6ee;
  background: #fff;
  display: flex;
  flex-direction: column;
  gap: 1px;
  box-shadow: 2px 0 0 rgba(15, 28, 36, 0.03);
}
.tl-head .tl-room.sticky {
  background: #f0f4f8;
  z-index: 4;
  font-size: 11px;
  font-weight: 700;
  color: #6b7c88;
  justify-content: center;
}
.tl-room strong {
  font-size: 13px;
  color: #0f1c24;
  font-variant-numeric: tabular-nums;
}
.tl-room small {
  font-size: 10px;
  color: #6b7c88;
}
.tl-room.hs-clean { border-left: 3px solid #1b7f4a; }
.tl-room.hs-dirty { border-left: 3px solid #c45c12; }
.tl-room.hs-occ { border-left: 3px solid #b42318; }
.tl-room.hs-ooo { border-left: 3px solid #667085; }
.tl-day {
  flex: 1;
  padding: 6px 2px;
  text-align: center;
  border-left: 1px solid #e4ecf2;
  display: flex;
  flex-direction: column;
  gap: 1px;
  line-height: 1.15;
}
.tl-day em {
  font-style: normal;
  font-size: 10px;
  font-weight: 600;
  color: #8a9aa6;
}
.tl-day strong {
  font-size: 11px;
  font-weight: 700;
  color: #3d4f5c;
}
.tl-day.today {
  background: #e8f4fb;
}
.tl-day.today em,
.tl-day.today strong {
  color: #0b7bb5;
}
.tl-row {
  border-top: 1px solid #e8eef3;
  min-height: 40px;
}
.tl-row:hover .tl-track {
  background-color: #fafcfd;
}
.tl-track {
  position: relative;
  flex: 1;
  background-image: repeating-linear-gradient(
    90deg,
    transparent,
    transparent calc(100% / 14 - 1px),
    #eef3f7 calc(100% / 14 - 1px),
    #eef3f7 calc(100% / 14)
  );
}
.tl-bar {
  position: absolute;
  top: 6px;
  height: 26px;
  border-radius: 4px;
  color: #fff;
  font-size: 11px;
  font-weight: 600;
  padding: 0 6px;
  display: flex;
  align-items: center;
  gap: 4px;
  cursor: pointer;
  overflow: hidden;
  background: #0b7bb5;
  z-index: 1;
}
.tl-bar:hover {
  filter: brightness(1.05);
  z-index: 2;
}
.tl-bar .name {
  flex: 1;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.tl-bar .more {
  border: 0;
  background: rgba(255, 255, 255, 0.22);
  color: #fff;
  border-radius: 3px;
  width: 18px;
  height: 18px;
  cursor: pointer;
  flex: 0 0 auto;
  line-height: 1;
}
.tl-bar.st-CHECKED_IN { background: #b42318; }
.tl-bar.st-CONFIRMED { background: #1b7f4a; }
.tl-bar.st-PENDING_PAYMENT { background: #c45c12; }
.tl_hint {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  z-index: 5;
  pointer-events: none;
  background: rgba(255, 255, 255, 0.92);
  border: 1px solid #dce6ee;
  border-radius: 8px;
  padding: 12px 16px;
  font-size: 13px;
  color: #5b6b76;
  text-align: center;
  max-width: 360px;
  box-shadow: 0 8px 24px rgba(15, 28, 36, 0.06);
}
.tl_empty {
  border: 1px dashed #d0dbe4;
  border-radius: 8px;
  padding: 40px 16px;
  text-align: center;
  color: #6b7c88;
  background: #fafcfd;
}
.tl_empty strong {
  display: block;
  color: #0f1c24;
  margin-bottom: 4px;
}
.tl_empty p {
  margin: 0;
  font-size: 13px;
}
.ctx {
  position: fixed;
  z-index: 2000;
  background: #fff;
  border: 1px solid #dce6ee;
  border-radius: 8px;
  box-shadow: 0 12px 28px rgba(15, 28, 36, 0.14);
  min-width: 160px;
  padding: 4px;
  display: flex;
  flex-direction: column;
}
.ctx button {
  border: 0;
  background: transparent;
  text-align: left;
  padding: 8px 10px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 13px;
}
.ctx button:hover {
  background: #eef5fa;
}
.ctx .danger {
  color: #b42318;
}
@media (max-width: 900px) {
  .tl_filters {
    grid-template-columns: 1fr;
    width: 100%;
  }
}
</style>
