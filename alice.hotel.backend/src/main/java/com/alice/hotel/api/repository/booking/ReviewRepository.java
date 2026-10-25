package com.alice.hotel.api.repository.booking;
import com.alice.hotel.api.entity.booking.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {
    Optional<ReviewEntity> findByBookingIdAndDelYn(String bookingId, String delYn);
    List<ReviewEntity> findByRoomTypeIdAndDelYnOrderByRegDateDesc(String roomTypeId, String delYn);
    List<ReviewEntity> findByDelYnOrderByRegDateDesc(String delYn);
}
