<template>
  <PageContainer>
    <h1 v-once>
      {{ t("internPlanningMng.crud.pageTitle") }}
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
                <th scope="row" class="required">{{ t("internPlanningMng.crud.title1") }}</th>
                <td>
                  <div class="wd_p50">
                    <InputBase
                      :placeholder="t('internPlanningMng.crud.placeholder1')"
                      id="internNew"
                      required
                      v-model="internPlanRequestModel.internPlanNm"
                      :is-detail="isDetail"
                      :disabled="internPlanRequestModel.referInternPlanSeq != null && internPlanRequestModel.referInternPlanSeq !== ''"
                    />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row">{{ t("internPlanningMng.crud.title2") }}</th>
                <td>
                  <div class="wd_p50">
                    <SelectBoxBaseSearch
                      v-if="
                        modeScreen === MODE_CREATE ||
                        modeScreen === MODE_EDIT ||
                        modeScreen === MODE_COPY ||
                        (listInternOld.length > 0 && internPlanRequestModel.referInternPlanSeq)
                      "
                      :id="'internOld'"
                      :placeholder="t('internPlanningMng.crud.placeholder2')"
                      v-model="internPlanRequestModel.referInternPlanSeq"
                      :name="'internOld'"
                      :data="listInternOld"
                      :is-detail="isDetail"
                      @change="changeReferInternPlanSeq"
                    />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row">{{ t("internPlanningMng.crud.title3") }}</th>
                <td>{{ internPlanRequestModel.executionCount ? internPlanRequestModel.executionCount + t("internPlanningMng.times") : "-" }}</td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanningMng.crud.title4") }}</th>
                <td>
                  <div class="wd_p50">
                    <InputBase :is-detail="isDetail" id="cormMemberInfo" :disabled="true" v-model="cormMemberInfo.cormNameKr" />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanningMng.crud.title5") }}</th>
                <td>
                  <div class="wd_p50">
                    <SelectBoxBase
                      :id="'listOperator'"
                      :name="'listOperator'"
                      :data="listOperator"
                      v-if="modeScreen === MODE_CREATE || (listOperator.length > 1 && internPlanRequestModel.commSeq != null)"
                      v-model="internPlanRequestModel.commSeq"
                      required
                      :is-detail="isDetail"
                    />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanningMng.crud.title6") }}</th>
                <td>
                  <span>{{ phoneNumberSelected ? phoneNumberSelected : "-" }}</span>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanningMng.crud.title7") }}</th>
                <td>
                  <div class="search_flex wd_p30" style="position: relative">
                    <BaseDatePicker
                      :is-detail="isDetail"
                      :id="'registerStr'"
                      :placeholder="t('common.startDate')"
                      v-model="internPlanRequestModel.registerStr"
                      required
                    />
                    <span>~</span>
                    <BaseDatePicker
                      :is-detail="isDetail"
                      :id="'registerEnd'"
                      :placeholder="t('common.endDate')"
                      v-model="internPlanRequestModel.registerEnd"
                      required
                    />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanningMng.crud.title8") }}</th>
                <td>
                  <div class="search_flex wd_p30" style="position: relative">
                    <BaseDatePicker
                      :id="'internshipStr'"
                      v-model="internPlanRequestModel.internshipStr"
                      :placeholder="t('common.startDate')"
                      :is-detail="isDetail"
                      required
                    />
                    <span>~</span>
                    <BaseDatePicker
                      :id="'internshipEnd'"
                      v-model="internPlanRequestModel.internshipEnd"
                      :is-detail="isDetail"
                      :placeholder="t('common.endDate')"
                      required
                    />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanningMng.crud.title9") }}</th>
                <td>
                  <div class="wd_p7 d-flex" style="align-items: center; gap: 10px">
                    <InputBase
                      :is-detail="isDetail"
                      :isNumber="true"
                      id="quantityPeople"
                      v-model="internPlanRequestModel.internRecruitmentQuantity"
                      required
                    />
                    {{ t("internPlanningMng.crud.placeholder4") }}
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanningMng.crud.title10") }}</th>
                <td>
                  <div class="d-flex wd_p25" style="align-items: center; gap: 10px">
                    <div style="flex: 0 0 80%">
                      <InputBase :is-detail="isDetail" id="internshipField" v-model="internPlanRequestModel.internshipField" required />
                    </div>
                    <div style="flex: 0 0 20%">
                      {{ t("internPlanningMng.crud.placeholder5") }}
                    </div>
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanningMng.crud.title11") }}</th>
                <td>
                  <div class="wd_p50">
                    <SelectBoxBaseSearch
                      :is-detail="isDetail"
                      :id="'listProfessor'"
                      v-model="internPlanRequestModel.staffNo"
                      :name="'listProfessor'"
                      :data="listProfessor"
                      v-if="modeScreen === MODE_CREATE || (listProfessor.length > 0 && internPlanRequestModel.staffNo)"
                      :placeholder="t('internPlanningMng.crud.placeholder6')"
                      required
                    />
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanningMng.crud.title12") }}</th>
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
                <th scope="row" class="required">{{ t("internPlanningMng.crud.title13") }}</th>
                <td>
                  <div class="search_flex wd_p50" style="gap: 10px">
                    <span>{{ t("internPlanningMng.crud.placeholder9") }}</span>
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
                    <div style="min-width: 30px">{{ t("internPlanningMng.crud.placeholder10") }}</div>
                    <SelectBoxBase
                      required
                      v-model="internPlanRequestModel.durationHourStr"
                      :id="'listHoursStr'"
                      :name="'listHoursStr'"
                      :data="listHours"
                      :is-detail="isDetail"
                    />
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
                    />
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
                <th scope="row" class="required">{{ t("internPlanningMng.crud.title14") }}</th>
                <td>
                  <div class="search_flex wd_p40" style="gap: 10px">
                    <span>{{ t("internPlanningMng.crud.placeholder11") }}</span>
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
                    <div style="min-width: 30px">{{ t("internPlanningMng.crud.placeholder12") }}</div>
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
                <th scope="row" class="required">{{ t("internPlanningMng.crud.title15") }}</th>
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
                      <div style="min-width: 25px">{{ t("internPlanningMng.crud.placeholder14") }}</div>
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
                      <span>{{ t("internPlanningMng.crud.placeholder13") }}</span>
                    </div>
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanningMng.crud.title16") }}</th>
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
                  <p class="required font_weight_p">{{ t("internPlanningMng.crud.title17") }}</p>
                  <p class="font_weight_p">{{ t("internPlanningMng.crud.title18") }}</p>
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
                <th scope="row" class="required">{{ t("internPlanningMng.crud.title19") }}</th>
                <td>
                  <div v-show="!isDetail">
                    <QuillEditor :modules="modulesInternJob" toolbar="full" @editorChange="changeEditorInternJob()" ref="editorInternJob" />
                  </div>
                  <div v-if="isDetail" class="quill_editor_custom" v-html="internPlanRequestModel.internJobSummary"></div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanningMng.crud.title20") }}</th>
                <td>
                  <div v-show="!isDetail">
                    <QuillEditor :modules="modulesRequirement" toolbar="full" @editorChange="changeEditorRequirement()" ref="editorRequirement" />
                  </div>
                  <div v-if="isDetail" class="quill_editor_custom" v-html="internPlanRequestModel.requirements"></div>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanningMng.crud.title21") }}</th>
                <td>
                  <InputFileBase
                    :mode="modeScreen === modeCopy ? modeCreate : modeScreen"
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
                    {{ t("internPlanningMng.crud.title25") }}
                    <br />
                    {{ t("internPlanningMng.crud.title26") }}
                  </span>
                </td>
              </tr>
              <tr>
                <th scope="row" class="required">{{ t("internPlanningMng.titleThumbnail") }}</th>
                <td>
                  <InputFileBase
                    :mode="modeScreen === modeCopy ? modeCreate : modeScreen"
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
                    v-model="fileThumbnailArray"
                  />
                  <div class="font_red mg_t20">
                    {{ t("internPlanningMng.textTitleThumbnail") }}
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row">
                  <p class="required font_weight_p">{{ t("internPlanningMng.crud.title22") }}</p>
                  <p class="font_red">{{ t("internPlanningMng.crud.title23") }}</p>
                </th>
                <td>
                  <div
                    class="mg_b20 mg_t20"
                    v-if="internPlanRequestModel.listInternPlanDetail"
                    v-for="(internPlanDetail, index) in internPlanRequestModel.listInternPlanDetail.filter((detail) => detail.delYn === statusN)"
                    :key="index"
                  >
                    <div class="search_flex wd_p50" style="position: relative">
                      <h2 class="width_week">{{ internPlanDetail.week }}{{ t("internPlanningMng.crud.week") }}</h2>
                      <BaseDatePicker
                        v-model="internPlanDetail.startDate"
                        :id="'startDatePlanDetail' + index"
                        :placeholder="t('common.startDate')"
                        required
                        :is-detail="isDetail"
                      />
                      <span>~</span>
                      <BaseDatePicker
                        v-model="internPlanDetail.endDate"
                        :id="'endDatePlanDetail' + index"
                        :placeholder="t('common.endDate')"
                        :is-detail="isDetail"
                        required
                      />
                      <span style="min-width: 50px">
                        <img
                          @click="createPlanDetail"
                          style="cursor: pointer"
                          src="../../assets/images/icon_plus.svg"
                          v-if="index === 0 && !isDetail"
                        />
                        <img
                          @click="deletePlanDetail(internPlanDetail)"
                          style="cursor: pointer"
                          src="../../assets/images/icon_minus.svg"
                          v-if="index > 0 && !isDetail"
                        />
                      </span>
                    </div>
                    <div class="mg_t20 wd_p50">
                      <TextArea
                        :placeholder="t('internPlanningMng.crud.placeholder16')"
                        :maxWords="2000"
                        :id="'contentPlanDetail' + index"
                        v-model="internPlanDetail.content"
                        :is-detail="isDetail"
                        required
                      />
                      <hr v-if="index < internPlanRequestModel.listInternPlanDetail.filter((detail) => detail.delYn === statusN).length - 1" />
                    </div>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="btn_group btn_end mg_t30">
          <button type="button" class="btn_md btn_white" @click="handleCancel" v-if="isDetail">{{ t("common.list") }}</button>
          <button type="button" class="btn_md btn_primary" v-if="isDetail" @click="gotoEdit">
            {{ t("common.edit") }}
          </button>
          <button type="button" class="btn_md btn_white" @click="openModalConfirmCancel" v-if="!isDetail">{{ t("common.cancel") }}</button>
          <button type="button" class="btn_md btn_primary" :disabled="isCheckRequired" v-if="!isDetail" @click="confirmSaveData">
            {{ t("internPlanningMng.crud.title24") }}
          </button>
        </div>
      </div>
    </div>
    <TModalConfirm :is-open="isModalConfirmSave" :is-show-header="true" @close-modal="closeModalConfirmSave">
      <template #default>
        <div class="box_section">
          <p>{{ t("common.message.save") }}</p>
        </div>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_primary" @click="saveData">{{ t("common.confirm") }}</button>
        <button type="button" class="button btn_md btn_white popup_close" @click="closeModalConfirmSave">{{ t("common.cancel") }}</button>
      </template>
    </TModalConfirm>
    <TModalConfirm :is-open="isModalConfirmCancel" :is-show-header="true" @close-modal="closeModalConfirmCancel">
      <template #default>
        <div class="box_section">
          <p>{{ t("internPlanningMng.crud.messageConfirmCancel") }}</p>
        </div>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_primary" @click="openModalAlertCancel">{{ t("common.confirm") }}</button>
        <button type="button" class="button btn_md btn_white popup_close" @click="closeModalConfirmCancel">{{ t("common.cancel") }}</button>
      </template>
    </TModalConfirm>
    <TModalAlert :is-open="isModalAlertCancel" :is-show-header="true" :is-show-footer="true">
      <template #default>
        <div class="box_section">
          <p>{{ t("internPlanningMng.crud.successCancel") }}</p>
        </div>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_primary" @click="handleCancel">{{ t("common.confirm") }}</button>
      </template>
    </TModalAlert>
    <TModalAlert :is-open="isModalAlertSuccessSave" :is-show-header="true" :is-show-footer="true">
      <template #default>
        <div class="box_section">
          <p>{{ t("common.message.saveSuccess") }}</p>
        </div>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_primary" @click="closeModalSuccessSave">{{ t("common.confirm") }}</button>
      </template>
    </TModalAlert>
    <TModalAlert
      :is-open="isModalValidateDate"
      :is-show-header="true"
      :is-show-footer="true"
      :modal-id="'modalValidateDate'"
      @close-modal="closeModalValidateDate"
    >
      <template #default>
        <div class="box_section">
          <p>{{ t("common.messageCheckDate") }}</p>
        </div>
      </template>
      <template #footer>
        <button type="button" class="btn_md btn_white popup_close" @click="closeModalValidateDate">{{ t("common.confirm") }}</button>
      </template>
    </TModalAlert>
    <TModalAlert
      :is-open="isModalValidateEdit"
      :is-show-header="true"
      :is-show-footer="true"
      :modal-id="'modalValidateEdit'"
      @close-modal="closeModalValidateEdit"
    >
      <template #default>
        <div class="box_section">
          <p>{{ t("internPlanningMng.crud.messageValidateEdit") }}</p>
        </div>
      </template>
      <template #footer>
        <button type="button" class="btn_md btn_white popup_close" @click="closeModalValidateEdit">{{ t("common.confirm") }}</button>
      </template>
    </TModalAlert>
    <TModalAlert
      :is-open="isModalValidateWeek"
      :is-show-header="true"
      :is-show-footer="true"
      :modal-id="'modalValidateWeek'"
      @close-modal="closeModalValidateWeek"
    >
      <template #default>
        <div class="box_section">
          <p>{{ t("internPlanningMng.crud.messageValidateWeek") }}</p>
        </div>
      </template>
      <template #footer>
        <button type="button" class="btn_md btn_white popup_close" @click="closeModalValidateWeek">{{ t("common.confirm") }}</button>
      </template>
    </TModalAlert>
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
  CD_ID_EC_Y,
  CD_ID_IP_Y,
  CD_ID_PM_TIME_01,
  CD_ID_STS_WAIT_APPROVE,
  MODE_DETAIL,
  STATUS_NO,
  STATUS_YES,
  UP_CD_DAY_OF_WEEK,
  UP_CD_EC_YN,
  UP_CD_IP_YN,
  UP_CD_NCR_T11,
  UP_CD_PM_FRE_00,
  UP_CD_PM_TIME_00,
} from "@/constants/common.const";
import { QuillEditor } from "@vueup/vue-quill";
import ImageUploader from "quill-image-uploader";
import { uploadFileEditor } from "@/stores/common/fileMng/fileMng.service";
import { ACCEPTTYPE_ALL, MODE_COPY, MODE_CREATE, MODE_EDIT } from "@/constants/screen.const";
import { InternPlanCormMemberInfoModel, InternPlanDetailListModel, InternPlanRequestModel } from "@/stores/internPlanningMng/internPlanningMng.type";
import { internPlanMngStore } from "@/stores/internPlanningMng";
import ButtonExportPDFV1 from "@/components/common/ButtonExportPDFV1.vue";
import ItemCard from "@/components/common/ItemCard.vue";

