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
      public: true,
    },
    component: () => import("@/views/Login/LoginView.vue"),
  },
  {
    path: SCREEN.register.path,
    name: SCREEN.register.name,
    meta: { title: "Đăng ký", public: true },
    component: () => import("@/views/booking/RegisterView.vue"),
  },
  {
    path: SCREEN.paymentReturn.path,
    name: SCREEN.paymentReturn.name,
    meta: { title: "Thanh toán", public: true },
    component: () => import("@/views/booking/PaymentReturnView.vue"),
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
        meta: { public: true, title: "Alice Hotel" },
        component: () => import("@/views/hotel/HotelHomeView.vue"),
      },
      {
        path: SCREEN.book.path,
        name: SCREEN.book.name,
        meta: { public: true, title: "Đặt phòng" },
        component: () => import("@/views/booking/HomeBookingView.vue"),
      },
      {
        path: SCREEN.myBookings.path,
        name: SCREEN.myBookings.name,
        component: () => import("@/views/booking/MyBookingsView.vue"),
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
