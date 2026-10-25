package com.winwintek.web.backend.api.domain.admin.attendanceTrackingCenter.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.attendanceTrackingCenter.service.AttendanceTrackingCenterService;
import com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.request.AttendanceTrackingCenterReqDTO;
import com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.response.AttendanceTrackingCenterResDTO;
import com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.response.AttendanceTrackingStudentCenterResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/a/attendance-tracking-center")
public class AttendanceTrackingCenterController {

    @Autowired
    private AttendanceTrackingCenterService attendanceTrackingCenterService;

    @PostMapping ()
    public ApiResponse<List<AttendanceTrackingCenterResDTO>> getLstAttendance(@RequestBody AttendanceTrackingCenterReqDTO dto) {
        List<AttendanceTrackingCenterResDTO> result = attendanceTrackingCenterService.getLstAttendance(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PostMapping ("/info-student")
    public ApiResponse<AttendanceTrackingStudentCenterResDTO> getAttendanceStudent(@RequestBody AttendanceTrackingCenterReqDTO dto) {
        AttendanceTrackingStudentCenterResDTO result = attendanceTrackingCenterService.getAttendanceStudent(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PutMapping ("/setup-tracking")
    public ApiResponse<Boolean> setAttendanceStudent(@RequestBody AttendanceTrackingCenterReqDTO dto) {
        Boolean result = attendanceTrackingCenterService.setAttendanceStudent(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }
}
