<template>
  <!-- Banner -->
  <BannerComponent img-url="/images/img_banner.webp" />
  <!-- /Banner -->
  <!-- Training List -->
  <PageContainer class="outline-job">
    <div class="title">{{ t("student.main.title") }}</div>
    <div class="menutab">
      <div class="menutab__title" :class="{ active: activeTab === 1 }" @click="handelChangeTab(1)">{{ t("student.main.tab1") }}</div>
      <div class="menutab__title" :class="{ active: activeTab === 2 }" @click="handelChangeTab(2)">{{ t("student.main.tab2") }}</div>
    </div>
    <div class="search">
      <div class="search__total">
        <strong>{{ activeTab === 1 ? totalRow1 : totalRow2 }}</strong>
        <span>{{ t("student.main.record") }}</span>
      </div>
      <div class="search__form">
        <div class="search__form__select">
          <div class="select-input">
            <SelectBoxBase
              v-model="selectedYear"
              name="selectboxYear"
              :data="activeTab === 1 ? availableYears : availableYearsTab2"
              @update:model-value="handleSearch"
            />
          </div>
          <div class="select-input">
            <SelectBoxBase
              name="selectboxStatus"
              v-model="receptionStatus"
              :data="activeTab === 1 ? receptionStatusOptions : listInternStatus"
              @update:model-value="handleSearch"
            />
          </div>
        </div>
        <InputBase
          class="btn_search btn-search-input"
          v-model="searchInternshipName"
          :placeholder="t('student.main.inputBasePlaceHolder')"
          :is-search="true"
          @search="handleSearch"
        />
      </div>
    </div>
  </PageContainer>
  <PageContainer class="page_swiper outline-job">
    <div class="list">
      <SwiperComponentV4 v-if="activeTab === 1" :data="rowData"></SwiperComponentV4>
      <SwiperComponentV5 v-if="activeTab === 2" :data="rowData1" @changeData="changeData">
        <template v-slot:footer="{ item }">
          <div class="option item_card">
            <div
              class="item"
              :class="{
                disabled:
                  !item.corporateSignYn ||
                  item.corporateSignYn !== 'Y' ||
                  item.approveStatusCd !== APPROVE_STATUS_CD_APPROVED ||
                  item.internshipStatusCd === INTERNSHIP_STATUS_RESERVED,
              }"
              :aria-disabled="
                !item.corporateSignYn ||
                item.corporateSignYn !== 'Y' ||
                item.approveStatusCd !== APPROVE_STATUS_CD_APPROVED ||
                item.internshipStatusCd === INTERNSHIP_STATUS_RESERVED
              "
              @click="handleClickBtnSign(item)"
            >
              <IconClean />
              <div class="text">{{ t("student.main.buttonSign") }}</div>
            </div>
            <div
              class="item"
              :class="{
                disabled:
                  isDisabledAfterEndDate(item.internshipStr, item.internshipEnd) ||
                  item.approveStatusCd !== APPROVE_STATUS_CD_APPROVED ||
                  item.internshipStatusCd === INTERNSHIP_STATUS_RESERVED,
              }"
              :aria-disabled="
                isDisabledAfterEndDate(item.internshipStr, item.internshipEnd) ||
                item.approveStatusCd !== APPROVE_STATUS_CD_APPROVED ||
                item.internshipStatusCd === INTERNSHIP_STATUS_RESERVED
              "
              @click="openStudentWeeklyReport(item.internPlanSeq)"
            >
              <IconBook />
              <div class="text">{{ t("student.main.buttonWeeklyReport") }}</div>
            </div>
            <div
              class="item"
              :class="{
                disabled:
                  isDisabledAfterEndDate(item.internshipStr, item.internshipEnd) ||
                  item.approveStatusCd !== APPROVE_STATUS_CD_APPROVED ||
                  item.internshipStatusCd === INTERNSHIP_STATUS_RESERVED,
              }"
              :aria-disabled="
                isDisabledAfterEndDate(item.internshipStr, item.internshipEnd) ||
                item.approveStatusCd !== APPROVE_STATUS_CD_APPROVED ||
                item.internshipStatusCd === INTERNSHIP_STATUS_RESERVED
              "
              @click="openSummaryReport(item)"
            >
              <IconReport />
              <div class="text">{{ t("student.main.buttonEvaluationReport") }}</div>
            </div>
            <div
              class="item"
              :class="{
                disabled: isDisabled(item.internshipEnd, item.approveStatusCd) || item.internshipStatusCd === INTERNSHIP_STATUS_RESERVED,
              }"
              :aria-disabled="isDisabled(item.internshipEnd, item.approveStatusCd) || item.internshipStatusCd === INTERNSHIP_STATUS_RESERVED"
              @click="openSurveyModal(item.internPlanSeq)"
            >
              <IconSurvey />
              <div class="text">{{ t("student.main.buttonSurvey") }}</div>
            </div>
            <div
              class="item"
              :class="{ disabled: item.internshipStatusCd !== INTERNSHIP_STATUS_APPROVED || item.approveStatusCd !== APPROVE_STATUS_CD_APPROVED }"
              :aria-disabled="item.internshipStatusCd !== INTERNSHIP_STATUS_APPROVED || item.approveStatusCd !== APPROVE_STATUS_CD_APPROVED"
              @click="openCertificateTrainingModal(item.internPlanSeq)"
            >
              <IconCertificate />
              <div class="text">{{ t("student.main.buttonCertification") }}</div>
            </div>
          </div>
        </template>
      </SwiperComponentV5>
    </div>
  </PageContainer>
  <!-- /Training List -->

  <!-- MenuTab -->
  <PageContainer :class="`outline-menutab`">
    <MenuTab
      @handelRedirectToDetail="handelRedirectToDetail"
      @handleActiveTab="handleActiveTab"
      @handelSeeMore="handleSeeMore"
      :tabs="[t('student.main.noticeTab'), t('student.main.docRoomTab'), t('student.main.qnaTab')]"
      :items="[notices, docRooms, qnas]"
    ></MenuTab>
  </PageContainer>

  <!-- Menutab -->
  <SummaryReportStudent ref="openModalSummaryReport" />
  <SignedAgreement ref="openModalSemesterAgreement" />
  <!--  Modal -->

  <InternSurveyModal ref="surveyModal" />
  <ParticipationCertificateModal ref="certificateTrainingModal" />
  <StudentWeeklyReport ref="studentWeeklyReport" />
