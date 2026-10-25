<template>
  <div class="swiper_custom">
    <div v-if="containerClass=='large'" class="custom__prev" :class="{disable:activePage === 1}" @click="handleSwiperPrev"></div>
    <div class="custom">
      <swiper
          :modules="modules"
          class="mySwiper"
          @swiper="onSwiper"
          ref="swiperRef"
          :navigation="false"
          :allowTouchMove="false"
      >
        <swiper-slide v-for="(i,index) in totalSlide" :key="index">
          <div class="search_box card col_3">
            <ul>
              <li v-for="(item,indexCart) in listItemCard" :key="indexCart">
                <ItemCardProf class="class_test">
                  <template #image>
                    <img :src="`/src/assets/images/img_card_${item.indexImage}.png`" alt="image" @error="handleError"/>
                  </template>
                  <template #status>
                    <p :data-status="item.internshipStatus">
                      {{ item.internshipStatus }}
                    </p>
                  </template>
                  <template #date>
                   <p>{{ item.internshipStr }} ~ {{ item.internshipEnd }}</p>
                  </template>
                  <template #title>
                    <p class="title_title" @click="handleOpenDetail(item)">
                      {{ item.internshipField ? item.internPlanNm + ' (' + item.internshipField + ') ': item.internPlanNm }}
                    </p>
                    <p class="title_des">
                      <span v-if="item.cormNameKr">{{ item.cormNameKr }} </span>
                      <span v-if="item.cormNameKr && item.internshipField">&#160; • &#160; </span>
                      <span v-if="item.internshipField"> {{ item.internshipField }} </span>
                      <span v-if="(item.cormNameKr || item.internshipField) && item.address">&#160; • &#160; </span>
                      <span v-if="item.address"> {{ item.address }}</span>
                    </p>
                  </template>
                  <template #body>
                    <div class="card_custom_content">
                      <slot name="footer" :item="item"></slot>
                    </div>
                  </template>
                </ItemCardProf>
              </li>
            </ul>
          </div>
        </swiper-slide>
      </swiper>
      <PaginationComponent class="swiper_custom__pagination" :isBounded="true" :total-record="itemCards.length" :pageSize="showPagesize" v-model="activePage" @change="handleChangePage"/>
    </div>
    <div v-if="containerClass=='large'" :class="{disable:activePage===totalSlide}" class="custom__next" @click="handleSwiperNext"></div>
  </div>
</template>

<script setup lang="ts">

import ItemCardProf from "@/components/internProfSwiper/ItemCardProf.vue";
import {Swiper, SwiperSlide} from 'swiper/vue';
import 'swiper/css';
import 'swiper/css/pagination';
import 'swiper/css/navigation';
import {Pagination, Navigation} from 'swiper/modules';
import {computed, onMounted, ref,toRef} from "vue";
import PaginationComponent from "@/components/common/PaginationComponent.vue";
import globalCommon from "@/utils/global";
import { SCREEN } from "@/router/screen";
import { MODE_DETAIL } from "@/constants/common.const";
import defaultImage from "@/assets/images/img_card.png";

const router = useRouter();

const props = defineProps({
  data: {
    type: Array as () => [],
    default: () => [],
  },
  paginationSize: {
    type: Number,
    default: 3
  }
})

const itemCards = toRef(props.data);
const modules = ref([Pagination, Navigation])
const slider = ref();
const activePage = ref(1)
const showPagesize = ref()
const totalSlide = ref()

const listItemCard = computed(() => {
  return itemCards.value.slice((activePage.value - 1) * showPagesize.value, activePage.value * showPagesize.value)    
})

const {windowWidth, containerClass} = globalCommon.useResponsive();

onMounted(() => {
  globalCommon.useResponsive();
  handleCheckSize(containerClass.value)
  totalSlide.value = Math.ceil(props.data.length / showPagesize.value)
})

onUpdated(()=>{
  totalSlide.value = Math.ceil(props.data.length / showPagesize.value)
})

watch(() => activePage.value, (value) => {
  slider.value.slideTo(value - 1);
})

watch(() => containerClass.value, (value) => {
  handleCheckSize(value)
})

watch(() => props.data, (value) => {
  itemCards.value = value
})

const onSwiper = (swiper) => {
  slider.value = swiper;
}

const handleCheckSize = (value) => {  
  switch (value) {
    case "small":
      showPagesize.value = 2
      break;
    case "medium":
      showPagesize.value = 4
      break;
    case "large":
      showPagesize.value = 6
      break;
    default:
      break;
  }  
}

const handleSwiperPrev = () => {
  activePage.value--
  slider.value.slidePrev();
}

const handleSwiperNext = () => {
  activePage.value++
  slider.value.slideNext()
}
const handleOpenDetail = (data: any) => {
  router.push({
    name: SCREEN.trainingPlan.name,
    params: { mode: MODE_DETAIL },
    state: { internPlanSeq: data.internPlanSeq },
  });
}

const handleError = (event) => {
  event.target.src = defaultImage;
}
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
.custom__prev.disable{
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
.title_title {
  cursor: pointer;
}
</style>