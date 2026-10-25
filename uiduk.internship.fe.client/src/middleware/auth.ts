import { getUserInfo } from "@/utils/storage";
import commonService from "@/service/common/CommonService";
import {SCREEN} from "@/router/screen";

export default async function authMiddleware({ to, from, next }) {
  console.log("auth middleware");
  // Call loading on store

  // Check userInfo
  const userInfo = getUserInfo();

  // If userInfo is undefined and the target route is not 'Login', redirect to 'Login'
  if (!userInfo && to.name !== "Login") {
    await commonService.logout();
    return next({ name: SCREEN.login.name });
  }
  // If userInfo is present and the target route is 'Login', redirect to 'Home'
  if (userInfo && to.name === SCREEN.login.name) {
    return next({ name: SCREEN.home.name });
  }

  return next();
}
