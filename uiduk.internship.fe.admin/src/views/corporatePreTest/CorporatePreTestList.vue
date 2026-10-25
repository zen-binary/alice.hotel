<template>
  <TModal classSize="large" :is-open="modalOpen" :is-show-header="true" :is-show-footer="true" :modal-id="'modalId'" @close-modal="closeModal">
    <!-- :is-open="modalOpen" -->
    <template #title>
      <strong>{{ t("corporatePreTestList.titleModal") }}</strong>
    </template>
    <template #default>
      <div id="corporate_pre_test" class="box_section">
        <div class="styleBox">
          <p class="box_title_sm"></p>
          <div class="btn_group btn_end">
            <button type="button" class="button btn_md btn_primary hidden_print" @click="showModal()" :disabled="!valiSave">
              {{ t("common.save") }}
            </button>
            <ButtonExportPDFV1 class="button btn_md btn_white" id="corporate_pre_test" :name-export="'corporate_pre_test'" />
          </div>
        </div>
        <div class="box_section">
          <p class="box_title_sm">{{ t("corporatePreTestList.title") }}</p>
          <p class="box_title_sm">{{ t("corporatePreTestList.purpose") }}</p>
          <div style="margin-bottom: 20px">
            <p>
              {{ t("corporatePreTestList.text1") }}
            </p>
            <p>
              {{ t("corporatePreTestList.text2") }}
            </p>
          </div>

          <p class="box_title_sm">{{ t("corporatePreTestList.titleTable") }}</p>
          <div class="tbl tbl_row tbl_scroll scrollx_tbl_xs">
            <table class="table_head">
              <colgroup>
                <col style="width: 18%" />
                <col style="width: 32%" />
                <col style="width: 18%" />
                <col style="width: 32%" />
              </colgroup>
              <tbody>
                <tr>
                  <th scope="row">{{ t("corporatePreTestList.nameTraining") }}</th>
                  <td>
                    <ul class="list_ul">
                      <li>{{ corporateMemberInfoModel.cormNameKr }}</li>
                    </ul>
                  </td>
                  <th scope="row" class="required">{{ t("corporatePreTestList.classificationCode") }}</th>
                  <td>
                    <ul class="list_ul">
                      <li>
                        <InputBase
                          :id="'standardIndustryType'"
                          :name="'standardIndustryType'"
                          v-model="corporateMemberInfoModel.standardIndustryType"
                        ></InputBase>
                      </li>
                    </ul>
                  </td>
                </tr>

                <tr>
                  <th scope="row">{{ t("corporatePreTestList.businessType") }}</th>
                  <td>
                    <ul class="list_ul">
                      <li>
                        {{ corporateMemberInfoModel.cormType }}
                      </li>
                    </ul>
                  </td>

                  <th scope="row">{{ t("corporatePreTestList.industry") }}</th>
                  <td>
                    <ul class="list_ul">
                      <li>
                        {{ corporateMemberInfoModel.cormCategories }}
                      </li>
                    </ul>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="box_section">
          <p class="box_title_sm">{{ t("corporatePreTestList.titleTable2") }}</p>
          <div class="tbl tbl_col tbl_scroll scrollx_tbl_xs scrolly_tbl_xs">
            <table class="table_head">
              <colgroup>
                <col style="width: 15%" />
                <col style="width: 35%" />
                <col style="width: 50%" />
              </colgroup>
              <thead>
                <th scope="row"></th>
                <th scope="row">{{ t("corporatePreTestList.checkList") }}</th>
                <th scope="row">{{ t("corporatePreTestList.content") }}</th>
              </thead>

              <tbody>
                <tr v-for="(item, index) in preTestCategoryModelCus" :key="index">
                  <th scope="row" class="text_center">{{ item.categoryIndex }}</th>
                  <th scope="row">{{ item.content }}</th>
                  <th scope="row">
                    <div class="select_checkbox" v-for="(detail, detailIndex) in item.preTestDetailDTOList" :key="detailIndex">
                      <CheckBoxBase
                        :key="detailIndex + index"
                        :id="detail.preTestDetailSeq"
                        :value="detail.preTestDetailSeq"
                        :mode="'show'"
                        :name="index"
                        :label="detail.content"
                        v-model="seqList"
                        @change="handleChange"
                        :isDisable="index + 2 === preTestCategoryModel.length && !seqList.includes(ptds20)"
                        :checked="seqList.includes(detail.preTestDetailSeq)"
                      />
                      <InputBase
                        v-if="detail.addContentYn === STATUS_YES"
                        :id="index + detail.preTestDetailSeq"
                        :name="index + detail.preTestDetailSeq"
                        v-model="detail.contentDetail"
                        style="width: 400px; margin-bottom: 5px"
                        :disabled="!(seqList.includes(detail.preTestDetailSeq) && detail.addContentYn === STATUS_YES)"
                      ></InputBase>
                    </div>
                  </th>
                </tr>
                <tr>
                  <th scope="row" class="text_center">{{ finalPreTestCategoryModel.categoryIndex }}</th>
                  <th scope="row">{{ finalPreTestCategoryModel.content }}</th>
                  <th scope="row">
                    <div class="select_checkbox" v-for="(detail1, detailIndex1) in preTestDetailModel" :key="detailIndex1">
                      <TextArea
                        class="wtd_400"
                        :id="detail1.preTestDetailSeq"
                        :name="detail1.preTestDetailSeq"
                        @change="checkValue(detail1.preTestDetailSeq)"
                        v-model="detail1.contentDetail"
                      ></TextArea>
                    </div>
                  </th>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </template>
    <template #footer>
      <div></div>
    </template>
  </TModal>

  <TModalConfirm :is-open="modalOpenConfirm" :is-show-header="true" :is-show-footer="true" :modal-id="modalId">
    <template #default>
      <div class="box_section">
        <p>{{ t("common.message.save") }}</p>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_md btn_primary" @click="saveData">{{ t("common.confirm") }}</button>
      <button type="button" class="button btn_md btn_white popup_close" @click="closeModalConfirm">{{ t("common.cancel") }}</button>
    </template>
  </TModalConfirm>

  <TModalAlert :is-open="modalOpenAlert" :is-show-header="true" :is-show-footer="true">
    <template #default>
      <div class="box_section">
        <p>{{ messAlert }}</p>
      </div>
    </template>
    <template #footer>
      <button type="button" class="button btn_md btn_primary" @click="closeModalAlert">{{ t("common.confirm") }}</button>
    </template>
  </TModalAlert>
