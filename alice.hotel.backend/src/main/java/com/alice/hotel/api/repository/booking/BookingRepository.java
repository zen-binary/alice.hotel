package com.alice.hotel.api.repository.booking;
import com.alice.hotel.api.entity.booking.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
public interface BookingRepository extends JpaRepository<BookingEntity, String> {
    Optional<BookingEntity> findByBookingIdAndDelYn(String id, String delYn);
    Optional<BookingEntity> findByBookingCodeAndDelYn(String code, String delYn);
    List<BookingEntity> findByUserIdAndDelYnOrderByRegDateDesc(String userId, String delYn);
    List<BookingEntity> findByDelYnOrderByRegDateDesc(String delYn);
    List<BookingEntity> findByStatusAndHoldExpireAtBeforeAndDelYn(String status, LocalDateTime before, String delYn);

    @Query("""
        SELECT COUNT(b) FROM BookingEntity b
        WHERE b.delYn = 'N' AND b.roomTypeId = :roomTypeId
          AND b.status IN ('DRAFT_HOLD','PENDING_PAYMENT','CONFIRMED','CHECKED_IN')
          AND b.checkIn < :checkOut AND b.checkOut > :checkIn
        """)
    long countOverlapping(@Param("roomTypeId") String roomTypeId,
                          @Param("checkIn") LocalDate checkIn,
                          @Param("checkOut") LocalDate checkOut);

    @Query("""
        SELECT b FROM BookingEntity b WHERE b.delYn = 'N'
          AND (:status IS NULL OR :status = '' OR b.status = :status)
          AND (:fromDate IS NULL OR b.checkIn >= :fromDate)
          AND (:toDate IS NULL OR b.checkOut <= :toDate)
        ORDER BY b.regDate DESC
        """)
    List<BookingEntity> searchAdmin(@Param("status") String status,
                                    @Param("fromDate") LocalDate fromDate,
                                    @Param("toDate") LocalDate toDate);

    @Query("""
        SELECT b FROM BookingEntity b WHERE b.delYn = 'N'
          AND b.checkIn = :day AND b.status IN ('CONFIRMED','PENDING_PAYMENT')
        """)
    List<BookingEntity> arrivals(@Param("day") LocalDate day);

    @Query("""
        SELECT b FROM BookingEntity b WHERE b.delYn = 'N'
          AND b.checkOut = :day AND b.status = 'CHECKED_IN'
        """)
    List<BookingEntity> departures(@Param("day") LocalDate day);

    @Query("""
        SELECT b FROM BookingEntity b WHERE b.delYn = 'N' AND b.status = 'CHECKED_IN'
        """)
    List<BookingEntity> inHouse();

    @Query("""
        SELECT b FROM BookingEntity b WHERE b.delYn = 'N'
          AND b.status = 'CONFIRMED' AND b.checkIn = :day AND b.reminderSentYn = 'N'
        """)
    List<BookingEntity> needReminder(@Param("day") LocalDate day);
}
