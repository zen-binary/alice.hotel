import { SCREEN } from "./screen";
import authMiddleware from "@/middleware/auth";

const getView = (path: string) => {
  return () => import(`@/views/${path}.vue`);
};

export const routes = [
  {
    path: SCREEN.login.path,
    name: SCREEN.login.name,
    meta: {
      title: "Đăng nhập",
      middleware: [authMiddleware],
    },
    component: getView("LoginView"),
  },
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
        meta: { title: "Trang chủ" },
        component: () => import("../views/dashboard/DashboardView.vue"),
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
          title: "Menu cạnh",
        },
        component: () => import("../views/MenuSide.vue"),
      },
      {
        path: SCREEN.pathMenu.path,
        name: SCREEN.pathMenu.name,
        meta: {
          title: "Quản lý menu",
        },
        component: () => import("../views/menu/List.vue"),
      },
      {
        path: SCREEN.crudPathMenu.path,
        name: SCREEN.crudPathMenu.name,
        meta: {
          title: "Chi tiết menu",
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
        path: SCREEN.hotelRoom.path,
        name: SCREEN.hotelRoom.name,
        meta: { title: "Phòng khách sạn" },
        component: () => import("../views/hotelRoom/List.vue"),
      },
      {
        path: SCREEN.roomType.path,
        name: SCREEN.roomType.name,
        meta: { title: "Loại phòng", master: "roomType" },
        component: () => import("../views/master/MasterCrudView.vue"),
      },
      {
        path: SCREEN.room.path,
        name: SCREEN.room.name,
        meta: { title: "Phòng", master: "room" },
        component: () => import("../views/master/MasterCrudView.vue"),
      },
      {
        path: SCREEN.hotelService.path,
        name: SCREEN.hotelService.name,
        meta: { title: "Dịch vụ", master: "service" },
        component: () => import("../views/master/MasterCrudView.vue"),
      },
      {
        path: SCREEN.voucher.path,
        name: SCREEN.voucher.name,
        meta: { title: "Mã giảm giá", master: "voucher" },
        component: () => import("../views/master/MasterCrudView.vue"),
      },
      {
        path: SCREEN.rateCalendar.path,
        name: SCREEN.rateCalendar.name,
        meta: { title: "Bảng giá" },
        component: () => import("../views/master/RateCalendarView.vue"),
      },
      {
        path: SCREEN.hotelSettings.path,
        name: SCREEN.hotelSettings.name,
        meta: { title: "Khách sạn", master: "hotel" },
        component: () => import("../views/master/MasterCrudView.vue"),
      },
      {
        path: SCREEN.frontDesk.path,
        name: SCREEN.frontDesk.name,
        meta: { title: "Lễ tân" },
        component: () => import("../views/frontDesk/FrontDeskView.vue"),
      },
      {
        path: SCREEN.bookingAdmin.path,
        name: SCREEN.bookingAdmin.name,
        meta: { title: "Đặt phòng" },
        component: () => import("../views/frontDesk/BookingsListView.vue"),
      },
      {
        path: SCREEN.dashboard.path,
        name: SCREEN.dashboard.name,
        redirect: { name: SCREEN.home.name },
      },
      {
        path: SCREEN.guestAccount.path,
        name: SCREEN.guestAccount.name,
        meta: { title: "Tài khoản khách" },
        component: () => import("../views/guest/GuestAccountsView.vue"),
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
    meta: { title: "Không tìm thấy" },
    component: getView("NotFound"),
  },
  {
    path: "/:pathMatch(.*)*",
    redirect: SCREEN.notFound.path,
    component: getView("NotFound"),
  },
];
