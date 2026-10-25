<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <footer id="footer" class="footer_wrapper">
    <p>Alice Hotel. Bản quyền thuộc về Alice Hotel.</p>
  </footer>
  <a href="#" class="button btn_scroll" :class="{ on: isScroll }" @click.prevent="handleToTop">Lên đầu trang</a>
</template>

<script setup lang="ts">
import globalCommom from "@/utils/global";

const {isScroll} = globalCommom.useScroll(200);

const handleToTop = () => {
  window.scrollTo({
    top: 0,
    behavior: 'smooth'
  });
}
</script>

<style scoped></style>
