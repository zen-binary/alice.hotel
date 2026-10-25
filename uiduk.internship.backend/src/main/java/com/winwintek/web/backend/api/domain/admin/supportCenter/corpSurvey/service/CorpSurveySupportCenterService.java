package com.winwintek.web.backend.api.domain.admin.supportCenter.corpSurvey.service;

import com.winwintek.web.backend.api.dto.admin.supportCenter.corpSurvey.CorpSurveySupportCenterResDTO;

public interface CorpSurveySupportCenterService {

    CorpSurveySupportCenterResDTO getDetailCorporateSurvey(String internPlanId);

}