const store = commonStore();
const router = useRouter();
const route = useRoute();
const { t } = useI18n();
const codeMng = codeMngStore();
const internPlanMng = internPlanMngStore();
const typeAceptAll = ACCEPTTYPE_ALL;
const modeEdit = ref(MODE_EDIT);
const modeDetail = ref(MODE_DETAIL);
const modeCreate = ref(MODE_CREATE);
const modeCopy = ref(MODE_COPY);
const statusN = ref(STATUS_NO);
const modeScreen = ref("");

const modulesInternJob = {
  name: "imageUploader",
  module: ImageUploader,
  options: {
    upload: (file: any) => {
      return new Promise((resolve, reject) => {
        if (!file) {
          reject("Upload failed");
        } else {
          store.setLoading(true);
          const formData = new FormData();
          formData.append("file", file);
          formData.append("category", "img");
          formData.append("orgName", "INTERN_JOB_SUMMARY");
          formData.append("sectionName", "INTERN_JOB_SUMMARY");
          uploadFileEditor(formData)
            .then((res: any) => {
              resolve(res.data.data.urlFile);
              store.setLoading(false);
            })
            .catch(() => {
              reject("Upload failed");
            });
        }
      });
    },
  },
};

const modulesRequirement = {
  name: "imageUploader",
  module: ImageUploader,
  options: {
    upload: (file: any) => {
      return new Promise((resolve, reject) => {
        if (!file) {
          reject("Upload failed");
        } else {
          store.setLoading(true);
          const formData = new FormData();
          formData.append("file", file);
          formData.append("category", "img");
          formData.append("orgName", "REQUIREMENTS");
          formData.append("sectionName", "REQUIREMENTS");
          uploadFileEditor(formData)
            .then((res: any) => {
              resolve(res.data.data.urlFile);
              store.setLoading(false);
            })
            .catch(() => {
              reject("Upload failed");
            });
        }
      });
    },
  },
};

