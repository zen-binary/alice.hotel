package com.winwintek.web.backend.api.domain.client.professor.guidanceReport.service;

import com.winwintek.web.backend.api.dto.client.professor.guidanceReport.GuidanceReportProfDetailResDTO;
import com.winwintek.web.backend.api.dto.client.professor.guidanceReport.GuidanceReportProfDetailSearchDTO;
import com.winwintek.web.backend.api.dto.client.professor.guidanceReport.GuidanceReportProfReqDTO;

public interface GuidanceReportProfService {

    GuidanceReportProfDetailResDTO getDetailGuidanceReport(GuidanceReportProfDetailSearchDTO searchDTO);

    String createGuidanceReport(GuidanceReportProfReqDTO dto);

    String updateGuidanceReport(GuidanceReportProfReqDTO dto);

}
