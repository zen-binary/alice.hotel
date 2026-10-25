package com.winwintek.web.backend.api.domain.client.professor.evaluationReport.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.professor.evaluationReport.service.EvaluationReportProfService;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.EvaluationReportProfDetailResDTO;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.EvaluationReportProfDetailSearchDTO;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.EvaluationReportProfReqDTO;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.summaryReport.SummaryReportProfResDTO;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.weekReport.PlanDetailProfResDTO;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.weekReport.WeekReportProfResDTO;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.weekReport.WeekReportProfSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/c/prof/eval-report")
public class EvaluationReportProfController {


    @Autowired
    private EvaluationReportProfService evaluationReportProfService;

    @PostMapping ("/detail")
    public ApiResponse<EvaluationReportProfDetailResDTO> getDetailEvaluationReport(
            @RequestBody EvaluationReportProfDetailSearchDTO searchDTO) {
        EvaluationReportProfDetailResDTO result = evaluationReportProfService.getDetailEvaluationReport(searchDTO);

        ApiResponse<EvaluationReportProfDetailResDTO> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

    @PostMapping ("/create")
    public ApiResponse<String> createEvaluationReport(@RequestBody EvaluationReportProfReqDTO dto) {
        String result = evaluationReportProfService.createEvaluationReport(dto);

        ApiResponse<String> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

    @PostMapping ("/update")
    public ApiResponse<String> updateEvaluationReport(@RequestBody EvaluationReportProfReqDTO dto) {
        String result = evaluationReportProfService.updateEvaluationReport(dto);

        ApiResponse<String> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

    @PostMapping ("/detail-summary-report")
    public ApiResponse<SummaryReportProfResDTO> getDetailSummaryReport(@RequestBody String internStdId) {
        SummaryReportProfResDTO result = evaluationReportProfService.getDetailSummaryReport(internStdId);

        ApiResponse<SummaryReportProfResDTO> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

    @PostMapping ("/list-week")
    public ApiResponse<List<PlanDetailProfResDTO>> getListWeekByInternPlan(@RequestBody String internPlanId) {
        List<PlanDetailProfResDTO> result = evaluationReportProfService.getListWeekByInternPlan(
                internPlanId);

        ApiResponse<List<PlanDetailProfResDTO>> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

    @PostMapping ("/detail-week-report")
    public ApiResponse<WeekReportProfResDTO> getDetailWeekReport(
            @RequestBody WeekReportProfSearchDTO searchDTO) {
        WeekReportProfResDTO result = evaluationReportProfService.getDetailWeekReport(searchDTO);

        ApiResponse<WeekReportProfResDTO> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

}
