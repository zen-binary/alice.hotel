package com.alice.hotel.api.dto.admin.hotelRoom;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HotelRoomDTO {
    private String roomId;
    private String roomCode;
    private String roomName;
    private String roomType;
    private BigDecimal price;
    private Integer capacity;
    private String status;
    private String description;
}
