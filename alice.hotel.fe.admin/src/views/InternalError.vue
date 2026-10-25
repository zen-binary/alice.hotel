<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div class="error_wrap">
    <div class="error_box">
      <strong>Máy chủ đã xảy ra sự cố.</strong>
      <p>
        Máy chủ đang gặp sự cố tạm thời và không thể xử lý yêu cầu.<br/>
        Vui lòng thử lại sau hoặc chuyển sang trang khác.
      </p>
      <router-link :to="{ name: SCREEN.home.name }">Về trang chủ</router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
import {SCREEN} from "@/router/screen";

</script>

<style scoped>
.error_wrap {
  display: flex;
  padding: 20px 0;
  width: 100%;
  min-height: 100dvh;
  justify-content: center;
  align-items: center;
  background-color: #f1f1f1;
}

.error_box {
  padding: 20px 280px 20px 20px;
  width: 720px;
  min-height: 200px;
  color: #727272;
  text-align: left;
  word-break: keep-all;
  background: url("@/assets/images/bg_err.png") no-repeat right 40%;
  background-size: auto 200px;
}

.error_box strong {
  display: block;
  margin-bottom: 20px;
  font-size: 2.8rem;
  color: #464646;
}

.error_box p {
  margin-bottom: 20px;
  font-size: 1.7rem;
  line-height: 1.8em;
}

.error_box a {
  display: inline-block;
  padding: 10px 20px;
  font-size: 1.6rem;
  color: #fff;
  background: #727272;
  -webkit-border-radius: 30px;
  -moz-border-radius: 30px;
  border-radius: 30px;
  -webkit-transition: all 0.3s;
  -moz-transition: all 0.3s;
  -o-transition: all 0.3s;
  transition: all 0.3s;
}
</style>
