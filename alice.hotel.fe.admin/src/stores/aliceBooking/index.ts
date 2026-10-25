import { defineStore } from "pinia";
import { ref } from "vue";
import http from "@/utils/http";
import {
  ALICE_BOOKINGS,
  ALICE_DASHBOARD,
  ALICE_FRONT_DESK,
  ALICE_HOTEL,
  ALICE_ROOM_TYPES,
  ALICE_ROOMS,
  ALICE_SERVICES,
  ALICE_GUESTS,
  ALICE_SHIFTS,
  ALICE_VOUCHERS,
} from "./aliceBooking.service";

export const aliceBookingStore = defineStore("aliceBookingStore", () => {
  const board = ref<any>();
  const dashboard = ref<any>();
  const roomTypes = ref<any[]>([]);
  const rooms = ref<any[]>([]);
  const services = ref<any[]>([]);
  const vouchers = ref<any[]>([]);
  const bookings = ref<any[]>([]);
  const hotel = ref<any>();
  const shift = ref<any>();

  const loadHotel = async () => {
    const res = await http.get(ALICE_HOTEL);
    hotel.value = res.data?.data;
    return hotel.value;
  };

  const saveHotel = async (body: any) => {
    const res = await http.put(ALICE_HOTEL, body);
    hotel.value = res.data?.data;
    return hotel.value;
  };

  const loadRoomTypes = async () => {
    const res = await http.post(`${ALICE_ROOM_TYPES}/list`, {});
    roomTypes.value = res.data?.data || [];
  };

  const saveRoomType = async (body: any) => {
    const res = body.roomTypeId
      ? await http.put(`${ALICE_ROOM_TYPES}/${body.roomTypeId}`, body)
      : await http.post(ALICE_ROOM_TYPES, body);
    await loadRoomTypes();
    return res.data?.data;
  };

  const loadRooms = async () => {
    const res = await http.post(`${ALICE_ROOMS}/list`, {});
    rooms.value = res.data?.data || [];
  };

  const saveRoom = async (body: any) => {
    if (body.roomId) {
      await http.put(`${ALICE_ROOMS}/${body.roomId}`, body);
    } else {
      await http.post(ALICE_ROOMS, body);
    }
    await loadRooms();
  };

  const setHouseStatus = async (roomId: string, houseStatus: string) => {
    await http.put(`${ALICE_ROOMS}/${roomId}/house-status`, { houseStatus });
    await loadRooms();
  };

  const loadServices = async () => {
    const res = await http.post(`${ALICE_SERVICES}/list`, {});
    services.value = res.data?.data || [];
  };

  const saveService = async (body: any) => {
    if (body.serviceId) {
      await http.put(`${ALICE_SERVICES}/${body.serviceId}`, body);
    } else {
      await http.post(ALICE_SERVICES, body);
    }
    await loadServices();
  };

  const loadVouchers = async () => {
    const res = await http.post(`${ALICE_VOUCHERS}/list`, {});
    vouchers.value = res.data?.data || [];
  };

  const saveVoucher = async (body: any) => {
    if (body.voucherId) {
      await http.put(`${ALICE_VOUCHERS}/${body.voucherId}`, body);
    } else {
      await http.post(ALICE_VOUCHERS, body);
    }
    await loadVouchers();
  };

  const loadBookings = async (status?: string) => {
    const res = await http.post(`${ALICE_BOOKINGS}/list`, { status });
    bookings.value = res.data?.data || [];
  };

  const walkIn = async (body: any) => {
    const res = await http.post(ALICE_BOOKINGS, body);
    return res.data?.data;
  };

  const checkIn = async (id: string, body: any = {}) => {
    const res = await http.post(`${ALICE_BOOKINGS}/${id}/check-in`, body);
    return res.data?.data;
  };

  const checkOut = async (id: string, body: any = {}) => {
    const res = await http.post(`${ALICE_BOOKINGS}/${id}/check-out`, body);
    return res.data?.data;
  };

  const addService = async (id: string, body: any) => {
    const res = await http.post(`${ALICE_BOOKINGS}/${id}/services`, body);
    return res.data?.data;
  };

  const pay = async (id: string, body: any) => {
    const res = await http.post(`${ALICE_BOOKINGS}/${id}/pay`, body);
    return res.data?.data;
  };

  const noShow = async (id: string) => {
    const res = await http.post(`${ALICE_BOOKINGS}/${id}/no-show`, {});
    return res.data?.data;
  };

  const loadBoard = async () => {
    const res = await http.get(ALICE_FRONT_DESK);
    board.value = res.data?.data;
  };

  const loadDashboard = async () => {
    const res = await http.get(ALICE_DASHBOARD);
    dashboard.value = res.data?.data;
  };

  const openShift = async (openingCash = 0) => {
    const res = await http.post(`${ALICE_SHIFTS}/open`, { openingCash });
    shift.value = res.data?.data;
  };

  const closeShift = async (closingCash = 0) => {
    await http.post(`${ALICE_SHIFTS}/close`, { closingCash });
    shift.value = null;
  };

  const loadShift = async () => {
    const res = await http.get(`${ALICE_SHIFTS}/current`);
    shift.value = res.data?.data;
  };

  const loadGuests = async () => {
    const res = await http.get(ALICE_GUESTS);
    return res.data?.data || [];
  };

  const saveGuest = async (body: any) => {
    const res = await http.put(`${ALICE_GUESTS}/${body.userId}`, body);
    return res.data?.data;
  };

  return {
    board,
    dashboard,
    roomTypes,
    rooms,
    services,
    vouchers,
    bookings,
    hotel,
    shift,
    loadHotel,
    saveHotel,
    loadRoomTypes,
    saveRoomType,
    loadRooms,
    saveRoom,
    setHouseStatus,
    loadServices,
    saveService,
    loadVouchers,
    saveVoucher,
    loadBookings,
    walkIn,
    checkIn,
    checkOut,
    addService,
    pay,
    noShow,
    loadBoard,
    loadDashboard,
    openShift,
    closeShift,
    loadShift,
    loadGuests,
    saveGuest,
  };
});
