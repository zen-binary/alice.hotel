<template>
  <span v-if="isDetail" class="is_disabled">{{ showValue }}</span>
  <div v-else class="group_checkbox">
    <input
      v-bind="attrs"
      ref="checkboxInput"
      :class="{ on: showList }"
      :value="showValue"
      readonly
      class="select_component_custom"
      @click="toggleDropdown"
    />
    <teleport v-if="showList && isReadonly" to="#app">
      <ul
        ref="ulPosition"
        class="list_select_custom"
        :class="{ on: showList && isReadonly }"
        :style="{ width: `${widthUl}px`, top: `${topUl}px`, left: `${leftUl}px`, right: 'auto' }"
      >
        <li @click.prevent="toggleCheckAll">
          <CheckboxBase
            :id="checkAll"
            v-model="checkBoxDataAll"
            :value="checkAll"
            :label="labelAll"
            :name="checkAll"
            @change.stop.prevent="checkAll"
          />
        </li>
        <li v-for="option in filteredOptions" :key="option.cdId" @click.prevent="toggleCheck(option)">
          <CheckboxBase
            :id="option.cdId"
            v-model="checkBoxData"
            :value="option.cdId"
            :label="option.cdNm"
            :name="option.id"
            @change.stop.prevent="changOne"
          />
        </li>
      </ul>
    </teleport>
  </div>
</template>

<script lang="ts">
import { ref, computed, onBeforeUnmount, onMounted, useAttrs, onUpdated, onBeforeMount, watch } from "vue";
import CodeMngModel from "@/model/common/CodeMngModel";
import CheckboxBase from "@/components/common/input/CheckboxBase.vue";
import { commonStore } from "@/stores/common";
import globalCommon from "@/utils/global"

export default {
  components: { CheckboxBase },
  inheritAttrs: false,

  props: {
    modelValue: {
      type: [String, Number],
      default: "",
    },
    className: {
      type: String,
      default: "",
    },
    mode: {
      type: String,
      default: "",
    },
    name: {
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
    isShowCheckAll: {
      type: Boolean,
      default: false,
    },
  },
  component: {
    CheckboxBase,
  },
  emits: ["update:modelValue"],
  setup(props, { emit }) {
    const attrs = useAttrs();

    const store = commonStore();
    const checkBoxData = ref([]);
    const checkBoxDataAll = ref(false);
    const searchQuery = ref("");
    const showDropdown = ref(false);
    const showValue = ref("");
    const showList = ref(false);
    const checkboxInput = ref<HTMLInputElement | null>(null);
    const ulPosition = ref<HTMLUListElement | null>(null);
    const widthUl = ref<number>(0);
    const topUl = ref<number>(0);
    const leftUl = ref<number>(0);
    const labelAll = ref("전체");
    const isReadonly = ref(true);
    const selectedOption = ref("");
    const {isScroll, heightTop} = globalCommon.useScroll();

    const filteredOptions = computed(() => {
      const query = searchQuery.value.toLowerCase();
      return props.data.filter((option) => option.cdNm.toLowerCase().includes(query));
    });

    const toggleDropdown = async () => {
      await checkError();
      await positionSelectbox();
      showDropdown.value = !showDropdown.value;
      showList.value = true;
      document.addEventListener("mousedown", handleClickOutside);
    };

    onBeforeMount(() => {
      showValue.value = props.valueSelect ? props.valueSelect : "";
    });

    onMounted(() => {
      if (attrs.id && attrs.required !== undefined) {
        store.setRequired(attrs.id);
      }
      if (attrs.readonly !== undefined) {
        isReadonly.value = false;
      }
      if (props.data?.length && props.modelValue !== undefined) {
        const option = props.data.find((item) => item?.cdId == props.modelValue);
        showValue.value = option?.cdNm || "";
        if (option?.cdId) toggleCheck(option);
        if (props.isShowCheckAll) toggleCheckAll();
      }
    });
    onUpdated(() => {
      if (attrs.id && attrs.required !== undefined) {
        checkError();
        store.checkRequired();
      }
    });

    watch(
      () => props.modelValue,
      () => {
        if (!props.modelValue) {
          checkBoxData.value = [];
          showValue.value = "";
          checkBoxDataAll.value = false;
        }
      },
    );

    onBeforeUnmount(() => {
      document.removeEventListener("mousedown", handleClickOutside);
    });

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

    function selectOption(option: CodeMngModel) {
      selectedOption.value = option.cdId;
      showValue.value = option.cdNm;
      // showDropdown = false;
      emit("update:modelValue", selectedOption);
    }

    function checkAll() {
      if (checkBoxData.value.length < filteredOptions.value.length) {
        checkBoxData.value = [];
        showValue.value = "";
        filteredOptions.value.forEach((element, index) => {
          checkBoxData.value.push(element.cdId);
          if (index < filteredOptions.value.length - 1) {
            showValue.value += element.cdNm + ", ";
          } else {
            showValue.value += element.cdNm;
          }
        });
      } else {
        checkBoxData.value = [];
        showValue.value = "";
      }
      emit("update:modelValue", checkBoxData);
    }

    function changOne() {
      if (checkBoxData.value.length < filteredOptions.value.length) {
        checkBoxDataAll.value = false;
      } else {
        checkBoxDataAll.value = true;
      }
      emit("update:modelValue", checkBoxData);
      let newValue = "";
      filteredOptions.value.forEach((element) => {
        checkBoxData.value.forEach((item) => {
          if (element.cdId == item) {
            if (!newValue) {
              newValue += element.cdNm;
            } else {
              newValue += "," + element.cdNm;
            }
          }
        });
      });
      showValue.value = newValue;
    }

    function toggleCheck(option) {
      const index = checkBoxData.value.indexOf(option.cdId);
      if (index > -1) {
        checkBoxData.value.splice(index, 1);
      } else {
        checkBoxData.value.push(option.cdId);
      }
      changOne();
      showList.value = false;
    }

    function toggleCheckAll() {
      checkBoxDataAll.value = !checkBoxDataAll.value;
      checkAll();
      showList.value = false;
    }

    return {
      attrs,
      searchQuery,
      showDropdown,
      filteredOptions,
      toggleDropdown,
      checkBoxData,
      checkBoxDataAll,
      showValue,
      showList,
      checkboxInput,
      ulPosition,
      widthUl,
      topUl,
      leftUl,
      labelAll,
      isReadonly,
      selectedOption,
      selectOption,
      checkAll,
      changOne,
      toggleCheckAll,
      toggleCheck,
    };
  },
};
</script>

<style scoped>
/* Add your styles here */
</style>
