<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div class="selector_component" v-bind="attrs">
    <ul>
      <li v-for="(i, index) in data" :class="{ active: index + 1 === modelValue }" @click.prevent="handleActive(index + 1)">
        {{ i }}
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { useAttrs } from "vue";

defineOptions({
  inheritAttrs: false,
});

defineProps({
  modelValue: {
    type: [String, Number],
    required: true,
  },
  data: {
    type: Array as () => string[],
    required: true,
  },
  isDisable: {
    type: Boolean,
    default: false,
  },
});

const emit = defineEmits(["update:modelValue"]);
const attrs = useAttrs();

const handleActive = (value: string | number) => {
  emit("update:modelValue", value);
};
</script>

<style scoped lang="scss">
.selector_component {
  ul {
    display: flex;
    flex-wrap: wrap;
    width: 100%;
    position: relative;

    &::after {
      content: "";
      position: absolute;
      width: 100%;
      height: 100%;
      bottom: 0;
      left: 0;
      border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));
      pointer-events: none;
    }

    li {
      position: relative;
      box-sizing: border-box;
      font-size: 1.6rem;
      font-weight: 500;
      line-height: 2.8rem;

      padding: 12px 20px;
      color: var(--Gray-scale-60, rgba(113, 113, 113, 1));
      cursor: pointer;

      &::after {
        content: "";
        position: absolute;
        width: 100%;
        height: 100%;
        bottom: 0;
        left: 0;
        border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));
        pointer-events: none;
      }

      &.active {
        color: var(--Primary-50_Primary, rgba(0, 152, 229, 1));
      }

      &.active::after {
        content: "";
        position: absolute;
        width: 100%;
        height: 100%;
        bottom: 0;
        left: 0;
        border-bottom: 4px solid var(--Primary-50_Primary, rgba(0, 152, 229, 1));
        z-index: 1;
      }
    }
  }
}
</style>
