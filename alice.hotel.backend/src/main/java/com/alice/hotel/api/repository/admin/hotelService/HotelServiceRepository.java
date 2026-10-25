package com.alice.hotel.api.repository.admin.hotelService;
import com.alice.hotel.api.entity.admin.hotelService.HotelServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface HotelServiceRepository extends JpaRepository<HotelServiceEntity, String> {
    List<HotelServiceEntity> findByDelYnOrderByNameAsc(String delYn);
    List<HotelServiceEntity> findByDelYnAndActiveYnOrderByNameAsc(String delYn, String activeYn);
    Optional<HotelServiceEntity> findByServiceIdAndDelYn(String id, String delYn);
}
