package com.alice.hotel.api.repository.booking;
import com.alice.hotel.api.entity.booking.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AuditLogRepository extends JpaRepository<AuditLogEntity, Long> {
}
