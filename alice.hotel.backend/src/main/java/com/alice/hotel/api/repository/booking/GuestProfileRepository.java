package com.alice.hotel.api.repository.booking;
import com.alice.hotel.api.entity.booking.GuestProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface GuestProfileRepository extends JpaRepository<GuestProfileEntity, String> {
    List<GuestProfileEntity> findAllByOrderByRegDateDesc();
}
