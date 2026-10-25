<template>
  <TModal
    :is-open="isOpen"
    :is-show-header="true"
    :is-show-footer="false"
    :modal-id="'modalId'"
    class-size="medium-large"
    @close-modal="closeModal"
    :key="modalKey"
  >
    <!-- Modal content goes here -->
    <template #title>
      <strong>{{ t("modalAgreementCenter.titleModal") }}</strong>
    </template>
    <template #default>
      <div class="semester_agreement" id="modalAgreementCenter">
        <div class="semester_agreement__title">
          <h2>{{ dataForm.interPlanNm }}</h2>
          <div class="semester_agreement__title__right">
            <SelectBoxBase v-model="stdValue" :id="idPlan" name="selectbox" :data="listStdName" />
            <button :disabled="isDisabled || stdValue !== ''" class="button btn_white btn_lg hidden_print" @click="openModalConfirm">
              {{ t("modalAgreementCenter.btnSave") }}
            </button>
            <ButtonExportPDFV1 class="btn_lg" :id="'modalAgreementCenter'" name-export="ModalAgreementCenter" />
          </div>
        </div>
        <div class="semester_agreement__content is_scroll">
          <div class="content_header">
            <span>
              {{ t("modalAgreementCenter.titleContent1") }}
            </span>
            <div>
              <span class="text_primary text_bold">
                {{ t("modalAgreementCenter.titleContent2") }} {{ dataForm.internYear }}{{ t("modalAgreementCenter.titleContent3") }}
                {{ dataForm.internTerm }}
              </span>
              &nbsp;
              <span class="text_bold">{{ t("modalAgreementCenter.titleContent4") }}</span>
            </div>
            <p class="text_bold">{{ t("modalAgreementCenter.titleContent5") }}</p>
          </div>
          <div class="content_body">
            <ul>
              <li>
                <span class="text_primary">1.</span>
                <span class="text_primary">{{ t("modalAgreementCenter.titleContent6") }} : {{ dataForm.cormNameKr }}</span>
              </li>
              <li>
                <span>2.</span>
                <div>
                  <span>
                    {{ t("modalAgreementCenter.titleContent7") }}
                  </span>
                </div>
              </li>
              <li>
                <span class="text_primary">3.</span>
                <div>
                  <span class="text_primary">
                    {{ t("modalAgreementCenter.titleContent31") }} {{ dataForm.internshipStr }}부터 {{ dataForm.internshipEnd
                    }}{{ t("modalAgreementCenter.titleContent32") }}
                  </span>
                  &nbsp;
                  <span>{{ t("modalAgreementCenter.titleContent8") }}</span>
                </div>
              </li>
              <li>
                <span>4.</span>
                <div class="content_body__end">
                  <span>{{ t("modalAgreementCenter.titleContent9") }}</span>
                  <span class="text_primary">
                    {{ t("modalAgreementCenter.titleContent33", { time: dataForm.duration }) }} / {{ dataForm.durationHourStr }} :{{ dataForm.durationMinuteStr }} ~
                    {{ dataForm.durationHourEnd }} :{{ dataForm.durationMinuteEnd }}
                    {{ t("modalAgreementCenter.titleContent34") }}
                  </span>
                  <span class="text_primary">
                    {{ t("modalAgreementCenter.titleContent35") }}{{ dataForm.internshipWeekDay }}일 ({{ dataForm.internshipDay }})
                  </span>
                  <span>
                    {{ t("modalAgreementCenter.titleContent10") }}
                  </span>
                  <span>
                    {{ t("modalAgreementCenter.titleContent40") }}
                  </span>
                </div>
              </li>
              <li>
                <span>5.</span>
                <div class="content_body__end">
                  <span>{{ t("modalAgreementCenter.titleContent11") }}</span>
                  <span>
                    {{ t("modalAgreementCenter.titleContent12") }}
                  </span>
                  <span>{{ t("modalAgreementCenter.titleContent13") }}</span>
                </div>
              </li>
              <li>
                <span>6.</span>
                <div class="content_body__end">
                  <span>{{ t("modalAgreementCenter.titleContent14") }}</span>
                  <span>
                    <span>{{ t("modalAgreementCenter.titleContent15") }}</span>
                    <span class="text_primary">
                      {{ " " + t("modalAgreementCenter.titleContent36") }} {{ dataForm.paymentAmount }}{{ t("modalAgreementCenter.titleContent37") }}
                      {{ dataForm.paymentDate }}{{ t("modalAgreementCenter.titleContent38") }}
                    </span>
                    <span>{{ t("modalAgreementCenter.titleContent16") }}</span>
                  </span>
                  <span>{{ t("modalAgreementCenter.titleContent17") }}</span>
                  <span>
                    {{ t("modalAgreementCenter.titleContent18") }}
                  </span>
                </div>
              </li>
              <li>
                <span>7.</span>
                <div class="content_body__end">
                  <span>
                    {{ t("modalAgreementCenter.titleContent21") }}
                  </span>
                </div>
              </li>
              <li>
                <span>8.</span>
                <div class="content_body__end">
                  <span>
                    {{ t("modalAgreementCenter.titleContent22") }}
                  </span>
                </div>
              </li>
              <li>
                <span>9.</span>
                <div class="content_body__end">
                  <span>
                    {{ t("modalAgreementCenter.titleContent23") }}
                    <span class="text_primary spaced_letters">
                      {{ dataForm.employmentContractYn }}, {{ t("modalAgreementCenter.titleContent39") }} {{ dataForm.insuranceParticipationYn }}.
                    </span>
                    <span class="spaced_letters">
                      {{ t("modalAgreementCenter.titleContent24") }}
                    </span>
                  </span>
                </div>
              </li>
              <div class="content_footer">
                <p>
                  {{ t("modalAgreementCenter.titleContent25") }}
                </p>
                <p>
                  {{ t("modalAgreementCenter.titleContent26") }}
                </p>
                <p>
                  {{ t("modalAgreementCenter.titleContent27") }}
                </p>
                <p>
                  {{ t("modalAgreementCenter.titleContent28") }}
                </p>
                <p>
                  {{ t("modalAgreementCenter.titleContent29") }}
                </p>
                <p>{{ t("modalAgreementCenter.titleContent30") }}</p>
                <p>{{ dataForm.uniSignedDate }}</p>
              </div>
            </ul>
          </div>
        </div>
        <div class="semester_agreement__content">
          <div class="group_file__content">
            <div class="group_file__content__title">
              <p>{{ t("modalAgreementCenter.school") }}</p>
            </div>
            <div class="group_file__content__file">
              <div>
                <p>{{ t("modalAgreementCenter.nameSchool") }}</p>
                <p>{{ t("modalAgreementCenter.positionSchool") }}</p>
              </div>

              <div style="display: flex; align-items: center">
                <div class="add_photo mg_r10" v-if="isShowUniv">
                  <img style="width: 100%; height: 100%; object-fit: cover" :src="imageUnivSign" />
                </div>
                <button v-else :disabled="!isShowUniv && !isShowCorp && !isShowStd" @click="openModalConfirm" class="btn_round btn_primary btn_md">
                  {{ t("modalAgreementCenter.btnStamp") }}
                </button>
              </div>
            </div>
          </div>
          <div class="group_file__content">
            <div class="group_file__content__title">
              <p>{{ t("modalAgreementCenter.trainingInstitute") }}</p>
            </div>
            <div class="group_file__content__file">
              <div>
                <p>{{ t("modalAgreementCenter.nameTrainingInstitute") }}</p>
                <p>{{ t("modalAgreementCenter.positionSchoolTrainingInstitute") }} {{ dataForm.managerNm }}</p>
              </div>
              <div>
                <div class="add_photo mg_r10" v-if="isShowCorp && dataForm.urlFile">
                  <img style="width: 100%; height: 100%; object-fit: cover" :src="dataForm.urlFile" />
                </div>
              </div>
            </div>
          </div>
          <div class="group_file__content">
            <div class="group_file__content__title">
              <p>{{ t("modalAgreementCenter.student") }}</p>
            </div>
            <div class="group_file__content__file">
              <div>
                <p>{{ t("modalAgreementCenter.nameStudent") }}</p>
                <p>{{ deptStd }} {{ stdNm }}</p>
              </div>
              <div v-if="isShowStd">
                <InputFileBase
                  :mode="MODE_DETAIL"
                  :id="`semester_agreement_univ`"
                  :type="'img'"
                  :preview-flag="true"
                  :show-mess="false"
                  :orgName="'STD_SIGN'"
                  :category="'img'"
                  :sectionName="'STD_SIGN'"
                  :referKey="tripartiteSeq"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </template>
  </TModal>
  <TModalConfirm :is-open="modalOpenConfirm" :is-show-header="true" :is-show-footer="true" :modal-id="`modalId2`" @close-modal="closeModalConfirm">
    <template #title>
      <strong>{{ t("modalAgreementCenter.titleModalConfirm") }}</strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ t("modalAgreementCenter.contentModalConfirm") }}</p>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_md btn_primary" @click="confirmAction">{{ t("common.confirm") }}</button>
      <button type="button" class="button btn_md btn_white popup_close" @click="cancelAction">{{ t("common.cancel") }}</button>
    </template>
  </TModalConfirm>
  <TModalAlert :is-open="modalOpenAlert" :is-show-header="true" :is-show-footer="true" :modal-id="`modalId1`" @close-modal="closeModalAlert">
    <template #title>
      <strong>{{ t("modalAgreementCenter.titleModalConfirm") }}</strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ t("modalAgreementCenter.contentModalAlert") }}</p>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_md btn_primary" @click="closeModalAlert">{{ t("common.confirm") }}</button>
    </template>
  </TModalAlert>
