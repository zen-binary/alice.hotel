<template>
  <TModal :is-open="isOpen" :is-show-header="true" :is-show-footer="true" class-size="medium-large" :key="modalKey" @close-modal="closeModal">
    <!-- Modal content goes here -->
    <template #title>
      <strong>{{ t("guidanceReportCenter.title") + ` ${planNm}` }}</strong>
    </template>
    <template #default>
      <div id="guidance_report_center" class="visitor_map">
        <div class="visitor_map__title">
          <h2>{{ t("guidanceReportCenter.title2") }}</h2>
          <div class="visitor_map__title__right">
            <SelectBoxBase
              id="selectbox_stdName"
              v-model="stdValue"
              name="selectbox_stdName"
              :data="listStdName"
              :placeholder="t('commonn.selectedStd')"
            />
            <ButtonExportPDFV1 class="btn_lg" id="guidance_report_center" :disabled="statusBtnPdf" :nameExport="'guidanceReport'" />
          </div>
        </div>

        <div class="visitor_map__content">
          <h3>{{ t("guidanceReportCenter.studentInfo") }}</h3>
          <div class="visitor_map__content__table">
            <div class="table_row">
              <!-- 실습생 소속 -->
              <div class="table_col">
                <div>
                  <p>{{ t("guidanceReportCenter.deptName") }}</p>
                </div>
                <div class="table_col__text">
                  <p>{{ dataForm?.deptStdNm }}</p>
                </div>
              </div>

              <!-- 실습기관명 -->
              <div class="table_col">
                <div>
                  <p>{{ t("guidanceReportCenter.corporateName") }}</p>
                </div>
                <div class="table_col__text">
                  <p>{{ dataForm?.companyNm }}</p>
                </div>
              </div>
            </div>

            <div class="table_row">
              <!-- 실습생 성명 -->
              <div class="table_col">
                <div>
                  <p>{{ t("guidanceReportCenter.studentName") }}</p>
                </div>
                <div class="table_col__text">
                  <p>{{ dataForm?.stdNm }}</p>
                </div>
              </div>

              <!-- 방문일자 -->
              <div class="table_col">
                <div>
                  <p>{{ t("guidanceReportCenter.internDate") }}</p>
                </div>
                <div>
                  <BaseDatePicker v-model="dataForm.internDate" />
                </div>
              </div>
            </div>

            <div class="table_row">
              <!-- 실습생 근무부서 -->
              <div class="table_col">
                <div>
                  <p>{{ t("guidanceReportCenter.stdPosition") }}</p>
                </div>
                <div>
                  <InputBase id="'internWorkDept'" v-model="dataForm.internWorkDept" />
                </div>
              </div>

              <!-- 현장실습 담당자(면담자) -->
              <div class="table_col">
                <div>
                  <p>{{ t("guidanceReportCenter.internManager") }}</p>
                </div>
                <div>
                  <InputBase id="'internManager'" v-model="dataForm.internManager" />
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="visitor_map__content">
          <h3>{{ t("guidanceReportCenter.actualSituation") }}</h3>
          <TextArea v-model="dataForm.internSituation" maxlength="300" :placeholder="t('guidanceReportCenter.placeholderActualSituation')" />
        </div>

        <div class="visitor_map__content">
          <h3>{{ t("guidanceReportCenter.guidanceContent") }}</h3>
          <TextArea v-model="dataForm.internContent" maxlength="300" :placeholder="t('guidanceReportCenter.placeholderGuidanceContent')" />
        </div>

        <div class="visitor_map__content">
          <h3>{{ t("guidanceReportCenter.corpFeedback") }}</h3>
          <TextArea v-model="dataForm.internCompanyFeedback" maxlength="300" :placeholder="t('guidanceReportCenter.placeholderCorpFeedback')" />
        </div>

        <div class="visitor_map__content">
          <h3>{{ t("guidanceReportCenter.resultFeedback") }}</h3>
          <TextArea v-model="dataForm.internProfFeedback" maxlength="300" :placeholder="t('guidanceReportCenter.placeholderResultFeedback')" />
        </div>

        <div class="visitor_map__content">
          <p>{{ t("guidanceReportCenter.profInfo") }}</p>
          <div class="group_file__content">
            <p>
              {{ new Date().getFullYear() }} {{ t("guidanceReportCenter.year") }} {{ new Date().getMonth() + 1 }}
              {{ t("guidanceReportCenter.month") }} {{ new Date().getDate() }} {{ t("guidanceReportCenter.day") }}
            </p>
            <p>{{ dataForm.deptProfNm }}</p>
            <p>{{ dataForm.profNm }}</p>
            <div>
              <InputFileBase
                v-if="stdValue !== ''"
                :key="key"
                :id="'upload_file_guidance'"
                ref="fileSignProfGuidance"
                :refer-key="dataForm.id"
                :mode="`edit`"
                :name="'upload_file_guidance'"
                :type="fileType"
                :org-name="'GUIDANCE_SIGN_PROF'"
                :category="'img'"
                :section-name="'GUIDANCE_SIGN_PROF'"
                :preview-flag="true"
                :show-mess="true"
              />
            </div>
          </div>

          <p>{{ t("guidanceReportCenter.recipientInfo") }}</p>
        </div>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_md btn_primary" :disabled="statusBtnConfirm" @click="handleActionSaveClick">
        {{ t("common.save") }}
      </button>
      <button type="button" class="button btn_md btn_white popup_close" @click="closeModal">{{ t("common.close") }}</button>
    </template>
  </TModal>

  <TModalConfirm :is-open="modalOpenConfirm" :is-show-footer="true" :is-show-header="true" @close-modal="closeModalConfirm">
    <!-- Modal content goes here -->
    <template #title>
      <strong></strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ t("guidanceReportCenter.confirmSave") }}</p>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button class="button btn_md btn_primary" type="button" @click="confirmAction">{{ t("common.confirm") }}</button>
      <button class="button btn_md btn_white popup_close" type="button" @click="closeModalConfirm">{{ t("common.cancel") }}</button>
    </template>
  </TModalConfirm>

  <TModalAlert :is-open="modalOpenAlert" :is-show-footer="true" :is-show-header="true" @close-modal="closeModal">
    <!-- Modal content goes here -->
    <template #title>
      <strong></strong>
    </template>
    <template #default>
      <div class="box_section">
        <p>{{ t("guidanceReportCenter.saveSuccess") }}</p>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button class="button btn_md btn_primary" type="button" @click="closeModalAlert">{{ t("common.confirm") }}</button>
    </template>
  </TModalAlert>
