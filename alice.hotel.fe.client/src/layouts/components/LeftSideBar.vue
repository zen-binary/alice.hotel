<template>
  <aside class="left-side-bar">
    <div class="side-bar-container">
      <section class="menu">
        <aside class="logo">
          <p>Logo</p>
        </aside>
        <aside class="header_func">
          <div class="header_func_left">
            <label class="burger" for="burger">
              <input id="burger" v-model="checked" type="checkbox" />
              <span />
              <span />
              <span />
            </label>
          </div>
          <section class="header_func_right">
            <aside class="header_func_info">
              <!--              <IconUser color="#1f4575"/>-->
              <!--              <p>name</p>-->
            </aside>
            <aside class="header_func_main">
              <!--              <IconMonitor color="#1fccccc  ccc   c c4575"/>-->
              <!--              <p>text</p>-->
            </aside>
            <aside class="header_func_logout">
              <!--              <IconLogout color="#1f4575"/>-->
              <p>logout</p>
            </aside>
          </section>
        </aside>
      </section>
    </div>
  </aside>
</template>
<script setup lang="ts">
import { ref, watch } from "vue";

const checked = ref(false);

watch(checked, () => {
  const header_wrap = document.getElementsByClassName("header_wrapper")[0];
  console.log(checked);
  checked.value ? header_wrap.classList.add("on") : header_wrap.classList.remove("on");
});
</script>

<style></style>
