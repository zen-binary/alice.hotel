package com.winwintek.web.backend.api.domain.client.student.signedAgreement.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.student.signedAgreement.service.ISignedAgreementService;
import com.winwintek.web.backend.api.dto.client.student.signedAgreement.SignedAgreementResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/c/signed-agreement")
public class SignedAgreementController {

    @Autowired
    private ISignedAgreementService signedAgreementService;

    @PostMapping("/get-form")
    public ApiResponse<SignedAgreementResDTO> getSignedAgreement(@RequestBody String internshipStdSeq){
        SignedAgreementResDTO response = signedAgreementService.getSignedAgreement(internshipStdSeq);
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }

    @PostMapping("/sign")
    public ApiResponse<Boolean> studentSignAgreement(@RequestBody String id){
        boolean response = signedAgreementService.studentSignAgreement(id);
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }
}
