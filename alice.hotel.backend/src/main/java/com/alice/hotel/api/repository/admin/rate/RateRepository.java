package com.alice.hotel.api.repository.admin.rate;
import com.alice.hotel.api.entity.admin.rate.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface RateRepository extends JpaRepository<RateEntity, String> {
    List<RateEntity> findByRoomTypeIdAndDelYn(String roomTypeId, String delYn);
}
