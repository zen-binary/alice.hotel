package com.winwintek.web.backend.api.domain.client.student.internStdSurvey.service;

import com.winwintek.web.backend.api.dto.client.student.internStdSurvey.InternStdSurveyCreDTO;
import com.winwintek.web.backend.api.dto.client.student.internStdSurvey.InternStdSurveyDetailReqDTO;

public interface IInternStdSurveyService {

    String create(InternStdSurveyCreDTO internStdSurveyCreDTO);

    String update(InternStdSurveyCreDTO internStdSurveyCreDTO);

    InternStdSurveyDetailReqDTO getDetailStdSurveyDetail(String seq);

}
