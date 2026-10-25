<template>
  <TModal
    :classSize="`large`"
    :is-open="isOpen"
    :is-show-header="true"
    :is-show-footer="true"
    :modal-id="'modalCorpDetail'"
    @close-modal="closeModal"
  >
    <!-- Modal content goes here -->
    <template #title>
      <strong>{{ t("corporateApprovalMng.modal.modalTitle") }}</strong>
    </template>
    <template #default>
      <div class="policy_info_page">
        <CollapBase
          :is-show="openCollapse1"
          :title="t('corporateApprovalMng.modal.collapse1.title')"
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
                <p>{{ t("corporateApprovalMng.modal.collapse1.cormNameKr") }}</p>
                <InputBase id="cormNameKr" disabled class="policy_field_data" v-model="dataForm.cormNameKr" />
              </li>
              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse1.cormNameEn") }}</p>
                <InputBase id="cormNameEn" disabled class="policy_field_data" v-model="dataForm.cormNameEn" />
              </li>
              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse1.cormAgentNmKr") }}</p>
                <InputBase id="cormAgentNmKr" disabled class="policy_field_data" v-model="dataForm.cormAgentNmKr" />
              </li>
              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse1.cormRegistNumber") }}</p>
                <div class="policy_field_data dp_flex gp_8">
                  <InputBase id="cormNumber1" class="policy_field_data" disabled :is-number="true" v-model="cormNumber.number1" />
                  <InputBase id="cormNumber2" class="policy_field_data" disabled :is-number="true" v-model="cormNumber.number2" />
                  <InputBase id="cormNumber3" class="policy_field_data" disabled :is-number="true" v-model="cormNumber.number3" />
                </div>
              </li>
              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse1.cormType") }}</p>
                <InputBase id="cormType" disabled class="policy_field_data" v-model="dataForm.cormType" />
              </li>
              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse1.address") }}</p>
                <InputBase id="address" disabled class="policy_field_data" v-model="dataForm.address" />
              </li>
              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse1.cormCategories") }}</p>
                <InputBase id="cormCategories" disabled class="policy_field_data" v-model="dataForm.cormCategories" />
              </li>
              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse1.cormHomepageUrl") }}</p>
                <InputBase id="cormHomepageUrl" disabled class="policy_field_data" v-model="dataForm.cormHomepageUrl" />
              </li>

              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse1.cormClassificationCd") }}</p>
                <div class="policy_field_data">
                  <div class="select_checkbox">
                    <RadiobuttonBase
                      isDisable
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
                <p>{{ t("corporateApprovalMng.modal.collapse1.cormAwardCd") }}</p>
                <div class="policy_field_data">
                  <div class="select_checkbox">
                    <RadiobuttonBase
                      isDisable
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
                <p>{{ t("corporateApprovalMng.modal.collapse1.cormAddress") }}</p>
                <div class="policy_field_data dp_flex flex_column gp_8">
                  <div class="dp_flex gp_8">
                    <InputBase id="cormZoneCode" class="policy_field_data" disabled v-model="dataForm.cormZoneCode" />
                  </div>
                  <InputBase id="cormAddress" class="policy_field_data" disabled v-model="dataForm.cormAddress" />
                  <InputBase id="cormAddressDetail" class="policy_field_data" disabled v-model="dataForm.cormAddressDetail" />
                </div>
              </li>
            </ul>
          </div>
        </CollapBase>

        <CollapBase
          :is-show="openCollapse2"
          :title="t('corporateApprovalMng.modal.collapse2.title')"
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
                <p>{{ t("corporateApprovalMng.modal.collapse2.userId") }}</p>
                <div class="policy_field_data dp_flex flex_column gp_8">
                  <div class="dp_flex gp_8">
                    <InputBase disabled id="userId" class="policy_field_data" v-model="dataForm.cormUserId" />
                  </div>
                </div>
              </li>
              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse2.cormPhone") }}</p>
                <div class="policy_field_data dp_flex flex_column gp_8">
                  <InputBase id="cormPhone" class="policy_field_data" disabled v-model="dataForm.cormPhone" />
                </div>
              </li>
            </ul>
          </div>

          <div class="search_box policy policy_border">
            <p class="policy_title_collap">{{ t("corporateApprovalMng.modal.collapse2.person1.title") }}</p>
            <ul>
              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse2.person1.commDept") }}</p>
                <InputBase id="commDept1" class="policy_field_data" disabled v-model="dataForm.lstCorporateManager[0].commDept" />
              </li>
              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse2.person1.commNm") }}</p>
                <InputBase id="commNm1" class="policy_field_data" disabled v-model="dataForm.lstCorporateManager[0].commNm" />
              </li>
              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse2.person1.positionName") }}</p>
                <InputBase id="positionName1" class="policy_field_data" disabled v-model="dataForm.lstCorporateManager[0].positionName" />
              </li>
              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse2.person1.companyPhone") }}</p>
                <div class="policy_field_data dp_flex flex_column gp_8">
                  <div class="dp_flex gp_8">
                    <InputBase id="bodyPhone1" class="policy_field_data" disabled v-model="dataForm.lstCorporateManager[0].companyPhone" />
                  </div>
                </div>
              </li>
              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse2.person1.mobilePhone") }}</p>
                <div class="policy_field_data dp_flex flex_column gp_8">
                  <div class="dp_flex gp_8">
                    <InputBase id="bodyPhone2" class="policy_field_data" disabled v-model="dataForm.lstCorporateManager[0].mobilePhone" />
                  </div>
                </div>
              </li>
              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse2.person1.email") }}</p>
                <div class="policy_field_data field_email dp_flex gp_8">
                  <div class="dp_flex gp_8">
                    <InputBase id="beforeDomain1" class="policy_field_data" disabled v-model="emailCharge.person1.beforeDomain" />
                    <span class="at_symbol">@</span>
                    <InputBase id="afterDomain1" class="policy_field_data" disabled v-model="emailCharge.person1.afterDomain" />
                  </div>
                  <div>
                    <SelectBoxBase
                      id="selectDomain1"
                      class="policy_field_data"
                      disabled
                      name="selectDomain1"
                      :data="lstDomainEmail"
                      v-model="emailCharge.person1.selectDomain"
                    />
                  </div>
                </div>
              </li>
            </ul>
          </div>

          <div class="search_box policy policy_border">
            <p class="policy_title_collap">{{ t("corporateApprovalMng.modal.collapse2.person2.title") }}</p>
            <ul>
              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse2.person2.commDept") }}</p>
                <InputBase id="commDep2" class="policy_field_data" disabled v-model="dataForm.lstCorporateManager[1].commDept" />
              </li>
              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse2.person2.commNm") }}</p>
                <InputBase id="commNm2" class="policy_field_data" disabled v-model="dataForm.lstCorporateManager[1].commNm" />
              </li>
              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse2.person2.positionName") }}</p>
                <InputBase id="positionName2" class="policy_field_data" disabled v-model="dataForm.lstCorporateManager[1].positionName" />
              </li>
              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse2.person2.companyPhone") }}</p>
                <div class="policy_field_data dp_flex flex_column gp_8">
                  <div class="dp_flex gp_8">
                    <InputBase id="bodyPhone3" class="policy_field_data" disabled v-model="dataForm.lstCorporateManager[1].companyPhone" />
                  </div>
                </div>
              </li>
              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse2.person2.mobilePhone") }}</p>
                <div class="policy_field_data dp_flex flex_column gp_8">
                  <div class="dp_flex gp_8">
                    <InputBase id="bodyPhone4" class="policy_field_data" disabled v-model="dataForm.lstCorporateManager[1].mobilePhone" />
                  </div>
                </div>
              </li>
              <li>
                <p>{{ t("corporateApprovalMng.modal.collapse2.person2.email") }}</p>
                <div class="policy_field_data field_email dp_flex gp_8">
                  <div class="dp_flex gp_8">
                    <InputBase id="beforeDomain2" class="policy_field_data" disabled v-model="emailCharge.person2.beforeDomain" />
                    <span class="at_symbol">@</span>
                    <InputBase id="afterDomain2" class="policy_field_data" disabled v-model="emailCharge.person2.afterDomain" />
                  </div>
                  <div>
                    <SelectBoxBase
                      id="selectDomain2"
                      class="policy_field_data"
                      disabled
                      name="selectDomain"
                      :data="lstDomainEmail"
                      v-model="emailCharge.person2.selectDomain"
                    />
                  </div>
                </div>
              </li>
            </ul>
          </div>
        </CollapBase>

        <CollapBase
          :is-show="openCollapse3"
          :title="t('corporateApprovalMng.modal.collapse3.title')"
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
                <p>{{ t("corporateApprovalMng.modal.collapse3.cormCi") }}</p>
                <div class="policy_field_data dp_flex flex_column max_wd100">
                  <InputFileBase
                    :mode="`detail`"
                    :id="`cormCi`"
                    :type="fileTypeImg"
                    :preview-flag="true"
                    :description="'JPG, PNG파일만 등록 가능합니다.'"
                    :multiple="true"
                    :max-file="5"
                    :show-mess="false"
                    ref="cormCiRef"
                    :orgName="'CORM_CI'"
                    :category="'img'"
                    :sectionName="'CORM_CI'"
                    :referKey="cormId"
                  ></InputFileBase>
                </div>
              </li>
              <li class="max_wd100">
                <p>{{ t("corporateApprovalMng.modal.collapse3.cormSign") }}</p>
                <div class="policy_field_data dp_flex flex_column max_wd100">
                  <InputFileBase
                    :mode="`detail`"
                    :id="`cormSign`"
                    :type="fileTypeImg"
                    :preview-flag="true"
                    :description="'JPG, PNG파일만 등록 가능합니다.'"
                    :multiple="true"
                    :max-file="5"
                    :show-mess="false"
                    :orgName="'CORM_SIGN'"
                    :category="'img'"
                    :sectionName="'CORM_SIGN'"
                    ref="cormSignRef"
                    :referKey="cormId"
                  ></InputFileBase>
                </div>
              </li>

              <li class="max_wd100">
                <p>{{ t("corporateApprovalMng.modal.collapse3.cormInsurance") }}</p>
                <div class="policy_field_data dp_flex flex_column max_wd100">
                  <InputFileBase
                    :mode="`detail`"
                    :id="`cormInsurance`"
                    :type="fileTypeImg"
                    :description="'파일갯수 1개 / JPG, PNG파일만 등록가능합니다.'"
                    :show-mess="false"
                    :orgName="'CORM_INSURANCE'"
                    :category="'img'"
                    :sectionName="'CORM_INSURANCE'"
                    ref="cormInsuranceRef"
                    :referKey="cormId"
                  ></InputFileBase>
                </div>
              </li>
              <li class="max_wd100">
                <p>{{ t("corporateApprovalMng.modal.collapse3.cormIntro") }}</p>
                <div class="policy_field_data dp_flex flex_column max_wd100">
                  <InputFileBase
                    :mode="`detail`"
                    :id="`cormIntro`"
                    :type="fileTypeOffice"
                    :description="'파일갯수 1개 / Hwp, Doc, PPT, PDF 파일만 등록가능합니다.'"
                    :show-mess="false"
                    :orgName="'CORM_INTRO'"
                    :category="'img'"
                    :sectionName="'CORM_INTRO'"
                    ref="cormIntroRef"
                    :referKey="cormId"
                  ></InputFileBase>
                </div>
              </li>
            </ul>
          </div>
        </CollapBase>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button type="button" class="button btn_md btn_white popup_close" @click="closeModal">{{ t("common.close") }}</button>
    </template>
  </TModal>
