<template>
  <PageContainer>
    <BreadcrumbComponent :pageTitle="title" />

    <div class="box dp_block">
      <div class="box_section">
        <div class="pms_toolbar">
          <div class="fields" :class="mode === 'room' ? 'cols-4' : 'cols-2'">
            <label v-if="mode !== 'hotel'">
              <span>Lọc nhanh</span>
              <InputBase v-model="keyword" placeholder="Tìm theo mã / tên…" isSearch @search="noop" />
            </label>
            <template v-if="mode === 'room'">
              <label>
                <span>Loại phòng</span>
                <SelectBoxBase id="filterType" name="filterType" v-model="filterType" :data="typeFilterOptions" />
              </label>
              <label>
                <span>Tầng</span>
                <SelectBoxBase id="filterFloor" name="filterFloor" v-model="filterFloor" :data="floorOptions" />
              </label>
              <label>
                <span>Trạng thái</span>
                <SelectBoxBase id="filterStatus" name="filterStatus" v-model="filterStatus" :data="statusOptions" />
              </label>
            </template>
          </div>
          <div class="btns">
            <button
              v-if="mode !== 'hotel'"
              type="button"
              class="button btn_md btn_lightgray"
              @click="keyword = ''; filterType = ''; filterFloor = ''; filterStatus = ''"
            >
              Đặt lại
            </button>
            <button v-if="mode !== 'hotel'" type="button" class="button btn_md btn_primary" @click="openCreate">
              Thêm mới
            </button>
            <button v-else type="button" class="button btn_md btn_primary" @click="openEdit(rows[0] || {})">
              Sửa cấu hình
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="box dp_block">
      <div class="box_section">
        <div class="pms_list_head">
          <h2>{{ title }} · {{ filteredRows.length }} mục</h2>
          <p class="hint">{{ subtitle }}</p>
        </div>

        <div class="table_wrap">
          <table class="table_base">
            <colgroup>
              <col style="width: 22%" />
              <col style="width: 28%" />
              <col v-if="mode === 'room' || mode === 'voucher' || mode === 'service'" style="width: 14%" />
              <col />
              <col style="width: 160px" />
            </colgroup>
            <thead>
              <tr>
                <th>{{ mode === "room" ? "Số phòng" : "Tên / Mã" }}</th>
                <th>Thông tin</th>
                <th v-if="mode === 'room' || mode === 'voucher' || mode === 'service'">Trạng thái</th>
                <th>Mô tả</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="row in filteredRows" :key="row[idKey] || row.code">
                <td>
                  <strong>{{ cardTitle(row) }}</strong>
                  <div v-if="row.code && mode !== 'room'" class="code">{{ row.code }}</div>
                </td>
                <td class="meta-cell">{{ cardSub(row) }}</td>
                <td v-if="mode === 'room'">{{ statusLabel(row.houseStatus) }}</td>
                <td v-else-if="mode === 'voucher' || mode === 'service'">
                  {{ row.activeYn === "Y" ? "Đang dùng" : "Ngừng" }}
                </td>
                <td class="desc-cell">{{ cardBody(row) }}</td>
                <td class="actions">
                  <button type="button" class="button btn_sm btn_primary" @click="openEdit(row)">Sửa</button>
                  <button
                    v-if="mode === 'room' && row.houseStatus === 'VACANT_DIRTY'"
                    type="button"
                    class="button btn_sm btn_primary"
                    @click="quickClean(row)"
                  >
                    Sạch
                  </button>
                  <button
                    v-if="mode === 'room' && row.houseStatus !== 'OUT_OF_ORDER'"
                    type="button"
                    class="button btn_sm btn_lightgray"
                    @click="quickOoo(row)"
                  >
                    Bảo trì
                  </button>
                  <button
                    v-if="mode === 'room' && row.houseStatus === 'OUT_OF_ORDER'"
                    type="button"
                    class="button btn_sm btn_primary"
                    @click="quickClean(row)"
                  >
                    Mở lại
                  </button>
                </td>
              </tr>
              <tr v-if="!filteredRows.length">
                <td :colspan="mode === 'room' || mode === 'voucher' || mode === 'service' ? 5 : 4" class="empty">
                  Không có dữ liệu
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <TModal :is-open="dialog" :is-show-header="true" :is-show-footer="true" modal-id="masterCrud" class-size="medium" @close-modal="dialog = false">
      <template #title><strong>{{ form[idKey] ? "Cập nhật" : "Thêm" }} {{ title }}</strong></template>
      <template #default>
        <table v-if="mode === 'roomType'" class="tbl_row">
          <colgroup><col style="width: 28%" /><col style="width: 72%" /></colgroup>
          <tbody>
            <tr><th scope="row"><p class="required">Mã loại</p></th><td><InputBase id="rtCode" v-model="form.code" placeholder="STANDARD" /></td></tr>
            <tr><th scope="row"><p class="required">Tên hiển thị</p></th><td><InputBase id="rtName" v-model="form.name" placeholder="Standard" /></td></tr>
            <tr><th scope="row"><p>Mô tả</p></th><td><TextArea id="rtDesc" v-model="form.description" placeholder="Mô tả hạng phòng" /></td></tr>
            <tr><th scope="row"><p class="required">Giá cơ bản</p></th><td><InputBase id="rtPrice" v-model="form.basePrice" isNumber placeholder="800000" /></td></tr>
            <tr>
              <th scope="row"><p>Người lớn</p></th>
              <td><InputBase id="rtAdult" v-model="form.capacityAdult" isNumber /></td>
            </tr>
            <tr>
              <th scope="row"><p>Trẻ em</p></th>
              <td><InputBase id="rtChild" v-model="form.capacityChild" isNumber /></td>
            </tr>
            <tr><th scope="row"><p>Giường</p></th><td><InputBase id="rtBed" v-model="form.bedInfo" placeholder="1 giường lớn" /></td></tr>
            <tr><th scope="row"><p>Tiện nghi</p></th><td><InputBase id="rtAmen" v-model="form.amenityText" placeholder="Wifi, Điều hòa, Hồ bơi" /></td></tr>
            <tr>
              <th scope="row"><p>Ảnh hạng phòng</p></th>
              <td>
                <InputFileBase
                  v-if="dialog"
                  :key="roomTypeFileKey"
                  ref="roomTypeFileRef"
                  id="rtImgUpload"
                  name="rtImgUpload"
                  type="img"
                  :mode="form.roomTypeId ? 'edit' : 'create'"
                  :preview-flag="true"
                  :multiple="true"
                  :max-file="8"
                  :max-size="5"
                  :show-mess="false"
                  category="img"
                  section-name="roomType"
                  org-name="alice"
                  :refer-key="form.roomTypeId || undefined"
                  description="JPG, PNG · tối đa 5MB/ảnh · tối đa 8 ảnh"
                />
              </td>
            </tr>
          </tbody>
        </table>

        <table v-else-if="mode === 'room'" class="tbl_row">
          <colgroup><col style="width: 28%" /><col style="width: 72%" /></colgroup>
          <tbody>
            <tr><th scope="row"><p class="required">Số phòng</p></th><td><InputBase id="rmNo" v-model="form.roomNumber" /></td></tr>
            <tr><th scope="row"><p class="required">Loại phòng</p></th><td><SelectBoxBase id="rmType" name="rmType" v-model="form.roomTypeId" :data="typeSelectOptions" /></td></tr>
            <tr><th scope="row"><p>Tầng</p></th><td><InputBase id="rmFloor" v-model="form.floorNo" isNumber /></td></tr>
            <tr><th scope="row"><p>Trạng thái</p></th><td><SelectBoxBase id="rmStatus" name="rmStatus" v-model="form.houseStatus" :data="statusSelectOptions" /></td></tr>
            <tr><th scope="row"><p>Đang bán</p></th><td><SelectBoxBase id="rmActive" name="rmActive" v-model="form.activeYn" :data="ynOptions" /></td></tr>
            <tr><th scope="row"><p>Ghi chú</p></th><td><InputBase id="rmNote" v-model="form.note" /></td></tr>
          </tbody>
        </table>

        <table v-else-if="mode === 'service'" class="tbl_row">
          <colgroup><col style="width: 28%" /><col style="width: 72%" /></colgroup>
          <tbody>
            <tr><th scope="row"><p class="required">Mã</p></th><td><InputBase id="svCode" v-model="form.code" /></td></tr>
            <tr><th scope="row"><p class="required">Tên</p></th><td><InputBase id="svName" v-model="form.name" /></td></tr>
            <tr><th scope="row"><p>Nhóm</p></th><td><SelectBoxBase id="svCat" name="svCat" v-model="form.category" :data="categoryOptions" /></td></tr>
            <tr><th scope="row"><p class="required">Đơn giá</p></th><td><InputBase id="svPrice" v-model="form.unitPrice" isNumber /></td></tr>
            <tr><th scope="row"><p>Đơn vị</p></th><td><InputBase id="svUnit" v-model="form.unitName" placeholder="lần" /></td></tr>
            <tr><th scope="row"><p>Trạng thái</p></th><td><SelectBoxBase id="svActive" name="svActive" v-model="form.activeYn" :data="ynOptions" /></td></tr>
          </tbody>
        </table>

        <table v-else-if="mode === 'voucher'" class="tbl_row">
          <colgroup><col style="width: 28%" /><col style="width: 72%" /></colgroup>
          <tbody>
            <tr><th scope="row"><p class="required">Mã voucher</p></th><td><InputBase id="vcCode" v-model="form.code" /></td></tr>
            <tr><th scope="row"><p class="required">Tên chiến dịch</p></th><td><InputBase id="vcName" v-model="form.name" /></td></tr>
            <tr><th scope="row"><p>Loại giảm</p></th><td><SelectBoxBase id="vcType" name="vcType" v-model="form.discountType" :data="discountOptions" /></td></tr>
            <tr><th scope="row"><p>Giá trị</p></th><td><InputBase id="vcVal" v-model="form.discountValue" isNumber /></td></tr>
            <tr><th scope="row"><p>Giảm tối đa</p></th><td><InputBase id="vcMax" v-model="form.maxDiscount" isNumber /></td></tr>
            <tr><th scope="row"><p>Đơn tối thiểu</p></th><td><InputBase id="vcMin" v-model="form.minSpend" isNumber /></td></tr>
            <tr><th scope="row"><p>Bắt đầu</p></th><td><BaseDatePicker id="vcStart" v-model="form.startDate" /></td></tr>
            <tr><th scope="row"><p>Kết thúc</p></th><td><BaseDatePicker id="vcEnd" v-model="form.endDate" /></td></tr>
            <tr><th scope="row"><p>Giới hạn lượt</p></th><td><InputBase id="vcLimit" v-model="form.usageLimit" isNumber /></td></tr>
            <tr><th scope="row"><p>Trạng thái</p></th><td><SelectBoxBase id="vcActive" name="vcActive" v-model="form.activeYn" :data="ynOptions" /></td></tr>
          </tbody>
        </table>

        <table v-else-if="mode === 'hotel'" class="tbl_row">
          <colgroup><col style="width: 28%" /><col style="width: 72%" /></colgroup>
          <tbody>
            <tr><th scope="row"><p>Tên khách sạn</p></th><td><InputBase id="htName" v-model="form.name" /></td></tr>
            <tr><th scope="row"><p>Địa chỉ</p></th><td><InputBase id="htAddr" v-model="form.address" /></td></tr>
            <tr><th scope="row"><p>Điện thoại</p></th><td><InputBase id="htPhone" v-model="form.phone" /></td></tr>
            <tr><th scope="row"><p>Email</p></th><td><InputBase id="htEmail" v-model="form.email" /></td></tr>
            <tr><th scope="row"><p>Mô tả</p></th><td><TextArea id="htDesc" v-model="form.description" /></td></tr>
            <tr><th scope="row"><p>Giờ nhận</p></th><td><InputBase id="htIn" v-model="form.checkInTime" placeholder="14:00" /></td></tr>
            <tr><th scope="row"><p>Giờ trả</p></th><td><InputBase id="htOut" v-model="form.checkOutTime" placeholder="12:00" /></td></tr>
            <tr><th scope="row"><p>Hủy miễn phí (h)</p></th><td><InputBase id="htCancelH" v-model="form.cancelFreeHours" isNumber /></td></tr>
            <tr><th scope="row"><p>% hoàn</p></th><td><InputBase id="htRefund" v-model="form.cancelRefundPercent" isNumber /></td></tr>
            <tr><th scope="row"><p>% cọc</p></th><td><InputBase id="htDep" v-model="form.depositPercent" isNumber /></td></tr>
            <tr><th scope="row"><p>Phí nhận sớm</p></th><td><InputBase id="htEarly" v-model="form.earlyCheckinFee" isNumber /></td></tr>
            <tr><th scope="row"><p>Phí trả muộn</p></th><td><InputBase id="htLate" v-model="form.lateCheckoutFee" isNumber /></td></tr>
            <tr><th scope="row"><p>Hold (phút)</p></th><td><InputBase id="htHold" v-model="form.holdMinutes" isNumber /></td></tr>
          </tbody>
        </table>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_lightgray popup_close" @click="dialog = false">Huỷ</button>
        <button type="button" class="button btn_md btn_primary" @click="save">Lưu</button>
      </template>
    </TModal>
  </PageContainer>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive, ref, watch } from "vue";
