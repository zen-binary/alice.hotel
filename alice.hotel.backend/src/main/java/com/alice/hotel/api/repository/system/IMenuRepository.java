package com.alice.hotel.api.repository.system;

import com.alice.hotel.api.entity.system.IMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMenuRepository extends JpaRepository<IMenuEntity, Long> {

    List<IMenuEntity> findByRoute(String route);

}
