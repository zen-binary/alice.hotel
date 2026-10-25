package com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceTrackingDayWeekReqDTO {

    private String dayOfWeek;

    private String date;

    private AttendanceTrackingDayReqDTO info;

}
