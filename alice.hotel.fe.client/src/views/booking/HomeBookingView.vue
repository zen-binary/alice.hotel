<template>
  <div class="booking-page">
    <BannerComponent
      variant="hotel"
      title="Alice Hotel"
      :description="bannerLines"
      :info="bannerInfo"
      class-description="banner_description_login"
    />

    <PageContainer class="booking_flow">
      <div class="shell">
      <ol class="steps">
        <li :class="{ on: step === 1, done: step > 1 }">1. Tìm phòng</li>
        <li :class="{ on: step === 2, done: step > 2 }">2. Chi tiết</li>
        <li :class="{ on: step === 3, done: step > 3 }">3. Khách & DV</li>
        <li :class="{ on: step === 4 }">4. Xác nhận</li>
      </ol>

      <div class="layout">
        <div class="main">
          <section v-show="step === 1" class="panel">
            <h2>Tìm phòng trống</h2>
            <div class="search-bar">
              <label>
                <span>Nhận phòng</span>
                <input v-model="form.checkIn" type="date" />
              </label>
              <label>
                <span>Trả phòng</span>
                <input v-model="form.checkOut" type="date" />
              </label>
              <label>
                <span>Người lớn</span>
                <input v-model.number="form.adults" type="number" min="1" />
              </label>
              <label>
                <span>Trẻ em</span>
                <input v-model.number="form.children" type="number" min="0" />
              </label>
              <button type="button" class="btn primary" @click="doSearch">Tìm phòng</button>
            </div>

            <div class="room-grid">
              <article v-for="rt in store.availability" :key="rt.roomTypeId" class="room" @click="selectType(rt)">
                <div class="room-media" :style="coverStyle(rt)">
                  <span class="avail">{{ rt.availableRooms }} trống</span>
                </div>
                <div class="room-body">
                  <div class="room-title">
                    <h3>{{ rt.name }}</h3>
                    <p v-if="rt.avgRating">★ {{ Number(rt.avgRating).toFixed(1) }}</p>
                  </div>
                  <p class="desc">{{ plainDesc(rt.description) }}</p>
                  <p class="beds">{{ rt.bedInfo || "—" }} · {{ rt.capacityAdult }} NL / {{ rt.capacityChild }} TE</p>
                  <div class="room-foot">
                    <strong>{{ money(rt.estimatedTotal) }}</strong>
                    <span>/ {{ nights }} đêm</span>
                    <button type="button" class="btn primary sm">Chọn</button>
                  </div>
                </div>
              </article>
            </div>
            <p v-if="searched && !store.availability.length" class="empty">Không còn phòng phù hợp khoảng ngày này.</p>
          </section>

          <section v-if="step === 2 && selected" class="panel">
            <h2>{{ selected.name }}</h2>
            <div class="detail-grid">
              <div class="detail-media" :style="coverStyle(selected)" />
              <div>
                <p>{{ plainDesc(selected.description) }}</p>
                <ul class="facts">
                  <li>Sức chứa: {{ selected.capacityAdult }} người lớn · {{ selected.capacityChild }} trẻ</li>
                  <li>Giường: {{ selected.bedInfo || "—" }}</li>
                  <li>Ước tính: {{ money(selected.estimatedTotal) }} ({{ nights }} đêm)</li>
                </ul>
                <div class="row-btns">
                  <button type="button" class="btn ghost" @click="step = 1">Quay lại</button>
                  <button type="button" class="btn primary" @click="step = 3">Tiếp tục</button>
                </div>
              </div>
            </div>
          </section>

          <section v-show="step === 3" class="panel">
            <h2>Thông tin khách & dịch vụ</h2>
            <div class="form-grid">
              <label class="full"><span>Họ tên khách lưu trú *</span><input v-model="book.guestName" /></label>
              <label><span>Số điện thoại</span><input v-model="book.guestPhone" /></label>
              <label><span>Email nhận xác nhận</span><input v-model="book.guestEmail" type="email" /></label>
              <label><span>Mã voucher</span><input v-model="book.voucherCode" placeholder="VD: ALICE10" /></label>
              <label class="full">
                <span>Dịch vụ thêm</span>
                <select v-model="book.serviceIds" multiple>
                  <option v-for="s in serviceItems" :key="s.value" :value="s.value">{{ s.title }}</option>
                </select>
                <small>Giữ Ctrl/Cmd để chọn nhiều</small>
              </label>
              <label class="check full">
                <input v-model="book.payAtHotel" type="checkbox" />
                Thanh toán tại khách sạn (không cọc online)
              </label>
            </div>
            <div class="row-btns">
              <button type="button" class="btn ghost" @click="step = 2">Quay lại</button>
              <button type="button" class="btn primary" @click="goConfirm">Xem xác nhận</button>
            </div>
          </section>

          <section v-show="step === 4" class="panel">
            <h2>Xác nhận & thanh toán</h2>
            <div class="policy">
              Chính sách hủy: miễn phí trước <b>{{ store.hotel?.cancelFreeHours || 48 }} giờ</b> so với ngày nhận phòng;
              hoàn <b>{{ store.hotel?.cancelRefundPercent || 100 }}%</b> phần đã thanh toán nếu đủ điều kiện.
            </div>
            <div class="confirm-box">
              <div><span>Loại phòng</span><b>{{ selected?.name }}</b></div>
              <div><span>Ngày</span><b>{{ form.checkIn }} → {{ form.checkOut }}</b></div>
              <div><span>Khách</span><b>{{ book.guestName }}</b></div>
              <div><span>Thanh toán</span><b>{{ book.payAtHotel ? "Tại khách sạn" : "Cọc online VNPay" }}</b></div>
            </div>

            <div v-if="!store.current" class="row-btns">
              <button type="button" class="btn ghost" @click="step = 3">Quay lại</button>
              <button type="button" class="btn primary" :disabled="busy" @click="submitBook">
                {{ busy ? "Đang tạo…" : "Tạo đơn đặt phòng" }}
              </button>
            </div>
            <div v-else class="success-box">
              <p>Mã đơn <strong>{{ store.current.bookingCode }}</strong> · {{ store.current.status }}</p>
              <div class="row-btns">
                <button
                  v-if="!book.payAtHotel && store.current.status === 'PENDING_PAYMENT'"
                  type="button"
                  class="btn primary"
                  @click="payNow"
                >
                  Thanh toán cọc VNPay ({{ store.hotel?.depositPercent || 30 }}%)
                </button>
                <router-link class="btn ghost" :to="{ name: 'MyBookings' }">Xem lịch sử</router-link>
                <button type="button" class="btn ghost" @click="resetFlow">Đặt tiếp</button>
              </div>
            </div>
          </section>
        </div>

        <aside class="summary">
          <h3>Tóm tắt giá</h3>
          <div class="line"><span>Loại phòng</span><span>{{ selected?.name || "—" }}</span></div>
          <div class="line"><span>Số đêm</span><span>{{ nights }}</span></div>
          <div class="line"><span>Tiền phòng</span><span>{{ money(selected?.estimatedTotal || 0) }}</span></div>
          <div class="line"><span>Dịch vụ</span><span>{{ money(extrasTotal) }}</span></div>
          <div class="line"><span>Cọc dự kiến</span><span>{{ money(book.payAtHotel ? 0 : depositPreview) }}</span></div>
          <div class="line total"><span>Tạm tính</span><span>{{ money(grandPreview) }}</span></div>
          <p class="note">Giá cuối sau voucher được chốt khi tạo đơn.</p>
        </aside>
      </div>
      </div>
    </PageContainer>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive, ref } from "vue";
