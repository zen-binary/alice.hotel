<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<script lang="ts" setup>
import IconArrowMore from "@/components/icon/IconArrowMore.vue";
import { ref } from "vue";
interface ItemsData {
  id: string | number;
  day: string;
  date: string;
  title: string;
}
const props = defineProps({
  tabs: {
    type: Array,
    default: () => [],
  },
  items: {
    type: Array as () => ItemsData[][],
    default: () => [],
  },
});
const emit = defineEmits(["handelRedirectToDetail", "handelSeeMore"]);
const tabActive = ref(1);
</script>

<template>
  <div class="block-menu-tab">
    <!-- Menu Tabs -->
    <div class="menu">
      <div class="menu__bg-block">
        <div
          v-for="(tab, index) in tabs"
          :key="index"
          class="menu__bg-block__text"
          :class="{ active: tabActive === index + 1 }"
          @click="tabActive = index + 1"
        >
          {{ tab }}
        </div>
      </div>
      <div class="menu__see-more" @click="emit('handelSeeMore')">
        <span>More</span>
        <IconArrowMore />
      </div>
    </div>
    <!-- Content Items -->
    <div v-for="(itemGroup, groupIndex) in items" :key="groupIndex" class="content" v-show="tabActive == groupIndex + 1">
      <a v-for="(item, index) in itemGroup" :key="index" href="javascript:;" @click="emit('handelRedirectToDetail', item.id)">
        <div class="content__date">
          <strong>{{ item?.day }}</strong>
          <span>{{ item?.date }}</span>
        </div>
        <div class="content__title">
          <p>{{ item?.title }}</p>
        </div>
      </a>
    </div>

    <!-- See More -->
    <div class="block-menu-tab__footer">
      <div class="block-menu-tab__footer__see-more" @click="emit('handelSeeMore')">
        <span>More</span>
        <IconArrowMore />
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.block-menu-tab {
  .menu {
    display: flex;
    justify-content: space-between;
    border-bottom: 1px solid var(--gray--1d);
    &__bg-block {
      display: flex;
      &__text {
        font-size: 24px;
        font-weight: 500;
        cursor: pointer;
        padding: 0px 60px 36px 0;
        flex-grow: 1;
        color: var(--gray--71);
        @media screen and (max-width: 768px) {
          font-size: 18px;
          padding: 0px 20px 32px 0;
        }
        @media screen and (max-width: 480px) {
          font-size: 15px;
          padding: 0px 20px 32px 0;
        }
        &:hover {
          color: var(--gray--1d);
        }
        &:first-child {
          padding-left: 0;
        }
      }
      .active {
        color: var(--gray--1d);
      }
    }
    &__see-more {
      cursor: pointer;
      display: flex;
      @media screen and (max-width: 480px) {
        display: none;
      }
      span {
        font-size: 16px;
        font-weight: 400;
      }
    }
  }
  .content {
    display: flex;
    padding-left: 0;
    flex-direction: column;
    a {
      padding: 20px 0;
      border-bottom: 1px solid var(--gray--d6);
      display: flex;
      align-items: center;
    }
    &__date {
      margin-bottom: 2px;
      width: 55px;
      flex-shrink: 0;
      color: var(--gray--1d);
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-right: 56px;
      @media screen and (max-width: 768px) {
        margin-right: 28px;
      }
      strong {
        font-size: 36px;
        font-weight: 700;
        @media screen and (max-width: 480px) {
          font-size: 28px;
        }
      }
      span {
        font-size: 14px;
        font-weight: 400;
      }
    }
    &__title {
      font-size: 16px;
      font-weight: 500;
    }
  }
  &__footer {
    display: none;
    justify-content: flex-end;
    margin-top: 28px;
    @media screen and (max-width: 480px) {
      display: flex;
    }
    &__see-more {
      cursor: pointer;
      display: none;
      @media screen and (max-width: 480px) {
        display: flex;
      }
      span {
        font-size: 14px;
        font-weight: 400;
      }
    }
  }
}
</style>
