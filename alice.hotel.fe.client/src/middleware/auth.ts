import { getUserInfo } from "@/utils/storage";
import commonService from "@/service/common/CommonService";
import { SCREEN } from "@/router/screen";

const PUBLIC_NAMES = new Set([
  SCREEN.login.name,
  SCREEN.register.name,
  SCREEN.paymentReturn.name,
  SCREEN.home.name,
  SCREEN.book.name,
]);

export default async function authMiddleware({ to, from, next }) {
  const userInfo = getUserInfo();
  const isPublic = to.meta?.public || PUBLIC_NAMES.has(to.name);

  if (!userInfo && !isPublic) {
    await commonService.logout();
    return next({ name: SCREEN.login.name });
  }
  if (userInfo && to.name === SCREEN.login.name) {
    return next({ name: SCREEN.home.name });
  }

  return next();
}
