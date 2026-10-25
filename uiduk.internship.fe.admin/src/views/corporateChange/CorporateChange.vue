<template>
  <PageContainer class="policy_info_page">
    <h1 v-once  id="titlePage">{{ t("corporateChange.title") }}</h1>
    <CollapBase
      :is-show="openCollapse1"
      :title="t('corporateChange.collapse1.title')"
      :is-check="true"
      :on-click="
        () => {
          openCollapse1 = !openCollapse1;
        }
      "
    >
      <div class="search_box policy">
        <ul>
          <li>
            <p>{{ t("corporateChange.collapse1.cormNameKr") }}</p>
            <InputBase id="cormNameKr" class="policy_field_data" v-model="dataForm.cormNameKr" required :placeholder="t('corporateChange.collapse1.placeholder.cormNameKr')"/>
          </li>
          <li>
            <p>{{ t("corporateChange.collapse1.cormNameEn") }}</p>
            <InputBase id="cormNameEn" class="policy_field_data" v-model="dataForm.cormNameEn" required :placeholder="t('corporateChange.collapse1.placeholder.cormNameEn')"/>
          </li>
          <li>
            <p>{{ t("corporateChange.collapse1.cormAgentNmKr") }}</p>
            <InputBase id="cormAgentNmKr" class="policy_field_data" v-model="dataForm.cormAgentNmKr" required :placeholder="t('corporateChange.collapse1.placeholder.cormAgentNmKr')"/>
          </li>
          <li>
            <p>{{ t("corporateChange.collapse1.cormRegistNumber") }}</p>
            <div class="policy_field_data dp_flex gp_8">
              <InputBase id="cormNumber1" required :is-number="true" v-model="cormNumber.number1" maxlength="3"/>
              <InputBase id="cormNumber2" required :is-number="true" v-model="cormNumber.number2" maxlength="2"/>
              <InputBase id="cormNumber3" required :is-number="true" v-model="cormNumber.number3" maxlength="5"/>
            </div>
          </li>
          <li>
            <p>{{ t("corporateChange.collapse1.cormType") }}</p>
            <InputBase id="cormType" required class="policy_field_data" v-model="dataForm.cormType" :placeholder="t('corporateChange.collapse1.placeholder.cormType')"/>
          </li>
          <li>
            <p>{{ t("corporateChange.collapse1.address") }}</p>
            <InputBase id="address" required class="policy_field_data" v-model="dataForm.address" :placeholder="t('corporateChange.collapse1.placeholder.address')"/>
          </li>
          <li>
            <p>{{ t("corporateChange.collapse1.cormCategories") }}</p>
            <InputBase id="cormCategories" required class="policy_field_data" v-model="dataForm.cormCategories" :placeholder="t('corporateChange.collapse1.placeholder.cormCategories')"/>
          </li>
          <li>
            <p>{{ t("corporateChange.collapse1.cormHomepageUrl") }}</p>
            <InputBase id="cormHomepageUrl" required class="policy_field_data" v-model="dataForm.cormHomepageUrl" :placeholder="'https://www.'"/>
          </li>

          <li>
            <p>{{ t("corporateChange.collapse1.cormClassificationCd") }}</p>
            <div class="policy_field_data">
              <div class="select_checkbox">
                <RadiobuttonBase
                  v-for="item in lstCorporateType"
                  :id="`policy_info_1_${item.cdId}`"
                  :key="item.cdId"
                  v-model="dataForm.cormClassificationCd"
                  :value="item.cdId"
                  :name="`policy_info_1_${item.cdId}`"
                  :checked="item.cdId == dataForm.cormClassificationCd"
                  :label="item.cdNm"
                ></RadiobuttonBase>
              </div>
            </div>
          </li>
          <li>
            <p>{{ t("corporateChange.collapse1.cormAwardCd") }}</p>
            <div class="policy_field_data">
              <div class="select_checkbox">
                <RadiobuttonBase
                  v-for="item in lstCorprateAward"
                  :id="`policy_info_2_${item.cdId}`"
                  :key="item.cdId"
                  v-model="dataForm.cormAwardCd"
                  :value="item.cdId"
                  :name="`policy_info_2_${item.cdId}`"
                  :checked="item.cdId == dataForm.cormAwardCd"
                  :label="item.cdNm"
                />
              </div>
            </div>
          </li>

          <li>
            <p>{{ t("corporateChange.collapse1.cormAddress") }}</p>
            <div class="policy_field_data dp_flex flex_column gp_8">
              <div class="dp_flex gp_8">
                <InputBase id="cormZoneCode" required disabled v-model="dataForm.cormZoneCode" :placeholder="t('corporateChange.collapse1.placeholder.cormZoneCode')"/>
                <button class="button btn_lg btn_secondary" @click="openPostcode">{{ t("corporateChange.button.zipCode") }}</button>
              </div>
              <InputBase id="cormAddress" required disabled v-model="dataForm.cormAddress" :placeholder="t('corporateChange.collapse1.placeholder.cormAddress')"/>
              <InputBase id="cormAddressDetail" required v-model="dataForm.cormAddressDetail" :placeholder="t('corporateChange.collapse1.placeholder.cormAddressDetail')"/>
            </div>
          </li>
        </ul>
      </div>
    </CollapBase>

    <CollapBase
      :is-show="openCollapse2"
      :title="t('corporateChange.collapse2.title')"
      :is-check="true"
      :on-click="
        () => {
          openCollapse2 = !openCollapse2;
        }
      "
    >
      <div class="search_box policy policy_border">
        <ul>
          <li>
            <p>{{ t("corporateChange.collapse2.userId") }}</p>
            <div class="policy_field_data dp_flex flex_column gp_8">
              <div class="dp_flex gp_8">
                <InputBase disabled id="userId" v-model="dataForm.cormUserId" :placeholder="t('corporateChange.collapse2.placeholder.userId')"/>
              </div>
            </div>
          </li>
          <li>
            <p>{{ t("corporateChange.collapse2.userPw") }}</p>
            <div class="policy_field_data dp_flex flex_column gp_8">
              <InputBase type="password" id="userPw" v-model="dataForm.userPw" :placeholder="t('corporateChange.collapse2.placeholder.userPw')"/>
              <p class="validate_password" v-show="isValidatePw">{{ t("corporateChange.message.messageValidationPass") }}</p>
              <InputBase type="password" id="userPwConfirm" v-model="pwConfirm" :placeholder="t('corporateChange.collapse2.placeholder.userPwConfirm')"/>
            </div>
          </li>
          <li>
            <p>{{ t("corporateChange.collapse2.cormPhone") }}</p>
            <div class="policy_field_data dp_flex flex_column gp_8">
              <InputBase id="cormPhone" @input="formatPhone" required v-model="dataForm.cormPhone" :placeholder="t('corporateChange.collapse2.placeholder.cormPhone')"/>
            </div>
          </li>
        </ul>
      </div>

      <div class="search_box policy policy_border">
        <p class="policy_title_collap">{{ t("corporateChange.collapse2.person1.title") }}</p>
        <ul>
          <li>
            <p>{{ t("corporateChange.collapse2.person1.commDept") }}</p>
            <InputBase id="commDept1" required v-model="dataForm.lstCorporateChange[0].commDept" :placeholder="t('corporateChange.collapse2.placeholder.commDept')"/>
          </li>
          <li>
            <p>{{ t("corporateChange.collapse2.person1.commNm") }}</p>
            <InputBase id="commNm1" required v-model="dataForm.lstCorporateChange[0].commNm" :placeholder="t('corporateChange.collapse2.placeholder.commNm')"/>
          </li>
          <li>
            <p>{{ t("corporateChange.collapse2.person1.positionName") }}</p>
            <InputBase id="positionName1" required v-model="dataForm.lstCorporateChange[0].positionName" :placeholder="t('corporateChange.collapse2.placeholder.positionName')"/>
          </li>
          <li>
            <p>{{ t("corporateChange.collapse2.person1.companyPhone") }}</p>
            <div class="policy_field_data dp_flex flex_column gp_8">
              <div class="dp_flex gp_8">
                <InputBase @input="formatPhone" id="bodyPhone1" required v-model="dataForm.lstCorporateChange[0].companyPhone" :placeholder="t('corporateChange.collapse2.placeholder.companyPhone')"/>
              </div>
            </div>
          </li>
          <li>
            <p>{{ t("corporateChange.collapse2.person1.mobilePhone") }}</p>
            <div class="policy_field_data dp_flex flex_column gp_8">
              <div class="dp_flex gp_8">
                <InputBase @input="formatPhone" id="bodyPhone2" required v-model="dataForm.lstCorporateChange[0].mobilePhone" :placeholder="t('corporateChange.collapse2.placeholder.mobilePhone')"/>
              </div>
            </div>
          </li>
          <li>
            <p>{{ t("corporateChange.collapse2.person1.email") }}</p>
            <div class="policy_field_data field_email dp_flex gp_8">
              <div class="dp_flex gp_8">
                <InputBase id="beforeDomain1" required v-model="emailCharge.person1.beforeDomain" />
                <span class="at_symbol">@</span>
                <InputBase id="afterDomain1" required v-model="emailCharge.person1.afterDomain" :disabled="emailCharge.person1.disabledDomain" />
              </div>
              <div>
                <SelectBoxBase id="selectDomain1" name="selectDomain1" :data="lstDomainEmail" v-model="emailCharge.person1.selectDomain" />
              </div>
            </div>
          </li>
        </ul>
      </div>

      <div class="search_box policy policy_border">
        <p class="policy_title_collap">{{ t("corporateChange.collapse2.person2.title") }}</p>
        <ul>
          <li>
            <p>{{ t("corporateChange.collapse2.person2.commDept") }}</p>
            <InputBase id="commDep2" required v-model="dataForm.lstCorporateChange[1].commDept" :placeholder="t('corporateChange.collapse2.placeholder.commDept')"/>
          </li>
          <li>
            <p>{{ t("corporateChange.collapse2.person2.commNm") }}</p>
            <InputBase id="commNm2" required v-model="dataForm.lstCorporateChange[1].commNm" :placeholder="t('corporateChange.collapse2.placeholder.commNm')"/>
          </li>
          <li>
            <p>{{ t("corporateChange.collapse2.person2.positionName") }}</p>
            <InputBase id="positionName2" required v-model="dataForm.lstCorporateChange[1].positionName" :placeholder="t('corporateChange.collapse2.placeholder.positionName')"/>
          </li>
          <li>
            <p>{{ t("corporateChange.collapse2.person2.companyPhone") }}</p>
            <div class="policy_field_data dp_flex flex_column gp_8">
              <div class="dp_flex gp_8">
                <InputBase @input="formatPhone" id="bodyPhone3" required v-model="dataForm.lstCorporateChange[1].companyPhone" :placeholder="t('corporateChange.collapse2.placeholder.companyPhone')"/>
              </div>
            </div>
          </li>
          <li>
            <p>{{ t("corporateChange.collapse2.person2.mobilePhone") }}</p>
            <div class="policy_field_data dp_flex flex_column gp_8">
              <div class="dp_flex gp_8">
                <InputBase @input="formatPhone" id="bodyPhone4" required v-model="dataForm.lstCorporateChange[1].mobilePhone" :placeholder="t('corporateChange.collapse2.placeholder.mobilePhone')"/>
              </div>
            </div>
          </li>
          <li>
            <p>{{ t("corporateChange.collapse2.person2.email") }}</p>
            <div class="policy_field_data field_email dp_flex gp_8">
              <div class="dp_flex gp_8">
                <InputBase id="beforeDomain2" required v-model="emailCharge.person2.beforeDomain" />
                <span class="at_symbol">@</span>
                <InputBase id="afterDomain2" required v-model="emailCharge.person2.afterDomain" :disabled="emailCharge.person2.disabledDomain" />
              </div>
              <div>
                <SelectBoxBase id="selectDomain2" name="selectDomain12312" :data="lstDomainEmail" v-model="emailCharge.person2.selectDomain" />
              </div>
            </div>
          </li>
        </ul>
      </div>
    </CollapBase>

    <CollapBase
      :is-show="openCollapse3"
      :title="t('corporateChange.collapse3.title')"
      :is-check="true"
      :on-click="
        () => {
          openCollapse3 = !openCollapse3;
        }
      "
    >
      <div class="search_box policy image">
        <ul style="margin-bottom: 0">
          <li class="max_wd100">
            <p>{{ t("corporateChange.collapse3.cormCi") }}</p>
            <div class="policy_field_data dp_flex flex_column max_wd100">
              <InputFileBase
                v-if="cormIdCheck"
                :mode="`edit`"
                :id="`cormCi`"
                :type="fileTypeImg"
                :preview-flag="true"
                :description="'JPG, PNG파일만 등록 가능합니다.'"
                :multiple="true"
                :show-mess="false"
                ref="cormCiRef"
                :orgName="'CORM_CI'"
                :category="'img'"
                :sectionName="'CORM_CI'"
                :referKey="dataForm.cormSeq"
                v-model="cormCiFileArr"
              ></InputFileBase>
            </div>
          </li>
          <li class="max_wd100">
            <p>{{ t("corporateChange.collapse3.cormSign") }}</p>
            <div class="policy_field_data dp_flex flex_column max_wd100">
              <InputFileBase
                v-if="cormIdCheck"
                :mode="`edit`"
                :id="`cormSign`"
                :type="fileTypeImg"
                :preview-flag="true"
                :description="'JPG, PNG파일만 등록 가능합니다.'"
                :multiple="true"
                :show-mess="false"
                :orgName="'CORM_SIGN'"
                :category="'img'"
                :sectionName="'CORM_SIGN'"
                ref="cormSignRef"
                :referKey="dataForm.cormSeq"
                v-model="cormSignFileArr"
              ></InputFileBase>
            </div>
          </li>

          <li class="max_wd100">
            <p>{{ t("corporateChange.collapse3.cormInsurance") }}</p>
            <div class="policy_field_data dp_flex flex_column max_wd100">
              <InputFileBase
                v-if="cormIdCheck"
                :mode="`edit`"
                :id="`cormInsurance`"
                :type="fileTypeImg"
                :description="'파일갯수 1개 / JPG, PNG파일만 등록가능합니다.'"
                :show-mess="false"
                :orgName="'CORM_INSURANCE'"
                :category="'img'"
                :sectionName="'CORM_INSURANCE'"
                ref="cormInsuranceRef"
                v-model="cormInsuranceFileArr"
                :referKey="dataForm.cormSeq"
              ></InputFileBase>
            </div>
          </li>
          <li class="max_wd100">
            <p>{{ t("corporateChange.collapse3.cormIntro") }}</p>
            <div class="policy_field_data dp_flex flex_column max_wd100">
              <InputFileBase
                v-if="cormIdCheck"
                :mode="`edit`"
                :id="`cormIntro`"
                :type="fileTypeOffice"
                :description="'파일갯수 1개 / Hwp, Doc, PPT, PDF 파일만 등록가능합니다.'"
                :show-mess="false"
                :orgName="'CORM_INTRO'"
                :category="'img'"
                :sectionName="'CORM_INTRO'"
                ref="cormIntroRef"
                v-model="cormIntroFileArr"
                :referKey="dataForm.cormSeq"
              ></InputFileBase>
            </div>
          </li>
        </ul>
      </div>
    </CollapBase>

    <div class="btn_group btn_end dp_flex gp_12">
      <button class="btn_white btn_md" @click="handlerRedirectList">{{ t("common.list") }}</button>
      <button class="btn_primary btn_md" @click="handlerRegisterCorporate">{{ t("common.edit") }}</button>
    </div>

    <TModalAlert :is-open="modalOpenAlert" :is-show-header="true" :is-show-footer="true" :modal-id="'alert'" @close-modal="closeModalAlert">
      <template #title>
        <strong>{{ modalMessAlert }}</strong>
      </template>
      <template #default>
        <div class="box_section">
          <p>{{ modalMessAlert }}</p>
        </div>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_primary" @click="closeModalAlert">{{ t("common.confirm") }}</button>
      </template>
    </TModalAlert>

    <TModalConfirm
      :is-open="modalOpenConfirmReg"
      :is-show-header="true"
      :is-show-footer="true"
      :modal-id="'configReg'"
      @close-modal="closeModalConfirmReg"
    >
      <template #title>
        <strong>{{ t("corporateChange.message.confrimChange") }}</strong>
      </template>
      <template #default>
        <div class="box_section">
          <p>{{ t("corporateChange.message.confrimChange") }}</p>
        </div>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_primary" @click="confrimRegister">{{ t("common.confirm") }}</button>
        <button type="button" class="button btn_md btn_white popup_close" @click="closeModalConfirmReg">{{ t("common.cancel") }}</button>
      </template>
    </TModalConfirm>
    <TModalAlert :is-open="modalOpenAlertReg" :is-show-header="true" :is-show-footer="true" :modal-id="'alertReg'" @close-modal="closeModalAlertReg">
      <template #title>
        <strong>{{ t("corporateChange.message.alertChangeSuccess") }}</strong>
      </template>
      <template #default>
        <div class="box_section">
          <p>{{ t("corporateChange.message.alertChangeSuccess") }}</p>
        </div>
      </template>
      <template #footer>
        <button type="button" class="button btn_md btn_primary" @click="closeModalAlertReg">{{ t("common.confirm") }}</button>
      </template>
    </TModalAlert>
  </PageContainer>
