package com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AttendanceTrackingResDTO {

    private String internDetailSeq;

    private String year;

    private String week;

    private List<List<AttendanceTrackingDayWeekResDTO>> lstDayWeek;

    private Long attendance;

    private Long absence;

    private Long holiday;

}
