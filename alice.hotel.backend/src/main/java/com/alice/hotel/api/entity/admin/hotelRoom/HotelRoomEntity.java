package com.alice.hotel.api.entity.admin.hotelRoom;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@DynamicUpdate
@Entity
@Table(name = "hotel_room")
@Getter
@Setter
public class HotelRoomEntity {

    @Id
    @Column(name = "room_id")
    private String roomId;

    @Column(name = "room_code")
    private String roomCode;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "room_type")
    private String roomType;

    private BigDecimal price;

    private Integer capacity;

    private String status;

    private String description;

    @Column(name = "del_yn")
    private String delYn;

    @Column(name = "reg_id")
    private String regId;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "mod_id")
    private String modId;

    @Column(name = "mod_date")
    private LocalDateTime modDate;
}
