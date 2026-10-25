package com.winwintek.web.backend.api.repository.admin.internPlanCenter;

import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterSearchDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCountStsReqDto;
import com.winwintek.web.backend.api.entity.admin.internPlanCenter.InternPlanCenterMng;
import com.winwintek.web.backend.api.entity.admin.internPlanCenter.InternPlanCenterResponse;
import com.winwintek.web.backend.api.entity.admin.internPlanCenter.InternPlanCenterStudentResponse;
import com.winwintek.web.backend.api.entity.admin.internPlanCenter.InternPlanCountStsResponse;
import com.winwintek.web.backend.api.entity.admin.internPlanCenter.InternPlanStdCenterMng;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InternPlanStdCenterRepository extends JpaRepository<InternPlanStdCenterMng, String> {
    @Query(value = """
SELECT
    ROW_NUMBER() OVER (ORDER BY iss.INTERN_STD_SEQ asc) AS rowNumb,
	iss.INTERN_STD_SEQ,
	FN_GET_CODE_NAME(iss.APPROVE_STATUS) AS approveStatus,
	iss.APPROVE_STATUS AS approveStatusCd,
	vscs.COLG_CD_NM AS colgNm,
	vscs.SUST_CD_NM AS sustNm,
	vscs.SHYR_CD_NM AS shyrNm,
	vscs.STD_NO,
	vscs.KOR_NM,
	vscs.HAND_NO,
	vscs.EMAIL,
	iss.REG_DATE
FROM
	INTERN_STD iss
JOIN V_STD_CDP_SREG vscs
ON
	iss.STD_NO = vscs.STD_NO
WHERE
	iss.DEL_YN = :delYn
	AND iss.INTERN_PLAN_SEQ = :idInternPlan
ORDER BY iss.INTERN_STD_SEQ DESC
""",countQuery = """
SELECT
	COUNT(iss.INTERN_STD_SEQ)
FROM
	INTERN_STD iss
JOIN V_STD_CDP_SREG vscs
ON
	iss.STD_NO = vscs.STD_NO
WHERE
	iss.DEL_YN = :delYn
	AND iss.INTERN_PLAN_SEQ = :idInternPlan
""", nativeQuery = true)
    Page<InternPlanCenterStudentResponse> getListInternPlanStudent(Pageable pageable,@Param("idInternPlan") String idInternPlan,
            @Param ("delYn") String delYn);

    @Query(value = """
SELECT
    	COUNT(CASE WHEN iss.APPROVE_STATUS = :#{#dto.waitApproveStatusCount} THEN 1 ELSE NULL END) AS waitApproveStatusCount,
        COUNT(CASE WHEN iss.APPROVE_STATUS = :#{#dto.approveStatusCount} THEN 1 ELSE NULL END) AS approveStatusCount,
        COUNT(CASE WHEN iss.APPROVE_STATUS = :#{#dto.notApproveStatusCount} THEN 1 ELSE NULL END) AS notApproveStatusCount
    FROM
    	INTERN_STD iss
    WHERE
    	iss.DEL_YN = :delYn
    	AND iss.INTERN_PLAN_SEQ = :#{#dto.idInternPlan} 
    """, nativeQuery = true)
    InternPlanCountStsResponse getStatusStudentInternPlan(InternPlanCountStsReqDto dto,
            @Param ("delYn") String delYn);

}
