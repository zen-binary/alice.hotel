package com.winwintek.web.backend.api.repository.admin.supportCenter.completionMng;

import com.winwintek.web.backend.api.dto.admin.supportCenter.completionMng.CompletionStdMngRequestDTO;
import com.winwintek.web.backend.api.entity.admin.supportCenter.completionMng.CompletionStdCountResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.completionMng.CompletionStdMngEntity;
import com.winwintek.web.backend.api.entity.admin.supportCenter.completionMng.CompletionStdMngResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletionStdMngRepository extends JpaRepository<CompletionStdMngEntity, String> {

    @Query (value = """
            WITH ITE_INTERN_STD AS (
            SELECT
            		iss.INTERN_STD_SEQ AS internStdSeq,
            		CASE
                      WHEN iss.INTERNSHIP_STATUS = :#{#dto.internComplete} THEN 4
                      WHEN iss.INTERNSHIP_STATUS = :#{#dto.internWaitComplete} THEN 3
                      WHEN iss.INTERNSHIP_STATUS = :#{#dto.internNotComplete} THEN 2
                      WHEN iss.INTERNSHIP_STATUS = :#{#dto.internCancelBackups} THEN 1
                      ELSE 5
                    END AS internShipStatusOrder,
            		FN_GET_CODE_NAME(iss.INTERNSHIP_STATUS) AS internShipStatus,
            		iss.INTERNSHIP_STATUS AS internShipStatusCd,
            		vscs.COLG_CD_NM AS colgNm,
            		vscs.SUST_CD_NM AS sustNm,
            		vscs.SHYR_CD_NM AS shyrNm,
            		vscs.STD_NO AS stdNo,
            		vscs.KOR_NM AS korNm,
            		vscs.HAND_NO AS handNo,
            		vscs.EMAIL AS email,
            		iss.REG_DATE AS regDate,
            		iss.MODIFY_DATE AS modifyDate
            FROM
            		INTERN_STD iss
            JOIN V_STD_CDP_SREG vscs
            	ON
            		iss.STD_NO = vscs.STD_NO
            WHERE
            		iss.DEL_YN = :delYn
                    AND iss.APPROVE_STATUS = :#{#dto.approveStatusIntern}
            		AND iss.INTERN_PLAN_SEQ = :#{#dto.internPlanId}
            )
            SELECT
            	ROW_NUMBER() OVER(ORDER BY internShipStatusOrder ASC, modifyDate ASC) AS rowNumb,
            	internStdSeq,
            	internShipStatus,
            	internShipStatusCd,
            	colgNm,
            	sustNm,
            	shyrNm,
            	stdNo,
            	korNm,
            	handNo,
            	email,
            	regDate,
            	modifyDate
            FROM
            	ITE_INTERN_STD
            ORDER BY
            	 rowNumb DESC
            """,
            countQuery = """
            SELECT
            		COUNT(iss.INTERN_STD_SEQ)
            FROM
            		INTERN_STD iss
            JOIN V_STD_CDP_SREG vscs
            	ON
            		iss.STD_NO = vscs.STD_NO
            WHERE
            		iss.DEL_YN = :delYn
                    AND iss.APPROVE_STATUS = :#{#dto.approveStatusIntern}
            		AND iss.INTERN_PLAN_SEQ = :#{#dto.internPlanId}
""", nativeQuery = true)
    Page<CompletionStdMngResponse> getPageCompletionStd(Pageable pageable, CompletionStdMngRequestDTO dto,
            @Param ("delYn") String delYn);

    @Query(value = """
SELECT
    	COUNT(CASE WHEN iss.INTERNSHIP_STATUS = :#{#dto.internWaitComplete} THEN 1 ELSE NULL END) AS waitCompleteCount,
        COUNT(CASE WHEN iss.INTERNSHIP_STATUS = :#{#dto.internComplete} THEN 1 ELSE NULL END) AS completeCount,
        COUNT(CASE WHEN iss.INTERNSHIP_STATUS = :#{#dto.internNotComplete} THEN 1 ELSE NULL END) AS notCompleteCount,
        COUNT(CASE WHEN iss.INTERNSHIP_STATUS = :#{#dto.internCancelBackups} THEN 1 ELSE NULL END) AS backupsCount
    FROM
    	INTERN_STD iss
    WHERE
    	iss.DEL_YN = :delYn
        AND iss.APPROVE_STATUS = :#{#dto.approveStatusIntern}
    	AND iss.INTERN_PLAN_SEQ = :#{#dto.internPlanId}
""", nativeQuery = true)
    CompletionStdCountResponse getCountStatusCompletion(CompletionStdMngRequestDTO dto, @Param ("delYn") String delYn);

}
