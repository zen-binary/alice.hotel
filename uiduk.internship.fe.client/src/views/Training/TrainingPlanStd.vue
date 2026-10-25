<template>
  <PageContainer class="page-first">
    <div class="training-header-title">{{ t("student.trainingPlan.title") }} - {{ internPlanRegForm?.internPlanName }}</div>
  </PageContainer>
  <PageContainer>
    <div class="training-container">
      <div class="training-container__table">
        <div class="row">
          <div class="item">
            <div class="label">{{ t("student.trainingPlan.internshipPlanName") }}</div>
            <div class="value">{{ internPlanRegForm?.internPlanName }}</div>
          </div>
          <div class="item">
            <div class="label">{{ t("student.trainingPlan.referInterPlanName") }}</div>
            <div class="value">{{ internPlanRegForm?.referInternPlanName }}</div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t("student.trainingPlan.executionCount") }}</div>
            <div class="value">{{ internPlanRegForm?.executionCount }}</div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t("student.trainingPlan.cormName") }}</div>
            <div class="value">{{ internPlanRegForm?.cormName }}</div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t("student.trainingPlan.commName") }}</div>
            <div class="value">{{ internPlanRegForm?.commName }}</div>
          </div>
          <div class="item">
            <div class="label">{{ t("student.trainingPlan.telNo") }}</div>
            <div class="value">{{ internPlanRegForm?.telNo }}</div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t("student.trainingPlan.internshipRegDate") }}</div>
            <div class="value" v-if="internPlanRegForm?.registerStr && internPlanRegForm?.registerEnd">
              {{ format(new Date(internPlanRegForm.registerStr), FORMAT_YYYY_MM_DD) }} ~
              {{ format(new Date(internPlanRegForm.registerEnd), FORMAT_YYYY_MM_DD) }}
            </div>
          </div>
          <div class="item">
            <div class="label">{{ t("student.trainingPlan.internshipDate") }}</div>
            <div class="value" v-if="internPlanRegForm?.internshipStr && internPlanRegForm?.internshipEnd">
              {{ format(new Date(internPlanRegForm.internshipStr), FORMAT_YYYY_MM_DD) }} ~
              {{ format(new Date(internPlanRegForm.internshipEnd), FORMAT_YYYY_MM_DD) }}
            </div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t("student.trainingPlan.quantity") }}</div>
            <div class="value">{{ internPlanRegForm?.quantity }}명</div>
          </div>
          <div class="item">
            <div class="label">{{ t("student.trainingPlan.internshipField") }}</div>
            <div class="value">{{ internPlanRegForm?.internshipField }}</div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t("student.trainingPlan.staffName") }}</div>
            <div class="value">{{ internPlanRegForm?.staffName }}</div>
          </div>
          <div class="item">
            <div class="label">{{ t("student.trainingPlan.internYear") }}</div>
            <div class="value">{{ internPlanRegForm?.internYear }}년도 {{ internPlanRegForm?.internTerm }}</div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t("student.trainingPlan.internDuration") }}</div>
            <div class="value">
              일 {{ internPlanRegForm?.duration }}시간({{ internPlanRegForm?.durationHourStr }}:{{ internPlanRegForm?.durationMinuteStr }} ~
              {{ internPlanRegForm?.durationHourEnd }}:{{ internPlanRegForm?.durationMinuteEnd }})
            </div>
          </div>
          <div class="item">
            <div class="label">{{ t("student.trainingPlan.internTime") }}</div>
            <div class="value">주 {{ internPlanRegForm?.internshipWeekday }}일({{ internPlanRegForm?.internshipDay }})</div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t("student.trainingPlan.internPayAmount") }}</div>
            <div class="value">
              {{ internPlanRegForm?.paymentAmount }}원/{{ internPlanRegForm?.paymentFrequency }} {{ internPlanRegForm?.paymentTimingType }}
              {{ internPlanRegForm?.paymentDate }}일
            </div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t("student.trainingPlan.contractYn") }}</div>
            <div class="value">{{ internPlanRegForm?.employmentContractYn === "EC_Y" ? "체결한다" : "체결하지 않는다" }}</div>
          </div>
          <div class="item">
            <div class="label">{{ t("student.trainingPlan.insuranceYn") }}</div>
            <div class="value">{{ internPlanRegForm?.insuranceParticipationYn === "IP_Y" ? "가입한다" : "가입하지 않는다" }}</div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t("student.trainingPlan.internJobSummary") }}</div>
            <div class="value quill_editor_custom" v-html="internPlanRegForm?.internJobSummary"></div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t("student.trainingPlan.requirements") }}</div>
            <div class="value quill_editor_custom" v-html="internPlanRegForm?.requirements"></div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t("student.trainingPlan.attachFile") }}</div>
            <div class="value">
              <InputFileBase
                :mode="`detail`"
                :id="`internPlanFile`"
                :type="'office'"
                :show-mess="false"
                :orgName="'INTERN_PLAN'"
                :category="'office'"
                :sectionName="'INTERN_PLAN'"
                ref="internPlanFileUpload"
                :referKey="internPlanRegForm.internPlanSeq"
                v-if="internPlanRegForm?.internPlanSeq"
              />
            </div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ $t("student.trainingPlan.plan") }}</div>
            <div class="block">
              <div class="value" v-for="(detail, index) in internPlanRegForm?.internPlanDetailResDTO" :key="index">
                <div style="display: flex; align-items: center;">
                  <span class="text-hight">{{ detail.week }}{{ t("student.trainingPlan.week") }}</span>
                {{ format(new Date(detail.startDate), FORMAT_YYYY_MM_DD) }} ~
                {{ format(new Date(detail.endDate), FORMAT_YYYY_MM_DD) }}
                </div>
                <div class="detail-plan">
                  <TextArea v-if="internPlanRegForm" v-model="detail.content" :is-detail="true"></TextArea>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="btn-action">
      <button class="button btn_round btn_md btn_primary" :disabled="internPlanRegForm?.isDisabled === STATE_Y" @click="handleClick">{{ t("common.register") }}</button>
      <button class="button btn_round btn_md btn_white" @click="back">{{ t("common.list") }}</button>
    </div>
  </PageContainer>
  <TModalConfirm :is-open="modalOpenConfirm" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
    <!-- Modal content goes here -->
    <template #title>
      <strong>개인정보 수집 및 이용에 대한 동의</strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ t("student.trainingPlan.confirmRegister") }}</p>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button type="button" class="button btn_md btn_primary" @click="confirmAction(internPlanRegForm?.internPlanSeq)">
        {{ t("common.confirm") }}
      </button>
      <button type="button" class="button btn_md btn_white popup_close" @click="cancelAction1">{{ t("common.cancel") }}</button>
    </template>
  </TModalConfirm>
  <TModalAlert :is-open="modalOpenAlert1" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal1">
    <!-- Modal content goes here -->
    <template #title>
      <strong>개인정보 수집 및 이용에 대한 동의</strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ t("student.trainingPlan.registerSuccess") }}</p>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button type="button" class="button btn_md btn_primary" @click="closeModal1">{{ t("common.confirm") }}</button>
    </template>
  </TModalAlert>
  <TModalAlert :is-open="modalOpenAlert2" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal2">
    <!-- Modal content goes here -->
    <template #title>
      <strong>개인정보 수집 및 이용에 대한 동의</strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ t("student.trainingPlan.noRegister") }}</p>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button type="button" class="button btn_md btn_primary" @click="cancelAction2">{{ t("common.confirm") }}</button>
    </template>
  </TModalAlert>
