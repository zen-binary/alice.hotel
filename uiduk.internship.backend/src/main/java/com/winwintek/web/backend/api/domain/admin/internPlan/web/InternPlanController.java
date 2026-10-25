package com.winwintek.web.backend.api.domain.admin.internPlan.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.internPlan.service.InternPlanService;
import com.winwintek.web.backend.api.dto.admin.corporateManager.CorporateManagerListDto;
import com.winwintek.web.backend.api.dto.admin.internPlan.InternPlanCormMemberInfoDTO;
import com.winwintek.web.backend.api.dto.admin.internPlan.InternPlanDetailDto;
import com.winwintek.web.backend.api.dto.admin.internPlan.InternPlanExistingDto;
import com.winwintek.web.backend.api.dto.admin.internPlan.InternPlanListDto;
import com.winwintek.web.backend.api.dto.admin.internPlan.InternPlanRequestDto;
import com.winwintek.web.backend.api.dto.admin.internPlan.InternPlanSearchDto;
import com.winwintek.web.backend.api.dto.admin.staffIntern.StaffInternDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/a/c/intern-plan")
public class InternPlanController {

    @Autowired
    private InternPlanService internPlanService;

    @PostMapping ("")
    public ApiResponse<Page<InternPlanListDto>> getPage(@RequestBody InternPlanSearchDto dto) {
        Page<InternPlanListDto> listDtoPage = internPlanService.getListInternPlan(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, listDtoPage);
    }

    @PostMapping ("/create")
    public ApiResponse<String> create(@RequestBody InternPlanRequestDto dto) {
        String result = internPlanService.create(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PostMapping ("/detail")
    public ApiResponse<InternPlanDetailDto> create(@RequestBody String data) {
        InternPlanDetailDto result = internPlanService.detail(data);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @GetMapping ("/existing")
    public ApiResponse<List<InternPlanExistingDto>> getInternPlanExisting() {
        List<InternPlanExistingDto> result = internPlanService.getInternPlanExisting();
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PostMapping ("/existing-update")
    public ApiResponse<List<InternPlanExistingDto>> getInternPlanExistingUpdate(@RequestBody String internPlanSeq) {
        List<InternPlanExistingDto> result = internPlanService.getInternPlanExistingUpdate(internPlanSeq);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PostMapping ("/execution")
    public ApiResponse<Integer> executionCount(@RequestBody String data) {
        Integer result = internPlanService.executionCount(data);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @GetMapping ("/corporate-manager")
    public ApiResponse<List<CorporateManagerListDto>> getCorporateManager() {
        List<CorporateManagerListDto> result = internPlanService.getCorporateManager();
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @GetMapping ("/staff")
    public ApiResponse<List<StaffInternDto>> getListStaff() {
        List<StaffInternDto> result = internPlanService.getListStaff();
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PostMapping ("/delete")
    public ApiResponse<Boolean> deleteInternPlan(@RequestBody List<String> data) {
        Boolean result = internPlanService.delete(data);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PostMapping ("/corm-member-info")
    public ApiResponse<InternPlanCormMemberInfoDTO> getCormMemberInfo() {
        InternPlanCormMemberInfoDTO result = internPlanService.getInternPlanCormMemberInfo();
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

}
