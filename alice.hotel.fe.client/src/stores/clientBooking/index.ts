import { defineStore } from "pinia";
import { ref } from "vue";
import http from "@/utils/http";
import {
  C_AVAILABILITY,
  C_BOOKING,
  C_HOTEL,
  C_MINE,
  C_OTP,
  C_REGISTER,
  C_ROOM_TYPES,
  C_SERVICES,
  C_REVIEWS,
} from "./clientBooking.service";

export const clientBookingStore = defineStore("clientBookingStore", () => {
  const hotel = ref<any>();
  const roomTypes = ref<any[]>([]);
  const availability = ref<any[]>([]);
  const services = ref<any[]>([]);
  const mine = ref<any[]>([]);
  const current = ref<any>();

  const loadHotel = async () => {
    const res = await http.get(C_HOTEL);
    hotel.value = res.data?.data;
  };

  const loadRoomTypes = async () => {
    const res = await http.get(C_ROOM_TYPES);
    roomTypes.value = res.data?.data || [];
  };

  const loadServices = async () => {
    const res = await http.get(C_SERVICES);
    services.value = res.data?.data || [];
  };

  const search = async (body: any) => {
    const res = await http.post(C_AVAILABILITY, body);
    availability.value = res.data?.data || [];
  };

  const createBooking = async (body: any) => {
    const res = await http.post(C_BOOKING, body);
    current.value = res.data?.data;
    return current.value;
  };

  const payVnPay = async (bookingId: string) => {
    const res = await http.post(`${C_BOOKING}/${bookingId}/vnpay`, {});
    return res.data?.data;
  };

  const loadMine = async () => {
    const res = await http.get(C_MINE);
    mine.value = res.data?.data || [];
  };

  const cancel = async (bookingId: string, reason?: string) => {
    const res = await http.post(`${C_BOOKING}/${bookingId}/cancel`, { reason });
    return res.data?.data;
  };

  const modify = async (bookingId: string, body: any) => {
    const res = await http.post(`${C_BOOKING}/${bookingId}/modify`, body);
    return res.data?.data;
  };

  const sendOtp = async (email: string) => {
    await http.post(C_OTP, { email, purpose: "REGISTER" });
  };

  const register = async (body: any) => {
    await http.post(C_REGISTER, body);
  };

  const review = async (body: any) => {
    const res = await http.post(C_REVIEWS, body);
    return res.data?.data;
  };

  return {
    hotel,
    roomTypes,
    availability,
    services,
    mine,
    current,
    loadHotel,
    loadRoomTypes,
    loadServices,
    search,
    createBooking,
    payVnPay,
    loadMine,
    cancel,
    modify,
    sendOtp,
    register,
    review,
  };
});
