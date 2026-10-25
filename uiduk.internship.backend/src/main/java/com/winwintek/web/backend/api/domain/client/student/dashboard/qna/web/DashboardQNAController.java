package com.winwintek.web.backend.api.domain.client.student.dashboard.qna.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.student.dashboard.qna.service.IDashboardQNAService;
import com.winwintek.web.backend.api.dto.client.student.dashboard.qna.DashboardQNAResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/c/dashboard")
public class DashboardQNAController {

    @Autowired
    private IDashboardQNAService qnaService;

    @PostMapping("/qna")
    public ApiResponse<List<DashboardQNAResDTO>> getDashboardQNAList(){
        List<DashboardQNAResDTO> response = qnaService.getDashboardQNA();
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }
}
