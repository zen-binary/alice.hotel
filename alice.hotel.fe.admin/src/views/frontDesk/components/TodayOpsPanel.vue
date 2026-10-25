<template>
  <div class="ops_grid">
    <section class="ops_col">
      <header>
        <h3>Sẽ đến</h3>
        <em>{{ arrivals.length }}</em>
      </header>
      <div v-for="b in arrivals" :key="b.bookingId" class="row">
        <div>
          <div class="code">{{ b.bookingCode }}</div>
          <div class="name">{{ b.guestName }}</div>
          <div class="meta">P.{{ b.roomNumber || "chưa gán" }} · {{ b.roomTypeName }}</div>
        </div>
        <div class="btns">
          <button type="button" class="button btn_sm btn_primary" @click="emit('check-in', b)">Nhận phòng</button>
          <button type="button" class="button btn_sm btn_lightgray" @click="emit('open-folio', b.bookingId)">Phiếu thu</button>
          <button type="button" class="button btn_sm btn_lightgray" @click="emit('no-show', b.bookingId)">Không đến</button>
        </div>
      </div>
      <p v-if="!arrivals.length" class="empty">Không có khách đến hôm nay</p>
    </section>

    <section class="ops_col">
      <header>
        <h3>Đang ở</h3>
        <em>{{ inHouse.length }}</em>
      </header>
      <div v-for="b in inHouse" :key="b.bookingId" class="row">
        <div>
          <div class="code">P.{{ b.roomNumber }}</div>
          <div class="name">{{ b.guestName }}</div>
          <div class="meta">Còn {{ money(b.remainingAmount) }} · đến {{ b.checkOut }}</div>
        </div>
        <div class="btns">
          <button type="button" class="button btn_sm btn_lightgray" @click="emit('add-service', b)">+ DV</button>
          <button type="button" class="button btn_sm btn_primary" @click="emit('pay', b)">Thu</button>
          <button type="button" class="button btn_sm btn_primary" @click="emit('check-out', b)">Trả phòng</button>
          <button type="button" class="button btn_sm btn_lightgray" @click="emit('open-folio', b.bookingId)">Phiếu thu</button>
        </div>
      </div>
      <p v-if="!inHouse.length" class="empty">Không có khách đang ở</p>
    </section>

    <section class="ops_col">
      <header>
        <h3>Sẽ đi</h3>
        <em>{{ departures.length }}</em>
      </header>
      <div v-for="b in departures" :key="b.bookingId" class="row">
        <div>
          <div class="code">P.{{ b.roomNumber }}</div>
          <div class="name">{{ b.guestName }}</div>
          <div class="meta">{{ b.checkOut }} · còn {{ money(b.remainingAmount) }}</div>
        </div>
        <div class="btns">
          <button type="button" class="button btn_sm btn_primary" @click="emit('check-out', b)">Trả phòng</button>
          <button type="button" class="button btn_sm btn_lightgray" @click="emit('open-folio', b.bookingId)">Phiếu thu</button>
        </div>
      </div>

      <header class="sub">
        <h3>Buồng phòng</h3>
        <em>{{ dirtyRooms.length }}</em>
      </header>
      <button
        v-for="r in dirtyRooms"
        :key="r.roomId"
        type="button"
        class="hk"
        @click="emit('mark-clean', r)"
      >
        {{ r.roomNumber }} → Đánh dấu sạch
      </button>
      <p v-if="!dirtyRooms.length" class="empty">Không phòng bẩn</p>
    </section>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  arrivals: any[];
  inHouse: any[];
  departures: any[];
  dirtyRooms: any[];
}>();

const emit = defineEmits<{
  (e: "check-in", b: any): void;
  (e: "check-out", b: any): void;
  (e: "open-folio", id: string): void;
  (e: "no-show", id: string): void;
  (e: "add-service", b: any): void;
  (e: "pay", b: any): void;
  (e: "mark-clean", r: any): void;
}>();

const money = (v: any) => Number(v || 0).toLocaleString("vi-VN") + " ₫";
</script>

<style scoped>
.ops_grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 12px;
}
@media (max-width: 1100px) {
  .ops_grid {
    grid-template-columns: 1fr;
  }
}
.ops_col {
  border: 1px solid #e4ecf2;
  border-radius: 8px;
  background: #fafcfd;
  padding: 12px 14px;
  min-height: 220px;
}
.ops_col header {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 4px;
  padding-bottom: 8px;
  border-bottom: 1px solid #e8eef3;
}
.ops_col header.sub {
  margin-top: 14px;
}
.ops_col h3 {
  margin: 0;
  font-size: 13px;
  font-weight: 700;
  color: #0f1c24;
}
.ops_col em {
  font-style: normal;
  font-size: 11px;
  font-weight: 700;
  color: #5b6b76;
  background: #eef3f7;
  padding: 2px 8px;
  border-radius: 999px;
}
.row {
  display: flex;
  justify-content: space-between;
  gap: 10px;
  padding: 10px 0;
  border-bottom: 1px solid #eef3f7;
}
.row:last-of-type {
  border-bottom: 0;
}
.code {
  font-size: 12px;
  font-weight: 700;
  color: #0b7bb5;
}
.name {
  font-weight: 600;
  color: #0f1c24;
  font-size: 13px;
}
.meta {
  font-size: 12px;
  color: #5b6b76;
  margin-top: 2px;
}
.btns {
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
  justify-content: flex-end;
  max-width: 200px;
  align-content: flex-start;
}
.hk {
  display: block;
  width: 100%;
  text-align: left;
  border: 1px dashed #f0c7a0;
  background: #fff8f1;
  color: #c45c12;
  border-radius: 6px;
  padding: 10px 12px;
  font-size: 13px;
  font-weight: 600;
  cursor: pointer;
  margin-bottom: 8px;
}
.empty {
  color: #8a9aa6;
  font-size: 13px;
  margin: 12px 0 0;
}
</style>
