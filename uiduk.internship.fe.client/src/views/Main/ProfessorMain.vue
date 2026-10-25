<template>
  <BannerComponent
    img-url="/images/banner_professor.webp"
    title="we are"
    :description="[t('professor.main.fieldTraining'), t('professor.main.onlineSystem')]"
    :info="[]"
    class-description="banner_description_login"
  />
  <PageContainer class="page_professor page_swiper">
    <div class="page_professor__group">
      <div class="page_professor__group__title">
        <h1 v-once>{{ pageTitle }}</h1>
        <p class="info">{{ t("professor.main.toolTip") }}</p>
      </div>
      <div class="page_professor__group__search">
        <div class="search_left dp_flex gp_4">
          <p>{{ internMngListModel.length }}</p>
          <p>{{ t("professor.main.title") }}</p>
        </div>
        <div class="search_right">
          <div class="search_right__box">
            <SelectBoxBase :data="yearList" v-model="internMngSearchModel.year" @update:model-value="handleSearch"/>
          </div>
          <div class="search_right__input">          
            <InputBase :placeholder="t('professor.main.placeholderSearch')" class="btn_search" v-model="internMngSearchModel.nameIntern" :isSearch="true" @search="handleSearch"/>
          </div>
        </div>
      </div>
    </div>
    <SwiperInternProf
        v-if="itemCards?.length"
        :data="itemCards"
    >
      <template v-slot:footer="{item}">
        <div class="card_footer">
          <div class="card_footer__item" :class="{active:!(item.isShowReport=='Y')}" @click="handleOpenModalVisitorMap(item)">
            <IconCardBook :color="!(item.isShowReport=='Y') ? 'rgba(190, 190, 190, 1)' : 'rgba(45, 45, 45, 1)'"/>
            <p>{{ t("professor.main.visitorMapReport") }}</p>
          </div>
          <div class="card_footer__item" :class="{active:!(item.isShowReport=='Y')}"
              @click="handleOpenModalInstructorEvaluation(item)">
            <IconCardPencil :color="!(item.isShowReport=='Y') ? 'rgba(190, 190, 190, 1)' : 'rgba(45, 45, 45, 1)'"/>
            <p>{{ t("professor.main.instructorEvaluationForm") }}</p>
          </div>
        </div>
      </template>
    </SwiperInternProf>
    <div v-else class="no_cnt">

    </div>
  </PageContainer>

  <TModalVisitorMap ref="guidanceModal" />
  <TModalInstructorEvaluation ref="evaluationModal" />
</template>

<script setup lang="ts">
import {ref, onBeforeMount } from "vue";
import BannerComponent from "@/components/common/BannerComponent.vue";
import PageContainer from "@/components/common/PageContainer.vue";
import SwiperInternProf from "@/components/internProfSwiper/SwiperInternProf.vue";
import SelectBoxBase from "@/components/common/input/SelectBoxBase.vue";
import InputBase from "@/components/common/input/InputBase.vue";
import IconCardBook from "@/components/icon/IconCardBook.vue";
import IconCardPencil from "@/components/icon/IconCardPencil.vue";
import TModalVisitorMap from "@/components/Modal/TModalVisitorMap.vue";
import TModalInstructorEvaluation from "@/components/Modal/TModalInstructorEvaluation.vue";

import { internProfStore } from "../../stores/internProf";
import type { 
  InternMngListModel, 
  InternMngResModel, 
  InternMngSearchModel
} from "../../stores/internProf/internProf.type";
import { useI18n } from "vue-i18n";

const { t } = useI18n();
const itemCards = ref();
const pageTitle = ref(t("professor.main.pageTitle"));
const guidanceModal = ref(null);
const evaluationModal = ref(null);

const internMngListModel = ref({} as InternMngListModel);
const internMngResModel = ref({} as InternMngResModel);
const internMngSearchModel = ref({} as InternMngSearchModel);
const internStore = internProfStore();

const handleOpenModalVisitorMap = (item) => {
  guidanceModal.value?.openModal(item.internPlanSeq, item.internPlanNm);
};

