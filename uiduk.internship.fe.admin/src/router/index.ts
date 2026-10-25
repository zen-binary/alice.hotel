import { createRouter, createWebHistory } from "vue-router";
import { routes } from "@/router/routes";
import { routesAdmin } from "@/router/routesAdmin";
import { routesStaff } from "@/router/routesStaff";
import {getUserInfo} from "@/utils/storage";
import {IS_AGREE, ROLE_CORM} from "@/constants/common.const";
import {SCREEN} from "@/router/screen";

let checkRoutes;

/*// mode when yarn run
if (import.meta.env.MODE === 'development') {
  checkRoutes = [...routesDev]
} else {
  checkRoutes = [...routesProd]
}
if (userRole === 'admin') {
  routes = [...routesDev]
} else if (userRole === 'staff') {
  routes = [...routesProd]
}*/

checkRoutes = routes;

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: checkRoutes,
  scrollBehavior(to, from, savedPosition) {
    return { top: 0, behavior: "smooth" };
  },
});

function nextFactory(context, middleware, index) {
  const subsequentMiddleware = middleware[index];
  if (!subsequentMiddleware) return context.next;

  return (...parameters) => {
    context.next(...parameters);
    const nextMiddleware = nextFactory(context, middleware, index + 1);
    subsequentMiddleware({ ...context, next: nextMiddleware });
  };
}

router.beforeEach((to, from, next) => {
  const metaTitle = to.meta.title as string;
  document.title = metaTitle || "위덕대학교 현장실습";
  const userInfo = JSON.parse(getUserInfo() || "{}");

  if (userInfo?.data?.userDiv === ROLE_CORM && userInfo?.data?.policy !== IS_AGREE) {
    if (to.name !== SCREEN.home.name) {
      return next({ name: SCREEN.home.name });
    }
  }

  if (to.meta.middleware) {
    const middleware = Array.isArray(to.meta.middleware) ? to.meta.middleware : [to.meta.middleware];
    const context = { from, next, router, to };
    const nextMiddleware = nextFactory(context, middleware, 1);
    return middleware[0]({ ...context, next: nextMiddleware });
  }
  return next();
});

export default router;
