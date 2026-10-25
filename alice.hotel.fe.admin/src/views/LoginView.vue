<template>
  <div class="auth">
    <aside class="auth-brand">
      <div class="brand-lockup">
        <span class="mark">A</span>
        <span>
          <strong>Alice Hotel</strong>
          <em>Hệ thống vận hành</em>
        </span>
      </div>
      <h1>Bàn điều hành cho cả ca trực</h1>
      <p class="lead">Lễ tân, sơ đồ phòng, giá và doanh thu nằm trên cùng một console — rõ số liệu, ít thao tác thừa.</p>
      <ul>
        <li>Lịch phòng và sơ đồ phòng theo thời gian thực</li>
        <li>Nhận phòng, phiếu thu, thu tiền theo ca</li>
        <li>Công suất phòng, giá trung bình và doanh thu trên phòng</li>
      </ul>
    </aside>
    <section class="auth-panel">
      <form class="auth-card" @submit.prevent="submit">
        <p class="eyebrow">Đăng nhập nhân viên</p>
        <h2>Alice Hotel · Quản trị</h2>
        <p class="hint">Dùng tài khoản được cấp cho lễ tân hoặc quản lý.</p>
        <label>
          <span>Tài khoản</span>
          <InputBase placeholder="Nhập tài khoản" v-model="loginForm.username" />
        </label>
        <label>
          <span>Mật khẩu</span>
          <InputBase placeholder="Nhập mật khẩu" type="password" v-model="loginForm.password" @keyup.enter="submit" />
        </label>
        <button class="button btn_primary btn_w_full btn_xxl" type="submit" :disabled="store.loading">
          {{ store.loading ? "Đang đăng nhập…" : "Đăng nhập" }}
        </button>
      </form>
    </section>
  </div>
  <LoadingComponent v-if="store.loading" />
</template>

<script setup lang="ts">
import LoadingComponent from "@/components/common/LoadingComponent.vue";
import { commonStore } from "@/stores/common";
import { onBeforeMount, ref } from "vue";
import InputBase from "@/components/common/input/InputBase.vue";
import Swal from "sweetalert2";
import commonService from "@/service/common/CommonService";
import { setUserInfo } from "@/utils/storage";
import { useRouter } from "vue-router";
import { SCREEN } from "@/router/screen";

const store = commonStore();
const router = useRouter();

const loginForm = ref({
  username: "",
  password: "",
});

/** Admin console luôn đăng nhập với loginDiv = a */
const LOGIN_DIV_ADMIN = "a";

const errorMessage = ref("");

onBeforeMount(() => {
  store.setShowMenu(false);
});

async function submit() {
  if (!loginForm.value.username || !loginForm.value.password) {
    await Swal.fire({
      text: "Vui lòng nhập tài khoản và mật khẩu.",
      showConfirmButton: true,
      confirmButtonColor: "#DD6B55",
      confirmButtonText: "Xác nhận",
    });
    return;
  }

  const obj = {
    userId: loginForm.value.username.trim(),
    password: loginForm.value.password,
    loginDiv: LOGIN_DIV_ADMIN,
  };

  store.setLoading(true);
  errorMessage.value = "";

  try {
    const response: any = await commonService.login(obj);
    if (response.status === 200 && response.data?.data) {
      // Keep ResultDTO shape: { data, code, message } — layouts read userInfo.data.*
      setUserInfo(response.data);
      store.setShowMenu(true);
      await router.replace({ name: SCREEN.home.name });
      return;
    }

    await Swal.fire({
      text: "Thông tin đăng nhập không đúng. Vui lòng kiểm tra lại.",
      showConfirmButton: true,
      confirmButtonColor: "#DD6B55",
      confirmButtonText: "Xác nhận",
    });
  } catch (e: any) {
    console.log(e);
    errorMessage.value = e?.message || "";
    await Swal.fire({
      text: e?.response?.data?.message || e?.response?.data || "Đăng nhập thất bại. Vui lòng thử lại.",
      showConfirmButton: true,
      confirmButtonColor: "#DD6B55",
      confirmButtonText: "Xác nhận",
    });
  } finally {
    store.setLoading(false);
  }
}
</script>

