<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <TModal :is-open="modelValue" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" class-size="medium-large" @close-modal="closeModal">
    <!-- Modal content goes here -->
    <template #title>
      <strong>산업체 현장실습 지도교수 평가서</strong>
    </template>
    <template #default>
      <div id="instructor_evaluation" class="instructor_evaluation">
        <div class="instructor_evaluation__title">
          <h2>현장실습명이 나오는 부분입니다.</h2>
          <div class="instructor_evaluation__title__right">
            <div class="right_select_box">
              <SelectBoxBase data="" />
            </div>
            <div>
              <div class="right_select_box">
                <SelectBoxBase data="" />
              </div>
              <!--              <button class="button btn_white btn_md">PDF 다운로드</button>-->
              <ButtonExporPDF id="instructor_evaluation" nameExport="instructorEvaluation" />
            </div>
          </div>
        </div>
        <div class="instructor_evaluation__content">
          <h3>평가대상 학생정보</h3>
          <div class="instructor_evaluation__content__table">
            <div class="table_row">
              <div class="table_col">
                <div><p>실습생 소속</p></div>
                <div class="table_col__text"><p>디자인 학과</p></div>
              </div>
              <div class="table_col">
                <div><p>실습기관명</p></div>
                <div class="table_col__text"><p>삼성전자</p></div>
              </div>
            </div>
            <div class="table_row">
              <div class="table_col">
                <div><p>실습생 성명</p></div>
                <div class="table_col__text"><p>홍길동</p></div>
              </div>
              <div class="table_col">
                <div><p>현장실습기간</p></div>
                <div class="table_col__text"><p>2024.10.01 ~ 2024.11.01(4주)</p></div>
                <!--                <div class="table_col__text"><InputBase model-value=""/></div>-->
              </div>
            </div>
            <div class="table_row">
              <div class="table_col">
                <div><p>실습생 근무부서</p></div>
                <div>
                  <InputBase model-value="" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="instructor_evaluation__content">
          <h3>평가대상 학생정보</h3>
          <div class="instructor_evaluation__content__table1">
            <div class="table_row__mobile">
              <p>평가점수(점)</p>
              <p>탁월-20 / 우수-16 / 보통-12 / 미흡-8 / 매우미흡-4</p>
              <p>기준점수 20(점)</p>
            </div>
            <div class="table_row">
              <div class="table_row__head"><p>평가항목</p></div>
              <div class="table_row__head"><p>기준점수</p></div>
              <div class="table_row__head">
                <div class="wd_p100 ta_c"><p>평가</p></div>
                <div>
                  <p>탁월</p>
                  <p>우수</p>
                  <p>보통</p>
                  <p>미흡</p>
                  <p>매우 미흡</p>
                </div>
              </div>
              <div class="table_row__head"><p>평가점수</p></div>
            </div>
            <div class="table_row" v-for="(i, index) in dataObject" :key="index">
              <div class="table_row__head">
                <p>{{ i.title }}</p>
              </div>
              <div class="table_row__head"><p>25점</p></div>
              <div class="table_row__head">
                <div></div>
                <div>
                  <p>25</p>
                  <p>20</p>
                  <p>15</p>
                  <p>10</p>
                  <p>5</p>
                </div>
              </div>
              <div class="table_row__head">
                <div>
                  <InputBase model-value="" />
                </div>
              </div>
            </div>
            <div class="table_row">
              <div class="table_row__head"><p>평가 총점</p></div>
              <div class="table_row__head"><p>-/100</p></div>
            </div>
          </div>
        </div>
        <div class="instructor_evaluation__content">
          <h3>평가대상 학생정보</h3>
          <div class="instructor_evaluation__content__table2">
            <div class="table_row">
              <div class="table_row__head">
                <p>성적 종합평가</p>
              </div>
              <div class="table_row__head">
                <div class="table_row__head__content">
                  <div>
                    <p>실습기관 평가점수</p>
                    <p>(총 100점 만점)</p>
                  </div>
                  <div><p>96점</p></div>
                </div>
                <div class="table_row__head__content">
                  <div>
                    <p>지도교수 평가점수</p>
                    <p>(총 100점 만점)</p>
                  </div>
                  <div><p>94점</p></div>
                </div>
                <div class="table_row__head__content">
                  <div><p>합계</p></div>
                  <div><p>190점</p></div>
                </div>
              </div>
            </div>
            <div class="table_row">
              <div class="table_row__head">
                <p>이수구분(Pass/Fail)</p>
              </div>
              <div class="table_row__head">
                <div class="table_row__head__content">
                  <div>
                    <p>Pass</p>
                    <p>(합계 140점 이상)</p>
                  </div>
                  <div><p>190점</p></div>
                </div>
                <div class="table_row__head__content">
                  <div>
                    <p>Fail</p>
                    <p>(합계 140점 미만)</p>
                  </div>
                  <div><p>-점</p></div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="instructor_evaluation__content">
          <p>위와 같이 산업체 현장실습 방문지도 보고서를 제출합니다.</p>
          <div class="group_file__content">
            <p>2024년 10월 1일</p>
            <p>디자인학과</p>
            <p>홍길동 교수</p>
            <InputFileBase :mode="`create`" :id="`instructor_evaluation`" :type="'img'" :preview-flag="true" :show-mess="false" />
          </div>
          <p>위덕대학교 현장실습지원센터장 귀하</p>
        </div>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button type="button" class="button btn_xl btn_primary" @click="cancelAction">확인</button>
    </template>
  </TModal>
