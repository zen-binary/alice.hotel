package com.winwintek.web.backend.api.repository.client.student.signedAgreement;

import com.winwintek.web.backend.api.entity.client.student.signedAgreement.ISignedAgreementOutput;
import com.winwintek.web.backend.api.entity.client.student.signedAgreement.TripartiteAgreementStdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SignedAgreementRepository extends JpaRepository<TripartiteAgreementStdEntity, String> {

    @Query (value = """
            SELECT
                ip.INTERN_PLAN_NM as internPlanName,
                ip.INTERN_YEAR AS internYear,
                FN_GET_CODE_NAME(ip.INTERN_TERM) AS internTerm,
                cm.CORM_SEQ as cormSeq,
                cm.CORM_NAME_KR AS cormName,
                ip.INTERNSHIP_STR as internshipStr,
                ip.INTERNSHIP_END as internshipEnd,
                ip.DURATION AS duration,
                ip.DURATION_HOUR_STR AS durationHourStr,
                ip.DURATION_MINUTE_STR AS durationMinuteStr,
                ip.DURATION_HOUR_END AS durationHourEnd,
                ip.DURATION_MINUTE_END AS durationMinuteEnd,
                ip.INTERNSHIP_WEEKDAY AS internshipWeekDay,
                ip.INTERNSHIP_DAY AS internshipDay,
                ip.PAYMENT_AMOUNT AS paymentAmount,
                FN_GET_CODE_NAME(ip.PAYMENT_FREQUENCY) AS paymentFrequency,
                FN_GET_CODE_NAME(ip.PAYMENT_TIMING_TYPE) AS paymentTimingType,
                ip.PAYMENT_DATE AS paymentDate,
                FN_GET_CODE_NAME(ip.EMPLOYMENT_CONTRACT_YN) AS employmentContract,
                FN_GET_CODE_NAME(ip.INSURANCE_PARTICIPATION_YN) AS insuranceParticipation,
                ta.CORPORATE_SIGNED_DATE AS corporateSignedDate,
                ta.TRIPARTITE_AGREEMENT_SEQ AS tripartiteSeq,
                ta.STD_SIGNED_DATE as stdSignedDate,
                ta.CORPORATE_SIGNED_YN AS corpSignedYn,
                ta.STD_SIGNED_YN AS stdSignedYn,
                ta.UNIV_SIGNED_YN AS univSignedYn,
                cmm.COMM_NM AS managerNm
            FROM INTERN_PLAN ip
                LEFT JOIN CORPORATE_MEMBERS cm ON (ip.CORM_SEQ = cm.CORM_SEQ AND cm.DEL_YN = :delYn)
                LEFT JOIN CORPORATE_MEMBERS_MANAGER cmm ON (cmm.COMM_SEQ = ip.COMM_SEQ AND cmm.DEL_YN = :delYn)
                LEFT JOIN INTERN_STD istd ON (ip.INTERN_PLAN_SEQ = istd.INTERN_PLAN_SEQ AND istd.DEL_YN = :delYn)
                LEFT JOIN TRIPARTITE_AGREEMENT ta ON (istd.INTERN_STD_SEQ = ta.INTERN_STD_SEQ AND ta.DEL_YN = :delYn)
            WHERE ip.DEL_YN = :delYn
              AND istd.STD_NO = :stdId
              AND istd.INTERN_STD_SEQ = :internStdSeq
              AND ta.CORPORATE_SIGNED_YN = :corporateSignedYn
            """, nativeQuery = true)
    ISignedAgreementOutput getSignedAgreement(@Param ("stdId") String stdId,
            @Param ("internStdSeq") String internStdSeq, @Param ("delYn") String delYn,
            @Param ("corporateSignedYn") String corporateSignedYn);

    Optional<TripartiteAgreementStdEntity> findByTripartiteAgreementSeqAndDelYn(String id, String delYn);


}
