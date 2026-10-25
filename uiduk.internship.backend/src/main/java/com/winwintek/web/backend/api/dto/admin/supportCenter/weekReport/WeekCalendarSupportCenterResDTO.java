package com.winwintek.web.backend.api.dto.admin.supportCenter.weekReport;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WeekCalendarSupportCenterResDTO {

    private String stdWeekReportId;

    private String dateWeek;

    private String inHour;

    private String inMinute;

    private String outHour;

    private String outMinute;

    private String jobContent;

    private String acquiredKnowledge;

    private String attendanceDate;

    private String isDisabled;

}
