package com.winwintek.web.backend.api.entity.client.student.reportWeek;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WeekCalendarClientResDTO {

    private String stdWeekReportId;

    private String internPlanDetailSeq;

    private String internStdSeq;

    private String dateWeek;

    private String inHour;

    private String inMinute;

    private String outHour;

    private String outMinute;

    private String jobContent;

    private String acquiredKnowledge;

//    private String attendanceDate;

    private String isDisabled;

//    private String dayOfWeek;

}