</template>

<script setup lang="ts">
import TModal from "@/components/common/excel/TModal.vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import { watch, ref } from "vue";
import { useI18n } from "vue-i18n";
import { modalAgreementCenterStore } from "@/stores/modalAgreementCenter";
import { AgreementCenterTableModel } from "@/stores/modalAgreementCenter/modalAgreementCenter.type";
import { MODE_DETAIL, STATUS_YES } from "@/constants/common.const";
import imageUnivSign from "@/assets/images/학교날인.jpg";
import { CodeMngModel } from "@/stores/common/codeMng/codeMng.type";

const isOpen = ref(false);
const { t } = useI18n();
const modalAgreementCenter = modalAgreementCenterStore();
const idPlan = ref("");
const key = ref(0);
const dataForm = ref<AgreementCenterTableModel>({
  internTerm: "",
  internYear: "",
  cormNameKr: "",
  internshipStr: "",
  internshipEnd: "",
  duration: "",
  durationHourStr: "",
  durationMinuteStr: "",
  durationHourEnd: "",
  durationMinuteEnd: "",
  internshipWeekDay: "",
  internshipDay: "",
  paymentAmount: "",
  paymentFrequency: "",
  paymentTimingType: "",
  paymentDate: "",
  employmentContractYn: "",
  insuranceParticipationYn: "",
  regDate: "",
  urlFile: "",
  interPlanNm: "",
  cormSeq: "",
  uniSignedDate: "",
  managerNm: "",
});
const modalOpenConfirm = ref(false);
const modalOpenAlert = ref(false);
const dayMap = {
  "1": "일",
  "2": "월",
  "3": "화",
  "4": "수",
  "5": "목",
  "6": "금",
  "7": "토",
};

