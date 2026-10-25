<template>
  <PageContainer class="page-first">
    <div class="training-header-title">{{ t('trainingPlan.pageTitle') }}</div>
  </PageContainer>
  <PageContainer>
    <div class="training-container">
      <div class="training-container__table">
        <div class="row">
          <div class="item">
            <div class="label">{{ t('trainingPlan.title1') }}</div>
            <div class="value">{{ internPlanRequestModel.internPlanNm }}</div>
          </div>
          <div class="item">
            <div class="label">{{ t('trainingPlan.title2') }}</div>
            <div class="value">{{ internPlanRequestModel.referInternPlanNm }}</div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t('trainingPlan.title3') }}</div>
            <div class="value">{{ internPlanRequestModel.executionCount ? internPlanRequestModel.executionCount + t('trainingPlan.times') : "-" }}</div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t('trainingPlan.title4') }}</div>
            <div class="value">{{ internPlanRequestModel.cormNameKr }}</div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t('trainingPlan.title5') }}</div>
            <div class="value">{{ internPlanRequestModel.commNm }}</div>
          </div>
          <div class="item">
            <div class="label">{{ t('trainingPlan.title6') }}</div>
            <div class="value">{{ internPlanRequestModel.mobilePhone ? internPlanRequestModel.mobilePhone : "-" }}</div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t('trainingPlan.title7') }}</div>
            <div class="value">{{internPlanRequestModel.registerStr}} ~ {{internPlanRequestModel.registerEnd}}</div>
          </div>
          <div class="item">
            <div class="label">{{ t('trainingPlan.title8') }}</div>
            <div class="value">{{internPlanRequestModel.internshipStr}} ~ {{ internPlanRequestModel.internshipEnd }}</div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t('trainingPlan.title9') }}</div>
            <div class="value">{{internPlanRequestModel.internRecruitmentQuantity}} {{ t('trainingPlan.total') }}</div>
          </div>
          <div class="item">
            <div class="label">{{ t('trainingPlan.title10') }}</div>
            <div class="value">{{ internPlanRequestModel.internshipField }}</div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t('trainingPlan.title11') }}</div>
            <div class="value">{{internPlanRequestModel.staffNm}} ({{internPlanRequestModel.deptNm}})</div>
          </div>
          <div class="item">
            <div class="label">{{ t('trainingPlan.title12') }}</div>
            <div class="value">{{internPlanRequestModel.internYear}} {{ t('trainingPlan.year') }} {{internPlanRequestModel.internTerm}}</div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t('trainingPlan.title13') }}</div>
            <div class="value">{{ t('trainingPlan.day') }} {{internPlanRequestModel.duration}} {{ t('trainingPlan.hour') }} ({{internPlanRequestModel.durationHourStr}}:00 ~ {{ internPlanRequestModel.durationHourEnd }}:00)</div>
          </div>
          <div class="item">
            <div class="label">{{ t('trainingPlan.title14') }}</div>
            <div class="value">{{ t('trainingPlan.weekday') }} 
              {{internPlanRequestModel.internshipWeekday}} {{ t('trainingPlan.day') }}
              ({{ dayOfWeek }})
            </div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t('trainingPlan.title15') }}</div>
            <div class="value">
              <span class="mr-2">{{internPlanRequestModel.paymentAmount}}{{ t('trainingPlan.won') }}/{{internPlanRequestModel.paymentFrequency}}</span>
              <span>{{ internPlanRequestModel.paymentTimingType }} {{ internPlanRequestModel.paymentDate }} {{ t('trainingPlan.day') }}</span>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t('trainingPlan.title16') }}</div>
            <div class="value">{{ internPlanRequestModel.employmentContractYn }}</div>
          </div>
          <div class="item">
            <div class="label">{{ t('trainingPlan.title17') }}</div>
            <div class="value">{{ internPlanRequestModel.insuranceParticipationYn }}</div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t('trainingPlan.title18') }}</div>
            <div class="value quill_editor_custom"  v-html="internPlanRequestModel.internJobSummary">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t('trainingPlan.title19') }}</div>
            <div class="value quill_editor_custom" v-html="internPlanRequestModel.requirements"></div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t('trainingPlan.title20') }}</div>
            <div class="value">
              <InputFileBase
                :mode="'detail'"
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
              />
            </div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t('trainingPlan.title22') }}</div>
            <div class="value">
              <InputFileBase
                :mode="'detail'"
                :id="`internPlanThumbnail`"
                :type="'img'"
                :multiple="false"
                :previewFlag="true"
                :show-mess="false"
                :orgName="'INTERN_PLAN_THUMBNAIL'"
                :category="'img'"
                :max-file="1"
                :sectionName="'INTERN_PLAN_THUMBNAIL'"
                ref="internPlanThumbnailFileUpload"
                :referKey="internPlanSeq"
                :max-size="5"
              />
            </div>
          </div>
        </div>
        <div class="row">
          <div class="item">
            <div class="label">{{ t('trainingPlan.title21') }}</div>
            <div class="block">
              <div class="value"  v-for="(internPlanDetail, index) in internPlanRequestModel.listInternPlanDetail" :key="index">
                <div style="display: flex; align-items: center; ">
                  <span style="font-size: 18px; font-weight: 500; margin-right: 10px">{{ internPlanDetail.week }} {{ t("trainingPlan.week") }}</span>
                  <div style="color: #555555;">
                    {{ internPlanDetail.startDate }}
                    <span>~</span>
                    {{ internPlanDetail.endDate }}
                </div>
                </div>
                <div class="mg_t10" style="white-space: pre-line; color: #555555;">
                  {{ internPlanDetail.content }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="btn-action">
      <button class="button btn_round btn_md btn_white" @click="handleCancel">{{ t("common.list") }}</button>
    </div>
  </PageContainer>
</template>
<script lang="ts" setup>
import { SCREEN } from "@/router/screen";
import { ref, onBeforeMount } from "vue";
import { InternPlanResponseModel } from "@/stores/internPlanDetailLecturer/internPlanDetailLecturer.type";
import { internPlanDetailLecturerStore } from "@/stores/internPlanDetailLecturer/useInternPlanDetailLecturer";
import { codeMngStore } from "@/stores/common/codeMng";
import { UP_CD_DAY_OF_WEEK,FORMAT_YYY_MM_DD_DOT }from "@/constants/common.const";
import { format } from "date-fns";
const router = useRouter();
const { t } = useI18n();
const internPlanMng = internPlanDetailLecturerStore();
const internPlanRequestModel = ref({} as InternPlanResponseModel);
const internPlanSeq = ref("");
const codeMng = codeMngStore();
const listDayOfWeek = ref([]);
const dayOfWeek = ref("");
onBeforeMount(async () => {
  internPlanSeq.value = window.history.state.internPlanSeq;
  await getListCodeMng()
  await getDetailInternPlan();
});

async function getDetailInternPlan() {
  await internPlanMng.detailInternPlanMng({
    method: "POST",
    body: internPlanSeq.value,
  });
  const reponse = internPlanMng.detailInternPlan.data;
  internPlanRequestModel.value = reponse;
  internPlanRequestModel.value.registerStr = formatDate(reponse.registerStr);
  internPlanRequestModel.value.registerEnd = formatDate(reponse.registerEnd);
  internPlanRequestModel.value.internshipStr = formatDate(reponse.internshipStr);
  internPlanRequestModel.value.internshipEnd = formatDate(reponse.internshipEnd);
  internPlanRequestModel.value.paymentAmount = formatNumber(internPlanRequestModel.value.paymentAmount);
  dayOfWeek.value = listDayOfWeek.value.length > 0 ? listDayOfWeek.value?.filter(item => internPlanRequestModel.value?.internshipDay?.includes(item.cdId))
                .map(item => item.cdNm)
                .join(', ') : internPlanRequestModel?.internshipDay;
}
const formatNumber = (num) => {
  return new Intl.NumberFormat('en-US').format(num);;
};

const formatDate = (date: any) => (date ? format(new Date(date), FORMAT_YYY_MM_DD_DOT) : "");

async function getListCodeMng() {
  await codeMng.getCodeInfo({
    method: "POST",
    body: {
      upCdIdList: [UP_CD_DAY_OF_WEEK],
    },
  });
  listDayOfWeek.value = codeMng.codeInfo.data
}

function handleCancel() {
  router.push({
    path: SCREEN.professorMain.path,
  });
}
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
  box-shadow: 0px 2px 4px 0px var(--GrayOpacirty12);
  border-radius: 10px;
  padding: 40px 32px;
  @media screen and (max-width: 768px) {
    padding: 28px 16px;
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