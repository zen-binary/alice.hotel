<template>
  <PageContainer>
    <BreadcrumbComponent :pageTitle="'Bảng giá'" />

    <div class="box dp_block">
      <div class="box_section">
        <div class="search_box col_3">
          <ul>
            <li>
              <p>Xem</p>
              <SelectBoxBase id="viewMode" name="viewMode" v-model="viewMode" :data="viewOptions" />
            </li>
            <li>
              <p>Loại phòng</p>
              <SelectBoxBase id="filterType" name="filterType" v-model="filterType" :data="typeItems" @change="load" />
            </li>
          </ul>
          <div class="btn_group btn_end mg_t10">
            <button type="button" class="button btn_md btn_primary" @click="openCreate">Thêm khoảng giá</button>
          </div>
        </div>
      </div>
    </div>

    <div class="box dp_block">
      <div class="box_section">
        <div class="page_toparea">
          <h2 class="title-search">{{ viewMode === "week" ? "Giá theo tuần" : "Danh sách khoảng giá" }}</h2>
          <p class="hint">Ưu tiên hơn giá cơ bản · có thể ngừng bán</p>
        </div>

        <table v-if="viewMode === 'week'" class="table_base">
          <thead>
            <tr>
              <th>Loại phòng</th>
              <th v-for="d in weekDays" :key="d" :class="{ today: d === todayIso }">{{ d.slice(5) }}</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="t in weekTypes" :key="t.roomTypeId">
              <td>
                <strong>{{ t.name }}</strong>
                <div class="base">Base {{ money(t.basePrice) }}</div>
              </td>
              <td
                v-for="d in weekDays"
                :key="d"
                class="cell"
                :class="{ stop: dayRate(t.roomTypeId, d)?.stop, today: d === todayIso }"
              >
                <div class="price">{{ money(dayRate(t.roomTypeId, d)?.price ?? t.basePrice) }}</div>
                <div v-if="dayRate(t.roomTypeId, d)?.stop" class="stop-tag">Ngừng bán</div>
              </td>
            </tr>
          </tbody>
        </table>

        <table v-else class="table_base">
          <thead>
            <tr>
              <th>Loại phòng</th>
              <th>Tên</th>
              <th>Từ ngày</th>
              <th>Đến ngày</th>
              <th>Giá/đêm</th>
              <th>Trạng thái</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="r in rates" :key="r.rateId">
              <td><strong>{{ r.roomTypeName || r.roomTypeId }}</strong></td>
              <td>{{ r.name || "—" }}</td>
              <td>{{ r.startDate }}</td>
              <td>{{ r.endDate }}</td>
              <td>{{ money(r.price) }} ₫</td>
              <td>{{ r.stopSellYn === "Y" ? "Ngừng bán" : "Mở bán" }}</td>
              <td class="actions">
                <button type="button" class="button btn_sm btn_primary" @click="openEdit(r)">Sửa</button>
                <button type="button" class="button btn_sm btn_lightgray" @click="remove(r.rateId)">Xóa</button>
              </td>
            </tr>
            <tr v-if="!rates.length">
              <td colspan="7" class="empty">Chưa có khoảng giá — dùng giá cơ bản loại phòng.</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <TModal :is-open="dialog" :is-show-header="true" :is-show-footer="true" modal-id="rateForm" class-size="small" @close-modal="dialog = false">
      <template #title><strong>{{ form.rateId ? "Sửa giá" : "Thêm khoảng giá" }}</strong></template>
      <template #default>
        <table class="tbl_row">
          <colgroup><col style="width: 30%" /><col style="width: 70%" /></colgroup>
          <tbody>
            <tr>
              <th scope="row"><p class="required">Loại phòng</p></th>
              <td><SelectBoxBase id="rateType" name="rateType" v-model="form.roomTypeId" :data="typeSelectItems" /></td>
            </tr>
            <tr>
              <th scope="row"><p>Tên</p></th>
              <td><InputBase id="rateName" v-model="form.name" placeholder="Weekend / Lễ 30-4" /></td>
            </tr>
            <tr>
              <th scope="row"><p class="required">Từ ngày</p></th>
              <td><BaseDatePicker id="rateStart" v-model="form.startDate" /></td>
            </tr>
            <tr>
              <th scope="row"><p class="required">Đến ngày</p></th>
              <td><BaseDatePicker id="rateEnd" v-model="form.endDate" /></td>
            </tr>
            <tr>
              <th scope="row"><p class="required">Giá/đêm</p></th>
              <td><InputBase id="ratePrice" v-model="form.price" isNumber /></td>
            </tr>
            <tr>
              <th scope="row"><p>Trạng thái</p></th>
              <td><SelectBoxBase id="rateStop" name="rateStop" v-model="form.stopSellYn" :data="stopOptions" /></td>
            </tr>
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
import { computed, onMounted, reactive, ref } from "vue";
import http from "@/utils/http";
import PageContainer from "@/components/common/PageContainer.vue";
import BreadcrumbComponent from "@/components/common/BreadcrumbComponent.vue";
import InputBase from "@/components/common/input/InputBase.vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import BaseDatePicker from "@/components/common/input/BaseDatePicker.vue";
import TModal from "@/components/common/excel/TModal.vue";
import { aliceBookingStore } from "@/stores/aliceBooking";

