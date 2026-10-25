package com.winwintek.web.backend.api.repository.system;

import com.winwintek.web.backend.api.entity.system.IMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMenuRepository extends JpaRepository<IMenuEntity, Long> {

    IMenuEntity findByRoute (String route);

}