</template>

<script lang="ts" setup>
import { corporatePreTestStore } from "../../stores/corporatePreTest";
import type { PreTestDetailSelectModel, PreTestCategoryModel, CorporateMemberInfoModel } from "../../stores/corporatePreTest/corporatePreTest.type";
import { ref, watch, computed } from "vue";
import CheckBoxBase from "@/components/common/input/CheckboxBase.vue";
import TextArea from "@/components/common/input/TextArea.vue";
import InputBase from "@/components/common/input/InputBase.vue";
import { useI18n } from "vue-i18n";
import { SUCCSESS_STATUS_CREATE } from "../../constants/screen.const";
import { STATUS_YES, STATUS_NO, ACCESS_TOKEN } from "@/constants/common.const";
import ButtonExportPDFV1 from "@/components/common/ButtonExportPDFV1.vue";

const { t } = useI18n();
const seqList = ref([]);
const corPreTestStore = corporatePreTestStore();
const corporateMemberInfoModel = ref({} as CorporateMemberInfoModel);
const newCorporateMemberInfoModel = ref({} as CorporateMemberInfoModel);
const preTestDetailSelectModel = ref({} as PreTestDetailSelectModel);
const preTestCategoryModel = ref({} as PreTestCategoryModel);
const preTestCategoryModelCus = ref([]);
const finalPreTestCategoryModel = ref({} as PreTestCategoryModel);
const preTestDetailModel = ref([]);
const modalOpen = ref(false);
const modalOpenConfirm = ref(false);
const modalOpenAlert = ref(false);
const messAlert = ref("");
const ptds20 = ref("PTDS0000000000000020");
const ptds21 = ref("PTDS0000000000000021");
const ptds22 = ref("PTDS0000000000000022");
const ptds23 = ref("PTDS0000000000000023");
const isGoToList = ref(true);
const planId = ref("");

const openModal = (internPlanId: string) => {
  planId.value = internPlanId;
  getAllDataList();
  seqList.value = [];

  modalOpen.value = true;
};
const modalId = ref("corpPreTest");

async function getAllDataList() {
  await corPreTestStore.getAllData({
    method: "POST",
    body: planId.value,
  });
  corporateMemberInfoModel.value = corPreTestStore.corporateMemberInfoModel?.data;
  preTestDetailSelectModel.value = corPreTestStore.corporateMemberInfoModel?.data.preTestDetailSelectDTOList;
  preTestCategoryModel.value = corPreTestStore.corporateMemberInfoModel?.data.preTestCategoryDTOList;
  preTestCategoryModelCus.value = preTestCategoryModel.value?.slice(0, preTestCategoryModel.value.length - 1);
  finalPreTestCategoryModel.value = preTestCategoryModel.value?.[preTestCategoryModel.value.length - 1];
  preTestDetailModel.value = finalPreTestCategoryModel.value?.preTestDetailDTOList;

  if (preTestCategoryModel.value != null) {
    preTestCategoryModel.value.forEach((element) => {
      element.preTestDetailDTOList.forEach((el) => {
        if (el.checkYn == STATUS_YES) {
          seqList.value.push(el.preTestDetailSeq);
        }
      });
    });
  }
}

const checkValue = (data: any) => {
  finalPreTestCategoryModel.value.preTestDetailDTOList.forEach((element) => {
    if (element.preTestDetailSeq == data) {
      if (element.contentDetail.length > 0) {
        element.checkYn = STATUS_YES;
      } else {
        element.checkYn = STATUS_NO;
      }
    }
  });
};

