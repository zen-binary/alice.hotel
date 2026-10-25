<template>
  <p v-if="isDetail">
    {{ value ? format(value, "yyyy-MM") : value }}
  </p>
  <div v-else ref="datepickerContainer" class="datepicker_container">
    <input
      v-bind="attrs"
      ref="datepickerInput"
      :value="value ? (viewMode === 'year' ? format(value, 'yyyy') : format(value, 'yyyy-MM')) : value"
      class="datepick"
      readonly
      @click.stop.prevent="handleSelectDate"
    />
    <teleport v-if="stateDatePicker && isReadonly" to="#app">
      <div ref="datepicker" class="datepicker-wrapper" :class="datepickerClass">
        <v-locale-provider locale="ko">
          <v-date-picker
            v-model="picker"
            :view-mode="currentViewMode"
            color="primary"
            border
            title="Select day"
            show-adjacent-months
            @update:view-mode="handleViewModeUpdate"
            @update:year="handleUpdateYear"
            @update:month="handleUpdateMonth"
            @update:model-value="handleUpdateMonth"
          />
        </v-locale-provider>
      </div>
    </teleport>
  </div>
</template>

<script>
import { ref, onBeforeMount, nextTick, useAttrs, onMounted, onBeforeUnmount, onUpdated, defineComponent } from "vue";
import { format } from "date-fns";
import { commonStore } from "@/stores/common";
import globalCommon from "@/utils/global";

export default defineComponent({
  inheritAttrs: false,
  props: {
    modelValue: {
      type: [String, Date],
      required: true,
    },
    isRequired: {
      type: Boolean,
      default: false,
    },
    viewMode: {
      type: String,
      default: "months",
    },
    isDetail: {
      type: Boolean,
      default: false,
    },
  },
  emits: ["update:modelValue"],
  setup(props, { emit }) {
    const store = commonStore();
    const picker = ref();
    const stateDatePicker = ref(false);
    const currentViewMode = ref(props.viewMode);
    const selectedYear = ref(new Date().getFullYear());
    const datepickerClass = ref("bottom-left");
    const datepickerInput = ref(null);
    const datepicker = ref(null);
    const datepickerContainer = ref(null);
    const value = ref();
    const attrs = useAttrs();
    const isReadonly = ref(true);
    const {isScroll, heightTop} = globalCommon.useScroll();

    onBeforeMount(() => {
      picker.value = props.modelValue ? new Date(props.modelValue) : new Date();
      value.value = props.modelValue ? format(props.modelValue, "yyyy-MM") : props.modelValue;
    });

    onMounted(() => {
      if (attrs.id && attrs.required !== undefined) {
        store.setRequired(attrs.id);
        store.checkRequired();
      }
      if (attrs.readonly !== undefined) {
        isReadonly.value = false;
      }
    });

    onBeforeUnmount(() => {
      if (attrs.id && attrs.required !== undefined) {
        store.removeRequired(attrs.id);
      }
    });

    onUpdated(() => {
      value.value = props.modelValue ? format(props.modelValue, "yyyy-MM") : props.modelValue;
      checkError();
      store.checkRequired();
    });

    const handleSelectDate = async () => {
      stateDatePicker.value = !stateDatePicker.value;
      if (stateDatePicker.value) {
        await nextTick();
        document.addEventListener("mousedown", handleClickOutside);
        positionDatePicker();
      } else {
        document.removeEventListener("mousedown", handleClickOutside);
      }
      checkError();
    };

    const checkError = () => {
      if (attrs.id && attrs.required !== undefined) {
        const input = document.getElementById(attrs.id);
        if (input && !input.value) {
          input.classList.add("error_validate");
        } else input?.classList.remove("error_validate");
      }
    };

    const handleClickOutside = (event) => {
      const targetElement = event.target;
      if (
        datepickerContainer.value &&
        !datepickerContainer.value.contains(targetElement) &&
        !(targetElement instanceof HTMLElement && targetElement.closest(".datepicker-wrapper"))
      ) {
        stateDatePicker.value = false;
        document.removeEventListener("mousedown", handleClickOutside);
      }
      checkError();
    };

    const handleViewModeUpdate = (newViewMode) => {
      if (props.viewMode === "year") return;
      currentViewMode.value = newViewMode === "month" ? "months" : newViewMode;
    };

    const handleUpdateYear = (newYear) => {
      selectedYear.value = newYear;
      if (props.viewMode === "year") {
        picker.value = new Date(selectedYear.value, 1, 1);
        value.value = new Date(selectedYear.value, 1, 1);
        emit("update:modelValue", format(value.value, "yyyy"));
        stateDatePicker.value = false;
        document.removeEventListener("mousedown", handleClickOutside);
      }
    };

    const handleUpdateMonth = (newMonth) => {
      picker.value = new Date(selectedYear.value, newMonth, 1);
      value.value = new Date(selectedYear.value, newMonth, 1);
      emit("update:modelValue", format(value.value, "yyyy-MM"));
      stateDatePicker.value = false;
      document.removeEventListener("mousedown", handleClickOutside);
    };

    const positionDatePicker = () => {
      const inputRect = datepickerInput.value.getBoundingClientRect();
      const documentHeight = window.innerHeight;
      const documentWidth = window.innerWidth;

      const inputTop = inputRect.top + window.scrollY;
      const inputLeft = inputRect.left;
      const inputRight = inputRect.right;

      datepicker.value.style.top = `${inputTop + 40}px`;
      if (inputRect.right > documentWidth * (2 / 3)) {
        datepicker.value.style.right = `${documentWidth - inputRight}px`;
        datepicker.value.style.left = "auto";
      } else {
        datepicker.value.style.left = `${inputLeft}px`;
        datepicker.value.style.right = "auto";
      }

      if (inputRect.bottom > documentHeight * (1 / 2)) {
        datepicker.value.style.top = `${inputTop - 355}px`;
      }
    };

    return {
      picker,
      currentViewMode,
      handleViewModeUpdate,
      handleSelectDate,
      stateDatePicker,
      handleUpdateYear,
      handleUpdateMonth,
      datepickerClass,
      datepickerInput,
      datepicker,
      handleClickOutside,
      datepickerContainer,
      value,
      attrs,
      checkError,
      isReadonly,
    };
  },
  methods: { format },
});
</script>

<style scoped></style>