const listInternOld = ref<Array<CodeMngModel>>([{ cdId: "", cdNm: t("common.select"), upCdId: "" }]);
const listOperator = ref<Array<CodeMngModel>>([{ cdId: "", cdNm: t("internPlanningMng.crud.placeholder3"), upCdId: "" }]);
const listOperatorManager = ref<Array<any>>([]);
const listProfessor = ref<Array<CodeMngModel>>([]);
const listYearCd = ref<Array<CodeMngModel>>([{ cdId: "", cdNm: t("internPlanningMng.crud.placeholder7"), upCdId: "" }]);
const listTermCd = ref<Array<CodeMngModel>>([{ cdId: "", cdNm: t("internPlanningMng.crud.placeholder8"), upCdId: "" }]);
const listHours = ref<Array<CodeMngModel>>([]);
const listMinutes = ref<Array<CodeMngModel>>([]);
const listDayOfWeek = ref<Array<CodeMngModel>>([]);
const listPmFre = ref<Array<CodeMngModel>>([{ cdId: "", cdNm: t("internPlanningMng.crud.placeholder15"), upCdId: "" }]);
const listPmTime = ref<Array<CodeMngModel>>([]);
const listEcYn = ref<Array<CodeMngModel>>([]);
const listIpYn = ref<Array<CodeMngModel>>([]);
const phoneNumberSelected = ref("");
const editorInternJob = ref(null);
const editorRequirement = ref(null);
const isModalConfirmSave = ref(false);
const isModalConfirmCancel = ref(false);
const isModalAlertCancel = ref(false);
const isModalAlertSuccessSave = ref(false);
const isDetail = ref(false);
const internPlanSeq = ref("");
const internPlanFileUpload = ref(null);
const internPlanThumbnailFileUpload = ref(null);
const fileArray = ref([]);
const fileThumbnailArray = ref([]);
const nameExportPDF = "INTERN_PLAN";
const isModalValidateDate = ref(false);
const isModalValidateEdit = ref(false);
const isModalValidateWeek = ref(false);

