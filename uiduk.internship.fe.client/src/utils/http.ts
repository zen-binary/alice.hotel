import axios from "axios";
import router from "@/router";
import { removeUserInfo } from "@/utils/storage";
import { decode } from "html-entities";
import {SCREEN} from "@/router/screen";
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
  (config) => {
    return config;
  },
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
    if (error.response.status === 404) {
      await removeUserInfo();
      await router.push({name:SCREEN.notFound.name});
    } else if (error.response.status === 500) {
      await removeUserInfo();
      await router.push({name:SCREEN.internalError.name});
    } else if (error.response.status === 401 || error.response.status === 403) {
      await removeUserInfo();
      await commonService.logout();
      await router.push({name:SCREEN.login.name});
    }
    return Promise.reject(error);
  },
);

function decodeHtmlEntities(data:any):any {
  if (data instanceof File || data instanceof ArrayBuffer || data instanceof Blob) {
    return data;
  }
  if (typeof data === 'string') {
    return decode(data);
  } else if (Array.isArray(data)) {
    return data.map((item) => decodeHtmlEntities(item));
  } else if (data && typeof data === "object") {
    return Object.keys(data).reduce((acc, key) => {
      acc[key] = decodeHtmlEntities(data[key]);
      return acc;
    }, {});
  } else {
    return data;
  }
}

export default http;
