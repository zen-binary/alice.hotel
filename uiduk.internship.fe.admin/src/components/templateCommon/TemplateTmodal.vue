<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div class="box dp_block">
    <div class="box_section">
      <p class="box_title_sm">Tmodal</p>
      <p class="box_title_sm required">Note size</p>
      <ul>
        <li>mini: width 450px</li>
        <li>small: width 450px</li>
        <li>medium: width 740px</li>
        <li>large: width 1164px</li>
        <li>xlarge: width 1278px</li>
      </ul>
      <!-- 2열 일때 -->
      <hr />
      <button class="button btn_md btn_primary" @click="modalOpen = true">Show modal</button>
      <TModal :is-open="modalOpen" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
        <!-- Modal content goes here -->
        <template #title>
          <strong>개인정보 수집 및 이용에 대한 동의</strong>
        </template>
        <template #default>
          <div class="box_section">
            <div class="search_box col_3">
              <ul>
                <li>
                  <p>Col3-1</p>
                  <MonthDatePicker v-model="picker1" />
                </li>
                <li>
                  <p>Col3-2</p>
                  <BaseDatePicker v-model="picker1" />
                </li>
                <li>
                  <p>Col3-3</p>
                  <SelectBoxBase :id="'selectbox'" v-model="selectBoxValue" :name="'selectbox'" :data="listSelectBox" />
                </li>

                <li>
                  <p>Col3-1</p>
                </li>
                <li>
                  <p>Col3-2</p>
                  <BaseDatePicker v-model="picker1" />
                </li>
                <li>
                  <p>Col3-3</p>
                  <BaseDatePicker v-model="picker1" />
                </li>

                <li>
                  <p>Col3-1</p>
                  <MonthDatePicker v-model="picker1" />
                </li>
                <li>
                  <p>Col3-2</p>
                  <BaseDatePicker v-model="picker1" />
                </li>
                <li>
                  <p>Col3-3</p>
                  <BaseDatePicker v-model="picker1" />
                </li>

                <li>
                  <p>Col3-1</p>
                  <MonthDatePicker v-model="picker1" />
                </li>
                <li>
                  <p>Col3-2</p>
                  <BaseDatePicker v-model="picker1" />
                </li>
                <li>
                  <p>Col3-3</p>
                  <BaseDatePicker v-model="picker1" />
                </li>

                <li>
                  <p>Select box with checkbox -- {{ selectSearch }}</p>
                  <SelectBoxBaseCheckBox v-model="selectSearch" :data="listSelectBox" />
                </li>
                <li>
                  <p>Select box with search</p>
                  <SelectBoxBaseSearch v-model="selectSearch" :data="listSelectBox" />
                </li>
                <li>
                  <p>Select box -- {{ selectBoxValue }}</p>
                  <SelectBoxBase :id="'selectbox'" v-model="selectBoxValue" :name="'selectbox'" :data="listSelectBox" />
                </li>
                <li>
                  <p>Select box disable</p>
                  <SelectBoxBase
                    :id="'select_box_disable'"
                    v-model="selectBoxValueDisable"
                    class="select_box"
                    :name="'select_box_disable'"
                    :data="listSelectBox"
                    :is-disabled="true"
                  />
                </li>
              </ul>
            </div>
          </div>
        </template>
        <template #footer>
          <!-- Footer content goes here -->
          <button type="button" class="button btn_xl btn_white popup_close" @click="confirmAction">닫기</button>
          <button type="button" class="button btn_xl btn_primary" @click="cancelAction">확인</button>
        </template>
      </TModal>

      <hr />
      <button class="button btn_md btn_primary" @click="modalOpenConfirm = true">Show modal confirm</button>
      <TModalConfirm :is-open="modalOpenConfirm" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
        <!-- Modal content goes here -->
        <template #title>
          <strong>개인정보 수집 및 이용에 대한 동의</strong>
        </template>
        <template #default>
          <div class="box_section">
            <p>Confirm content</p>
          </div>
        </template>
        <template #footer>
          <!-- Footer content goes here -->
          <button type="button" class="button btn_md btn_white popup_close" @click="confirmAction">닫기</button>
          <button type="button" class="button btn_md btn_primary" @click="cancelAction">확인</button>
        </template>
      </TModalConfirm>

      <hr />
      <button class="button btn_md btn_primary" @click="modalOpenAlert = true">Show modal alert</button>
      <TModalAlert :is-open="modalOpenAlert" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
        <!-- Modal content goes here -->
        <template #title>
          <strong>개인정보 수집 및 이용에 대한 동의</strong>
        </template>
        <template #default>
          <div class="box_section">
            <p>Alert content</p>
          </div>
        </template>
        <template #footer>
          <!-- Footer content goes here -->
          <button type="button" class="button btn_md btn_primary" @click="cancelAction">확인</button>
        </template>
      </TModalAlert>

      <br />
      <br />
      <button class="button btn_md btn_primary" @click="openConsentModal">개인정보이용동의</button>
      <ConsentModal ref="consentModal" @confirm="handleConfirm" @cancel="handleCancel" />
      <br />
      <br />
      <button class="button btn_md btn_primary" @click="openSurveyModal">설문조사</button>
      <SurveyModal ref="surveyModal" @confirm="handleSurveyConfirm" @cancel="handleSurveyCancel" />
      <br />
      <br />
      <button class="button btn_md btn_primary" @click="openComprehensiveTrainingReportModal">현장실습종합보고서</button>
      <ComprehensiveTrainingReportModal
        ref="comprehensiveTrainingReportModal"
        @confirm="handleComprehensiveTrainingReportConfirm"
        @cancel="handleComprehensiveTrainingReportCancel"
      />
      <br />
      <br />
      <button class="button btn_md btn_primary" @click="openCertificateTrainingModal">현장실습 참가증명서</button>
      <CertificateTrainingModal
        ref="certificateTrainingModal"
        @confirm="handleCertificateTrainingModalConfirm"
        @cancel="handleCertificateTrainingModalCancel"
      />
      <br />
      <br />
      <button class="button btn_md btn_primary" @click="openModalSemesterAgreement = true">표준 현장실습학기제(Co-op)협약서</button>
      <TModalSemesterAgreement v-model="openModalSemesterAgreement" modalId="TModalSemesterAgreement" />
      <br />
      <br />
      <button class="button btn_md btn_primary" @click="openTrainingWeeklyReportModal">현장실습 주간보고서</button>
      <TrainingWeeklyReportModal
        ref="trainingWeeklyReportModal"
        @confirm="handletrainingWeeklyReportModalConfirm"
        @cancel="handletrainingWeeklyReportModalCancel"
      />
      <br />
      <br />
      <button class="button btn_md btn_primary" @click="openEvaluationTableModal">평가표</button>
      <TModalEvaluationTable ref="evaluationTableModal" @confirm="handleEvaluationTableModalConfirm" @cancel="handleEvaluationTabletModalCancel" />
      <br />
      <br />
      <button class="button btn_md btn_primary" @click="openPreCheckTableModal">사전 서면 점검서</button>
      <TModalPreCheckTable ref="preCheckTableModal" @confirm="handlePreCheckTableModalConfirm" @cancel="handlePreCheckTabletModalCancel" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";