<style scoped lang="scss">
.auth {
  min-height: 100dvh;
  display: grid;
  grid-template-columns: minmax(320px, 1.05fr) minmax(360px, 0.95fr);
  background: #f4f7fb;
}
.auth-brand {
  position: relative;
  overflow: hidden;
  color: #f7f4ee;
  padding: 48px 56px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 28px;
  background:
    radial-gradient(circle at 18% 18%, rgba(196, 163, 90, 0.28), transparent 32%),
    linear-gradient(160deg, rgba(8, 22, 38, 0.2), rgba(8, 22, 38, 0.55)),
    #0e2438;
}
.auth-brand::after {
  content: "";
  position: absolute;
  width: 420px;
  height: 420px;
  right: -120px;
  top: -80px;
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 50%;
}
.brand-lockup {
  position: relative;
  z-index: 1;
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}
.mark {
  width: 42px;
  height: 42px;
  border-radius: 12px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(145deg, #e7d3a1, #c4a35a);
  color: #0e2438;
  font-weight: 700;
  font-size: 18px;
}
.brand-lockup strong {
  display: block;
  font-size: 16px;
  font-weight: 700;
}
.brand-lockup em {
  display: block;
  font-style: normal;
  font-size: 12px;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  opacity: 0.7;
}
.auth-brand h1 {
  position: relative;
  z-index: 1;
  margin: 0 0 12px;
  max-width: 460px;
  font-size: clamp(2.8rem, 4vw, 4.4rem);
  line-height: 1.15;
  font-weight: 500;
  letter-spacing: -0.03em;
}
.lead {
  position: relative;
  z-index: 1;
  margin: 0 0 28px;
  max-width: 460px;
  font-size: 15px;
  line-height: 1.6;
  color: rgba(247, 244, 238, 0.82);
}
.auth-brand ul {
  position: relative;
  z-index: 1;
  margin: 0;
  padding: 0;
  display: grid;
  gap: 10px;
}
.auth-brand li {
  list-style: none;
  font-size: 14px;
  padding-left: 16px;
  position: relative;
  color: rgba(247, 244, 238, 0.9);
}
.auth-brand li::before {
  content: "";
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: #c4a35a;
  position: absolute;
  left: 0;
  top: 0.55em;
}
.auth-panel {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px 24px;
}
.auth-card {
  width: min(420px, 100%);
  background: #fff;
  border: 1px solid #e4ecf2;
  border-radius: 16px;
  box-shadow: 0 18px 50px rgba(14, 36, 56, 0.08);
  padding: 36px 32px 32px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}
.eyebrow {
  margin: 0;
  font-size: 11px;
  letter-spacing: 0.14em;
  text-transform: uppercase;
  color: #6b7c88;
  font-weight: 600;
}
.auth-card h2 {
  margin: -8px 0 0;
  font-size: 2.6rem;
  line-height: 1.25;
  font-weight: 600;
  color: #0f1c24;
}
.hint {
  margin: 0 0 8px;
  color: #5b6b76;
  font-size: 14px;
  line-height: 1.5;
}
.auth-card label {
  display: flex;
  flex-direction: column;
  gap: 8px;
}
.auth-card label > span {
  font-size: 13px;
  font-weight: 600;
  color: #1d1d1d;
}
@media (max-width: 900px) {
  .auth {
    grid-template-columns: 1fr;
  }
  .auth-brand {
    min-height: 280px;
    padding: 28px 24px;
    justify-content: flex-start;
    gap: 18px;
  }
  .brand-lockup {
    margin-bottom: 12px;
  }
  .auth-brand ul {
    display: none;
  }
}
</style>
