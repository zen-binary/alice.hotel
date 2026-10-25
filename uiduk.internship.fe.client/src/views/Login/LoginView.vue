<template>
  <THeaderHorizontalV2 :isBanner="isScroll"/>
  <BannerComponent
      img-url="/images/banner_login.webp"
      title="WE DO"
      :description="['현장실습','온라인 시스템']"
      :info="[]"
      class-description="banner_description_login"
  />
  <PageContainer class="login_page">
    <h1 v-once>{{ pageTitle }}</h1>
    <h3>현장실습 시스템은 로그인 후 이용가능합니다.</h3>
    <SelectorComponent
        v-model="loginForm.mode"
        :data="titleForm"
        class="mg_b32"
    />

    <!-- Login SSO   -->
<!--    <div class="login_wrapper" v-if="loginForm.mode === 3">-->
<!--      <div class="login_wrapper__content">-->
<!--        <p v-if="containerClass === 'large'" class="login_wrapper__content__title">{{ titleForm[loginForm.mode - 1] }}</p>-->
<!--        <p class="login_wrapper__content__des">현장실습 시스템은 로그인 후 이용가능합니다.</p>-->
<!--        <div class="login_wrapper__content__component">-->
<!--          <p>아이디</p>-->
<!--          <InputBase placeholder="아이디를 입력하세요." v-model="loginForm.account"/>-->
<!--        </div>-->
<!--        <div class="login_wrapper__content__component">-->
<!--          <p>비밀번호</p>-->
<!--          <InputBase placeholder="비밀번호를 입력하세요." type="password" v-model="loginForm.password"/>-->
<!--        </div>-->
<!--        <div class="button_group">-->
<!--          <button class="button btn_primary btn_w_full btn_xxl" @click="handleLogin">로그인</button>-->
<!--          <button class="button btn_primary btn_w_full btn_xxl" @click="redirectPolycy">회원가입-->
<!--          </button>-->
<!--        </div>-->

<!--        <CheckboxBase-->
<!--            :label="'아이디 저장'"-->
<!--            id="save_info"-->
<!--            v-model="loginForm.saveLogin"-->
<!--        />-->

<!--      </div>-->
<!--    </div>-->
<!--    <div class="login_wrapper" v-else>-->
<!--      <div class="login_wrapper__content">-->
<!--        <p v-if="containerClass === 'large'" class="login_wrapper__content__title">{{ titleForm[loginForm.mode - 1] }}</p>-->
<!--        <div class="button_group">-->
<!--          <button class="button btn_primary btn_w_full btn_xxl" @click="handleLoginSSO">통합 로그인</button>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->

    <div class="login_wrapper">
      <div class="login_wrapper__content">
        <p v-if="containerClass === 'large'" class="login_wrapper__content__title">{{ titleForm[loginForm.mode - 1] }}</p>
        <p v-if="loginForm.mode === 3" class="login_wrapper__content__des">현장실습 시스템은 로그인 후 이용가능합니다.</p>
        <div class="login_wrapper__content__component">
          <p>아이디</p>
          <InputBase placeholder="아이디를 입력하세요." v-model="loginForm.account"/>
        </div>
        <div class="login_wrapper__content__component">
          <p>비밀번호</p>
          <InputBase placeholder="비밀번호를 입력하세요." type="password" v-model="loginForm.password"/>
        </div>
        <div class="button_group">
          <button class="button btn_primary btn_w_full btn_xxl" @click="handleLogin">로그인</button>
          <button v-if="loginForm.mode===3" class="button btn_primary btn_w_full btn_xxl" @click="redirectPolycy">회원가입
          </button>
        </div>

        <CheckboxBase
            :label="'아이디 저장'"
            id="save_info"
            v-model="loginForm.saveLogin"
        />

      </div>
    </div>
  </PageContainer>
  <PageContainer class="login_footer">
    <div class="login_wrapper">
      <div class="login_wrapper__left">
        <p>Notice</p>
        <p>공지사항</p>
      </div>
      <div class="login_wrapper__right">
        <div class="footer__menu__tab">
          <div v-for="(i, index) in noti" class="footer__menu__tab__title" :class="{ active: index === activeTabFooter }" :key="index">
            <p @click.prevent="activeTabFooter = index" class="item_title">{{ i.title }}</p>
            <a :href="i.url" target="_blank">More</a>
          </div>
        </div>
        <div class="footer__content" v-for="i in notices">
          <div class="footer__content__time" >
            <p>{{ i.day }}</p>
            <p>{{ i.date }}</p>
          </div>
          <div class="footer__content__description item_card" @click="goToDetail(i.id)">{{ i.title}}</div>
        </div>

        <div class="footer_link">
          <a :href="noti[activeTabFooter].url" target="_blank">More</a>
        </div>
      </div>
    </div>
  </PageContainer>
  <TFooter />
  <LoadingComponent v-if="store.loading" />
  <ConsentModal ref="consentModal" @cancel="closeModal" @confirm="handleConfirm" />
