package com.winwintek.web.backend.api.domain.admin.supportCenter.stdSurvey.service;

import com.winwintek.web.backend.api.dto.admin.supportCenter.stdSurvey.StdSurveySupportCenterResDTO;

public interface StdSurveySupportCenterService {

    StdSurveySupportCenterResDTO getDetailStudentSurvey(String internPlanId);

}
