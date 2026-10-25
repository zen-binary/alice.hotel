package com.alice.hotel.api.repository.system;

import com.alice.hotel.api.entity.system.SysApiAuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SysApiAuditRepository extends JpaRepository<SysApiAuditEntity, Long> {
}