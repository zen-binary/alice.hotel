package com.winwintek.web.backend.api.repository.admin.internPlanCenter;

import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterSearchDto;
import com.winwintek.web.backend.api.entity.admin.internPlanCenter.InternPlanCenterDetailResponse;
import com.winwintek.web.backend.api.entity.admin.internPlanCenter.InternPlanCenterMng;
import com.winwintek.web.backend.api.entity.admin.internPlanCenter.InternPlanCenterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InternPlanCenterRepository extends JpaRepository<InternPlanCenterMng, String> {
    @Query (value = """
            SELECT
                ROW_NUMBER() OVER (ORDER BY ip.INTERN_PLAN_SEQ asc) AS rowNumb,
                ip.INTERN_PLAN_SEQ AS internPlanSeq,
                FN_GET_CODE_NAME(ip.STATUS) AS status,
                ip.STATUS AS statusCd,
                cm.CORM_SEQ,
                ip.CONFIRM_DATE AS confirmDate,
                cm.CORM_NAME_KR AS cormNameKr,
                ip.INTERN_PLAN_NM AS internPlanNm,
                ip.EXECUTION_COUNT AS executionCount,
                ip.REGISTER_STR AS registerStr,
                ip.REGISTER_END AS registerEnd,
                ip.INTERNSHIP_STR AS internShipStr,
                ip.INTERNSHIP_END AS internShipEnd,
                ip.REG_DATE AS regDate,
                ip.INTERN_RECRUITMENT_QUANTITY AS internRecruit,
                COALESCE(internApplyRecruit.count, 0) AS internApplyRecruit,
                ip.REJECTION_REASON_DETAILS AS rejectionReasonDetails
            FROM
                INTERN_PLAN ip
            JOIN
                CORPORATE_MEMBERS cm ON cm.CORM_SEQ = ip.CORM_SEQ
            LEFT JOIN (
                SELECT
                    INTERN_PLAN_SEQ,
                    COUNT(*) AS count
                FROM
                    INTERN_STD iss
                    WHERE iss.DEL_YN = :delYn
                GROUP BY
                    iss.INTERN_PLAN_SEQ
            ) internApplyRecruit ON internApplyRecruit.INTERN_PLAN_SEQ = ip.INTERN_PLAN_SEQ
            WHERE ip.DEL_YN = :delYn AND cm.DEL_YN = :delYn    
            AND (:#{#dto.status} IS NULL OR :#{#dto.status} = '' OR  ip.STATUS = :#{#dto.status})
            AND (:#{#dto.internPlanNm} IS NULL OR :#{#dto.internPlanNm} = '' OR LOWER(ip.INTERN_PLAN_NM) LIKE '%' || LOWER(:#{#dto.internPlanNm}) || '%')
            AND (:#{#dto.registerStr} IS NULL OR TRUNC(ip.REGISTER_STR) >= TO_DATE(:#{#dto.registerStr}, 'YYYY-MM-DD'))
            AND (:#{#dto.registerEnd} IS NULL OR TRUNC(ip.REGISTER_END) <= TO_DATE(:#{#dto.registerEnd}, 'YYYY-MM-DD'))  
            AND (:#{#dto.internShipStr} IS NULL OR TRUNC(ip.INTERNSHIP_STR) >= TO_DATE(:#{#dto.internShipStr}, 'YYYY-MM-DD'))
            AND (:#{#dto.internShipEnd} IS NULL OR TRUNC(ip.INTERNSHIP_END) <= TO_DATE(:#{#dto.internShipEnd}, 'YYYY-MM-DD'))
            ORDER BY ip.INTERN_PLAN_SEQ DESC
            """, countQuery = """
            SELECT COUNT(ip.INTERN_PLAN_SEQ)
            FROM INTERN_PLAN ip
            JOIN CORPORATE_MEMBERS cm ON cm.CORM_SEQ = ip.CORM_SEQ
            WHERE ip.DEL_YN = :delYn AND cm.DEL_YN = :delYn  
            AND (:#{#dto.status} IS NULL OR :#{#dto.status} = '' OR  ip.STATUS = :#{#dto.status})
            AND (:#{#dto.internPlanNm} IS NULL OR :#{#dto.internPlanNm} = '' OR LOWER(ip.INTERN_PLAN_NM) LIKE '%' || LOWER(:#{#dto.internPlanNm}) || '%')
            AND (:#{#dto.registerStr} IS NULL OR TRUNC(ip.REGISTER_STR) >= TO_DATE(:#{#dto.registerStr}, 'YYYY-MM-DD'))
            AND (:#{#dto.registerEnd} IS NULL OR TRUNC(ip.REGISTER_END) <= TO_DATE(:#{#dto.registerEnd}, 'YYYY-MM-DD'))  
            AND (:#{#dto.internShipStr} IS NULL OR TRUNC(ip.INTERNSHIP_STR) >= TO_DATE(:#{#dto.internShipStr}, 'YYYY-MM-DD'))
            AND (:#{#dto.internShipEnd} IS NULL OR TRUNC(ip.INTERNSHIP_END) <= TO_DATE(:#{#dto.internShipEnd}, 'YYYY-MM-DD'))
            """, nativeQuery = true)
    Page<InternPlanCenterResponse> getListInternPlanCenter(Pageable pageable, InternPlanCenterSearchDto dto,
            @Param ("delYn") String delYn);

    @Query (value = """
                        SELECT
                               ip.INTERN_PLAN_SEQ AS internPlanSeq,
                               ip.INTERN_PLAN_NM AS internPlanNm,
                               ip.REFER_INTERN_PLAN_SEQ AS referInternPlanSeq,
                               referInternPlan.INTERN_PLAN_NM AS referInternPlanNm,
                               ip.EXECUTION_COUNT AS executionCount,
                               cm.CORM_NAME_KR AS cormNameKr,
                               ip.COMM_SEQ AS commSeq,
                               cmm.COMM_NM AS commNm,
                               cmm.MOBILE_PHONE AS mobilePhone,
                               ip.REGISTER_STR AS registerStr,
                               ip.REGISTER_END AS registerEnd,
                               ip.INTERNSHIP_STR AS internShipStr,
                               ip.INTERNSHIP_END AS internShipEnd,
                               ip.INTERN_RECRUITMENT_QUANTITY AS internRecruitmentQuantity,
                               ip.INTERNSHIP_FIELD AS internshipField,
                               ip.STAFF_NO AS staffNo,
                               vscs.KOR_NM AS staffNm,
                               ip.INTERN_YEAR AS internYear,
                               ip.INTERN_TERM AS internTerm,
                               ip.DURATION AS duration,
                               ip.DURATION_HOUR_STR AS durationHourStr,
                               ip.DURATION_HOUR_END AS durationHourEnd,
                               ip.DURATION_MINUTE_STR AS durationMinuteStr,
                               ip.DURATION_MINUTE_END AS durationMinuteEnd,
                               ip.INTERNSHIP_WEEKDAY AS internshipWeekday,
                               ip.INTERNSHIP_DAY AS internshipDay,
                               ip.PAYMENT_AMOUNT AS paymentAmount,
                               ip.PAYMENT_FREQUENCY AS paymentFrequency,
                               ip.PAYMENT_TIMING_TYPE AS paymentTimingType,
                               ip.PAYMENT_DATE AS paymentDate,
                               ip.EMPLOYMENT_CONTRACT_YN AS employmentContractYn,
                               ip.INSURANCE_PARTICIPATION_YN AS insuranceParticipationYn,
                               ip.INTERN_JOB_SUMMARY AS internJobSummary,
                               ip.REQUIREMENTS AS requirements,
                               ip.STATUS AS status
                        FROM INTERN_PLAN ip
                        JOIN CORPORATE_MEMBERS cm 
                        ON cm.CORM_SEQ = ip.CORM_SEQ
                        LEFT JOIN CORPORATE_MEMBERS_MANAGER cmm 
                        ON cmm.COMM_SEQ = ip.COMM_SEQ
                        LEFT JOIN V_STD_CDP_STAF vscs
                        ON vscs.STAFF_NO = ip.STAFF_NO
                        LEFT JOIN (
                        	SELECT
                        		INTERN_PLAN_SEQ,
                        		INTERN_PLAN_NM,
                        		REFER_INTERN_PLAN_SEQ
                        		FROM
                        		INTERN_PLAN
                        		WHERE DEL_YN = :delYn) referInternPlan
                        	ON referInternPlan.INTERN_PLAN_SEQ = ip.REFER_INTERN_PLAN_SEQ
                        WHERE ip.DEL_YN = :delYn
                        AND ip.INTERN_PLAN_SEQ = :seq
            """, nativeQuery = true)
    InternPlanCenterDetailResponse getInternPlanById(@Param ("delYn") String delYn, @Param ("seq") String seq);

}
