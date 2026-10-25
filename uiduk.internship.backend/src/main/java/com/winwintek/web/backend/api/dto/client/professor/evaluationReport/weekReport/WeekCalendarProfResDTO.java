package com.winwintek.web.backend.api.dto.client.professor.evaluationReport.weekReport;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WeekCalendarProfResDTO {

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
