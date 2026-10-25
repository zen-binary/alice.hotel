<!--
 @file

 Description: 
 Date: 2024-10-28
 @header-script
-->

<template>
  <div class="accordion" :class="{ acd_active: show }" @keyup="handleKeydown">
    <p v-if="mode === MODE_SHOW()" class="acd_tit" @click="handleClick">
      {{ title }}
    </p>
    <p v-else class="acd_tit" :class="isCheck ? 'on' : 'off'" @click="handleClick">
      {{ title }}
    </p>
    <div class="transition_group" :style="{ height: show ? heightSlot : '0px' }">
      <div ref="transitionRef" class="transition_card">
        <slot />
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { onUpdated, watch, ref, nextTick, onMounted, onBeforeUnmount } from "vue";

export default {
  props: {
    isShow: {
      type: Boolean,
      default: false,
    },
    onClick: {
      type: Function,
      default: () => {},
    },
    title: {
      type: String,
      default: "",
    },
    isCheck: {
      type: Boolean,
      default: false,
    },
    mode: {
      type: String,
      default: "show",
    },
  },
  setup(props) {
    const transitionRef = ref<HTMLElement | null>(null);
    const heightSlot = ref("");
    const show = ref(props.isShow);
    const observer = ref(null);

    onMounted(() => {
      observer.value = new MutationObserver(update);

      observer.value.observe(transitionRef.value, {
        childList: true,
        subtree: true,
      });
    });

    onBeforeUnmount(() => {
      if (observer.value) {
        observer.value.disconnect();
      }
    });

    onUpdated(async () => {
      setTimeout(async () => {
        await nextTick();
        update();
      }, 100);
    });

    watch(
      () => props.isShow,
      (newValue) => {
        show.value = newValue;
      },
    );

    const update = () => {
      heightSlot.value = `${transitionRef.value?.offsetHeight}px`;
    };

    const handleKeydown = (event: KeyboardEvent) => {
      if (event.key === "Tab") {
        show.value = true;

        nextTick(() => {
          update();
        });
      }
    };

    const handleClick = () => {
      show.value = !show.value;
    };
    return {
      transitionRef,
      heightSlot,
      handleKeydown,
      show,
      handleClick,
    };
  },
  methods: {
    MODE_SHOW() {
      return "show";
    },
  },
};
</script>

<style>
.transition_group {
  transition: max-height 0.7s ease-in-out;
  overflow: hidden;
}

.transition_card {
  padding: 1px 32px 40px;
  background-color: white;
}

.acd_tit.on {
  background-color: #6ec5ff !important;
}

.acd_tit.off {
  background-color: #b9b9b9 !important;
}

.accordion {
  margin-bottom: 32px;
  border-radius: 14px;
  box-shadow: var(--shadow);
  overflow: hidden;
}

.acd_tit {
  position: relative;
  padding: 40px 32px 40px 32px;
  font-size: 2.2rem;
  font-weight: 600;
  line-height: 3rem;
  background: var(--white);
  cursor: pointer;
  transition: all 0.2s;
}

.acd_tit::before {
  position: absolute;
  top: 50%;
  right: 32px;
  content: "";
  width: 19px;
  height: 19px;
  background: url(/src/assets/images/icon_accordion.svg) no-repeat;
  transform: translateY(-50%);
}

.acd_cnt {
  max-height: 0;
  overflow: hidden;
  will-change: max-height;
  transition: all 0.25s ease-out;
  opacity: 0;
}

.accordion.acd_active .acd_tit {
  padding: 40px 32px;
}

.accordion.acd_active .acd_cnt {
  opacity: 1;
}

.accordion.acd_active .acd_inner {
  padding: 0 50px 68px;
  background: var(--white);
  border-radius: 0 0 14px 14px;
  box-shadow: var(--shadow);
}

.accordion.acd_active .acd_tit {
  //background: #ffebe3;
  //border-bottom: 1px solid rgba(216, 216, 216, 1);
}

.accordion.acd_active .acd_tit::before {
  transform: translateY(-50%) rotate(180deg);
}
</style>
