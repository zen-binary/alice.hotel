<template>
  <div class="sn-container">
    <div class="list-qa">
      <div class="list-qa__form">
        <div class="total">
          <span>{{ totalRow }} 개의 자료</span>
        </div>
        <div class="search">
          <InputBase
            v-model="searchParams.title"
            class="search--input btn_search"
            :placeholder="t('docRoom.listScreen.inputBasePlaceHolder')"
            :is-search="true"
            @search="resetSearch"
          ></InputBase>
        </div>
      </div>
      <div class="list-qa__table">
        <div class="head">
          <div class="item">{{ t("docRoom.listScreen.header1") }}</div>
          <div class="item">{{ t("docRoom.listScreen.header2") }}</div>
          <div class="item">{{ t("docRoom.listScreen.header3") }}</div>
          <div class="item">{{ t("docRoom.listScreen.header4") }}</div>
        </div>
        <div class="content-block" v-if="rowData.length > 0" v-for="(item, index) in rowData" :key="index">
          <div class="item no-column">
            <div v-if="item.topFixYn === 'Y'" class="notice-icon">{{ t("docRoom.category") }}</div>
            <span v-else>{{ item.rowNumber }}</span>
          </div>
          <div class="item">
            <div class="title" @click="goToDetail(item.docSeq)" style="cursor: pointer">
              <span class="line-clamp-two" :title="item.title">{{ item.title }}</span>
              <div class="status" v-if="isNew(item.regDate)">
                <IconNew />
              </div>
            </div>
            <div class="date-mobile">{{ item.regDate }}</div>
          </div>
          <div class="item date">{{ item.regDate }}</div>
          <div class="item action">
            <!-- <IconDowload @click="dowloadMultiple(item.docSeq)"></IconDowload> -->
            <IconDowload @click="submit(item.docSeq)"></IconDowload>
          </div>
        </div>
        <div class="content-block" v-else>
          <div class="item">
            <div class="title" style="justify-content: space-around">{{ t("noDataFoundMessage") }}</div>
          </div>
        </div>
      </div>
      <div class="list-qa__pagination" v-if="rowData.length > 0">
        <!-- <Pagination v-model="paginationValue" :total-record="totalRow" /> -->
        <Pagination v-model="paginationValue" :total-record="totalRow" />
      </div>
    </div>
    <TModalConfirm :is-open="modalOpenConfirm" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
      <!-- Modal content goes here -->
      <template #title>
        <strong>개인정보 수집 및 이용에 대한 동의</strong>
      </template>
      <template #default>
        <div class="box_section">
          <p>{{ t("common.message.confirmDownloadFile") }}</p>
        </div>
      </template>
      <template #footer>
        <!-- Footer content goes here -->
        <button type="button" class="button btn_md btn_primary" @click="dowloadMultiple">{{ t("common.confirm") }}</button>
        <button type="button" class="button btn_md btn_white popup_close" @click="cancelAction1">{{ t("common.cancel") }}</button>
      </template>
    </TModalConfirm>
  </div>
</template>
<script lang="ts" setup>
import { useI18n } from "vue-i18n";
import { ref, onMounted, watch } from "vue";
import { SCREEN } from "@/router/screen";
import { MODE_DETAIL } from "@/constants/common.const";
import { useRouter } from "vue-router";
import Pagination from "@/components/common/PaginationComponent.vue";
import { format } from "date-fns";
import { FORMAT_YYY_MM_DD } from "../../constants/screen.const";
import { DocRoomReqDTO, DocRoomResDTO } from "@/stores/docRoom/docRoom.type";
import { docRoomStore } from "@/stores/docRoom";
import { fileMngStore } from "@/stores/common/fileMng/index";

const { t } = useI18n();
const totalRow = ref<number>(0);
const rowData = ref<DocRoomResDTO[]>([]);
const currentPage = ref(1);
const docStore = docRoomStore();
const router = useRouter();
const paginationValue = ref<number>(1);
const modalOpenConfirm = ref(false);
const modalId = ref("modalId");
const modalOpen = ref(false);
const docSeq = ref("");
const searchParams = ref<DocRoomReqDTO>({
  title: "",
  page: 1,
  size: 10,
  sort: "",
});
const fileStore = fileMngStore();

const getDocRoomList = async () => {
  await docStore.fetchDocRoomList({
    method: "POST",
    body: searchParams.value,
  });
  rowData.value = docStore.listDocRoom;
  totalRow.value = docStore.totalItems;
  rowData.value.forEach((item: any) => {
    item.regDate = format(new Date(item.regDate), FORMAT_YYY_MM_DD);
  });
};

