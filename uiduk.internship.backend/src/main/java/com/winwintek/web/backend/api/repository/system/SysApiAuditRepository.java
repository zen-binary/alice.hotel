package com.winwintek.web.backend.api.repository.system;

import com.winwintek.web.backend.api.entity.system.SysApiAuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SysApiAuditRepository extends JpaRepository<SysApiAuditEntity, Long> {
}