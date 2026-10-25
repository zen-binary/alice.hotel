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
      <strong>{{ t("modalSemesterAgreement.titleModal") }}</strong>
    </template>
    <template #default>
      <div class="semester_agreement" id="semester_agreement">
        <div class="semester_agreement__title">
          <h2>{{ dataForm.interPlanNm }}</h2>
          <div class="semester_agreement__title__right">
            <SelectBoxBase v-model="stdId" id="selectbox_student" name="selectbox_student" :data="listStdName" />
            <button :disabled="isDisabled" class="button btn_white btn_lg hidden_print" @click="openModalConfirm">
              {{ t("modalSemesterAgreement.btnSave") }}
            </button>
            <ButtonExportPDFV1 class="btn_lg" id="semester_agreement" name-export="semester_agreement" />
          </div>
        </div>

        <div class="semester_agreement__content is_scroll">
          <div class="content_header">
            <span>
              {{ t("modalSemesterAgreement.titleContent1") }}
            </span>
            <div>
              <span class="text_primary text_bold">
                {{ t("modalSemesterAgreement.titleContent2") }} {{ dataForm.internYear }}{{ t("modalSemesterAgreement.titleContent3") }}
                {{ dataForm.internTerm }}
              </span>
              &nbsp;
              <span class="text_bold">{{ t("modalSemesterAgreement.titleContent4") }}</span>
            </div>
            <p class="text_bold">{{ t("modalSemesterAgreement.titleContent5") }}</p>
          </div>
          <div class="content_body">
            <ul>
              <li>
                <span class="text_primary">1.</span>
                <span class="text_primary">{{ t("modalSemesterAgreement.titleContent6") }} : {{ dataForm.cormNameKr }}</span>
              </li>
              <li>
                <span>2.</span>
                <div>
                  <span>
                    {{ t("modalSemesterAgreement.titleContent7") }}
                  </span>
                </div>
              </li>
              <li>
                <span class="text_primary">3.</span>
                <div>
                  <span class="text_primary">
                    {{ t("modalSemesterAgreement.titleContent31") }} {{ dataForm.internshipStr }}부터 {{ dataForm.internshipEnd
                    }}{{ t("modalSemesterAgreement.titleContent32") }}
                  </span>
                  &nbsp;
                  <span>{{ t("modalSemesterAgreement.titleContent8") }}</span>
                </div>
              </li>
              <li>
                <span>4.</span>
                <div class="content_body__end">
                  <span>{{ t("modalSemesterAgreement.titleContent9") }}</span>
                  <span class="text_primary">
                    {{ t("modalSemesterAgreement.titleContent33", {time: dataForm.duration}) }} / {{ dataForm.durationHourStr }} :{{ dataForm.durationMinuteStr }} ~
                    {{ dataForm.durationHourEnd }} :{{ dataForm.durationMinuteEnd }}
                    {{ t("modalSemesterAgreement.titleContent34") }}
                  </span>
                  <span class="text_primary">
                    {{ t("modalSemesterAgreement.titleContent35") }}{{ dataForm.internshipWeekDay }}일 ({{ dataForm.internshipDay }})
                  </span>
                  <span>
                    {{ t("modalSemesterAgreement.titleContent10") }}
                  </span>
                  <span>
                    {{ t("modalSemesterAgreement.titleContent11") }}
                  </span>
                </div>
              </li>
              <li>
                <span>5.</span>
                <div class="content_body__end">
                  <span>{{ t("modalSemesterAgreement.titleContent12") }}</span>
                  <span>
                    {{ t("modalSemesterAgreement.titleContent13") }}
                  </span>
                  <span>{{ t("modalSemesterAgreement.titleContent14") }}</span>
                </div>
              </li>
              <li>
                <span>6.</span>
                <div class="content_body__end">
                  <span>{{ t("modalSemesterAgreement.titleContent15") }}</span>
                  <span>
                    <span>{{ t("modalSemesterAgreement.titleContent16") }}</span>
                    <span class="text_primary">
                      {{ t("modalSemesterAgreement.titleContent36") }} {{ dataForm.paymentAmount }}{{ t("modalSemesterAgreement.titleContent37") }}
                      {{ dataForm.paymentDate }}{{ t("modalSemesterAgreement.titleContent38") }}
                    </span>
                    <span>{{ t("modalSemesterAgreement.titleContent17") }}</span>
                  </span>
                  <span>{{ t("modalSemesterAgreement.titleContent18") }}</span>
                  <span>
                    {{ t("modalSemesterAgreement.titleContent19") }}
                  </span>
                </div>
              </li>
              <li>
                <span>
                  7.
                </span>
                <div class="content_body__end">
                  <span>{{ t("modalSemesterAgreement.titleContent20") }}</span>
                </div>
              </li>
              <li>
                <span>8.</span>
                <div class="content_body__end">
                  <span>
                    {{ t("modalSemesterAgreement.titleContent21") }}
                  </span>
                </div>
              </li>
              <li>
                <span>9.</span>
                <div class="content_body__end">
                  <span>{{ t("modalSemesterAgreement.titleContent22") }}
                    <span class="text_primary spaced_letters">
                      {{ dataForm.employmentContractYn }}, {{ "" + t("modalSemesterAgreement.titleContent39") }} {{ dataForm.insuranceParticipationYn }}.
                    </span>
                    <span class="spaced_letters">
                      {{ "" + t("modalSemesterAgreement.titleContent23") }}
                    </span> 
                  </span>
                </div>
              </li>
              <div class="content_footer">
                <p>
                  {{ t("modalSemesterAgreement.titleContent24") }}
                </p>
                <p>{{ t("modalSemesterAgreement.titleContent25") }}</p>
                <p>
                  {{ t("modalSemesterAgreement.titleContent26") }}
                </p>
                <p>
                  {{ t("modalSemesterAgreement.titleContent27") }}
                </p>
                <p>
                  {{ t("modalSemesterAgreement.titleContent28") }}
                </p>
                <p>
                  {{ t("modalSemesterAgreement.titleContent29") }}
                </p>
                <p>{{ corporateSignedDate }}</p>
              </div>
            </ul>
          </div>
        </div>
        <div class="semester_agreement__content">
          <div class="group_file__content">
            <div class="group_file__content__title">
              <p>{{ t("modalSemesterAgreement.school") }}</p>
            </div>
            <div class="group_file__content__file">
              <div>
                <p>{{ t("modalSemesterAgreement.nameSchool") }}</p>
                <p>{{ t("modalSemesterAgreement.positionSchool") }}</p>
              </div>
              <div class="add_photo mg_r10" v-if="isShowUnivSign">
                <img v-if="isShowUnivSign" style="width: 100%; height: 100%; object-fit: cover" :src="imageUnivSign" />
              </div>
            </div>
          </div>
          <div class="group_file__content">
            <div class="group_file__content__title">
              <p>{{ t("modalSemesterAgreement.trainingInstitute") }}</p>
            </div>
            <div class="group_file__content__file">
              <div>
                <p>{{ t("modalSemesterAgreement.nameTrainingInstitute") }}</p>
                <p>{{ t("modalSemesterAgreement.positionSchoolTrainingInstitute") }} {{ dataForm.managerNm }} </p>
              </div>
              <div class="btn_custom_modal_confirm">
                <button v-if="statusBtn" class="button btn_md btn_primary" :disabled="statusBtnSave" @click="openModalConfirm">
                  {{ t("modalSemesterAgreement.buttonSave") }}
                </button>
                <div class="add_photo mg_r10" v-if="!statusBtn">
                  <img style="width: 100%; height: 100%; object-fit: cover" :src="dataForm.urlFile" />
                </div>
              </div>
            </div>
          </div>
          <div class="group_file__content">
            <div class="group_file__content__title">
              <p>{{ t("modalSemesterAgreement.student") }}</p>
            </div>
            <div class="group_file__content__file">
              <div>
                <p>{{ t("modalSemesterAgreement.nameStudent") }}</p>
                <p>{{ `${deptStd}  ${stdNm}` }}</p>
              </div>
              <div v-if="isShowStdSign">
                <InputFileBase
                  :mode="MODE_DETAIL"
                  :id="`semester_agreement_corp`"
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
      <strong>{{ t("modalSemesterAgreement.titleModalConfirm") }}</strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ stdId !== "" ? t("modalSemesterAgreement.contentModalConfirm2") : t("modalSemesterAgreement.contentModalConfirm") }}</p>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_md btn_primary" @click="confirmAction">{{ t("common.confirm") }}</button>
      <button type="button" class="button btn_md btn_white popup_close" @click="closeModalConfirm">{{ t("common.cancel") }}</button>
    </template>
  </TModalConfirm>

  <TModalAlert :is-open="modalOpenAlert" :is-show-header="true" :is-show-footer="true" :modal-id="`modalId1`" @close-modal="closeModalAlert">
    <template #title>
      <strong>{{ t("modalSemesterAgreement.titleModalConfirm") }}</strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ stdId !== "" ? t("modalSemesterAgreement.contentModalAlert2") : t("modalSemesterAgreement.contentModalAlert") }}</p>
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
import InputFileBase from "@/components/common/input/InputFileBase.vue";
import { watch, ref } from "vue";
import ButtonExportPDFV1 from "@/components/common/ButtonExportPDFV1.vue";
import { semesterAgreementStore } from "@/stores/corporate/semesterAgreement";
import { SignedResponses, StudentInternCorporateRes } from "@/stores/corporate/semesterAgreement/semesterAgreement.type";
import { useI18n } from "vue-i18n";
import { CodeMngModel } from "@/stores/common/codeMng/codeMng.type";
import { MODE_DETAIL, STATUS_YES } from "@/constants/common.const";
import imageUnivSign from "@/assets/images/학교날인.jpg";

