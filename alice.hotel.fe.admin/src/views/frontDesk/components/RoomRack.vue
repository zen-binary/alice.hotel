<template>
  <div>
    <div class="legend">
      <span><i class="dot clean" /> Trống sạch</span>
      <span><i class="dot dirty" /> Cần dọn</span>
      <span><i class="dot occ" /> Đang ở</span>
      <span><i class="dot ooo" /> Bảo trì</span>
      <span><i class="dot res" /> Có đặt hôm nay</span>
    </div>
    <section v-for="floor in floors" :key="floor" class="floor">
      <h2>Tầng {{ floor }}</h2>
      <div class="grid">
        <button
          v-for="r in roomsByFloor(floor)"
          :key="r.roomId"
          type="button"
          class="card"
          :class="rackClass(r)"
          @click="emit('room-click', r)"
        >
          <div class="top">
            <strong>{{ r.roomNumber }}</strong>
            <span class="badge">{{ statusLabel(r.houseStatus) }}</span>
          </div>
          <div class="type">{{ shortType(r.roomTypeName || r.roomTypeId) }}</div>
          <div class="guest">{{ guestMap[r.roomId] || "—" }}</div>
        </button>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";

const props = defineProps<{
  rooms: any[];
  arrivals: any[];
  inHouse: any[];
  departures: any[];
}>();

const emit = defineEmits<{ (e: "room-click", r: any): void }>();

const floors = computed(() => {
  const set = new Set<number>();
  (props.rooms || []).forEach((r: any) => set.add(r.floorNo ?? 0));
  return [...set].sort((a, b) => a - b);
});

const reservedToday = computed(() => {
  const set = new Set<string>();
  [...(props.arrivals || []), ...(props.inHouse || [])].forEach((b: any) => {
    if (b.roomId) set.add(b.roomId);
  });
  return set;
});

const guestMap = computed(() => {
  const m: Record<string, string> = {};
  [...(props.arrivals || []), ...(props.inHouse || []), ...(props.departures || [])].forEach((b: any) => {
    if (b.roomId) m[b.roomId] = b.guestName;
  });
  return m;
});

const roomsByFloor = (floor: number) => (props.rooms || []).filter((r: any) => (r.floorNo ?? 0) === floor);
const statusLabel = (s: string) =>
  ({ VACANT_CLEAN: "Sạch", VACANT_DIRTY: "Bẩn", OCCUPIED: "Đang ở", OUT_OF_ORDER: "Bảo trì" } as any)[s] || s;
const shortType = (n: string) => (n || "").slice(0, 14);
const rackClass = (r: any) => ({
  clean: r.houseStatus === "VACANT_CLEAN" && !reservedToday.value.has(r.roomId),
  dirty: r.houseStatus === "VACANT_DIRTY",
  occ: r.houseStatus === "OCCUPIED",
  ooo: r.houseStatus === "OUT_OF_ORDER",
  res: reservedToday.value.has(r.roomId) && r.houseStatus !== "OCCUPIED" && r.houseStatus !== "OUT_OF_ORDER",
});
</script>

<style scoped>
.legend {
  display: flex;
  flex-wrap: wrap;
  gap: 14px;
  font-size: 12px;
  color: #5b6b76;
  margin-bottom: 12px;
  padding-bottom: 10px;
  border-bottom: 1px solid #e8eef3;
}
.dot {
  display: inline-block;
  width: 10px;
  height: 10px;
  border-radius: 2px;
  margin-right: 5px;
  vertical-align: middle;
}
.dot.clean { background: #1b7f4a; }
.dot.dirty { background: #c45c12; }
.dot.occ { background: #b42318; }
.dot.ooo { background: #667085; }
.dot.res { background: #0b7bb5; }
.floor { margin-bottom: 16px; }
.floor h2 {
  margin: 0 0 8px;
  font-size: 12px;
  font-weight: 700;
  color: #6b7c88;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}
.grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
  gap: 8px;
}
.card {
  border: 1px solid #e4ecf2;
  border-radius: 6px;
  padding: 10px;
  text-align: left;
  background: #fff;
  cursor: pointer;
  min-height: 88px;
  border-left-width: 3px;
  transition: border-color 0.12s, box-shadow 0.12s;
}
.card:hover {
  box-shadow: 0 4px 14px rgba(15, 28, 36, 0.08);
}
.card.clean { border-left-color: #1b7f4a; background: #f4fbf7; }
.card.dirty { border-left-color: #c45c12; background: #fff7ef; }
.card.occ { border-left-color: #b42318; background: #fff5f4; }
.card.ooo { border-left-color: #667085; background: #f5f6f8; opacity: 0.85; }
.card.res { border-left-color: #0b7bb5; background: #f1f9fe; }
.top { display: flex; justify-content: space-between; gap: 6px; align-items: center; }
.top strong {
  font-size: 1.05rem;
  color: #0f1c24;
  font-variant-numeric: tabular-nums;
}
.badge {
  font-size: 10px;
  font-weight: 700;
  color: #5b6b76;
  background: rgba(15, 28, 36, 0.06);
  padding: 2px 6px;
  border-radius: 4px;
}
.type { margin-top: 6px; font-size: 12px; color: #5b6b76; }
.guest {
  margin-top: 4px;
  font-size: 12px;
  font-weight: 600;
  color: #0f1c24;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
