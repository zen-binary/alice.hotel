package com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AttendanceTrackingCenterResDTO {
    private String internDetailSeq;

    private String year;

    private String week;

    private List<List<AttendanceTrackingDayWeekCenterResDTO>> lstDayWeek;

    private Long attendance;

    private Long absence;

    private Long holiday;
}
