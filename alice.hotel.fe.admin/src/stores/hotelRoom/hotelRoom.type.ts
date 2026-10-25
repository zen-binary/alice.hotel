export interface HotelRoomModel {
  roomId?: string;
  roomCode: string;
  roomName: string;
  roomType: string;
  price: number;
  capacity: number;
  status: string;
  description?: string;
}

export interface HotelRoomSearchModel {
  keyword?: string;
  roomType?: string;
  status?: string;
  page: number;
  size: number;
}
