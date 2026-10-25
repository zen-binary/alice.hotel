package com.alice.hotel.api.repository.system;

import com.alice.hotel.api.entity.system.SysLoginEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SysLoginEventRepository extends JpaRepository<SysLoginEventEntity, Long> {
}