<template>
  <div class="hotel-home">
    <BannerComponent
      variant="hotel"
      title="Alice Hotel"
      :description="['Khách sạn', 'tại Hà Nội']"
      :info="[lead]"
      class-description="banner_description_login"
    />

    <div class="hero-cta">
      <router-link class="btn primary" :to="{ name: 'Book' }">Đặt phòng</router-link>
      <a class="btn ghost" href="#hang-phong">Xem hạng phòng</a>
    </div>

    <section id="gioi-thieu" class="block">
      <div class="copy">
        <p class="kicker">Về chúng tôi</p>
        <h2>Khách sạn thành phố cho kỳ nghỉ ngắn và chuyến công tác</h2>
        <p>
          {{ intro }}
        </p>
        <p>
          Giá phòng được niêm yết theo đêm trước khi giữ chỗ. Khách có thể đặt cọc online hoặc thanh toán tại khách sạn,
          và xem điều kiện hủy ngay trên đơn.
        </p>
      </div>
      <ul class="facts">
        <li>
          <strong>{{ hotel?.checkInTime || "14:00" }}</strong>
          <span>Giờ nhận phòng</span>
        </li>
        <li>
          <strong>{{ hotel?.checkOutTime || "12:00" }}</strong>
          <span>Giờ trả phòng</span>
        </li>
        <li>
          <strong>{{ hotel?.depositPercent || 30 }}%</strong>
          <span>Mức cọc khi giữ chỗ</span>
        </li>
        <li>
          <strong>{{ hotel?.cancelFreeHours || 48 }} giờ</strong>
          <span>Hủy miễn phí trước ngày nhận</span>
        </li>
      </ul>
    </section>

    <section class="pillars">
      <article>
        <h3>Phòng rõ hạng</h3>
        <p>Mỗi hạng có sức chứa, giường và giá cơ bản. Khách chọn theo số đêm và số người, không phải đoán phụ phí.</p>
      </article>
      <article>
        <h3>Lễ tân theo ca</h3>
        <p>Đơn online được lễ tân nhận, gán phòng và nhận khách. Thanh toán quầy chỉ mở khi ca trực đang mở.</p>
      </article>
      <article>
        <h3>Dịch vụ kèm phòng</h3>
        <p>Bữa sáng, đưa đón, giặt ủi và các dịch vụ khác được cộng vào phiếu thu, khách thấy trước khi xác nhận.</p>
      </article>
      <article>
        <h3>Chính sách viết sẵn</h3>
        <p>Giờ nhận trả, phí đến sớm hoặc trả muộn, và tỷ lệ hoàn khi hủy nằm trong hồ sơ khách sạn.</p>
      </article>
    </section>

    <section id="hang-phong" class="block">
      <div class="section-head">
        <div>
          <p class="kicker">Hạng phòng</p>
          <h2>Chọn phòng theo nhu cầu ở</h2>
        </div>
        <router-link class="btn primary" :to="{ name: 'Book' }">Kiểm tra phòng trống</router-link>
      </div>
      <div class="room-grid">
        <article v-for="rt in roomTypes" :key="rt.roomTypeId" class="room">
          <div class="room-media" :style="coverStyle(rt)">
            <span v-if="!firstImage(rt)" class="room-mark">{{ rt.code || "ALICE" }}</span>
          </div>
          <div class="room-body">
            <h3>{{ roomName(rt) }}</h3>
            <p class="desc">{{ roomBlurb(rt) }}</p>
            <p class="meta">{{ bedLabel(rt) }} · {{ rt.capacityAdult || 2 }} người lớn</p>
            <div class="room-foot">
              <strong>{{ money(rt.basePrice) }}</strong>
              <span>/ đêm</span>
              <router-link class="btn primary sm" :to="{ name: 'Book' }">Đặt</router-link>
            </div>
          </div>
        </article>
      </div>
      <p v-if="!roomTypes.length" class="empty">Đang cập nhật danh sách hạng phòng.</p>
    </section>

    <section id="dich-vu" class="block soft">
      <div class="section-head">
        <div>
          <p class="kicker">Dịch vụ</p>
          <h2>Thêm vào kỳ ở khi cần</h2>
        </div>
      </div>
      <ul class="services">
        <li v-for="s in services" :key="s.serviceId">
          <strong>{{ serviceName(s) }}</strong>
          <span>{{ money(s.unitPrice) }} / {{ unitLabel(s.unitName) }}</span>
        </li>
      </ul>
      <p v-if="!services.length" class="empty">Dịch vụ kèm phòng sẽ hiện khi lễ tân khai báo.</p>
    </section>

    <section id="luu-tru" class="block">
      <div>
        <p class="kicker">Khi lưu trú</p>
        <h2>Những điều khách cần biết trước khi đến</h2>
      </div>
      <div class="policy-grid">
        <article>
          <h3>Nhận và trả phòng</h3>
          <p>
            Nhận phòng từ {{ hotel?.checkInTime || "14:00" }}, trả phòng trước {{ hotel?.checkOutTime || "12:00" }}.
            Đến sớm hoặc trả muộn có thể phát sinh phí
            {{ money(hotel?.earlyCheckinFee) }} và {{ money(hotel?.lateCheckoutFee) }}.
          </p>
        </article>
        <article>
          <h3>Giữ chỗ và cọc</h3>
          <p>
            Đơn chưa thanh toán được giữ {{ hotel?.holdMinutes || 15 }} phút. Cọc online là
            {{ hotel?.depositPercent || 30 }}% giá trị đơn qua VNPay, hoặc khách chọn trả tại khách sạn.
          </p>
        </article>
        <article>
          <h3>Hủy phòng</h3>
          <p>
            Hủy trước {{ hotel?.cancelFreeHours || 48 }} giờ so với ngày nhận phòng được hoàn
            {{ hotel?.cancelRefundPercent || 100 }}% phần đã thanh toán nếu đơn đủ điều kiện.
          </p>
        </article>
        <article>
          <h3>Sau khi trả phòng</h3>
          <p>Khách đã trả phòng có thể gửi đánh giá từ mục đơn của tôi. Lễ tân đối soát phiếu thu trước khi đóng phòng.</p>
        </article>
      </div>
    </section>

    <section id="lien-he" class="contact">
      <div>
        <p class="kicker">Liên hệ</p>
        <h2>{{ hotelName }}</h2>
        <p>{{ addressLabel }}</p>
      </div>
      <ul>
        <li>
          <span>Điện thoại</span>
          <a :href="`tel:${phone}`">{{ phone }}</a>
        </li>
        <li>
          <span>Email</span>
          <a :href="`mailto:${email}`">{{ email }}</a>
        </li>
        <li>
          <span>Lễ tân</span>
          <strong>Hỗ trợ nhận phòng và đơn đã đặt</strong>
        </li>
      </ul>
      <router-link class="btn primary" :to="{ name: 'Book' }">Bắt đầu đặt phòng</router-link>
    </section>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted } from "vue";
