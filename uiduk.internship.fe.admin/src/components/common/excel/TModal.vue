<template>
  <Transition
      enter-active-class="transition ease-out duration-200 transform"
      enter-from-class="opacity-0"
      enter-to-class="opacity-100"
      leave-active-class="transition ease-in duration-200 transform"
      leave-from-class="opacity-100"
      leave-to-class="opacity-0"
  >
    <div v-show="isOpen"  :id="uniqueModalId" class="overlay_popup" :class="classSize">
      <div class="overlay_bg" :class="isOpen ? 'on' : ''" @click="closeModal" />
      <Transition
          enter-active-class="transition ease-out duration-200 transform"
          enter-from-class="opacity-0"
          enter-to-class="opacity-100"
          leave-active-class="transition ease-in duration-200 transform"
          leave-from-class="opacity-100"
          leave-to-class="opacity-0"
      >
        <div v-show="isOpen" ref="modalContentRef" class="popup_container" :style="isQr ? 'max-width:300px;' : ''">
          <div v-if="isShowHeader" class="popup_title">
            <button type="button" class="popup_close" @click="closeModal">닫기</button>
            <h3>
              <!-- template v-slot:title (Start) -->
              <slot name="title" />
              <!-- template v-slot:title (End) -->
            </h3>
          </div>

          <div class="popup_content" :class="{footer_hidden:!isShowFooter}">
            <slot :modal-id="modalId" />
            <!-- 본문영역 slot 입력 X -->
          </div>

          <div class="popup_footer" :class="{footer_hidden:!isShowFooter}">
            <slot name="footer" />
            <!-- template v-slot:content -->
          </div>
        </div>
      </Transition>
    </div>
  </Transition>
</template>

<script lang="ts">
import { nextTick } from "vue";
export default {
  props: {
    isOpen: Boolean,
    isShowHeader: {
      type: Boolean,
      default: true,
    },
    isShowFooter: {
      type: Boolean,
      default: true,
    },
    modalId: {
      type: String,
      default: "",
    },
    isQr: {
      type: Boolean,
      default: false,
    },
    classSize: {
      type: String,
      default: "medium",
    },
    isScrollTop: {
      type: Boolean,
      default: true,
    },
  },
  
  emits: ["close-modal"],
  data() {
    return {
      uniqueModalId: `modal-${Math.random().toString(36).substr(2, 9)}`,
    };
  },
  watch: {
    isOpen(newValue) {
      if (newValue) {
        nextTick(() => {
          const modalContainer = document.querySelector(`#${this.uniqueModalId} .popup_container .popup_content`);
          if (modalContainer && this.isScrollTop === true) {
            modalContainer.scrollTop = 0;
            // Optional: Smooth scroll
            // modalContainer.scrollTo({ top: 0, behavior: 'smooth' });
          }
        });
      }
    },
  },
  methods: {
    closeModal() {
      this.$emit("close-modal");
    },
  },
};
</script>

<style lang="scss">
.slide-fade-enter-active,
.slide-fade-leave-active {
  transition:
      opacity 0.5s,
      transform 0.5s;
}

.slide-fade-enter,
.slide-fade-leave-to {
  opacity: 0;
  transform: translateY(-20px);
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.bounce-enter-active {
  animation: bounce-in 0.5s;
}

.bounce-leave-active {
  animation: bounce-in 0.5s reverse;
}

@keyframes bounce-in {
  0% {
    transform: scale(0);
  }
  50% {
    transform: scale(1.25);
  }
  100% {
    transform: scale(1);
  }
}

html:has(.list_select_custom.on) .popup_content.isDesktop,
html:has(.select_component_custom.on) .popup_content.isDesktop,
html:has(.datepicker-wrapper) .popup_content.isDesktop {
  overflow: hidden;
  padding-right: 37px;

  @media screen and (max-width: 768px) {
    padding-right: 25px;
  }
}

html:has(.list_select_custom.on) .popup_content,
html:has(.datepicker-wrapper) .popup_content{
  overflow: hidden;
  padding-right: 37px;
  pointer-events: none;
}
</style>