</template>

<script setup lang="ts">
import TModal from "@/components/common/excel/TModal.vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import InputBase from "@/components/common/input/InputBase.vue";
import InputFileBase from "@/components/common/input/InputFileBase.vue";
import ButtonExporPDF from "@/components/common/ButtonExporPDF.vue";

defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
  modalId: {
    type: String,
    default: true,
  },
});

const emit = defineEmits(["update:modelValue"]);
const dataObject = ref([
  {
    title: "평가항목",
    baselineScore: "20점",
    evaluation: "평가",
    score: "평가점수",
  },
  {
    title: "평가항목",
    baselineScore: "20점",
    evaluation: "평가",
    score: "평가점수",
  },
  {
    title: "평가항목",
    baselineScore: "20점",
    evaluation: "평가",
    score: "평가점수",
  },
  {
    title: "평가항목",
    baselineScore: "20점",
    evaluation: "평가",
    score: "평가점수",
  },
]);

// Modal
const closeModal = () => {
  emit("update:modelValue", false);
};
const confirmAction = () => {
  // Handle confirm action
  closeModal();
};
const cancelAction = () => {
  // Handle cancel action
  closeModal();
};
</script>

<style scoped lang="scss">
.instructor_evaluation {
  color: var(--Gray-scale-90, rgba(29, 29, 29, 1));

  &__title {
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-wrap: wrap;
    gap: 12px;

    & > h2 {
      font-size: 18px;
      font-weight: 500;
      line-height: 27px;
    }

    &__right {
      display: flex;
      align-items: center;
      gap: 8px;

      .right_select_box {
        width: 160px;
      }

      & > div {
        display: flex;
        align-items: center;
        gap: 8px;
      }
    }
  }

  &__content {
    margin-top: 20px;
    margin-bottom: 24px;

    & > h3 {
      font-size: 16px;
      font-weight: 500;
      line-height: 24px;
      position: relative;
      padding-left: 12px;
      margin-bottom: 8px;

      &:before {
        content: "";
        position: absolute;
        top: 50%;
        left: 0;
        transform: translateY(-50%);
        width: 4px;
        height: 4px;
        border-radius: 50%;
        background: var(--Primary-50_Primary, rgba(0, 152, 229, 1));
      }
    }

    &__table {
      border-top: 1px solid #2d2d2d;

      .table_row {
        display: flex;

        .table_col {
          width: 100%;
          display: flex;
          align-items: center;
          border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

          & > div:first-child {
            width: 200px;
            background: var(--Gray-scale-5, rgba(248, 248, 248, 1));
            padding: 17.5px 16px;

            & p {
              font-size: 14px;
              font-weight: 500;
              line-height: 21px;
              color: var(--Gray-scale-90, rgba(29, 29, 29, 1));
            }
          }

          & > div:last-child {
            padding: 8px 16px;
            //word-break: break-word;
            overflow-wrap: break-word;
            flex: 1;

            & p {
              font-size: 14px;
              font-weight: 400;
              line-height: 21px;
              color: var(--Gray-scale-80, rgba(45, 45, 45, 1));
            }
          }
        }
      }
    }

    &__table1 {
      border-top: 1px solid #2d2d2d;
      border-bottom: 1px solid #2d2d2d;

      .table_row__mobile {
        display: none;
      }

      .table_row {
        display: flex;
        border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

        &__mobile {
          display: none;
        }

        &:nth-child(2) {
          background: var(--Gray-scale-5, rgba(248, 248, 248, 1)) !important;
        }

        &:nth-last-child(1) {
          .table_row__head:nth-child(1) {
            flex: 9;
            background: var(--Gray-scale-5, rgba(248, 248, 248, 1));
          }

          .table_row__head:nth-child(2) {
            flex: 3;
          }
        }

        p {
          font-size: 14px;
          font-weight: 500;
          line-height: 21px;
          padding: 9.5px 0;
        }

        &__head {
          display: flex;
          justify-content: center;
          align-items: center;
          border-right: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

          &:nth-child(1),
          &:nth-child(2) {
            flex: 2;
          }

          &:nth-child(3) {
            flex: 5;
            display: flex;
            flex-direction: column;

            & > div:nth-child(1) {
              border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));
            }

            & > div:nth-child(2) {
              display: flex;
              width: 100%;

              & p {
                flex: 1;
                white-space: nowrap;
                text-align: center;
              }
            }
          }

          &:nth-child(4) {
            flex: 3;
          }

          &:nth-last-child(1) {
            border-right: none;
            padding: 8px 16px;

            > div {
              width: 100%;
            }
          }
        }
      }
    }

    &:nth-last-child(1) {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      gap: 24px;
      white-space: normal;

      & > p:first-child {
        font-size: 18px;
        font-weight: 500;
        line-height: 27px;
        color: var(--Gray-scale-80, rgba(45, 45, 45, 1));
        text-align: center;
      }

      & > p:last-child {
        font-size: 16px;
        font-weight: 500;
        line-height: 24px;
        color: var(--Gray-scale-70, rgba(85, 85, 85, 1));
        text-align: center;
      }
    }

    .group_file__content {
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
      align-items: center;
      gap: 24px;

      & p {
        font-size: 16px;
        font-weight: 500;
        line-height: 24px;
        color: var(--Gray-scale-70, rgba(85, 85, 85, 1));
      }

      .add_photo {
        background: red;
      }
    }

    &__table2 {
      display: flex;
      justify-content: center;

      .table_row {
        flex: 1;

        &:nth-child(1) {
          flex: 3;
        }

        &:nth-child(2) {
          flex: 2;
        }

        &__head {
          display: flex;
          justify-content: center;
          box-sizing: border-box;

          & > div {
            flex: 1;
            text-align: center;
          }

          &:nth-child(1) {
            border-top: 1px solid var(--Gray-scale-80, rgba(45, 45, 45, 1));
            background: var(--Gray-scale-5, rgba(248, 248, 248, 1));
            border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));
            padding: 9.5px;

            p {
              font-size: 14px;
              font-weight: 500;
              line-height: 21px;
            }
          }

          &:nth-child(2) {
            .table_row__head__content {
              display: flex;
              flex-direction: column;

              & > div:first-child {
                flex: 1;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                gap: 2px;
                background: var(--Gray-scale-5, rgba(248, 248, 248, 1));
                padding: 8px 0;
                border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

                & p {
                  font-size: 12px;
                  font-weight: 500;
                  line-height: 18px;
                  letter-spacing: -0.01em;
                  color: var(--Gray-scale-60, rgba(113, 113, 113, 1));

                  &:first-child {
                    font-size: 14px;
                    font-weight: 500;
                    line-height: 21px;
                    color: var(--Gray-scale-90, rgba(29, 29, 29, 1));
                  }
                }
              }

              & > div:last-child {
                padding: 17.5px 0;
                border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

                p {
                  font-size: 14px;
                  font-weight: 400;
                  line-height: 21px;
                  letter-spacing: -0.02em;
                  color: var(--Gray-scale-80, rgba(45, 45, 45, 1));
                }
              }
            }
          }
        }
      }
    }
  }
}

