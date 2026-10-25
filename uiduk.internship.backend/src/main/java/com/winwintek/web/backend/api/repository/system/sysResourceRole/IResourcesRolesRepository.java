package com.winwintek.web.backend.api.repository.system.sysResourceRole;

import com.winwintek.web.backend.api.entity.system.sysResourceRole.IResourcesRolesResponse;
import com.winwintek.web.backend.api.entity.system.sysResourceRole.ResourcesRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IResourcesRolesRepository extends JpaRepository<ResourcesRolesEntity, Long> {

    @Query(value = """
            SELECT srr.rr_id, srr.rl_id, srr.rs_id 
            FROM SYS_RESOURCE_ROLE srr 
            WHERE (:roleId IS NULL OR srr.rl_id = :roleId)
                AND (:resourceId IS NULL OR srr.rs_id = :resourceId)
    """, nativeQuery = true)
    List<IResourcesRolesResponse> findByRoleIdAndResourceId(@Param("roleId") Long roleId, @Param("resourceId") Long resourceId);

}
