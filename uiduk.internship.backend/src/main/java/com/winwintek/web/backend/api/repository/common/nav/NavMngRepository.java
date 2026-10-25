package com.winwintek.web.backend.api.repository.common.nav;

import com.winwintek.web.backend.api.entity.common.navItems.RoleAuthenticateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NavMngRepository extends JpaRepository<RoleAuthenticateEntity, Long> {

    RoleAuthenticateEntity findByRlId(Long rlId);

}
