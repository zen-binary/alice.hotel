package com.winwintek.web.backend.api.domain.client.student.agreeTerm.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.student.agreeTerm.service.IAgreeTermService;
import com.winwintek.web.backend.api.dto.client.student.agreeTerm.AgreeTermUserResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/c/agree-term")
public class AgreeTermController {

    @Autowired
    private IAgreeTermService agreeTermService;

    @PostMapping("")
    public ApiResponse<AgreeTermUserResDTO> updateAgreeTermUser(){
        AgreeTermUserResDTO response = agreeTermService.updateAgreeTermUser();
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }
}