</template>
<script lang="ts" setup>
import { SCREEN } from "@/router/screen";
import { internshipPlanRegistrationStore } from "@/stores/internshipPlanRegistration";
import { internStudentStore } from "@/stores/internStudent";
import { InternshipPlanRegFormDTO } from "@/stores/internshipPlanRegistration/internshipPlan.type";
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { format } from "date-fns";
import { FORMAT_YYYY_MM_DD, SUCCSESS_STATUS } from "@/constants/screen.const";
import InputFileBase from "@/components/common/input/InputFileBase.vue";
import { useI18n } from "vue-i18n";
import TextArea from "@/components/common/input/TextArea.vue";
import { STATE_Y } from "@/constants/common.const";

const router = useRouter();
const { t } = useI18n();
const internStore = internshipPlanRegistrationStore();
const internPlanRegForm = ref<InternshipPlanRegFormDTO>();
const modalOpenConfirm = ref(false);
const modalId = ref("modalId");
const modalOpen = ref(false);
const modalOpenAlert1 = ref(false);
const modalOpenAlert2 = ref(false);
const internStudentStr = internStudentStore();

const getInternPlanRegForm = async (id: string) => {
  await internStore.fetchInternPlanRegForm({
    method: "POST",
    body: id,
    headers: {
      "Content-Type": "text/plain",
    },
  });

  internPlanRegForm.value = internStore.internPlanRegForm;
};

