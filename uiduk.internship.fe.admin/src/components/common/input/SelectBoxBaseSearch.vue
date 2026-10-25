<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <span v-if="isDetail" class="is_disabled">{{ searchQuery }}</span>
  <div v-else class="group_checkbox">
    <input
      v-bind="attrs"
      ref="checkboxInput"
      :value="showValueInputSearch"
      :class="{ on: showList }"
      class="select_component_custom"
      @click="focus"
      readonly
    />
    <teleport v-if="showList && isReadonly" to="#app">
      <ul
        ref="ulPosition"
        class="list_select_custom"
        :class="{ on: showList && isReadonly }"
        :style="{ width: `${widthUl}px`, top: `${topUl}px`, left: `${leftUl}px`, right: 'auto' }"
      >
        <li>
          <input
            v-model="searchQuery"
            @keyup="changData($event)"
          />
        </li>
        <li v-for="option in filteredOptions" :key="option.cdId" :class="{ selected: option.cdNm == showValue }" @click="selectOption(option)">
          {{ option.cdNm }}
        </li>
        <li v-if="filteredOptions.length === 0" class="disabled" @click="hideList">선택</li>
      </ul>
    </teleport>
  </div>
</template>
<script lang="ts">
import { ref, computed, onMounted, onUpdated, onBeforeUnmount, useAttrs, onBeforeMount } from "vue";
import CodeMngModel from "../../../model/common/CodeMngModel";
import {commonStore} from "@/stores/common";
import globalCommon from "@/utils/global"

export default {
  inheritAttrs: false,
  props: {
    modelValue: {
      type: [String, Number],
      default: "",
    },
    mode: {
      type: String,
      default: "",
    },
    valueDefault: {
      type: String,
      default: "",
    },
    nameDefault: {
      type: String,
      default: "",
    },
    data: {
      type: Array as () => Array<CodeMngModel>,
      default: () => [],
    },
    isDetail: {
      type: Boolean,
      default: false,
    },
    valueSelect: {
      type: String,
      default: "",
    },
  },
  emits: ["update:modelValue", "focusout", "change"],
  setup(props, { emit }) {
    const attrs = useAttrs();

    const store = commonStore();
    const searchQuery = ref("");
    const showDropdown = ref(false);
    const showList = ref(false);
    const checkboxInput = ref<HTMLInputElement | null>(null);
    const ulPosition = ref<HTMLUListElement | null>(null);
    const widthUl = ref<number>(0);
    const topUl = ref<number>(0);
    const leftUl = ref<number>(0);
    const isReadonly = ref(true);
    const selectedOption = ref("");
    const showValue = ref("");
    const {isScroll, heightTop} = globalCommon.useScroll();

    const filteredOptions = computed(() => {
      const query = searchQuery.value.toLowerCase();
      return props.data.filter((option) => option.cdNm.toLowerCase().includes(query));
    });
    const showValueInputSearch = computed(() => {
      return props.data.filter((option) => option.cdId.includes(props?.modelValue))[0]?.cdNm || "";
    });

    const toggleDropdown = () => {
      showDropdown.value = !showDropdown.value;
    };

    onBeforeMount(() => {
      showValue.value = props.valueSelect;
    });

    onMounted(() => {
      if (attrs.id && attrs.required !== undefined) {
        store.setRequired(attrs.id);
      }
      if (props.data?.length && props.modelValue !== undefined) {
        searchQuery.value = props.data.find((item) => item.cdId == props.modelValue)?.cdNm || "";
      }
      if (attrs.readonly !== undefined) {
        isReadonly.value = false;
      }
    });

    onUpdated(() => {
      if (attrs.id && attrs.required !== undefined) {
        store.checkRequired();
        checkError();
      }
    });

    onBeforeUnmount(() => {
      document.removeEventListener("mousedown", handleClickOutside);
    });

    const focus = async () => {
      await checkError();
      await positionSelectbox();
      showList.value = true;
      document.addEventListener("mousedown", handleClickOutside);
    };

    const handleClickOutside = (event: MouseEvent) => {
      const ul = ulPosition.value;
      const input = checkboxInput.value;
      if (ul && !ul.contains(event.target as Node) && input && !input.contains(event.target as Node)) {
        showList.value = false;
        document.removeEventListener("mousedown", handleClickOutside);
      }
    };

    const positionSelectbox = async () => {
      if (checkboxInput.value) {
        const inputRect = checkboxInput.value.getBoundingClientRect();
        widthUl.value = inputRect.width;
        const inputTop = inputRect.top + window.scrollY;
        const inputLeft = inputRect.left;
        topUl.value = inputTop + 40;
        leftUl.value = inputLeft;
      }
    };

    const hideList = async () => {
      showList.value = false;
    };

    const checkError = async () => {
      if (attrs.id && attrs.required !== undefined) {
        const input = document.getElementById(attrs.id);
        if (input && !input.value) {
          input.classList.add("error_validate");
        } else if (input) {
          input.classList.remove("error_validate");
        }
      }
    };

    const changData = () => {
      showDropdown.value = true;
    };

    const selectOption = (option: CodeMngModel) => {
      selectedOption.value = option.cdId.toString();
      searchQuery.value = option.cdNm;
      showValue.value = option.cdNm;
      showDropdown.value = false;
      emit("update:modelValue", selectedOption);
      emit("change", selectedOption);
      showList.value = false;
    };

    return {
      searchQuery,
      showDropdown,
      filteredOptions,
      toggleDropdown,
      showList,
      checkboxInput,
      ulPosition,
      widthUl,
      topUl,
      leftUl,
      focus,
      hideList,
      attrs,
      isReadonly,
      selectedOption,
      showValue,
      selectOption,
      changData,
      showValueInputSearch
    };
  },
};
</script>

<style scoped>
/* Add your styles here */
</style>
