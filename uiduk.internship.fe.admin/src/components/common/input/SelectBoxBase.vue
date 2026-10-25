<template>
  <span v-if="isDetail" class="is_disabled">{{ name }}</span>
  <div v-else class="group_checkbox">
    <input v-bind="attrs" ref="checkboxInput" class="select_component_custom" :class="{ on: showList }" :value="name"
        :data-cdid="cdIdValue" readonly @click="focus"/>
    <teleport v-if="showList && isReadonly" to="#app">
      <ul
          ref="ulPosition"
          class="list_select_custom"
          :class="{ on: showList && isReadonly }"
          :style="{ width: `${widthUl}px`, top: `${topUl}px`, left: `${leftUl}px`, right: 'auto' }"
      >
        <li v-for="item in data" :key="item.cdId" @click="updateValue(item.cdId, item.cdNm)">
          {{ item.cdNm }}
        </li>
        <li v-if="data.length === 0" class="disabled" @click="hideList">선택</li>
      </ul>
    </teleport>
  </div>
</template>

<script lang="ts">
import CodeMngModel from "../../../model/common/CodeMngModel";
import {commonStore} from "@/stores/common";
import {ref, onMounted, onBeforeUnmount, onUpdated, useAttrs} from "vue";
import globalCommon from "@/utils/global";

export default {
  inheritAttrs: false,
  props: {
    modelValue: {
      type: [String, Number],
      default: "",
    },
    data: {
      type: Array as () => Array<CodeMngModel>,
      required: true,
      default: () => [],
    },
    isDetail: {
      type: Boolean,
      default: false,
    },
  },
  emits: ["update:modelValue", "focusout", "change"],
  setup(props, {emit}) {
    const attrs = useAttrs();

    const store = commonStore();
    const name = ref("");
    const showList = ref(false);
    const checkboxInput = ref<HTMLInputElement | null>(null);
    const ulPosition = ref<HTMLUListElement | null>(null);
    const widthUl = ref<number>(0);
    const topUl = ref<number>(0);
    const leftUl = ref<number>(0);
    const disabled = ref(false);
    const isReadonly = ref(true);
    const {isScroll, heightTop} = globalCommon.useScroll();
    const cdIdValue = ref("data-set-default")

    const focusout = () => {
      emit("focusout");
      showList.value = false;
    };

    const updateValue = async (cdId: string, cdNm: string) => {
      emit("update:modelValue", cdId);
      name.value = cdNm;
      showList.value = false;
      cdIdValue.value = cdId;
      const input = document.getElementById(attrs?.id);
      if (cdNm && input) {
        input.classList.remove("error_validate");
      }
      emit('change', cdId)
    };

    const focus = async () => {
      await checkError();
      await positionSelectbox();
      showList.value = true;
      document.addEventListener("mousedown", handleClickOutside);
    };

    const handleClickOutside = (event: MouseEvent) => {
      if(cdIdValue.value === 'data-set-default') cdIdValue.value = ''
      const ul = ulPosition.value;
      const input = checkboxInput.value;
      if (ul && !ul.contains(event.target as Node) && input && !input.contains(event.target as Node)) {
        showList.value = false;
        document.removeEventListener("mousedown", handleClickOutside);
      }
    };

    onMounted(() => {
      if (attrs.id && attrs.required !== undefined) {
        store.setRequired(attrs.id);
      }
      if (props.data?.length && props.modelValue !== undefined) {
        name.value = props.data.find((item) => item.cdId == props.modelValue)?.cdNm || "";
      }
      if (attrs.readonly !== undefined) {
        isReadonly.value = false;
      }
    });

    onUpdated(() => {
      if (props.data?.length && props.modelValue !== undefined) {
        name.value = props.data.find((item) => item.cdId == props.modelValue)?.cdNm;
      }
      if (attrs.id && attrs.required !== undefined) {
        store.checkRequired();
        checkError();
      }
    });

    onBeforeUnmount(() => {
      if (attrs.id && attrs.required !== undefined) {
        store.removeRequired(attrs.id);
      }
      document.removeEventListener("mousedown", handleClickOutside);
    });

    const checkError = async () => {
      if (attrs.id && attrs.required !== undefined) {
        const input = document.getElementById(attrs.id);
        if (input && (!input.value || !input.dataset.cdid)) {
          input.classList.add("error_validate");
        } else if (input) {
          input.classList.remove("error_validate");
        }
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

    const hideList = () => {
      showList.value = false;
    };
    return {
      attrs,
      updateValue,
      focusout,
      store,
      name,
      focus,
      showList,
      checkboxInput,
      widthUl,
      topUl,
      leftUl,
      ulPosition,
      disabled,
      hideList,
      isReadonly,
      cdIdValue
    };
  },
};
</script>

<style scoped lang="scss">
/* Thêm style nếu cần */
</style>
