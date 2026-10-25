package com.winwintek.web.backend.api.domain.client.professor.guidanceReport.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.professor.guidanceReport.service.GuidanceReportProfService;
import com.winwintek.web.backend.api.dto.client.professor.guidanceReport.GuidanceReportProfDetailResDTO;
import com.winwintek.web.backend.api.dto.client.professor.guidanceReport.GuidanceReportProfDetailSearchDTO;
import com.winwintek.web.backend.api.dto.client.professor.guidanceReport.GuidanceReportProfReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/c/prof/guid-report")
public class GuidanceReportProfController {

    @Autowired
    private GuidanceReportProfService guidanceReportProfService;

    @PostMapping ("/detail")
    public ApiResponse<GuidanceReportProfDetailResDTO> getDetailGuidanceReport(@RequestBody
            GuidanceReportProfDetailSearchDTO searchDTO) {
        GuidanceReportProfDetailResDTO result = guidanceReportProfService.getDetailGuidanceReport(searchDTO);

        ApiResponse<GuidanceReportProfDetailResDTO> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

    @PostMapping ("/create")
    public ApiResponse<String> createGuidanceReport(@RequestBody GuidanceReportProfReqDTO dto) {
        String result = guidanceReportProfService.createGuidanceReport(dto);

        ApiResponse<String> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

    @PostMapping ("/update")
    public ApiResponse<String> updateGuidanceReport(@RequestBody GuidanceReportProfReqDTO dto) {
        String result = guidanceReportProfService.updateGuidanceReport(dto);

        ApiResponse<String> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

}
