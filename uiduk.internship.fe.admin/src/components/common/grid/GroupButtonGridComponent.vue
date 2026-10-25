<template>
  <div class="mg_t5">
    <button class="button btn_xs btn_primary hg_30" type="button" :disabled="isDisabled1" @click="addEvent1">
      {{ value1 }}
    </button>
    <button v-if="isCheck2" class="button btn_xs btn_lightgray hg_30 mg_l10" type="button" :disabled="isDisabled2" @click="addEvent2">
      {{ value2 }}
    </button>
  </div>
</template>

<script>
export default {
  props: {
    params: {},
    onCustomEvent1: null,
    onCustomEvent2: null,
  },
  mounted() {
    if (this.params) {
      this.value1 = this.params.value1;
      this.isDisabled1 = this.params.isDisabled1;
      this.value2 = this.params.value2;
      this.isDisabled2 = this.params.isDisabled2;
      this.isCheck2 = this.params.data.isCheck;
    }
  },
  data() {
    return {
      value1: "",
      value2: "",
      isDisabled1: false,
      isDisabled2: false,
      isCheck1: true,
      isCheck2: true,
    };
  },
  methods: {
    addEvent1() {
      if (typeof this.params.onCustomEvent1 === "function") {
        this.params.onCustomEvent1(this.params.data);
      }
    },
    addEvent2() {
      if (typeof this.params.onCustomEvent2 === "function") {
        this.params.onCustomEvent2(this.params.data);
      }
    },
  },
};
</script>
