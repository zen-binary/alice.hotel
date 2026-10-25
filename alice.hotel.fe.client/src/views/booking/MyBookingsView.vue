<template>
  <div class="mine">
    <header class="head">
      <div>
        <p class="kicker">Alice Hotel</p>
        <h1>Đơn đặt phòng của tôi</h1>
        <p class="sub">Quản lý đơn: hủy (có ước tính hoàn) · đổi ngày · đánh giá sau khi trả phòng</p>
      </div>
      <button type="button" class="btn" @click="store.loadMine()">Làm mới</button>
    </header>

    <article v-for="b in store.mine" :key="b.bookingId" class="card">
      <div class="card-main">
        <div class="top">
          <strong>{{ b.bookingCode }}</strong>
          <span class="status" :class="statusClass(b.status)">{{ statusLabel(b.status) }}</span>
        </div>
        <h2>{{ b.roomTypeName || "Loại phòng" }} · P.{{ b.roomNumber || "—" }}</h2>
        <p class="dates">{{ b.checkIn }} → {{ b.checkOut }}</p>
        <div class="money">
          <span>Tổng {{ money(b.totalAmount) }}</span>
          <span>Đã trả {{ money(b.paidAmount) }}</span>
          <span class="due">Còn {{ money(b.remainingAmount) }}</span>
        </div>
        <p v-if="canCancel(b)" class="refund">Ước tính hoàn nếu hủy ngay: <b>{{ money(b.refundEstimate) }}</b></p>
      </div>
      <div class="card-actions">
        <button v-if="canCancel(b)" type="button" class="btn danger" @click="cancel(b)">Hủy đơn</button>
        <button v-if="canModify(b)" type="button" class="btn" @click="openModify(b)">Đổi ngày</button>
        <button v-if="b.status === 'CHECKED_OUT'" type="button" class="btn primary" @click="review(b)">Đánh giá</button>
      </div>
    </article>

    <p v-if="!(store.mine || []).length" class="empty">Chưa có đơn đặt phòng.</p>

    <div v-if="dlgModify" class="modal-backdrop" @click.self="dlgModify = false">
      <div class="modal">
        <h3>Đổi lịch — {{ current?.bookingCode }}</h3>
        <label><span>Nhận phòng mới</span><input v-model="mod.checkIn" type="date" /></label>
        <label><span>Trả phòng mới</span><input v-model="mod.checkOut" type="date" /></label>
        <div class="row">
          <button type="button" class="btn" @click="dlgModify = false">Huỷ</button>
          <button type="button" class="btn primary" @click="submitModify">Xác nhận đổi</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from "vue";
import { clientBookingStore } from "@/stores/clientBooking";

const store = clientBookingStore();
const dlgModify = ref(false);
const current = ref<any>(null);
const mod = reactive({ checkIn: "", checkOut: "" });

const money = (v: any) => Number(v || 0).toLocaleString("vi-VN") + " ₫";
const canCancel = (b: any) => ["CONFIRMED", "PENDING_PAYMENT"].includes(b.status);
const canModify = (b: any) => ["CONFIRMED", "PENDING_PAYMENT"].includes(b.status);
const statusLabel = (s: string) =>
  ({
    PENDING_PAYMENT: "Chờ thanh toán",
    CONFIRMED: "Đã xác nhận",
    CHECKED_IN: "Đang ở",
    CHECKED_OUT: "Đã trả phòng",
    CANCELLED: "Đã hủy",
    NO_SHOW: "Không đến",
    EXPIRED_HOLD: "Hết hạn giữ chỗ",
  } as any)[s] || s;
const statusClass = (s: string) =>
  ({
    CONFIRMED: "ok",
    PENDING_PAYMENT: "warn",
    CHECKED_IN: "info",
    CANCELLED: "bad",
    CHECKED_OUT: "muted",
  } as any)[s] || "";

const cancel = async (b: any) => {
  const ok = confirm(`Hủy đơn ${b.bookingCode}?\nƯớc tính hoàn: ${money(b.refundEstimate)}`);
  if (!ok) return;
  const reason = prompt("Lý do hủy?") || "";
  await store.cancel(b.bookingId, reason);
  await store.loadMine();
};

