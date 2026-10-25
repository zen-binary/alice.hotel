package com.winwintek.web.backend.api.domain.client.student.stdSurveyAns.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.student.stdSurveyAns.service.IStdSurveyAnsService;
import com.winwintek.web.backend.api.dto.client.student.stdSurveyAns.ListStdSurveyAnsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/s/std-survey-ans")
@RequiredArgsConstructor
public class StdSurveyAnsController {

    @Autowired
    private IStdSurveyAnsService stdSurveyAnsService;

    @PostMapping ("/create")
    public ApiResponse<Boolean> create(@RequestBody ListStdSurveyAnsDTO dto) {
        Boolean result = stdSurveyAnsService.create(dto);
        ApiResponse<Boolean> response = new ApiResponse<>(ApiStatus.SUCCESS, result);
        return response;
    }

    @PostMapping ("/update")
    public ApiResponse<Boolean> update(@RequestBody ListStdSurveyAnsDTO dto) {
        Boolean result = stdSurveyAnsService.update(dto);
        ApiResponse<Boolean> response = new ApiResponse<>(ApiStatus.SUCCESS, result);
        return response;
    }

}
