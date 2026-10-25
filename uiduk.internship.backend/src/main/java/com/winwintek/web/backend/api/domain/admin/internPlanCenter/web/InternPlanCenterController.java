package com.winwintek.web.backend.api.domain.admin.internPlanCenter.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.internPlanCenter.service.InternPlanCenterService;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterDetailDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterListDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterSearchDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterStsReqDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterStudentDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCenterStudentListDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCountStsDto;
import com.winwintek.web.backend.api.dto.admin.internPlanCenter.InternPlanCountStsReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/a/intern-plan-center")
public class InternPlanCenterController {
    @Autowired
    private InternPlanCenterService internPlanCenterService;

    @PostMapping ()
    public ApiResponse<Page<InternPlanCenterListDto>> getPage(@RequestBody InternPlanCenterSearchDto dto) {
        Page<InternPlanCenterListDto> listDtoPage = internPlanCenterService.getListInternPlanCenter(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, listDtoPage);
    }

    @PostMapping ("/detail")
    public ApiResponse<InternPlanCenterDetailDto> create(@RequestBody String data) {
        InternPlanCenterDetailDto result = internPlanCenterService.detailInternPlan(data);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PostMapping ("/student")
    public ApiResponse<Page<InternPlanCenterStudentListDto>> getPageStudent(@RequestBody InternPlanCenterStudentDto dto) {
        Page<InternPlanCenterStudentListDto> listDtoPage = internPlanCenterService.getListInternPlanStudent(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, listDtoPage);
    }

    @PostMapping ("/student-status")
    public ApiResponse<InternPlanCountStsDto> getStudentStatus(@RequestBody InternPlanCountStsReqDto dto) {
        InternPlanCountStsDto result = internPlanCenterService.getStatusStudentInternPlan(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PutMapping ("/delete")
    public ApiResponse<Boolean> deleteInternPlan(@RequestBody List<String> lstIdInternPlan){
        Boolean result = internPlanCenterService.deleteInternPlan(lstIdInternPlan);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PutMapping ("/approve-intern-plan")
    public ApiResponse<Boolean> approveInternPlan(@RequestBody InternPlanCenterStsReqDto dto){
        Boolean result = internPlanCenterService.approveInternPlan(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PutMapping ("/reject-approve-intern-plan")
    public ApiResponse<Boolean> rejectApproveInternPlan(@RequestBody InternPlanCenterStsReqDto dto){
        Boolean result = internPlanCenterService.rejectApproveInternPlan(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PutMapping ("/cancel-approve-intern-plan")
    public ApiResponse<Boolean> cancelApproveInternPlan(@RequestBody InternPlanCenterStsReqDto dto){
        Boolean result = internPlanCenterService.cancelApproveInternPlan(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PutMapping ("/delete-student")
    public ApiResponse<Boolean> deleteInternStd(@RequestBody List<String> lstIdInternStd){
        Boolean result = internPlanCenterService.deleteInternStd(lstIdInternStd);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PutMapping ("/approve-intern-std")
    public ApiResponse<Boolean> approveInternStd(@RequestBody List<String> lstIdInternStd){
        Boolean result = internPlanCenterService.approveInternStd(lstIdInternStd);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PutMapping ("/not-approve-intern-std")
    public ApiResponse<Boolean> rejectApproveInternStd(@RequestBody List<String> lstIdInternStd){
        Boolean result = internPlanCenterService.notApproveInternStd(lstIdInternStd);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PutMapping ("/cancel-approve-intern-std")
    public ApiResponse<Boolean> cancelApproveInternStd(@RequestBody List<String> lstIdInternStd){
        Boolean result = internPlanCenterService.cancelApproveInternStd(lstIdInternStd);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

}
