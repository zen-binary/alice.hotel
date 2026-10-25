<template>
  <THeaderHorizontalV2 :is-banner="true" />
  <PageContainer class="map_page">
    <h1 v-once>{{ pageTitle }}</h1>
    <div class="box dp_block">
      <div class="f_child">
        <img src="@/assets/images/img_visit_map1.webp" alt="image_map_1" />
        <div class="f_child_des">
          <p>{{ t("map.phone") }}</p>
          <div>
            <p>TEL. 054 - 760 - 1122~1128</p>
            <p>FAX. 054 - 760 -0000</p>
          </div>
        </div>
      </div>
      <div class="group_image_map">
        <div>
          <img src="@/assets/images/img_visit_map2.webp" alt="image_map_2" />
        </div>
        <div>
          <img src="@/assets/images/img_visit_map3.webp" alt="image_map_3" />
        </div>
      </div>

      <div class="map_card">
        <div class="map_card__left">
          <p>{{ t("map.visitorGuide") }}</p>
          <p>{{ t("map.visitorGuideDetail") }}</p>
        </div>
        <div class="map_card__right">
          <div class="card_item" v-for="(i, index) in listArr" :key="index">
            <p>{{ i.title }}</p>
            <ul>
              <li v-for="(item, itemIndex) in i.des" :key="itemIndex">{{ item }}</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </PageContainer>
  <TFooter />
  <LoadingComponent v-if="store.loading" />
</template>

<script setup lang="ts">
import PageContainer from "@/components/common/PageContainer.vue";
import LoadingComponent from "@/components/common/LoadingComponent.vue";
import TFooter from "@/layouts/components/TFooter.vue";
import { commonStore } from "@/stores/common";
import { ref } from "vue";
import THeaderHorizontalV2 from "@/layouts/components/THeaderHorizontalV2.vue";
import { useI18n } from "vue-i18n";

const { t } = useI18n();
const store = commonStore();
const pageTitle = ref(t("map.title"));

const listArr = ref([
  {
    title: t("map.city1"),
    des: [t("map.detailCity1"), t("map.content")],
  },
  {
    title: t("map.city2"),
    des: [t("map.detailCity2")],
  },
  {
    title: t("map.city3"),
    des: [t("map.detailCity3")],
  },
  {
    title: t("map.city4"),
    des: [t("map.detailCity4")],
  },
  {
    title: t("map.city5"),
    des: [t("map.detailCity5")],
  },
  {
    title: t("map.city6"),
    des: [t("map.detailCity6")],
  },
]);
</script>

