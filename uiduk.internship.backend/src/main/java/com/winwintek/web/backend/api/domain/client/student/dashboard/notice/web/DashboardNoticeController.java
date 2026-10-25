package com.winwintek.web.backend.api.domain.client.student.dashboard.notice.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.student.dashboard.notice.impl.DashboardNoticeServiceImpl;
import com.winwintek.web.backend.api.domain.client.student.dashboard.notice.service.IDashboardNoticeService;
import com.winwintek.web.backend.api.dto.client.student.dashboard.notice.DashboardNoticeResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/c/dashboard")
public class DashboardNoticeController {

    @Autowired
    private IDashboardNoticeService noticeService;

    @PostMapping("/notice")
    public ApiResponse<List<DashboardNoticeResDTO>> getDashboardNotice(){
        List<DashboardNoticeResDTO> response = noticeService.getDashboardNotice();
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }
}