const goToDetail = (id: string) => {
  const mode = MODE_DETAIL;
  sessionStorage.setItem("docId", id);
  router.push({
    name: SCREEN.docRoomCRUD.name,
    params: { path: mode },
    state: { id },
  });
};

const resetSearch = () => {
  searchParams.value.page = 1;
  searchParams.value.size = 10;
  searchParams.value.sort = "";
  paginationValue.value = 1;
  getDocRoomList();
};

const dowloadMultiple = async () => {
  let params = {} as any;
  params.lstId = [docSeq.value];
  params.sectionName = ["DOCROOM"];
  await fileStore.dowLoadMultipleFile(params);
  closeModal();
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

const submit = (id) => {
  docSeq.value = id;
  modalOpenConfirm.value = true;
};

const cancelAction1 = () => {
  modalOpen.value = false;
  modalOpenConfirm.value = false;
};

const closeModal = () => {
  // modalOpen.value = false;
  modalOpenConfirm.value = false;
  // modalOpenAlert.value = false;
};

watch(
  () => paginationValue.value,
  (newVal) => {
    searchParams.value.page = newVal;
    getDocRoomList(newVal);
  },
);

onMounted(() => {
  getDocRoomList(currentPage.value);
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

    .total,
    .search {
      display: flex;
      align-items: center;
      gap: 10px;

      @media screen and (max-width: 768px) {
        width: 100%;
      }
    }

    .total {
      font-size: 14px;

      @media screen and (max-width: 768px) {
        text-align: left;
        margin-top: 20px;
      }

      span {
        font-weight: 400;
        color: var(--gray--55);

        &:last-child {
          font-weight: 500;
          color: var(--blue--009);
        }
      }
    }

    .search {
      height: 48px;

      @media screen and (max-width: 768px) {
        flex-direction: column;
        height: auto;
      }

      &--input,
      &--btn {
        height: 100%;
        font-size: 16px;

        @media screen and (max-width: 768px) {
          height: auto;
          font-size: 14px;
        }
      }

      &--input {
        min-width: 320px;

        @media screen and (max-width: 768px) {
          min-width: auto;
        }
      }

      &--btn {
        min-width: 107px;
        font-weight: 500;
        white-space: nowrap;

        @media screen and (max-width: 768px) {
          padding: 10px;
          min-width: auto;
        }
      }
    }
  }

  &__table {
    @media screen and (max-width: 768px) {
      border-top: 2px solid var(--gray--2d);
    }
    .head,
    .content-block {
      display: flex;
      justify-content: space-between;
      align-items: center;
      color: var(--gray--1d);
      border-bottom: 1px solid var(--gray--e4);

      .item {
        flex: 2;
        padding: 20px 0;
        text-align: center;
        box-sizing: border-box;

        @media screen and (max-width: 768px) {
          padding: 5px 0;
        }

        &:nth-child(2) {
          flex: 12;

          @media screen and (max-width: 768px) {
            width: 100%;
            text-align: left;
          }
        }
      }
    }

    .head {
      border-top: 2px solid var(--gray--2d);

      @media screen and (max-width: 768px) {
        display: none;
      }
    }

    .content-block {
      .item {
        @media screen and (max-width: 768px) {
          margin: 10px 0;
        }

        .notice-icon {
          width: fit-content;
          font-size: 12px;
          padding: 5px 10px;
          margin-bottom: 5px;
          font-weight: 500;
          color: var(--blue--009);
          border: 1px solid var(--blue--009);
          border-radius: 20px;
        }

        &:nth-child(1) {
          display: flex;
          align-items: center;
          justify-content: space-around;
          color: var(--gray--71);

          @media screen and (max-width: 768px) {
            display: none;
          }
        }

        .key,
        .date-mobile {
          display: none;

          @media screen and (max-width: 768px) {
            display: block;
            color: var(--gray--71);
            font-size: 14px;
          }
        }

        .title {
          display: flex;
          align-items: center;
          gap: 5px;

          span {
            font-weight: 500;
            font-size: 16px;
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
        .disabled {
          background: var(--gray--f0);
          color: var(--gray--71);
          border: none;
        }

        .active {
          background: rgba(0, 152, 229, 0.12);
          color: var(--blue--009);
          border: none;
        }
      }
    }

    .content-block:first-of-type {
      border-top: 2px solid var(--gray--2d);
    }
  }

  &__pagination {
    margin-top: 32px;
  }
}
</style>