</template>
<script setup lang="ts">
import PageContainer from "@/components/common/PageContainer.vue";
import { commonStore } from "@/stores/common";
import { ref, watch } from "vue";
import CollapBase from "@/components/common/CollapBase.vue";
import InputBase from "@/components/common/input/InputBase.vue";
import RadiobuttonBase from "@/components/common/input/RadiobuttonBase.vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import InputFileBase from "@/components/common/input/InputFileBase.vue";
import { corporateChangeStore } from "@/stores/corporateChange/useCorporateChange";
import { CorporateInfoChange } from "@/stores/corporateChange/corporateChange.type";
import { useI18n } from "vue-i18n";
import { UP_CD_DOMAIN, UP_CD_CORM_TYPE, UP_CD_CORM_AWARD } from "@/constants/common.const";
import { SUCCSESS_STATUS, FILE_TYPE_IMAGE, FILE_TYPE_OFFICE } from "@/constants/screen.const";
import { codeMngStore } from "@/stores/common/codeMng";
import {SCREEN} from "@/router/screen";
const { t } = useI18n();

const store = commonStore();
const router = useRouter()
const corporateStore = corporateChangeStore();
const codeMng = codeMngStore();

const openCollapse1 = ref(false);
const openCollapse2 = ref(false);
const openCollapse3 = ref(false);

