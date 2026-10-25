<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<script setup lang="ts"></script>

<template>
  <div class="overlay" style="display: block">
    <div id="loading" style="display: block;">
      <p id="loading_image">
        <img src="@/assets/images/loading.gif" alt="Loading..."><br>
        <span>Loading</span>
      </p>
    </div>
  </div>
</template>

<style>
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1000;
}
#loading {
  width: 100%; height: 100%;
  position: fixed; top: 0; bottom: 0;
  background: rgba(255,255,255,0.6);
  z-index: 1000;
}
#loading #loading_image{
  position: absolute; top: 50%; left: 50%;
  text-align: center;
  -webkit-transform: translate(-50%, -50%);
  -moz-transform: translate(-50%, -50%);
  -o-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
}
#loading #loading_image img {
  height: 140px;
}
#loading #loading_image span {
  display: block;
  padding: 0px 5px;
  font-weight: bold; font-size: 1.2rem;
}
</style>
