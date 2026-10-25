import {SCREEN} from "./screen";
import authMiddleware from "@/middleware/auth";
import logMiddleware from "@/middleware/log";

const getView = (path: string) => {
  return () => import(`@/views/${path}.vue`);
};

export const routes = [
  {
    path: "/",
    component: () => import("@/layouts/DefaultLayout.vue"),
    meta: {
      middleware: [authMiddleware],
    },
    children: [
      {
        path: SCREEN.home.path,
        name: SCREEN.home.name,
        component: () => import("@/views/Main/index.vue"),
      },
      {
        path: SCREEN.template.path,
        name: SCREEN.template.name,
        component: () => import("@/views/TemplateUI.vue"),
      },
      {
        path: SCREEN.main.path,
        name: SCREEN.main.name,
        component: () => import("@/views/Main/MainIndex.vue"),
      },
      {
        path: SCREEN.qaCRUD.path,
        name: SCREEN.qaCRUD.name,
        component: () => import("@/views/QA/QAIndex.vue"),
      },
      {
        path: SCREEN.schoolNoticeCRUD.path,
        name: SCREEN.schoolNoticeCRUD.name,
        meta: {
          middleware: [logMiddleware],
        },
        component: () => import("@/views/SchoolNotice/SchoolNoticeIndex.vue"),
      },
      {
        path: SCREEN.docRoomCRUD.path,
        name: SCREEN.docRoomCRUD.name,
        component: () => import("@/views/DocRoom/DocRoomIndex.vue"),
      },
      {
        path: "/demo",
        name: "/demo",
        component: () => import("@/views/DataFetchDemo.vue"),
      },
      {
        path: SCREEN.professorMain.path,
        name: SCREEN.professorMain.name,
        component: () => import("@/views/Main/ProfessorMain.vue"),
      },
      {
        path: SCREEN.trainingPlan.path,
        name: SCREEN.trainingPlan.name,
        component: () => import("@/views/Training/TrainingPlan.vue"),
      },
      {
        path: SCREEN.trainingPlanStd.path,
        name: SCREEN.trainingPlanStd.name,
        component: () => import("@/views/Training/TrainingPlanStd.vue"),
      },
      {
        path: SCREEN.fieldTraining.path,
        name: SCREEN.fieldTraining.name,
        component: () => import("@/views/FieldTraining/FieldTraining.vue"),
      },
      {
        path: SCREEN.policy.path,
        name: SCREEN.policy.name,
        meta: {
          middleware: [logMiddleware],
        },
        component: () => import("@/views/Login/PolicyManagement.vue"),
      },
    ],
  },
  {
    path: SCREEN.login.path,
    name: SCREEN.login.name,
    meta: {
      middleware: [authMiddleware],
    },
    component: () => import("@/views/Login/LoginView.vue"),
  },
  {
    path: SCREEN.internalError.path,
    name: SCREEN.internalError.name,
    component: getView("InternalError"),
  },
  {
    path: SCREEN.notFound.path,
    name: SCREEN.notFound.name,
    meta: {title: "Not Found"},
    component: getView("NotFound"),
  },
  {
    path: "/:pathMatch(.*)*",
    redirect: SCREEN.notFound.path,
    component: getView("NotFound"),
  },
  {
    path: SCREEN.map.path,
    name: SCREEN.map.name,
    component: () => import("@/views/FooterPolicies/Map.vue"),
  },
  {
    path: SCREEN.terms.path,
    name: SCREEN.terms.name,
    component: () => import("@/views/FooterPolicies/Terms.vue"),
  },
  {
    path: SCREEN.privacyPolicy.path,
    name: SCREEN.privacyPolicy.name,
    component: () => import("@/views/FooterPolicies/PrivacyPolicy.vue"),
  },
];
