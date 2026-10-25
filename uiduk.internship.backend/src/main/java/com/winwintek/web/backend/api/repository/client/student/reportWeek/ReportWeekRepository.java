package com.winwintek.web.backend.api.repository.client.student.reportWeek;

import com.winwintek.web.backend.api.entity.client.student.reportWeek.*;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportWeekRepository extends JpaRepository<ReportWeekStudentEntity, String> {


    @Query (value = """
            SELECT
            	ipd.INTERN_DETAIL_SEQ as internDetailSeq,
            	TO_CHAR(ipd.START_DATE, 'yyyy-MM-dd') as internStartDate,
            	TO_CHAR(ipd.END_DATE, 'yyyy-MM-dd') as internEndDate,
            	ipd.WEEK as week
            FROM
            	INTERN_PLAN_DETAIL ipd
            WHERE
            	ipd.DEL_YN = :delYn
                AND ipd.INTERN_PLAN_SEQ = :internPlanId
    """, nativeQuery = true)
    List<IListWeekResponse> getListWeekClient(@Param ("internPlanId") String internPlanId, @Param ("delYn") String delYn);

    @Query (value = """
        SELECT 
            istd.INTERN_STD_SEQ AS internStdSeq,
            vscs.KOR_NM AS stdNm,
            vscd.DEPT_KOR_NM AS deptNm,
            FN_GET_CODE_NAME(vscs.SHYR_CD) AS stdYear,
            vscs.STD_NO AS stdNo,
            ip.CORM_SEQ AS cormSeq,
            istd.INTERNSHIP_STATUS AS internshipStatus
         FROM 
            INTERN_STD istd
            JOIN V_STD_CDP_SREG vscs ON vscs.STD_NO = istd.STD_NO AND istd.APPROVE_STATUS = :statusApprove
            LEFT JOIN V_STD_CDP_DEPT vscd ON vscd.DEPT_CD = vscs.SUST_CD
            JOIN INTERN_PLAN ip ON ip.INTERN_PLAN_SEQ = istd.INTERN_PLAN_SEQ
         WHERE 
            istd.DEL_YN = :delYn 
            AND vscs.STD_NO = :stdNo AND istd.INTERN_PLAN_SEQ = :internPlanSeq
    """, nativeQuery = true)
    IWeekReportStudentResponse getInfoStudentClient(@Param ("stdNo") String stdNo,
            @Param ("internPlanSeq") String internPlanSeq,@Param ("statusApprove") String statusApprove,
            @Param ("delYn") String delYn);

    List<ReportWeekStudentEntity> findAllByInternDetailSeqAndInternStdSeqAndDelYn(String internDetailId,
            String internStdId, String delYn);




}
