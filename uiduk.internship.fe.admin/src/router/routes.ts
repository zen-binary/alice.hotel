import { SCREEN } from "./screen";
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
        component: () => import("@/views/dashboard/index.vue"),
      },
      {
        path: SCREEN.template.path,
        name: SCREEN.template.name,
        component: () => import("@/views/TemplateUI.vue"),
      },
      {
        path: SCREEN.demo.path,
        name: SCREEN.demo.name,
        component: () => import("@/views/DataFetchDemo.vue"),
      },
      {
        path: SCREEN.menuSide.path,
        name: SCREEN.menuSide.name,
        meta: {
          title: "Menu Side",
        },
        component: () => import("../views/MenuSide.vue"),
      },
      {
        path: SCREEN.pathMenu.path,
        name: SCREEN.pathMenu.name,
        meta: {
          title: "List Menu",
        },
        component: () => import("../views/menu/List.vue"),
      },
      {
        path: SCREEN.crudPathMenu.path,
        name: SCREEN.crudPathMenu.name,
        meta: {
          title: "Create Menu",
        },
        component: () => import("../views/menu/Crud.vue"),
      },
      {
        path: SCREEN.resourceManagement.path,
        name: SCREEN.resourceManagement.name,
        component: () => import("../views/resourceManagement/List.vue"),
      },
      {
        path: SCREEN.roleResourceManagement.path,
        name: SCREEN.roleResourceManagement.name,
        component: () => import("../views/roleResourceManagement/List.vue"),
      },
      {
        path: SCREEN.indexInternPlan.path,
        name: SCREEN.indexInternPlan.name,
        component: () => import("@/views/indexInternPlan/IndexInternPlan.vue")
      },
      {
        path: SCREEN.operateIntern.path,
        name: SCREEN.operateIntern.name,
        component: () => import("../views/corporate/operateIntern/OperateInternList.vue"),
      },
      {
        path: SCREEN.beforeApproval.path,
        name: SCREEN.beforeApproval.name,
        component: () => import("@/views/BeforeApproval.vue"),
      },
      {
        path: SCREEN.internPlanCenterMng.path,
        name: SCREEN.internPlanCenterMng.name,
        component: () => import("@/views/internPlanCenterMng/InternPlanCenterMng.vue"),
      },
      {
        path: SCREEN.corporateChange.path,
        name: SCREEN.corporateChange.name,
        component: () => import("@/views/corporateChange/CorporateChange.vue"),
      },
      {
        path: SCREEN.qna.path,
        name: SCREEN.qna.name,
        component: () => import("@/views/qna/QnaList.vue"),
      },
      {
        path: SCREEN.qnaCrud.path,
        name: SCREEN.qnaCrud.name,
        meta:{
          parent:SCREEN.qna.name
        },
        component: () => import("@/views/qna/QnaCrud.vue"),
      },
      {
        path: SCREEN.internPlanningMng.path,
        name: SCREEN.internPlanningMng.name,
        component: () => import("../views/internPlanningMng/List.vue"),
      },
      {
        path: SCREEN.internPlanningCrud.path,
        name: SCREEN.internPlanningCrud.name,
        meta:{
          parent:SCREEN.internPlanningMng.name
        },
        component: () => import("../views/internPlanningMng/Crud.vue"),
      },
      {
        path: SCREEN.internPlanCenterDetail.path,
        name: SCREEN.internPlanCenterDetail.name,
        meta:{
          parent:SCREEN.internPlanCenterMng.name
        },
        component: () => import("@/views/internPlanCenterMng/InternPlanCenterDetail.vue"),
      },
      {
        path: SCREEN.notice.path,
        name: SCREEN.notice.name,
        component: () => import("@/views/notice/NoticeList.vue"),
      },
      {
        path: SCREEN.noticeCrud.path,
        name: SCREEN.noticeCrud.name,
        meta:{
          parent:SCREEN.notice.name
        },
        component: () => import("@/views/notice/NoticeCrud.vue"),
      },
      {
        path: SCREEN.corporateApprovalMng.path,
        name: SCREEN.corporateApprovalMng.name,
        component: () => import("@/views/corporateApprovalMng/CorporateApprovalMngList.vue"),
      },
      {
        path: SCREEN.docRoom.path,
        name: SCREEN.docRoom.name,
        component: () => import("@/views/docRoom/DocRoom.vue"),
      },
      {
        path: SCREEN.crudDocRoom.path,
        name: SCREEN.crudDocRoom.name,
        meta:{
          parent:SCREEN.docRoom.name
        },
        component: () => import("@/views/docRoom/CrudDocRoom.vue"),
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