const isOpen = ref(false);
const modalOpenConfirm = ref(false);
const modalOpenAlert = ref(false);
const modalKey = ref(1);
const { t } = useI18n();
const store = semesterAgreementStore();

const stdId = ref("");
const planId = ref("");
const statusBtn = ref(true);
const statusBtnSave = ref(true);

const isShowUnivSign = ref(false);
const isShowStdSign = ref(false);
const tripartiteSeq = ref("");
const deptStd = ref("");
const stdNm = ref("");

const dayMap = {
  "1": "일",
  "2": "월",
  "3": "화",
  "4": "수",
  "5": "목",
  "6": "금",
  "7": "토",
};
const listStdName = ref<CodeMngModel[]>([{ cdId: "", cdNm: t("common.selectedStd"), upCdId: "ALL" }]);
const originalList = ref<StudentInternCorporateRes[]>([]);

const dataForm = ref<SignedResponses>({
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
  corporateSignedDate: "",
  managerNm: ""
});

const isDisabled = ref(true);
const corporateSignedDate = ref("");

//first data
const openModal = (id: string) => {
  planId.value = id;
  modalKey.value++;
  getDetail();
  getListStdName();

  isOpen.value = true;
};

const closeModal = () => {
  listStdName.value = [{ cdId: "", cdNm: t("common.all"), upCdId: "ALL" }];
  isDisabled.value = true;
  isOpen.value = false;
  stdId.value = "";
  tripartiteSeq.value = "";
  statusBtn.value = true;
};

