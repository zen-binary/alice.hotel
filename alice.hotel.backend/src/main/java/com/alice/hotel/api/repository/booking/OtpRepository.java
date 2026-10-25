package com.alice.hotel.api.repository.booking;
import com.alice.hotel.api.entity.booking.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
    Optional<OtpEntity> findFirstByEmailAndPurposeAndUsedYnOrderByExpireAtDesc(String email, String purpose, String usedYn);
}
