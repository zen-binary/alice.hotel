<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <!-- [pp] 에러 화면 -->
  <div class="error_wrap">
    <div class="error_box">
      <strong>요청하신 페이지를 찾을 수 없습니다.</strong>
      <p>
        사이트 이용에 불편을 드려 죄송합니다.<br/>
        새로고침 &#40;F5&#41;키를 누르거나, 잠시 후에 다시 이용해 주십시오.
      </p>
      <router-link :to="{name:SCREEN.home.name}">메인화면으로 이동</router-link>
    </div>
  </div>
  <!-- [pp] //에러 화면 -->
</template>

<script setup lang="ts">

import {SCREEN} from "@/router/screen";
</script>

<style scoped lang="scss">
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