const internPlanRequestModel = ref({} as InternPlanRequestModel);
const cormMemberInfo = ref({} as InternPlanCormMemberInfoModel);

const isCheckRequired = computed(
  () =>
    store.check ||
    !internPlanRequestModel.value.internJobSummary ||
    !internPlanRequestModel.value.requirements ||
    !internPlanRequestModel.value.commSeq ||
    !internPlanRequestModel.value.internYear ||
    !internPlanRequestModel.value.internTerm ||
    !internPlanRequestModel.value.paymentFrequency ||
    !internPlanRequestModel.value.internshipDay ||
    internPlanRequestModel.value.internshipDay.length === 0 ||
    fileArray.value.length === 0 ||
    fileThumbnailArray.value.length === 0,
);

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
          openModalValidateDate();
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
          openModalValidateDate();
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
          openModalValidateDate();
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
          openModalValidateDate();
        });
      }
    }
  },
);

const prevListInternPlanDetail = ref([]);

watch(
  () => internPlanRequestModel.value.listInternPlanDetail,
  (newValue) => {
    newValue.forEach((internPlanDetail, index) => {
      const oldItem = prevListInternPlanDetail.value?.[index] || {};

      const isStartDateChanged = internPlanDetail.startDate !== oldItem.startDate;
      const isEndDateChanged = internPlanDetail.endDate !== oldItem.endDate;

      if (isStartDateChanged || isEndDateChanged) {
        handleDateChange(internPlanDetail, isStartDateChanged ? "startDate" : "endDate");
      }
    });

    nextTick(() => {
      prevListInternPlanDetail.value = JSON.parse(JSON.stringify(newValue));
    });
  },
  { deep: true },
);

