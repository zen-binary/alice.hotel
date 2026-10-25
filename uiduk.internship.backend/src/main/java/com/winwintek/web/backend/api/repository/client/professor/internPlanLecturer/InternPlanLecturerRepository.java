package com.winwintek.web.backend.api.repository.client.professor.internPlanLecturer;

import com.winwintek.web.backend.api.entity.client.professor.internPlanLecturer.InternPlanDetailLecturerResponse;
import com.winwintek.web.backend.api.entity.client.professor.internPlanLecturer.InternPlanLecturerEntity;
import com.winwintek.web.backend.api.entity.client.professor.internPlanLecturer.InternPlanLecturerResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternPlanLecturerRepository extends JpaRepository<InternPlanLecturerEntity, String> {

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
                               vscs.DEPT_NM AS deptNm,
                               ip.INTERN_YEAR AS internYear,
                               FN_GET_CODE_NAME(ip.INTERN_TERM) AS internTerm,
                               ip.DURATION AS duration,
                               ip.DURATION_HOUR_STR AS durationHourStr,
                               ip.DURATION_HOUR_END AS durationHourEnd,
                               ip.DURATION_MINUTE_STR AS durationMinuteStr,
                               ip.DURATION_MINUTE_END AS durationMinuteEnd,
                               ip.INTERNSHIP_WEEKDAY AS internshipWeekday,
                               ip.INTERNSHIP_DAY AS internshipDay,
                               ip.PAYMENT_AMOUNT AS paymentAmount,
                               FN_GET_CODE_NAME(ip.PAYMENT_FREQUENCY) AS paymentFrequency,
                               FN_GET_CODE_NAME(ip.PAYMENT_TIMING_TYPE) AS paymentTimingType,
                               ip.PAYMENT_DATE AS paymentDate,
                               FN_GET_CODE_NAME(ip.EMPLOYMENT_CONTRACT_YN) AS employmentContractYn,
                               FN_GET_CODE_NAME(ip.INSURANCE_PARTICIPATION_YN) AS insuranceParticipationYn,
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
    Optional<InternPlanLecturerResponse> getInternPlanById(@Param ("delYn") String delYn, @Param ("seq") String seq);

    @Query (value = """
                        SELECT
                            INTERN_DETAIL_SEQ,
                            WEEK,
                            START_DATE,
                            END_DATE,
                            CONTENT,
                            DEL_YN
                        FROM INTERN_PLAN_DETAIL
                        WHERE INTERN_PLAN_SEQ = :seq
                        AND DEL_YN = :delYn
                        ORDER BY WEEK ASC
            """, nativeQuery = true)
    List<InternPlanDetailLecturerResponse> getListDetail(@Param ("seq") String seq, @Param ("delYn") String delYn);


}
