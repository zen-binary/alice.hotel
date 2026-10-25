package com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceTrackingDayWeekCenterResDTO {

    private String dayOfWeek;

    private String date;

    private AttendanceTrackingDayCenterResDTO info;
}
