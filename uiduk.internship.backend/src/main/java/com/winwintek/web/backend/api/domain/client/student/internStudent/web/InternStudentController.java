package com.winwintek.web.backend.api.domain.client.student.internStudent.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.student.internStudent.service.IInternStudentService;
import com.winwintek.web.backend.api.dto.client.student.internStudent.InternStudentListResDTO;
import com.winwintek.web.backend.api.dto.client.student.internStudent.InternStudentRegResDTO;
import com.winwintek.web.backend.api.dto.client.student.internStudent.InternStudentReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/c/intern-student")
public class InternStudentController {

    @Autowired
    private IInternStudentService internStudentService;

    @PostMapping("/create")
    public ApiResponse<InternStudentRegResDTO> createInternStudent(@RequestBody String internPlanSeq){
        InternStudentRegResDTO response = internStudentService.createInternStudent(internPlanSeq);
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }

    @PostMapping("/reg-list")
    public ApiResponse<List<InternStudentListResDTO>> getInternStudentRegList(@RequestBody InternStudentReqDTO req){
        List<InternStudentListResDTO> response = internStudentService.getInternStudentList(req);
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }
    @PostMapping("/update")
    public ApiResponse<Boolean> updateInternStudentStatus(@RequestBody String internStudentSeq){
        boolean response = internStudentService.updateInternStudentApproStatus(internStudentSeq);
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }

    @PostMapping("/internship-available-year")
    public ApiResponse<List<String>> getAvailableInternshipYear(@RequestBody String id){
        List<String> response = internStudentService.getInternshipYearAvailableByStudent(id);
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }
}
