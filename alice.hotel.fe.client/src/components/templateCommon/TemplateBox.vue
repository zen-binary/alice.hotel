<template>
  <div>
    <div class="box">
      <div class="box_inner">
        <h3 class="box_title">BOX TITLE</h3>
        <p>Khi box có 2 vùng</p>
      </div>
      <div class="box_inner">
        <h3 class="box_title">BOX TITLE</h3>
        <p>Khi box có 2 vùng</p>
      </div>
    </div>
    <div class="divide_box">
      <div class="box">
        <h3 class="box_title">DIVIDE BOX TITLE</h3>
        <p>Khi chia ngoài box thành 2 vùng</p>
      </div>
      <div class="box">
        <h3 class="box_title">DIVIDE BOX TITLE</h3>
        <p>Khi chia ngoài box thành 2 vùng</p>
      </div>
    </div>
    <div class="box dp_block">
      <div class="box_section">
        <h4 class="box_title_sm">BOX box_title_sm</h4>
        <p>Nhiều vùng trên một hàng trong box</p>
      </div>
      <div class="box_section">
        <h4 class="box_title_sm">BOX box_title_sm</h4>
        <p>Nhiều vùng trên một hàng trong box</p>
      </div>
    </div>
    <div class="box">
      <div class="box_inner">
        <h3 class="box_title">BOX TITLE</h3>
        <div class="box_title_funtion">
          <a href="javascript:void(0);">Link cạnh tiêu đề</a>
        </div>
      </div>
      <div class="box_inner">
        <h3 class="box_title">BOX TITLE</h3>
        <div class="box_title_funtion">
          <p class="check_row">
            <input id="title_check" type="checkbox" name="title_check" />
            <label for="title_check">Checkbox cạnh tiêu đề</label>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped></style>
