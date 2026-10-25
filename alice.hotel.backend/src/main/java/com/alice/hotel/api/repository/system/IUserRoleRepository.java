package com.alice.hotel.api.repository.system;

import com.alice.hotel.api.entity.system.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRoleRepository extends JpaRepository<UserRoleEntity,Long> {
 //   List<UserRoleEntity> findByUserTsid(Long userTsId);
}