</template>
<script setup lang="ts">
import InputBase from "@/components/common/input/InputBase.vue";
import mockDataItemCard from "@/data/mockItemCard.json";
import "swiper/swiper-bundle.css";
import { getUserInfo } from "@/utils/storage";
import { onMounted, ref } from "vue";
import SignedAgreement from "../SignedAgreement/SignedAgreement.vue";
import ParticipationCertificateModal from "@/views/ParticipationCertificate/ParticipationCertificateModal.vue";
import InternSurveyModal from "./modal/InternSurveyModal.vue";
import { SCREEN } from "@/router/screen";
import { computed } from "vue";
import { internshipPlanRegistrationStore } from "@/stores/internshipPlanRegistration";
import { internStudentStore } from "@/stores/internStudent";
import { dashboardNoticeStore } from "@/stores/dashboard/notice";
import { dashboardDocRoomStore } from "@/stores/dashboard/docRoom";
import { dashboardQnaStore } from "@/stores/dashboard/qna";
import { InternshipRegReqDTO } from "@/stores/internshipPlanRegistration/internshipPlan.type";
import SwiperComponentV4 from "@/components/common/SwiperComponentV4.vue";
import SwiperComponentV5 from "@/components/common/SwiperComponentV5.vue";
import { codeMngStore } from "@/stores/common/codeMng";
import {
  DEFAULT_CD_ID,
  DEFAULT_CD_NM,
  INTERN_PLAN_START_YEAR,
  MODE_DETAIL,
  APPROVE_STATUS_CD_APPROVED,
  INTERNSHIP_STATUS_APPROVED,
  INTERNSHIP_STATUS_RESERVED,
  INTERN_PLAN_STATUS_WAIT,
  INTERN_PLAN_STATUS_ACCEPT,
  INTERN_PLAN_STATUS_COMPLETE,
} from "@/constants/common.const";
import { InternshipStudentReqDTO } from "@/stores/internStudent/internStudent.type";
import { useI18n } from "vue-i18n";
import { CodeMngModel } from "@/stores/common/codeMng/codeMng.type";
import { useRouter } from "vue-router";
import StudentWeeklyReport from "./StudentWeeklyReport.vue";
import SummaryReportStudent from "../SummaryReportStudent/SummaryReportStudent.vue";

