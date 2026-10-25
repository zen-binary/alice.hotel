<template>
  <PageContainer class="page-first field_page">
    <h1 v-once>{{ pageTitle }}</h1>
    <div class="box dp_block">
      <div class="block-banner">
        <div class="block-banner__bg">
          <img src="/images/banner_field_training.webp" alt="Banner" loading="lazy" />
        </div>
        <div class="block-banner__content">
          <div class="block-banner__group">
            <h2 class="block-banner__group__title">{{ t("fieldTraining.questions") }}</h2>
            <p class="block-banner__group__description">
              {{ t("fieldTraining.intro") }}
            </p>
          </div>
        </div>
      </div>

      <div class="field_page__box">
        <p>{{ t("fieldTraining.plan") }}</p>
        <div class="field_page__box-table1">
          <div>
            <p>{{ t("fieldTraining.fieldTrainingPeriod") }}</p>
            <div>
              <p>{{ t("fieldTraining.fieldTrainingPeriodDetail1") }}</p>
              <p>{{ t("fieldTraining.fieldTrainingPeriodDetail2") }}</p>
            </div>
          </div>
          <div>
            <p>{{ t("fieldTraining.fieldTrainingLocation") }}</p>
            <div>
              <p>{{ t("fieldTraining.fieldTrainingLocationDetail") }}</p>
            </div>
          </div>
          <div>
            <p>{{ t("fieldTraining.fieldTrainingContent") }}</p>
            <div>
              <p>{{ t("fieldTraining.fieldTrainingContentDetail") }}</p>
            </div>
          </div>
        </div>
      </div>
      <div class="field_page__box">
        <p>{{ t("fieldTraining.operatingProcedure") }}</p>
        <div ref="stepContainer" class="field_page__box-step">
          <div class="field_page__box-step__item" v-for="(i, index) in arr1" :key="index">
            <div>
              <p>{{ i.title }}</p>
              <p>{{ i.content }}</p>
              <p>{{ i.des }}</p>
            </div>
            <IconArrowLeft v-if="i.activeIcon" />
          </div>
        </div>
      </div>
      <div class="field_page__box">
        <p>{{ t("fieldTraining.eligibility") }}</p>
        <div class="field_page__box-content">
          <div>
            <p>{{ t("fieldTraining.eligibilityDetail1") }}</p>
            <p>{{ t("fieldTraining.eligibilityDetail2") }}</p>
          </div>
          <div>
            <p>{{ t("fieldTraining.eligibilityDetail3") }}</p>
            <p>{{ t("fieldTraining.eligibilityDetail4") }}</p>
          </div>
        </div>
      </div>
      <div class="field_page__box">
        <p>{{ t("fieldTraining.apply") }}</p>
        <div class="field_page__box-content">
          <div>
            <p style="width: 86px">{{ t("fieldTraining.applyDetail1") }}</p>
            <p>{{ t("fieldTraining.applyDetail2") }}</p>
          </div>
        </div>
      </div>
      <div class="field_page__box">
        <p>{{ t("fieldTraining.schedule") }}</p>
        <div class="field_page__box-table2">
          <div class="field_page__box-table2--column desktop">
            <div class="table__head">
              <div><p style="text-indent: -9999px">Header empty</p></div>
              <div>
                <p>{{ t("fieldTraining.scheduleDetail1") }}</p>
              </div>
              <div>
                <p>{{ t("fieldTraining.scheduleDetail2") }}</p>
              </div>
              <div>
                <p>{{ t("fieldTraining.scheduleDetail3") }}</p>
              </div>
              <div>
                <p>{{ t("fieldTraining.scheduleDetail4") }}</p>
              </div>
            </div>
          </div>
          <div v-for="(item, index) in arr2" :key="index" class="field_page__box-table2--column">
            <div class="table__head">
              <div>
                <p>{{ item.head }}</p>
              </div>
            </div>
            <div class="table_info">
              <div v-for="(itemRow, indexRow) in item.data" :key="indexRow" class="table_info__row">
                <div class="table_info__row-mobile">
                  <p>{{ titleHead[indexRow] }}</p>
                </div>
                <div>
                  <p>{{ itemRow.content }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="field_page__footer">
        <div class="field_page__footer-left">
          <div>
            <img src="@/assets/images/icon_call.svg" alt="call" />
          </div>
          <div>
            <p>{{ t("fieldTraining.contact") }}</p>
          </div>
        </div>
        <div class="field_page__footer-right">
          <div>
            <div>
              <p>{{ t("fieldTraining.contactDetail1") }}</p>
              <p>{{ t("fieldTraining.contactDetail2") }}</p>
            </div>
            <div>
              <p>TEL</p>
              <p>054-760-1122</p>
            </div>
            <div>
              <p>FAX</p>
              <p>054-760-1129</p>
            </div>
          </div>
          <div>
            <p>{{ t("fieldTraining.contactDetail3") }}</p>
          </div>
        </div>
      </div>
    </div>
  </PageContainer>
</template>

<script setup lang="ts">
import PageContainer from "@/components/common/PageContainer.vue";
import IconArrowLeft from "@/components/icon/IconArrowLeft.vue";
import { ref } from "vue";
import { useI18n } from "vue-i18n";

const { t } = useI18n();
const pageTitle = ref(t("map.title"));

const arr1 = ref([
  {
    title: "Step 01",
    content: t("fieldTraining.step1"),
    des: "",
    activeIcon: true,
  },
  {
    title: "Step 02",
    content: t("fieldTraining.step2"),
    des: "",
    activeIcon: true,
  },
  {
    title: "Step 03",
    content: t("fieldTraining.step3"),
    des: "",
    activeIcon: true,
  },
  {
    title: "Step 04",
    content: t("fieldTraining.step4"),
    des: t("fieldTraining.desStep4"),
    activeIcon: true,
  },
  {
    title: "Step 05",
    content: t("fieldTraining.step5"),
    des: "",
    activeIcon: true,
  },
  {
    title: "Step 06",
    content: t("fieldTraining.step6"),
    des: "",
    activeIcon: true,
  },
  {
    title: "Step 07",
    content: t("fieldTraining.step7"),
    des: "",
    activeIcon: false,
  },
]);
const isWrapped = ref(false);
const stepContainer = ref(null);
const observer = ref(null);
const titleHead = ref([t("fieldTraining.titleHead1"), t("fieldTraining.titleHead2"), t("fieldTraining.titleHead3"), t("fieldTraining.titleHead4")]);
const arr2 = ref([
  {
    head: t("fieldTraining.head1"),
    data: [
      { content: t("fieldTraining.detailHead1") },
      { content: t("fieldTraining.detailHead2") },
      { content: t("fieldTraining.detailHead3") },
      { content: t("fieldTraining.detailHead4") },
    ],
  },
  {
    head: t("fieldTraining.head2"),
    data: [
      { content: t("fieldTraining.detailHead5") },
      { content: t("fieldTraining.detailHead6") },
      { content: t("fieldTraining.detailHead7") },
      { content: t("fieldTraining.detailHead8") },
    ],
  },
  {
    head: t("fieldTraining.head3"),
    data: [
      { content: t("fieldTraining.detailHead9") },
      { content: t("fieldTraining.detailHead10") },
      { content: t("fieldTraining.detailHead11") },
      { content: t("fieldTraining.detailHead12") },
    ],
  },
  {
    head: t("fieldTraining.head4"),
    data: [
      { content: t("fieldTraining.detailHead13") },
      { content: t("fieldTraining.detailHead14") },
      { content: t("fieldTraining.detailHead15") },
      { content: t("fieldTraining.detailHead16") },
    ],
  },
  {
    head: t("fieldTraining.head5"),
    data: [
      { content: t("fieldTraining.detailHead17") },
      { content: t("fieldTraining.detailHead18") },
      { content: t("fieldTraining.detailHead19") },
      { content: t("fieldTraining.detailHead20") },
    ],
  },
]);

const checkWrapping = () => {
  const items = stepContainer.value.querySelectorAll(".field_page__box-step__item");
  let previousTop = null;
  isWrapped.value = false;

  arr1.value.forEach((item, index) => {
    if (index + 1 < arr1.value.length) arr1.value[index].activeIcon = true;
  });

  for (let i = 0; i < items.length - 1; i++) {
    const currentTop = items[i].offsetTop;
    const nextTop = items[i + 1].offsetTop;

    if (currentTop !== nextTop) {
      arr1.value[i].activeIcon = false;
    }
  }
};

onMounted(() => {
  observer.value = new ResizeObserver(checkWrapping);
  observer.value.observe(stepContainer.value);

  checkWrapping();
});

onUnmounted(() => {
  observer.value.disconnect();
});
</script>

<style scoped lang="scss">
.page_container.field_page {
  color: var(--Gray-scale-90, rgba(29, 29, 29, 1));
  background: #f8f9fe;

  h1 {
    font-size: 4rem;
    font-weight: 500;
    line-height: 6rem;
    margin-bottom: 4rem;
  }

  .block-banner {
    position: relative;

    &__bg > img {
      border-radius: 8px;
      width: 100%;
      height: auto;
      object-fit: cover;
      object-position: 65% center;
      min-height: 190px;
    }

    &__content {
      position: absolute;
      top: 0;
      left: 0;
      height: 100%;
      padding: 60px;

      .block-banner__group {
        max-width: 577px;

        &__title {
          font-size: 24px;
          font-weight: 500;
          line-height: 36px;

          margin-bottom: 12px;
        }

        &__description {
          font-size: 16px;
          font-weight: 400;
          line-height: 24px;
        }
      }
    }
  }

  .field_page__box {
    margin-top: 6rem;

    & > p {
      font-size: 24px;
      font-weight: 500;
      line-height: 36px;
      margin-bottom: 2rem;
    }

    &-table1 {
      border-top: 1px solid var(--Gray-scale-80, rgba(45, 45, 45, 1));

      & > div {
        display: flex;
        border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

        & > p {
          width: 260px;
          padding: 16px;
          font-size: 14px;
          font-weight: 500;
          line-height: 21px;
          background: var(--Gray-scale-5, rgba(248, 248, 248, 1));
        }

        div {
          flex: 1;
          display: flex;
          padding: 16px;
          flex-direction: column;

          & > p {
            font-size: 14px;
            font-weight: 400;
            line-height: 20.27px;
          }
        }
      }
    }

    &-step {
      display: flex;
      flex-direction: row;
      gap: 7px;
      flex-wrap: wrap;
      row-gap: 16px;

      &__item {
        flex: 1;
        display: flex;
        align-items: center;
        gap: 7px;
        min-width: 155px;

        & > div {
          flex: 1;
          height: 100%;
          padding: 20.5px 12px;
          border: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));
          border-radius: 8px;
          text-align: center;
          display: flex;
          flex-direction: column;
          justify-content: center;

          & > p:first-child {
            font-size: 14px;
            font-weight: 500;
            line-height: 21px;
            color: var(--Primary-50_Primary, rgba(0, 152, 229, 1));
          }

          & > p:nth-child(2) {
            margin-top: 8px;
            font-size: 16px;
            font-weight: 400;
            line-height: 24px;
          }

          & > p:last-child {
            margin-top: 8px;
            font-size: 14px;
            font-weight: 400;
            line-height: 21px;
            color: var(--Gray-scale-70, rgba(85, 85, 85, 1));
          }
        }
      }
    }

    &-content {
      & div {
        display: flex;
        flex-wrap: wrap;
        gap: 8px;

        & p {
          font-size: 16px;
          line-height: 24px;
        }

        & p:first-child {
          font-weight: 500;
          padding-left: 12px;
          position: relative;
          width: 172px;

          &:before {
            content: "";
            position: absolute;
            top: 50%;
            left: 0;
            width: 4px;
            height: 4px;
            background: var(--Primary-50_Primary, rgba(0, 152, 229, 1));
            border-radius: 50%;
            transform: translateY(-50%);
          }
        }

        & p:last-child {
          font-weight: 400;
          color: var(--Gray-scale-80, rgba(45, 45, 45, 1));
        }
      }
    }

    &-table2 {
      display: flex;
      border-top: 1px solid var(--Gray-scale-80, rgba(45, 45, 45, 1));
      border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

      &--column {
        flex: 1;
        border-left: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

        .table__head {
          & div {
            background: var(--Gray-scale-5, rgba(248, 248, 248, 1));
            padding: 17.5px 0;
            text-align: center;

            &:not(:last-child) {
              border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));
            }

            & > p {
              font-size: 14px;
              font-weight: 500;
              line-height: 21px;
              white-space: nowrap;
            }
          }
        }

        .table_info {
          &__row {
            display: flex;
            justify-content: center;
            border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

            &:first-of-type {
              border-top: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));
            }

            & > div:first-of-type {
              flex: 1;
              background: var(--Gray-scale-5, rgba(248, 248, 248, 1));
              text-align: center;
              padding: 17.5px 0;

              & > p {
                font-size: 14px;
                font-weight: 500;
                line-height: 21px;
              }
            }

            & > div:last-of-type {
              flex: 2;
              background: var(--white-color);
              text-align: center;
              padding: 18px 0;

              & > p {
                font-size: 14px;
                font-weight: 400;
                line-height: 20px;
              }
            }

            &-mobile {
              display: none;
            }
          }
        }
      }

      .desktop {
        display: block;
      }
    }
  }

  .field_page__footer {
    margin-top: 24px;
    padding: 20px;
    border-radius: 8px;
    background: rgba(0, 152, 229, 0.06);
    display: flex;
    flex-wrap: wrap;
    column-gap: 6rem;
    row-gap: 2rem;

    &-left {
      display: flex;
      align-items: center;
      gap: 12px;
      //margin-right: 6rem;

      & > div:first-child {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        background: white;
        display: grid;
        place-items: center;
      }

      p {
        font-size: 18px;
        font-weight: 500;
        line-height: 27px;
        letter-spacing: -0.01em;
        color: var(--Primary-50_Primary, rgba(0, 152, 229, 1));
      }
    }

    &-right {
      display: flex;
      flex-direction: column;
      gap: 1.2rem;

      & > div:first-child {
        display: flex;
        flex-wrap: wrap;
        //gap: 4rem;
        column-gap: 4rem;
        row-gap: 1.2rem;
        color: var(--Gray-scale-80, rgba(45, 45, 45, 1));

        & div {
          display: flex;
          gap: 1.2rem;

          p:first-child {
            font-size: 16px;
            font-weight: 500;
            line-height: 24px;
          }

          p:last-child {
            font-size: 16px;
            font-weight: 400;
            line-height: 24px;
          }
        }
      }

      & > div:last-child {
        p {
          font-size: 16px;
          font-weight: 400;
          line-height: 24px;
        }
      }
    }
  }
}

