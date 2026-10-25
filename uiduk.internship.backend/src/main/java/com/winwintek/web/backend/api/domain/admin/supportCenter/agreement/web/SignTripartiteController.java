package com.winwintek.web.backend.api.domain.admin.supportCenter.agreement.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.supportCenter.agreement.service.SignTripartiterService;
import com.winwintek.web.backend.api.dto.admin.supportCenter.agreement.SignTripartiteDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.agreement.StudentInternSupportCenterDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.agreement.UpdateListAppendSupportCenterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/a")
public class SignTripartiteController {

    @Autowired
    private SignTripartiterService signTripartiteService;

    @PostMapping ("/sign-list-support-center")
    public ApiResponse<SignTripartiteDTO> getListSignSupportCenter(@RequestBody String internPlanId) {
        SignTripartiteDTO lstResult = signTripartiteService.getListSignSupportCenter(internPlanId);

        ApiResponse<SignTripartiteDTO> lstResultDTO = new ApiResponse<>(ApiStatus.SUCCESS, lstResult);

        return lstResultDTO;
    }

    @PostMapping ("/list-student-support-center")
    public ApiResponse<List<StudentInternSupportCenterDTO>> getListStudentCorporate(@RequestBody String internPlanId) {
        List<StudentInternSupportCenterDTO> lstResult = signTripartiteService.getListStudentSupportCenter(internPlanId);

        ApiResponse<List<StudentInternSupportCenterDTO>> lstResultDTO = new ApiResponse<>(ApiStatus.SUCCESS, lstResult);

        return lstResultDTO;
    }

    @PutMapping ("/update-append-support-center")
    public ApiResponse<Boolean> appendAll(@RequestBody UpdateListAppendSupportCenterDTO updateListAppendDTO) {
        boolean result = signTripartiteService.appendAllSupportCenter(updateListAppendDTO);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

}