import BannerComponent from "@/components/common/BannerComponent.vue";
import { clientBookingStore } from "@/stores/clientBooking";

const store = clientBookingStore();
const hotel = computed(() => store.hotel);
const roomTypes = computed(() => store.roomTypes || []);
const services = computed(() => (store.services || []).filter((s: any) => s.activeYn !== "N"));

const ROOM_COPY: Record<string, string> = {
  STANDARD: "Phòng tiêu chuẩn cho hai khách, giường twin, phù hợp công tác một đến hai đêm.",
  DELUXE: "Phòng cao cấp rộng hơn, giường lớn, dành cho khách muốn không gian yên tĩnh.",
  SUITE: "Phòng hạng sang có khu ngồi riêng, phù hợp ở dài ngày hoặc tiếp khách ngắn.",
  FAMILY: "Phòng gia đình với hai giường đôi lớn, sức chứa bốn người lớn.",
};
const SERVICE_NAME: Record<string, string> = {
  BREAKFAST: "Bữa sáng",
  AIRPORT: "Đưa đón sân bay",
  LAUNDRY: "Giặt ủi",
  EXTRA_BED: "Giường phụ",
  MINIBAR: "Minibar",
};
const UNIT_NAME: Record<string, string> = {
  suat: "suất",
  luot: "lượt",
  giuong: "giường",
  lan: "lần",
};

