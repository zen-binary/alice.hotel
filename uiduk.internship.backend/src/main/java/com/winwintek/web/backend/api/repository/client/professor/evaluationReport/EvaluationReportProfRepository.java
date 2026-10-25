package com.winwintek.web.backend.api.repository.client.professor.evaluationReport;

import com.winwintek.web.backend.api.entity.client.professor.evaluationReport.EvaluationReportProfEntity;
import com.winwintek.web.backend.api.entity.client.professor.evaluationReport.IEvaluationReportDetailResponse;
import com.winwintek.web.backend.api.entity.client.professor.evaluationReport.IStudentInfoEvaluationResponse;
import com.winwintek.web.backend.api.entity.client.professor.evaluationReport.summaryReport.ISummaryReportProfResponse;
import com.winwintek.web.backend.api.entity.client.professor.evaluationReport.weekReport.IPlanDetailProfResponse;
import com.winwintek.web.backend.api.entity.client.professor.evaluationReport.weekReport.IStudentInfoResponse;
import com.winwintek.web.backend.api.entity.client.professor.evaluationReport.weekReport.IWeekReportProfResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationReportProfRepository extends JpaRepository<EvaluationReportProfEntity, String> {

    @Query (value = """
                SELECT 
                    ipl.INTERN_PLAN_NM AS internPlanNm,
                    vscs.SUST_CD_NM AS deptStdNm,
                    cm.CORM_NAME_KR AS companyNm,
                    ipl.INTERNSHIP_STR AS internStartDate,
                    ipl.INTERNSHIP_END AS internEndDate,
                    vscs.KOR_NM AS stdNm,
                    vscsf.KOR_NM AS profNm,
                    vscsf.DEPT_NM AS deptProfNm,
                    SUM(tmp.SCORE) AS totalCompanyScore
                FROM INTERN_STD istd 
                        JOIN V_STD_CDP_SREG vscs ON vscs.STD_NO = istd.STD_NO
                        JOIN INTERN_PLAN ipl ON ipl.INTERN_PLAN_SEQ  = istd.INTERN_PLAN_SEQ
                        JOIN V_STD_CDP_STAF vscsf ON vscsf.STAFF_NO = ipl.STAFF_NO
                        JOIN CORPORATE_MEMBERS cm ON cm.CORM_SEQ = ipl.CORM_SEQ
                        LEFT JOIN (
                                SELECT 
                                    es.INTERN_STD_SEQ,
                                    se.SCORE
                                FROM 
                                    STUDENT_EVALUATION se 
                                        JOIN EVALUATION_STD es ON es.EVALUATION_STD_SEQ = se.EVALUATION_STD_SEQ
                                WHERE se.DEL_YN = :delYn
                                    AND es.DEL_YN = :delYn
                                    AND es.INTERN_STD_SEQ = :internStdId
                                ) tmp ON tmp.INTERN_STD_SEQ = istd.INTERN_STD_SEQ
                WHERE istd.DEL_YN = :delYn
                    AND ipl.DEL_YN = :delYn
                    AND cm.DEL_YN = :delYn
                    AND istd.INTERN_STD_SEQ = :internStdId
                GROUP BY
                    ipl.INTERN_PLAN_NM,
                    vscs.SUST_CD_NM,
                    cm.CORM_NAME_KR,
                    ipl.INTERNSHIP_STR,
                    ipl.INTERNSHIP_END,
                    vscs.KOR_NM,
                    vscsf.KOR_NM,
                    vscsf.DEPT_NM
            """, nativeQuery = true)
    IStudentInfoEvaluationResponse getStudentInfoByStdId(@Param ("internStdId") String internStdId,
            @Param ("delYn") String delYn);

    @Query (value = """
                SELECT
                    ipe.INTERN_PROCESS_EVALUATION_SEQ AS id,
                    ipe.WORKING_DEPARTMENT AS internWorkDept,
                    ipe.ATTENDANCE_STATUS AS attendanceStatus,
                    ipe.WEEK_REPORT AS weekReport,
                    ipe.SUMMARY_REPORT AS summaryReport,
                    ipe.GUIDANCE_REPORT AS guidanceReport
                FROM
                    INTERN_PROCESS_EVALUATION ipe
                WHERE ipe.DEL_YN = :delYn
                    AND ipe.INTERN_PLAN_SEQ = :internPlanId
                    AND ipe.INTERN_STD_SEQ = :internStdId
            """, nativeQuery = true)
    IEvaluationReportDetailResponse getDetailEvaluationReport(@Param ("internPlanId") String internPlanId,
            @Param ("internStdId") String internStdId, @Param ("delYn") String delYn);

    EvaluationReportProfEntity findByInternProcessEvaluationSeqAndDelYn(String evaluationSeq, String delYn);

    //    week report
    @Query (value = """
                SELECT
                    ipd.INTERN_DETAIL_SEQ AS internPlanDetailId,
                    ipd.WEEK AS internWeek,
                    TO_CHAR(ipd.START_DATE, 'yyyy-MM-dd') AS internStartDate,
                    TO_CHAR(ipd.END_DATE, 'yyyy-MM-dd') AS internEndDate
                FROM INTERN_PLAN_DETAIL ipd
                WHERE ipd.DEL_YN = :delYn
                    AND (:internPlanId IS NULL OR ipd.INTERN_PLAN_SEQ = :internPlanId)
                    AND (:internDetailId IS NULL OR ipd.INTERN_DETAIL_SEQ = :internDetailId)
            """, nativeQuery = true)
    List<IPlanDetailProfResponse> getPlanDetail(@Param ("internPlanId") String internPlanId,
            @Param ("internDetailId") String internDetailId, @Param ("delYn") String delYn);

    @Query (value = """
                SELECT 
                    vscs.KOR_NM AS stdNm,
                    vscs.SUST_CD AS deptCd,
                    vscd.DEPT_KOR_NM AS deptNm,
                    vscs.SHYR_CD AS shyrCd,
                    FN_GET_CODE_NAME(vscs.SHYR_CD) AS shyrNm,
                    vscs.STD_NO AS stdNo,
                    cm.CORM_SEQ AS corpId,
                    ipl.INTERNSHIP_DAY AS dayInWeek
                FROM INTERN_STD istd 
                    LEFT JOIN V_STD_CDP_SREG vscs ON vscs.STD_NO = istd.STD_NO
                    LEFT JOIN V_STD_CDP_DEPT vscd ON vscd.DEPT_CD = vscs.SUST_CD
                    LEFT JOIN INTERN_PLAN ipl ON (ipl.INTERN_PLAN_SEQ = istd.INTERN_PLAN_SEQ AND ipl.DEL_YN = :delYn)
                    LEFT JOIN CORPORATE_MEMBERS cm ON (cm.CORM_SEQ = ipl.CORM_SEQ AND cm.DEL_YN = :delYn)
                WHERE istd.DEL_YN = :delYn
                    AND istd.APPROVE_STATUS = :stdApproveStatus 
                    AND ipl.STATUS = :planApproveStatus
                    AND cm.CORM_STATUS = :corpApproveStatus
                    AND istd.INTERN_STD_SEQ = :internStdId
            """, nativeQuery = true)
    Optional<IStudentInfoResponse> getStudentInfo(@Param ("internStdId") String internStdId,
            @Param ("stdApproveStatus") String stdApproveStatus, @Param ("planApproveStatus") String planApproveStatus,
            @Param ("corpApproveStatus") String corpApproveStatus, @Param ("delYn") String delYn);

    @Query (value = """
                SELECT
                    swr.STD_WEEK_REPORT_SEQ AS stdWeekReportId,
                    swr.DATE_WEEK AS dateWeek,
                    swr.IN_HOUR AS inHour,
                    swr.IN_MINUTE AS inMinute,
                    swr.OUT_HOUR AS outHour,
                    swr.OUT_MINUTE AS outMinute,
                    swr.JOB_CONTENT AS jobContent,
                    swr.ACQUIRED_KNOWLEDGE AS acquiredKnowledge,
                    TO_CHAR(temp.ATTENDANCE_DATE, 'yyyy-MM-dd') AS attendanceDate,
                    (CASE 
                        WHEN 
                            (temp.STATUS = :absenceStatus OR temp.STATUS = :holidayStatus) THEN 'Y' ELSE 'N' 
                        END
                    ) AS isDisabled
                FROM
                    (
                        SELECT
                            sa.STD_ATTENDANCE_SEQ,
                            sa.ATTENDANCE_DATE,
                            sa.INTERN_STD_SEQ,
                            sa.STATUS
                        FROM
                            STD_ATTENDANCE sa
                        WHERE sa.DEL_YN = :delYn
                            AND sa.INTERN_DETAIL_SEQ = :internDetailId
                            AND sa.INTERN_STD_SEQ = :internStdId
                    ) temp 
                        LEFT JOIN STD_WEEK_REPORT swr ON (swr.DATE_WEEK = temp.ATTENDANCE_DATE AND swr.INTERN_STD_SEQ = temp.INTERN_STD_SEQ)
                WHERE swr.DATE_WEEK IS NULL 
                   OR swr.DEL_YN = :delYn
            """, nativeQuery = true)
    List<IWeekReportProfResponse> getDetailWeekReport(@Param ("internDetailId") String internDetailId,
            @Param ("internStdId") String internStdId, @Param ("absenceStatus") String absenceStatus,
            @Param ("holidayStatus") String holidayStatus, @Param ("delYn") String delYn);

    //    summary report
    @Query (value = """
                SELECT
                    istd.INTERN_STD_SEQ AS internStdId,
                    ssr.STD_SUMMARY_REPORT_SEQ AS stdSummaryReportId,
                    vscs.KOR_NM AS stdNm,
                    vscd.DEPT_KOR_NM AS deptNm,
                    FN_GET_CODE_NAME(vscs.SHYR_CD) AS stdYear,
                    vscs.STD_NO AS stdNo,
                    ssr.CONTENT AS content
                FROM
                    INTERN_STD istd
                        JOIN V_STD_CDP_SREG vscs ON vscs.STD_NO = istd.STD_NO
                        LEFT JOIN V_STD_CDP_DEPT vscd ON vscd.DEPT_CD = vscs.SUST_CD
                        LEFT JOIN STD_SUMMARY_REPORT ssr ON ssr.INTERN_STD_SEQ = istd.INTERN_STD_SEQ
                WHERE istd.DEL_YN = :delYn
                    AND (ssr.DEL_YN IS NULL OR ssr.DEL_YN = :delYn)
                    AND istd.INTERN_STD_SEQ = :internStdId
            """, nativeQuery = true)
    ISummaryReportProfResponse getDetailSummaryReport(@Param ("internStdId") String internStdId,
            @Param ("delYn") String delYn);

}
