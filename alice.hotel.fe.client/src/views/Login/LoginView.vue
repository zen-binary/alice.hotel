<template>
  <div class="guest-login">
  <BannerComponent
    variant="hotel"
    title="Alice Hotel"
    :description="['Đăng nhập', 'để đặt phòng']"
    :info="['Giữ chỗ, thanh toán cọc và xem lại đơn đã đặt.']"
    class-description="banner_description_login"
  />
  <PageContainer class="login_page">
    <h1>Đăng nhập</h1>
    <h3>Website khách dùng tài khoản khách hàng. Demo: guest / Alice@123</h3>
    <form class="login_wrapper" @submit.prevent="handleLogin">
      <div class="login_wrapper__content">
        <p class="login_wrapper__content__title">Khách hàng</p>
        <div class="login_wrapper__content__component">
          <p>Tài khoản</p>
          <InputBase placeholder="Nhập tài khoản" v-model="loginForm.account" />
        </div>
        <div class="login_wrapper__content__component">
          <p>Mật khẩu</p>
          <InputBase
            placeholder="Nhập mật khẩu"
            type="password"
            v-model="loginForm.password"
            @keyup.enter="handleLogin"
          />
        </div>
        <div class="button_group">
          <button class="button btn_primary btn_w_full btn_xxl" type="submit" :disabled="store.loading">
            {{ store.loading ? "Đang đăng nhập…" : "Đăng nhập" }}
          </button>
          <button class="button btn_secondary btn_w_full btn_xxl" type="button" @click="router.push('/register')">
            Đăng ký tài khoản
          </button>
        </div>
      </div>
    </form>
  </PageContainer>
  <LoadingComponent v-if="store.loading" />
  </div>
</template>

<script setup lang="ts">
import LoadingComponent from "@/components/common/LoadingComponent.vue";
import { commonStore } from "@/stores/common";
import { reactive } from "vue";
import BannerComponent from "@/components/common/BannerComponent.vue";
import PageContainer from "@/components/common/PageContainer.vue";
import InputBase from "@/components/common/input/InputBase.vue";
import { useFetchData } from "@/utils/useFetchData";
import { PostType } from "@/stores/common/demoFetchData/demo.type";
import { END_POINTS_LOGIN } from "@/stores/common/commonServices/commonServices.service";
import { SUCCSESS_STATUS } from "@/constants/screen.const";
import { setUserInfo } from "@/utils/storage";
import { SCREEN } from "@/router/screen";
import { useRouter } from "vue-router";
import Swal from "sweetalert2";

const store = commonStore();
const router = useRouter();

/** Website khách luôn đăng nhập với loginDiv = g */
const LOGIN_DIV_GUEST = "g";

const loginForm = reactive({
  account: "",
  password: "",
});

const handleLogin = async () => {
  if (!loginForm.account || !loginForm.password) {
    await Swal.fire({
      text: "Vui lòng nhập tài khoản và mật khẩu.",
      showConfirmButton: true,
      confirmButtonColor: "#0e2438",
      confirmButtonText: "Xác nhận",
    });
    return;
  }

  const obj = {
    userId: loginForm.account.trim(),
    password: loginForm.password,
    loginDiv: LOGIN_DIV_GUEST,
  };

  store.setLoading(true);
  try {
    const { dataFetch, statusFetch } = await useFetchData<PostType[]>(`${END_POINTS_LOGIN}`, {
      method: "POST",
      body: obj,
    });
    if (statusFetch.value === SUCCSESS_STATUS) {
      setUserInfo(dataFetch.value);
      await router.push({ name: SCREEN.home.name });
    } else {
      await Swal.fire({
        text: "Thông tin đăng nhập không đúng. Vui lòng kiểm tra lại.",
        showConfirmButton: true,
        confirmButtonColor: "#0e2438",
        confirmButtonText: "Xác nhận",
      });
    }
  } finally {
    store.setLoading(false);
  }
};
</script>

<style scoped lang="scss">
.guest-login {
  min-height: 100dvh;
  background: #f6f4ef;
}
.guest-login :deep(.block-banner.hotel .block-banner__content) {
  box-sizing: border-box;
  width: min(var(--main-width-vertical), calc(100% - 4rem));
  max-width: var(--main-width-vertical);
  padding-right: 0;
  padding-left: 0;
}
.page_container.login_page {
  padding: 28px 2rem 8rem;
  background: #f6f4ef;

  h1 {
    font-size: 4rem;
    font-weight: 500;
    line-height: 1.3;
    color: #0e2438;
  }

  h3 {
    margin-top: 1.2rem;
    margin-bottom: 2.8rem;
    font-size: 1.6rem;
    font-weight: 400;
    line-height: 1.5;
    color: #5d6b75;
  }

  .login_wrapper {
    padding: 36px 28px;
    border-radius: 16px;
    border: 1px solid #e7e1d6;
    background: #fff;
    box-shadow: 0 16px 40px rgba(14, 36, 56, 0.06);
    max-width: 520px;
    margin: 0 auto;
    position: relative;

    &__content {
      margin: auto;
      max-width: 360px;

      &__title {
        font-size: 2rem;
        font-weight: 600;
        line-height: 1.4;
        text-align: center;
        margin-bottom: 2.4rem;
        color: #14212b;
      }

      &__component {
        margin-bottom: 1.6rem;

        & > p {
          font-size: 1.4rem;
          font-weight: 600;
          margin-bottom: 0.8rem;
        }
      }

      .button_group {
        margin-top: 2.4rem;
        display: flex;
        flex-direction: column;
        gap: 10px;
      }
    }
  }
}

:deep(.custom-input) {
  background: #f7f5f1;
  border: 1px solid #efeae2;
}
</style>
