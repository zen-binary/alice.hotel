<template>
  <p v-if="isDetail">
    {{ props?.modelValue }}
  </p>
  <div v-else class="input-wrapper" :class="{ 'textarea-input': isShowCountWord && maxWords > 0 }">
    <input
      v-bind="attrs"
      :placeholder="placeholder"
      v-model="inputValue"
      :value="inputValue"
      class="custom-input"
      @input="onInput"
      @keyup="checkError"
      @focus="checkError"
    />
    <label v-if="isSearch" class="label_search" @click="handleSearch">Search</label>
    <p class="count_word" v-if="isShowCountWord && maxWords > 0">
    <span>{{ wordCount ?? '' }}</span>
    <span>/{{ maxWords ?? '' }}</span>
  </p>
  </div>
</template>

<script setup lang="ts">
import {onMounted, onBeforeUnmount, onUpdated, useAttrs, ref, computed, getCurrentInstance, watch} from "vue";
import {commonStore} from "@/stores/common";

defineOptions({
  inheritAttrs: false,
});
const attrs = useAttrs();
const props = defineProps({
  modelValue: {
    type: [String, Number],
    required: true,
  },
  isNumber: {
    type: Boolean,
    default: false,
  },
  isMinMaxValue: {
    type: Boolean,
    default: false,
  },
  minValue: {
    type: [String, Number],
    required: false,
    default: 0,
  },
  maxValue: {
    type: [String, Number],
    required: false,
    default: 5,
  },
  decimalPlaces: {
    type: Number,
    default: 2,
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
  isSearch: {
    type: Boolean,
    default: false,
  }
});
const emit = defineEmits(["update:modelValue", "search"]);
const {appContext} = getCurrentInstance();
const global = appContext.config.globalProperties.$global;

const store = commonStore();
let timeout: NodeJS.Timeout | null = null;
const inputValue = ref(props.modelValue);
const regex = new RegExp(`^[0-9]*\\.?[0-9]{0,${props.decimalPlaces}}$`);
let stampValue = "";

watch(
    () => props.modelValue,
    () => {
      inputValue.value = props.modelValue;
    },
);

const onInput = (event: any) => {
  let inputText = event.target.value;
  if (props.isNumber) {
    const target = event.target as HTMLInputElement;
    let value = target.value;
    if (props.maxWords === 0 || inputText.length <= props.maxWords) {
      inputValue.value = value;
    } else {
      value = inputText.slice(0, props.maxWords);
      inputValue.value = value;
    }
    // Bước 1: Xóa tất cả ký tự không phải số và dấu thập phân
    inputValue.value = value.replace(/[^0-9.]/g, "");

    // Bước 2: Xử lý số 0 tiền tố nếu có
    if (inputValue.value.length > 1 && inputValue.value.startsWith("0") && !inputValue.value.startsWith("0.") && props.isMinMaxValue) {
      inputValue.value = inputValue.value.replace(/^0+/, "");
    }
    if (inputValue.value.length > 1 && inputValue.value.startsWith("0.") == true) {
      inputValue.value = inputValue.value.replace(/^0\./, "");
    }

    // Bước 3: Xử lý số thập phân và nhiều dấu chấm
    if (regex.test(inputValue.value)) {
      stampValue = inputValue.value;
    }
    inputValue.value = stampValue;

    const hasMultipleDots = (inputValue.value.match(/[.]/g) || []).length > 1;
    if (hasMultipleDots) {
      inputValue.value = inputValue.value.slice(0, value.length - 1);
    }

    // Bước 4: Xử lý dấu chấm ở đầu chuỗi
    if (inputValue.value.startsWith(".")) {
      inputValue.value = "0" + inputValue.value;
    }

    // Bước 5: Kiểm tra và xử lý giá trị số
    if (props.isMinMaxValue === true) {
      const numericValue = parseFloat(inputValue.value);
      if (!isNaN(numericValue) && typeof props.minValue !== "undefined" && typeof props.maxValue !== "undefined") {
        if (numericValue < +props.minValue) {
          inputValue.value = props.minValue;
        } else if (numericValue > +props.maxValue) {
          inputValue.value = props.maxValue;
        }
      }
    }

    emit("update:modelValue", inputValue.value);
  } else {
    if (props.maxWords === 0 || inputText.length <= props.maxWords) {
      inputValue.value = inputText;
      debouncedEmit(inputValue.value);
    } else {
      event.target.value = inputText.slice(0, props.maxWords);
      inputValue.value = event.target.value;
      debouncedEmit(inputValue.value);
    }
  }
};
const wordCount = computed(() => inputValue.value ? inputValue.value.length : 0);
const debouncedEmit = global.debounce((value: any) => {
  emit("update:modelValue", value);
}, 50);

const checkError = () => {
  if (attrs.id && attrs.required !== undefined) {
    const input = document.getElementById(attrs.id as any);
    if (input && !input.value) {
      input.classList.add("error_validate");
    } else if (input) {
      input.classList.remove("error_validate");
    }
  }
};

onMounted(() => {
  if (attrs.id && attrs.required !== undefined) {
    store.setRequired(attrs.id);
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

const handleSearch = () => {
  emit('search', inputValue.value)
}
</script>

<style scoped lang="scss">
.count_word {
  display: flex;
  padding-right: 0;
  align-items: center;
  justify-content: space-around;
  width: 50px !important;
  span{
    &:first-child{
      font-weight: 500;
    }
  }
}
.textarea-input{
display: flex;
align-items: flex-end;
width: 100%;
gap: 15px
}

.input-wrapper {
  position: relative;
  width: 100%;
}

.input-wrapper:has(.label_search) input {
  padding-right: 40px;
}

.label_search {
  text-indent: -9999px;
  position: absolute;
  top: 0;
  right: 0;
  width: var(--input-height);
  height: var(--input-height);
  background: url("@/assets/images/icon_search.svg") no-repeat center;
  cursor: pointer;
}
</style>