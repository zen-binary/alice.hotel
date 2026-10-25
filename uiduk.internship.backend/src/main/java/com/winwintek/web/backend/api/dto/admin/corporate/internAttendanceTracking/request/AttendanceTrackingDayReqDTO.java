package com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceTrackingDayReqDTO {

    private String stdAttendanceSeq;

    private String date;

    private String status;

}