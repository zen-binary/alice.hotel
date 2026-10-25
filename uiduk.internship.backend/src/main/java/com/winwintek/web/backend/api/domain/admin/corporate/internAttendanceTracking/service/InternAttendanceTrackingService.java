package com.winwintek.web.backend.api.domain.admin.corporate.internAttendanceTracking.service;

import com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.request.AttendanceTrackingReqDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.response.AttendanceTrackingLstStudentResDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.response.AttendanceTrackingResDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.response.AttendanceTrackingStudentResDTO;

import java.util.List;

public interface InternAttendanceTrackingService {

    List<AttendanceTrackingResDTO> getLstAttendance(AttendanceTrackingReqDTO dto);

    AttendanceTrackingStudentResDTO getAttendanceStudent(AttendanceTrackingReqDTO dto);

    boolean setAttendanceStudent (AttendanceTrackingReqDTO dto);
}
