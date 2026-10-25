package com.winwintek.web.backend.api.domain.client.student.summaryReportStudent.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.student.summaryReportStudent.service.SummaryReportStudentService;
import com.winwintek.web.backend.api.dto.client.student.summaryReportStudent.SummaryReportStudentReqDTO;
import com.winwintek.web.backend.api.dto.client.student.summaryReportStudent.SummaryReportStudentResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping ("/c/summer-report")
@RequiredArgsConstructor
public class SummaryReportStudentController {

    @Autowired
    private SummaryReportStudentService summerReportService;

    @PostMapping ("/detail")
    public ApiResponse<SummaryReportStudentResDTO> getDetailSummaryReport(@RequestBody String internPlanId) {
        SummaryReportStudentResDTO result = summerReportService.getDetailSummaryReportStudent(internPlanId);
        ApiResponse<SummaryReportStudentResDTO> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

    @PostMapping ("/create")
    public ApiResponse<String> createSummaryReport(@RequestBody SummaryReportStudentReqDTO dto) {
        String result = summerReportService.createSummaryReport(dto);

        ApiResponse<String> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

    @PostMapping ("/update")
    public ApiResponse<String> updateSummaryReport(@RequestBody SummaryReportStudentReqDTO dto) {
        String result = summerReportService.updateSummaryReport(dto);

        ApiResponse<String> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }


}
