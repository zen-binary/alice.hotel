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
      <strong>산업체 현장실습 방문지도 보고서</strong>
    </template>
    <template #default>
      <div class="visitor_map">
        <div class="visitor_map__title">
          <h2>현장실습명이 나오는 부분입니다.</h2>
          <div class="visitor_map__title__right">
            <SelectBoxBase data="" />
            <button class="button btn_white btn_md">PDF 다운로드</button>
          </div>
        </div>
        <div class="visitor_map__content">
          <h3>평가대상 학생정보</h3>
          <div class="visitor_map__content__table">
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
                <div><p>방문일자</p></div>
                <div>
                  <BaseDatePicker model-value="" />
                </div>
              </div>
            </div>
            <div class="table_row">
              <div class="table_col">
                <div><p>실습생 근무부서</p></div>
                <div>
                  <InputBase model-value="" />
                </div>
              </div>
              <div class="table_col">
                <div><p>현장실습 담당자(면담자)</p></div>
                <div>
                  <InputBase model-value="" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="visitor_map__content">
          <h3>현장실습 실태 : 실습기관 환경,실습내용의 적합성 등</h3>
          <TextArea model-value="" placeholder="내용을 입력해 주세요. (최대 300자 제한)" />
        </div>
        <div class="visitor_map__content">
          <h3>지도사항 : 현장실습 시 실습생에게 필요한 사항,실습생과 면담 내용 등</h3>
          <TextArea model-value="" placeholder="내용을 입력해 주세요. (최대 300자 제한)" />
        </div>
        <div class="visitor_map__content">
          <h3>실습기관 의견 : 실습생 장·단점,실습생 업무처리 능력, 건의사항 등</h3>
          <TextArea model-value="" placeholder="내용을 입력해 주세요. (최대 300자 제한)" />
        </div>
        <div class="visitor_map__content">
          <h3>방문지도 결과 및 의견 : 지도교수로서 현장실습 평가 소견</h3>
          <TextArea model-value="" placeholder="내용을 입력해 주세요. (최대 300자 제한)" />
        </div>
        <div class="visitor_map__content">
          <p>위와 같이 산업체 현장실습 방문지도 보고서를 제출합니다.</p>
          <div class="group_file__content">
            <p>2024년 10월 1일</p>
            <p>디자인학과</p>
            <p>홍길동 교수</p>
            <InputFileBase :mode="`create`" :id="`visitor_map`" :type="'img'" :preview-flag="true" :show-mess="false" />
          </div>
          <p>위덕대학교 현장실습지원센터장 귀하</p>
        </div>
      </div>
    </template>
    <template #footer>
      <!-- Footer content goes here -->
      <button type="button" class="button btn_xl btn_white popup_close" @click="confirmAction">닫기</button>
      <button type="button" class="button btn_xl btn_primary" @click="cancelAction">확인</button>
    </template>
  </TModal>
</template>

<script setup lang="ts">
import TModal from "@/components/common/excel/TModal.vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import TextArea from "@/components/common/input/TextArea.vue";
import BaseDatePicker from "@/components/common/input/BaseDatePicker.vue";
import InputBase from "@/components/common/input/InputBase.vue";
import InputFileBase from "@/components/common/input/InputFileBase.vue";

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
.visitor_map {
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
          flex: 1;
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
            word-break: break-word;
            padding: 8px 16px;
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
  }
}

@media screen and (max-width: 768px) {
  .visitor_map {
    &__title {
      display: flex;
      align-items: center;
      justify-content: space-between;

      & > h2 {
        font-size: 18px;
        font-weight: 500;
        line-height: 27px;
      }

      &__right {
        display: flex;
        align-items: center;
        gap: 8px;
      }
    }

    &__content {
      .table_row {
        flex-direction: column;
      }
    }
  }
}

@media screen and (max-width: 480px) {
  .visitor_map {
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
        display: flex;
        align-items: center;
        gap: 8px;
        width: 100%;
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
    }
  }
}
</style>
