<template>
  <div class="sn-container">
    <div class="sn-detail">
      <div class="sn-detail__head">
        <div class="sn-detail__head--title">
          <span class="notice">{{ t('docRoom.category') }}</span>
          <span class="text">{{ docRoomDetail?.title }}</span>
        </div>
        <div class="sn-detail__head--datetime">{{ docRoomDetail?.regDate }}</div>
      </div>
      <div class="sn-detail__body">
        <div class="content">
          <div class="quill_editor_custom" v-html="docRoomDetail?.content"></div>
        </div>
        <div class="files">
          <div class="item">
            <InputFileBase
              :mode="`detail`"
              :id="'docRoomFile'"
              :type="'all'"
              :show-mess="false"
              :orgName="'DOCROOM'"
              v-if="id"
              :referKey="id"             
              ref="docRoomFileUpload"            
              :category="'all'"
              :sectionName="'DOCROOM'"           
            />
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="btn-action">
    <button class="button btn_round btn_md btn_white" @click="back">{{ t('common.list') }}</button>
  </div>
</template>
<script lang="ts" setup>
import { FORMAT_YYY_MM_DD_HH_MM_SS } from "../../constants/screen.const";
import { onBeforeMount } from "vue";
import { ref } from "vue";
import { SCREEN } from "@/router/screen";
import { useRouter } from "vue-router";
import { format } from "date-fns";
import { docRoomStore } from "@/stores/docRoom";
import { DocRoomDetailResDTO } from "@/stores/docRoom/docRoom.type";
import InputFileBase from "@/components/common/input/InputFileBase.vue";
import { useI18n } from "vue-i18n";

const docRoomDetail = ref<DocRoomDetailResDTO>();
const router = useRouter();
const docStore = docRoomStore();
const id = window.history.state.id;
const { t } = useI18n();

const unescapeHtml = (html) => {
  const txt = document.createElement("textarea");
  txt.innerHTML = html;
  return txt.value;
};

const getDocRoomDetail = async (id: string) => {
  await docStore.fetchDocRoomDetail({
    method: "POST",
    body: id,
    headers: {
      "Content-Type": "text/plain",
    },
  });

  docRoomDetail.value = docStore.docRoomDetail;

  docRoomDetail.value.regDate = format(new Date(docRoomDetail.value.regDate), FORMAT_YYY_MM_DD_HH_MM_SS);
  docRoomDetail.value.content = unescapeHtml(docRoomDetail.value.content);

};

const back = () => {
  const mode = "";
  router.push({
    name: SCREEN.docRoomCRUD.name,
    params: { path: mode },
  });
};

onBeforeMount(() => {
  const id = window.history.state.id;
  if (id) {
    getDocRoomDetail(id);
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
