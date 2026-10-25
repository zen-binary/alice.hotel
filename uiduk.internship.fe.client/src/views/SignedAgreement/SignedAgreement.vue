<template>
  <TModal :is-open="isOpen" :is-show-header="true" :is-show-footer="false" :modal-id="'modalId'" class-size="medium-large" @close-modal="closeModal">
    <!-- Modal content goes here -->
    <template #title>
      <strong>{{ t("signedAgreement.titleModal") }}</strong>
    </template>
    <template #default>
      <div class="semester_agreement" id="semester_agreement2">
        <div class="semester_agreement__title">
          <h2>{{ dataForm.internPlanName }}</h2>
          <div class="semester_agreement__title__right">
            <SelectBoxBase disabled="true" name="selectbox" :data="listStdName" />
            <button :disabled="status || signedAgreementFileArr.length <= 0" class="button btn_white btn_lg" @click="openModalConfirm">
              {{ t("signedAgreement.btnSave") }}
            </button>
            <ButtonExportPDFV1 class="btn_lg" id="semester_agreement2" name-export="semester_agreement" />
          </div>
        </div>
        <div class="semester_agreement__content is_scroll">
          <div class="content_header">
            <span>
              {{ t("signedAgreement.titleContent1") }}
            </span>
            <div>
              <span class="text_primary text_bold">
                {{ t("signedAgreement.titleContent2") }} {{ dataForm.internYear }}{{ t("signedAgreement.titleContent3") }}
                {{ dataForm.internTerm }}
              </span>
              &nbsp;
              <span class="text_bold">{{ t("signedAgreement.titleContent4") }}</span>
            </div>
            <p class="text_bold">{{ t("signedAgreement.titleContent5") }}</p>
          </div>
          <div class="content_body">
            <ul>
              <li>
                <span class="text_primary">1.</span>
                <span class="text_primary">{{ t("signedAgreement.titleContent6") }} : {{ dataForm.cormName }}</span>
              </li>
              <li>
                <span>2.</span>
                <div>
                  <span>
                    {{ t("signedAgreement.titleContent7") }}
                  </span>
                </div>
              </li>
              <li>
                <span class="text_primary">3.</span>
                <div>
                  <span class="text_primary">
                    {{ t("signedAgreement.titleContent31") }} {{ dataForm.internshipStr }}부터 {{ dataForm.internshipEnd
                    }}{{ t("signedAgreement.titleContent32") }}
                  </span>
                  &nbsp;
                  <span>{{ t("signedAgreement.titleContent8") }}</span>
                </div>
              </li>
              <li>
                <span>4.</span>
                <div class="content_body__end">
                  <span>{{ t("signedAgreement.titleContent9") }}</span>
                  <span class="text_primary">
                    {{ t("signedAgreement.titleContent33", { time: dataForm.duration }) }} / {{ dataForm.durationHourStr }} :{{ dataForm.durationMinuteStr }} ~
                    {{ dataForm.durationHourEnd }} :{{ dataForm.durationMinuteEnd }}
                    {{ t("signedAgreement.titleContent34") }}
                  </span>
                  <span class="text_primary">
                    {{ t("signedAgreement.titleContent35") }}{{ dataForm.internshipWeekDay }}일 ({{ dataForm.internshipDay }})
                  </span>
                  <span>
                    {{ t("signedAgreement.titleContent10") }}
                  </span>
                  <span>
                    {{ t("signedAgreement.titleContent11") }}
                  </span>
                </div>
              </li>
              <li>
                <span>5.</span>
                <div class="content_body__end">
                  <span>{{ t("signedAgreement.titleContent12") }}</span>
                  <span>
                    {{ t("signedAgreement.titleContent13") }}
                  </span>
                  <span>{{ t("signedAgreement.titleContent14") }}</span>
                </div>
              </li>
              <li>
                <span>6.</span>
                <div class="content_body__end">
                  <span>{{ t("signedAgreement.titleContent15") }}</span>
                  <span>
                    <span>{{ t("signedAgreement.titleContent16") }}</span>
                    <span class="text_primary">
                      {{ t("signedAgreement.titleContent36") }} {{ dataForm.paymentAmount }}{{ t("signedAgreement.titleContent37") }}
                      {{ dataForm.paymentDate }}{{ t("signedAgreement.titleContent38") }}
                    </span>
                    <span>{{ t("signedAgreement.titleContent17") }}</span>
                  </span>
                  <span>{{ t("signedAgreement.titleContent18") }}</span>
                  <span>
                    {{ t("signedAgreement.titleContent19") }}
                  </span>
                </div>
              </li>
              <li>
                <span>7.</span>
                <div class="content_body__end">
                  <span>
                    {{ t("signedAgreement.titleContent21") }}
                  </span>
                </div>
              </li>
              <li>
                <span>8.</span>
                <div class="content_body__end">
                  <span>
                    {{ t("signedAgreement.titleContent22") }}
                  </span>
                </div>
              </li>
              <li>
                <span>9.</span>
                <div class="content_body__end">
                  <span>{{ t("signedAgreement.titleContent23") }}
                    <span class="text_primary spaced_letters">
                      {{ dataForm.employmentContract }}, {{ t("signedAgreement.titleContent39") }} {{ dataForm.insuranceParticipation }}.
                    </span>
                    <span class="spaced_letters">
                      {{ t("signedAgreement.titleContent24") }}
                    </span>
                  </span>
                </div>
              </li>
              <div class="content_footer">
                <p>
                  {{ t("signedAgreement.titleContent25") }}
                </p>
                <p>
                  {{ t("signedAgreement.titleContent26") }}
                </p>
                <p>
                  {{ t("signedAgreement.titleContent27") }}
                </p>
                <p>
                  {{ t("signedAgreement.titleContent28") }}
                </p>
                <p>
                  {{ t("signedAgreement.titleContent29") }}
                </p>
                <p>{{ t("signedAgreement.titleContent30") }}</p>
                <p>
                  {{ dataForm.corporateSignedDate }}
                </p>
              </div>
            </ul>
          </div>
        </div>       
        <div class="semester_agreement__content">
          <div class="group_file__content">
            <div class="group_file__content__title">
              <p>{{ t("signedAgreement.school") }}</p>
            </div>
            <div class="group_file__content__file">
              <div>
                <p>{{ t("signedAgreement.nameSchool") }}</p>
                <p>{{ t("signedAgreement.positionSchool") }}</p>
              </div>
              <div class="add_photo mg_r10" v-if="dataForm.tripartiteSeq && dataForm.univSignedYn === STATE_Y">
                <img style="width: 100%; height: 100%; object-fit: cover" :src="imageUnivSign" />
              </div>
            </div>
          </div>
          <div class="group_file__content">
            <div class="group_file__content__title">
              <p>{{ t("signedAgreement.trainingInstitute") }}</p>
            </div>
            <div class="group_file__content__file">
              <div>
                <p>{{ t("signedAgreement.nameTrainingInstitute") }}</p>
                <p>{{ t("signedAgreement.positionSchoolTrainingInstitute") }} {{ dataForm.managerNm }} </p>
              </div>
              <div>
                <div class="add_photo mg_r10" v-if="dataForm.tripartiteSeq && dataForm.corpSignedYn === STATE_Y">
                  <img style="width: 100%; height: 100%; object-fit: cover" :src="dataForm.urlFile" />
                </div>
              </div>
            </div>
          </div>
          <div class="group_file__content">
            <div class="group_file__content__title">
              <p>{{ t("signedAgreement.student") }}</p>
            </div>
            <div class="group_file__content__file">
              <div>
                <p>{{ t("signedAgreement.nameStudent") }}</p>
                <p>{{ userInfo.sustCdNm }} {{ userInfo.userName }} </p>
              </div>
              <div v-if="dataForm.tripartiteSeq && dataForm.stdSignedYn === STATE_Y">
                <InputFileBase
                  :mode="mode"
                  :id="`semester_agreement_3`"
                  :type="'img'"
                  :preview-flag="true"
                  :show-mess="false"
                  :orgName="'STD_SIGN'"
                  :category="'img'"
                  :sectionName="'STD_SIGN'"
                  ref="signedAgreementRef"
                  :referKey="dataForm.tripartiteSeq"
                  v-model="signedAgreementFileArr"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </template>
  </TModal>
  <TModalConfirm :is-open="modalOpenConfirm" :is-show-header="true" :is-show-footer="true" :modal-id="`modalId2`" @close-modal="closeModalConfirm">
    <!-- Modal content goes here -->
    <template #title>
      <strong>{{ t("signedAgreement.titleModalConfirm") }}</strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ t("signedAgreement.contentModalConfirm2") }}</p>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button type="button" class="button btn_md btn_primary" @click="confirmAction">{{ t("common.confirm") }}</button>
      <button type="button" class="button btn_md btn_white popup_close" @click="cancelAction">{{ t("common.cancel") }}</button>
    </template>
  </TModalConfirm>
  <TModalAlert :is-open="modalOpenAlert" :is-show-header="true" :is-show-footer="true" :modal-id="`modalId1`" @close-modal="closeModalAlert">
    <!-- Modal content goes here -->
    <template #title>
      <strong>{{ t("signedAgreement.titleModalConfirm") }}</strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ t("signedAgreement.contentModalAlert2") }}</p>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button type="button" class="button btn_md btn_primary" @click="confirmActionAlert">{{ t("common.confirm") }}</button>
    </template>
  </TModalAlert>