const isShowUniv = ref(false);
const isShowCorp = ref(false);
const isShowStd = ref(false);
const stdNm = ref("");
const deptStd = ref("");
const stdValue = ref("");
const studentSelected = ref();
const listStdName = ref<CodeMngModel[]>([{ cdId: "", cdNm: t("common.selectedStd"), upCdId: "ALL" }]);
const listStdFullInfo = ref([]);
const isDisabled = ref(true);
const modalKey = ref(1);
const tripartiteSeq = ref("");
const closeModal = () => {
  listStdName.value = [{ cdId: "", cdNm: t("common.selectedStd"), upCdId: "ALL" }];
  isDisabled.value = true;
  isOpen.value = false;
  stdValue.value = "";
  dataForm.value = {
    internTerm: "",
    internYear: "",
    cormNameKr: "",
    internshipStr: "",
    internshipEnd: "",
    duration: "",
    durationHourStr: "",
    durationMinuteStr: "",
    durationHourEnd: "",
    durationMinuteEnd: "",
    internshipWeekDay: "",
    internshipDay: "",
    paymentAmount: "",
    paymentFrequency: "",
    paymentTimingType: "",
    paymentDate: "",
    employmentContractYn: "",
    insuranceParticipationYn: "",
    regDate: "",
    urlFile: "",
    interPlanNm: "",
    cormSeq: "",
    managerNm: "",
  } as AgreementCenterTableModel;
};
const closeModalConfirm = () => {
  modalOpenConfirm.value = false;
};
const openModalConfirm = () => {
  modalOpenConfirm.value = true;
};
const confirmAction = () => {
  saveData();
};
const cancelAction = () => {
  closeModalConfirm();
};
const closeModalAlert = () => {
  stdValue.value = ""
  modalKey.value++;
  listStdName.value = [{ cdId: "", cdNm: t("common.selectedStd"), upCdId: "ALL" }];
  getListStdName();

  modalOpenAlert.value = false;
};
const formatDateToKorean = (dateStr) => {
  const date = new Date(dateStr);
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  return `${year}년 ${month}월 ${day}일`;
};
const formatDaysToKorean = (daysStr) => {
  return daysStr
    .split(",")
    .map((day) => dayMap[day.trim()])
    .join(", ");
};
const saveData = async () => {
  const lstStdId =
    stdValue.value === ""
      ? listStdFullInfo.value.filter((item) => item.univSignedYn === "N").map((el) => el.tripartiteAgreementSeq)
      : [studentSelected.value.tripartiteAgreementSeq];

  await modalAgreementCenter.appendAll({
    method: "PUT",
    body: { lstId: lstStdId },
  });

  modalOpenConfirm.value = false;
  modalOpenAlert.value = true;
};