import { useRoute } from "vue-router";
import PageContainer from "@/components/common/PageContainer.vue";
import BreadcrumbComponent from "@/components/common/BreadcrumbComponent.vue";
import InputBase from "@/components/common/input/InputBase.vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import TextArea from "@/components/common/input/TextArea.vue";
import BaseDatePicker from "@/components/common/input/BaseDatePicker.vue";
import InputFileBase from "@/components/common/input/InputFileBase.vue";
import TModal from "@/components/common/excel/TModal.vue";
import { aliceBookingStore } from "@/stores/aliceBooking";
import { getFileUploaded } from "@/stores/common/fileMng/fileMng.service";
import http from "@/utils/http";
import { ALICE_ROOM_TYPES } from "@/stores/aliceBooking/aliceBooking.service";

const route = useRoute();
const store = aliceBookingStore();
const dialog = ref(false);
const roomTypeFileRef = ref<any>(null);
const roomTypeFileKey = ref(0);
const keyword = ref("");
const filterType = ref("");
const filterFloor = ref("");
const filterStatus = ref("");
const form = reactive<Record<string, any>>({
  code: "",
  name: "",
  description: "",
  basePrice: "",
  capacityAdult: "",
  capacityChild: "",
  bedInfo: "",
  amenityText: "",
  imageUrls: "",
  roomNumber: "",
  roomTypeId: "",
  floorNo: "",
  houseStatus: "VACANT_CLEAN",
  activeYn: "Y",
  note: "",
  category: "OTHER",
  unitPrice: "",
  unitName: "",
  discountType: "PERCENT",
  discountValue: "",
  maxDiscount: "",
  minSpend: "",
  startDate: "",
  endDate: "",
  usageLimit: "",
  address: "",
  phone: "",
  email: "",
  checkInTime: "",
  checkOutTime: "",
  cancelFreeHours: "",
  cancelRefundPercent: "",
  depositPercent: "",
  earlyCheckinFee: "",
  lateCheckoutFee: "",
  holdMinutes: "",
});

