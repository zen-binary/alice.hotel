<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div>
    <div class="box dp_block">
      <div class="box_section">
        <h2 class="box_title">Các trạng thái component</h2>
        <hr />
        <div class="">
          <div class="box_section">
            <h3 class="box_title">Form validate</h3>
            <hr />
            <p>List required : {{ store.arrRequired }}</p>
            <p>Check required : {{ store.check }}</p>
            <strong class="required">Note</strong>
            <span>Error when use placehover + required but value is null</span>
            <br />
            <strong class="required">Note</strong>
            <span>Validate when exist id and required</span>
            <br />
            <hr />
            <div style="display: flex; flex-direction: column; gap: 10px">
              <InputBase id="input_validate" v-model="inputValue" required />
              <TextArea id="text_validate" v-model="textValue" required />
              <BaseDatePicker id="date_validate" v-model="date" required />
              <MonthDatePicker id="month_date_validate" v-model="picker1" required />
              <hr />
              <strong class="required">Note</strong>
              <span>Default value follow v-model value - {{ selectBoxValue }}</span>
              <br />
              <hr />
              <div class="search_box col_3">
                <ul>
                  <li>
                    <p>Select box with checkbox</p>
                  </li>
                  <li>
                    <p>Value: {{ selectBoxValueValidate1 }}</p>
                  </li>
                  <li>
                    <SelectBoxBaseCheckBox id="selecbox_1" v-model="selectBoxValueValidate1" required :data="listSelectBoxValidate" />
                  </li>
                  <li>
                    <p>Select box with search</p>
                  </li>
                  <li>
                    <p>Value: {{ selectBoxValueValidate2 }}</p>
                  </li>
                  <li>
                    <SelectBoxBaseSearch id="selectbox2" v-model="selectBoxValueValidate2" required :data="listSelectBoxValidate" />
                  </li>
                  <li>
                    <p>Select box</p>
                  </li>
                  <li>
                    <p>Value: {{ selectBoxValueValidate3 }}</p>
                  </li>
                  <li>
                    <SelectBoxBase id="selectbox3" v-model="selectBoxValueValidate3" required name="selectbox" :data="listSelectBoxValidate" />
                  </li>
                  <li>
                    <p>Select box</p>
                  </li>
                  <li>
                    <p>Value: {{ selectBoxValueValidate4 }}</p>
                  </li>
                  <li>
                    <SelectBoxBase id="selectbox4" v-model="selectBoxValueValidate4" required name="selectbox" :data="listSelectBoxValidate1" />
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div style="display: flex; flex-direction: column; gap: 10px">
          <h3 class="box_title">Default</h3>
          <InputBase v-model="inputValue" />
          <TextArea v-model="textValue" />
          <BaseDatePicker v-model="date" />
          <MonthDatePicker v-model="picker1" />
          <div class="search_box col_3">
            <ul>
              <li>
                <p>Select box with checkbox</p>
              </li>
              <li>
                <p>Value: {{ selectSearch }}</p>
              </li>
              <li>
                <SelectBoxBaseCheckBox v-model="selectSearch" :data="listSelectBox" />
              </li>
              <li>
                <p>Select box with search</p>
              </li>
              <li>
                <p>Value: {{ selectSearch1 }}</p>
              </li>
              <li>
                <SelectBoxBaseSearch v-model="selectSearch1" :data="listSelectBox" />
              </li>
              <li>
                <p>Select box</p>
              </li>
              <li>
                <p>Value: {{ selectBoxValue }}</p>
              </li>
              <li>
                <SelectBoxBase id="selectbox" v-model="selectBoxValue" name="selectbox" :data="listSelectBox" />
              </li>
              <li>
                <p>Select box with check all</p>
              </li>
              <li>
                <p>Value: {{ selectSearchCheckAll }}</p>
              </li>
              <li>
                <SelectBoxBaseCheckBox v-model="selectSearchCheckAll" :data="listSelectBox" :is-show-check-all="true" />
              </li>
            </ul>
          </div>
        </div>
        <hr />
        <div style="display: flex; flex-direction: column; gap: 10px">
          <h3 class="box_title">Disable</h3>
          <InputBase :value="inputValueState" disabled />
          <TextArea :value="textValueState" disabled />
          <BaseDatePicker v-model="date" disabled />
          <MonthDatePicker v-model="picker1" disabled />
          <div class="search_box col_3">
            <ul>
              <li>
                <p>Select box with checkbox disable</p>
              </li>
              <li>
                <p>Value: {{ selectSearch }}</p>
              </li>
              <li>
                <SelectBoxBaseCheckBox v-model="selectSearch" :data="listSelectBox" disabled />
              </li>
              <li>
                <p>Select box with search disable</p>
              </li>
              <li>
                <p>Value: {{ selectSearch1 }}</p>
              </li>
              <li>
                <SelectBoxBaseSearch v-model="selectSearch1" :data="listSelectBox" disabled />
              </li>
              <li>
                <p>Select box disable</p>
              </li>
              <li>
                <p>Value: {{ selectBoxValue }}</p>
              </li>
              <li>
                <SelectBoxBase id="selectbox" v-model="selectBoxValue" name="selectbox" :data="listSelectBox" disabled />
              </li>
            </ul>
          </div>
        </div>
        <hr />
        <div style="display: flex; flex-direction: column; gap: 10px">
          <h3 class="box_title">Readonly</h3>
          <InputBase :value="inputValueState" readonly />
          <TextArea :value="textValueState" readonly />
          <BaseDatePicker v-model="dateState" readonly />
          <MonthDatePicker v-model="dateState" readonly />
          <div class="search_box col_3">
            <ul>
              <li>
                <p>Select box with checkbox</p>
              </li>
              <li>
                <p>Value: {{ selectSearch }}</p>
              </li>
              <li>
                <SelectBoxBaseCheckBox v-model="selectSearch" :data="listSelectBox" readonly />
              </li>
              <li>
                <p>Select box with search</p>
              </li>
              <li>
                <p>Value: {{ selectSearch1 }}</p>
              </li>
              <li>
                <SelectBoxBaseSearch v-model="selectSearch1" :data="listSelectBox" readonly />
              </li>
              <li>
                <p>Select box</p>
              </li>
              <li>
                <p>Value: {{ selectBoxValue }}</p>
              </li>
              <li>
                <SelectBoxBase id="selectbox" v-model="selectBoxValue" name="selectbox" :data="listSelectBox" readonly />
              </li>
            </ul>
          </div>
        </div>
        <hr />
        <div style="display: flex; flex-direction: column; gap: 10px">
          <h3 class="box_title">Mode detail</h3>
          <div class="search_box col_2">
            <ul>
              <li>
                <p>Input detail</p>
              </li>
              <li>
                <InputBase v-model="inputValueState" :is-detail="true" />
              </li>
              <li>
                <p>Text area detail</p>
              </li>
              <li>
                <TextArea v-model="textValueState" :is-detail="true" />
              </li>
              <li>
                <p>Base Date detail</p>
              </li>
              <li>
                <BaseDatePicker v-model="dateState" :is-detail="true" />
              </li>
              <li>
                <p>Month Date detail</p>
              </li>
              <li>
                <MonthDatePicker v-model="dateState" :is-detail="true" />
              </li>
              <li>
                <p>Select box with checkbox detail</p>
              </li>
              <li>
                <SelectBoxBaseCheckBox v-model="selectBoxDisable" :data="listSelectBox" :is-detail="true" />
              </li>
              <li>
                <p>Select box with search detail</p>
              </li>
              <li>
                <SelectBoxBaseSearch v-model="selectSearchDisable" :data="listSelectBox" :is-detail="true" />
              </li>
              <li>
                <p>Select box detail</p>
              </li>
              <li>
                <SelectBoxBase
                  id="selectbox"
                  v-model="selectBoxValueDisable"
                  class="select_box"
                  name="selectbox"
                  :data="listSelectBox"
                  :is-detail="true"
                />
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <div class="box dp_block">
      <div class="box_section">
        <h3 class="box_title">FORM CONTROL</h3>
      </div>
      <div class="box_section">
        <p class="box_title_sm">Col4</p>
        <div class="search_box col_4">
          <ul>
            <li>
              <p class="">Col4-1</p>
              <span>Something</span>
            </li>
            <li>
              <p class="">Col4-2</p>
              <span>Something</span>
            </li>
            <li>
              <p class="">Col4-3</p>
              <span>Something</span>
            </li>
            <li>
              <p class="">Col4-4</p>
              <span>Something</span>
            </li>
          </ul>
        </div>
      </div>
      <div class="box_section">
        <p class="box_title_sm">Col3</p>
        <div class="search_box col_3">
          <ul>
            <li>
              <p>Col3-1</p>
              <span>Something</span>
            </li>
            <li>
              <p>Col3-2</p>
              <span>Something</span>
            </li>
            <li>
              <p>Col3-3</p>
              <div class="search_flex">
                <p>Something</p>
                <p>~</p>
                <p>Something</p>
              </div>
            </li>
          </ul>
        </div>
      </div>
      <div class="box_section">
        <p class="box_title_sm">Col2</p>
        <div class="search_box col_2">
          <ul>
            <li>
              <p>Col2-1</p>
              <p>Something</p>
            </li>
            <li>
              <p>Col2-2</p>
              <p>Something</p>
            </li>
          </ul>
        </div>
      </div>
      <div class="box_section">
        <p class="box_title_sm">SELECT BOX</p>
        <div class="search_box col_3">
          <ul>
            <li>
              <p>Select box with checkbox</p>
            </li>
            <li>
              <p>Value: {{ selectSearch }}</p>
            </li>
            <li>
              <SelectBoxBaseCheckBox v-model="selectSearch" :data="listSelectBox" />
            </li>
            <li>
              <p>Select box with search</p>
            </li>
            <li>
              <p>Value: {{ selectSearch1 }}</p>
            </li>
            <li>
              <SelectBoxBaseSearch v-model="selectSearch1" :data="listSelectBox" />
            </li>
            <li>
              <p>Select box</p>
            </li>
            <li>
              <p>Value: {{ selectBoxValue }}</p>
            </li>
            <li>
              <SelectBoxBase id="selectbox" v-model="selectBoxValue" name="selectbox" :data="listSelectBox" />
            </li>
            <li>
              <p>Select box disable</p>
            </li>
            <li>
              <p>Value: {{ selectBoxValue1 }}</p>
            </li>
            <li>
              <SelectBoxBase id="selectbox" v-model="selectBoxValue1" name="selectbox" :data="listSelectBox" />
            </li>
          </ul>
        </div>
        <hr />
        <div class="search_box col_3">
          <ul>
            <li>
              <p>Select box with checkbox disable</p>
              <SelectBoxBaseCheckBox v-model="selectBoxDisable" :data="listSelectBox" :is-detail="true" />
            </li>

            <li>
              <p>Select box with search disable</p>
              <SelectBoxBaseSearch v-model="selectSearchDisable" :data="listSelectBox" :is-detail="true" />
            </li>
            <li>
              <p>Select box disable</p>
              <SelectBoxBase
                id="selectbox"
                v-model="selectBoxValueDisable"
                class="select_box"
                name="selectbox"
                :data="listSelectBox"
                :is-detail="true"
              />
            </li>
            <li>
              <p>Select box with check all</p>
            </li>
            <li>
              <p>Value: {{ selectSearchCheckAll }}</p>
            </li>
            <li>
              <SelectBoxBaseCheckBox v-model="selectSearchCheckAll" :data="listSelectBox" :is-show-check-all="true" />
            </li>
          </ul>
        </div>
      </div>
      <div class="box_section">
        <h3 class="box_title">Date picker</h3>
        <div class="search_box col_4">
          <ul>
            <li>
              <p class="">Date picker</p>
              <BaseDatePicker v-model="picker1" />
            </li>
            <li>
              <p class="">Month Date picker</p>
              <MonthDatePicker v-model="picker2" />
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import BaseDatePicker from "@/components/common/input/BaseDatePicker.vue";
import MonthDatePicker from "@/components/common/input/MonthDatePicker.vue";
import InputBase from "@/components/common/input/InputBase.vue";
import SelectBoxBaseSearch from "@/components/common/input/SelectBoxBaseSearch.vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import SelectBoxBaseCheckBox from "@/components/common/input/SelectBoxBaseCheckBox.vue";
import { ref } from "vue";
import CodeMngModel from "@/model/common/CodeMngModel.js";
import { commonStore } from "@/stores/common.js";