const router = useRouter();
const { t } = useI18n();
const itemCards = ref(mockDataItemCard);
const userInfo = ref(getUserInfo());
const availableYears = ref([]);
const currentYear = new Date().getFullYear().toString();
const internStore = internshipPlanRegistrationStore();
const dashboardStore = dashboardNoticeStore();
const dashboardDocStore = dashboardDocRoomStore();
const dashboardQNAStore = dashboardQnaStore();
const internStdStore = internStudentStore();
const searchParams = ref<InternshipRegReqDTO>({
  selectedYear: currentYear,
  receptionStatus: DEFAULT_CD_ID,
  internshipName: "",
});

const internStdSearchParams = ref<InternshipStudentReqDTO>({
  selectedYear: "",
  internshipStatus: "",
  internshipName: "",
});

const rowData = ref<any[]>([]);
const totalRow1 = ref<number>(0);
const totalRow2 = ref<number>(0);
const rowData1 = ref<any[]>([]);
const codeMngStr = codeMngStore();
const listInternStatus = ref([]);
const internshipStudentYear = ref([]);
const availableYearsTab2 = ref([]);
const dashboardNoticeList = ref([]);
const dashboardDocRoomList = ref([]);
const dashboardQNAList = ref([]);
const openModalSummaryReport = ref(null);

const chunkedCards = computed(() => {
  const chunkSize = 6;
  const result = [];
  for (let i = 0; i < itemCards.value.length; i += chunkSize) {
    result.push(itemCards.value.slice(i, i + chunkSize));
  }
  return result;
});

const activeTab = ref(1);
const openModalSemesterAgreement = ref(null);
const activeTab1 = ref(0);
const handleClickBtnSign = (item) => {
  openModalSemesterAgreement.value?.openModal(item.internStdSeq);
};
const openSummaryReport = (item) => {
  openModalSummaryReport.value?.openModal(item.internPlanSeq);
};

const receptionStatusOptions = ref([
  { cdId: DEFAULT_CD_ID, cdNm: DEFAULT_CD_NM, upCdId: "1" },
  { cdId: INTERN_PLAN_STATUS_WAIT, cdNm: t("student.main.statusWaitNm"), upCdId: "1" },
  { cdId: INTERN_PLAN_STATUS_ACCEPT, cdNm: t("student.main.statusAcceptNm"), upCdId: "1" },
  { cdId: INTERN_PLAN_STATUS_COMPLETE, cdNm: t("student.main.statusCompleteNm"), upCdId: "1" },
]);

const notices = ref([]);
const docRooms = ref([]);
const qnas = ref([]);

const getInternPlanRegList = async () => {
  await internStore.fetchInternshipPlanRegistrationList({
    method: "POST",
    body: searchParams.value,
  });
  rowData.value = internStore.listInternshipPlanRegistration;
  totalRow1.value = internStore.totalItems;
};

const getInternStudentList = async () => {
  await internStdStore.fetchInternStudentList({
    method: "POST",
    body: internStdSearchParams.value,
  });
  rowData1.value = internStdStore.internStudentList;
  totalRow2.value = internStdStore.totalItems;
};

const getListYear = async () => {
  const strYear = INTERN_PLAN_START_YEAR;
  const currentYear = Number(new Date().getFullYear());
  let arrYear = [];
  for (let i = currentYear + 1; i >= strYear; i--) {
    const year = {} as CodeMngModel;
    year.cdId = i;
    year.cdNm = i;
    arrYear.push(year);
  }

  availableYears.value = [{ cdId: "", cdNm: "년도 전체", upCdId: "1" }, ...arrYear];
};

const getInternshipStudentYear = async () => {
  const stdId = JSON.parse(userInfo.value)?.data?.userId;
  await internStdStore.fetchInternshipStudentYear({
    method: "POST",
    body: stdId,
  });
  internshipStudentYear.value = internStdStore.listInternshipYear;
  availableYearsTab2.value = [
    { cdId: "", cdNm: "년도 전체", upCdId: "1" },
    ...internshipStudentYear.value.map((year) => ({
      cdId: year,
      cdNm: year,
      upCdId: "1",
    })),
  ];
  internStdSearchParams.value.selectedYear = "";
};

const getListInternStatus = async () => {
  await codeMngStr.getCodeInfo({
    method: "POST",
    body: { upCdIdList: ["STD_I01_P00"] },
  });

  listInternStatus.value = [
    { cdId: "", cdNm: "이수여부 전체" },
    ...codeMngStr.codeInfo.data.slice(1).map((item: { cdId: string; cdNm: string; upCdId: string }) => ({
      cdId: item.cdId,
      cdNm: item.cdNm,
      upCdId: item.upCdId,
    })),
  ];
};