const modalOpenAlert = ref(false);
const modalOpenConfirmReg = ref(false);
const modalOpenAlertReg = ref(false);
const modalMessAlert = ref("");

const pwConfirm = ref("");
const isValidatePw = ref(false);
const isCheckPassword = ref(false);

const lstCorporateType = ref([]);
const lstCorprateAward = ref([]);

const lstDomainEmail = ref([]);
const cormNumber = ref({
  number1: "",
  number2: "",
  number3: "",
});
const emailCharge = ref({
  person1: {
    beforeDomain: "",
    afterDomain: "",
    selectDomain: "",
    disabledDomain: true,
  },
  person2: {
    beforeDomain: "",
    afterDomain: "",
    selectDomain: "",
    disabledDomain: true,
  },
});

const fileTypeImg = FILE_TYPE_IMAGE;
const fileTypeOffice = FILE_TYPE_OFFICE;
const cormCiRef = ref(null);
const cormSignRef = ref(null);
const cormInsuranceRef = ref(null);
const cormIntroRef = ref(null);

const cormCiFileArr = ref([]);
const cormSignFileArr = ref([]);
const cormInsuranceFileArr = ref([]);
const cormIntroFileArr = ref([]);
const cormIdCheck = ref(false);
const emailPerson = ref({
  person1: "",
  person2: ""
});

