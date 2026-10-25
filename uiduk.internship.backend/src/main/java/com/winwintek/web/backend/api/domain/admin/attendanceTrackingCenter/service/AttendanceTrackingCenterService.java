package com.winwintek.web.backend.api.domain.admin.attendanceTrackingCenter.service;

import com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.request.AttendanceTrackingCenterReqDTO;
import com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.response.AttendanceTrackingCenterResDTO;
import com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.response.AttendanceTrackingStudentCenterResDTO;

import java.util.List;

public interface AttendanceTrackingCenterService {

    List<AttendanceTrackingCenterResDTO> getLstAttendance(AttendanceTrackingCenterReqDTO dto);

    AttendanceTrackingStudentCenterResDTO getAttendanceStudent(AttendanceTrackingCenterReqDTO dto);

    boolean setAttendanceStudent (AttendanceTrackingCenterReqDTO dto);
}
