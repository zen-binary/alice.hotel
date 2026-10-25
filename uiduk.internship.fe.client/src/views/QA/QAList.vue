<template>
  <div class="qa-container">
    <div class="list-qa">
      <div class="list-qa__form">
        <div class="total">
          <span>{{ totalRow }} 개의 Q&A</span>
        </div>
        <div class="search">
          <InputBase
            v-model="searchParams.title"
            class="search--input btn_search "
            :placeholder="t('qna.listSreen.inputBasePlaceHolder')"
            :is-search="true"
            @search="resetSearch"
          ></InputBase>
          <button class="button btn_md btn_primary btn_responsive search--btn" @click="goToCreate">{{ t("qna.listSreen.buttonCreate") }}</button>
        </div>
      </div>
      <div class="list-qa__table">
        <div class="head">
          <div class="item">{{ t("qna.listSreen.header1") }}</div>
          <div class="item">{{ t("qna.listSreen.header2") }}</div>
          <div class="item">{{ t("qna.listSreen.header3") }}</div>
        </div>
        <div class="content" v-if="rowData.length > 0" v-for="(item, index) in rowData" :key="index">
          <div class="item">
            <div class="title">
              <span class="line-clamp-two item_title" :title="item.title" @click="goToDetail(item.qnaSeq)">{{ item.title }}</span>
              <div class="status" v-if="isNew(item.regDate)">
                <IconNew />
              </div>
            </div>
            <div class="date-mobile">{{ item.regDate }}</div>
          </div>
          <div class="item date">{{ item.regDate }}</div>
          <div class="item action">
            <span class="button status-qa" :class="item.answer === null ? 'status-qa-no' : 'status-qa-yes'" :disabled="item.answer === null">
              {{ item.answer === null ? t("qna.listSreen.answerStatus_NO") : t("qna.listSreen.answerStatus_YES") }}
            </span>
          </div>
        </div>
        <div class="content" v-else>
          <div class="item">
            <div class="title" style="justify-content: space-around">{{ t("noDataFoundMessage") }}</div>
          </div>
        </div>
      </div>
      <div class="list-qa__pagination" v-if="rowData.length > 0">
        <Pagination v-model="paginationValue" :total-record="totalRow" />
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { ref } from "vue";
import Pagination from "@/components/common/PaginationComponent.vue";
import { qnaStore } from "@/stores/qna";
import { QnaListResDTO, QnaReqDTO } from "@/stores/qna/qna.type";
import { format } from "date-fns";
import { MODE_DETAIL } from "@/constants/common.const";
import { SCREEN } from "@/router/screen";
import { useRouter } from "vue-router";
import { onBeforeMount, watch } from "vue";
import { FORMAT_YYY_MM_DD, MODE_CREATE } from "@/constants/screen.const";
import { useI18n } from "vue-i18n";

const { t } = useI18n();
const paginationValue = ref<number>(1);
const currentPage = ref(1);
const searchParams = ref<QnaReqDTO>({
  title: "",
  page: 1,
  size: 10,
  sort: "",
});
const rowData = ref<QnaListResDTO[]>([]);
const totalRow = ref<number>(0);
const router = useRouter();
const qaStore = qnaStore();

const getQnaList = async () => {
  await qaStore.fetchQnaList({
    method: "POST",
    body: searchParams.value,
  });
  rowData.value = qaStore.listQna;
  totalRow.value = qaStore.totalItems;
  rowData.value.forEach((item: any) => {
    item.regDate = format(new Date(item.regDate), FORMAT_YYY_MM_DD);
  });
};
watch(
  () => paginationValue.value,
  (newVal) => {
    searchParams.value.page = newVal;
    getQnaList(newVal);
  },
);

const resetSearch = () => {
  searchParams.value.page = 1;
  searchParams.value.size = 10;
  searchParams.value.sort = "";
  paginationValue.value = 1;
  getQnaList();
};

const goToDetail = (id: string) => {
  const mode = MODE_DETAIL;
  router.push({
    name: SCREEN.qaCRUD.name,
    params: { path: mode },
    state: { id },
  });
};

const goToCreate = () => {
  const mode = MODE_CREATE;
  router.push({
    name: SCREEN.qaCRUD.name,
    params: { path: mode },
  });
};

