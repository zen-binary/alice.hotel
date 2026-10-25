package com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.request;

import com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.response.AttendanceTrackingDayWeekCenterResDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AttendanceTrackingCustomCenterReqDTO {

    private String internDetailSeq;

    private String year;

    private String week;

    private List<List<AttendanceTrackingDayWeekCenterReqDTO>> lstDayWeek;

    private Long attendance;

    private Long absence;

    private Long holiday;
}
