package com.winwintek.web.backend.api.repository.admin.supportCenter.indexInternPlan;

import com.winwintek.web.backend.api.dto.admin.supportCenter.indexInternPlan.IndexInternPlanSearchDTO;
import com.winwintek.web.backend.api.entity.admin.supportCenter.indexInternPlan.IIndexInternPlanResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.indexInternPlan.IndexInternPlanEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexInternPlanRepository extends JpaRepository<IndexInternPlanEntity, String> {

    @Query (value = """
                WITH TEMP_INTERN_PLAN AS (
                    SELECT
                        CASE
                            WHEN (TO_CHAR(SYSDATE, 'yyyy-MM-dd') >= TO_CHAR(ipl.INTERNSHIP_STR, 'yyyy-MM-dd')
                                    AND TO_CHAR(SYSDATE, 'yyyy-MM-dd') <= TO_CHAR(ipl.INTERNSHIP_END, 'yyyy-MM-dd')) THEN 1
                            WHEN (ipl.INTERNSHIP_STR IS NULL OR ipl.INTERNSHIP_END IS NULL) THEN 3
                            ELSE 2
                        END AS sortByDate,
                        ipl.INTERN_PLAN_SEQ AS internPlanSeq,
                        cm.CORM_NAME_KR AS cormNameKr,
                        ipl.INTERN_PLAN_NM AS internPlanNm,
                        ipl.INTERN_RECRUITMENT_QUANTITY AS quality,
                        ipl.INTERNSHIP_STR AS internStrDate,
                        ipl.INTERNSHIP_END AS internEndDate
                    FROM INTERN_PLAN ipl
                        LEFT JOIN CORPORATE_MEMBERS cm ON ipl.CORM_SEQ = cm.CORM_SEQ
                    WHERE ipl.DEL_YN = :delYn
                        AND ipl.STATUS = :planApproveStatus
                        AND cm.DEL_YN = :delYn
                        AND cm.CORM_STATUS = :corpApproveStatus
                        AND (:#{#dto.internPlanNm} IS NULL 
                                OR LOWER(ipl.INTERN_PLAN_NM) LIKE '%' || LOWER(:#{#dto.internPlanNm}) || '%') 
                        AND (:#{#dto.startDate} IS NULL 
                                 OR TRUNC(ipl.INTERNSHIP_STR) >= TO_DATE(:#{#dto.startDate}, 'YYYY-MM-DD'))
                        AND (:#{#dto.endDate} IS NULL 
                                 OR TRUNC(ipl.INTERNSHIP_END) <= TO_DATE(:#{#dto.endDate}, 'YYYY-MM-DD'))
                )
                SELECT
                    ROW_NUMBER() OVER(
                        ORDER BY tipl.sortByDate DESC,
                        tipl.internStrDate ASC,
                        tipl.internEndDate DESC) AS rowNumber,
                    tipl.internPlanSeq,
                    tipl.cormNameKr,
                    tipl.internPlanNm,
                    tipl.quality,
                    tipl.internStrDate,
                    tipl.internEndDate
                FROM
                    TEMP_INTERN_PLAN tipl
                ORDER BY
                    tipl.sortByDate ASC,
                    tipl.internStrDate DESC,
                    tipl.internEndDate ASC
            """, countQuery = """
                SELECT 
                    COUNT(ipl.INTERN_PLAN_SEQ)
                FROM INTERN_PLAN ipl
                        JOIN CORPORATE_MEMBERS cm ON ipl.CORM_SEQ = cm.CORM_SEQ
                    WHERE ipl.DEL_YN = :delYn
                        AND ipl.STATUS = :planApproveStatus
                        AND cm.DEL_YN = :delYn
                        AND cm.CORM_STATUS = :corpApproveStatus
                        AND (:#{#dto.internPlanNm} IS NULL 
                                OR LOWER(ipl.INTERN_PLAN_NM) LIKE '%' || LOWER(:#{#dto.internPlanNm}) || '%') 
                        AND (:#{#dto.startDate} IS NULL 
                                 OR TRUNC(ipl.INTERNSHIP_STR) >= TO_DATE(:#{#dto.startDate}, 'YYYY-MM-DD'))
                        AND (:#{#dto.endDate} IS NULL 
                                 OR TRUNC(ipl.INTERNSHIP_END) <= TO_DATE(:#{#dto.endDate}, 'YYYY-MM-DD'))
            """, nativeQuery = true)
    Page<IIndexInternPlanResponse> getListIndexInternPlan(@Param ("dto") IndexInternPlanSearchDTO dto,
            @Param ("planApproveStatus") String planApproveStatus,
            @Param ("corpApproveStatus") String corpApproveStatus, @Param ("delYn") String delYn, Pageable pageable);

}
