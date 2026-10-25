<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <label class="switch">
    <input v-model="toggleMode" type="checkbox" @change.prevent.stop="setTheme(toggleMode)" />
    <span class="slider" />
  </label>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";

const toggleMode = ref();

function setTheme(value: boolean) {
  let check = value ? "drank" : "light";
  document.documentElement.setAttribute("data-theme", check);
  localStorage.setItem("theme", check);
}

onMounted(() => {
  let theme = localStorage.getItem("theme");
  toggleMode.value = theme === "drank";
  document.documentElement.setAttribute("data-theme", theme);
});
</script>

<style scoped lang="scss">
/* The switch - the box around the slider */
.switch {
  font-size: 17px;
  position: relative;
  display: inline-block;
  width: 3em;
  height: 1.5em;
}

/* Hide default HTML checkbox */
.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

/* The slider */
.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #2196f3;
  transition: 0.4s;
  border-radius: 30px;
}

.slider:before {
  position: absolute;
  content: "";
  height: 1.4em;
  width: 1.4em;
  border-radius: 20px;
  left: 0.5em;
  bottom: 0.4em;
  background-color: yellow;
  transition: 0.4s;
}

.switch input:checked + .slider:before {
  background-color: black;
  border-radius: 50px;
  box-shadow: inset -9px 0px 1px 0px white;
}

.switch input:checked + .slider {
  background-color: black;
}

.switch input:focus + .slider {
  box-shadow: 0 0 1px #2196f3;
}

.switch input:checked + .slider:before {
  transform: translateX(2em);
}
</style>