</template>

<script setup lang="ts">
import LoadingComponent from "@/components/common/LoadingComponent.vue";
import { commonStore } from "@/stores/common";
import BannerComponent from "@/components/common/BannerComponent.vue";
import PageContainer from "@/components/common/PageContainer.vue";
import { onBeforeMount, onMounted, onUnmounted, reactive, ref } from "vue";
import CheckboxBase from "@/components/common/input/CheckboxBase.vue";
import SelectorComponent from "@/components/common/SelectorComponent.vue";
import InputBase from "@/components/common/input/InputBase.vue";
import globalCommon from "@/utils/global";
import { useFetchData } from "@/utils/useFetchData";
import { PostType } from "@/stores/common/demoFetchData/demo.type";
import { END_POINTS_LOGIN, END_POINTS_LOGIN_ADMIN } from "@/stores/common/commonServices/commonServices.service";
import { SUCCSESS_STATUS } from "@/constants/screen.const";
import {getLocalStorage, getUserInfo, setLocalStorage, setUserInfo} from "@/utils/storage";
import { MODE_DETAIL, REMEMBER_KEY } from "@/constants/common.const";
import { SCREEN } from "@/router/screen";
import { useRouter } from "vue-router";
import TFooter from "@/layouts/components/TFooter.vue";
import THeaderHorizontalV2 from "@/layouts/components/THeaderHorizontalV2.vue";
import ConsentModal from "@/components/Modal/ConsentModal.vue";
import commonService from "@/service/common/CommonService";
import { schoolNoticeStore } from "@/stores/schoolNotice";
import { LoginSchoolNoticeResDTO } from "@/stores/schoolNotice/schoolNotice.type";

const store = commonStore();
const router = useRouter();
const pageTitle = ref("로그인");
const titleForm = reactive(["학생", "교원", "실습기관", "지원센터"]);
const roleLogin = reactive(["s", "p", "o", "a"]);
const consentModal = ref(null)

const schoolNotice = schoolNoticeStore();
const schoolNoticeList = ref<LoginSchoolNoticeResDTO[]>([]);
const notices = ref([]);

const getSchoolNotice = async () => {
  await schoolNotice.fetchLoginSchoolNotice({
    method: "POST",
  });
  schoolNoticeList.value = schoolNotice.listLoginSchoolNotice;
  const {notices: transformedNotices} = transformData();
  notices.value = transformedNotices;
};

const transformData = () => {
  const listNotices = schoolNoticeList.value || [];
  const notices = listNotices.map((notice) => {
    const regDate = new Date(notice.regDate);
    return {
      id: notice.noticeSeq,
      day: regDate.getDate().toString(),
      date: `${regDate.getFullYear()}.${regDate.getMonth() + 1}`,
      title: notice.title,
    };
  });
  return { notices };
};

const goToDetail = (id: string) => {
  const mode = MODE_DETAIL;
  router.push({ 
    name: SCREEN.schoolNoticeCRUD.name, 
    params: { path: mode },
    state: {
      id
    }
  }
  );
};

const loginForm = reactive({
  mode: 1,
  account: "",
  password: "",
  loginDiv: roleLogin[0],
  saveLogin: false,
});
const info = ref({});

