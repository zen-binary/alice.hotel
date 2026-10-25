<template>
  <TModal
    class-size="xlarge"
    :is-open="isOpen"
    :is-show-header="true"
    :is-show-footer="false"
    :modal-id="'modalId'"
    @close-modal="closeModal"
  >
    <!-- :is-open="modalOpen" -->
    <template #title>
      <strong>{{ t("preTest.titleModal") }}</strong>
    </template>
    <template #default>
      <div
        id="corporate_pre_test"
        class="box_section"
      >
        <div class="btn_group btn_end">
          <ButtonExportPDFV1
            id="corporate_pre_test"
            class="button btn_md btn_white"
            name-export="corporate_pre_test"
          />
        </div>
        <div class="box_section">
          <p class="box_title_sm">
            {{ t("preTest.title") }}
          </p>
          <p class="box_title_sm">
            {{ t("preTest.purpose") }}
          </p>
          <div style="margin-bottom: 20px">
            <p>
              {{ t("preTest.text1") }}
            </p>
            <p>
              {{ t("preTest.text2") }}
            </p>
          </div>

          <p class="box_title_sm">
            {{ t("preTest.titleTable") }}
          </p>
          <div class="tbl tbl_row tbl_scroll scrollx_tbl_xs">
            <table class="table_head">
              <colgroup>
                <col style="width: 15%">
                <col style="width: 35%">
                <col style="width: 15%">
                <col style="width: 35%">
              </colgroup>
              <tbody>
                <tr>
                  <th
                    scope="row"
                  >
                    {{ t("preTest.nameTraining") }}
                  </th>
                  <td>
                    <ul class="list_ul">
                      <li>
                        {{ corporateMemberReqDTO.cormNameKr }}
                      </li>
                    </ul>
                  </td>
                  <th scope="row">
                    {{ t("preTest.classificationCode") }}
                  </th>
                  <td>
                    <ul class="list_ul">
                      <li>
                        <InputBase
                          :id="'standardIndustryType'"
                          v-model="corporateMemberReqDTO.standardIndustryType"
                          :name="'standardIndustryType'"
                          :is-detail="true"
                        />
                      </li>
                    </ul>
                  </td>
                </tr>

                <tr>
                  <th scope="row">
                    {{ t("preTest.businessType") }}
                  </th>
                  <td>
                    <ul class="list_ul">
                      <li>
                        {{ corporateMemberReqDTO.cormType }}
                      </li>
                    </ul>
                  </td>

                  <th scope="row">
                    {{ t("preTest.industry") }}
                  </th>
                  <td>
                    <ul class="list_ul">
                      <li>
                        {{ corporateMemberReqDTO.cormCategories }}
                      </li>
                    </ul>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="box_section">
          <p class="box_title_sm">
            {{ t("preTest.titleTable2") }}
          </p>
          <div class="tbl tbl_col tbl_scroll scrollx_tbl_xs scrolly_tbl_xs">
            <table class="table_head">
              <colgroup>
                <col style="width: 10%">
                <col style="width: 40%">
                <col style="width: 50%">
              </colgroup>
              <thead>
                <th scope="row" />
                <th scope="row">
                  {{ t("preTest.checkList") }}
                </th>
                <th scope="row">
                  {{ t("preTest.content") }}
                </th>
              </thead>

              <tbody>
                <tr
                  v-for="(item, index) in preTestCategoryResDTOCus"
                  :key="index"
                >
                  <th
                    scope="row"
                    style="text-align: center;"
                  >
                    {{ item.categoryIndex }}
                  </th>
                  <th
                    scope="row"
                  >
                    {{ item.content }}
                  </th>
                  <th scope="row">
                    <div
                      v-for="(detail, detailIndex) in item.preTestDetailResDTOList"
                      :key="detailIndex"
                      class="select_checkbox"
                    >
                      <CheckBoxBase
                        :id="detail.preTestDetailSeq"
                        :key="detailIndex + index"
                        v-model="seqList"
                        :name="detail.preTestDetailSeq"
                        :value="detail.preTestDetailSeq"
                        :mode="'show'"
                        :label="detail.content"
                        :is-disable="true"
                        :checked="seqList.includes(detail.preTestDetailSeq)"
                      />
                      <InputBase
                        v-if="detail.addContentYn === 'Y'"
                        :id="index + detail.preTestDetailSeq"
                        v-model="detail.contentDetail"
                        :name="index + detail.preTestDetailSeq"
                        style="max-width: 500px; margin-bottom: 5px"
                        :disabled="true"
                        :value="detail.contentDetail"
                      />
                    </div>
                  </th>
                </tr>
                <tr>
                  <th
                    scope="row"
                    style="text-align: center;"
                  >
                    {{ finalPreTestCategoryResDTO.categoryIndex }}
                  </th>
                  <th
                    scope="row"
                  >
                    {{ finalPreTestCategoryResDTO.content }}
                  </th>
                  <th scope="row">
                    <div
                      v-for="(detail1, detailIndex1) in preTestDetailModel"
                      :key="detailIndex1"
                    >
                      <TextArea
                        :id="detail1.preTestDetailSeq"
                        v-model="detail1.contentDetail"
                        style="max-width: 500px"
                        :name="detail1.preTestDetailSeq"
                        :disabled="true"
                      />
                    </div>
                  </th>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </template>
  </TModal>
