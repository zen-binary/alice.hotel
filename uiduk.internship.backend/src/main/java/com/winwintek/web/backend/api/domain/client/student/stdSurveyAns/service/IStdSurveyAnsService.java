package com.winwintek.web.backend.api.domain.client.student.stdSurveyAns.service;

import com.winwintek.web.backend.api.dto.client.student.stdSurveyAns.ListStdSurveyAnsDTO;

public interface IStdSurveyAnsService {

    Boolean create(ListStdSurveyAnsDTO dto);

    Boolean update(ListStdSurveyAnsDTO dto);

}