const getDetail = async () => {
  await store.getListSign({
    method: "POST",
    body: planId.value,
  });
  dataForm.value = store.resultData.data;
  dataForm.value.internshipEnd = dataForm.value?.internshipEnd ? formatDateToKorean(dataForm.value?.internshipEnd) : "";
  dataForm.value.internshipStr = dataForm.value?.internshipEnd ? formatDateToKorean(dataForm.value?.internshipStr) : "";
  dataForm.value.internshipDay = dataForm.value.internshipDay ? formatDaysToKorean(dataForm.value.internshipDay) : "";
  corporateSignedDate.value = dataForm.value?.corporateSignedDate ? formatDateToKorean(dataForm.value?.corporateSignedDate) : "";
};

const getListStdName = async () => {
  await store.getListStudent({
    method: "POST",
    body: planId.value,
  });

  if (store.resultDataStd.data.length > 0) {
    originalList.value = store.resultDataStd.data;

    listStdName.value = [
      ...listStdName.value,
      ...store.resultDataStd.data.map((item) => ({
        cdId: item.internStdSeq,
        cdNm: item.internStdNm,
        upCdId: "ALL",
      })),
    ];

    isDisabled.value = !originalList.value.some((item) => item.tripartiteAgreementSeq == null || item.corporateSignedYn === "N");
  } else {
    isDisabled.value = true;
  }

  statusBtn.value = true;
};

