<script setup lang="ts">
import { participationCertificateStore } from "@/stores/participationCertificate";
import { ParticipationCertificateDTO } from "@/stores/participationCertificate/participationCertificate.type";
import { ref, defineExpose } from "vue";
import { useI18n } from "vue-i18n";
import { format } from "date-fns";

const store = participationCertificateStore();
const isOpen = ref(false);
const modalId = ref("certificateTrainingId");
const dataRes = ref<ParticipationCertificateDTO>();
const { t } = useI18n();
const internPlanSeq = ref("");
const fetchDataId = async () => {  
  await store.fetchId({
    method: "POST",
    body: {internPlanSeq:internPlanSeq.value}
  });
  dataRes.value = store.dataId?.data;
};
const closeModal = () => {
  isOpen.value = false;
};

const openModal = (internDetailId: string) => {
  isOpen.value = true;
  internPlanSeq.value = internDetailId;
  fetchDataId();
};
const formatDateToKorean = (dateStr: any) =>{
    const date = new Date(dateStr);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    
    return `${year}년 ${month}월 ${day}일`;
}
const formatBirthDateToKorean = (dateStr: any) =>{
    const date = new Date(dateStr);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    
    return `${year}.${month}.${day}`;
}
defineExpose({
  openModal,
});
</script>
<template>
  <TModal
    :class-size="`xlarge`"
    :is-open="isOpen"
    :is-show-header="true"
    :is-show-footer="false"
    :modal-id="modalId"
    @close-modal="closeModal"
  >
    <template #title>
      <strong>{{ t('participationCertificate.title') }}</strong>
    </template>
    <template #default>
      <div class="personal-info-block">
        <div class="head">
          <div class="head__title" />
          <div class="head__dowload">
            <ButtonExportPDFV1
              id="certificate"
              name-export="certificate"
            />
          </div>
        </div>
        <div id="certificate">
          <div class="body">
            <div class="overlay-text">
              <div
                class="overlay-text__outline"
                style="width: 450px;"
              >
                <div class="title">
                  {{ t('participationCertificate.subTitle') }}
                </div>
                <div class="content">
                  <div class="outline">
                    <span>{{ t('participationCertificate.studentName') }}</span>
                    <span>{{ dataRes?.studentName }}</span>
                  </div>
                  <div class="outline">
                    <span>{{ t('participationCertificate.department') }}</span>
                    <span>{{ t('participationCertificate.department1') }} {{ dataRes?.department }} {{ t('participationCertificate.department2') }}</span>
                  </div>
                  <div class="outline">
                    <span>{{ t('participationCertificate.birthDate') }}</span>
                    <span>{{dataRes?.birthDate ? formatBirthDateToKorean(format(dataRes.birthDate, "yyyy-MM-dd")) : "" }}</span>
                  </div>
                  <div class="outline">
                    <span>{{ t('participationCertificate.internshipOrganization') }}</span>
                    <span>{{ dataRes?.internshipOrganization }}</span>
                  </div>
                  <div class="outline">
                    <span>{{ t('participationCertificate.internshipTime') }}</span>
                    <span>{{ dataRes?.internshipStr ? formatBirthDateToKorean(format(dataRes?.internshipStr, "yyyy-MM-dd")) : "" }} ~ {{ dataRes?.internshipEnd ? formatBirthDateToKorean(format(dataRes?.internshipEnd, "yyyy-MM-dd")) : "" }}</span>
                  </div>
                  <div class="outline">
                    <span>{{ t('participationCertificate.internshipField') }}</span>
                    <span>{{ dataRes?.internshipField }} {{ t('participationCertificate.internshipFieldText') }}</span>
                  </div>
                </div>
              </div>
              <div class="overlay-text__certificate">
                <div class="title">
                  {{ t('participationCertificate.title2') }}
                </div>
                <div class="date">
                  {{ formatDateToKorean(dataRes?.completionDate ? format( dataRes?.completionDate, "yyyy-MM-dd") : dataRes?.completionDate) }}
                </div>
                <div class="note">
                  {{ t('participationCertificate.note') }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </template>
  </TModal>
</template>
<style lang="scss" scoped>
.personal-info-block {
  margin-bottom: 24px;
  .head {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 24px;
    @media screen and (max-width: 865px) {
      flex-direction: column-reverse;
      align-items: flex-start;
      gap: 10px;
      margin-bottom: 16px;
    }
    &__title {
      span {
        font-size: 16px;
        font-weight: 400;
        @media screen and (max-width: 865px) {
          font-size: 14px;
        }
        &:last-child {
          margin-left: 15px;
          color: var(--gray--55);
        }
      }
    }
    &__dowload {
      font-size: 14px;
      font-weight: 400;
      @media screen and (max-width: 865px) {
        font-size: 12px;
      }
    }
  }
}
.body {
  border: 1px solid var(--gray--e4);
  border-radius: 5px;
  max-height: 774px;
  height: 774px;
  display: flex;
  justify-content: center;
  align-items: center;

  margin: auto;
  background-image: url("@/assets/images/iconlogo.png");
  background-repeat: no-repeat;
  background-position: left 60% center;
  background-position-y: 35%;

  background-size: 40%;

  @media screen and (max-width: 865px) {
    height: 500px;
  }

  .overlay-text {
    font-size: 18px;
    z-index: 1;
    color: var(--gray--1d);
    font-weight: 400;
    display: flex;
    flex-direction: column;
    align-items: center;
    &__outline {
      .title {
        font-size: 32px;
        margin-bottom: 60px;
        font-weight: 500px;
        @media screen and (max-width: 865px) {
          font-size: 20px;
          margin-bottom: 20px;
          white-space: nowrap;
        }
      }
      .content {
        padding: 0;
        .outline {
          display: flex;
          margin-bottom: 24px;
          align-items: center;
          flex-direction: row;
          @media screen and (max-width: 865px) {
            margin-bottom: 12px;
          }
          span {
            font-size: 18px;
            @media screen and (max-width: 865px) {
              font-size: 14px;
            }
            &:first-child {
              width: 40%;
            }
            &:last-child {
              width: 60%;
            }
          }
        }
      }
    }
    &__certificate {
      text-align: center;
      margin-top: 60px;
      font-weight: 500;
      font-size: 18px;
      max-width: 519px;
      @media screen and (max-width: 865px) {
        margin-top: 20px;
        font-size: 14px;
        max-width: 400px;
      }
      .title {
        color: var(--gray--1d);
        font-weight: 500;
      }
      .date {
        margin: 24px;
        color: var(--primary--e5);
      }
      .note {
        color: var(--gray--55);
      }
    }
  }
}
</style>
