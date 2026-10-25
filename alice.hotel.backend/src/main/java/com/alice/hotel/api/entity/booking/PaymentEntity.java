package com.alice.hotel.api.entity.booking;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ah_payment")
@Getter
@Setter
public class PaymentEntity {
    @Id @Column(name = "payment_id") private String paymentId;
    @Column(name = "booking_id") private String bookingId;
    private String method;
    private BigDecimal amount;
    private String status;
    @Column(name = "txn_ref") private String txnRef;
    @Column(name = "vnp_txn_no") private String vnpTxnNo;
    @Column(name = "shift_id") private String shiftId;
    private String note;
    @Column(name = "reg_id") private String regId;
    @Column(name = "reg_date") private LocalDateTime regDate;
}
