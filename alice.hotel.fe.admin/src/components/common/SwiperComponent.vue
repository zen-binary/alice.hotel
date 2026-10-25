<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div class="swiper_custom">
    <div v-if="containerClass == 'large'" class="custom__prev" :class="{ disable: activePage === 1 }" @click="handleSwiperPrev"></div>
    <div class="custom">
      <swiper :modules="modules" class="mySwiper" @swiper="onSwiper" ref="swiperRef" :navigation="false" :allowTouchMove="false">
        <swiper-slide v-for="i in totalSlide">
          <div class="search_box card col_3">
            <ul>
              <li v-for="i in listItemCard">
                <ItemCard class="class_test">
                  <template #image>
                    <img :src="i.image" alt="image" />
                  </template>
                  <template #status>
                    <p :data-status="i.status">
                      {{ i.statusContent }}
                    </p>
                  </template>
                  <template #date>
                    <p v-if="i.startDate && i.endDate">{{ i.startDate }} ~ {{ i.endDate }}</p>
                  </template>
                  <template #title>
                    <p class="title_title">
                      {{ i.title }}
                    </p>
                    <p class="title_des">
                      {{ i.description }}
                    </p>
                  </template>
                  <template #body>
                    <div class="card_custom_content">
                      <slot name="footer" :item="i"></slot>
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
        :total-record="100"
        :pageSize="showPagesize"
        v-model="activePage"
        @change="handleChangePage"
      />
    </div>
    <div v-if="containerClass == 'large'" :class="{ disable: activePage === totalSlide }" class="custom__next" @click="handleSwiperNext"></div>
  </div>
</template>

<script setup lang="ts">
import ItemCard from "@/components/common/ItemCard.vue";
import { Swiper, SwiperSlide } from "swiper/vue";
import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/navigation";
import { Pagination, Navigation } from "swiper/modules";
import { computed, onMounted, ref } from "vue";
import PaginationComponent from "@/components/common/PaginationComponent.vue";
import globalCommon from "@/utils/global";

const props = defineProps({
  data: {
    type: Array as () => [],
    default: () => [],
  },
  paginationSize: {
    type: Number,
    default: 3,
  },
});

const itemCards = toRef(props.data);
const modules = ref([Pagination, Navigation]);
const slider = ref();
const activePage = ref(1);
const showPagesize = ref();
const totalSlide = ref();

const listItemCard = computed(() => {
  return itemCards.value.slice((activePage.value - 1) * showPagesize.value, activePage.value * showPagesize.value);
});

const { windowWidth, containerClass } = globalCommon.useResponsive();

onMounted(() => {
  globalCommon.useResponsive();
  handleCheckSize(containerClass.value);
  totalSlide.value = Math.ceil(props.data.length / showPagesize.value);
});

watch(
  () => activePage.value,
  (value) => {
    slider.value.slideTo(value - 1);
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
</script>

<style scoped lang="scss">
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
</style>
