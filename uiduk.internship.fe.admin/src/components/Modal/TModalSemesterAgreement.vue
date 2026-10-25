<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <TModal
    :is-open="modelValue"
    :is-show-header="true"
    :is-show-footer="false"
    :modal-id="modalId"
    class-size="medium-large"
    @close-modal="closeModal"
  >
    <!-- Modal content goes here -->
    <template #title>
      <strong>현장실습 종합보고서</strong>
    </template>
    <template #default>
      <div class="semester_agreement" id="semester_agreement">
        <div class="semester_agreement__title">
          <h2>현장실습명이 나오는 부분입니다.</h2>
          <div class="semester_agreement__title__right">
            <SelectBoxBase data="" />
            <!--            <button class="button btn_white btn_md">PDF 다운로드</button>-->
            <ButtonExportPDFV1 id="semester_agreement" name-export="semester_agreement" />
          </div>
        </div>
        <div class="semester_agreement__content is_scroll">
          <div class="content_header">
            <span>
              본 협약은 「대학생 현장실습학기제 운영규정」(이하 ʻ교육부 고시’라 한다)에 의거하여 대학교(이하 ʻ학교’라 한다)에서 운영하는 표준
              현장실습학기제(Co-op) 교육과정의 운영근거를 마련하고, 표준 현장실습학기제(Co-op)를 공동으로 운영하는 실습기관, 참여하는 학생, 운영
              주체인 학교 간 필요한 사항을 정하고 이를 성실하게 준수할 것을 약정함을 목적으로 체결한다.
            </span>
            <div>
              <span class="text_primary text_bold">제 1조(교육과정) 2024학년 1학기</span>
              &nbsp;
              <span class="text_bold">표준 현장실습학기제(Co-op)과정으로 운영한다.</span>
            </div>
            <p class="text_bold">제 2조(운영사항) 표준 현장실습학기제(Co-op) 운영사항은 아래 각 호와 같이 실시한다.</p>
          </div>
          <div class="content_body">
            <ul>
              <li>
                <span class="text_primary">1.</span>
                <span class="text_primary">실습기관명 : 상상 디자인</span>
              </li>
              <li>
                <span>2.</span>
                <div>
                  <span>
                    표준 현장실습학기제(Co-op) 운영 내용 및 지도 계획등은 실습기관에서 학교에 기 제출한 교육부 고시 별지 제 1호 서식(표준 현장실습
                    학기제(Co-op)운영 계획서)에 따라 운영한다. 다만, 내용 등의 변경이 필요한 경우에는 학교 측에 통보하고 학교 및 학생의 동의하에
                    변경할 수 있다.
                  </span>
                </div>
              </li>
              <li>
                <span class="text_primary">3.</span>
                <div>
                  <span class="text_primary">실습기간은 2024년 9월 30일부터 2024년 10월 11일까지로 한다.</span>
                  &nbsp;
                  <span>다만, 학생별 실제 실습기간은 실습상황에 따라 변경될 수 있으며, 이 경우 상호 협의하여 확정한다.</span>
                </div>
              </li>
              <li>
                <span>4.</span>
                <div class="content_body__end">
                  <span>실습시간 및 요일</span>
                  <span class="text_primary">가. 실습시간 : 1일 8시간/09:00~18:00 (휴게시간 1시간 포함)</span>
                  <span class="text_primary">나. 실습일수 및 요일 : 주 5일(월,화,수,목,금)</span>
                </div>
              </li>
            </ul>
          </div>
          <div class="content_footer">
            <p>제 3조 내용이 나오는 영역입니다.</p>
            <p>제 4조 내용이 나오는 영역입니다.</p>
            <p>제 5조 내용이 나오는 영역입니다.</p>
            <p>본 협약의 성립을 증명하기 위해 각각 서명 날인 후 상호 협의한 방법으로 1부씩 보관한다.</p>
            <p>2024년 10월 1일</p>
          </div>
        </div>
        <div class="semester_agreement__content">
          <div class="group_file__content">
            <div class="group_file__content__title">
              <p>학교</p>
            </div>
            <div class="group_file__content__file">
              <div>
                <p>위덕대학교</p>
                <p>현장실습센터장 홍길동</p>
              </div>
              <div>
                <InputFileBase :mode="`create`" :id="`semester_agreement_1`" :type="'img'" :preview-flag="true" :show-mess="false" />
              </div>
            </div>
          </div>
          <div class="group_file__content">
            <div class="group_file__content__title">
              <p>실습기관</p>
            </div>
            <div class="group_file__content__file">
              <div>
                <p>실습기관명</p>
                <p>실습기관담당자 홍길동</p>
              </div>
              <div>
                <InputFileBase :mode="`detail`" :id="`semester_agreement_2`" :type="'img'" :preview-flag="true" :show-mess="false" />
              </div>
            </div>
          </div>
          <div class="group_file__content">
            <div class="group_file__content__title">
              <p>학생</p>
            </div>
            <div class="group_file__content__file">
              <div>
                <p>위덕대학교</p>
                <p>디자인학과 홍길동</p>
              </div>
              <div>
                <InputFileBase :mode="`create`" :id="`semester_agreement_3`" :type="'img'" :preview-flag="true" :show-mess="false" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </template>
  </TModal>
