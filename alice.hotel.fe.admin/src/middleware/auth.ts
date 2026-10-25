import {getUserInfo, removeUserInfo} from "@/utils/storage";
import commonService from "@/service/common/CommonService";
import {SCREEN} from "@/router/screen";

export default async function authMiddleware({ to, from, next }) {
  console.log("auth middleware");
  // SSO token login (Redis) temporarily disabled — use form login on this site

  const userInfo = getUserInfo();

  // Not logged in → stay on Login, otherwise go to admin login page
  if (!userInfo && to.name !== SCREEN.login.name) {
    await removeUserInfo();
    return next({ name: SCREEN.login.name });
  }

  // Already logged in → leave Login
  if (userInfo && to.name === SCREEN.login.name) {
    return next({ name: SCREEN.home.name });
  }

  return next();
}
