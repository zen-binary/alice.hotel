package com.winwintek.web.backend.api.entity.client.student.reportWeek;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class WeekReportClientResDTO {

    private List<WeekCalendarClientResDTO> lstWeekCalendar;
}
