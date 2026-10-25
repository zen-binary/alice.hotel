package com.winwintek.web.backend.api.domain.client.student.schoolNotice.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.student.schoolNotice.service.ISchoolNoticeService;
import com.winwintek.web.backend.api.dto.client.student.schoolNotice.SchoolNoticeDetailResDTO;
import com.winwintek.web.backend.api.dto.client.student.schoolNotice.SchoolNoticeListResDTO;
import com.winwintek.web.backend.api.dto.client.student.schoolNotice.SchoolNoticeReqDTO;
import com.winwintek.web.backend.api.dto.client.student.schoolNotice.SchoolNoticeResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/c/public/notice")
public class SchoolNoticeController {

    @Autowired
    private ISchoolNoticeService schoolNoticeService;

    @PostMapping("/dashboard-list")
    public ApiResponse<List<SchoolNoticeResDTO>> getSchoolNoticeList(){
        List<SchoolNoticeResDTO> response = schoolNoticeService.getSchoolNoticeList();
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }


    @PostMapping("/list")
    public ApiResponse<Page<SchoolNoticeListResDTO>> getSchoolNoticeList(@RequestBody SchoolNoticeReqDTO request){
        Page<SchoolNoticeListResDTO> response = schoolNoticeService.getSChoolNoticeList(request);
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }

    @PostMapping("/detail")
    public ApiResponse<SchoolNoticeDetailResDTO> getSchoolNoticeDetail(@RequestBody String id){
        SchoolNoticeDetailResDTO response = schoolNoticeService.getSchoolNoticeDetail(id);
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }

}