const store = aliceBookingStore();
const rates = ref<any[]>([]);
const filterType = ref("");
const viewMode = ref("week");
const dialog = ref(false);
const form = reactive<any>({
  rateId: "",
  roomTypeId: "",
  name: "",
  startDate: "",
  endDate: "",
  price: "",
  stopSellYn: "N",
});

const todayIso = new Date().toISOString().slice(0, 10);
const money = (v: any) => Number(v || 0).toLocaleString("vi-VN");

const viewOptions = [
  { cdId: "week", cdNm: "Tuần" },
  { cdId: "list", cdNm: "Danh sách" },
];
const stopOptions = [
  { cdId: "N", cdNm: "Mở bán" },
  { cdId: "Y", cdNm: "Ngừng bán" },
];
const typeItems = computed(() => [
  { cdId: "", cdNm: "Tất cả" },
  ...(store.roomTypes || []).map((t: any) => ({ cdId: t.roomTypeId, cdNm: t.name })),
]);
const typeSelectItems = computed(() => typeItems.value.filter((x) => x.cdId));

const weekDays = computed(() => {
  const days: string[] = [];
  const start = new Date();
  for (let i = 0; i < 7; i++) {
    const d = new Date(start);
    d.setDate(start.getDate() + i);
    days.push(d.toISOString().slice(0, 10));
  }
  return days;
});
const weekTypes = computed(() => {
  let list = store.roomTypes || [];
  if (filterType.value) list = list.filter((t: any) => t.roomTypeId === filterType.value);
  return list;
});
const dayRate = (roomTypeId: string, day: string) => {
  const hit = (rates.value || []).find((r: any) => {
    if (r.roomTypeId !== roomTypeId) return false;
    const a = String(r.startDate).slice(0, 10);
    const b = String(r.endDate).slice(0, 10);
    return day >= a && day <= b;
  });
  if (!hit) return null;
  return { price: hit.price, stop: hit.stopSellYn === "Y" };
};

const load = async () => {
  await store.loadRoomTypes();
  const body: Record<string, string> = {};
  if (filterType.value) body.roomTypeId = filterType.value;
  const res = await http.post("/a/rates/list", body);
  rates.value = res.data?.data || [];
};

const openCreate = () => {
  Object.assign(form, {
    rateId: "",
    roomTypeId: typeSelectItems.value[0]?.cdId || "",
    name: "",
    startDate: todayIso,
    endDate: todayIso,
    price: "",
    stopSellYn: "N",
  });
  dialog.value = true;
};
const openEdit = (r: any) => {
  Object.assign(form, {
    ...r,
    startDate: String(r.startDate || "").slice(0, 10),
    endDate: String(r.endDate || "").slice(0, 10),
    price: String(r.price ?? ""),
    name: r.name || "",
  });
  dialog.value = true;
};

const save = async () => {
  if (!form.roomTypeId || !form.startDate || !form.endDate || form.price === "") {
    return alert("Nhập đủ loại phòng, ngày và giá");
  }
  const payload = { ...form, price: Number(form.price) };
  if (form.rateId) await http.put(`/a/rates/${form.rateId}`, payload);
  else await http.post("/a/rates", payload);
  dialog.value = false;
  await load();
};

const remove = async (id: string) => {
  if (!confirm("Xóa khoảng giá này?")) return;
  await http.delete(`/a/rates/${id}`);
  await load();
};

onMounted(load);
</script>

<style scoped>
.hint { margin: 0 0 12px; color: #717171; font-size: 13px; }
.base { font-size: 11px; color: #717171; margin-top: 2px; }
.cell { text-align: center; }
.cell.today, th.today { background: #eaf6fd; }
.cell.stop { background: #fff5f4; }
.price { font-weight: 700; }
.stop-tag { font-size: 10px; color: #b42318; font-weight: 700; }
.actions { text-align: right; white-space: nowrap; }
.actions .button { margin-left: 4px; }
.empty { text-align: center; color: #888; padding: 24px !important; }
</style>