</template>

<script lang="ts" setup>
import type {
  PreTestDetailSelectResDTO,
  PreTestCategoryResDTO,
  CorporateMemberReqDTO,
} from "../../../stores/preTest/preTest.type";
import { ref, watch } from "vue";
import CheckBoxBase from "@/components/common/input/CheckboxBase.vue";
import TextArea from "@/components/common/input/TextArea.vue";
import InputBase from "@/components/common/input/InputBase.vue";
import { useI18n } from "vue-i18n";
import ButtonExportPDFV1 from "@/components/common/ButtonExportPDFV1.vue";
import { preTestStore } from "@/stores/preTest";

const { t } = useI18n();
const seqList = ref([]);
const corPreTestStore = preTestStore();
const corporateMemberReqDTO = ref({} as CorporateMemberReqDTO);
const preTestDetailSelectModel = ref({} as PreTestDetailSelectResDTO);
const preTestCategoryResDTO = ref({} as PreTestCategoryResDTO);
const preTestCategoryResDTOCus = ref([]);
const finalPreTestCategoryResDTO = ref({} as PreTestCategoryResDTO);
const preTestDetailModel = ref([]);
const modalOpen = ref(false);
const props = defineProps<{
  internPlanSeq: string;
  isOpen: boolean;
}>();

async function getAllDataList() {
  await corPreTestStore.getAllData({
    method: "POST",
    body: {internPlanSeq: props.internPlanSeq},
  });
  corporateMemberReqDTO.value = corPreTestStore.corporateMemberReqDTO?.data;  
  preTestDetailSelectModel.value = corPreTestStore.corporateMemberReqDTO?.data.preTestDetailSelectResDTOList;
  preTestCategoryResDTO.value = corPreTestStore.corporateMemberReqDTO?.data.preTestCategoryResDTOList;
  preTestCategoryResDTOCus.value = preTestCategoryResDTO.value?.slice(0, preTestCategoryResDTO.value.length - 1);  
  finalPreTestCategoryResDTO.value = preTestCategoryResDTO.value?.[preTestCategoryResDTO.value.length - 1];
  preTestDetailModel.value = finalPreTestCategoryResDTO.value.preTestDetailResDTOList;

  if (preTestCategoryResDTO.value != null) {
    preTestCategoryResDTO.value.forEach((element) => {
      element.preTestDetailResDTOList.forEach((el) => {     
        if (el.checkYn == "Y") {
          seqList.value.push(el.preTestDetailSeq);
        }
      });
    });
  }
}

function closeModal() {
  modalOpen.value = false;
}
watch(
  () => props.isOpen,
  async (newVal) => {
    if (newVal) {
      await getAllDataList();
    } else {
      seqList.value = [];
    }
  },
);

// defineExpose({
//   openModal,
// });

</script>
