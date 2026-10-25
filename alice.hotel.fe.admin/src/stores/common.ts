import { ref } from "vue";
import { defineStore } from "pinia";

export const commonStore = defineStore("common", () => {
  const loading = ref<boolean>(false);
  const check = ref<boolean>(true);
  const arrRequired = ref<string[]>([]);
  const isShowMenu = ref(false);
  const isShowBackground = ref(false);
  const isCheckRole = ref(false)

  const setLoading = (isLoading: boolean) => {
    loading.value = isLoading;
  };
  const setRequired = async (id: unknown) => {
    arrRequired.value.push(id);
    await checkRequired();
  };
  const removeRequired = async (id: unknown) => {
    arrRequired.value = arrRequired.value.filter((vId: string) => vId !== id);
    await checkRequired();
    return arrRequired.value;
  };

  const funcCheck = () => {
    let arr = [];

    arrRequired.value.forEach((id) => {
      const element = document.getElementById(id);
      if (element) {
        arr = [
          ...arr,
          {
            id: id,
            count: element.value ? element.value.length : 0,
            element: element,
          },
        ];
      }
    });

    return arr;
  };
  const checkRequired = () => {
    const arr = funcCheck();

    check.value = arr.some((item) => item.count === 0);
    // if(check.value){
    //   Swal.fire({
    //     title: "Are you sure?",
    //     text: "you want to Cancel Operation!",
    //     showCancelButton: false,
    //     showConfirmButton:false,
    //     icon: "error",
    //   })
    // }
  };

  const scrollToErrorRequired = () => {
    if (!check.value) return;
    const arr = funcCheck();

    const firstEmptyElement = arr.find((item) => item.count === 0);
    if (firstEmptyElement) {
      arr.map((item) => {
        if (item.count === 0) {
          const element = document.getElementById(item.id);
          element.classList.add("error_validate");
        }
      });
      firstEmptyElement.element.scrollIntoView({ behavior: "smooth", block: "center" });
    }
  };

  const setShowMenu = (isShow: boolean) => {
    isShowMenu.value = isShow;
  };

  const setShowBackground = (isShow: boolean) => {
    isShowBackground.value = isShow;
  };

  const setCheckRole = (isCheck: boolean) => {
    isCheckRole.value = isCheck;
  }
  return {
    loading,
    setLoading,
    check,
    checkRequired,
    setRequired,
    removeRequired,
    arrRequired,
    scrollToErrorRequired,
    setShowMenu,
    isShowMenu,
    setShowBackground,
    isShowBackground,
    isCheckRole,
    setCheckRole
  };
});
