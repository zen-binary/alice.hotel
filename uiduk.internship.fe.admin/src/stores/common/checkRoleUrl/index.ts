import { defineStore } from "pinia";
import { getRole } from "@/stores/common/checkRoleUrl/checkRole.service";
import { ref } from "vue";

export const checkFlagStore = defineStore(
  "checkFlag",
  () => {
    const status = ref<number>();
    const flag = ref<boolean>(false);
    const acceptWirteCode = ref<String>("a2l5bXhh");
    const checkRole = async (params: String) => {
      status.value = 0;
      const response = await getRole(params);
      const role = response.data.data;
      flag.value = false;
      if (role == acceptWirteCode.value) {
        flag.value = true;
      }
      status.value = response.status;
    };
    return {
      flag,
      status,
      checkRole,
    };
  },
  {
    persist: true,
  },
);