function handleDateChange(internPlanDetail: InternPlanDetailListModel, changedField: "startDate" | "endDate") {
  if (!internPlanDetail.startDate || !internPlanDetail.endDate) {
    return;
  }

  const dStart = new Date(internPlanDetail.startDate);
  const dEnd = new Date(internPlanDetail.endDate);

  if (changedField === "startDate" && dStart > dEnd) {
    internPlanDetail.startDate = internPlanDetail.endDate;
    openModalValidateDate();
  } else if (changedField === "endDate" && dEnd < dStart) {
    internPlanDetail.endDate = internPlanDetail.startDate;
    openModalValidateDate();
  }
}

async function genDataCd() {
  for (let i = 0; i < 24; i++) {
    const hour = i.toString().padStart(2, "0");
    listHours.value.push({ cdId: hour, cdNm: hour, upCdId: "hours" });
  }

  for (let i = 0; i < 60; i++) {
    const minute = i.toString().padStart(2, "0");
    listMinutes.value.push({ cdId: minute, cdNm: minute, upCdId: "minutes" });
  }

  const currentYear = new Date().getFullYear();
  for (let year = 2024; year <= 2034; year++) {
    listYearCd.value.push({
      cdId: year.toString(),
      cdNm: year.toString(),
      upCdId: "",
    });
  }

  if (modeScreen.value === MODE_CREATE) {
    internPlanRequestModel.value.internYear = currentYear + "";
    internPlanRequestModel.value.paymentTimingType = CD_ID_PM_TIME_01;
    internPlanRequestModel.value.employmentContractYn = CD_ID_EC_Y;
    internPlanRequestModel.value.insuranceParticipationYn = CD_ID_IP_Y;
    internPlanRequestModel.value.listInternPlanDetail = [];
    internPlanRequestModel.value.status = CD_ID_STS_WAIT_APPROVE;
    const internPlanDetailListModel = ref<InternPlanDetailListModel>({
      internDetailSeq: null,
      content: "",
      startDate: "",
      endDate: "",
      week: "1",
      delYn: STATUS_NO,
    });
    internPlanRequestModel.value.listInternPlanDetail.push(internPlanDetailListModel.value);
    internPlanRequestModel.value.executionCount = 1;
    internPlanRequestModel.value.referInternPlanSeq = null;
  } else {
    getDetailInternPlan();
  }
}

