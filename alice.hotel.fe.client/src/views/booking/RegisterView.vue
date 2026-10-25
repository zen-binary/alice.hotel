<template>
  <div class="auth">
    <aside class="auth-brand">
      <router-link class="brand-lockup" to="/">
        <span class="mark">A</span>
        <span>
          <strong>Alice Hotel</strong>
          <em>Tài khoản khách</em>
        </span>
      </router-link>
      <h1>Mở tài khoản trong một phút</h1>
      <p class="lead">Nhận mã OTP qua email, sau đó dùng tài khoản để đặt phòng và theo dõi đơn.</p>
    </aside>
    <section class="auth-panel">
      <form class="auth-card" @submit.prevent="register">
        <p class="eyebrow">Đăng ký</p>
        <h2>Tạo tài khoản</h2>
        <div class="grid">
          <label>
            <span>Tên đăng nhập</span>
            <InputBase placeholder="Ví dụ: guest" v-model="form.userId" />
          </label>
          <label>
            <span>Họ tên</span>
            <InputBase placeholder="Nguyễn Văn A" v-model="form.userName" />
          </label>
          <label>
            <span>Email</span>
            <InputBase placeholder="ban@email.com" v-model="form.email" />
          </label>
          <label>
            <span>Số điện thoại</span>
            <InputBase placeholder="09xx" v-model="form.phone" />
          </label>
          <label class="full">
            <span>Mật khẩu</span>
            <InputBase placeholder="Ít nhất 6 ký tự" type="password" v-model="form.password" />
          </label>
          <label class="full otp">
            <span>Mã OTP</span>
            <div class="otp-row">
              <InputBase placeholder="Nhập mã đã gửi" v-model="form.otp" />
              <button class="button btn_md btn_lightgray" type="button" :disabled="sending" @click="sendOtp">
                {{ sending ? "Đang gửi…" : "Gửi OTP" }}
              </button>
            </div>
          </label>
        </div>
        <button class="button btn_primary btn_w_full btn_xxl" type="submit" :disabled="busy">
          {{ busy ? "Đang tạo…" : "Đăng ký" }}
        </button>
        <p class="switch">
          Đã có tài khoản?
          <router-link to="/login">Đăng nhập</router-link>
        </p>
      </form>
    </section>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import Swal from "sweetalert2";
import InputBase from "@/components/common/input/InputBase.vue";
import { clientBookingStore } from "@/stores/clientBooking";

const store = clientBookingStore();
const router = useRouter();
const sending = ref(false);
const busy = ref(false);
const form = reactive({
  userId: "",
  userName: "",
  email: "",
  phone: "",
  password: "",
  otp: "",
});

const notify = (text: string) =>
  Swal.fire({
    text,
    confirmButtonColor: "#0e2438",
    confirmButtonText: "Đã hiểu",
  });

const sendOtp = async () => {
  if (!form.email) {
    await notify("Nhập email trước khi gửi OTP.");
    return;
  }
  sending.value = true;
  try {
    await store.sendOtp(form.email);
    await notify("OTP đã được gửi. Nếu chưa cấu hình SMTP, xem mã trong log máy chủ.");
  } catch {
    await notify("Không gửi được OTP. Kiểm tra email và thử lại.");
  } finally {
    sending.value = false;
  }
};

const register = async () => {
  if (!form.userId || !form.userName || !form.email || !form.password || !form.otp) {
    await notify("Điền đủ tên đăng nhập, họ tên, email, mật khẩu và OTP.");
    return;
  }
  busy.value = true;
  try {
    await store.register({ ...form });
    await notify("Đăng ký thành công. Hãy đăng nhập để đặt phòng.");
    await router.push("/login");
  } catch {
    await notify("Đăng ký chưa thành công. Kiểm tra OTP hoặc tên đăng nhập đã tồn tại.");
  } finally {
    busy.value = false;
  }
};
</script>

<style scoped lang="scss">
.auth {
  min-height: 100dvh;
  display: grid;
  grid-template-columns: minmax(260px, 0.8fr) minmax(420px, 1.2fr);
  background: #f6f4ef;
}
.auth-brand {
  position: relative;
  color: #f7f4ee;
  padding: 48px 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 20px;
  background:
    radial-gradient(circle at 20% 80%, rgba(196, 163, 90, 0.28), transparent 34%),
    linear-gradient(165deg, #16344a, #0e2438 62%);
}
.brand-lockup {
  display: flex;
  align-items: center;
  gap: 12px;
  color: inherit;
  text-decoration: none;
  position: absolute;
  top: 36px;
  left: 40px;
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
}
.brand-lockup strong,
.brand-lockup em {
  display: block;
}
.brand-lockup em {
  font-style: normal;
  font-size: 12px;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  opacity: 0.7;
}
.auth-brand h1 {
  margin: 24px 0 12px;
  font-size: clamp(2.6rem, 3vw, 3.6rem);
  line-height: 1.15;
  font-weight: 500;
}
.lead {
  margin: 0;
  max-width: 360px;
  line-height: 1.6;
  color: rgba(247, 244, 238, 0.82);
}
.auth-panel {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 32px 20px;
}
.auth-card {
  width: min(640px, 100%);
  background: #fff;
  border: 1px solid #e7e1d6;
  border-radius: 16px;
  box-shadow: 0 18px 50px rgba(14, 36, 56, 0.08);
  padding: 32px;
}
.eyebrow {
  margin: 0;
  font-size: 11px;
  letter-spacing: 0.14em;
  text-transform: uppercase;
  color: #8a7960;
  font-weight: 700;
}
.auth-card h2 {
  margin: 6px 0 18px;
  font-size: 2.6rem;
  font-weight: 600;
  color: #14212b;
}
.grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 14px;
  margin-bottom: 18px;
}
.grid label {
  display: flex;
  flex-direction: column;
  gap: 8px;
}
.grid .full {
  grid-column: 1 / -1;
}
.grid label > span {
  font-size: 13px;
  font-weight: 600;
}
.otp-row {
  display: grid;
  grid-template-columns: 1fr auto;
  gap: 8px;
  align-items: center;
}
.switch {
  margin: 14px 0 0;
  text-align: center;
  color: #5d6b75;
  font-size: 14px;
}
.switch a {
  margin-left: 4px;
  color: #0e2438;
  font-weight: 700;
}
@media (max-width: 900px) {
  .auth,
  .grid {
    grid-template-columns: 1fr;
  }
  .auth-brand {
    min-height: 220px;
    padding: 24px;
  }
  .brand-lockup {
    position: static;
  }
}
</style>
