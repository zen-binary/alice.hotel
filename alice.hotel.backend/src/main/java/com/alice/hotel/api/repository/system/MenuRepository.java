package com.alice.hotel.api.repository.system;

import com.alice.hotel.api.entity.system.menuMng.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, String> {
    /**
     * Get meny by site type
     *
     * @param siteType
     * @return
     */
    List<MenuEntity> findBySiteTypeOrderByParentIdAscDisplayOrderAsc(String siteType);

    /**
     * Get all menu
     *
     * @return
     */
    List<MenuEntity> findAllByOrderByParentIdAscDisplayOrderAsc();
}