const mode = computed(() => String(route.meta.master || "roomType"));
const title = computed(
  () =>
    ({ room: "Phòng", service: "Dịch vụ", voucher: "Mã giảm giá", hotel: "Cấu hình khách sạn", roomType: "Loại phòng" } as any)[
      mode.value
    ],
);
const subtitle = computed(
  () =>
    ({
      room: "Phòng vật lý, tầng, trạng thái buồng",
      service: "Dịch vụ thêm và minibar gắn phiếu thu",
      voucher: "Giảm theo phần trăm hoặc số tiền, có hạn dùng",
      hotel: "Chính sách hủy, đặt cọc, nhận sớm và trả muộn",
      roomType: "Danh mục hạng phòng đang bán",
    } as any)[mode.value],
);
const idKey = computed(
  () =>
    ({ room: "roomId", service: "serviceId", voucher: "voucherId", hotel: "hotelId", roomType: "roomTypeId" } as any)[
      mode.value
    ],
);

const ynOptions = [
  { cdId: "Y", cdNm: "Đang dùng" },
  { cdId: "N", cdNm: "Ngừng" },
];
const statusOptions = [
  { cdId: "", cdNm: "Tất cả" },
  { cdId: "VACANT_CLEAN", cdNm: "Sạch" },
  { cdId: "VACANT_DIRTY", cdNm: "Bẩn" },
  { cdId: "OCCUPIED", cdNm: "Đang ở" },
  { cdId: "OUT_OF_ORDER", cdNm: "Bảo trì" },
];
const statusSelectOptions = statusOptions.filter((x) => x.cdId);
const categoryOptions = [
  { cdId: "FNB", cdNm: "Ăn uống" },
  { cdId: "ROOM", cdNm: "Trong phòng" },
  { cdId: "TRANSPORT", cdNm: "Đưa đón" },
  { cdId: "OTHER", cdNm: "Khác" },
];
const discountOptions = [
  { cdId: "PERCENT", cdNm: "Phần trăm (%)" },
  { cdId: "AMOUNT", cdNm: "Số tiền" },
];
const typeFilterOptions = computed(() => [
  { cdId: "", cdNm: "Tất cả" },
  ...(store.roomTypes || []).map((t: any) => ({ cdId: t.roomTypeId, cdNm: t.name })),
]);
const typeSelectOptions = computed(() =>
  (store.roomTypes || []).map((t: any) => ({ cdId: t.roomTypeId, cdNm: t.name })),
);
const floorOptions = computed(() => {
  const floors = [...new Set((store.rooms || []).map((r: any) => r.floorNo).filter((n: any) => n != null))].sort(
    (a: any, b: any) => a - b,
  );
  return [{ cdId: "", cdNm: "Tất cả" }, ...floors.map((f) => ({ cdId: String(f), cdNm: `Tầng ${f}` }))];
});

