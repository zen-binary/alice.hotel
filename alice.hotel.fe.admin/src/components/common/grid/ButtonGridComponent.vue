<template>
  <button type="button" :class="className" @click="addEvent">{{ value }}</button>
  <div v-if="id">
    <InputFileRegSelection
      :referKey="id"
      :mode="'detail'"
      :id="'fileUpload'"
      :name="'fileUpload'"
      :type="fileTypeOffice"
      :ref="childRef"
      :orgName="orgName"
      :category="category"
      :sectionName="sectionName"
    ></InputFileRegSelection>
  </div>
</template>
<script lang="ts" setup>
import { ref, onMounted } from "vue";

const props = defineProps({
  params: Object,
});

const value = ref("");
const className = ref("");
const isDisabled = ref(true);
const type = ref("");
const childRef = ref("childRef");
const orgName = ref("");
const category = ref("");
const sectionName = ref("");
const id = ref(null);

onMounted(() => {
  if (props.params) {
    value.value = props.params.value || "";
    className.value = props.params.className || "";
    id.value = props.params.data?.[props.params.id] || null;
    type.value = props.params.type || "";
    orgName.value = props.params.orgName || "";
    category.value = props.params.category || "";
    sectionName.value = props.params.sectionName || "";
    isDisabled.value = props.params.isDisabled ?? true;
  }
});

function addEvent() {
  if (typeof props.params.onCustomEvent === "function") {
    props.params.onCustomEvent(props.params.data);
  }

  //   if (type.value === TYPE_BUTTON_DOWLOAD) {
  //     const refFile = childRef.value;
  //     const fileInfo = refFile.fileUploadedInfo;
  //     fileInfo?.forEach((item) => {
  //       refFile.dowload(item.fileName, item.uploadDirectory);
  //     });
  //   }
}
</script>
