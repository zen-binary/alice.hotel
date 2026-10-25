package com.winwintek.web.backend.api.dto.client.professor.evaluationReport.weekReport;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class WeekReportProfResDTO {

    private String stdNm;

    private String deptCd;

    private String deptNm;

    private String shyrCd;

    private String shyrNm;

    private String stdNo;

    private String corpId;

    private String dayInWeek;

    private List<WeekCalendarProfResDTO> lstWeekCalendar;

}
