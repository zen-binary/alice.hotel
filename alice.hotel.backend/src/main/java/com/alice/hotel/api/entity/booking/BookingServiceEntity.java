package com.alice.hotel.api.entity.booking;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "ah_booking_service")
@Getter
@Setter
public class BookingServiceEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "booking_id") private String bookingId;
    @Column(name = "service_id") private String serviceId;
    private Integer qty;
    @Column(name = "unit_price") private BigDecimal unitPrice;
    private BigDecimal amount;
    private String note;
}
