<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div class="card_container" v-bind="attrs">
    <div class="card_header">
      <slot name="header" />
    </div>
    <div class="card_image">
      <slot name="image" />
    </div>
    <div class="card_content">
      <div class="card_content_top">
        <div class="content_top_left">
          <div class="card_content_header">
            <div class="card_content_status" :style="{ borderRadius }">
              <slot name="status" />
            </div>
            <div class="card_content_date">
              <slot name="date" />
            </div>
          </div>
          <div class="card_content_title">
            <slot name="title" />
          </div>
        </div>
        <div v-if="isShowIcon" class="content_top_right">
          <slot name="icon" />
        </div>
      </div>
      <div class="card_content_body">
        <slot name="body" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, useAttrs } from "vue";

const attrs = useAttrs();
defineOptions({
  inheritAttrs: false,
});

const props = defineProps({
  borderShape: {
    type: String,
    default: "rounded",
    validator: (value: string) => ["rounded", "normal"].includes(value),
  },
  isShowIcon: {
    type: Boolean,
    default: false,
  },
});

const borderRadius = ref(props.borderShape === "rounded" ? "100px" : "4px");
</script>

<style lang="scss">
.card_container {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.12);
}

.card_image > img {
  width: 100%;
  height: auto;
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
}

.card_content {
  display: flex;
  flex-direction: column;
  padding: 24px 20px;
  height: inherit;

  .card_content_top {
    display: flex;
    justify-content: space-between;
    gap: 8px;
    height: inherit;
    margin-bottom: 16px;

    .content_top_left {
      height: inherit;
      display: flex;
      flex-direction: column;
      gap: 8px;

      .card_content_title {
        flex-grow: 1;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        gap: 8px;

        .title_title {
          font-size: 18px;
          font-weight: 500;
          line-height: 27px;
          letter-spacing: -0.01em;
          text-align: left;
        }

        .title_des {
          font-size: 14px;
          font-weight: 500;
          line-height: 21px;
          letter-spacing: -0.02em;
          text-align: left;
          color: var(--Gray-scale-60, rgba(113, 113, 113, 1));
          display: flex;
          align-items: flex-end;
        }

        .card_custom_content {
          margin-top: 8px;
          display: flex;
        }
      }

      .card_content_header {
        display: flex;
        align-items: center;
        gap: 12px;

        .card_content_date > p,
        .card_content_date > span {
          font-size: 14px;
          font-weight: 500;
          line-height: 21px;
          color: var(--Gray-scale-90, rgba(29, 29, 29, 1));
        }
      }
    }

    .card_content_status > p,
    .card_content_status > span {
      font-size: 12px;
      font-weight: 500;
      line-height: 12px;
    }
  }
}

.card_content_status {
  width: max-content;
  padding: 6px 8px;
  background: var(--Gray-scale-50, rgba(190, 190, 190, 1));
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
}

.card_content_status:has(p[data-status="1"]) {
  background: var(--Gray-scale-30, rgba(216, 216, 216, 1));
}

.card_content_status:has(p[data-status="2"]) {
  background: rgba(255, 109, 133, 1);
}

.card_content_status:has(p[data-status="3"]) {
  background: var(--Gray-scale-50, rgba(190, 190, 190, 1));
}

.card_content_status:has(p[data-status="4"]) {
  background: rgba(92, 200, 255, 1);
}

.card_content_status:has(p[data-status="5"]) {
  background: var(--Gray-scale-50, rgba(190, 190, 190, 1));
}

.card_content_status:has(p[data-status="6"]) {
  background: rgba(0, 152, 229, 1);
}

.card_content_status:has(p[data-status="7"]) {
  background: var(--Gray-scale-60, rgba(113, 113, 113, 1));
}

.card_content_status:has(p[data-status="8"]) {
  background: rgba(92, 200, 255, 1);
}

.card_content_status:has(p[data-status="9"]) {
  background: var(--Gray-scale-50, rgba(190, 190, 190, 1));
}

.card_content_status:has(p[data-status="10"]) {
  background: rgba(255, 194, 106, 1);
}

.card_content_status:has(p[data-status="11"]) {
  background: rgba(224, 239, 247, 1);
  color: rgba(0, 152, 229, 1);
}

.card_content_status:has(p[data-status="12"]) {
  background: var(--Gray-scale-20, rgba(228, 228, 228, 1));
  color: var(--Gray-scale-60, rgba(113, 113, 113, 1));
}
</style>
