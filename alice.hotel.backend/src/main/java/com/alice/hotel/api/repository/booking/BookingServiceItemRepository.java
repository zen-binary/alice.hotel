package com.alice.hotel.api.repository.booking;
import com.alice.hotel.api.entity.booking.BookingServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface BookingServiceItemRepository extends JpaRepository<BookingServiceEntity, Long> {
    List<BookingServiceEntity> findByBookingId(String bookingId);
    void deleteByBookingId(String bookingId);
}
