<template>
  <PageContainer id="block_intern_plan">
    <h1 v-once>
      {{ t("internPlanCenterMng.detail.pageTitle") }}
    </h1>
    <div class="box dp_block" id="block_intern_plan">
      <div class="box_section">
        <div class="btn_group btn_end mg_b20"><ButtonExportPDFV1 id="block_intern_plan" :name-export="nameExportPDF" /></div>
        <div class="tbl tbl_row tbl_scroll scrollx_tbl_xs">
          <table>
            <colgroup>
              <col style="width: 15%" />
              <col style="width: auto" />
            </colgroup>
            <tbody>
              <tr>
                <th scope="row" class="required">{{ t("internPlanCenterMng.detail.title1") }}</th>
                <td>
                  <div class="wd_p50">
                    <InputBase
                      :placeholder="t('internPlanCenterMng.detail.placeholder1')"
                      id="internNew"
                      required
                      v-model="internPlanRequestModel.internPlanNm"
                      :is-detail="isDetail"
                    />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row">{{ t("internPlanCenterMng.detail.title2") }}</th>
                <td>
                  <div class="wd_p50">
                    <InputBase :is-detail="isDetail" id="referInternPlanNm" :disabled="true" v-model="internPlanRequestModel.referInternPlanNm" />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row">{{ t("internPlanCenterMng.detail.title3") }}</th>
                <td>{{ internPlanRequestModel.executionCount ? internPlanRequestModel.executionCount + t("internPlanningMng.times") : "-" }}</td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanCenterMng.detail.title4") }}</th>
                <td>
                  <div class="wd_p50">
                    <InputBase :is-detail="isDetail" id="cormMemberInfo" :disabled="true" v-model="internPlanRequestModel.cormNameKr" />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanCenterMng.detail.title5") }}</th>
                <td>
                  <div class="wd_p50">
                    <InputBase :is-detail="isDetail" id="commNm" :disabled="true" v-model="internPlanRequestModel.commNm" />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanCenterMng.detail.title6") }}</th>
                <td>
                  <span>{{ phoneNumberSelected ? phoneNumberSelected : "-" }}</span>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanCenterMng.detail.title7") }}</th>
                <td>
                  <div class="search_flex wd_p30" style="position: relative">
                    <BaseDatePicker :is-detail="isDetail" :id="'registerStr'" v-model="internPlanRequestModel.registerStr" required />
                    <span>~</span>
                    <BaseDatePicker :is-detail="isDetail" :id="'registerEnd'" v-model="internPlanRequestModel.registerEnd" required />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanCenterMng.detail.title8") }}</th>
                <td>
                  <div class="search_flex wd_p30" style="position: relative">
                    <BaseDatePicker :id="'internshipStr'" v-model="internPlanRequestModel.internshipStr" :is-detail="isDetail" required />
                    <span>~</span>
                    <BaseDatePicker :id="'internshipEnd'" v-model="internPlanRequestModel.internshipEnd" :is-detail="isDetail" required />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanCenterMng.detail.title9") }}</th>
                <td>
                  <div class="wd_p7 d-flex" style="align-items: center; gap: 10px">
                    <InputBase
                      :is-detail="isDetail"
                      :isNumber="true"
                      id="quantityPeople"
                      v-model="internPlanRequestModel.internRecruitmentQuantity"
                      required
                    />
                    {{ t("internPlanCenterMng.detail.placeholder4") }}
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanCenterMng.detail.title10") }}</th>
                <td>
                  <div class="d-flex wd_p25" style="align-items: center; gap: 10px">
                    <div style="flex: 0 0 80%">
                      <InputBase :is-detail="isDetail" id="internshipField" v-model="internPlanRequestModel.internshipField" required />
                    </div>
                    <div style="flex: 0 0 20%">
                      {{ t("internPlanCenterMng.detail.placeholder5") }}
                    </div>
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanCenterMng.detail.title11") }}</th>
                <td>
                  <div class="wd_p50">
                    <InputBase :is-detail="isDetail" id="staffNm" v-model="internPlanRequestModel.staffNm" required />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanCenterMng.detail.title12") }}</th>
                <td>
                  <div :class="!isDetail ? 'search_flex wd_p30' : 'dp_flex'" style="gap: 10px; white-space: nowrap">
                    <SelectBoxBase
                      :is-detail="isDetail"
                      :id="'listYear'"
                      :name="'listYear'"
                      required
                      :data="listYearCd"
                      v-model="internPlanRequestModel.internYear"
                    />
                    <SelectBoxBase
                      :is-detail="isDetail"
                      :id="'listTerm'"
                      :name="'listTerm'"
                      required
                      :data="listTermCd"
                      v-model="internPlanRequestModel.internTerm"
                    />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanCenterMng.detail.title13") }}</th>
                <td>
                  <div class="search_flex wd_p50" style="gap: 10px">
                    <span>{{ t("internPlanCenterMng.detail.placeholder9") }}</span>
                    <InputBase
                      :minValue="1"
                      :maxValue="24"
                      v-model="internPlanRequestModel.duration"
                      :isMinMaxValue="true"
                      :isNumber="true"
                      id="dayNumber"
                      :is-detail="isDetail"
                      required
                    />
                    <div style="min-width: 30px">{{ t("internPlanCenterMng.detail.placeholder10") }}</div>
                    <SelectBoxBase
                      required
                      v-model="internPlanRequestModel.durationHourStr"
                      :id="'listHoursStr'"
                      :name="'listHoursStr'"
                      :data="listHours"
                      :is-detail="isDetail"
                    />:
                    <SelectBoxBase
                      v-model="internPlanRequestModel.durationMinuteStr"
                      :id="'listMinuteStr'"
                      :name="'listMinuteStr'"
                      :is-detail="isDetail"
                      :data="listMinutes"
                      required
                    />
                    <span>~</span>
                    <SelectBoxBase
                      required
                      v-model="internPlanRequestModel.durationHourEnd"
                      :id="'listHoursEnd'"
                      :name="'listHoursEnd'"
                      :is-detail="isDetail"
                      :data="listHours"
                    />:
                    <SelectBoxBase
                      required
                      v-model="internPlanRequestModel.durationMinuteEnd"
                      :id="'listMinuteEnd'"
                      :is-detail="isDetail"
                      :name="'listMinuteEnd'"
                      :data="listMinutes"
                    />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanCenterMng.detail.title14") }}</th>
                <td>
                  <div class="search_flex wd_p40" style="gap: 10px">
                    <span>{{ t("internPlanCenterMng.detail.placeholder11") }}</span>
                    <InputBase
                      v-model="internPlanRequestModel.internshipWeekday"
                      :minValue="1"
                      :maxValue="7"
                      :is-detail="isDetail"
                      :isMinMaxValue="true"
                      :isNumber="true"
                      id="weekNumber"
                      required
                    />
                    <div style="min-width: 30px">{{ t("internPlanCenterMng.detail.placeholder12") }}</div>
                    <ListCheckBoxBase
                      :listData="listDayOfWeek"
                      v-if="modeScreen === MODE_CREATE || (listDayOfWeek.length > 0 && internPlanRequestModel.internshipDay)"
                      v-model="internPlanRequestModel.internshipDay"
                      id="listDayOfWeek"
                      :isRequire="true"
                      :is-disable="isDetail"
                      :requireId="'listDayOfWeekRequired'"
                      :useArray="false"
                    />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanCenterMng.detail.title15") }}</th>
                <td>
                  <div class="search_flex">
                    <div class="search_flex wd_p30" style="gap: 10px">
                      <InputBase
                        v-model="internPlanRequestModel.paymentAmount"
                        :minValue="1"
                        :maxValue="1000000000"
                        :isMinMaxValue="true"
                        :isNumber="true"
                        :is-detail="isDetail"
                        id="paymentAmount"
                        required
                      />
                      <div style="min-width: 25px">{{ t("internPlanCenterMng.detail.placeholder14") }}</div>/
                      <SelectBoxBase
                        v-model="internPlanRequestModel.paymentFrequency"
                        required
                        :is-detail="isDetail"
                        :id="'listPmFre'"
                        :name="'listPmFre'"
                        :data="listPmFre"
                      />
                    </div>
                    <div class="select_checkbox mg_l20">
                      <RadiobuttonBase
                        v-for="item in listPmTime"
                        :value="item.cdId"
                        :id="'listPmTime' + item.cdId"
                        :isDisable="isDetail"
                        :key="'listPmTime' + item.cdId"
                        :label="item.cdNm"
                        v-model="internPlanRequestModel.paymentTimingType"
                        :checked="internPlanRequestModel.paymentTimingType === item.cdId"
                      />
                    </div>
                    <div class="search_flex">
                      <InputBase
                        v-model="internPlanRequestModel.paymentDate"
                        class="wd_50"
                        :minValue="1"
                        :maxValue="31"
                        :is-detail="isDetail"
                        :isMinMaxValue="true"
                        :isNumber="true"
                        id="paymentDate"
                        required
                      />
                      <span>{{ t("internPlanCenterMng.detail.placeholder13") }}</span>
                    </div>
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanCenterMng.detail.title16") }}</th>
                <td>
                  <div class="select_checkbox">
                    <RadiobuttonBase
                      v-for="item in listEcYn"
                      :value="item.cdId"
                      :id="'listEcYn' + item.cdId"
                      :isDisable="isDetail"
                      :key="'listEcYn' + item.cdId"
                      :label="item.cdNm"
                      v-model="internPlanRequestModel.employmentContractYn"
                      :checked="internPlanRequestModel.employmentContractYn === item.cdId"
                    />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row">
                  <p class="required font_weight_p">{{ t("internPlanCenterMng.detail.title17") }}</p>
                  <p class="font_weight_p">{{ t("internPlanCenterMng.detail.title18") }}</p>
                </th>
                <td>
                  <div class="select_checkbox">
                    <RadiobuttonBase
                      v-for="item in listIpYn"
                      :value="item.cdId"
                      :isDisable="isDetail"
                      :id="'listIpYn' + item.cdId"
                      :key="'listIpYn' + item.cdId"
                      :label="item.cdNm"
                      v-model="internPlanRequestModel.insuranceParticipationYn"
                      :checked="internPlanRequestModel.insuranceParticipationYn === item.cdId"
                    />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanCenterMng.detail.title19") }}</th>
                <td>
                  <div v-if="isDetail" class="quill_editor_custom" v-html="internPlanRequestModel.internJobSummary"></div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanCenterMng.detail.title20") }}</th>
                <td>
                  <div v-if="isDetail" class="quill_editor_custom" v-html="internPlanRequestModel.requirements"></div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanCenterMng.detail.title21") }}</th>
                <td>
                  <InputFileBase
                    :mode="modeScreen"
                    :id="`internPlanFile`"
                    :type="'office'"
                    :multiple="true"
                    :show-mess="false"
                    :orgName="'INTERN_PLAN'"
                    :category="'office'"
                    :max-file="3"
                    :sectionName="'INTERN_PLAN'"
                    ref="internPlanFileUpload"
                    :referKey="internPlanSeq"
                    :max-size="20"
                    v-model="fileArray"
                  />
                  <span class="font_red">
                    {{ t("internPlanCenterMng.detail.title25") }}
                    <br />
                    {{ t("internPlanCenterMng.detail.title26") }}
                  </span>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanningMng.titleThumbnail") }}</th>
                <td>
                  <InputFileBase
                    :mode="MODE_DETAIL"
                    :id="`internPlanCenterThumbnail`"
                    :type="'img'"
                    :multiple="false"
                    :previewFlag="true"
                    :show-mess="false"
                    :orgName="'INTERN_PLAN_THUMBNAIL'"
                    :category="'img'"
                    :max-file="1"
                    :sectionName="'INTERN_PLAN_THUMBNAIL'"
                    ref="internPlanCenterThumbnail"
                    :referKey="internPlanSeq"
                    :max-size="5"
                  />
                  <div class="font_red mg_t20">
                    {{ t("internPlanningMng.textTitleThumbnail") }}
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row">
                  <p class="required font_weight_p">{{ t("internPlanCenterMng.detail.title22") }}</p>
                  <p class="font_red">{{ t("internPlanCenterMng.detail.title23") }}</p>
                </th>
                <td>
                  <div
                    class="mg_b20 mg_t20"
                    v-if="internPlanRequestModel.listInternPlanDetail"
                    v-for="(internPlanDetail, index) in internPlanRequestModel.listInternPlanDetail.filter((detail) => detail.delYn === statusN)"
                    :key="index"
                  >
                    <div class="search_flex wd_p50" style="position: relative">
                      <h2 style="min-width: 100px; font-weight: 500;">{{ internPlanDetail.week }}{{ t("internPlanCenterMng.detail.week") }}</h2>
                      <BaseDatePicker v-model="internPlanDetail.startDate" :id="'startDatePlanDetail' + index" required :is-detail="isDetail" />
                      <span>~</span>
                      <BaseDatePicker v-model="internPlanDetail.endDate" :id="'endDatePlanDetail' + index" :is-detail="isDetail" required />
                    </div>
                    <div class="mg_t20 wd_p50">
                      <TextArea
                        :placeholder="t('internPlanCenterMng.detail.placeholder16')"
                        :maxWords="2000"
                        :id="'contentPlanDetail' + index"
                        v-model="internPlanDetail.content"
                        :is-detail="isDetail"
                        required
                      />
                    </div>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="btn_group btn_end mg_t30">
          <button type="button" class="btn_md btn_white" @click="handleCancel" v-if="isDetail">{{ t("common.list") }}</button>
        </div>
      </div>
    </div>
  </PageContainer>