const parseAmenities = (row: any) => {
  const m = String(row?.description || "").match(/\[Tiện nghi:\s*([^\]]+)\]/);
  return m ? m[1].trim() : "";
};
const stripAmenityTag = (desc: string) => String(desc || "").replace(/\s*\[Tiện nghi:[^\]]*\]\s*/g, "").trim();
const withAmenityTag = (desc: string, text: string) => {
  const base = stripAmenityTag(desc);
  const chips = String(text || "")
    .split(",")
    .map((s) => s.trim())
    .filter(Boolean);
  if (!chips.length) return base;
  return `${base}${base ? " " : ""}[Tiện nghi: ${chips.join(", ")}]`;
};

const rows = computed(() => {
  if (mode.value === "room") return store.rooms;
  if (mode.value === "service") return store.services;
  if (mode.value === "voucher") return store.vouchers;
  if (mode.value === "hotel") return store.hotel ? [store.hotel] : [];
  return store.roomTypes || [];
});

const filteredRows = computed(() => {
  let list = [...(rows.value || [])];
  if (mode.value === "room") {
    if (filterType.value) list = list.filter((r: any) => r.roomTypeId === filterType.value);
    if (filterFloor.value) list = list.filter((r: any) => String(r.floorNo) === filterFloor.value);
    if (filterStatus.value) list = list.filter((r: any) => r.houseStatus === filterStatus.value);
  }
  const q = keyword.value.trim().toLowerCase();
  if (!q) return list;
  return list.filter((r: any) => JSON.stringify(r).toLowerCase().includes(q));
});

