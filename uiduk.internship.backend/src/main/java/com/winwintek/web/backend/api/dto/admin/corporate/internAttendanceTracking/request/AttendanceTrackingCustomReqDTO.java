package com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.request;

import com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.response.AttendanceTrackingDayWeekResDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class AttendanceTrackingCustomReqDTO {

    private String internDetailSeq;

    private String year;

    private String week;

    private List<List<AttendanceTrackingDayWeekReqDTO>> lstDayWeek;

    private Long attendance;

    private Long absence;

    private Long holiday;

}