const getDetail = async () => {
  await modalAgreementCenter.getSignSupportCenter({
    method: "POST",
    body: idPlan.value,
  });
  dataForm.value = modalAgreementCenter.resultData.data;
  dataForm.value.uniSignedDate = formatDateToKorean(dataForm.value.uniSignedDate);
  dataForm.value.internshipEnd = formatDateToKorean(dataForm.value.internshipEnd);
  dataForm.value.internshipStr = formatDateToKorean(dataForm.value.internshipStr);
  dataForm.value.internshipDay = formatDaysToKorean(dataForm.value.internshipDay);
};

const getListStdName = async () => {
  await modalAgreementCenter.getListStudent({
    method: "POST",
    body: idPlan.value,
  });

  if (modalAgreementCenter.resultDataStd.data.length > 0) {
    listStdFullInfo.value = modalAgreementCenter.resultDataStd.data;
    listStdName.value = [
      ...listStdName.value,
      ...modalAgreementCenter.resultDataStd.data.map((item) => ({
        cdId: item.id,
        cdNm: item.name,
        upCdId: "ALL",
      })),
    ];
    isDisabled.value = false;
  } else {
    isDisabled.value = true;
  }
};

const openModal = (id: string) => {
  idPlan.value = id;
  getDetail();
  isOpen.value = true;
  key.value++;
  getListStdName();
  modalKey.value++;
};

watch(
  () => stdValue.value,
  async (newValue) => {
    const currentDate = new Date();
    const formattedDate = currentDate.toISOString().split("T")[0];

    if (newValue !== "") {
      const stdSelected = listStdFullInfo.value.find((item) => item.id === newValue);
      dataForm.value.uniSignedDate = stdSelected.uniSignedDate ? formatDateToKorean(stdSelected.uniSignedDate) : formatDateToKorean(formattedDate);
      studentSelected.value = stdSelected;
      tripartiteSeq.value = stdSelected?.tripartiteAgreementSeq || "";
      stdNm.value = stdSelected?.name || "";
      deptStd.value = stdSelected?.deptStdNm || "";
      isShowUniv.value = tripartiteSeq.value !== "" && stdSelected?.univSignedYn === STATUS_YES;
      isShowCorp.value = tripartiteSeq.value !== "" && stdSelected?.corpSignedYn === STATUS_YES;
      isShowStd.value = tripartiteSeq.value !== "" && stdSelected?.stdSignedYn === STATUS_YES;
    } else {
      dataForm.value.uniSignedDate = formatDateToKorean(formattedDate);
      tripartiteSeq.value = "";
      stdNm.value = "";
      deptStd.value = "";
      isShowUniv.value = false;
      isShowCorp.value = false;
      isShowStd.value = false;
    }
  },
);

defineExpose({
  openModal,
});
</script>