import { useRouter } from "vue-router";
import BannerComponent from "@/components/common/BannerComponent.vue";
import PageContainer from "@/components/common/PageContainer.vue";
import { clientBookingStore } from "@/stores/clientBooking";
import { getUserInfo } from "@/utils/storage";
import { SCREEN } from "@/router/screen";

const store = clientBookingStore();
const router = useRouter();
const step = ref(1);
const searched = ref(false);
const busy = ref(false);
const selected = ref<any>(null);

const form = reactive({
  checkIn: new Date().toISOString().slice(0, 10),
  checkOut: new Date(Date.now() + 86400000).toISOString().slice(0, 10),
  adults: 2,
  children: 0,
});
const book = reactive({
  guestName: "",
  guestPhone: "",
  guestEmail: "",
  voucherCode: "",
  serviceIds: [] as string[],
  payAtHotel: false,
});

const bannerLines = computed(() => [store.hotel?.name || "Đặt phòng", "trực tuyến"]);
const bannerInfo = computed(() => {
  const raw = String(store.hotel?.description || "").replace(/&mdash;/g, "—");
  const pitch =
    !raw || /datn demo/i.test(raw)
      ? "Giá rõ trước khi giữ chỗ. Cọc VNPay hoặc thanh toán tại khách sạn."
      : raw;
  const address = store.hotel?.address === "Ha Noi, Viet Nam" ? "Hà Nội, Việt Nam" : store.hotel?.address;
  return [
    pitch,
    address
      ? `${address} · Nhận ${store.hotel.checkInTime || "14:00"} / Trả ${store.hotel.checkOutTime || "12:00"}`
      : "Nhận phòng 14:00 · Trả phòng 12:00",
  ];
});
const money = (v: any) => Number(v || 0).toLocaleString("vi-VN") + " ₫";
const nights = computed(() => {
  const a = new Date(form.checkIn).getTime();
  const b = new Date(form.checkOut).getTime();
  return Math.max(1, Math.round((b - a) / 86400000));
});
const serviceItems = computed(() =>
  (store.services || []).map((s: any) => ({
    title: `${s.name} — ${money(s.unitPrice)}`,
    value: s.serviceId,
    price: Number(s.unitPrice || 0),
  })),
);
const extrasTotal = computed(() =>
  book.serviceIds.reduce((sum, id) => sum + (serviceItems.value.find((s) => s.value === id)?.price || 0), 0),
);
const grandPreview = computed(() => Number(selected.value?.estimatedTotal || 0) + extrasTotal.value);
const depositPreview = computed(() =>
  Math.round((grandPreview.value * Number(store.hotel?.depositPercent || 30)) / 100),
);