const cardTitle = (row: any) => row.name || row.roomNumber || row.code || row.hotelId;
const cardSub = (row: any) => {
  if (mode.value === "room") return `${row.roomTypeName || row.roomTypeId} · Tầng ${row.floorNo ?? "-"}`;
  if (mode.value === "voucher") {
    const left = row.usageLimit == null ? "∞" : Math.max(0, Number(row.usageLimit) - Number(row.usedCount || 0));
    const kind = discountOptions.find((x) => x.cdId === row.discountType)?.cdNm || row.discountType;
    return `${kind} ${row.discountValue} · còn ${left} lượt`;
  }
  if (mode.value === "service") {
    const cat = categoryOptions.find((x) => x.cdId === row.category)?.cdNm || row.category;
    return `${cat} · ${Number(row.unitPrice || 0).toLocaleString("vi-VN")} ₫`;
  }
  if (mode.value === "hotel") return row.address;
  return `${Number(row.basePrice || 0).toLocaleString("vi-VN")} ₫/đêm · ${row.capacityAdult || 0} NL`;
};
const cardBody = (row: any) => {
  if (mode.value === "roomType") return stripAmenityTag(row.description) || row.bedInfo || "—";
  if (mode.value === "hotel") {
    return `Hủy ${row.cancelFreeHours || 0}h · hoàn ${row.cancelRefundPercent || 0}% · cọc ${row.depositPercent || 0}%`;
  }
  return row.note || row.description || (row.activeYn === "N" ? "Ngừng" : "Đang dùng");
};
const statusLabel = (s: string) =>
  ({ VACANT_CLEAN: "Sạch", VACANT_DIRTY: "Bẩn", OCCUPIED: "Đang ở", OUT_OF_ORDER: "Bảo trì" } as any)[s] || s;

