package com.winwintek.web.backend.api.repository.system;

import com.winwintek.web.backend.api.entity.system.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRoleRepository extends JpaRepository<UserRoleEntity,Long> {
 //   List<UserRoleEntity> findByUserTsid(Long userTsId);
}
