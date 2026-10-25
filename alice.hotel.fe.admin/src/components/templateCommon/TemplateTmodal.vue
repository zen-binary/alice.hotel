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
      <!-- Khi 2 cột -->
      <hr />
      <button class="button btn_md btn_primary" @click="modalOpen = true">Show modal</button>
      <TModal :is-open="modalOpen" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
        <!-- Modal content goes here -->
        <template #title>
          <strong>Đồng ý thu thập và sử dụng thông tin cá nhân</strong>
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
          <button type="button" class="button btn_xl btn_white popup_close" @click="confirmAction">Đóng</button>
          <button type="button" class="button btn_xl btn_primary" @click="cancelAction">Xác nhận</button>
        </template>
      </TModal>

      <hr />
      <button class="button btn_md btn_primary" @click="modalOpenConfirm = true">Show modal confirm</button>
      <TModalConfirm :is-open="modalOpenConfirm" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
        <!-- Modal content goes here -->
        <template #title>
          <strong>Đồng ý thu thập và sử dụng thông tin cá nhân</strong>
        </template>
        <template #default>
          <div class="box_section">
            <p>Confirm content</p>
          </div>
        </template>
        <template #footer>
          <!-- Footer content goes here -->
          <button type="button" class="button btn_md btn_white popup_close" @click="confirmAction">Đóng</button>
          <button type="button" class="button btn_md btn_primary" @click="cancelAction">Xác nhận</button>
        </template>
      </TModalConfirm>

      <hr />
      <button class="button btn_md btn_primary" @click="modalOpenAlert = true">Show modal alert</button>
      <TModalAlert :is-open="modalOpenAlert" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
        <!-- Modal content goes here -->
        <template #title>
          <strong>Đồng ý thu thập và sử dụng thông tin cá nhân</strong>
        </template>
        <template #default>
          <div class="box_section">
            <p>Alert content</p>
          </div>
        </template>
        <template #footer>
          <!-- Footer content goes here -->
          <button type="button" class="button btn_md btn_primary" @click="cancelAction">Xác nhận</button>
        </template>
      </TModalAlert>

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
import TModalConfirm from "@/components/common/excel/TModalConfirm.vue";
import TModalAlert from "@/components/common/excel/TModalAlert.vue";

const modalOpen = ref(false);
const modalOpenConfirm = ref(false);
const modalOpenAlert = ref(false);
const picker1 = ref("");
const modalId = ref("modalId");
const selectBoxValueDisable = ref("11");
const selectBoxValue = ref("11");
const selectSearch = ref("12");

const listSelectBox = ref([
  { cdId: "11", cdNm: "Mẫu 1", upCdId: "1" },
  { cdId: "12", cdNm: "Mẫu 2", upCdId: "1" },
  { cdId: "13", cdNm: "Mẫu 3", upCdId: "1" },
  { cdId: "14", cdNm: "Mẫu 4", upCdId: "1" },
  { cdId: "15", cdNm: "Mẫu 5", upCdId: "1" },
]);

const closeModal = () => {
  modalOpen.value = false;
  modalOpenConfirm.value = false;
  modalOpenAlert.value = false;
};
const confirmAction = () => {
  closeModal();
};
const cancelAction = () => {
  closeModal();
};
</script>

<style lang="scss" scoped></style>