@media screen and (max-width: 768px) {
  .instructor_evaluation {
    &__title {
      & > h2 {
        font-size: 18px;
        font-weight: 500;
        line-height: 27px;
      }

      &__right {
        display: flex;
        align-items: center;
        gap: 8px;
        width: 100%;

        .right_select_box {
          width: 100%;
        }
        & > div:first-child {
          flex: 2;
          width: 100%;
        }
        & > div:last-child {
          flex: 3;
          width: 100%;
        }
      }
    }

    &__content {
      .table_row {
        flex-direction: column;
      }

      &__table1 {
        border-top: none;
        border-bottom: none;

        .table_row__mobile {
          padding: 16px;
          border-radius: 8px;
          background: var(--Gray-scale-5, rgba(248, 248, 248, 1));
          margin-bottom: 20px;
          display: flex;
          flex-direction: column;
          gap: 4px;

          p {
            font-size: 14px;
            font-weight: 500;
            line-height: 21px;
          }
        }

        .table_row {
          display: flex;
          border-bottom: none;
          margin-top: 12px;

          &:nth-child(1) {
            display: block;
          }

          &:nth-child(2) {
            display: none;
          }

          p {
            font-size: 14px;
            font-weight: 500;
            line-height: 21px;
            padding: 0;
          }

          &__head {
            display: flex;
            justify-content: center;
            border-bottom: none;
            border-right: none;

            &:nth-child(1) {
              flex: 2;
            }

            &:nth-last-child(1) {
              border-right: none;
              padding: 8px 0px;

              > div {
                width: 100%;
              }
            }
          }

          &:nth-last-child(1) {
            border-top: 1px solid var(--Gray-scale-80, rgba(45, 45, 45, 1));
            border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

            .table_row__head {
              p {
                padding: 9.5px;
              }
            }

            .table_row__head:nth-last-child(1) {
              padding: 0;
            }
          }

          &:not(:nth-last-child(1)) {
            .table_row__head {
              justify-content: flex-start;
              border-right: none;

              &:nth-child(2),
              &:nth-child(3) {
                display: none;
              }
            }
          }
        }
      }

      &__table2 {
        flex-direction: column;
      }
    }
  }
}

@media screen and (max-width: 480px) {
  .instructor_evaluation {
    &__title {
      display: flex;
      align-items: flex-start;
      flex-direction: column-reverse;

      & > h2 {
        font-size: 18px;
        font-weight: 500;
        line-height: 27px;
      }

      &__right {
        flex-direction: column;
      }
    }

    &__content {
      &__table {
        .table_row {
          border-bottom: none;

          .table_col {
            flex-direction: column;
            align-items: flex-start;
            border-top: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));

            & > div {
              width: 100% !important;
            }

            & > div.table_col__text {
              padding: 18px 16px;

              & p {
                font-size: 14px;
                font-weight: 400;
                line-height: 20px;
              }
            }
          }
        }
      }

      &__table2 {
        flex-direction: column;

        .table_row {
          flex: 1;

          &__head {
            display: flex;
            flex-direction: column;
            text-align: center;

            &:nth-child(2) {
              .table_row__head__content {
                display: flex;
                flex-direction: column;

                & > div:first-child {
                  padding: 9.5px 0;
                  flex-direction: row;
                }

                & > div:last-child {
                  padding: 9.5px 0;
                }
              }
            }
          }
        }
      }
    }
  }
}
</style>
