package com.winwintek.web.backend.api.repository.system.roles;

import com.winwintek.web.backend.api.entity.system.roles.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, Long> {

}