const hasTone = (value: string) => /[àáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ]/i.test(value);
const hotelName = computed(() => hotel.value?.name || "Alice Hotel");
const phone = computed(() => {
  const raw = String(hotel.value?.phone || "0240000000").replace(/\s/g, "");
  if (raw === "0240000000") return "024 000 0000";
  return hotel.value?.phone || "024 000 0000";
});
const email = computed(() => hotel.value?.email || "contact@alice.hotel");
const addressLabel = computed(() => {
  const raw = String(hotel.value?.address || "").trim();
  if (!raw || raw === "Ha Noi, Viet Nam") return "Hà Nội, Việt Nam";
  return raw;
});
const lead = computed(
  () =>
    "Phòng niêm yết theo đêm, giờ nhận trả rõ ràng, đặt cọc hoặc thanh toán tại khách sạn.",
);
const intro = computed(() => {
  const text = plainDesc(hotel.value?.description);
  if (text && text.length > 40 && !/datn demo/i.test(text)) return text;
  return "Alice Hotel phục vụ khách ở ngắn ngày tại Hà Nội: công tác, nghỉ cuối tuần và gia đình nhỏ. Chúng tôi giữ vận hành gọn — phòng, giá, dịch vụ và chính sách hủy nằm trên cùng một hồ sơ — để khách đọc được trước khi đặt và lễ tân làm việc trên cùng dữ liệu đó.";
});

const money = (v: any) => Number(v || 0).toLocaleString("vi-VN") + " ₫";
const plainDesc = (d: string) =>
  String(d || "")
    .replace(/&mdash;/g, "—")
    .replace(/\s*\[Tiện nghi:[^\]]*\]\s*/g, "")
    .trim();
const roomName = (rt: any) => {
  const name = String(rt?.name || "");
  if (hasTone(name)) return name;
  return ROOM_COPY[rt?.code] ? name : name || "Hạng phòng";
};
const roomBlurb = (rt: any) => {
  const text = plainDesc(rt?.description);
  if (text && hasTone(text)) return text;
  return ROOM_COPY[rt?.code] || text || "Hạng phòng của Alice Hotel.";
};
const bedLabel = (rt: any) => {
  const bed = String(rt?.bedInfo || "");
  const map: Record<string, string> = {
    "2 twin": "2 giường đơn",
    "1 king": "1 giường king",
    "2 queen": "2 giường queen",
    "1 king + sofa": "1 giường king và sofa",
  };
  return map[bed] || bed || "Giường tiêu chuẩn";
};
const serviceName = (s: any) => {
  const name = String(s?.name || "");
  if (hasTone(name)) return name;
  return SERVICE_NAME[s?.code] || name;
};
const unitLabel = (unit: string) => UNIT_NAME[String(unit || "").toLowerCase()] || unit || "lần";
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

onMounted(async () => {
  await Promise.all([
    store.loadHotel().catch(() => undefined),
    store.loadRoomTypes().catch(() => undefined),
    store.loadServices().catch(() => undefined),
  ]);
});
</script>

