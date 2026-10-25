package com.winwintek.web.backend.api.repository.common.codeMng;

import com.winwintek.web.backend.api.entity.common.codeMng.CodeMngEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeMngRepository extends JpaRepository<CodeMngEntity, String> {
    /**
     * 코드 검색
     *
     * @param upCdId
     * @return
     */
    @Query("SELECT c FROM CodeMngEntity c WHERE (:upCdId IS NULL OR c.upCdId = :upCdId) ORDER BY c.ordNo ASC")
    List<CodeMngEntity> selectByUpCdId(@Param("upCdId") String upCdId);

    List<CodeMngEntity> findByUpCdIdIn(List<String> upCdIds);

    CodeMngEntity findByCdId(String CdId);
}
