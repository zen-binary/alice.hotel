package com.winwintek.web.backend.api.domain.client.professor.attendanceTrackingLecturer.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.professor.attendanceTrackingLecturer.service.AttendanceTrackingLecturerService;
import com.winwintek.web.backend.api.dto.client.professor.attendanceTrackingLecturer.request.AttendanceTrackingLecturerReqDTO;
import com.winwintek.web.backend.api.dto.client.professor.attendanceTrackingLecturer.response.AttendanceTrackingLecturerResDTO;
import com.winwintek.web.backend.api.dto.client.professor.attendanceTrackingLecturer.response.AttendanceTrackingStudentLecturerResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/c/prof/attendance-tracking-lecturer")
public class AttendanceTrackingLecturerController {

    @Autowired
    private AttendanceTrackingLecturerService attendanceTrackingLecturerService;

    @PostMapping ()
    public ApiResponse<List<AttendanceTrackingLecturerResDTO>> getLstAttendance(@RequestBody AttendanceTrackingLecturerReqDTO dto) {
        List<AttendanceTrackingLecturerResDTO> result = attendanceTrackingLecturerService.getLstAttendance(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

    @PostMapping ("/info-student")
    public ApiResponse<AttendanceTrackingStudentLecturerResDTO> getAttendanceStudent(@RequestBody AttendanceTrackingLecturerReqDTO dto) {
        AttendanceTrackingStudentLecturerResDTO result = attendanceTrackingLecturerService.getAttendanceStudent(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }
}
