package com.winwintek.web.backend.api.repository.client.student.internshipRegistration;

import com.winwintek.web.backend.api.dto.client.student.internshipRegistration.InternshipRegReqDTO;
import com.winwintek.web.backend.api.entity.client.student.internshipRegistration.DetailInternPlanRegFormOutput;
import com.winwintek.web.backend.api.entity.client.student.internshipRegistration.IInternRegOutput;
import com.winwintek.web.backend.api.entity.client.student.internshipRegistration.InternshipPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternRegRepository extends JpaRepository<InternshipPlanEntity, String> {

    @Query (value = """
            SELECT 
                IP.INTERN_PLAN_SEQ AS internPlanSeq,
                IP.INTERN_PLAN_NM AS internshipName,
                CM.CORM_NAME_KR AS corpName,
                IP.INTERN_RECRUITMENT_QUANTITY AS quantity,
                IP.INTERNSHIP_FIELD AS internshipField,
                CM.ADDRESS AS address,
                IP.REGISTER_STR AS startDate,
                IP.REGISTER_END AS endDate,
                IP.CORM_SEQ AS cormSeq,
                CASE
                    WHEN TRUNC(CURRENT_DATE) < TRUNC(IP.REGISTER_STR) THEN 'wait'
                    WHEN (TRUNC(CURRENT_DATE) BETWEEN TRUNC(IP.REGISTER_STR) AND TRUNC(IP.REGISTER_END)) AND
                         (ISTD.TOTAL_INTERN IS NULL OR ISTD.TOTAL_INTERN < IP.INTERN_RECRUITMENT_QUANTITY) THEN 'accept'
                    WHEN TRUNC(CURRENT_DATE) > TRUNC(IP.REGISTER_END) THEN 'complete'
                    ELSE 'complete'
                END AS receptionStatus
            FROM INTERN_PLAN IP
                LEFT JOIN CORPORATE_MEMBERS CM ON IP.CORM_SEQ = CM.CORM_SEQ
                LEFT JOIN (
                    SELECT
                       INTERN_PLAN_SEQ,
                       COUNT(INTERN_STD_SEQ) AS TOTAL_INTERN
                    FROM INTERN_STD
                    WHERE APPROVE_STATUS = :stdApproveStatus 
                        AND DEL_YN = :delYn
                    GROUP BY INTERN_PLAN_SEQ
               ) ISTD ON ISTD.INTERN_PLAN_SEQ = IP.INTERN_PLAN_SEQ
            WHERE
                IP.DEL_YN = :delYn
                AND IP.STATUS = :acceptedStatus
                AND (:#{#req.selectedYear} IS NULL OR IP.INTERN_YEAR = :#{#req.selectedYear})
                AND (
                    :#{#req.receptionStatus} IS NULL
                    OR (:#{#req.receptionStatus} = 'wait' AND TRUNC(CURRENT_DATE) < TRUNC(IP.REGISTER_STR))
                    OR (:#{#req.receptionStatus} = 'accept' 
                            AND (TRUNC(CURRENT_DATE) BETWEEN TRUNC(IP.REGISTER_STR) AND TRUNC(IP.REGISTER_END)) 
                            AND (ISTD.TOTAL_INTERN IS NULL OR ISTD.TOTAL_INTERN < IP.INTERN_RECRUITMENT_QUANTITY))
                    OR (:#{#req.receptionStatus} = 'complete' AND TRUNC(CURRENT_DATE) > TRUNC(IP.REGISTER_END))
                )
                AND (:#{#req.internshipName} IS NULL
                    OR LOWER(IP.INTERN_PLAN_NM) LIKE LOWER('%' || :#{#req.internshipName} || '%'))
                AND IP.INTERN_PLAN_SEQ NOT IN 
                    (SELECT 
                         ip.INTERN_PLAN_SEQ AS internPlanSeq
                    FROM INTERN_PLAN ip 
                        LEFT JOIN INTERN_STD id ON ip.INTERN_PLAN_SEQ = id.INTERN_PLAN_SEQ
                    WHERE id.APPROVE_STATUS <> :cancelStatus 
                        AND id.STD_NO = :stdNo
                    GROUP BY ip.INTERN_PLAN_SEQ 
                    HAVING COUNT(id.INTERN_STD_SEQ) > 0
                    )
            ORDER BY
                EXTRACT(YEAR FROM IP.REGISTER_STR) DESC,
                IP.INTERN_PLAN_SEQ
            """, nativeQuery = true)
    List<IInternRegOutput> getInternPlanRegList(@Param ("req") InternshipRegReqDTO req,
            @Param ("acceptedStatus") String acceptedStatus,@Param ("stdApproveStatus") String stdApproveStatus,
            @Param("cancelStatus") String cancelStatus, @Param ("delYn") String delYn,
            @Param("stdNo") String stdNo);

    @Query (value = """
                SELECT DISTINCT
                    EXTRACT(YEAR FROM ip.INTERNSHIP_STR) AS year
                FROM INTERN_PLAN ip
                WHERE
                    ip.DEL_YN= :delYn
                ORDER BY year DESC
            """, nativeQuery = true)
    List<String> getListYear(@Param ("delYn") String delYn);

    @Query (value = """
                WITH DayCodes AS (
                    SELECT
                        ip.INTERN_PLAN_SEQ,
                        TRIM(REGEXP_SUBSTR(ip.INTERNSHIP_DAY, '[^,]+', 1, LEVEL)) AS DAY_CODE
                    FROM
                        INTERN_PLAN ip
                    WHERE
                        ip.DEL_YN = :delYn
                        AND ip.STATUS = :acceptedStatus
                        AND ip.INTERN_PLAN_SEQ = :internSeq
                    CONNECT BY LEVEL <= LENGTH(REGEXP_REPLACE(ip.INTERNSHIP_DAY, '[^,]', '')) + 1
                    AND PRIOR INTERN_PLAN_SEQ = INTERN_PLAN_SEQ
                    AND PRIOR DBMS_RANDOM.VALUE IS NOT NULL
                ),
                InternPlanData AS (
                    SELECT
                        ip.INTERN_PLAN_SEQ AS internPlanSeq,
                        ip.INTERN_PLAN_NM AS internPlanName,
                        ref_ip.INTERN_PLAN_NM AS referInternPlanName,
                        ip.EXECUTION_COUNT as executionCount,
                        cm.CORM_NAME_KR AS cormName,
                        cmm.COMM_NM AS commName,
                        cmm.MOBILE_PHONE AS telNo,
                        ip.REGISTER_STR AS registerStr,
                        ip.REGISTER_END AS registerEnd,
                        ip.INTERNSHIP_STR AS internshipStr,
                        ip.INTERNSHIP_END AS internshipEnd,
                        ip.INTERN_RECRUITMENT_QUANTITY AS quantity,
                        ip.INTERNSHIP_FIELD AS internshipField,
                        VSCS.KOR_NM AS staffName,
                        ip.INTERN_YEAR AS internYear,
                        FN_GET_CODE_NAME(ip.INTERN_TERM) AS internTerm,
                        ip.DURATION AS duration,
                        ip.DURATION_HOUR_STR AS durationHourStr,
                        ip.DURATION_MINUTE_STR AS durationMinuteStr,
                        ip.DURATION_HOUR_END AS durationHourEnd,
                        ip.DURATION_MINUTE_END AS durationMinuteEnd,
                        ip.INTERNSHIP_WEEKDAY AS internshipWeekday,
                        LISTAGG(FN_GET_CODE_NAME(DayCodes.DAY_CODE), ', ') WITHIN GROUP (ORDER BY DayCodes.DAY_CODE) AS internshipDay,
                        ip.PAYMENT_AMOUNT AS paymentAmount,
                        FN_GET_CODE_NAME(ip.PAYMENT_FREQUENCY) AS paymentFrequency,
                        FN_GET_CODE_NAME(ip.PAYMENT_TIMING_TYPE) AS paymentTimingType,
                        ip.PAYMENT_DATE AS paymentDate,
                        ip.EMPLOYMENT_CONTRACT_YN AS employmentContractYn,
                        ip.INSURANCE_PARTICIPATION_YN AS insuranceParticipationYn,
                        CASE
                            WHEN (TRUNC(CURRENT_DATE) BETWEEN TRUNC(ip.REGISTER_STR) AND TRUNC(ip.REGISTER_END)) AND
                                 (istd.TOTAL_INTERN IS NULL OR istd.TOTAL_INTERN < ip.INTERN_RECRUITMENT_QUANTITY) THEN 'N'
                            ELSE 'Y'
                        END AS isDisabled
                    FROM
                        INTERN_PLAN ip
                    LEFT JOIN (
                        SELECT
                            INTERN_PLAN_SEQ,
                            COUNT(INTERN_STD_SEQ) AS TOTAL_INTERN
                        FROM INTERN_STD
                        WHERE APPROVE_STATUS = :stdApproveStatus 
                            AND DEL_YN = :delYn
                        GROUP BY INTERN_PLAN_SEQ
                       ) istd ON istd.INTERN_PLAN_SEQ = ip.INTERN_PLAN_SEQ
                    LEFT JOIN
                        INTERN_PLAN ref_ip ON ip.REFER_INTERN_PLAN_SEQ = ref_ip.INTERN_PLAN_SEQ
                    LEFT JOIN
                        CORPORATE_MEMBERS cm ON ip.CORM_SEQ = cm.CORM_SEQ
                    LEFT JOIN
                        CORPORATE_MEMBERS_MANAGER cmm ON ip.COMM_SEQ = cmm.COMM_SEQ
                    LEFT JOIN
                        V_STD_CDP_STAF VSCS ON ip.STAFF_NO = VSCS.STAFF_NO
                    LEFT JOIN
                        DayCodes ON ip.INTERN_PLAN_SEQ = DayCodes.INTERN_PLAN_SEQ
                    WHERE
                        ip.DEL_YN = :delYn
                        AND ip.STATUS = :acceptedStatus
                        AND ip.INTERN_PLAN_SEQ = :internSeq
                    GROUP BY
                        ip.INTERN_PLAN_SEQ,
                        ip.INTERN_PLAN_NM,
                        ref_ip.INTERN_PLAN_NM,
                        ip.EXECUTION_COUNT,
                        cm.CORM_NAME_KR,
                        cmm.COMM_NM,
                        cmm.MOBILE_PHONE,
                        ip.REGISTER_STR,
                        ip.REGISTER_END,
                        ip.INTERNSHIP_STR,
                        ip.INTERNSHIP_END,
                        ip.INTERN_RECRUITMENT_QUANTITY,
                        ip.INTERNSHIP_FIELD,
                        VSCS.KOR_NM,
                        ip.INTERN_YEAR,
                        FN_GET_CODE_NAME(ip.INTERN_TERM),
                        ip.DURATION,
                        ip.DURATION_HOUR_STR,
                        ip.DURATION_MINUTE_STR,
                        ip.DURATION_HOUR_END,
                        ip.DURATION_MINUTE_END,
                        ip.INTERNSHIP_WEEKDAY,
                        ip.PAYMENT_AMOUNT,
                        FN_GET_CODE_NAME(ip.PAYMENT_FREQUENCY),
                        FN_GET_CODE_NAME(ip.PAYMENT_TIMING_TYPE),
                        ip.PAYMENT_DATE,
                        ip.EMPLOYMENT_CONTRACT_YN,
                        ip.INSURANCE_PARTICIPATION_YN,
                        istd.TOTAL_INTERN
                )
                SELECT
                    InternPlanData.*,
                    ip.INTERN_JOB_SUMMARY AS internJobSummary,
                    ip.REQUIREMENTS AS requirements
                FROM
                    InternPlanData
                JOIN
                    INTERN_PLAN ip ON InternPlanData.internPlanSeq = ip.INTERN_PLAN_SEQ
            """, nativeQuery = true)
    DetailInternPlanRegFormOutput getDetailInternPlanRegForm(@Param ("internSeq") String internSeq,
            @Param ("acceptedStatus") String acceptedStatus, @Param ("stdApproveStatus") String stdApproveStatus,
            @Param ("delYn") String delYn);

}
