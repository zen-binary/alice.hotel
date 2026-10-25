package com.winwintek.web.backend.api.domain.admin.supportCenter.completeManage.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.supportCenter.completeManage.service.CompleteManageService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.completeManage.CompleteGetByInternPlanId;
import com.winwintek.web.backend.api.dto.admin.supportCenter.completeManage.CompleteManageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/a")
public class CompleteManageController {

    @Autowired
    private CompleteManageService completeManageService;

    @PostMapping("/list-complete-manage")
    public ApiResponse<Page<CompleteManageDTO>> getListStudent(@RequestBody CompleteGetByInternPlanId dto){
        Page<CompleteManageDTO> list = completeManageService.getListStudent(dto);

        ApiResponse<Page<CompleteManageDTO>> lstResultDTO = new ApiResponse<>(ApiStatus.SUCCESS, list);

        return lstResultDTO;
    }

    @PostMapping ("/complete")
    public ApiResponse<Boolean> complete(@RequestBody List<String> value) {
        Boolean result = completeManageService.complete(value);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PostMapping ("/cancel-complete")
    public ApiResponse<Boolean> cancelComplete(@RequestBody List<String> value) {
        Boolean result = completeManageService.cancelComplete(value);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PostMapping ("/un-finish")
    public ApiResponse<Boolean> unFinish(@RequestBody List<String> value) {
        Boolean result = completeManageService.unFinish(value);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PostMapping ("/reserve")
    public ApiResponse<Boolean> reserve(@RequestBody List<String> value) {
        Boolean result = completeManageService.reserve(value);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PostMapping ("/cancel-not-complete")
    public ApiResponse<Boolean> cancelNotComplete(@RequestBody List<String> value) {
        Boolean result = completeManageService.cancelNotComplete(value);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PostMapping ("/cancel-reservation")
    public ApiResponse<Boolean> cancelReservation(@RequestBody List<String> value) {
        Boolean result = completeManageService.cancelReservation(value);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

}
