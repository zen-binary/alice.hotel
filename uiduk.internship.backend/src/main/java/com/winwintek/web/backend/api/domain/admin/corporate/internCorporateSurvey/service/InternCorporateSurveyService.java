package com.winwintek.web.backend.api.domain.admin.corporate.internCorporateSurvey.service;

import com.winwintek.web.backend.api.dto.admin.corporate.internCorporateSurvey.InternCorporateSurveyDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.internCorporateSurvey.InternCorporateSurveyReqDTO;

public interface InternCorporateSurveyService {

    /**
     * Get Intern Corporate Survey
     *
     * @param internCorporateSurveyReqDTO .
     * @return InternCorporateSurveyDTO
     */
    InternCorporateSurveyDTO getInternCorporateSurvey(InternCorporateSurveyReqDTO internCorporateSurveyReqDTO);

    /**
     * Save Intern Corporate Survey
     *
     * @param internCorporateSurveyDTO .
     * @return Boolean
     */
    Boolean saveInternCorporateSurvey(InternCorporateSurveyDTO internCorporateSurveyDTO);

}
