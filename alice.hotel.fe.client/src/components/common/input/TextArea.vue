<template>
  <p v-if="isDetail" class="text-pre" v-html="props?.modelValue">
  </p>
  <div v-else class="outline-input" :class="isShowCountWord && maxWords > 0 ? 'textarea-input' : ''">
    <textarea
      v-bind="attrs"
      :placeholder="placeholder"
      :value="localText"
      rows="5"
      class="custom-input"
      @input="onInput"
      @focus="checkError"
      @keyup="checkError"
    />
    <p class="count_word" v-if="props.isShowCountWord && props.maxWords > 0">
      <span>{{ wordCount }}</span>
      <span>/{{ props.maxWords }}</span>
    </p>
  </div>
</template>

<script setup lang="ts">
import { onMounted, onBeforeUnmount, onUpdated, useAttrs, ref, computed, getCurrentInstance, watch } from "vue";
import { commonStore } from "@/stores/common";

const attrs = useAttrs();
const props = defineProps({
  modelValue: {
    type: String,
    required: true,
  },
  resizeAuto: {
    type: Boolean,
    default: false,
    required: false,
  },
  placeholder: {
    type: [String],
  },
  isShowCountWord: {
    type: Boolean,
    default: false,
  },
  maxWords: {
    type: Number,
    default: 0,
  },
  isDetail: {
    type: Boolean,
    default: false,
  },
});
defineOptions({
  inheritAttrs: false,
});

const emit = defineEmits(["update:modelValue"]);
const store = commonStore();
let timeout: NodeJS.Timeout | null = null;
const { appContext } = getCurrentInstance();
const global = appContext.config.globalProperties.$global;

const localText = ref(props.modelValue);
watch(
  () => props.modelValue,
  (newVal: any) => {
    localText.value = newVal;
  },
);
const onInput = (event: any) => {
  const inputText = event.target.value;

  // Check if the current word count exceeds the maxWords limit
  if (props.maxWords === 0 || inputText.length <= props.maxWords) {
    localText.value = inputText;
    debouncedEmit(event.target.value);
  } else {
    // Prevent further input by setting the value back to localText
    event.target.value = localText.value;
  }
};

const debouncedEmit = global.debounce((value: any) => {
  emit("update:modelValue", value);
}, 50);

onMounted(() => {
  if (attrs.id && attrs.required !== undefined) {
    store.setRequired(attrs.id);
  }
});

onMounted(() => {
  if (props.resizeAuto && attrs.id) {
    const textArea = document.getElementById(attrs.id as string) as HTMLTextAreaElement;

    if (!textArea) {
      console.error("TextArea element not found.");
      return;
    }

    const computedStyle = window.getComputedStyle(textArea);
    const paddingNode = {
      top: parseFloat(computedStyle.paddingTop),
      right: parseFloat(computedStyle.paddingRight),
      bottom: parseFloat(computedStyle.paddingBottom),
      left: parseFloat(computedStyle.paddingLeft),
    };
    const borderNode = {
      top: parseFloat(computedStyle.borderTopWidth),
      right: parseFloat(computedStyle.borderRightWidth),
      bottom: parseFloat(computedStyle.borderBottomWidth),
      left: parseFloat(computedStyle.borderLeftWidth),
    };

    const nodeReality = textArea.getBoundingClientRect().height - paddingNode.top - paddingNode.bottom - borderNode.top - borderNode.bottom;

    const heightRow = nodeReality / textArea.rows;

    const parentNode = textArea.parentElement as HTMLElement;
    if (!parentNode) {
      console.error("Parent node not found.");
      return;
    }

    const computedStyleParentNode = window.getComputedStyle(parentNode);
    const paddingParentNode = {
      top: parseFloat(computedStyleParentNode.paddingTop),
      right: parseFloat(computedStyleParentNode.paddingRight),
      bottom: parseFloat(computedStyleParentNode.paddingBottom),
      left: parseFloat(computedStyleParentNode.paddingLeft),
    };
    const borderParentNode = {
      top: parseFloat(computedStyleParentNode.borderTopWidth),
      right: parseFloat(computedStyleParentNode.borderRightWidth),
      bottom: parseFloat(computedStyleParentNode.borderBottomWidth),
      left: parseFloat(computedStyleParentNode.borderLeftWidth),
    };

    const parentNodeReality =
      parentNode.getBoundingClientRect().height - paddingParentNode.top - paddingParentNode.bottom - borderParentNode.top - borderParentNode.bottom;

    if (heightRow > 0) {
      textArea.rows = Math.floor(parentNodeReality / heightRow) - 1;
    } else {
      console.error("Invalid height per row.");
    }
  }
});

onBeforeUnmount(() => {
  if (attrs.id && attrs.required !== undefined) {
    store.removeRequired(attrs.id);
  }
  clearTimeout(timeout);
});

onUpdated(() => {
  store.checkRequired();
});

const checkError = () => {
  if (attrs.id && attrs.required !== undefined) {
    const input = document.getElementById(attrs.id);
    if (input && !input.value) {
      input.classList.add("error_validate");
    } else if (input) {
      input.classList.remove("error_validate");
    }
  }
};
// Computed property for word count
const wordCount = computed(() => (localText.value ? localText.value.length : 0));
</script>

<style scoped lang="scss">
.count_word {
  display: flex;
  padding-right: 0;
  align-items: center;
  justify-content: space-around;
  width: fit-content !important;
  margin-left: 10px;
  span {
    &:first-child {
      font-weight: 500;
    }
  }
}
.textarea-input {
  display: flex;
  align-items: flex-end;
  width: 100%;
}
.outline-input {
  width: 100%;
}
.text-pre {
  white-space: break-spaces;
  word-break: break-all;
}
</style>
