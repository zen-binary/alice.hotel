package com.alice.hotel.api.entity.booking;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ah_shift")
@Getter
@Setter
public class ShiftEntity {
    @Id @Column(name = "shift_id") private String shiftId;
    @Column(name = "user_id") private String userId;
    @Column(name = "opened_at") private LocalDateTime openedAt;
    @Column(name = "closed_at") private LocalDateTime closedAt;
    @Column(name = "opening_cash") private BigDecimal openingCash;
    @Column(name = "closing_cash") private BigDecimal closingCash;
    @Column(name = "expected_cash") private BigDecimal expectedCash;
    @Column(name = "cash_total") private BigDecimal cashTotal;
    @Column(name = "transfer_total") private BigDecimal transferTotal;
    @Column(name = "online_total") private BigDecimal onlineTotal;
    private String note;
    private String status;
}
