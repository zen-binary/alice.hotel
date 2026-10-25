package com.alice.hotel.api.repository.admin.hotel;
import com.alice.hotel.api.entity.admin.hotel.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface HotelRepository extends JpaRepository<HotelEntity, String> {
    Optional<HotelEntity> findFirstByDelYn(String delYn);
}