const noop = () => undefined;

const resetForm = () => {
  Object.keys(form).forEach((k) => {
    if (typeof form[k] === "string") form[k] = "";
  });
  form.houseStatus = "VACANT_CLEAN";
  form.activeYn = "Y";
  form.category = "OTHER";
  form.discountType = "PERCENT";
  delete form.roomId;
  delete form.roomTypeId;
  delete form.serviceId;
  delete form.voucherId;
  delete form.hotelId;
};

const num = (v: any) => (v === "" || v == null ? null : Number(v));

const load = async () => {
  if (mode.value === "room") await Promise.all([store.loadRooms(), store.loadRoomTypes()]);
  else if (mode.value === "service") await store.loadServices();
  else if (mode.value === "voucher") await store.loadVouchers();
  else if (mode.value === "hotel") await store.loadHotel();
  else await store.loadRoomTypes();
};

const openCreate = () => {
  resetForm();
  roomTypeFileKey.value += 1;
  dialog.value = true;
};
const openEdit = (row: any) => {
  resetForm();
  Object.assign(form, row || {});
  Object.keys(form).forEach((k) => {
    if (form[k] == null) form[k] = "";
    else if (typeof form[k] === "number") form[k] = String(form[k]);
  });
  if (mode.value === "roomType") {
    form.amenityText = parseAmenities(row);
    form.description = stripAmenityTag(row.description);
  }
  if (mode.value === "voucher") {
    form.startDate = String(row.startDate || "").slice(0, 10);
    form.endDate = String(row.endDate || "").slice(0, 10);
  }
  roomTypeFileKey.value += 1;
  dialog.value = true;
};