<style scoped lang="scss">
.page_container.map_page {
  padding-top: calc(var(--height--horizontal) + 6rem);
  padding-bottom: 10rem;
  background: rgb(248, 249, 254);
  min-height: 100dvh;

  h1 {
    font-size: 4rem;
    font-weight: 500;
    line-height: 6rem;
    margin-bottom: 4rem;
  }

  .f_child {
    position: relative;
    margin-bottom: 24px;

    &_des {
      position: absolute;
      bottom: 32px;
      right: 32px;
      background: rgba(255, 255, 255, 1);
      padding: 24px;
      border-radius: 8px;

      p {
        font-size: 18px;
        font-weight: 500;
        line-height: 27px;
        letter-spacing: -0.01em;
      }

      & > div {
        display: flex;
        flex-wrap: wrap;
        justify-content: flex-end;
        gap: 4px;
        margin-top: 8px;

        p {
          font-size: 16px;
          font-weight: 400;
          line-height: 24px;
          position: relative;
          margin-left: 8px;
        }

        p:first-child {
          padding-right: 12px;
        }

        p:first-child:after {
          content: "";
          position: absolute;
          top: 6px;
          right: 0;
          width: 1px;
          height: 16px;
          background: var(--Gray-scale-20, rgba(228, 228, 228, 1));
        }
      }
    }
  }

  img {
    max-width: 100%;
    min-height: 380px;
    width: 100%;
    height: auto;
    object-fit: cover;
    border-radius: 8px;
  }

  .group_image_map {
    display: flex;
    flex-wrap: wrap;
    gap: 24px;

    & div {
      flex: 1;

      & > img {
        min-height: 200px;
        min-width: 200px;
      }
    }
  }

  .map_card {
    display: flex;
    position: relative;
    margin-top: 6rem;

    &__left {
      position: absolute;
      left: 0;
      top: 0;

      & p:first-child {
        font-size: 24px;
        font-weight: 500;
        line-height: 36px;
        margin-bottom: 16px;
      }

      & p:last-child {
        max-width: 158px;
        font-size: 16px;
        font-weight: 400;
        line-height: 24px;
        color: var(--Gray-scale-70, rgba(85, 85, 85, 1));
      }
    }

    &__right {
      flex: 1;
      display: flex;
      justify-content: flex-end;
      gap: 24px;
      flex-wrap: wrap;

      .card_item {
        width: 332px;
        padding: 32px 24px;
        border: 1px solid var(--Gray-scale-30, rgba(216, 216, 216, 1));
        border-radius: 8px;

        & p {
          font-size: 18px;
          font-weight: 500;
          line-height: 27px;

          &:before {
            content: "";
            display: inline-block;
            background-color: rgba(183, 224, 255, 1);
            background-image: url("@/assets/images/icon_addr.png");
            background-position: center center;
            background-repeat: no-repeat;
            width: 48px;
            height: 48px;
            border-radius: 8px;
            margin-right: 12px;
          }
        }

        & ul {
          display: flex;
          flex-direction: column;
          gap: 8px;
          margin-top: 16px;

          & li {
            position: relative;
            font-size: 16px;
            font-weight: 400;
            line-height: 24px;
            padding-left: 12px;

            &:before {
              content: ".";
              position: absolute;
              top: -10px;
              left: 0;
              font-size: 3rem;
              vertical-align: middle;
            }
          }
        }

        &:nth-child(4),
        &:nth-child(1) {
          margin-left: auto;
        }

        &:nth-child(6) {
          margin-right: auto;
        }

        &:nth-child(1) {
          & p {
            &:before {
              background-color: rgba(183, 224, 255, 1);
            }
          }
        }

        &:nth-child(2) {
          & p {
            &:before {
              background-color: #deeeb8;
            }
          }
        }

        &:nth-child(3) {
          & p {
            &:before {
              background-color: #fbc9c9;
            }
          }
        }

        &:nth-child(4) {
          & p {
            &:before {
              background-color: #e0cefb;
            }
          }
        }

        &:nth-child(5) {
          & p {
            &:before {
              background-color: #bcc9fb;
            }
          }
        }

        &:nth-child(6) {
          & p {
            &:before {
              background-color: #fbe9c9;
            }
          }
        }
      }
    }
  }
}

@media screen and (max-width: 768px) {
  .page_container.map_page {
    padding-top: calc(var(--height--horizontal) + 4rem);

    h1 {
      margin-bottom: 3.6rem;
      font-size: 26px;
      font-weight: 500;
      line-height: 39px;
      letter-spacing: -0.02em;
      text-align: left;
    }

    .f_child {
      &_des {
        bottom: 16px;
        right: 16px;
        max-width: calc(100% - 32px);
        width: 100%;
        text-align: right;
        padding: 16px;

        p {
          font-size: 16px;
          font-weight: 500;
          line-height: 24px;
          letter-spacing: -0.02em;
        }

        > div {
          p {
            font-size: 14px;
            font-weight: 400;
            line-height: 21px;
            //white-space: nowrap;
          }

          p:first-child:after {
            top: 3px;
          }
        }
      }
    }

    .map_card {
      padding-top: 91px;
      margin-top: 3.6rem;

      .map_card__left {
        p:first-child {
          font-size: 20px;
          font-weight: 500;
          line-height: 30px;
          margin-bottom: 12px;
        }

        p:last-child {
          font-size: 14px;
          font-weight: 400;
          line-height: 21px;
          max-width: 100%;
          width: 100%;
        }
      }

      .map_card__right {
        gap: 12px;

        .card_item {
          max-width: 100%;
          width: 100%;
        }
      }
    }
  }
}

@media screen and (max-width: 480px) {
  .page_container.map_page {
    .map_card {
      &__right {
        .card_item {
          padding: 16px;
        }
      }
    }
  }
}
</style>
