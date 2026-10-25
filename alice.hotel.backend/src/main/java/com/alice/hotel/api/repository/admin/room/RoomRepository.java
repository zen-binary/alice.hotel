package com.alice.hotel.api.repository.admin.room;
import com.alice.hotel.api.entity.admin.room.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface RoomRepository extends JpaRepository<RoomEntity, String> {
    List<RoomEntity> findByDelYnOrderByRoomNumberAsc(String delYn);
    Optional<RoomEntity> findByRoomIdAndDelYn(String id, String delYn);
    List<RoomEntity> findByRoomTypeIdAndDelYnAndActiveYn(String roomTypeId, String delYn, String activeYn);

    @Query("""
        SELECT r FROM RoomEntity r
        WHERE r.delYn = 'N' AND r.activeYn = 'Y'
          AND r.roomTypeId = :roomTypeId
          AND r.houseStatus <> 'OUT_OF_ORDER'
          AND r.roomId NOT IN (
            SELECT b.roomId FROM BookingEntity b
            WHERE b.delYn = 'N' AND b.roomId IS NOT NULL
              AND b.status IN ('DRAFT_HOLD','PENDING_PAYMENT','CONFIRMED','CHECKED_IN')
              AND b.checkIn < :checkOut AND b.checkOut > :checkIn
          )
        ORDER BY r.roomNumber
        """)
    List<RoomEntity> findAvailable(@Param("roomTypeId") String roomTypeId,
                                   @Param("checkIn") LocalDate checkIn,
                                   @Param("checkOut") LocalDate checkOut);
}
