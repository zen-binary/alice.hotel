package com.alice.hotel.api.repository.admin.roomType;
import com.alice.hotel.api.entity.admin.roomType.RoomTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface RoomTypeRepository extends JpaRepository<RoomTypeEntity, String> {
    List<RoomTypeEntity> findByDelYnOrderByNameAsc(String delYn);
    Optional<RoomTypeEntity> findByRoomTypeIdAndDelYn(String id, String delYn);
    boolean existsByCodeAndDelYn(String code, String delYn);
}
