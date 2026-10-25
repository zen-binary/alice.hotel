<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <Transition
    enter-active-class="transition ease-out duration-200 transform"
    enter-from-class="opacity-0"
    enter-to-class="opacity-100"
    leave-active-class="transition ease-in duration-200 transform"
    leave-from-class="opacity-100"
    leave-to-class="opacity-0"
  >
    <div v-show="isOpen" class="overlay_popup" :class="classSize">
      <div class="overlay_bg" :class="isOpen ? 'on' : ''" @click="closeModal" />
      <Transition
        enter-active-class="transition ease-out duration-200 transform"
        enter-from-class="opacity-0"
        enter-to-class="opacity-100"
        leave-active-class="transition ease-in duration-200 transform"
        leave-from-class="opacity-100"
        leave-to-class="opacity-0"
      >
        <div v-show="isOpen" ref="modalContentRef" class="popup_container popup_container_confirm" :style="isQr ? 'max-width:300px;' : ''">
          <div class="popup_content" :class="{ footer_hidden: !isShowFooter }">
            <slot :modal-id="modalId" />
            <!-- 본문영역 slot 입력 X -->
          </div>

          <div class="popup_footer" :class="{ footer_hidden: !isShowFooter }">
            <slot name="footer" />
            <!-- template v-slot:content -->
          </div>
        </div>
      </Transition>
    </div>
  </Transition>
</template>

<script lang="ts">
export default {
  props: {
    isOpen: Boolean,
    isShowHeader: {
      type: Boolean,
      default: false,
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
      default: "small",
    },
  },
  emits: ["close-modal"],
  methods: {
    closeModal() {
      this.$emit("close-modal");
    },
  },
};
</script>

<style lang="scss" scoped>
.overlay_popup.mini > .popup_container {
  width: 450px !important;
}
.overlay_popup.small > .popup_container {
  width: 450px !important;
}
.overlay_popup.medium > .popup_container {
  width: 740px !important;
}
.overlay_popup.medium-large > .popup_container {
  width: 1000px !important;
}
.overlay_popup.large > .popup_container {
  width: 1164px !important;
}
.overlay_popup.xlarge > .popup_container {
  width: 1278px !important;
}

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

//
//html:has(.overlay_bg.on) .header_wrapper,
//html:has(.overlay_bg.on) .footer_wrapper,
//html:has(.overlay_bg.on) #main_content {
//  padding-right: 10px;
//}
.popup_content p{
  font-size: 20px;
  line-height: 1.5em;
  color: #333;
}
.popup_content {
  border-top-right-radius: 8px;
  border-top-left-radius: 8px;
}
</style>