const openModify = (b: any) => {
  current.value = b;
  mod.checkIn = String(b.checkIn).slice(0, 10);
  mod.checkOut = String(b.checkOut).slice(0, 10);
  dlgModify.value = true;
};

const submitModify = async () => {
  try {
    await store.modify(current.value.bookingId, { ...mod });
    dlgModify.value = false;
    await store.loadMine();
  } catch {
    alert("Không đổi được lịch — kiểm tra phòng trống.");
  }
};

const review = async (b: any) => {
  const rating = Number(prompt("Số sao 1-5", "5") || "5");
  const comment = prompt("Nhận xét") || "";
  await store.review({ bookingId: b.bookingId, rating, comment });
  alert("Cảm ơn bạn đã đánh giá!");
};

onMounted(() => store.loadMine());
</script>

<style scoped>
.mine {
  --ink: #14212b;
  --muted: #5d6b75;
  --line: #e7e1d6;
  --accent: #0e2438;
  max-width: 920px;
  margin: 0 auto;
  padding: 32px 16px 56px;
  color: var(--ink);
}
.head {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  flex-wrap: wrap;
  margin-bottom: 20px;
}
.kicker {
  margin: 0;
  font-size: 11px;
  letter-spacing: 0.14em;
  text-transform: uppercase;
  color: var(--muted);
}
.head h1 { margin: 4px 0; font-size: 2rem; font-weight: 600; letter-spacing: -0.02em; color: #0e2438; }
.sub { margin: 0; color: var(--muted); font-size: 14px; }
.card {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  flex-wrap: wrap;
  background: #fff;
  border: 1px solid var(--line);
  border-radius: 16px;
  padding: 18px;
  margin-bottom: 12px;
  box-shadow: 0 10px 24px rgba(20, 33, 43, 0.04);
}
.top { display: flex; gap: 10px; align-items: center; margin-bottom: 6px; }
.card h2 { margin: 0 0 4px; font-size: 1.05rem; }
.dates { margin: 0; color: var(--muted); font-size: 14px; }
.money {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 10px;
  font-size: 13px;
}
.money .due { color: #b42318; font-weight: 700; }
.refund { margin: 8px 0 0; font-size: 13px; color: var(--muted); }
.status {
  font-size: 11px;
  font-weight: 700;
  padding: 3px 8px;
  border-radius: 999px;
  background: #eef2f5;
  color: var(--muted);
}
.status.ok { background: #e5f6ee; color: #1b7f4a; }
.status.warn { background: #fff4e8; color: #c45c12; }
.status.info { background: #e8eef5; color: #0e2438; }
.status.bad { background: #fdecea; color: #b42318; }
.card-actions { display: flex; flex-direction: column; gap: 8px; min-width: 140px; }
.btn {
  border: 1px solid var(--line);
  background: #fff;
  border-radius: 999px;
  padding: 9px 14px;
  font-weight: 600;
  cursor: pointer;
  font: inherit;
}
.btn.primary { background: var(--accent); color: #fff; border-color: var(--accent); }
.btn.danger { color: #b42318; border-color: #f3c6c2; background: #fff7f6; }
.empty { color: var(--muted); }
.modal-backdrop {
  position: fixed;
  inset: 0;
  background: rgba(15, 23, 28, 0.45);
  display: grid;
  place-items: center;
  padding: 16px;
  z-index: 50;
}
.modal {
  width: min(420px, 100%);
  background: #fff;
  border-radius: 16px;
  padding: 18px;
  display: grid;
  gap: 12px;
}
.modal h3 { margin: 0; }
.modal label { display: grid; gap: 6px; font-size: 12px; color: var(--muted); font-weight: 600; }
.modal input {
  border: 1px solid var(--line);
  border-radius: 10px;
  padding: 10px 12px;
  font: inherit;
}
.row { display: flex; justify-content: flex-end; gap: 8px; }
</style>
