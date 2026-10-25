<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <p class="radio_row custom-radio radio_base">
    <input
      :id="id"
      v-model="computedValue"
      type="radio"
      :name="name"
      :indeterminate="indeterminate"
      :true-value="trueValue"
      :false-value="falseValue"
      :value="value"
      :checked="checked"
      :class="className"
      :disabled="isDisable"
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
    modelValue: {
      type: [Boolean, String, Number, Array],
      default: () => [],
    },
    value: {
      type: [String, Number, Array],
      default: () => [],
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
    name: {
      type: String,
      default: "",
    },
    round: Boolean,
    disabled: Boolean,
    indeterminate: Boolean,
    mode: {
      type: String,
      default: "",
    },
    className: {
      type: String,
      default: "",
    },
    label: {
      type: String,
      default: "",
    },
    isDisable: Boolean,
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

    // Function to handle radio button change
    const handleChange = (event: Event) => {
      emit("change", event);
    };
    return { computedValue, handleChange };
  },
};
</script>

<style scoped>
.radio_base {
  width: auto !important;
}
</style>
