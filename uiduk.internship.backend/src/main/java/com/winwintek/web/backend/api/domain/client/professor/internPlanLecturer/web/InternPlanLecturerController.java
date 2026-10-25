package com.winwintek.web.backend.api.domain.client.professor.internPlanLecturer.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.professor.internPlanLecturer.service.InternPlanLecturerService;
import com.winwintek.web.backend.api.dto.client.professor.internPlanLecturer.InternPlanLecturerRepDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/c/prof/intern-plan")
public class InternPlanLecturerController {

    @Autowired
    private InternPlanLecturerService internPlanLecturerService;

    @PostMapping ("/detail")
    public ApiResponse<InternPlanLecturerRepDTO> getDetailGuidanceReport(@RequestBody String internPlanSeq) {
        InternPlanLecturerRepDTO result = internPlanLecturerService.getDetailInternPlan(internPlanSeq);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }
}
