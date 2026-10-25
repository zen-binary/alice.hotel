package com.winwintek.web.backend.api.dto.admin.supportCenter.weekReport;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class WeekReportSupportCenterResDTO {

    private String stdNm;

    private String deptCd;

    private String deptNm;

    private String shyrCd;

    private String shyrNm;

    private String stdNo;

    private String corpId;

    private String dayInWeek;

    private List<WeekCalendarSupportCenterResDTO> lstWeekCalendar;

}
