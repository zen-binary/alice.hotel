package com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.request;

import com.winwintek.web.backend.api.common.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AttendanceTrackingCenterReqDTO {

    private String internPlanSeq;

    private String internStdSeq;

    private List<AttendanceTrackingCustomCenterReqDTO> lstAttendanceTracking;

    private String delYn = Constants.STATE_N;
}