</template>

<script setup lang="ts">
import TModal from "@/components/common/excel/TModal.vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import TextArea from "@/components/common/input/TextArea.vue";
import BaseDatePicker from "@/components/common/input/BaseDatePicker.vue";
import InputBase from "@/components/common/input/InputBase.vue";
import InputFileBase from "@/components/common/input/InputFileBase.vue";
import { watch, ref, defineEmits, onBeforeMount } from "vue";
import { guidanceReportCenterStore } from "@/stores/guidanceReportCenter/useGuidanceReportCenter";
import { GuidanceReportCenterDetailRes, GuidanceReportCenterRequest } from "@/stores/guidanceReportCenter/guidanceReportCenter.type";
import { FILE_TYPE_IMAGE, SUCCSESS_STATUS } from "@/constants/screen.const";
import { useI18n } from "vue-i18n";
import ButtonExportPDFV1 from "@/components/common/ButtonExportPDFV1.vue";
import { CodeMngModel } from "@/stores/common/codeMng/codeMng.type";

const { t } = useI18n();
const store = guidanceReportCenterStore();

const modalOpenConfirm = ref(false);
const modalOpenAlert = ref(false);
const idPlan = ref("");
const planNm = ref("");
const stdValue = ref("");
const statusBtnConfirm = ref(true);
const statusBtnPdf = ref(true);

const listStdName = ref<CodeMngModel[]>([{ cdId: "", cdNm: t("common.selectedStd"), upCdId: "ALL" }]);

