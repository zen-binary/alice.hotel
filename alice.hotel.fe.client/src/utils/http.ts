import axios from "axios";
import router from "@/router";
import { removeUserInfo } from "@/utils/storage";
import { decode } from "html-entities";
import { SCREEN } from "@/router/screen";
import commonService from "@/service/common/CommonService";

const http = axios.create({
  baseURL: import.meta.env.VITE_API_ENDPOINT,
  headers: {
    "X-Requested-With": "XMLHttpRequest",
    "Content-Type": "application/json",
    Accept: "application/json",
  },
  withCredentials: true,
});

http.interceptors.request.use(
  (config) => config,
  (error) => Promise.reject(error),
);

http.interceptors.response.use(
  (response) => {
    if (response) {
      response.data = decodeHtmlEntities(response.data);
    }
    return response;
  },
  async (error) => {
    const status = error?.response?.status;
    // Only kick to login on true unauthenticated — not on 403/404/500
    if (status === 401) {
      if (router.currentRoute.value.name === SCREEN.login.name) {
        return Promise.reject(error);
      }
      await removeUserInfo();
      await commonService.logout().catch(() => undefined);
      await router.push({ name: SCREEN.login.name });
    }
    return Promise.reject(error);
  },
);

function decodeHtmlEntities(data: any): any {
  if (data instanceof File || data instanceof ArrayBuffer || data instanceof Blob) {
    return data;
  }
  if (typeof data === "string") {
    return decode(data);
  } else if (Array.isArray(data)) {
    return data.map((item) => decodeHtmlEntities(item));
  } else if (data && typeof data === "object") {
    return Object.keys(data).reduce((acc: any, key) => {
      acc[key] = decodeHtmlEntities(data[key]);
      return acc;
    }, {});
  } else {
    return data;
  }
}

export default http;
