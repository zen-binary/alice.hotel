package com.winwintek.web.backend.api.repository.admin.supportCenter.corpSurvey;

import com.winwintek.web.backend.api.entity.admin.supportCenter.corpSurvey.CorpSurveySupportCenterEntity;
import com.winwintek.web.backend.api.entity.admin.supportCenter.corpSurvey.ICorpSurveyQuestionResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.corpSurvey.IPlanInternInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorpSurveySupportCenterRepository extends JpaRepository<CorpSurveySupportCenterEntity, String> {

    @Query (value = """
                SELECT
                    ics.INTERN_CORPORATE_SURVEY_SEQ AS corpSurveyId,
                    cm.CORM_NAME_KR AS corporateNm,
                    ipl.INTERN_RECRUITMENT_QUANTITY AS numberIntern,
                    ipl.INTERN_TERM AS termCd,
                    FN_GET_CODE_NAME(ipl.INTERN_TERM) AS termNm,
                    ics.INTERN_RECOMMEND AS recommend
                FROM INTERN_PLAN ipl
                        LEFT JOIN CORPORATE_MEMBERS cm ON cm.CORM_SEQ = ipl.CORM_SEQ
                        LEFT JOIN INTERN_CORPORATE_SURVEY ics ON 
                            ics.INTERN_PLAN_SEQ  = ipl.INTERN_PLAN_SEQ AND (ics.DEL_YN IS NULL OR ics.DEL_YN = :delYn)
                WHERE ipl.DEL_YN = :delYn
                    AND cm.DEL_YN = :delYn
                    AND ipl.INTERN_PLAN_SEQ = :internPlanId
            """, nativeQuery = true)
    IPlanInternInfoResponse getPlanInfoByPlanId(@Param ("internPlanId") String internPlanId,
            @Param ("delYn") String delYn);

    @Query (value = """
                SELECT
                    DISTINCT
                    csq.CORPORATE_SURVEY_QUS_SEQ AS surveyQuestId,
                    csq.QUS_CONTENT AS questContent,
                    csq.DISPLAY_ORDER AS displayOrder,
                    csa.SCORE AS score
                FROM
                    CORPORATE_SURVEY_QUS csq
                LEFT JOIN CORPORATE_SURVEY_ANS csa ON csa.CORPORATE_SURVEY_QUS_SEQ = csq.CORPORATE_SURVEY_QUS_SEQ
                    AND (csa.DEL_YN IS NULL OR csa.DEL_YN = :delYn)
                    AND (csa.INTERN_CORPORATE_SURVEY_SEQ IS NULL OR csa.INTERN_CORPORATE_SURVEY_SEQ = :internSurveyId)
                WHERE
                    csq.DEL_YN = :delYn
                ORDER BY
                    csq.DISPLAY_ORDER
            """, nativeQuery = true)
    List<ICorpSurveyQuestionResponse> getListSurveyQuestion(@Param ("internSurveyId") String internSurveyId,
            @Param ("delYn") String delYn);

}