const handleOpenModalInstructorEvaluation = (item) => {
  evaluationModal.value?.openModal(item.internPlanSeq, item.internPlanNm);

}
const yearList = [{cdId:'', cdNm: t("professor.main.year")}]

onBeforeMount(async () => {        
    await getAllInternList(internMngSearchModel.value);
    internStore.internMngResModel?.data.yearList.forEach(element => {
    yearList.push({
      cdId: element,
      cdNm: element
    })
  });
});

async function getAllInternList(internMngSearchModel: any) {
  await internStore.getAllData({
    method: "POST",
    body: internMngSearchModel,
  });  
  
  internMngResModel.value = internStore.internMngResModel?.data;
  internMngListModel.value = internStore.internMngResModel?.data.internMngList;  
  internMngListModel.value = internStore.internMngResModel?.data.internMngList.map(item => ({
    ...item,
    indexImage: Math.floor(Math.random() * 9)  
  }));
  
  itemCards.value = internMngListModel.value; 
  
}
async function handleSearch() {
  getAllInternList(internMngSearchModel.value)
}

</script>

<style scoped lang="scss">
.page_container.page_professor.page_swiper {
  padding-bottom: 80px;
}

.page_professor {
  &__group {
    //max-width: var(--main-width-vertical);
    margin: auto;
    width: 100%;
    padding: 0 108px;

    &__title {
      display: flex;
      justify-content: space-between;
      align-items: center;
      flex-wrap: wrap;
      gap: 12px;

      .info {
        font-size: 16px;
        font-weight: 400;
        line-height: 24px;
        letter-spacing: -0.02em;
        text-align: left;
        color: var(--Gray-scale-60, rgba(113, 113, 113, 1));
        padding-left: 28px;
        position: relative;

        &:before {
          content: "";
          position: absolute;
          top: 4px;
          left: 0;
          width: 1.6rem;
          height: 1.6rem;
          background-image: url("@/assets/images/icon_info_gray.svg");
          background-repeat: no-repeat;
          background-position: center center;
        }
      }
    }

    &__search {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-top: 24px;
      margin-bottom: 28px;
      gap: 24px;

      p {
        font-size: 14px;
        font-weight: 400;
        line-height: 21px;
        color: var(--Gray-scale-70, rgba(85, 85, 85, 1));
        white-space: nowrap;
      }

      p:first-child {
        color: var(--Primary-50_Primary, rgba(0, 152, 229, 1));
        font-weight: 700;
      }
    }
  }
}

.card_footer {
  display: flex;

  .card_footer__item {
    display: flex;
    flex: 1;
    align-items: center;
    cursor: pointer;
    gap: 8px;

    &.active {
      pointer-events: none;

      & p {
        color: var(--Gray-scale-50, rgba(190, 190, 190, 1));
      }
    }

    & p {
      font-size: 14px;
      font-weight: 500;
      line-height: 21px;
      color: var(--Gray-scale-60, rgba(113, 113, 113, 1));
    }
  }
}

@media screen and (max-width: 1024px) {
  .page_professor {
    &__group {
      padding: 0 58px;
    }
  }
}

@media screen and (max-width: 768px) {
  .page_container.page_professor.page_swiper {
    padding-bottom: 100px;
  }

  .page_professor {
    &__group {
      padding: 0 0;
    }
  }
}

@media screen and (max-width: 768px) {
  .page_professor {
    &__group {
      &__title {
        flex-direction: column;
        align-items: flex-start;
        gap: 12px;

        .info {
          font-size: 14px;
          line-height: 21px;

          &:before {
            top: 2px;
          }
        }
      }

      &__search {
        display: flex;
        justify-content: space-between;
        flex-direction: column-reverse;
        gap: 24px;
        align-items: flex-start;
        margin-top: 24px;
        margin-bottom: 28px;

        p {
          font-size: 14px;
          font-weight: 400;
          line-height: 21px;
          color: var(--Gray-scale-70, rgba(85, 85, 85, 1));
        }

        p:first-child {
          color: var(--Primary-50_Primary, rgba(0, 152, 229, 1));
          font-weight: 700;
        }
      }
    }
  }
}
</style>