onBeforeMount(() => {
  modeScreen.value = route.params.mode;
  internPlanSeq.value = window.history.state.internPlanSeq;
  isDetail.value = modeScreen.value === modeDetail.value;
  genDataCd();
  getListCodeMng();
  getCormInfoCurrent();
  if (modeScreen.value === MODE_CREATE || modeScreen.value === MODE_COPY) {
    getListInternExists();
  } else {
    getListInternExistsUpdate();
  }

  getListComporatorManager();
  getListProfessor();
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

  if (modeScreen.value === MODE_COPY) {
    internPlanRequestModel.value.internPlanSeq = null;
    internPlanRequestModel.value.registerStr = null;
    internPlanRequestModel.value.registerEnd = null;
    internPlanRequestModel.value.internshipStr = null;
    internPlanRequestModel.value.internshipEnd = null;
    internPlanRequestModel.value.status = CD_ID_STS_WAIT_APPROVE;
    internPlanRequestModel.value.listInternPlanDetail.forEach((item) => {
      item.internDetailSeq = null;
      item.startDate = null;
      item.endDate = null;
    });
    internPlanRequestModel.value.internPlanNm = "Copy_" + internPlanRequestModel.value.internPlanNm;
    if(internPlanRequestModel.value.referInternPlanSeq) {
      internPlanRequestModel.value.executionCount += 1;
    }
  }
}

async function getCormInfoCurrent() {
  await internPlanMng.getCormMemberInfo({
    method: "POST",
  });
  cormMemberInfo.value = internPlanMng.cormMemberInfo.data;
  internPlanRequestModel.value.cormSeq = cormMemberInfo.value.cormSeq;
}

async function getListInternExists() {
  await internPlanMng.getListInternExist({
    method: "GET",
  });
  let res = internPlanMng.listInternExist.data;
  res.forEach((item) => {
    listInternOld.value.push({ cdId: item.internPlanSeq, cdNm: item.internPlanNm });
  });
}

async function getListInternExistsUpdate() {
  await internPlanMng.getListInternExistUpdate({
    method: "POST",
    body: internPlanSeq.value,
  });
  let res = internPlanMng.listInternExist.data;
  res.forEach((item) => {
    listInternOld.value.push({ cdId: item.internPlanSeq, cdNm: item.internPlanNm });
  });
}

async function getListProfessor() {
  await internPlanMng.getListProfessor({
    method: "GET",
  });
  let res = internPlanMng.listProfessor.data;
  res.forEach((item) => {
    listProfessor.value.push({ cdId: item.staffNo, cdNm: item.korNm });
  });
}

async function getListComporatorManager() {
  await internPlanMng.getComporatorManager({
    method: "GET",
  });
  let res = internPlanMng.listComporatorManager.data;
  listOperatorManager.value = res;
  res.forEach((item) => {
    listOperator.value.push({ cdId: item.commSeq, cdNm: item.commNm });
  });
}

function changeReferInternPlanSeq() {
  if (internPlanRequestModel.value.referInternPlanSeq) {
    getCountExecution(internPlanRequestModel.value.referInternPlanSeq);
    internPlanRequestModel.value.internPlanNm =
      listInternOld.value.find((item) => item.cdId === internPlanRequestModel.value.referInternPlanSeq)?.cdNm || "";
  } else {
    internPlanRequestModel.value.executionCount = 1;
    internPlanRequestModel.value.internPlanNm = "";
  }
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

async function getCountExecution(data: string) {
  await internPlanMng.getCountExecution({
    method: "POST",
    body: data,
  });
  internPlanRequestModel.value.executionCount = internPlanMng.countExecution.data;
}

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

function createPlanDetail() {
  const activeItemsCount = internPlanRequestModel.value.listInternPlanDetail.filter((detail) => detail.delYn === statusN.value).length;

  const internPlanDetailListModel = ref<InternPlanDetailListModel>({
    internDetailSeq: null,
    content: "",
    startDate: "",
    endDate: "",
    week: (activeItemsCount + 1).toString(),
    delYn: statusN.value,
  });

  internPlanRequestModel.value.listInternPlanDetail.push(internPlanDetailListModel.value);
}

function deletePlanDetail(item: InternPlanDetailListModel) {
  const index = internPlanRequestModel.value.listInternPlanDetail.indexOf(item);

  if (index !== -1) {
    if (item.internDetailSeq) {
      internPlanRequestModel.value.listInternPlanDetail[index].delYn = STATUS_YES;
    } else {
      internPlanRequestModel.value.listInternPlanDetail.splice(index, 1);
    }
  }

  let activeWeek = 1;
  internPlanRequestModel.value.listInternPlanDetail.forEach((detail) => {
    if (detail.delYn === STATUS_NO) {
      detail.week = activeWeek.toString();
      activeWeek += 1;
    }
  });
}

function confirmSaveData() {
  const details = internPlanRequestModel.value.listInternPlanDetail;
  for (let i = 1; i < details.length; i++) {
    const prevEndDate = new Date(details[i - 1].endDate as string);
    const currentStartDate = new Date(details[i].startDate as string);
    if (currentStartDate <= prevEndDate) {
      openModalValidateWeek();
      return;
    }
  }
  isModalConfirmSave.value = true;
}

function closeModalConfirmSave() {
  isModalConfirmSave.value = false;
}

async function saveData() {
  if (Array.isArray(internPlanRequestModel.value.internshipDay)) {
    internPlanRequestModel.value.internshipDay = internPlanRequestModel.value.internshipDay.join(",");
  }
  internPlanRequestModel.value.referInternPlanSeq = internPlanRequestModel.value.referInternPlanSeq || null;
  await internPlanMng.saveInternPlan({
    method: "POST",
    body: internPlanRequestModel.value,
  });
  uploadFile(internPlanMng.internPlanSeqSave.data);
  closeModalConfirmSave();
  isModalAlertSuccessSave.value = true;
}

function changeEditorInternJob() {
  if (editorInternJob.value) {
    internPlanRequestModel.value.internJobSummary = editorInternJob.value.getHTML().toString().replace("<p><br></p>", "");
  }
}

function changeEditorRequirement() {
  if (editorRequirement.value) {
    internPlanRequestModel.value.requirements = editorRequirement.value.getHTML().toString().replace("<p><br></p>", "");
  }
}

function uploadFile(data) {
  internPlanFileUpload.value.upLoadFile(data);
  internPlanThumbnailFileUpload.value.upLoadFile(data);
}

function openModalConfirmCancel() {
  isModalConfirmCancel.value = true;
}

function closeModalConfirmCancel() {
  isModalConfirmCancel.value = false;
}

function closeModalSuccessSave() {
  isModalAlertSuccessSave.value = false;
  handleCancel();
}

function openModalAlertCancel() {
  isModalAlertCancel.value = true;
  closeModalConfirmCancel();
}

function gotoEdit() {
  const currentDate = new Date();
  const startDate = new Date(internPlanRequestModel.value.internshipStr);
  const endDate = new Date(internPlanRequestModel.value.internshipEnd);

  if (!isNaN(startDate.getTime()) && !isNaN(endDate.getTime())) {
    if (startDate <= currentDate && currentDate <= endDate) {
      openModalValidateEdit();
      return;
    }
  }

  isDetail.value = false;
  modeScreen.value = MODE_EDIT;

  if (editorInternJob.value) {
    editorInternJob.value.setHTML(internPlanRequestModel.value.internJobSummary);
  }
  if (editorRequirement.value) {
    editorRequirement.value.setHTML(internPlanRequestModel.value.requirements);
  }
}

function handleCancel() {
  router.push({
    path: SCREEN.internPlanningMng.path,
  });
}

function openModalValidateDate() {
  isModalValidateDate.value = true;
}

function closeModalValidateDate() {
  isModalValidateDate.value = false;
}

function openModalValidateEdit() {
  isModalValidateEdit.value = true;
}

function closeModalValidateEdit() {
  isModalValidateEdit.value = false;
}

function openModalValidateWeek() {
  isModalValidateWeek.value = true;
}

function closeModalValidateWeek() {
  isModalValidateWeek.value = false;
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
.width_week {
  min-width: 70px;
  font-weight: 500;
}
</style>