const plainDesc = (d: string) => String(d || "").replace(/\s*\[Tiện nghi:[^\]]*\]\s*/g, "").trim();
const firstImage = (rt: any) =>
  String(rt?.imageUrls || "")
    .split(",")
    .map((s) => s.trim())
    .find(Boolean);
const coverStyle = (rt: any) => {
  const img = firstImage(rt);
  if (img) return { backgroundImage: `url(${img})` };
  return {};
};

const doSearch = async () => {
  searched.value = true;
  await store.search({ ...form });
};
const selectType = (rt: any) => {
  selected.value = rt;
  step.value = 2;
};
const goConfirm = () => {
  if (!book.guestName) return alert("Nhập tên khách");
  step.value = 4;
};
const submitBook = async () => {
  if (!getUserInfo()) {
    alert("Vui lòng đăng nhập (vai trò Khách hàng) trước khi đặt phòng.");
    router.push({ name: SCREEN.login.name });
    return;
  }
  busy.value = true;
  try {
    await store.createBooking({
      roomTypeId: selected.value.roomTypeId,
      checkIn: form.checkIn,
      checkOut: form.checkOut,
      adults: form.adults,
      children: form.children,
      guestName: book.guestName,
      guestPhone: book.guestPhone,
      guestEmail: book.guestEmail,
      voucherCode: book.voucherCode || undefined,
      services: book.serviceIds.map((id) => ({ serviceId: id, qty: 1 })),
      payAtHotel: book.payAtHotel,
      source: "ONLINE",
    });
  } catch {
    alert("Không tạo được đơn. Kiểm tra phòng trống / voucher / tài khoản.");
  } finally {
    busy.value = false;
  }
};
const payNow = async () => {
  const pay = await store.payVnPay(store.current.bookingId);
  if (pay?.paymentUrl) window.location.href = pay.paymentUrl;
};
const resetFlow = () => {
  selected.value = null;
  store.$patch({ current: null });
  step.value = 1;
};

onMounted(async () => {
  await Promise.all([store.loadHotel(), store.loadRoomTypes(), store.loadServices()]);
  await doSearch();
});
</script>

