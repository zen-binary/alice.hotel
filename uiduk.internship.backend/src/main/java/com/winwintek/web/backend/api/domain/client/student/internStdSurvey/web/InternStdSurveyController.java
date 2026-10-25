package com.winwintek.web.backend.api.domain.client.student.internStdSurvey.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.student.internStdSurvey.service.IInternStdSurveyService;
import com.winwintek.web.backend.api.dto.client.student.internStdSurvey.InternStdSurveyCreDTO;
import com.winwintek.web.backend.api.dto.client.student.internStdSurvey.InternStdSurveyDetailReqDTO;
import com.winwintek.web.backend.api.dto.client.student.internStdSurvey.InternStdSurveyReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/s/intern-std-survey")
@RequiredArgsConstructor
public class InternStdSurveyController {

    @Autowired
    private IInternStdSurveyService internStdSurveyService;

    @PostMapping ("/create")
    public ApiResponse<String> create(@RequestBody InternStdSurveyCreDTO InternStdSurveyCreDTO) {
        String seq = internStdSurveyService.create(InternStdSurveyCreDTO);
        ApiResponse<String> response = new ApiResponse<>(ApiStatus.SUCCESS, seq);
        return response;
    }

    @PostMapping ("/update")
    public ApiResponse<String> update(@RequestBody InternStdSurveyCreDTO InternStdSurveyCreDTO) {
        String seq = internStdSurveyService.update(InternStdSurveyCreDTO);
        ApiResponse<String> response = new ApiResponse<>(ApiStatus.SUCCESS, seq);
        return response;
    }

    @PostMapping ("/detail")
    public ApiResponse<InternStdSurveyDetailReqDTO> getDetail(@RequestBody InternStdSurveyReqDTO dto) {
        InternStdSurveyDetailReqDTO result = internStdSurveyService.getDetailStdSurveyDetail(dto.getInternPlanSeq());
        ApiResponse<InternStdSurveyDetailReqDTO> response = new ApiResponse<>(ApiStatus.SUCCESS, result);
        return response;
    }

}