const dataForm = ref<CorporateInfoChange>({
  cormUserId: "",
  userPw: "",
  cormSeq: "",
  cormNameKr: "",
  cormNameEn: "",
  cormAgentNmKr: "",
  cormRegistNumber: "",
  cormType: "",
  address: "",
  cormCategories: "",
  cormClassificationCd: "",
  cormHomepageUrl: "",
  cormAwardCd: "",
  cormZoneCode: "",
  cormAddress: "",
  cormAddressDetail: "",
  cormPhone: "",
  cormStatus: "",
  lstCorporateChange: [
    {
      commNm: "",
      commDept: "",
      positionName: "",
      companyPhone: "",
      mobilePhone: "",
      email: "",
    },
    {
      commNm: "",
      commDept: "",
      positionName: "",
      companyPhone: "",
      mobilePhone: "",
      email: "",
    },
  ],
});
onBeforeMount(() => {
  fnGetLstCmng();
  getDetailCorm();
});
const fnGetLstCmng = async () => {
  await codeMng.getCodeInfo({
    method: "POST",
    body: {
      upCdIdList: [UP_CD_CORM_AWARD, UP_CD_CORM_TYPE, UP_CD_DOMAIN],
    },
  });

  lstCorporateType.value = codeMng.codeInfo.data?.filter((item) => item.upCdId == UP_CD_CORM_TYPE);
  lstCorprateAward.value = codeMng.codeInfo.data?.filter((item) => item.upCdId == UP_CD_CORM_AWARD);
  lstDomainEmail.value = codeMng.codeInfo.data?.filter((item) => item.upCdId == UP_CD_DOMAIN);
};

