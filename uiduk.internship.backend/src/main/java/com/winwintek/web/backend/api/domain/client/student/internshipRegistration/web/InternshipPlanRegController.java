package com.winwintek.web.backend.api.domain.client.student.internshipRegistration.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.student.internshipRegistration.service.IInternshipPlanRegService;
import com.winwintek.web.backend.api.dto.client.student.internshipRegistration.DetailInternPlanRegFormResDTO;
import com.winwintek.web.backend.api.dto.client.student.internshipRegistration.InternshipRegReqDTO;
import com.winwintek.web.backend.api.dto.client.student.internshipRegistration.InternshipRegResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/c/internship-plan")
public class InternshipPlanRegController {

    @Autowired
    private IInternshipPlanRegService internshipPlanRegService;

    @PostMapping("/list")
    public ApiResponse<List<InternshipRegResDTO>> getInternshipPlanRegList(@RequestBody InternshipRegReqDTO req){
        List<InternshipRegResDTO> response = internshipPlanRegService.getInternPlanRegList(req);
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }

    @PostMapping("/year")
    public ApiResponse<List<String>> getListYear(){
        List<String> response = internshipPlanRegService.getListYear();
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }

    @PostMapping("/reg-form")
    public ApiResponse<DetailInternPlanRegFormResDTO> getRegForm(@RequestBody String id){
        DetailInternPlanRegFormResDTO response = internshipPlanRegService.getRegForm(id);
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }

}
