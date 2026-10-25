package com.winwintek.web.backend.api.dto.client.professor.attendanceTrackingLecturer.request;

import com.winwintek.web.backend.api.common.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceTrackingLecturerReqDTO {

    private String internPlanSeq;

    private String internStdSeq;

    private String delYn = Constants.STATE_N;
}