<style scoped>
.hotel-home {
  --ink: #14212b;
  --muted: #5d6b75;
  --line: #e7e1d6;
  --accent: #0e2438;
  background: #f6f4ef;
  color: var(--ink);
}
#gioi-thieu,
#hang-phong,
#dich-vu,
#luu-tru,
#lien-he {
  scroll-margin-top: 88px;
}
.hotel-home :deep(.block-banner.hotel .block-banner__content) {
  box-sizing: border-box;
  width: min(var(--main-width-vertical), calc(100% - 4rem));
  max-width: var(--main-width-vertical);
  padding-right: 0;
  padding-left: 0;
}
.hero-cta,
.block,
.contact,
.pillars {
  box-sizing: border-box;
  width: min(var(--main-width-vertical), calc(100% - 4rem));
  max-width: var(--main-width-vertical);
  margin-left: auto;
  margin-right: auto;
}
.hero-cta {
  margin-top: 24px;
  padding: 0;
  position: relative;
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}
.block,
.pillars {
  padding: 56px 0 0;
}
.kicker {
  margin: 0 0 8px;
  letter-spacing: 0.14em;
  text-transform: uppercase;
  font-size: 12px;
  font-weight: 700;
  color: #8a7960;
}
h2 {
  margin: 0 0 14px;
  font-size: clamp(1.7rem, 3vw, 2.3rem);
  letter-spacing: -0.03em;
  color: #0e2438;
  font-weight: 600;
}
.copy p,
.pillars p,
.policy-grid p,
.contact p {
  margin: 0 0 12px;
  line-height: 1.65;
  color: var(--muted);
  max-width: 68ch;
}
#gioi-thieu {
  display: grid;
  grid-template-columns: 1.4fr 0.8fr;
  gap: 28px;
  align-items: start;
}
.facts {
  list-style: none;
  margin: 0;
  padding: 8px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 8px;
  background: #fff;
  border: 1px solid var(--line);
  border-radius: 18px;
}
.facts li {
  padding: 16px;
  border-radius: 12px;
  background: #fbf6ea;
}
.facts strong {
  display: block;
  font-size: 1.35rem;
  color: #0e2438;
}
.facts span {
  color: var(--muted);
  font-size: 13px;
}
.pillars,
.policy-grid,
.room-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 14px;
}
.pillars {
  padding-top: 28px;
}
.pillars article,
.policy-grid article,
.room,
.services li {
  background: #fff;
  border: 1px solid var(--line);
  border-radius: 16px;
}
.pillars article,
.policy-grid article {
  padding: 18px 18px 8px;
}
.pillars h3,
.policy-grid h3,
.room h3 {
  margin: 0 0 8px;
  font-size: 1.05rem;
  color: #0e2438;
}
.section-head {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: flex-end;
  margin-bottom: 16px;
  flex-wrap: wrap;
}
.room {
  overflow: hidden;
}
.room-media {
  height: 160px;
  display: flex;
  align-items: flex-end;
  padding: 14px;
  background:
    radial-gradient(circle at 80% 20%, rgba(196, 163, 90, 0.4), transparent 36%),
    linear-gradient(135deg, #0e2438, #2a4a63);
  background-size: cover;
  background-position: center;
}
.room-mark {
  letter-spacing: 0.16em;
  font-size: 12px;
  font-weight: 700;
  color: #e7d3a1;
}
.room-body {
  padding: 16px;
}
.desc {
  margin: 0 0 8px;
  color: var(--muted);
  font-size: 14px;
  line-height: 1.5;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
.meta {
  margin: 0;
  font-size: 13px;
  color: var(--muted);
}
.room-foot {
  margin-top: 14px;
  display: flex;
  align-items: center;
  gap: 8px;
}
.room-foot strong {
  font-size: 1.05rem;
}
.room-foot span {
  color: var(--muted);
  font-size: 12px;
  margin-right: auto;
}
.soft {
  background: transparent;
}
.services {
  list-style: none;
  margin: 0;
  padding: 0;
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 12px;
}
.services li {
  padding: 16px;
  display: flex;
  flex-direction: column;
  gap: 6px;
}
.services span {
  color: var(--muted);
  font-size: 13px;
}
.contact {
  margin-top: 48px;
  margin-bottom: 48px;
  background: #0e2438;
  color: #f7f4ee;
  border-radius: 20px;
  padding: 28px;
  display: grid;
  gap: 16px;
}
.contact h2,
.contact .kicker {
  color: #e7d3a1;
}
.contact p {
  color: rgba(247, 244, 238, 0.8);
}
.contact ul {
  list-style: none;
  margin: 0;
  padding: 0;
  display: grid;
  gap: 10px;
}
.contact li span {
  display: block;
  font-size: 12px;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  opacity: 0.6;
}
.contact a,
.contact strong {
  color: #fff;
  font-weight: 600;
}
.btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  border-radius: 999px;
  padding: 10px 16px;
  text-decoration: none;
  font-size: 14px;
  font-weight: 700;
  border: 1px solid transparent;
  cursor: pointer;
}
.btn.primary {
  background: #c4a35a;
  color: #0e2438;
}
.btn.ghost {
  background: #fff;
  color: #0e2438;
  border-color: var(--line);
}
.btn.sm {
  padding: 8px 12px;
  font-size: 13px;
}
.empty {
  color: var(--muted);
}
@media (max-width: 860px) {
  #gioi-thieu,
  .pillars,
  .policy-grid,
  .room-grid,
  .services {
    grid-template-columns: 1fr;
  }
}
</style>
