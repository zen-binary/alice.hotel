<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <p class="check_row custom-checkbox">
    <input
      :id="id"
      v-model="computedValue"
      type="checkbox"
      :name="name"
      :indeterminate="indeterminate"
      :disabled="isDisable"
      :true-value="trueValue"
      :false-value="falseValue"
      :value="value"
      :checked="checked"
      :class="className"
      @change="handleChange"
    />
    <label :for="id">
      <span />
      {{ label }}
    </label>
  </p>
</template>

<script lang="ts">
import { computed } from "vue";

export default {
  props: {
    isRemoveCheckBox: Boolean,
    modelValue: {
      type: [Boolean, String, Number, Array as () => Array<string | number>],
      default: null,
    },
    value: {
      type: [String, Number, Array as () => Array<string>],
      default: null,
    },
    trueValue: {
      type: [Boolean, String, Number],
      default: true,
    },
    falseValue: {
      type: [Boolean, String, Number],
      default: false,
    },
    checked: Boolean,
    id: {
      type: String,
      default: "",
    },
    name: { type: String, default: "" },
    round: Boolean,
    disabled: Boolean,
    indeterminate: Boolean,
    mode: {
      type: String,
      default: "",
    },
    className: { type: String, default: "" },
    label: { type: String, default: "" },
    isDisable: {
      type: Boolean,
      default: false,
    },
    flagCheckAll: {
      type: Boolean,
      default: true,
    },
  },
  emits: ["update:modelValue", "change"],
  setup: (props, { emit }) => {
    const computedValue = computed({
      get(): boolean | string | number | (string | number)[] | undefined {
        return props.modelValue;
      },
      set(value: boolean | string | number | (string | number)[] | undefined) {
        emit("update:modelValue", value);
      },
    });
    const handleChange = (event: Event) => {
      emit("change", event);
    };
    return { computedValue, handleChange };
  },
};
</script>

<style scoped></style>
