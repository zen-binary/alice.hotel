<template>
 <div class="swiper_custom" v-if="data.length > 0">
 <div v-if="containerClass == 'large'" class="custom__prev" :class="{ disable: activePage === 1 }" @click="handleSwiperPrev"></div>
 <div class="custom">
 <swiper :modules="modules" class="mySwiper" @swiper="onSwiper" ref="swiperRef" :navigation="false" :allowTouchMove="false">
 <swiper-slide v-for="i in totalSlide">
 <div class="search_box card col_3">
 <ul>
 <li v-for="i in listItemCard">
 <ItemCard class="class_test">
 <template #image>
 <img :src="i.urlFile" alt="image" @error="handleError" />
 <button v-if="i.approveStatusCd === APPROVE_STATUS_CD_WAITING_APPROVED" class="button btn_white btn_custom" @click="handleOpenConfirm(i.internStdSeq)">
 {{ t("common.cancel") }}
 </button>
 </template>
 <template #status>
 <p
 :data-status="
 i.approveStatusCd === APPROVE_STATUS_CD_WAITING_APPROVED ||
 i.approveStatusCd === APPROVE_STATUS_CD_REJECTED ||
 i.approveStatusCd === APPROVE_STATUS_CD_CANCEL
 ? i.approveStatusNm
 : i.internshipStatusNm
 "
 >
 <span v-if="i.approveStatusCd === APPROVE_STATUS_CD_APPROVED">{{ i.internshipStatusNm }}</span>
 <span
 v-else-if="
 i.approveStatusCd === APPROVE_STATUS_CD_WAITING_APPROVED ||
 i.approveStatusCd === APPROVE_STATUS_CD_REJECTED ||
 i.approveStatusCd === APPROVE_STATUS_CD_CANCEL
 "
 >
 {{ i.approveStatusNm }}
 </span>
 </p>
 </template>
 <template #date>
 <p v-if="i.internshipStr && i.internshipEnd">
 {{ format(new Date(i.internshipStr), FORMAT_YYYY_MM_DD) }} ~ {{ format(new Date(i.internshipEnd), FORMAT_YYYY_MM_DD) }}
 </p>
 </template>
 <template #title>
 <p class="title_title">
 {{ i.internshipName }}
 </p>
 <p class="title_des">{{ i.cormName }} · {{ i.internshipField }} · {{ i.address }}</p>
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

 <TModalConfirm :is-open="modalOpenConfirm" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
 <!-- Modal content goes here -->
 <template #title>
 <strong>Đồng ý thu thập và sử dụng thông tin cá nhân</strong>
 </template>
 <template #default>
 <div class="box_section">
 <p>{{ t("regInternForm.confirmDelete") }}</p>
 </div>
 </template>
 <template #footer>
 <!-- Footer content goes here -->
 <button type="button" class="button btn_md btn_primary" @click="handleClick(id)">{{ t("common.confirm") }}</button>
 <button type="button" class="button btn_md btn_white popup_close" @click="cancelAction1">{{ t("common.cancel") }}</button>
 </template>
 </TModalConfirm>
 <TModalAlert :is-open="modalOpenAlert" :is-show-header="true" :is-show-footer="true" :modal-id="modalId" @close-modal="closeModal">
 <!-- Modal content goes here -->
 <template #title>
 <strong>Đồng ý thu thập và sử dụng thông tin cá nhân</strong>
 </template>
 <template #default>
 <div class="box_section">
 <p>{{ t("Hủy") }}</p>
 </div>
 </template>
 <template #footer>
 <!-- Footer content goes here -->
 <button type="button" class="button btn_md btn_primary" @click="cancelAction2">{{ t("common.confirm") }}</button>
 </template>
 </TModalAlert>
</template>

<script setup lang="ts">
import ItemCard from "@/components/common/ItemCard.vue";
import { Swiper, SwiperSlide } from "swiper/vue";
import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/navigation";
import { Pagination, Navigation } from "swiper/modules";
import { computed, onMounted, ref, onUpdated, watch, defineEmits } from "vue";
import PaginationComponent from "@/components/common/PaginationComponent.vue";
import globalCommon from "@/utils/global";
import { InternStudentListResDTO } from "@/stores/internStudent/internStudent.type";
import { format } from "date-fns";
import { FORMAT_YYYY_MM_DD } from "@/constants/screen.const";
import { internStudentStore } from "@/stores/internStudent";
import { useI18n } from "vue-i18n";
import defaultImage from "@/assets/images/img_card.png";
import { APPROVE_STATUS_CD_CANCEL, APPROVE_STATUS_CD_APPROVED, APPROVE_STATUS_CD_REJECTED, APPROVE_STATUS_CD_WAITING_APPROVED } from "@/constants/common.const";

const props = defineProps({
 data: {
 type: Array as () => InternStudentListResDTO[],
 default: () => [],
 },
 paginationSize: {
 type: Number,
 default: 3,
 },
});

const emit = defineEmits(["changeData"]);

const { t } = useI18n();
const modules = ref([Pagination, Navigation]);
const slider = ref();
const activePage = ref(1);
const showPagesize = ref();
const totalSlide = ref();
const modalOpenConfirm = ref(false);
const modalId = ref("modalId");
const modalOpen = ref(false);
const modalOpenAlert = ref(false);
const internStdStore = internStudentStore();
const selectedInternStdSeq = ref<number | null>(null);

const listItemCard = computed(() => {
 return props.data.slice((activePage.value - 1) * showPagesize.value, activePage.value * showPagesize.value);
});

const handleOpenConfirm = (id: number) => {
 selectedInternStdSeq.value = id;
 modalOpenConfirm.value = true;
};

const handleClick = async () => {
 await internStdStore.fetchUpdateInternStudentStatus({
 method: "POST",
 body: selectedInternStdSeq.value,
 });
 if (internStdStore.status === "200001") {
 modalOpenConfirm.value = false;
 modalOpenAlert.value = true;
 }
};

const cancelAction1 = () => {
 modalOpen.value = false;
 modalOpenConfirm.value = false;
};

const cancelAction2 = () => {
 modalOpenAlert.value = false;
 emit("changeData");
};

const closeModal = () => {
 modalOpen.value = false;
 modalOpenConfirm.value = false;
 modalOpenAlert.value = false;
};

const { windowWidth, containerClass } = globalCommon.useResponsive();

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

.btn_custom {
 height: 28px;
}
</style>
