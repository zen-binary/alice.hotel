package com.winwintek.web.backend.api.domain.admin.supportCenter.summaryReport.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.supportCenter.summaryReport.service.SummaryReportSupportCenterService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.summaryReport.SummaryReportSupportCenterReqDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.summaryReport.SummaryReportSupportCenterResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/a/support-center/summary-report")
public class SummaryReportSupportCenterController {

    @Autowired
    private SummaryReportSupportCenterService summaryReportSupportCenterService;

    @PostMapping ("/detail")
    public ApiResponse<SummaryReportSupportCenterResDTO> getDetailSummaryReport(@RequestBody String internStdId) {
        SummaryReportSupportCenterResDTO result = summaryReportSupportCenterService.getDetailSummaryReport(internStdId);

        ApiResponse<SummaryReportSupportCenterResDTO> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

    @PostMapping ("/create")
    public ApiResponse<String> createSummaryReport(@RequestBody SummaryReportSupportCenterReqDTO dto) {
        String result = summaryReportSupportCenterService.createSummaryReport(dto);

        ApiResponse<String> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

    @PostMapping ("/update")
    public ApiResponse<String> updateSummaryReport(@RequestBody SummaryReportSupportCenterReqDTO dto) {
        String result = summaryReportSupportCenterService.updateSummaryReport(dto);

        ApiResponse<String> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

}
