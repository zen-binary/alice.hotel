<template>
  <TModal
    :is-open="open"
    :is-show-header="true"
    :is-show-footer="true"
    modal-id="folioDrawer"
    class-size="small"
    @close-modal="emit('update:open', false)"
  >
    <template #title><strong>Phiếu thu {{ folio?.bookingCode }}</strong></template>
    <template #default>
      <div v-if="folio" class="folio">
        <div class="guest">
          <strong>{{ folio.guestName }}</strong>
          <span>{{ folio.guestPhone || "—" }}</span>
          <span>{{ folio.roomTypeName }} / P.{{ folio.roomNumber || "—" }}</span>
          <span>{{ folio.checkIn }} → {{ folio.checkOut }}</span>
          <span class="status-pill">{{ statusLabel(folio.status) }}</span>
        </div>
        <table class="tbl_row">
          <colgroup><col style="width: 45%" /><col style="width: 55%" /></colgroup>
          <tbody>
            <tr><th scope="row"><p>Tiền phòng</p></th><td class="right">{{ money(folio.roomAmount) }}</td></tr>
            <tr><th scope="row"><p>Dịch vụ</p></th><td class="right">{{ money(folio.serviceAmount) }}</td></tr>
            <tr><th scope="row"><p>Giảm giá</p></th><td class="right">-{{ money(folio.discountAmount) }}</td></tr>
            <tr><th scope="row"><p>Phụ phí</p></th><td class="right">{{ money(folio.feeAmount) }}</td></tr>
            <tr><th scope="row"><p><strong>Tổng</strong></p></th><td class="right"><strong>{{ money(folio.totalAmount) }}</strong></td></tr>
            <tr><th scope="row"><p>Đã thu</p></th><td class="right">{{ money(folio.paidAmount) }}</td></tr>
            <tr><th scope="row"><p>Còn lại</p></th><td class="right due">{{ money(folio.remainingAmount) }}</td></tr>
          </tbody>
        </table>
        <p class="box_title_sm mg_t15">Dịch vụ</p>
        <div v-for="s in folio.services || []" :key="s.id" class="mini">
          <span>{{ s.serviceName }} × {{ s.qty }}</span><span>{{ money(s.amount) }}</span>
        </div>
        <p v-if="!(folio.services || []).length" class="empty">Chưa có dịch vụ</p>
        <p class="box_title_sm mg_t15">Thanh toán</p>
        <div v-for="p in folio.payments || []" :key="p.paymentId" class="mini">
          <span>{{ payLabel(p.method) }} · {{ statusLabel(p.status) }}</span><span>{{ money(p.amount) }}</span>
        </div>
        <p v-if="!(folio.payments || []).length" class="empty">Chưa thu</p>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_md btn_lightgray" @click="printFolio">In</button>
      <button type="button" class="button btn_md btn_lightgray" @click="emit('add-service', folio)">Thêm DV</button>
      <button type="button" class="button btn_md btn_primary" @click="emit('pay', folio)">Thu tiền</button>
      <button type="button" class="button btn_md btn_lightgray" @click="emit('move', folio)">Đổi phòng</button>
    </template>
  </TModal>
</template>

<script setup lang="ts">
import TModal from "@/components/common/excel/TModal.vue";

const props = defineProps<{ open: boolean; folio: any }>();
const emit = defineEmits<{
  (e: "update:open", v: boolean): void;
  (e: "add-service", b: any): void;
  (e: "pay", b: any): void;
  (e: "move", b: any): void;
}>();

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
    PENDING: "Chờ xử lý",
  } as any)[s] || s;
const payLabel = (s: string) =>
  ({ CASH: "Tiền mặt", TRANSFER: "Chuyển khoản", CARD: "Thẻ", VNPAY: "VNPay" } as any)[s] || s;

const printFolio = () => {
  const f = props.folio;
  if (!f) return;
  const moneyFmt = (v: any) => Number(v || 0).toLocaleString("vi-VN") + " ₫";
  const svcRows = (f.services || [])
    .map((s: any) => `<tr><td>${s.serviceName} × ${s.qty}</td><td style="text-align:right">${moneyFmt(s.amount)}</td></tr>`)
    .join("");
  const payRows = (f.payments || [])
    .map((p: any) => `<tr><td>${payLabel(p.method)} · ${statusLabel(p.status)}</td><td style="text-align:right">${moneyFmt(p.amount)}</td></tr>`)
    .join("");
  const html = `<!DOCTYPE html><html><head><meta charset="utf-8"><title>Phiếu thu ${f.bookingCode}</title>
<style>
@page{margin:16mm}body{font-family:Segoe UI,Arial,sans-serif;color:#111;max-width:720px;margin:0 auto}
h1{font-size:20px;margin:0 0 4px}.muted{color:#555;font-size:13px;margin-bottom:16px}
table{width:100%;border-collapse:collapse;margin:12px 0}td{padding:6px 0;border-bottom:1px solid #ddd;font-size:13px}
.total td{font-size:16px;font-weight:700;border:0;padding-top:12px}.remain td{color:#b71c1c}
h2{font-size:14px;margin:18px 0 6px}.brand{letter-spacing:.08em;text-transform:uppercase;font-size:11px;color:#666}
</style></head><body>
<div class="brand">Alice Hotel · Phiếu thu</div>
<h1>${f.bookingCode}</h1>
<div class="muted">${f.guestName || ""} · ${f.guestPhone || ""}<br/>
${f.roomTypeName || ""} / P.${f.roomNumber || ""} · ${f.checkIn} → ${f.checkOut} · ${statusLabel(f.status)}</div>
<table>
<tr><td>Tiền phòng</td><td style="text-align:right">${moneyFmt(f.roomAmount)}</td></tr>
<tr><td>Dịch vụ</td><td style="text-align:right">${moneyFmt(f.serviceAmount)}</td></tr>
<tr><td>Giảm giá</td><td style="text-align:right">-${moneyFmt(f.discountAmount)}</td></tr>
<tr><td>Phụ phí</td><td style="text-align:right">${moneyFmt(f.feeAmount)}</td></tr>
<tr class="total"><td>Tổng</td><td style="text-align:right">${moneyFmt(f.totalAmount)}</td></tr>
<tr><td>Đã thu</td><td style="text-align:right">${moneyFmt(f.paidAmount)}</td></tr>
<tr class="remain"><td>Còn lại</td><td style="text-align:right">${moneyFmt(f.remainingAmount)}</td></tr>
</table>
<h2>Dòng dịch vụ</h2><table>${svcRows || "<tr><td>—</td></tr>"}</table>
<h2>Thanh toán</h2><table>${payRows || "<tr><td>—</td></tr>"}</table>
<script>window.onload=()=>window.print()</` + `script></body></html>`;
  const w = window.open("", "_blank", "width=800,height=900");
  if (!w) return;
  w.document.write(html);
  w.document.close();
};
</script>

<style scoped>
.guest {
  display: grid;
  gap: 2px;
  font-size: 13px;
  color: #5b6b76;
  margin-bottom: 14px;
}
.guest strong { color: #0f1c24; font-size: 15px; }
.status-pill {
  display: inline-block;
  margin-top: 4px;
  font-size: 11px;
  font-weight: 600;
  padding: 2px 8px;
  border-radius: 999px;
  background: #eef5fa;
  color: #0098e5;
  width: fit-content;
}
.right { text-align: right; }
.due { color: #b42318; font-weight: 700; }
.mini {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  padding: 6px 0;
  font-size: 13px;
  border-bottom: 1px solid #eef2f5;
}
.empty { color: #5b6b76; font-size: 13px; }
</style>
