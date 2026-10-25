package com.winwintek.web.backend.api.domain.client.student.dashboard.docRoom.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.student.dashboard.docRoom.service.IDashboardDocRoomService;
import com.winwintek.web.backend.api.dto.client.student.dashboard.docRoom.DashboardDocRoomResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/c/dashboard")
public class DashboardDocRoomController {

    @Autowired
    private IDashboardDocRoomService docRoomService;

    @PostMapping("/doc-room")
    public ApiResponse<List<DashboardDocRoomResDTO>> getDashboardDocRoom(){
        List<DashboardDocRoomResDTO> response = docRoomService.getDashboardDocRoom();
        return new ApiResponse<>(ApiStatus.SUCCESS, response);
    }
}
