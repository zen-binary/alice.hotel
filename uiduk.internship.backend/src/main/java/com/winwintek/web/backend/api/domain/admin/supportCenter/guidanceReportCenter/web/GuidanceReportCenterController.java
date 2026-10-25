package com.winwintek.web.backend.api.domain.admin.supportCenter.guidanceReportCenter.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.supportCenter.guidanceReportCenter.service.GuidanceReportCenterService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.guidanceReportCenter.GuidanceReportCenterDetailResDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.guidanceReportCenter.GuidanceReportCenterDetailSearchDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.guidanceReportCenter.GuidanceReportCenterReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/a/guid-report")
public class GuidanceReportCenterController {

    @Autowired
    private GuidanceReportCenterService guidanceReportCenterService;

    @PostMapping ("/detail")
    public ApiResponse<GuidanceReportCenterDetailResDTO> getDetailGuidanceReport(@RequestBody GuidanceReportCenterDetailSearchDTO searchDTO) {
        GuidanceReportCenterDetailResDTO result = guidanceReportCenterService.getDetailGuidanceReport(searchDTO);

        ApiResponse<GuidanceReportCenterDetailResDTO> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);
        return resultDTO;
    }

    @PostMapping ("/create")
    public ApiResponse<String> createGuidanceReport(@RequestBody GuidanceReportCenterReqDTO dto) {
        String result = guidanceReportCenterService.createGuidanceReport(dto);

        ApiResponse<String> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);
        return resultDTO;
    }

    @PutMapping ("/update")
    public ApiResponse<String> updateGuidanceReport(@RequestBody GuidanceReportCenterReqDTO dto) {
        String result = guidanceReportCenterService.updateGuidanceReport(dto);

        ApiResponse<String> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }
}
