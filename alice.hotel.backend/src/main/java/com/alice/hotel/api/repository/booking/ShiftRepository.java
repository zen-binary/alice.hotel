package com.alice.hotel.api.repository.booking;
import com.alice.hotel.api.entity.booking.ShiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface ShiftRepository extends JpaRepository<ShiftEntity, String> {
    Optional<ShiftEntity> findFirstByUserIdAndStatus(String userId, String status);
    Optional<ShiftEntity> findByShiftId(String shiftId);
}
