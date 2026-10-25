import { SCREEN } from "./screen";
import authMiddleware from "@/middleware/auth";
import logMiddleware from "@/middleware/log";

const getView = (path: string) => {
  return () => import(`@/views/${path}.vue`);
};

export const routesStaff = [
  {
    path: "/",
    component: () => import("@/layouts/DefaultLayout.vue"),
    meta: {
      title: "Layout",
    },
    children: [
      {
        path: "/templateProd",
        name: "template",
        component: () => import("@/views/TemplateUI.vue"),
      },
    ],
  },
  {
    path: SCREEN.login.path,
    name: SCREEN.login.name,
    meta: {
      title: "Login",
      middleware: [authMiddleware],
    },
    component: getView("LoginView"),
  },
  {
    path: SCREEN.internalError.path,
    name: SCREEN.internalError.name,
    component: getView("InternalError"),
  },
  {
    path: SCREEN.notFound.path,
    name: SCREEN.notFound.name,
    meta: { title: "Not Found" },
    component: getView("NotFound"),
  },
  {
    path: "/:pathMatch(.*)*",
    redirect: SCREEN.notFound.path,
    component: getView("NotFound"),
  },
];
