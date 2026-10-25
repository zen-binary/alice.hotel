package com.winwintek.web.backend.api.repository.system.sysRole;

import com.winwintek.web.backend.api.entity.system.sysRole.SysRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolesRepository extends JpaRepository<SysRolesEntity, Long> {
}