const getDetailCorm = async () => {
  cormIdCheck.value = false;
  openCollapse1.value = false;
  openCollapse2.value = false;
  openCollapse3.value = false;
  isCheckPassword.value = false;
  isValidatePw.value = false;
  await corporateStore.fetchDetailCorporate({
    method: "POST",
  });
  if (corporateStore.status?.value == SUCCSESS_STATUS) {
    dataForm.value = corporateStore.corporateChange.data;
    dataForm.value.userPw = "";
    pwConfirm.value = "";
    let number = dataForm.value.cormRegistNumber?.split("-");
    if (number.length > 0) {
      cormNumber.value.number1 = number[0];
      cormNumber.value.number2 = number[1];
      cormNumber.value.number3 = number[2];
    }
    if (dataForm.value.lstCorporateChange.length < 2) {
      while (dataForm.value.lstCorporateChange.length < 2) {
        dataForm.value.lstCorporateChange.push({
          commNm: "",
          commDept: "",
          positionName: "",
          companyPhone: "",
          mobilePhone: "",
          email: "",
        });
      }
    }
    dataForm.value.lstCorporateChange?.forEach((item, index) => {
      const emailParts = item?.email?.split("@");
      if (emailParts?.length == 2) {
        if (index == 0) {
          emailCharge.value.person1.beforeDomain = emailParts[0];
          emailPerson.value.person1 = emailParts[1];
          const domainList = lstDomainEmail.value.map(domain => domain.cdId);
          emailCharge.value.person1.selectDomain = domainList.includes(emailParts[1]) ? emailParts[1] : 'etc';
          emailCharge.value.person1.afterDomain = emailParts[1];
        } else if (index == 1) {
          emailCharge.value.person2.beforeDomain = emailParts[0];
          emailPerson.value.person2 = emailParts[1];
          const domainList = lstDomainEmail.value.map(domain => domain.cdId);
          emailCharge.value.person2.selectDomain = domainList.includes(emailParts[1]) ? emailParts[1] : 'etc';
          emailCharge.value.person2.afterDomain = emailParts[1];
        }
      }
    });
  }
  cormIdCheck.value = true;
  const titlePage = document.getElementById("titlePage");
  titlePage?.scrollIntoView({ behavior: "smooth", block: "center" });
  openCollapse1.value = true;
  openCollapse2.value = true;
  openCollapse3.value = true;
};
const registerCorporate = async () => {
  dataForm.value.lstCorporateChange[0].email = emailCharge.value.person1.beforeDomain + "@" + emailCharge.value.person1.afterDomain;
  dataForm.value.lstCorporateChange[1].email = emailCharge.value.person2.beforeDomain + "@" + emailCharge.value.person2.afterDomain;
  dataForm.value.cormRegistNumber = cormNumber.value.number1 + "-" + cormNumber.value.number2 + "-" + cormNumber.value.number3;
  await corporateStore.fetchUpdateCorporate({
    method: "PUT",
    body: {
      ...dataForm.value,
    },
  });
  if (corporateStore.status?.value == SUCCSESS_STATUS) {
    let cormId = corporateStore.dataId.data;
    await uploadFile(cormId, cormCiRef);
    await uploadFile(cormId, cormSignRef);
    await uploadFile(cormId, cormInsuranceRef);
    await uploadFile(cormId, cormIntroRef);
    openModalAlertReg();
  }
};
const handlerRegisterCorporate = () => {
  if (store.check) {
    store.scrollToErrorRequired();
    validatePostcode();

    openModalAlert(t("corporateChange.message.alertTrimValidation"));
    openCollapse1.value = true;
    openCollapse2.value = true;
    openCollapse3.value = true;
    return;
  }
  if (cormInsuranceFileArr.value.length <= 0 || cormIntroFileArr.value.length <= 0||
      cormCiFileArr.value.length <= 0 || cormSignFileArr.value.length <= 0) {
    openModalAlert(t("corporateChange.message.alertTrimValidation"));
    return;
  }

  const userPw = document.getElementById("userPw");
  const userPwConfirm = document.getElementById("userPwConfirm");
  if (dataForm.value.userPw.trim().length > 0) {
    if (pwConfirm.value.trim().length == 0) {
      userPwConfirm?.classList.add("error_validate");
      userPwConfirm?.scrollIntoView({ behavior: "smooth", block: "center" });
      openModalAlert(t("corporateChange.message.alertPassValidation"));
      return;
    }else{
      userPwConfirm?.classList.remove("error_validate");
    }
  }
  if (pwConfirm.value.trim().length > 0) {
    if (dataForm.value.userPw.trim().length == 0) {
      userPw?.classList.add("error_validate");
      userPw?.scrollIntoView({ behavior: "smooth", block: "center" })
      openModalAlert(t("corporateChange.message.alertPassValidation"));
      return;
    }else{
      userPw?.classList.remove("error_validate");
    }
  }

  if (dataForm.value.userPw.trim().length > 0 || pwConfirm.value.trim().length > 0) {
    if (dataForm.value.userPw != pwConfirm.value) {
      userPw?.classList.add("error_validate");
      userPwConfirm?.classList.add("error_validate");
      userPw?.scrollIntoView({ behavior: "smooth", block: "center" });
      openModalAlert(t("corporateChange.message.alertConfrimPassError"));
      return;
    } else {
      userPw?.classList.remove("error_validate");
      userPwConfirm?.classList.remove("error_validate");
      isCheckPassword.value = true;
    }
  }

  if(isValidatePw.value){
    userPw?.scrollIntoView({ behavior: "smooth", block: "center" });
    userPw?.classList.add("error_validate");
    return;
  }else{
    userPw?.classList.remove("error_validate");
  }

  openModalConfirmReg();
};

