package com.winwintek.web.backend.api.domain.client.student.summaryReportStudent.service;

import com.winwintek.web.backend.api.dto.client.student.summaryReportStudent.SummaryReportStudentReqDTO;
import com.winwintek.web.backend.api.dto.client.student.summaryReportStudent.SummaryReportStudentResDTO;


public interface SummaryReportStudentService {

    SummaryReportStudentResDTO getDetailSummaryReportStudent(String internPlanId);

    String createSummaryReport(SummaryReportStudentReqDTO dto);

    String updateSummaryReport(SummaryReportStudentReqDTO dto);

}
