package com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.request;

import com.winwintek.web.backend.api.common.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AttendanceTrackingReqDTO {

    private String internPlanSeq;

    private String internStdSeq;

    private List<AttendanceTrackingCustomReqDTO> lstAttendanceTracking;

    private String delYn = Constants.STATE_N;

}