async function saveData() {
  if (valiSave) {
    preTestCategoryModelCus.value.forEach((parent) => {
      parent.preTestDetailDTOList.forEach((detail) => {
        if (seqList.value.includes(detail.preTestDetailSeq)) {
          detail.checkYn = STATUS_YES;
        } else {
          detail.checkYn = null;
        }
      });
    });

    newCorporateMemberInfoModel.value.cormCategories = corporateMemberInfoModel.value.cormCategories;
    newCorporateMemberInfoModel.value.cormNameEn = corporateMemberInfoModel.value.cormNameEn;
    newCorporateMemberInfoModel.value.cormNameKr = corporateMemberInfoModel.value.cormNameKr;
    newCorporateMemberInfoModel.value.cormSeq = corporateMemberInfoModel.value.cormSeq;
    newCorporateMemberInfoModel.value.cormType = corporateMemberInfoModel.value.cormType;
    newCorporateMemberInfoModel.value.corporatePreTestSeq = corporateMemberInfoModel.value.corporatePreTestSeq;
    newCorporateMemberInfoModel.value.internPlanSeq = corporateMemberInfoModel.value.internPlanSeq;
    newCorporateMemberInfoModel.value.standardIndustryType = corporateMemberInfoModel.value.standardIndustryType;

    newCorporateMemberInfoModel.value.preTestCategoryDTOList = preTestCategoryModelCus.value;
    newCorporateMemberInfoModel.value.preTestCategoryDTOList.push(finalPreTestCategoryModel.value);

    await corPreTestStore.createData({
      method: "POST",
      body: newCorporateMemberInfoModel.value,
    });
    if (corPreTestStore.status.value == SUCCSESS_STATUS_CREATE) {
      handleCloseAction();
    }
  } else {
    dataEmrtyAction();
  }
}

async function closeModal() {
  modalOpen.value = false;
}

const closeModalConfirm = () => {
  modalOpenConfirm.value = false;
};

const closeModalAlert = () => {
  modalOpenAlert.value = false;
  if (isGoToList.value) {
    closeModal();
  }
};

const handleChange = (event) => {
  if (
    event.target.value ===
    preTestCategoryModel.value[preTestCategoryModel.value.length - 3].preTestDetailDTOList[
      preTestCategoryModel.value[preTestCategoryModel.value.length - 3].preTestDetailDTOList.length - 1
    ].preTestDetailSeq
  ) {
    if (!seqList.value.includes(event.target.value)) {
      if (seqList.value.includes(ptds21.value)) {
        seqList.value.splice(seqList.value.indexOf(ptds21.value), 1);
      }
      if (seqList.value.includes(ptds22.value)) {
        seqList.value.splice(seqList.value.indexOf(ptds22.value), 1);
      }
    }
  }
};

const isCheck = computed(() => {
  return Object.keys(preTestCategoryModel.value || {}).map((categoryKey) => {
    const category = preTestCategoryModel.value[categoryKey];
    return (category.preTestDetailDTOList || []).map((detail) => {
      return !(seqList.value.includes(detail.preTestDetailSeq) && detail.addContentYn === STATUS_YES);
    });
  });
});

const valiSave = computed(() => {
  if (
    corporateMemberInfoModel.value.standardIndustryType == ACCESS_TOKEN ||
    (seqList.value.length == 0 && preTestDetailModel.value[0]?.contentDetail == null)
  ) {
    return false;
  } else {
    if (preTestDetailModel.value[0]?.contentDetail != null && preTestDetailModel.value[0]?.contentDetail.length == 0) {
      return false;
    } else {
      return true;
    }
  }
});

watch(
  isCheck,
  (newValues) => {
    newValues.forEach((categoryValues, categoryIndex) => {
      categoryValues.forEach((shouldClear, detailIndex) => {
        if (shouldClear && preTestCategoryModel.value[categoryIndex].preTestDetailDTOList[detailIndex].preTestDetailSeq != ptds23.value) {
          preTestCategoryModel.value[categoryIndex].preTestDetailDTOList[detailIndex].contentDetail = null;
        }
      });
    });
  },
  valiSave,
  { deep: true },
);

defineExpose({
  openModal,
});

function handleCloseAction() {
  isGoToList.value = true;
  messAlert.value = t("common.message.saveSuccess");
  modalOpenConfirm.value = false;
  modalOpenAlert.value = true;
}

function dataEmrtyAction() {
  isGoToList.value = false;
  messAlert.value = t("corporatePreTestList.validateForm");
  modalOpenConfirm.value = false;
  modalOpenAlert.value = true;
}

function showModal() {
  modalOpenConfirm.value = true;
}
</script>
<style scoped lang="scss">
.styleBox {
  display: flex;
  justify-content: space-between;
}

.wtd_400 {
  width: 400px;
}
.text_center {
  text-align: center;
}
</style>
