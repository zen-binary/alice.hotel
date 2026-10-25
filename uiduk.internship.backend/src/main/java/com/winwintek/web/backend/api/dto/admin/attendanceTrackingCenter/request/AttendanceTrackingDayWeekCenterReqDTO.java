package com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceTrackingDayWeekCenterReqDTO {

    private String dayOfWeek;

    private String date;

    private AttendanceTrackingDayCenterReqDTO info;
}
