package com.winwintek.web.backend.api.repository.admin.evaluationStd;

import com.winwintek.web.backend.api.entity.admin.evaluationStd.EvaluationStdDetailResponse;
import com.winwintek.web.backend.api.entity.admin.evaluationStd.EvaluationStdEntity;
import com.winwintek.web.backend.api.entity.admin.evaluationStd.EvaluationStdResponse;
import com.winwintek.web.backend.api.entity.admin.evaluationStd.InternshipEvaluatorResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationStdRepository extends JpaRepository<EvaluationStdEntity, String> {

    @Query (value = """
                SELECT ins.STD_NO, vs.KOR_NM, FN_GET_CODE_NAME(vs.COLG_CD) AS COLG_CD_NM, 
                FN_GET_CODE_NAME(vs.SUST_CD)  AS SUST_CD_NM, 
                FN_GET_CODE_NAME(vs.MJ_CD) AS mjCdNm, 
                TO_CHAR(ip.INTERNSHIP_STR, 'yyyy-MM-dd') AS INTERNSHIP_STR,                  
                TO_CHAR(ip.INTERNSHIP_END, 'yyyy-MM-dd') AS INTERNSHIP_END, 
                cm.CORM_NAME_KR
                FROM INTERN_PLAN ip
                JOIN INTERN_STD ins
                ON ins.INTERN_PLAN_SEQ = ip.INTERN_PLAN_SEQ
                JOIN V_STD_CDP_SREG vs
                ON vs.STD_NO = ins.STD_NO
                JOIN CORPORATE_MEMBERS cm
                ON cm.CORM_SEQ = ip.CORM_SEQ
                WHERE ip.INTERN_PLAN_SEQ = :seq
                AND ins.APPROVE_STATUS = :statusApprove
                AND ip.DEL_YN = :delYn
            """, nativeQuery = true)
    List<EvaluationStdResponse> getStudentInfo(@Param ("seq") String seq, @Param ("delYn") String delYn,
            @Param ("statusApprove") String statusApprove);

    @Query (value = """
            SELECT 
            crm.CORM_SEQ,
            crm.CORM_NAME_KR,
            cmm.COMM_NM AS commNm,
            cmm.COMM_DEPT AS commDept,
            cmm.POSITION_NAME AS positionName
            FROM INTERN_PLAN ip
            JOIN CORPORATE_MEMBERS crm ON crm.CORM_SEQ = ip.CORM_SEQ 
            JOIN CORPORATE_MEMBERS_MANAGER cmm
            ON cmm.COMM_SEQ = ip.COMM_SEQ
            WHERE INTERN_PLAN_SEQ = :seq
                """, nativeQuery = true)
    InternshipEvaluatorResponse getInternshipEvaluator(@Param ("seq") String seq);

    @Query (value = """
            SELECT 
            iss.INTERN_STD_SEQ,
            es.EVALUATION_STD_SEQ,
            es.EVALUATION_FEEDBACK,
            es.STUDENT_FEEDBACK,
            TO_CHAR(es.EVALUATION_SUBMIT_DATE, 'yyyy-MM-dd') AS EVALUATION_SUBMIT_DATE
            FROM EVALUATION_STD es RIGHT JOIN INTERN_STD iss 
            ON es.INTERN_STD_SEQ = iss.INTERN_STD_SEQ
            WHERE iss.STD_NO = :stdNo AND iss.INTERN_PLAN_SEQ = :internPlanSeq
            AND iss.APPROVE_STATUS = :approveStatus
            """, nativeQuery = true)
    EvaluationStdDetailResponse getEvaluationStdDetail(@Param ("stdNo") String stdNo,
            @Param ("internPlanSeq") String internPlanSeq, @Param ("approveStatus") String approveStatus);


}
