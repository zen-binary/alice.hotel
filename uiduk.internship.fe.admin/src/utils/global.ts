// globalMethods.js
import { i18n } from "../plugins/i18n.ts";
import { onMounted, onUnmounted } from "vue";

export default {
  debounce(func, delay = 50) {
    let timeout;
    return function (...args) {
      if (timeout) {
        clearTimeout(timeout);
      }
      timeout = setTimeout(() => {
        func.apply(this, args);
      }, delay);
    };
  },
  capitalize(value: string) {
    return value.toUpperCase();
  },
  lowercase(value: string) {
    return value.toLowerCase();
  },
  formatCurrency(value: any) {
    return new Intl.NumberFormat().format(value);
  },
  useResponsive() {
    const windowWidth = ref(window.innerWidth);

    const updateWindowWidth = () => {
      windowWidth.value = window.innerWidth;
    };

    // Watch for changes in the window width
    const containerClass = computed(() => {
      if (windowWidth.value < 400) {
        return "small";
      } else if (windowWidth.value < 768) {
        return "medium";
      } else {
        return "large";
      }
    });

    onMounted(() => {
      window.addEventListener("resize", updateWindowWidth);
    });

    onUnmounted(() => {
      window.removeEventListener("resize", updateWindowWidth);
    });

    return {
      windowWidth,
      containerClass,
    };
  },
  setLocale(locale: "vi" | "ko" | "en") {
    i18n.global.locale.value = locale;
  },
  useScroll(offset = document.body.scrollHeight) {
    const isScroll = ref(false);
    const heightTop = ref(0)

    const handleScrolling = (event) => {
      heightTop.value = window.pageYOffset || document.documentElement.scrollTop;
      isScroll.value = heightTop.value >= offset;
    };

    onMounted(() => {
      window.addEventListener("scroll", handleScrolling);
    });

    onUnmounted(() => {
      window.removeEventListener("scroll", handleScrolling);
    });

    return {
      isScroll,
      heightTop
    };
  },
  scrollToTop(){
    const element = document.getElementById('page_wrapper');
    element.scrollTo({
      top: 0,
      behavior: 'smooth'
    });
  }
  //other
};