const confrimRegister = async () => {
  closeModalConfirmReg();
  await registerCorporate();
};


const validatePassword = (password: any) => {
  const regex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[a-zA-Zㄱ-ㅎ가-힣\d!@#$%^&*]{8,}$/;
  const userPw = document.getElementById("userPw");
  if (regex.test(password) || password.length == 0) {
    userPw?.classList.remove("error_validate");
    isValidatePw.value = false;
  } else {
    userPw?.classList.add("error_validate");
    isValidatePw.value = true;
  }
};

function formatPhone(event) {
  let input = event.target.value;
  input = input.replace(/[^\d]/g, "");
  if(input.startsWith("02")) {
    if (input.length > 2 && input.length <= 6) {
      input = `${input.slice(0, 2)}-${input.slice(2)}`;
    } else if (input.length > 6) {
      input = `${input.slice(0, 2)}-${input.slice(2, 6)}-${input.slice(6, 10)}`;
    }
  }else{
    if (input.length > 3 && input.length <= 7) {
      input = `${input.slice(0, 3)}-${input.slice(3)}`;
    } else if (input.length > 7) {
      input = `${input.slice(0, 3)}-${input.slice(3, 7)}-${input.slice(7, 11)}`;
    }
  }
  event.target.value = input;
}

const openModalAlert = (mess: any) => {
  modalOpenAlert.value = true;
  modalMessAlert.value = mess;
};
const closeModalAlert = () => {
  modalOpenAlert.value = false;
};

const openModalConfirmReg = () => {
  modalOpenConfirmReg.value = true;
};
const closeModalConfirmReg = () => {
  modalOpenConfirmReg.value = false;
};

const openModalAlertReg = () => {
  modalOpenAlertReg.value = true;
};
const closeModalAlertReg = () => {
  getDetailCorm();
  modalOpenAlertReg.value = false;
};

const validatePostcode = () => {
  const cormAddress = document.getElementById("cormAddress");
  const cormZoneCode = document.getElementById("cormZoneCode");
  if (cormZoneCode && !dataForm.value.cormZoneCode) {
    cormZoneCode.classList.add("error_validate_disabled");
  } else if (cormZoneCode) {
    cormZoneCode.classList.remove("error_validate_disabled");
  }
  if (cormAddress && !dataForm.value.cormAddress) {
    cormAddress.classList.add("error_validate_disabled");
  } else if (cormAddress) {
    cormAddress.classList.remove("error_validate_disabled");
  }
};

const uploadFile = async (id: any, refName: any) => {
  const childComponent = refName.value;
  await childComponent.upLoadFile(id);
};
const openPostcode = () => {
  new window.daum.Postcode({
    oncomplete: (data: any) => {
      dataForm.value.cormZoneCode = data.zonecode;
      dataForm.value.cormAddress = data.address;
    },
  }).open();
};

watch(
  () => emailCharge.value.person1.selectDomain,
  (newVal) => {
    if (newVal == "etc") {
      emailCharge.value.person1.disabledDomain = false;
      emailCharge.value.person1.afterDomain = emailPerson.value.person1;
    } else {
      emailCharge.value.person1.disabledDomain = true;
      emailCharge.value.person1.afterDomain = emailCharge.value.person1.selectDomain;
    }
  },
);
watch(
  () => emailCharge.value.person2.selectDomain,
  (newVal) => {
    if (newVal == "etc") {
      emailCharge.value.person2.disabledDomain = false;
      emailCharge.value.person2.afterDomain = emailPerson.value.person2
    } else {
      emailCharge.value.person2.disabledDomain = true;
      emailCharge.value.person2.afterDomain = emailCharge.value.person2.selectDomain;
    }
  },
);

watch(
  () => dataForm.value.cormZoneCode,
  () => {
    validatePostcode();
  },
);
watch(
  () => dataForm.value.userPw,
  (newVal) => {
    validatePassword(newVal);
  },
);

const handlerRedirectList = () => {
  router.push({name:SCREEN.home.name})
}
</script>
<style lang="scss">
.page_container.policy_info_page {
  padding-top: calc(var(--height--horizontal) + 6rem);
  padding-bottom: 10rem;
  min-height: 100dvh;
  background: rgba(248, 249, 254, 1);

  h1 {
    font-size: 4rem;
    font-weight: 500;
    line-height: 6rem;
    margin-bottom: 1.6rem;
  }

  h3 {
    font-size: 1.6rem;
    font-weight: 400;
    line-height: 2.4rem;
    color: var(--Gray-scale-70, rgba(85, 85, 85, 1));
    margin-bottom: 4rem;
  }

  .search_box.policy {
    color: var(--Gray-scale-90, rgba(29, 29, 29, 1));

    ul {
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      gap: 2.4rem;

      li {
        max-width: 800px;
        display: flex;
        justify-content: space-between;
        margin-bottom: 0 !important;
        align-items: flex-start;

        & > p {
          width: 200px;
          font-size: 16px;
          font-weight: 500;
          line-height: 21px;
          margin-top: 9.5px;
        }

        & > p:not(:has(.not_required)):after {
          content: "*";
          font-size: 1.5rem;
          margin-left: 4px;
          color: var(--error);
          font-weight: 500;
          line-height: 2.1rem;
          letter-spacing: -0.02em;
        }

        & > p.not_required:after {
          content: "";
        }

        .policy_field_data {
          width: 100%;
          max-width: 600px;

          .select_checkbox {
            & p {
              width: 110px;
            }
          }

          .at_symbol {
            margin: auto;
            font-size: 14px;
            font-weight: 400;
            line-height: 21px;
          }
        }
      }
    }
  }

  .search_box.policy.policy_border {
    border-top: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));
    padding-bottom: 2.8rem;

    .policy_title_collap {
      font-size: 18px;
      font-weight: 500;
      line-height: 27px;
      padding: 28px 0px 16px 0px;
    }

    ul {
      margin-bottom: 0;
    }

    &:first-child {
      padding-top: 0;
      border-top: none;
    }
  }

  .btn_group {
    margin-top: 40px;
  }
}

@media screen and (max-width: 768px) {
  .page_container.policy_info_page {
    .search_box.policy {
      ul {
        li {
          & > p {
            width: 150px;
          }
        }
      }
    }
  }
}
.error_validate_disabled {
  border-color: var(--error) !important;
}
</style>
