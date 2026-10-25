import { defineStore } from "pinia";
import { ref } from "vue";
import type { ResourceRoleResponseModel } from "./resourceRole.type";
import { FIND_RESOURCE_ROLE, CREATE_RESOURCE_ROLE, RELOAD_RESOURCE } from "./resourceRole.service";
import { useFetchData } from "@/utils/useFetchData";
import { PostType } from "@/stores/common/demoFetchData/demo.type.ts";
import { RequestOptions } from "@/utils/useFetchData.ts";

export const resourceRoleStore = defineStore("resourceRoleStore", () => {
  const resultResourceRole = ref<ResourceRoleResponseModel[]>([]);
  const status = ref<number>();

  const findByRoleId = async (option: RequestOptions) => {
    const { dataFetch, statusFetch } = await useFetchData<PostType[]>(FIND_RESOURCE_ROLE, option);
    resultResourceRole.value = dataFetch.value;
    status.value = statusFetch;
  };

  const createOrDeleteResourceRole = async (option: RequestOptions) => {
    const { statusFetch } = await useFetchData<PostType[]>(CREATE_RESOURCE_ROLE, option);
    await useFetchData<PostType[]>(RELOAD_RESOURCE, {
      method: "POST",
    });
    status.value = statusFetch;
  };

  return {
    resultResourceRole,
    status,
    findByRoleId,
    createOrDeleteResourceRole,
  };
});
