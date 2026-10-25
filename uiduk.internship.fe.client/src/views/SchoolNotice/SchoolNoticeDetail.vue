<template>
  <div class="sn-container">
    <div class="sn-detail">
      <div class="sn-detail__head">
        <div class="sn-detail__head--title">
          <span class="notice">{{ t("schoolNotice.listScreen.category") }}</span>
          <span class="text">{{ noticeDetail?.title }}</span>
        </div>
        <div class="sn-detail__head--datetime">{{ noticeDetail?.regDate }}</div>
      </div>
      <div class="sn-detail__body">
        <div class="content">
          <div class="quill_editor_custom" v-html="noticeDetail?.content"></div>
        </div>
        <div class="files">
          <div class="item">
            <InputFileBase
              :mode="`detail`"
              :id="'noticeFile'"
              :type="'all'"
              :show-mess="false"
              :orgName="'NOTICE'"
              v-if="id"
              :referKey="id"             
              ref="noticeFileUpload"            
              :category="'all'"
              :sectionName="'NOTICE'"           
            />
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="btn-action">
    <button class="button btn_round btn_md btn_white" @click="back">목록</button>
  </div>
</template>


<script lang="ts" setup>
import { FORMAT_YYY_MM_DD_HH_MM_SS } from "../../constants/screen.const";
import { onBeforeMount } from "vue";
import { ref } from "vue";
import { schoolNoticeStore } from "@/stores/schoolNotice";
import { SchoolNoticeDetailResDTO } from "@/stores/schoolNotice/schoolNotice.type";
import { SCREEN } from "@/router/screen";
import { useRouter } from "vue-router";
import { format } from "date-fns";
import { useI18n } from "vue-i18n";


const noticeDetail = ref<SchoolNoticeDetailResDTO>();
const schoolStore = schoolNoticeStore();
const { t } = useI18n();
const router = useRouter();
const id = window.history.state.id;

const getSchoolNoticeDetail = async (id: string) => {
  await schoolStore.fetchNoticeDetailNoLogin({
    method: "POST",
    body: id,
    headers: {
      "Content-Type": "text/plain",
    },
  });

  if(schoolStore.status === 200){
    noticeDetail.value = schoolStore.noticeDetailNoLogin;
  }
  noticeDetail.value.regDate = format(new Date(noticeDetail.value.regDate), FORMAT_YYY_MM_DD_HH_MM_SS);

};

const back = () => {
  const mode = "";
  router.push({
    name: SCREEN.schoolNoticeCRUD.name,
    params: { path: mode },
  });
};

onBeforeMount(() => {
  const id = window.history.state.id;
  if (id) {
    getSchoolNoticeDetail(id);
  } else {
    back();
  }
});
</script>

<style lang="scss" scoped>
.sn-detail {
  &__head {
    border-bottom: 1px solid var(--gray--e4);
    padding: 40px 16px;
    @media screen and (max-width: 768px) {
      padding: 0px 8px 28px;
    }
    &--title {
      display: flex;
      gap: 10px;
      align-items: center;
      margin-bottom: 16px;
      @media screen and (max-width: 768px) {
        flex-direction: column;
        align-items: flex-start;
        margin-bottom: 12px;
      }
      .notice {
        width: fit-content;
        font-size: 12px;
        padding: 5px 12px;
        font-weight: 500;
        color: var(--blue--009);
        border: 1px solid var(--blue--009);
        border-radius: 20px;
      }
      .text {
        font-size: 24px;
        font-weight: 500;
        @media screen and (max-width: 768px) {
          font-size: 20px;
        }
      }
    }
    &--datetime {
      font-size: 14px;
      font-weight: 400;
      color: var(--gray--71);
    }
  }
  &__body {
    padding: 40px 16px;
    @media screen and (max-width: 768px) {
      padding: 28px 8px 0;
    }
    .content {
      padding: 0;
      img {
        width: 100%;
        object-fit: cover;
        margin-bottom: 16px;
      }
    }
    .files {
      margin-top: 40px;
      @media screen and (max-width: 768px) {
        margin-top: 28px;
      }
      .item {
        display: flex;
        align-items: center;
        background: var(--gray--f8);
        padding: 14px 16px;
        border-radius: 5px;
        gap: 8px;
        .name {
          color: var(--gray--71);
          font-weight: 400;
        }
      }
    }
  }
}
.btn-action {
  display: flex;
  gap: 10px;
  justify-content: center;
  margin-top: 40px;
  @media screen and (max-width: 768px) {
    margin-top: 36px;
  }
  button {
    width: 78px;
    border: 1px solid var(--gray--c6);
    color: var(--gray--2d);
    @media screen and (max-width: 768px) {
      width: fit-content;
    }
  }
}
</style>
