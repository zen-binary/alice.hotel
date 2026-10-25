<template>
  <div class="swiper_custom" v-if="data.length > 0">
    <div v-if="containerClass == 'large'" class="custom__prev" :class="{ disable: activePage === 1 }" @click="handleSwiperPrev"></div>
    <div class="custom">
      <swiper
        :modules="modules"
        class="mySwiper"
        @swiper="onSwiper"
        ref="swiperRef"
        :navigation="false"
        :allowTouchMove="false"
        @slideChange="handleSlideChange"
      >
        <swiper-slide v-for="i in totalSlide">
          <div class="search_box card col_3">
            <ul>
              <li v-for="i in listItemCard">
                <ItemCard class="class_test item_card" border-shape="normal" :is-show-icon="true" @click="handleClick(i.internPlanSeq)">
                  <template #status>
                    <p :data-status="i.receptionStatus === INTERN_PLAN_STATUS_ACCEPT ? 11 : 12">
                      {{ getStatusNm(i.receptionStatus) }}
                    </p>
                  </template>
                  <template #title>
                    <p class="title_title">
                      {{ i.internshipName }}
                    </p>
                    <p class="title_des">{{ i.corpName }}·{{ i.quantity }}명·{{ i.internshipField }}·{{ i.address }}</p>
                  </template>
                  <template #icon>
                    <img :src="i.urlFile" alt="icon" @error="handleError" />
                  </template>
                  <template #body>
                    <div class="card_custom_content">
                      <div class="datetime">
                        <div class="reception">
                          <IconCalendar></IconCalendar>
                          <span> {{ t("student.main.meetingCalendar") }} </span>
                        </div>
                        <div class="time" v-if="i.startDate && i.endDate">
                          {{ format(new Date(i.startDate), FORMAT_YYYY_MM_DD) }} ~ {{ format(new Date(i.endDate), FORMAT_YYYY_MM_DD) }}
                        </div>
                      </div>
                    </div>
                  </template>
                </ItemCard>
              </li>
            </ul>
          </div>
        </swiper-slide>
      </swiper>
      <PaginationComponent
        class="swiper_custom__pagination"
        :isBounded="true"
        :total-record="props.data.length"
        :pageSize="showPagesize"
        v-model="activePage"
      />
    </div>
    <div v-if="containerClass == 'large'" :class="{ disable: activePage === totalSlide }" class="custom__next" @click="handleSwiperNext"></div>
  </div>
  <div v-else class="no_cnt">
    <div class="msg">
      {{ t("noDataFoundMessage") }}
    </div>
  </div>
</template>

<script setup lang="ts">
import ItemCard from "@/components/common/ItemCard.vue";
import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/navigation";
import { Pagination, Navigation } from "swiper/modules";
import { computed, onMounted, ref, toRef } from "vue";
import PaginationComponent from "@/components/common/PaginationComponent.vue";
import globalCommon from "@/utils/global";
import { format } from "date-fns";
import { FORMAT_YYYY_MM_DD } from "@/constants/screen.const";
import { InternshipRegResDTO } from "@/stores/internshipPlanRegistration/internshipPlan.type";
import { SCREEN } from "@/router/screen";
import { useRouter } from "vue-router";
import defaultImage from "@/assets/images/img_card_logo.png";
import { useI18n } from "vue-i18n";
import { INTERN_PLAN_STATUS_WAIT, INTERN_PLAN_STATUS_ACCEPT, INTERN_PLAN_STATUS_COMPLETE } from "@/constants/common.const";

const props = defineProps({
  data: {
    type: Array as () => InternshipRegResDTO[],
    default: () => [],
  },
  paginationSize: {
    type: Number,
    default: 3,
  },
});
const router = useRouter();
const modules = ref([Pagination, Navigation]);
const slider = ref();
const activePage = ref(1);
const showPagesize = ref();
const totalSlide = ref();

const { t } = useI18n();

const listItemCard = computed(() => {
  return props.data.slice((activePage.value - 1) * showPagesize.value, activePage.value * showPagesize.value);
});

const { containerClass } = globalCommon.useResponsive();

const handleClick = (id: number) => {
  router.push({
    name: SCREEN.trainingPlanStd.name,
    state: { id },
  });
};

onMounted(() => {
  globalCommon.useResponsive();
  handleCheckSize(containerClass.value);
  totalSlide.value = Math.ceil(props.data.length / showPagesize.value);
});

onUpdated(() => {
  totalSlide.value = Math.ceil(props.data.length / showPagesize.value);
});

watch(
  () => activePage.value,
  (value) => {
    slider.value.slideTo(value - 1);
  },
);
watch(
  () => props.data,
  () => {
    activePage.value = 1;
  },
);
watch(
  () => containerClass.value,
  (value) => {
    handleCheckSize(value);
  },
);

const onSwiper = (swiper) => {
  slider.value = swiper;
};

const handleCheckSize = (value) => {
  switch (value) {
    case "small":
      showPagesize.value = 2;
      break;
    case "medium":
      showPagesize.value = 4;
      break;
    case "large":
      showPagesize.value = 6;
      break;
    default:
      break;
  }
};

const handleSwiperPrev = () => {
  activePage.value--;
  slider.value.slidePrev();
};

const handleSwiperNext = () => {
  activePage.value++;
  slider.value.slideNext();
};

const handleError = (event) => {
  event.target.src = defaultImage;
};

const getStatusNm = (status: any) => {
  switch (status) {
    case INTERN_PLAN_STATUS_WAIT:
      return t("student.main.statusWaitNm");
    case INTERN_PLAN_STATUS_ACCEPT:
      return t("student.main.statusAcceptNm");
    case INTERN_PLAN_STATUS_COMPLETE:
      return t("student.main.statusCompleteNm");
    default:
      return "";
  }
};
</script>

<style scoped lang="scss">
.no_cnt {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 200px;
}

.msg {
  font-size: 25px;
}

.swiper_custom {
  position: relative;
  padding: 0 108px;
}

.custom__prev {
  position: absolute;
  top: 50%;
  left: 0;
  background: url("@/assets/images/icon_vector_swiper.svg") no-repeat center center;
  width: 48px;
  height: 48px;
  cursor: pointer;
}

.custom__next {
  position: absolute;
  top: 50%;
  right: 0;
  background: url("@/assets/images/icon_vector_swiper.svg") no-repeat center center;
  width: 48px;
  height: 48px;
  transform: rotate(180deg);
  cursor: pointer;
}
.custom__next.disable,
.custom__prev.disable {
  pointer-events: none;
  opacity: 0.3;
}

.swiper_custom__pagination {
  margin-top: 32px;
}
@media screen and (max-width: 1024px) {
  .swiper_custom {
    padding: 0 58px;
  }
}

@media screen and (max-width: 768px) {
  .swiper_custom {
    padding: 0 0;
  }
}
.datetime {
  background: var(--gray--f8);
  padding: 16px;
  border-radius: 5px;
  display: flex;
  flex-direction: column;
  gap: 5px;
  .reception {
    display: flex;
    align-items: center;
  }
  .time {
    color: var(--gray--71);
    font-size: 14px;
    font-weight: 500;
  }
}

.item_card {
  cursor: pointer;
}
</style>
