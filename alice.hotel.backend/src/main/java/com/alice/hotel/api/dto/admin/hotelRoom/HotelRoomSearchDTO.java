package com.alice.hotel.api.dto.admin.hotelRoom;

import lombok.Data;

@Data
public class HotelRoomSearchDTO {
    private String keyword;
    private String roomType;
    private String status;
    private int page = 1;
    private int size = 10;
}
