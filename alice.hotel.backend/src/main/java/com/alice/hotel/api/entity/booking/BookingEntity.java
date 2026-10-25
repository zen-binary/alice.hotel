package com.alice.hotel.api.entity.booking;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@DynamicUpdate
@Entity
@Table(name = "ah_booking")
@Getter
@Setter
public class BookingEntity {
    @Id @Column(name = "booking_id") private String bookingId;
    @Column(name = "booking_code") private String bookingCode;
    @Column(name = "user_id") private String userId;
    @Column(name = "guest_name") private String guestName;
    @Column(name = "guest_phone") private String guestPhone;
    @Column(name = "guest_email") private String guestEmail;
    @Column(name = "guest_id_number") private String guestIdNumber;
    @Column(name = "room_type_id") private String roomTypeId;
    @Column(name = "room_id") private String roomId;
    @Column(name = "check_in") private LocalDate checkIn;
    @Column(name = "check_out") private LocalDate checkOut;
    private Integer adults;
    private Integer children;
    private String status;
    private String source;
    @Column(name = "hold_expire_at") private LocalDateTime holdExpireAt;
    @Column(name = "room_amount") private BigDecimal roomAmount;
    @Column(name = "service_amount") private BigDecimal serviceAmount;
    @Column(name = "discount_amount") private BigDecimal discountAmount;
    @Column(name = "fee_amount") private BigDecimal feeAmount;
    @Column(name = "total_amount") private BigDecimal totalAmount;
    @Column(name = "paid_amount") private BigDecimal paidAmount;
    @Column(name = "voucher_id") private String voucherId;
    @Column(name = "cancel_reason") private String cancelReason;
    private String note;
    @Column(name = "reminder_sent_yn") private String reminderSentYn;
    @Column(name = "del_yn") private String delYn;
    @Column(name = "reg_id") private String regId;
    @Column(name = "reg_date") private LocalDateTime regDate;
    @Column(name = "mod_id") private String modId;
    @Column(name = "mod_date") private LocalDateTime modDate;
}
