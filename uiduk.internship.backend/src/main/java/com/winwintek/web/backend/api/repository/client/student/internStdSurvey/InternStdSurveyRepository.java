package com.winwintek.web.backend.api.repository.client.student.internStdSurvey;

import com.winwintek.web.backend.api.dto.client.student.internStdSurvey.IInternStdSurveyCusDTO;
import com.winwintek.web.backend.api.dto.client.student.internStdSurvey.IStdSurveyAnsCusDTO;
import com.winwintek.web.backend.api.entity.client.student.internStdSurvey.InternStdSurveyEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternStdSurveyRepository extends JpaRepository<InternStdSurveyEntity, String> {

    @Query (value = """
            SELECT
                FN_GET_NAME_KR(std.STD_NO) AS studentName,
                vscs.SHYR_CD AS shyrCode,
                ip.INTERN_TERM AS internTerm,
                ip.INTERN_PLAN_SEQ AS internPlanSeq,
                std.INTERN_STD_SEQ AS internStdSeq,
                iss.INTERN_RECOMMEND AS internRecommend,
                iss.INTERN_STD_SURVEY_SEQ AS internStdSurveySeq
            FROM INTERN_STD std
                 LEFT JOIN V_STD_CDP_SREG vscs ON vscs.STD_NO = std.STD_NO
                 LEFT JOIN INTERN_PLAN ip ON ip.INTERN_PLAN_SEQ = std.INTERN_PLAN_SEQ
                 LEFT JOIN INTERN_STD_SURVEY iss ON (iss.INTERN_STD_SEQ = std.INTERN_STD_SEQ AND iss.DEL_YN = :delYn)
            WHERE std.DEL_YN = :delYn
                AND std.APPROVE_STATUS = :stdApproveStatus
                AND ip.DEL_YN = :delYn
                AND ip.STATUS = :planApproveStatus
                AND vscs.STD_NO = :stdNo
                AND ip.INTERN_PLAN_SEQ = :seq
            """, nativeQuery = true)
    IInternStdSurveyCusDTO getInternStdDetail(@Param ("seq") String seq, @Param ("stdNo") String stdNo,
            @Param ("stdApproveStatus") String stdApproveStatus, @Param ("planApproveStatus") String planApproveStatus,
            @Param ("delYn") String delYn);

    @Query (value = """
            SELECT
                ssq.QUS_CONTENT,
                ssq.DISPLAY_ORDER,
                ssq.STD_SURVEY_QUS_SEQ,
                ssa.STD_SURVEY_ANS_SEQ,
                ssa.SCORE
            FROM UU_INTERN.STD_SURVEY_QUS ssq
            LEFT JOIN UU_INTERN.STD_SURVEY_ANS ssa
                ON ssq.STD_SURVEY_QUS_SEQ = ssa.STD_SURVEY_QUS_SEQ
                AND ssa.INTERN_STD_SURVEY_SEQ = :seq
            """, nativeQuery = true)
    List<IStdSurveyAnsCusDTO> getStdSurveyAnsDetail(@Param ("seq") String seq);

}

