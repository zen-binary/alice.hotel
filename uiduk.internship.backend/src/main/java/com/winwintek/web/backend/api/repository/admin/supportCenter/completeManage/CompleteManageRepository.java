package com.winwintek.web.backend.api.repository.admin.supportCenter.completeManage;

import com.winwintek.web.backend.api.entity.admin.supportCenter.completeManage.ICompleteManageReponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.completeManage.InternStdEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompleteManageRepository extends JpaRepository<InternStdEntity, String> {
    @Query(value = """
        SELECT
            ROW_NUMBER() OVER (ORDER BY cmr.INTERNSHIP_STATUS, cmr.MODIFY_DATE DESC) AS rowNumber,
            cmr.internPlanNm,
            cmr.internshipStatus,
            cmr.colgCdNm,
            cmr.sustCdNm,
            cmr.shyrCdNm,
            cmr.stdNo,
            cmr.korNm,
            cmr.handNo,
            cmr.email,
            cmr.approveDate,
            cmr.interStdSeq
        FROM (
            SELECT
                ip.INTERN_PLAN_NM AS internPlanNm,
                CASE WHEN ist.INTERNSHIP_STATUS = 'STD_I01_P02' THEN 1 ELSE 2 END AS INTERNSHIP_STATUS,
                ist.MODIFY_DATE,
                FN_GET_CODE_NAME(ist.INTERNSHIP_STATUS) AS internshipStatus,
                vs.COLG_CD_NM AS colgCdNm,
                vs.SUST_CD_NM AS sustCdNm,
                vs.SHYR_CD_NM AS shyrCdNm,
                vs.STD_NO AS stdNo,
                vs.KOR_NM AS korNm,
                vs.HAND_NO AS handNo,
                vs.EMAIL AS email,
                ist.MODIFY_DATE AS approveDate,
                ist.INTERN_STD_SEQ AS interStdSeq
            FROM
                INTERN_STD ist
            JOIN
                INTERN_PLAN ip ON ist.INTERN_PLAN_SEQ = ip.INTERN_PLAN_SEQ
            JOIN
                V_STD_CDP_SREG vs ON ist.STD_NO = vs.STD_NO
            WHERE
                ist.DEL_YN = :delYn
                AND ip.DEL_YN = :delYn
                AND ip.INTERN_PLAN_SEQ = :internPlanId
                AND ist.APPROVE_STATUS = :approveStatus
        ) cmr
        ORDER BY
            cmr.INTERNSHIP_STATUS, cmr.MODIFY_DATE DESC
    """, nativeQuery = true)
    Page<ICompleteManageReponse> getListInfoStudent(@Param ("internPlanId") String internPlanId,  @Param ("approveStatus") String approveStatus,
            Pageable pageable,
            @Param ("delYn") String delYn);



}
