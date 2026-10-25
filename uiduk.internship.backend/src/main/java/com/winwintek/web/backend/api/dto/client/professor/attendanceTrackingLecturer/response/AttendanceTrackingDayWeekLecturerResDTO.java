package com.winwintek.web.backend.api.dto.client.professor.attendanceTrackingLecturer.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceTrackingDayWeekLecturerResDTO {
    private String dayOfWeek;

    private String date;

    private AttendanceTrackingDayLecturerResDTO info;
}
