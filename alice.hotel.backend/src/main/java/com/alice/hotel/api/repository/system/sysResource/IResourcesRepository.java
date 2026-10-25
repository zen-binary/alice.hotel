package com.alice.hotel.api.repository.system.sysResource;

import com.alice.hotel.api.dto.system.sysResource.ResourcesSearchDTO;
import com.alice.hotel.api.entity.system.sysResource.IResourcesSearchResponse;
import com.alice.hotel.api.entity.system.sysResource.ResourcesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IResourcesRepository extends JpaRepository<ResourcesEntity, Long> {

    @Query(value = """
        SELECT ROW_NUMBER() OVER (ORDER BY sr.rs_id DESC) AS rowNumber,
            sr.rs_id,
            sr.url,
            sr.http_Method,
            sr.rs_Nm,
            sr.rs_Type,
            sr.reg_Date
        FROM sys_resource sr
        WHERE (:#{#searchRequest.name} IS NULL OR LOWER(sr.rs_Nm) LIKE CONCAT('%', LOWER(:#{#searchRequest.name}), '%'))
            AND (:#{#searchRequest.url} IS NULL OR LOWER(sr.url) LIKE CONCAT('%', LOWER(:#{#searchRequest.url}), '%'))
            AND (:#{#searchRequest.type} IS NULL OR sr.rs_Type LIKE CONCAT('%', :#{#searchRequest.type}, '%'))
            AND (:#{#searchRequest.method} IS NULL OR sr.http_Method LIKE CONCAT('%', :#{#searchRequest.method}, '%'))
            AND (NULLIF(:#{#searchRequest.startDate}, '') IS NULL OR DATE_FORMAT(reg_Date, '%Y-%m-%d') >= :#{#searchRequest.startDate})
            AND (NULLIF(:#{#searchRequest.endDate}, '') IS NULL OR DATE_FORMAT(reg_Date, '%Y-%m-%d') <= :#{#searchRequest.endDate})
    """, nativeQuery = true)
    Page<IResourcesSearchResponse> searchResource(@Param("searchRequest") ResourcesSearchDTO searchRequest, Pageable pageable);

}
