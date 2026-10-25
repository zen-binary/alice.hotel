package com.alice.hotel.api.repository.system;

import com.alice.hotel.api.entity.system.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {
    RoleEntity findByRlId(Long rlId);
}