const isNew = (regDate: string) => {
  const threeDaysInMs = 3 * 24 * 60 * 60 * 1000;
  const registrationDate = new Date(regDate);
  registrationDate.setHours(0, 0, 0, 0);
  const newDate = new Date(registrationDate.getTime() + threeDaysInMs);
  const currentDate = new Date();
  currentDate.setHours(0, 0, 0, 0);
  return currentDate <= newDate;
};

onBeforeMount(() => {
  getQnaList(currentPage.value);
});
</script>
<style lang="scss" scoped>
.list-qa {
  &__form {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 24px;
    @media screen and (max-width: 768px) {
      flex-direction: column-reverse;
      margin-bottom: 20px;
    }
    .total {
      display: flex;
      align-items: center;
      gap: 5px;
      font-size: 14px;
      @media screen and (max-width: 768px) {
        width: 100%;
        text-align: left;
        margin: 20px 0 0;
      }
      span {
        &:first-child {
          font-weight: 400;
          color: var(--gray--55);
        }
        &:last-child {
          font-weight: 500;
          color: var(--blue--009);
        }
      }
    }
    .search {
      display: flex;
      gap: 10px;
      height: 48px;
      @media screen and (max-width: 768px) {
        width: 100%;
        height: fit-content;
        flex-direction: column;
      }
      &--input {
        height: 100%;
        min-width: 320px;
        @media screen and (max-width: 768px) {
          min-width: fit-content;
          height: fit-content;
          font-size: 14px;
        }
      }
      &--btn {
        white-space: nowrap;
        height: 100%;
        min-width: 107px;
        font-size: 16px;
        font-weight: 500;
        max-height: 40px;
        @media screen and (max-width: 768px) {
          min-width: fit-content;
          height: fit-content;
          padding: 10px;
        }
      }
    }
  }
  &__table {
    @media screen and (max-width: 768px) {
      border-top: 2px solid var(--gray--2d);
    }
    .head,
    .content {
      display: flex;
      justify-content: space-between;
      align-items: center;
      color: var(--gray--1d);
      padding: 0;
      border-bottom: 1px solid var(--gray--e4);

      .item {
        flex: 2;
        padding: 20px 0;
        text-align: center;
        box-sizing: border-box;

        &:first-child {
          flex: 12;
          @media screen and (max-width: 768px) {
            width: 100%;
            text-align: left;
          }
        }
        @media screen and (max-width: 768px) {
          padding: 5px 0;
        }
      }
      @media screen and (max-width: 768px) {
        flex-direction: column-reverse;
        align-items: flex-start;
      }
    }

    .head {
      border-top: 2px solid var(--gray--2d);
      border-bottom: 1px solid var(--gray--2d);
      @media screen and (max-width: 768px) {
        display: none;
      }
    }
    .content {
      @media screen and (max-width: 768px) {
        margin-top: 10px;
      }
      .item {
        .title {
          text-align: left;
          display: flex;
          align-items: center;
          gap: 5px;
          span {
            font-weight: 500;
            font-size: 16px;
          }
        }
        .date-mobile {
          display: none;
          color: var(--gray--71);

          @media screen and (max-width: 768px) {
            display: block;
            margin: 10px 0;
            font-size: 14px;
          }
        }
      }
      .date {
        color: var(--gray--71);
        @media screen and (max-width: 768px) {
          display: none;
        }
      }
      .action {
        button {
          height: 28px;
        }
        .status-qa-no {
          background-color: #f2f2f2;
          color: #666666;
          padding: 5px 10px;
          border-radius: 5px;
        }
        .status-qa-yes {
          background-color: #e5f5ff;
          color: #39a8ff;
          padding: 5px 10px;
          border: none;
          border-radius: 5px;
          display: inline-block;
          text-align: center;
        }
        .disabled {
          background: var(--gray--f0);
          border: none;
          color: var(--gray--71);
        }
        .active {
          background: var(--primary-opacirty-12, rgba(0, 152, 229, 0.12));
          border: none;
          color: var(--blue--009);
        }
      }
    }
  }
  &__pagination {
    margin-top: 32px;
  }
}
.item_title {
  cursor: pointer;
}
</style>