const handelChangeTab = (tab) => {
  activeTab.value = tab;
  if (tab === 1) {
    getInternPlanRegList();
  }else if (tab === 2) {
    getInternStudentList();
  }
};

const getDashboardNotice = async () => {
  await dashboardStore.fetchDashboardNotice({ method: "POST" });
  dashboardNoticeList.value = dashboardStore.listDashboardNotice;
  const { notices: transformedNotices } = transformTabData();
  notices.value = transformedNotices;
};

const getDashboardDocRoom = async () => {
  await dashboardDocStore.fetchDashboardDocRoom({ method: "POST" });
  dashboardDocRoomList.value = dashboardDocStore.listDashboardDocRoom;
  const { docRooms: transformedDocRooms } = transformTabData();
  docRooms.value = transformedDocRooms;
};

const getDashboardQNA = async () => {
  await dashboardQNAStore.fetchDashboardQNA({ method: "POST" });
  dashboardQNAList.value = dashboardQNAStore.listDashboardQNA;
  const { qnas: transformedQnas } = transformTabData();
  qnas.value = transformedQnas;
};

const transformTabData = () => {
  const listNotice = dashboardNoticeList.value || [];
  const listQNA = dashboardQNAList.value || [];
  const listdocRoom = dashboardDocRoomList.value || [];

  //Mapping notice data
  const notices = listNotice.map((notice) => {
    const regDate = new Date(notice.regDate);
    return {
      id: notice.noticeSeq,
      day: regDate.getDate().toString(),
      date: `${regDate.getFullYear()}.${regDate.getMonth() + 1}`,
      title: notice.title,
    };
  });

  //Mapping docRoom data
  const docRooms = listdocRoom.map((docRoom) => {
    const regDate = new Date(docRoom.regDate);
    return {
      id: docRoom.docSeq,
      day: regDate.getDate().toString(),
      date: `${regDate.getFullYear()}.${regDate.getMonth() + 1}`,
      title: docRoom.title,
    };
  });

  //Mapping qna data
  const qnas = listQNA.map((qna) => {
    const regDate = new Date(qna.regDate);
    return {
      id: qna.qnaSeq,
      day: regDate.getDate().toString(),
      date: `${regDate.getFullYear()}.${regDate.getMonth() + 1}`,
      title: qna.title,
    };
  });

  return {
    notices,
    qnas,
    docRooms,
  };
};

const handleActiveTab = (tabIndex1) => {
  activeTab1.value = tabIndex1;
};

const handelRedirectToDetail = (id) => {
  const mode = MODE_DETAIL;
  switch (activeTab1.value) {
    case 1:
      router.push({
        name: SCREEN.schoolNoticeCRUD.name,
        params: { path: mode },
        state: { id },
      });
      break;
    case 2:
      router.push({
        name: SCREEN.docRoomCRUD.name,
        params: { path: mode },
        state: { id },
      });
      break;
    case 3:
      router.push({
        name: SCREEN.qaCRUD.name,
        params: { path: mode },
        state: { id },
      });
  }
};

const handleSeeMore = () => {
  switch (activeTab1.value) {
    case 1:
      router.push({
        name: SCREEN.schoolNoticeCRUD.name,
      });
      break;
    case 2:
      router.push({
        name: SCREEN.docRoomCRUD.name,
      });
      break;
    case 3:
      router.push({
        name: SCREEN.qaCRUD.name,
      });
      break;
    default:
      break;
  }
};

const listOption = ref([
  {
    icon: "",
  },
]);

onMounted(() => {
  getListYear();
  getInternPlanRegList();
  getInternStudentList();
  getListInternStatus();
  getInternshipStudentYear();
  getDashboardNotice();
  getDashboardDocRoom();
  getDashboardQNA();
  activeTab1.value = 1;
});

const changeData = () => {
  getInternStudentList();
};

const selectedYear = computed({
  get() {
    return activeTab.value === 1 ? searchParams.value.selectedYear : internStdSearchParams.value.selectedYear;
  },
  set(value) {
    if (activeTab.value === 1) {
      searchParams.value.selectedYear = value;
    } else {
      internStdSearchParams.value.selectedYear = value;
    }
  },
});

const receptionStatus = computed({
  get() {
    return activeTab.value === 1 ? searchParams.value.receptionStatus : internStdSearchParams.value.internshipStatus;
  },
  set(value) {
    if (activeTab.value === 1) {
      searchParams.value.receptionStatus = value;
    } else {
      internStdSearchParams.value.internshipStatus = value;
    }
  },
});

