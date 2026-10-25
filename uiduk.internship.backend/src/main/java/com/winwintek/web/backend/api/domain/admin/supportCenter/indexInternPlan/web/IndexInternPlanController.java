package com.winwintek.web.backend.api.domain.admin.supportCenter.indexInternPlan.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.supportCenter.indexInternPlan.service.IndexInternPlanService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.indexInternPlan.IndexInternPlanListDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.indexInternPlan.IndexInternPlanSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/a/intern-plan")
@RequiredArgsConstructor
public class IndexInternPlanController {

    @Autowired
    private IndexInternPlanService indexInternPlanService;

    @PostMapping ("/list")
    public ApiResponse<Page<IndexInternPlanListDTO>> getList(@RequestBody IndexInternPlanSearchDTO dto) {
        Page<IndexInternPlanListDTO> lstResults = indexInternPlanService.getIndexInternPlanList(dto);
        ApiResponse<Page<IndexInternPlanListDTO>> lstResultsDTO = new ApiResponse<>(ApiStatus.SUCCESS, lstResults);

        return lstResultsDTO;
    }

}