<style scoped>
.booking-page {
  --ink: #14212b;
  --muted: #5d6b75;
  --line: #e7e1d6;
  --accent: #0e2438;
  --accent-2: #c4a35a;
  min-height: calc(100dvh - 72px);
  background: #f6f4ef;
  color: var(--ink);
}
.hero {
  position: relative;
  color: #fff;
  padding: 48px 20px 64px;
  overflow: hidden;
}
.hero-bg {
  position: absolute;
  inset: 0;
  background:
    linear-gradient(120deg, rgba(8, 20, 34, 0.88), rgba(14, 36, 56, 0.72)),
    radial-gradient(circle at 85% 15%, rgba(196, 163, 90, 0.42), transparent 36%),
    #0e2438;
}
.hero-inner {
  position: relative;
  max-width: 1120px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  gap: 24px;
  flex-wrap: wrap;
}
.eyebrow {
  margin: 0 0 8px;
  letter-spacing: 0.16em;
  text-transform: uppercase;
  font-size: 11px;
  opacity: 0.75;
}
.hero h1 {
  margin: 0 0 10px;
  font-family: Georgia, "Times New Roman", serif;
  font-size: clamp(2rem, 4vw, 2.8rem);
  font-weight: 500;
}
.lead { margin: 0; max-width: 560px; opacity: 0.92; line-height: 1.5; }
.meta { margin: 12px 0 0; opacity: 0.7; font-size: 14px; }
.hero-points {
  list-style: none;
  margin: 0;
  padding: 16px 18px;
  display: grid;
  gap: 8px;
  min-width: 240px;
  border: 1px solid rgba(255, 255, 255, 0.16);
  border-radius: 16px;
  background: rgba(255, 255, 255, 0.06);
}
.hero-points li {
  font-size: 14px;
  padding-left: 14px;
  position: relative;
}
.hero-points li::before {
  content: "";
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: #c4a35a;
  position: absolute;
  left: 0;
  top: 0.55em;
}
.btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  border-radius: 999px;
  padding: 10px 16px;
  text-decoration: none;
  font-size: 14px;
  font-weight: 600;
  border: 1px solid transparent;
  cursor: pointer;
}
.booking-page :deep(.block-banner.hotel .block-banner__content) {
  box-sizing: border-box;
  width: min(var(--main-width-vertical), calc(100% - 4rem));
  max-width: var(--main-width-vertical);
  padding-right: 0;
  padding-left: 0;
}
.shell {
  width: 100%;
  max-width: none;
  margin: 28px auto 48px;
  padding: 0;
  position: relative;
}
.steps {
  list-style: none;
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 10px;
  padding: 0;
  margin: 0 0 16px;
}
.steps li {
  display: flex;
  align-items: center;
  box-sizing: border-box;
  min-height: 48px;
  background: #fff;
  border: 1px solid var(--line);
  border-radius: 12px;
  padding: 12px 16px;
  font-size: 13px;
  color: var(--muted);
  font-weight: 600;
  line-height: 1.3;
}
.steps li.on { color: #0e2438; border-color: #e3d3ae; background: #fbf6ea; }
.steps li.done { color: #1b7f4a; }
.layout {
  display: grid;
  grid-template-columns: minmax(0, 1fr) 300px;
  gap: 16px;
  align-items: start;
}
.panel, .summary {
  background: #fff;
  border: 1px solid var(--line);
  border-radius: 18px;
  padding: 20px;
  box-shadow: 0 12px 30px rgba(20, 33, 43, 0.04);
}
.panel h2, .summary h3 { margin: 0 0 16px; font-size: 1.15rem; }
.search-bar {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr)) auto;
  gap: 10px;
  margin-bottom: 18px;
}
.search-bar label, .form-grid label {
  display: flex;
  flex-direction: column;
  gap: 6px;
  font-size: 12px;
  color: var(--muted);
  font-weight: 600;
}
.search-bar input, .form-grid input, .form-grid select {
  border: 1px solid var(--line);
  border-radius: 10px;
  padding: 10px 12px;
  font: inherit;
  color: var(--ink);
  background: #fff;
}
.btn.primary { background: var(--accent); color: #fff; border: 0; }
.btn.ghost { background: #fff; border-color: var(--line); color: var(--ink); }
.btn.sm { padding: 8px 12px; font-size: 13px; }
.btn:disabled { opacity: 0.6; cursor: not-allowed; }
.room-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 14px;
}
.room {
  border: 1px solid var(--line);
  border-radius: 16px;
  overflow: hidden;
  cursor: pointer;
  transition: transform 0.15s ease, box-shadow 0.15s ease;
  background: #fff;
}
.room:hover { transform: translateY(-2px); box-shadow: 0 14px 28px rgba(20, 33, 43, 0.08); }
.room-media {
  height: 140px;
  background:
    linear-gradient(135deg, #0e2438, #2a4a63),
    radial-gradient(circle at 30% 30%, rgba(196, 163, 90, 0.35), transparent 45%);
  background-size: cover;
  background-position: center;
  position: relative;
}
.avail {
  position: absolute;
  top: 10px;
  right: 10px;
  background: rgba(255, 255, 255, 0.92);
  color: #1b7f4a;
  font-size: 12px;
  font-weight: 700;
  padding: 4px 8px;
  border-radius: 999px;
}
.room-body { padding: 14px; }
.room-title { display: flex; justify-content: space-between; gap: 8px; align-items: baseline; }
.room-title h3 { margin: 0; font-size: 1.05rem; }
.room-title p { margin: 0; color: var(--accent-2); font-size: 13px; font-weight: 700; }
.desc {
  margin: 8px 0;
  color: var(--muted);
  font-size: 13px;
  line-height: 1.45;
  min-height: 38px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
.beds { margin: 0; font-size: 12px; color: var(--muted); }
.room-foot {
  margin-top: 12px;
  display: flex;
  align-items: center;
  gap: 8px;
}
.room-foot strong { font-size: 1.05rem; }
.room-foot span { color: var(--muted); font-size: 12px; margin-right: auto; }
.detail-grid {
  display: grid;
  grid-template-columns: 240px 1fr;
  gap: 18px;
}
.detail-media {
  min-height: 180px;
  border-radius: 14px;
  background: linear-gradient(135deg, #0e2438, #2a4a63);
  background-size: cover;
  background-position: center;
}
.facts { margin: 12px 0 0; padding-left: 18px; color: var(--muted); line-height: 1.7; }
.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
  margin-bottom: 16px;
}
.form-grid .full { grid-column: 1 / -1; }
.form-grid select { min-height: 96px; }
.form-grid small { font-weight: 400; }
.check {
  flex-direction: row !important;
  align-items: center;
  gap: 8px !important;
  font-size: 14px !important;
  color: var(--ink) !important;
}
.row-btns { display: flex; gap: 8px; flex-wrap: wrap; margin-top: 8px; }
.policy {
  background: #fbf6ea;
  border: 1px solid #eadcbe;
  border-radius: 12px;
  padding: 12px 14px;
  font-size: 13px;
  margin-bottom: 14px;
  line-height: 1.5;
}
.confirm-box {
  border: 1px solid var(--line);
  border-radius: 12px;
  padding: 8px 14px;
  margin-bottom: 14px;
}
.confirm-box > div {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  padding: 8px 0;
  border-bottom: 1px solid var(--line);
  font-size: 14px;
}
.confirm-box > div:last-child { border-bottom: 0; }
.success-box {
  background: #eefaf3;
  border: 1px solid #c6e9d4;
  border-radius: 12px;
  padding: 14px;
}
.summary { position: sticky; top: 16px; }
.line {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  font-size: 14px;
  margin-bottom: 10px;
}
.line.total {
  margin-top: 8px;
  padding-top: 12px;
  border-top: 1px solid var(--line);
  font-size: 18px;
  font-weight: 700;
}
.note { margin: 8px 0 0; font-size: 12px; color: var(--muted); }
.empty { color: var(--muted); }
@media (max-width: 960px) {
  .layout, .room-grid, .detail-grid, .search-bar, .form-grid { grid-template-columns: 1fr; }
  .steps { grid-template-columns: repeat(2, minmax(0, 1fr)); }
  .shell { margin-top: 20px; }
  .summary { position: static; }
}
@media (max-width: 640px) {
  .steps { grid-template-columns: 1fr; }
}
</style>
