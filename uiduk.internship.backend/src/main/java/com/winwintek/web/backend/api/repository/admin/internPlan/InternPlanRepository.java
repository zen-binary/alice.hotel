package com.winwintek.web.backend.api.repository.admin.internPlan;

import com.winwintek.web.backend.api.dto.admin.internPlan.InternPlanSearchDto;
import com.winwintek.web.backend.api.entity.admin.internPlan.InternPlanDetailResponse;
import com.winwintek.web.backend.api.entity.admin.internPlan.InternPlanEntity;
import com.winwintek.web.backend.api.entity.admin.internPlan.InternPlanExistingResponse;
import com.winwintek.web.backend.api.entity.admin.internPlan.InternPlanResponse;
import com.winwintek.web.backend.api.entity.admin.internPlan.InternPlanTreeResponse;
import com.winwintek.web.backend.api.entity.admin.internPlanDetail.InternPlanDetailEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InternPlanRepository extends JpaRepository<InternPlanEntity, String> {

    @Query (value = """
            SELECT ROW_NUMBER() OVER(ORDER BY ip.INTERN_PLAN_SEQ) AS rowNumb,
                   ip.INTERN_PLAN_SEQ AS internPlanSeq,
                    ip.STATUS AS statusCd,
                   FN_GET_CODE_NAME(ip.STATUS) AS status,
                   ip.CONFIRM_DATE AS confirmDate,
                   cm.CORM_NAME_KR AS cormNameKr,
                   ip.INTERN_PLAN_NM AS internPlanNm,
                   ip.EXECUTION_COUNT AS executionCount, 
                   ip.REGISTER_STR AS registerStr,
                   ip.REGISTER_END AS registerEnd,
                   ip.INTERNSHIP_STR AS internShipStr,
                   ip.INTERNSHIP_END AS internShipEnd,
                   ip.REG_DATE AS regDate,
                   ip.REJECTION_REASON_DETAILS as rejectionReasonDetails
            FROM INTERN_PLAN ip
            JOIN CORPORATE_MEMBERS cm ON cm.CORM_SEQ = ip.CORM_SEQ
            WHERE ip.DEL_YN = :delYn     
            AND (:#{#dto.status} IS NULL OR :#{#dto.status} = '' OR  ip.STATUS = :#{#dto.status})
            AND (:#{#dto.internPlanNm} IS NULL OR :#{#dto.internPlanNm} = '' OR LOWER(ip.INTERN_PLAN_NM) LIKE '%' || LOWER(:#{#dto.internPlanNm}) || '%')
            AND (:#{#dto.registerStr} IS NULL OR TRUNC(ip.REGISTER_STR) >= TO_DATE(:#{#dto.registerStr}, 'YYYY-MM-DD'))
            AND (:#{#dto.registerEnd} IS NULL OR TRUNC(ip.REGISTER_END) <= TO_DATE(:#{#dto.registerEnd}, 'YYYY-MM-DD'))  
            AND (:#{#dto.internShipStr} IS NULL OR TRUNC(ip.INTERNSHIP_STR) >= TO_DATE(:#{#dto.internShipStr}, 'YYYY-MM-DD'))
            AND (:#{#dto.internShipEnd} IS NULL OR TRUNC(ip.INTERNSHIP_END) <= TO_DATE(:#{#dto.internShipEnd}, 'YYYY-MM-DD'))
            AND ip.REG_ID = :regId
            ORDER BY ip.INTERN_PLAN_SEQ DESC
            """, countQuery = """
            SELECT ip.INTERN_PLAN_SEQ
            FROM INTERN_PLAN ip
            JOIN CORPORATE_MEMBERS cm ON cm.CORM_SEQ = ip.CORM_SEQ
            WHERE ip.DEL_YN = :delYn     
            AND (:#{#dto.status} IS NULL OR :#{#dto.status} = '' OR  ip.STATUS = :#{#dto.status})
            AND (:#{#dto.internPlanNm} IS NULL OR :#{#dto.internPlanNm} LIKE '' OR LOWER(ip.INTERN_PLAN_NM) LIKE '%' || LOWER(:#{#dto.internPlanNm}) || '%')
            AND (:#{#dto.registerStr} IS NULL OR TRUNC(ip.REGISTER_STR) >= TO_DATE(:#{#dto.registerStr}, 'YYYY-MM-DD'))
            AND (:#{#dto.registerEnd} IS NULL OR TRUNC(ip.REGISTER_END) <= TO_DATE(:#{#dto.registerEnd}, 'YYYY-MM-DD'))  
            AND (:#{#dto.internShipStr} IS NULL OR TRUNC(ip.INTERNSHIP_STR) >= TO_DATE(:#{#dto.internShipStr}, 'YYYY-MM-DD'))
            AND (:#{#dto.internShipEnd} IS NULL OR TRUNC(ip.INTERNSHIP_END) <= TO_DATE(:#{#dto.internShipEnd}, 'YYYY-MM-DD'))
            AND ip.REG_ID = :regId
            """, nativeQuery = true)
    Page<InternPlanResponse> getListInternPlan(Pageable pageable, InternPlanSearchDto dto,
            @Param ("delYn") String delYn, @Param ("regId") String regId);

    @Query (value = """
            SELECT
            INTERN_PLAN_SEQ,
            REFER_INTERN_PLAN_SEQ,
            EXECUTION_COUNT,
            CONNECT_BY_ROOT INTERN_PLAN_SEQ AS TREE_ROOT
            FROM
            INTERN_PLAN
            WHERE DEL_YN = :delYn
            START WITH REFER_INTERN_PLAN_SEQ IS NULL
            CONNECT BY PRIOR INTERN_PLAN_SEQ = REFER_INTERN_PLAN_SEQ
            """, nativeQuery = true)
    List<InternPlanTreeResponse> getInternPlanTree(@Param ("delYn") String delYn);

    @Query (value = """
            SELECT
                MAX(EXECUTION_COUNT) AS MAX_EXECUTION_COUNT
            FROM (
                SELECT
                    INTERN_PLAN_SEQ,
                    REFER_INTERN_PLAN_SEQ,
                    EXECUTION_COUNT,
                    CONNECT_BY_ROOT INTERN_PLAN_SEQ AS TREE_ROOT
                FROM
                    INTERN_PLAN
                WHERE DEL_YN = :delYn
                START WITH REFER_INTERN_PLAN_SEQ = :detailId
                CONNECT BY PRIOR INTERN_PLAN_SEQ = REFER_INTERN_PLAN_SEQ
            )
            """, nativeQuery = true)
    Integer countInternPlanTreeById(@Param ("detailId") String detailId, @Param ("delYn") String delYn);

    @Query (value = """
            SELECT
                INTERN_PLAN_SEQ AS internPlanSeq,
                INTERN_PLAN_NM AS internPlanNm,
                REFER_INTERN_PLAN_SEQ AS referInternPlanSeq
            FROM
                INTERN_PLAN
            WHERE DEL_YN = :delYn
            AND REG_ID = :regId
                """, nativeQuery = true)
    List<InternPlanExistingResponse> getInternPlanExisting(@Param ("regId") String regId,
            @Param ("delYn") String delYn);

    @Query (value = """
            SELECT
                INTERN_PLAN_SEQ AS internPlanSeq,
                INTERN_PLAN_NM AS internPlanNm,
                REFER_INTERN_PLAN_SEQ AS referInternPlanSeq
            FROM
                INTERN_PLAN
            WHERE DEL_YN = :delYn
            AND REG_ID = :regId AND INTERN_PLAN_SEQ <> :internPlanSeq
                """, nativeQuery = true)
    List<InternPlanExistingResponse> getInternPlanExistingUpdate(@Param ("regId") String regId,
            @Param ("internPlanSeq") String internPlanSeq, @Param ("delYn") String delYn);

    @Query (value = """
                        SELECT
                               ip.INTERN_PLAN_SEQ AS internPlanSeq,
                               ip.INTERN_PLAN_NM AS internPlanNm,
                               ip.REFER_INTERN_PLAN_SEQ AS referInternPlanSeq,
                               ip.EXECUTION_COUNT AS executionCount,
                               cm.CORM_NAME_KR AS cormNameKr,
                               ip.COMM_SEQ AS commSeq,
                               cmm.MOBILE_PHONE AS mobilePhone,
                               ip.REGISTER_STR AS registerStr,
                               ip.REGISTER_END AS registerEnd,
                               ip.INTERNSHIP_STR AS internShipStr,
                               ip.INTERNSHIP_END AS internShipEnd,
                               ip.INTERN_RECRUITMENT_QUANTITY AS internRecruitmentQuantity,
                               ip.INTERNSHIP_FIELD AS internshipField,
                               ip.STAFF_NO AS staffNo,
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
                        WHERE ip.DEL_YN = :delYn
                        AND ip.INTERN_PLAN_SEQ = :seq
            """, nativeQuery = true)
    InternPlanDetailResponse getInternPlanById(@Param ("delYn") String delYn, @Param ("seq") String seq);

    List<InternPlanDetailEntity> findAllByInternPlanSeq(String internPlanSeq);

}
