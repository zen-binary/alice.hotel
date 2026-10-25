package com.winwintek.web.backend.api.dto.client.professor.attendanceTrackingLecturer.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AttendanceTrackingLecturerResDTO {
    private String internDetailSeq;

    private String year;

    private String week;

    private List<List<AttendanceTrackingDayWeekLecturerResDTO>> lstDayWeek;

    private Long attendance;

    private Long absence;

    private Long holiday;
}
