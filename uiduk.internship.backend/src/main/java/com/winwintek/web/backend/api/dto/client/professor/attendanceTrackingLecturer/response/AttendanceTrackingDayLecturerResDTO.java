package com.winwintek.web.backend.api.dto.client.professor.attendanceTrackingLecturer.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceTrackingDayLecturerResDTO {
    private String stdAttendanceSeq;

    private String date;

    private String status;
}