</template>
<script lang="ts" setup>
import { ref, onBeforeMount, watch } from "vue";
import { commonStore } from "@/stores/common";
import { useRouter } from "vue-router";
import { codeMngStore } from "@/stores/common/codeMng";
import InputBase from "@/components/common/input/InputBase.vue";
import { CodeMngModel } from "@/stores/common/codeMng/codeMng.type";
import BaseDatePicker from "@/components/common/input/BaseDatePicker.vue";
import { SCREEN } from "@/router/screen";
import ListCheckBoxBase from "@/components/common/input/ListCheckBoxBase.vue";
import {
  MODE_DETAIL,
  STATUS_NO,
  UP_CD_DAY_OF_WEEK,
  UP_CD_EC_YN,
  UP_CD_IP_YN,
  UP_CD_NCR_T11,
  UP_CD_PM_FRE_00,
  UP_CD_PM_TIME_00,
} from "@/constants/common.const";
import { InternPlanResponseModel } from "@/stores/internPlanCenterMng/internPlanCenterMng.type";
import { internPlanCenterMngStore } from "@/stores/internPlanCenterMng/useInternPlanCenterMng";
import ButtonExportPDFV1 from "@/components/common/ButtonExportPDFV1.vue";

const store = commonStore();
const router = useRouter();
const route = useRoute();
const { t } = useI18n();
const codeMng = codeMngStore();
const internPlanMng = internPlanCenterMngStore();
const modeDetail = ref(MODE_DETAIL);
const statusN = ref(STATUS_NO);
const modeScreen = ref("");

