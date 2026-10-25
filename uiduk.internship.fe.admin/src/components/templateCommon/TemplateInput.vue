<template>
  <div class="box dp_block">
    <div class="box_section">
      <h3 class="box_title">Các chức năng Input + TextArea</h3>
      <p>
        <strong class="required">Note:</strong>
        Với mẫu number cần truyền giá trị props isNumber và sẽ chỉ nhận 2 số sau số thập phân (decimalPlaces : 2)
      </p>
      <p>
        <strong class="required">Note:</strong>
        Khi chỉ nhập dấu . là tiền tố bắt đầu mặc định 0.
      </p>
      <p>
        <strong class="required">Note:</strong>
        Text area auto resize rows cần id và thêm props resizeAuto
      </p>

      <div class="search_box col_3">
        <ul>
          <li>
            <p class="">Input Search Giao diện</p>
          </li>
          <li>
            <p class="">Value: {{ listValue.value1 }}</p>
          </li>
          <li>
            <InputBase v-model="listValue.value1" class="btn_search"/>
          </li>
          <li>
            <p class="">Input Search Chức năng search khi click</p>
          </li>
          <li>
            <p class="">Value: {{ listValue.value1 }}</p>
          </li>
          <li>
            <InputBase v-model="listValue.value1" :isSearch="true" @search="handleSearch"/>
          </li>
          <li>
            <p class="">Input count word</p>
          </li>
          <li>
            <p class="">Value: {{ listValue.value1 }}</p>
          </li>

          <li>
            <InputBase v-model="listValue.value1" :is-show-count-word="true" :max-words="20"/>
          </li>
          <li>
            <p class="">Input Base</p>
          </li>
          <li>
            <p class="">Value: {{ listValue.value1 }}</p>
          </li>
          <li>
            <InputBase v-model="listValue.value1"/>
          </li>
          <li>
            <p class="">Input Number</p>
          </li>
          <li>
            <p class="">Value: {{ listValue.value2 }}</p>
          </li>
          <li>
            <InputBase v-model="listValue.value2" :is-number="true" :is-show-count-word="true" :max-words="20"/>
          </li>
          <li>
            <p class="">
              Input Number
              <br/>
              Min 1 Max 5
            </p>
          </li>
          <li>
            <p class="">Value: {{ listValue.value3 }}</p>
          </li>
          <li>
            <InputBase v-model="listValue.value3" :is-number="true" :min-value="0" :max-value="5"
                :is-min-max-value="true"/>
          </li>
          <li>
            <p class="">Text area auto resize rows</p>
          </li>
          <li>
            <p class="">Value: {{ listValue.value4 }}</p>
          </li>
          <li>
            <div style="width:100% ;height: 200px">
              <TextArea id="reszie_auto" v-model="listValue.value4" :resize-auto="true" :is-show-count-word="true"
                  :max-words="2000"/>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {ref} from "vue";
import InputBase from "@/components/common/input/InputBase.vue";

const listValue = ref({
  value1: "",
  value2: "",
  value3: "",
  value4: "",
});

const handleSearch = (value) => {
  alert(value)
}
</script>

<style scoped>
strong {
  font-size: 16px;
  font-weight: bold;
}

ul li p {
  white-space: nowrap;
}
</style>