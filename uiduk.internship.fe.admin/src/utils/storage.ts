import { USER_INFO } from "@/constants/common.const";

export const setUserInfo = (data: object) => {
  const userInfo = JSON.stringify(data);
  localStorage.setItem(USER_INFO, userInfo);
};

export const getUserInfo = () => {
  return localStorage.getItem(USER_INFO);
};

export const removeUserInfo = () => {
  const keysToRemove = [];

  for (let i = 0; i < localStorage.length; i++) {
    const key = localStorage.key(i);
    keysToRemove.push(key);
  }

  keysToRemove.forEach((key) => {
    localStorage.removeItem(key);
  });
};

export const setLocalStorage = (key: string, data: object) => {
  const information = JSON.stringify(data);
  localStorage.setItem(key, information);
};

export const getLocalStorage = (key) => {
  return localStorage.getItem(key);
};

export const setSessionStorage = (key: string, data: object) => {
  const information = JSON.stringify(data);
  sessionStorage.setItem(key, information);
};

export const getSessionStorage = (key) => {
  return sessionStorage.getItem(key);
};
