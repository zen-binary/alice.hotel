<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div>
    <div class="box">
      <div class="box_inner">
        <h3 class="box_title">BOX TITLE</h3>
        <p>box 내 영역 2개일때</p>
      </div>
      <div class="box_inner">
        <h3 class="box_title">BOX TITLE</h3>
        <p>box 내 영역 2개일때</p>
      </div>
    </div>
    <div class="divide_box">
      <div class="box">
        <h3 class="box_title">DIVIDE BOX TITLE</h3>
        <p>box 외 영역 2개로 나눌때</p>
      </div>
      <div class="box">
        <h3 class="box_title">DIVIDE BOX TITLE</h3>
        <p>box 외 영역 2개로 나눌때</p>
      </div>
    </div>
    <div class="box dp_block">
      <div class="box_section">
        <h4 class="box_title_sm">BOX box_title_sm</h4>
        <p>box 내에 영역 한줄로 여러개</p>
      </div>
      <div class="box_section">
        <h4 class="box_title_sm">BOX box_title_sm</h4>
        <p>box 내에 영역 한줄로 여러개</p>
      </div>
    </div>
    <div class="box">
      <div class="box_inner">
        <h3 class="box_title">BOX TITLE</h3>
        <div class="box_title_funtion">
          <a href="javascript:void(0);">title 옆 링크</a>
        </div>
      </div>
      <div class="box_inner">
        <h3 class="box_title">BOX TITLE</h3>
        <div class="box_title_funtion">
          <p class="check_row">
            <input id="title_check" type="checkbox" name="title_check" />
            <label for="title_check">title 옆 체크박스</label>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped></style>