</template>

<script setup lang="ts">
import TModal from "@/components/common/excel/TModal.vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import InputFileBase from "@/components/common/input/InputFileBase.vue";
import ButtonExporPDF from "@/components/common/ButtonExporPDF.vue";
import ButtonExportPDFV1 from "@/components/common/ButtonExportPDFV1.vue";

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
.semester_agreement {
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
    &:nth-child(2) {
      margin-top: 20px;
      border: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));
      padding: 16px;
      border-radius: 8px;

      & p,
      & span {
        font-size: 16px;
        font-weight: 400;
        line-height: 24px;
      }

      .content_header {
        display: flex;
        flex-direction: column;
        gap: 24px;
      }

      .content_body {
        margin-top: 12px;

        & ul {
          display: flex;
          flex-direction: column;
          gap: 4px;

          li {
            display: flex;

            & > span:first-child {
              display: block;
              width: 20px;
            }

            & > div {
              width: calc(100% - 20px);
            }

            .content_body__end {
              display: flex;
              flex-direction: column;
              gap: 8px;
            }
          }
        }
      }

      .content_footer {
        margin-top: 12px;
        display: flex;
        flex-direction: column;
        gap: 24px;

        & p {
          font-size: 16px;
          font-weight: 500;
          line-height: 24px;
          color: var(--Gray-scale-90, rgba(29, 29, 29, 1));

          &:last-child {
            color: var(--Gray-scale-70, rgba(85, 85, 85, 1));
          }

          &:nth-last-child(2){
            font-size: 18px;
            font-weight: 500;
            line-height: 27px;
            letter-spacing: -0.01em;
          }
        }
      }
    }

    &:nth-child(3) {
      display: flex;
      gap: 8px;
      margin-top: 24px;
      flex-wrap: wrap;

      .group_file__content {
        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
        gap: 0;
        flex: 1;

        &__title {
          padding: 17.5px;
          width: 100%;
          text-align: center;
          border-top: 1px solid var(--Gray-scale-80, rgba(45, 45, 45, 1));
          background: var(--Gray-scale-5, rgba(248, 248, 248, 1));
          border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));
          p {
            font-size: 14px;
            font-weight: 500;
            line-height: 21px;
          }
        }

        &__file {
          display: flex;
          justify-content: space-between;
          width: 100%;
          padding: 16px;
          border-bottom: 1px solid var(--Gray-scale-20, rgba(228, 228, 228, 1));
          gap: 8px;
          flex: 1;

          & > div:first-child {
            display: flex;
            flex-direction: column;
            justify-content: space-evenly;
            p {
              &:first-child {
                font-size: 14px;
                font-weight: 400;
                line-height: 21px;
                color: var(--Gray-scale-70, rgba(85, 85, 85, 1));
              }
              &:last-child {
                font-size: 16px;
                font-weight: 500;
                line-height: 24px;
                color: var(--Gray-scale-80, rgba(45, 45, 45, 1));
              }
            }
          }
        }
      }
    }
  }

  .text_primary {
    color: var(--primary);
  }

  .text_bold {
    font-weight: 500;
  }
}

@media screen and (max-width: 768px) {
  .semester_agreement {
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
      &:nth-child(2) {
        max-height: 480px;
        overflow: auto;
      }
      &:nth-child(3) {
        flex-direction: column;
      }
    }
  }
}

@media screen and (max-width: 480px) {
  .semester_agreement {
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
      &:nth-child(2) {
        max-height: 240px;
        overflow: auto;
      }
      &:nth-child(3) {
        gap: 12px;
        .group_file__content {
          &__title {
            padding: 9.5px;
          }
        }
      }
    }
  }
}
</style>
