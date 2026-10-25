<template>
  <footer id="footer" class="footer_wrapper">
    <div class="grid_content">
      <h2>
        <a href="javascript:void(0);">
          <img src="@/assets/images/icon_logo_white.svg" alt="위덕대학교 현장실습">
        </a>
      </h2>
      <div class="footer_wrap">
        <ul class="footer_link">
          <li>
            <router-link :to="{ name: SCREEN.terms.name }">이용약관</router-link>
          </li>
          <li class="">
            <router-link :to="{name:SCREEN.privacyPolicy.name}">개인정보 처리방침</router-link>
          </li>
          <li class="">
            <a href="javascript:void(0);">이메일무단수집거부</a>
          </li>
          <li class="">
            <router-link :to="{name:SCREEN.map.name}">찾아오시는 길</router-link>
          </li>
        </ul>
        <ul class="footer_info">
          <li class="address">
            <span>경상북도 경주시 강동면 동해대로 261 [38004]</span></li>
          <li>
            <span>안내전화 </span>
            <a href="tel:054-760-1122">054-760-1122</a>
          </li>
          <li>
            <span> FAX. 054-760-1129</span>
          </li>
          <li>
            <span>E-mail. </span>
            <a href="mailto:webmaster@uu.ac.kr">webmaster@uu.ac.kr</a>
          </li>
        </ul>
        <p class="copyright">Uiduk University . All rights reserved.</p>
      </div>
    </div>
  </footer>
  <div>
    <a href="#" class="button btn_scroll" :class="{ on: isScroll }" @click="handleToTop">Scroll to top</a>
  </div>
</template>

<script setup lang="ts">
import globalCommom from "@/utils/global";
import {SCREEN} from "@/router/screen";

const {isScroll} = globalCommom.useScroll(200);

const handleToTop = () => {
  globalCommom.scrollToTop()
}
</script>

<style scoped>
.router-link-active.router-link-exact-active {
  color: white;
}
</style>
