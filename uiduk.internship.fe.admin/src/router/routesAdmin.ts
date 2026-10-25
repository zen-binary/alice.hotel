import { SCREEN } from "./screen";
import authMiddleware from "@/middleware/auth";
import logMiddleware from "@/middleware/log";

const getView = (path: string) => {
  return () => import(`@/views/${path}.vue`);
};

export const routesAdmin = [
  {
    path: "/",
    component: () => import("@/layouts/DefaultLayout.vue"),
    meta: {
      title: "Layout",
    },
    children: [
      {
        path: "/templateDev",
        name: "template",
        component: () => import("@/views/TemplateUI.vue"),
      },
    ],
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
