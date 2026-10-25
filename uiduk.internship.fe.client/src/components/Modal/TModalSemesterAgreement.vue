<template>
  <TModal
      :is-open="modelValue"
      :is-show-header="true"
      :is-show-footer="false"
      :modal-id="modalId"
      class-size="medium-large"
      @close-modal="closeModal">
    <!-- Modal content goes here -->
    <template #title>
      <strong>현장실습 종합보고서</strong>
    </template>
    <template #default>
      <div class="semester_agreement" id="semester_agreement">
        <div class="semester_agreement__title">
          <h2>현장실습명이 나오는 부분입니다.</h2>
          <div class="semester_agreement__title__right">
            <SelectBoxBase data=""/>
<!--            <button class="button btn_white btn_md">PDF 다운로드</button>-->
            <ButtonExportPDFV1 class="btn_lg" id="semester_agreement" name-export="semester_agreement"/>
          </div>
        </div>
        <div class="semester_agreement__content is_scroll">
          <div class="content_header">
            <span>본 협약은 「대학생 현장실습학기제 운영규정」(이하 ʻ교육부 고시’라 한다)에 의거하여 대학교(이하 ʻ학교’라 한다)에서 운영하는 표준 현장실습학기제(Co-op) 교육과정의 운영근거를
              마련하고, 표준 현장실습학기제(Co-op)를 공동으로 운영하는 실습기관, 참여하는 학생, 운영 주체인 학교 간 필요한 사항을 정하고 이를 성실하게 준수할 것을 약정함을 목적으로
              체결한다.</span>
            <div>
              <span class="text_primary text_bold">제 1조(교육과정) 2024학년 1학기</span>&nbsp;<span class="text_bold">표준 현장실습학기제(Co-op)과정으로 운영한다.</span>
            </div>
            <p class="text_bold">제 2조(운영사항) 표준 현장실습학기제(Co-op) 운영사항은 아래 각 호와 같이 실시한다.</p>
          </div>
          <div class="content_body">
            <ul>
              <li>
                <span class="text_primary">1.</span><span class="text_primary">실습기관명 : 상상 디자인</span>
              </li>
              <li>
                <span>2.</span>
                <div>
                  <span>표준 현장실습학기제(Co-op) 운영 내용 및 지도 계획등은 실습기관에서 학교에 기 제출한 교육부 고시 별지 제 1호 서식(표준 현장실습 학기제(Co-op)운영 계획서)에 따라 운영한다. 다만, 내용 등의 변경이 필요한 경우에는 학교 측에 통보하고 학교 및 학생의 동의하에 변경할 수 있다.</span>
                </div>
              </li>
              <li>
                <span class="text_primary">3.</span>
                <div>
                  <span class="text_primary">실습기간은 2024년 9월 30일부터 2024년 10월 11일까지로 한다.</span>&nbsp;<span>다만, 학생별 실제 실습기간은 실습상황에 따라 변경될 수 있으며,
                  이 경우 상호 협의하여 확정한다.</span>
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
              <li>
                <span>5.</span>
                <div class="content_body__end">
                  <span>휴게 및 휴일</span>
                  <span>가. 교육부 고시에 따라 실습기관에서는 1일 1시간 이상의 휴게시간과 1주 1일 이상의 휴일 및 공휴일, 1개월 기준 1일의 휴일, 실습기간 중 학생의 공적 의무 수행일, 경조사일, 입원일 등의 휴일 활용을 보장한다.</span>
                  <span>나. 실습기관에서는 위 휴일 외 실습기관의 사정 및 필요에 따라 별도의 휴일을 부여할 수 있다.</span>
                </div>
              </li>
              <li>
                <span>6.</span>
                <div class="content_body__end">
                  <span>실습지원비 등</span>
                  <p class="dp_flex">가. 실습기관은 교육부 고시에 따른 실습지원비 기준을 준수하여&nbsp;<span class="text_primary">[주] 기준 500,000원(세전)을 [당월] 30일 경에</span>&nbsp;학생별 계좌로 지급한다.</p>
                  <span>다만, 학생이 중도 포기하거나, 결근하는 경우에는 그 실습일수에 비례하여 실습지원비를 지급한다.</span>
                  <span>나. 실습기관에서 연장ㆍ야간 실습을 실시하는 경우 해당 연장ㆍ야간 실습에 대해서는 당해 연도 시간급 최저임금의 100분의 50 이상을 가산하여 지급한다.</span>
                  <span>다. 실습기관에서는 실습지원비 외 해당 실습기관의 편의ㆍ복지 제도의 활용을 지원한다.</span>
                </div>
              </li>
              <li>
                <span>7.</span>
                <div>
                  <span>실습기관은 산업재해 예방을 위해 작업에 적합한 보호구 지급, 해당 기계ㆍ기구ㆍ설비 등에 대한 위험방지 조치 및 원재료ㆍ가스ㆍ분진 등에 디자인 현장실습 (디자인 의한 건강장해 방지조치 등 안전ㆍ보건 상 필요사항을 조치한다.</span>
                </div>
              </li>
              <li>
                <span>8.</span>
                <div>
                  <span>실습기관에서는 「산업재해보상보험법」 제123조에 따른 「현장실습생에 대한 산업재해보상보험 적용범위」(고용노동부 고시)에 의거하여 참여 학생을 대상으로 산재보험에 가입하고, 학교에서는 상해보험에 가입하여 학생이 표준 현장실습학기제(Co-op) 운영과 관련하여 재 입는 경우 해당 보험에 따라 재해보상을 한다.</span>
                </div>
              </li>
              <li>
                <span>9.</span>
                <div>
                  <p class="dp_flex">실습기관에서는 본 협약서 외 학생별 별도의 (근로)계약서를&nbsp;<span class="text_primary">[체결하며], 4대보험 중 산재보험 외 다른 보험 등에</span></p>
                  <p class="dp_flex mt-2"><span class="text_primary">[가입한다].</span>&nbsp;<span> 별도의 (근로)계약서를 체결하더라도 표준 현장실습학기제(Co-op) 운영 및 지도계획은 준수하여야 한다.</span></p>
                </div>
              </li>
            </ul>
          </div>
          <div class="content_footer">
            <p>제3조(협약해지 및 중단 등) 표준 현장실습학기제(Co-op) 운영 중 부득이하거나, 합당한 협약 해지 또는 중단 등의 사유가 발생할 경우 실습기관 또는 학생은 학교와 협의한 후 실습기관, 학생, 학교 간 상호 동의 및 합의하에 본 협약을 해지하거나, 운영을 중단할 수 있다 <br/><span class="text_custom">다만, 실습기관의 귀책사유로 중단되어 학생의 해당 학기 이수에 문제가 발생할 경우에는 학교와 협의하여 해당 실습기관과 동일하거나 유사한 직종의 다른 실습기관을 연계하여 학생의 학기 이수 등에 문제가 발생하지 않도록 노력하여야 한다.</span></p>
            <p>제4조(상호협력 등) 학교 및 실습기관에서는 산업안전보호 및 성희롱예방 등 학생 보호를 위한 조치를 취하고, 실습기관 및 학생, 학교는 교육부
              고시에 따른 상호 권리 및 의무사항을 숙지하여 표준 현장실습학기제(Co-op)가 원활하게 운영될 수 있도록 상호 협력하여 운영한다.</p>
            <p>제5조(방문 지도ㆍ점검) 학교에서는 참여 학생을 대상으로 실습기관의 표준 현장실습학기제(Co-op) 운영 사항의 적합성을 방문ㆍ서면 등으로 점검하고, 실습기관에서는 학교의 교직원이 현장을 방문하여 지도 및 점검 시 적극 협조한다.</p>
            <p>제6조(출석ㆍ평가) 실습기관에서는 교육부 고시의 출석 및 평가 기준에 따라 학생의 출석관리 및 평가를 실시한 후 별지 제3호 서식(표준 현장실습학기제(Co-op) 평가표 및 출석부)을 학교에 제출한다.</p>
            <p>제7조(기타) 본 협약은 각 조항에 따른 사항이 모두 완료되는 시점에 자동으로 종료되며, 본 협약서에 명기되지 아니한 사항은 교육부 고시 기준에 따른다.</p>
            <p>본 협약의 성립을 증명하기 위해 각각 서명 날인 후 상호 협의한 방법으로 1부씩 보관한다.</p>
            <p class="">2024년 10월 1일</p>
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
                <InputFileBase
                    :mode="`create`"
                    :id="`semester_agreement_1`"
                    :type="'img'"
                    :preview-flag="true"
                    :show-mess="false"
                />
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
                <InputFileBase
                    :mode="`detail`"
                    :id="`semester_agreement_2`"
                    :type="'img'"
                    :preview-flag="true"
                    :show-mess="false"
                />
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
                <InputFileBase
                    :mode="`create`"
                    :id="`semester_agreement_3`"
                    :type="'img'"
                    :preview-flag="true"
                    :show-mess="false"
                />
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
  }
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
        flex:1;

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
              &:first-child{
                font-size: 14px;
                font-weight: 400;
                line-height: 21px;
                color: var(--Gray-scale-70, rgba(85, 85, 85, 1));
              }
              &:last-child{
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
    color: var(--primary) !important;
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
        gap:12px;
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