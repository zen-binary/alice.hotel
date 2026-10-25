package com.winwintek.web.backend.api.domain.client.student.reportWeek.service;

import com.winwintek.web.backend.api.dto.client.student.reportWeek.*;
import com.winwintek.web.backend.api.entity.client.student.reportWeek.WeekCalendarClientResDTO;
import com.winwintek.web.backend.api.entity.client.student.reportWeek.WeekReportClientResDTO;
import com.winwintek.web.backend.api.entity.client.student.reportWeek.WeekReportClientSearchDTO;

import java.util.List;

public interface ReportWeekService {

    WeekClientDTO getListWeekClient(SearchWeekReportDTO dto);

    List<WeekCalendarClientResDTO> getListDetailWeekReport(WeekReportClientSearchDTO searchDTO);

//    String createWeekReport(WeekReportCreateDTO  dto);

    List<String> saveWeekReport(List<WeekCalendarClientResDTO> listWeekReportDTO);

}
