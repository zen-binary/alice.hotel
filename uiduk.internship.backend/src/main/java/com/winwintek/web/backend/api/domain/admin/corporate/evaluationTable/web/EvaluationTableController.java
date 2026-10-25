package com.winwintek.web.backend.api.domain.admin.corporate.evaluationTable.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.corporate.evaluationTable.service.EvaluationTableService;
import com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable.EvaluationTableDetailDto;
import com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable.EvaluationTableRequestDto;
import com.winwintek.web.backend.api.dto.admin.corporate.evaluationTable.evaluationStd.EvaluationStdDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/a/c/evaluation-std")
public class EvaluationTableController {

    @Autowired
    private EvaluationTableService evaluationTableService;

    @PostMapping ("/create")
    public ApiResponse<Boolean> create(@RequestBody EvaluationTableRequestDto data) {
        Boolean evaluationStdDto = evaluationTableService.save(data);
        return new ApiResponse<>(ApiStatus.SUCCESS, evaluationStdDto);
    }

    @PostMapping ("/evaluation-table")
    public ApiResponse<EvaluationStdDto> getEvaluationStd(@RequestBody EvaluationTableDetailDto data) {
        EvaluationStdDto evaluationStd = evaluationTableService.getEvaluationStd(data);
        return new ApiResponse<>(ApiStatus.SUCCESS, evaluationStd);
    }

}
