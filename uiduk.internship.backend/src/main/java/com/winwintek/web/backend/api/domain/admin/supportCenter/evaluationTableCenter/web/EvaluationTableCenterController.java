package com.winwintek.web.backend.api.domain.admin.supportCenter.evaluationTableCenter.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.supportCenter.evaluationTableCenter.service.EvaluationTableCenterService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationTableCenter.EvaluationStdCenterDto;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationTableCenter.EvaluationTableCenterRequestDto;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationTableCenter.EvaluationTableDetailCenterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/a/evaluation-std-center")
public class EvaluationTableCenterController {
    @Autowired
    private EvaluationTableCenterService evaluationTableService;

    @PostMapping ("/create")
    public ApiResponse<Boolean> create(@RequestBody EvaluationTableCenterRequestDto data) {
        Boolean evaluationStdDto = evaluationTableService.save(data);
        return new ApiResponse<>(ApiStatus.SUCCESS, evaluationStdDto);
    }

    @PostMapping ("/evaluation-table")
    public ApiResponse<EvaluationStdCenterDto> getEvaluationStd(@RequestBody EvaluationTableDetailCenterDto data) {
        EvaluationStdCenterDto evaluationStd = evaluationTableService.getEvaluationStd(data);
        return new ApiResponse<>(ApiStatus.SUCCESS, evaluationStd);
    }
}
