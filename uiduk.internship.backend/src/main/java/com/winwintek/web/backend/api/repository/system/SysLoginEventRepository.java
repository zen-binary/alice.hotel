package com.winwintek.web.backend.api.repository.system;

import com.winwintek.web.backend.api.entity.system.SysLoginEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SysLoginEventRepository extends JpaRepository<SysLoginEventEntity, Long> {
}