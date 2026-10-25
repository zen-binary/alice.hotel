<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div class="block-banner">
    <div class="block-banner__bg">
      <img :src="imgUrl" alt="Banner" loading="lazy" />
    </div>
    <div class="block-banner__content">
      <div class="block-banner__group">
        <h1 class="block-banner__group__title">{{ globalCommon.capitalize(title) }}</h1>
        <p
          class="block-banner__group__description"
          :class="[{ lastChild: index + 1 === description.length }, classDescription]"
          v-for="(i, index) in description"
          :key="index"
        >
          {{ i }}
        </p>
        <p class="block-banner__group__info" v-for="(i, index) in info" :key="index">{{ i }}</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import globalCommon from "@/utils/global";

const props = defineProps({
  imgUrl: {
    type: String,
    default: "https://via.placeholder.com/1920x680",
  },
  title: {
    type: String,
    default: "WE ARE",
  },
  description: {
    type: Array as () => string[],
    default: ["현장실습이란"],
  },
  classDescription: {
    type: String,
    default: "",
  },
  info: {
    type: Array as () => string[],
    default: ["기업체 현장경험을 통해서", "취업 기회와 나의 진로를 찾아볼 수 있는 소중한 기회입니다"],
  },
});
</script>

<style scoped lang="scss">
.block-banner {
  position: relative;
  margin-bottom: 80px;

  &__bg {
    img {
      width: 100%;
      height: auto;
      object-fit: cover;
      object-position: 65% center;
      min-height: 540px;
    }
  }

  &__content {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    color: #fff;
    max-width: calc(var(--main-width-vertical) + 2rem);
    padding: 2rem;
    width: 100%;
  }

  &__group {
    width: 100%;
    margin: 0 auto;

    &__title {
      font-weight: 700;
      padding-left: 24px;
      position: relative;
      color: rgba(38, 170, 255, 1);
      font-size: 16px;
      line-height: 28px;

      text-align: left;
    }

    &__title:before {
      content: "";
      position: absolute;
      top: 50%;
      left: 0;
      width: 20px;
      height: 2px;
      background: rgba(38, 170, 255, 1);
    }

    &__description {
      margin-top: 32px;
      margin-bottom: 16px;
      font-size: 60px;
      font-weight: 500;
      line-height: 63px;

      text-align: left;
      color: var(--Gray-scale-90, rgba(29, 29, 29, 1));
    }

    &__description.lastChild {
      margin-top: 0px !important;
      margin-bottom: 28px;
    }

    &__info {
      font-size: 16px;
      font-weight: 400;
      line-height: 24px;

      text-align: left;
      color: var(--Gray-scale-70, rgba(85, 85, 85, 1));
    }
  }
}

.banner_description_login {
  color: var(--white-color);
}

@media screen and (max-width: 768px) {
  .block-banner {
    margin-bottom: calc(var(--height--horizontal) + 80px);

    &__bg {
      position: relative;
      top: var(--height--horizontal);
    }

    &__content {
      top: 40%;
    }

    &__group {
      &__title {
        font-size: 14px;
      }

      &__description {
        margin-top: 16px;
        margin-bottom: 12px;
        font-size: 30px;
        font-weight: 500;
        line-height: 45px;
        letter-spacing: -0.02em;
      }

      &__info {
        max-width: 200px;
        font-size: 14px;
        font-weight: 400;
        line-height: 21px;
        letter-spacing: -0.02em;
        text-align: left;
      }
    }
  }
}
</style>