const listOperatorManager = ref<Array<any>>([]);
const listYearCd = ref<Array<CodeMngModel>>([{ cdId: "", cdNm: t("internPlanCenterMng.detail.placeholder7"), upCdId: "" }]);
const listTermCd = ref<Array<CodeMngModel>>([{ cdId: "", cdNm: t("internPlanCenterMng.detail.placeholder8"), upCdId: "" }]);
const listHours = ref<Array<CodeMngModel>>([]);
const listMinutes = ref<Array<CodeMngModel>>([]);
const listDayOfWeek = ref<Array<CodeMngModel>>([]);
const listPmFre = ref<Array<CodeMngModel>>([{ cdId: "", cdNm: t("internPlanCenterMng.detail.placeholder15"), upCdId: "" }]);
const listPmTime = ref<Array<CodeMngModel>>([]);
const listEcYn = ref<Array<CodeMngModel>>([]);
const listIpYn = ref<Array<CodeMngModel>>([]);
const phoneNumberSelected = ref("");
const editorInternJob = ref(null);
const editorRequirement = ref(null);
const isDetail = ref(false);
const internPlanSeq = ref("");
const internPlanFileUpload = ref(null);
const fileArray = ref([]);
const nameExportPDF = "INTERN_PLAN_CENTER";

const internPlanRequestModel = ref({} as InternPlanResponseModel);

