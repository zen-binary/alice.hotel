package com.winwintek.web.backend.api.domain.client.professor.attendanceTrackingLecturer.service;

import com.winwintek.web.backend.api.dto.client.professor.attendanceTrackingLecturer.request.AttendanceTrackingLecturerReqDTO;
import com.winwintek.web.backend.api.dto.client.professor.attendanceTrackingLecturer.response.AttendanceTrackingLecturerResDTO;
import com.winwintek.web.backend.api.dto.client.professor.attendanceTrackingLecturer.response.AttendanceTrackingStudentLecturerResDTO;

import java.util.List;

public interface AttendanceTrackingLecturerService {

    List<AttendanceTrackingLecturerResDTO> getLstAttendance(AttendanceTrackingLecturerReqDTO dto);

    AttendanceTrackingStudentLecturerResDTO getAttendanceStudent(AttendanceTrackingLecturerReqDTO dto);
}
