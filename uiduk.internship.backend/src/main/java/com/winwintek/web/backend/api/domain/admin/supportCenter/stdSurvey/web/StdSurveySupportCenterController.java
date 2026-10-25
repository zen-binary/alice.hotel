package com.winwintek.web.backend.api.domain.admin.supportCenter.stdSurvey.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.supportCenter.stdSurvey.service.StdSurveySupportCenterService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.stdSurvey.StdSurveySupportCenterResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/a/support-center/std-survey")
public class StdSurveySupportCenterController {

    @Autowired
    private StdSurveySupportCenterService stdSurveySupportCenterService;

    @PostMapping("/detail")
    public ApiResponse<StdSurveySupportCenterResDTO> getDetailStudentSurvey(@RequestBody String internStdId) {
        StdSurveySupportCenterResDTO result = stdSurveySupportCenterService.getDetailStudentSurvey(internStdId);

        ApiResponse<StdSurveySupportCenterResDTO> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }


}
