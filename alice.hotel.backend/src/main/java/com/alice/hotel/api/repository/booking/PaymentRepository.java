package com.alice.hotel.api.repository.booking;
import com.alice.hotel.api.entity.booking.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {
    List<PaymentEntity> findByBookingIdOrderByRegDateAsc(String bookingId);
    List<PaymentEntity> findByTxnRefOrderByRegDateDesc(String txnRef);
    List<PaymentEntity> findByShiftId(String shiftId);
}
