package com.winwintek.web.backend.api.domain.admin.supportCenter.evaluationReportCenter.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.supportCenter.evaluationReportCenter.service.EvaluationReportCenterService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationReportCenter.EvaluationReportCenterDetailResDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationReportCenter.EvaluationReportCenterDetailSearchDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationReportCenter.EvaluationReportCenterReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/a/eval-report-center")
public class EvaluationReportCenterController {

    @Autowired
    private EvaluationReportCenterService evaluationReportCenterService;

    @PostMapping ("/detail")
    public ApiResponse<EvaluationReportCenterDetailResDTO> getDetailEvaluationReport(
            @RequestBody EvaluationReportCenterDetailSearchDTO dto) {
        EvaluationReportCenterDetailResDTO result = evaluationReportCenterService.getDetailEvaluationReport(dto);

        ApiResponse<EvaluationReportCenterDetailResDTO> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

    @PostMapping ("/create")
    public ApiResponse<String> createEvaluationReport(@RequestBody EvaluationReportCenterReqDTO dto) {
        String result = evaluationReportCenterService.createEvaluationReport(dto);

        ApiResponse<String> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

    @PutMapping ("/update")
    public ApiResponse<String> updateEvaluationReport(@RequestBody EvaluationReportCenterReqDTO dto) {
        String result = evaluationReportCenterService.updateEvaluationReport(dto);

        ApiResponse<String> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, result);

        return resultDTO;
    }

}
