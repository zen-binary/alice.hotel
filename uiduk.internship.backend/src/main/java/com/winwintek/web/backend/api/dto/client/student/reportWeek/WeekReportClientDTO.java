package com.winwintek.web.backend.api.dto.client.student.reportWeek;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeekReportClientDTO {

    private String stdWeekReportId;

    private String content;

    private String inHour;

    private String inMinute;

    private String outHour;

    private String outMinute;

    private String acquiredKnowledge;

}
