package com.winwintek.web.backend.api.repository.admin.supportCenter.evaluationReportCenter;

import com.winwintek.web.backend.api.entity.admin.supportCenter.evaluationReportCenter.EvaluationReportCenterEntity;
import com.winwintek.web.backend.api.entity.admin.supportCenter.evaluationReportCenter.IEvaluationReportDetailCenterResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.evaluationReportCenter.IStudentInfoEvaluationCenterResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EvaluationReportCenterRepository extends JpaRepository<EvaluationReportCenterEntity, String> {

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
                FROM
                	INTERN_STD istd
                LEFT JOIN V_STD_CDP_SREG vscs ON
                	vscs.STD_NO = istd.STD_NO
                LEFT JOIN INTERN_PLAN ipl ON
                	ipl.INTERN_PLAN_SEQ = istd.INTERN_PLAN_SEQ
                LEFT JOIN V_STD_CDP_STAF vscsf ON
                	vscsf.STAFF_NO = ipl.STAFF_NO
                LEFT JOIN CORPORATE_MEMBERS cm ON
                	cm.CORM_SEQ = ipl.CORM_SEQ
                LEFT JOIN (
                	SELECT
                		es.INTERN_STD_SEQ,
                		se.SCORE
                	FROM
                		STUDENT_EVALUATION se
                	JOIN EVALUATION_STD es ON
                		es.EVALUATION_STD_SEQ = se.EVALUATION_STD_SEQ
                	WHERE
                		se.DEL_YN = :delYn
                		AND es.DEL_YN = :delYn
                		AND es.INTERN_STD_SEQ = :internStdId
                                            ) tmp ON
                	tmp.INTERN_STD_SEQ = istd.INTERN_STD_SEQ
                WHERE
                	istd.DEL_YN = :delYn
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
    Optional<IStudentInfoEvaluationCenterResponse> getStudentInfoByStdId(@Param ("internStdId") String internStdId,
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
                    JOIN INTERN_PLAN ipl ON
                	ipl.INTERN_PLAN_SEQ = ipe.INTERN_PLAN_SEQ
                WHERE
                	ipe.DEL_YN = :delYn
                	AND ipl.DEL_YN = :delYn
                	AND ipe.INTERN_PLAN_SEQ = :internPlanId
                	AND ipe.INTERN_STD_SEQ = :internStdId
            """, nativeQuery = true)
    Optional<IEvaluationReportDetailCenterResponse> getDetailEvaluationReport(@Param ("internPlanId") String internPlanId,
            @Param ("internStdId") String internStdId, @Param ("delYn") String delYn);

    Optional<EvaluationReportCenterEntity> findByInternProcessEvaluationSeqAndDelYn(String evaluationSeq, String delYn);

}
