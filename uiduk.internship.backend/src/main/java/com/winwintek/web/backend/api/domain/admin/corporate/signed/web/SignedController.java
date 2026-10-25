package com.winwintek.web.backend.api.domain.admin.corporate.signed.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.corporate.signed.service.SignedService;
import com.winwintek.web.backend.api.dto.admin.corporate.signed.SignedDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.signed.StudentInternCorporate;
import com.winwintek.web.backend.api.dto.admin.corporate.signed.TripartiteAgreeReqDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.signed.UpdateListAppendDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/a/c")
public class SignedController {

    @Autowired
    private SignedService signedService;

    @PostMapping ("/sign-list")
    public ApiResponse<SignedDTO> getListSign(@RequestBody String internPlanId) {
        SignedDTO lstResult = signedService.getListSign(internPlanId);

        ApiResponse<SignedDTO> lstResultDTO = new ApiResponse<>(ApiStatus.SUCCESS, lstResult);

        return lstResultDTO;
    }

    @PostMapping ("/list-student")
    public ApiResponse<List<StudentInternCorporate>> getListStudentCorporate(@RequestBody String internPlanId) {
        List<StudentInternCorporate> lstResult = signedService.getListStudentCorporate(internPlanId);

        ApiResponse<List<StudentInternCorporate>> lstResultDTO = new ApiResponse<>(ApiStatus.SUCCESS, lstResult);

        return lstResultDTO;
    }

    @PutMapping ("/update-append")
    public ApiResponse<Boolean> appendAll(@RequestBody UpdateListAppendDTO updateListAppendDTO) {
        boolean result = signedService.appendAll(updateListAppendDTO);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PostMapping ("/create-tripartite-agree")
    public ApiResponse<List<StudentInternCorporate>> createTripartiteAgree(@RequestBody TripartiteAgreeReqDTO dto) {
        List<StudentInternCorporate> lstResult = signedService.createTripartiteAgree(dto);

        ApiResponse<List<StudentInternCorporate>> lstResultDTO = new ApiResponse<>(ApiStatus.SUCCESS, lstResult);

        return lstResultDTO;
    }

}