const props = defineProps<{
  isOpen: boolean;
  internPlan: object;
}>();
const emit = defineEmits(["closeModal"]);

const dataForm = ref<GuidanceReportCenterDetailRes>({
  id: "",
  internPlanNm: "",
  deptStdNm: "",
  companyNm: "",
  internDate: "",
  stdNm: "",
  internWorkDept: "",
  internManager: "",
  internSituation: "",
  internContent: "",
  internCompanyFeedback: "",
  internProfFeedback: "",
  deptProfNm: "",
  profNm: "",
  isUpdate: "N",
});
const dataRequest = ref<GuidanceReportCenterRequest>({
  guidanceReportSeq: "",
  internPlanSeq: "",
  internStdSeq: "",
  workingDepartment: "",
  personInCharge: "",
  actualSituation: "",
  guidanceContent: "",
  corporateFeedback: "",
  resultsAndFeedback: "",
  internDate: "",
});

const key = ref(1);
const modalKey = ref(1);
const fileType = FILE_TYPE_IMAGE;
const fileSignProfGuidance = ref(null);
onBeforeMount(async () => {
  idPlan.value = props.internPlan.internPlanSeq;
  planNm.value = props.internPlan.internPlanNm;
  getListStdName();
});

const closeModal = () => {
  emit("closeModal");
};

const getListStdName = async () => {
  await store.getlistStd({
    method: "POST",
    body: idPlan.value,
  });

  listStdName.value = [
    ...listStdName.value,
    ...store.listStd.data.map((item) => ({
      cdId: item.id,
      cdNm: item.name,
    })),
  ];
};

const getDetail = async () => {
  await store.fetchDetail({
    method: "POST",
    body: { internPlanId: idPlan.value, internStdId: stdValue.value },
  });
  if (!store.list?.data) {
    dataForm.value = {
      id: "",
      internPlanNm: "",
      deptStdNm: "",
      companyNm: "",
      internDate: "",
      stdNm: "",
      internWorkDept: "",
      internManager: "",
      internSituation: "",
      internContent: "",
      internCompanyFeedback: "",
      internProfFeedback: "",
      deptProfNm: "",
      profNm: "",
      isUpdate: "N",
    } as GuidanceReportCenterDetailRes;
  } else {
    dataForm.value = store.list?.data;
  }
  statusBtnPdf.value = !(dataForm.value.isUpdate === "Y");
  key.value++;
};

//action handle click
const closeModalConfirm = () => {
  modalOpenConfirm.value = false;
};

const closeModalAlert = () => {
  stdValue.value = "";
  modalKey.value++;
  modalOpenAlert.value = false;
};

const handleActionSaveClick = () => {
  modalOpenConfirm.value = true;
};

const checkData = async (id: any) => {
  const childComponent = fileSignProfGuidance.value;
  await childComponent.upLoadFile(id);
};

//interact with db
const confirmAction = async () => {
  dataRequest.value.guidanceReportSeq = dataForm.value.id ? dataForm.value.id : "";
  dataRequest.value.internPlanSeq = idPlan.value;
  dataRequest.value.internStdSeq = stdValue.value;
  dataRequest.value.workingDepartment = dataForm.value.internWorkDept;
  dataRequest.value.personInCharge = dataForm.value.internManager;
  dataRequest.value.actualSituation = dataForm.value.internSituation;
  dataRequest.value.guidanceContent = dataForm.value.internContent;
  dataRequest.value.corporateFeedback = dataForm.value.internCompanyFeedback;
  dataRequest.value.resultsAndFeedback = dataForm.value.internProfFeedback;
  dataRequest.value.internDate = dataForm.value.internDate;

  if (dataForm.value.isUpdate == "Y") {
    await store
      .updateForm({
        method: "PUT",
        body: dataRequest.value,
      })
      .then(() => {
        if (store.status?.value === SUCCSESS_STATUS) {
          checkData(store.dataRes.data);
        }
      });
  } else {
    await store
      .createForm({
        method: "POST",
        body: dataRequest.value,
      })
      .then(() => {
        if (store.status?.value === SUCCSESS_STATUS) {
          dataForm.value.id = store.dataRes.data;
          dataForm.value.isUpdate = "Y";
          statusBtnPdf.value = !(dataForm.value.isUpdate === "Y");
          checkData(store.dataRes.data);
        }
      });
  }
  getDetail();
  modalOpenConfirm.value = false;
  modalOpenAlert.value = true;
};

