package com.winwintek.web.backend.api.domain.admin.supportCenter.corpSurvey.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.supportCenter.corpSurvey.service.CorpSurveySupportCenterService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.corpSurvey.CorpSurveySupportCenterResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/a/support-center/corp-survey")
public class CorpSurveySupportCenterController {

    @Autowired
    private CorpSurveySupportCenterService corpSurveySupportCenterService;

    @PostMapping("/detail")
    public ApiResponse<CorpSurveySupportCenterResDTO> getDetailCorporateSurvey(@RequestBody String internPlanId) {
        CorpSurveySupportCenterResDTO result = corpSurveySupportCenterService.getDetailCorporateSurvey(internPlanId);

        ApiResponse<CorpSurveySupportCenterResDTO> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

}
