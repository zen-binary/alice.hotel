package com.winwintek.web.backend.api.domain.client.student.reportWeek.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.client.student.reportWeek.service.ReportWeekService;
import com.winwintek.web.backend.api.dto.client.student.reportWeek.*;
import com.winwintek.web.backend.api.entity.client.student.reportWeek.WeekCalendarClientResDTO;
import com.winwintek.web.backend.api.entity.client.student.reportWeek.WeekReportClientResDTO;
import com.winwintek.web.backend.api.entity.client.student.reportWeek.WeekReportClientSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/c")
public class ReportWeekController {

    @Autowired
    private ReportWeekService reportWeekService;

    @PostMapping("/list-week")
    public ApiResponse<WeekClientDTO> getListWeekClient(@RequestBody SearchWeekReportDTO dto){
        WeekClientDTO lstResult = reportWeekService.getListWeekClient(dto);

        ApiResponse<WeekClientDTO> lstResultDTO = new ApiResponse<>(ApiStatus.SUCCESS, lstResult);

        return lstResultDTO;
    }

    @PostMapping("/detail-report-week")
    public ApiResponse<List<WeekCalendarClientResDTO>> getDetailReportWeekClient(@RequestBody WeekReportClientSearchDTO dto){
        List<WeekCalendarClientResDTO> lstResult = reportWeekService.getListDetailWeekReport(dto);

        ApiResponse<List<WeekCalendarClientResDTO>> resultDTO = new ApiResponse<>(ApiStatus.SUCCESS, lstResult);

        return resultDTO;
    }

//    @PostMapping("/create-report-week")
//    public ApiResponse<String> createWeekReport(@RequestBody WeekReportCreateDTO dto){
//        String result = reportWeekService.createWeekReport(dto);
//        return new ApiResponse<>(ApiStatus.SUCCESS, result);
//    }

    @PostMapping ("/save-report-week")
    public ApiResponse<List<String>> saveWeekReport(@RequestBody List<WeekCalendarClientResDTO> listWeekReport) {
        List<String> result = reportWeekService.saveWeekReport(listWeekReport);

        return new ApiResponse<>(ApiStatus.SUCCESS, result);
    }

}
