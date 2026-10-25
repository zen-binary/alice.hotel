package com.winwintek.web.backend.api.repository.admin.supportCenter.agreement;

import com.winwintek.web.backend.api.entity.admin.supportCenter.agreement.ISignedTripartiteResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.agreement.IStdInternSupportCenterResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.agreement.SignTripartiteAgreementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SignTripartiteRepository extends JpaRepository<SignTripartiteAgreementEntity, String> {

    @Query (value = """
            SELECT
                 FN_GET_CODE_NAME(ip.INTERN_TERM) AS internTerm,  
                ip.INTERN_YEAR AS internYear,   
                cm.CORM_NAME_KR AS cormNameKr, 
                ip.INTERNSHIP_STR AS internshipStr,  
                ip.INTERNSHIP_END AS internshipEnd,   
                ip.DURATION AS duration,   
                ip.DURATION_HOUR_STR AS durationHourStr,   
                ip.DURATION_MINUTE_STR AS durationMinuteStr,   
                ip.DURATION_HOUR_END AS durationHourEnd,   
                ip.DURATION_MINUTE_END AS durationMinuteEnd,   
                ip.INTERNSHIP_WEEKDAY AS internshipWeekDay, 
                ip.INTERNSHIP_DAY AS internshipDay, 
                ip.PAYMENT_AMOUNT AS paymentAmount,   
                ip.PAYMENT_FREQUENCY AS paymentFrequency,   
                ip.PAYMENT_TIMING_TYPE AS paymentTimingType,   
                ip.PAYMENT_DATE AS paymentDate,   
                FN_GET_CODE_NAME(ip.EMPLOYMENT_CONTRACT_YN) AS employmentContractYn,   
                FN_GET_CODE_NAME(ip.INSURANCE_PARTICIPATION_YN) AS insuranceParticipationYn,   
                ip.REG_DATE AS regDate,
                ip.INTERN_PLAN_NM AS interPlanNm,
                ip.CORM_SEQ as cormSeq,
                TO_CHAR(SYSDATE, 'yyyy-MM-dd') as uniSignedDate,
                cmm.COMM_NM AS managerNm
            FROM INTERN_PLAN ip
                LEFT JOIN CORPORATE_MEMBERS cm ON ip.CORM_SEQ = cm.CORM_SEQ
                LEFT JOIN CORPORATE_MEMBERS_MANAGER cmm ON ip.COMM_SEQ = cmm.COMM_SEQ
            WHERE ip.DEL_YN = :delYn 
              AND ip.STATUS = :planApproveStatus
              AND cm.DEL_YN = :delYn
              AND cm.CORM_STATUS = :corpApproveStatus
              AND cmm.DEL_YN = :delYn 
              AND ip.INTERN_PLAN_SEQ = :internPlanId
            """, nativeQuery = true)
    ISignedTripartiteResponse getListSignSupportCenter(@Param ("internPlanId") String internPlanId,
            @Param ("planApproveStatus") String planApproveStatus,
            @Param ("corpApproveStatus") String corpApproveStatus, @Param ("delYn") String delYn);

    @Query (value = """
                SELECT 
                    istd.INTERN_STD_SEQ AS id,
                    vscs.KOR_NM AS name,
                    ta.TRIPARTITE_AGREEMENT_SEQ AS tripartiteAgreementSeq,
                    ta.CORPORATE_SIGNED_YN AS corpSignedYn,
                    ta.STD_SIGNED_YN AS stdSignedYn,
                    ta.UNIV_SIGNED_YN AS univSignedYn, 
                    TO_CHAR(ta.UNIV_SIGNED_DATE, 'yyyy-MM-dd') AS uniSignedDate,
                    vscd.DEPT_KOR_NM AS deptStdNm
                FROM INTERN_STD istd 
                    JOIN TRIPARTITE_AGREEMENT ta ON 
                        (ta.INTERN_STD_SEQ = istd.INTERN_STD_SEQ AND ta.DEL_YN = :delYn 
                             AND ta.CORPORATE_SIGNED_YN = :corporateSignedYn AND ta.STD_SIGNED_YN = :stdSignedYn)
                    LEFT JOIN V_STD_CDP_SREG vscs ON vscs.STD_NO = istd.STD_NO
                    LEFT JOIN V_STD_CDP_DEPT vscd ON vscd.DEPT_CD = vscs.SUST_CD
                WHERE istd.DEL_YN = :delYn
                    AND istd.INTERN_PLAN_SEQ = :internPlanId
                    AND istd.APPROVE_STATUS = :approveStatus
                ORDER BY vscs.KOR_NM ASC
            """, nativeQuery = true)
    List<IStdInternSupportCenterResponse> getListStudentReportSupportCenter(@Param ("internPlanId") String internPlanId,
            @Param ("approveStatus") String approveStatus, @Param ("corporateSignedYn") String corporateSignedYn,
            @Param ("stdSignedYn") String stdSignedYn, @Param ("delYn") String delYn);

    Optional<SignTripartiteAgreementEntity> findByTripartiteAgreementSeqAndDelYn(String id, String delYn);

}
