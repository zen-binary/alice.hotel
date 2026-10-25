package com.winwintek.web.backend.api.repository.admin.corporate.operateInternMng;

import com.winwintek.web.backend.api.dto.admin.corporate.operateInternMng.OperateInternCorpSearchDTO;
import com.winwintek.web.backend.api.entity.admin.corporate.operateInternMng.IOperateInternCorpResponse;
import com.winwintek.web.backend.api.entity.admin.corporate.operateInternMng.OperateInternCorpEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OperateInternCorpRepository extends JpaRepository<OperateInternCorpEntity, String> {

    @Query (value = """
                WITH CTE_INTERN_PLAN AS (
                    SELECT
                        CASE
                            WHEN (TO_CHAR(SYSDATE, 'yyyy-MM-dd') >= TO_CHAR(ipl.INTERNSHIP_STR, 'yyyy-MM-dd')
                                    AND TO_CHAR(SYSDATE, 'yyyy-MM-dd') <= TO_CHAR(ipl.INTERNSHIP_END, 'yyyy-MM-dd')) THEN 1
                            WHEN (ipl.INTERNSHIP_STR IS NULL OR ipl.INTERNSHIP_END IS NULL) THEN 3
                            ELSE 2
                        END AS sortByDate,
                        ipl.INTERN_PLAN_SEQ AS internPlanId,
                        cm.CORM_NAME_KR AS corporateNm,
                        ipl.INTERN_PLAN_NM AS internPlanNm,
                        ipl.INTERN_RECRUITMENT_QUANTITY AS numberIntern,
                        ipl.INTERNSHIP_STR AS internStrDate,
                        ipl.INTERNSHIP_END AS internEndDate
                    FROM INTERN_PLAN ipl
                        LEFT JOIN CORPORATE_MEMBERS cm ON cm.CORM_SEQ = ipl.CORM_SEQ
                    WHERE ipl.DEL_YN = :#{#dto.delYn}
                        AND ipl.STATUS = :#{#dto.planApproveStatus}
                        AND cm.DEL_YN = :#{#dto.delYn}
                        AND cm.CORM_STATUS = :#{#dto.corpApproveStatus}
                        AND (:#{#dto.planName} IS NULL
                                OR LOWER(ipl.INTERN_PLAN_NM) LIKE '%' || LOWER(:#{#dto.planName}) || '%')
                        AND (:#{#dto.startDate} IS NULL OR TO_CHAR(ipl.INTERNSHIP_STR, 'yyyy-MM-dd') >= :#{#dto.startDate})
                        AND (:#{#dto.endDate} IS NULL OR TO_CHAR(ipl.INTERNSHIP_END, 'yyyy-MM-dd') <= :#{#dto.endDate})
                        AND ipl.CORM_SEQ = :cormSeq
                )
                SELECT
                    ROW_NUMBER() OVER(
                        ORDER BY cipl.sortByDate DESC, 
                        cipl.internStrDate ASC, 
                        cipl.internEndDate DESC 
                    ) AS rowNumber,
                    cipl.internPlanId,
                    cipl.corporateNm,
                    cipl.internPlanNm,
                    cipl.numberIntern,
                    cipl.internStrDate,
                    cipl.internEndDate
                FROM CTE_INTERN_PLAN cipl
                ORDER BY cipl.sortByDate ASC, 
                         cipl.internStrDate DESC, 
                         cipl.internEndDate ASC
            """, countQuery = """
                SELECT 
                    COUNT(ipl.INTERN_PLAN_SEQ)
                FROM INTERN_PLAN ipl
                    LEFT JOIN CORPORATE_MEMBERS cm ON cm.CORM_SEQ = ipl.CORM_SEQ
                WHERE ipl.DEL_YN = :#{#dto.delYn}
                    AND ipl.STATUS = :#{#dto.planApproveStatus}
                    AND cm.DEL_YN = :#{#dto.delYn}
                    AND cm.CORM_STATUS = :#{#dto.corpApproveStatus}
                    AND (:#{#dto.planName} IS NULL 
                            OR LOWER(ipl.INTERN_PLAN_NM) LIKE '%' || LOWER(:#{#dto.planName}) || '%')
                    AND (:#{#dto.startDate} IS NULL OR TO_CHAR(ipl.INTERNSHIP_STR, 'yyyy-MM-dd') >= :#{#dto.startDate})
                    AND (:#{#dto.endDate} IS NULL OR TO_CHAR(ipl.INTERNSHIP_END, 'yyyy-MM-dd') <= :#{#dto.endDate})
                    AND ipl.CORM_SEQ = :cormSeq
            """, nativeQuery = true)
    Page<IOperateInternCorpResponse> searchOperateIntern(@Param ("dto") OperateInternCorpSearchDTO dto,
            @Param ("cormSeq") String cormSeq,
            Pageable pageable);

}
