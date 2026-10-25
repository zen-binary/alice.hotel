package com.alice.hotel.api.entity.admin.room;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import java.time.LocalDateTime;

@DynamicUpdate
@Entity
@Table(name = "ah_room")
@Getter
@Setter
public class RoomEntity {
    @Id @Column(name = "room_id") private String roomId;
    @Column(name = "room_type_id") private String roomTypeId;
    @Column(name = "room_number") private String roomNumber;
    @Column(name = "floor_no") private Integer floorNo;
    @Column(name = "house_status") private String houseStatus;
    @Column(name = "active_yn") private String activeYn;
    private String note;
    @Column(name = "del_yn") private String delYn;
    @Column(name = "reg_id") private String regId;
    @Column(name = "reg_date") private LocalDateTime regDate;
    @Column(name = "mod_id") private String modId;
    @Column(name = "mod_date") private LocalDateTime modDate;
}
