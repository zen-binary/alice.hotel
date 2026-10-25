package com.winwintek.web.backend.api.domain.common.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.common.service.CommonStdInternService;
import com.winwintek.web.backend.api.dto.common.stdIntern.StudentInternAdminDTO;
import com.winwintek.web.backend.api.dto.common.stdIntern.StudentInternClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("")
public class CommonStdInternController {

    @Autowired
    private CommonStdInternService commonStdInternService;

    @PostMapping ("/a/list-student")
    public ApiResponse<List<StudentInternAdminDTO>> getListStudentAdmin(@RequestBody String internPlanId) {
        List<StudentInternAdminDTO> lstResult = commonStdInternService.getListStudentAdmin(internPlanId);

        ApiResponse<List<StudentInternAdminDTO>> lstResultDTO = new ApiResponse<>(ApiStatus.SUCCESS, lstResult);

        return lstResultDTO;
    }

    @PostMapping ("/c/list-student")
    public ApiResponse<List<StudentInternClientDTO>> getListStudentClient(@RequestBody String internPlanId) {
        List<StudentInternClientDTO> lstResult = commonStdInternService.getListStudentClient(internPlanId);

        ApiResponse<List<StudentInternClientDTO>> lstResultDTO = new ApiResponse<>(ApiStatus.SUCCESS, lstResult);

        return lstResultDTO;
    }

}