//action handle click
const openModalConfirm = () => {
  modalOpenConfirm.value = true;
};

const closeModalConfirm = () => {
  modalOpenConfirm.value = false;
};

const closeModalAlert = () => {
  stdId.value = "";
  statusBtn.value = true;
  modalKey.value++;
  modalOpenAlert.value = false;
};

const confirmAction = () => {
  saveData();
};

//interact with db
const saveData = async () => {
  const lstStdId =
    stdId.value === ""
      ? originalList.value.filter((item) => item.tripartiteAgreementSeq == null || item.corporateSignedYn === "N").map((el) => el.internStdSeq)
      : [stdId.value];

  await store.createTripartiteAgreeData({
    method: "POST",
    body: { planId: planId.value, lstStdId: lstStdId },
  });

  if (store.resultDataStd.data.length > 0) {
    const resultDataMap = new Map(store.resultDataStd.data.map((el: any) => [el.internStdSeq, el]));
    originalList.value.forEach((item) => {
      const matchedItem = resultDataMap.get(item.internStdSeq);
      if (matchedItem) {
        item.tripartiteAgreementSeq = matchedItem.tripartiteAgreementSeq;
        item.corporateSignedYn = matchedItem.corporateSignedYn;
        item.corporateSignedDate = matchedItem.corporateSignedDate;
        item.univSignedYn = matchedItem.univSignedYn;
      }
    });
  }

  modalOpenConfirm.value = false;
  modalOpenAlert.value = true;
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

watch(
  () => stdId.value,
  async (newValue) => {
    if (newValue === "") {
      isShowUnivSign.value = false;
      isShowStdSign.value = false;
      tripartiteSeq.value = "";
      deptStd.value = "";
      stdNm.value = "";
      isDisabled.value = !originalList.value.some((item) => item.tripartiteAgreementSeq == null || item.corporateSignedYn === "N");
      statusBtn.value = true;
      statusBtnSave.value = true;
      corporateSignedDate.value = dataForm.value?.corporateSignedDate ? formatDateToKorean(dataForm.value?.corporateSignedDate) : "";
    } else {
      statusBtnSave.value = false;
      isDisabled.value = true;
      corporateSignedDate.value = dataForm.value?.corporateSignedDate ? formatDateToKorean(dataForm.value?.corporateSignedDate) : "";

      const selectedValue = originalList.value.find((item) => item.internStdSeq === newValue);
      tripartiteSeq.value = selectedValue?.tripartiteAgreementSeq || "";
      statusBtn.value = selectedValue?.corporateSignedYn !== STATUS_YES;
      isShowUnivSign.value = tripartiteSeq.value !== "" && selectedValue?.univSignedYn === STATUS_YES;
      isShowStdSign.value = tripartiteSeq.value !== "" && selectedValue?.stdSignedYn === STATUS_YES;
      deptStd.value = selectedValue?.internStdDeptNm || "";
      stdNm.value = selectedValue?.internStdNm || "";
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

  &__form {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid var(--black);
    padding-bottom: 20px;
    @media screen and (max-width: 768px) {
      flex-direction: column-reverse;
      align-items: flex-start;
      gap: 10px;
    }
    .option {
      display: flex;
      align-items: center;
      gap: 10px;
      @media screen and (max-width: 768px) {
        width: 100%;
        justify-content: flex-end;
      }
    }
  }

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
        flex: 1 1;

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

.title-block {
  font-size: 16px;
  font-weight: 700;
  margin-bottom: 10px;
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

.btn_custom_modal_confirm {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
}

@media print {
  .group_file__content__file {
    align-items: stretch;
    height: auto !important;
  }
  .group_file__content__file > div {
    height: auto !important;
    max-height: none !important;
  }
}
.spaced_letters {
  position: relative;
  bottom: 3px;
}
</style>
