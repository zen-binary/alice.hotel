<script setup>
import { defineProps } from "vue";
const props = defineProps({
  params: Object,
});
const buttons = ref("");
const data = ref("");
onMounted(() => {
  if (props.params) {
    buttons.value = props.params.buttons || "";
    data.value = props.params.data || "";
  }
});
</script>

<template>
  <div class="outline-btn">
    <button
      v-for="(button, index) in buttons"
      :key="index"
      type="button"
      v-show="button.isShow"
      :disabled="button.isDisabled"
      :class="button.className"
      @click="
        () => {
          button.onClick(data);
        }
      "
    >
      {{ button.label ?? "" }}
    </button>
  </div>
</template>

<style lang="scss">
.outline-btn{
  display: flex;
  gap:10px;
  height: 100%;
  align-items: center;
}
</style>
<!-- 
demo:
{
    headerName: "Action",
    cellRenderer: MutiButtonGridComponent,
    cellRendererParams: {
      buttons: [
        {
          label: “move”,
          className: "button btn_xs btn_primary",
          isDisabled: true,
          onClick: (data: object) => {
            console.log(data);
          },
        },
        {
          label: "Edit",
          isDisabled: false,
          className: "button btn_xs btn_primary",
          onClick: (data: object) => {
            console.log(data);
          },
        },
        {
          label: “copy”,
          isDisabled: true,
          className: "button btn_xs btn_primary",
          onClick: (data: object) => {
            console.log(data);
          },
        },
        {
          label: “Delete,
          className: "button btn_xs btn_primary",
          onClick: (data: object) => {
            console.log(data);
          },
        },
      ],
    },
  },

 -->
