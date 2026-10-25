<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div class="box dp_block">
    <div class="box_section">
      <p class="box_title_sm">CHECKBOX GROUP</p>
      <strong class="required">Note</strong>
      <span>List checkbox use mode String or Array (useArray)</span>
      <div class="search_box col_3">
        <ul>
          <li>
            <p>checkbox value</p>
            <p>{{ checkboxGroup }}</p>
          </li>
          <li>
            <p>checkbox has selected value</p>
            <div class="select_checkbox">
              <p>{{ checkboxGroup2 }}</p>
            </div>
          </li>
          <li>
            <p>checkbox disabled value</p>
            <div class="select_checkbox">
              <p>{{ checkboxGroup3 }}</p>
            </div>
          </li>
        </ul>
      </div>
      <div class="search_box col_3">
        <ul>
          <li>
            <p>checkbox</p>
            <div class="select_checkbox">
              <CheckboxBase
                v-for="item in listCheckBox"
                :id="item.id"
                :key="item.id"
                v-model="checkboxGroup"
                :value="item.name"
                :name="item.id"
                :label="item.name"
                :checked="checkboxGroup.includes(item.id)"
              />
            </div>
          </li>
          <li>
            <p>checkbox has selected</p>
            <div class="select_checkbox">
              <CheckboxBase
                v-for="item in listCheckBox2"
                :id="item.id"
                :key="item.id"
                v-model="checkboxGroup2"
                :mode="'show'"
                :value="item.name"
                :name="item.id"
                :label="item.name"
                :checked="checkboxGroup.includes(item.id)"
              />
            </div>
          </li>
          <li>
            <p>checkbox disabled</p>
            <div class="select_checkbox">
              <CheckboxBase
                v-for="item in listCheckBox3"
                :id="item.id"
                :key="item.id"
                v-model="checkboxGroup3"
                :mode="'show'"
                :value="item.name"
                :is-disable="true"
                :name="item.id"
                :label="item.name"
                :checked="checkboxGroup.includes(item.id)"
              />
            </div>
          </li>
        </ul>
      </div>

      <div class="search_box col_3">
        <ul>
          <li>
            <p>List checkbox</p>
            <p>{{ checkboxGroupAll }}</p>
          </li>
        </ul>
      </div>
      <div class="search_box col_3">
        <ul>
          <li>
            <p>List checkbox</p>
            <div class="select_checkbox">
              <ListCheckBoxBase
                id="checkAll"
                v-model="checkboxGroupAll"
                :list-data="listCheckBox1"
                :mode="'show'"
                name="checkAll"
                :has-check-all="true"
                :use-array="true"
              />
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div class="box_section">
      <p class="box_title_sm">Radio</p>
      <div class="search_box col_2">
        <ul>
          <li>
            <p>radio</p>
            <div class="select_checkbox">
              <RadiobuttonBase
                v-for="item in listRadio1"
                :id="item.id"
                :key="item.id"
                v-model="radiovalue1"
                :value="item.name"
                :name="item.id"
                :checked="item.name == radiovalue1"
                :label="item.name"
              >
                Checkbox 1
              </RadiobuttonBase>
            </div>
          </li>
          <li>
            <p>radio disable</p>
            <div class="select_checkbox">
              <RadiobuttonBase
                v-for="item in listRadio2"
                :id="item.id"
                :key="item.id"
                v-model="radiovalue2"
                :value="item.name"
                :name="item.id"
                :checked="item.name == radiovalue2"
                :label="item.name"
                :is-disable="true"
              >
                Checkbox 1
              </RadiobuttonBase>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import ListCheckBoxBase from "@/components/common/input/ListCheckBoxBase.vue";
import CheckboxBase from "@/components/common/input/CheckboxBase.vue";
import RadiobuttonBase from "@/components/common/input/RadiobuttonBase.vue";

// check box
const checkboxGroup = ref([]);
const checkboxGroup2 = ref(["name9", "name08"]);
const checkboxGroup3 = ref(["name11", "name13"]);
const checkboxGroupAll = ref(["51"]);
const listCheckBox = ref([
  { id: "1", name: "name01" },
  { id: "2", name: "name02" },
]);
const listCheckBox1 = ref([
  { cdId: "51", cdNm: "비게", upCdId: "1" },
  { cdId: "52", cdNm: "비게시", upCdId: "1" },
  { cdId: "53", cdNm: "비게시1", upCdId: "1" },
  { cdId: "54", cdNm: "비게시2", upCdId: "1" },
]);
const listCheckBox2 = ref([
  { id: "8", name: "name08" },
  { id: "9", name: "name9" },
  { id: "10", name: "name10" },
]);
const listCheckBox3 = ref([
  { id: "11", name: "name11" },
  { id: "12", name: "name12" },
  { id: "13", name: "name13" },
]);

const radiovalue1 = ref("name03");
const radiovalue2 = ref("name06");
const listRadio1 = ref([
  { id: "3", name: "name03" },
  { id: "4", name: "name04" },
]);
const listRadio2 = ref([
  { id: "5", name: "name05" },
  { id: "6", name: "name06" },
]);
</script>

<style scoped></style>
