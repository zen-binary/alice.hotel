package com.winwintek.web.backend.api.repository.admin.supportCenter.guidanceReportCenter;

import com.winwintek.web.backend.api.entity.admin.supportCenter.guidanceReportCenter.GuidanceReportCenterEntity;
import com.winwintek.web.backend.api.entity.admin.supportCenter.guidanceReportCenter.IGuidanceReportCenterDetailResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.guidanceReportCenter.IStudentInfoGuidanceCenterResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuidanceReportCenterRepository extends JpaRepository<GuidanceReportCenterEntity, String> {
    @Query (value = """
                SELECT
                    ipl.INTERN_PLAN_NM AS internPlanNm, 
                    vscs.SUST_CD_NM AS deptStdNm,
                    cm.CORM_NAME_KR AS companyNm,
                    vscs.KOR_NM AS stdNm,
                    vscsf.KOR_NM AS profNm,
                    vscsf.DEPT_NM AS deptProfNm
                FROM INTERN_STD istd 
                        LEFT JOIN INTERN_PLAN ipl ON ipl.INTERN_PLAN_SEQ  = istd.INTERN_PLAN_SEQ
                        LEFT JOIN V_STD_CDP_STAF vscsf ON vscsf.STAFF_NO = ipl.STAFF_NO
                        LEFT JOIN V_STD_CDP_SREG vscs ON vscs.STD_NO = istd.STD_NO
                        LEFT JOIN CORPORATE_MEMBERS cm ON cm.CORM_SEQ = ipl.CORM_SEQ
                WHERE istd.DEL_YN = :delYn
                    AND ipl.DEL_YN = :delYn
                    AND istd.INTERN_STD_SEQ = :internStdId
            """, nativeQuery = true)
    Optional<IStudentInfoGuidanceCenterResponse> getStudentInfoByStdId(@Param ("internStdId") String internStdId,
            @Param ("delYn") String delYn);

    @Query (value = """
                SELECT
                    gr.GUIDANCE_REPORT_SEQ AS id,
                    gr.INTERN_DATE AS internDate,
                    gr.WORKING_DEPARTMENT AS internWorkDept,
                    gr.PERSON_IN_CHARGE AS internManager,
                    gr.ACTUAL_SITUATION AS internSituation,
                    gr.GUIDANCE_CONTENT AS internContent,
                    gr.CORPORATE_FEEDBACK AS internCompanyFeedback,
                    gr.RESULTS_AND_FEEDBACK AS internProfFeedback
                FROM
                    GUIDANCE_REPORT gr
                WHERE gr.DEL_YN = :delYn
                    AND gr.INTERN_PLAN_SEQ = :internPlanId
                    AND gr.INTERN_STD_SEQ = :internStdId
            """, nativeQuery = true)
    Optional<IGuidanceReportCenterDetailResponse> getDetailGuidanceReport(@Param ("internPlanId") String internPlanId,
            @Param ("internStdId") String internStdId, @Param ("delYn") String delYn);

    Optional<GuidanceReportCenterEntity> findByGuidanceReportSeqAndDelYn(String guidanceReportSeq, String delYn);
}