</template>

<script setup lang="ts">
import TModal from "@/components/common/excel/TModal.vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import InputFileBase from "@/components/common/input/InputFileBase.vue";
import { ref } from "vue";
import ButtonExportPDFV1 from "@/components/common/ButtonExportPDFV1.vue";
import { SignedResponses } from "@/stores/signedAgreement/semesterAgreement.type";
import { useI18n } from "vue-i18n";
import { getUserInfo } from "@/utils/storage";
import { signedAgreementStore } from "@/stores/signedAgreement";
import { MODE_CREATE, MODE_DETAIL, SUCCSESS_STATUS } from "@/constants/screen.const";
import imageUnivSign from "@/assets/images/학교날인.jpg";
import { STATE_Y } from "@/constants/common.const";

const isOpen = ref(false);
const { t } = useI18n();
const idPlan = ref("");
const signedAgreementRef = ref(null);
const signedAgreementFileArr = ref([]);
const dataForm = ref<SignedResponses>({
  internPlanName: "",
  internYear: "",
  internTerm: "",
  cormName: "",
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
  employmentContract: "",
  insuranceParticipation: "",
  corporateSignedDate: "",
  urlFile: "",
  tripartiteSeq: "",
  stdSignedDate: "",
  corpSignedYn: "",
  stdSignedYn: "",
  univSignedYn: "",
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
const userInfo = ref(JSON.parse(getUserInfo()).data);
const listStdName = ref([{ cdId: "", cdNm: userInfo.value?.userName, upCdId: "ALL" }]);
const signStore = signedAgreementStore();
const mode = ref(MODE_CREATE);
const status = ref(false);

const closeModal = () => {
  signedAgreementFileArr.value = [];
  idPlan.value = "";
  listStdName.value = [];
  isOpen.value = false;
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
  modalOpenAlert.value = false;
  closeModal();
};
const confirmActionAlert = () => {
  closeModalAlert();
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

const uploadFile = async (id: any) => {
  const childComponent = signedAgreementRef.value;
  await childComponent?.upLoadFile(id);
};

const saveData = async () => {
  await signStore.updateAppentData({
    method: "POST",
    body: dataForm.value?.tripartiteSeq,
  });
  if (signStore.status == SUCCSESS_STATUS) {
    await uploadFile(dataForm.value?.tripartiteSeq);
    closeModalConfirm()
    modalOpenAlert.value = true
  }

};
const getDetail = async () => {
  await signStore.getListSign({
    method: "POST",
    body: idPlan.value,
  });
  if (signStore?.resultData) {   
    dataForm.value = signStore?.resultData;
    dataForm.value.internshipEnd = dataForm.value?.internshipEnd ? formatDateToKorean(dataForm.value?.internshipEnd) : "";
    dataForm.value.internshipStr = dataForm.value?.internshipStr ? formatDateToKorean(dataForm.value?.internshipStr) : "";
    dataForm.value.internshipDay = dataForm.value.internshipDay ? formatDaysToKorean(dataForm.value.internshipDay) : "";
    dataForm.value.corporateSignedDate = dataForm.value?.corporateSignedDate ? formatDateToKorean(dataForm.value?.corporateSignedDate) : "";
    if (dataForm.value?.stdSignedYn === STATE_Y) {
      status.value = true;
      mode.value = MODE_DETAIL;
    }
  }
  isOpen.value = true;
};

const openModal = (id: string) => {
  idPlan.value = id;
  getDetail();
};

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
