<template>
  <div class="pay">
    <div class="card">
      <router-link class="brand" to="/">
        <span class="mark">A</span>
        Alice Hotel
      </router-link>
      <p class="eyebrow">VNPay</p>
      <h1>Kết quả thanh toán</h1>
      <p v-if="loading" class="state">Đang xác nhận giao dịch…</p>
      <div v-else-if="booking" class="result ok">
        <strong>Đơn {{ booking.bookingCode }}</strong>
        <p>Trạng thái: {{ statusLabel(booking.status) }}</p>
        <p>Đã thanh toán: {{ money(booking.paidAmount) }}</p>
      </div>
      <div v-else class="result bad">
        <strong>Chưa xác nhận được giao dịch</strong>
        <p>Vui lòng kiểm tra lại trong mục đơn của tôi, hoặc liên hệ lễ tân.</p>
      </div>
      <div class="actions">
        <router-link class="button btn_md btn_primary" to="/my-bookings">Xem đơn của tôi</router-link>
        <router-link class="button btn_md btn_lightgray" to="/dat-phong">Đặt phòng khác</router-link>
        <router-link class="button btn_md btn_lightgray" to="/">Về trang chủ</router-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import http from "@/utils/http";

const route = useRoute();
const loading = ref(true);
const booking = ref<any>();

const money = (v: any) => Number(v || 0).toLocaleString("vi-VN") + " ₫";
const statusLabel = (s: string) =>
  ({
    PENDING_PAYMENT: "Chờ thanh toán",
    CONFIRMED: "Đã xác nhận",
    CHECKED_IN: "Đang ở",
    CHECKED_OUT: "Đã trả phòng",
    CANCELLED: "Đã hủy",
  } as Record<string, string>)[s] || s;

onMounted(async () => {
  try {
    const params = { ...route.query } as Record<string, string>;
    const res = await http.get("/c/booking/vnpay/return", { params });
    booking.value = res.data?.data;
  } catch {
    booking.value = null;
  } finally {
    loading.value = false;
  }
});
</script>

<style scoped>
.pay {
  min-height: 100dvh;
  display: grid;
  place-items: center;
  padding: 24px;
  background:
    radial-gradient(circle at 80% 0%, rgba(196, 163, 90, 0.18), transparent 30%),
    #f6f4ef;
}
.card {
  width: min(520px, 100%);
  background: #fff;
  border: 1px solid #e7e1d6;
  border-radius: 18px;
  padding: 28px;
  box-shadow: 0 18px 40px rgba(14, 36, 56, 0.08);
}
.brand {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  color: #0e2438;
  font-weight: 700;
  text-decoration: none;
  margin-bottom: 18px;
}
.mark {
  width: 28px;
  height: 28px;
  border-radius: 8px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  background: #0e2438;
  color: #e7d3a1;
  font-size: 13px;
}
.eyebrow {
  margin: 0;
  letter-spacing: 0.14em;
  text-transform: uppercase;
  font-size: 11px;
  color: #8a7960;
  font-weight: 700;
}
h1 {
  margin: 6px 0 16px;
  font-size: 2.4rem;
  color: #14212b;
}
.state {
  color: #5d6b75;
}
.result {
  border-radius: 12px;
  padding: 14px 16px;
  margin-bottom: 18px;
}
.result strong {
  display: block;
  margin-bottom: 6px;
  font-size: 16px;
}
.result p {
  margin: 0 0 4px;
  color: #3d4f5c;
}
.result.ok {
  background: #eefaf3;
  border: 1px solid #c6e9d4;
}
.result.bad {
  background: #fff7f0;
  border: 1px solid #f3ddc8;
}
.actions {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}
</style>
