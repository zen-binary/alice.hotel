package com.alice.hotel.api.entity.admin.hotel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@DynamicUpdate
@Entity
@Table(name = "ah_hotel")
@Getter
@Setter
public class HotelEntity {
    @Id @Column(name = "hotel_id") private String hotelId;
    private String name;
    private String address;
    private String phone;
    private String email;
    @Column(length = 2000) private String description;
    @Column(name = "check_in_time") private String checkInTime;
    @Column(name = "check_out_time") private String checkOutTime;
    @Column(name = "cancel_free_hours") private Integer cancelFreeHours;
    @Column(name = "cancel_refund_percent") private Integer cancelRefundPercent;
    @Column(name = "deposit_percent") private Integer depositPercent;
    @Column(name = "early_checkin_fee") private BigDecimal earlyCheckinFee;
    @Column(name = "late_checkout_fee") private BigDecimal lateCheckoutFee;
    @Column(name = "hold_minutes") private Integer holdMinutes;
    @Column(name = "image_url") private String imageUrl;
    @Column(name = "del_yn") private String delYn;
    @Column(name = "reg_id") private String regId;
    @Column(name = "reg_date") private LocalDateTime regDate;
    @Column(name = "mod_id") private String modId;
    @Column(name = "mod_date") private LocalDateTime modDate;
}
