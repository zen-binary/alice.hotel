package com.winwintek.web.backend.api.repository.client.student.summaryReportStudent;

import com.winwintek.web.backend.api.entity.client.student.summaryReportStudent.ISummaryReportStudentResponse;
import com.winwintek.web.backend.api.entity.client.student.summaryReportStudent.SummaryReportStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SummaryReportStudentRepository extends JpaRepository<SummaryReportStudentEntity, String> {

    @Query (value = """
                SELECT
                    istd.INTERN_STD_SEQ AS internStdId,
                    ssr.STD_SUMMARY_REPORT_SEQ AS stdSummaryReportId,
                    vscs.KOR_NM AS stdNm,
                    vscd.DEPT_KOR_NM AS deptNm,
                    FN_GET_CODE_NAME(vscs.SHYR_CD) AS stdYear,
                    vscs.STD_NO AS stdNo,
                    ssr.CONTENT AS content,
                    ssr.INTERN_PLAN_SEQ AS internPlanSeq,
                    istd.INTERNSHIP_STATUS AS internshipStatus
                FROM
                    INTERN_STD istd
                        LEFT JOIN V_STD_CDP_SREG vscs ON vscs.STD_NO = istd.STD_NO
                        LEFT JOIN V_STD_CDP_DEPT vscd ON vscd.DEPT_CD = vscs.SUST_CD
                        LEFT JOIN STD_SUMMARY_REPORT ssr ON 
                            ssr.INTERN_STD_SEQ = istd.INTERN_STD_SEQ AND (ssr.DEL_YN IS NULL OR ssr.DEL_YN = :delYn)
                WHERE istd.DEL_YN = :delYn
                    AND istd.APPROVE_STATUS = :approveStatus
                    AND vscs.STD_NO = :stdNo
                    AND istd.INTERN_PLAN_SEQ = :internPlanId
            """, nativeQuery = true)
    ISummaryReportStudentResponse getDetailSummaryReport(@Param ("stdNo") String stdNo,
            @Param ("internPlanId") String internPlanId, @Param ("approveStatus") String approveStatus,
            @Param ("delYn") String delYn);

    Optional<SummaryReportStudentEntity> findByStdSummaryReportSeqAndDelYn(String summaryReportSeq, String delYn);

    @Query (value = """
                SELECT
                    COUNT(istd.STD_NO)
                FROM INTERN_STD istd 
                    JOIN INTERN_PLAN ipl ON ipl.INTERN_PLAN_SEQ = istd.INTERN_PLAN_SEQ
                WHERE istd.DEL_YN = :delYn 
                  AND ipl.DEL_YN = :delYn 
                  AND istd.APPROVE_STATUS = :stdApproveStatus
                  AND ipl.STATUS = :planApproveStatus
                  AND istd.INTERN_STD_SEQ = :stdId 
                  AND ipl.INTERN_PLAN_SEQ = :planId
            """, nativeQuery = true)
    Integer checkExistByPlanIdAndStudentIdAndDelYn(@Param ("planId") String planId, @Param ("stdId") String stdId,
            @Param ("stdApproveStatus") String stdApproveStatus, @Param ("planApproveStatus") String planApproveStatus,
            @Param ("delYn") String delYn);

}
