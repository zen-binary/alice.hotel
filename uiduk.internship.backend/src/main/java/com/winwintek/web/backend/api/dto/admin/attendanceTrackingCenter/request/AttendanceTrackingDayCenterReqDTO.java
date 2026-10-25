package com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceTrackingDayCenterReqDTO {

    private String stdAttendanceSeq;

    private String date;

    private String status;
}
