<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <CheckboxBase
    v-if="hasCheckAll"
    :id="id"
    v-model="checkBoxDataAll"
    :value="modelValue"
    :label="'전체'"
    :name="name"
    :is-disable="isDisable"
    @change="checkAll"
    :checked="checkBoxDataAll"
  />
  <CheckboxBase
    v-for="option in listData"
    :id="option.cdId.toString()"
    :key="option.cdId"
    v-model="checkBoxData"
    :value="option.cdId"
    :label="option.cdNm"
    :name="option.cdNm"
    :is-disable="isDisable"
    @change="changOne"
    :checked="checkBoxData.includes(option.cdId)"
  />
  <input :id="requireId" hidden :value="modelValue" />
</template>
<script lang="ts">
import { onMounted, ref, onUpdated } from "vue";
import CodeMngModel from "../../../model/common/CodeMngModel";
import CheckboxBase from "@/components/common/input/CheckboxBase.vue";
import { commonStore } from "@/stores/common";

export default {
  components: { CheckboxBase },
  props: {
    modelValue: {
      type: [String, Number, Array],
      required: true,
    },
    className: { type: String, required: false, default: "" },
    mode: {
      type: String,
      default: "show",
    },
    id: { type: String, required: false, default: "" },
    name: { type: String, required: false, default: "" },
    listData: {
      type: Array as () => Array<CodeMngModel>,
      required: true,
    },
    isDisable: {
      type: Boolean,
      default: false,
    },
    hasCheckAll: {
      type: Boolean,
      default: false,
    },
    class: {
      type: String,
      required: false,
      default: "",
    },
    useArray: {
      type: Boolean,
      default: false,
    },
    requireId: {
      type: String,
      required: false,
      default: "",
    },
    isRequire: {
      type: Boolean,
      default: false,
    },
  },
  component: {
    CheckboxBase,
  },
  emits: ["update:modelValue"],
  setup(props, { emit }) {
    const checkBoxData = ref([]);
    const checkBoxDataAll = ref(false);

    const store = commonStore();

    onMounted(() => {
      if (props.modelValue) {
        checkBoxData.value = [];
        if (props.useArray) {
          checkBoxData.value = props.modelValue;
        } else {
          checkBoxData.value = props.modelValue.toString().split(",");
        }
        if (checkBoxData.value.length == props.listData?.length) {
          checkBoxDataAll.value = true;
        }
      }

      if (props.requireId && props.isRequire) {
        store.setRequired(props.requireId);
        store.checkRequired();
      }
    });

    onUpdated(() => {
      store.checkRequired();
    });

    function checkAll() {
      if (checkBoxData.value.length < props.listData.length) {
        checkBoxData.value = [];
        props.listData?.forEach((element) => {
          checkBoxData.value.push(element.cdId);
        });
      } else {
        checkBoxData.value = [];
      }
      emit("update:modelValue", checkBoxData.value);
    }

    function changOne() {
      if (props.listData && Array.isArray(props.listData)) {
        checkBoxDataAll.value = checkBoxData.value.length >= props.listData.length;
        emit("update:modelValue", checkBoxData.value);
      }
    }

    return {
      checkBoxData,
      checkBoxDataAll,
      store,
      checkAll,
      changOne,
    };
  },
};
</script>
