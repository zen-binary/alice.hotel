package com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceTrackingDayCenterResDTO {

    private String stdAttendanceSeq;

    private String date;

    private String status;
}