//Validate
const inputValue = ref("");
const textValue = ref("");
const date = ref("");
const selectBoxValueValidate1 = ref("");
const selectBoxValueValidate2 = ref("");
const selectBoxValueValidate3 = ref("");
const selectBoxValueValidate4 = ref("");
const listSelectBoxValidate = ref<CodeMngModel[]>([
  { cdId: "21", cdNm: "비게", upCdId: "1" },
  { cdId: "22", cdNm: "비게시", upCdId: "1" },
  { cdId: "23", cdNm: "비게시1", upCdId: "1" },
  { cdId: "24", cdNm: "비게시2", upCdId: "1" },
  { cdId: "25", cdNm: "비게시비게시비게시2", upCdId: "1" },
]);
const listSelectBoxValidate1 = ref<CodeMngModel[]>([
  { cdId: "", cdNm: "All", upCdId: "1" },
  { cdId: "22", cdNm: "비게시", upCdId: "1" },
  { cdId: "23", cdNm: "비게시1", upCdId: "1" },
  { cdId: "24", cdNm: "비게시2", upCdId: "1" },
  { cdId: "25", cdNm: "비게시비게시비게시2", upCdId: "1" },
]);

//store
const store = commonStore();

//date
const picker1 = ref(new Date());
const picker2 = ref(new Date());
const dateState = ref(new Date());

//input
const inputValueState = ref("Disable + readonly");
const textValueState = ref("Disable + readonly");

// Select box
const selectBoxValueDisable = ref("");
const selectBoxValue = ref("12");
const selectBoxValue1 = ref("");
const selectSearch = ref("12");
const selectSearch1 = ref("12");
const selectSearchDisable = ref("15");
const selectBoxDisable = ref("12");
const selectSearchCheckAll = ref("");
const listSelectBox = ref<CodeMngModel[]>([
  { cdId: "", cdNm: "비게", upCdId: "1" },
  { cdId: "12", cdNm: "비게시", upCdId: "1" },
  { cdId: "13", cdNm: "비게시1", upCdId: "1" },
  { cdId: "14", cdNm: "비게시2", upCdId: "1" },
  { cdId: "15", cdNm: "비게시비게시비게시2", upCdId: "1" },
]);
</script>

<style scoped></style>