watch(
  () => stdValue.value,
  async (newValue) => {
    if (newValue !== "") {
      statusBtnConfirm.value = false;
      await getDetail();
    } else if (newValue == "") {
      dataForm.value = {
        id: "",
        internPlanNm: "",
        deptStdNm: "",
        companyNm: "",
        internDate: "",
        stdNm: "",
        internWorkDept: "",
        internManager: "",
        internSituation: "",
        internContent: "",
        internCompanyFeedback: "",
        internProfFeedback: "",
        deptProfNm: "",
        profNm: "",
        isUpdate: "N",
      } as GuidanceReportCenterDetailRes;
      statusBtnConfirm.value = true;
      statusBtnPdf.value = true;
    }
  },
);
</script>

<style scoped lang="scss">
.visitor_map {
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
    margin-top: 20px;
    margin-bottom: 24px;

    & > h3 {
      font-size: 16px;
      font-weight: 500;
      line-height: 24px;
      position: relative;
      padding-left: 12px;
      margin-bottom: 8px;

      &:before {
        content: "";
        position: absolute;
        top: 50%;
        left: 0;
        transform: translateY(-50%);
        width: 4px;
        height: 4px;
        border-radius: 50%;
        background: var(--Primary-50_Primary, rgba(0, 152, 229, 1));
      }
    }

    &__table {
      border-top: 1px solid #2d2d2d;

      .table_row {
        display: flex;

        .table_col {
          flex: 1;
          display: flex;
          align-items: center;
          border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

          & > div:first-child {
            width: 200px;
            background: var(--Gray-scale-5, rgba(248, 248, 248, 1));
            padding: 17.5px 16px;

            & p {
              font-size: 14px;
              font-weight: 500;
              line-height: 21px;
              color: var(--Gray-scale-90, rgba(29, 29, 29, 1));
            }
          }

          & > div:last-child {
            word-break: break-word;
            padding: 8px 16px;
            flex: 1;

            & p {
              font-size: 14px;
              font-weight: 400;
              line-height: 21px;
              color: var(--Gray-scale-80, rgba(45, 45, 45, 1));
            }
          }
        }
      }
    }

    &:nth-last-child(1) {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      gap: 24px;
      white-space: normal;

      & > p:first-child {
        font-size: 18px;
        font-weight: 500;
        line-height: 27px;
        color: var(--Gray-scale-80, rgba(45, 45, 45, 1));
        text-align: center;
      }

      & > p:last-child {
        font-size: 16px;
        font-weight: 500;
        line-height: 24px;
        color: var(--Gray-scale-70, rgba(85, 85, 85, 1));
        text-align: center;
      }
    }

    .group_file__content {
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
      align-items: center;
      gap: 24px;

      & p {
        font-size: 16px;
        font-weight: 500;
        line-height: 24px;
        color: var(--Gray-scale-70, rgba(85, 85, 85, 1));
      }

      .add_photo {
        background: red;
      }
    }
  }
}

@media screen and (max-width: 768px) {
  .visitor_map {
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
      .table_row {
        flex-direction: column;
      }
    }
  }
}

@media screen and (max-width: 480px) {
  .visitor_map {
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
      &__table {
        .table_row {
          border-bottom: none;

          .table_col {
            flex-direction: column;
            align-items: flex-start;
            border-top: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

            & > div {
              width: 100% !important;
            }

            & > div.table_col__text {
              padding: 18px 16px;

              & p {
                font-size: 14px;
                font-weight: 400;
                line-height: 20px;
              }
            }
          }
        }
      }
    }
  }
}
</style>