@media screen and (max-width: 768px) {
  .page_container.field_page {
    h1 {
      font-size: 2.6rem;
      font-weight: 500;
      line-height: 3.9rem;
      margin-bottom: 3.6rem;
    }

    .block-banner {
      &__content {
        padding: 32px 24px;

        .block-banner__group {
          &__title {
            font-size: 20px;
            font-weight: 500;
            line-height: 30px;
            margin-bottom: 12px;
          }

          &__description {
            font-size: 14px;
            font-weight: 400;
            line-height: 21px;
          }
        }
      }
    }

    .field_page__box {
      margin-top: 4rem;

      & > p {
        font-size: 20px;
        font-weight: 500;
        line-height: 30px;
        margin-bottom: 1.6rem;
      }

      &-table1 {
        border-top: 1px solid var(--Gray-scale-80, rgba(45, 45, 45, 1));

        & > div {
          display: flex;
          flex-direction: column;
          border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

          & > p {
            width: 100%;
            padding: 17.5px 16px;
          }

          div {
            padding: 18px 16px;
          }
        }
      }

      &-step {
        display: flex;
        flex-direction: row;
        gap: 7px;
        flex-wrap: wrap;
        row-gap: 16px;

        &__item {
          flex: 1;
          display: flex;
          align-items: center;
          gap: 7px;
          min-width: 140px;

          & > div {
            flex: 1;
            height: 100%;
            padding: 16px 8px;
            border: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));
            border-radius: 8px;
            text-align: center;
            display: flex;
            flex-direction: column;
            justify-content: center;

            & > p:first-child {
              font-size: 12px;
              font-weight: 500;
              line-height: 18px;
              color: var(--Primary-50_Primary, rgba(0, 152, 229, 1));
              letter-spacing: -0.01em;
            }

            & > p:nth-child(2) {
              margin-top: 4px;
              font-size: 14px;
              font-weight: 400;
              line-height: 21px;
            }

            & > p:last-child {
              margin-top: 4px;
              font-size: 12px;
              font-weight: 400;
              line-height: 18px;
              color: var(--Gray-scale-70, rgba(85, 85, 85, 1));
            }
          }
        }
      }

      &-content {
        & div {
          display: flex;
          flex-wrap: wrap;

          & p {
            font-size: 16px;
            line-height: 24px;
          }

          & p:first-child {
            font-weight: 500;
            padding-left: 12px;
            position: relative;
            width: 180px;

            &:before {
              content: "";
              position: absolute;
              top: 50%;
              left: 0;
              width: 4px;
              height: 4px;
              background: var(--Primary-50_Primary, rgba(0, 152, 229, 1));
              border-radius: 50%;
              transform: translateY(-50%);
            }
          }

          & p:last-child {
            font-weight: 400;
            color: var(--Gray-scale-80, rgba(45, 45, 45, 1));
          }
        }
      }

      &-table2 {
        flex-direction: column;

        &--column {
          flex: 1;
          border-left: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

          .table__head {
            & div {
              background: var(--Gray-scale-5, rgba(248, 248, 248, 1));
              padding: 17.5px 0;
              text-align: center;

              &:not(:last-child) {
                border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));
              }

              & > p {
                font-size: 14px;
                font-weight: 500;
                line-height: 21px;
                white-space: nowrap;
              }
            }
          }

          .table_info {
            &__row {
              display: flex;
              justify-content: center;

              & > div:first-of-type {
                flex: 1;
                background: var(--Gray-scale-5, rgba(248, 248, 248, 1));
                text-align: center;
                padding: 17.5px 0;

                & > p {
                  font-size: 14px;
                  font-weight: 500;
                  line-height: 21px;
                }
              }

              & > div:last-of-type {
                flex: 2;
                background: var(--white-color);
                text-align: center;
                padding: 18px 0;

                & > p {
                  font-size: 14px;
                  font-weight: 400;
                  line-height: 20px;
                }
              }

              &-mobile {
                display: block;
              }
            }
          }
        }

        .desktop {
          display: none;
        }
      }
    }
  }
}
</style>
