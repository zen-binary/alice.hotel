<template>
  <PageContainer class="login_page">
    <div class="login_wrapper">
      <div class="login_wrapper__content">
        <p class="login_wrapper__content__title">Login</p>
        <div class="login_wrapper__content__component">
          <p>Role</p>
          <RadiobuttonBase
            v-for="item in listRole"
            :mode="modeShow"
            :value="item.cdId"
            :id="'status' + item.cdId"
            :name="'status' + item.cdId"
            :key="'status' + item.cdId"
            :label="item.cdNm"
            v-model="loginForm.status"
            :checked="loginForm.status === item.cdId"
          ></RadiobuttonBase>
        </div>
        <div class="login_wrapper__content__component">
          <p>Username</p>
          <InputBase placeholder="Username" v-model="loginForm.username" />
        </div>
        <div class="login_wrapper__content__component">
          <p>Password</p>
          <InputBase placeholder="Password" v-model="loginForm.password" />
        </div>
        <div class="button_group">
          <button class="button btn_primary btn_w_full btn_xxl" @click="submit()">Login</button>
        </div>
      </div>
    </div>
  </PageContainer>
  <LoadingComponent v-if="store.loading" />
</template>

<script setup lang="ts">
import LoadingComponent from "@/components/common/LoadingComponent.vue";
import { commonStore } from "@/stores/common";
import PageContainer from "@/components/common/PageContainer.vue";
import { onBeforeMount, ref } from "vue";
import InputBase from "@/components/common/input/InputBase.vue";
import Swal from "sweetalert2";
import commonService from "@/service/common/CommonService";
import { setUserInfo, setLocalStorage } from "@/utils/storage";
import { useRouter } from "vue-router";

const store = commonStore();

const listRole = ref([
  {
    cdId: "a",
    cdNm: "Admin",
  },
  {
    cdId: "o",
    cdNm: "Corporate member",
  },
]);

const loginForm = ref({
  username: "",
  password: "",
  status: "a",
  saveLogin: false,
});

const errorMessage = ref();
const router = useRouter();

onBeforeMount(() => {
  store.setShowMenu(false);
});

function submit() {
  const obj = {
    userId: loginForm.value.username,
    password: loginForm.value.password,
    loginDiv: loginForm.value.status,
  };
  store.setLoading(true);
  errorMessage.value = "";

  commonService
    .login(obj)
    .then((response: any) => {
      store.setLoading(false);
      if (response.status == 200 && response.data.data) {
        // setLocalStorage(SAVE_ID, {
        //   userName: loginForm.value.username,
        //   isRemember: loginParam.value.idSave,
        // });
        setUserInfo(response.data.data);
        router.replace({ name: "moveMenu" });
      } else {
        Swal.fire({
          text: "로그인 정보가 잘못되었습니다. 확인해 주십시오",
          showConfirmButton: true,
          confirmButtonColor: "#DD6B55",
          confirmButtonText: "확인",
        });
      }
      router.push("/");
    })
    .catch((e: any) => {
      console.log(e);
      errorMessage.value = e.message;
    })
    .finally(() => {
      store.setLoading(false);
    });
}
</script>

<style scoped lang="scss">
.page_container.login_page {
  padding-bottom: 8rem;

  h1 {
    font-size: 4rem;
    font-weight: 500;
    line-height: 6rem;
  }

  h3 {
    margin-top: 1.6rem;
    margin-bottom: 2.4rem;
    font-size: 1.6rem;
    font-weight: 400;
    line-height: 2.4rem;
    color: var(--Gray-scale-70, rgba(85, 85, 85, 1));
  }

  .login_wrapper {
    padding: 32px 20px 32px 20px;
    border-radius: 8px;
    border: 1px solid var(--Gray-scale-70, rgba(85, 85, 85, 1));
    max-width: 640px;
    margin: 0 auto;

    &__content {
      margin: auto;
      max-width: 295px;

      &__title {
        font-size: 2.4rem;
        font-weight: 500;
        line-height: 3.6rem;

        text-align: center;
        margin-bottom: 4rem;
      }

      &__component {
        &:last-of-type {
          margin-top: 2rem;
          margin-bottom: 2.4rem;
        }

        & > p {
          font-size: 1.4rem;
          font-weight: 500;
          line-height: 2.027rem;
          color: var(--Gray-scale-90, rgba(29, 29, 29, 1));
          margin-bottom: 0.8rem;
        }

        & > input {
          border: 1px solid var(--Gray-scale-5, rgba(248, 248, 248, 1));
          background: var(--Gray-scale-5, rgba(248, 248, 248, 1));
        }
      }

      .button_group {
        margin-bottom: 2rem;
        margin-top: 2.4rem;
        display: flex;
        gap: 16px;
      }
    }
  }
}