const activeTabFooter = ref(0);
const clientUrl = import.meta.env.VITE_DOMAIN_SITE_CLIENT;
const noti = reactive([
  {
    title: "학교공지",
    url: clientUrl + "/school-notice",
  },
]);
const idInfo = ref(REMEMBER_KEY);

onMounted(() => {
  saveInfo();
  getSchoolNotice();
});

watch(
  () => loginForm.mode,
  (value) => {
    loginForm.account = "";
    loginForm.password = "";
    loginForm.loginDiv = roleLogin[value - 1] || "";
    loginForm.saveLogin = false;
    saveInfo();
  },
);

watch(
  () => loginForm.saveLogin,
  () => {},
);

const { windowWidth, containerClass } = globalCommon.useResponsive();
const { isScroll, heightTop } = globalCommon.useScroll(200);

const handleLogin = async () => {
  if (!loginForm.account || !loginForm.password) {
    return;
  }

  let obj = {
    userId: loginForm.account,
    password: loginForm.password,
    loginDiv: loginForm.loginDiv,
  };
  if (obj.loginDiv === roleLogin[0] || obj.loginDiv === roleLogin[1]) {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(`${END_POINTS_LOGIN}`, {
      method: "POST",
      body: obj,
    });
    if (statusFetch.value === SUCCSESS_STATUS) {
      setUserInfo(dataFetch.value);

      if (loginForm.saveLogin) {
        let objSave = {
          mode: loginForm.mode,
          account: loginForm.account,
          loginDiv: loginForm.loginDiv,
          saveLogin: loginForm.saveLogin,
        };
        setLocalStorage(`${idInfo.value}_${roleLogin[loginForm.mode - 1]}`, objSave);
      }
      if (info && !loginForm.saveLogin) {
        localStorage.removeItem(`${idInfo.value}_${roleLogin[loginForm.mode - 1]}`);
      }

      if (dataFetch.value.data?.publicInfo === "N" && dataFetch.value.data?.userDiv === "STD") {
        return consentModal.value?.openModal();
      }
      await router.push({ name: SCREEN.home.name });
    }
  } else {
    const { dataFetch, statusFetch, error, isLoading, fetchData } = await useFetchData<PostType[]>(`${END_POINTS_LOGIN_ADMIN}`, {
      method: "POST",
      body: obj,
    });
    if (statusFetch.value === SUCCSESS_STATUS) {
      window.location.href = `${import.meta.env.VITE_DOMAIN_SITE_ADMIN}?token=${dataFetch.value.token}&role=${dataFetch.value.div}`;

      if (loginForm.saveLogin) {
        let objSave = {
          mode: loginForm.mode,
          account: loginForm.account,
          loginDiv: loginForm.loginDiv,
          saveLogin: loginForm.saveLogin,
        };
        setLocalStorage(`${idInfo.value}_${roleLogin[loginForm.mode - 1]}`, objSave);
      }
      if (info && !loginForm.saveLogin) {
        localStorage.removeItem(`${idInfo.value}_${roleLogin[loginForm.mode - 1]}`);
      }
    }
  }
};

const saveInfo = () => {
  info.value = JSON.parse(getLocalStorage(`${idInfo.value}_${roleLogin[loginForm.mode - 1]}` || `${idInfo.value}_${roleLogin[0]}`));

  if (info.value) {
    loginForm.account = info.value.account;
    loginForm.loginDiv = info.value.loginDiv;
    loginForm.saveLogin = info.value.saveLogin;
  }
};

const redirectPolycy = () => {
  router.push(SCREEN.policy.path);
};

const closeModal = () => {
};

const handleConfirm = async () => {
  await router.push({ name: SCREEN.home.name });
};

const handleLoginSSO = async () => {
  console.log('Login with SSO')
}
</script>

<style scoped lang="scss">
.page_container.login_page {
  padding-bottom: 8rem;
  background: white;

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
      &__des{
        font-size: 1.6rem;
        font-weight: 500;
        line-height: 2rem;
        text-align: center;
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
          &:first-child{
            margin-top: 20px;
          }
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

.item_card {
  cursor: pointer;
}
.item_title {
  cursor: auto !important;
}
:deep(.custom-input){
  background: rgba(248, 248, 248, 1);
  border: 1px solid rgba(248, 248, 248, 1);
}
</style>