const formatDate = (date) => {
  return new Date(date);
};

watch(
  () => internPlanRequestModel.value.registerStr,
  (newValue) => {
    if (!internPlanRequestModel.value.registerEnd) {
      internPlanRequestModel.value.registerStr = newValue;
    } else {
      const dStart = formatDate(newValue);
      const dEnd = formatDate(internPlanRequestModel.value.registerEnd);
      if (dStart > dEnd) {
        nextTick(() => {
          internPlanRequestModel.value.registerStr = internPlanRequestModel.value.registerEnd;
        });
      }
    }
  },
);

watch(
  () => internPlanRequestModel.value.registerEnd,
  (newValue) => {
    if (!internPlanRequestModel.value.registerStr) {
      internPlanRequestModel.value.registerEnd = newValue;
    } else {
      const dStart = formatDate(internPlanRequestModel.value.registerStr);
      const dEnd = formatDate(newValue);
      if (dStart > dEnd) {
        nextTick(() => {
          internPlanRequestModel.value.registerEnd = internPlanRequestModel.value.registerStr;
        });
      }
    }
  },
);

watch(
  () => internPlanRequestModel.value.internshipStr,
  (newValue) => {
    if (!internPlanRequestModel.value.internshipEnd) {
      internPlanRequestModel.value.internshipStr = newValue;
    } else {
      const dStart = formatDate(newValue);
      const dEnd = formatDate(internPlanRequestModel.value.internshipEnd);
      if (dStart > dEnd) {
        nextTick(() => {
          internPlanRequestModel.value.internshipStr = internPlanRequestModel.value.internshipEnd;
        });
      }
    }
  },
);

