<template>
  <input v-if="mode === 'all'" :id="idAll" type="checkbox" @change="changeAll" />
  <input v-else :id="idChild" type="checkbox" :checked="props.params.value" :name="name" @change="toggleChild" />
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";

const props = defineProps({
  params: {
    type: Object,
    default: () => ({}),
  },
});
const idAll = ref("all");
const idChild = ref("child");
const name = ref("name");
const mode = ref("all");

onMounted(() => {
  idAll.value = props.params.checkAll;
  mode.value = props.params.mode;
  name.value = props.params.gridName;
});

const changeAll = (event) => {
  const e = document.querySelectorAll(`input[type="checkbox"][name="${name.value}"]`);

  e.forEach((item) => {
    item.checked = event.target.checked;
  });
  props.params.onCustomClick(event.target.checked);
};

const toggleChild = (event) => {
  const e = document.querySelectorAll(`input[type="checkbox"][name="${name.value}"]`);
  const all = document.getElementById(idAll.value);

  const valueCheck = Array.from(e).some((item) => item.checked === false);

  all.checked = !valueCheck;
  props.params.onCustomClick(props.params.data, event.target.checked);
};
</script>

<style scoped></style>
