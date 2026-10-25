package com.alice.hotel.api.entity.admin.roomType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@DynamicUpdate
@Entity
@Table(name = "ah_room_type")
@Getter
@Setter
public class RoomTypeEntity {
    @Id @Column(name = "room_type_id") private String roomTypeId;
    private String code;
    private String name;
    @Column(length = 2000) private String description;
    @Column(name = "base_price") private BigDecimal basePrice;
    @Column(name = "capacity_adult") private Integer capacityAdult;
    @Column(name = "capacity_child") private Integer capacityChild;
    @Column(name = "bed_info") private String bedInfo;
    @Column(name = "image_urls", length = 2000) private String imageUrls;
    @Column(name = "del_yn") private String delYn;
    @Column(name = "reg_id") private String regId;
    @Column(name = "reg_date") private LocalDateTime regDate;
    @Column(name = "mod_id") private String modId;
    @Column(name = "mod_date") private LocalDateTime modDate;
}