import TModal from "@/components/common/excel/TModal.vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import SelectBoxBaseCheckBox from "@/components/common/input/SelectBoxBaseCheckBox.vue";
import SelectBoxBaseSearch from "@/components/common/input/SelectBoxBaseSearch.vue";
import MonthDatePicker from "@/components/common/input/MonthDatePicker.vue";
import BaseDatePicker from "@/components/common/input/BaseDatePicker.vue";
import ConsentModal from "@/components/Modal/ConsentModal.vue";
import SurveyModal from "@/components/Modal/SurveyModal.vue";
import ComprehensiveTrainingReportModal from "@/components/Modal/ComprehensiveTrainingReport.vue";
import CertificateTrainingModal from "@/components/Modal/CertificateTrainingModal.vue";
import TModalSemesterAgreement from "@/components/Modal/TModalSemesterAgreement.vue";
import TrainingWeeklyReportModal from "@/components/Modal/TrainingWeeklyReportModal.vue";
import TModalConfirm from "@/components/common/excel/TModalConfirm.vue";
import TModalAlert from "@/components/common/excel/TModalAlert.vue";
import TModalEvaluationTable from "@/components/Modal/TModalEvaluationTable.vue";
const modalOpen = ref(false);
const modalOpenConfirm = ref(false);
const modalOpenAlert = ref(false);
const picker1 = ref("");
const modalId = ref("modalId");
// Select box
const selectBoxValueDisable = ref("11");
const selectBoxValue = ref("11");
const selectSearch = ref("12");

