package com.alice.hotel.api.repository.system.sysRole;

import com.alice.hotel.api.entity.system.sysRole.SysRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolesRepository extends JpaRepository<SysRolesEntity, Long> {
}
