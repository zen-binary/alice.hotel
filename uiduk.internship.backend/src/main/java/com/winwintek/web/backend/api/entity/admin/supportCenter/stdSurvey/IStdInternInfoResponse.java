package com.winwintek.web.backend.api.entity.admin.supportCenter.stdSurvey;

import java.sql.Clob;

public interface IStdInternInfoResponse {

    String getStdSurveyId();

    String getStdNm();

    String getShyrCd();

    String getShyrNm();

    String getTermCd();

    String getTermNm();

    Clob getRecommend();

}
