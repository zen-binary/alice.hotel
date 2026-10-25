package com.winwintek.web.backend.api.domain.admin.supportCenter.weekReport.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.supportCenter.weekReport.service.WeekReportSupportCenterService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.weekReport.PlanDetailSupportCenterResDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.weekReport.WeekReportSupportCenterReqDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.weekReport.WeekReportSupportCenterResDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.weekReport.WeekReportSupportCenterSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/a/support-center/week-report")
public class WeekReportSupportCenterController {

    @Autowired
    private WeekReportSupportCenterService weekReportSupportCenterService;

    @PostMapping ("/list-week")
    public ApiResponse<List<PlanDetailSupportCenterResDTO>> getListWeekByInternPlan(@RequestBody String internPlanId) {
        List<PlanDetailSupportCenterResDTO> result = weekReportSupportCenterService.getListWeekByInternPlan(
                internPlanId);

        ApiResponse<List<PlanDetailSupportCenterResDTO>> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

    @PostMapping ("/detail")
    public ApiResponse<WeekReportSupportCenterResDTO> getDetailWeekReport(
            @RequestBody WeekReportSupportCenterSearchDTO searchDTO) {
        WeekReportSupportCenterResDTO result = weekReportSupportCenterService.getDetailWeekReport(searchDTO);

        ApiResponse<WeekReportSupportCenterResDTO> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

    @PostMapping ("/create")
    public ApiResponse<List<String>> createWeekReport(
            @RequestBody List<WeekReportSupportCenterReqDTO> lstDTO) {
        List<String> lstResult = weekReportSupportCenterService.createWeekReport(lstDTO);

        ApiResponse<List<String>> lstResultDTO = new ApiResponse<>(ApiStatus.SUCCESS, lstResult);

        return lstResultDTO;
    }

}
