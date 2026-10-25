package com.winwintek.web.backend.api.repository.admin.supportCenter.stdSurvey;

import com.winwintek.web.backend.api.entity.admin.supportCenter.stdSurvey.IStdInternInfoResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.stdSurvey.IStdSurveyQuestionResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.stdSurvey.StdSurveySupportCenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StdSurveySupportCenterRepository extends JpaRepository<StdSurveySupportCenterEntity, String> {

    @Query (value = """
                SELECT
                    iss.INTERN_STD_SURVEY_SEQ AS stdSurveyId,
                    vscs.KOR_NM AS stdNm,
                    vscs.SHYR_CD AS shyrCd,
                    FN_GET_CODE_NAME(vscs.SHYR_CD) AS shyrNm,
                    ipl.INTERN_TERM AS termCd,
                    FN_GET_CODE_NAME(ipl.INTERN_TERM) AS termNm,
                    iss.INTERN_RECOMMEND AS recommend
                FROM INTERN_STD istd
                        JOIN V_STD_CDP_SREG vscs ON vscs.STD_NO = istd.STD_NO
                        JOIN INTERN_PLAN ipl ON ipl.INTERN_PLAN_SEQ = istd.INTERN_PLAN_SEQ
                        LEFT JOIN INTERN_STD_SURVEY iss ON iss.INTERN_STD_SEQ = istd.INTERN_STD_SEQ
                WHERE istd.DEL_YN = :delYn
                    AND ipl.DEL_YN = :delYn
                    AND (iss.DEL_YN IS NULL OR iss.DEL_YN = :delYn)
                    AND istd.INTERN_STD_SEQ = :internStdId
            """, nativeQuery = true)
    IStdInternInfoResponse getPlanInfoByInternStdId(@Param ("internStdId") String internStdId,
            @Param ("delYn") String delYn);

    @Query (value = """
                SELECT
                    DISTINCT
                    ssq.STD_SURVEY_QUS_SEQ AS surveyQuestId,
                    ssq.QUS_CONTENT AS questContent,
                    ssq.DISPLAY_ORDER AS displayOrder,
                    ssa.SCORE AS score
                FROM
                    STD_SURVEY_QUS ssq
                LEFT JOIN STD_SURVEY_ANS ssa ON
                    ssa.STD_SURVEY_QUS_SEQ = ssq.STD_SURVEY_QUS_SEQ
                    AND (ssa.DEL_YN IS NULL OR ssa.DEL_YN = :delYn)
                    AND (ssa.INTERN_STD_SURVEY_SEQ IS NULL OR ssa.INTERN_STD_SURVEY_SEQ = :internSurveyId)
                WHERE
                    ssq.DEL_YN = :delYn
                ORDER BY
                    ssq.DISPLAY_ORDER
            """, nativeQuery = true)
    List<IStdSurveyQuestionResponse> getListSurveyQuestion(@Param ("internSurveyId") String internSurveyId,
            @Param ("delYn") String delYn);

}
