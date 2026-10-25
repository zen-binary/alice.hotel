<template>
  <footer id="footer" class="footer_wrapper hotel-footer">
    <div class="grid_content">
      <div class="hotel-foot-brand">
        <strong>Alice Hotel</strong>
        <p>Khách sạn thành phố tại Hà Nội. Giá phòng, giờ nhận trả và điều kiện hủy được ghi trước khi giữ chỗ.</p>
      </div>
      <ul class="footer_info">
        <li><a href="/#gioi-thieu">Giới thiệu</a></li>
        <li><a href="/#hang-phong">Hạng phòng</a></li>
        <li><a href="/#luu-tru">Lưu trú</a></li>
        <li><router-link to="/dat-phong">Đặt phòng</router-link></li>
        <li><a href="/#lien-he">Liên hệ</a></li>
      </ul>
      <p class="copyright">© Alice Hotel. Mọi quyền được bảo lưu.</p>
    </div>
  </footer>
  <div>
    <a href="#" class="button btn_scroll" :class="{ on: isScroll }" @click.prevent="handleToTop">Lên đầu trang</a>
  </div>
</template>

<script setup lang="ts">
import globalCommom from "@/utils/global";

const { isScroll } = globalCommom.useScroll(200);

const handleToTop = (event: Event) => {
  event.preventDefault();
  globalCommom.scrollToTop();
};
</script>

<style lang="scss">
.hotel-footer.footer_wrapper {
  background: #0e2438;
  color: rgba(247, 244, 238, 0.72);
}

.hotel-footer.footer_wrapper .grid_content {
  align-items: flex-start;
  gap: 28px 48px;
  flex-wrap: wrap;
}

.hotel-foot-brand {
  min-width: 220px;
}

.hotel-foot-brand strong {
  display: block;
  color: #f7f4ee;
  font-size: 18px;
  font-weight: 700;
  margin-bottom: 6px;
}

.hotel-foot-brand p {
  margin: 0;
  max-width: 280px;
  line-height: 1.5;
}

.hotel-footer .footer_info {
  margin: 0;
  padding: 0;
}

.hotel-footer .footer_info a {
  color: #e7d3a1;
}

.hotel-footer .copyright {
  margin-left: auto;
  color: rgba(247, 244, 238, 0.55);
}

@media (max-width: 800px) {
  .hotel-footer .copyright {
    margin-left: 0;
  }
}
</style>
