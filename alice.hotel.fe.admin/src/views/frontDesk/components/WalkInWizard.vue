<template>
  <TModal
    :is-open="open"
    :is-show-header="true"
    :is-show-footer="true"
    modal-id="walkInWizard"
    class-size="medium"
    @close-modal="emit('update:open', false)"
  >
    <template #title><strong>Khách vãng lai tại quầy</strong></template>
    <template #default>
      <div class="step_group mg_b15">
        <ul>
          <li v-for="i in 3" :key="i">
            <button type="button" class="button" :class="{ btn_active: step === i }" @click="step = i">{{ i }}</button>
          </li>
        </ul>
      </div>
      <p class="step_label">{{ stepLabels[step - 1] }}</p>

      <div v-show="step === 1">
        <table class="tbl_row">
          <colgroup>
            <col style="width: 18%" /><col style="width: 32%" />
            <col style="width: 18%" /><col style="width: 32%" />
          </colgroup>
          <tbody>
            <tr>
              <th scope="row"><p class="required">Tên khách</p></th>
              <td><InputBase id="wiName" v-model="form.guestName" /></td>
              <th scope="row"><p>SĐT</p></th>
              <td><InputBase id="wiPhone" v-model="form.guestPhone" /></td>
            </tr>
            <tr>
              <th scope="row"><p>CCCD</p></th>
              <td><InputBase id="wiId" v-model="form.guestIdNumber" /></td>
              <th scope="row"><p class="required">Loại phòng</p></th>
              <td><SelectBoxBase id="wiType" name="wiType" v-model="form.roomTypeId" :data="typeItems" /></td>
            </tr>
            <tr>
              <th scope="row"><p>Nhận phòng</p></th>
              <td><BaseDatePicker id="wiIn" v-model="form.checkIn" /></td>
              <th scope="row"><p>Trả phòng</p></th>
              <td><BaseDatePicker id="wiOut" v-model="form.checkOut" /></td>
            </tr>
          </tbody>
        </table>
      </div>

      <div v-show="step === 2">
        <p class="hint">Chỉ hiện phòng trống sạch trong khoảng ngày</p>
        <div class="select_checkbox">
          <RadiobuttonBase
            v-for="r in availableRooms"
            :key="r.roomId"
            :id="`wiRoom_${r.roomId}`"
            name="wiRoom"
            v-model="form.roomId"
            :value="r.roomId"
            :label="`P.${r.roomNumber} · Tầng ${r.floorNo ?? '-'} · ${r.houseStatus === 'VACANT_CLEAN' ? 'Sạch' : r.houseStatus === 'VACANT_DIRTY' ? 'Bẩn' : r.houseStatus === 'OCCUPIED' ? 'Đang ở' : 'Bảo trì'}`"
            :isDisable="r.houseStatus !== 'VACANT_CLEAN'"
          />
        </div>
        <p v-if="!availableRooms.length" class="empty">Không còn phòng trống loại này</p>
      </div>

      <div v-show="step === 3">
        <table class="tbl_row">
          <colgroup><col style="width: 20%" /><col style="width: 80%" /></colgroup>
          <tbody>
            <tr>
              <th scope="row"><p>Dịch vụ kèm</p></th>
              <td>
                <ListCheckBoxBase
                  id="wiSvc"
                  name="wiSvc"
                  v-model="form.serviceIds"
                  :list-data="serviceItems"
                  :use-array="true"
                />
              </td>
            </tr>
            <tr>
              <th scope="row"><p>Voucher</p></th>
              <td><InputBase id="wiVoucher" v-model="form.voucherCode" placeholder="Tuỳ chọn" /></td>
            </tr>
          </tbody>
        </table>
        <div class="preview">
          <div><span>Số đêm</span><b>{{ nights }}</b></div>
          <div><span>Tiền phòng (ước tính)</span><b>{{ money(roomEst) }}</b></div>
          <div><span>Dịch vụ</span><b>{{ money(svcEst) }}</b></div>
          <div class="sum"><span>Tạm tính</span><b>{{ money(roomEst + svcEst) }}</b></div>
        </div>
      </div>
    </template>
    <template #footer>
      <button v-if="step > 1" type="button" class="button btn_md btn_lightgray" @click="step -= 1">Quay lại</button>
      <button type="button" class="button btn_md btn_lightgray popup_close" @click="emit('update:open', false)">Đóng</button>
      <button
        v-if="step < 3"
        type="button"
        class="button btn_md btn_primary"
        :disabled="(step === 1 && (!form.guestName || !form.roomTypeId)) || (step === 2 && !form.roomId)"
        @click="step += 1"
      >
        Tiếp tục
      </button>
      <button v-else type="button" class="button btn_md btn_primary" :disabled="loading" @click="submit">
        {{ loading ? "Đang tạo…" : "Tạo & xác nhận" }}
      </button>
    </template>
  </TModal>
