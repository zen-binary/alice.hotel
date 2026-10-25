package com.winwintek.web.backend.api.entity.admin.supportCenter.corpSurvey;

import java.sql.Clob;

public interface IPlanInternInfoResponse {

    String getCorpSurveyId();

    String getCorporateNm();

    Integer getNumberIntern();

    String getTermCd();

    String getTermNm();

    Clob getRecommend();

}