</template>

<script setup lang="ts">
import { ref, defineEmits } from "vue";
import { SUCCSESS_STATUS, FILE_TYPE_IMAGE, FILE_TYPE_OFFICE } from "@/constants/screen.const";
import { useI18n } from "vue-i18n";
import { corporateApprovalMngStore } from "@/stores/corporateApprovalMng/useCorporateApprovalMng";
import { CorporateApproval } from "@/stores/corporateApprovalMng/corporateApprovalMng.type";
const { t } = useI18n();
import { UP_CD_DOMAIN, UP_CD_CORM_TYPE, UP_CD_CORM_AWARD } from "@/constants/common.const";
import { codeMngStore } from "@/stores/common/codeMng";
const props = defineProps<{
  isOpen: boolean;
  cormSeq: string;
}>();
const emit = defineEmits(["closeModal"]);
const corporateApprovalMng = corporateApprovalMngStore();
const codeMng = codeMngStore();

const fileTypeImg = FILE_TYPE_IMAGE;
const fileTypeOffice = FILE_TYPE_OFFICE;
const lstCorporateType = ref([]);
const lstCorprateAward = ref([]);
const lstDomainEmail = ref([]);
const openCollapse1 = ref(false);
const openCollapse2 = ref(false);
const openCollapse3 = ref(false);
const cormId = ref("");
const dataForm = ref<CorporateApproval>({
  cormUserId: "",
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
  lstCorporateManager: [
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
onBeforeMount(() => {
  cormId.value = props.cormSeq;
  fnGetLstCmng();
  getDataDetail();
});
const getDataDetail = async () => {
  openCollapse1.value = false;
  openCollapse2.value = false;
  openCollapse3.value = false;
  await corporateApprovalMng.getDetailCorporate({
    method: "POST",
    body: cormId.value,
  });
  if (corporateApprovalMng.status?.value == SUCCSESS_STATUS) {
    dataForm.value = corporateApprovalMng.resultDataCorporate.data;
    if (dataForm.value.lstCorporateManager.length < 2) {
      while (dataForm.value.lstCorporateManager.length < 2) {
        dataForm.value.lstCorporateManager.push({
          commNm: "",
          commDept: "",
          positionName: "",
          companyPhone: "",
          mobilePhone: "",
          email: "",
        });
      }
    }
    let number = dataForm.value.cormRegistNumber?.split("-");
    if (number.length > 0) {
      cormNumber.value.number1 = number[0];
      cormNumber.value.number2 = number[1];
      cormNumber.value.number3 = number[2];
    }
    dataForm.value.lstCorporateManager?.forEach((item, index) => {
      const emailParts = item?.email?.split("@");
      if (emailParts?.length == 2) {
        if (index == 0) {
          emailCharge.value.person1.beforeDomain = emailParts[0];
          emailCharge.value.person1.selectDomain = emailParts[1];
          emailCharge.value.person1.afterDomain = emailParts[1];
        } else if (index == 1) {
          emailCharge.value.person2.beforeDomain = emailParts[0];
          emailCharge.value.person2.selectDomain = emailParts[1];
          emailCharge.value.person2.afterDomain = emailParts[1];
        }
      }
    });
  }
  openCollapse1.value = true;
  openCollapse2.value = true;
  openCollapse3.value = true;
};
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
const closeModal = () => {
  emit("closeModal");
};
</script>
<style lang="scss">
.policy_info_page {
  min-height: max-content;

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

        & > p:not(:has(.not_)):after {
          content: "*";
          font-size: 1.5rem;
          margin-left: 4px;
          color: var(--error);
          font-weight: 500;
          line-height: 2.1rem;
          letter-spacing: -0.02em;
        }

        & > p.not_:after {
          content: "";
        }

        .policy_field_data {
          width: 100%;
          max-width: 600px;
          color: black !important;
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
  .policy_info_page {
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
.field_data_black {
}
</style>
