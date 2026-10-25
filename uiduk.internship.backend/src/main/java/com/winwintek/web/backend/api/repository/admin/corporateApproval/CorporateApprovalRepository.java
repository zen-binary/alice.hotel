package com.winwintek.web.backend.api.repository.admin.corporateApproval;

import com.winwintek.web.backend.api.dto.admin.corporateApproval.CorporateApprovalResDTO;
import com.winwintek.web.backend.api.dto.admin.corporateApproval.CorporateApprovalSearchDTO;
import com.winwintek.web.backend.api.entity.admin.corporateApproval.CorporateApproval;
import com.winwintek.web.backend.api.entity.admin.corporateApproval.ICorporateApprovalResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorporateApprovalRepository extends JpaRepository<CorporateApproval, String> {

    @Query (value = """
                WITH CTE_CORPORATE_MEMBERS AS (
                    SELECT
                        cm.CORM_SEQ AS cormSeq,
                        CASE
                           WHEN cm.CORM_STATUS = :cormWaitApproval THEN 1
                           WHEN cm.CORM_STATUS = :cormApproval THEN 2
                           ELSE 3
                        END AS cormStatusOrder,
                        cm.CORM_STATUS AS cormStatusCd,
                        FN_GET_CODE_NAME(cm.CORM_STATUS) AS cormStatusNm,
                        cm.CORM_CONFIRM_DATE AS cormConfirmDate,
                        cm.CORM_USER_ID AS cormUserId,
                        cm.CORM_NAME_KR AS cormNameKr,
                        FN_GET_CODE_NAME(cm.CORM_CLASSIFICATION_CD) AS cormClassificationNm,
                        FN_GET_CODE_NAME(cm.CORM_AWARD_CD) AS cormAwardNm,
                        TO_CHAR(cm.REG_DATE, 'yyyy-MM-dd') AS regDate
                    FROM
                        CORPORATE_MEMBERS cm
                    WHERE cm.DEL_YN = :delYn
                    AND (:#{#dto.cormStatusCd} IS NULL OR :#{#dto.cormStatusCd} = '' OR  cm.CORM_STATUS = :#{#dto.cormStatusCd})
                    AND (:#{#dto.cormUserId} IS NULL OR :#{#dto.cormUserId} = '' OR LOWER(cm.CORM_USER_ID) LIKE '%' || LOWER(:#{#dto.cormUserId}) || '%')
                    AND (:#{#dto.cormNmKr} IS NULL OR :#{#dto.cormNmKr} = '' OR LOWER(cm.CORM_NAME_KR) LIKE '%' || LOWER(:#{#dto.cormNmKr}) || '%')
                    AND (:#{#dto.cormClassificationCd} IS NULL OR :#{#dto.cormClassificationCd} = '' OR  cm.CORM_CLASSIFICATION_CD = :#{#dto.cormClassificationCd})
                    AND (:#{#dto.cormAwardCd} IS NULL OR :#{#dto.cormAwardCd} = '' OR  cm.CORM_AWARD_CD = :#{#dto.cormAwardCd})
                    AND (:#{#dto.cormConfirmDateStr} IS NULL OR TRUNC(cm.CORM_CONFIRM_DATE) >= TO_DATE(:#{#dto.cormConfirmDateStr}, 'YYYY-MM-DD'))
                    AND (:#{#dto.cormConfirmDateEnd} IS NULL OR TRUNC(cm.CORM_CONFIRM_DATE) <= TO_DATE(:#{#dto.cormConfirmDateEnd}, 'YYYY-MM-DD'))
                    AND (:#{#dto.strDate} IS NULL OR TRUNC(cm.REG_DATE) >= TO_DATE(:#{#dto.strDate}, 'YYYY-MM-DD'))
                    AND (:#{#dto.endDate} IS NULL OR TRUNC(cm.REG_DATE) <= TO_DATE(:#{#dto.endDate}, 'YYYY-MM-DD'))
                )
                SELECT
                    ROW_NUMBER() OVER(ORDER BY cormStatusOrder DESC, regDate ASC, cormNameKr DESC ) AS rowNumb,
                    cormSeq,
                    cormStatusOrder,
                    cormStatusCd,
                    cormStatusNm,
                    cormConfirmDate,
                    cormUserId,
                    cormNameKr,
                    cormClassificationNm,
                    cormAwardNm,
                    regDate
                FROM
                    CTE_CORPORATE_MEMBERS
                ORDER BY rowNumb DESC 
            """, countQuery = """
                SELECT COUNT(cm.CORM_SEQ)
                FROM CORPORATE_MEMBERS cm
                WHERE cm.DEL_YN = :delYn
                AND (:#{#dto.cormStatusCd} IS NULL OR :#{#dto.cormStatusCd} = '' OR  cm.CORM_STATUS = :#{#dto.cormStatusCd})
                AND (:#{#dto.cormUserId} IS NULL OR :#{#dto.cormUserId} = '' OR LOWER(cm.CORM_USER_ID) LIKE '%' || LOWER(:#{#dto.cormUserId}) || '%')
                AND (:#{#dto.cormNmKr} IS NULL OR :#{#dto.cormNmKr} = '' OR LOWER(cm.CORM_NAME_KR) LIKE '%' || LOWER(:#{#dto.cormNmKr}) || '%')
                AND (:#{#dto.cormClassificationCd} IS NULL OR :#{#dto.cormClassificationCd} = '' OR  cm.CORM_CLASSIFICATION_CD = :#{#dto.cormClassificationCd})
                AND (:#{#dto.cormAwardCd} IS NULL OR :#{#dto.cormAwardCd} = '' OR  cm.CORM_AWARD_CD = :#{#dto.cormAwardCd})
                AND (:#{#dto.cormConfirmDateStr} IS NULL OR TRUNC(cm.CORM_CONFIRM_DATE) >= TO_DATE(:#{#dto.cormConfirmDateStr}, 'YYYY-MM-DD'))
                AND (:#{#dto.cormConfirmDateEnd} IS NULL OR TRUNC(cm.CORM_CONFIRM_DATE) <= TO_DATE(:#{#dto.cormConfirmDateEnd}, 'YYYY-MM-DD'))
                AND (:#{#dto.strDate} IS NULL OR TRUNC(cm.REG_DATE) >= TO_DATE(:#{#dto.strDate}, 'YYYY-MM-DD'))
                AND (:#{#dto.endDate} IS NULL OR TRUNC(cm.REG_DATE) <= TO_DATE(:#{#dto.endDate}, 'YYYY-MM-DD'))
            """, nativeQuery = true)
    Page<ICorporateApprovalResponse> getListCorporateApproval(Pageable pageable, CorporateApprovalSearchDTO dto,
            @Param ("delYn") String delYn, @Param ("cormWaitApproval") String cormWaitApproval,
            @Param ("cormApproval") String cormApproval);

    @Query (value = """
                            WITH CTE_CORPORATE_MEMBERS AS (
                                SELECT
                                    cm.CORM_SEQ AS cormSeq,
                            		CASE
                            		   WHEN cm.CORM_STATUS = :cormWaitApproval THEN 1
                            		   WHEN cm.CORM_STATUS = :cormApproval THEN 2
                            		   ELSE 3
                            		END AS cormStatusOrder,
                                    cm.CORM_STATUS AS cormStatusCd,
                                    FN_GET_CODE_NAME(cm.CORM_STATUS) AS cormStatusNm,
                                    cm.CORM_CONFIRM_DATE AS cormConfirmDate,
                                    cm.CORM_USER_ID AS cormUserId,
                                    cm.CORM_NAME_KR AS cormNameKr,
                                    FN_GET_CODE_NAME(cm.CORM_CLASSIFICATION_CD) AS cormClassificationNm,
                                    FN_GET_CODE_NAME(cm.CORM_AWARD_CD) AS cormAwardNm,
                                    TO_CHAR(cm.REG_DATE, 'yyyy-MM-dd') AS regDate
                                FROM
                                    CORPORATE_MEMBERS cm
                                WHERE cm.DEL_YN = :delYn
                                AND (:#{#dto.cormStatusCd} IS NULL OR :#{#dto.cormStatusCd} = '' OR  cm.CORM_STATUS = :#{#dto.cormStatusCd})
                                AND (:#{#dto.cormUserId} IS NULL OR :#{#dto.cormUserId} = '' OR LOWER(cm.CORM_USER_ID) LIKE '%' || LOWER(:#{#dto.cormUserId}) || '%')
                                AND (:#{#dto.cormNmKr} IS NULL OR :#{#dto.cormNmKr} = '' OR LOWER(cm.CORM_NAME_KR) LIKE '%' || LOWER(:#{#dto.cormNmKr}) || '%')
                                AND (:#{#dto.cormClassificationCd} IS NULL OR :#{#dto.cormClassificationCd} = '' OR  cm.CORM_CLASSIFICATION_CD = :#{#dto.cormClassificationCd})
                                AND (:#{#dto.cormAwardCd} IS NULL OR :#{#dto.cormAwardCd} = '' OR  cm.CORM_AWARD_CD = :#{#dto.cormAwardCd})
                                AND (:#{#dto.cormConfirmDateStr} IS NULL OR TRUNC(cm.CORM_CONFIRM_DATE) >= TO_DATE(:#{#dto.cormConfirmDateStr}, 'YYYY-MM-DD'))
                                AND (:#{#dto.cormConfirmDateEnd} IS NULL OR TRUNC(cm.CORM_CONFIRM_DATE) <= TO_DATE(:#{#dto.cormConfirmDateEnd}, 'YYYY-MM-DD'))
                                AND (:#{#dto.strDate} IS NULL OR TRUNC(cm.REG_DATE) >= TO_DATE(:#{#dto.strDate}, 'YYYY-MM-DD'))
                                AND (:#{#dto.endDate} IS NULL OR TRUNC(cm.REG_DATE) <= TO_DATE(:#{#dto.endDate}, 'YYYY-MM-DD'))
                            )
                            SELECT
                            	ROW_NUMBER() OVER(ORDER BY cormStatusOrder DESC, regDate ASC, cormNameKr DESC ) AS rowNumb,
                            	cormSeq,
                            	cormStatusOrder,
                                cormStatusCd,
                                cormStatusNm,
                                cormConfirmDate,
                                cormUserId,
                                cormNameKr,
                                cormClassificationNm,
                                cormAwardNm,
                                regDate
                            FROM
                                CTE_CORPORATE_MEMBERS
                        	ORDER BY rowNumb DESC
            """, nativeQuery = true)
    List<ICorporateApprovalResponse> getAllDataExcelCorporateApproval(CorporateApprovalSearchDTO dto,
            @Param ("delYn") String delYn, @Param ("cormWaitApproval") String cormWaitApproval,
            @Param ("cormApproval") String cormApproval);

}
