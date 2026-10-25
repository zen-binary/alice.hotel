package com.winwintek.web.backend.api.domain.client.professor.evaluationReport.service;

import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.EvaluationReportProfDetailResDTO;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.EvaluationReportProfDetailSearchDTO;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.EvaluationReportProfReqDTO;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.summaryReport.SummaryReportProfResDTO;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.weekReport.PlanDetailProfResDTO;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.weekReport.WeekReportProfResDTO;
import com.winwintek.web.backend.api.dto.client.professor.evaluationReport.weekReport.WeekReportProfSearchDTO;

import java.util.List;

public interface EvaluationReportProfService {

    EvaluationReportProfDetailResDTO getDetailEvaluationReport(EvaluationReportProfDetailSearchDTO searchDTO);

    String createEvaluationReport(EvaluationReportProfReqDTO dto);

    String updateEvaluationReport(EvaluationReportProfReqDTO dto);

    SummaryReportProfResDTO getDetailSummaryReport(String internStdId);

    List<PlanDetailProfResDTO> getListWeekByInternPlan(String internPlanId);

    WeekReportProfResDTO getDetailWeekReport(WeekReportProfSearchDTO searchDTO);

}