.page_container.login_footer {
  padding-top: 100px;
  padding-bottom: 100px;
  background: rgba(248, 249, 254, 1);

  .login_wrapper {
    display: flex;
    justify-content: space-between;

    &__left {
      display: flex;
      flex-direction: column;
      gap: 2.4rem;
      text-align: right;

      & p:first-child {
        font-size: 1.6rem;
        font-weight: 400;
        line-height: 2.4rem;

        position: relative;

        &::before {
          content: "";
          position: absolute;
          top: 50%;
          left: 0;
          width: 60px;
          height: 1px;
          background: var(--Gray-scale-80, rgba(45, 45, 45, 1));
          transform: translateY(50%);
        }
      }

      & p:last-child {
        font-size: 4rem;
        font-weight: 500;
        line-height: 6rem;
      }
    }

    &__right {
      flex: 1;
      max-width: 986px;
      margin-left: 19.2rem;

      .footer__menu__tab {
        display: flex;
        justify-content: space-between;
        border-bottom: 1px solid black;
        padding-bottom: 3.6rem;

        &__title {
          flex: 1;
          display: flex;
          justify-content: space-between;
          align-items: center;
          margin-left: 5.6rem;
          box-sizing: border-box;

          &:first-child {
            margin-left: 0rem;
          }

          & p {
            font-size: 2.4rem;
            font-weight: 500;
            line-height: 3.6rem;
            cursor: pointer;
            color: var(--Gray-scale-60, rgba(113, 113, 113, 1));
          }
        }

        &__title.active {
          & p {
            color: var(--Gray-scale-90, rgba(29, 29, 29, 1));
          }
        }

        &__content {
        }
      }

      .footer__content {
        display: flex;
        align-items: center;
        gap: 5.6rem;
        padding: 20px 0;
        box-sizing: border-box;
        border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

        &__time {
          & p:first-child {
            font-size: 3.6rem;
            font-weight: 700;
            line-height: 5.213rem;
            text-align: center;
          }

          & p:last-child {
            font-size: 1.4rem;
            font-weight: 400;
            line-height: 2.1rem;
            text-align: center;
            color: var(--Gray-scale-60, rgba(113, 113, 113, 1));
          }
        }

        &__description {
          font-size: 1.6rem;
          font-weight: 500;
          line-height: 2.4rem;
        }
      }

      & a {
        font-size: 1.6rem;
        font-weight: 400;
        line-height: 2.4rem;
        position: relative;
        padding-right: 24px;

        &::after {
          content: "";
          position: absolute;
          top: 0;
          transform: translateY(50%);
          right: 0;
          background: url("@/assets/images/icon_vector_more.svg") no-repeat center;
          width: 12px;
          height: 12px;
        }
      }

      .footer_link {
        display: none;
        width: 100%;
        text-align: right;
        margin-top: 2.8rem;
      }
    }
  }
}

@media screen and (max-width: 768px) {
  .page_container.login_page {
    h1 {
      font-size: 2.6rem;
      font-weight: 500;
      line-height: 3.9rem;
    }

    h3 {
      margin-top: 1.2rem;
      margin-bottom: 2.4rem;
      font-size: 1.4rem;
      font-weight: 400;
      line-height: 2.1rem;
    }
  }
  .page_container.login_footer {
    padding-top: 80px;

    .login_wrapper {
      display: flex;
      flex-direction: column;
      gap: 4rem;

      &__left {
        flex-direction: column;
        gap: 1.6rem;
        text-align: left;

        & p:first-child {
          font-size: 14px;
          font-weight: 400;
          line-height: 21px;
          padding-left: 44px;

          &::before {
            content: "";
            position: absolute;
            top: 50%;
            left: 0;
            width: 28px;
            height: 1px;
            background: var(--Gray-scale-80, rgba(45, 45, 45, 1));
            transform: translateY(50%);
          }
        }

        & p:last-child {
          font-size: 2.6rem;
          font-weight: 500;
          line-height: 3.9rem;
        }
      }

      &__right {
        flex: 1;
        max-width: 986px;
        margin-left: 0rem;

        .footer__menu__tab {
          justify-content: left;
          gap: 2rem;

          &__title {
            flex: initial;
            margin-left: 0rem;

            & a {
              display: none;
            }
          }

          &__content {
          }
        }

        .footer__content {
          &__time {
            & p:first-child {
              font-size: 2.8rem;
              line-height: 4.054rem;
            }
          }

          &__description {
            font-size: 1.6rem;
            font-weight: 500;
            line-height: 2.4rem;
          }
        }

        .footer_link {
          display: block;
        }
      }
    }
  }
}
</style>
