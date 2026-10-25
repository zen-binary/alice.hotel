package com.alice.hotel.api.repository.system;

import com.alice.hotel.api.dto.system.VerifyCheckUrlDTO;
import com.alice.hotel.api.entity.system.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface IResourceRepository extends JpaRepository<ResourceEntity, Long> {
    @Query(value = "SELECT " +
            "    (select sr.url from sys_resource sr where sr.rs_id = srr.rs_id) AS url, " +
            "    COALESCE(GROUP_CONCAT(rl_id ORDER BY rl_id SEPARATOR ','), 'permitAll') AS roles " +
            "FROM " +
            "    sys_resource_role srr " +
            "GROUP BY " +
            "    srr.rs_id", nativeQuery = true)
    List<Map<String, Object>> findAllResources();

//    @Query(value = "SELECT r.url, role.rl_id FROM system_resource r left JOIN system_resource_role rr ON r.rs_id = rr.rs_id left JOIN system_role role ON rr.rl_id = role.rl_id WHERE r.rs_type = :rsType", nativeQuery = true)
//    List<Map<String, Object>> findUrlAndRoleIdsByRsTypeNative(@Param("rsType") String rsType);

    ResourceEntity findByUrl(String url);

    @Query("SELECT r.rsId FROM ResourceEntity r WHERE r.url = :url and LOWER(r.httpMethod) = LOWER(:method) ")
    Long findRsIdByUrlAndMethod(@Param("url") String url, @Param("method") String method);

}
