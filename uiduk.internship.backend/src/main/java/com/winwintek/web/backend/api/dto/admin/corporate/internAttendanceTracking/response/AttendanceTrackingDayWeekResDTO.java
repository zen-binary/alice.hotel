package com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceTrackingDayWeekResDTO {

    private String dayOfWeek;

    private String date;

    private AttendanceTrackingDayResDTO info;

}