const listSelectBox = ref([
  { cdId: "11", cdNm: "비게", upCdId: "1" },
  { cdId: "12", cdNm: "비게시", upCdId: "1" },
  { cdId: "13", cdNm: "비게시1", upCdId: "1" },
  { cdId: "14", cdNm: "비게시2", upCdId: "1" },
  { cdId: "15", cdNm: "비게시비게시비게시2", upCdId: "1" },
]);

// Modal
const closeModal = () => {
  modalOpen.value = false;
  modalOpenConfirm.value = false;
  modalOpenAlert.value = false;
};
const confirmAction = () => {
  // Handle confirm action
  closeModal();
};
const cancelAction = () => {
  // Handle cancel action
  closeModal();
};
//개인정보이용동의
const consentModal = ref(null);

const openConsentModal = () => {
  consentModal.value?.openModal();
};
const handleConfirm = () => {
  console.log("Confirmed action received from ConsentModal");
};

const handleCancel = () => {
  console.log("Cancel action received from ConsentModal");
};

//Survey
const surveyModal = ref(null);

const openSurveyModal = () => {
  surveyModal.value?.openModal();
};
const handleSurveyConfirm = () => {
  console.log("Confirmed action received from SurveyModal");
};

const handleSurveyCancel = () => {
  console.log("Cancel action received from SurveyModal");
};
//ComprehensiveTrainingReport
const comprehensiveTrainingReportModal = ref(null);

const openComprehensiveTrainingReportModal = () => {
  comprehensiveTrainingReportModal.value?.openModal();
};
const handleComprehensiveTrainingReportConfirm = () => {
  console.log("Confirmed action received from comprehensiveTrainingReport");
};

const handleComprehensiveTrainingReportCancel = () => {
  console.log("Cancel action received from comprehensiveTrainingReport");
};
//ComprehensiveTrainingReport
const certificateTrainingModal = ref(null);

const openCertificateTrainingModal = () => {
  certificateTrainingModal.value?.openModal();
};
const handleCertificateTrainingModalConfirm = () => {
  console.log("Confirmed action received from certificateTrainingModal");
};

const handleCertificateTrainingModalCancel = () => {
  console.log("Cancel action received from certificateTrainingModal");
};

//Semester Agreement
const openModalSemesterAgreement = ref(false);
//trainingWeeklyReportModal
const trainingWeeklyReportModal = ref(null);

const openTrainingWeeklyReportModal = () => {
  trainingWeeklyReportModal.value?.openModal();
};
const handletrainingWeeklyReportModalConfirm = () => {
  console.log("Confirmed action received from trainingWeeklyReportModal");
};

const handletrainingWeeklyReportModalCancel = () => {
  console.log("Cancel action received from trainingWeeklyReportModal");
};
//EvaluationTableModal
const evaluationTableModal = ref(null);

const openEvaluationTableModal = () => {
  evaluationTableModal.value?.openModal();
};
const handleEvaluationTableModalConfirm = () => {
  console.log("Confirmed action received from trainingWeeklyReportModal");
};

const handleEvaluationTabletModalCancel = () => {
  console.log("Cancel action received from trainingWeeklyReportModal");
};
//precheck
const preCheckTableModal = ref(null);

const openPreCheckTableModal = () => {
  preCheckTableModal.value?.openModal();
};
const handlePreCheckTableModalConfirm = () => {
  console.log("Confirmed action received from handlePreCheckTableModalConfirm");
};

const handlePreCheckTabletModalCancel = () => {
  console.log("Cancel action received from handlePreCheckTabletModalCancel");
};
</script>

<style lang="scss" scoped></style>