</template>

<script setup lang="ts">
import { computed, reactive, ref, watch } from "vue";
import InputBase from "@/components/common/input/InputBase.vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import BaseDatePicker from "@/components/common/input/BaseDatePicker.vue";
import ListCheckBoxBase from "@/components/common/input/ListCheckBoxBase.vue";
import RadiobuttonBase from "@/components/common/input/RadiobuttonBase.vue";
import TModal from "@/components/common/excel/TModal.vue";

const props = defineProps<{
  open: boolean;
  roomTypes: any[];
  rooms: any[];
  services: any[];
  timeline: any[];
  loading?: boolean;
}>();

const emit = defineEmits<{
  (e: "update:open", v: boolean): void;
  (e: "submit", payload: any): void;
}>();

const step = ref(1);
const stepLabels = ["Khách & ngày", "Chọn phòng", "DV & xác nhận"];
const form = reactive({
  guestName: "",
  guestPhone: "",
  guestIdNumber: "",
  roomTypeId: "",
  roomId: "",
  checkIn: new Date().toISOString().slice(0, 10),
  checkOut: new Date(Date.now() + 86400000).toISOString().slice(0, 10),
  voucherCode: "",
  serviceIds: [] as string[],
});

const money = (v: any) => Number(v || 0).toLocaleString("vi-VN") + " ₫";
const typeItems = computed(() =>
  (props.roomTypes || []).map((t: any) => ({
    cdId: t.roomTypeId,
    cdNm: `${t.name} · ${money(t.basePrice)}/đêm`,
    base: Number(t.basePrice || 0),
  })),
);
const serviceItems = computed(() =>
  (props.services || []).map((s: any) => ({
    cdId: s.serviceId,
    cdNm: `${s.name} (${money(s.unitPrice)})`,
    price: Number(s.unitPrice || 0),
  })),
);

const availableRooms = computed(() => {
  if (!form.roomTypeId) return [];
  const occupied = new Set(
    (props.timeline || [])
      .filter((b) => {
        const inOk = String(b.checkIn) < form.checkOut;
        const outOk = String(b.checkOut) > form.checkIn;
        return inOk && outOk && ["CONFIRMED", "CHECKED_IN", "PENDING_PAYMENT"].includes(b.status);
      })
      .map((b) => b.roomId)
      .filter(Boolean),
  );
  return (props.rooms || []).filter(
    (r: any) => r.roomTypeId === form.roomTypeId && r.activeYn !== "N" && !occupied.has(r.roomId),
  );
});

const nights = computed(() => {
  const a = new Date(form.checkIn).getTime();
  const b = new Date(form.checkOut).getTime();
  return Math.max(1, Math.round((b - a) / 86400000));
});
const roomEst = computed(() => {
  const t = typeItems.value.find((x) => x.cdId === form.roomTypeId);
  return (t?.base || 0) * nights.value;
});
const svcEst = computed(() =>
  form.serviceIds.reduce((s, id) => s + (serviceItems.value.find((x) => x.cdId === id)?.price || 0), 0),
);

watch(
  () => props.open,
  (v) => {
    if (v) {
      step.value = 1;
      form.roomId = "";
      form.serviceIds = [];
      form.voucherCode = "";
    }
  },
);
watch(
  () => [form.roomTypeId, form.checkIn, form.checkOut],
  () => {
    form.roomId = "";
  },
);

const submit = () => {
  const { serviceIds, ...rest } = form;
  emit("submit", {
    ...rest,
    services: serviceIds.map((id) => ({ serviceId: id, qty: 1 })),
    payAtHotel: true,
    source: "WALK_IN",
  });
};
</script>

<style scoped>
.step_label { margin: 0 0 12px; font-size: 13px; font-weight: 600; color: #0f1c24; }
.hint { font-size: 13px; color: #5b6b76; margin: 0 0 8px; }
.empty { color: #5b6b76; font-size: 13px; margin: 8px 0; }
.preview {
  background: #f5f9fc;
  border: 1px solid #e6edf2;
  border-radius: 8px;
  padding: 12px 14px;
  margin-top: 12px;
}
.preview > div { display: flex; justify-content: space-between; padding: 4px 0; font-size: 13px; }
.preview .sum {
  font-weight: 700;
  font-size: 15px;
  margin-top: 6px;
  border-top: 1px solid #e6edf2;
  padding-top: 8px;
}
.button:disabled { opacity: 0.5; cursor: not-allowed; }
</style>