watch(
  () => internPlanRequestModel.value.internshipEnd,
  (newValue) => {
    if (!internPlanRequestModel.value.internshipStr) {
      internPlanRequestModel.value.internshipEnd = newValue;
    } else {
      const dStart = formatDate(internPlanRequestModel.value.internshipStr);
      const dEnd = formatDate(newValue);
      if (dStart > dEnd) {
        nextTick(() => {
          internPlanRequestModel.value.internshipEnd = internPlanRequestModel.value.internshipStr;
        });
      }
    }
  },
);

async function genDataCd() {
  for (let i = 0; i < 24; i++) {
    const hour = i.toString().padStart(2, "0");
    listHours.value.push({ cdId: hour, cdNm: hour, upCdId: "hours" });
  }

  for (let i = 0; i < 60; i++) {
    const minute = i.toString().padStart(2, "0");
    listMinutes.value.push({ cdId: minute, cdNm: minute, upCdId: "minutes" });
  }

  for (let year = 2024; year <= 2034; year++) {
    listYearCd.value.push({
      cdId: year.toString(),
      cdNm: year.toString(),
      upCdId: "",
    });
  }
  getDetailInternPlan();
}

onBeforeMount(() => {
  modeScreen.value = route.params.mode;
  internPlanSeq.value = window.history.state.internPlanSeq;
  isDetail.value = modeScreen.value === modeDetail.value;
  genDataCd();
  getListCodeMng();
});

async function getDetailInternPlan() {
  await internPlanMng.detailInternPlanMng({
    method: "POST",
    body: internPlanSeq.value,
  });
  internPlanRequestModel.value = internPlanMng.detailInternPlan.data;
  if (internPlanSeq.value && editorInternJob.value) {
    editorInternJob.value.setHTML(internPlanRequestModel.value.internJobSummary);
  }
  if (internPlanSeq.value && editorRequirement.value) {
    editorRequirement.value.setHTML(internPlanRequestModel.value.requirements);
  }
  phoneNumberSelected.value = internPlanRequestModel.value.mobilePhone;
}

watch(
  () => internPlanRequestModel.value.commSeq,
  (newValue, oldValue) => {
    if (newValue) {
      listOperatorManager.value.forEach((item) => {
        if (newValue === item.commSeq) {
          phoneNumberSelected.value = item.mobilePhone;
        }
      });
    } else {
      phoneNumberSelected.value = "";
    }
  },
);
async function getListCodeMng() {
  await codeMng.getCodeInfo({
    method: "POST",
    body: {
      upCdIdList: [UP_CD_DAY_OF_WEEK, UP_CD_PM_FRE_00, UP_CD_PM_TIME_00, UP_CD_EC_YN, UP_CD_IP_YN, UP_CD_NCR_T11],
    },
  });
  codeMng.codeInfo.data.forEach((item) => {
    if (item.upCdId === UP_CD_DAY_OF_WEEK) {
      listDayOfWeek.value.push(item);
    }
    if (item.upCdId === UP_CD_PM_FRE_00) {
      listPmFre.value.push(item);
    }
    if (item.upCdId === UP_CD_PM_TIME_00) {
      listPmTime.value.push(item);
    }
    if (item.upCdId === UP_CD_EC_YN) {
      listEcYn.value.push(item);
    }
    if (item.upCdId === UP_CD_IP_YN) {
      listIpYn.value.push(item);
    }
    if (item.upCdId === UP_CD_NCR_T11) {
      listTermCd.value.push(item);
    }
  });
}

function handleCancel() {
  router.push({
    path: SCREEN.internPlanCenterMng.path,
  });
}
</script>
<style scoped>
.width_block_custom {
  width: 500px;
}

.font_red {
  color: var(--error) !important;
}
.font_weight_p {
  font-weight: 500;
}
</style>