const searchInternshipName = computed({
  get: () => {
    return activeTab.value === 1 ? searchParams.value.internshipName : internStdSearchParams.value.internshipName;
  },
  set: (value) => {
    if (activeTab.value === 1) {
      searchParams.value.internshipName = value;
    } else {
      internStdSearchParams.value.internshipName = value;
    }
  },
});

const handleSearch = () => {
  if (activeTab.value === 1) {
    getInternPlanRegList();
  } else if (activeTab.value === 2) {
    getInternStudentList();
  }
};

const certificateTrainingModal = ref(null);
const openCertificateTrainingModal = (internPlanSeq: string) => {
  certificateTrainingModal.value?.openModal(internPlanSeq);
};
const surveyModal = ref(null);
const openSurveyModal = (internPlanSeq: string) => {
  surveyModal.value?.openModal(internPlanSeq);
};
const studentWeeklyReport = ref(null);
const openStudentWeeklyReport = (internPlanSeq: string) => {
  studentWeeklyReport.value?.openModal(internPlanSeq);
};
const isDisabledAfterEndDate = (internshipStr, internshipEnd) => {
  if (!internshipEnd || !internshipStr) return true;
  const currentDate = new Date();
  const startDate = new Date(internshipStr);
  const endDatePlus14Days = new Date(new Date(internshipEnd).setDate(new Date(internshipEnd).getDate() + 14));
  return currentDate < startDate || currentDate > endDatePlus14Days;
};

const isDisabled = (internshipEnd, approveStatusCd) => {
  if (!internshipEnd || approveStatusCd !== APPROVE_STATUS_CD_APPROVED) return true;

  const currentDate = new Date();
  const endDatePlus14Days = new Date(new Date(internshipEnd).setDate(new Date(internshipEnd).getDate() + 14));
  return currentDate > endDatePlus14Days || currentDate < new Date(internshipEnd);
};
</script>

<style lang="scss" scoped>
.outline-menutab {
  min-height: 500px;
  background: var(--white-color);
  padding: 100px 2rem;
}
.outline-job {
  .title {
    font-size: 40px;
    font-weight: 500;
    letter-spacing: -2%;
    color: var(--gray--1d);
    margin-bottom: 24px;
    @media screen and (max-width: 1024px) {
      font-size: 26px;
    }
  }
  .menutab {
    display: flex;
    margin-bottom: 28px;
    border-bottom: 1px solid var(--gray--e4);
    .active {
      color: var(--blue--009);
      border-bottom: 4px solid var(--blue--009);
    }
    &__title {
      cursor: pointer;
      font-size: 16px;
      font-weight: 500;
      padding: 12px 24px;
      color: var(--gray--71);
      text-align: center;
      @media screen and (max-width: 1024px) {
        width: 50%;
      }
      &:hover {
        color: var(--blue--009);
      }
    }
  }
  .search {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 24px;
    @media screen and (max-width: 1024px) {
      flex-direction: column-reverse;
      align-items: flex-start;
    }

    &__total {
      @media screen and (max-width: 1024px) {
        margin-top: 24px;
      }
      strong,
      span {
        font-size: 14px;
        font-weight: 400;
      }
      strong {
        color: var(--blue--009);
        margin-right: 5px;
      }
      span {
        color: var(--gray-55);
      }
    }
    &__form {
      display: flex;
      gap: 10px;
      @media screen and (max-width: 1024px) {
        flex-direction: column;
        width: 100%;
      }
      &__select {
        display: flex;
        gap: 10px;
        .select-input {
          width: 220px;
          background-color: var(--white-color);
          @media screen and (max-width: 1024px) {
            width: 50%;
          }
        }
      }
      .btn-search-input {
        width: 320px;
        background-color: var(--white-color);
        @media screen and (max-width: 1024px) {
          width: 100%;
        }
      }
    }
  }
  .list {
    .option {
      display: flex;
      flex-wrap: wrap;
      gap: 5px;
    }

    .item {
      display: flex;
      align-items: center;
      flex-direction: column;
      gap: 5px;
      flex: 1 1 calc(20% - 5px);
      .text {
        font-weight: 500;
        font-size: 14px;
        color: var(--gray--71);
        padding: 0;
      }
    }

    @media screen and (max-width: 1024px) {
      .item {
        flex: 1 1 calc(50% - 5px);
        gap: 10px;
        flex-direction: row;
      }
    }
  }
}
.item.disabled {
  pointer-events: none;
  opacity: 0.5;
  cursor: not-allowed;
}
.item_card {
  cursor: pointer;
}
</style>
