package com.winwintek.web.backend.api.repository.client.professor.guidanceReport;

import com.winwintek.web.backend.api.entity.client.professor.guidanceReport.GuidanceReportProfEntity;
import com.winwintek.web.backend.api.entity.client.professor.guidanceReport.IGuidanceReportDetailResponse;
import com.winwintek.web.backend.api.entity.client.professor.guidanceReport.IStudentInfoGuidanceResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GuidanceReportProfRepository extends JpaRepository<GuidanceReportProfEntity, String> {

    @Query (value = """
                SELECT
                    ipl.INTERN_PLAN_NM AS internPlanNm, 
                    vscs.SUST_CD_NM AS deptStdNm,
                    cm.CORM_NAME_KR AS companyNm,
                    vscs.KOR_NM AS stdNm,
                    vscsf.KOR_NM AS profNm,
                    vscsf.DEPT_NM AS deptProfNm
                FROM INTERN_STD istd 
                        JOIN INTERN_PLAN ipl ON ipl.INTERN_PLAN_SEQ  = istd.INTERN_PLAN_SEQ
                        JOIN V_STD_CDP_STAF vscsf ON vscsf.STAFF_NO = ipl.STAFF_NO
                        JOIN V_STD_CDP_SREG vscs ON vscs.STD_NO = istd.STD_NO
                        JOIN CORPORATE_MEMBERS cm ON cm.CORM_SEQ = ipl.CORM_SEQ
                WHERE istd.DEL_YN = :delYn
                    AND ipl.DEL_YN = :delYn
                    AND istd.INTERN_STD_SEQ = :internStdId
            """, nativeQuery = true)
    IStudentInfoGuidanceResponse getStudentInfoByStdId(@Param ("internStdId") String internStdId,
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
    IGuidanceReportDetailResponse getDetailGuidanceReport(@Param ("internPlanId") String internPlanId,
            @Param ("internStdId") String internStdId, @Param ("delYn") String delYn);

    GuidanceReportProfEntity findByGuidanceReportSeqAndDelYn(String guidanceReportSeq, String delYn);

}
