package com.winwintek.web.backend.api.domain.admin.supportCenter.evaluationReportCenter.service;

import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationReportCenter.EvaluationReportCenterDetailResDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationReportCenter.EvaluationReportCenterDetailSearchDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.evaluationReportCenter.EvaluationReportCenterReqDTO;

public interface EvaluationReportCenterService {

    EvaluationReportCenterDetailResDTO getDetailEvaluationReport(EvaluationReportCenterDetailSearchDTO searchDTO);

    String createEvaluationReport(EvaluationReportCenterReqDTO dto);

    String updateEvaluationReport(EvaluationReportCenterReqDTO dto);
}