const syncRoomTypeImageUrls = async (roomTypeId: string) => {
  if (!roomTypeId) return;
  try {
    const res = await getFileUploaded({
      fimFileCategory: "img",
      fimReferKeyId: roomTypeId,
      fimSectionName: "roomType",
    });
    const list = res?.data?.data || [];
    const urls = list
      .map((f: any) => f.urlFile)
      .filter(Boolean)
      .join(",");
    const current = (store.roomTypes || []).find((t: any) => t.roomTypeId === roomTypeId);
    if (!current) return;
    await http.put(`${ALICE_ROOM_TYPES}/${roomTypeId}`, { ...current, imageUrls: urls });
    await store.loadRoomTypes();
  } catch (e) {
    console.warn("Không đồng bộ được URL ảnh loại phòng", e);
  }
};

const save = async () => {
  if (mode.value === "room") {
    await store.saveRoom({
      roomId: form.roomId || undefined,
      roomTypeId: form.roomTypeId,
      roomNumber: form.roomNumber,
      floorNo: num(form.floorNo),
      houseStatus: form.houseStatus,
      activeYn: form.activeYn,
      note: form.note,
    });
  } else if (mode.value === "service") {
    await store.saveService({
      serviceId: form.serviceId || undefined,
      code: form.code,
      name: form.name,
      category: form.category,
      unitPrice: num(form.unitPrice),
      unitName: form.unitName,
      activeYn: form.activeYn,
    });
  } else if (mode.value === "voucher") {
    await store.saveVoucher({
      voucherId: form.voucherId || undefined,
      code: form.code,
      name: form.name,
      discountType: form.discountType,
      discountValue: num(form.discountValue),
      maxDiscount: num(form.maxDiscount),
      minSpend: num(form.minSpend),
      startDate: form.startDate || null,
      endDate: form.endDate || null,
      usageLimit: num(form.usageLimit),
      activeYn: form.activeYn,
    });
  } else if (mode.value === "hotel") {
    await store.saveHotel({
      hotelId: form.hotelId || undefined,
      name: form.name,
      address: form.address,
      phone: form.phone,
      email: form.email,
      description: form.description,
      checkInTime: form.checkInTime,
      checkOutTime: form.checkOutTime,
      cancelFreeHours: num(form.cancelFreeHours),
      cancelRefundPercent: num(form.cancelRefundPercent),
      depositPercent: num(form.depositPercent),
      earlyCheckinFee: num(form.earlyCheckinFee),
      lateCheckoutFee: num(form.lateCheckoutFee),
      holdMinutes: num(form.holdMinutes),
      imageUrl: form.imageUrl || undefined,
    });
  } else {
    const payload = {
      roomTypeId: form.roomTypeId || undefined,
      code: form.code,
      name: form.name,
      basePrice: num(form.basePrice),
      capacityAdult: num(form.capacityAdult),
      capacityChild: num(form.capacityChild),
      bedInfo: form.bedInfo,
      imageUrls: form.imageUrls,
      description: withAmenityTag(form.description, form.amenityText),
    };
    const saved = await store.saveRoomType(payload);
    const roomTypeId = saved?.roomTypeId || form.roomTypeId;
    if (roomTypeId) {
      if (roomTypeFileRef.value?.upLoadFile) {
        await roomTypeFileRef.value.upLoadFile(roomTypeId);
      }
      await syncRoomTypeImageUrls(roomTypeId);
    }
  }
  dialog.value = false;
  await load();
};

const quickClean = async (row: any) => {
  await store.setHouseStatus(row.roomId, "VACANT_CLEAN");
  await load();
};
const quickOoo = async (row: any) => {
  await store.setHouseStatus(row.roomId, "OUT_OF_ORDER");
  await load();
};

watch(mode, load, { immediate: true });
onMounted(load);
</script>

<style scoped>
.code {
  margin-top: 2px;
  font-size: 11px;
  color: #8a9aa6;
  font-weight: 600;
  letter-spacing: 0.02em;
}
</style>
