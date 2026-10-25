package com.winwintek.web.backend.api.repository.admin.corporateManager;

import com.winwintek.web.backend.api.entity.admin.corporateManager.CorporateManagerEntity;
import com.winwintek.web.backend.api.entity.admin.corporateManager.CorporateManagerResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateManagerRepository extends JpaRepository<CorporateManagerEntity, String> {

    @Query (value = """
            SELECT COMM_SEQ AS commSeq,
                   COMM_NM AS commNm,
                   MOBILE_PHONE AS mobilePhone
            FROM CORPORATE_MEMBERS_MANAGER
            WHERE DEL_YN = :delYn AND CORM_SEQ = :cormSeq
            """, nativeQuery = true)
    List<CorporateManagerResponse> getCorporateManager(@Param ("cormSeq") String cormSeq,
            @Param ("delYn") String delYn);

}
