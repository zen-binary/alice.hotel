<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div class="error_wrap">
    <div class="error_box">
      <strong>서버에 문제가 발생했습니다.</strong>
      <p>
        현재 서버에 일시적인 문제가 발생하여 요청을 처리할 수 없습니다.<br/>
        잠시 후 다시 시도해 주시거나, 다른 페이지로 이동해 주십시오.
      </p>
      <router-link :to="{ name: SCREEN.home.name }">메인화면으로 이동</router-link>
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