const handleClick = () => {
  const currentDate = format(new Date(), FORMAT_YYYY_MM_DD);
  const registerStart = format(new Date(internPlanRegForm.value?.registerStr), FORMAT_YYYY_MM_DD);
  const registerEnd = format(new Date(internPlanRegForm.value?.registerEnd), FORMAT_YYYY_MM_DD);
  if (currentDate >= registerStart && currentDate <= registerEnd) {
    modalOpenConfirm.value = true;
  } else {
    modalOpenAlert2.value = true;
  }
};

const confirmAction = async (id: string) => {
  await internStudentStr.fetchCreateInternStudent({
    method: "POST",
    body: id,
    headers: {
      "Content-Type": "text/plain",
    },
  });

  if (internStudentStr.status === 200) {
    modalOpenConfirm.value = false;
    modalOpenAlert1.value = true;
  }
};

const cancelAction1 = () => {
  modalOpen.value = false;
  modalOpenConfirm.value = false;
};

const cancelAction2 = () => {
  modalOpenAlert2.value = false;
};

const closeModal1 = () => {
  modalOpen.value = false;
  modalOpenConfirm.value = false;
  modalOpenAlert1.value = false;

  router.push({
    name: SCREEN.home.name,
  });
};

const closeModal2 = () => {
  modalOpen.value = false;
  modalOpenConfirm.value = false;
  modalOpenAlert2.value = false;
};

const back = () => {
  router.push({
    name: SCREEN.home.name,
  });
};

onMounted(() => {
  const id = window.history.state.id;

  if (id) {
    getInternPlanRegForm(id);
  } else {
    back();
  }
});
</script>
<style lang="scss">
.training-header-title {
  font-size: 40px;
  font-weight: 500;
  color: var(--gray--1d);
  @media screen and (max-width: 768px) {
    font-size: 26px;
  }
}
.training-container {
  background: var(--white);
  box-shadow: 0px 2px 4px 0px rgba(0, 0, 0, 12);
  border-radius: 10px;
  padding: 40px 32px;
  @media screen and (max-width: 768px) {
    padding: 28px 16px;
  }
  .text-hight{
    font-size: 25px;
    line-height: 35px;
    font-weight: 700;
    margin-right: 15px;
  }
  .detail-plan{
    margin-top: 10px;
  }
  &__table {
    display: flex;
    flex-direction: column;
    border-top: 2px solid var(--gray--1d);
    .row {
      display: flex;
      @media screen and (max-width: 1024px) {
        flex-direction: column;
      }
      .item {
        border-bottom: 1px solid var(--gray--e4);
        display: flex;
        flex: 1;
        @media screen and (max-width: 480px) {
          flex-direction: column;
        }
        .label {
          max-width: 260px;
          width: 100%;
          padding: 16px;
          background: var(--gray--f8);
          @media screen and (max-width: 480px) {
            max-width: 100%;
          }
        }
        .value {
          flex: 1;
          width: 100%;
          padding: 16px;
          background: var(--white);
        }
        &:last-child {
          .block {
            flex: 1;
            display: flex;
            flex-direction: column;
            .value {
              &:first-child {
                border-bottom: 1px solid var(--gray--e4);
              }
            }
          }
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
    @media screen and (max-width: 768px) {
      width: fit-content;
    }
  }
}
</style>
