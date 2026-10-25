package com.winwintek.web.backend.api.domain.admin.corporate.internAttendanceTracking.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.corporate.internAttendanceTracking.service.InternAttendanceTrackingService;
import com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.request.AttendanceTrackingReqDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.response.AttendanceTrackingLstStudentResDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.response.AttendanceTrackingResDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.response.AttendanceTrackingStudentResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/a/c/intern-attendance-tracking")
public class InternAttendanceTrackingController {

    @Autowired
    private InternAttendanceTrackingService internAttendanceTrackingService;

    @PostMapping ()
    public ApiResponse<List<AttendanceTrackingResDTO>> getLstAttendance(@RequestBody AttendanceTrackingReqDTO dto) {
        List<AttendanceTrackingResDTO> result = internAttendanceTrackingService.getLstAttendance(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PostMapping ("/student")
    public ApiResponse<AttendanceTrackingStudentResDTO> getAttendanceStudent(@RequestBody AttendanceTrackingReqDTO dto) {
        AttendanceTrackingStudentResDTO result = internAttendanceTrackingService.getAttendanceStudent(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PutMapping ("/setup-tracking")
    public ApiResponse<Boolean> setAttendanceStudent(@RequestBody AttendanceTrackingReqDTO dto) {
        Boolean result = internAttendanceTrackingService.setAttendanceStudent(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }
}