<style scoped lang="scss">
.semester_agreement {
  color: var(--Gray-scale-90, rgba(29, 29, 29, 1));

  &__title {
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-wrap: wrap;
    gap: 12px;

    & > h2 {
      font-size: 18px;
      font-weight: 500;
      line-height: 27px;
    }

    &__right {
      display: flex;
      align-items: center;
      gap: 8px;
    }
  }

  &__content {
    &:nth-child(2) {
      margin-top: 20px;
      border: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));
      padding: 16px;
      border-radius: 8px;

      & p,
      & span {
        font-size: 16px;
        font-weight: 400;
        line-height: 24px;
      }

      .content_header {
        display: flex;
        flex-direction: column;
        gap: 24px;
      }

      .content_body {
        margin-top: 12px;

        & ul {
          display: flex;
          flex-direction: column;
          gap: 4px;

          li {
            display: flex;

            & > span:first-child {
              display: block;
              width: 20px;
            }

            & > div {
              width: calc(100% - 20px);
            }

            .content_body__end {
              display: flex;
              flex-direction: column;
              gap: 8px;
            }
          }
        }
      }

      .content_footer {
        margin-top: 12px;
        display: flex;
        flex-direction: column;
        gap: 24px;

        & p {
          font-size: 16px;
          font-weight: 500;
          line-height: 24px;
          color: var(--Gray-scale-90, rgba(29, 29, 29, 1));

          &:last-child {
            color: var(--Gray-scale-70, rgba(85, 85, 85, 1));
          }

          &:nth-last-child(2){
            font-size: 18px;
            font-weight: 500;
            line-height: 27px;
            letter-spacing: -0.01em;
          }
        }
      }
    }

    &:nth-child(3) {
      display: flex;
      gap: 8px;
      margin-top: 24px;
      flex-wrap: wrap;

      .group_file__content {
        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
        gap: 0;
        flex: 1;

        &__title {
          padding: 17.5px;
          width: 100%;
          text-align: center;
          border-top: 1px solid var(--Gray-scale-80, rgba(45, 45, 45, 1));
          background: var(--Gray-scale-5, rgba(248, 248, 248, 1));
          border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));
          p {
            font-size: 14px;
            font-weight: 500;
            line-height: 21px;
          }
        }

        &__file {
          display: flex;
          justify-content: space-between;
          width: 100%;
          padding: 16px;
          border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));
          gap: 8px;
          flex: 1;

          & > div:first-child {
            display: flex;
            flex-direction: column;
            justify-content: space-evenly;
            p {
              &:first-child {
                font-size: 14px;
                font-weight: 400;
                line-height: 21px;
                color: var(--Gray-scale-70, rgba(85, 85, 85, 1));
              }
              &:last-child {
                font-size: 16px;
                font-weight: 500;
                line-height: 24px;
                color: var(--Gray-scale-80, rgba(45, 45, 45, 1));
              }
            }
          }
        }
      }
    }
  }

  .text_primary {
    color: var(--primary);
  }

  .text_bold {
    font-weight: 500;
  }

  .text_red {
    color: #fa0707;
    font-weight: bold !important;
    margin-top: 15px;
  }
}

@media screen and (max-width: 768px) {
  .semester_agreement {
    &__title {
      display: flex;
      align-items: center;
      justify-content: space-between;

      & > h2 {
        font-size: 18px;
        font-weight: 500;
        line-height: 27px;
      }

      &__right {
        display: flex;
        align-items: center;
        gap: 8px;
      }
    }

    &__content {
      &:nth-child(2) {
        max-height: 480px;
        overflow: auto;
      }
      &:nth-child(3) {
        flex-direction: column;
      }
    }
  }
}

@media screen and (max-width: 480px) {
  .semester_agreement {
    &__title {
      display: flex;
      align-items: flex-start;
      flex-direction: column-reverse;

      & > h2 {
        font-size: 18px;
        font-weight: 500;
        line-height: 27px;
      }

      &__right {
        display: flex;
        align-items: center;
        gap: 8px;
        width: 100%;
      }
    }
    &__content {
      &:nth-child(2) {
        max-height: 240px;
        overflow: auto;
      }
      &:nth-child(3) {
        gap: 12px;
        .group_file__content {
          &__title {
            padding: 9.5px;
          }
        }
      }
    }
  }
}
.spaced_letters {
  position: relative;
  bottom: 3px;
}
</style>
